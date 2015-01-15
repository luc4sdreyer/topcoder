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


public class Solution {
	public static void main(String[] args) {
		//System.out.println(taumAndBday(System.in));
		//System.out.println(sherlockAndAnagrams(System.in));
		//System.out.println(aSuperHero(System.in));
		//System.out.println(swapsAndSum(System.in));
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
