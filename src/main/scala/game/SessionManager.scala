package game

import java.util.Date

import game.UserDB.User
import server.Bimap
import server.Server.{RequestLogin, RequestRegister}



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
    UserDB.register(req.email,req.nickname,req.avatar,req.gender.toString,req.password).flatMap{ _=>
      login(RequestLogin(req.email,req.password))
    }
  }

  /**
    *
    */
}
