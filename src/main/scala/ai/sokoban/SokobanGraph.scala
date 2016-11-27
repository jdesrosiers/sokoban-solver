package ai.sokoban

import ai.search.{Graph, Node, Heuristic}

case class SokobanGraph(game: Game, heuristic: Heuristic[GameState]) extends Graph[GameState] {
  def g(from: GameState, to: GameState) = 1
  def h(state: GameState) = heuristic.h(state)
  def successors(state: GameState) =
    for {
      direction <- game.allowedMoves(state)
      successor = state.move(direction)
      if !game.isDeadlocked(successor)
    } yield (direction, successor)
  def isGoal(node: Node[GameState]) = game.isGoal(node.state)
}
