import java.util.Arrays;

public class Stamp {

	public int getMinimumCost(String desiredColor, int stampCost, int pushCost) {
		int min = Integer.MAX_VALUE;
		char[] desired = desiredColor.toCharArray(); 
		for (int len = 1; len <= desired.length; len++) {			
			int[][] dp = new int[desired.length][3];
			for (int i = 0; i < dp.length; i++) {
				Arrays.fill(dp[i], Integer.MAX_VALUE);
			}
			
			for (int color = 0; color < 3; color++) {
				boolean invalid = false;
				for (int i = 0; i < len; i++) {
					if (desired[i] != intToChar(color) && desired[i] != '*') {
						invalid = true;
						break;
					}
				}
				if (!invalid) {
					dp[len-1][color] = 1;
				}
			}
			
			for (int pos = len; pos < dp.length; pos++) {
				for (int color = 0; color < 3; color++) {
					boolean invalid = false;
					for (int i = pos-len+1; i <= pos; i++) {
						if (desired[i] != intToChar(color) && desired[i] != '*') {
							invalid = true;
							break;
						}
					}
					if (!invalid) {
						for (int i = pos-len+1; i < pos; i++) {
							if (i >= 0 && dp[i][color] != Integer.MAX_VALUE) {
								dp[pos][color] = Math.min(dp[pos][color], dp[i][color] + 1);
							}
						}
						for (int col2 = 0; col2 < 3; col2++) {
							int old = pos-len;
							if (old >= 0 && dp[old][col2] != Integer.MAX_VALUE) {
								dp[pos][color] = Math.min(dp[pos][color], dp[old][col2] + 1);
							}
						}
					}
				}
			}
			for (int color = 0; color < 3; color++) {
				if (dp[dp.length-1][color] != Integer.MAX_VALUE) {
					min = Math.min(min, dp[dp.length-1][color]*pushCost + stampCost * len);
				}
			}			
		}
		return min;
	}
	public int charToInt(char c) {
		if (c == 'R') {
			return 0;
		}
		if (c == 'G') {
			return 1;
		}
		if (c == 'B') {
			return 2;
		}
		return 3;
	}
	public int intToChar(int i) {
		if (i == 0) {
			return 'R';
		}
		if (i == 1) {
			return 'G';
		}
		if (i == 2) {
			return 'B';
		}
		return '*';
	}
}
