package dataStructures;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Based on paladin8's segment tree: http://codeforces.com/blog/entry/3327
 */

public class SegmentTreeHolder {

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

		/**
		 * Find the point x where the function over [0, x] == v, assuming the function (not the underlying array!) is nondecreasing. Time: O(log n)
		 */
		public int search(int v) {
			int x = 0;
			int value = v;
			if (value > t[0][n]) {
				return -(search(t[0][n]) + 1) -1;
			}
			int height = n-1;
			while (height >= 0 && x >= 0 && x < N) {
				int left = t[x][height];
				int right = left + (x + (1 << (height)) < N ? t[x + (1 << (height))][height] : 0);
				if (value <= left) {
					// go left
					if (height == 0) {
						value -= left;
					}
				} else {
					// go right
					if (height == 0) {
						value -= right;
					} else {
						value -= left;
					}
					x += (1 << (height));
				} 
				height--;
			}
			if (value == 0) {
				return x;
			} else {
				return -1 * x - 1;
			}
		}

		/**
		 * Find the first point b where the function over [a, b] == v, assuming the function (not the underlying array!) is nondecreasing. Time: O(log n)^2
		 */
		public int search2(int a, int v) {
			int left = a;
			int right = N-1;
			int mid = 0;
			int f = 0;
			int best = -1;
			if (v > t[0][n]) {
				return -(search2(0, t[0][n]) + 1) -1;
			}
			boolean found = false;
			while (left <= right) {
				mid = (left + right) / 2;
				f = get(a, mid);
				if (f == v) {
					found = true;
					best = mid;
					right = mid-1;
				} else if (f < v) {
					left = mid+1;
				} else {
					if (best+1 <= mid) {
						if (get(best+1, mid) != 0) {
							best = mid;
						}
					} else {
						best = mid;
					}
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

	public static interface BinaryOperation<E> {
		public E identity();
		public E function(E e1, E e2);
	}

	public static class SegmentTreeE<E extends BinaryOperation<E>> {

		private ArrayList<ArrayList<E>> t;
		private ArrayList<E> a;
		private int N;
		private int n;
		private E identity;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected E function(E e1, E e2) {
			return e1.function(e1, e2);
		}

		public SegmentTreeE(E[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new ArrayList<E>(N);
			this.identity = b[0].identity();
			for (int i = 0; i < N; i++) {
				if (i < b.length) {
					this.a.add(b[i]);
				} else {
					this.a.add(identity);
				}
			}
			t = new ArrayList<ArrayList<E>>(N);
			for (int x = 0; x < N; x++) {
				ArrayList<E> list = new ArrayList<E>();
				t.add(list);
				for (int i = 0; i < n+1; i++) {
					list.add(identity);
				}
				t.get(x).set(0, a.get(x));
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t.get(x).set(y, function(t.get(x).get(y-1), t.get(x+(1<<(y-1))).get(y-1)));
				}
			}
		}

		public void set(int i, E v) {
			this.a.set(i, v);
			t.get(i).set(0, v);
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t.get(x).set(y, function(t.get(x).get(y-1), t.get(x+(1<<(y-1))).get(y-1)));
			}
		}

		/**
		 * Get the function over the interval [a, b].
		 */
		public E get(int i, int j) {
			E res = identity;
			int h = 0; j++;
			while (i+(1<<h) <= j) {
				while ((i&((1<<(h+1))-1)) == 0 && i+(1<<(h+1)) <= j) h++;
				res = function(res, t.get(i).get(h));
				i += (1<<h);
			}
			while (i < j) {
				while (i+(1<<h) > j) h--;
				res = function(res, t.get(i).get(h));
				i += (1<<h);
			}
			return res;
		}
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
}