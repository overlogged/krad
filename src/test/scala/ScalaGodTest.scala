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

  def test2(): Unit = {
    SessionController.addGhost()
    val fs = for (sid <- 0 to 1)
      yield
        for (data1 <- SessionController.gameRequest(RequestGame(sid, "{}"));
             data2 <- SessionController.gameRequest(RequestGame(sid, "{\"hero\":\"hero\"}"));
             data3 <- SessionController.gameRequest(RequestGame(sid, "{}"))) yield (data1, data2, data3)
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
    for (_ <- SessionController.gameRequest(RequestGame(1, "{}")); // Wed Aug 08 22:25:38 CST 2018
         _ <- SessionController.gameRequest(RequestGame(1, "{\"hero\":\"Linear Algebra\"}")); // Wed Aug 08 22:25:43 CST 2018
         _ <- SessionController.gameRequest(RequestGame(1, "{}")); // Wed Aug 08 22:25:46 CST 2018
         _ <- SessionController.gameRequest(RequestGame(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}")); // Wed Aug 08 22:25:56 CST 2018
         _ <- SessionController.gameRequest(RequestGame(1, "{\"seenCard\":1}")); // Wed Aug 08 22:26:04 CST 2018
         _ <- SessionController.gameRequest(RequestGame(1, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"desertCardList\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"seenCard\":2}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"desertCardList\":[1,2,3,4,5]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"seenCard\":2}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"desertCardList\":[0,1,2,3,4]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"decision\":0,\"moveDirection\":1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"seenCard\":2}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"desertCardList\":[0,1,2]}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(1, "{\"seenCard\":1}"))
    ){

    }

    for (_ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"hero\":\"Calculus\"}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"seenCard\":1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"desertCardList\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"seenCard\":1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"desertCardList\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"seenCard\":1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"desertCardList\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"seenCard\":1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"gambleCard\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"desertCardList\":[-1]}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"decision\":-1,\"moveDirection\":-1,\"fireTarget\":-1}"));
         _ <- SessionController.gameRequest(RequestGame(2, "{\"seenCard\":1}"))
    ){}
    return ();
  }

  def main(args: Array[String]): Unit = {
    test4()
  }
}
