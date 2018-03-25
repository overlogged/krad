import Server.Config
import game.{Stats, User}
import spray.json.DefaultJsonProtocol

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val ConfigFormat = jsonFormat3(Config)
  implicit val StatsFormat = jsonFormat1(Stats)
  implicit val UserFormat = jsonFormat4(User)
}

