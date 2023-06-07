import java.util.*;

public class Uninformed_Search {

	private long startTime, endTime;
	protected int[] goal = { 1, 2, 3, 4, 5, 6, 7, 8, 0 }; // We created the goal array the user wanted to reach

	int count = 0; // We created an integer called count to act as our counter later to calculate
					// number of steps we used to reach the goal
	// board size
	protected final static int BOARD = 9;
	// represents the goal of the puzzle
	protected Node goall;
	// The start state, which will be randomly shuffled and solved
	protected int[] startState;
	// private Node start;
	// open list for the A* search
	protected ArrayList<Node> open;
	// closed list for the A* search
	protected ArrayList<Node> closed;
	// explored list for the A* search;
	protected HashMap<String, String> explored;
	// heuristic choice specified by "user"
	protected int heuristicChoice;
	// path cost for path finding
	protected int pathCost;

	protected int k = 1;

	public void BFS(Node root) { // The first method we started with was Breadth-First Search method which take
									// the root of type node as a parameter
		Node.depth = 0;
		Node.numberNodes = 0;
		Queue<Node> path = new LinkedList<Node>(); // Then, We made a queue called path which will contain the correct
													// path of movements to the goal
		ArrayList<Node> visited = new ArrayList<Node>(); // Visited is an ArrayList which contain the visited nodes so
															// we don't cross the same movement twice
		ArrayList<Node> Temp = new ArrayList<Node>(); // Temp is an another ArrayList Which contain the children a.k.a
														// the movements (up, down , left, right)

		start(); // Here we called the start function which start a stopwatch which we will use
					// to calculate the excution time of our method
		boolean goalFound = false; // We created a boolean value that we will use later for the goal checking
		Queue<Node> frontTier = new LinkedList<Node>(); // First, We made our FrontTier list of type queue

		frontTier.add(root); // Then, We added the root in the queue frontTier using the add method

		while (!(frontTier.isEmpty()) && !goalFound) { // We made a While loop which loops until the frontier is empty
														// or the goal is found

			Node currentNode = frontTier.poll(); // Here we polled the first element of the frontTier and put it in a
													// node we created called currentNode
			visited.add(currentNode); // Then we added it in the visited ArrayList
			Temp = currentNode.expandNode(); // Then we used the expandNode method which take the root and make its
												// children out of the 4 movements and put them in an ArrayList called
												// Temp

			for (int i = 0; i < Temp.size(); i++) { // Here we loops on the Temp (Children)
				Node currentChild = Temp.get(i); // We initialized a new node called currentChild with the element of
													// index i
				if (!(contains(visited, currentChild))) { // We used the contains method to check if the currentChild is
															// visited or not
					frontTier.add(Temp.get(i)); // if not we will add the currentChild in the frontTier
				}
				if (currentChild.isSamePuzzle(goal)) { // Then, everytime we had to check if we reached the goal or not
					goalFound = true; // If yes we will change the boolean value of goalFound to true
					count = pathTrace(path, currentChild); // Here we called the pathTrace Function which backtrack
															// parents of children by their pointers we made in class
															// Node
					break; // Then, we broke out of the for loop

				}
			}
		}
		TracePrint(path); // Here we called the TracePrint method to print the correct steps to reach our
							// goal
		System.out.println("Depth : " + Node.depth);
		System.out.println("# Nodes Generated : " + Node.numberNodes);

		System.out.print("\nBFS ");
		end(startTime); // Here we used the end method to stop our stopwatch and to print the excution
						// time of our program
	}

