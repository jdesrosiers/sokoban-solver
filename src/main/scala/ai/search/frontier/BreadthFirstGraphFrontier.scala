package ai.search.frontier

import ai.search.Node
import scala.collection.mutable.Queue
import scala.collection.mutable.Set

class BreadthFirstGraphFrontier[A] extends Frontier[A] {
  val frontier = Queue[Node[A]]()
  val discovered = Set[A]()

  def isEmpty = frontier.isEmpty
  def next = frontier.dequeue
  def head = frontier.head
  def add(node: Node[A]): Unit =
    if (!discovered.contains(node.state)) {
      discovered.add(node.state)
      frontier.enqueue(node)
    }
}
