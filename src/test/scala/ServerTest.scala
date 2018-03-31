object ServerTest{
  def main(args: Array[String]): Unit = {
    Server.main(Array("migrant"))
    println(Server.config)
  }
}