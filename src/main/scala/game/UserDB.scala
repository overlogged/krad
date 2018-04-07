

/*
 * hold players' information
 * long-lasting static database
 * 1. auth
 * 2. stats
 */
package game

import server.MyJsonProtocol._
import spray.json._
import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoDB}
import com.mongodb.casbah.query.Imports._
import server.Server.{config, log}
import org.apache.commons.codec.digest.DigestUtils.sha1Hex
import server.MyUtils._
import com.mongodb.DBCollection

// statistics
final case class Stats(score: Int)

object Stats {
  def apply(score: Int = 0): Stats = new Stats(score)
}


// user information
// related function : login register changePassword
final case class User(email: String,
                      nickname: String,
                      avatar: String,
                      gender: Int,
                      password: String,
                      stats: Stats)

object User {
  val boy = 0
  val girl = 1
}



object UserDB {

  // database
  lazy val db: MongoDB = connect()
  lazy val col_users: DBCollection = db.getCollection("user")

  def connect() = {
    val url = MongoClientURI(s"mongodb://${config.db_user}:${config.db_password}@${config.db_host}/?authMechanism=SCRAM-SHA-1")
    val mongoClient = MongoClient(url)
    log("start", "connect db")
    mongoClient.getDB("krad")
  }

  def migrant() = {
    val url = MongoClientURI(s"mongodb://${config.db_host}/?authMechanism=SCRAM-SHA-1")
    val mongoClient = MongoClient(url)
    val admin_db = mongoClient.getDB("admin")
    val cmd = s"db.createUser({user:'${config.db_user}',pwd:'${config.db_password}',roles:[{role:'root',db:'admin'}]})"
    log("info", "please start mongodb with `auth=false`")
    log("mongodb", cmd)
    admin_db.eval(cmd)
    log("migrant", "init db")
    log("info", "please restart mongodb with `auth=true`")
  }

  // user
  def encrypt(str: String) = sha1Hex(str)

  def register(email: String, nickname: String, avatar: String, gender: String, password: String) = {
    Some(Unit)
      .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
      .guard(!nickname.contains("{};:'"))
      //      .guard(avatar)  // todo: check avatar
      .guard(gender == "boy" || gender == "girl")
      .guard(password.matches("""[a-zA-Z0-9]{6,18}""")) // todo: if encrypted then change the rule
      .guard(col_users.find(MongoDBObject("email" -> email)).count() == 0)
      .flatMap { _ =>
        Some(col_users.insert(MongoDBObject(
          "email" -> email,
          "nickname" -> nickname,
          "avatar" -> avatar,
          "gender" -> (if (gender == "boy") User.boy else User.girl),
          "password" -> encrypt(password), // todo: encrypt the password at frontend
          "stats" -> Stats().toJson.toString()
        )))
      }
  }

  def login(email: String, password: String) = {
    Some(Unit)
      .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
      .guard(password.matches("""[a-zA-Z0-9]{6,18}"""))
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

  def changePassword(email: String, old_password: String, new_password: String) = {
    Some(Unit)
      .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
      .guard(old_password.matches("""[a-zA-Z0-9]{6,18}"""))
      .guard(new_password.matches("""[a-zA-Z0-9]{6,18}"""))
      .flatMap { _ =>
        Option(col_users.findOne(MongoDBObject(
          "email" -> email,
          "password" -> encrypt(old_password)
        )))
      }
      .map { _ =>
        col_users.update(
          MongoDBObject("email" -> email),
          $set("password" -> encrypt(new_password))
        )
      }
  }

  def changeProfile(email: String, nickname: String, avatar: String, gender: String) = {
    Some(Unit)
      .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
      .guard(!nickname.contains("{};:'"))
      //      .guard(avatar)  // todo: check avatar
      .guard(gender == "boy" || gender == "girl")
      .map { _ =>
        col_users.update(MongoDBObject("email" -> email), $set(
          "nickname" -> nickname,
          "avatar" -> avatar,
          "gender" -> (if (gender == "boy") User.boy else User.girl),
          "stats" -> Stats().toJson.toString()
        ))
      }
  }

  def changeStat(email: String, f: Stats => Stats) = {
    Some(Unit)
      .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
      .flatMap { _ =>
        col_users.findOne(MongoDBObject("email" -> email))
          .getAs[String]("stats")
          .flatMap { x => Some(x.parseJson.convertTo[Stats]) }
      }
      .map { old_stats =>
        col_users.update(
          MongoDBObject("email" -> email),
          $set("stats" -> f(old_stats).toJson.toString())
        )
      }
  }
}
