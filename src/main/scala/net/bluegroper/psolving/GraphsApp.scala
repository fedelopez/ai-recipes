package net.bluegroper.psolving

/**
 * @author fede
 */

import swing._
import java.awt.{Color, RenderingHints}

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
        g.setColor(Color.darkGray)
        g.drawString("Select a start node.", 10, 20)
        graph.edges.foreach((edge: Edge) => drawEdge(edge, g))
        graph.nodes.foreach((node: Node) => drawNode(node, g))
      }
    }
  }

  def drawNode(n: Node, g: Graphics2D) = {
    g.setColor(Color.lightGray)
    val xs: Int = top.preferredSize.width / 7
    val x: Int = n.x * xs
    val centre: Int = x + (xs / 2)
    val y: Int = (n.y * xs) + (xs + xs / 4) / 2

    g.fillArc(centre - xs / 4, y, 50, 50, 0, 360)

    g.setColor(Color.darkGray)
    g.drawArc(centre - xs / 4, y, 50, 50, 0, 360)

    val width = g.getFontMetrics.stringWidth(n.name)
    g.drawString(n.name, centre - (width / 2), y - 10)
  }

  def drawEdge(e: Edge, g: Graphics2D) = {
    val xs: Int = top.preferredSize.width / 7

    val nodeA_x: Int = e.nodeA.x * xs + 50
    val nodeA_y: Int = (e.nodeA.y * xs) + (xs + xs / 4) / 2

    val nodeB_x: Int = e.nodeB.x * xs + 50
    val nodeB_y: Int = (e.nodeB.y * xs) + (xs + xs / 4) / 2

    g.drawLine(nodeA_x, nodeA_y + 25, nodeB_x, nodeB_y + 25)
  }


}
