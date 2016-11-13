package ai.search.frontier

import org.scalatest._
import ai.search.Node

class AStarGraphFrontierSpec extends FlatSpec with BeforeAndAfterEach with Matchers {

	private var frontier: Frontier[String] = _

	override def beforeEach() {
		frontier = new AStarGraphFrontier[String]()
	}

	"A new frontier" should "should be empty" in {
    frontier.isEmpty should be (true)
  }

	"A non empty frontier" should "should not be empty" in {
    frontier.add(new Node(null, 'op, "one", 0, 0))
    frontier.isEmpty should be (false)
  }

  "A frontier with one node" should "be empty after retieving that node" in {
    val node = new Node(null, 'op, "one", 0, 0)
    frontier.add(node);

    frontier.next should be (node)
    frontier.isEmpty should be (true)
  }

  "A frontier" should "not accept a node that has alread been discovered" in {
    val node1 = new Node(null, 'op, "one", 0, 0)
    val node2 = new Node(node1, 'op, "one", 0, 0)
    frontier.add(node1)
    frontier.add(node2)
    frontier.next

    frontier.isEmpty should be (true);
  }

  "A frontier" should "replace an existing node with a better one" in {
    val node1 = new Node(null, 'op, "one", 1, 2)
    val node2 = new Node(node1, 'op, "one", 1, 1)
    frontier.add(node1)
    frontier.add(node2)

    frontier.next should be theSameInstanceAs (node2)
    frontier.isEmpty should be (true)
  }

  "A frontier" should "not replace an existing node with a worse one" in {
    val node1 = new Node(null, 'op, "one", 1, 1)
    val node2 = new Node(node1, 'op, "one", 1, 2)
    frontier.add(node1)
    frontier.add(node2)

    frontier.next should be theSameInstanceAs (node1)
    frontier.isEmpty should be (true)
  }

  "A frontier" should "emit nodes by lowest f value first" in {
    val node1 = new Node(null, 'op, "one", 0, 0)
    val node2 = new Node(node1, 'op, "two", 1, 1)
    val node3 = new Node(node1, 'op, "three", 1, 2)
    frontier.add(node1)
    frontier.add(node3)
    frontier.add(node2)

    frontier.next should be theSameInstanceAs (node1)
    frontier.next should be theSameInstanceAs (node2)
    frontier.next should be theSameInstanceAs (node3)
    frontier.isEmpty should be (true)
  }

  "A frontier" should "accept nodes with same f value but different state" in {
    val node1 = new Node(null, 'op, "one", 0, 0)
    val node2 = new Node(node1, 'op, "two", 1, 1)
    val node3 = new Node(node1, 'op, "three", 1, 1)
    frontier.add(node1)
    frontier.add(node3)
    frontier.add(node2)

    frontier.next should be theSameInstanceAs (node1)
    frontier.next should be theSameInstanceAs (node3)
    frontier.next should be theSameInstanceAs (node2)
    frontier.isEmpty should be(true)
  }
}
