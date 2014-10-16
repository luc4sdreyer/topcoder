public class MagicalGirlLevelOneDivTwo {
	public double theMinDistance(int d, int x, int y) {
		double best = 1000000;
		int max = Math.max(Math.abs(x), Math.abs(y));
		int min = Math.min(Math.abs(x), Math.abs(y));
		if (Math.abs(min) < d) {
			best = Math.max(0, Math.abs(max) - d);
		} else {
			for (int i = -d; i <= d; i += 2*d) {
				for (int j = -d; j <= d; j += 2*d) {
					best = Math.min(best, Math.sqrt(((x + i) * (x + i) + (y + j) * (y + j))));
				}
			}
		}
		return best;
	}
	public double theMinDistance2(int d, int x, int y) {
		int max = Math.max(x, y);
		int min = Math.min(x, y);
		double max2 = Math.max(0, max - d);
		double a = (d * min / (double)max);
		double min2 = Math.max(0, min - a);		
		return Math.sqrt(max2 * max2 + min2 * min2);
	}

}
