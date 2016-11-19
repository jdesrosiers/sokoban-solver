package ai.sokoban

case class Game(dimensions: Point, walls: Set[Point], storage: Set[Point]) {
  // Corners
  var restricted: Set[Point] = for {
    wall <- walls
    (a, b) <- List('U -> 'R, 'U -> 'L, 'D -> 'R, 'D -> 'L)
    if (isCorner(wall, a, b))
  } yield wall.move(a).move(b)

  private def isCorner(wall: Point, a: Symbol, b: Symbol) =
    isWall(wall.move(a)) && isWall(wall.move(b)) && !isStorage(wall.move(a).move(b))

  // Restrict points between corners along vertical walls
  restricted.foreach(a =>
    restricted.foreach(b => {
      val dir = if (a.y < b.y) 'D else 'U
      if (a.x == b.x) {
        // wall segment has a goal and wall is to the left
        var hasGoal = false
        var p = a
        var leftWall = true
        var rightWall = true
        while (p.y != b.y) {
          if (isStorage(p)) hasGoal = true
          if (!isWall(p.move('L))) leftWall = false
          p = p.move(dir)
        }

        // restrict points on the wall segment
        if (!hasGoal && leftWall) {
          p = a
          while (p.y != b.y) {
            restricted += p
            p = p.move(dir)
          }
        }

        // wall segment has a goal and wall is to the right
        p = a
        while (p.y != b.y) {
          if (isStorage(p)) hasGoal = true
          if (!isWall(p.move('R))) rightWall = false
          p = p.move(dir)
        }

        // restrict points on the wall segment
        if (!hasGoal && rightWall) {
          p = a
          while (p.y != b.y) {
            restricted += p
            p = p.move(dir)
          }
        }
      }
    })
  )

  // Restrict points between corners along horizontal walls
  restricted.foreach(a =>
    restricted.foreach(b => {
      val dir = if (a.x < b.x) 'R else 'L
      if (a.y == b.y) {
        // wall segment has a goal and wall is above
        var hasGoal = false
        var p = a
        var upWall = true
        var downWall = true
        while (p.x != b.x) {
          if (isStorage(p)) hasGoal = true
          if (!isWall(p.move('U))) upWall = false
          p = p.move(dir)
        }

        // restrict points on the wall segment
        if (!hasGoal && upWall) {
          p = a
          while (p.x != b.x) {
            restricted += p
            p = p.move(dir)
          }
        }

        // wall segment has a goal and wall is to the right
        p = a
        while (p.x != b.x) {
          if (isStorage(p)) hasGoal = true
          if (!isWall(p.move('D))) downWall = false
          p = p.move(dir)
        }

        // restrict points on the wall segment
        if (!hasGoal && downWall) {
          p = a
          while (p.x != b.x) {
            restricted += p
            p = p.move(dir)
          }
        }
      }
    })
  )

  def isWall(point: Point) = walls contains point
  def isStorage(point: Point) = storage contains point
  def isRestricted(point: Point) = restricted contains point

  private def isFrozen(state: GameState, point: Point , a: Symbol, b: Symbol) =
    isWall(point.move(a)) && state.isBox(point.move(b)) && isWall(point.move(a).move(b))

  def canMove(state: GameState, direction: Symbol) = {
    val destination = state.player.move(direction)

    if (isWall(destination)) false
    else if (state.isBox(destination)) {
      val boxDestination = destination.move(direction)
      val frozen = !isStorage(boxDestination) && List('U, 'D, 'L, 'R).exists(isFrozen(state, boxDestination, direction, _))

      !isWall(boxDestination) && !state.isBox(boxDestination) && !isRestricted(boxDestination) && !frozen
    } else true
  }

  def allowedMoves(state: GameState) = List('L, 'R, 'U, 'D).filter(canMove(state, _))
  def isGoal(state: GameState) = storage.forall(state.isBox)
}
