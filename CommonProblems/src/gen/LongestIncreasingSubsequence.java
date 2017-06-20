package gen;
import java.util.Arrays;


public class LongestIncreasingSubsequence {

	public static void main(String[] args) {
		int[] a = new int[]{10, 22, 9, 33, 21, 50, 41, 60, 80};
		longestIncreasingSubsequence(a);
	}

	public static int[] longestIncreasingSubsequence(int[] a) {
		int[] dp = new int[a.length];
		dp[0] = 1;
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (a[i] > a[j]) {
					dp[i] = Math.max(dp[i], dp[j]+1);
					max = Math.max(max, dp[i]);
				}
			}
		}
		int[] longest = new int[max];
		max = -1;
		int t = 0;
		for (int i = 0; i < dp.length; i++) {
			if (dp[i] > max) {
				max = dp[i];
				longest[t++] = a[i];
			}
		}
		System.out.println("input:  " + Arrays.toString(a));
		System.out.println("dp:     " + Arrays.toString(dp));
		System.out.println("output: " + Arrays.toString(longest));
		return longest;
	}

}
