package server

/*
 * Server
 */

import java.io.{File, FileWriter}
import java.util.Date
import scala.io.Source
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import spray.json._
import game.UserDB
import server.MyJsonProtocol._


object Server extends Directives with SprayJsonSupport{


  // config and log
  final case class Config(db_user:String,
                          db_password:String,
                          db_host:String,
                          web_host:String,
                          web_port:Int,
                          email_host:String,
                          email_username:String,
                          email_password:String)

  val log_file = new FileWriter(new File("log.txt"),true)
  val config = Source.fromFile("config.txt")(io.Codec("UTF-8")).mkString.parseJson.convertTo[Config]

  def log[A](action:String,info:A): Unit = {
    val date = new Date()
    val s=s"[$action] $info #\t$date\n"
    print(s)
    log_file.write(s)
    log_file.flush()
  }

  // requests
  final case class RequestLogin(email:String,password:String)
  final case class RequestRegister(email:String,
                                   nickname: String,
                                   avatar: String,
                                   gender: Int,
                                   password: String)
  final case class RequestForgetPassword(email:String)
  final case class RequestSetNewPassword(sid:Int,new_password:String)
  final case class RequestChangePassword(sid:Int,old_password:String,new_password:String)
  final case class RequestChangeProfile(sid:Int,
                                        nickname: String,
                                        avatar: String,
                                        gender: Int)

  // http server
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  // api
  val route =
    path("hello") {
      get {
        log("get","hello")
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to krad</h1>"))
      }
    }~
    path("session") {
      post {
        entity(as[RequestLogin]){req=>
          log("post","session")
          complete(req.toString)
        }
      }
    }~
    path("user") {
      post{
        entity(as[RequestRegister]){req=>
          log("post","user")
          complete(req.toString)
        }
      }
    }~
    path("user"/"forget"){
      post{
        entity(as[RequestForgetPassword]){req=>
          log("post","user/forget")
          complete(req.toString)
        }
      }
    }


  // main
  def main(args: Array[String]): Unit = {
    if(args.length > 0 && args(0)=="migrant") {
      UserDB.migrant()
    } else {
      UserDB.connect()
    }

    Http().bindAndHandle(route, config.web_host, config.web_port)
    log("bind",s"${config.web_host}:${config.web_port}")
  }

}
