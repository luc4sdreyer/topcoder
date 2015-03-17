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

public class R292 {
	public static void main(String[] args) {
		//drazilAndDate(System.in);
		//drazilAndHisHappyFriends(System.in);
		//drazilAndFactorial(System.in);
		drazilAndPark(System.in);
		//test();
	}
	
	public static void drazilAndPark(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int[] d = scan.nextIntArray(n);
		int[] h = scan.nextIntArray(n);
		int[][] day = new int[m][2];
		for (int i = 0; i < day.length; i++) {
			day[i] = scan.nextIntArray(2);
		}
		long[] L = new long[n*2];
		long[] R = new long[n*2];
		long dist = 0;
//		for (int i = 0; i < n; i++) {
//			L[i] = 2 * h[i] - dist;
//			R[i] = 2 * h[i] + dist;
//			dist += d[i];
//		}
//		for (int i = n; i < L.length; i++) {
//			L[i] = L[i % n];
//			R[i] = R[i % n];
//		}
		for (int i = 0; i < 2*n; i++) {
			L[i] = 2 * h[i%n] + dist;
			R[i] = 2 * h[i%n] - dist;
			dist += d[i%n];
		}
		
		SegmentTree left = new SegmentTree(L);
		SegmentTree right = new SegmentTree(R);
		
		for (int i = 0; i < day.length; i++) {
			int iL = day[i][0] - 1;
			int iR = day[i][1] - 1;
			if (iL > iR) {
				iR += n;
			}
			iL += n -1;
			iR++;
			
			long best = left.get(iR, iL) + right.get(iR, iL);
			System.out.println(best);
		}
	}

	public static class SegmentTree {
		private long[][] t;
		private long[] a;
		private int N;
		private int n;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 * 
		 * t[width == N][height == n] is essentially a binary tree structure with the root at t[0][n], children at t[0][n-1] and t[N/2][n-1], and so forth. 
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected long function(long a, long b) {
			return Math.max(a, b);
		}

		protected int IDENTITY = 0;

		public SegmentTree(long[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new long[N];
			for (int i = 0; i < b.length; i++) {
				this.a[i] = b[i];
			}
			t = new long[N][n+1];
			for (int x = 0; x < N; x++) {
				t[x][0] = a[x];
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
				}
			}
		} 

		/**
		 * Set position i to v. Time: O(log n)
		 */
		public void set(int i, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public long get(int i, int j) {
			long res = IDENTITY; 
			int height = 0; j++;
			while (i+(1<<height) <= j) {
				while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			while (i < j) {
				while (i+(1<<height) > j) height--;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			return res;
		}

		/**
		 * Get the index of the function over the interval [a, b]. Time: O(log n). Assumes a binary/ternary function like Integer.compareTo
		 */
		public long getIndex(int i, int j) {
			long res = IDENTITY; 
			int height = 0; j++;
			while (i+(1<<height) <= j) {
				while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			while (i < j) {
				while (i+(1<<height) > j) height--;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			return res;
		}
	}

	public static void drazilAndFactorial(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		char[] c = scan.nextLine().toCharArray();
		char[] r = drazilAndFactorial(c);
		Arrays.sort(r);
		for (int i = 0; i < r.length; i++) {
			System.out.print(r[r.length - i - 1]);
		}
		System.out.println();
	}
	
	public static char[] drazilAndFactorial(char[] c) {
		String res = "";
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '2') {
				res += "2";
			} else if (c[i] == '3') {
				res += "3";
			} else if (c[i] == '4') {
				res += "223";
			} else if (c[i] == '5') {
				res += "5";
			} else if (c[i] == '6') {
				res += "53";
			} else if (c[i] == '7') {
				res += "7";
			} else if (c[i] == '8') {
				res += "2227";
			} else if (c[i] == '9') {
				res += "2337";
			}
		}
		char[] r = res.toCharArray();
		return r;
	}
	
	public static void test() {
		for (int i = 1; i < 10000000; i++) {
			char[] c = Integer.toString(i).toCharArray();
			char[] r = drazilAndFactorial(c);
			long f1 = 1;
			for (int j = 0; j < c.length; j++) {
				for (int k = 1; k <= c[j] - '0'; k++) {
					f1 *= k;
				}
			}
			long f2 = 1;
			for (int j = 0; j < r.length; j++) {
				for (int k = 1; k <= r[j] - '0'; k++) {
					f2 *= k;
				}
			}
			if (f1 != f2) {
				System.out.println("fail");
			}
		}
	}

	public static void drazilAndHisHappyFriends(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		
		int[] b = scan.nextIntArray(scan.nextInt());
		int[] g = scan.nextIntArray(scan.nextInt());
		
		boolean[] happyB = new boolean[n];
		for (int i = 0; i < b.length; i++) {
			happyB[b[i]] = true;
		}
		boolean[] happyG = new boolean[m];
		for (int i = 0; i < g.length; i++) {
			happyG[g[i]] = true;
		}
		
		int i = 0;
		while (i < n * m) {
			if (happyB[i%n] != happyG[i%m]) {
				happyB[i%n] = true;
				happyG[i%m] = true;
				i = 0;
			} else {
				i++;
			}
		}
		
		int num = 0;
		for (int j = 0; j < happyB.length; j++) {
			if (happyB[j]) {
				num++;
			}
		}
		for (int j = 0; j < happyG.length; j++) {
			if (happyG[j]) {
				num++;
			}
		}
		if (num == n+m) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}
	
	public static void drazilAndDate(InputStream in) {
		MyScanner scan = new MyScanner(in);
		long a = scan.nextLong();
		long b = scan.nextLong();
		long s = scan.nextLong();
		
		if (s < Math.abs(a) + Math.abs(b)) {
			System.out.println("No");
			return;
		}
		
		if ((Math.abs(a) + Math.abs(b) - s) % 2 == 0) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
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
