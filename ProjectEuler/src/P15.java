import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P15 {

	public static void main(String[] args) {		
		latticePaths(System.in);
	}

	public static void latticePaths(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		allLatticePaths(501, 501);
//		for (int i = 0; i < dp.length; i++) {
//			System.out.println(Arrays.toString(dp[i]));
//		}
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int m = scan.nextInt();

			sb.append(latticePaths(n, m));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String latticePaths(int n, int m) {
		return dp[n][m]+"";
	}

	public static long[][] dp;
	public static void allLatticePaths(int n, int m) {
		dp = new long[n][m];
		dp[0][0] = 1;
		for (int y = 0; y < dp.length; y++) {
			for (int x = 0; x < dp[0].length; x++) {
				if (!(x == 0 && y == 0)) {
					dp[y][x] = ((x > 0 ? dp[y][x-1] : 0) + (y > 0 ? dp[y-1][x] : 0)) % 1000000007L;
				}
			}
		}		
	}

}
