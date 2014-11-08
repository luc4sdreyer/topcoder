public class TimeTravellingGardener {

	public int determineUsage(int[] distance, int[] height) {
		if (distance.length < 2) {
			return 0;
		}
		int[] xdist = new int[height.length];
		for (int i = 1; i < xdist.length; i++) {
			xdist[i] = xdist[i-1] + distance[i-1];
		}
		for (int i = 0; i < height.length; i++) {
			for (int j = i+1; j < height.length; j++) {
				for (int oneUp = 0; oneUp < 2; oneUp++) {
					for (int twoUp = 0; twoUp < 2; twoUp++) {
						int x1 = xdist[i], x2 = xdist[j], y1 = 0, y2 = 0;
						y1 = height[i];
						y2 = height[j];
						if (oneUp == 0) {
							y1 = 0;
						}
						if (twoUp == 0) {
							y2 = 0;
						}
						
						double m = (y2-y1)/(x2-x1);
						double c = y2 - m * x2;
						
						int num = 0;
						for (int k = 0; k < height.length; k++) {
							
						}
								
					}
				}
			}
		}
		return 0;
	}

}
