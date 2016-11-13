package ai.search

case class Node[A](parent: Node[A], operation: Symbol, state: A, g: Float, h: Float) {
  override def equals(o: Any) = o match {
    case Node(_, _, `state`, _, _) => true
    case _ => false
  }

  val f = g + h

  override def hashCode = (state).##

  def states(): List[A] = states(Nil)
  def states(acc: List[A]): List[A] = parent match {
    case null => state :: acc
    case _ => parent.states(state :: acc)
  }

  def operations(): List[Symbol] = operations(Nil)
  def operations(acc: List[Symbol]): List[Symbol] = parent match {
    case null => operation :: acc
    case _ => parent.operations(operation :: acc)
  }
}
