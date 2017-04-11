/*
ID: luc4sdr1
LANG: JAVA
TASK: milk2
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

class milk2 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);
	public static String input_name = "milk2";

	public static void main(String[] args) throws IOException {
//		OutputStream outputStream = System.out;
		in = new InputReader(new FileReader(input_name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(input_name + ".out")));
//		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

		solve();
		out.close();
	}
	
	public static void solve() {
		int N = in.nextInt();
		
		int[] a = new int[N];
		int[] b = new int[N];
		int begin = Integer.MAX_VALUE;
		int end = 0;
		for (int i = 0; i < N; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
			begin = Math.min(begin, a[i]);
			end = Math.max(end, b[i]);
		}
		
		int[] start = new int[1000001];
		for (int i = 0; i < N; i++) {
			start[a[i]]++;
			start[b[i]]--;
		}
		
		int numActive = 0;
		int busy = 0;
		int maxbusy = 0;
		int idle = 0;
		int maxidle = 0;
		for (int i = begin; i < end; i++) {
			numActive += start[i];
			if (numActive > 0) {
				busy++;
			} else {
				maxbusy = Math.max(maxbusy, busy);
				busy = 0;
			}
			if (numActive == 0) {
				idle++;
			} else {
				maxidle = Math.max(maxidle, idle);
				idle = 0;
			}
		}
		maxbusy = Math.max(maxbusy, busy);
		maxidle = Math.max(maxidle, idle);
		out.println(maxbusy + " " + maxidle);
		
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
		public StringTokenizer tokenizer = null;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
		}
		public InputReader(InputStreamReader stream) {
			reader = new BufferedReader(stream, 32768);
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
