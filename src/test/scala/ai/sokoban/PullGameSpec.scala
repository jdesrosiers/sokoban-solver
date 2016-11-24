package ai.sokoban

import org.scalatest._

import scala.io.Source

import ai.search.{Frontier, Search}

class PullGameSpec extends FlatSpec with Matchers {
	def searchLevel(filename: String) = {
    val initializer = new Initializer(Source.fromFile(filename))
    val storage = initializer.player
    val game = PullGame(initializer.walls, storage)
    val graph = new SokobanPullGraph(game)
    val initialNodes = for (neighbor <- storage.neighbors if !game.isWall(neighbor)) yield
     graph.get(PullGameState(neighbor, storage))

    Search.breadthFirst(graph, initialNodes).visited.map { case PullGameState(_, box) => box }
  }

  "Pull on Level 1" should "should visit [(2, 4), (2, 3)]" in {
    val visited = searchLevel("resources/level01")

    visited should be (Set(Point(2, 4), Point(2, 3)))
  }

  "Pull on Level 3" should "should visit [(2, 2), (2, 3), (3, 3)]" in {
    val visited = searchLevel("resources/level03")

    visited should be (Set(Point(2, 2), Point(2, 3), Point(3, 3)))
  }
}
