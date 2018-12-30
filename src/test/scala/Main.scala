import glib.GError
import glib.json.JsonParser
import libsoup.{SoupMessage, SoupSession}

import scala.scalanative.native.cobj.Out
import scalanative.native._

object Main {
  def main(args: Array[String]): Unit = Zone{ implicit z =>
    AutoreleasePool { implicit pool =>
      val session = new SoupSession
      val msg = new SoupMessage(c"GET",c"https://www.omdbapi.com/?apikey=381e81be&s=double").autorelease
      val status = session.sendMessage(msg)
      println(status)
      println(fromCString(msg.responseString))
      val parser = new JsonParser()
      parser.loadFromData(msg.responseString,-1)(null)
      val results = parser.root.getObject().getMember(c"Search")
      val res1 = results.getArray().getElement(0.toUInt).getObject()
      println(res1.size)
    }

//    ext.soup_session_send(session.__ref,msg.__ref.cast[Ptr[Byte]],null,null)
  }
}

@extern
object ext {
  def soup_session_send(session: cobj.Ref[Byte], msg: Ptr[Byte], cancel: Ptr[Byte], error: Ptr[Ptr[Byte]]): Unit = extern
}
