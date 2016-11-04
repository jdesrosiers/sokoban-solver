package ai.frontier

import ai.Node
import scala.collection.mutable.Stack

class DepthFirstFrontier[A] extends Frontier[A] {
  var frontier = Stack[Node[A]]()

  def hasNext = !frontier.isEmpty
  def next() = frontier.pop
  def add(node: Node[A]): Unit = frontier.push(node)
}
