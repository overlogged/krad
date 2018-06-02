package game

import common.MyJsonProtocol
import server.Server.RequestGame
import spray.json._

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

object GodHelper extends MyJsonProtocol{
  case class ResInit(state:String,heroList:Array[String])
  case class MsgChooseHero(sid:Int,hero:String)
  case class ResChooseHero(state:String,count:Int,result:String)

  def toInit(state:String,heroList:Array[String]):String = {
    ResInit(state,heroList).toJson.toString
  }

  def getChooseHero(str:String):MsgChooseHero = {
    str.parseJson.convertTo[MsgChooseHero]
  }

  def toChooseHero(state:String,count:Int,result:String):String = {
    ResChooseHero(state,count,result).toJson.toString
  }
}
