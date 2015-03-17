import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class Weekly13 {
	public static void main(String[] args) {
		//System.out.println(taumAndBday(System.in));
		//System.out.println(sherlockAndAnagrams(System.in));
		//System.out.println(aSuperHero(System.in));
		System.out.println(swapsAndSum(System.in));
	}

	public static String swapsAndSum(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();
		int n = scan.nextInt();
		int q = scan.nextInt();
		long[] a = new long[n];
		int[][] queries = new int[q][3];
		for (int i = 0; i < a.length; i++) {
			a[i] = scan.nextInt();
		}
		for (int i = 0; i < q; i++) {
			for (int j = 0; j < 3; j++) {
				queries[i][j] = scan.nextInt();
			}
		}
		
		SegmentTree st = new SegmentTree(a);
		for (int i = 0; i < q; i++) {
			if (queries[i][0] == 1) {
				for (int j = queries[i][1]-1; j <= queries[i][2]-1; j+=2) {
					long temp = st.get(j, j);
					st.set(j, st.get(j+1, j+1));
					st.set(j+1, temp);
				}
			} else {
				sb.append(st.get(queries[i][1]-1, queries[i][2]-1));
				sb.append("\n");
			}
		}
		return sb.toString();
	}


	public static class SegmentTree {
		private long[][] t;
		private long[] a;
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
			return a+b;
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

		public void set(int x, long v) {
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
				res = function(res, t[i][h]);
				i += (1<<h);
			}
			while (i < j) {
				while (i+(1<<h) > j) h--;
				res = function(res, t[i][h]);
				i += (1<<h);
			}
			return res;
		}
	}

	public static String aSuperHero(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			int[][][] costReward = new int[n][m][2];
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < m; k++) {
					costReward[j][k][0] = scan.nextInt();
				}
			}
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < m; k++) {
					costReward[j][k][1] = scan.nextInt();
				}
			}
			sb.append(aSuperHero(n, m, costReward));
			sb.append("\n");
		}
		return sb.toString();
	}

	public static final int empty = 1000000;
	public static int aSuperHero(int n, int m, int[][][] costReward) {
		int[] dp = new int[1001];
		//HashMap<Integer, Integer> dp = new HashMap<>();
		for (int i = 0; i < n; i++) {
			//int[] dpNew = dp;
			int[] dpNew = new int[1001];
			Arrays.fill(dpNew, empty);
			Arrays.sort(costReward[i], new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return Integer.compare(o2[1], o1[1]);
				}
			});
			if (i == 0) {
				for (int k = 0; k < m; k++) {
					dpNew[costReward[i][k][1]] = Math.min(dpNew[costReward[i][k][1]], costReward[i][k][0]);
				}
			} else {
				for (int j = 0; j < dp.length; j++) {
					if (dp[j] != empty) {
						int reward = j;
						int extra = 0;
						for (int k = 0; k < m; k++) {
							extra = Math.max(0, costReward[i][k][0] - reward);
							dpNew[costReward[i][k][1]] = Math.min(dpNew[costReward[i][k][1]], dp[j] + extra);
						}
					}
				}
			}
			int min = Integer.MAX_VALUE;
			for (int j = dp.length-1; j >= 0; j--) {
				if (dp[j] < min) {
					min = dp[j];
				} else {
					dp[j] = 1000000;
				}
			}
			dp = dpNew;
			
		}
		int min = empty;
		for (int i = 0; i < dp.length; i++) {
			min = Math.min(min, dp[i]);
		}
		return min;
	}

	public static String sherlockAndAnagrams(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			String s = scan.next();
			sb.append(sherlockAndAnagrams(s.toCharArray()));
			sb.append("\n");
		}
		return sb.toString();
	}

	public static long sherlockAndAnagrams(char[] s) {
		HashSet<String> visited = new HashSet<>();
		BigInteger num = BigInteger.ZERO;
		for (int len = 1; len <= s.length; len++) {
			for (int start = 0; start < s.length - len+1; start++) {
				int pairs = 0;
				char[] t = new char[len];
				int xx = 0;
				for (int i = start; i < start+len; i++) {
					t[xx++] = s[i];
				}
				Arrays.sort(t);
				String current = new String(t);
				if (visited.contains(current)) {
					continue;
				}
				visited.add(current);
				int[] aFcount = new int[26];
				for (int i = 0; i < t.length; i++) {
					aFcount[t[i]-'a']++;
				}
				int[] bFcount = aFcount.clone();
				for (int i = start+1; i < s.length - len+1; i++) {
					bFcount[s[i-1]-'a']--;
					bFcount[s[i+len-1]-'a']++;
					if (Arrays.equals(aFcount, bFcount)) {
						if (pairs == 0) {
							pairs = 1;
						}
						pairs++;
					}
				}
				BigInteger pairs2 = combination(pairs, 2);
				num = num.add(pairs2);
			}
		}
		return num.longValue();
	}
	

	public static BigInteger combination(int n, int k) {
        return permutation(n,k).divide(factorial(k));
	}	
	
	public static BigInteger permutation(int n, int k) {
		BigInteger ret;
		if (k > n) {
			return BigInteger.ZERO;
		}
		BigInteger d = factorial((BigInteger.valueOf(n).subtract(BigInteger.valueOf(k))).intValue());
        ret = factorial(n).divide(d);
        return ret;
	}
	
	private static long fCacheSize = 1000000; // Used to speed up calls, but answers will be correct without it.
	private static HashMap<Integer,BigInteger> fCache = new HashMap<Integer,BigInteger>();	
	private static BigInteger factorial(int n)
    {
        BigInteger ret;
        
        if (n < 0) return BigInteger.ZERO;
        if (n == 0) return BigInteger.ONE;
        
        if (null != (ret = fCache.get(n))) return ret;
        else ret = BigInteger.ONE;
        for (int i = n; i >= 1; i--) {
        	if (fCache.containsKey(n)) return ret.multiply(fCache.get(n));
        	ret = ret.multiply(BigInteger.valueOf(i));
        }
        
        if (fCache.size() < fCacheSize) {
        	fCache.put(n, ret);
        }
        return ret;
    }
	

	public static String taumAndBday(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int b = scan.nextInt();
			int w = scan.nextInt();
			int x = scan.nextInt();
			int y = scan.nextInt();
			int z = scan.nextInt();
			sb.append(taumAndBday(b, w, x, y, z));
			sb.append("\n");
		}
		return sb.toString();
	}

	public static long taumAndBday(long b, long w, long x, long y, long z) {
		return Math.min(x, y + z) * b + Math.min(y, x + z) * w;
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
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
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
