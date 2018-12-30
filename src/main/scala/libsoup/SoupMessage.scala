package libsoup

import glib.{GBytes, gint, gsize, guint}
import gobject.GObject

import scalanative.native._
import cobj._

/**
 * An HTTP request and response.
 *
 * @see [[https://developer.gnome.org/libsoup/stable/SoupMessage.html]]
 */
@CObj
class SoupMessage(method: CString, uriString: CString) extends GObject {

  def setRequest(contentType: CString, reqUse: SoupMemoryUse, reqBody: CString, reqLength: gsize): Unit = extern

  @inline def getHttpVersion: SoupHTTPVersion = extern
  @inline def setHttpVersion(version: SoupHTTPVersion): Unit = extern

  @inline def getUri: SoupURI = extern
  @inline def setUri(uri: SoupURI): Unit = extern

  @inline def getAddress: SoupAddress = extern

  @inline def statusCode: guint = getUInt(c"status-code")
  @inline def statusCode_=(code: guint): Unit = setUInt(c"status-code",code)

  def responseBody: SoupMessageBody = {
    val p = getObject(c"response-body")
    if(p==null)
      null
    else
      new SoupMessageBody(p)
  }

  def responseBodyData: GBytes = {
    val p = getObject(c"response-body-data")
    if(p==null)
      null
    else
      new GBytes(p)
  }

  def responseHeaders: SoupMessageHeaders = {
    val p = getObject(c"response-headers")
    if(p==null)
      null
    else
      new SoupMessageHeaders(p)
  }

  def responseString: CString = responseBody.data
}

object SoupMessage {
}
