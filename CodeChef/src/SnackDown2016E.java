
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

public class SnackDown2016E {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

//		SEGSUMQ();
		NUMSET();
//		VCAKE();
		
		out.close();
	}
	
	public static long SEGSUMQslow(int[] a, int[] b, int[] q) {
		int c = q[2];
		int d = q[3];
		int[] max = new int[a.length];
		long sum = 0; 
		for (int i = q[0]; i <= q[1]; i++) {
			max[i] = Math.max(max[i], a[i]*c - b[i]*d);
			sum += max[i];
		}
		return sum;
	}
	
	public static void SEGSUMQ() {
		int[] a = {1,2,3,4,5};
		int[] b = {5,4,3,2,1};
		for (int c = 0; c <= 10; c++) {
			for (int d = 0; d <= 10; d++) {
				int[] max = new int[a.length];
				for (int i = 0; i < max.length; i++) {
					max[i] = Math.max(max[i], a[i]*c - b[i]*d);
				}
				System.out.println(Arrays.toString(max));
				
			}
		}
	}

	static BitSet P_1000;
	public static void NUMSET() {
		int limit = 100;
		P_1000 = getPrimes(limit);

		ArrayList<Integer> primes = new ArrayList<>();
		BitSet p = getPrimes(limit);
		
		for (int i = p.nextSetBit(0); i >= 0 && i <= limit; i = p.nextSetBit(i+1)) {
			primes.add(i);
		}
		int[] P = new int[limit+1];
		for (int i = 1; i < P.length; i++) {
			for (int j = 0; j < primes.size(); j++) {
				if (i % primes.get(j) == 0) {
					P[i] |= 1 << j;
				}
			}
		}
		int[] P_32 = { 2,   3,   5,   7,  11,  13,  17,  19,  23,  29,  31};
		int len = P_32.length;
		int N = 1 << len;
		long[][] F = new long[limit][N];

		for (int n = 0; n < limit; n++) {
			for (int k = 0; k < N; k++) {
				if (n == 0) {
					F[n][k] = 1;
				} else {
					F[n][k] = 0;
					for (int v = 1; v <= limit; v++) {
						if ((k & P[v]) == P[v]) {
//							F[n][k] += F[n-1][k ^ P[v]];
							if (F[n-1][k ^ P[v]] > 0) {
								F[n][k] = F[n-1][k ^ P[v]] + 1;
							}
						}
					}
				}
			}
		}
		
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int na = in.nextInt();
			int[] a = in.nextIntArray(na);
			NUMSET(na, a);
		}
	}
	
	public static void NUMSET(int n, int[] a) {
		
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
	
	public static void VCAKE() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			long r = in.nextLong();
			long c = in.nextLong();
			long[] mjk = in.nextLongArray(3);
			if (VCAKE(r, c, mjk)) {
				out.println("Yes");
			} else {
				out.println("No");
			}
		}
	}

	public static boolean VCAKE(long R, long C, long[] m) {
		long sum = m[0] + m[1] + m[2];
		if (sum != R*C) {
			return false;
		}
		for (int orient = 0; orient < 2; orient++) {
			long Y = R;
			long X = C;
			if (orient == 1) {
				Y = C;
				X = R;
			}
			int[] p = {0, 1, 2};
			do { 
				if (m[p[0]] % Y == 0) {
					long a = m[p[0]] / Y;
					// Y cut:
					if (m[p[1]] % Y == 0) {
						long b = m[p[1]] / Y;
						if (m[p[2]] % Y == 0) {
							long c = m[p[2]] / Y;
							if (a+b+c == X) {
								return true;
							}
						}
					// X cut
					} else if (m[p[1]] % (X-a) == 0) {
						long b = m[p[1]] / (X-a);
						if (m[p[2]] == (Y-b)*(X-a)) {
							return true;
						}
					}
				}
			} while (next_permutation(p));
		}
		return false;
	}

	public static boolean next_permutation(int str[]) {
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
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
