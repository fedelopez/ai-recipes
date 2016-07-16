package cat.pseudocodi.psolving.constraints

import java.awt.Color

import cat.pseudocodi.psolving.graphs.{Graph, GraphParser, Node}

import scala.io.Source

/**
  * @author fede
  */

case class Variable(n: Node, color: Color)

object Domain {
  val amber: Color = new Color(255, 191, 0)
  val apricot: Color = new Color(251, 206, 177)
  val chartreuse: Color = new Color(127, 255, 0)
  val mustard: Color = new Color(255, 219, 88)
  val olive: Color = new Color(128, 128, 0)

  def colors = List(amber, apricot, chartreuse, mustard, olive)
}

object MapColoringApp {

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
