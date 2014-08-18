package net.bluegroper.psolving


/**
 * @author fede
 */
case class Node(name: String, x: Int, y: Int)

case class Edge(nodeA: Node, nodeB: Node)

case class Graph(nodes: List[Node], edges: List[Edge]) {

  def adjacent(a: Node, b: Node): Boolean = {
    edges.find((edge: Edge) => (edge.nodeA == a && edge.nodeB == b) || (edge.nodeA == b && edge.nodeB == a)).nonEmpty
  }

  def neighbors(a: Node): List[Node] = {
    edges.map((edge: Edge) => {
      if (edge.nodeA == a) edge.nodeB
      else if (edge.nodeB == a) edge.nodeA
      else null
    }).filter((node: Node) => node != null)
  }

}
