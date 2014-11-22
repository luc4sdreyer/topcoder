import java.util.Arrays;

public class Stamp {

	public int getMinimumCost(String desiredColor, int stampCost, int pushCost) {
		char[] desired = desiredColor.toCharArray();
		char[] mapToCol = {'R', 'G', 'B', '*'};
		int min = Integer.MAX_VALUE;
		for (int len = 1; len <= desired.length; len++) {
			int[][] dp = new int[3][desired.length];
			for (int j = 0; j < dp.length; j++) {
				 Arrays.fill(dp[j], 100);
			}
			for (int pos = len-1; pos < desired.length; pos++) {
				for (int col = 0; col < 3; col++) {
					boolean valid = true;
					for (int i = pos-len+1; i <= pos; i++) {
						if (!(desired[i] == mapToCol[col] || desired[i] == '*')) {
							valid = false;
							break;
						}
					}
					if (valid) {
						for (int dpCol = 0; dpCol < 3; dpCol++) {
							if (dpCol == col) {
								// we can overlap
								for (int i = pos-len+1; i < pos; i++) {
									dp[col][pos] = Math.min(dp[col][pos], dp[dpCol][i]+1);
								}
							}
							// normal stamp
							if (pos == len-1) {
								dp[col][len-1] = 1;
							} else {
								dp[col][pos] = Math.min(dp[col][pos], dp[dpCol][pos-len]+1);
							}
						}
					}
				}
			}
			for (int i = 0; i < dp.length; i++) {
				if (dp[i][dp[0].length-1] < 100) {
					min = Math.min(min, dp[i][dp[0].length-1] * pushCost + len * stampCost);
				}
			}
		}
		return min;
	}

}
