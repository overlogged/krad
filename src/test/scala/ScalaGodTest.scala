package game

import common.MyJsonProtocol
import server.Server
import server.Server.RequestGame

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object ScalaGodTest extends MyJsonProtocol {
  def testSample(): Unit = {
    val god = new God
    val sid = Array(1, 2, 3, 4)
    god.initialPlayer(sid)
    for (s <- sid) {
      println(s)
      Future {
        god.request(s, s"Hero${4 - s}")
      } onComplete { x =>
        println(x)
      }
      Thread.sleep(100)
    }
  }


  def exper(): Unit = {
    val f1 = Future {
      Thread.sleep(1000)
      1
    }
    println("Hello world")
    val f2 = f1.map { x =>
      println("f2:", x)
      x + 1
    }
    val f3 = f2.flatMap(x => Future {
      Thread.sleep(1000)
      println("complete")
      x * 10
    }
    )
    f3.onComplete(x =>
      println("done")
    )
    Thread.sleep(8000)
  }

  def test1(): Unit = {
    MapGenerator.map1()
    val sids = 0 to 1
    val god = new God
    var i = 0
    god.initialPlayer(sids.toArray)
    val fs = for (sid <- sids)
      yield Future {
        for (i <- 1 to 20) {
          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"hero":"hero1"}"""
          ))


          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"decision":-1,"moveDirection":-1,"fireTarget":-1}"""
          ))

          println(sid, god.request(sid,
            """{"seenCard":2}"""
          ))

          println(sid, god.request(sid,
            """{"gambleCard":[-1]}"""
          ))
          // Account
          println(sid, god.request(sid, ""))
          // Account
          println(sid, god.request(sid, ""))
          // cardDesert
          println(sid, god.request(sid,
            """{"desertCardList":[-1]}"""
          ))

          println(sid, god.request(sid, "{}"))
          println(sid, god.request(sid,
            """{"decision":-1,"moveDirection":-1,"fireTarget":-1}"""
          ))

          println(sid, god.request(sid,
            """{"seenCard":0}"""
          ))

          println(sid, god.request(sid,
            """{"gambleCard":[1]}"""
          ))
          // Account
          println(sid, god.request(sid, ""))
          // Account
          println(sid, god.request(sid, ""))
          // cardDesert
          println(sid, god.request(sid,
            """{"desertCardList":[0,1,2,3,4]}"""
          ))
        }
      }
    for (f <- fs) {
      f onComplete println
      Await.ready(f, Duration.Inf)
    }
  }

  def test3(): Unit = {
    SessionController.addGhost()
    SessionController.gameRequest(RequestGame(2, "{}"))
    SessionController.gameRequest(RequestGame(1, "{}"))
  }

  def test4(): Unit = {
    SessionController.addGhost()
    val god = SessionController.states(1).god
    val f1 = Future {
      god.request(1, "{}") // Wed Aug 08 22:25:38 CST 2018
      god.request(1, "{\"hero\":\"Linear Algebra\"}") // Wed Aug 08 22:25:43 CST 2018
      god.request(1, "{}") // Wed Aug 08 22:25:46 CST 2018
      god.request(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}") // Wed Aug 08 22:25:56 CST 2018
      god.request(1, "{\"seenCard\":1}") // Wed Aug 08 22:26:04 CST 2018
      god.request(1, "{\"gambleCard\":[-1]}")
      god.request(1, "{}")
      god.request(1, "{}")
      god.request(1, "{\"desertCardList\":[-1]}")
      god.request(1, "{}")
      god.request(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(1, "{\"seenCard\":2}")
      god.request(1, "{\"gambleCard\":[-1]}")
      god.request(1, "{}")
      god.request(1, "{}")
      god.request(1, "{\"desertCardList\":[1,2,3,4,5]}")
      god.request(1, "{}")
      god.request(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(1, "{\"seenCard\":2}")
      god.request(1, "{\"gambleCard\":[-1]}")
      god.request(1, "{}")
      god.request(1, "{}")
      god.request(1, "{\"desertCardList\":[0,1,2,3,4]}")
      god.request(1, "{}")
      god.request(1, "{\"decision\":0,\"moveDirection\":1,\"fireTarget\":-1}")
      god.request(1, "{\"seenCard\":2}")
      god.request(1, "{\"gambleCard\":[-1]}")
      god.request(1, "{}")
      god.request(1, "{}")
      god.request(1, "{\"desertCardList\":[0,1,2]}")
      god.request(1, "{}")
      god.request(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(1, "{\"seenCard\":1}")
    }

    val f2 = Future {
      god.request(2, "{}")
      god.request(2, "{\"hero\":\"Calculus\"}")
      god.request(2, "{}")
      god.request(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(2, "{\"seenCard\":1}")
      god.request(2, "{\"gambleCard\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{}")
      god.request(2, "{\"desertCardList\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(2, "{\"seenCard\":1}")
      god.request(2, "{\"gambleCard\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{}")
      god.request(2, "{\"desertCardList\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(2, "{\"seenCard\":1}")
      god.request(2, "{\"gambleCard\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{}")
      god.request(2, "{\"desertCardList\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(2, "{\"seenCard\":1}")
      god.request(2, "{\"gambleCard\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{}")
      god.request(2, "{\"desertCardList\":[-1]}")
      god.request(2, "{}")
      god.request(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")
      god.request(2, "{\"seenCard\":1}")
    }
    Await.result(f1, Duration.Inf)
    Await.result(f2, Duration.Inf)
  }

  def test5(): Unit = {
    SessionController.addGhost()
    val god = SessionController.states(1).god
    val f1 = Future {
      god.request(1, "{}") // Fri Aug 10 12:39:32 CST 2018
      god.request(1, "{\"hero\":\"Calculus\"}") // Fri Aug 10 12:39:37 CST 2018
      god.request(1, "{}") // Fri Aug 10 12:39:42 CST 2018
      god.request(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}") // Fri Aug 10 12:39:47 CST 2018
      god.request(1, "{\"seenCard\":2}") // Fri Aug 10 12:39:50 CST 2018
      god.request(1, "{\"gambleCard\":[-1]}") // Fri Aug 10 12:39:53 CST 2018
      god.request(1, "{}") // Fri Aug 10 12:39:53 CST 2018
      god.request(1, "{}") // Fri Aug 10 12:39:53 CST 2018
      god.request(1, "{\"desertCardList\":[-1]}") // Fri Aug 10 12:40:02 CST 2018
      god.request(1, "{}") // Fri Aug 10 12:40:03 CST 2018
      god.request(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}") // Fri Aug 10 12:40:06 CST 2018
      god.request(1, "{\"seenCard\":0}") // Fri Aug 10 12:40:14 CST 2018
      god.request(1, "{\"gambleCard\":[7]}") // Fri Aug 10 12:40:17 CST 2018
    }
    val f2 = Future {
      god.request(2, "{}") // Fri Aug 10 12:39:37 CST 2018
      god.request(2, "{\"hero\":\"Linear Algebra\"}") // Fri Aug 10 12:39:42 CST 2018
      god.request(2, "{}") // Fri Aug 10 12:39:42 CST 2018
      god.request(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}") // Fri Aug 10 12:39:45 CST 2018
      god.request(2, "{\"seenCard\":1}") // Fri Aug 10 12:39:53 CST 2018
      god.request(2, "{\"gambleCard\":[-1]}") // Fri Aug 10 12:39:53 CST 2018
      god.request(2, "{}") // Fri Aug 10 12:39:53 CST 2018
      god.request(2, "{}") // Fri Aug 10 12:39:53 CST 2018
      god.request(2, "{\"desertCardList\":[-1]}") // Fri Aug 10 12:39:59 CST 2018
      god.request(2, "{}") // Fri Aug 10 12:40:03 CST 2018
      god.request(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}") // Fri Aug 10 12:40:09 CST 2018
      god.request(2, "{\"seenCard\":1}") // Fri Aug 10 12:40:11 CST 2018
      god.request(2, "{\"gambleCard\":[-1]}") // Fri Aug 10 12:40:14 CST 2018
//      god.request(2, "{}") // Fri Aug 10 12:40:14 CST 2018
    }
    Await.ready(f1, Duration.Inf)
    Await.ready(f2, Duration.Inf)
  }

  def main(args: Array[String]): Unit = {
    test5()
  }
}
