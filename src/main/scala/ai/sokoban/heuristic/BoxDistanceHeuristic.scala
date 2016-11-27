package ai.sokoban.heuristic

import ai.search.Heuristic
import ai.sokoban.{Game, GameState, Point}

case class BoxDistanceHeuristic(game: Game) extends Heuristic[GameState] {
  private val storage = game.storage.toList

  def h(state: GameState) = {
    val boxes = state.boxes.toList
    3 * ((state.player :: storage).map((p: Point) => minDistance(p, boxes)).sum - 1) + state.boxes.count(!game.isStorage(_))
  }
  private def minDistance(storage: Point, boxes: List[Point]) = boxes.map(storage.manhattanDistance).min
}
