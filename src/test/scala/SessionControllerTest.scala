package game

import server.Server.{RequestGame, RequestMatch, executionContext}

object SessionControllerTest {

  def test1(): Unit ={
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

  def test2(): Unit ={
//    val sid1 = SessionController.createSession("tester1@krad.com")
//    val sid2 = SessionController.createSession("tester2@krad.com")
    SessionController.addGhost()
    SessionController.states.transform{(sid,state)=>if (sid==0||sid==1) SessionController.SessionState() else state}
    val f1 = SessionController.matchPlayers(RequestMatch(0,2))
    val f2 = SessionController.matchPlayers(RequestMatch(1,2))
    f1 onComplete { _ =>
      SessionController.gameRequest(RequestGame(0,"{}"))
    }
    f2 onComplete { _ =>
      SessionController.gameRequest(RequestGame(0,"{}"))
    }
  }

  def main(args: Array[String]): Unit = {
    test2()
  }
}