	public void DFS(Node root) { // The second method we made was the Depth-First Search method which take the
									// root of type node as a parameter
		Node.depth = 0;
		Node.numberNodes = 0;
		Queue<Node> path = new LinkedList<Node>(); // Then, We made a queue called path which will contain the correct
													// path of movements to the goal
		ArrayList<Node> visited = new ArrayList<Node>(); // Visited is an ArrayList which contain the visited nodes so
															// we don't cross the same movement twice
		ArrayList<Node> Temp = new ArrayList<Node>(); // Temp is an another ArrayList Which contain the children a.k.a
														// the movements (up, down , left, right)
		start();
		boolean goalFound = false; // We created a boolean value that we will use later for the goal checking
		Stack<Node> frontTier = new Stack<Node>(); // First, We made our FrontTier list of type stack

		frontTier.push(root); // Then, We added the root in the stack frontTier using the push method

		while (!(frontTier.empty()) && !goalFound) { // ...We made all the same exact steps in BFS with only the
														// difference of changing the container we store our items in
														// from queue to stack

			Node currentNode = frontTier.pop();
			visited.add(currentNode);
			Temp = currentNode.expandNode();

			for (int i = 0; i < Temp.size(); i++) {
				Node currentChild = Temp.get(i);
				if (!(contains(visited, currentChild))) {
					frontTier.push(Temp.get(i));
				}
				if (currentChild.isSamePuzzle(goal)) {
					goalFound = true;
					count = pathTrace(path, currentChild);
					break;

				}
			}
		}
		TracePrint(path);
		System.out.println("Depth : " + Node.depth);
		System.out.println("# Nodes Generated : " + Node.numberNodes);

		System.out.print("\nDFS ");
		end(startTime);
	}

	public void AStarSearch(int hc, Node root) {
		Node.depth = 0;
		Node.numberNodes = 0;
		open = new ArrayList<Node>(); // empty open set
		closed = new ArrayList<Node>(); // empty closed set
		explored = new HashMap<String, String>(); // empty explored set
		goall = new Node(goal);
		startState = new int[BOARD];
		for (int i = 0; i < BOARD; i++) { // 1,4,2,6,5,8,7,3,0
			startState[i] = root.Currentarr[i];
		}
		heuristicChoice = hc;
		pathCost = 0;
		// creating a node containing the goal state
		// Node goalNode = new Node(goal);
		// creating a node containing the start state
		start();
		Node startNode = new Node(startState);

		// Putting the start node on the open list/frontier
		open.add(startNode);

		// Estimated total cost from the start
		// note: start state will have a G(n) val of 0, naturally
		heuristic(startNode);
		startNode.setFCost();

		// while the frontier is not the empty set
		while (!open.isEmpty()) {
			// find node with lowest F(n) cost
			Node current = null;
			int pos = 0;
			int lowest = open.get(pos).getFCost();
			for (int i = 0; i < open.size(); i++) {
				if (open.get(i).getFCost() < lowest) {
					lowest = open.get(i).getFCost();
					pos = i;
				}
			}
			// this is the node we found it
			current = open.get(pos);

			// is this node's state the same as the GOAL state?!?!

			if (current.isSamePuzzle(goal)) {
				// solved!
				explored.put(current.getParentNode().convertToString(), current.convertToString());
				String path = printPath(explored, current);
				// print out the solution
				System.out.println("\nStep " + k++ + ":");
				System.out.println(path);
				System.out.println(current.convertToString());
				break;
			}

			// remove current from the frontier
			open.remove(current);
			// add current to closed set
			closed.add(current);

			// increase pathCost since we have to expand the path
			// note: the distance from current to a neighbour will just
			// be 1 in the case of the 8 puzzle
			pathCost = current.getGn() + 1;
			// get current's state expansion. This will return a list of prioritised
			// neighbours to explore
			ArrayList<Node> neighbourNodes = expand(current, pathCost);

			// for neighbour in neighbourNodes
			for (int i = 0; i < neighbourNodes.size(); i++) {
				Node neighbour = neighbourNodes.get(i);
				// System.out.println(neighbour);
				boolean inClosedSet = false;
				// check to see if it's in the closed set so we don't re-explore it
				for (int j = 0; j < closed.size(); j++) {
					if (neighbour.isSamePuzzle(closed.get(j).Currentarr))
						inClosedSet = true;
				}
				// check to see if it's the open set
				boolean inOpenSet = false;
				for (int j = 0; j < open.size(); j++) {
					if (neighbour.isSamePuzzle(open.get(j).Currentarr))
						inOpenSet = true;
				}
				// can explore the node
				if (!inClosedSet) {
					// if it's not in the open set
					if (!inOpenSet) {
						// convert current and neighbour states to strings
						String currentString = current.convertToString();
						String neighbourString = neighbour.convertToString();

						explored.put(currentString, neighbourString);

						// add to frontier
						open.add(neighbour);
					}
				}
			}
		}
		System.out.println("Depth : " + Node.depth);
		System.out.println("# Nodes Generated : " + Node.numberNodes);
		System.out.print("\nA* ");
		end(startTime);
	}

