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

  "CountGoals on level20" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level20")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    //println("Level 20: " + path.map(_.name).mkString(" "))
  }

  /*
  "CountGoals on level25" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level25")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Level 25: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level-example" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level-example")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Example: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level50" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level50")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Level 50: " + path.map(_.name).mkString(" "))
  }

  "CountGoals on level70" should "be fast" in {
    val (graph, initialState) = loadLevel("resources/level70")
    val path = Search.aStar(graph, graph.get(initialState)).operations
    println("Level 70: " + path.map(_.name).mkString(" "))
  }
  */
}
