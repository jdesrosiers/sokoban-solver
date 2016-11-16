package ai.sokoban.heuristic

import org.scalatest._
import ai.search.Search
import ai.sokoban._
import scala.io.Source

class SokobanBoxDistanceSpec extends FlatSpec with Matchers {
	def loadLevel(filename: String) = {
    val initializer = new Initializer(Source.fromFile(filename))
    (new SokobanGraph(initializer.game, BoxDistanceHeuristic(initializer.game)), initializer.gameState)
	}

  "CountGoals on level15" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level15")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    //println("Level 15: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level-example4" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level-example4")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    //println("Example 4 Boxes: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level-example5" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level-example5")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    //println("Example 5 Boxes: " + path.map(_.name).mkString(" "))
  }

  /*
  "CountGoals on level-example6" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level-example6")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Example 6 Boxes: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level-example7" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level-example7")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Example: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level50" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level50")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Example: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level100" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level100")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Example: " + path.map(_.name).mkString(" "))
  }
  */
}
