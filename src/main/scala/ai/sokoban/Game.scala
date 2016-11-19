package ai.sokoban

case class Game(dimensions: Point, walls: Set[Point], storage: Set[Point]) {
  var restricted = Set[Point]()
  walls.foreach(wall => {
    if (walls.contains(wall.move('R)) &&
      walls.contains(wall.move('U)) &&
      !storage.contains(wall.move('R).move('U)))
      restricted += wall.move('R).move('U)

    if (walls.contains(wall.move('L)) &&
      walls.contains(wall.move('U)) &&
      !storage.contains(wall.move('L).move('U)))
      restricted += wall.move('L).move('U)

    if (walls.contains(wall.move('R)) &&
      walls.contains(wall.move('D)) &&
      !storage.contains(wall.move('R).move('D)))
      restricted += wall.move('R).move('D)

    if (walls.contains(wall.move('L)) &&
      walls.contains(wall.move('D)) &&
      !storage.contains(wall.move('L).move('D)))
      restricted += wall.move('L).move('D)
  })

  def isWall(point: Point): Boolean = walls contains point
  def isStorage(point: Point): Boolean = storage contains point
  def isRestricted(point: Point): Boolean = restricted contains point

  def canMove(state: GameState, direction: Symbol): Boolean = {
    val destination = state.player.move(direction)

    if (isWall(destination)) false
    else if (state.isBox(destination)) {
      val boxDestination = destination.move(direction)
      !isWall(boxDestination) && !state.isBox(boxDestination) && !isRestricted(boxDestination)
    } else true
  }

  def allowedMoves(state: GameState) = List('L, 'R, 'U, 'D).filter(canMove(state, _))
  def isGoal(state: GameState): Boolean = {
    storage.forall(state.isBox)
  }
}
