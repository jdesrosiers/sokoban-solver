package ai.search.frontier

import ai.search.Node
import scala.collection.mutable.SortedSet
import scala.collection.mutable.Set
import scala.collection.mutable.Map

class AStarGraphFrontier[A <: Comparable[A]] extends Frontier[A] {
  val frontier = SortedSet[Node[A]]()(Ordering.by((n: Node[A]) => (n.f, n.state)))
  val discovered = Map[A, Node[A]]()

  def isEmpty = frontier.isEmpty
  def head = frontier.head

  def next = {
    val node = frontier.head
    frontier.remove(node)
    node
  }

  def add(node: Node[A]): Unit = {
    discovered.get(node.state) match {
      case Some(existing) if node.f < existing.f =>
        discovered.put(node.state, node);
        frontier.remove(existing);
        frontier.add(node)
      case None =>
        discovered.put(node.state, node);
        frontier.add(node)
      case _ => ()
    }
  }
}

