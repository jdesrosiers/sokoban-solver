package ai.sokoban.heuristic

import ai.search.Heuristic
import ai.sokoban.{Game, GameState, Point}

case class BoxManhattanDistanceHeuristic(game: Game) extends Heuristic[GameState] {
  private def distance(from: Point) = game.storage.map(from.manhattanDistance).min

  val boxDistance = (game.reachable -- game.restricted).foldLeft(Map[Point, Int]()) {
    (acc, p) => acc + (p -> distance(p))
  }

  val playerDistance = game.reachable.foldLeft(Map[Point, Int]()) {
    (acc, p) => acc + (p -> distance(p))
  }

  def h(state: GameState) =
    2 * state.boxes.toList.map(boxDistance).sum - playerDistance(state.player)
}
