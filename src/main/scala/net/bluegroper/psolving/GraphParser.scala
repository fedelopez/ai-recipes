package net.bluegroper.psolving

import scala.io.Source
import java.util.ArrayList
import scala.collection.immutable.TreeSet

import scala.collection.JavaConversions._

/**
 * @author fede
 */
object GraphParser {

  val source = Source.fromFile(getClass.getResource("layout.json").getFile).mkString

  def parse(json: String): Graph = {
    val res: List[Node] = com.codahale.jerkson.Json.parse[List[Node]](json)
    val jsonAsMap = com.codahale.jerkson.Json.parse[List[Map[String, Any]]](json)

    val allEdges: List[Edge] = edges(res, jsonAsMap, List())

    implicit val ord = new Ordering[Edge] {
      def compare(e1: Edge, e2: Edge) = {
        if ((e1.nodeA.name.equals(e2.nodeA.name) && e1.nodeB.name.equals(e2.nodeB.name)) || (e1.nodeA.name.equals(e2.nodeB.name) && e1.nodeB.name.equals(e2.nodeA.name))) 0
        else {
          if (e1.nodeA.name.equals(e2.nodeA.name)) e1.nodeB.name.compareTo(e2.nodeB.name)
          else e1.nodeA.name.compareTo(e2.nodeA.name)
        }
      }
    }

    val noDuplicates: TreeSet[Edge] = TreeSet()(ord) ++ allEdges
    new Graph(res, noDuplicates.toList)
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
