package cat.pseudocodi.psolving.constraints

import java.awt.image.BufferedImage
import java.awt.{Color, Point}
import java.io.{ByteArrayOutputStream, File}
import java.util.Date
import java.util.concurrent.atomic.AtomicInteger
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JFrame, JLabel}

/**
  * Constraint satisfaction problems
  *
  * - https://www.youtube.com/watch?v=hJ9WOiueJes&feature=youtu.be
  * - https://www.youtube.com/watch?v=d1KyYyLmGpA&index=8&list=PLUl4u3cNGP63gFHB6xb-kVBiQHYe_4hSi
  *
  * @author fede
  */
object MapColoring {

  val target: Int = new Color(149, 149, 149).getRGB
  val yellowRGB: Int = Color.YELLOW.getRGB

  def main(args: Array[String]) {
    val file: String = MapColoring.getClass.getResource("vall-daran.png").getFile
    val bi: BufferedImage = ImageIO.read(new File(file))
    val width: Int = bi.getWidth
    val height: Int = bi.getHeight
    val date: Date = new Date
    val visited = new AtomicInteger(0)

    floodFillQueue(new Point(width / 2, height / 2), bi)
    System.out.println("time = " + (new Date().getTime - date.getTime))

    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    ImageIO.write(bi, "png", baos)
    baos.flush
    val imageBytes: Array[Byte] = baos.toByteArray
    baos.close

    val imageIcon: ImageIcon = new ImageIcon(imageBytes)
    val frame = new JFrame("MapColoring")
    frame.add(new JLabel(imageIcon))
    frame.setVisible(true)
    frame.pack
  }

  /**
    * Flood fill using recursion
    *
    * -Xss4M
    *
    * https://en.wikipedia.org/wiki/Flood_fill
    */
  def floodFillRecursive(p: Point, bi: BufferedImage) {
    val color: Int = bi.getRGB(p.x, p.y)
    if (color == target) {
      bi.setRGB(p.x, p.y, yellowRGB)
      floodFillRecursive(new Point(p.x - 1, p.y), bi)
      floodFillRecursive(new Point(p.x + 1, p.y), bi)
      floodFillRecursive(new Point(p.x, p.y - 1), bi)
      floodFillRecursive(new Point(p.x, p.y + 1), bi)
    }
  }

  /**
    * Flood fill using queues
    *
    * https://en.wikipedia.org/wiki/Flood_fill
    */
  def floodFillQueue(initial: Point, bi: BufferedImage) {
    val q = scala.collection.mutable.Queue[Point]()
    q.enqueue(initial)
    val processed = scala.collection.mutable.Set[Point]()
    while (!q.isEmpty) {
      val p = q.dequeue()
      val color = bi.getRGB(p.x, p.y)
      if (color == target) {
        processed.add(p)
        bi.setRGB(p.x, p.y, yellowRGB)
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
}
