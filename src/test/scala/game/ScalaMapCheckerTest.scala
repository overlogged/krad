package game

object ScalaMapCheckerTest {
  def main(args: Array[String]): Unit = {
    println(MapChecker.tryMove(new game.Map("map/1.map"),0,1,3))
    println(MapChecker.tryMove(new game.Map("map/1.map"),6,7,7))
    println(MapChecker.tryMove(new game.Map("map/1.map"),24,6,4))
    println(MapChecker.tryMove(new game.Map("map/1.map"),24,23,0))
    println(MapChecker.tryMove(new game.Map("map/1.map"),3,4,0))
    println(MapChecker.tryMove(new game.Map("map/1.map"),6,7,0))
    println(MapChecker.tryMove(new game.Map("map/1.map"),8,9,0))
    println(MapChecker.tryMove(new game.Map("map/1.map"),6,7,3))
  }
}
