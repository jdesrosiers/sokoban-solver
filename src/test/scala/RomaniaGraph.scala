import ai.{Graph, Node}

class RomaniaGraph extends Graph[Symbol] {
  val graph = Map(
    'Arad -> List('Zerind, 'Sibiu, 'Timisoara),
    'Bucharest -> List('Urziceni, 'Giurgiu, 'Pitesti, 'Fagaras),
    'Craiova -> List('Pitesti, 'Dobreta, 'RimnicuVilcea),
    'Dobreta -> List('Mehadia, 'Craiova),
    'Eforie -> List('Hirsova),
    'Fagaras -> List('Bucharest, 'Sibiu),
    'Giurgiu -> List('Bucharest),
    'Hirsova -> List('Eforie, 'Urziceni),
    'Iasi -> List('Neamt, 'Vaslui),
    'Lugoj -> List('Mehadia, 'Timisoara),
    'Mehadia -> List('Dobreta, 'Lugoj),
    'Neamt -> List('Iasi),
    'Oradea -> List('Sibiu, 'Zerind),
    'Pitesti -> List('Bucharest, 'Craiova, 'RimnicuVilcea),
    'RimnicuVilcea -> List('Pitesti, 'Craiova, 'Sibiu),
    'Sibiu -> List('Fagaras, 'RimnicuVilcea, 'Arad, 'Oradea),
    'Timisoara -> List('Arad, 'Lugoj),
    'Urziceni -> List('Vaslui, 'Hirsova, 'Bucharest),
    'Vaslui -> List('Urziceni, 'Iasi),
    'Zerind -> List('Oradea, 'Arad)
  )

  val dist = Map(
    ('Arad, 'Zerind) -> 75,
    ('Arad, 'Sibiu) -> 140,
    ('Arad, 'Timisoara) -> 118,
    ('Bucharest, 'Urziceni) -> 85,
    ('Bucharest, 'Giurgiu) -> 90,
    ('Bucharest, 'Pitesti) -> 101,
    ('Bucharest, 'Fagaras) -> 211,
    ('Craiova, 'Pitesti) -> 138,
    ('Craiova, 'Dobreta) -> 120,
    ('Craiova, 'RimnicuVilcea) -> 146,
    ('Dobreta, 'Mehadia) -> 75,
    ('Dobreta, 'Craiova) -> 120,
    ('Eforie, 'Hirsova) -> 86,
    ('Fagaras, 'Bucharest) -> 211,
    ('Fagaras, 'Sibiu) -> 99,
    ('Giurgiu, 'Bucharest) -> 90,
    ('Hirsova, 'Eforie) -> 86,
    ('Hirsova, 'Urziceni) -> 98,
    ('Iasi, 'Neamt) -> 87,
    ('Iasi, 'Vaslui) -> 92,
    ('Lugoj, 'Mehadia) -> 70,
    ('Lugoj, 'Timisoara) -> 111,
    ('Mehadia, 'Dobreta) -> 75,
    ('Mehadia, 'Lugoj) -> 70,
    ('Neamt, 'Iasi) -> 87,
    ('Oradea, 'Sibiu) -> 151,
    ('Oradea, 'Zerind) -> 71,
    ('Pitesti, 'Bucharest) -> 101,
    ('Pitesti, 'Craiova) -> 138,
    ('Pitesti, 'RimnicuVilcea) -> 97,
    ('RimnicuVilcea, 'Pitesti) -> 97,
    ('RimnicuVilcea, 'Craiova) -> 146,
    ('RimnicuVilcea, 'Sibiu) -> 80,
    ('Sibiu, 'Fagaras) -> 99,
    ('Sibiu, 'RimnicuVilcea) -> 80,
    ('Sibiu, 'Arad) -> 140,
    ('Sibiu, 'Oradea) -> 151,
    ('Timisoara, 'Arad) -> 118,
    ('Timisoara, 'Lugoj) -> 111,
    ('Urziceni, 'Vaslui) -> 142,
    ('Urziceni, 'Hirsova) -> 98,
    ('Urziceni, 'Bucharest) -> 85,
    ('Vaslui, 'Urziceni) -> 142,
    ('Vaslui, 'Iasi) -> 92,
    ('Zerind, 'Oradea) -> 71,
    ('Zerind, 'Arad) -> 75
  )

  val strLineDist = Map(
    'Arad -> 366,
    'Bucharest -> 0,
    'Craiova -> 160,
    'Dobreta -> 242,
    'Eforie -> 161,
    'Fagaras -> 178,
    'Giurgiu -> 77,
    'Hirsova -> 151,
    'Iasi -> 226,
    'Lugoj -> 244,
    'Mehadia -> 241,
    'Neamt -> 234,
    'Oradea -> 380,
    'Pitesti -> 98,
    'RimnicuVilcea -> 193,
    'Sibiu -> 253,
    'Timisoara -> 329,
    'Urziceni -> 80,
    'Vaslui -> 199,
    'Zerind -> 374
  )

  def g(from: Symbol, to: Symbol): Float = dist((from, to))
  def h(state: Symbol): Float = strLineDist(state)
  def get(state: Symbol): Node[Symbol] = Node(null, 'start, state, 0, strLineDist('Bucharest))
  def successors(state: Symbol): List[(Symbol,Symbol)] = graph.get(state) match {
    case Some(x) => x.map(state => ('travel, state))
    case None => Nil
  }
  def isGoal(node: Node[Symbol]): Boolean = node.state == 'Bucharest
}
