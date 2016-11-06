package ai.frontier

import ai.Node
import scala.collection.immutable.Stack

class DepthFirstFrontier[A] extends Frontier[A] {
  var frontier = Stack[Node[A]]()
  var visited = Map[A, Node[A]]()

  def hasNext = !frontier.isEmpty

  def next() = {
    val next = frontier.head
    frontier = frontier.tail
    visited = visited + (next.state -> next)
    //println("Visited", visited.map({ case (k, v) => (v.state, v.g)}))
    next
  }

  def add(node: Node[A]): Unit = {
    frontier = if (visited contains node.state) frontier else frontier.push(node)
  }
}
