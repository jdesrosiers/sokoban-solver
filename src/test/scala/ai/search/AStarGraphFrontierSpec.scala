package ai.search

import org.scalatest._

class AStarGraphFrontierSpec extends FlatSpec with BeforeAndAfterEach with Matchers {

	private var frontier: Frontier[String] = _

	override def beforeEach() {
		frontier = Frontier.aStar[String]
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

    frontier = frontier.add(node1).add(node2)
    frontier = frontier.tail

    frontier.isEmpty should be (true)
  }

  "A frontier" should "replace an existing node with a better one" in {
    val node1 = Node(null, 'op, "one", 1, 2)
    val node2 = Node(node1, 'op, "one", 1, 1)

    frontier = frontier.add(node1).add(node2)

    frontier.head should be theSameInstanceAs node2
    frontier = frontier.tail

    frontier.isEmpty should be (true)
  }

  "A frontier" should "not replace an existing node with a worse one" in {
    val node1 = Node(null, 'op, "one", 1, 1)
    val node2 = Node(node1, 'op, "one", 1, 2)

    frontier = frontier.add(node1).add(node2)

    frontier.head should be theSameInstanceAs node1
    frontier = frontier.tail

    frontier.isEmpty should be (true)
  }

  "A frontier" should "emit nodes by lowest f value first" in {
    val node1 = Node(null, 'op, "one", 0, 0)
    val node2 = Node(node1, 'op, "two", 1, 1)
    val node3 = Node(node1, 'op, "three", 1, 2)

    frontier = frontier.add(node1).add(node3).add(node2)

    frontier.head should be theSameInstanceAs node1
    frontier = frontier.tail

    frontier.head should be theSameInstanceAs node2
    frontier = frontier.tail

    frontier.head should be theSameInstanceAs node3
    frontier = frontier.tail

    frontier.isEmpty should be (true)
  }

  "A frontier" should "accept nodes with same f value but different state" in {
    val node1 = Node(null, 'op, "one", 0, 0)
    val node2 = Node(node1, 'op, "two", 1, 1)
    val node3 = Node(node1, 'op, "three", 1, 1)

    frontier = frontier.add(node1).add(node3).add(node2)

    frontier.head should be theSameInstanceAs node1
    frontier = frontier.tail

    frontier.head should be theSameInstanceAs node3
    frontier = frontier.tail

    frontier.head should be theSameInstanceAs node2
    frontier = frontier.tail

    frontier.isEmpty should be(true)
  }
}
