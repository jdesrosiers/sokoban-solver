package ai.search

import scala.annotation.tailrec

object Search {
  @tailrec
  private def search[A <: Comparable[A]](frontier: Frontier[A], graph: Graph[A]): SearchResult[A] =
    if (frontier.isEmpty) SearchResult(null, frontier)
    else if (graph.isGoal(frontier.head)) SearchResult(frontier.head, frontier.tail)
    else search(graph.children(frontier.head).foldLeft(frontier.tail)(_ add _), graph)

  @tailrec
  private def depthLimitedSearch[A <: Comparable[A]](frontier: Frontier[A], graph: Graph[A], limit: Int): SearchResult[A] =
    if (frontier.isEmpty) SearchResult(null, frontier)
    else if (graph.isGoal(frontier.head)) SearchResult(frontier.head, frontier.tail)
    else if (frontier.head.depth >= limit) depthLimitedSearch(frontier.tail, graph, limit)
    else depthLimitedSearch(graph.children(frontier.head).foldLeft(frontier.tail)(_ add _), graph, limit)

  def depthFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) =
    search(Frontier.depthFirst.add(start), graph)

  def iterativeDeepeningDepthFirst[A <: Comparable[A]](graph: Graph[A], start: Node[A]) = {
    @tailrec
    def iterativeDeepening(frontier: Frontier[A], depth: Int): SearchResult[A] =
      depthLimitedSearch(frontier, graph, depth) match {
        case SearchResult(null, f) => iterativeDeepening(frontier, depth + 1)
        case result => result
      }

    iterativeDeepening(Frontier.depthFirst.add(start), 1)
  }

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
