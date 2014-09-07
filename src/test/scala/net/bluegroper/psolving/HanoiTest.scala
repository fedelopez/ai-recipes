package net.bluegroper.psolving

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

/**
 * @author fede
 */
@RunWith(classOf[JUnitRunner])
class HanoiTest extends FunSuite {

  trait TestSets extends Hanoi {

    object SmallDisk extends Disk(1)

    object MediumDisk extends Disk(2)

    object LargeDisk extends Disk(3)


    val initial: State = new State(List(new Peg("Left", 0, List(SmallDisk, MediumDisk, LargeDisk)), new Peg("Middle", 1, List()), new Peg("Right", 2, List())))
    val goal: State = new State(List(new Peg("Left", 0, List()), new Peg("Middle", 1, List()), new Peg("Right", 2, List(SmallDisk, MediumDisk, LargeDisk))))

    val emptyPeg: Peg = new Peg("Left", 0)
    val smallDiskOnPeg: Peg = new Peg("Left", 0, List(SmallDisk))
    val mediumDiskOnPeg: Peg = new Peg("Left", 0, List(MediumDisk))
    val smallAndLargeDiskOnPeg: Peg = new Peg("Left", 0, List(SmallDisk, LargeDisk))

  }

  test("hanoi: 3 disks, 3 pegs") {
    new TestSets {
      val actual: List[State] = hanoi(initial, goal)
      assert(actual.length === 8)
      assert(actual.head === initial)
      assert(actual.reverse.head === goal)
      actual.foreach(println)
    }
  }

  test("init") {
    new TestSets {
      val res: State = initial
      assert(res.pegs(0).disks.length === 3)
      assert(res.pegs(1).disks.length === 0)
      assert(res.pegs(2).disks.length === 0)

      assert(res.pegs(0).disks(0) === SmallDisk)
      assert(res.pegs(0).disks(1) === MediumDisk)
      assert(res.pegs(0).disks(2) === LargeDisk)
    }
  }

  test("Peg: accepts when empty") {
    new TestSets {
      assert(emptyPeg.accepts(SmallDisk))
      assert(emptyPeg.accepts(MediumDisk))
      assert(emptyPeg.accepts(LargeDisk))
    }
  }

  test("Peg: accepts when larger disk on peg") {
    new TestSets {
      assert(mediumDiskOnPeg.accepts(SmallDisk))
    }
  }

  test("Peg: do not accept when smaller disk on peg") {
    new TestSets {
      assert(smallAndLargeDiskOnPeg.accepts(MediumDisk) === false)
    }
  }

  test("State: neighbors") {
    new TestSets {
      val actual: List[State] = initial.neighbors
      assert(actual.size === 2)

      val expected1: State = new State(List(new Peg("Left", 0, List(MediumDisk, LargeDisk)), new Peg("Middle", 1, List(SmallDisk)), new Peg("Right", 2)))
      val expected2: State = new State(List(new Peg("Left", 0, List(MediumDisk, LargeDisk)), new Peg("Middle", 1), new Peg("Right", 2, List(SmallDisk))))

      assert(actual.contains(expected1))
      assert(actual.contains(expected2))
    }
  }

  test("State: move") {
    new TestSets {

      val result1: State = initial.move(0, 1)
      val expected1: State = new State(List(new Peg("Left", 0, List(MediumDisk, LargeDisk)), new Peg("Middle", 1, List(SmallDisk)), new Peg("Right", 2)))
      assert(expected1 === result1)

      val expected2: State = new State(List(new Peg("Left", 0, List(MediumDisk, LargeDisk)), new Peg("Middle", 1), new Peg("Right", 2, List(SmallDisk))))
      val result2: State = initial.move(0, 2)
      assert(expected2 === result2)

    }
  }

}
