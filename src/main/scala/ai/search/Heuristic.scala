package ai.search

trait Heuristic[A] {
  def h(state: A): Double
}
