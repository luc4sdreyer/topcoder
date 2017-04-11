// Long16_07
import java.io.*;
import java.util.*;
import java.math.*;

public class Long16_07 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		new Thread(null, new Runnable() {
            public void run() {
                Long16_07.run();
            }
        }, "1", 1 << 26).start(); // Set stack memory to 64 MB
	}
	
	public static void run() {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

		expPOLYEVAL();
//		perfPOLYEVAL();
//		POLYEVAL();
//		ALLPOLY();
//		perfWORKCHEF();
//		expWORKCHEF();
//		WORKCHEF();
//		prefCHSGMNTS();
//		CHSGMNTS();
//		CHEFARC();
//		CHEFTET();
//		CHEFELEC();
//		EGRANDR();
		
		out.close();
	}
	
	static boolean debug = true;
	
	public static void expPOLYEVAL() {
		mod = 13;
		debug = false;
		
		for (int i = 0; i < 3; i++) {
			int N = (int) mod;
			int nq = N;
			int[] c = new int[N+1];
			int[] q = new int[N];
			for (int j = 0; j < N+1; j++) {
//				c[j] = ra.nextInt((int) mod);
				c[j] = j;
			}
			Arrays.sort(c);
			for (int j = 0; j < N; j++) {
				q[j] = j;
			}
			System.out.println(Arrays.toString(c));
			POLYEVAL(N, c, nq, q);
		}
	}
	
	public static void perfPOLYEVAL() {
		HashSet<Integer> p = getPrimesSet(786433);
		System.out.println(p.size());
		
		for (int i = 0; i < 5; i++) {
			long t1 = System.currentTimeMillis();

			int N = 15000;
			int nq = N;
			int[] c = new int[N+1];
			int[] q = new int[N];
			for (int j = 0; j < N+1; j++) {
				c[j] = ra.nextInt((int) mod);
			}
			for (int j = 0; j < N; j++) {
				q[j] = ra.nextInt((int) mod);
			}
			POLYEVAL(N, c, nq, q);
			System.out.println(System.currentTimeMillis() - t1);
		}
	}

	public static void POLYEVAL() {
		int N = in.nextInt();
		int[] c = in.nextIntArray(N+1);
		int nq = in.nextInt();
		int[] q = in.nextIntArray(nq);
		POLYEVAL(N, c, nq, q);
	}
	
	public static long mod = 786433;
	public static void POLYEVAL(int N, int[] c, int nq, int[] q) {
		for (int i = 0; i < q.length; i++) {
			long sum = c[0];
			long x = q[i];
			long xn = 1; 
			int[] poly = new int[N];
			for (int j = 1; j <= N; j++) {
				xn = xn * x % mod;
				sum += c[j] * xn;
				poly[j-1] = (int) ((c[j] * xn) % mod);
			}
			sum %= mod;
			if (!debug) {
				System.out.println(sum + "\t" + Arrays.toString(poly));
			}
		}
	}
	
	public static BitSet getPrimes(int limit) {
		BitSet notPrimes = new BitSet(limit+1);
		BitSet primes = new BitSet(limit+1);
		
		for (int i = notPrimes.nextClearBit(2); i >= 0 && i <= limit; i = notPrimes.nextClearBit(i+1)) {
			primes.set(i);
			for (int j = 2*i; j <= limit; j+=i) {
				notPrimes.set(j);
			}
		}
		return primes;
	}
	
	public static HashSet<Integer> getPrimesSet(int limit) {
		HashSet<Integer> primes = new HashSet<Integer>((int) (limit/10));
		BitSet p = getPrimes(limit);
		
		for (int i = p.nextSetBit(0); i >= 0 && i <= limit; i = p.nextSetBit(i+1)) {
			primes.add(i);
		}
		return primes;
	}
	
	public static void perfWORKCHEF() {
		//    	WORKCHEF(0, 200000, 2);
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			long L = 1L;
			long R = 1000000000000000000L;
			long K = i+1;
			WORKCHEF(L, R, K);
		}
		System.out.println(System.currentTimeMillis() - t1);
		testWORKCHEF();
	}

	public static void testWORKCHEF() {
		for (int i = 0; i < 20; i++) {
			debug = false;
			long L = ra.nextInt(1000000000);
			long R = ra.nextInt(1000000000) + L;
			long K = i+1;
			long r1 = Long16_07.WORKCHEF(L, R, K);
			long r2 = WORKCHEF(L, R, K);
			if (r1 != r2) {
				System.out.println("fail");
				break;
			}
		}
		if ((WORKCHEF(1, 12345, 1) - 9924) != 0) System.out.println("fail");
		if ((WORKCHEF(1, 12345, 2) - 4092) != 0) System.out.println("fail");
		if ((WORKCHEF(1, 12345, 3) - 908) != 0) System.out.println("fail");
		if ((WORKCHEF(1, 1234567, 4) - 47925) != 0) System.out.println("fail");
		if ((WORKCHEF(1, 1234567, 5) - 5836) != 0) System.out.println("fail");
		System.out.println("no bugs found!");
	}

	public static void WORKCHEFtest() {
		for (int i = 0; i < 10; i++) {
			long L = 1;
			long R = 1000;
			long K = i;
			System.out.println(WORKCHEF(L, R, K) + "\t" + WORKCHEFslow(L, R, K));
		}
	}

	public static void WORKCHEF() {
		int nq = in.nextInt();
		for (int i = 0; i < nq; i++) {
			long L = in.nextLong();
			long R = in.nextLong();
			long K = in.nextLong();
			System.out.println(WORKCHEF(L, R, K));
		}
	}

	static long WORKCHEF(long L, long R, long K) {
		if (dp == null) {
			dp = new long[20][1 << 10][2520];
			pre_taken = new int[1 << 10][MOD];

			long pt = 1;
			for (int i = 0; i < pow10.length; i++) {
				pow10[i] = pt;
				pt *= 10;
			}
		}
		
		int rlen = Long.toString(R).length();
		int llen = Long.toString(L).length();
		
		for (int len = 0; len < rlen; len++) {
			for (int mask = 0; mask < dp[0].length; mask++) {
				Arrays.fill(dp[len][mask], -1);
			}
		}
		
		long wr = WORKCHEF(R, K);
		
		for (int len = llen-1; len < rlen; len++) {
			for (int mask = 0; mask < dp[0].length; mask++) {
				Arrays.fill(dp[len][mask], -1);
			}
		}
		
		return wr - WORKCHEF(L - 1, K);
	}

	public static long gcd(long a, long b) {
		long r;
		while (b != 0) {
			r = a % b;
			a = b;
			b = r;
		}
		return a;
	}

	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}

	static int[][] count = new int[1 << 10][2520];
	static int ops = 0;

	static long dfs(int len, int num, int taken_mask, boolean flag) {
		if (len < 0) {
			return pre_taken[taken_mask][num];
		}
		if (!flag && dp[len][taken_mask][num] != -1) {
			return dp[len][taken_mask][num];
		}
		
		if (len < kspecial - Integer.bitCount(taken_mask)) {
			dp[len][taken_mask][num] = 0;
			return 0;
		}
		
		if (!flag && kspecial == 1) {
			if (((taken_mask >> 1) & 1) == 1) {
				dp[len][taken_mask][num] = pow10[len+1];
				return pow10[len+1];
			}
		}

		int end = flag ? digit[len] : 9;
		long ans = 0;

		int newNum = num * 10;
		for (int i = 0; i <= end; i++) {
			ans += dfs(len - 1, (newNum + i) % MOD, taken_mask | (1 << i), (i == end && flag));
		}

//		if (len == 3 && flag == false && debug) {
//			System.out.println(ans);
//			debug = false;
//		} 
		if (!flag) {
			dp[len][taken_mask][num] = ans;
		}
		return ans;

	}

	static long[] pow10 = new long[21]; 
	static final int MOD = 2520;
	static long[][][] dp;
	static int[] digit;
	static int[] LCM;
	static int[][] pre_taken;
	static int kspecial;
	static BitSet visited;

	static long WORKCHEF(long n, long K) {
		kspecial = (int)K;
		digit = new int[20];


		for (int i = 0; i < MOD; i++) {
			for (int j = 0; j < pre_taken.length; j++) {
				int special = 0;
				for (int k = 1; k <= 9; k++) {
					if (((j >> k) & 1) == 1 && i % k == 0) {
						special++;
					}
				}
				pre_taken[j][i] = special >= kspecial ? 1 : 0;
			}
		}

		int pos = 0;
		while (n > 0) {
			digit[pos++] = (int) (n % 10L);
			n /= 10L;
		}

		long r1 = dfs(pos - 1, 0, 1, true);

		return r1;
	}

	public static int WORKCHEFslow(long L, long R, long K) {
		int[] sum = new int[10];
		int[] sumx = new int[10];
		int[] sumk = new int[10];
		int[][] x = new int[1000001][10];
		for (int i = 1; i <= 1000000; i++) {
			int t = i;
			int[] pres = new int[10];
			while (t > 0) {
				pres[t % 10]++;
				t /= 10;
			}
			int kcount = 0;
			for (int k = 1; k <= 9; k++) {
				if (i % k == 0 && pres[k] > 0) {
					kcount++;
					sumk[k]++;
				}
			}
			for (int j = 0; j < 10; j++) {
				x[i][j] += x[i - 1][j];
			}
			for (int j = 0; j <= kcount; j++) {
				sum[j]++;
				x[i][j]++;
			}
			sumx[kcount]++;
			//			System.out.println(i + "\t" + kcount + "\t" + Arrays.toString(sum) + "\t" + Arrays.toString(sumx) + "\t" + Arrays.toString(sumk));
		}
		return x[(int) R][(int) K] - x[(int) (L - 1)][(int) K];

	}

	public static void expWORKCHEF() {
		int[] sum = new int[10];
		int[] sumx = new int[10];
		int[] sumk = new int[10];
		for (int i = 1; i <= 3000; i++) {
			int t = i;
			int[] pres = new int[10];
			while (t > 0) {
				pres[t % 10]++;
				t /= 10;
			}
			int kcount = 0;
			for (int k = 1; k <= 9; k++) {
				if (i % k == 0 && pres[k] > 0) {
					kcount++;
					sumk[k]++;
				}
			}
			for (int j = 0; j <= kcount; j++) {
				sum[j]++;
			}
			sumx[kcount]++;
			System.out.println(i + "\t" + kcount + "\t" + Arrays.toString(sum) + "\t" + Arrays.toString(sumx) + "\t" + Arrays.toString(sumk));
		}
	}

	public static void WORKCHEF(int[][] q) {
		int[] sum = new int[10];
		int[] sumx = new int[10];
		int[] sumk = new int[10];
		int[][] x = new int[1000001][10];
		for (int i = 1; i <= 1000000; i++) {
			int t = i;
			int[] pres = new int[10];
			while (t > 0) {
				pres[t % 10]++;
				t /= 10;
			}
			int kcount = 0;
			for (int k = 1; k <= 9; k++) {
				if (i % k == 0 && pres[k] > 0) {
					kcount++;
					sumk[k]++;
				}
			}
			for (int j = 0; j < 10; j++) {
				x[i][j] += x[i-1][j];
			}
			for (int j = 0; j <= kcount; j++) {
				sum[j]++;
				x[i][j]++;
			}
			sumx[kcount]++;
			//			System.out.println(i + "\t" + kcount + "\t" + Arrays.toString(sum) + "\t" + Arrays.toString(sumx) + "\t" + Arrays.toString(sumk));
		}
		for (int i = 0; i < q.length; i++) {
			int a = q[i][0];
			int b = q[i][1];
			int k = q[i][2];
			System.out.println(x[b][k] - x[a-1][k]);
		}

	}
	
	public static void ALLPOLY() {
		int T = in.nextInt();
		for (int t = 0; t < T; t++) {
			int N = in.nextInt();
			ArrayList<Point> p = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				p.add(new Point(in.nextInt(), in.nextInt()));
			}
			System.out.println(ALLPOLY(N, p));
		}
	}
	
	public static double ALLPOLY(int N, ArrayList<Point> poly1) {
		ArrayList<Point> points = new ArrayList<>();
		
		// Add all points inside, and find all intersections
		
		for (int i = 0; i < poly1.size(); i++) {
			for (int j = 0; j < poly1.size(); j++) {
				Point p = new Point();
				if (intersection(poly1.get(i), poly1.get((i+1) % N), poly1.get(j), poly1.get((j+1) % N), p) == 0) {
					if ((pointOnSegment(poly1.get(i), poly1.get((i+1) % N), p) == 1) && 
							(pointOnSegment(poly1.get(j), poly1.get((j+1) % N), p) == 1)) {
						points.add(p);
					}
				}
			}
		}
		double area = area(points);
		return area / (20000001L * 20000001L);
	}



	public static final int INF = 1000000000;
	
	public static void prefCHSGMNTS() {
		for (int t = 0; t < 3; t++) {
			long t1 = System.currentTimeMillis();
			for (int t2 = 0; t2 < 5; t2++) {
				int N = 1000;
				int[] a = new int[N];
				for (int i = 0; i < a.length; i++) {
					a[i] = ra.nextInt(10000);
				}
				CHSGMNTS(N, a);
			}
			System.out.println(System.currentTimeMillis() - t1);
		}
		
		for (int t = 0; t < 10000; t++) {
			int N = ra.nextInt(50);
			int[] a = new int[N];
			for (int i = 0; i < a.length; i++) {
				a[i] = ra.nextInt(N*5)+1;
			}
			long r1 = CHSGMNTS(N, a);
			long r2 = slowCHSGMNTS(N, a);
			if (r1 != r2) {
				System.out.println("test " + t + "\t " +CHSGMNTS(N, a) +" != " + slowCHSGMNTS(N, a));
				break;
			}
			if (t == 9999) {
				System.out.println("no bugs found!");
			}
		}
		
	}
	
	public static void CHSGMNTS() {
		int T = in.nextInt();
		for (int test = 0; test < T; test++) {
			int N = in.nextInt();
			int[] a = in.nextIntArray(N);
			System.out.println(CHSGMNTS(N, a));
//			System.out.println(slowCHSGMNTS(N, a));
//			System.out.println(slowCHSGMNTS2(N, a));
		}
	}
	
	public static long CHSGMNTS(int N, int[] xin) {
		// compression
		ArrayList<int[]> comp = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			comp.add(new int[]{xin[i], i, 0});
		}
		int[] x = new int[N];
		int xMax = 0;
		Collections.sort(comp, (int[] a, int[] y) -> Integer.compare(a[0], y[0]));
		int t = -1;
		for (int i = 0; i < x.length; i++) {
			if (i == 0) {
				t++;
			} else if (comp.get(i)[0] != comp.get(i-1)[0]) {
				t++;
			}
			comp.get(i)[2] = t;
			x[comp.get(i)[1]] = t;
			xMax = t;
		}
		xMax++;
		
		// STree
		int[][] tdist = new int[N][xMax];
		for (int i = N-1; i >= 0; i--) {
			for (int j = 0; j < xMax; j++) {
				if (i == N-1) {
					tdist[i][j] = INF;
				} else {
					tdist[i][j] = tdist[i+1][j] + 1;
				}
			}
			tdist[i][x[i]] = 0;
		}

		int[][] dist = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				dist[i][j] = tdist[i][x[j]];
			}
		}
