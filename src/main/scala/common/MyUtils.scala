package common

/**
  * some useful functions
  */
object MyUtils {

  /**
    * implicit class Guard
    */
  implicit class Guard[+A](x:Option[A]) {

    def guard(exp:Boolean): Option[A] = x.flatMap { a =>
      if(exp) Some(a) else None
    }

    def also[B](fn : A=>B):Option[A] = x.flatMap { a =>
      fn(a)
      Some(a)
    }

    def succ() = Some(())
  }

  /**
    * implicit class Sanitizer
    * used by UserDB to check strings
    * @param x string
    */
  implicit class Sanitizer(x:String) {
    lazy val is_valid_password:Boolean = x.matches("""[a-zA-Z0-9]{6,18}""") // todo: if encrypted then change the rule
    lazy val is_valid_email:Boolean = x.matches("""^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$""")
    lazy val is_valid_gender:Boolean = x == "boy" || x == "girl"
    lazy val is_valid_avatar:Boolean = true                                 // todo: add check
    lazy val is_valid_nickname:Boolean = !x.contains("{};:'")
  }
}

