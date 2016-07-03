package cat.pseudocodi.psolving.graphs

import cat.pseudocodi.psolving.graphs.GraphParser.{parse, source}
import org.scalatest.FunSuite

/**
 * @author fede
 */
class GraphParserSuite extends FunSuite {

  trait TestSets {
    val croydon = new Node("Croydon", 1, 2)
    val petersham = new Node("Petersham", 1, 3)
  }

  test("parse") {

    new TestSets {
      val json: String = "[{\"name\":\"Croydon\",\"x\": 1,\"y\": 2,\"neighbors\":[\"Petersham\"]},{\"name\":\"Petersham\",\"x\": 1,\"y\": 3,\"neighbors\":[\"Croydon\"]}]"
      val actual: Graph = parse(json)

      assert(actual.edges.length === 1)
      assert(actual.adjacent(croydon, petersham) === true)
    }
  }

  test("parse source") {

    new TestSets {
      val actual: Graph = parse(source)

      assert(actual.nodes.length === 16)
      assert(actual.edges.length === 26)

    }
  }

}
