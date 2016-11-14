package ai.romania

import ai.search.{Graph, Node}

class RomaniaGraph extends Graph[String] {
  val graph = Map(
    "Arad" -> List("Zerind", "Sibiu", "Timisoara"),
    "Bucharest" -> List("Urziceni", "Giurgiu", "Pitesti", "Fagaras"),
    "Craiova" -> List("Pitesti", "Dobreta", "RimnicuVilcea"),
    "Dobreta" -> List("Mehadia", "Craiova"),
    "Eforie" -> List("Hirsova"),
    "Fagaras" -> List("Bucharest", "Sibiu"),
    "Giurgiu" -> List("Bucharest"),
    "Hirsova" -> List("Eforie", "Urziceni"),
    "Iasi" -> List("Neamt", "Vaslui"),
    "Lugoj" -> List("Mehadia", "Timisoara"),
    "Mehadia" -> List("Dobreta", "Lugoj"),
    "Neamt" -> List("Iasi"),
    "Oradea" -> List("Sibiu", "Zerind"),
    "Pitesti" -> List("Bucharest", "Craiova", "RimnicuVilcea"),
    "RimnicuVilcea" -> List("Pitesti", "Craiova", "Sibiu"),
    "Sibiu" -> List("Fagaras", "RimnicuVilcea", "Arad", "Oradea"),
    "Timisoara" -> List("Arad", "Lugoj"),
    "Urziceni" -> List("Vaslui", "Hirsova", "Bucharest"),
    "Vaslui" -> List("Urziceni", "Iasi"),
    "Zerind" -> List("Oradea", "Arad")
  )

  val dist = Map(
    ("Arad", "Zerind") -> 75,
    ("Arad", "Sibiu") -> 140,
    ("Arad", "Timisoara") -> 118,
    ("Bucharest", "Urziceni") -> 85,
    ("Bucharest", "Giurgiu") -> 90,
    ("Bucharest", "Pitesti") -> 101,
    ("Bucharest", "Fagaras") -> 211,
    ("Craiova", "Pitesti") -> 138,
    ("Craiova", "Dobreta") -> 120,
    ("Craiova", "RimnicuVilcea") -> 146,
    ("Dobreta", "Mehadia") -> 75,
    ("Dobreta", "Craiova") -> 120,
    ("Eforie", "Hirsova") -> 86,
    ("Fagaras", "Bucharest") -> 211,
    ("Fagaras", "Sibiu") -> 99,
    ("Giurgiu", "Bucharest") -> 90,
    ("Hirsova", "Eforie") -> 86,
    ("Hirsova", "Urziceni") -> 98,
    ("Iasi", "Neamt") -> 87,
    ("Iasi", "Vaslui") -> 92,
    ("Lugoj", "Mehadia") -> 70,
    ("Lugoj", "Timisoara") -> 111,
    ("Mehadia", "Dobreta") -> 75,
    ("Mehadia", "Lugoj") -> 70,
    ("Neamt", "Iasi") -> 87,
    ("Oradea", "Sibiu") -> 151,
    ("Oradea", "Zerind") -> 71,
    ("Pitesti", "Bucharest") -> 101,
    ("Pitesti", "Craiova") -> 138,
    ("Pitesti", "RimnicuVilcea") -> 97,
    ("RimnicuVilcea", "Pitesti") -> 97,
    ("RimnicuVilcea", "Craiova") -> 146,
    ("RimnicuVilcea", "Sibiu") -> 80,
    ("Sibiu", "Fagaras") -> 99,
    ("Sibiu", "RimnicuVilcea") -> 80,
    ("Sibiu", "Arad") -> 140,
    ("Sibiu", "Oradea") -> 151,
    ("Timisoara", "Arad") -> 118,
    ("Timisoara", "Lugoj") -> 111,
    ("Urziceni", "Vaslui") -> 142,
    ("Urziceni", "Hirsova") -> 98,
    ("Urziceni", "Bucharest") -> 85,
    ("Vaslui", "Urziceni") -> 142,
    ("Vaslui", "Iasi") -> 92,
    ("Zerind", "Oradea") -> 71,
    ("Zerind", "Arad") -> 75
  )

  val strLineDist = Map(
    "Arad" -> 366.0,
    "Bucharest" -> 0.0,
    "Craiova" -> 160.0,
    "Dobreta" -> 242.0,
    "Eforie" -> 161.0,
    "Fagaras" -> 178.0,
    "Giurgiu" -> 77.0,
    "Hirsova" -> 151.0,
    "Iasi" -> 226.0,
    "Lugoj" -> 244.0,
    "Mehadia" -> 241.0,
    "Neamt" -> 234.0,
    "Oradea" -> 380.0,
    "Pitesti" -> 98.0,
    "RimnicuVilcea" -> 193.0,
    "Sibiu" -> 253.0,
    "Timisoara" -> 329.0,
    "Urziceni" -> 80.0,
    "Vaslui" -> 199.0,
    "Zerind" -> 374.0
  )

  def g(from: String, to: String) = dist((from, to))
  def h(state: String) = strLineDist.getOrElse(state, Double.MaxValue)
  def successors(state: String) = graph.get(state) match {
    case Some(x) => x.map(state => ('Travel, state))
    case None => Nil
  }
  def isGoal(node: Node[String]) = node.state == "Bucharest"
}
