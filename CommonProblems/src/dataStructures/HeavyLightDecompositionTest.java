package dataStructures;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;

import org.junit.Test;

import com.sun.xml.internal.bind.v2.model.core.ID;



public class HeavyLightDecompositionTest {

	/*******************************************************************************************************************************
	 * Heavy Light Decomposition
	 * 
	 * Query time: O(log^2 N)
	 * 
	 * Based on anudeep's HLD: https://blog.anudeep2011.com/heavy-light-decomposition/
	 * 
	 * We will divide the tree into vertex-disjoint chains (Meaning no two chains has a node in common) in such
	 * a way that to move from any node in the tree to the root node, we will have to change at most log N chains.
	 * To put it in another words, the path from any node to root can be broken into pieces such that the each
	 * piece belongs to only one chain, then we will have no more than log N pieces.
	 * 
	 * This solution uses edge costs because it's built around finding the maximum edge cost on a path from A to B:
	 * http://www.spoj.com/problems/QTREE/
	 */
	
	public static class HLD {
		ArrayList<ArrayList<Integer>> g;
		int N;
		int log2N;
		int root;
		int maxDepth = 0;
		ArrayList<ArrayList<Integer>> cost;
		
		// With respect to a node
		int[] depth;
		int[] parent;
		int[] subtreeSize;
		int[] chainId;
		int[] chainPosition;
		boolean[] leaf;
		int[][] ancestor; 	// ancestor[node][i] is node's 2^(i-1)'th ancestor
		int[] nodeCost;
		
		// With respect to a chain
		int[] getChainHead;
		int[] getChainLength;
		SegmentTreeLazySum[] tree;
		
