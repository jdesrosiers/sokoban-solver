package ai.sokoban.heuristic

import ai.search.Heuristic
import ai.sokoban.{Game, GameState}

case class BoxDistanceHeuristic(game: Game) extends Heuristic[GameState] {
  private val storage = game.storage.toList

  def h(state: GameState) =
    2 * state.boxes.toList.map(game.boxDistance).sum - game.playerDistance(state.player)
}
