import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		int[] puzzle = new int[9];
		boolean flag1 = true;
		while (flag1 == true) {
			System.out.println("Enter Scramble :-\n\\\\Sample Puzzle : { 1, 2, 5, 3, 4, 0, 6, 7, 8 }");
			for (int i = 0; i < 9; i++) {
				System.out.print("Cell " + i + " : ");
				puzzle[i] = sc.nextInt();
			}
			Node root = new Node(puzzle);
			Uninformed_Search us = new Uninformed_Search();
			boolean flag2 = true;
			while (flag2 == true) {
				System.out.println("\tSelection Menu");
				System.out.println("1) Breadth First Search" + "\n2) Depth First Search"
						+ "\n3) A* (Using Manhattan Distance)" + "\n4) A* (Using Euclidean Distance)" + "\n5) Exit");
				int x = sc.nextInt();
				if (x == 1) {
					us.BFS(root);
				} else if (x == 2) {
					us.DFS(root);
				} else if (x == 3) {
					System.out.println("Manhattan distance:");
					us.AStarSearch(1, root);
				} else if (x == 4) {
					System.out.println("Euclidean distance:");
					us.AStarSearch(0, root);
				} else if (x == 5) {
					flag1 = false;
					flag2 = false;
					System.out.println("\t\tProgram Ended");
					System.out.println("\n\t\t   Credits\n");
					System.out.println("Youssef Hossam Mohammed	 	(20221372991)");
					System.out.println("Fares Hazem Shalaby     	(20221443356)");
					System.out.println("Peter Hany Fayez                (20221441026)");
					System.out.println("\nDepartment :  Intelligent Systems");
					break;
				} else {
					System.out.println("Invalid Input");
				}
				// boolean flag3 = true;
				// while (flag3 == true) {
				System.out.println("Do you want to enter a new scramble ?");
				System.out.println("1) Yes\n2) No");
				int y = sc.nextInt();
				if (y == 1) {
					flag2 = false;
					// flag3 = false;
				} else if (x == 2) {
					// flag3 = false;
				} else {
					System.out.println("Invalid Input");
				}

				// }
			}
		}
	}
}