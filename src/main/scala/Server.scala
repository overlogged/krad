/*
 * Server
 */

import java.io.{File, FileWriter}
import java.util.Date

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives

import scala.io.Source
import spray.json._
import MyJsonProtocol._
import game.UserDB


object Server extends Directives with SprayJsonSupport {

  case class Config(db_user:String,db_password:String,db_host:String)

  val log_file = new FileWriter(new File("log.txt"))
  val config = Source.fromFile("config.txt")(io.Codec("UTF-8")).mkString.parseJson.convertTo[Config]

  def log[A](action:String,info:A): Unit = {
    val date = new Date()
    val s=s"[$action] $info #\t$date\n"
    print(s)
    log_file.write(s)
    log_file.flush()
  }

  def main(args: Array[String]): Unit = {
    args(0) match {
      case "migrant" => {
        UserDB.migrant()
      }
      case _ => {
        UserDB.connect()
      }
    }
  }

}
