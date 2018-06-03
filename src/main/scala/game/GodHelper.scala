package game

import common.MyJsonProtocol
import server.Server.RequestGame
import spray.json._

import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

object GodHelper extends MyJsonProtocol{
  case class UserInfo(index:Int,nickName:String)
  case class ResInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String])
  case class MsgChooseHero(sid:Int,hero:String)
  case class ResChooseHero(state:String,heroChoices:Array[String])
  case class ResTeamDivide(state:String,teamResult:Array[Int])

  def toInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String]):String = {
    ResInit(allUserInfo,state,heroList).toJson.toString
  }

  def getChooseHero(str:String):MsgChooseHero = {
    str.parseJson.convertTo[MsgChooseHero]
  }

  def toChooseHero(state:String,heroChoices:Array[String]):String = {
    ResChooseHero(state,heroChoices).toJson.toString
  }

  def toTeamDivide(state:String,teamResult:Array[Int]):String = {
    ResTeamDivide(state,teamResult).toJson.toString
  }
}
