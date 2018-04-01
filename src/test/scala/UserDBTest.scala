package game

object UserDBTest {
  def main(args: Array[String]): Unit = {
    UserDB.register("nicekingwei@sina.com","nicekingwei")
    UserDB.register("afsfds252tvg","sdg4252")
    UserDB.login("nicekinasfsf","fassfsaf")
    println(UserDB.login("nicekingwei@sina.com","nicekingwei"))
  }
}
