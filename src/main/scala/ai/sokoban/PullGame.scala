package ai.sokoban

case class PullGame(walls: Set[Point], storage: Point) {
  def isWall(point: Point) = walls contains point

  def canMove(state: PullGameState, direction: Symbol) = {
    val destination = state.player.move(direction)
    !isWall(destination) && !state.isBox(destination)
  }

  def allowedMoves(state: PullGameState) = List('L, 'R, 'U, 'D).filter(canMove(state, _))
  def isGoal(state: PullGameState) = false
}

