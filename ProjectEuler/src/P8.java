import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P8 {

	public static void main(String[] args) {
		largestProductInASeries(System.in);
	}

	public static void largestProductInASeries(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			char[] d = scan.next().toCharArray();
			sb.append(largestProductInASeries(n, k, d));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	private static String largestProductInASeries(int n, int k, char[] d) {
		int max = 0;
		int prod = 1;
		for (int i = k; i <= n; i++) {
			prod = 1;
			for (int j = 1; j <= k; j++) {
				prod *= (d[i-j]-'0');
			}
			max = Math.max(max, prod);
		}
		return max+"";
	}
}
