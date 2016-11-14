package ai.sokoban.heuristic

import ai.search.Heuristic
import ai.sokoban.{Game, GameState, Point}

case class BoxDistanceHeuristic(game: Game) extends Heuristic[GameState] {
  def h(state: GameState) = game.storage.map((p: Point) => minDistance(p, state)).sum
  private def minDistance(storage: Point, state: GameState) =
    state.boxes.map((p: Point) => p.manhattanDistance(storage)).min
}
