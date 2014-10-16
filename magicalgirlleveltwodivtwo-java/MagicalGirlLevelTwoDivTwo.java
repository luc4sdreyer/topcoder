import java.nio.file.NotLinkException;
import java.util.LinkedList;
import java.util.Queue;

public class MagicalGirlLevelTwoDivTwo {
	
	public static class Node {
		public int x;
		public int y;
		public Node(int x, int y) {
			this.x  = x;
			this.y  = y;
		}
	}

	public String isReachable(int[] jumpTypes, int tx, int ty) {
		boolean[][] grid = new boolean[630][630];
		//boolean can = dfs(grid, jumpTypes, x, y, 0, 0);
		Queue<Node> q = new LinkedList<>();
		boolean can = false;
		int startX = grid.length/2;
		int startY = startX;				
		tx += startX;
		ty += startY;
		Node top = new Node(startX, startY);
		q.add(top);
		while (!q.isEmpty()) {
			top = q.poll();
			int x = top.x;
			int y = top.y;
			if (x == tx && y == ty) {
				can = true;
				break;
			}
			if (x < 0 || x >= grid.length) {
				continue;
			}
			if (y < 0 || y >= grid.length) {
				continue;
			}
			if (grid[y][x] == true) {
				continue;
			}
			grid[y][x] = true;
			for (int i = 0; i < jumpTypes.length; i++) {
				int n = jumpTypes[i];
				q.add(new Node(x-1, y-n));
				q.add(new Node(x+1, y-n));
				q.add(new Node(x-1, y+n));
				q.add(new Node(x+1, y+n));
				q.add(new Node(x-n, y-1));
				q.add(new Node(x+n, y-1));
				q.add(new Node(x-n, y+1));
				q.add(new Node(x+n, y+1));
			}
		}
		if (can) {
			return "YES";
		} else {
			return "NO";
		}
	}
}
