package ai.sokoban

import scala.io.Source

import ai.search.{DefaultHeuristic, Search}

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

  val reachable = {
    val game = PullGame(walls)
    val graph = SokobanPullGraph(game)
    val state = PullGameState(player, null)

    Search.breadthFirst(graph, graph.get(state)).visited.map { case PullGameState(player, _) => player }
  }

  val restricted = {
    def boxVisited(box: Point) = {
      val game = PullGame(walls)
      val graph = SokobanPullGraph(game)
      val initialNodes = for (neighbor <- box.neighbors if !game.isWall(neighbor)) yield
        graph.get(PullGameState(neighbor, box))

      Search.breadthFirst(graph, initialNodes).visited.map { case PullGameState(_, box) => box }
    }

    val visited = for (box <- storage; point <- boxVisited(box)) yield point

    reachable -- visited
  }

  /*
  // Manhattan Distance
  private def pushDistance(from: Point) = storage.map(from.manhattanDistance).min
  private def moveDistance = pushDistance _
  */

  // Actual Distance
  private def pushDistance(from: Point) = {
    val pushGame = Game(walls, storage, restricted, Map(), Map())
    val graph = SokobanGraph(pushGame, DefaultHeuristic())
    val initialNodes = for (neighbor <- from.neighbors if !pushGame.isWall(neighbor)) yield
      graph.get(GameState(neighbor, Set(from)))

    Search.breadthFirst(graph, initialNodes).node.depth
  }

  private def moveDistance(from: Point) = {
    val moveGame = Game(walls, storage, restricted, Map(), Map())
    val graph = SokobanMoveGraph(moveGame, DefaultHeuristic())
    val state = GameState(from, Set())

    Search.breadthFirst(graph, graph.get(state)).node.depth
  }

  val boxDistance = (reachable -- restricted).foldLeft(Map[Point, Int]()) {
    (acc, p) => acc + (p -> pushDistance(p))
  }

  val playerDistance = reachable.foldLeft(Map[Point, Int]()) {
    (acc, p) => acc + (p -> moveDistance(p))
  }

  val game = Game(walls, storage, restricted, boxDistance, playerDistance)
  val gameState = GameState(player, boxes)
}
