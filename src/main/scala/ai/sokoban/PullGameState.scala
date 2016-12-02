package ai.sokoban

case class PullGameState(player: Point, box: Point) extends Comparable[PullGameState] {
  private val pullOperations = Set('l, 'r, 'u, 'd)

  def isBox(point: Point): Boolean = box == point
  def isPullOperation(direction: Symbol) = pullOperations contains direction

  def move(direction: Symbol): PullGameState = {
    val playerDestination = player.move(direction)
    val boxDestination = if (isPullOperation(direction)) player else box

    PullGameState(playerDestination, boxDestination)
  }

  def compareTo(state: PullGameState) = str(this) compareTo str(state)

  private def str(state: PullGameState) = List(state.player, state.box).map { case Point(x, y) => x + " " + y }.mkString(" ")
}
