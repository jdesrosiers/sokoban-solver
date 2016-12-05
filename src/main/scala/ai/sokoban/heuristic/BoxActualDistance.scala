package ai.sokoban.heuristic

import ai.search.{Search, Heuristic, DefaultHeuristic}
import ai.sokoban.{Game, GameState, Point, SokobanGraph, SokobanMoveGraph}

case class BoxActualDistanceHeuristic(game: Game) extends Heuristic[GameState] {
  private def pushDistance(from: Point) = {
    val graph = SokobanGraph(game, DefaultHeuristic())
    val initialNodes = for (neighbor <- from.neighbors if !game.isWall(neighbor)) yield
      graph.get(GameState(neighbor, Set(from)))

    Search.breadthFirst(graph, initialNodes).node.depth
  }

  private def moveDistance(from: Point) = {
    val graph = SokobanMoveGraph(game, DefaultHeuristic())
    val state = GameState(from, Set())

    Search.breadthFirst(graph, graph.get(state)).node.depth
  }

  val boxDistance = (game.reachable -- game.restricted).foldLeft(Map[Point, Int]()) {
    (acc, p) => acc + (p -> pushDistance(p))
  }

  val playerDistance = game.reachable.foldLeft(Map[Point, Int]()) {
    (acc, p) => acc + (p -> moveDistance(p))
  }

  def h(state: GameState) =
    2 * state.boxes.toList.map(boxDistance).sum - playerDistance(state.player)
}
