package ai.sokoban

import scala.io.Source

class Initializer(serializedBoard: Source) {
  private val iter = serializedBoard.getLines

  iter.next // Skip the dimensions
  private val walls = toPoints(parseLine(iter.next)).toSet
  private val boxes = toPoints(parseLine(iter.next)).toSet
  private val storage = toPoints(parseLine(iter.next)).toSet
  private val player = toPoint(parseLine(iter.next))

  private def toPoint(point: Array[Int]): Point = Point(point(0), point(1))
  private def parseLine(line: String): Array[Int] = line.split(" ").map(_.toInt)
  private def toPoints(input: Array[Int]): Iterator[Point] = input.tail.sliding(2, 2).map(toPoint)

  val game = Game(walls, storage)
  val gameState = GameState(player, boxes)
}
