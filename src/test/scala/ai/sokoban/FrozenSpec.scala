package ai.sokoban

import org.scalatest._

import scala.io.Source

class FrozenSpec extends FlatSpec with Matchers {
  "Frozen U-L" should "not be allowed" in {
    val level = List(
      "6 5",
      "18 1 1 2 1 3 1 4 1 1 2 1 3 1 4 6 1 5 1 6 2 6 3 6 4 1 5 3 5 2 5 4 5 5 5 6 5",
      "2 3 2 4 2",
      "2 2 2 5 2",
      "4 3"
    )
    val initializer = new Initializer(Source.fromString(level.mkString("\n")))
    val game = initializer.game
    val state = initializer.gameState

    game.isDeadlocked(state) should be (true)
  }
}
