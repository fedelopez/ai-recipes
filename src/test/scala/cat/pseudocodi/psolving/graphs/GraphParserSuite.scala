package cat.pseudocodi.psolving.graphs

import cat.pseudocodi.psolving.graphs.GraphParser.parse
import org.scalatest.FunSuite

import scala.io.Source

/**
  * @author fede
  */
class GraphParserSuite extends FunSuite {

  test("parse") {
    val json: String = "[{\"name\":\"Croydon\",\"x\": 1,\"y\": 2,\"neighbors\":[\"Petersham\"]},{\"name\":\"Petersham\",\"x\": 1,\"y\": 3,\"neighbors\":[\"Croydon\"]}]"
    val actual: Graph = parse(json)
    assert(actual.edges.length === 1)
    assert(actual.adjacent(Node("Croydon", 1, 2), Node("Petersham", 1, 3)) === true)
  }

  test("parse source") {
    val source = Source.fromFile(getClass.getResource("layout.json").getFile).mkString
    val actual: Graph = parse(source)
    assert(actual.nodes.length === 16)
    assert(actual.edges.length === 26)
  }

}
