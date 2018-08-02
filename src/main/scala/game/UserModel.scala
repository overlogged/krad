
package game

import spray.json._
import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoDB}
import com.mongodb.casbah.query.Imports._
import com.mongodb.DBCollection
import common.MyJsonProtocol
import org.apache.commons.codec.digest.DigestUtils.sha1Hex
import common.MyUtils._
import server.Server.{config, log}

/**
  * UserDB
  * hold players' information
  * long-lasting static database
  * 1. auth
  * 2. stats
 */
object UserModel extends MyJsonProtocol{
  /**
    * statistics
    */
  final case class Stats(score: Int)

  object Stats {
    def apply(score: Int = 0): Stats = new Stats(score)
  }


  /**
    * @param avatar image url
    * @param gender boy:0 girl:1
    * @param password encrypted
    */
  final case class User(email: String,
                        nickname: String,
                        avatar: String,
                        gender: Int,
                        password: String,
                        stats: Stats) {
    def uid:String = email
  }
  object User {
    val boy = 0
    val girl = 1

    def apply(email: String,
              nickname: String,
              avatar: String,
              gender: Int,
              password: String,
              stats: Stats): User = new User(email, nickname, avatar, gender, password, stats)
  }

  // database
  lazy val db: MongoDB = connect()
  lazy val col_users: DBCollection = db.getCollection("user")

  /**
    * connect to db
    * @return MongoDB
    */
  def connect():MongoDB = {
    val url = MongoClientURI(s"mongodb://${config.db_user}:${config.db_password}@${config.db_host}/?authMechanism=SCRAM-SHA-1")
    val mongoClient = MongoClient(url)
    log("start", "connect db")
    mongoClient.getDB("krad")
  }

  /**
    * migrant db
    */
  def migrant():Unit = {
    val url = MongoClientURI(s"mongodb://${config.db_host}/?authMechanism=SCRAM-SHA-1")
    val mongoClient = MongoClient(url)
    val admin_db = mongoClient.getDB("admin")
    val cmd = s"db.createUser({user:'${config.db_user}',pwd:'${config.db_password}',roles:[{role:'root',db:'admin'}]})"
    log("info", "please start mongodb with `auth=false`")
    log("mongodb", cmd)
    admin_db.eval(cmd)
    register("ghost@ghost.com","ghost","figure1",0,"ghost")
    log("migrant", "init db")
    log("info", "please restart mongodb with `auth=true`")
  }

  /**
    * encrypt
    * @param str src
    * @return dest
    */
  private def encrypt(str: String):String = sha1Hex(str)

  /**
    * register
    * will check the input
    * @return None if failed
    */
  def register(email: String, nickname: String, avatar: String, gender: Int, password: String):Option[Unit] = {
    Some(())
      .guard(email.is_valid_email)
      .guard(nickname.is_valid_nickname)
      .guard(avatar.is_valid_avatar)
      .guard(password.is_valid_password)
      .guard(col_users.find(MongoDBObject("email" -> email)).count() == 0)
      .flatMap { _ =>
        Some(col_users.insert(MongoDBObject(
          "email" -> email,
          "nickname" -> nickname,
          "avatar" -> avatar,
          "gender" -> gender,
          "password" -> encrypt(password), // todo: encrypt the password at frontend
          "stats" -> Stats().toJson.toString()
        )))
      }.succ()
  }

  /**
    * login
    * will check the input
    * @return None if failed
    */
  def login(email: String, password: String):Option[User] = {
    Some(())
      .guard(email.is_valid_email)
      .guard(password.is_valid_password)
      .flatMap { _ => Option(col_users.findOne(MongoDBObject("email" -> email, "password" -> encrypt(password)))) }
      .flatMap { one =>
        for (
          email <- one.getAs[String]("email");
          nickname <- one.getAs[String]("nickname");
          avatar <- one.getAs[String]("avatar");
          gender <- one.getAs[Int]("gender");
          stats <- one.getAs[String]("stats").flatMap { x => Some(x.parseJson.convertTo[Stats]) }
        ) yield User(email, nickname, avatar, gender, "", stats)
      }
  }

  /**
    * get profile
    */
  def getProfile(email:String):Option[User] = {
    Option(col_users.findOne(MongoDBObject("email" -> email))).flatMap{ one =>
      for (
        email <- one.getAs[String]("email");
        nickname <- one.getAs[String]("nickname");
        avatar <- one.getAs[String]("avatar");
        gender <- one.getAs[Int]("gender");
        stats <- one.getAs[String]("stats").flatMap { x => Some(x.parseJson.convertTo[Stats]) }
      ) yield User(email, nickname, avatar, gender, "", stats)
    }
  }

  /**
    * change password
    * @return None if failed
    */
  def changePassword(email: String, old_password: String, new_password: String) : Option[Unit]= {
    Some(())
      .guard(email.is_valid_email)
      .guard(old_password.is_valid_password)
      .guard(new_password.is_valid_password)
      .flatMap { _ =>
        Option(col_users.findOne(MongoDBObject(
          "email" -> email,
          "password" -> encrypt(old_password)
        )))
      }
      .also { _ =>
        col_users.update(
          MongoDBObject("email" -> email),
          $set("password" -> encrypt(new_password))
        )
      }
      .succ()
  }

  /**
    * set new password
    * will check the input
    * @return None if failed
    */
  def setNewPassword(email:String,password:String):Option[Unit] = {
    Some(())
      .guard(email.is_valid_email)
      .guard(password.is_valid_password)
      .also { _ =>
        col_users.update(
          MongoDBObject("email"->email),
          $set("password"->encrypt(password))
        )
      }
  }

  /**
    *
    */
  def checkUser(email:String):Option[Unit] = {
    Some(())
      .guard(email.is_valid_email)
      .guard(col_users.find(MongoDBObject("email"->email)).count()==1)
  }


  /**
    * change profile
    * will check the input
    * @return None if failed
    */
  def changeProfile(email: String, nickname: String, avatar: String, gender: Int):Option[Unit] = {
    Some(())
      .guard(email.is_valid_email)
      .guard(nickname.is_valid_nickname)
      .guard(avatar.is_valid_avatar)
      .also { _ =>
        col_users.update(MongoDBObject("email" -> email), $set(
          "nickname" -> nickname,
          "avatar" -> avatar,
          "gender" -> gender,
          "stats" -> Stats().toJson.toString()
        ))
      }
  }

  /**
    * change statistics
    * @param delta delta
    * @return None if failed
    */
  def changeStat(email: String, delta:Int):Option[Unit] = {
    Some(())
      .guard(email.is_valid_email)
      .flatMap { _ =>
        col_users.findOne(MongoDBObject("email" -> email))
          .getAs[String]("stats")
          .flatMap { x => Some(x.parseJson.convertTo[Stats]) }
      }
      .also { old_stats =>
        col_users.update(
          MongoDBObject("email" -> email),
          $set("stats" -> Stats(old_stats.score+delta).toJson.toString())
        )
      }
      .succ()
  }
}
