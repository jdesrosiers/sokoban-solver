package ai.sokoban

case class GameState(player: Point, boxes: Set[Point]) extends Comparable[GameState] {
  def isBox(point: Point): Boolean = boxes contains point

  def move(game: Game, direction: Symbol): GameState = {
    val playerDestination = player.move(direction)
    val boxesDestination = if (isBox(playerDestination))
      boxes - playerDestination + playerDestination.move(direction)
    else
      boxes

    GameState(playerDestination, boxesDestination)
  }

  def compareTo(state: GameState) = str(this) compareTo str(state)

  private def str(state: GameState) = (state.player :: state.boxes.toList).map { case Point(x, y) => x + " " + y }.mkString(" ")
}
