
import java.util.*;

public class Node {

	protected static int numberNodes = 0;
	protected static int depth = 0;
	protected int fn, gn, hn; // A* cost stuff
	protected Node parent; // "parent" node. Where the current node comes from
	protected ArrayList<Node> children = new ArrayList<Node>(); // Children ArrayList which store maximum all the 4
																// moves
																// up, down, left, right
	public int x = 0;
	int[] Currentarr = new int[9];

	public Node(int[] arr) { // Constructor. Sets state of the node and the other variables with zeros and

		for (int i = 0; i < 9; i++) {
			Currentarr[i] = arr[i];
		}
		gn = 0;
		fn = 0;
		parent = null;
	}

	public Node(Node newState, int pathcost) { // Constructor. Sets state of the node and the current pathcost
		for (int i = 0; i < 9; i++) {
			Currentarr[i] = newState.Currentarr[i];
		}
		gn = pathcost;
		fn = 0;
		parent = null;
	}

	public String convertToString() { // converts state to a string representation
		String stringState = "";
		for (int i = 0; i < 9; i++) {
			if ((i + 1) % 3 == 0)
				stringState += Currentarr[i] + "\n";
			else
				stringState += Currentarr[i] + " ";
		}

		return stringState;
	}

	public int euclideanDistance(Node goalState) { // sets and returns hn based on the euclidean distance heuristic
		hn = 0;
		int current[] = this.Currentarr; // takes the current puzzle from the class state and store it in array called
											// current
		int goal[] = goalState.Currentarr; // takes the goal puzzle from the class state and store it in array called
											// current
		int current2D[][] = new int[3][3];
		int goal2D[][] = new int[3][3];
		int pos = 0;

		// convert current and goal into a 2D array for easier calculation
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				current2D[i][j] = current[pos];
				goal2D[i][j] = goal[pos];
				pos++;
			}
		}

		// the manhatten distance
		// int val: n
		// val coord: (x1,y1)
		// find goalVal
		// goal coord: (x2,y2)
		// distance: |x1-x2| + |y1-y2|
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int val = current2D[i][j];
				// val coord
				int x1 = i;
				int y1 = j;
				// goal coord
				int x2 = 0;
				int y2 = 0;

				// find the goal coordinates
				for (int l = 0; l < 3; l++) {
					for (int m = 0; m < 3; m++) {
						int goalVal = goal2D[l][m];
						if (val == goalVal) {
							x2 = l;
							y2 = m;
							break;
						}
					}
				}
				// calculates current value's manhattan distance
				hn += Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
			}
		}

		return hn;
	}

	public int manhattanDistance(Node goalState) {
		hn = 0;
		int current[] = this.Currentarr;
		int goal[] = goalState.Currentarr;
		int current2D[][] = new int[3][3];
		int goal2D[][] = new int[3][3];
		int pos = 0;

		// convert current and goal into a 2D array for easier calculation
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				current2D[i][j] = current[pos];
				goal2D[i][j] = goal[pos];
				pos++;
			}
		}

		// the manhatten distance
		// int val: n
		// val coord: (x1,y1)
		// find goalVal
		// goal coord: (x2,y2)
		// distance: |x1-x2| + |y1-y2|
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int val = current2D[i][j];
				// val coord
				int x1 = i;
				int y1 = j;
				// goal coord
				int x2 = 0;
				int y2 = 0;

				// find the goal coordinates
				for (int l = 0; l < 3; l++) {
					for (int m = 0; m < 3; m++) {
						int goalVal = goal2D[l][m];
						if (val == goalVal) {
							x2 = l;
							y2 = m;
							break;
						}
					}
				}
				// calculates current value's manhattan distance
				hn += Math.abs(x1 - x2) + Math.abs(y1 - y2);
			}
		}

		return hn;
	}

	public ArrayList<Node> expandNodes(int heuristic, int cost, Node goalState) {
		ArrayList<Node> states = new ArrayList<Node>();
		Node newState;
		// convert 9 to 2D array for easier calculation
		int[] current = this.Currentarr;
		int[][] current2D = new int[3][3];
		int pos = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				current2D[i][j] = current[pos];
				pos++;
			}
		}
		// figure out the possible paths based on the 0 element
		// find 0
		// 0 coords: x, y
		// check left, right, up, down
		// left: (x,y-1)
		// right: (x,y+1)
		// up: (x-1, y)
		// down: (x+1,y)
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int val = current2D[i][j];
				if (val == 0) {
					int x = i;
					int y = j;
					// slide left
					if (y - 1 >= 0) {
						newState = slideTile(x, y, x, y - 1, current2D);

						states.add(new Node(newState, cost));
						numberNodes++;
					}
					// slide right
					if (y + 1 < 3) {
						newState = slideTile(x, y, x, y + 1, current2D);

						states.add(new Node(newState, cost));
						numberNodes++;
					}
					// slide up
					if (x - 1 >= 0) {
						newState = slideTile(x, y, x - 1, y, current2D);

						states.add(new Node(newState, cost));
						numberNodes++;
					}
					// slide down
					if (x + 1 < 3) {
						newState = slideTile(x, y, x + 1, y, current2D);

						states.add(new Node(newState, cost));
						numberNodes++;
					}
				}
			}
		}

		// calculate and set each state's f(n)
		// set the parent node of each neighbour to this
		for (int i = 0; i < states.size(); i++) {
			// if heuristic is euclidean
			if (heuristic == 0) {
				states.get(i).euclideanDistance(goalState);
				states.get(i).setFCost();
				states.get(i).setParentNode(this);
			}
			// if heuristic is manhattan
			if (heuristic == 1) {
				states.get(i).manhattanDistance(goalState);
				states.get(i).setFCost();
				states.get(i).setParentNode(this);
			}
		}
		Node.depth++;
		states = mergeSort(states); // sort neighbours for priority/convenience
		return states;
	}

	private static ArrayList<Node> mergeSort(ArrayList<Node> list) {
		if (list.size() <= 1) {
			return list;
		}
		ArrayList<Node> firstHalf = new ArrayList<Node>();
		ArrayList<Node> secondHalf = new ArrayList<Node>();
		for (int i = 0; i < list.size() / 2; i++) {
			firstHalf.add(list.get(i));
		}

		for (int i = list.size() / 2; i < list.size(); i++) {
			secondHalf.add(list.get(i));
		}

		return merge(mergeSort(firstHalf), mergeSort(secondHalf));
	}

	private static ArrayList<Node> merge(ArrayList<Node> l1, ArrayList<Node> l2) {
		if (l1.size() == 0) {
			return l2;
		}

		if (l2.size() == 0) {
			return l1;
		}

		ArrayList<Node> result = new ArrayList<Node>();
		Node nextElement;
		if (l1.get(0).getFCost() > l2.get(0).getFCost()) {
			nextElement = l2.get(0);
			l2.remove(0);
		} else {
			nextElement = l1.get(0);
			l1.remove(0);
		}

		result.add(nextElement);
		result.addAll(merge(l1, l2));

		return result;
	}

	private Node slideTile(int x1, int y1, int x2, int y2, int[][] current2D) {
		Node newState;
		int pos = 0;
		int[][] configuration = new int[3][3];
		for (int l = 0; l < 3; l++) {
			for (int m = 0; m < 3; m++) {
				configuration[l][m] = current2D[l][m];
			}
		}
		// switch tile positions
		int temp = configuration[x1][y1];
		configuration[x1][y1] = configuration[x2][y2];
		configuration[x2][y2] = temp;
		// now convert configuration into a 1D array (complicated, I know)
		// maybe this wasn't the best design in the world okay?!
		int[] config = new int[9];

		for (int l = 0; l < 3; l++) {
			for (int m = 0; m < 3; m++) {
				config[pos] = configuration[l][m];
				pos++;
			}
		}
		newState = new Node(config);
		return newState;
	}

	private void MoveToRight(int[] p, int i) {
		if (i % 3 < 3 - 1) {
			int[] pc = new int[9];
			copyPuzzle(pc, p);
			int temp = pc[i + 1];
			pc[i + 1] = pc[i];
			pc[i] = temp;
			Node child = new Node(pc);
			children.add(child);
			child.parent = this;
			numberNodes++;
		}

	}

	private void MoveToLeft(int[] p, int i) {
		if (i % 3 > 0) {
			int[] pc = new int[9];
			copyPuzzle(pc, p);
			int temp = pc[i - 1];
			pc[i - 1] = pc[i];
			pc[i] = temp;
			Node child = new Node(pc);
			children.add(child);
			child.parent = this;
			numberNodes++;

		}

	}

	private void MoveToUp(int[] p, int i) {
		if (i - 3 >= 0) {
			int[] pc = new int[9];
			copyPuzzle(pc, p);
			int temp = pc[i - 3];
			pc[i - 3] = pc[i];
			pc[i] = temp;
			Node child = new Node(pc);
			children.add(child);
			child.parent = this;
			numberNodes++;
		}

	}

	private void MoveToDown(int[] p, int i) {
		if (i + 3 < this.Currentarr.length) {
			int[] pc = new int[9];
			copyPuzzle(pc, p);
			int temp = pc[i + 3];
			pc[i + 3] = pc[i];
			pc[i] = temp;
			Node child = new Node(pc);
			children.add(child);
			child.parent = this;
			numberNodes++;
		}

	}

	private void copyPuzzle(int[] a, int[] b) {
		for (int i = 0; i < b.length; i++) {
			a[i] = b[i];

		}

	}

	public void printPuzzle() {
		System.out.println();
		int m = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(this.Currentarr[m] + " ");
				m++;

			}
			System.out.println();
		}
	}

	public boolean isSamePuzzle(int[] p) {
		boolean samePuzzle = true;
		for (int i = 0; i < p.length; i++) {
			if (this.Currentarr[i] != p[i]) {
				samePuzzle = false;
			}

		}
		return samePuzzle;
	}

	public ArrayList<Node> expandNode() {
		for (int i = 0; i < 9; i++) {
			if (this.Currentarr[i] == 0) {
				x = i;
				break;
			}
		}
		MoveToRight(this.Currentarr, x);
		MoveToLeft(this.Currentarr, x);
		MoveToUp(this.Currentarr, x);
		MoveToDown(this.Currentarr, x);
		Node.depth++;

		return children;
	}

	public int getHn() {
		return hn;
	}

	public void setGn(int g) {
		gn = g;
	}

	public int getGn() {
		return gn;
	}

	public void setFCost() {
		fn = gn + hn;
	}

	public void setFCost(int fc) {
		fn = fc;
	}

	public int getFCost() {
		return fn;
	}

	public void setParentNode(Node node) {
		parent = node;
	}

	public Node getParentNode() {
		return parent;
	}
}