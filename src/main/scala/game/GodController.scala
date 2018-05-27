package game

import common.MyJsonProtocol
import server.Server.RequestGame
import spray.json._

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

object GodController extends MyJsonProtocol{
  case class MsgChooseHero(sid:Int,hero:String)


}
