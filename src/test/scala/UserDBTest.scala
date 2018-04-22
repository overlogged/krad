package game

object UserDBTest {
  def main(args: Array[String]): Unit = {
    UserDB.register("nicekingwei@sina.com","nicekingwei","avatars/1.jpg","boy","mypassword")
    UserDB.register("sky@icloud.com","sky","avatars/2.jpg","girl","herpassword")
    assert(UserDB.register("afsfds252tvg","sdg4252","invalid","invaid","invaid").isEmpty)
    assert(UserDB.login("nicekinasfsf","fassfsaf").isEmpty)
    assert(UserDB.login("nicekingwei@sina.com","mypassword").nonEmpty)
    UserDB.changePassword("nicekingwei@sina.com","mypassword","mynewpassword")
    UserDB.changeStat("nicekingwei@sina.com",x=>UserDB.Stats(x.score+1))
    assert(UserDB.login("nicekingwei@sina.com","mynewpassword").nonEmpty)
    UserDB.setNewPassword("nicekingwei@sina.com","mypassword")
    assert(UserDB.login("nicekingwei@sina.com","mypassword").nonEmpty)
    println("finish UserDBTest")
    sys.exit(0)
  }
}
