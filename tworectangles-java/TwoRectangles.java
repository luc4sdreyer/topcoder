public class TwoRectangles {

	public String describeIntersection(int[] A, int[] B) {
		int[][] grid = new int[1001][1001];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
//				if ((x >= A[0] && x <= A[2]) && (y == A[1] || y == A[3])) {
//					grid[y][x]++;
//				} else if ((y >= A[1] && y <= A[3]) && (x == A[0] || x == A[2])) {
//					grid[y][x]++;
//				}
//				if ((x >= B[0] && x <= B[2]) && (y == B[1] || y == B[3])) {
//					grid[y][x]++;
//				} else if ((y >= B[1] && y <= B[3]) && (x == B[0] || x == B[2])) {
//					grid[y][x]++;
//				}
				if ((x >= A[0] && x <= A[2]) && (y >= A[1] && y <= A[3])) {
					grid[y][x]++;
				}
				if ((x >= B[0] && x <= B[2]) && (y >= B[1] && y <= B[3])) {
					grid[y][x]++;
				}
			}
		}
		int numOverlay = 0;
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = -1;
		int maxY = -1;
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] == 2) {
					numOverlay++;
					minX = Math.min(minX, x);
					minY = Math.min(minY, y);
					maxX = Math.max(maxX, x);
					maxY = Math.max(maxY, y);
				}
			}
		}
		if (numOverlay == 0) {
			return "none"; 
		} else if (numOverlay == 1) {
			return "point"; 
		} else {
			if (maxY == minY || maxX == minX) {
				return "segment"; 
			} else {
				return "rectangle";	
			}
		}
	}
}
