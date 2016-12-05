package ai.sokoban.heuristic

import org.scalatest._
import ai.search.Search
import ai.sokoban._
import scala.io.Source

class SokobanCountGoalsSpec extends FlatSpec with Matchers {
	def loadLevel(filename: String) = {
    val initializer = new Initializer(Source.fromFile(filename))
    (SokobanGraph(initializer.game, CountGoalsHeuristic(initializer.game)), initializer.gameState)
	}

  "CountGoals on level5" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level05")
    val path = Search.greedyBestFirst(graph, graph.get(initialState)).operations
    //println("Level 15: " + path)
  }

  "CountGoals on level10" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level10")
    val path = Search.greedyBestFirst(graph, graph.get(initialState)).operations
    //println("Example 4 Boxes: " + path)
  }

  "CountGoals on level15" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level15")
    val path = Search.greedyBestFirst(graph, graph.get(initialState)).operations
    //println("Example 5 Boxes: " + path)
  }

  /*
  "CountGoals on level-example6" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level-example6")
    val path = Search.greedyBestFirst(graph, graph.get(initialState)).operations
    println("Example 6 Boxes: " + path)
  }

  "CountGoals on level-example7" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level-example7")
    val path = Search.greedyBestFirst(graph, graph.get(initialState)).operations
    println("Example: " + path)
  }
  */
}
