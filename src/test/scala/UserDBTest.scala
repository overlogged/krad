package game

import sun.security.smartcardio.SunPCSC.Factory

object UserDBTest {
  def main(args: Array[String]): Unit = {
    UserDB.register("nicekingwei@sina.com","nicekingwei")
  }
}
