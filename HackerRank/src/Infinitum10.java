import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Infinitum10 {
	public static void main(String[] args) {
		//sherlockAndMovingTiles(System.in);
		//fibonacciFinding(System.in);
		//numberOfZeroXorSubsetsTest();
		//numberOfZeroXorSubsets(System.in);
		cheeseAndRandomToppings(System.in);
	}

	public static void cheeseAndRandomToppings(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			System.out.println(cheeseAndRandomToppings(scan.nextInt(), scan.nextInt(), scan.nextInt()));
		}
	}
	
	public static int cheeseAndRandomToppings(int n, int r, int m) {
		return (int) (nCr(n, r) % (long)m);
	}


	/**
	 * Number of combinations without repetition:  n! / (r! * (n - r)!)
	 */
	public static long nCr(int n, int r) {
		if (n == 0) {
			return 1;
		}
		long ret = 1;
		//
		// symmetry
		//
		if (n - r < r) {
			r = n - r;
		}
		for (long i = n - r + 1; i <= n; i++) {
			ret *= i;
		}
		for (long i = 2; i <= r; i++) {
			ret /= i;
		}
		return ret;
	}

	public static void numberOfZeroXorSubsetsTest() {
		for (int a = 1; a <= 1000; a++) {
			System.out.println(a+ " " +  numberOfZeroXorSubsets2(a));
		}
	}
	
	public static int numberOfZeroXorSubsets(long a) {
		long n = 1L << a;
		//long n = fastModularExponent(2, a, mod);
		return fastModularExponent(2, n, mod) / fastModularExponent(2, a, mod);
	}
	
	public static int numberOfZeroXorSubsets2(long a) {
		BigInteger n = BigInteger.valueOf(2).shiftLeft((int)(a-1));
		//return fastModularExponent(BigInteger.valueOf(2), n.subtract(BigInteger.valueOf(a)), BigInteger.valueOf(mod)).intValue();
		BigInteger res = fastExponent(BigInteger.valueOf(2), n.subtract(BigInteger.valueOf(a)));
		String s = res.toString();
		if (s.length() > 20) {
			s = s.substring(s.length()-20, s.length());
		}
		System.out.println(s);
		return res.intValue();
	}
	
	public static BigInteger fastExponent(BigInteger a, BigInteger exp) {
		ArrayList<BigInteger> results = new ArrayList<>();
		int power = 1;
		BigInteger res = BigInteger.ONE;
		BigInteger two = BigInteger.valueOf(2);
		results.add(BigInteger.ZERO);
		while (!exp.equals(BigInteger.ZERO)) {
			if (power == 1) {
				results.add(a);
			} else {
				results.add(results.get(power-1).multiply(results.get(power-1)));
			}
			if (exp.mod(two).equals(BigInteger.ONE)) {
				res = (res.multiply(results.get(power)));
			}
			exp = exp.divide(two);
			power++;
		}
		return res;
	}
	
	public static BigInteger fastModularExponent(BigInteger a, BigInteger exp, BigInteger mod) {
		ArrayList<BigInteger> results = new ArrayList<>();
		BigInteger m = mod;
		int power = 1;
		BigInteger res = BigInteger.ONE;
		BigInteger two = BigInteger.valueOf(2);
		results.add(BigInteger.ZERO);
		while (!exp.equals(BigInteger.ZERO)) {
			if (power == 1) {
				results.add(a.mod(m));
			} else {
				results.add(results.get(power-1).multiply(results.get(power-1)).mod(m));
			}
			if (exp.mod(two).equals(BigInteger.ONE)) {
				res = (res.multiply(results.get(power))).mod(m);
			}
			exp = exp.divide(two);
			power++;
		}
		return res.mod(m);
	}

	public static int fastModularExponent(int a, long exp, int mod) {
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

	public static void numberOfZeroXorSubsets(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			System.out.println(numberOfZeroXorSubsets2(scan.nextLong()));
		}
	}

	public static final int mod = 1000000007;
	public static void fibonacciFinding(InputStream in) {
		MyScanner scan = new MyScanner(in);
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			//long[][] d = {{Math.min(a, b)}, {Math.max(a, b)}}; 
			long[][] d = {{b}, {a}};
			int n = scan.nextInt();
			long ret = 0;
			if (n == 1) {
				ret = b;
			} else {
				n -= 1;
				long[][] m = {{1, 1}, {1, 0}};
				long[][] mp = fastMatrixExponent(m, n, mod);
				long[][] res = multiplyMatrix(mp, d, mod);
				ret = res[0][0]; 
			}
			System.out.println(ret);
		}
	}
	
	public static long[][] multiplyMatrix(long[][] A, long[][] B, int mod) {
		long[][] C = new long[A.length][B[0].length];
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[0].length; j++) {
				for (int k = 0; k < B.length; k++) {
					C[i][j] = (C[i][j] + (A[i][k] * B[k][j])) % mod; 
				}
			}
		}
		return C;
	}
	
	public static long[][] fastMatrixExponent(long[][] A, long n, int mod) {
		if (n == 1) {
			return A;
		} else {
			if (n % 2 == 0) {
				long[][] T = fastMatrixExponent(A, n/2, mod);
				return multiplyMatrix(T, T, mod);
			} else {
				return multiplyMatrix(A, fastMatrixExponent(A, n-1, mod), mod);
			}
		}
	}

	public static void sherlockAndMovingTiles(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int L = scan.nextInt();
		int s1 = scan.nextInt();
		int s2 = scan.nextInt();
		int b = Math.abs(s1 - s2);
		int n = scan.nextInt();
		for (int i = 0; i < n; i++) {
			System.out.println(Math.sqrt(2)/b * (L - Math.sqrt(scan.nextLong())));
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
