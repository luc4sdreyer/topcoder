/*
ID: luc4sdr1
LANG: JAVA
TASK: transform
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

class transform {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);
	public static String input_name = "transform";

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
		int[][] mat = new int[N][N];
		int[][] tar = new int[N][N];
		
		read(mat, N);
		read(tar, N);
		out.println(solve(mat, tar));
	}

	public static int solve(int[][] mat, int[][] tar) {		
		if (equal(rot(mat), tar)) {
			return 1;
		}	
		if (equal(rot(mat, 2), tar)) {
			return 2;
		}	
		if (equal(rot(mat, 3), tar)) {
			return 3;
		}	
		if (equal(refl(mat), tar)) {
			return 4;
		}
		for (int r = 1; r <= 3; r++) {
			if (equal(tar, rot(refl(mat), r))) {
				return 5;
			}
		}
		if (equal(mat, tar)) {
			return 6;
		}
		return 7;
	}
	
	public static int[][] refl(int[][] mat) {
		int N = mat.length;
		int[][] ret = new int[N][N];
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				ret[y][x] = mat[y][N-x-1];
			}
		}
		return ret;
	}
	
	public static int[][] rot(int[][] mat, int times) {
		int[][] r = mat;
		while (times > 0) {
			r = rot(r);
			times--;
		}
		return r;
	}

	public static int[][] rot(int[][] mat) {
		int N = mat.length;
		int[][] ret = new int[N][N];
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				ret[x][N-y-1] = mat[y][x];
			}
		}
		return ret;
	}

	public static boolean equal(int[][] mat, int[][] tar) {
		for (int i = 0; i < tar.length; i++) {
			if (!Arrays.equals(tar[i], mat[i])) {
				return false;
			}
		}
		return true;
	}

	public static void read(int[][] mat, int N) {
		for (int i = 0; i < N; i++) {
			char[] x = in.next().toCharArray();
			for (int j = 0; j < x.length; j++) {
				mat[i][j] = x[j] == '@' ? 1 : 0;
			}
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
