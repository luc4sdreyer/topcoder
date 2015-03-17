import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solution2 {
	public static void main(String[] args) {
		knockKnock(System.in);
//		for (int i = 0; i < 10000000; i++) {
//			long r0 = getR(i);
//			if (r0 > (long)(Integer.MAX_VALUE/5)) {
//				System.out.println(i + " " + r0);	
//			}
//		}
	}

	public static HashMap<Integer, Long> r;
	static {
		r = new HashMap<>();		
		r.put(0, 1L);
		r.put(1, 1L);
		r.put(2, 1L);
		r.put(3, 3L);
		r.put(4, 3L);
		r.put(5, 15L);
		r.put(6, 15L);
		r.put(7, 105L);
		r.put(8, 105L);
		r.put(9, 105L);
		r.put(10, 105L);
	}

	public static void knockKnock(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			long r0 = getR(n);
			long max = 0;
			for (int x = 1; x <= 5; x++) {
				for (int y = 1; y <= 5; y++) {
					if (gcd(r0 * x, r0 * y) == r0) {
						max = Math.max(max, fastModularExponent(r0 * x, r0 * y, mod));
					}
				}
			}
			System.out.println(max);
		}
	}

	public static long gcd(long a, long b) {
	    return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
	}
	
	public static int mod = 1000000000;
	
	public static long getR(int n) {
		if (r.containsKey(n)) {
			return r.get(n);
		} else {
			long n1 = (long)fastModularExponent(2, (int)Math.ceil(n/4.0), mod) % (long)mod;
			long n2 = getR((int) Math.ceil(n/5.0)) % (long)mod;
			long n3 = getR((int) Math.ceil(n/10.0)) % (long)mod;
			long next = (((n1 * n2) % (long)mod) * n3) % (long)mod;
			r.put(n, next);
			return next;
		}
	}
	
	public static long fastModularExponent(long a, long exp, long mod) {
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
		return (res % m);
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
