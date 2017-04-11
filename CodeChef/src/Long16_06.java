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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.math.BigInteger;

public class Long16_06 {
	public static InputReader in;
	public static PrintWriter out;
	
	public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, true);
//        out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

        prefCHSQARR();
//        testCHSQARR();
//        CHSQARR();
//      CHEFARK();
//		testCHEARMY();
//		CHEARMY();
//		BINOP();
//		CHCOINSG(System.in);
//		DEVARRAY(System.in);
		
		out.close();
	}

	public static void prefCHSQARR() {
		Random rand = new Random(0);
		for (int k = 0; k < 10; k++) {
			long t1 = System.currentTimeMillis();
			
			int[][] q = new int[15][2];
			int N = 1200;
			int M = N;
			int[][] mat = new int[N][M];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					mat[i][j] = rand.nextInt(1000);
				}
			}
			for (int i = 0; i < q.length; i++) {
//				q[i][0] = rand.nextInt(10)+1;
//				q[i][1] = rand.nextInt(M)+1;
				q[i][0] = rand.nextInt(N)+1;
				q[i][1] = rand.nextInt(M)+1;
//				q[i][0] = N/200;
//				q[i][1] = N/2;
			}
			CHSQARR(N, M, mat, q);
			out.println(System.currentTimeMillis() - t1);
			
		}
	}

	public static void testCHSQARR() {
		Random rand = new Random(0);
		for (int N = 1; N <= 20; N++) {
			for (int M = 1; M <= 20; M++) {
				int[][] mat = new int[N][M];
				int[][] q = new int[N*M][2];
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < M; j++) {
						int a = i+1;
						int b = j+1;
						q[i*M + j][0] = a;
						q[i*M + j][1] = b;
						mat[i][j] = rand.nextInt(10);
					}
				}
				long[] r1 = CHSQARR_slow(N, M, mat, q);
				long[] r2 = CHSQARR(N, M, mat, q);
				for (int i = 0; i < r2.length; i++) {
					if (r1[i] != r2[i]) {
						System.out.println("fail");
					}
				}
				//out.println(Arrays.toString());
			}
		}
	}
	
	public static long[] CHSQARR_slow(int N, int M, int[][] mat, int[][] q) {
		long[] r1 = new long[q.length];
		for (int i = 0; i < q.length; i++) {
			int a = q[i][0];
			int b = q[i][1];
			long minFill = Long.MAX_VALUE;
			for (int y = 0; y <= N-a; y++) {
				for (int x = 0; x <= M-b; x++) {
					long max = 0;
					for (int y2 = y; y2 < y+a; y2++) {
						for (int x2 = x; x2 < x+b; x2++) {
							max = Math.max(max, mat[y2][x2]);
						}
					}
					long sum = 0;
					for (int y2 = y; y2 < y+a; y2++) {
						for (int x2 = x; x2 < x+b; x2++) {
							sum += mat[y2][x2];
						}
					}
					long fill = max * a * b - sum;
					minFill = Math.min(fill, minFill);
				}
			}
			r1[i] = minFill;
		}
		return r1;
	}

	public static void CHSQARR() {
		int N = in.nextInt();
		int M = in.nextInt();
		int[][] mat = new int[N][];
		for (int i = 0; i < mat.length; i++) {
			mat[i] = in.nextIntArray(M);
		}
		int nq = in.nextInt();
		int[][] q = new int[nq][];
		for (int i = 0; i < q.length; i++) {
			q[i] = in.nextIntArray(2);
		}
		long[] ret = CHSQARR(N, M, mat, q);
		for (int i = 0; i < ret.length; i++) {
			out.println(ret[i]);
		}
	}

	public static long[] CHSQARR(int N, int M, int[][] mat, int[][] q) {
		SegmentTreeMax[] yMaxSeg = new SegmentTreeMax[M];
		int[][] yMax = new int[M][N+1];
		int[][] ySum = new int[M][];
		
		for (int x = 0; x < M; x++) {
			int[] sumColumn = new int[N+1];
			int[] column = new int[N];
			for (int y = 0; y < N; y++) {
				column[y] = mat[y][x];
				sumColumn[y+1] = sumColumn[y] + column[y];
			}
			yMaxSeg[x] = new SegmentTreeMax(column);
			ySum[x] = sumColumn;
		}

		SegmentTreeMax[] xMaxSeg = new SegmentTreeMax[N];
		int[][] xMax = new int[N][M+1];
		int[][] xSum = new int[N][];
		
		for (int y = 0; y < N; y++) {
			int[] sumRow = new int[M+1];
			for (int x = 0; x < M; x++) {
				sumRow[x+1] = sumRow[x] + mat[y][x];
			}
			xMaxSeg[y] = new SegmentTreeMax(mat[y]);
			xSum[y] = sumRow;
		}
		
		long[] ret = new long[q.length];
		
//		long maxTime = 0;
		for (int i = 0; i < q.length; i++) {
			
//			long t1 = System.currentTimeMillis();
			int a = q[i][0];
			int b = q[i][1];
			if (a * 5 < b) {
				ret[i] = doQuery(xMaxSeg, xMax, xSum, b, a, M, N);
			} else {
				ret[i] = doQuery(yMaxSeg, yMax, ySum, a, b, N, M);
			}
//			t1 = System.currentTimeMillis() - t1;
//			if (t1 > maxTime) {
//				maxTime = t1;
//				System.out.println(a + " " + b + " " + maxTime );
//			}
		}
		
		
		return ret;
	}
	
	private static long doQuery(SegmentTreeMax[] yMaxSeg, int[][] yMax, int[][] ySum, int a, int b, int N, int M) {

		int ab = a * b;
		//System.out.println(a + " " + b);
		
		for (int x = 0; x < M; x++) {
			for (int y = 0; y <= N-a; y++) {
				yMax[x][y] = (int) yMaxSeg[x].query_tree(y, y+a-1);
			}
		}
		
		//SegmentTreeLazyMax txMax2 = new SegmentTreeLazyMax(new int[M]);
		TreeMap<Integer, Integer> txMax = new TreeMap<>();
		int[] txSum = new int[M+1];
		int minFill = Integer.MAX_VALUE;
		
		for (int y = 0; y <= N - a; y++) {
			if (y != 0) {
				txMax.clear();
				for (int x2 = 0; x2 < b; x2++) {
					//txMax2.set_tree(x2, x2, (int)yMax[x2][y]);
					int key = yMax[x2][y];
					if (!txMax.containsKey(key)) {
						txMax.put(key, 1);
					} else {
						txMax.put(key, txMax.get(key) + 1);
					}
					
					txSum[x2+1] = txSum[x2] + (int)(ySum[x2][y+a] - ySum[x2][y]);
				}
			}
			for (int x = 0; x <= M - b; x++) {
				if (x == 0 && y == 0) {
					for (int x2 = 0; x2 < b; x2++) {
						//txMax2.set_tree(x2, x2, (int)yMax[x2][y]);
						int key = yMax[x2][y];
						if (!txMax.containsKey(key)) {
							txMax.put(key, 1);
						} else {
							txMax.put(key, txMax.get(key) + 1);
						}
						
						txSum[x2+1] = txSum[x2] + (int)(ySum[x2][y+a] - ySum[x2][y]);
					}
				} else if (x > 0) {
					// add right, erase left
					//txMax2.set_tree(x-1, x-1, 0);
					
					int key = yMax[x-1][y];
					txMax.put(key, txMax.get(key) - 1);
					if (txMax.get(key) == 0) {
						txMax.remove(key);
						// removed
					}
					if (x+b <= M) {

						key = yMax[x+b-1][y];
						if (!txMax.containsKey(key)) {
							txMax.put(key, 1);
						} else {
							txMax.put(key, txMax.get(key) + 1);
						}
						//txMax2.set_tree(x+b-1, x+b-1, (int)yMax[x+b-1][y]);
						txSum[x+b] = txSum[x+b-1] + (int)(ySum[x+b-1][y+a] - ySum[x+b-1][y]);
					}
				}
				int fill = ab * txMax.lastKey() - (txSum[x+b] - txSum[x]);
//				long fill = ((long)a*b) * txMax2.query_tree(x, x+b-1) - (txSum[x+b] - txSum[x]);
//				if (fill != fill2) {
//					System.currentTimeMillis();
//				}
				minFill = Math.min(minFill, fill);
				//System.out.println("max: " + txMax.query_tree(x, x+b-1) + "\t sum: " + txSum.query_tree(x, x+b-1) + "\t " + fill);
			}
		}
		return minFill;
	}

	public static class SegmentTreeMax extends SegmentTreeSum {
		protected int IDENTITY = Integer.MIN_VALUE;
		
		public SegmentTreeMax(int[] b) {
			super(b);
		}
		
		@Override
		protected int function(int a, int b) {
			return Math.max(a, b);
		}
	}

	public static class SegmentTreeSum {
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
			return a + b;
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = 0;

		public SegmentTreeSum(int[] b) {
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
		public void set_tree(int i, int j, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public int query_tree(int i, int j) {
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
	
	/**
	 * Segment tree with lazy updates. The default implementation is the max function.
	 * 
	 * Lazy updates are a bit of a hack, because they might to be changed if the function changes.
	 * For example with the max function a lazy parent node the update is just added, but with the sum function
	 * the update must be multiplied by the number of children.
	 */

	public static class SegmentTreeLazyMax {
		protected long tree[];
		protected long lazy_update[];
		protected long lazy_set[];
		private int N;
		private int MAX;
		protected long neg_inf = Long.MIN_VALUE;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 * If one of the inputs are neg_inf the function should ignore them. If both are, it should probably return neg_inf.
		 */
		protected long function(long a, long b) {
			return Math.max(a, b);
		}

		public SegmentTreeLazyMax(int[] a) {
			N = 1 << (int) (Math.log10(a.length)/Math.log10(2))+1;
			MAX = N*2;

			int[] arr = new int[N];
			for (int i = 0; i < a.length; i++) {
				arr[i] = a[i];
			}

			tree = new long[MAX];
			lazy_update = new long[MAX];
			lazy_set = new long[MAX];
			Arrays.fill(lazy_set, Long.MIN_VALUE);
			build_tree(1, 0, N-1, arr);
			Arrays.fill(lazy_update, 0);
		}
		
		/**
		 * Build and initialize tree
		 */
		private void build_tree(int node, int a, int b, int[] arr) {
			if (a > b) {
				return; // Out of range
			}

			if (a == b) { // Leaf node
				tree[node] = arr[a]; // Initialize value
				return;
			}

			build_tree(node*2, a, (a+b)/2, arr); // Initialize left child
			build_tree(node*2+1, 1+(a+b)/2, b, arr); // Initialize right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Initialize root value
		}

		/**
		 * Increment elements within range [i, j] with value value
		 */
		public void update_tree(int i, int j, int value) {
			update_tree(1, 0, N-1, i, j, value);
		}

		protected void update_tree(int node, int a, int b, int i, int j, int value) {
			if (lazy_update[node] != 0 || lazy_set[node] != Long.MIN_VALUE) {
				propogate_lazy_updates(node, a, b);
				propogate_lazy_sets(node, a, b);
			}
			
			if (a > b || a > j || b < i) { // Current segment is not within range [i, j]
				return;
			}

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] += value;

				if (a != b) { // Not leaf node
					lazy_update[node*2] += value;
					lazy_update[node*2 + 1] += value;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}
		
		/**
		 * Sets elements within range [i, j] to the specified value. Setting to Long.MIN_VALUE is not supported.
		 */
		public void set_tree(int i, int j, int value) {
			set_tree(1, 0, N-1, i, j, value);
		}

		protected void set_tree(int node, int a, int b, int i, int j, int value) {
			if (lazy_update[node] != 0 || lazy_set[node] != Long.MIN_VALUE) {
				propogate_lazy_updates(node, a, b);
				propogate_lazy_sets(node, a, b);
			}

			if (a > b || a > j || b < i) { // Current segment is not within range [i, j]
				return;
			}

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] = value;

				if (a != b) { // Not leaf node
					lazy_set[node*2] = value;
					lazy_set[node*2 + 1] = value;
				}
				return;
			}

			set_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			set_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		/**
		 * Query tree to get max element value within range [i, j]
		 */
		public long query_tree(int i, int j) {
			return query_tree(1, 0, N-1, i, j);
		}

		/**
		 * All bounds are inclusive.
		 * @param node The tree index
		 * @param a Internal query's lower bound
		 * @param b Internal query's upper bound
		 * @param i External query's lower bound
		 * @param j External query's upper bound
		 */
		protected long query_tree(int node, int a, int b, int i, int j) {
			if (a > b || a > j || b < i) {
				return neg_inf; // Out of range
			}

			if (a >= i && b <= j) { // Current segment is totally within range [i, j]
				return tree[node];
			}

			long q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			long q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			long res = function(q1, q2); // Return final result
			return res;
		}
		
		protected void propogate_lazy_updates(int node, int a, int b) {
			if (lazy_update[node] != 0) { // This node needs to be updated
				tree[node] += lazy_update[node]; // Update it

				if (a != b) {
					lazy_update[node*2] += lazy_update[node]; // Mark child as lazy
					lazy_update[node*2+1] += lazy_update[node]; // Mark child as lazy
				}

				lazy_update[node] = 0; // Reset it
			}
		}
		
		protected void propogate_lazy_sets(int node, int a, int b) {
			if (lazy_set[node] != Long.MIN_VALUE) { // This node needs to be updated
				tree[node] = lazy_set[node]; // Update it

				if (a != b) {
					lazy_set[node*2] = lazy_set[node]; // Mark child as lazy
					lazy_set[node*2+1] = lazy_set[node]; // Mark child as lazy
				}

				lazy_set[node] = Long.MIN_VALUE; // Reset it
			}
		}
		
		public String toString() {
			return Arrays.toString(this.tree);
		}
		
		/**
		 * Find point b where the function over [a, b] == v, assuming the function (not the underlying array!)
		 * is nondecreasing. Time: O(log n)^2
		 * 
		 * @return index of the search key. Follows Arrays.binarySearch convention:
		 * if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the
		 * point at which the key would be inserted into the array: the index of the first element greater than the key,
		 * or a.length if all elements in the array are less than the specified key. Note that this guarantees that the
		 * return value will be >= 0 if and only if the key is found.
		 */
		public long search(int i, long value) {
			int left = i;
			int right = N-1;
			int mid = 0;
			long f = 0;
			int best = -1;
			if (value > query_tree(i, N)) {
				return -(search(0, tree[1]) + 1) -1;
			}
			boolean found = false;
			while (left <= right) {
				mid = (left + right) / 2;
				f = query_tree(i, mid);
				if (f == value) {
					found = true;
					best = mid;
					right = mid-1;
				} else if (f < value) {
					left = mid+1;
				} else {
					best = mid;
					right = mid-1;
				}
			}
			
			if (found) {
				return best;
			} else {
				return -best - 1;
			}
		}
	}

	/**
	 * Watch out for overflows. If the input is integers you should be fine.
	 * If the input is longs just don't use range updates. 
	 */
	public static class SegmentTreeLazySum extends SegmentTreeLazyMax {
		@Override
		protected long function(long a, long b) {
			return a + b;
		}
		
		public SegmentTreeLazySum(int[] a) {
			super(a);
			this.neg_inf = 0;
		}

		@Override
		protected void update_tree(int node, int a, int b, int i, int j, int value) {
			if (lazy_update[node] != 0) { // This node needs to be updated
				tree[node] += lazy_update[node]; // Update it

				if (a != b) {
					lazy_update[node*2] += lazy_update[node]/2; // Mark child as lazy
					lazy_update[node*2+1] += lazy_update[node]/2; // Mark child as lazy
				}

				lazy_update[node] = 0; // Reset it
			}

			if (a > b || a > j || b < i) // Current segment is not within range [i, j]
				return;

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] += value * (b - a + 1);

				if (a != b) { // Not leaf node
					lazy_update[node*2] += value * (b - a + 1)/2;
					lazy_update[node*2 + 1] += value * (b - a + 1)/2;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		@Override
		protected long query_tree(int node, int a, int b, int i, int j) {
			if (a > b || a > j || b < i) {
				return neg_inf; // Out of range
			}

			if (a >= i && b <= j) { // Current segment is totally within range [i, j]
				return tree[node];
			}

			long q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			long q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			long res = function(q1, q2); // Return final result
			return res;
		}
	}
	

	public static void CHEFARK() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = in.nextInt();
			int k = in.nextInt();
			int[] a = in.nextIntArray(n);
			out.println(CHEFARK(n, k, a));
		}
	}

	public static long CHEFARK(int n, int k, int[] a) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				n--;
			}
		}
		long mod = 1000000007;
		long combin = 1;
		long sum = k % 2 == 0 ? 1 : 0;
		
		for (int i = 1; i <= k && i <= n; i++) {
			combin = (combin * ((n - i + 1) / i)) % mod;
			if (k % 2 == i % 2) {
				sum = (sum + combin) % mod; 
			}
		}
		return sum;
	}

	public static String fastCHEARMY(long k) {
		char[] s = Long.toString(k, 5).toCharArray();
		for (int i = 0; i < s.length; i++) {
			s[i] = (char) (((s[i] - '0') * 2) + '0');
		}
		return (new String(s));
	}

	public static boolean CHEARMY(int i) {
		char[] s = Integer.toString(i).toCharArray();
		long sum = 0;

		int len = s.length;
		int N = 1 << len;
		for (int n = 1; n < N; n++) {
			long prod = 1;
			for (int t = 0; t < len; t++) {
				if (((1 << t) & n) != 0) {
					prod *= (s[t] - '0');
				}
			}
			sum += prod;
		}
		if (sum % 2L == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void testCHEARMY() {
		int numNot = 0;
		int pnumNot = 0;
		long[] ref = new long[10];
		int t = 0;
		for (int i = 0; i < 10000; i++) {
			if (CHEARMY(i)) {
				//System.out.println(Integer.toString(i, 5));
				System.out.println(i + "\t" + numNot + " \t " + Integer.toString(numNot, 5)+ "\t"+fastCHEARMY(numNot));
				numNot++;
			}
			if (i == 9 || i == 99 || i == 999 || i == 9999 || i == 99999 || i == 999999 || i == 9999999) {
				ref[t] = numNot - pnumNot;
				pnumNot = numNot;
				t++;
			}
		}
		
		long[][] F = new long[10][10];
		F[0][0] = 1;
		for (int L = 1; L <= F.length-1; L++) {
			for (int d = 0; d <= L; d++) {
				F[d][L] += 4 * F[d][L-1];
				if (d > 0) {
					F[d][L] += 5 * F[d-1][L-1];
				}
			}
		}
		long[] G = new long[10];

		for (int L = 1; L <= F.length-1; L++) {
			//G[L] = G[L-1];
			for (int d = 0; d <= L; d++) {
				if (d % 4 == 1 || d % 4 == 2 || d % 4 == 0 || d % 4 == 3) {
					G[L] += F[d][L];
				}
			}
		}
		
		out.println();
	}

	public static void CHEARMY() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			long K = in.nextLong();
			out.println(fastCHEARMY(K-1));
		}
	}

	public static void BINOP() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			char[] a = in.next().toCharArray();
			char[] b = in.next().toCharArray();
			int oneA = 0;
			int oneB = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] == '1') {
					oneA++;
				}
				if (b[i] == '1') {
					oneB++;
				}
			}
			if (oneA == 0 || oneA == a.length) {
				out.println("Unlucky Chef");
				continue;
			}
			
			int ops = 0;
			int net = oneB - oneA;
			if (net < 0) {
				net *= -1;
				ops += net;
				for (int i = 0; i < b.length && net > 0; i++) {
					if (a[i] == '1' && b[i] == '0') {
						a[i] = '0';
						net--;
					}
				}
			} else if (net > 0) {
				ops += net;
				for (int i = 0; i < b.length && net > 0; i++) {
					if (a[i] == '0' && b[i] == '1') {
						a[i] = '1';
						net--;
					}
				}
			}
			for (int i = 0; i < b.length; i++) {
				if (a[i] == '0' && b[i] == '1') {
					ops++;
				}
			}
			out.println("Lucky Chef");
			out.println(ops);
		}
	}

	public static void CHCOINSG(InputStream in) {
		InputReader scan = new InputReader(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			if (n % 6 == 0) {
				System.out.println("Misha");
			} else {
				System.out.println("Chef");
			}
		}
	}

	public static void DEVARRAY(InputStream in) {
		InputReader scan = new InputReader(in);
		int n = scan.nextInt();
		int tests = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int min = Integer.MAX_VALUE;
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			min = Math.min(min, a[i]);
			max = Math.max(max, a[i]);
		}
		for (int i = 0; i < tests; i++) {
			int q = scan.nextInt();
			if (min <= q && q <= max) {
				System.out.println("Yes");
			} else {
				System.out.println("No");
			}
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
