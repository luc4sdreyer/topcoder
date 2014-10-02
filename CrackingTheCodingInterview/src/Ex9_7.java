import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Ex9_7 {
	public static void main(String args[]) {
		int[][] a = new int[10][8];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (Math.random() > 0.5) {
					a[i][j] = 2;
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			System.out.println(Arrays.toString(a[i]));
		}
		int[] start = {0,0};
		floodFill(a, start, 1);
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			System.out.println(Arrays.toString(a[i]));
		}
	}

	public static void floodFill(int[][] grid, int[] start, int newColor) {
		// start out of bounds
		Queue<int[]> q = new LinkedList<int[]>();
		int oldColor = grid[start[0]][start[1]];
		q.add(start);

		while (!q.isEmpty()) {
			int[] top = q.poll();
			if (top[0] < 0 || top[0] >= grid.length || top[1] < 0 || top[1] >= grid[0].length || grid[top[0]][top[1]] != oldColor) {
				continue;
			}
			grid[top[0]][top[1]] = newColor;

			int[] next = top.clone();
			next[0]--;
			q.add(next);

			next = top.clone();
			next[0]++;
			q.add(next);

			next = top.clone();
			next[1]--;
			q.add(next);

			next = top.clone();
			next[1]++;
			q.add(next);
		}
	}
}