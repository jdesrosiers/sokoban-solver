package ai.sokoban

case class Game(dimensions: Point, walls: Set[Point], storage: Set[Point]) {
  var restricted = Set[Point]()

  // Corners
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

  def isWall(point: Point): Boolean = walls contains point
  def isStorage(point: Point): Boolean = storage contains point
  def isRestricted(point: Point): Boolean = restricted contains point

  def canMove(state: GameState, direction: Symbol): Boolean = {
    val destination = state.player.move(direction)

    if (isWall(destination)) false
    else if (state.isBox(destination)) {
      val boxDestination = destination.move(direction)
      var isFrozen = false
      /*
      if (!isStorage(boxDestination)) {
        val neighbors1 = List('U, 'D, 'L, 'R).map(boxDestination.move)
        val boxNeighbors1 = neighbors1.filter(state.isBox)
        val wallNeighbors1 = neighbors1.filter(isWall)
        boxNeighbors1.foreach(n => {
          // wall neighboring this box
          val neighbors2 = List('U, 'D, 'L, 'R).map(n.move)
          val wallNeighbors2 = neighbors2.filter(isWall)

          wallNeighbors2.foreach(n => {
            val neighbors3 = List('U, 'D, 'L, 'R).map(n.move)
            val wallNeighbors3 = neighbors3.filter(isWall)
            if (wallNeighbors1.intersect(wallNeighbors3).size > 0)
              isFrozen = true
          })
        })
      }
      */
      !isWall(boxDestination) && !state.isBox(boxDestination) && !isRestricted(boxDestination) && !isFrozen
    } else true
  }

  def allowedMoves(state: GameState) = List('L, 'R, 'U, 'D).filter(canMove(state, _))
  def isGoal(state: GameState): Boolean = {
    storage.forall(state.isBox)
  }
}
