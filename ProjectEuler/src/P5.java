import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class P5 {

	public static void main(String[] args) {
		smallestMultiple(System.in);
	}

	public static void smallestMultiple(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			sb.append(smallestMultiple(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String smallestMultiple(long n) {
		int[] a = new int[(int) n];
		for (int i = 0; i < a.length; i++) {
			a[i] = i+1;
		}
		SegmentTree st = new SegmentTree(a);
		return st.get(0, (int)(n-1)) + "";
	}
	
	public static class SegmentTree {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;
		
		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */		
		protected int function(int a, int b) {
			if (a*b == 0) {
				return 0;
			}
			return BigInteger.valueOf(b).multiply(BigInteger.valueOf(a)).divide(
					BigInteger.valueOf(a).gcd(BigInteger.valueOf(b))).intValue();
		}
		
		protected int IDENTITY = 0;

		public SegmentTree(int[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new int[N];
			for (int i = 0; i < b.length; i++) {
				this.a[i] = b[i];
			}
			t = new int[N][n+1];
			for (int x = 0; x < N; x++) {
				t[x][0] = a[x];
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
				}
			}
		}

		public void set(int x, int v) {
			t[x][0] = a[x] = v;
			for (int y = 1; y <= n; y++) {
				int xx = x-(x&((1<<y)-1));
				t[xx][y] = function(t[xx][y-1], t[xx+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b].
		 */
		public int get(int i, int j) {
			int res = IDENTITY, h = 0; j++;
			while (i+(1<<h) <= j) {
				while ((i&((1<<(h+1))-1)) == 0 && i+(1<<(h+1)) <= j) h++;
				if (res == 0) {
					res = t[i][h];
				} else {
					res = function(res, t[i][h]);
				}
				i += (1<<h);
			}
			while (i < j) {
				while (i+(1<<h) > j) h--;
				if (res == 0) {
					res = t[i][h];
				} else {
					res = function(res, t[i][h]);
				}
				i += (1<<h);
			}
			return res;
		}
	}


}
