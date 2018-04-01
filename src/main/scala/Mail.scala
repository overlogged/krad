import Server.config
import org.apache.commons.mail.HtmlEmail
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

final case class MsgMailForget(to:String,anchor:String)

object Mail {
  def send(to:String,subject:String,text:String) = Future {
    val email = new HtmlEmail
    email.setHostName(config.email_host)
    email.setAuthentication(config.email_username,config.email_password)
    email.addTo(to)
    email.setFrom(config.email_username)
    email.setSubject(subject)
    email.setCharset("utf-8")
    email.setHtmlMsg(text)
    email.send
  }
}
