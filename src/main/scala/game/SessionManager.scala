package game

import java.util.Date

import game.UserDB.User
import server.{Bimap, Mail}
import server.Server.{RequestChangePassword, RequestChangeProfile, RequestForgetPassword, RequestLogin, RequestRegister, RequestSetNewPassword, config}
import server.MyUtils._

object SessionManager {

  /**
    * Session
    * @param sid session id
    * @param uid user id (email)
    * @param user user's information
    */
  final case class Session(sid:Int,uid:String,user:User)

  // map: sid - uid
  val map = new Bimap[Int,String]()

  /**
    * create a session or return an existing session for user
    * @param uid user id
    * @return session id
    */
  def createSession(uid:String):Int = {
    val sid = s"${new Date().getTime}$uid".hashCode
    map.getA(uid).getOrElse {
      if(map.getB(sid).isEmpty){
        map.set(sid,uid)
        sid
      } else {
        Thread.sleep(10)
        createSession(uid)
      }
    }
  }

  // api for http server

  /**
    * login
    * @param req request
    */
  def login(req:RequestLogin): Option[Session] = {
    UserDB.login(req.email, req.password).flatMap { user =>
      val uid = user.uid
      val sid = createSession(uid)
      Some(Session(sid, uid, user))
    }
  }

  /**
    * register
    * call login after doing register
    * @param req request
    */
  def register(req:RequestRegister): Option[Session] = {
    UserDB.register(req.email,req.nickname,req.avatar,req.gender,req.password).flatMap{ _ =>
      login(RequestLogin(req.email,req.password))
    }
  }

  /**
    * forget password
    */
  def forget(req:RequestForgetPassword) : Option[Unit] = {
    val uid = req.email
    UserDB.checkUser(uid).flatMap { _ =>
      val sid = createSession(uid)
      val title = "[Krad] 重置密码"
      val text = s"请点击以下链接以重置密码： ${config.web_url}/session/reset.html?sid=$sid"
      Some(Mail.send(req.email, title, text))
    }.succ()
  }

  /**
    * set new password
    */
  def setNewPassword(req:RequestSetNewPassword) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
        UserDB.setNewPassword(uid, req.new_password)
      }
  }

  /**
    * change password
    */
  def changePassword(req:RequestChangePassword) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
        UserDB.changePassword(uid,req.old_password,req.new_password)
      }
  }

  /**
    * change profile
    */
  def changeProfile(req:RequestChangeProfile) : Option[Unit] = {
    map.getB(req.sid).flatMap { uid =>
      UserDB.changeProfile(uid,req.nickname,req.avatar,req.gender)
    }
  }
}
