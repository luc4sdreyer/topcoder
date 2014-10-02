public class PointErasingTwo {

	public int getMaximum(int[] y) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < y.length; i++) {
			for (int j = 0; j < y.length; j++) {
				int x1 = i;
				int y1 = y[i];
				int x2 = j;
				int y2 = y[j];
				int count = 0;
				for (int k = 0; k < y.length; k++) {
					if (Math.min(x1, x2) < k && k < Math.max(x1, x2) && Math.min(y1, y2) < y[k] && y[k] < Math.max(y1, y2)) {
						count++;
					}
				}
				max = Math.max(max, count);
			}
		}
		return max;
	}

}
