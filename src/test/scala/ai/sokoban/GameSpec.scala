package ai.sokoban

import org.scalatest._

import scala.io.Source

class GameSpec extends FlatSpec with Matchers {
  "restricted points" should "be in restricted" in {
    val initializer = new Initializer(Source.fromFile("resources/level03"))
    val game = initializer.game

    game.restricted should be (Set(Point(4, 1), Point(4, 3), Point(2, 4), Point(4, 4)))
  }

  "restricted points" should "not be in allowed moves" in {
    val initializer = new Initializer(Source.fromFile("resources/level03"))
    val game = initializer.game
    val gameState = initializer.gameState.move(game, 'L).move(game, 'L).move(game, 'U)

    game.allowedMoves(gameState) should be (List('D))
  }
}

