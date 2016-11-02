package ai

class Path[A](goal: Node[A]) {
  def states(): List[A] = statePath(goal, Nil)
  def statePath(node: Node[A], acc: List[A]): List[A] = node match {
    case null => acc
    case Node(parent, _, state, _, _) => statePath(parent, state :: acc)
  }

  def operations(): List[Symbol] = operationPath(goal, Nil)
  def operationPath(node: Node[A], acc: List[Symbol]): List[Symbol] = goal match {
    case null => acc
    case Node(parent, operation, _, _, _) => operationPath(parent, operation :: acc)
  }
}
