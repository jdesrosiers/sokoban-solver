package ai.search

trait Graph[A] {
  def g(from: A, to: A): Double
  def h(state: A): Double
  def successors(state: A): List[(Symbol,A)]
  def isGoal(node: Node[A]): Boolean

  def get(state: A): Node[A] = Node(null, 'Start, state, 0, h(state))
  def children(parent: Node[A]): List[Node[A]] = successors(parent.state).map {
    case (operation, state) => Node(parent, operation, state, parent.g + g(parent.state, state), h(state))
  }.sortWith(_.f > _.f)
}
