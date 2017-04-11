import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Long16_05 {
	public static void main(String[] args) {
//		LADDU(System.in);
//		CHBLLS(System.in);
//		FORESTGA(System.in);
//		testFORESTGA();
//		CHEFSOC2(System.in);
//		SEAGCD2(System.in);
//		testSEAGCD2();
//		testCHEFMATH();
		CHEFMATH(System.in);
//		testCHEFMATH_timing();
//		testCHEFMATH_timing();
//		System.out.println(getPrimes(100).cardinality());
	}

	public static void testSEAGCD2() {
		SEAGCD2_slow(5,2);
		for (int n = 1; n <= 5; n++) {
			for (int m = 1; m <= 5; m++) {
				System.out.println("n = " + n + "\t m = " + m + "\t slow = " + SEAGCD2_slow(n,m) + "\t fast1 = " + SEAGCD2(n,m) + "\t fast2 = " + SEAGCD2_2(n,m));
			}
		}
	}

	static final int mapSize = 6;
	static HashMap<Integer, int[]> map;
	
	public static void SEAGCD2(InputStream in) {
		System.out.println(SEAGCD2_2(2, 2) + "\t= 3");
		System.out.println(SEAGCD2_2(3, 3) + "\t= 13");
		System.out.println(SEAGCD2_2(10, 10) + "\t= 9990174303");
		System.out.println(SEAGCD2_2(5, 2) + "\t= 31");
		System.out.println(SEAGCD2_2(2, 5) + "\t= 19");
		
		System.out.println();
		System.out.println(SEAGCD2(2, 2) + "\t= 3");
		System.out.println(SEAGCD2(3, 3) + "\t= 13");
		System.out.println(SEAGCD2(10, 10) + "\t= 9990174303");
		System.out.println(SEAGCD2(5, 2) + "\t= 31");
		System.out.println(SEAGCD2(2, 5) + "\t= 19");
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			System.out.println(SEAGCD2_2(n, m));
		}
	}
	
	public static int[] mobius;

	public static long SEAGCD2_2(int n, int m) {
		if (mobius == null) {
			int limit = 100000;
			BitSet primes = getPrimes(limit);
			mobius = mobiusFunction(limit+1, primes); 
		}
		//long mod = 1000000007;
		long mod = 1000000000000000007L;
		
		long sum = 0;
		for (int i = 1; i <= m; i++) {
			sum = (sum + (mod + mobius[i] * fastModularExponent(m/i, n, mod) % mod)) % mod;
		}
		
		return sum;
	}

	public static long SEAGCD2_slow(int n, int m) {
		int[] y = new int[n];
		int num = 0;
		do {
			HashSet<Integer> allFactors = new HashSet<>();
			boolean valid = true;
			for (int i = 0; i < y.length && valid; i++) {
				int x = y[i] + 1;
				int j = 2;
				while (x > 1) {
					if (x % j == 0) {
						x /= j;
						if (allFactors.contains(j)) {
							valid = false;
							x = 0;
							break;
						}
						allFactors.add(j);
						while (x % j == 0) {
							x /= j;
						}
					}
					j++;
				}
			}
			
			if (valid) {
				num++;
			}
		} while (next_number(y, m));
		
		return num;
	}
	
	public static int fastModularExponent(int a, int exp, long mod) {
		long[] results = new long[65];
		long m = mod;
		int power = 1;
		long res = 1;
		while (exp > 0) {
			if (power == 1) {
				results[power] = a % m;
			} else {
				results[power] = (results[power-1] * results[power-1]) % m;
			}
			if (exp % 2 == 1) {
				res = (res * results[power]) % m;
			}
			exp /= 2;
			power++;
		}
		return (int) (res % m);
	}

	public static int[] mobiusFunction(int N, BitSet primes) {
		int[] mobius = new int[N];
		mobius[1] = 1;
		for (int i = 2; i < N; i++) {
			if (primes.get(i)) {
				mobius[i] = -1;
				continue;
			}
			int numPrimes = 0;
			int x = i;
			boolean isSet = false;
			int j = 2;
			while (x > 1) {
				int primeHits = 0;
				while (x % j == 0) {
					x /= j;
					primeHits++;
					numPrimes++;
				}
				if (primeHits > 1) {
					mobius[i] = 0;
					isSet = true;
					break;
				}
				j++;
			}
			if (!isSet) {
				if (numPrimes % 2 == 0) {
					mobius[i] = 1;
				} else {
					mobius[i] = -1;
				}
			}
		}
		
		return mobius;
	}

	public static long SEAGCD2(int n, int m) {
		long[][] phi = new long[m+1][m+1];
		long[][] f = new long[n+1][m+1];
		//long mod = 1000000007;
		long mod = 1000000000000000007L;
		
		phi[1][1] = 1;
		for (int i = 1; i < f[0].length; i++) {
			for (int j = 1; j < phi[0].length; j++) {
				if (j % i == 0) {
					phi[i][j] = phi[i][j-1] + 1;
				} else {
					phi[i][j] = phi[i][j-1];
				}
			}
		}
		
		for (int j = 1; j < f[0].length; j++) {
			f[1][j] = j;
		}
		for (int i = 1; i < f.length; i++) {
			f[i][1] = 1;
		}

		for (int N = 2; N < f.length; N++) {
			for (int M = 1; M < f[0].length; M++) {
				for (int K = 1; K <= M; K++) {
					f[N][M] = f[N][M] + (phi[K][M] * f[N-1][M/K] % mod) % mod;
					if (N-1 < 1 || M/K < 1) {
						System.out.println("fail");
					}
				}	
			}
		}
		
		return f[n][m];
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
	
	public static void testCHEFMATH3() {
		int a = 1;
		int b = 1;
		int[] f = new int[50];
		int k2 = 0;
		while (b <= 1000000000) {
			f[k2++] += b;
			int old = b;
			b += a;
			a = old;
		}
		f = Arrays.copyOf(f, k2);
		for (int k = 1; k <= 8; k++) {
			for (int i = 1; i < f.length; i++) {
				System.out.print(f[i] + "\tk: " + k + "\t");
				CHEFMATH(f[i], k);
			}
			System.out.println();
		}
	}
	
	public static void testCHEFMATH() {
		int a = 1;
		int b = 1;
		int[] f = new int[50];
		int k2 = 0;
		while (b <= 30) {
			f[k2++] += b;
			int old = b;
			b += a;
			a = old;
		}
		f = Arrays.copyOf(f, k2);
		for (int i = 1; i < 1000000000; i*=2) {
			
			for (int k = 9; k <= 9; k++) {
				
				long r1 = CHEFMATH_slow2(i, k);
//				long r2 = CHEFMATH_slow2(f[i], k);
				long r2 = CHEFMATH(i, k);
//				System.out.println(f[i] + "\tk: "+k+"\t" + r1 + "\t" + r2);
				if (r1 != r2) {
					System.out.println("fail");
					System.out.println(i + "\tk: "+k+"\t" + r1 + "\t" + r2);
				}
			}
		}
	}
	
	public static void testCHEFMATH_timing() {
		Random rand = new Random(0);
		long t1 = System.currentTimeMillis();
		long t2 = 0;
		for (int j = 0; j < 100; j++) {
			for (int k = 10; k <= 10; k++) {
				long in = rand.nextInt(1000000000);
				if (j == 0) {
					t2 = System.currentTimeMillis();
				}
				CHEFMATH((int) in, k);
				if (j == 0) {
					t2 = System.currentTimeMillis() - t2;
					System.out.println("init time " + (t2));
				}
			}
		}
		System.out.println("total time: " + (System.currentTimeMillis() - t1));
	}
	
	public static long CHEFMATH(int x, int k) {
		int a = 1;
		int b = 1;
		int[] f = new int[50];
		int k2 = 0;
		while (b <= x) {
			f[k2++] += b;
			int old = b;
			b += a;
			a = old;
		}
		f = Arrays.copyOf(f, k2);
		
		return CHEFMATH(x, k, k2, f);
	}
	
	private static long CHEFMATH(int x, int k, int n, int[] f) {
		if (k == 0) {
			return x == 0 ? 1 : 0;
		}
		if (x <= 0 || n == 0) {
			return 0;
		}
		long r1 = CHEFMATH(x, k, n-1, f);
		if (x >= f[n-1] && (long)x <= (long)k * (long)f[n-1]) {
			r1 += CHEFMATH(x - f[n-1], k-1, n, f); 
		}
		return r1;
	}

	private static void precomp() {
		map = new HashMap<>(5000000);
		final int limit = 1000000000;
		final int[] f = getF(limit);
		final int split = mapSize;
	
		for (int j = f.length - 1; j >= 0; j--) {
			int[] count = new int[split];
			for (int i = 0; i < count.length; i++) {
				count[i] = j;
			}
			do {
				if (count[0] != j) {
					break;
				}
				int sum = 0;
				for (int i = 0; i < split; i++) {
					sum += f[count[i]];
					if (sum > limit) {
						for (int m = i; m < split; m++) {
							count[m] = f.length-1;
						}
						break;
					}
				}
				if (sum <= limit) {
					int[] ref = map.get(sum);
					if (ref == null) {
						ref = new int[f.length];
						map.put(sum, ref);
					}
					for (int i = 0; i <= j; i++) {
						ref[i] += 1;
					}
				}
			} while (next_combination_with_rep(count, f.length));
		}
//		int[] dist = new int[f.length];
//		for (int key: map.keySet()) {
//			int[] ref = map.get(key);
//			for (int i = 0; i < ref.length; i++) {
//				dist[i] += ref[i];
//			}
//		}
//
//		System.out.println(Arrays.toString(dist));
//		System.out.println("map size: " + map.size());
	}
	
	public static int[] getF(long x) {
		int a = 1;
		int b = 1;
		int[] f = new int[50];
		int k2 = 0;
		while (b <= x) {
			f[k2++] += b;
			int old = b;
			b += a;
			a = old;
		}
		return Arrays.copyOf(f, k2);
	}
	
	public static long CHEFMATH_slow2(long x, int k) {
		if (map == null) {
			precomp();
		}
		int[] f = getF(x);
		int ways = 0;
		int[] ref = null;
		
		if (k > mapSize) {
			k -= mapSize;			
			int[] count = new int[k];
			do {
				int preSum = 0;
				for (int i = 0; i < count.length; i++) {
					preSum += f[count[i]];
					if (preSum > x) {
						for (int j = i; j < count.length; j++) {
							count[j] = f.length-1;
						}
						break;
					}
				}
				if (preSum <= x) {
					int remaining = (int) (x - preSum);
					ref = map.get(remaining);
					if (ref != null && count[count.length-1] < ref.length) {
						ways += ref[count[count.length-1]];
					}
				}
			} while (next_combination_with_rep(count, f.length));
		} else {
			int[] count = new int[k];
			do {
				int sum = 0;
				for (int i = 0; i < count.length; i++) {
					sum += f[count[i]];
					if (sum > x) {
						for (int j = i; j < count.length; j++) {
							count[j] = f.length-1;
						}
					}
				}
				if (sum == x) {
					ways++;
				}
			} while (next_combination_with_rep(count, f.length));
		}
		
		//System.out.println(map.size());
		
		return ways;
	}

	public static long CHEFMATH_slow(long x, int k) {
		int a = 1;
		int b = 1;
		int[] f2 = new int[50];
		int k2 = 0;
		while (b <= x) {
			f2[k2++] += b;
			int old = b;
			b += a;
			a = old;
		}
		final int[] f = Arrays.copyOf(f2, k2);
		
		int[] count = new int[k];
		int ways = 0;
		do {
			int sum = 0;
			for (int i = 0; i < count.length; i++) {
				sum += f[count[i]];
				if (sum > x) {
					for (int j = i; j < count.length; j++) {
						count[j] = f.length-1;
					}
				}
			}
			if (sum == x) {
				ways++;
			}
		} while (next_combination_with_rep(count, f.length));
		return ways;
	}
	
	public static boolean next_combination_with_rep(int[] list, int base) {
		int i = list.length - 1;
		list[i]++;
		
		if (list[i] == base) {
			int carryEndIdx = -1;
			int carryEnd = -1;
			
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;

				carryEndIdx = i;
				carryEnd = list[carryEndIdx];
				
				if (list[i] == base) {
					carry = true;
				}
			}
			
			for (int j = carryEndIdx; j < list.length; j++) {
				list[j] = carryEnd;
			}
		}
		
		return true;
	}
	
	public static boolean next_number(int list[], int base) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}
	
	public static void CHEFMATH(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			long X = scan.nextInt();
			int k = scan.nextInt();
			System.out.println(CHEFMATH((int) X, k));
		}
	}

	public static void CHEFSOC2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			int s = scan.nextInt() - 1;
			int[] a = scan.nextIntArray(m);
			
			long[] x = new long[n];
			x[s] = 1;
			for (int i = 0; i < a.length; i++) {
				long[] nx = new long[n];
				for (int j = 0; j < x.length; j++) {
					if (j - a[i] >= 0) {
						nx[j - a[i]] = (x[j] + nx[j - a[i]]) % 1000000007L;
					}
					if (j + a[i] < x.length) {
						nx[j + a[i]] = (x[j] + nx[j + a[i]]) % 1000000007L;
					}
				}
				x = nx;
			}
			
			System.out.print(x[0]);
			for (int i = 1; i < x.length; i++) {
				System.out.print(" " + x[i]);
			}
			System.out.println();
		}
	}
	
	public static void testFORESTGA() {
		Random rand = new Random(0);
		for (int i = 0; i < 1000000; i++) {
			int N = rand.nextInt(100)+1;
			long L = rand.nextInt(100)+1;
			long W = rand.nextInt(1000)+1;
			long[] h = new long[N];
			long[] r = new long[N];
			for (int j = 0; j < N; j++) {
				h[j] = rand.nextInt(50)+1;
				r[j] = rand.nextInt(50)+1;
			}
			long r1 = FORESTGA(N, L, W, h, r);
			long r2 = FORESTGA_slow(N, L, W, h, r);
			if (r1 != r2) {
				System.out.println("fail " + i);
				r1 = FORESTGA(N, L, W, h, r);
				r2 = FORESTGA_slow(N, L, W, h, r);
			} else {
				//System.out.println(i);
			}
		}
	}

	public static void FORESTGA(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int N = scan.nextInt();
		long W = scan.nextLong();
		long L = scan.nextLong();
		long[] h = new long[N];
		long[] r = new long[N];
		for (int i = 0; i < N; i++) {
			h[i] = scan.nextLong();
			r[i] = scan.nextLong();
		}
		System.out.println(FORESTGA(N, L, W, h, r));
	}
	
	public static long FORESTGA(int N, long L, long W, long[] h, long[] r) {
		TreeMap<Long, ArrayList<Integer>> month = new TreeMap<>();
		for (int i = 0; i < r.length; i++) {
			long m = 0;
			if (h[i] < L) {
				m = (L - h[i]) / r[i];
				if ((L - h[i]) % r[i] != 0) {
					m++;
				}
			}
			
			if (!month.containsKey(m)) {
				month.put(m, new ArrayList<>());
			}
			ArrayList<Integer> list = month.get(m);
			list.add(i);
		}
		
		long totalH = 0;
		long totalR = 0;
		long min = -1;
		
		for (long m: month.keySet()) {
			ArrayList<Integer> list = month.get(m);
			for (int i = 0; i < list.size(); i++) {
				totalH += h[list.get(i)];
				totalR += r[list.get(i)];
			}
			//System.out.println(m + "\t" + totalW);
			if (BigInteger.valueOf(totalH).add(BigInteger.valueOf(totalR).multiply(BigInteger.valueOf(m))).compareTo(BigInteger.valueOf(W)) >= 0) {
				min = m;
				break;
			}
			if (month.higherKey(m) != null) {
				// Min might be before next marked month
				// So you're telling me there's a chance?
				long best = (W - totalH) / totalR;
				if ((W - totalH) % totalR != 0) {
					best++;
				}
				if (best <= month.higherKey(m) - 1) {
					return best;
				}
			}
		}
		if (min < 0) {
			min = (W - totalH) / totalR;
			if ((W - totalH) % totalR != 0) {
				min++;
			}
		}
		return min;
	}
	
	public static long FORESTGA_slow(int N, long L, long W, long[] h, long[] r) {
		long month = 0;
		while (true) {
			long totalW = 0;
			for (int i = 0; i < r.length; i++) {
				if (h[i] + r[i] * month >= L) {
					totalW += h[i] + r[i] * month;
				}
			}
			if (totalW >= W) {
				return month;
			}
			month++;
		}
	}

	public static void CHBLLS(InputStream in) {
		MyScanner scan = new MyScanner(in);
		System.out.println("1\n3 1 1 2\n3 3 3 4");
		int w = scan.nextInt();
		int heavy = 0;
		if (w == 0) {
			heavy = 5;
		} else if (w == 2) {
			heavy = 1;
		} else if (w == 1) {
			heavy = 2;
		} else if (w == -1) {
			heavy = 4;
		} else if (w == -2) {
			heavy = 3;
		}
		System.out.println("2");
		System.out.println(heavy);
	}

	public static void LADDU(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int act = scan.nextInt();
			String nat = scan.next();
			int laddu = 0;
			for (int i = 0; i < act; i++) {
				String type = scan.next();
				if (type.equals("CONTEST_WON")) {
					int rank = scan.nextInt();
					laddu += 300;
					if (rank <= 20) {
						laddu += 20 - rank;
					}
				} else if (type.equals("TOP_CONTRIBUTOR")) {
					laddu += 300;
				} else if (type.equals("BUG_FOUND")) {
					laddu += scan.nextInt();
				} else if (type.equals("TOP_CONTRIBUTOR")) {
					laddu += 300;
				} else if (type.equals("CONTEST_HOSTED")) {
					laddu += 50;
				}
			}
			int min = nat.equals("INDIAN") ? 200 : 400;
			System.out.println(laddu / min);
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
