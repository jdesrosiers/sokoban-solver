package ai.sokoban

case class Game(walls: Set[Point], storage: Set[Point], restricted: Set[Point], boxDistance: Map[Point, Int], playerDistance: Map[Point, Int]) {
  def isWall(point: Point) = walls contains point
  def isStorage(point: Point) = storage contains point
  def isRestricted(point: Point) = restricted contains point

  def isDeadlocked(state: GameState) = {
    def isBoxFrozen(game: Game, state: GameState, box: Point): Boolean = {
      def isFrozen(a: Point, b: Point) =
        (game.isWall(a) || game.isWall(b)) ||
          (game.isRestricted(a) && game.isRestricted(b)) ||
          (state.isBox(a) && isBoxFrozen(Game(game.walls + box, storage, restricted, boxDistance, playerDistance), state, a)) ||
          (state.isBox(b) && isBoxFrozen(Game(game.walls + box, storage, restricted, boxDistance, playerDistance), state, b))

      isFrozen(box.move('U), box.move('D)) && isFrozen(box.move('R), box.move('L))
    }

    state.boxes.filterNot(isStorage).exists(isBoxFrozen(this, state, _))
  }

  def canMove(state: GameState, direction: Symbol) = {
    val destination = state.player.move(direction)

    if (isWall(destination)) false
    else if (state.isBox(destination)) {
      val boxDestination = destination.move(direction)
      !isWall(boxDestination) && !state.isBox(boxDestination) && !isRestricted(boxDestination)
    } else true
  }

  def allowedMoves(state: GameState) = List('L, 'R, 'U, 'D).filter(canMove(state, _))
  def isGoal(state: GameState) = state.boxes.forall(isStorage)
}
