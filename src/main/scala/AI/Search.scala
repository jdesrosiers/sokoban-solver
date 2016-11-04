package ai

import ai.frontier._

object Search {
  private def search[A](frontier: Frontier[A], visited: Set[Node[A]])(graph: Graph[A], current: Node[A]): Node[A] =
    if (graph.isGoal(current))
      current
    else {
      graph.children(current).filter(!visited.contains(_)).foreach(frontier.add(_))
      if (frontier.hasNext)
        search(frontier, visited + current)(graph, frontier.next)
      else
        null // No solution found
    }

  def depthFirst[A] = search(new DepthFirstFrontier[A](), Set[Node[A]]()) _
  def breadthFirst[A] = search(new BreadthFirstFrontier[A](), Set[Node[A]]()) _
  def uniformCost[A] = search(new UniformCostFrontier[A](), Set[Node[A]]()) _
  def greedyBestFirst[A] = search(new GreedyBestFirstFrontier[A](), Set[Node[A]]()) _
  def aStar[A] = search(new AStarFrontier[A](), Set[Node[A]]()) _
}
