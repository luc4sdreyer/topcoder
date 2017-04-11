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

public class Various {
	public static void main(String[] args) {
		CSEQ(System.in);
//		testCSEQ();
	}

	public static void CSEQ(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int l = scan.nextInt();
			int r = scan.nextInt();
			int x = r - l + 1;
			System.out.println(CSEQ_2(n, x));
		}
	}
	
	public final static long mod_2 = 1000003;
	
	public static void testCSEQ() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i + "\t" + CSEQ(i, i) + "\t" + CSEQ_2(i, i));
		}
	}
	
	public static long CSEQ(int n, int x) {
		long res = 0;
		for (int i = 1; i <= n; i++) {
			res = (res + nCrWithRep(x, i)) % mod_2; 
		}
		return res;
	}
	
	public static long CSEQ_2(int N, int x) {
		long res = 1;
		for (int j = 1; j <= N; j++) {
			res = (res * (x+j) / j) % mod_2;
			
		}
		return res-1;
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

	/**
	 * Number of combinations WITH repetition:  (n + r - 1)! / (r! * (n - 1)!)
	 */
	public static long nCrWithRep(int n, int r) {
		return nCr(n + r - 1, r);
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
