package ai.sokoban

case class PullGameState(player: Point, box: Point) extends Comparable[PullGameState] {
  private val inverse = Map('U -> 'D, 'D -> 'U, 'L -> 'R, 'R -> 'L)

  def isBox(point: Point): Boolean = box == point

  def move(direction: Symbol): PullGameState = {
    val playerDestination = player.move(direction)
    val boxDestination = if (isBox(player.move(inverse(direction)))) player else box

    PullGameState(playerDestination, boxDestination)
  }

  def compareTo(state: PullGameState) = str(this) compareTo str(state)

  private def str(state: PullGameState) = List(state.player, state.box).map { case Point(x, y) => x + " " + y }.mkString(" ")
}
