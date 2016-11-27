package ai.search

case class Node[A](parent: Node[A], operation: Symbol, state: A, g: Double, h: Double) {
  val f = g + h
  val depth: Int = if (parent == null) 1 else parent.depth + 1

  def states: List[A] = states(Nil)
  def states(acc: List[A]): List[A] = parent match {
    case null => state :: acc
    case _ => parent.states(state :: acc)
  }

  def operations: List[Symbol] = operations(Nil)
  def operations(acc: List[Symbol]): List[Symbol] = parent match {
    case null => acc
    case _ => parent.operations(operation :: acc)
  }
}
