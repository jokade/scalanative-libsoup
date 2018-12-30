package libsoup

import de.surfice.smacrotools.debug
import gio.{GCancellable, GInputStream}
import glib.{GError, guint}
import gobject.GObject

import scalanative.native._
import cobj._

/**
 * Soup session state object.
 *
 * @see [[https://developer.gnome.org/libsoup/stable/SoupSession.html]]
 */
@CObj
@debug
class SoupSession extends GObject {
  def sendMessage(msg: SoupMessage): guint = extern
  def send(msg: SoupMessage, cancellable: GCancellable)(implicit error: Out[GError]): GInputStream = extern

}