	public String printPath(HashMap<String, String> cameFrom, Node current) {
		String result = "";

		// beyond the graph at this point: done
		if (current.getParentNode() == null) {
			return result;
		} else {
			// find parent in explored map
			String parent = "";
			if (cameFrom.containsKey(current.getParentNode().convertToString()))
				parent += current.getParentNode().convertToString();
			parent += "\nStep " + k++ + ":\n";
			// move up to parent node
			result += printPath(cameFrom, current.getParentNode()) + "\n" + parent;
		}

		return result;
	}

	public ArrayList<Node> expand(Node node, int cost) {
		ArrayList<Node> result = null;
		// find the paths
		result = node.expandNodes(heuristicChoice, cost, goall);
		return result;
	}

	public int heuristic(Node node) {
		int result = 0;
		if (heuristicChoice == 0)
			result = node.euclideanDistance(goall);
		else if (heuristicChoice == 1)
			result = node.manhattanDistance(goall);

		return result;
	}

	public int pathTrace(Queue<Node> path, Node current) { // Here we created a method called pathTrace which backtrack
															// parents of children by their pointers we made in class
															// Node

		int count = 1; // First, We intialized count with 1 to start counting from 1
		path.add(current); // Here we added the current Node in the path queue we created above
		while (current.parent != null) { // Then, We will loop until the current node (root) which doesn't have parents
											// appear
			current = current.parent; // Here We used the parent pointer to track the parent of the current
			path.add(current); // Then, We added the current Node in the path queue we created above again
			count++; // And then increase our counter by 1
		}
		return count; // And after we finish the method it should return the total number of steps to
						// the goal

	}

	public boolean contains(ArrayList<Node> visited, Node current) { // Here We created the contains method to check if
																		// the currentChild is visited or not which
																		// takes 2 parameters the visited ArrayList to
																		// check in it and a Node tocheck fo it's
																		// presence

		for (int i = 0; i < visited.size(); i++) { // Here We made a for loop which loops on the elements of the visited
													// Nodes in the ArrayList

			if (visited.get(i).isSamePuzzle(current.Currentarr)) { // Then, We used an if condition which check if the 2
																	// puzzles are similar index by index by a method we
																	// created in the Node class
				return true;

			}
		}
		return false; // And it will return if it's true or false

	}

	public void TracePrint(Queue<Node> path) { // Here We created a method called TracePrint which prints the path to
												// the goal we found by either way BFS or DFS
		System.out.println("Solved in " + count + " Step(s)"); // Here we printed the total number of steps to reach our
																// goal
		while (!(path.isEmpty())) { // Then, We created a while loop which stops when the path is empty
			Node currentNode = path.poll(); // Everytime we remove a Node from the ArrayList path and store it in a Node
											// called currentNode
			System.out.println("Step " + count-- + " :"); // Here we made this line to print number of each step before
															// printing it
			currentNode.printPuzzle(); // Then we print the puzzle by a method called printPuzzle we created previously
										// in the Node class
			System.out.println();
		}
	}

	public void start() { // We created the start fuction to start a stopwatch which we will be used to
							// calculate the excution time of any method in our program
		startTime = System.nanoTime(); // Here We initialized the startTime variable with the start time in nano
										// seconds
	}

	public void end(long startTime) { // We created the end function to stop the stopwatch which we started before in
										// the start method and calculate the excution time of our program then print it
										// in the console
		endTime = System.nanoTime();
		System.out.println("Runtime : " + (endTime - startTime) + " nano seconds\n");

	}
}