object ScalaTest {
  def main(args: Array[String]): Unit = {
    case class A(x:Int)
    val x:A = null
    println(s"hello ${x}")
  }
}
