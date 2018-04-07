package server

import game.{Stats, User}
import server.Server.{Config, RequestForgetPassword, RequestLogin, RequestRegister}
import spray.json.DefaultJsonProtocol

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val ConfigFormat = jsonFormat8(Config)
  implicit val StatsFormat = jsonFormat1(Stats.apply)
  implicit val UserFormat = jsonFormat6(User.apply)
  implicit val RequestLoginFormat = jsonFormat2(RequestLogin)
  implicit val RequestRegisterFormat = jsonFormat2(RequestRegister)
  implicit val RequestForgetPasswordFormat = jsonFormat1(RequestForgetPassword)
}

