package cat.pseudocodi.psolving.graphs

import org.scalatest.FunSuite

/**
  * @author fede
  */
class GraphSuite extends FunSuite {

  trait TestSets {
    val nA = new Node("a", 0, 0)
    val nB = new Node("b", 1, 0)
    val nC = new Node("c", 2, 0)
    val nD = new Node("d", 3, 0)
    val nE = new Node("e", 4, 0)
    val nF = new Node("f", 5, 0)
    val nG = new Node("g", 6, 0)
    val nH = new Node("h", 7, 0)

    val g: Graph = new Graph(List(nA, nB, nC), List(new Edge(nA, nC), new Edge(nB, nC)))
    val g2: Graph = new Graph(List(nA, nB, nC, nD, nE, nF, nG), List(new Edge(nA, nH), new Edge(nA, nC), new Edge(nC, nB), new Edge(nC, nD), new Edge(nD, nE), new Edge(nE, nF), new Edge(nB, nG)))
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
      assert(res.head === nA)
      assert(res(1) === nC)
      assert(res(2) === nB)
    }
  }

  test("breadthFirstSearch: C to A") {
    new TestSets {
      val res: List[Node] = g.breadthFirstSearch(nC, nA)
      assert(res.length === 2)
      assert(res.head === nC)
      assert(res(1) === nA)
    }
  }

  test("breadthFirstSearch graph2: B to A") {
    new TestSets {
      val res: List[Node] = g2.breadthFirstSearch(nB, nA)
      assert(res.length === 3)
      assert(res.head === nB)
      assert(res(1) === nC)
      assert(res(2) === nA)
    }
  }

  test("breadthFirstSearch graph2: A to F") {
    new TestSets {
      val res: List[Node] = g2.breadthFirstSearch(nA, nF)
      assert(res.length === 5)
      assert(res.head === nA)
      assert(res(1) === nC)
      assert(res(2) === nD)
      assert(res(3) === nE)
      assert(res(4) === nF)
    }
  }

  test("breadthFirstSearch: A to F when shorter path available via C") {
    new TestSets {
      val g3: Graph = new Graph(List(nA, nB, nC, nD, nE, nF, nG), List(new Edge(nA, nH), new Edge(nA, nC), new Edge(nC, nB), new Edge(nC, nD), new Edge(nC, nF), new Edge(nD, nE), new Edge(nE, nF), new Edge(nB, nG)))
      val res: List[Node] = g3.breadthFirstSearch(nA, nF)
      assert(res.length === 3)
      assert(res.head === nA)
      assert(res(1) === nC)
      assert(res(2) === nF)
    }
  }

  test("depthFirstSearch: A to F when shorter path available via C is missed") {
    new TestSets {
      val g3: Graph = new Graph(List(nA, nB, nC, nD, nE, nF, nG), List(new Edge(nA, nH), new Edge(nA, nC), new Edge(nC, nB), new Edge(nC, nD), new Edge(nC, nF), new Edge(nD, nE), new Edge(nE, nF), new Edge(nB, nG)))
      val res: List[Node] = g3.depthFirstSearch(nA, nF)
      assert(res.length === 5)
      assert(res.head === nA)
      assert(res(1) === nC)
      assert(res(2) === nD)
      assert(res(3) === nE)
      assert(res(4) === nF)
    }
  }

  /**
    * https://en.wikipedia.org/wiki/Depth-first_search#/media/File:Graph.traversal.example.svg
    */
  test("depthFirstSearch visits the edges traversed in a search forming a Trémaux tree") {
    new TestSets {
      val edges: List[Edge] = List(new Edge(nA, nB), new Edge(nA, nC), new Edge(nA, nE), new Edge(nB, nD), new Edge(nB, nF), new Edge(nC, nG), new Edge(nE, nF))
      val graph: Graph = new Graph(List(nA, nB, nC, nD, nE, nF, nG), edges)
      val res: List[Node] = graph.depthFirstSearch(nA, nE)
      assert(res.length === 4)
      assert(res.head === nA)
      assert(res(1) === nB)
      assert(res(2) === nF)
      assert(res(3) === nE)
    }
  }
}
