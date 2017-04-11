import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class _101HackJune2016 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
		easyGcd();
//		pointsOnALine();
	}
	
	public static void easyGcd() {
		int n = in.nextInt();
		long k = in.nextInt();
		long[] a = in.nextLongArray(n);
		System.out.println(easyGcd(n, k, a));
	}
	
	public static long easyGcd(int n, long k, long[] a) {
		Arrays.sort(a);
		long g = a[a.length-1];
		for (int i = a.length-1; i >= 0; i--) {
			g = gcd(a[i], g);
		}
		if (g == 1) {
			return 0;
		} else {
			long max = (k/g)*g;
			for (long i = 2; i*i <= g; i++) {
				if (g % i == 0) {
					max = Math.max((k/i)*i, max);
				}
			}
			return max;
		}
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
	
	public static void pointsOnALine() {
		int n = in.nextInt();
		int[][] p = new int[n][2];
		for (int i = 0; i < p.length; i++) {
			p[i][0] = in.nextInt();
			p[i][1] = in.nextInt();
		}
		if (pointsOnALine(n, p)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}

	public static boolean pointsOnALine(int n, int[][] p) {
		
		for (int i = 0; i < 2; i++) {
			boolean valid = true;
			for (int j = 1; j < p.length; j++) {
				if (i == 0) {
					if (p[j][1] - p[j-1][1] != 0) {
						valid = false;
						break;
					}
				} else {
					if (p[j][0] - p[j-1][0] != 0) {
						valid = false;
						break;
					}
				}
			}
			if (valid) {
				return true;
			}
			
		}
		return false;
	}

	@SuppressWarnings("serial")
	public static class Counter<T> extends HashMap<T, Integer> {
		public Counter() {
			super();
		}
		public void add(T key) {
			this.add(key, 1);
		}
		public void add(T key, int count) {
			Integer i = this.get(key);
			if (i == null) {
				this.put(key, count);
			} else {
				this.put(key, i+count);
			}
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
