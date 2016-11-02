package ai

import ai.frontier._
import scala.collection.mutable.Set

object Search {
  private def search[A](frontier: Frontier[A])(graph: Graph[A], start: Node[A]): Path[A] = {
    var visited = Set[Node[A]]()
    frontier.add(start)

    while (!frontier.isEmpty()) {
      val node = frontier.next()
      if (graph.isGoal(node)) {
        return new Path(node)
      }

      for (successor <- graph.children(node)) {
        if (!(visited contains successor))
          frontier.add(successor)
      }

      visited += node
    }

    return new Path(null)
  }

  def depthFirst[A] = search(new DepthFirstFrontier[A]()) _
  def breadthFirst[A] = search(new BreadthFirstFrontier[A]()) _
  def uniformCost[A] = search(new UniformCostFrontier[A]()) _
  def greedyBestFirst[A] = search(new GreedyBestFirstFrontier[A]()) _
  def aStar[A] = search(new AStarFrontier[A]()) _
}
