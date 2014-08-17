package net.bluegroper.psolving

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

/**
 * @author fede
 */
@RunWith(classOf[JUnitRunner])
class GraphTest extends FunSuite {

  trait TestSets {
    val nodeA = new Node("a", 0, 0)
    val nodeB = new Node("b", 1, 0)
    val nodeC = new Node("c", 1, 1)
    val nodeD = new Node("d", 0, 1)
    val nodeE = new Node("d", 1, 1)

    val graph: Graph = new Graph(List(nodeA, nodeB, nodeC), List(new Edge(nodeA, nodeC), new Edge(nodeB, nodeC)))
    val graph2: Graph = new Graph(List(nodeA, nodeB, nodeC, nodeD), List(new Edge(nodeA, nodeC), new Edge(nodeA, nodeD)))
  }

  test("adjacent") {
    new TestSets {
      assert(graph.adjacent(nodeA, nodeB) === false)
      assert(graph.adjacent(nodeB, nodeA) === false)

      assert(graph.adjacent(nodeA, nodeC) === true)
      assert(graph.adjacent(nodeC, nodeA) === true)

      assert(graph.adjacent(nodeB, nodeC) === true)
      assert(graph.adjacent(nodeC, nodeB) === true)
    }
  }

  //todo
  test("neighbors") {
    new TestSets {
      val actual: List[Node] = graph2.neighbors(nodeA)
      assert(actual.size === 2)
      assert(actual.contains(nodeC))
      assert(actual.contains(nodeD))
    }
  }


}
