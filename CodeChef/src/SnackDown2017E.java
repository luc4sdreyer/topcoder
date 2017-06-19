import java.io.*;
import java.util.*;

public class SnackDown2017E {
	public static InputReader in;
	public static PrintWriter out;
	public static final boolean debug = false;

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, false);

		// PLUSMUL();
		ANCESTOR();

		out.close();
	}

	public static void ANCESTOR() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = in.nextInt();
			Tree[] nodes1 = getTree(n);
			Tree[] nodes2 = getTree(n);
			LCA lca = new LCA(n, nodes2);
			
			dfs(nodes1, nodes2, 0, lca);
			for (int i = 0; i < nodes1.length; i++) {
				System.out.print(nodes1[i].matchingAncestors + " ");
			}
			System.out.println();
		}
	}

	public static void dfs(Tree[] nodes1, Tree[] nodes2, int idx, LCA lca) {
		Tree parent = nodes1[idx];
		if (parent.children.isEmpty()) {
			return;
		}
		for (Tree child: nodes1[idx].children) {
			child.matchingAncestors = parent.matchingAncestors;
			
			// in tree 2: is parent.id among child.id's ancestors?
			int ancestor = lca.query(child.id, parent.id);
			if (ancestor == child.id || ancestor == parent.id) {
				child.matchingAncestors++;
			}
			dfs(nodes1, nodes2, child.id, lca);
		}
	}

	private static Tree[] getTree(int n) {
		int offset = -1;
		Tree[] nodes1 = new Tree[n];
		Tree root1 = new Tree(0);
		nodes1[0] = root1; 
		for (int i = 1; i < n; i++) {
			int a = in.nextInt() -1;
			int b = in.nextInt() -1;
			nodes1[b] = nodes1[a].addChild(b);
		}
		return nodes1;
	}

	public static class Tree {
		Tree parent;
		ArrayList<Tree> children;
		int id;
		int matchingAncestors = 0;
		int level = 0;
		public Tree(int id) {
			children = new ArrayList<>();
			this.id = id;
			parent = null;
		}

		public Tree addChild(int id) {
			Tree child = new Tree(id);
			children.add(child);
			child.parent = this;
			child.level = this.level+1;
			return child;
		}

		public String toString() {
			return id + "";
		}
	}
	
	/*******************************************************************************************************************************
	 * Lowest common ancestor in O(log(N)) time. O(N) preprocessing.
	 * 
	 * Takes 0-based array of Tree nodes as input.
	 * 
	 * Based on https://www.topcoder.com/community/data-science/data-science-tutorials/range-minimum-query-and-lowest-common-ancestor/
	 */
	public static class LCA {
		int N;
		int log2N;
		int[] parent;
		int[] level;
		int[][] ancestor;

		public LCA(int N, Tree[] tree) {
			this.N = N;
			this.log2N = 32 - Integer.numberOfLeadingZeros(N-1) +1;
			
			this.parent = new int[N];
			this.level = new int[N];
			this.ancestor = new int[N][log2N];
			
			int i, j;
			
			// Set up the parent array
			parent[0] = - 1;
			for (i = 1; i < N; i++) {
				parent[i] = tree[i].parent.id;
			}
			
			// Set up the level array
			for (i = 0; i < N; i++) {
				level[i] = tree[i].level;
			}

			// Set up the ancestor array
			// Initialise every element in P with -1
			for (i = 0; i < N; i++) {
				for (j = 0; 1 << j < N; j++) {
					ancestor[i][j] = -1;
				}
			}

			// The first ancestor of every node i is T[i]
			for (i = 0; i < N; i++) {
				ancestor[i][0] = parent[i];
			}

			// Bottom up dynamic programming
			for (j = 1; 1 << j < N; j++) {
				for (i = 0; i < N; i++) {
					if (ancestor[i][j - 1] != -1) {
						ancestor[i][j] = ancestor[ancestor[i][j - 1]][j - 1];
					}
				}
			}
		}
		
		/**
		 * Return the LCA nodes p and q. 
		 */
		public int query(int p, int q) {
			int tmp, log, i;

			// If p is situated on a higher level than q then we swap them
			if (level[p] < level[q]) {
				tmp = p;
				p = q;
				q = tmp;
			}

			// We compute the value of [log(L[p)]
			for (log = 1; 1 << log <= level[p]; log++);
			log--;

			// We find the ancestor of node p situated on the same level,
			// with q using the values in P.
			for (i = log; i >= 0; i--) {
				if (level[p] - (1 << i) >= level[q]) {
					p = ancestor[p][i];
				}
			}

			if (p == q) {
				return p;
			}

			// We compute LCA(p, q) using the values in P
			for (i = log; i >= 0; i--) {
				if (ancestor[p][i] != -1 && ancestor[p][i] != ancestor[q][i]) {
					p = ancestor[p][i];
					q = ancestor[q][i];
				}
			}

			return parent[p];
		}
	}

	public static void PLUSMUL() {
		final long mod = 1000 * 1000 * 1000 + 7;
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = in.nextInt();
			long[] a = in.nextLongArray(n);

			if (n == 1) {
				System.out.println(a[0] % mod);
				continue;
			}

			long sum = 0;
			long powTemp = 1;
			long[] pow = new long[n];
			for (int i = 0; i < n - 2 - n / 2; i++) {
				powTemp = (powTemp * 2L) % mod;
			}

			for (int i = a.length / 2; i >= 0; i--) {
				pow[n - i - 1] = powTemp;
				pow[i] = powTemp;
				powTemp = (powTemp * 2L) % mod;
			}

			for (int i = 0; i < n; i++) {
				sum = ((a[i] * pow[i] * 1) + sum) % mod;
			}

			powTemp = 0;
			long prod = 0;
			long prev = a[0];
			long[] mem = new long[n];
			for (int i = 1; i < n; i++) {
				prev = (prev * a[i] + ((a[i - 1] * a[i]) % mod) * powTemp)
						% mod;
				prod = (prod + prev) % mod;
				if (i >= 2) {
					prod = (prod + mem[i - 2]) % mod;
				}
				if (i == 1) {
					powTemp = 1;
				}
				if (i > 1) {
					powTemp = (powTemp * 2L) % mod;
				}
				if (i > 0) {
					mem[i] = (prod + mem[i - 1]) % mod;
					// mem[i] = prod;
				} else {
					mem[i] = prod;
				}
				// System.out.println(prod);
			}

			long total = (prod + sum) % mod;
			System.out.println(total);
		}
	}

	public static class InputReader {
		public BufferedReader r;
		public StringTokenizer st;

		public InputReader(InputStream s) {
			r = new BufferedReader(new InputStreamReader(s), 32768);
			st = null;
		}

		public String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(r.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextLong();
			}
			return a;
		}

		public int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextInt();
			}
			return a;
		}
	}
}
