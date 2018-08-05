package game

import server.Server.RequestMatch
import server.Server.executionContext

object SessionControllerTest {
  def main(args: Array[String]): Unit = {
    val players = (0 to 3).map { i =>
      val uid = i.toString
      val sid = SessionController.createSession(uid)
      (uid,sid)
    }
    players.foreach { p =>
      println(p)
      val f = SessionController.matchPlayers(RequestMatch(p._2,4))
      f.onComplete(x=>println(p,x))
      Thread.sleep(100)
    }
  }
}
