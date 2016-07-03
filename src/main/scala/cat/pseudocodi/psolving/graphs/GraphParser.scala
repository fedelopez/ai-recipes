package cat.pseudocodi.psolving.graphs

import java.util.ArrayList

import scala.collection.JavaConversions._
import scala.io.Source

/**
 * @author fede
 */
object GraphParser {

  lazy val source = Source.fromFile(getClass.getResource("layout.json").getFile).mkString

  def parse(json: String): Graph = {
    val res: List[Node] = com.codahale.jerkson.Json.parse[List[Node]](json)
    val jsonAsMap = com.codahale.jerkson.Json.parse[List[Map[String, Any]]](json)
    val allEdges: List[Edge] = edges(res, jsonAsMap, List())
    new Graph(res, GraphUtil.removeDuplicates(allEdges))
  }

  def edges(nodes: List[Node], jsonMap: List[Map[String, Any]], acc: List[Edge]): List[Edge] = {
    nodes.flatMap((n: Node) => nodeEdges(n, nodes.filter((other: Node) => n != other), jsonMap))
  }

  def nodeEdges(node: Node, otherNodes: List[Node], jsonMap: List[Map[String, Any]]): List[Edge] = {
    val map: Map[String, Any] = jsonMap.find((map: Map[String, Any]) => map("name").equals(node.name)).get
    val neighbors: Option[Any] = map.get("neighbors")
    val nodeNames: List[String] = toNodeNames(neighbors.get)
    otherNodes.filter((n: Node) => nodeNames.contains(n.name)).map((otherNode: Node) => new Edge(node, otherNode))
  }

  def toNodeNames(nodeNames: Any): List[String] = {
    nodeNames.asInstanceOf[ArrayList[String]].toList
  }

}
