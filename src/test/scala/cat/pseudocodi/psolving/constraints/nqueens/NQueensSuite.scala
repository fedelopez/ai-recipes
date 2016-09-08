package cat.pseudocodi.psolving.constraints.nqueens

import cat.pseudocodi.psolving.constraints.nqueens.NQueens.Square
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author fede
  */
class NQueensSuite extends FlatSpec with Matchers {

  it should "position 2 queens in a 3 x 3 board" in {
    val solution: List[Square] = NQueens.solution(2, 3)
    val result: Boolean = solution.forall(queen => solution.filter(q => q != queen).forall(other => !NQueens.isThreatening(queen, other)))
    assert(result)
  }

  it should "position 8 queens in a 8 x 8 board" in {
    val solution: List[Square] = NQueens.solution(8, 8)
    val result: Boolean = solution.forall(queen => solution.filter(q => q != queen).forall(other => !NQueens.isThreatening(queen, other)))
    assert(result)
  }

  it should "return 8 queens in different positions" in {
    val solution: List[Square] = NQueens.solution(8, 8)
    for (elem <- solution) {
      assert(elem.col > -1 && elem.col < 8)
      assert(elem.row > -1 && elem.row < 8)
      val count: Int = solution.count(q => q != elem)
      assert(count == 7)
    }
  }

  it should "be threatening when in same column" in {
    assert(NQueens.isThreatening(Square(0, 0), Square(1, 0)))
  }

  it should "be threatening when in same row" in {
    assert(NQueens.isThreatening(Square(0, 0), Square(0, 1)))
  }

  it should "be threatening when in same diagonal" in {
    assert(NQueens.isThreatening(Square(0, 0), Square(2, 2)))
  }

  it should "be threatening when in same diagonal next row" in {
    assert(NQueens.isThreatening(Square(1, 2), Square(2, 3)))
  }

  it should "not be threatening when in diff diagonal, row and column" in {
    assert(!NQueens.isThreatening(Square(1, 1), Square(2, 3)))
  }

}
