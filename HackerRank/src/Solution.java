import java.io.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;

//  world-codesprint-10

public class Solution {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
//		rewardPoints();
//		zigzagArray2();
		maximalAndSubsequences();
//		testMaximalAndSubsequences2();
		
		out.close();
	}
	
	public static void testMaximalAndSubsequences2() {
		Random rand = new Random(0);
		int numTests = 10000;
		for (int test = 0; test < numTests; test++) {
			long[] a = new long[6];
			for (int j = 0; j < a.length; j++) {
				a[j] = rand.nextInt(100000000);
			}
			for (int k = 1; k <= a.length; k++) {
				long[] exp = all_combinations(a, k);
				long[] actual = maximalAndSubsequences2(a.length, k, Arrays.copyOf(a, a.length));
				for (int j = 0; j < actual.length; j++) {
					if (exp[j] != actual[j]) {
						maximalAndSubsequences2(a.length, k, Arrays.copyOf(a, a.length));
						System.out.println("fail");
					}
				}
			}
		}
	}
	
	public static void testMaximalAndSubsequences() {
		long[] a = {15, 14, 12, 12, 12, 11, 11, 10, 10, 10, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 8};
		for (int k = 1; k <= a.length; k++) {
			long[] ret = maximalAndSubsequences(a.length, k, Arrays.copyOf(a, a.length));
			System.out.println("k: " + k + ", " + Long.toBinaryString(ret[0]) + " " + ret[1]);
		}
	}

	public static void maximalAndSubsequences() {
		int n = in.nextInt();
		int k = in.nextInt();
		long[] a = in.nextLongArray(n);
		long[] ret = maximalAndSubsequences2(n, k, a);
		System.out.println(ret[0]);
		System.out.println(ret[1]);
	}
	
	public static long[] maximalAndSubsequences2(int n, int k, long[] a) {
		boolean[] valid = new boolean[n];
		Arrays.fill(valid, true);
		
		for (int bit = 63; bit >= 0; bit--) {
			int numSet = 0;
			for (int i = 0; i < a.length; i++) {
				if (valid[i] && getBitL(a[i], bit)) {
					numSet++;
				}
			}
			if (numSet >= k) {
				// exclude 0's
				for (int i = 0; i < a.length; i++) {
					if (!getBitL(a[i], bit)) {
						valid[i] = false;
					}
				}
			}
		}

		long max = Long.MAX_VALUE;
		int numMax = 0;
		for (int i = 0; i < valid.length; i++) {
			if (valid[i]) {
				max &= a[i];
				numMax++;
			}
		}
		int mod = 1000 * 1000 * 1000 + 7;

		return new long[]{max, nCrBig(numMax, k, mod)};
	}

	public static int nCrBig(int n, int r, int modulo) {
		if (n < r) {
			return 0;
		}
		
		// symmetry
		if (n - r < r) {
			r = n - r;
		}

		long a = 1;
		long b = 1;
		long mod = modulo;
		
		for (long i = n - r + 1; i <= n; i++) {
			a = (a * i) % mod;
		}
		for (long i = 2; i <= r; i++) {
			b = (b * i) % mod;
		}

		long bInv = modularMultiplicativeInverse((int) b, modulo);
		long ret = (a * bInv) % mod;
		
		return (int) ret;
	}
	
	public static int eulerTotientFunction(int n) {
		int result = n;
		for (int i = 2; i*i <= n; i++) {
			if (n % i == 0) {
				result -= result / i;
			}
			while (n % i == 0) {
				n /= i;
			}
		}
		   
		if (n > 1) {
			result -= result / n;
		}
		return result;
	}
	
	public static int modularMultiplicativeInverse(int b, int mod) {
		return fastModularExponent(b, eulerTotientFunction(mod) - 1, mod) % mod;
	}
	
	public static int fastModularExponent(int a, int exp, int mod) {
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

	public static long[] all_combinations(long list[], int k) {
		int N = 1 << list.length;
		long mmax = 0;
		for (int n = 0; n < N; n++) {
			int count = 0;
			long bAnd = Integer.MAX_VALUE;
			for (int i = 0; i < list.length; i++) {
				if (((1 << i) & n) != 0) {
					count++;
					bAnd &= list[i];
				}
			}
			if (count == k) {
				mmax = Math.max(mmax, bAnd);
			}
		}
		int numMax = 0;
		HashSet<Integer> chosen = new HashSet<>();
		for (int n = 0; n < N; n++) {
			int count = 0;
			long bAnd = Integer.MAX_VALUE;
			HashSet<Integer> chosen2 = new HashSet<>();
			for (int i = 0; i < list.length; i++) {
				if (((1 << i) & n) != 0) {
					count++;
					bAnd &= list[i];
					chosen2.add(i);
				}
			}
			if (count == k && bAnd == mmax) {
				chosen.addAll(chosen2);
				numMax++;
			}
			
		}
		return new long[]{mmax, numMax};
	}
	
	public static long[] maximalAndSubsequences(int n, int k, long[] a) {
		Arrays.sort(a);

		long max = 0;
		int numMax = 0;
		
		if (k == n) {
			max = Integer.MAX_VALUE;
			max = a[0];
			for (int i = 0; i < a.length; i++) {
				max &= a[i];
			}
			numMax = a.length;
		} else {
			for (int bit = 2; bit >= 0; bit--) {
			//for (int bit = 63; bit >= 0; bit--) {
				int lastValid = 0;
				lastValid = binarySearchWithDuplicates(a, bit, n - k -1);
				//int amount = (n - k -1) - (lastValid);
				int amount = (n) - (lastValid);
				if (k > amount) {
					// this index doesn't matter anymore.
					for (int i = 0; i < a.length; i++) {
						a[i] = clearBitL(a[i], bit);
					}
					Arrays.sort(a);
				}
			}
			
			for (int i = 0; i < a.length; i++) {
				max = Math.max(max, a[i]);
			}
			
			for (int i = 0; i < a.length; i++) {
				if (a[i] == max) {
					numMax++;
				}
			}
		}
		
		long mod = 1000 * 1000 * 1000 + 7;
		long ways = 1;
		for (int i = 0; i < numMax; i++) {
			ways = ways * 1;
		}
		
		return new long[]{max, numMax};
	}
	
	public static int binarySearchWithDuplicates(long[] a, int target, int low) {
		int high = a.length-1;
		int lastValid = a.length;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			if (getBitL(a[mid], target)) {
				high = mid - 1;
				lastValid = mid;
			} else {
				low = mid + 1;
			}
		}
		return lastValid;
	}
	
	public static long clearBitL(long value, int idx) {
		return (value & ~(1L << idx));
	}
	
	public static long setBitL(long value, int idx) {
		return (value | (1L << idx));
	}
	
	public static boolean getBitL(long value, int idx) {
		return (value & (1L << idx)) != 0;
	}
	
	public static void zigzagArray2() {
		int n = in.nextInt();
		int[] a = in.nextIntArray(n);
		ArrayList<Integer> b = new ArrayList<>();
		for (int j = 0; j < a.length; j++) {
			b.add(a[j]);
		}
//		for (int i = 1; i < b.size(); i++) {
//			while (i < b.size() && b.get(i-1) == b.get(i)) {
//				b.remove(i);
//			}
//		}
		for (int i = 2; i < b.size(); i++) {
			while (i < b.size() && b.get(i-2) > b.get(i-1) && b.get(i-1) > b.get(i)) {
				b.remove(i-1);
			}
			while (i < b.size() && b.get(i-2) < b.get(i-1) && b.get(i-1) < b.get(i)) {
				b.remove(i-1);
			}
		}

		System.out.println(n - b.size());
	}
	
	public static void zigzagArray() {
		int n = in.nextInt();
		int[] a = in.nextIntArray(n);
		int minTotal = a.length;
		for (int i = 0; i < 2; i++) {
			ArrayList<Integer> b = new ArrayList<>();
			for (int j = 0; j < a.length; j++) {
				b.add(a[j]);
			}
			int idx = 0;
			boolean increase = true;
			if (i == 1) {
				increase = false;
			}
			while (idx < b.size() -1) {
				if (increase) {
					if (b.get(idx) <= b.get(idx+1)) {
						int start = idx;
						int end = start;
						int max = 0;
						
						while (end < b.size() -1 && b.get(end) <= b.get(end+1)) {
							max = Math.max(max, b.get(end+1));
							end++;
						}
						
						for (int j = start; j < end; j++) {
							int x = b.remove(start+1);
							System.out.println("removed " + x + " at " + (start+1));
						}
						if (start != end) {
							b.add(start+1, max);
							System.out.println("add " + max + " at " + (start+1));
						}
					} else {
						while (idx < b.size() -1 && b.get(idx) > b.get(idx+1)) {
							int x = b.remove(idx+1);
							System.out.println("removed " + x + " at " + (idx+1));
						}
					}
				} else {
					if (b.get(idx) >= b.get(idx+1)) {
						int start = idx;
						int end = start;
						int min = Integer.MAX_VALUE;
						
						while (end < b.size() -1 && b.get(end) >= b.get(end+1)) {
							min = Math.min(min, b.get(end+1));
							end++;
						}
						
						for (int j = start; j < end; j++) {
							int x = b.remove(start+1);
							System.out.println("removed " + x + " at " + (start+1));
						}
						if (start != end) {
							b.add(start+1, min);
							System.out.println("add " + min + " at " + (start+1));
						}
						
					} else {
						while (idx < b.size() -1 && b.get(idx) < b.get(idx+1)) {
							int x = b.remove(idx+1);
							System.out.println("removed " + x + " at " + (idx+1));
						}
					}
				}
				increase = increase ? false : true;
				idx++;
			}
			minTotal = Math.min(minTotal, n - b.size());
			System.out.println();
		}
		System.out.println(minTotal);
	}
	
	public static void rewardPoints() {
		int[] a = in.nextIntArray(3);
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += Math.min(10,a[i]);
		}
		sum *= 10;
		System.out.println(sum);
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
