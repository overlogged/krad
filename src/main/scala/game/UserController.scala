package game

import common.Mail
import common.MyUtils._
import game.SessionController.{createSession, map}
import game.UserModel.User
import server.Server.{RequestChangePassword, RequestChangeProfile, RequestForgetPassword, RequestSetNewPassword, config}
import server.Server.executionContext

import scala.concurrent.Future

object UserController {
  /**
    * forget password
    */
  def forget(req:RequestForgetPassword) : Option[Future[Unit]] = {
    val uid = req.email
    UserModel.checkUser(uid).map { _ =>
      val sid = createSession(uid)
      val title = "[Krad] 重置密码"
      val text = s"请点击以下链接以重置密码： ${config.web_url}changepassword.html?sid=$sid"
      Mail.send(req.email, title, text)
    }
  }

  /**
    * set new password
    */
  def setNewPassword(req:RequestSetNewPassword) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
      UserModel.setNewPassword(uid, req.new_password)
    }
  }

  /**
    * change password
    */
  def changePassword(req:RequestChangePassword) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
      UserModel.changePassword(uid,req.old_password,req.new_password)
    }
  }

  /**
    * change profile
    */
  def changeProfile(req:RequestChangeProfile) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
      UserModel.changeProfile(uid,req.nickname,req.avatar,req.gender)
    }
  }

  /**
    * get profile
    */
  def getProfile(sid:Int): Option[User] = {
    map.getB(sid).flatMap { uid =>
      UserModel.getProfile(uid)
    }
  }
}
