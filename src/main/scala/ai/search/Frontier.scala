package ai.search

import scala.collection.immutable.Queue
import scala.collection.immutable.SortedSet

trait Frontier[A] {
  def visited: Set[A]
  def add(node: Node[A]): Frontier[A]
  def isEmpty: Boolean
  def head: Node[A]
  def tail: Frontier[A]
}

object Frontier {
  case class DepthFirstGraphFrontier[A](discovered: Set[A], frontier: List[Node[A]]) extends Frontier[A] {
    def visited = discovered
    def isEmpty = frontier.isEmpty
    def tail = DepthFirstGraphFrontier(discovered, frontier.tail)
    def head = frontier.head
    def add(node: Node[A]) =
      if (!discovered.contains(node.state))
        DepthFirstGraphFrontier(discovered + node.state, node :: frontier)
      else
        this
  }

  case class BreadthFirstGraphFrontier[A](discovered: Set[A], frontier: Queue[Node[A]]) extends Frontier[A] {
    def visited = discovered
    def isEmpty = frontier.isEmpty
    def tail = BreadthFirstGraphFrontier(discovered, frontier.tail)
    def head = frontier.head
    def add(node: Node[A]) =
      if (!discovered.contains(node.state))
        BreadthFirstGraphFrontier(discovered + node.state, frontier.enqueue(node))
      else
        this
  }

  case class BestFirstGraphFrontier[A](discovered: Map[A, Node[A]], frontier: SortedSet[Node[A]]) extends Frontier[A] {
    def visited = discovered.keys.toSet
    def isEmpty = frontier.isEmpty
    def tail = BestFirstGraphFrontier(discovered, frontier.tail)
    def head = frontier.head
    def add(node: Node[A]) =
      discovered.get(node.state) match {
        case Some(existing) if frontier.ordering.lt(node, existing) =>
          BestFirstGraphFrontier(discovered - node.state + (node.state -> node), frontier - existing + node)
        case None =>
          BestFirstGraphFrontier(discovered + (node.state -> node), frontier + node)
        case _ => this
      }
  }

  def depthFirst[A] = DepthFirstGraphFrontier(Set[A](), List[Node[A]]())
  def breadthFirst[A] = BreadthFirstGraphFrontier(Set[A](), Queue[Node[A]]())
  def uniformCost[A <: Comparable[A]] = {
    val ordering = Ordering.by((n: Node[A]) => (n.g, n.state))
    BestFirstGraphFrontier(Map[A, Node[A]](), SortedSet[Node[A]]()(ordering))
  }
  def greedyBestFirst[A <: Comparable[A]] = {
    val ordering = Ordering.by((n: Node[A]) => (n.h, n.state))
    BestFirstGraphFrontier(Map[A, Node[A]](), SortedSet[Node[A]]()(ordering))
  }
  def aStar[A <: Comparable[A]] = {
    val ordering = Ordering.by((n: Node[A]) => (n.f, n.state))
    BestFirstGraphFrontier(Map[A, Node[A]](), SortedSet[Node[A]]()(ordering))
  }
}
