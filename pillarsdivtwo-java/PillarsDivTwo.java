	public class PillarsDivTwo {

		public double maximalLength(int[] height, int w) {
			double[][] dp = new double[height.length][101];
			for (int i = 0; i < dp[0].length; i++) {
				dp[0][i] = 0.0;
			}
			for (int pos = 1; pos < height.length; pos++) {
				for (int h = 1; h <= height[pos]; h++) {
					for (int h2 = 1; h2 <= height[pos-1]; h2++) {
						dp[pos][h] = Math.max(dp[pos][h], dp[pos-1][h2] + pyth(h - h2, w));
					}
				}
			}
			double max = 0;
			for (int i = 0; i < dp[0].length; i++) {
				max = Math.max(max, dp[height.length-1][i]);
			}
			return max;
		}
		
		public double pyth(int x, int y) {
			return Math.sqrt(x*x + y*y);
		}

}
