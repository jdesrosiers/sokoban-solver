package ai.sokoban.heuristic

import ai.search.{Heuristic, Search, DefaultHeuristic}
import ai.sokoban.{Game, GameState, Point, SokobanMoveGraph}

case class ActualDistanceHeuristic(game: Game, boxDistance: Map[Point, Int]) extends Heuristic[GameState] {
  def h(state: GameState) = {
    val playerDistance = actualDistance(state.player, state.boxes -- game.storage)
    //val playerDistance = manhattanDistance(state.player, state.boxes -- game.storage)
    10 * state.boxes.map(boxDistance).sum + playerDistance
  }

  def manhattanDistance(from: Point, to: Set[Point]) =
    if (to.size == 0) 0
    else to.map(from.manhattanDistance).min

  def actualDistance(from: Point, to: Set[Point]) =
    if (to.size == 0) 0
    else {
      val moveGame = Game(game.walls, to, game.restricted)
      val graph = new SokobanMoveGraph(moveGame, DefaultHeuristic())
      val state = GameState(from, Set())

      Search.breadthFirst(graph, graph.get(state)).node.depth
    }
}
