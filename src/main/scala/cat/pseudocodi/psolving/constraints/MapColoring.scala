package cat.pseudocodi.psolving.constraints

import java.awt.Color

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
    def doIt(nodes: List[Node], visited: List[Variable]): List[Variable] = {
      if (nodes.size > 0) {
        val n: Node = nodes.head
        val neighbors: List[Node] = g.neighbors(n).filterNot(p => visited.exists(v => v.n == p))
        val usedColors: List[Color] = visited.filter(v => g.adjacent(v.n, n)).map(v => v.color)
        val c: Color = Domain.colors.diff(usedColors).head
        val v: Variable = Variable(n, c)
        val newVars: List[Variable] = v :: visited
        val remaining: List[Node] = nodes.tail.filterNot(p => neighbors.contains(p))
        doIt(neighbors ::: remaining, newVars)
      } else {
        visited
      }
    }
    doIt(g.nodes, List())
  }

}
