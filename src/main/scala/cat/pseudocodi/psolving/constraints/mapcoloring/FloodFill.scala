package cat.pseudocodi.psolving.constraints.mapcoloring

import java.awt.image.BufferedImage
import java.awt.{Color, Point}

/**
  * https://en.wikipedia.org/wiki/Flood_fill
  *
  * @author fede
  */
object FloodFill {

  val targetColor: Int = new Color(142, 215, 176).getRGB
  val replacementColor: Int = new Color(149, 149, 149).getRGB

  /**
    * Flood fill using recursion
    *
    * cons: needs -Xss4M
    */
  def floodFillRecursive(p: Point, bi: BufferedImage) {
    val color: Int = bi.getRGB(p.x, p.y)
    if (color == targetColor) {
      bi.setRGB(p.x, p.y, replacementColor)
      floodFillRecursive(new Point(p.x - 1, p.y), bi)
      floodFillRecursive(new Point(p.x + 1, p.y), bi)
      floodFillRecursive(new Point(p.x, p.y - 1), bi)
      floodFillRecursive(new Point(p.x, p.y + 1), bi)
    }
  }

  /**
    * Flood fill using queues
    */
  def floodFillQueue(initial: Point, bi: BufferedImage, replacementColor: Color) {
    val q = scala.collection.mutable.Queue[Point]()
    q.enqueue(initial)
    val processed = scala.collection.mutable.Set[Point]()
    while (!q.isEmpty) {
      val p = q.dequeue()
      val color = bi.getRGB(p.x, p.y)
      if (color == targetColor) {
        processed.add(p)
        bi.setRGB(p.x, p.y, replacementColor.getRGB)
        val left: Point = new Point(p.x - 1, p.y)
        if (!processed.contains(left)) q.enqueue(left)
        val right: Point = new Point(p.x + 1, p.y)
        if (!processed.contains(right)) q.enqueue(right)
        val up: Point = new Point(p.x, p.y - 1)
        if (!processed.contains(up)) q.enqueue(up)
        val down: Point = new Point(p.x, p.y + 1)
        if (!processed.contains(down)) q.enqueue(down)
      }
    }
  }

  /**
    * Flood fill using a loop for the west and east directions as an optimization to avoid the overhead of stack or queue management
    */
  def floodFillQueueEastWest(initial: Point, bi: BufferedImage) {
    val q = scala.collection.mutable.Queue[Point]()
    q.enqueue(initial)
    while (!q.isEmpty) {
      val p = q.dequeue()
      var minX = p.x
      var keepGoing = true
      while (keepGoing) {
        if (bi.getRGB(minX, p.y) != targetColor) keepGoing = false
        else minX = minX - 1
      }
      var maxX = p.x
      keepGoing = true
      while (keepGoing) {
        if (bi.getRGB(maxX, p.y) != targetColor) keepGoing = false
        else maxX = maxX + 1
      }
      (minX to maxX).foreach(x => {
        bi.setRGB(x, p.y, replacementColor)
        if (bi.getRGB(x, p.y - 1) == targetColor) q.enqueue(new Point(x, p.y - 1))
        if (bi.getRGB(x, p.y + 1) == targetColor) q.enqueue(new Point(x, p.y + 1))
      })
    }
  }
}
