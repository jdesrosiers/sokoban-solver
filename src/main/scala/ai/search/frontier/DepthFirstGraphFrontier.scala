package ai.search.frontier

import ai.search.Node
import scala.collection.mutable.Stack
import scala.collection.mutable.Set

class DepthFirstGraphFrontier[A] extends Frontier[A] {
  val frontier = Stack[Node[A]]()
  val discovered = Set[A]()

  def isEmpty = frontier.isEmpty
  def next = frontier.pop
  def head = frontier.head
  def add(node: Node[A]): Unit =
    if (!discovered.contains(node.state)) {
      discovered.add(node.state)
      frontier.push(node)
    }
}
