package game

import common.MyJsonProtocol
import game.UserModel.{Stats, User}
import spray.json._

object GodHelper extends MyJsonProtocol{
  case class UserInfo(index:Int,nickName:String)
  case class ResInit(allUserInfo:Array[UserInfo],state:String,heroList:Array[String])
  case class MsgChooseHero(hero:String)
  case class ResChooseHero(state:String,heroChoices:Array[String],teamResult:Array[Int])
  case class ResCardDistribute(state:String,handCards:Array[Int])
  case class MsgChooseDecision(decision:Int)
  /*
   * about MsgChooseDecision
   * Int decision is the index in card array
   * if a player does not choose anything, then decision is -1
   */

  case class ResChooseDecision(state:String,handCards:Array[Int])
  case class MsgDecisionFeature(moveDirection:Int,fireTarget:Int)
  /*
   * about MsgDecisionFeature
   * moveDirection is the index of mapUnit that player chose to move towards
   * fireTarget is the index of target player(index in UserInfo) of firing
   * only one variable would be used in God in one turn
   */

  case class ResDecisionFeature(state:String)
  case class MsgSeenCard(seenCard:Int)
  /*
   * about MsgSeenCard
   * if the player choose to use "Seen Card",then seenCard is an integer of 1 to 3
   * 1 fot PAPER, 2 for SCISSORS, 3 for STONES
   * if the player dose not want to use seen cards, seenCard is 0
   * if the player does not have gamble card in hand, he must use seen card (already judged in God)
   */

  case class ResSeenCard(state:String,decisionChoices:Array[Int],seenCardChoices:Array[Int])
  case class MsgChooseGamble(gambleCard:Int,cardNum:Int)
  /*
   * gambleCard tells the index of gamble card the player use in this turn
   * cardNum tells how many cards this player choose to use in this turn
   */
  case class ResChooseGamble(state:String,gambleChoices:Array[Int],cardNumList:Array[Int],playerWinList:Array[Int],handCards:Array[Int])
  /*
   * gambleChoices tell what gamble choice players choose
   * cardNumList tell how many card they choose to use
   * playerWinList tells which players win and which lose
   * after gamble handcards should be refreshed
   */

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

  def toChooseDecision(state:String,handCards:Array[Int]):String = {
    ResChooseDecision(state,handCards).toJson.toString()
  }

  def getDecisionFeature(str:String):MsgDecisionFeature = {
    str.parseJson.convertTo[MsgDecisionFeature]
  }

  def toDecisionFeature(state:String):String = {
    ResDecisionFeature(state).toJson.toString()
  }

  def getSeenCard(str:String):MsgSeenCard = {
    str.parseJson.convertTo[MsgSeenCard]
  }

  def toSeenCard(state:String,decisionChoices:Array[Int],seenCardChoices:Array[Int]):String = {
    ResSeenCard(state,decisionChoices,seenCardChoices).toJson.toString()
  }

  def getChooseGamble(str:String):MsgChooseGamble = {
    str.parseJson.convertTo[MsgChooseGamble]
  }

  def toChooseGamble(state:String,gambleChoices:Array[Int],cardNumList:Array[Int],playerWinList:Array[Int],handCards:Array[Int]):String = {
    ResChooseGamble(state,gambleChoices,cardNumList,playerWinList,handCards).toJson.toString()
  }

  val ghostUser = User("ghost@ghost.com", "ghost", "figure1", 0, "password",Stats())
}
