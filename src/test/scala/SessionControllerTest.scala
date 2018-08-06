package game

import server.Server.RequestMatch
import server.Server.executionContext

object SessionControllerTest {
  def main(args: Array[String]): Unit = {
    SessionController.addGhost()
    Seq(0,1,2).foreach { p =>
      println(p)
      val f = if(p<2) SessionController.matchPlayers(RequestMatch(0,2)) else  SessionController.matchPlayers(RequestMatch(1,2))
      f.onComplete(x=>println(p,x))
      if(p==0){
        SessionController.unmatchPlayers(RequestMatch(0,2))
      }
    }
  }
}
