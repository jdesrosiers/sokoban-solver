package ai.sokoban

case class PullGame(walls: Set[Point]) {
  def isWall(point: Point) = walls contains point

  private val inverse = Map('u -> 'd, 'd -> 'u, 'l -> 'r, 'r -> 'l)

  def canMove(state: PullGameState, direction: Symbol) = {
    val destination = state.player.move(direction)
    !isWall(destination) && !state.isBox(destination) &&
      (!state.isPullOperation(direction) || state.isBox(state.player.move(inverse(direction))))
  }

  def allowedMoves(state: PullGameState) = List('L, 'R, 'U, 'D, 'l, 'r, 'u, 'd).filter(canMove(state, _))
  def isGoal(state: PullGameState) = false
}

