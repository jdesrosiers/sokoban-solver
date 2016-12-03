package ai.sokoban

import ai.search.{Graph, Node, Heuristic}

case class SokobanPullGraph(game: PullGame) extends Graph[PullGameState] {
  def g(from: PullGameState, to: PullGameState) = 1
  def h(state: PullGameState) = 0
  def successors(state: PullGameState) = game.allowedMoves(state).map(direction => (direction, state.move(direction)))
  def isGoal(node: Node[PullGameState]) = game.isGoal(node.state)
}
