package ai.sokoban

import scala.io.Source

import ai.search.Search

class Initializer(serializedBoard: Source) {
  private val iter = serializedBoard.getLines

  val size = toPoint(parseLine(iter.next))
  val walls = toPoints(parseLine(iter.next)).toSet
  val boxes = toPoints(parseLine(iter.next)).toSet
  val storage = toPoints(parseLine(iter.next)).toSet
  val player = toPoint(parseLine(iter.next))

  private def toPoint(point: Array[Int]): Point = Point(point(0), point(1))
  private def parseLine(line: String): Array[Int] = line.split(" ").map(_.toInt)
  private def toPoints(input: Array[Int]): Iterator[Point] = input.tail.sliding(2, 2).map(toPoint)

  /*
  private def isWall(point: Point) = walls contains point
  private def isStorage(point: Point) = storage contains point

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

    def reachable(player: Point) = {
      val game = PullGame(walls, null)
      val graph = new SokobanPullGraph(game)
      val state = PullGameState(player, null)

      Search.breadthFirst(graph, graph.get(state)).visited.map { case PullGameState(player, _) => player }
    }

    reachable(player) intersect (corners ++ horizontalCornerPaths ++ verticalCornerPaths)
  }
  */

  val restricted = {
    def boxVisited(box: Point) = {
      val game = PullGame(walls, box)
      val graph = new SokobanPullGraph(game)
      val initialNodes = for (neighbor <- box.neighbors if !game.isWall(neighbor)) yield
        graph.get(PullGameState(neighbor, box))

      Search.breadthFirst(graph, initialNodes).visited.map { case PullGameState(_, box) => box }
    }

    def reachable(player: Point) = {
      val game = PullGame(walls, null)
      val graph = new SokobanPullGraph(game)
      val state = PullGameState(player, null)

      Search.breadthFirst(graph, graph.get(state)).visited.map { case PullGameState(player, _) => player }
    }

    val visited = for (box <- storage; point <- boxVisited(box)) yield point

    reachable(player) -- visited
  }

  val game = Game(walls, storage, restricted)
  val gameState = GameState(player, boxes)
}
