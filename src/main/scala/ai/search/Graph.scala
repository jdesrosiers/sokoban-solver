package ai.search

trait Graph[A] {
  def get(state: A): Node[A]
  def g(from: A, to: A): Float
  def h(state: A): Float
  def successors(state: A): List[(Symbol,A)]
  def isGoal(node: Node[A]): Boolean

  def children(parent: Node[A]): List[Node[A]] = successors(parent.state).map(x => x match {
    case (operation, state) => Node(parent, operation, state, parent.g + g(parent.state, state), h(state))
  })
}
