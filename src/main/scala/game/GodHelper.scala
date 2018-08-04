package game

import common.MyJsonProtocol
import game.UserModel.{Stats, User}
import spray.json._

object GodHelper extends MyJsonProtocol{
  case class UserInfo(index:Int,nickName:String)
  case class ResInit(allUserInfo:Array[UserInfo],state:String,myIndex:Int,heroList:Array[String])
  case class MsgChooseHero(hero:String)
  case class ResChooseHero(state:String,heroChoices:Array[String],teamResult:Array[Int])
  case class ResCardDistribute(state:String,handCards:Array[Int],availableFireTarget:Array[Int],availableMoveDirection:Array[Int])
  /*
   * availableFireTarget tells which players can be fired
   * eg: if(availableFireTarget[i] = 1, it means that allPlayers[i] is in range
   * availableMoveDirecion is the same
   */

  case class MsgChooseDecision(decision:Int,moveDirection:Int,fireTarget:Int)
  /*
   * about MsgChooseDecision
   * Int decision is the index in card array
   * if a player does not choose anything, then decision is -1
   * moveDirection is the direction of the player chooses (0-7)
   * fireTarget is the index of target player(index in UserInfo) of firing
   * only one variable would be used in God in one turn
   */
  case class ResChooseDecision(state:String,handCards:Array[Int])
  case class MsgSeenCard(seenCard:Int)
  /*
   * about MsgSeenCard
   * if the player choose to use "Seen Card",then seenCard is an integer of 1 to 3
   * 1 fot PAPER, 2 for SCISSORS, 3 for ROCKS
   * if the player dose not want to use seen cards, seenCard is 0
   * if the player does not have gamble card in hand, he must use seen card (already judged in God)
   */

  case class ResSeenCard(state:String,decisionChoices:Array[Int],seenCardChoices:Array[Int])
  case class MsgChooseGamble(gambleCard:Array[Int])
  /*
   * gambleCard is the gamble choice that one player chooses to use
   */
  case class ResChooseGamble(state:String,handCards:Array[Int])
  case class ResWinJudge(state:String,gambleChoices:Array[Int],cardNumList:Array[Int],playerWinList:Array[Int],fireList:Array[Int])
  /*
   * gambleChoices tell what gamble choice players choose
   * cardNumList tell how many card they choose to used
   * playerWinList tells which players win and which lose
   * after gamble handcards should be refreshed
   */
  case class ResAccount(state:String,
                     eneregyList:Array[Int],
                     healthPointList:Array[Int],
                     locationList:Array[Int],
                     elemList:Array[Int],
                     teamList:Array[Int]
                    )

  case class ResDepositAccount(state:String,eneregyList:Array[Int])
  case class ResSkillsAccount(state:String)
  case class ResFireAccount(state:String,healthPointList:Array[Int])
  case class ResMoveAccount(state:String,locationList:Array[Int])
  case class ResElemAccount(state:String,elemList:Array[Int])
  case class ResHumanVictory(state:String)
  case class ResInfectionAccount(state:String,teamList:Array[Int])

  case class MsgDesertAccount(desertCardList:Array[Int])
  case class ResDesertAccount(state:String,energy:Int,handCards:Array[Int])
  case class ResGameOver(state:String,scoreList:Array[Int])

  def toInit(allUserInfo:Array[UserInfo],state:String,myIndex:Int,heroList:Array[String]):String = {
    ResInit(allUserInfo,state,myIndex,heroList).toJson.toString
  }

  def getChooseHero(str:String):MsgChooseHero = {
    str.parseJson.convertTo[MsgChooseHero]
  }

  def toChooseHero(state:String,heroChoices:Array[String],teamResult:Array[Int]):String = {
    ResChooseHero(state,heroChoices,teamResult).toJson.toString
  }

  def toCardDistribute(state:String,handCards:Array[Int],availableFireTarget:Array[Int],availableMoveDirection:Array[Int]):String = {
    ResCardDistribute(state,handCards,availableFireTarget,availableMoveDirection).toJson.toString
  }

  def getChooseDecision(str:String):MsgChooseDecision = {
    str.parseJson.convertTo[MsgChooseDecision]
  }

  def toChooseDecision(state:String,handCards:Array[Int]):String = {
    ResChooseDecision(state,handCards).toJson.toString()
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

  def toChooseGamble(state:String,handCards:Array[Int]):String = {
    ResChooseGamble(state,handCards).toJson.toString()
  }

  def toWinJudge(state:String,gambleChoices:Array[Int],cardNumList:Array[Int],playerWinList:Array[Int],fireList:Array[Int]):String = {
    ResWinJudge(state,gambleChoices,cardNumList,playerWinList,fireList).toJson.toString()
  }

  def toDepositAccount(state:String,energyList:Array[Int]):String = {
    ResDepositAccount(state,energyList).toJson.toString()
  }

  def toSkillsAccount(state:String):String = {
    ResSkillsAccount(state).toJson.toString()
  }

  def toFireAccount(state:String,healthPointList:Array[Int]):String = {
    ResFireAccount(state,healthPointList).toJson.toString()
  }

  def toMoveAccount(state:String,locationList:Array[Int]):String = {
    ResMoveAccount(state,locationList).toJson.toString()
  }

  def toElemAccount(state:String,elemList:Array[Int]):String = {
    ResMoveAccount(state,elemList).toJson.toString()
  }

  def toHumanVictory(state:String):String = {
    ResHumanVictory(state).toJson.toString()
  }

  def toAccount(state:String,eneregyList:Array[Int], healthPointList:Array[Int], locationList:Array[Int], elemList:Array[Int], teamList:Array[Int]): String ={
    ResAccount(state,eneregyList,healthPointList,locationList,elemList,teamList).toJson.toString()
  }

  def toInfectionAccount(state:String,teamList:Array[Int]):String = {
    ResInfectionAccount(state,teamList).toJson.toString()
  }
  /*
   * teamList: 0 for Human ,1 for Zombie
   */

  def getDesertAccount(str:String):MsgDesertAccount = {
    str.parseJson.convertTo[MsgDesertAccount]
  }

  def toDesertAccount(state:String,energy:Int,handCards:Array[Int]):String = {
    ResDesertAccount(state,energy,handCards).toJson.toString()
  }

  def toGameOver(state:String,scoreList:Array[Int]):String = {
    ResGameOver(state,scoreList).toJson.toString()
  }

  val ghostUser = User("ghost@ghost.com", "ghost", "figure1", 0, "password",Stats())
}
