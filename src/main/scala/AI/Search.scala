package ai

import ai.frontier._

object Search {
  private def search[A](frontier: Frontier[A])(graph: Graph[A], start: Node[A]): Node[A] = {
    def searchRec[A](frontier: Frontier[A], graph: Graph[A], current: Node[A], visited: Set[Node[A]]): Node[A] =
      if (graph.isGoal(current))
        current
      else {
        graph.children(current).filter(!visited.contains(_)).foreach(frontier.add(_))
        if (frontier.hasNext)
          searchRec(frontier, graph, frontier.next, visited + current)
        else
          null // No solution found
      }

    searchRec(frontier, graph, start, Set[Node[A]]())
  }

  def depthFirst[A] = search(new DepthFirstFrontier[A]()) _
  def breadthFirst[A] = search(new BreadthFirstFrontier[A]()) _
  def uniformCost[A] = search(new UniformCostFrontier[A]()) _
  def greedyBestFirst[A] = search(new GreedyBestFirstFrontier[A]()) _
  def aStar[A] = search(new AStarFrontier[A]()) _
}
