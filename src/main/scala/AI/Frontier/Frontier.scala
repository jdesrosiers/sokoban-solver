package ai.frontier

import ai.Node

trait Frontier[A] extends Iterator[Node[A]] {
  def add(node: Node[A]): Unit
}
