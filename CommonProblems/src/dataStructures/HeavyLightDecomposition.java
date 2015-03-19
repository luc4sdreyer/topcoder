package dataStructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class HeavyLightDecomposition {
	final int root = 0;
	
	int N;
	int LN;
	ArrayList<ArrayList<Integer>> adj;
	ArrayList<ArrayList<Integer>> costs;
	ArrayList<ArrayList<Integer>> indexx;

	int chainNo;
	int ptr;
	
	SegmentTreeVerbose segtree;
	
	int[] baseArray;
	int[] chainInd;
	int[] chainHead;
	int[] posInBase;
	int[] depth;
	int[][] pa;
	int[] otherEnd;
	int[] subsize;
	int[] st;
	int[] qt;
	
	public HeavyLightDecomposition(int N) {
		super();
		this.N = N;
		LN = 32 - Integer.numberOfLeadingZeros(N -1);
		chainNo = 0;
		ptr = 0;
		
		adj = new ArrayList<>();
		costs = new ArrayList<>();
		indexx = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			adj.add(new ArrayList<Integer>());
			costs.add(new ArrayList<Integer>());
			indexx.add(new ArrayList<Integer>());
		}
		
		baseArray = new int[N];
		chainInd = new int[N];
		chainHead = new int[N];
		posInBase = new int[N];
		depth = new int[N];
		pa = new int[N][N];
		otherEnd = new int[N];
		subsize = new int[N];
		
		Arrays.fill(chainHead, -1);
		for (int i = 0; i < pa.length; i++) {
			Arrays.fill(pa[i], -1);
		}
	}
	
	void query_tree(int cur, int s, int e, int S, int E) {
		if(s >= E || e <= S) {
			qt[cur] = -1;
			return;
		}
		if(s >= S && e <= E) {
			qt[cur] = st[cur];
			return;
		}
		int c1 = (cur<<1), c2 = c1 | 1, m = (s+e)>>1;
		query_tree(c1, s, m, S, E);
		query_tree(c2, m, e, S, E);
		qt[cur] = qt[c1] > qt[c2] ? qt[c1] : qt[c2];
	}
		
	/*
	 * LCA:
	 * Takes two nodes u, v and returns Lowest Common Ancestor of u, v
	 */
	int LCA(int u, int v) {
		if(depth[u] < depth[v]) {
			int temp = u;
			u = v;
			v = temp;
		}
		int diff = depth[u] - depth[v];
		for(int i=0; i<LN; i++) if( ((diff>>i)&1) != 0 ) u = pa[i][u];
		if(u == v) return u;
		for(int i=LN-1; i>=0; i--) if(pa[i][u] != pa[i][v]) {
			u = pa[i][u];
			v = pa[i][v];
		}
		return pa[0][u];
	}
	
	/*
	 * query_up:
	 * It takes two nodes u and v, condition is that v is an ancestor of u
	 * We query the chain in which u is present till chain head, then move to next chain up
	 * We do that way till u and v are in the same chain, we query for that part of chain and break
	 */

	int query_up(int u, int v) {
		if(u == v) return 0; // Trivial
		int uchain, vchain = chainInd[v], ans = -1;
		// uchain and vchain are chain numbers of u and v
		while(true) {
			uchain = chainInd[u];
			if(uchain == vchain) {
				// Both u and v are in the same chain, so we need to query from u to v, update answer and break.
				// We break because we came from u up till v, we are done
				if(u==v) break;
				query_tree(1, 0, ptr, posInBase[v]+1, posInBase[u]+1);
				// Above is call to segment tree query function
				if(qt[1] > ans) ans = qt[1]; // Update answer
				break;
			}
			query_tree(1, 0, ptr, posInBase[chainHead[uchain]], posInBase[u]+1);
			// Above is call to segment tree query function. We do from chainHead of u till u. That is the whole chain from
			// start till head. We then update the answer
			if(qt[1] > ans) ans = qt[1];
			u = chainHead[uchain]; // move u to u's chainHead
			u = pa[0][u]; //Then move to its parent, that means we changed chains
		}
		return ans;
	}

	int query(int u, int v) {
		/*
		 * We have a query from u to v, we break it into two queries, u to LCA(u,v) and LCA(u,v) to v
		 */
		int lca = LCA(u, v);
		int ans = query_up(u, lca); // One part of path
		int temp = query_up(v, lca); // another part of path
		if(temp > ans) ans = temp; // take the maximum of both paths
		return ans;
	}

	/*
	 * change:
	 * We just need to find its position in segment tree and update it
	 */
	void change(int i, int val) {
		int u = otherEnd[i];
		segtree.update_tree(posInBase[u], posInBase[u], val);
	}

	/*
	 * Actual HL-Decomposition part
	 * Initially all entries of chainHead[] are set to -1.
	 * So when ever a new chain is started, chain head is correctly assigned.
	 * As we add a new node to chain, we will note its position in the baseArray.
	 * In the first for loop we find the child node which has maximum sub-tree size.
	 * The following if condition is failed for leaf nodes.
	 * When the if condition passes, we expand the chain to special child.
	 * In the second for loop we recursively call the function on all normal nodes.
	 * chainNo++ ensures that we are creating a new chain for each normal child.
	 */
	void HLD(int curNode, int cost, int prev) {
		if(chainHead[chainNo] == -1) {
			chainHead[chainNo] = curNode; // Assign chain head
		}
		chainInd[curNode] = chainNo;
		posInBase[curNode] = ptr; // Position of this node in baseArray which we will use in Segtree
		baseArray[ptr++] = cost;

		int sc = -1, ncost = 0;
		// Loop to find special child
		for(int i=0; i<adj.get(curNode).size(); i++) if(adj.get(curNode).get(i) != prev) {
			if(sc == -1 || subsize[sc] < subsize[adj.get(curNode).get(i)]) {
				sc = adj.get(curNode).get(i);
				ncost = costs.get(curNode).get(i);
			}
		}

		if(sc != -1) {
			// Expand the chain
			HLD(sc, ncost, curNode);
		}

		for(int i=0; i<adj.get(curNode).size(); i++) if(adj.get(curNode).get(i) != prev) {
			if(sc != adj.get(curNode).get(i)) {
				// New chains at each normal node
				chainNo++;
				HLD(adj.get(curNode).get(i), costs.get(curNode).get(i), curNode);
			}
		}
	}

	/*
	 * dfs used to set parent of a node, depth of a node, subtree size of a node
	 */
	void dfs(int cur, int prev, int _depth) {
		pa[0][cur] = prev;
		depth[cur] = _depth;
		subsize[cur] = 1;
		for(int i=0; i<adj.get(cur).size(); i++)
			if(adj.get(cur).get(i) != prev) {
				otherEnd[indexx.get(cur).get(i)] = adj.get(cur).get(i);
				dfs(adj.get(cur).get(i), cur, _depth+1);
				subsize[cur] += subsize[adj.get(cur).get(i)];
			}
	}
	
	void initLCA() {
		for(int i=1; i<LN; i++)
			for(int j=0; j<N; j++)
				if(pa[i-1][j] != -1)
					pa[i][j] = pa[i-1][pa[i-1][j]];
	}


	public static class SegmentTreeVerbose {
		private int tree[];
		private int lazy[];
		private int N;
		private int MAX;
		private int inf = Integer.MAX_VALUE;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return Math.max(a, b);
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = 0;

		public SegmentTreeVerbose(int[] a) {
			N = 1 << (int) (Math.log10(a.length)/Math.log10(2))+1;
			MAX = N*2;

			int[] arr = new int[N];
			for (int i = 0; i < a.length; i++) {
				arr[i] = a[i];
			}

			tree = new int[MAX];
			lazy = new int[MAX];	 
			build_tree(1, 0, N-1, arr);
			Arrays.fill(lazy, 0);
		}
		/**
		 * Build and init tree
		 */
		private void build_tree(int node, int a, int b, int[] arr) {
			if(a > b) return; // Out of range

			if(a == b) { // Leaf node
				tree[node] = arr[a]; // Init value
				return;
			}

			build_tree(node*2, a, (a+b)/2, arr); // Init left child
			build_tree(node*2+1, 1+(a+b)/2, b, arr); // Init right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Init root value
		}

		/**
		 * Increment elements within range [i, j] with value value
		 */
		public void update_tree(int i, int j, int value) {
			update_tree(1, 0, N-1, i, j, value);
		}

		private void update_tree(int node, int a, int b, int i, int j, int value) {

			if(lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if(a != b) {
					lazy[node*2] += lazy[node]; // Mark child as lazy
					lazy[node*2+1] += lazy[node]; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if(a > b || a > j || b < i) // Current segment is not within range [i, j]
				return;

			if(a >= i && b <= j) { // Segment is fully within range
				tree[node] += value;

				if(a != b) { // Not leaf node
					lazy[node*2] += value;
					lazy[node*2+1] += value;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1+node*2, 1+(a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		/**
		 * Query tree to get max element value within range [i, j]
		 */
		public int query_tree(int i, int j) {
			return query_tree(1, 0, N-1, i, j);
		}

		private int query_tree(int node, int a, int b, int i, int j) {
			if(a > b || a > j || b < i) return -inf; // Out of range

			if(lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if(a != b) {
					lazy[node*2] += lazy[node]; // Mark child as lazy
					lazy[node*2+1] += lazy[node]; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if(a >= i && b <= j) // Current segment is totally within range [i, j]
				return tree[node];

			int q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			int q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			int res = function(q1, q2); // Return final result

			return res;
		}
	}
	
	public static void main(String[] args) {
		MyScanner scan = new MyScanner(System.in) ;
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			HeavyLightDecomposition hld = new HeavyLightDecomposition(n);
			
			for (int i = 1; i < n; i++) {
				int u = scan.nextInt() -1;
				int v = scan.nextInt() -1;
				int c = scan.nextInt();

				hld.adj.get(u).add(v);
				hld.costs.get(u).add(c);
				hld.indexx.get(u).add(i-1);
				hld.adj.get(v).add(u);
				hld.costs.get(v).add(c);
				hld.indexx.get(v).add(i-1);
			}
			
			hld.dfs(hld.root, -1, 0);
			hld.HLD(hld.root, -1, -1);
			// mnake tree
			hld.initLCA();
			
			String str = scan.nextLine();
			if (!str.equals("DONE")) {
				String[] line = str.split(" ");
				int a = Integer.parseInt(line[1]);
				int b = Integer.parseInt(line[2]);
				if (line[0].equals("QUERY")) {
					System.out.println(hld.query(a-1, b-1));
				} else {
					hld.change(a-1, b);
				}
			}
		}
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
					st = new StringTokenizer(br.readLine());
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
