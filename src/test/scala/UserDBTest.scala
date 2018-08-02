package game

object UserDBTest {
  def main(args: Array[String]): Unit = {
    UserModel.register("nicekingwei@sina.com","nicekingwei","avatars/1.jpg",0,"mypassword")
    UserModel.register("sky@icloud.com","sky","avatars/2.jpg",1,"herpassword")
    assert(UserModel.register("afsfds252tvg","sdg4252","invalid",1,"invaid").isEmpty)
    assert(UserModel.login("nicekinasfsf","fassfsaf").isEmpty)
    assert(UserModel.login("nicekingwei@sina.com","mypassword").nonEmpty)
    UserModel.changePassword("nicekingwei@sina.com","mypassword","mynewpassword")
    UserModel.changeStat("nicekingwei@sina.com", 1)
    assert(UserModel.login("nicekingwei@sina.com","mynewpassword").nonEmpty)
    UserModel.setNewPassword("nicekingwei@sina.com","mypassword")
    assert(UserModel.login("nicekingwei@sina.com","mypassword").nonEmpty)
    println("finish UserDBTest")
    sys.exit(0)
  }
}
