public class XorTravelingSalesman {

	public int maxProfit(int[] cityValues, String[] roads) {
		int n = roads.length;
		int[][] sp = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (roads[i].charAt(j) == 'Y') {
					sp[i][j] = 1;
				} else {
					sp[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		for (int k = 0; k < sp.length; k++) {
			for (int i = 0; i < sp.length; i++) {
				for (int j = 0; j < sp.length; j++) {
					if (sp[i][k] != Integer.MAX_VALUE && sp[k][j] != Integer.MAX_VALUE) {
						sp[i][j] = Math.min(sp[i][j], sp[i][k] + sp[k][j]);
					}
				}
			}
		}
		return 0;
	}

}
