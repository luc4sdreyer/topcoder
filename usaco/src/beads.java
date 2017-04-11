/*
ID: luc4sdr1
LANG: JAVA
TASK: beads
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

class beads {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);
	public static String input_name = "beads";

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
		char[] a = in.next().toCharArray();
		int max = 0;
		
		for (int i = 0; i < a.length; i++) {
			int f = (i + N) % N;
			int b = (i-1 + N) % N;

			int lenF = 1;
			char startF = a[f];

			int lenB = 1;
			char startB = a[b];
			
			while (true) {
				f = (f+1 + N) % N;
				if (f == b) {
					break;
				}
				if (a[f] == 'w' || startF == a[f] || startF == 'w') {
					lenF++;
				} else {
					break;
				}
				if (startF == 'w') {
					startF = a[f];
				}
			}

			if (f == b) {
				max = Math.max(max, lenF + lenB);
				continue;
			}
			
			while (true) {
				if (f == b) {
					break;
				}
				b = (b-1 + N) % N;
				if (a[b] == 'w' || startB == a[b] || startB == 'w') {
					lenB++;
				} else {
					break;
				}
				if (startB == 'w') {
					startB = a[b];
				}
			}
			max = Math.max(max, lenF + lenB);
		}
		out.println(max);
		
	}
	
	public static void solve2() {
		int N = in.nextInt();
		char[] a = in.next().toCharArray();
		int[] len = paint(N, a);
		if (len[N-1] == N) {
			out.println(N);
			return;
		}
		for (int i = 0; i < N; i++) {
			char before = a[(i+N-1) % N];
			char after = a[(i+N+1) % N];
			if (a[i] == 'w') {
				if (before == after) {
					a[i] = before;
				} else {
					if (len[(i+N-1) % N] >= len[(i+N-1) % N]) {
						a[i] = before;
					} else {
						a[i] = after;
					}
				}
			}
		}
		
		len = paint(N, a);
		if (len[N-1] == N) {
			out.println(N);
			return;
		}
		
	}
	
	public static int[] paint(int N, char[] a) {
		int[] len = new int[N];
		int start = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == a[(i+N-1) % N]) {
				len[i] = len[(i+N-1) % N] + 1; 
			} else {
				for (int j = start; j < i; j++) {
					len[j] = len[i];
				}
				start = i;
				len[i] = 1;
			}
		}
		for (int j = start; j < N; j++) {
			len[j] = len[N-1];
		}
		return len;
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
