package game

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ScalaGodTest {
  def main(args: Array[String]): Unit = {
    val god = new God
    val sid = Array(1,2,3,4)
    god.initialPlayer(sid)
    for(s<-sid){
      println(s)
      Future {
        god.request(s,s"Hero${4-s}")
      } onComplete { x =>
        println(x)
      }
      Thread.sleep(100)
    }
  }
}
