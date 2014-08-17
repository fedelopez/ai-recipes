package net.bluegroper.psolving

/**
 * @author fede
 */

import swing._
import java.awt.RenderingHints
import scala.swing.Color
import scala.io.Source

object GraphsApp extends SimpleSwingApplication {

  def top = new MainFrame {
    val source = Source.fromFile(getClass.getResource("layout.json").getFile).mkString
    title = "Hello, World!"
    contents = new Component {
      override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setColor(new Color(100, 100, 100))
        g.drawString("Press left mouse button and drag to paint.", 10, size.height - 10)
        g.drawArc(size.width / 2, size.height / 2, 50, 50, 0, 360)

      }
    }
  }

}
