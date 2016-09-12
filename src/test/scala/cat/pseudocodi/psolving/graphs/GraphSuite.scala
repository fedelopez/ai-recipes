package cat.pseudocodi.psolving.graphs

import org.scalatest.FunSuite

/**
  * @author fede
  */
class GraphSuite extends FunSuite {

  trait TestSets {
    val nA = Node("a", 0, 0)
    val nB = Node("b", 1, 0)
    val nC = Node("c", 2, 0)
    val nD = Node("d", 3, 0)
    val nE = Node("e", 4, 0)
    val nF = Node("f", 5, 0)
    val nG = Node("g", 6, 0)
    val nH = Node("h", 7, 0)

    val g: Graph = Graph(List(nA, nB, nC), List(Edge(nA, nC), Edge(nB, nC)))
    val g2: Graph = Graph(List(nA, nB, nC, nD, nE, nF, nG), List(Edge(nA, nH), Edge(nA, nC), Edge(nC, nB), Edge(nC, nD), Edge(nD, nE), Edge(nE, nF), Edge(nB, nG)))
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
      val g3: Graph = Graph(List(nA, nB, nC, nD, nE, nF, nG), List(Edge(nA, nH), Edge(nA, nC), Edge(nC, nB), Edge(nC, nD), Edge(nC, nF), Edge(nD, nE), Edge(nE, nF), Edge(nB, nG)))
      val res: List[Node] = g3.breadthFirstSearch(nA, nF)
      assert(res.length === 3)
      assert(res.head === nA)
      assert(res(1) === nC)
      assert(res(2) === nF)
    }
  }

  /**
    * https://en.wikipedia.org/wiki/Breadth-first_search#/media/File:Breadth-first-tree.svg
    */
  test("breadthFirstSearch order traversal on a tree like structure") {
    val n1 = Node("1", 0, 0)
    val n2 = Node("2", 1, 0)
    val n3 = Node("3", 2, 0)
    val n4 = Node("4", 3, 0)
    val n5 = Node("5", 4, 0)
    val n6 = Node("6", 5, 0)
    val n7 = Node("7", 6, 0)
    val n8 = Node("8", 7, 0)
    val n9 = Node("9", 8, 0)
    val n10 = Node("10", 9, 0)
    val n11 = Node("11", 10, 0)
    val n12 = Node("12", 11, 0)

    val edges: List[Edge] = List(
      Edge(n1, n2), Edge(n1, n3), Edge(n1, n4),
      Edge(n2, n5), Edge(n2, n6),
      Edge(n4, n7), Edge(n4, n8),
      Edge(n5, n9), Edge(n5, n10),
      Edge(n7, n11), Edge(n7, n12),
      Edge(n9, n11))

    val graph: Graph = Graph(List(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12), edges)
    val res: List[Node] = graph.breadthFirstSearch(n1, n11)
    assert(res.length === 4)
    assert(res.head === n1)
    assert(res(1) === n4)
    assert(res(2) === n7)
    assert(res(3) === n11)
  }

  /**
    * https://en.wikipedia.org/wiki/Breadth-first_search#/media/File:Breadth-first-tree.svg
    */
  test("depthFirstSearch order traversal on a tree like structure") {
    val n1 = Node("1", 0, 0)
    val n2 = Node("2", 1, 0)
    val n3 = Node("3", 2, 0)
    val n4 = Node("4", 3, 0)
    val n5 = Node("5", 4, 0)
    val n6 = Node("6", 5, 0)
    val n7 = Node("7", 6, 0)
    val n8 = Node("8", 7, 0)
    val n9 = Node("9", 8, 0)
    val n10 = Node("10", 9, 0)
    val n11 = Node("11", 10, 0)
    val n12 = Node("12", 11, 0)

    val edges: List[Edge] = List(
      Edge(n1, n2), Edge(n1, n3), Edge(n1, n4),
      Edge(n2, n5), Edge(n2, n6),
      Edge(n4, n7), Edge(n4, n8),
      Edge(n5, n9), Edge(n5, n10),
      Edge(n7, n11), Edge(n7, n12),
      Edge(n9, n11))

    val graph: Graph = Graph(List(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12), edges)
    val res: List[Node] = graph.depthFirstSearch(n1, n11)
    assert(res.length === 5)
    assert(res.head === n1)
    assert(res(1) === n2)
    assert(res(2) === n5)
    assert(res(3) === n9)
    assert(res(4) === n11)
  }

  test("depthFirstSearch: A to F when shorter path available via C is missed") {
    new TestSets {
      val g3: Graph = Graph(List(nA, nB, nC, nD, nE, nF, nG), List(Edge(nA, nH), Edge(nA, nC), Edge(nC, nB), Edge(nC, nD), Edge(nC, nF), Edge(nD, nE), Edge(nE, nF), Edge(nB, nG)))
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
      val edges: List[Edge] = List(Edge(nA, nB), Edge(nA, nC), Edge(nA, nE), Edge(nB, nD), Edge(nB, nF), Edge(nC, nG), Edge(nE, nF))
      val graph: Graph = Graph(List(nA, nB, nC, nD, nE, nF, nG), edges)
      val res: List[Node] = graph.depthFirstSearch(nA, nE)
      assert(res.length === 4)
      assert(res.head === nA)
      assert(res(1) === nB)
      assert(res(2) === nF)
      assert(res(3) === nE)
    }
  }
}
