package ai.sokoban

case class Game(walls: Set[Point], storage: Set[Point]) {
  val restricted = {
    def isCorner(wall: Point, a: Symbol, b: Symbol) =
      isWall(wall.move(a)) && isWall(wall.move(b)) && !isStorage(wall.move(a).move(b))

    def pathNeighborsWall(path: Seq[Point]) =
      List('U, 'D, 'R, 'L).exists(direction => path.forall(p => isWall(p.move(direction))))

    val corners = for {
      wall <- walls
      (a, b) <- List('U -> 'R, 'U -> 'L, 'D -> 'R, 'D -> 'L)
      if (isCorner(wall, a, b))
    } yield wall.move(a).move(b)

    val horizontalCornerPaths = for {
      a <- corners
      b <- corners
      path = for (x <- a.x to b.x) yield Point(x, a.y)
      p <- path
      if a.y == b.y && a.x < b.x && pathNeighborsWall(path) && !path.exists(isStorage)
    } yield p

    val verticalCornerPaths = for {
      a <- corners
      b <- corners
      path = for (y <- a.y to b.y) yield Point(a.x, y)
      p <- path
      if a.x == b.x && a.y < b.y && pathNeighborsWall(path) && !path.exists(isStorage)
    } yield p

    corners ++ horizontalCornerPaths ++ verticalCornerPaths
  }

  def isWall(point: Point) = walls contains point
  def isStorage(point: Point) = storage contains point
  def isRestricted(point: Point) = restricted contains point

  def canMove(state: GameState, direction: Symbol) = {
    def isFrozen(state: GameState, point: Point , a: Symbol, b: Symbol) =
      (isWall(point.move(a)) || state.isBox(point.move(a))) &&
      (isWall(point.move(b)) || state.isBox(point.move(b))) &&
      (isWall(point.move(a).move(b)) || state.isBox(point.move(a).move(b)))

    val destination = state.player.move(direction)

    if (isWall(destination)) false
    else if (state.isBox(destination)) {
      val boxDestination = destination.move(direction)
      val frozen = !isStorage(boxDestination) && (direction match {
        case 'U => List('L, 'R).exists(isFrozen(state, boxDestination, direction, _))
        case 'D => List('L, 'R).exists(isFrozen(state, boxDestination, direction, _))
        case 'R => List('U, 'D).exists(isFrozen(state, boxDestination, direction, _))
        case 'L => List('U, 'D).exists(isFrozen(state, boxDestination, direction, _))
      })

      !isWall(boxDestination) && !state.isBox(boxDestination) && !isRestricted(boxDestination) && !frozen
    } else true
  }

  def allowedMoves(state: GameState) = List('L, 'R, 'U, 'D).filter(canMove(state, _))
  def isGoal(state: GameState) = storage.forall(state.isBox)
}
