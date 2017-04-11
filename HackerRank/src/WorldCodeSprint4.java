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
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

// WorldCodeSprint4

public class WorldCodeSprint4 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
		gridlandProvinces();
//		johnland();
//		testaorb2();
//		testaorb();
//		aorb();
//		minimumDistances();
//		equalStacks();
		
		out.close();
	}
	
	public static void gridlandProvinces() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = in.nextInt();
			char[][] x = new char[2][];
			int[][] g = new int[2][n];
			x[0] = in.next().toCharArray();
			x[1] = in.next().toCharArray();
			for (int i = 0; i < g.length; i++) {
				for (int j = 0; j < g[0].length; j++) {
					g[i][j] = (int) (x[i][j] - 'a');
				}
			}
			System.out.println(gridlandProvinces(n, g));
		}
	}
	
	static int[] dy = {0, 1, 0, -1}; // > v < ^
	static int[] dx = {1, 0, -1, 0};
	
	static int[] dyLeft = {0,  1, -1,  0};
	static int[] dxLeft = {-1, 0,  0, -1};

	static int[] dyRight = {0, 1, -1,  0};
	static int[] dxRight = {1, 0,  0, -1};
	public static int gridlandProvinces(int N, int[][] g) {
		HashSet<String> set = new HashSet<>();
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < 2; y++) {
				for (int clock = 0; clock < 2; clock++) {
					
					int[] path = new int[2*N];
					path[0] = g[y][x];
					boolean[][] visited = new boolean[2][N];
					visited[y][x] = true; 

					getPath(N, x, y, clock, g, 1, path, visited);
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < path.length; i++) {
						sb.append((char)(path[i] + 'a'));
					}
					set.add(sb.toString());
					System.out.println(sb.toString());
				}
			}
		}
		System.out.println("\nnext\n");
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < N; x ++) {
				if (x > 0 && N > 2) {
					x = N-1;
				}
				for (int clock = 0; clock < 2; clock++) {
					for (int zig = 1; zig < N*2; zig++) {
						int[] dyd = dyLeft;
						int[] dxd = dxLeft;
						boolean[][] visited = new boolean[2][N];
						if (x > 0) {
							dyd = dyRight;
							dxd = dxRight;
						}
						int inc = 1;
						int dir = 0;
						if (y == 1) {
							dir = 2;
						}
						int[] path = new int[2*N];
						int t = 0;
						int px = x;
						int py = y;
						path[t] = g[py][px];
						visited[py][px] = true; 
						t++;
						while (t <= zig) {
							int nx = px + dxd[(dir + (1 << 16)) % 4];
							int ny = py + dyd[(dir + (1 << 16)) % 4];
							int att = 0;
							while (!(nx >= 0 && nx < N && ny >= 0 && ny < 2 && !visited[ny][nx])) {
								dir += inc;
								nx = px + dxd[(dir + (1 << 16)) % 4];
								ny = py + dyd[(dir + (1 << 16)) % 4];
								att++;
								if (att > 4) {
									break;
								}
							}
							if (att > 4) {
								break;
							}
							py = ny;
							px = nx;
							visited[ny][nx] = true;
							path[t] = g[py][px];
							t++;
						}
						getPath(N, px, py, clock, g, t, path, visited);
						
						int steps = 0;
						for (int i = 0; i < visited.length; i++) {
							for (int j = 0; j < visited[0].length; j++) {
								if (visited[i][j]) {
									steps++;
								}
							}
						}
						
						if (steps == N*2) {
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < path.length; i++) {
								sb.append((char)(path[i] + 'a'));
							}
							set.add(sb.toString());
							System.out.println(sb.toString());
						}
					}
				}
			}
		}
		return set.size();
	}

	private static void getPath(int N, int x, int y, int clock, int[][] g, int t, int[] path, boolean[][] visited) {
		int inc = 1;
		int dir = 0;
		if (y == 1) {
			dir = 2;
		}
		if (clock == 1) {
			inc = -1;
			dir = (dir + 2) % 4;
		}
		int px = x;
		int py = y;
		while (t < path.length) {
			int nx = px + dx[(dir + (1 << 16)) % 4];
			int ny = py + dy[(dir + (1 << 16)) % 4];

			int att = 0;
			while (!(nx >= 0 && nx < N && ny >= 0 && ny < 2 && !visited[ny][nx])) {
				dir += inc;
				nx = px + dx[(dir + (1 << 16)) % 4];
				ny = py + dy[(dir + (1 << 16)) % 4];
				if (att > 4) {
					break;
				}
			}
			if (att > 4) {
				break;
			}
			py = ny;
			px = nx;
			visited[ny][nx] = true;
			path[t] = g[py][px];
			t++;
		}
	}

	public static void johnland() {
		int n = in.nextInt(); // vertices
		int m = in.nextInt(); // edges
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Integer>());
			cost.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			int c = in.nextInt();
			g.get(a).add(b);
			g.get(b).add(a);
			cost.get(a).add(c);
			cost.get(b).add(c);
		}
		
		johnland(n, m, g, cost);
	}
	
	public static void johnland(int N, int M, ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> cost) {
		HashMap<Pair<Integer, Integer>, Integer> m = primMST(g, cost);
		int[] subtreeSize = new int[N];
		
		ArrayList<ArrayList<Integer>> mst = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			mst.add(new ArrayList<Integer>());
		}
		for (Pair<Integer, Integer> p: m.keySet()) {
			mst.get(p.a).add(p.b);
			mst.get(p.b).add(p.a);
		}
		
		subTreeSize(mst, subtreeSize);
		
		long[] bin = new long[M*2];
		for (Entry<Pair<Integer, Integer>, Integer> e: m.entrySet()) {
			long childSize = Math.min(subtreeSize[e.getKey().a], subtreeSize[e.getKey().b]);
			long used = (N - childSize) * childSize;
			bin[e.getValue()] = used;
		}
		int lastOne = 0;
		for (int i = 0; i < bin.length-1; i++) {
			bin[i+1] += bin[i]/2;
			bin[i] = bin[i] % 2;
			if (bin[i] > 0) {
				lastOne = i;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = lastOne; i >= 0; i--) {
			sb.append(bin[i]);
		}
		out.println(sb.toString());
	}
	
	public static void subTreeSize(ArrayList<ArrayList<Integer>> g, int[] subtreeSize) {
		int N;
		int root = 0;
		int maxDepth = 0;
		
		// With respect to a node
		int[] depth;
		int[] parent;
		
		// With respect to a chain
		
		N = g.size();
		parent = new int[N]; 	// -1 for root
		depth = new int[N];	// 0 for root node

		// Find the node in the middle of the graph and set it as the root if no root is given. 
		Arrays.fill(depth, -1);
		Arrays.fill(parent, -1);
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			if (g.get(i).size() == 1) {
				queue.add(i);
			}
		}
		
		queue.clear();
		depth[root] = 0;
		queue.add(root);
		
		// Set depth and parent
		while (!queue.isEmpty()) {
			int top = queue.poll();
			ArrayList<Integer> children = g.get(top);
			for (int i = 0; i < children.size(); i++) {
				int childIdx = children.get(i);
				if (depth[childIdx] == -1) {
					parent[childIdx] = top;
					depth[childIdx] = depth[top]+1;
					maxDepth = Math.max(maxDepth, depth[childIdx]);
					queue.add(childIdx);
				}
			}
		}
		
		// Set subtree size
		Arrays.fill(subtreeSize, 1);
		HashMap<Integer, ArrayList<Integer>> nodesPerDepth = new HashMap<>();
		for (int i = 0; i < N; i++) {
			if (depth[i] != -1) {
				if (!nodesPerDepth.containsKey(depth[i])) {
					nodesPerDepth.put(depth[i], new ArrayList<Integer>());
				}
				nodesPerDepth.get(depth[i]).add(i);
			}
		}
		for (int i = maxDepth; i > 0; i--) {
			ArrayList<Integer> list = nodesPerDepth.get(i);
			for (int j = 0; j < list.size(); j++) {
				subtreeSize[parent[list.get(j)]] += subtreeSize[list.get(j)];
			}
		}
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

	public static void testaorb2() {
		int len = 12;
		int size = 1 << len;
		
		for (int t = 0; t < 10000; t++) {
			int a = ra.nextInt(size);
			int b = ra.nextInt(size);
			int c = ra.nextInt(size);
			int k = ra.nextInt(size*3);
			
			char[] ra1 = new char[len];
			char[] rb1 = new char[len];
			Arrays.fill(ra1, '0');
			Arrays.fill(rb1, '0');
			boolean r1 = aorbSlow(len, toHexString(a), toHexString(b), toHexString(c), ra1, rb1, k);
			
			char[] ra2 = new char[len];
			char[] rb2 = new char[len];
			Arrays.fill(ra2, '0');
			Arrays.fill(rb2, '0');
			boolean r2 = aorb(len, toHexString(a), toHexString(b), toHexString(c), ra2, rb2, k);
			
			if (r1 != r2 || (r1 == r2 && r1 == false) && (!Arrays.equals(ra1, ra2) || !Arrays.equals(rb1, rb2))) {
				System.out.println("fail " + a + " " + b + " " + c + " " + k);
				r1 = aorbSlow(len, toHexString(a), toHexString(b), toHexString(c), ra1, rb1, k);
				r2 = aorb(len, toHexString(a), toHexString(b), toHexString(c), ra2, rb2, k);
			}
		}
	}
	
	public static void testaorb() {
		int len = 6;
		int size = 1 << len;
		for (int a = 0; a < size; a++) {
			for (int b = 0; b < size; b++) {
				for (int c = 0; c < size; c++) {
					for (int k = 0; k < size; k++) {
						char[] ra1 = new char[len];
						char[] rb1 = new char[len];
						Arrays.fill(ra1, '0');
						Arrays.fill(rb1, '0');
						boolean r1 = aorbSlow(len, toHexString(a), toHexString(b), toHexString(c), ra1, rb1, k);
						
						char[] ra2 = new char[len];
						char[] rb2 = new char[len];
						Arrays.fill(ra2, '0');
						Arrays.fill(rb2, '0');
						boolean r2 = aorb(len, toHexString(a), toHexString(b), toHexString(c), ra2, rb2, k);
						
						if (r1 != r2 || (r1 == r2 && r1 == false) && (!Arrays.equals(ra1, ra2) || !Arrays.equals(rb1, rb2))) {
							System.out.println("fail");
							r1 = aorbSlow(len, toHexString(a), toHexString(b), toHexString(c), ra1, rb1, k);
							r2 = aorb(len, toHexString(a), toHexString(b), toHexString(c), ra2, rb2, k);
						}
					}
				}
			}
		}
	}
	
	public static String toHexString(int x) {
		char[] s = Integer.toHexString(x).toCharArray();
		for (int i = 0; i < s.length; i++) {
			if (s[i] >= 'a') {
				s[i] = (char) ((char)(s[i] - 'a') + 'A');
			}
		}
		return new String(s);
	}

	public static boolean aorbSlow(int size, String as, String bs, String cs, char[] newA, char[] newB, int k) {
		int a = Integer.parseInt(as, 16);
		int b = Integer.parseInt(bs, 16);
		int c = Integer.parseInt(cs, 16);
		
		int max = (1 << (32 - Integer.numberOfLeadingZeros(Math.max(Math.max(a, b), c) - 1))) + 1;
		int min = Integer.MAX_VALUE;
		int bestA = Integer.MAX_VALUE;
		int bestB = Integer.MAX_VALUE;
		for (int na = 0; na < max; na++) {
			for (int nb = 0; nb < max; nb++) {
				if ((na | nb) == c) {
					int ch = Integer.bitCount(na ^ a) + Integer.bitCount(nb ^ b);
					boolean update = false;
					if (ch <= k) {
						if (min == Integer.MAX_VALUE) {
							update = true;
						}
						if (na < bestA) {
							update = true;
						} else if (na == bestA) {
							if (nb < bestB) {
								update = true;
							}
						}
					}
					if (update) {
						min = ch;
						bestB = nb;
						bestA = na;
						char[] tA = String.format("%0"+size+"X", na).toCharArray();
						char[] tB = String.format("%0"+size+"X", nb).toCharArray();
						for (int i = 0; i < tA.length; i++) {
							newA[i] = tA[i];
							newB[i] = tB[i];
						}
					}
				}
			}
		}
		return min > k;
	}

	public static int[][][][] pre = new int[16][16][16][3]; // ops, new a, new b
	static {
		for (int a = 0; a < 16; a++) {
			for (int b = 0; b < 16; b++) {
				for (int c = 0; c < 16; c++) {
					int k = 0;
					int newA = a;
					int newB = b;
					for (int i = 0; i < 4; i++) {
						if ((getBit(a, i) || getBit(b, i)) != getBit(c, i)) {
							if (getBit(c, i)) {
								newB = setBit(newB, i);
								k++;
							} else {
								if (getBit(a, i)) {
									newA = clearBit(newA, i);
									k++;
								}
								if (getBit(b, i)) {
									newB = clearBit(newB, i);
									k++;
								}
							}
						}
					}
					int[] p = pre[a][b][c];
					p[0] = k;
					p[1] = newA;
					p[2] = newB;
					if ((newB | newA) != c) {
						System.out.println("fail");
					}
				}
			}
		}
	}

	public static void aorb() {
		
		int qs = in.nextInt();
		for (int q = 0; q < qs; q++) {
			int k = in.nextInt();
			String as = in.next();
			String bs = in.next();
			String cs = in.next();

			int size = Math.max(as.length(), Math.max(bs.length(), cs.length()));
			char[] newA = new char[size];
			Arrays.fill(newA, '0');
			char[] newB = new char[size];
			Arrays.fill(newB, '0');
			
			boolean invalid = aorb(size, as, bs, cs, newA, newB, k);
			
			if (invalid) {
				out.println("-1");
			} else {
				print16(newA);
				print16(newB);
			}
		}
	}
	
	public static boolean aorb(int size, String as, String bs, String cs, char[] newA, char[] newB, int k) {
		char[] a = getArray(size, as);
		char[] b = getArray(size, bs);
		char[] c = getArray(size, cs);
		
		boolean invalid = false;
		for (int i = size-1; i >= 0; i--) {
			int[] ref = pre[from16(a[i])][from16(b[i])][from16(c[i])];
			k -= ref[0];
			newA[i] = to16(ref[1]);
			newB[i] = to16(ref[2]);
			
			if (k < 0) {
				invalid = true;
				break;
			}
		}
		
		for (int i = 0; i < size; i++) {
			int na = from16(newA[i]);
			int nb = from16(newB[i]);
			for (int j = 3; j >= 0; j--) {
				if (getBit(na, j)) {
					if (!getBit(nb, j) && k >= 2) {
						k -= 2;
						na = clearBit(na, j);
						nb = setBit(nb, j);
					} else if (getBit(nb, j) && k >= 1) {
						k -= 1;
						na = clearBit(na, j);
					}
				}
			}
			newA[i] = to16(na);
			newB[i] = to16(nb);
		}
		return invalid;
	}

	private static char[] getArray(int size, String as) {
		char[] a = new char[size];
		
		for (int i = size-1; i >= 0; i--) {
			int idx = i - (size - as.length());
			if (idx < 0) {
				a[i] = '0';
			} else {
				a[i] = as.charAt(i - (size - as.length()));
			}
		}
		return a;
	}

	public static void print16(char[] newA) {
		boolean leadingZ = true;
		for (int i = 0; i < newA.length; i++) {
			if ((leadingZ && newA[i] != '0') || i == newA.length-1) {
				leadingZ = false;
			}
			if (!leadingZ) {
				out.print(newA[i]);
			}
		}
		out.println();
	}

	public static int from16(char x) {
		if (x <= '9') {
			return x - '0';
		}
		return (x - 'A') + 10;
	}
	
	public static char to16(int x) {
		if (x <= 9) {
			return (char) (x + '0');
		}
		return (char) (((x - 10) + 'A'));
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
	
	public static void minimumDistances() {
		int as = in.nextInt();
		int bs = in.nextInt();
		int cs = in.nextInt();
		int[][] a = new int[3][];
		a[0] = in.nextIntArray(as);
		a[1] = in.nextIntArray(bs);
		a[2] = in.nextIntArray(cs);
		
		int max = -1;
		Counter<Integer> count = new Counter<>();
		
		for (int i = 0; i < a.length; i++) {
			int h = 0;
			count.add(h);
			for (int j = 0; j < a[i].length; j++) {
				h += a[i][a[i].length - j - 1];
				count.add(h);
			}
		}
		
		for (Integer k: count.keySet()) {
			if (count.get(k) == 3) {
				max = Math.max(max, k);
			}
		}
		System.out.println(max);
	}
	
	public static void easyGcd() {
		int n = in.nextInt();
		int[] a = in.nextIntArray(n);
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			for (int j = i+1; j < a.length; j++) {
				if (a[i] == a[j]) {
					min = Math.min(min, Math.abs(i - j));
				}		
			}
		}
		if (min == Integer.MAX_VALUE) {
			min = -1;
		}
		System.out.println(min);
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
