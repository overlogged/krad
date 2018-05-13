package game

object UserDBTest {
  def main(args: Array[String]): Unit = {
    UserModel.register("nicekingwei@sina.com","nicekingwei","avatars/1.jpg","boy","mypassword")
    UserModel.register("sky@icloud.com","sky","avatars/2.jpg","girl","herpassword")
    assert(UserModel.register("afsfds252tvg","sdg4252","invalid","invaid","invaid").isEmpty)
    assert(UserModel.login("nicekinasfsf","fassfsaf").isEmpty)
    assert(UserModel.login("nicekingwei@sina.com","mypassword").nonEmpty)
    UserModel.changePassword("nicekingwei@sina.com","mypassword","mynewpassword")
    UserModel.changeStat("nicekingwei@sina.com", x=>UserModel.Stats(x.score+1))
    assert(UserModel.login("nicekingwei@sina.com","mynewpassword").nonEmpty)
    UserModel.setNewPassword("nicekingwei@sina.com","mypassword")
    assert(UserModel.login("nicekingwei@sina.com","mypassword").nonEmpty)
    println("finish UserDBTest")
    sys.exit(0)
  }
}
