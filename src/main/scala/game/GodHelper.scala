package game

import common.MyJsonProtocol
import game.UserModel.{Stats, User}
import spray.json._

import concurrent.ExecutionContext.Implicits.global

object GodHelper extends MyJsonProtocol{
  case class UserInfo(index:Int,nickName:String)
  case class ResInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String])
  case class MsgChooseHero(hero:String)
  case class ResChooseHero(state:String,heroChoices:Array[String],teamResult:Array[Int])
  case class ResCardDistribute(state:String,cardAcq:Array[Int])
  case class MsgChooseDecision(decision:Int,cardNum:Int)
  case class ResChooseDecision(state:String)

  def toInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String]):String = {
    ResInit(allUserInfo,state,heroList).toJson.toString
  }

  def getChooseHero(str:String):MsgChooseHero = {
    str.parseJson.convertTo[MsgChooseHero]
  }

  def toChooseHero(state:String,heroChoices:Array[String],teamResult:Array[Int]):String = {
    ResChooseHero(state,heroChoices,teamResult).toJson.toString
  }

  def toCardDistribute(state:String,cardAcq:Array[Int]):String = {
    ResCardDistribute(state,cardAcq).toJson.toString
  }

  def getChooseDecision(str:String):MsgChooseDecision = {
    str.parseJson.convertTo[MsgChooseDecision]
  }

  def toChooseDecision(state:String):String = {
    ResChooseDecision(state).toJson.toString()
  }

  val ghostUser = User("ghost@ghost.com", "ghost", "figure1", 0, "password",Stats())
}
