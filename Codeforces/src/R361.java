// Practice
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.math.BigInteger;

public class R361 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

//		FriendsAndSubsequences();
//		MikeAndChocolateThieves();
//		MikeAndShortcuts();
		MikeAndCellphone();
		
		out.close();
	}
	
	public static void FriendsAndSubsequences() {
		int N = in.nextInt();
		int[] a = in.nextIntArray(N);
		int[] b = in.nextIntArray(N);
		FriendsAndSubsequences(N, a, b);
	}
	
	static void FriendsAndSubsequences(int N, int[] a, int[] b) {
		SegmentTreeMax smax = new SegmentTreeMax(a);
		SegmentTreeMin smin = new SegmentTreeMin(b);
		long num = 0;
		for (int i = 0; i < N; i++) {
			int low = i;
			int high = N-1;
			int best = -1;
			while (low <= high) {
				int mid = (low + high) >>> 1;
				boolean eq = smax.get(i, mid) == smin.get(i, mid);
				if (eq) {
					best = Math.max(best, mid);
					low = mid + 1;
				} else {
					high = mid - 1;
				}
			}
			if (best >= i) {
				int len = best - i +1;
				//num += len * (len+1) / 2;
				num += len;
			}
		}
		System.out.println(num);
	}

	public static class SegmentTreeMin {
		private int[][] t;
		private int[] a;
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
		protected int function(int a, int b) {
			return Math.min(a, b);
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = Integer.MAX_VALUE;

		public SegmentTreeMin(int[] b) {
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
		public int get(int i, int j) {
			int res = IDENTITY, height = 0; j++;
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
	
	public static class SegmentTreeMax {
		private int[][] t;
		private int[] a;
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
		protected int function(int a, int b) {
			return Math.max(a, b);
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = Integer.MIN_VALUE;

		public SegmentTreeMax(int[] b) {
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
		public int get(int i, int j) {
			int res = IDENTITY, height = 0; j++;
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
	
	
	
	public static void MikeAndChocolateThieves() {
		System.out.println(MikeAndChocolateThieves(in.nextLong()));
	}

	public static long MikeAndChocolateThieves(long m) {
		long low = 0;
		long high = Long.MAX_VALUE/2;
		long target = m;
		long best = -1;
		while (low <= high) {
			long mid = (low + high) >>> 1;
			long f = mct(mid);
			if (f < target) {
				low = mid + 1;
			} else if (f > target) {
				high = mid - 1;
			} else {
				high = mid - 1;
				best = mid;
			}
		}
		return best;
	}

	static long mct(long n) {
		long ways = 0; 
		for (long k = 2; k*k*k <= n; k++) {
			ways += n / (k*k*k);
		}
		return ways;
	}

	public static void MikeAndShortcuts() {
		int n = in.nextInt();
		int[] a = in.nextIntArray(n);
		MikeAndShortcuts(n, a);
	}
	

	static void MikeAndShortcuts(int n, int[] a) {

		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Integer>());
		}
		
		int[] F = new int[n];
		Arrays.fill(F, Integer.MAX_VALUE);
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[]{0, 0});
		while (!q.isEmpty()) {
			int[] top = q.poll();
			int pos = top[0];
			int e = top[1];
			if (pos < 0 || pos >= F.length || e >= F[pos]) {
				continue;
			}
			F[pos] = e;
			q.add(new int[]{pos - 1, e+1});
			q.add(new int[]{pos + 1, e+1});
			q.add(new int[]{a[pos] - 1, e+1});
		}
		for (int i = 0; i < F.length; i++) {
			out.print(F[i] + " ");
		}
		out.println("");
	}

	static void MikeAndShortcuts2(int n, int[] a) {
		int[] F = new int[n];
		Arrays.fill(F, Integer.MAX_VALUE);
		F[0] = 0;
		for (int i = 0; i < F.length; i++) {
			F[a[i]-1] = Math.min(F[a[i]-1], F[i] + 1);
			if (i < F.length - 1) {
				F[i+1] = Math.min(F[i+1], F[i]+1);
			}
		}
		for (int i = 0; i < F.length; i++) {
			out.print(F[i] + " ");
		}
		out.println("");
	}

	public static void MikeAndCellphone() {
		@SuppressWarnings("unused")
		int n = in.nextInt();
		char[] s = in.next().toCharArray();
		System.out.println(MikeAndCellphone(s));
	}

	static String MikeAndCellphone(char[] s) {
		int n = s.length;

		ArrayList<int[]> path = new ArrayList<>();
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '1') path.add(new int[]{0,0});
			if (s[i] == '2') path.add(new int[]{1,0});
			if (s[i] == '3') path.add(new int[]{2,0});
			if (s[i] == '4') path.add(new int[]{0,1});
			if (s[i] == '5') path.add(new int[]{1,1});
			if (s[i] == '6') path.add(new int[]{2,1});
			if (s[i] == '7') path.add(new int[]{0,2});
			if (s[i] == '8') path.add(new int[]{1,2});
			if (s[i] == '9') path.add(new int[]{2,2});
			if (s[i] == '0') path.add(new int[]{1,3});
		}
		
		boolean[][] grid = {
				{true, true, true},
				{true, true, true},
				{true, true, true},
				{false, true, false},
		};
		
		if (n == 1) {
			return "NO";
		}
		
		int ways = 0;
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				int[] start = new int[]{x, y};
				boolean v = true;
				for (int i = 0; i < path.size(); i++) {
					int nx = path.get(i)[0] - path.get(0)[0] + x;
					int ny = path.get(i)[1] - path.get(0)[1] + y;
					if (!(nx >= 0 && nx < 3 && ny >= 0 && ny < 4 && grid[ny][nx])) {
						v = false;
						break;
					}
				}
				if (v) {
					ways++;
				}
			}
		}
		if (ways > 1) {
			return "NO";
		} else {
			return "YES";
		}
	}
	

	@SuppressWarnings("serial")
	public static class Counter<T> extends HashMap<T, Integer> {
		public void add(T key) {
			Integer i = this.get(key);
			this.put(key, i == null ? 1 : i + 1);
		}
		public void add(T key, int count) {
			Integer i = this.get(key);
			this.put(key, i == null ? count : i + count);
		}
	}

	public static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
			tokenizer = null;
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {return Integer.parseInt(next());}
		public long nextLong() {return Long.parseLong(next());}
		public double nextDouble() {return Double.parseDouble(next());}
		public long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) a[i] = this.nextLong();
			return a;
		}
		public int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) a[i] = this.nextInt();
			return a;
		}
	}
}
