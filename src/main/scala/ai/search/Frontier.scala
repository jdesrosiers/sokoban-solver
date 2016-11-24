package ai.search

import scala.collection.immutable.Stack
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
  case class DepthFirstGraphFrontier[A](discovered: Set[A], frontier: Stack[Node[A]]) extends Frontier[A] {
    def visited = discovered
    def isEmpty = frontier.isEmpty
    def tail = DepthFirstGraphFrontier(discovered, frontier.tail)
    def head = frontier.head
    def add(node: Node[A]) =
      if (!discovered.contains(node.state))
        DepthFirstGraphFrontier(discovered + node.state, frontier.push(node))
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

  case class UniformCostGraphFrontier[A](discovered: Map[A, Node[A]], frontier: SortedSet[Node[A]]) extends Frontier[A] {
    def visited = discovered.keys.toSet
    def isEmpty = frontier.isEmpty
    def tail = UniformCostGraphFrontier(discovered, frontier.tail)
    def head = frontier.head
    def add(node: Node[A]) =
      discovered.get(node.state) match {
        case Some(existing) if node.g < existing.g =>
          UniformCostGraphFrontier(discovered + (node.state -> node), frontier - existing + node)
        case None =>
          UniformCostGraphFrontier(discovered + (node.state -> node), frontier + node)
        case _ => this
      }
  }

  case class GreedyBestFirstGraphFrontier[A](discovered: Map[A, Node[A]], frontier: SortedSet[Node[A]]) extends Frontier[A] {
    def visited = discovered.keys.toSet
    def isEmpty = frontier.isEmpty
    def tail = GreedyBestFirstGraphFrontier(discovered, frontier.tail)
    def head = frontier.head
    def add(node: Node[A]) =
      discovered.get(node.state) match {
        case Some(existing) if node.h < existing.h =>
          GreedyBestFirstGraphFrontier(discovered + (node.state -> node), frontier - existing + node)
        case None =>
          GreedyBestFirstGraphFrontier(discovered + (node.state -> node), frontier + node)
        case _ => this
      }
  }

  case class AStarGraphFrontier[A](discovered: Map[A, Node[A]], frontier: SortedSet[Node[A]]) extends Frontier[A] {
    def visited = discovered.keys.toSet
    def isEmpty = frontier.isEmpty
    def tail = AStarGraphFrontier(discovered, frontier.tail)
    def head = frontier.head
    def add(node: Node[A]) =
      discovered.get(node.state) match {
        case Some(existing) if node.f < existing.f =>
          AStarGraphFrontier(discovered + (node.state -> node), frontier - existing + node)
        case None =>
          AStarGraphFrontier(discovered + (node.state -> node), frontier + node)
        case _ => this
      }
  }

  def depthFirst[A] = DepthFirstGraphFrontier(Set[A](), Stack[Node[A]]())
  def breadthFirst[A] = BreadthFirstGraphFrontier(Set[A](), Queue[Node[A]]())
  def uniformCost[A <: Comparable[A]] = {
    val ordering = Ordering.by((n: Node[A]) => (n.g, n.state))
    UniformCostGraphFrontier(Map[A, Node[A]](), SortedSet[Node[A]]()(ordering))
  }
  def greedyBestFirst[A <: Comparable[A]] = {
    val ordering = Ordering.by((n: Node[A]) => (n.h, n.state))
    GreedyBestFirstGraphFrontier(Map[A, Node[A]](), SortedSet[Node[A]]()(ordering))
  }
  def aStar[A <: Comparable[A]] = {
    val ordering = Ordering.by((n: Node[A]) => (n.f, n.state))
    AStarGraphFrontier(Map[A, Node[A]](), SortedSet[Node[A]]()(ordering))
  }
}

