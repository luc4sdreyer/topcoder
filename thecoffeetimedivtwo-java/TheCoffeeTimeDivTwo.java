import java.util.Arrays;
import java.util.HashSet;

import javax.print.attribute.HashAttributeSet;

public class TheCoffeeTimeDivTwo {

	public int find(int n, int[] tea) {
		HashSet<Integer> teaSet = new HashSet<Integer>();
		for (int j = 0; j < tea.length; j++) {
			teaSet.add(tea[j]);
		}
		HashSet<Integer> coffeeSet = new HashSet<Integer>();
		for (int i = 1; i <= n; i++) {
			coffeeSet.add(i);
		}
		for (Integer i : teaSet) {
			coffeeSet.remove(i);
		}
		int ret = 0;
		for (int i = 0; i < 2; i++) {
//			int[] target = i == 0 ? tea : new int[n - tea.length];
//			if (i == 1) {
//				int k = 0;
//				for (int j = 1; j <= n; j++) {
//					boolean t = false;
//					for (int j2 = 0; j2 < tea.length; j2++) {
//						if (tea[j2] == j) {
//							t = true;
//							break;
//						}
//					}
//					if (!t) {
//						target[k++] = j;
//					}
//				}				 
//			}
			HashSet<Integer> target = i == 0 ? teaSet : coffeeSet;
			if (target.isEmpty()) {
				continue;
			}
			int[][] dp = new int[n+2][7+1];
			for (int j = 0; j < dp.length; j++) {
				Arrays.fill(dp[j], Integer.MAX_VALUE);
			}
			//dp[0][0] = 0;
			int nextIdx = -1;
			for (int k = 1; k < dp.length; k++) {
				if (target.contains(k)) {
					nextIdx = k;
					break;
				}
			}
			dp[nextIdx][6] = nextIdx + 4 + 47;

			for (int j = 0; j < dp.length; j++) {
				if (target.contains(j)) {
					nextIdx = -1;
					for (int k = j+1; k < dp.length; k++) {
						if (target.contains(k)) {
							nextIdx = k;
							break;
						}
					}
					if (nextIdx == -1) {
						int min = Integer.MAX_VALUE;
						for (int k = 0; k < dp[0].length; k++) {
							min = Math.min(min, dp[j][k]);
						}
						ret += min + j;
						break;
					}
					for (int k = 0; k < dp[0].length; k++) {
						if (dp[j][k] != Integer.MAX_VALUE) {
							if (k > 0) {
								dp[nextIdx][k-1] = Math.min(dp[nextIdx][k-1], dp[j][k] + (nextIdx - j + 4));
							}
							dp[nextIdx][6] = Math.min(dp[nextIdx][6], dp[j][k] + (j + nextIdx + 4 + 47));
						}
					}
				}
			}
		}
		return ret;
	}

}
