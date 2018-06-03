package game

import common.MyJsonProtocol
import server.Server.RequestGame
import spray.json._

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

object GodHelper extends MyJsonProtocol{
  case class UserInfo(index:Int,nickName:String,hero:String)
  case class ResInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String])
  case class MsgChooseHero(sid:Int,hero:String)
  case class ResChooseHero(state:String,allUserInfo:Array[UserInfo])
  case class ResTeamDivide(state:String,allUserInfo:Array[UserInfo])

  def toInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String]):String = {
    ResInit(allUserInfo,state,heroList).toJson.toString
  }

  def getChooseHero(str:String):MsgChooseHero = {
    str.parseJson.convertTo[MsgChooseHero]
  }

  def toChooseHero(state:String,allUserInfo:Array[UserInfo]):String = {
    ResChooseHero(state,allUserInfo).toJson.toString
  }

  def toTeamDivide(state:String,allUserInfo:Array[UserInfo]):String = {
    ResTeamDivide(state,allUserInfo).toJson.toString
  }
}
