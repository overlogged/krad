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

  case class User(id: Int, email: String, password: String, stats: Stats)

  object UserDB {

    val db:MongoDB = connect()
    val col_users:DBCollection = db.getCollection("user")

    def connect(): MongoDB = {
      val url = MongoClientURI(s"mongodb://${config.db_user}:${config.db_password}@${config.db_host}/?authMechanism=SCRAM-SHA-1")
      val mongoClient =  MongoClient(url)
      log("start","connect db")
      return mongoClient.getDB("krad")
    }

    def register(email: String, password: String) = {
      Some(Unit)
        .guard(email.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$"""))
        .guard(password.matches("""[a-zA-Z0-9]{6,18}"""))
        .flatMap { _ =>
          Some(col_users.insert(MongoDBObject(
            "email"->email,
            "password"->sha1Hex(password)
          )))
        }
    }
  }

}