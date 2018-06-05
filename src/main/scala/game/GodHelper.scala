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
  case class ResCardDistribute(state:String, handCards:Array[Int])
  case class MsgChooseDecision(decision:Int)
  /* about MsgChooseDecision
   * Int decision is the index in card array
   * if a player does not choose anything, then decision is -1
   */
  case class ResChooseDecision(state:String)
  case class MsgDecisionFeature(moveDirection:Int,fireTarget:Int)
  /* about MsgDecisionFeature
   * moveDirection is the index of mapUnit that player chose to move towards
   * fireTarget is the index of target player(index in UserInfo) of firing
   * only one variable would be used in God in one turn
   */
  case class ResDecisionFeature(state:String)

  def toInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String]):String = {
    ResInit(allUserInfo,state,heroList).toJson.toString
  }

  def getChooseHero(str:String):MsgChooseHero = {
    str.parseJson.convertTo[MsgChooseHero]
  }

  def toChooseHero(state:String,heroChoices:Array[String],teamResult:Array[Int]):String = {
    ResChooseHero(state,heroChoices,teamResult).toJson.toString
  }

  def toCardDistribute(state:String,handCards:Array[Int]):String = {
    ResCardDistribute(state,handCards).toJson.toString
  }

  def getChooseDecision(str:String):MsgChooseDecision = {
    str.parseJson.convertTo[MsgChooseDecision]
  }

  def toChooseDecision(state:String):String = {
    ResChooseDecision(state).toJson.toString()
  }

  def getDecisionFeature(str:String):MsgDecisionFeature = {
    str.parseJson.convertTo[MsgDecisionFeature]
  }

  def toDecisionFeature(state:String):String = {
    ResDecisionFeature(state).toJson.toString()
  }

  val ghostUser = User("ghost@ghost.com", "ghost", "figure1", 0, "password",Stats())
}
