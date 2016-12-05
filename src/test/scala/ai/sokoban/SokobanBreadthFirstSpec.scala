package ai.sokoban

import org.scalatest._
import ai.search.{Search, DefaultHeuristic}
import scala.io.Source

class SokobanBreadthFirstSpec extends FlatSpec with Matchers {
	def loadLevel(filename: String) = {
    val initializer = new Initializer(Source.fromFile(filename))
    (SokobanGraph(initializer.game, DefaultHeuristic()), initializer.gameState)
	}

  "BFS of level 1" should "find path D" in {
    val (graph, initialState) = loadLevel("resources/level01")

    Search.breadthFirst(graph, graph.get(initialState)).operations should be (List('D))
  }

  "BFS of level 2" should "find path L" in {
    val (graph, initialState) = loadLevel("resources/level02")

    Search.breadthFirst(graph, graph.get(initialState)).operations should be (List('L))
  }

  "BFS of level 3" should "find path L -> L -> U -> D -> R -> R -> U -> L" in {
    val (graph, initialState) = loadLevel("resources/level03")

    val expected = List('L, 'L, 'U, 'D, 'R, 'R, 'U, 'L)
    Search.breadthFirst(graph, graph.get(initialState)).operations should be (expected)
  }

  "BFS of level 4" should "find path U -> L -> L -> D -> L -> R -> U -> R -> R -> D -> R -> D -> D -> L -> L -> L -> L -> U" in {
    val (graph, initialState) = loadLevel("resources/level04")

    val expected = List('U, 'L, 'L, 'D, 'L, 'R, 'U, 'R, 'R, 'D, 'R, 'D, 'D, 'L, 'L, 'L, 'L, 'U)
    Search.breadthFirst(graph, graph.get(initialState)).operations should be (expected)
  }

  "BFS of level 5" should "find path D -> R -> R -> R -> R -> U -> R -> U -> U -> L -> L -> D -> L -> R -> U -> R -> R -> D -> D -> L -> D -> L -> L -> U" in {
    val (graph, initialState) = loadLevel("resources/level05")

    val expected = List('D, 'R, 'R, 'R, 'R, 'U, 'R, 'U, 'U, 'L, 'L, 'D, 'L, 'R, 'U, 'R, 'R, 'D, 'D, 'L, 'D, 'L, 'L, 'U)
    Search.breadthFirst(graph, graph.get(initialState)).operations should be (expected)
  }
}
