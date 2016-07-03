package cat.pseudocodi.psolving.graphs

/**
 * @author fede
 */

import java.awt.{BasicStroke, Color, RenderingHints}

import scala.swing._
import scala.swing.event.MouseClicked

object GraphsApp extends SimpleSwingApplication {

  val graph: Graph = GraphParser.parse(GraphParser.source)

  val frameWidth = 700
  val frameHeight = 800

  val blue = new Color(0, 102, 153)

  val selectedNodes: scala.collection.mutable.ArrayBuffer[Node] = new scala.collection.mutable.ArrayBuffer[Node]()
  val path: scala.collection.mutable.ArrayBuffer[Node] = new scala.collection.mutable.ArrayBuffer[Node]()

  def top = new MainFrame {
    title = "Problem Solving: finding paths"
    preferredSize = new Dimension(frameWidth, frameHeight)

    contents = new Panel {

      override def paintComponent(g: Graphics2D) = {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setColor(Color.darkGray)

        if (selectedNodes.size > 0)
          g.drawString(s"Start node: ${selectedNodes.apply(0).name} ", 10, 720)
        if (selectedNodes.size > 1)
          g.drawString(s"End node: ${selectedNodes.apply(1).name} ", 10, 740)

        graph.edges.foreach((edge: Edge) => drawEdge(edge, g))
        graph.nodes.foreach((node: Node) => drawNode(node, g))
      }

      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked =>
          val res: Option[Node] = graph.nodes.find((node: Node) => nodeRect(node).contains(e.point))
          if (res.nonEmpty) {
            println(s"Node ${res.get.name} selected")
            selectedNodes.append(res.get)
            if (selectedNodes.size > 2) {
              selectedNodes.remove(0)
            }
            if (selectedNodes.size == 2) {
              path.clear()
              path.appendAll(graph.breadthFirstSearch(selectedNodes.apply(0), selectedNodes.apply(1)))
            }
            repaint()
          }
      }
    }

  }

  def drawNode(n: Node, g: Graphics2D) = {
    if (selectedNodes.contains(n)) g.setColor(blue)
    else g.setColor(Color.lightGray)

    val rect = nodeRect(n)

    g.fillArc(rect.x, rect.y, rect.width, rect.height, 0, 360)

    if (path.contains(n)) {
      g.setColor(blue)
      g.setStroke(new BasicStroke(2))
    } else {
      g.setColor(Color.darkGray)
      g.setStroke(new BasicStroke())
    }
    g.drawArc(rect.x, rect.y, rect.width, rect.height, 0, 360)

    val width = g.getFontMetrics.stringWidth(n.name)
    g.drawString(n.name, (rect.x + rect.width / 2) - (width / 2), rect.y - 10)
  }

  def drawEdge(e: Edge, g: Graphics2D) = {
    val xs: Int = top.preferredSize.width / 7

    val nodeA_x: Int = e.nodeA.x * xs + 50
    val nodeA_y: Int = (e.nodeA.y * xs) + (xs + xs / 4) / 2

    val nodeB_x: Int = e.nodeB.x * xs + 50
    val nodeB_y: Int = (e.nodeB.y * xs) + (xs + xs / 4) / 2

    if (path.contains(e.nodeA) && path.contains(e.nodeB)) {
      g.setColor(blue)
      g.setStroke(new BasicStroke(2))
    } else {
      g.setColor(Color.lightGray)
      g.setStroke(new BasicStroke())
    }

    g.drawLine(nodeA_x, nodeA_y + 25, nodeB_x, nodeB_y + 25)
  }

  def nodeRect(n: Node): Rectangle = {
    val xs: Int = frameWidth / 7
    val x: Int = n.x * xs
    val centre: Int = x + (xs / 2)
    val y: Int = (n.y * xs) + (xs + xs / 4) / 2
    new Rectangle(centre - xs / 4, y, 50, 50)
  }
}
