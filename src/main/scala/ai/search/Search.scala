package ai.search

import scala.annotation.tailrec

object Search {
  @tailrec
  def search[A <: Comparable[A]](frontier: Frontier[A], graph: Graph[A]): SearchResult[A] =
    if (frontier.isEmpty) SearchResult(null, frontier)
    else if (graph.isGoal(frontier.head)) SearchResult(frontier.head, frontier)
    else search(graph.children(frontier.head).foldLeft(frontier.tail)(_ add _), graph)

  def depthFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.depthFirst.add(start), graph)

  def breadthFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.breadthFirst.add(start), graph)

  def breadthFirst[A <: Comparable[A]](graph: Graph[A], start: List[Node[A]]) = {
    val frontier: Frontier[A] = Frontier.breadthFirst
    search(start.foldLeft(frontier)(_ add _), graph)
  }

  def uniformCost[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.uniformCost.add(start), graph)

  def greedyBestFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.greedyBestFirst.add(start), graph)

  def aStar[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.aStar.add(start), graph)
}

case class SearchResult[A](node: Node[A], frontier: Frontier[A]) {
  def operations = node.operations
  def states = node.states
  def visited = frontier.visited
}
