public class PillarsDivTwo {

	public double maximalLength(int[] height, int w) {
		if (height.length == 0) {
			return 0.0;
		}
		double[][] dp = new double[height.length][101];
		for (int i = 1; i < dp.length; i++) {
			for (int h1 = 1; h1 <= height[i-1]; h1++) {
				for (int h2 = 1; h2 <= height[i]; h2++) {
					int diff = Math.abs(h2 - h1);
					double length = Math.sqrt(diff*diff + w*w);
					dp[i][h2] = Math.max(dp[i][h2], length + dp[i-1][h1]);
				}
			}
		}
		double b = 0;
		for (int i = 0; i < dp[0].length; i++) {
			b = Math.max(b, dp[dp.length-1][i]);
			
		}
		return b;
	}

}
