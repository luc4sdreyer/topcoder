/*
ID: luc4sdr1
LANG: JAVA
TASK: gift1
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

class gift1 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);
	public static String input_name = "gift1";

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
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < N; i++) {
			map.put(in.next(), 0);
		}
		int[] m = new int[N];
		for (int i = 0; i < N; i++) {
			String name = in.next();
			m[i] = in.nextInt();
			//map.put(name, map.get(name) + m[i]);
			int friends = in.nextInt();
			for (int j = 0; j < friends; j++) {
				String f = in.next();
				map.put(name, map.get(name) - m[i]/friends);
				map.put(f, map.get(f) + m[i]/friends);
			}
		}
		for (String name: map.keySet()) {
			out.println(name + " " + map.get(name));
		}
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
