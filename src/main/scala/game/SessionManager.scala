package game

import java.util.Date

import akka.actor.Actor

import scala.collection.mutable

final case class Session(sid:Int,email:String)

object Session{
  def apply(sid: Int, email: String): Session = new Session(sid, email)
}

object SessionManager {

  // Sid -> Session
  val session_map = new mutable.TreeMap[Int,Session]()


  // create a session for user
  //
  def createSession(email:String):Int = {
    val sid = s"${new Date().getTime}$email".hashCode
    if(session_map.get(sid).isEmpty){
      session_map(sid) = Session(sid,email)
      sid
    } else {
      Thread.sleep(10)
      createSession(email)
    }
  }


}
