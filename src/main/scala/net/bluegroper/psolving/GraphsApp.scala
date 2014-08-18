package net.bluegroper.psolving

/**
 * @author fede
 */

import swing._
import java.awt.RenderingHints
import scala.swing.Color

object GraphsApp extends SimpleSwingApplication {

  val graph: Graph = GraphParser.parse(GraphParser.source)

  val frameWidth = 700
  val frameHeight = 800

  def top = new MainFrame {
    title = "Problem Solving: finding paths"
    preferredSize = new Dimension(frameWidth, frameHeight)
    contents = new Component {

      override def paintComponent(g: Graphics2D) = {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setColor(new Color(100, 100, 100))
        g.drawString("Select a start node.", 10, 20)

        graph.nodes.foreach((node: Node) => drawNode(node, g))
        graph.edges.foreach((edge: Edge) => drawEdge(edge, g))
      }
    }
  }

  def drawNode(n: Node, g: Graphics2D) = {
    val xs: Int = top.preferredSize.width / 7
    val x: Int = n.x * xs
    val centre: Int = x + (xs / 2)
    val y: Int = (n.y * xs) + (xs + xs / 4) / 2

    g.drawArc(centre - xs / 4, y, 50, 50, 0, 360)

    val width = g.getFontMetrics.stringWidth(n.name)

    g.drawString(n.name, centre - (width / 2), y - 10)
  }

  def drawEdge(e: Edge, g: Graphics2D) = {

  }


}
