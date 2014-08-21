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
    val nA = new Node("a", 0, 0)
    val nB = new Node("b", 1, 0)
    val nC = new Node("c", 2, 0)
    val nD = new Node("d", 3, 0)
    val nE = new Node("d", 4, 0)
    val nF = new Node("f", 5, 0)
    val nG = new Node("g", 6, 0)
    val nH = new Node("h", 7, 0)

    val g: Graph = new Graph(List(nA, nB, nC), List(new Edge(nA, nC), new Edge(nB, nC)))
    val gB: Graph = new Graph(List(nA, nB, nC, nD, nE, nF, nG), List(new Edge(nA, nH), new Edge(nA, nC), new Edge(nC, nB), new Edge(nC, nD), new Edge(nD, nE), new Edge(nE, nF), new Edge(nB, nG)))
  }

  test("adjacent") {
    new TestSets {
      assert(g.adjacent(nA, nB) === false)
      assert(g.adjacent(nB, nA) === false)

      assert(g.adjacent(nA, nC) === true)
      assert(g.adjacent(nC, nA) === true)

      assert(g.adjacent(nB, nC) === true)
      assert(g.adjacent(nC, nB) === true)
    }
  }

  test("neighbors: no elements") {
    new TestSets {
      val actualForA: List[Node] = g.neighbors(nD)
      assert(actualForA.size === 0)
    }
  }

  test("neighbors: one element") {
    new TestSets {
      val actualForA: List[Node] = g.neighbors(nA)
      assert(actualForA.size === 1)
      assert(actualForA.contains(nC))

      val actualForB: List[Node] = g.neighbors(nB)
      assert(actualForB.size === 1)
      assert(actualForB.contains(nC))
    }
  }

  test("neighbors: two elements") {
    new TestSets {
      val actualForC: List[Node] = g.neighbors(nC)
      assert(actualForC.size === 2)
      assert(actualForC.contains(nA))
      assert(actualForC.contains(nB))
    }
  }

  test("breadthFirstSearch") {
    new TestSets {
      val res: List[Node] = g.breadthFirstSearch(nA, nB)
      assert(res.length === 3)
      assert(res(0) === nA)
      assert(res(1) === nC)
      assert(res(2) === nB)
    }
  }


}
