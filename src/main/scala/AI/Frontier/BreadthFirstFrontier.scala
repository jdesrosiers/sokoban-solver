package ai.frontier

import ai.Node
import scala.collection.mutable.Queue

class BreadthFirstFrontier[A] extends Frontier[A] {
  var frontier = Queue[Node[A]]()

  def hasNext = !frontier.isEmpty
  def next() = frontier.dequeue
  def add(node: Node[A]): Unit = frontier.enqueue(node)
}
