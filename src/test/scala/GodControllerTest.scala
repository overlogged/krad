package game

import GodController._
import spray.json._

object GodControllerTest {
  def main(args: Array[String]): Unit = {
    val x = MsgChooseHero(1,"sfaf")
    val s = x.toJson.toString // JsValue
    println(s)
    println(s.parseJson.convertTo[MsgChooseHero])
  }
}
