AI Framework
============

This is a generic framework for all kinds of search.  I intend to rewrite this
in Java.  This can be considered a first draft.  I'm getting it out there now
so everyone can get an idea of where it's at.  It supports

* Depth First Search
* Breadth First Search
* Uniform Cost Search
* Greedy Best First Search
* AStar Search

All search implementations use a generic graph search algorithm.  Each search
is implemented by providing a different Frontier implmentation.

* Depth First Search => Stack
* Breadth First Search => Queue
* Uniform Cost Search => PriorityQueue ordered by g
* Greedy Best First Search => PriorityQueue ordered by h
* AStar Search => PriorityQueue ordered by g+h

Graph
-----
The search functions take a Graph.  A Graph is a trait that can be extended to
create a SokobanGraph.  We can easily implment different heuristics by extending
SokobanGraph and overriding the `h` function.  A RomaniaGraph is implemented for
testing and as an example.


TODO
----
* When adding a node to the frontier in UniformCost, GreedyBestFirst, and AStar,
  it needs to check if the node already exists on the queue.  If it exists and
  the existing node has a lower f value, then new node should be discarded.
  Otherwise, the new node should replace the existing node.

