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
import java.util.TreeSet;

public class SolutionAll3 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
//		permutationGame();
//		chocolateInBox();
//		floydCityOfBlindingLights();
//		primsmstsub();
		kruskalmstrsub();
	}
	
	public static void kruskalmstrsub() {
		int n = in.nextInt(); // vertices
		int m = in.nextInt(); // vertices
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Integer>());
			cost.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() -1;
			int b = in.nextInt() -1;
			int c = in.nextInt();
			g.get(a).add(b);
			g.get(b).add(a);
			cost.get(a).add(c);
			cost.get(b).add(c);
		}
		HashMap<Pair<Integer, Integer>, Integer> mst = primMST(g, cost);
		long total = 0;
		for (Integer i: mst.values()) {
			total += (long)i;
		}
		out.println(total);
	}

	public static HashMap<Pair<Integer, Integer>, Integer> primMST(ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> cost) {
		int N = g.size();
		int[] e = new int[N];				// The vertex that connects to this node in the MST.
		int[] d = new int[N];				// The cost of the edge leading to the vertex in e.
		boolean[] visited = new boolean[N];
		Arrays.fill(d, Integer.MAX_VALUE);
		Arrays.fill(e, -1);
		TreeSet<Pair<Integer, Integer>> s = new TreeSet<>();
		for (int i = 0; i < N; i++) {
			s.add(new Pair<>(d[i], i));
		}
		int top = 0;
		while (!s.isEmpty()) {
			top = s.first().b;
			s.remove(s.first());
			if (visited[top]) {
				continue;
			}
			visited[top] = true;
			
			for (int i = 0; i < g.get(top).size(); i++) {
				int neighbour = g.get(top).get(i);
				int weight = cost.get(top).get(i);
				if (!visited[neighbour] && d[neighbour] > weight) {
					s.remove(new Pair<>(d[neighbour], neighbour));
					d[neighbour] = weight;
					e[neighbour] = top;
					s.add(new Pair<>(d[neighbour], neighbour));
				}
			}
		}
		
		HashMap<Pair<Integer, Integer>, Integer> mst = new HashMap<>();
		for (int i = 0; i < d.length; i++) {
			int a = Math.min(i, e[i]);
			int b = Math.max(i, e[i]);
			if (a != -1 && !mst.containsKey(a)) {
				mst.put(new Pair<>(a, b), d[i]);
			}
		}
		return mst;
	}

	public static class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A, B>> {
		public A a;
		public B b;
		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
		public int hashCode() {
			return a.hashCode() * 13 + b.hashCode();
		}
		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<A, B> op = (Pair<A, B>) other;
				return ((this.a == op.a || this.a.equals(op.a)) && (this.b == op.b || this.b.equals(op.b)));
			}
			return false;
		}
		public String toString() { 
			return "(" + a + ", " + b + ")"; 
		}
		@Override
		public int compareTo(Pair<A, B> o) {
			int compareFirst = this.a.compareTo(o.a);
			return compareFirst != 0 ? compareFirst : this.b.compareTo(o.b);
		}
	}
	
	public static void floydCityOfBlindingLights() {
		int n = in.nextInt();
		int m = in.nextInt();
		int[][] g = new int[n][n];

		int N = n;
		for (int i = 0; i < N; i++) {
			Arrays.fill(g[i], Integer.MAX_VALUE);
		}
		
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() -1;
			int b = in.nextInt() -1;
			int c = in.nextInt();
			g[a][b] = c;
		}
		
		for (int k = 0; k < N; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (g[i][k] < Integer.MAX_VALUE && g[k][j] < Integer.MAX_VALUE) {
						g[i][j] = Math.min(g[i][j], g[i][k] + g[k][j]);	
					}
				}
			}
		}
		
		int nq = in.nextInt();
		for (int i = 0; i < nq; i++) {
			int a = in.nextInt() -1;
			int b = in.nextInt() -1;
			if (a == b) {
				out.println(0);
			} else {
				out.println(g[a][b] == Integer.MAX_VALUE ? -1 : g[a][b]);
			}
		}
	}
	
	public static void chocolateInBox() {
		int n = in.nextInt();
		long[] a = in.nextLongArray(n);
		out.println(chocolateInBox(n, a));
	}
	
	public static long chocolateInBox(int n, long[] a) {
		long ret = 0;
		if (n == 1) {
			return 1;
		} else if (n == 2) {
			if (a[0] == a[1]) {
				return 0;
			} else {
				return 1;
			}
		} else {
			long x = 0;
			for (int i = 0; i < a.length; i++) {
				x ^= a[i];
			}
			
			if (x == 0) {
				return 0;
			} else {
				int idx = 0;
				for (int i = 63; i  >= 0; i--) {
					if (getBit(x, i)) {
						idx = i;
						break;
					}
				}
				
				ret = 0;
				for (int i = 0; i < a.length; i++) {
					if (getBit(a[i], idx)) {
						ret++;
					}
				}
				
				return ret;
			}
		}
	}
	
	public static int clearBit(int x, int i) {
		return (x & ~(1 << i));
	}
	
	public static int setBit(int x, int i) {
		return (x | (1 << i));
	}
	
	public static boolean getBit(int x, int i) {
		return (x & (1 << i)) != 0;
	}
	
	public static boolean getBit(long x, int i) {
		return (x & (1 << i)) != 0;
	}
	
	public static void permutationGame() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = in.nextInt();
			int[] a = in.nextIntArray(n);
			int[] state = new int[(1 << n)];
			state[0] = 1;
			
			for (int i = 0; i < state.length; i++) {
				if (state[i] == 1) { // reachable
					int prev = -1;
					boolean sorted = true;
					for (int j = 0; j < n; j++) {
						if (!getBit(i, j)) {
							if (a[j] > prev) {
								prev = a[j];
							} else {
								sorted = false;
								break;
							}
						}
					}
					if (sorted) {
						state[i] = 2; // terminal
					} else {
						for (int j = 0; j < n; j++) {
							if (state[setBit(i, j)] == 0) {
								state[setBit(i, j)] = 1;
							}
						}
					}
				}
			}
			
			int[] sg = new int[(1 << n)];
			for (int i = state.length-1; i >= 0; i--) {
				if (state[i] == 2) {
					sg[i] = 2; // P
				} else if (state[i] == 1) {
					int numP = 0;
					for (int j = 0; j < n; j++) {
						if (sg[setBit(i, j)] == 2) {
							numP++;
						}
					}
					if (numP > 0) {
						sg[i] = 1;
					} else {
						sg[i] = 2; // P
					}
				}
			}
			
			if (sg[0] == 2) {
				System.out.println("Bob");
			} else if (sg[0] == 1)  {
				System.out.println("Alice");
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
