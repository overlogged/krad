package game

object ScalaMapCheckerTest {
  def main(args: Array[String]): Unit = {
    val map = new game.Map("map/1.map")
    println(MapChecker.tryMove(map,0,1,3))
    println(MapChecker.tryMove(map,6,7,7))
    println(MapChecker.tryMove(map,24,6,4))
    println(MapChecker.tryMove(map,24,23,0))
    println(MapChecker.tryMove(map,18,19,1))
    println(MapChecker.tryMove(map,19,20,3))
    println(MapChecker.tryMove(map,23,24,2))
    println(MapChecker.tryMove(map,24,7,4))
    println(MapChecker.tryMove(map,3,4,0))
    println(MapChecker.tryMove(map,6,7,0))
    println(MapChecker.tryMove(map,8,9,0))
    println(MapChecker.tryMove(map,6,7,3))
    println(MapChecker.tryMove(map,4,map.units(4).neighbors(1),2))
    println(MapChecker.tryMove(map,4,map.units(4).neighbors(1),1))
    println(MapChecker.tryMove(map,4,map.units(4).neighbors(5),1))
  }
}
