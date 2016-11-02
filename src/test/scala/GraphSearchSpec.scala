import org.scalatest._
import ai.Search
import ai.frontier._

class GraphSearchSpec extends FlatSpec with Matchers {
  val graph = new RomaniaGraph

  "Search from NewYork -> Bucharest" should "have no path" in {
    Search.depthFirst(graph, graph.get('NewYork)).states should be (Nil)
  }

  "Search from Bucharest -> Bucharest" should "be Bucharest" in {
    val expected = List('Bucharest)
    Search.depthFirst(graph, graph.get('Bucharest)).states should be (expected)
  }

  "Search Giurgiu -> Bucharest" should "be Giurgiu -> Bucharest" in {
    val expected = List('Giurgiu, 'Bucharest)
    Search.depthFirst(graph, graph.get('Giurgiu)).states should be (expected)
  }

  "Search from Hirsova -> Bucharest" should "be Hirsova -> Urziceni -> Bucharest" in {
    val expected = List('Hirsova, 'Urziceni, 'Bucharest)
    Search.depthFirst(graph, graph.get('Hirsova)).states should be (expected)
  }

  "DepthFirst from Arad -> Bucharest" should "be Arad -> Timisoara -> Lugoj -> Mehadia -> Dobreta -> Craiova -> RimnicuVilcea -> Sibiu -> Fagaras -> Bucharest" in {
    val expected = List('Arad, 'Timisoara, 'Lugoj, 'Mehadia, 'Dobreta, 'Craiova, 'RimnicuVilcea, 'Sibiu, 'Fagaras, 'Bucharest)
    Search.depthFirst(graph, graph.get('Arad)).states should be (expected)
  }

  "BreadthFirst from Arad -> Bucharest" should "be Arad -> Sibiu -> Fagaras -> Bucharest" in {
    val expected = List('Arad, 'Sibiu, 'Fagaras, 'Bucharest)
    Search.breadthFirst(graph, graph.get('Arad)).states should be (expected)
  }

  "UniformCost from Arad -> Bucharest" should "be Arad -> Sibiu -> RimnicuVilcea -> Pitesti -> Bucharest" in {
    val expected = List('Arad, 'Sibiu, 'RimnicuVilcea, 'Pitesti, 'Bucharest)
    Search.uniformCost(graph, graph.get('Arad)).states should be (expected)
  }

  "GreedyBestFirst from Arad -> Bucharest" should "be Arad -> Sibiu -> Fagaras -> Bucharest" in {
    val expected = List('Arad, 'Sibiu, 'Fagaras, 'Bucharest)
    Search.greedyBestFirst(graph, graph.get('Arad)).states should be (expected)
  }

  "A* from Arad -> Bucharest" should "be Arad -> Sibiu -> RimnicuVilcea -> Pitesti -> Bucharest" in {
    val expected = List('Arad, 'Sibiu, 'RimnicuVilcea, 'Pitesti, 'Bucharest)
    Search.aStar(graph, graph.get('Arad)).states should be (expected)
  }
}
