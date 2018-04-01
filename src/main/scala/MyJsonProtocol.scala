import Server.{Config, RequestForgetPassword, RequestLogin, RequestRegister}
import game.{Stats, User}
import spray.json.DefaultJsonProtocol

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val ConfigFormat = jsonFormat8(Config)
  implicit val StatsFormat = jsonFormat1(Stats.apply)
  implicit val UserFormat = jsonFormat3(User)
  implicit val RequestLoginFormat = jsonFormat2(RequestLogin)
  implicit val RequestRegisterFormat = jsonFormat2(RequestRegister)
  implicit val RequestForgetPasswordFormat = jsonFormat1(RequestForgetPassword)
}

