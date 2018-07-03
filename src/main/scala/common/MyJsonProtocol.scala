package common

import game.GodHelper.{MsgChooseDecision, MsgChooseGamble, MsgChooseHero, MsgDesertAccount, MsgSeenCard, ResCardDistribute, ResChooseDecision, ResChooseGamble, ResChooseHero, ResDepositAccount, ResDesertAccount, ResElemAccount, ResFireAccount, ResGameOver, ResHumanVictory, ResInfectionAccount, ResInit, ResMoveAccount, ResSeenCard, ResSkillsAccount, ResWinJudge, UserInfo}
import game.SessionController.Session
import game.UserModel.{Stats, User}
import server.Server.{Config, RequestChangePassword, RequestChangeProfile, RequestForgetPassword, RequestGame, RequestLogin, RequestMatch, RequestRegister, RequestSetNewPassword}
import spray.json.DefaultJsonProtocol

/**
  * json formatter
  */
trait MyJsonProtocol extends DefaultJsonProtocol {
  implicit val ConfigFormat = jsonFormat10(Config)
  implicit val StatsFormat = jsonFormat1(Stats.apply)
  implicit val UserFormat = jsonFormat6(User.apply)
  implicit val RequestLoginFormat = jsonFormat2(RequestLogin)
  implicit val RequestRegisterFormat = jsonFormat5(RequestRegister)
  implicit val RequestForgetPasswordFormat = jsonFormat1(RequestForgetPassword)
  implicit val RequestSetNewFormat = jsonFormat2(RequestSetNewPassword)
  implicit val RequestChangePasswordFormat = jsonFormat3(RequestChangePassword)
  implicit val RequestChangeProfileFormat = jsonFormat4(RequestChangeProfile)
  implicit val RequestMatchFormat = jsonFormat1(RequestMatch)
  implicit val SessionFormat = jsonFormat3(Session)
  implicit val MsgChooseHeroFormat = jsonFormat1(MsgChooseHero)
  implicit val RequestGameFormat = jsonFormat2(RequestGame)
  implicit val ResUserInfoFormat = jsonFormat2(UserInfo)
  implicit val ResChooseHeroFormat = jsonFormat3(ResChooseHero)
  implicit val ResInitFormat = jsonFormat4(ResInit)
  implicit val ResCardDistributeFormat = jsonFormat4(ResCardDistribute)
  implicit val MsgChooseDecisionFormat = jsonFormat3(MsgChooseDecision)
  implicit val ResChooseDecisionFormat = jsonFormat2(ResChooseDecision)
  implicit val MsgSeenCardFormat = jsonFormat1(MsgSeenCard)
  implicit val ResSeenCardFormat = jsonFormat3(ResSeenCard)
  implicit val MsgChooseGambleFormat = jsonFormat1(MsgChooseGamble)
  implicit val ResChooseGambleFormat = jsonFormat2(ResChooseGamble)
  implicit val ResWinJudgeFormat = jsonFormat4(ResWinJudge)
  implicit val ResDepositAccountFormat = jsonFormat2(ResDepositAccount)
  implicit val ResSkillsAccountFormat = jsonFormat1(ResSkillsAccount)
  implicit val ResFireAccountFormat = jsonFormat2(ResFireAccount)
  implicit val ResMoveAccountFormat = jsonFormat2(ResMoveAccount)
  implicit val ResElemAccountFormat = jsonFormat2(ResElemAccount)
  implicit val ResHumanVictoryFormat = jsonFormat1(ResHumanVictory)
  implicit val ResInfectionAccountFormat = jsonFormat2(ResInfectionAccount)
  implicit val MsgDesertAccountFormat = jsonFormat1(MsgDesertAccount)
  implicit val ResDesertAccountFormat = jsonFormat3(ResDesertAccount)
  implicit val ResGameOverFormat = jsonFormat2(ResGameOver)
}