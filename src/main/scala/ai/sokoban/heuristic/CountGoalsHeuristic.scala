package ai.sokoban.heuristic

import ai.search.Heuristic
import ai.sokoban.{Game, GameState}

case class CountGoalsHeuristic(game: Game) extends Heuristic[GameState] {
  def h(state: GameState) = state.boxes.count(!game.isStorage(_))
}
