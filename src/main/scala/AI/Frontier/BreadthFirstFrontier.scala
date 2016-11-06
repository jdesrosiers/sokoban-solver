package ai.frontier

import ai.Node
import scala.collection.immutable.Queue

class BreadthFirstFrontier[A] extends Frontier[A] {
  var frontier = Queue[Node[A]]()
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
    frontier = if (visited contains node.state) frontier else frontier.enqueue(node)
  }
}
