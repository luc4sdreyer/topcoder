public class DevuAndPlantingTrees {

	public int maximumTreesDevuCanGrow(String[] garden) {
		char[][] g = {garden[0].toCharArray(), garden[1].toCharArray()};
		int n = g[0].length;
		int[] best = new int[n];

		int[] old = new int[n];
		for (int i = 0; i < old.length; i++) {
			for (int j = 0; j < 2; j++) {
				if (g[j][i] == '*') {
					old[i]++;
				}
			}
		}
		for (int i = 0; i < best.length; i++) {
			int add = 1;
			if (i < g[0].length) {
				for (int j = 0; j < 2; j++) {
					for (int k = -1; k < 2; k++) {
						if (i + k >= 0 && i + k < g[0].length && g[j][i + k] == '*') {
							add = 0;
						}
					}
				}
			}
			best[i] = Math.max((i >= 2 ? best[i-2] : 0), (i >= 3 ? best[i-3] : 0)) + add + old[i];
		}
		int max = 0;
		for (int i = best.length-2; i < best.length; i++) {
			if (i >= 0) {
				max = Math.max(max, best[i]);
			}
		}
		return max;
	}

}
