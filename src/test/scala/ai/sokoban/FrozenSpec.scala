package ai.sokoban

import org.scalatest._

import scala.io.Source

class FrozenSpec extends FlatSpec with Matchers {
  "Frozen U-L" should "not be allowed" in {
    val game = Game(Set(Point(1, 1), Point(2, 1)), Set())
    val state = GameState(Point(2, 4), Set(Point(1, 2), Point(2, 3)))

    game.canMove(state, 'L) should be (true)
    game.canMove(state, 'U) should be (false)
  }

  "Frozen U-R" should "not be allowed" in {
    val game = Game(Set(Point(1, 1), Point(2, 1)), Set())
    val state = GameState(Point(1, 4), Set(Point(1, 3), Point(2, 2)))

    game.canMove(state, 'R) should be (true)
    game.canMove(state, 'U) should be (false)
  }

  "Frozen D-L" should "not be allowed" in {
    val game = Game(Set(Point(1, 4), Point(2, 4)), Set())
    val state = GameState(Point(2, 1), Set(Point(1, 3), Point(2, 2)))

    game.canMove(state, 'L) should be (true)
    game.canMove(state, 'D) should be (false)
  }

  "Frozen D-R" should "not be allowed" in {
    val game = Game(Set(Point(1, 4), Point(2, 4)), Set())
    val state = GameState(Point(1, 1), Set(Point(1, 2), Point(2, 3)))

    game.canMove(state, 'R) should be (true)
    game.canMove(state, 'D) should be (false)
  }

  "Frozen L-U" should "not be allowed" in {
    val game = Game(Set(Point(1, 1), Point(1, 2)), Set())
    val state = GameState(Point(4, 2), Set(Point(2, 1), Point(3, 2)))

    game.canMove(state, 'U) should be (true)
    game.canMove(state, 'L) should be (false)
  }

  "Frozen L-D" should "not be allowed" in {
    val game = Game(Set(Point(1, 1), Point(1, 2)), Set())
    val state = GameState(Point(4, 1), Set(Point(3, 1), Point(2, 2)))

    game.canMove(state, 'D) should be (true)
    game.canMove(state, 'L) should be (false)
  }

  "Frozen R-U" should "not be allowed" in {
    val game = Game(Set(Point(4, 1), Point(4, 2)), Set())
    val state = GameState(Point(1, 2), Set(Point(3, 1), Point(2, 2)))

    game.canMove(state, 'U) should be (true)
    game.canMove(state, 'R) should be (false)
  }

  "Frozen R-D" should "not be allowed" in {
    val game = Game(Set(Point(4, 1), Point(4, 2)), Set())
    val state = GameState(Point(1, 1), Set(Point(2, 1), Point(3, 2)))

    game.canMove(state, 'D) should be (true)
    game.canMove(state, 'R) should be (false)
  }
}
