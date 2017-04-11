import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class SolutionAll2 {
	public static void main(String[] args) {
//		simpleArraySum(System.in);
//		diagonalDifference(System.in);
//		plusMinus(System.in);
//		staircase(System.in);
		timeConversion(System.in);
	}

	public static void timeConversion(InputStream in) {
		MyScanner scan = new MyScanner(in);
		String a = scan.nextLine();
		int mi = a.indexOf('M')-1;
		String m = a.substring(mi);
		String t = a.substring(0, mi);
		int hour = Integer.parseInt(t.substring(0, 2));
		if (m.equals("PM")) {
			if (hour != 12) {
				hour = (hour + 12) % 24; 
			}
		} else if (hour == 12) {
			hour = 0;
		}
		String sh = hour + "";
		if (sh.length() == 1) {
			sh = "0" + sh;
		}
		System.out.println(sh + t.substring(2));
	}

	public static void staircase(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		char[][] a = new char[n][n];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (j < n - i - 1) {
					a[i][j] = ' ';
				} else {
					a[i][j] = '#';
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			System.out.println(String.valueOf(a[i]));
		}
	}

	public static void plusMinus(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int[] dist = new int[3];
		for (int i = 0; i < a.length; i++) {
			if (a[i] > 0) {
				dist[0]++;
			} else if (a[i] < 0) {
				dist[1]++;
			} else {
				dist[2]++;
			}
		}
		for (int i = 0; i < dist.length; i++) {
			System.out.format("%.6f\n", (dist[i] * 1.0 / n));
		}
	}

	public static void diagonalDifference(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[][] a = new int[n][];
		for (int i = 0; i < n; i++) {
			a[i] = scan.nextIntArray(n);
		}
		int sum1 = 0;
		int sum2 = 0;
		for (int i = 0; i < a.length; i++) {
			sum1 += a[i][i];
			sum2 += a[i][n - i - 1];
		}
		System.out.println(Math.abs(sum1 - sum2));
	}

	public static void simpleArraySum(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		System.out.println(sum);
	}

	public static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream in) {
			this.br = new BufferedReader(new InputStreamReader(in));
		}

		public void close() {
			try {
				this.br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					String s = br.readLine();
					if (s != null) {
						st = new StringTokenizer(s);
					} else {
						return null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextLong();
			}
			return a;
		}

		int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextInt();
			}
			return a;
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine(){
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
