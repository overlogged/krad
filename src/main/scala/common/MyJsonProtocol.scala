package common

import game.GodHelper.{MsgChooseDecision, MsgChooseHero, MsgDecisionFeature, MsgSeenCard, ResCardDistribute, ResChooseDecision, ResChooseHero, ResDecisionFeature, ResInit, ResSeenCard, UserInfo}
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
  implicit val ResInitFormat = jsonFormat3(ResInit)
  implicit val ResCardDistributeFormat = jsonFormat2(ResCardDistribute)
  implicit val MsgChooseDecisionFormat = jsonFormat1(MsgChooseDecision)
  implicit val ResChooseDecisionFormat = jsonFormat1(ResChooseDecision)
  implicit val MsgDecisionFeatureFormat = jsonFormat2(MsgDecisionFeature)
  implicit val ResDecisionFeatureFormat = jsonFormat1(ResDecisionFeature)
  implicit val MsgSeenCardFormat = jsonFormat1(MsgSeenCard)
  implicit val ResSeenCardFormat = jsonFormat3(ResSeenCard)
}