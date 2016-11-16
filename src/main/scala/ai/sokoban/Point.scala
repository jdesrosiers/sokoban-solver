package ai.sokoban

case class Point(x: Int, y: Int) {
  def move(direction: Symbol) = direction match {
    case 'U => Point(x, y - 1)
    case 'R => Point(x + 1, y)
    case 'D => Point(x, y + 1)
    case 'L => Point(x - 1, y)
  }

  def manhattanDistance(p: Point) = Math.abs(x - p.x) + Math.abs(y - p.y)
}