import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class P18 {

	public static void main(String[] args) {
		maximumPathSumI(System.in);
	}
	
	public static void maximumPathSumI(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int[][] triangle = new int[n][n];
			for (int j = 0; j < triangle.length; j++) {
				for (int k = 0; k <= j; k++) {
					triangle[j][k] = scan.nextInt();
				}
			}
			sb.append(maximumPathSumI(n, triangle));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}
	
	public static String maximumPathSumI(int n, int[][] triangle) {
		int[][] dp = new int[n][n+1];
		dp[0][0] = triangle[0][0];
		for (int y = 1; y < dp.length; y++) {
			for (int x = 0; x < dp.length; x++) {
				dp[y][x] = Math.max(triangle[y][x] + dp[y-1][x], triangle[y][x] + (x > 0 ? dp[y-1][x-1] : 0));
			}
		}
		int max = 0;
		for (int i = 0; i < dp.length; i++) {
			max = Math.max(max, dp[dp.length-1][i]);
		}
		return max+"";
	}

}
