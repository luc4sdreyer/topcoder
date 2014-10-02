public class KingdomAndTrees {
	public int minLevel(int[] heights) {
		int min = 0;
		boolean success = false;
		while (!success) {
			success = true;
			int[] modHeights = heights.clone();
			modHeights[0] = Math.max(1, modHeights[0]-min);
			for (int j = 1; j < modHeights.length; j++) {
				int diff = (heights[j] + min) - modHeights[j-1];
				if (diff > 0) {
					modHeights[j] = Math.max(modHeights[j-1] + 1, heights[j] - min);
				} else {
					//enlarge min
					min = min + Math.max(1, (-1*diff + 1)/2);
					success = false;
					break;
				}
			}
			if (success) {
				for (int j = 1; j < modHeights.length; j++) {
					if (modHeights[j] <= modHeights[j-1]) {
						success = false;
					}
				}
			}
		}
		return min;
	}


}
