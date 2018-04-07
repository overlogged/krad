package server

object MyUtils {
  implicit class Guard[+A](x:Option[A]) {
    def guard(exp:Boolean): Option[A] = x.flatMap { a=>
      if(exp) Some(a) else None
    }
  }
}

