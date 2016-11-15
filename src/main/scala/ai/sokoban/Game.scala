package ai.sokoban

case class Game(dimensions: Point, walls: Set[Point], storage: Set[Point]) {
  def isWall(point: Point): Boolean = walls contains point
  def isStorage(point: Point): Boolean = storage contains point

  def canMove(state: GameState, direction: Symbol): Boolean = {
    val destination = state.player.move(direction)

    if (isWall(destination)) false
    else if (state.isBox(destination)) {
      val boxDestination = destination.move(direction)
      !isWall(boxDestination) && !state.isBox(boxDestination)
    } else true
  }

  def allowedMoves(state: GameState) = List('L, 'R, 'U, 'D).filter(canMove(state, _))
  def isGoal(state: GameState): Boolean = {
    storage.forall(state.isBox)
  }
}
