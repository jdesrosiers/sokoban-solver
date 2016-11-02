package ai.frontier

import ai.Node
import scala.collection.mutable.Queue

class BreadthFirstFrontier[A] extends Frontier[A] {
  var frontier = Queue[Node[A]]()

  def isEmpty(): Boolean = frontier.isEmpty
  def next(): Node[A] = frontier.dequeue()
  def add(node: Node[A]): Unit = frontier.enqueue(node)
}
