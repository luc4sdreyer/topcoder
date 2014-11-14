public class TimeTravellingGardener {
	
	public static double error = 0.0000000001;

	public int determineUsage(int[] distance, int[] height) {
		if (distance.length < 2) {
			return 0;
		}
		int[] xdist = new int[height.length];
		for (int i = 1; i < xdist.length; i++) {
			xdist[i] = xdist[i-1] + distance[i-1];
		}
		int min = height.length-1;
		for (int i = 0; i < height.length; i++) {
			for (int j = i+1; j < height.length; j++) {
				for (int oneUp = 0; oneUp < 2; oneUp++) {
					for (int twoUp = 0; twoUp < 2; twoUp++) {
						int x1 = xdist[i], x2 = xdist[j], y1 = 0, y2 = 0;
						y1 = height[i];
						y2 = height[j];
						int num = 0;
						if (oneUp == 0) {
							y1 = 0;
							num++;
						}
						if (twoUp == 0) {
							y2 = 0;
							num++;
						}
						
						double m = (y2-y1)/((double)x2-x1);
						double c = y2 - m * x2;
						
						boolean invalid = false;
						for (int k = 0; k < height.length; k++) {
							if (k != i && k != j) {
								double yNeeded = m*xdist[k] + c;
								if (yNeeded < 0) {
									invalid = true;
									break;
								}
								if ((double)height[k] - yNeeded < -error) {
									invalid = true;
									break;
								}
								if (yNeeded - (double)height[k] < -error) {
									num++;
								}
							}
						}
						if (!invalid) {
							min = Math.min(min, num);
						}	
					}
				}
			}
		}
		return min;
	}

}
