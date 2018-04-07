package game

object UserDBTest {
  def main(args: Array[String]): Unit = {
    UserDB.register("nicekingwei@sina.com","nicekingwei","avatars/1.jpg","boy","mypassword")
    UserDB.register("sky@icloud.com","sky","avatars/2.jpg","girl","herpassword")
    println(UserDB.register("afsfds252tvg","sdg4252","invalid","invaid","invaid"))
    println(UserDB.login("nicekinasfsf","fassfsaf"))
    println(UserDB.login("nicekingwei@sina.com","mypassword"))
    UserDB.changePassword("nicekingwei@sina.com","mypassword","mynewpassword")
    UserDB.changeStat("nicekingwei@sina.com",x=>Stats(x.score+1))
    println(UserDB.login("nicekingwei@sina.com","mynewpassword"))
  }
}
