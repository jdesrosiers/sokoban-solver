package ai.search

import org.scalatest._

class DepthFirstGraphFrontierSpec extends FlatSpec with BeforeAndAfterEach with Matchers {

	private var frontier: Frontier[String] = _

	override def beforeEach() {
		frontier = Frontier.depthFirst[String]
	}

	"A new frontier" should "should be empty" in {
    frontier.isEmpty should be (true)
  }

	"A non empty frontier" should "should not be empty" in {
    frontier = frontier.add(Node(null, 'op, "one", 0, 0))

    frontier.isEmpty should be (false)
  }

  "A frontier with one node" should "be empty after retrieving that node" in {
    val node = Node(null, 'op, "one", 0, 0)
    frontier = frontier.add(node)

    frontier.head should be (node)
    frontier = frontier.tail

    frontier.isEmpty should be (true)
  }

  "A frontier" should "not accept a node that has already been discovered" in {
    val node1 = Node(null, 'op, "one", 0, 0)
    val node2 = Node(node1, 'op, "one", 0, 0)

    frontier = frontier.add(node1).add(node2).tail

    frontier.isEmpty should be (true)
  }

  "A frontier" should "emit nodes in FILO" in {
    val node1 = Node(null, 'op, "one", 0, 0)
    val node2 = Node(node1, 'op, "two", 0, 0)
    val node3 = Node(node1, 'op, "three", 0, 0)

    frontier = frontier.add(node1).add(node2).add(node3)

    frontier.head should be (node3)
    frontier = frontier.tail

    frontier.head should be (node2)
    frontier = frontier.tail

    frontier.head should be (node1)
    frontier = frontier.tail

    frontier.isEmpty should be (true)
  }
}