		public HLD(ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> cost, int inRoot) {
			N = g.size();
			log2N =  32 - Integer.numberOfLeadingZeros(N - 1);
			this.g = g;
			this.cost = cost;
			this.parent = new int[N]; 	// -1 for root
			this.depth = new int[N];	// 0 for root node
			this.subtreeSize = new int[N];
			this.leaf = new boolean[N];
			this.chainId = new int[N];
			this.chainPosition = new int[N];
			this.nodeCost = new int[N];
			this.getChainHead = new int[N];
			this.getChainLength = new int[N];
			this.ancestor = new int[N][log2N];
			
			this.root = inRoot;

			// Find the node in the middle of the graph and set it as the root if no root is given. 
			Arrays.fill(depth, -1);
			Arrays.fill(parent, -1);
			Queue<Integer> queue = new LinkedList<>();
			for (int i = 0; i < N; i++) {
				Arrays.fill(this.ancestor[i], -1);
				if (g.get(i).size() == 1) {
					leaf[i] = true;
					queue.add(i);
				}
			}
			BitSet visited = new BitSet();
			if (this.root == -1) {
				while (!queue.isEmpty()) {
					int top = queue.poll();
					visited.set(top);
					this.root = top;
					ArrayList<Integer> children = g.get(top);
					for (int i = 0; i < children.size(); i++) {
						int childIdx = children.get(i);
						if (!visited.get(childIdx)) {
							queue.add(childIdx);
						}
					}
				}
			}
			
			queue.clear();
			depth[this.root] = 0;
			queue.add(this.root);
			
			// Set depth and parent
			while (!queue.isEmpty()) {
				int top = queue.poll();
				ArrayList<Integer> children = g.get(top);
				for (int i = 0; i < children.size(); i++) {
					int childIdx = children.get(i);
					if (depth[childIdx] == -1) {
						parent[childIdx] = top;
						nodeCost[childIdx] = cost.get(top).get(i);
						ancestor[childIdx][0] = top;
						depth[childIdx] = depth[top]+1;
						maxDepth = Math.max(maxDepth, depth[childIdx]);
						queue.add(childIdx);
					}
				}
			}
			
			// Set ancestor for LCA
			for (int i = 1; i < log2N; i++) {
				for (int j = 0; j < N; j++) {
					if (ancestor[j][i-1] != -1) {
						ancestor[j][i] = ancestor[ancestor[j][i-1]][i-1];
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
			
			// Chain creation
			visited.clear();
			queue.clear();
			queue.add(this.root);
			int chainCounter = 0;
			Arrays.fill(getChainHead, -1);
			Arrays.fill(getChainLength, 0);
			Arrays.fill(chainId, -1);
			Arrays.fill(chainPosition, -1);
			
			chainId[root] = 0;
			chainPosition[root] = 0;
			getChainHead[chainId[root]] = root;
			getChainLength[chainId[root]] = 1;
			
			while (!queue.isEmpty()) {
				int top = queue.poll();
				ArrayList<Integer> children = g.get(top);
				visited.set(top);
				
				int specialChild = -1;
				int maxSize = -1;
				for (int i = 0; i < children.size(); i++) {
					int childIdx = children.get(i);
					if (!visited.get(childIdx) && subtreeSize[childIdx] > maxSize) {
						maxSize = subtreeSize[childIdx];
						specialChild = childIdx;
					}
				}
				for (int i = 0; i < children.size(); i++) {
					int childIdx = children.get(i);
					if (!visited.get(childIdx)) {
						if (childIdx == specialChild) {
							chainId[childIdx] = chainId[top];
							chainPosition[childIdx] = chainPosition[top] + 1;
							
						} else {
							// Start a new chain
							chainCounter++;
							chainId[childIdx] = chainCounter;
							chainPosition[childIdx] = 0;
							getChainHead[chainCounter] = childIdx; 
						}
						getChainLength[chainId[childIdx]]++;
						queue.add(childIdx);
					}
				}
			}
			
			chainCounter++;
			getChainLength = Arrays.copyOf(getChainLength, chainCounter);
			getChainHead = Arrays.copyOf(getChainHead, chainCounter);
			
			tree = new SegmentTreeLazySum[chainCounter];
			int[][] tempTree = new int[chainCounter][];
			for (int i = 0; i < chainCounter; i++) {
				tempTree[i] = new int[getChainLength[i]];
			}
			for (int i = 0; i < N; i++) {
				tempTree[chainId[i]][chainPosition[i]] = nodeCost[i];
			}
			for (int i = 0; i < chainCounter; i++) {
				tree[i] = new SegmentTreeLazySum(tempTree[i]);
			}
		}
		
		/**
		 * https://csengerg.github.io/2015/12/24/lowest-common-ancestor.html
		 */
		public int LCA(int u, int v) {
			// Ensure that u is deeper than v.
			if (depth[u] < depth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			
			for (int i = log2N; i >= 0; i--) {
				// Get u on the same logarithmic height as v.
				if (depth[u] - (1 << i) >= depth[v]) {
					u = ancestor[u][i];
				}
			}
			
			if (u == v) {
				return u;
			} else {
				// Jump to the highest node below the LCA node on both sides
				for (int i = log2N-1; i >= 0; i--) {
					if (ancestor[u][i] != ancestor[v][i] && ancestor[u][i] != -1) {
						u = ancestor[u][i];
						v = ancestor[v][i];
					}
				}
				return ancestor[u][0];
			}
		}
		
		/**
		 * Each edge has a cost, but this data structure requires vertex costs. So each vertex is given the cost of the 
		 * edge towards its parent. This works because this is a tree, not a general graph with cycles.  
		 */
		public long query(int u, int v) {
			int lca = LCA(u, v);
			long costU = tree[0].neg_inf;
			if (lca != u) {
				costU = queryUp(u, lca);
			}
			long costV = tree[0].neg_inf;
			if (lca != v) {
				costV = queryUp(v, lca);
			}
			return tree[0].function(costU, costV);
		}
		
		public long queryUp(int child, int ansc) {
			long res = tree[0].neg_inf;
			while (true) {
				if (chainId[child] != chainId[ansc]) {
					res = tree[0].function(res, tree[chainId[child]].query_tree(0, chainPosition[child]));
					child = parent[getChainHead[chainId[child]]];
				} else {
					res = tree[0].function(res, tree[chainId[child]].query_tree(chainPosition[ansc]+1, chainPosition[child]));
					break;
				}
			}
			return res;
		}
		
		/**
		 * Set the weight of the connection between u an v to value.
		 */
		public void set_tree(int u, int v, int value) {
			int child = v;
			if (parent[u] == v) {
				child = u;
			}
			tree[chainId[child]].set_tree(chainPosition[child], chainPosition[child], value);
		}
		
	}
	
	/**
	 * Segment tree with lazy updates. The default implementation is the max function.
	 * 
	 * Lazy updates are a bit of a hack, because they might be changed if the function changes.
	 * For example with the max function a lazy parent node the update is just added, but with the sum function
	 * the update must be multiplied by the number of children.
	 */

	public static class SegmentTreeLazy {
		protected long tree[];
		protected long lazy_update[];
		protected long lazy_set[];
		private int N;
		private int MAX;
		protected long neg_inf = Long.MIN_VALUE;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 * If one of the inputs are neg_inf the function should ignore them. If both are, it should probably return neg_inf.
		 */
		protected long function(long a, long b) {
			return Math.max(a, b);
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
		
		/**
		 * Find point b where the function over [a, b] == v, assuming the function (not the underlying array!)
		 * is nondecreasing. Time: O(log n)^2
		 * 
		 * @return index of the search key. Follows Arrays.binarySearch convention:
		 * if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the
		 * point at which the key would be inserted into the array: the index of the first element greater than the key,
		 * or a.length if all elements in the array are less than the specified key. Note that this guarantees that the
		 * return value will be >= 0 if and only if the key is found.
		 */
		public long search(int i, long value) {
			int left = i;
			int right = N-1;
			int mid = 0;
			long f = 0;
			int best = -1;
			if (value > query_tree(i, N)) {
				return -(search(0, tree[1]) + 1) -1;
			}
			boolean found = false;
			while (left <= right) {
				mid = (left + right) / 2;
				f = query_tree(i, mid);
				if (f == value) {
					found = true;
					best = mid;
					right = mid-1;
				} else if (f < value) {
					left = mid+1;
				} else {
					best = mid;
					right = mid-1;
				}
			}
			
			if (found) {
				return best;
			} else {
				return -best - 1;
			}
		}
	}
	

	/**
	 * Watch out for overflows. If the input is integers you should be fine.
	 * If the input is longs just don't use range updates. 
	 */
	public static class SegmentTreeLazySum extends SegmentTreeLazy {
		@Override
		protected long function(long a, long b) {
			return a + b;
		}
		
		public SegmentTreeLazySum(int[] a) {
			super(a);
			this.neg_inf = 0;
		}

		@Override
		protected void update_tree(int node, int a, int b, int i, int j, int value) {
			if (lazy_update[node] != 0) { // This node needs to be updated
				tree[node] += lazy_update[node]; // Update it

				if (a != b) {
					lazy_update[node*2] += lazy_update[node]/2; // Mark child as lazy
					lazy_update[node*2+1] += lazy_update[node]/2; // Mark child as lazy
				}

				lazy_update[node] = 0; // Reset it
			}

			if (a > b || a > j || b < i) // Current segment is not within range [i, j]
				return;

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] += value * (b - a + 1);

				if (a != b) { // Not leaf node
					lazy_update[node*2] += value * (b - a + 1)/2;
					lazy_update[node*2 + 1] += value * (b - a + 1)/2;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		@Override
		protected long query_tree(int node, int a, int b, int i, int j) {
			if (a > b || a > j || b < i) {
				return neg_inf; // Out of range
			}

			if (lazy_update[node] != 0) { // This node needs to be updated
				tree[node] += lazy_update[node]; // Update it

				if (a != b) {
					lazy_update[node*2] += lazy_update[node]/ 2; // Mark child as lazy
					lazy_update[node*2+1] += lazy_update[node]/ 2; // Mark child as lazy
				}

				lazy_update[node] = 0; // Reset it
			}

			if (a >= i && b <= j) { // Current segment is totally within range [i, j]
				return tree[node];
			}

			long q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			long q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			long res = function(q1, q2); // Return final result
			return res;
		}
	}

	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*******************************************************************************************************************************
	 * Heavy Light Decomposition
	 * 
	 * Query time: O(log^2 N)
	 * 
	 * Based on anudeep's HLD: https://blog.anudeep2011.com/heavy-light-decomposition/
	 * 
	 * This is a simpler solution. It's built to find if vertex C is on the path from A to B.
	 * Cost is kept separate for convenience, in this case it's going to be the same as the graph structure.
	 * Uses a plain segment tree for speed.
	 */
	
	public static class HLD2 {
		ArrayList<ArrayList<Integer>> g;
		int N;
		int log2N;
		int root;
		int maxDepth = 0;
		ArrayList<ArrayList<Integer>> cost;
		
		// With respect to a node
		int[] depth;
		int[] parent;
		int[] subtreeSize;
		int[] chainId;
		int[] chainPosition;
		boolean[] leaf;
		int[][] ancestor; 	// ancestor[node][i] is node's 2^(i-1)'th ancestor
		int[] nodeCost;
		
		// With respect to a chain
		int[] getChainHead;
		int[] getChainLength;
		SegmentTree[] tree;
		
		public HLD2(ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> cost, int inRoot) {
			N = g.size();
			log2N =  32 - Integer.numberOfLeadingZeros(N - 1);
			this.g = g;
			this.cost = cost;
			this.parent = new int[N]; 	// -1 for root
			this.depth = new int[N];	// 0 for root node
			this.subtreeSize = new int[N];
			this.leaf = new boolean[N];
			this.chainId = new int[N];
			this.chainPosition = new int[N];
			this.nodeCost = new int[N];
			this.getChainHead = new int[N];
			this.getChainLength = new int[N];
			this.ancestor = new int[N][log2N];
			
			this.root = inRoot;

			// Find the node in the middle of the graph and set it as the root if no root is given. 
			Arrays.fill(depth, -1);
			Arrays.fill(parent, -1);
			Queue<Integer> queue = new LinkedList<>();
			for (int i = 0; i < N; i++) {
				Arrays.fill(this.ancestor[i], -1);
				if (g.get(i).size() == 1) {
					leaf[i] = true;
					queue.add(i);
				}
			}
			BitSet visited = new BitSet();
			if (this.root == -1) {
				while (!queue.isEmpty()) {
					int top = queue.poll();
					visited.set(top);
					this.root = top;
					ArrayList<Integer> children = g.get(top);
					for (int i = 0; i < children.size(); i++) {
						int childIdx = children.get(i);
						if (!visited.get(childIdx)) {
							queue.add(childIdx);
						}
					}
				}
			}
			
			queue.clear();
			depth[this.root] = 0;
			queue.add(this.root);
			
			// Set depth and parent
			while (!queue.isEmpty()) {
				int top = queue.poll();
				ArrayList<Integer> children = g.get(top);
				ArrayList<Integer> costs = cost.get(top);
				for (int i = 0; i < children.size(); i++) {
					int childIdx = children.get(i);
					if (depth[childIdx] == -1) {
						parent[childIdx] = top;
						nodeCost[childIdx] = costs.get(i);
						ancestor[childIdx][0] = top;
						depth[childIdx] = depth[top]+1;
						maxDepth = Math.max(maxDepth, depth[childIdx]);
						queue.add(childIdx);
					}
				}
			}
			
			// Set ancestor for LCA
			for (int i = 1; i < log2N; i++) {
				for (int j = 0; j < N; j++) {
					if (ancestor[j][i-1] != -1) {
						ancestor[j][i] = ancestor[ancestor[j][i-1]][i-1];
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
			
			// Chain creation
			visited.clear();
			queue.clear();
			queue.add(this.root);
			int chainCounter = 0;
			Arrays.fill(getChainHead, -1);
			Arrays.fill(getChainLength, 0);
			Arrays.fill(chainId, -1);
			Arrays.fill(chainPosition, -1);
			
			chainId[root] = 0;
			chainPosition[root] = 0;
			getChainHead[chainId[root]] = root;
			getChainLength[chainId[root]] = 1;
			
			while (!queue.isEmpty()) {
				int top = queue.poll();
				ArrayList<Integer> children = g.get(top);
				visited.set(top);
				
				int specialChild = -1;
				int maxSize = -1;
				for (int i = 0; i < children.size(); i++) {
					int childIdx = children.get(i);
					if (!visited.get(childIdx) && subtreeSize[childIdx] > maxSize) {
						maxSize = subtreeSize[childIdx];
						specialChild = childIdx;
					}
				}
				for (int i = 0; i < children.size(); i++) {
					int childIdx = children.get(i);
					if (!visited.get(childIdx)) {
						if (childIdx == specialChild) {
							chainId[childIdx] = chainId[top];
							chainPosition[childIdx] = chainPosition[top] + 1;
							
						} else {
							// Start a new chain
							chainCounter++;
							chainId[childIdx] = chainCounter;
							chainPosition[childIdx] = 0;
							getChainHead[chainCounter] = childIdx; 
						}
						getChainLength[chainId[childIdx]]++;
						queue.add(childIdx);
					}
				}
			}
			
			chainCounter++;
			getChainLength = Arrays.copyOf(getChainLength, chainCounter);
			getChainHead = Arrays.copyOf(getChainHead, chainCounter);
			
			tree = new SegmentTree[chainCounter];
			int[][] tempTree = new int[chainCounter][];
			for (int i = 0; i < chainCounter; i++) {
				tempTree[i] = new int[getChainLength[i]];
			}
			for (int i = 0; i < N; i++) {
				tempTree[chainId[i]][chainPosition[i]] = nodeCost[i];
			}
			for (int i = 0; i < chainCounter; i++) {
				tree[i] = new SegmentTree(tempTree[i]);
			}
		}
		
		/**
		 * https://csengerg.github.io/2015/12/24/lowest-common-ancestor.html
		 */
		public int LCA(int u, int v) {
			// Ensure that u is deeper than v.
			if (depth[u] < depth[v]) {
				int temp = u;
				u = v;
				v = temp;
			}
			
			for (int i = log2N; i >= 0; i--) {
				// Get u on the same logarithmic height as v.
				if (depth[u] - (1 << i) >= depth[v]) {
					u = ancestor[u][i];
				}
			}
			
			if (u == v) {
				return u;
			} else {
				// Jump to the highest node below the LCA node on both sides
				for (int i = log2N-1; i >= 0; i--) {
					if (ancestor[u][i] != ancestor[v][i] && ancestor[u][i] != -1) {
						u = ancestor[u][i];
						v = ancestor[v][i];
					}
				}
				return ancestor[u][0];
			}
		}
		
		/**
		 * Each edge has a cost, but this data structure requires vertex costs. So each vertex is given the cost of the 
		 * edge towards its parent. This works because this is a tree, not a general graph with cycles.  
		 */
		public int query(int u, int v, int searchValue) {
			int lca = LCA(u, v);
			int costU = tree[0].IDENTITY;
			if (lca != u) {
				costU = queryUp(u, lca, searchValue);
			}
			int costV = tree[0].IDENTITY;
			if (lca != v) {
				costV = queryUp(v, lca, searchValue);
			}
			return tree[0].function(costU, costV);
		}
		
		public int queryUp(int child, int ansc, int searchValue) {
			int res = tree[0].IDENTITY;
			while (true) {
				if (chainId[child] != chainId[ansc]) {
					res = tree[0].function(res, tree[chainId[child]].get(0, chainPosition[child], searchValue));
					child = parent[getChainHead[chainId[child]]];
				} else {
					res = tree[0].function(res, tree[chainId[child]].get(chainPosition[ansc]+1, chainPosition[child], searchValue));
					break;
				}
			}
			return res;
		}
		
		/**
		 * Set the value of the tree at u to value.
		 */
		public void set_tree(int u, int value) {
			int child = u;
			tree[chainId[child]].set(chainPosition[child], value);
		}
		
	}

	public static class SegmentTree {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			if (a == searchValue || b == searchValue) {
				return searchValue;
			}
			return -1;
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = -1;
		private int searchValue = IDENTITY;

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
		public int get(int i, int j, int searchValue) {
			this.searchValue = searchValue;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static InputReader in;
	public static PrintWriter out;
	
	public static void HLD() {
		Random ra = new Random(0); 
				
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = in.nextInt(); // vertices
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				g.add(new ArrayList<Integer>());
				cost.add(new ArrayList<Integer>());
			}
			for (int i = 0; i < n-1; i++) {
				int a = in.nextInt();
				int b = in.nextInt();
				int c = in.nextInt();
				g.get(a).add(b);
				g.get(b).add(a);
				cost.get(a).add(c);
				cost.get(b).add(c);
			}
			HLD hld = new HLD(g, cost, 10);
			for (int i = 0; i < 10; i++) {
				long res = hld.query(ra.nextInt(n), ra.nextInt(n));
				System.out.println(res);
			}
			
		}
	}

	static String intputString = 
			"1\n" + 
			"23\n" +
			"0 1 1\n" +
			"1 8 8\n" +
			"4 8 8\n" +
			"7 8 8\n" +
			"7 3 7\n" +
			"6 7 7\n" +
			"2 3 3\n" +
			"5 6 6\n" +
			"6 9 9\n" +
			"8 10 10\n" +
			"11 12 12\n" +
			"17 13 17\n" +
			"12 13 13\n" +
			"15 16 16\n" +
			"16 17 17\n" +
			"20 17 20\n" +
			"13 8 13\n" +
			"10 14 14\n" +
			"18 14 18\n" +
			"10 19 19\n" +
			"10 22 22\n" +
			"19 21 21\n"
			;
	
	public static void main(String[] args) {
		InputStream inputStream = null;
//		inputStream = System.in;
		inputStream = new ByteArrayInputStream(intputString.getBytes());
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

		HLD();
		
		out.close();
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
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/
	
	@Test
	public void testQueriesHLD() {
		/**
		 * Problem : SPOJ – QTREE
		 * Solution : Each edge has a number associated with it. Given 2 nodes A and B, we need to find
		 * the edge on path from A to B with maximum value. Clearly we can break the path into A to
		 * LCA( A, B ) and B to LCA( A, B ), calculate answer for each of them and take the maximum of
		 * both. As mentioned above as the tree need not be balanced, it may take up to O( N ) to travel
		 * from A to LCA( A, B ) and find the maximum. Let us use HLD as detailed above to solve the problem.
		 */
		Random ra = new Random(0);
		for (int size = 2; size <= 20; size++) {
			for (int tests = 0; tests < 100; tests++) {
				ArrayList<ArrayList<Integer>> g = new ArrayList<>();
				ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
				for (int i = 0; i < size; i++) {
					g.add(new ArrayList<Integer>());
					cost.add(new ArrayList<Integer>());
				}
				for (int i = 1; i < size; i++) {
					int oldNode = ra.nextInt(i);
					int newNode = i;
					int c = ra.nextInt(100);
					g.get(oldNode).add(newNode);
					g.get(newNode).add(oldNode);
					cost.get(oldNode).add(c);
					cost.get(newNode).add(c);
				}
				
				HLD hld = new HLD(g, cost, -1);
				
				for (int i = 0; i < size; i++) {
					boolean query = ra.nextBoolean();
					if (query) {
						for (int start = 0; start < size; start++) {
							for (int end = 0; end < size; end++) {
								Stack<int[]> s = new Stack<>();
								int[] top = {start, 0};
								s.add(top);
								BitSet visited = new BitSet();
								int totalCost = -1;
								while (!s.isEmpty()) {
									top = s.pop();
									if (visited.get(top[0])) {
										continue;
									}
									if (top[0] == end) {
										totalCost = top[1];
										break;
									}
									visited.set(top[0]);
									ArrayList<Integer> children = g.get(top[0]); 
									for (int j = 0; j < children.size(); j++) {
										s.add(new int[]{children.get(j), top[1] + cost.get(top[0]).get(j)});
									}
								}
								if (hld.query(start, end) != totalCost) {
									hld.query(start, end);
								}
								assertEquals(hld.query(start, end), totalCost);
							}
						}
					} else {
						// update random cost
						int a = ra.nextInt(size);
						int bIdx = ra.nextInt(g.get(a).size());
						int c = ra.nextInt(100);
						cost.get(a).set(bIdx, c);
						boolean done = false;
						int b = g.get(a).get(bIdx);
						for (int j = 0; j < g.get(b).size(); j++) {
							if (g.get(b).get(j) == a) {
								cost.get(b).set(j, c);
								done = true;
								break;
							}
						}
						assertTrue(done);
						
						hld.set_tree(a, b, c);
					}
				}
			}
		}
	}

	@Test
	public void testPerformance() {
		Random ra = new Random(0);
		int size = 100000;
		for (int tests = 0; tests < 5; tests++) {
			long time = System.currentTimeMillis();
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				g.add(new ArrayList<Integer>());
				cost.add(new ArrayList<Integer>());
			}
			for (int i = 1; i < size; i++) {
				int oldNode = ra.nextInt(i);
				int newNode = i;
				int c = ra.nextInt(100);
				g.get(oldNode).add(newNode);
				g.get(newNode).add(oldNode);
				cost.get(oldNode).add(c);
				cost.get(newNode).add(c);
			}
			
			HLD hld = new HLD(g, cost, -1);
			for (int i = 0; i < size; i++) {
				boolean query = ra.nextBoolean();
				if (query) {
					int start = ra.nextInt(size);
					int end = ra.nextInt(size);
					long res = hld.query(start, end);
					assertNotEquals(res, -1);
				} else {
					// update random cost
					int a = ra.nextInt(size);
					int bIdx = ra.nextInt(g.get(a).size());
					int c = ra.nextInt(100);
					int b = g.get(a).get(bIdx);
					hld.set_tree(a, b, c);
				}
			}
			time = System.currentTimeMillis() - time;
			System.out.println("Total time: " + time);
		}
	}
	
	public static void generateTree(Random ra, int N, ArrayList<ArrayList<Integer>> g) {
		for (int i = 0; i < N; i++) {
			g.add(new ArrayList<Integer>());
		}
		for (int i = 1; i < N-1; i++) {
			int oldNode = ra.nextInt(i);
			int newNode = i;
			g.get(oldNode).add(newNode);
			g.get(newNode).add(oldNode);
		}
	}
		
//	@Test
//	public void testQueriesHLD2() {
//		/**
//		 * Is C on the path from A to B?
//		 */
//		
//		Random rand = new Random(0);
//		int maxSize = 10;
//		int numTests = 10000;
//		
//		for (int test = 0; test < numTests; test++) {
//			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
//			int N = rand.nextInt(maxSize) +1;
//			generateTree(rand, N, g);
//			
//			Tree[] tree = adjacencyListToTree(g);
//			HLD2 hld = new HLD2(g, g, -1);
//
//			for (int q = 0; q < N; q++) {
//				int a = rand.nextInt(N);
//				int b = rand.nextInt(N);
//				int c = rand.nextInt(N);
//								
//				
//			}
//			for (int i = 0; i < N; i++) {
//				boolean query = rand.nextBoolean();
//				if (query) {
//					for (int start = 0; start < size; start++) {
//						for (int end = 0; end < size; end++) {
//							Stack<int[]> s = new Stack<>();
//							int[] top = {start, 0};
//							s.add(top);
//							BitSet visited = new BitSet();
//							int totalCost = -1;
//							while (!s.isEmpty()) {
//								top = s.pop();
//								if (visited.get(top[0])) {
//									continue;
//								}
//								if (top[0] == end) {
//									totalCost = top[1];
//									break;
//								}
//								visited.set(top[0]);
//								ArrayList<Integer> children = g.get(top[0]); 
//								for (int j = 0; j < children.size(); j++) {
//									s.add(new int[]{children.get(j), top[1] + cost.get(top[0]).get(j)});
//								}
//							}
//							if (hld.query(start, end) != totalCost) {
//								hld.query(start, end);
//							}
//							assertEquals(hld.query(start, end), totalCost);
//						}
//					}
//				} else {
//					// update random cost
//					int a = rand.nextInt(size);
//					int bIdx = rand.nextInt(g.get(a).size());
//					int c = rand.nextInt(100);
//					cost.get(a).set(bIdx, c);
//					boolean done = false;
//					int b = g.get(a).get(bIdx);
//					for (int j = 0; j < g.get(b).size(); j++) {
//						if (g.get(b).get(j) == a) {
//							cost.get(b).set(j, c);
//							done = true;
//							break;
//						}
//					}
//					assertTrue(done);
//					
//					hld.set_tree(a, b, c);
//				}
//			}
//		}
//	}
}
