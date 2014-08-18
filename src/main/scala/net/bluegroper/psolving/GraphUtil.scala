package net.bluegroper.psolving


/**
 * @author fede
 */
object GraphUtil {

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

}
