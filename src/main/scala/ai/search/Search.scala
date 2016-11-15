package ai.search

import ai.search.frontier._
import scala.annotation.tailrec

object Search {
  private def search[A <: Comparable[A]](frontier: Frontier[A])(graph: Graph[A], start: Node[A]) = {
    @tailrec
    def searchRec(frontier: Frontier[A], graph: Graph[A]): Node[A] =
      if (frontier.isEmpty) null
      else if (graph.isGoal(frontier.head)) frontier.head
      else {
        graph.children(frontier.next).foreach(frontier.add)
        searchRec(frontier, graph)
      }

    frontier.add(start)
    searchRec(frontier, graph)
  }

  def depthFirst[A <: Comparable[A]] = search(new DepthFirstGraphFrontier[A]()) _
  def breadthFirst[A <: Comparable[A]] = search(new BreadthFirstGraphFrontier[A]()) _
  def uniformCost[A <: Comparable[A]] = search(new UniformCostGraphFrontier[A]()) _
  def greedyBestFirst[A <: Comparable[A]] = search(new GreedyBestFirstGraphFrontier[A]()) _
  def aStar[A <: Comparable[A]] = search(new AStarGraphFrontier[A]()) _
}
