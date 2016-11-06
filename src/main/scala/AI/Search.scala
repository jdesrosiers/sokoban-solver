package ai

import ai.frontier._

object Search {
  private def search[A](frontier: Frontier[A])(graph: Graph[A], start: Node[A]) = {
    def searchRec[A](frontier: Frontier[A], graph: Graph[A], current: Node[A]): Node[A] = {
      //println("\nIteration")
      //println("Current", (current.state, current.g))
      if (graph.isGoal(current)) {
        //println("Path", current.states)
        current
      } else {
        val childs = graph.children(current)
        //println("Expanded", childs.map(n => (n.state, n.g)))
        childs.foreach(frontier.add(_))
        //println("Frontier", frontier)
        //println("Path", current.states)
        if (frontier.hasNext)
          searchRec(frontier, graph, frontier.next)
        else
          //println("No solution found")
          null // No solution found
      }
    }

    frontier.add(start)
    searchRec(frontier, graph, frontier.next)
  }

  def depthFirst[A] = search(new DepthFirstFrontier[A]()) _
  def breadthFirst[A] = search(new BreadthFirstFrontier[A]()) _
  def uniformCost[A] = search(new UniformCostFrontier[A]()) _
  def greedyBestFirst[A] = search(new GreedyBestFirstFrontier[A]()) _
  def aStar[A] = search(new AStarFrontier[A]()) _
}

