package game

/*
 * hold players' information
 * long-lasting static database
 * 1. auth
 * 2. stats
 */

case class Stats(score:Int)
case class User(id:Int,email:String,password:String,stats:Stats)

object UserDB {

}
