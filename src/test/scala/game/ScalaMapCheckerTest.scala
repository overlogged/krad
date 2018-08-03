package game

object ScalaMapCheckerTest {
  def main(args: Array[String]): Unit = {
    println(MapChecker.tryMove(new game.Map("map/1.map"),0,1,3))
    println(MapChecker.tryMove(new game.Map("map/1.map"),6,7,7))
    println(MapChecker.tryMove(new game.Map("map/1.map"),24,6,4))
  }
}
