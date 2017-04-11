public class TriangleEasy {

	public int find(int n, int[] x, int[] y) {
		boolean[][] adj = new boolean[n][n];
		for (int i = 0; i < x.length; i++) {
			adj[x[i]][y[i]] = true;
			adj[y[i]][x[i]] = true;
		}
		
		int min = 3;
		
		for (int a = 0; a < n; a++) {
			for (int b = a+1; b < n; b++) {
				for (int c = b+1; c < n; c++) {
					int count = 3;
					if (adj[a][b]) {
						count--;
					}
					if (adj[b][c]) {
						count--;
					}
					if (adj[c][a]) {
						count--;
					}
					min = Math.min(min, count);
				}
			}
		}
		return min;
	}

}
