package ai.search

import scala.annotation.tailrec

object Search {
  @tailrec
  private def search[A <: Comparable[A]](frontier: Frontier[A], graph: Graph[A]): Node[A] =
    if (frontier.isEmpty) null
    else if (graph.isGoal(frontier.head)) frontier.head
    else search(graph.children(frontier.head).foldLeft(frontier.tail)(_ add _), graph)

  def depthFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.depthFirst.add(start), graph)

  def breadthFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.breadthFirst.add(start), graph)

  def uniformCost[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.uniformCost.add(start), graph)

  def greedyBestFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.greedyBestFirst.add(start), graph)

  def aStar[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.aStar.add(start), graph)
}
