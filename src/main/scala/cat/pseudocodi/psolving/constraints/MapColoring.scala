package cat.pseudocodi.psolving.constraints

import java.awt.image.BufferedImage
import java.awt.{Color, Point}
import java.io.{ByteArrayOutputStream, File}
import java.util.Date
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JFrame, JLabel}

import cat.pseudocodi.psolving.graphs.{Graph, GraphParser, Node}

import scala.io.Source

/**
  * @author fede
  */

case class Variable(n: Node, color: Color)

object Domain {
  val c1: Color = new Color(59, 89, 152)
  val c2: Color = new Color(139, 157, 195)
  val c3: Color = new Color(223, 227, 238)
  val c4: Color = new Color(247, 247, 247)
  val c5: Color = new Color(91, 192, 222)

  def colors = List(c1, c2, c3, c4, c5)
}

object MapColoring {

  val source = Source.fromFile(getClass.getResource("comarques.json").getFile).mkString
  val graph: Graph = GraphParser.parse(source)

  def backtracking(g: Graph): List[Variable] = {
    def doIt(nodes: List[Node], variables: List[Variable]): List[Variable] = {
      if (nodes.isEmpty) {
        variables
      } else {
        val n: Node = nodes.head
        val neighbors: List[Node] = g.neighbors(n).filterNot(p => variables.exists(v => v.n == p))
        val usedColors: List[Color] = variables.filter(v => g.adjacent(v.n, n)).map(v => v.color)
        val c: Color = Domain.colors.diff(usedColors).head
        val newVars: List[Variable] = Variable(n, c) :: variables
        val remaining: List[Node] = nodes.tail.filterNot(p => neighbors.contains(p))
        doIt(neighbors ::: remaining, newVars)
      }
    }
    doIt(g.nodes, List())
  }

  def main(args: Array[String]) {
    val file: String = getClass.getResource("comarques.png").getFile
    val bi: BufferedImage = ImageIO.read(new File(file))
    val date: Date = new Date
    val graph: Graph = MapColoring.graph
    backtracking(graph).foreach(v => FloodFill.floodFillQueue(new Point(v.n.x, v.n.y), bi, v.color))
    System.out.println("time = " + (new Date().getTime - date.getTime))
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    ImageIO.write(bi, "png", baos)
    baos.flush
    val imageBytes: Array[Byte] = baos.toByteArray
    baos.close
    val frame = new JFrame("Map Coloring")
    frame.add(new JLabel(new ImageIcon(imageBytes)))
    frame.setVisible(true)
    frame.pack
  }

}
