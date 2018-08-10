package game

import java.io.PrintWriter
import java.util.Date

import common.Bimap
import game.UserModel.User
import server.Server
import server.Server.{RequestGame, RequestLogin, RequestMatch, RequestRegister, executionContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

object SessionController {

  /**
    * Session
    *
    * @param sid  session id
    * @param uid  user id (email)
    * @param user user's information
    */
  final case class Session(sid: Int, uid: String, user: User)

  val StateWaiting = 0
  val StateMatching = 1
  val StatePlaying = 2

  final case class SessionState(state: Int, timestamp: Long, god: God)

  object SessionState {
    def apply(state: Int = StateWaiting, god: God = null): SessionState = new SessionState(state, System.currentTimeMillis(), god)
  }

  val WaitingTime: Long = 1000 * 60 * 30

  // map: sid - uid
  val map = new Bimap[Int, String]()
  val states = new mutable.TreeMap[Int, SessionState]()

  /**
    * create a session or return an existing session for user
    *
    * @param uid user id
    * @return session id
    */
  def createSession(uid: String): Int = {
    val sid = s"${new Date().getTime}$uid".hashCode
    map.getA(uid).getOrElse {
      if (map.getB(sid).isEmpty) {
        this.synchronized {
          map.set(sid, uid)
          states += (sid -> SessionState())
        }
        sid
      } else {
        Thread.sleep(10)
        createSession(uid)
      }
    }
  }

  def routine(): Unit = Future {
    while (true) {
      Thread.sleep(WaitingTime * 20)
      this.synchronized {
        states.retain { (_, item) =>
          item.state == StatePlaying ||
            item.timestamp >= System.currentTimeMillis() - WaitingTime
        }
      }
    }
  }

  val ghostGroup = Seq(Array(0), Array(1, 2), Array(3, 4, 5, 6), Array(7, 8, 9), Array(10, 11))

  def addGhost(): Unit = {
    for (arr <- ghostGroup) {
      val god = new God
      god.initialPlayer(arr)
      for (x <- arr) {
        states += (x -> SessionState(StatePlaying, god))
        map.set(x, "ghost@ghost.com")
      }
    }
  }

  def removeGhost(): Unit = {
    for (arr <- ghostGroup;
         sid <- arr) {
      try {
        states.remove(sid)
      } catch {
        case _: Throwable => {}
      }
    }
  }

  // api for http server

  /**
    * login
    *
    * @param req request
    */
  def login(req: RequestLogin): Option[Session] = {
    UserModel.login(req.email, req.password).flatMap { user =>
      val uid = user.uid
      val sid = createSession(uid)
      Some(Session(sid, uid, user))
    }
  }

  /**
    * register
    * call login after doing register
    *
    * @param req request
    */
  def register(req: RequestRegister): Option[Session] = {
    UserModel.register(req.email, req.nickname, req.avatar, req.gender, req.password).flatMap { _ =>
      login(RequestLogin(req.email, req.password))
    }
  }

  /**
    * matchPlayers
    */

  val match_pool: mutable.TreeMap[Int, mutable.TreeSet[Int]] = {
    val map = new mutable.TreeMap[Int, mutable.TreeSet[Int]]()
    map(2) = new mutable.TreeSet[Int]()
    map(4) = new mutable.TreeSet[Int]()
    map
  }

  def matchPlayers(req: RequestMatch): Future[Option[Int]] = Future {
    try {
      Server.log("verbose match", req)
      val player_count = req.player_count
      assert(player_count == 2 || player_count == 4)

      def clearMatchPool(n: Int): Int = {
        match_pool(n).retain { sid =>
          states.get(sid).exists { state =>
            state.state == StateMatching
          }
        }
        match_pool(n).size
      }

      states.get(req.sid).map { _ =>
        this.synchronized {
          // try to start a game
          if (match_pool(player_count).size >= player_count - 1 &&
            clearMatchPool(player_count) >= player_count - 1 &&
            !(match_pool(player_count) contains req.sid)) {

            // pick players
            val choosed_players = new Array[Int](player_count)
            var i = 1
            choosed_players(0) = req.sid
            for (player <- match_pool(player_count)) if (i < player_count && player != req.sid) {
              choosed_players(i) = player
              i += 1
            }
            assert(i == player_count)

            // init
            val god = new God()
            choosed_players.foreach(match_pool(player_count).remove)
            god.initialPlayer(choosed_players)
            states.transform { (sid, state) =>
              if (choosed_players contains sid) {
                state.copy(state = StatePlaying, god = god)
              } else {
                state
              }
            }
            this.notifyAll()
          } else {
            val s = SessionState(StateMatching)
            states(req.sid) = s
            val timestamp = s.timestamp
            match_pool(player_count) += req.sid

            // get ready and not be occupied
            var quit = false
            do {
              this.wait()
              quit = states.get(req.sid).forall { s =>
                if (s.timestamp != timestamp) {
                  true
                } else if (s.state == StatePlaying) {
                  true
                } else {
                  false
                }
              }
            } while (!quit)
          }
        }
        Server.log("verbose match pool", match_pool(player_count).toSeq)
        player_count
      }
    } catch {
      case ex: Throwable => {
        ex.printStackTrace(new PrintWriter(Server.log_file))
        None
      }
    }

  }

  def unmatchPlayers(req: RequestMatch): Unit = {
    Server.log("verbose unmatch", req)
    this.synchronized {
      match_pool.foreach(map =>
        map._2.remove(req.sid)
      )
      states.transform { (sid, state) =>
        if (sid == req.sid && state.state != StatePlaying) {
          SessionState(StateWaiting)
        } else {
          state
        }
      }
    }
  }


  def endGame(players: Array[Int], deltaScore: Array[Int]): Unit = {
    this.synchronized {
      states.transform { (sid, state) =>
        if (players contains sid) {
          SessionState(StateWaiting)
        } else {
          state
        }
      }
    }
    for (elem <- players zip deltaScore) {
      map.getB(elem._1).map {
        UserModel.changeStat(_, elem._2)
      }
    }
  }

  def gameRequest(req: RequestGame): Future[Option[String]] = Future {
    Server.gamelog(req)
    states.get(req.sid) map { states =>
      Server.log("verbose game in", req.toString)
      val god = states.god
      val result =
        try {
          god.request(req.sid, req.msg)
        } catch {
          case ex: Exception => {
            ex.printStackTrace(new PrintWriter(Server.log_file))
            "Internal Error"
          }
        }
      Server.log("verbose game out", result)
      result
    }
  }
}
