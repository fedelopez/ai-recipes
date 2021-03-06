package cat.pseudocodi.psolving.graphs

import org.scalatest.FunSuite

/**
  * @author fede
  */
class GraphObjectSuite extends FunSuite {

  trait TestSets {
    val nodeA = Node("a", 0, 0)
    val nodeB = Node("b", 1, 0)
    val nodeC = Node("c", 1, 1)
    val nodeD = Node("d", 0, 1)
    val nodeE = Node("e", 1, 1)

    val edgeAB = Edge(nodeA, nodeB)
    val edgeBA = Edge(nodeB, nodeA)
    val edgeAC = Edge(nodeA, nodeC)
    val edgeCA = Edge(nodeC, nodeA)
  }

  test("remove duplicates") {
    new TestSets {
      val actual: List[Edge] = Graph.removeDuplicates(edgeAB :: edgeBA :: List())
      assert(actual.length === 1)
      assert(actual.contains(edgeAB) || actual.contains(edgeBA))
    }
  }

  test("remove duplicates for four edges") {
    new TestSets {
      val actual: List[Edge] = Graph.removeDuplicates(edgeAB :: edgeBA :: edgeAC :: edgeCA :: List())
      assert(actual.length === 2)
      assert(actual.contains(edgeAB) || actual.contains(edgeBA))
      assert(actual.contains(edgeAC) || actual.contains(edgeCA))
    }
  }

  test("should return true when 2 adjacent nodes on a given path") {
    new TestSets {
      val nodes = List(nodeA, nodeB, nodeC, nodeD, nodeE)
      assert(Graph.adjacentOnPath(Edge(nodeC, nodeD), nodes) === true)
    }
  }

  test("should return true when 2 adjacent nodes on a given path are last ones") {
    new TestSets {
      val nodes = List(nodeA, nodeB, nodeC, nodeD, nodeE)
      assert(Graph.adjacentOnPath(Edge(nodeD, nodeE), nodes) === true)
    }
  }

  test("should return false when no adjacent nodes on a given path") {
    new TestSets {
      val nodes = List(nodeA, nodeB, nodeC, nodeD, nodeE)
      assert(Graph.adjacentOnPath(Edge(nodeB, nodeD), nodes) === false)
    }
  }
}
