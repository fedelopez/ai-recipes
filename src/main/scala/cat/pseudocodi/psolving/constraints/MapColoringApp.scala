package cat.pseudocodi.psolving.constraints

import java.awt.image.BufferedImage
import java.io.{ByteArrayOutputStream, File}
import java.util.Date
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JFrame, JLabel}

import cat.pseudocodi.psolving.graphs.Graph

import scala.swing.Point

/**
  * @author fede
  */
object MapColoringApp {

  def main(args: Array[String]) {
    val file: String = getClass.getResource("comarques.png").getFile
    val bi: BufferedImage = ImageIO.read(new File(file))
    val date: Date = new Date
    val graph: Graph = MapColoring.graph
    MapColoring.backtracking(graph).foreach(v => FloodFill.floodFillQueue(new Point(v.n.x, v.n.y), bi, v.color))
    System.out.println("time = " + (new Date().getTime - date.getTime))
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream
    ImageIO.write(bi, "png", baos)
    baos.flush
    val imageBytes: Array[Byte] = baos.toByteArray
    baos.close
    val frame = new JFrame("Map Coloring")
    frame.add(new JLabel(new ImageIcon(imageBytes)))
    frame.setVisible(true)
    frame.pack
  }
}
