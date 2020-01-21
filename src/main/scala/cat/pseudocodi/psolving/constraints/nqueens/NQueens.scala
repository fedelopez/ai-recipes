package cat.pseudocodi.psolving.constraints.nqueens

import java.awt.{Color, Dimension}
import java.net.URL

import javax.swing._

/**
  * @author fede
  */
object NQueens {

  case class Square(row: Int, col: Int)

  def solution(queens: Int, squares: Int): List[Square] = {
    @scala.annotation.tailrec
    def doIt(paths: List[List[Square]]): List[Square] = {
      val head: List[Square] = paths.head
      val row: Int = head.length
      if (row == queens) head
      else {
        val columns: Seq[Int] = Range(0, squares).filter(column => head.forall(q => !isThreatening(q, Square(row, column))))
        if (columns.isEmpty) {
          doIt(paths.tail)
        } else {
          val newPaths: List[List[Square]] = columns.map(i => Square(row, i)).map(sq => sq :: head).toList
          doIt(newPaths ::: paths.tail)
        }
      }
    }
    doIt(List(List()))
  }

  def isThreatening(square: Square, other: Square): Boolean = {
    if (square.row == other.row) true
    else if (square.col == other.col) true
    else if (Math.abs(square.row - other.row) == Math.abs(square.col - other.col)) true
    else false
  }

  val color1 = new Color(255, 206, 158)
  val color2 = new Color(209, 139, 71)

  def main(args: Array[String]) {
    val frame = new JFrame("8 Queens")
    val layeredPane = new JLayeredPane()
    layeredPane.setPreferredSize(new Dimension(400, 400))
    for (row <- Range(0, 8)) {
      for (col <- Range(0, 8)) {
        val label: JLabel = new JLabel
        label.setOpaque(true)
        if (row % 2 == 0) label.setBackground(if (col % 2 == 0) color1 else color2)
        else label.setBackground(if (col % 2 == 0) color2 else color1)
        label.setBorder(BorderFactory.createLineBorder(Color.darkGray))
        label.setBounds(col * 50, row * 50, 50, 50)
        layeredPane.add(label, row + col)
      }
    }
    val resource: URL = getClass.getResource("queen.png")
    val icon: ImageIcon = new ImageIcon(resource)

    val squares: List[Square] = solution(8, 8)
    for (sq <- squares) {
      val queen: JLabel = new JLabel(icon)
      queen.setBounds(sq.col * 50, sq.row * 50, icon.getIconWidth, icon.getIconHeight)
      layeredPane.add(queen, 2, squares.indexOf(sq))
    }
    frame.setContentPane(layeredPane)
    frame.setVisible(true)
    frame.pack()
  }
}
