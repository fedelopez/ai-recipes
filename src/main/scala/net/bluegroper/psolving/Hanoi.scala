package net.bluegroper.psolving

/**
 * Generic solution to the classic Towers of Hanoi problem.
 *
 * @author fede
 */
trait Hanoi {

  type Steps = List[String]

  def hanoi(initial: State, goal: State): List[State] = {

    def doMoveDisks(visited: List[State], frontier: List[List[State]]): List[State] = {
      if (frontier.isEmpty) throw new IllegalStateException("No solution")
      else {
        val path: List[State] = frontier.head
        val state: State = path.head
        if (state == goal) {
          path.reverse
        } else {
          val neighbors: List[State] = state.neighbors.filterNot((neighbor: State) => visited.contains(neighbor))
          val paths: List[List[State]] = neighbors.map((state: State) => state :: path)
          val newFrontier: List[List[State]] = frontier.filter((states: List[State]) => states != path) ::: paths
          doMoveDisks(state :: visited, newFrontier)
        }
      }
    }

    doMoveDisks(List(), List(List(initial)))
  }

  case class Disk(diameter: Integer) {
    def <(other: Disk) = this.diameter < other.diameter
  }

  case class Peg(name: String, position: Int, disks: List[Disk]) {

    def this(name: String, position: Int) = this(name, position, List())

    def accepts(disk: Disk): Boolean = {
      if (disks.isEmpty) true
      else disk < disks.head
    }

    def addDisk(disk: Disk): Peg = new Peg(name, position, disk :: disks)

    def removeTopDisk(): Peg = new Peg(name, position, disks.tail)
  }

  case class State(pegs: List[Peg]) {

    def neighbors: List[State] = {
      val map: List[State] = pegs.flatMap((a: Peg) => pegs.filterNot((other: Peg) => other == a).map((b: Peg) => move(a.position, b.position)))
      map.filter((state: State) => state != this)
    }

    def move(originIndex: Int, destIndex: Int): State = {
      val origin: Peg = pegs(originIndex)
      val dest: Peg = pegs(destIndex)

      if (origin.disks.isEmpty || !dest.accepts(origin.disks.head)) this
      else {
        val allPegs: List[Peg] = origin.removeTopDisk :: dest.addDisk(origin.disks.head) :: pegs.filter((peg: Peg) => peg != origin && peg != dest)
        val pegsSorted = allPegs.sortWith(_.position < _.position).filter((peg: Peg) => peg != origin && peg != dest)
        new State(pegsSorted)
      }
    }
  }

}