//		int[][] skip = new int[N][N];
//		for (int j = 0; j < N; j++) {
//			int tt = 0;
//			for (int i = N-1; i >= 0; i--) {
//				if (dist[j][i] == 0) {
//					tt = 0;
//				} else {
//					tt++;
//				}
//				skip[j][i] = tt;
//			}
//		}
//		SegmentTree[] st = new SegmentTree[N];
//		for (int i = 0; i < st.length; i++) {
//			st[i] = new SegmentTree(dist[i]);
//		}
		
		long num = 0;
		for (int a = 0; a < N; a++) {
			int[] set = new int[N];
			int[] minX = new int[N];
			Arrays.fill(minX, INF);
			minX[a] = dist[a][a];
			for (int i = 0; i < minX.length; i++) {
				minX[i] = dist[i][a];
			}
			
			for (int b = a; b < N; b++) {
				set[x[b]]++;
				for (int c = b+1; c < N; c++) {
					if ((dist[c][b] > minX[c] || dist[c][b] >= INF)) {
						if (minX[c] > 0) {
							c += minX[c];
						} else if (minX[c] < 0) {
							c += -minX[c]-1;
						}
					} else {
						minX[c] = Math.min(minX[c], dist[c][b]);
						int e = c;
						while (e >= 0 && e < N-1 && minX[e] <= 0 && minX[e+1] <= 0) {
							minX[e] = minX[e+1]-1;
							e--;
						}
					}		
				
					if (c < N && minX[c] < 0) {
						c += -minX[c]-1;
					}
				}
				for (int c = b+1; c < N; c++) {
					int notFound = 0;
					//if (notFound > 40) {
					int minDupDist = 0;
//					minDupDist = st[c].get(a, b);
					//minDupDist = minDupDist2;
					int minDupDist2 = minX[c];
//					if (minDupDist < INF && minDupDist2 < INF && Math.abs(minDupDist) != Math.abs(minDupDist2)) {
//						System.currentTimeMillis();
//					}
					minDupDist = minDupDist2;
					if (minDupDist >= INF) {
						int s = N-c;
						num += s*(s+1)/2 - notFound;
						c = N;
					} else {
						if (minDupDist >= 0) {
							int s = minDupDist;
							num += s*(s+1)/2 - notFound;
							c += minDupDist;
						} else {
							c += -minDupDist;
						}
					}
					notFound = 0;
				}
			}
		}
		return num;
	}
	
	public static class SegmentTree {
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
		protected int IDENTITY = INF;

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
	
	public static long slowCHSGMNTS(int N, int[] x) {
		long num = 0;
		for (int a = 0; a < N; a++) {
			for (int b = a; b < N; b++) {
				Counter<Integer> set = new Counter<>();
				for (int i = a; i <= b; i++) {
					set.add(x[i]);	
				}
				for (int c = b+1; c < N; c++) {
					for (int d = c; d < N; d++) {
						if (!set.containsKey(x[d])) {
//							System.out.println((a+1) + " " + (b+1) + ", " + (c+1) + " " + (d+1));
							num++;
						} else {
							int s = d-c;
							num += s*(s-1)/2;
							c = d;
							d = N;
							break;
						}
					}
				}
			}
		}
		return num;
	}
	
	public static long slowCHSGMNTS2(int N, int[] x) {
		long num = 0;
		for (int a = 0; a < N; a++) {
			for (int b = a; b < N; b++) {
				for (int c = b+1; c < N; c++) {
					for (int d = c; d < N; d++) {
						HashSet<Integer> set = new HashSet<>();
						for (int i = a; i <= b; i++) {
							set.add(x[i]);
						}
						boolean valid = true;
						for (int i = c; i <= d; i++) {
							if (set.contains(x[i])) {
								valid = false;
								break;
							}
						}
						if (valid) {
//							System.out.println((a+1) + " " + (b+1) + ", " + (c+1) + " " + (d+1));
							num++;
						}
					}
				}
			}
		}
		return num;
	}

	public static void CHEFARC() {
		int T = in.nextInt();
		for (int test = 0; test < T; test++) {
			int N = in.nextInt();
			int M = in.nextInt();
			int K1 = in.nextInt();
			int K2 = in.nextInt();
			boolean[][] closed = new boolean[N][M];
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < M; x++) {
					int t = in.nextInt();
					if (t == 1) {
						closed[y][x] = true;
					}
				}
			}
			System.out.println(CHEFARC(N, M, K1, K2, closed));
		}
	}

	static int[] dy = {0, 1, 0, -1};
	static int[] dx = {1, 0, -1, 0};
	static int MV = 1000000;
	
	public static int CHEFARC(int N, int M, int K1, int K2, boolean[][] closed) {
		int[][] d1 = new int[N][M];
		int[][] d2 = new int[N][M];
		
		walk(0, 0, d1, closed, K1, N, M);
		walk(M-1, 0, d2, closed, K2, N, M);
		
		int min = MV;
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < M; x++) {
				min = Math.min(min, Math.max(d1[y][x], d2[y][x]));
			}
		}
		if (min >= MV) {
			min = -1;
		}
		return min;
	}

	public static void walk(int ox, int oy, int[][] d1, boolean[][] closed, int K, int N, int M) {
		for (int i = 0; i < d1.length; i++) {
			Arrays.fill(d1[i], MV);
		}
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[]{ox, oy, 0});
		int visited = 0;
		while (!q.isEmpty()) {
			int[] top = q.poll();
			int x = top[0];
			int y = top[1];
			int steps = top[2];
			if (steps >= d1[y][x]) {
				continue;
			}
			visited++;
			d1[y][x] = steps;
			if (visited == N*M) {
				break;
			}
			for (int ny = 0; ny < N; ny++) {
				int manY = Math.abs(ny - y);
				int xk = K - manY;
				if (manY <= K) {
					for (int nx = 0; nx < M; nx++) {
						if (Math.abs(nx - x) <= xk && !closed[ny][nx] && steps+1 < d1[ny][nx]) {
							q.add(new int[]{nx, ny, steps+1});
						}
					}
				}
			}
		}
	}

	public static void CHEFTET() {
		int T = in.nextInt();
		for (int test = 0; test < T; test++) {
			int N = in.nextInt();
			int[] b = in.nextIntArray(N);
			int[] a = in.nextIntArray(N);
			System.out.println(CHEFTET(N, a, b));
		}
	}
	
	public static long CHEFTET(int N, int[] a, int[] b) {
		if (N == 1) {
			return a[0] + b[0];
		}
		long max = tryCHEFTET(N, a, b, a[0]);
		max = Math.max(max, tryCHEFTET(N, a, b, a[0] + b[0]));
		max = Math.max(max, tryCHEFTET(N, a, b, a[0] + b[1]));
		max = Math.max(max, tryCHEFTET(N, a, b, a[0] + b[0] + b[1]));
		return max;
	}

	public static long tryCHEFTET(int N, int[] ain, int[] bin, int target) {
		int[] a = ain.clone();
		int[] b = bin.clone();
		for (int i = 0; i < b.length; i++) {
			boolean valid = false;
			for (int j = i-1; j <= i+1; j++) {
				if (j >= 0 && j < N) {
					if (b[i] > 0 && b[i] + a[j] == target) {
						a[j] += b[i];
						b[i] = 0;
						valid = true;
						break;
					}
				}
			}
			if (!valid) {
				if (i > 0 && a[i-1] != target) {
					return -1;
				}
				if (i == N-1) {
					return -1;
				}
				if (b[i+1] + b[i] + a[i] == target) {
					a[i] += b[i];
					b[i] = 0;
				} else {
					a[i+1] += b[i];
					b[i] = 0;
				}
			}
		}
		for (int i = 0; i < b.length; i++) {
			if (a[i] != target || b[i] != 0) {
				return -1;
			}
		}
		return (long)target;
	}

	public static void CHEFELEC() {
		int T = in.nextInt();
		for (int test = 0; test < T; test++) {
			int N = in.nextInt();
			char[] st = in.next().toCharArray();
			boolean[] power = new boolean[st.length];
			for (int i = 0; i < power.length; i++) {
				if (st[i] == '1') {
					power[i] = true;
				}
			}
			long[] x = in.nextLongArray(N);
			long[] leftE = new long[N];
			long[] rightE = new long[N];
			Arrays.fill(leftE, Integer.MAX_VALUE);
			Arrays.fill(rightE, Integer.MAX_VALUE);
			long dist = Integer.MAX_VALUE;
			int firstPow = -1;
			int lastPow = -1;
			for (int i = 0; i < leftE.length; i++) {
				if (power[i]) {
					lastPow = i;
					dist = 0;
				} else {
					if (i > 0) {
						dist += x[i] - x[i-1];
					}
				}
				leftE[i] = dist;
			}
			dist = Integer.MAX_VALUE;
			for (int i = leftE.length-1; i >= 0; i--) {
				if (power[i]) {
					firstPow = i;
					dist = 0;
				} else {
					if (i < N-1) {
						dist += x[i+1] - x[i];
					}
				}
				rightE[i] = dist;
			}
			long cost = rightE[0] + leftE[N-1];
			long segmentCost = Integer.MAX_VALUE;
			boolean active = false;
			for (int i = firstPow; i <= lastPow; i++) {
				if (!power[i]) {
					active = true;
					segmentCost = Math.min(segmentCost, leftE[i-1] + rightE[i]);
				} else {
					if (active) {
						segmentCost = Math.min(segmentCost, leftE[i-1] + rightE[i]);
						cost += segmentCost;
						active = false;
						segmentCost = Integer.MAX_VALUE;
					}
				}
			}
			System.out.println(cost);
		}
	}
	
	public static void EGRANDR() {
		int T = in.nextInt();
		for (int test = 0; test < T; test++) {
			int N = in.nextInt();
			int[] a = in.nextIntArray(N);
			int avg = 0;
			int min = 5;
			int numA = 0;
			for (int i = 0; i < a.length; i++) {
				avg += a[i];
				min = Math.min(min, a[i]);
				if (a[i] == 5) {
					numA++;
				}
			}
			if (numA >= 1 && min > 2 && avg >= N*4) {
				System.out.println("Yes");
			} else {
				System.out.println("No");
			}
		}
	}
	
	static final double PI = Math.PI;
	static final double EPS = 1e-9;

	
	/***************************************************************************
	 * Convex Hull
	 **************************************************************************/

	/**
	 * Which side is p3 to the line p1->p2? returns: 1 left, 0 on, -1 right
	 */
	static int sideSign(Point p1, Point p2, Point p3) {
		double sg = (p1.x - p3.x) * (p2.y - p3.y) - (p1.y - p3.y) * (p2.x - p3.x);
		if (Math.abs(sg) < EPS) return 0;
		if (sg > 0) return 1;
		return -1;
	}

	/**
	 * used by convex hull: from p3, if p1 is better than p2
	 */
	static boolean better(Point p1, Point p2, Point p3) {
		double sg = (p1.y - p3.y) * (p2.x - p3.x) - (p1.x - p3.x) * (p2.y - p3.y);
		// watch range of the numbers
		if (Math.abs(sg) < EPS) {
			return dist(p3, p1) > dist(p3, p2);
		}
		return sg < 0;
	}

	/**
	 * Convex hull (Graham scan)
	 * vin is modified
	 */
	static void convexHull(ArrayList<Point> vin, ArrayList<Point> vout)	{
		int n = vin.size();
		Collections.sort(vin);
		Point[] stk = new Point[n];
		int pstk, i;
		// hopefully more than 2 points
		stk[0] = vin.get(0);
		stk[1] = vin.get(1);
		pstk = 2;
		for (i = 2; i < n; i++) {
			if (dist(vin.get(i), vin.get(i - 1)) < EPS) continue;
			while (pstk > 1 && better(vin.get(i), stk[pstk - 1], stk[pstk - 2]))
				pstk--;
			stk[pstk] = vin.get(i);
			pstk++;
		}
		for (i = 0; i < pstk; i++) vout.add(new Point(stk[i]));
		
		// turn 180 degree
		for (i = 0; i < n; i++) {
			vin.get(i).y = -vin.get(i).y;
			vin.get(i).x = -vin.get(i).x;
		}
		Collections.sort(vin);
		stk[0] = vin.get(0);
		stk[1] = vin.get(1);
		pstk = 2;
		for (i = 2; i < n; i++) {
			if (dist(vin.get(i), vin.get(i - 1)) < EPS) continue;
			while (pstk > 1 && better(vin.get(i), stk[pstk - 1], stk[pstk - 2]))
				pstk--;
			stk[pstk] = vin.get(i);
			pstk++;
		}
		for (i = 1; i < pstk - 1; i++) {
			stk[i].x = -stk[i].x; // don’t forget rotate 180 d back.
			stk[i].y = -stk[i].y;
			vout.add(stk[i]);
		}
	}

	/**
	 * Test whether a simple polygon is convex
	 * return 0 if not convex, 1 if strictly convex,
	 * 2 if convex but there are unnecessary points 
	 * this function does not work if the polygon is self intersecting
	 * in that case, compute the convex hull of v, and see if both have the same area
	 */
	static int isConvex(ArrayList<Point> v) {
		int i, j, k;
		int c1 = 0;
		int c2 = 0;
		int c0 = 0;
		int n = v.size();
		for (i = 0; i < n; i++) {
			j = (i + 1) % n;
			k = (j + 1) % n;
			int s = sideSign(v.get(i), v.get(j), v.get(k));
			if (s == 0) c0++;
			if (s > 0) c1++;
			if (s < 0) c2++;
		}
		if (c1 != 0 && c2 != 0) return 0;
		if (c0 != 0) return 2;
		return 1;
	}

	/***************************************************************************
	 * Areas
	 **************************************************************************/
	static double trap(Point a, Point b) {
		return (0.5 * (b.x - a.x) * (b.y + a.y));
	}

	/**
	 * Area of any simple polygon, not necessarily convex.
	 */
	static double area(ArrayList<Point> vin) {
		int n = vin.size();
		double ret = 0.0;
		for (int i = 0; i < n; i++)
			ret += trap(vin.get(i), vin.get((i + 1) % n));
		return Math.abs(ret);
	}

	/**
	 * Area of any simple polygon, not necessarily convex.
	 */
	static double perimeter(ArrayList<Point> vin) {
		int n = vin.size();
		double ret = 0.0;
		for (int i = 0; i < n; i++)
			ret += dist(vin.get(i), vin.get((i + 1) % n));
		return ret;
	}
	
	/**
	 * Area of any simple triangle. Faster than the general area method.
	 */
	static double triarea(Point a, Point b, Point c) {
		return Math.abs(trap(a, b) + trap(b, c) + trap(c, a));
	}

	/**
	 * Height from a to the line bc
	 */
	static double height(Point a, Point b, Point c) {
		double s3 = dist(c, b);
		double ar = triarea(a, b, c);
		return (2.0 * ar / s3);
	}

	/***************************************************************************
	 * Points and Lines
	 **************************************************************************/
	
	/**
	 * Two lines given by p1->p2, p3->p4
	 * r is the intersection point
	 * return -1 if two lines are parallel
	 */
	static int intersection(Point p1, Point p2, Point p3, Point p4, Point r) {
		double d = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);
		if (Math.abs(d) < EPS) return -1;
		// might need to do something special!!!
		double ua;
		ua = (p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x);
		ua /= d;
		// ub = (p2.x - p1.x)*(p1.y-p3.y) - (p2.y-p1.y)*(p1.x-p3.x);
		//ub /= d;
		r.assign(p1.add((p2.subtract(p1)).multiply(ua)));
		return 0;
	}

	/**
	 * The closest point on the line p1->p2 to p3
	 */
	static void closestPoint(Point p1, Point p2, Point p3, Point r) {
		if (Math.abs(triarea(p1, p2, p3)) < EPS) {
			r.assign(p3);
			return;
		}
		Point v = p2.subtract(p1);
		v.normalize();
		double pr; // inner product
		pr = (p3.y - p1.y) * v.y + (p3.x - p1.x) * v.x;
		r.assign(p1.add(v.multiply(pr)));
	}

	/**
	 * Get the orthocenter, or the point where the altitudes intersect.
	 * An altitude of a triangle is a line segment through a vertex
	 * and perpendicular to (i.e. forming a right angle with) a line
	 */
	static int orthocenter(Point p1, Point p2, Point p3, Point r) {
		if (triarea(p1, p2, p3) < EPS) return -1;
		Point a1 = new Point();
		Point a2 = new Point();
		closestPoint(p2, p3, p1, a1);
		closestPoint(p1, p3, p2, a2);
		intersection(p1, a1, p2, a2, r);
		return 0;
	}

	/**
	 * The circumcenter of a triangle is the center of the circumscribed circle:
	 * a circle that passes through all the vertices of the polygon.
	 */
	static int circumcenter(Point p1, Point p2, Point p3, Point r) {
		if (triarea(p1, p2, p3) < EPS) return -1;
		Point a1 = new Point();
		Point a2 = new Point();
		Point b1 = new Point();
		Point b2 = new Point();
		a1.assign((p2.add(p3)).multiply(0.5));
		a2.assign((p1.add(p3)).multiply(0.5));
		b1.x = a1.x - (p3.y - p2.y);
		b1.y = a1.y + (p3.x - p2.x);
		b2.x = a2.x - (p3.y - p1.y);
		b2.y = a2.y + (p3.x - p1.x);
		intersection(a1, b1, a2, b2, r);
		return 0;
	}

	/**
	 * Angle bisection
	 */
	static int bcenter(Point p1, Point p2, Point p3, Point r) {

		if (triarea(p1, p2, p3) < EPS) return -1;
		double s1, s2, s3;
		s1 = dist(p2, p3);
		s2 = dist(p1, p3);
		s3 = dist(p1, p2);
		double rt = s2 / (s2 + s3);
		Point a1, a2;
		a1 = p2.multiply(rt).add(p3.multiply(1.0 - rt));
		rt = s1 / (s1 + s3);
		a2 = p1.multiply(rt).add(p3.multiply(1.0 - rt));
		intersection(a1, p1, a2, p2, r);
		return 0;
	}

	/***************************************************************************
	 * Angles
	 **************************************************************************/
	
	/**
	 * Angle from p1->p2 to p1->p3, returns -PI to PI
	 */
	static double angle(Point p1, Point p2, Point p3) {
		Point va = p2.subtract(p1);
		va.normalize();
		Point vb = new Point(va.y, va.x);
		Point v = p3.subtract(p1);
		double x, y;
		x = dot(v, va);
		y = dot(v, vb);
		return Math.atan2(y, x);
	}
	
	/**
	 * Angle from p1->p2 to p1->p3, returns -180 to 180
	 */
	static double angleDeg(Point p1, Point p2, Point p3) {
		return angle(p1, p2, p3) * 180.0 / PI;
	}
	
	/**
	 * Angle from p1->p2 to p1->p3, meaning how far you have to travel from p2 to p3.
	 * This function doesn't ignore the sign of the angle like angleDeg does.
	 */
	static double angleDeg2(Point p1, Point p2, Point p3) {
		Point va = p2.subtract(p1);
		Point vb = p3.subtract(p1);
		double b = Math.atan2(vb.y, vb.x);
		double a = Math.atan2(va.y, va.x);
		double a312 = b -a;	
		if (a312 < -PI/2) {
			a312 += 2*PI;
		}
		return a312 * 180.0 / PI;
	}

	/**
	 * In a triangle with sides a,b,c, the angle between b and c
	 * we do not check if a,b,c is a triangle here.
	 */
	static double angle(double a, double b, double c) {
		double cs = (b * b + c * c - a * a) / (2.0 * b * c);
		return Math.acos(cs);
	}

	/**
	 * Rotate p1 around p0 clockwise, by angle a
	 * Don’t pass by reference for p1, so r and p1 can be the same
	 */
	static void rotate(Point p0, Point p1, double a, Point r) {
		Point temp = p1.subtract(p0);
		r.x = Math.cos(a) * temp.x - Math.sin(a) * temp.y;
		r.y = Math.sin(a) * temp.x + Math.cos(a) * temp.y;
		r.assign(r.add(p0));
	}

	/**
	 * Reflect p3 around the p1->p2 line, return as r
	 */
	static void reflect(Point p1, Point p2, Point p3, Point r)	{
		if (dist(p1, p3) < EPS) {
			r = p3;
			return;
		}
		double a = angle(p1, p2, p3);
		r.assign(p3);
		rotate(p1, r, -2.0 * a, r);
	}

	/***************************************************************************
	 * Points, lines, and circles
	 **************************************************************************/

	/**
	 * Returns:
	 * 1 if the point is on the segment p1->p2;
	 * -1 if on the line but not on the segment
	 * 0 if not on the line;
	 */
	static int pointOnSegment(Point p1, Point p2, Point p)	{
		double s = triarea(p, p1, p2);
		if (s > EPS) return (0);
		double sg = (p.x - p1.x) * (p.x - p2.x);
		if (sg > EPS) return (-1);
		sg = (p.y - p1.y) * (p.y - p2.y);
		if (sg > EPS) return (-1);
		return 1;
	}

	/**
	 * Returns the number of intersections between a circle and a line: 0 - 2
	 */
	static int lineCircleIntersect(Point oo, double r, Point p1, Point p2, Point r1, Point r2) {
		Point m = new Point();
		closestPoint(p1, p2, oo, m);
		Point v = p2.subtract(p1);
		v.normalize();
		double r0 = dist(oo, m);
		if (r0 > r + EPS) return -1;
		if (Math.abs(r0 - r) < EPS) {
			r1.assign(m);
			r2.assign(m);
			return 1;
		}
		double dd = Math.sqrt(r * r - r0 * r0);
		r1.assign(m.subtract(v.multiply(dd)));
		r2.assign(m.subtract(v.multiply(dd)));
		return 0;
	}
	
	/**
	 * Returns the number of intersections between a circle and another circle: 0 - 2
	 * -1 if no intersection or infinite intersection
	 */
	static int circleCircleIntersect(Point o1, double r1, Point o2, double r2, Point q1, Point q2)	{
		double r = dist(o1, o2);
		if (r1 < r2) {
			swap(o1, o2);
			double temp = r1;
			r1 = r2;
			r2 = temp;
		}
		if (r < EPS) return (-1);
		if (r > r1 + r2 + EPS) return (-1);
		if (r < r1 - r2 - EPS) return (-1);
		Point v = o2.subtract(o1);
		v.normalize();
		q1 = o1.add(v.multiply(r1));
		if (Math.abs(r - r1 - r2) < EPS || Math.abs(r + r2 - r1) < EPS) {
			q2.assign(q1);
			return (1);
		}
		double a = angle(r2, r, r1);
		q2.assign(q1);
		rotate(o1, q1, a, q1);
		rotate(o1, q2, -a, q2);
		return 0;
	}

	/**
	 * Returns 1 if the point is in the polygon; 0 if outside; -1 if on the polygon 
	 */
	static int pointInPolygon(ArrayList<Point> poly, Point pin) {
		Point p = new Point(pin);
		ArrayList<Point> pv = new ArrayList<>();
		for (int i = 0; i < poly.size(); i++) {
			pv.add(new Point(poly.get(i)));
		}
		
		int i, j;
		int n = pv.size();
		pv.add(pv.get(0));
		for (i = 0; i < n; i++)
			if (pointOnSegment(pv.get(i), pv.get(i + 1), p) == 1) return (-1);
		for (i = 0; i < n; i++)
			pv.get(i).assign(pv.get(i).subtract(p));
		p.x = p.y = 0.0;
		double a, y;
		while (true) {
			a = Math.random() * 3;
			j = 0;
			for (i = 0; i < n; i++) {
				rotate(p, pv.get(i), a, pv.get(i));
				if (Math.abs(pv.get(i).x) < EPS) j = 1;
			}
			if (j == 0) {
				pv.get(n).assign(pv.get(0));
				j = 0;
				for (i = 0; i < n; i++)
					if (pv.get(i).x * pv.get(i + 1).x < -EPS) {
						y = pv.get(i + 1).y - pv.get(i + 1).x * (pv.get(i).y - pv.get(i + 1).y) / (pv.get(i).x - pv.get(i + 1).x);
						if (y > 0) j++;
					}
				return (j % 2);
			}
		}
	}

	/**
	 * Cut a convex polygon along the line p1->p2. pol1 is the resulting polygon on the left, pol2 on the right. 
	 */
	static void cutPoly(ArrayList<Point> pol, Point p1, Point p2, ArrayList<Point> pol1, ArrayList<Point> pol2) {
		ArrayList<Point> pp = new ArrayList<>();
		ArrayList<Point> pn = new ArrayList<>();
		pp.clear();
		pn.clear();
		int i, sg, n = pol.size();
		Point q1 = new Point();
		Point q2 = new Point();
		Point r = new Point();
		for (i = 0; i < n; i++) {
			q1.assign(pol.get(i));
			q2.assign(pol.get((i + 1) % n));
			sg = sideSign(p1, p2, q1);
			if (sg >= 0) pp.add(q1);
			if (sg <= 0) pn.add(q1);
			if (intersection(p1, p2, q1, q2, r) >= 0) {
				if (pointOnSegment(q1, q2, r) == 1) {
					pp.add(r);
					pn.add(r);
				}
			}
		}
		pol1.clear();
		pol2.clear();
		if (pp.size() > 2) convexHull(pp, pol1);
		if (pn.size() > 2) convexHull(pn, pol2);
	}

	public static class Point implements Comparable<Point> {
		public double x, y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Point(Point p) {
			this.x = p.x;
			this.y = p.y;
		}

		public Point() {
			this.x = 0;
			this.y = 0;
		}

		double length() {
			return Math.sqrt(x * x + y * y);
		}

		/**
		 * Normalise the vector to unit length; return -1 if the vector's length is 0.
		 */
		int normalize() {
			double l = length();
			if (Math.abs(l) < EPS) return -1;
			x /= l;
			y /= l;
			return 0;
		}

		Point subtract(Point p) {
			return new Point(x - p.x, y - p.y);
		}

		Point add(Point p) {
			return new Point(x + p.x, y + p.y);
		}

		Point multiply(double scalar) {
			return new Point(x * scalar, y * scalar);
		}

		void assign(Point p) {
			x = p.x;
			y = p.y;
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}

		public int compareTo(Point p) {
			if (Math.abs(x - p.x) < EPS) return Double.compare(y, p.y);
			return Double.compare(x, p.x);
		}
	}

	static void swap(Point a, Point b) {
		Point t = new Point(a);
		a.assign(b);
		b.assign(t);
	}

	/**
	 * The distance between two points
	 */
	static double dist(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	/**
	 * the inner product of two vectors
	 */
	static double dot(Point a, Point b) {
		return (a.x * b.x + a.y * b.y);
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
		public void subtract(T key) {
			Integer i = this.get(key);
			this.put(key, i-1);
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
