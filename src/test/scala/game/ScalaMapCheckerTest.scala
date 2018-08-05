package game

object ScalaMapCheckerTest {


  def main(args: Array[String]): Unit = {
    val map = new game.Map("map/1.map")

    val LEFT = 7
    val RIGHT = 3
    val UP = 1
    val DOWN = 5

    def jump(start: Int, dir: Int, energy: Int) = MapChecker.tryMove(map, start, map.units(start).neighbors(dir), energy)

    assert(jump(17, DOWN, 2) == 19)
    assert(jump(16, RIGHT, 3) == 19)
    assert(jump(22, UP, 6) == 16)
    assert(jump(22, DOWN, 5) == 5)
  }
}
