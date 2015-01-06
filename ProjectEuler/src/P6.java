import java.io.InputStream;
import java.math.BigInteger;
import java.util.Scanner;

public class P6 {

	public static void main(String[] args) {
		sumSquareDifference(System.in);
	}

	public static void sumSquareDifference(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		int[] a1 = new int[10000];
		int[] a2 = new int[10000];
		for (int i = 0; i < a1.length; i++) {
			a1[i] = i+1;
			a2[i] = a1[i] * a1[i]; 
		}
		SegmentTree[] st = {new SegmentTree(a1), new SegmentTree(a2)};
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			sb.append(sumSquareDifference((int) n, st));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String sumSquareDifference(int n, SegmentTree[] st) {
		long sumOfSquares = st[1].get(0, n-1);
		long squaresOfSum = st[0].get(0, n-1);
		squaresOfSum = squaresOfSum * squaresOfSum;
		long diff = Math.abs(sumOfSquares - squaresOfSum);
		return diff + "";
	}
	
	public static class SegmentTree {
		private long[][] t;
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
		protected long function(long a, long b) {
			return a + b;
		}
		
		protected int IDENTITY = 0;

		public SegmentTree(int[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new int[N];
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
		public long get(int i, int j) {
			long res = IDENTITY;
			int h = 0; j++;
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
