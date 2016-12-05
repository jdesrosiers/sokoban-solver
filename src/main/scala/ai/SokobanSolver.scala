package ai

import scala.io.Source

import ai.search.Search
import ai.search.{DefaultHeuristic, Heuristic}
import ai.sokoban.{GameState, Initializer, SokobanGraph}
import ai.sokoban.heuristic.{BoxActualDistanceHeuristic, BoxManhattanDistanceHeuristic, CountGoalsHeuristic}

object SokobanSolver {
  // run --a={search-algorithm} --h={heuristic} {level}
  def main(args: Array[String]): Unit = {
    val level = args.filterNot(_ startsWith "--").head

    val startTime = System.nanoTime()

    val initializer = new Initializer(Source.fromFile(level))
    val heuristic: Heuristic[GameState] =
      if (args.contains("--h=CountGoals"))
        CountGoalsHeuristic(initializer.game)
      else if (args.contains("--h=BoxManhattanDistance"))
        BoxManhattanDistanceHeuristic(initializer.game)
      else if (args.contains("--h=BoxActualDistance"))
        BoxActualDistanceHeuristic(initializer.game)
      else
        DefaultHeuristic()
    val graph = SokobanGraph(initializer.game, heuristic)
    val initialState = initializer.gameState

    println("Searching ...")
    val result =
      if (args.contains("--a=bfs"))
        Search.breadthFirst(graph, graph.get(initialState))
      else if (args.contains("--a=dfs"))
        Search.depthFirst(graph, graph.get(initialState))
      else if (args.contains("--a=iddfs"))
        Search.iterativeDeepeningDepthFirst(graph, graph.get(initialState))
      else if (args.contains("--a=uc"))
        Search.iterativeDeepeningDepthFirst(graph, graph.get(initialState))
      else if (args.contains("--a=gbf"))
        Search.greedyBestFirst(graph, graph.get(initialState))
      else if (args.contains("--a=aStar"))
        Search.aStar(graph, graph.get(initialState))
      else
        null

    val endTime = System.nanoTime()

    println("Path found: " + result.operations.map(_.name).mkString(" "))
    println("Nodes Visited: " + result.visited.size)
    println("Depth: " + result.node.g)

    println("")
    println("Search time: " + ((endTime - startTime) / 1000000000.0) + " seconds")

    val mb = 1024*1024
    val runtime = Runtime.getRuntime
    println("Used Memory:  " + (runtime.totalMemory - runtime.freeMemory) / mb + " MB")
    println("Free Memory:  " + runtime.freeMemory / mb + " MB")
    println("Total Memory: " + runtime.totalMemory / mb + " MB")
    println("Max Memory:   " + runtime.maxMemory / mb + " MB")
  }
}
