package server

object ServerTest{
  def main(args: Array[String]): Unit = {
    Server.main(Array(""))
    println(Server.config)
  }
}