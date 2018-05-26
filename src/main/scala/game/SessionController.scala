package game

import java.util.Date

import common.Bimap
import game.UserModel.User
import server.Server.{RequestLogin, RequestMatch, RequestRegister}
import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object SessionController {

  /**
    * Session
    * @param sid session id
    * @param uid user id (email)
    * @param user user's information
    */
  final case class Session(sid:Int,uid:String,user:User)

  val StateWaiting = 0
  val StateMatching = 1
  val StateReady = 2
  val StatePlaying = 3

  final case class SessionState(state:Int,god:God)
  object SessionState {
    def apply(state: Int, god: God): SessionState = new SessionState(state, god)
    def apply():SessionState = new SessionState(0,null)
  }

  val WaitingTime = 10000

  // map: sid - uid
  val map = new Bimap[Int,String]()
  val states = new mutable.TreeMap[Int,SessionState]()

  /**
    * create a session or return an existing session for user
    * @param uid user id
    * @return session id
    */
  def createSession(uid:String):Int = {
    val sid = s"${new Date().getTime}$uid".hashCode
    map.getA(uid).getOrElse {
      if(map.getB(sid).isEmpty){
        this.synchronized {
          map.set(sid,uid)
          states += (sid->SessionState())
        }
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
    UserModel.login(req.email, req.password).flatMap { user =>
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
    UserModel.register(req.email,req.nickname,req.avatar,req.gender,req.password).flatMap{ _ =>
      login(RequestLogin(req.email,req.password))
    }
  }

  /**
    * matchPlayers
    */
  var matching_count = 0
  def matchPlayers(req:RequestMatch):Future[Option[Int]] = Future {
    matching_count.synchronized {
      matching_count += 1
    }

    // wait
    var stop = false
    var result:Option[Int] = None
    val player_count = 4
    while(!stop) {
      Thread.sleep(WaitingTime)
      states.get(req.sid) match {
        case None => {
          stop = true
        }
        case Some(state) => {
          if(state.state == StateReady){
            stop = true
            result = Some(player_count)
          } else if (matching_count >= player_count) {
            // start a game
            this.synchronized {
              // todo: random
              var in_game_count:Int = 0
              val god = new God()
              for(player<-states) if(player._2.state == StateMatching && in_game_count < player_count) {
                states(player._1) = player._2.copy(state = StateReady,god = god)
                matching_count -= 1
                in_game_count += 1
              }
            }
          }
        }
      }
    }

    result
  }
}
