// Long16_07
import java.io.*;
import java.util.*;
import java.math.*;

public class WORKCHEF {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		new Thread(null, new Runnable() {
			public void run() {
				WORKCHEF.run();
			}
		}, "1", 1 << 26).start(); // Set stack memory to 64 MB
	}

	public static void run() {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

//		perfPOLYEVAL();
//		POLYEVAL();
//		ALLPOLY();
//		perfWORKCHEF();
//		expWORKCHEF();
		WORKCHEF();
//		prefCHSGMNTS();
//		CHSGMNTS();
//		CHEFARC();
//		CHEFTET();
//		CHEFELEC();
//		EGRANDR();

		out.close();
	}

	static boolean debug = true;


	public static void perfWORKCHEF() {
		//    	WORKCHEF(0, 200000, 2);
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 3; i++) {
			long L = 1000000000000000000L;
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
