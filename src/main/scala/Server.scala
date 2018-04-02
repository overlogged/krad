/*
 * Server
 */

import java.io.{File, FileWriter}
import java.util.Date

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives

import scala.io.{Source, StdIn}
import spray.json._
import MyJsonProtocol._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import game.UserDB


object Server extends Directives with SprayJsonSupport {

  /// config and log
  final case class Config(db_user:String,
                          db_password:String,
                          db_host:String,
                          web_host:String,
                          web_port:Int,
                          email_host:String,
                          email_username:String,
                          email_password:String)

  val log_file = new FileWriter(new File("log.txt"))
  val config = Source.fromFile("config.txt")(io.Codec("UTF-8")).mkString.parseJson.convertTo[Config]

  def log[A](action:String,info:A): Unit = {
    val date = new Date()
    val s=s"[$action] $info #\t$date\n"
    print(s)
    log_file.write(s)
    log_file.flush()
  }


  /// route
  final case class RequestLogin(email:String,password:String)
  final case class RequestRegister(email:String,password:String)
  final case class RequestForgetPassword(email:String)

  def main(args: Array[String]): Unit = {
    if(args.length>0) {
      args(0) match {
        case "migrant" => {
          UserDB.migrant()
        }
        case _ => {
        }
      }
    }
    UserDB.connect()

    // http server
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()

    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    // restful api
    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to krad</h1>"))
        }
      }~
      path("session") {
        post {
          entity(as[RequestLogin]){req=>
            complete(req.toString)
          }
        }
      }~
      path("user") {
        post{
          entity(as[RequestRegister]){req=>
            complete(req.toString)
          }
        }
      }~
      path("user"/"forget"){
        post{
          entity(as[RequestForgetPassword]){req=>
            complete(req.toString)
          }
        }
      }

    val bindingFuture = Http().bindAndHandle(route, config.web_host, config.web_port)
    log("server",s"${config.web_host}:${config.web_port}")
    StdIn.readLine()                          // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind())                    // trigger unbinding from the port
      .onComplete(_ => system.terminate())    // and shutdown when done
  }

}
