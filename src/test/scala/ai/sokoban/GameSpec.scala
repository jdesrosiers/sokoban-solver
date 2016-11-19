package ai.sokoban

import org.scalatest._

import scala.io.Source

class GameSpec extends FlatSpec with Matchers {
  "restricted points in level 3" should "be in restricted" in {
    val initializer = new Initializer(Source.fromFile("resources/level03"))
    val game = initializer.game

    game.restricted should be (Set(Point(3, 4), Point(4, 1), Point(4, 3), Point(2, 4), Point(4, 4)))
  }

  "restricted points in level 15" should "be in restricted" in {
    val initializer = new Initializer(Source.fromFile("resources/level15"))
    val game = initializer.game

    game.restricted should be (Set(Point(7,5), Point(2,5), Point(5,2), Point(3,4), Point(4,7), Point(6,2), Point(6,3), Point(2,6), Point(5,7), Point(1,3), Point(2,2), Point(2,7), Point(4,2), Point(3,7)))
  }

  "restricted points in level 20" should "be in restricted" in {
    val initializer = new Initializer(Source.fromFile("resources/level20"))
    val game = initializer.game

    game.restricted should be (Set(Point(1,8), Point(2,5), Point(5,2), Point(6,8), Point(2,6), Point(3,2), Point(2,2), Point(2,4), Point(3,7), Point(2,3), Point(5,6)))
  }

  "restricted points" should "not be in allowed moves" in {
    val initializer = new Initializer(Source.fromFile("resources/level03"))
    val game = initializer.game
    val gameState = initializer.gameState.move(game, 'L).move(game, 'L).move(game, 'U)

    game.allowedMoves(gameState) should be (List('D))
  }
}

