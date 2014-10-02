import java.util.Stack;

class Ex9_9 {
	public static void main(String args[]) {
		eightQueens();
	}

	public static class Node {
		int position;
		int[][] grid;
		public Node(int position, int[][] grid) {
			this.position = position;
			this.grid = new int[grid.length][grid[0].length];
			for (int i = 0; i < grid.length; i++) {
				this.grid[i] = grid[i].clone();
			}
		}
	}

	public static void eightQueens() {
		Stack<Node> s = new Stack<Node>();
		int[][] grid = new int[8][8];
		s.push(new Node(-1, grid));
		int num = 0;

		while (!s.isEmpty()) {
			Node top = s.pop();

			if (top.position == 7) {
				printGrid(top.grid);
				num++;
				continue;
			}

			int newPos = top.position + 1;
			for (int y = 0; y < top.grid[0].length; y++) {
				if (top.grid[y][newPos] == 0) {
					Node next = new Node(newPos, top.grid);
					int i = y;
					int j = newPos;
					while (i >= 0 && i < next.grid.length && j >= 0 && j < next.grid[0].length) {
						next.grid[i][j] = 1;
						i++;
						j++;
					}
					i = y;
					j = newPos;
					while (i >= 0 && i < next.grid.length && j >= 0 && j < next.grid[0].length) {
						next.grid[i][j] = 1;
						i--;
						j++;
					}
					i = y;
					j = newPos;
					while (i >= 0 && i < next.grid.length && j >= 0 && j < next.grid[0].length) {
						next.grid[i][j] = 1;
						j++;
					}
					next.grid[y][newPos] = 2;
					s.push(next);
				}
			}
		}
		System.out.println(num);
	}

	public static void printGrid(int[][] grid) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 2) {
					sb.append("X");
				} else {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		System.out.println("--------");
	}
}