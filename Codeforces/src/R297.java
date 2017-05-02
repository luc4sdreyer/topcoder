import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;


public class R297 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, false);

		v3();

		out.close();
	}
	public static void v4_2() {
		int[] dx4 = {1, 0, -1, 0};
		int[] dy4 = {0, -1, 0, 1};
		
		int[] dx = {1, 1, 1, 0, -1, -1, -1, 0};
		int[] dy = {1, 0, -1, -1, -1, 0, 1, 1};
		
		int Y = in.nextInt();
		int X = in.nextInt();
		char[][] s = new char[Y][];
		for (int i = 0; i < s.length; i++) {
			s[i] = in.next().toCharArray();
		}
		int[][] map = new int[Y][X];
		for (int y = 0; y < Y; y++) {
			int[] temp = new int[X];
			for (int x = 0; x < X; x++) {
				if (s[y][x] == '.') {
					temp[x] = 1;
				} else {
					temp[x] = 0;
				}
			}	
			map[y] = temp;
		}

		boolean wallMod = true;

		while (wallMod) {
			wallMod = false;
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if (map[y][x] == 0) {

						// Declare stack of nodes
						Stack<NodeDfs> queue = new Stack<NodeDfs>();
						NodeDfs top = new NodeDfs(x, y);
						queue.add(top);
						
						while (!queue.isEmpty()) {
							top = queue.pop();

							if (top.x < 0 || top.x >= X) continue;
							if (top.y < 0 || top.y >= map.length) continue;
							if (map[top.y][x] == 1) continue;
							
//							int blockCount = 0;
//							for (int i = 0; i < dx4.length; i++) {
//								int nx = top.x + dx[i];
//								int ny = top.y + dy[i];
//								if (nx >= 0 && nx < X && ny >= 0 && ny < Y && map[ny][nx] == 0) {
//									blockCount = 1;
//									break;
//								}
//							}
//							if (blockCount > 0) {
//								continue;
//							}
							
							for (int startY = -1; startY <= 0; startY++) {
								for (int startX = -1; startX <= 0; startX++) {
									int wallPoints = 0;
									int steps = 0;
									NodeDfs wall = null;
									for (int y2 = top.y + startY; y2 < top.y + 2 + startY && y2 >= 0 && y2 < Y; y2++) {
										for (int x2 = top.x + startX; x2 < top.x + 2 + startX && x2 >= 0 && x2 < X; x2++) {
											steps++;
											if (map[y2][x2] == 0) {
												wallPoints++;
												wall = new NodeDfs(x2, y2);
											}
										}
									}
									if (wallPoints == 1 && steps == 4) {
										// break walls
										//wallMod = true;
										map[wall.y][wall.x] = 1;
										for (int i = 0; i < dx.length; i++) {
											queue.add(new NodeDfs(wall.x + dx[i], wall.y + dy[i]));
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		for (int y = 0; y < Y; y++) {
			char[] output = new char[X];
			for (int x = 0; x < X; x++) {
				if (map[y][x] == 1) {
					output[x] = '.';
				} else {
					output[x] = '*';
				}
			}
			out.println(new String(output));
		}
	}

	public static void v4() {
		int Y = in.nextInt();
		int X = in.nextInt();
		char[][] s = new char[Y][];
		for (int i = 0; i < s.length; i++) {
			s[i] = in.next().toCharArray();
		}
		SegmentTreeLazy[] map = new SegmentTreeLazy[Y];
		for (int y = 0; y < Y; y++) {
			int[] temp = new int[X];
			for (int x = 0; x < X; x++) {
				if (s[y][x] == '.') {
					temp[x] = 1;
				} else {
					temp[x] = 0;
				}
			}	
			map[y] = new SegmentTreeLazy(temp);
		}

		boolean wallMod = true;
		int wallCount = 0;

		while (wallMod) {
			HashSet<Integer> invalidatedColours = new HashSet<>();
			wallMod = false;
			PriorityQueue<SearchResult> q = new PriorityQueue<>();
			int colour = 0;
			int[][] colourMap = new int[Y][X];
			for (int y = 0; y < Y; y++) {
				for (int x = 0; x < X; x++) {
					if (map[y].query_tree(x,x) == 1 && colourMap[y][x] == 0) {
						int space = 0;
						colour++;
						int[] minMaxX = {X, 0};
						int[] minMaxY = {Y, 0};

						// Declare stack of nodes
						Stack<NodeDfs> stack = new Stack<NodeDfs>();
						NodeDfs start = new NodeDfs(x, y);
						stack.push(start);

						while (!stack.isEmpty()) {
							NodeDfs top = stack.pop();

							if (top.x < 0 || top.x >= X) continue;
							if (top.y < 0 || top.y >= map.length) continue;
							if (map[top.y].query_tree(top.x,top.x) == 0) continue;
							if (colourMap[top.y][top.x] != 0) continue;

							colourMap[top.y][top.x] = colour;

							minMaxX[0] = Math.min(minMaxX[0], top.x);
							minMaxX[1] = Math.max(minMaxX[1], top.x);
							minMaxY[0] = Math.min(minMaxY[0], top.y);
							minMaxY[1] = Math.max(minMaxY[1], top.y);

							space++;

							// visit every adjacent node
							stack.push(new NodeDfs(top.x + 1, top.y));
							stack.push(new NodeDfs(top.x - 1, top.y));
							stack.push(new NodeDfs(top.x, top.y + 1));
							stack.push(new NodeDfs(top.x, top.y - 1));
						}

						int rectSpace = (minMaxY[1] - minMaxY[0] + 1) * (minMaxX[1] - minMaxX[0] + 1);
						q.add(new SearchResult(start, space, rectSpace, colour, minMaxX, minMaxY));
					}
				}
			}

			while (!q.isEmpty()) {
				SearchResult res = q.poll();

				if (res.space != res.rectSpace && !invalidatedColours.contains(res.colour)) {
					// break walls

					for (int y = res.minMaxY[0]; y <= res.minMaxY[1]; y++) {
						if (map[y].query_tree( res.minMaxX[0], res.minMaxX[1]) !=  res.minMaxX[1] - res.minMaxX[0] +1) {
							map[y].set_tree(res.minMaxX[0], res.minMaxX[1], 1);
						}
					}
				}		
			}
		}
		char[][] output = new char[Y][X];
		for (int y = 0; y < Y; y++) {
			for (int x = 0; x < X; x++) {
				if (map[y].query_tree(x,x) == 1) {
					output[y][x] = '.';
				} else {
					output[y][x] = '*';
				}
			}
			out.println(new String(output[y]));
		}
//		out.println(wallCount);
	}

	static class SearchResult implements Comparable<SearchResult> {
		NodeDfs start;
		int space;
		int rectSpace;
		int colour;
		int[] minMaxX;
		int[] minMaxY;
		public SearchResult(NodeDfs start, int space, int rectSpace, int colour, int[] minMaxX, int[] minMaxY) {
			super();
			this.start = start;
			this.space = space;
			this.colour = colour;
			this.rectSpace = rectSpace;
			this.minMaxX = minMaxX;
			this.minMaxY = minMaxY;
		}
		@Override
		public int compareTo(SearchResult o) {
			return Integer.compare(o.rectSpace, rectSpace);
		}

	}

	private static class NodeDfs {
		public int x, y;
		public NodeDfs (int x, int y) {
			this.x = x;this.y = y;
		}
	}
	/**
	 * Segment tree with lazy updates. The default implementation is the max function.
	 * 
	 * Lazy updates are a bit of a hack, because they might to be changed if the function changes.
	 * For example with the max function a lazy parent node the update is just added, but with the sum function
	 * the update must be multiplied by the number of children.
	 */

	public static class SegmentTreeLazy {
		protected long tree[];
		protected long lazy_update[];
		protected long lazy_set[];
		private int N;
		private int MAX;
		protected long neg_inf = 0;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 * If one of the inputs are neg_inf the function should ignore them. If both are, it should probably return neg_inf.
		 */
		protected long function(long a, long b) {
			return a + b;
		}

		public SegmentTreeLazy(int[] a) {
			N = 1 << (int) (Math.log10(a.length)/Math.log10(2))+1;
			MAX = N*2;

			int[] arr = new int[N];
			for (int i = 0; i < a.length; i++) {
				arr[i] = a[i];
			}

			tree = new long[MAX];
			lazy_update = new long[MAX];
			lazy_set = new long[MAX];
			Arrays.fill(lazy_set, Long.MIN_VALUE);
			build_tree(1, 0, N-1, arr);
			Arrays.fill(lazy_update, 0);
		}
		
		/**
		 * Build and initialize tree
		 */
		private void build_tree(int node, int a, int b, int[] arr) {
			if (a > b) {
				return; // Out of range
			}

			if (a == b) { // Leaf node
				tree[node] = arr[a]; // Initialize value
				return;
			}

			build_tree(node*2, a, (a+b)/2, arr); // Initialize left child
			build_tree(node*2+1, 1+(a+b)/2, b, arr); // Initialize right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Initialize root value
		}

		/**
		 * Increment elements within range [i, j] with value value
		 */
		public void update_tree(int i, int j, int value) {
			update_tree(1, 0, N-1, i, j, value);
		}

		protected void update_tree(int node, int a, int b, int i, int j, int value) {
			if (lazy_update[node] != 0 || lazy_set[node] != Long.MIN_VALUE) {
				propogate_lazy_updates(node, a, b);
				propogate_lazy_sets(node, a, b);
			}
			
			if (a > b || a > j || b < i) { // Current segment is not within range [i, j]
				return;
			}

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] += value;

				if (a != b) { // Not leaf node
					lazy_update[node*2] += value;
					lazy_update[node*2 + 1] += value;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}
		
		/**
		 * Sets elements within range [i, j] to the specified value. Setting to Long.MIN_VALUE is not supported.
		 */
		public void set_tree(int i, int j, int value) {
			set_tree(1, 0, N-1, i, j, value);
		}

		protected void set_tree(int node, int a, int b, int i, int j, int value) {
			if (lazy_update[node] != 0 || lazy_set[node] != Long.MIN_VALUE) {
				propogate_lazy_updates(node, a, b);
				propogate_lazy_sets(node, a, b);
			}

			if (a > b || a > j || b < i) { // Current segment is not within range [i, j]
				return;
			}

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] = value;

				if (a != b) { // Not leaf node
					lazy_set[node*2] = value;
					lazy_set[node*2 + 1] = value;
				}
				return;
			}

			set_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			set_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		/**
		 * Query tree to get max element value within range [i, j]
		 */
		public long query_tree(int i, int j) {
			return query_tree(1, 0, N-1, i, j);
		}

		/**
		 * All bounds are inclusive.
		 * @param node The tree index
		 * @param a Internal query's lower bound
		 * @param b Internal query's upper bound
		 * @param i External query's lower bound
		 * @param j External query's upper bound
		 */
		protected long query_tree(int node, int a, int b, int i, int j) {
			if (a > b || a > j || b < i) {
				return neg_inf; // Out of range
			}

			if (lazy_update[node] != 0 || lazy_set[node] != Long.MIN_VALUE) {
				propogate_lazy_updates(node, a, b);
				propogate_lazy_sets(node, a, b);
			}

			if (a >= i && b <= j) { // Current segment is totally within range [i, j]
				return tree[node];
			}

			long q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			long q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			long res = function(q1, q2); // Return final result
			return res;
		}
		
		protected void propogate_lazy_updates(int node, int a, int b) {
			if (lazy_update[node] != 0) { // This node needs to be updated
				tree[node] += lazy_update[node]; // Update it

				if (a != b) {
					lazy_update[node*2] += lazy_update[node]; // Mark child as lazy
					lazy_update[node*2+1] += lazy_update[node]; // Mark child as lazy
				}

				lazy_update[node] = 0; // Reset it
			}
		}
		
		protected void propogate_lazy_sets(int node, int a, int b) {
			if (lazy_set[node] != Long.MIN_VALUE) { // This node needs to be updated
				tree[node] = lazy_set[node]; // Update it

				if (a != b) {
					lazy_set[node*2] = lazy_set[node]; // Mark child as lazy
					lazy_set[node*2+1] = lazy_set[node]; // Mark child as lazy
				}

				lazy_set[node] = Long.MIN_VALUE; // Reset it
			}
		}
		
		public String toString() {
			return Arrays.toString(this.tree);
		}
	}
	

	public static class SegmentTree {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 * 
		 * t[width == N][height == n] is essentially a binary tree structure with the root at t[0][n], children at t[0][n-1] and t[N/2][n-1], and so forth. 
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return a + b;
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = 0;

		public SegmentTree(int[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new int[N];
			for (int i = 0; i < b.length; i++) {
				this.a[i] = b[i];
			}
			t = new int[N][n+1];
			for (int x = 0; x < N; x++) {
				t[x][0] = a[x];
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
				}
			}
		} 

		/**
		 * Set position i to v. Time: O(log n)
		 */
		public void set(int i, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public int get(int i, int j) {
			int res = IDENTITY, height = 0; j++;
			while (i+(1<<height) <= j) {
				while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			while (i < j) {
				while (i+(1<<height) > j) height--;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			return res;
		}
	}


	public static void v2() {
		char[] s = in.next().toCharArray();
		int n = in.nextInt();
		int[] a = in.nextIntArray(n);

		int[] swapCount = new int[s.length];
		int[] swapSum = new int[s.length];

		for (int i = 0; i < a.length; i++) {
			swapCount[a[i]-1]++;
		}

		swapSum[0] = swapCount[0];
		for (int i = 1; i < swapSum.length; i++) {
			swapSum[i] = swapSum[i-1] + swapCount[i];
		}

		for (int i = 0; i < s.length/2; i++) {
			if (swapSum[i] % 2 == 1) {
				char t = s[i];
				s[i] = s[s.length - i -1];
				s[s.length - i -1] = t;
			}
		}
		out.println(new String(s));
	}

	public static void v1() {
		in.nextInt();
		char[] s = in.next().toCharArray();
		int store = 0;
		int[] chain = new int[26];
		for (int i = 0; i < s.length; i+=2) {
			chain[s[i]-'a']++;
			int door = s[i+1]-'A';
			if (chain[door] > 0) {
				chain[door]--;
			} else {
				store++;
			}
		}
		out.println(store);
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

	public static void v3() {
		int n = in.nextInt();
		int[] a = in.nextIntArray(n);
		Arrays.sort(a);
		int[] stx = new int[1000002];
		for (int i = 0; i < a.length; i++) {
			stx[a[i]]++;
		}
		ArrayList<Long> pairs = new ArrayList<>();
		long total = 0;
		for (int i = stx.length-1; i >= 0; i--) {
			boolean valid = true;
			while (stx[i] > 0 && valid) {
				if (stx[i] >= 2) {
					pairs.add((long) i);
					stx[i] -= 2;
				} else {
					if (i > 0 && stx[i-1] > 0) {
						pairs.add((long) i-1);
						stx[i]--;
						stx[i-1]--;
					} else {
						valid = false;
					}
				}
				if (pairs.size() == 2) {
					total += pairs.get(0) * pairs.get(1);
					pairs.clear();
				}				
			}
		}
		//		System.out.println(Arrays.toString(Arrays.copyOf(stx, a.length*2)));
		out.println(total);
	}
}
