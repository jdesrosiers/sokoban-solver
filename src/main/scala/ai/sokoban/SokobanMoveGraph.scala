package ai.sokoban

import ai.search.{Graph, Node, Heuristic}

case class SokobanMoveGraph(game: Game, heuristic: Heuristic[GameState]) extends Graph[GameState] {
  def g(from: GameState, to: GameState) = 1
  def h(state: GameState) = heuristic.h(state)
  def successors(state: GameState) = game.allowedMoves(state).map {
    direction => (direction, state.move(direction))
  }
  def isGoal(node: Node[GameState]) = game.isStorage(node.state.player)
}
