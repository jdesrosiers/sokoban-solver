package ai.sokoban.heuristic

import ai.search.{Heuristic, Search, DefaultHeuristic}
import ai.sokoban.{Game, GameState, Point, SokobanGraph}

case class ActualDistanceHeuristic(game: Game) extends Heuristic[GameState] {
  private val graph = new SokobanGraph(game, DefaultHeuristic())

  def h(state: GameState) = state.boxes.map(actualDistance).reduce(_ + _)

  private def actualDistance(box: Point) = {
    val initialNodes = for (neighbor <- box.neighbors if !game.isWall(neighbor)) yield
      graph.get(GameState(neighbor, Set(box)))

    Search.breadthFirst(graph, initialNodes).node.depth
  }
}
