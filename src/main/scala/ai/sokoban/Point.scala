package ai.sokoban

case class Point(x: Int, y: Int) {
  def move(direction: Symbol) = direction match {
    case 'U => Point(x, y - 1)
    case 'u => Point(x, y - 1)
    case 'R => Point(x + 1, y)
    case 'r => Point(x + 1, y)
    case 'D => Point(x, y + 1)
    case 'd => Point(x, y + 1)
    case 'L => Point(x - 1, y)
    case 'l => Point(x - 1, y)
  }

  def neighbors = List('U, 'R, 'D, 'L).map(move)

  def manhattanDistance(p: Point) = Math.abs(x - p.x) + Math.abs(y - p.y)
}
