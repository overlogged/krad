import MyJsonProtocol._
import spray.json._
import com.mongodb.casbah.{MongoClient,MongoClientURI,MongoDB}
import com.mongodb.casbah.query.Imports._
import Server.{config,log}
import org.apache.commons.codec.digest.DigestUtils.sha1Hex
import MyUtils._


package game {

  import com.mongodb.DBCollection

  /*
 * hold players' information
 * long-lasting static database
 * 1. auth
 * 2. stats
 */


  case class Stats(score: Int)
  
  object Stats {
    def apply(score: Int = 0): Stats = new Stats(score)
  }

  case class User(id: Int, email: String, password: String, stats: Stats)

  object UserDB {
    lazy val db:MongoDB = connect()
    lazy val col_users:DBCollection = db.getCollection("user")


    def connect() = {
      val url = MongoClientURI(s"mongodb://${config.db_user}:${config.db_password}@${config.db_host}/?authMechanism=SCRAM-SHA-1")
      val mongoClient =  MongoClient(url)
      log("start","connect db")
      mongoClient.getDB("krad")
    }

    def migrant() = {
      val url = MongoClientURI(s"mongodb://${config.db_host}/?authMechanism=SCRAM-SHA-1")
      val mongoClient = MongoClient(url)
      val admin_db = mongoClient.getDB("admin")
      val cmd = s"db.createUser({user:'${config.db_user}',pwd:'${config.db_password}',roles:[{role:'root',db:'admin'}]})"
      log("info","please start mongodb with `auth=false`")
      log("mongodb",cmd)
      admin_db.eval(cmd)
      log("migrant","init db")
      log("info","please restart mongodb with `auth=true`")
    }

    def register(email: String, password: String) = {
      Some(Unit)
        .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
        .guard(password.matches("""[a-zA-Z0-9]{6,18}"""))
        .guard(col_users.findOne(MongoDBObject("email"->email))==null)
        .flatMap { _ =>
          Some(col_users.insert(MongoDBObject(
            "email"->email,
            "password"->sha1Hex(password),
            "stats"->Stats().toJson.toString()
          )))
        }
    }

    def login(email:String,password:String) = {
      Some(Unit)
        .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
        .guard(password.matches("""[a-zA-Z0-9]{6,18}"""))
        .flatMap { _ => Option(col_users.findOne(MongoDBObject("email"->email,"password"->sha1Hex(password))))}
        .flatMap { _.getAs[String]("stats").flatMap{x=>Some(x.parseJson.convertTo[Stats])}}
    }
  }

}