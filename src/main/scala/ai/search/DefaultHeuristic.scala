package ai.search

case class DefaultHeuristic[A]() extends Heuristic[A] {
  def h(state: A) = 0
}
