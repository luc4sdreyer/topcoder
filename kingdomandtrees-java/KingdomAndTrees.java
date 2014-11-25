public class KingdomAndTrees {

	public int minLevel(int[] heights) {
		getPossible(heights, 0);
		int low = 0;
		int high = 1000000000;
		int min = -1;
		while (low <= high) {
			int mid = (low + high)/2;
			if (getPossible(heights, mid)) {
				high = mid-1;
				min = mid;
			} else {
				low = mid+1; 
			}
		}
		return min;
	}

	private boolean getPossible(int[] h, long power) {
		long[] heights = new long[h.length];
		for (int i = 0; i < heights.length; i++) {
			heights[i] = h[i];
		}
		heights[heights.length-1] += power;
		for (int i = heights.length-2; i >= 0; i--) {
			long maxHeight = heights[i+1]-1;
			if (heights[i] - power <= maxHeight) {
				heights[i] = Math.min(heights[i] + power, maxHeight);
			} else {
				return false;
			}
			if (heights[i] < 1) {
				return false;
			}
		}
		return true;
	}

}
