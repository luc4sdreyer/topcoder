public class ZigZag {

	public int longestZigZag(int[] sequence) {
		int[][] dp = new int[sequence.length][2];
		dp[0][0] = 1;
		dp[0][1] = 0;
		for (int i = 1; i < sequence.length; i++) {
			for (int j = 0; j < i; j++) {
				if ((dp[j][1] == 0 && sequence[i] != sequence[j]) 
						|| (sequence[i] > sequence[j] && dp[j][1] == -1)
						|| (sequence[i] < sequence[j] && dp[j][1] == 1)) {
					dp[i][0] = Math.max(dp[j][0] + 1, dp[i][0]);
					dp[i][1] = sequence[i] > sequence[j] ? 1 : -1; 
				}
			}
		}		
		int max = 0;
		for (int i = 0; i < dp.length; i++) {
			max = Math.max(max, dp[i][0]);
		}
		return max;
	}

}
