import scala.collection.mutable

object MagicTest {
  def main(args: Array[String]): Unit = {
    val map = new mutable.TreeMap[Int,String]()
    map(3) = "a"
    map(3) = "43234"
  }
}
