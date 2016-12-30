Sokoban Solver
==============

Usage
-----
`bin/activator "run-main ai.SokobanSolver --a={search-algorithm} [--h={heuristic}] {level}"`

**search-algorithm**
* bfs - Breadth-First Search
* dfs - Depth-First Search
* iddfs - Iterative Deepening DFS
* uc - Uniform Cost
* gbf - Greedy Best-First Search
* aStar - A* Search

**heuristic**
* CountGoals - The number of storage locations that don't have a box on them
* BoxManhattanDistance - The manhattan distance of each box to the nearest goal
* BoxActualDistance - The number of moves required for the player to move each box to the nearest goal if there are no other boxes in the level.

Build
-----
`bin/activator assembly`

Run Tests
---------
`bin/activator test`

Levels
------
You can find a collection of levels in the `resources` folder.  These levels were
created from http://www.newgrounds.com/portal/view/591235.  Or, you can create 
your own with the level builder at https://jdesrosiers.github.io/sokoban-ui/.

Level File Format
-----------------
Input is 5 lines defining the board
* sixeH sizeV, e.g. "3 5"
* nWallSquares a list of coordinates of wall squares, e.g. "12 1 1 1 2 1 3 2 1 2 3 3 1 3 3 4 1 4 3 5 1 5 2 5 3"
* nBoxes a list of coordinates of boxes, e.g. "1 3 2"
* nStorageLocations a list of coordinates of storage locations, e.g. "1 4 2"
* players initial locatin x and y, e.g. "2 2"

Search Optimizations
--------------------
Deadlocks are game states that can never lead to a goal state.  We implemented
detection of two types of deadlock.

### Dead Sqaure Deadlock
Dead squares are spaces that are always a deadlock if there is a box at that
location.  For example, if you push a box into a wall that doesn't have a
storage location, there is no way to push the box off the wall to get it to
a storage location.

### Freeze Deadlock
A freeze deadlock is when a box becomes immovable and is not a storage location.
A common example is pushing a box into a corner, but a box can also be frozen
without any walls involved.

Search Framework
----------------

### Search
All the standard search algorithms use the same general structure an differ
only in the data structure used to implement the frontier.  The following 
are the graph search frontiers we implemented and the data structures they 
use.

* Depth-First Search => Stack
* Breadth-First Search => Queue
* Uniform Cost Search => PriorityQueue ordered by g
* Greedy Best-First Search => PriorityQueue ordered by h
* AStar Search => PriorityQueue ordered by g+h

### Graph
The search functions take a Graph.  A Graph is a trait that can be extended to
create a Graph for your domain.  A RomaniaGraph is implemented for testing and 
as an example.

Authors
-------
* Jason Desrosiers
* Thomas Kwak
* Prachi Mistry
* Peeyush Gupta

We used Mob Programming and Test Driven Development for most of the implementation.
