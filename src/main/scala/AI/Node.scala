package ai

case class Node[A](parent: Node[A], operation: Symbol, state: A, g: Float, h: Float) {
  override def equals(o: Any) = o match {
    case Node(_, _, `state`, _, _) => true
    case _ => false
  }
  override def hashCode = (state).##
}
