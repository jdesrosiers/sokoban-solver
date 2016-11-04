package ai.frontier

import ai.Node
import scala.collection.mutable.PriorityQueue

class GreedyBestFirstFrontier[A] extends Frontier[A] {
  var frontier = PriorityQueue[Node[A]]()

  def hasNext = !frontier.isEmpty
  def next() = frontier.dequeue
  def add(node: Node[A]): Unit = frontier.enqueue(node)  // TODO: Don't add if there is a better node already queued

  implicit def ordering[N <: Node[A]]: Ordering[N] = Ordering.by((node: N) => node.h).reverse
}

