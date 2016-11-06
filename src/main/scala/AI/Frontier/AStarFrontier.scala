package ai.frontier

import ai.Node
import scala.collection.immutable.SortedSet

class AStarFrontier[A] extends Frontier[A] {
  var frontier = SortedSet[Node[A]]()
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
    frontier = visited.get(node.state) match {
      case Some(existing) if node.g + node.h > existing.g + existing.h => frontier
      case _ =>
        frontier.find(_ == node) match {
          case Some(existing) if node.g + node.h > existing.g + existing.h => frontier
          case Some(existing) => frontier - existing + node
          case _ => frontier + node
        }
    }
  }

  override def toString = frontier.map(n => (n.state, n.g + n.h)).toString

  implicit def ordering[N <: Node[A]]: Ordering[N] = new Ordering[N] {
    override def compare(x: N, y: N): Int =
      if (x.state == y.state) 0
      else if (x.g + x.h < y.g + y.h) -1
      else 1
  }
}

