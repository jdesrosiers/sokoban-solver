package ai.sokoban

import ai.search.{Node, Heuristic}

class SokobanMoveGraph(game: Game, heuristic: Heuristic[GameState]) extends SokobanGraph(game, heuristic) {
  override def isGoal(node: Node[GameState]) = game.isStorage(node.state.player)
}
