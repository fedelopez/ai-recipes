package cat.pseudocodi.psolving.graphs

/**
  * @author fede
  */
sealed case class Node(name: String, x: Int, y: Int)

sealed case class Edge(nodeA: Node, nodeB: Node)

sealed case class Graph(nodes: List[Node], edges: List[Edge]) {

  def adjacent(a: Node, b: Node): Boolean =
    edges.exists((edge: Edge) => (edge.nodeA == a && edge.nodeB == b) || (edge.nodeA == b && edge.nodeB == a))

  def neighbors(a: Node): List[Node] = {
    edges.map((edge: Edge) => {
      if (edge.nodeA == a) edge.nodeB
      else if (edge.nodeB == a) edge.nodeA
      else null
    }).filter(_ != null)
  }

  def graphSearch(from: Node, goal: Node, f: (List[List[Node]], List[List[Node]]) => List[List[Node]]): List[Node] = {
    def doIt(visited: List[Node], frontier: List[List[Node]]): List[Node] = {
      if (frontier.isEmpty) throw new IllegalStateException("No solution: empty frontier")
      else {
        val path: List[Node] = frontier.head
        val lastNode: Node = path.reverse.head
        if (lastNode == goal) path
        else {
          val actions: List[Node] = neighbors(lastNode).filterNot((node: Node) => visited.contains(node))
          val paths: List[List[Node]] = actions.map((action: Node) => path ::: List(action))
          doIt(lastNode :: visited, f(frontier.tail, paths))
        }
      }
    }
    doIt(List(), List(List(from)))
  }

  def breadthFirstSearch(from: Node, goal: Node): List[Node] =
    graphSearch(from, goal, (frontier: List[List[Node]], paths: List[List[Node]]) => frontier ::: paths)

  def depthFirstSearch(from: Node, goal: Node): List[Node] =
    graphSearch(from, goal, (frontier: List[List[Node]], paths: List[List[Node]]) => paths ::: frontier)

}

object Graph {

  def removeDuplicates(edges: List[Edge]): List[Edge] = {
    def doIt(edges: List[Edge], acc: List[Edge]): List[Edge] = {
      if (edges.isEmpty) acc
      else {
        val e = edges.head
        if (acc.exists((edge: Edge) => e.nodeA == edge.nodeB && e.nodeB == edge.nodeA)) {
          doIt(edges.tail, acc)
        } else {
          doIt(edges.tail, e :: acc)
        }
      }
    }
    doIt(edges, List())
  }

  def adjacentOnPath(e: Edge, path: List[Node]): Boolean = {
    def doIt(nodes: List[Node]): Boolean = {
      if (nodes.length < 2) false
      else {
        val n1: Node = nodes.head
        val n2: Node = nodes.tail.head
        if (e.nodeA == n1 && e.nodeB == n2 || e.nodeA == n2 && e.nodeB == n1) true
        else doIt(nodes.tail)
      }
    }
    doIt(path)
  }
}
