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

  def breadthFirstSearch(initial: Node, goal: Node): List[Node] = {

    def doIt(visited: List[Node], frontier: List[List[Node]]): List[Node] = {
      if (frontier.isEmpty) throw new IllegalStateException("No solution: empty frontier")
      else {
        val path: List[Node] = frontier.head
        val lastNode: Node = path.reverse.head
        if (lastNode == goal) {
          path
        } else {
          val actions: List[Node] = neighbors(lastNode).filterNot((node: Node) => visited.contains(node))
          val paths: List[List[Node]] = actions.map((action: Node) => path ::: List(action))
          val newFrontier: List[List[Node]] = frontier.filterNot((nodes: List[Node]) => nodes == path) ::: paths
          doIt(lastNode :: visited, newFrontier)
        }

      }
    }
    doIt(List(), List(List(initial)))
  }


}
