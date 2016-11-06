package ai.frontier

import ai.Node
import scala.collection.immutable.SortedMap

class GreedyBestFirstFrontier[A] extends Frontier[A] {
  var frontier = SortedMap[Node[A], Node[A]]()
  var visited = Map[A, Node[A]]()

  def hasNext = !frontier.isEmpty

  def next() = {
    val next = frontier.head._2
    frontier = frontier.tail
    visited = visited + (next.state -> next)
    //println("Visited", visited.map({ case (k, v) => (v.state, v.g)}))
    next
  }

  def add(node: Node[A]): Unit = {
    frontier = visited.get(node.state) match {
      case Some(existing) if node.h > existing.h => frontier
      case _ =>
        frontier.get(node) match {
          case Some(existing) if node.h > existing.h => frontier
          case Some(existing) => frontier - existing + (node -> node)
          case _ => frontier + (node -> node)
        }
    }
  }

  override def toString = frontier.map { case (k, v) => (v.state, v.h) }.toString

  implicit def ordering[N <: Node[A]]: Ordering[N] = Ordering.by(_.h)
}

