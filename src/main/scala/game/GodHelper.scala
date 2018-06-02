package game

import common.MyJsonProtocol
import server.Server.RequestGame
import spray.json._

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

object GodHelper extends MyJsonProtocol{
  case class ResInit(nextStage:String,heroList:Array[String])
  case class MsgChooseHero(sid:Int,hero:String)
  case class ResChooseHero(count:Int,result:String)

  def toInit(nextStage:String,heroList:Array[String]):String = {
    ResInit(nextStage,heroList).toJson.toString
  }

  def getChooseHero(str:String):MsgChooseHero = {
    str.parseJson.convertTo[MsgChooseHero]
  }

  def toChooseHero(count:Int,result:String):String = {
    ResChooseHero(count,result).toJson.toString
  }
}
