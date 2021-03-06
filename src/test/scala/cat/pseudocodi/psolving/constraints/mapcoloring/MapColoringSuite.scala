package cat.pseudocodi.psolving.constraints.mapcoloring

import java.awt.Color

import cat.pseudocodi.psolving.graphs.GraphParser._
import cat.pseudocodi.psolving.graphs.{Edge, Graph, Node}
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  * @author fede
  */
class MapColoringSuite extends FlatSpec with Matchers {

  it should "load map of catalan comarques graph nodes" in {
    val graph: Graph = MapColoring.graph
    assert(graph.nodes.size == 42)
  }

  it should "load map of catalan comarques graph edges" in {
    val graph: Graph = MapColoring.graph
    val node: Node = Node("el Pla de l'Estany", 590, 225)
    assert(graph.adjacent(node, Node("l'Alt Empordà", 625, 170)))
    assert(graph.adjacent(node, Node("la Garrotxa", 540, 205)))
    assert(graph.adjacent(node, Node("el Gironès", 600, 271)))
    assert(!graph.adjacent(node, Node("el Baix Empordà", 650, 275)))
  }

  "backtracking" should "return adjacent regions with different colors" in {
    val nsw = Node("NSW", 0, 0)
    val nt = Node("NT", 0, 0)
    val sa = Node("SA", 0, 0)
    val qld = Node("QLD", 0, 0)
    val vic = Node("VIC", 0, 0)
    val wa = Node("WA", 0, 0)
    val nodes = List(nsw, nt, sa, qld, vic, wa)
    val edges = List(Edge(wa, nt), Edge(wa, sa), Edge(nt, qld), Edge(nt, sa), Edge(qld, sa), Edge(qld, nsw), Edge(nsw, sa), Edge(nsw, vic), Edge(vic, sa))
    val g = Graph(nodes, edges)
    val vars: List[Variable] = MapColoring.backtracking(g)
    assert(edges.forall(edge => color(edge.nodeA, vars) != color(edge.nodeB, vars)))
  }

  "backtracking" should "return adjacent regions with different colors on large graph" in {
    val source = Source.fromFile(getClass.getResource("comarques.json").getFile).mkString
    val g: Graph = parse(source)
    val edges = g.edges
    val vars: List[Variable] = MapColoring.backtracking(g)
    assert(vars.size === g.nodes.size)
    assert(edges.forall(edge => color(edge.nodeA, vars) != color(edge.nodeB, vars)))
  }

  def color(n: Node, vars: List[Variable]): Color = {
    vars.find(v => v.n == n).map(_.color).get
  }
}
