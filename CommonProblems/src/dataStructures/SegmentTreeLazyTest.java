package dataStructures;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class SegmentTreeLazyTest {	
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
		public void update(int i, int j, int value) {
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
		 * Sets element at i to the specified value. Setting to Long.MIN_VALUE is not supported.
		 */
		public void set(int i, int value) {
			set_tree(1, 0, N-1, i, i, value);
		}
		
		/**
		 * Sets elements within range [i, j] to the specified value. Setting to Long.MIN_VALUE is not supported.
		 */
		public void set(int i, int j, int value) {
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
		public long get(int i, int j) {
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
			if (value > get(i, N)) {
				return -(search(0, tree[1]) + 1) -1;
			}
			boolean found = false;
			while (left <= right) {
				mid = (left + right) / 2;
				f = get(i, mid);
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
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/
	
	/**
	 * Tests fast range updates (O(log n))
	 */
	@Test
	public void testMaxUpdate() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazy st = new SegmentTreeLazy(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] += change;
				}
				st.update(low, high, change);
				
				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					int expected = Integer.MIN_VALUE;
					for (int k = low; k <= high; k++) {
						expected = Math.max(expected, a[k]);
					}
					long actual = st.get(low, high);
					
					assertEquals(expected, actual);
				}
			}
		}
	}
	
	/**
	 * Tests fast range sets (as opposed to updates) (O(log n))
	 */
	@Test
	public void testMaxSet() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazy st = new SegmentTreeLazy(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] = change;
				}
				st.set(low, high, change);
				
				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					int expected = Integer.MIN_VALUE;
					for (int k = low; k <= high; k++) {
						expected = Math.max(expected, a[k]);
					}
					long actual = st.get(low, high);
					
					if (actual != expected) {
						st.get(low, high);
					}
					
					assertEquals(expected, actual);
				}
			}
		}
	}

	@Test
	public void testFastSearch() {
		int maxLength = 30;
		int maxValue = 50;
		int numTests = 10000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue)+1;
			}
			
			SegmentTreeLazySum st = new SegmentTreeLazySum(a);
			
			int[] firstSum = new int[len * maxValue + 1];
			int sum = 0;
			Arrays.fill(firstSum, Integer.MAX_VALUE);
			for (int i = 0; i < a.length; i++) {
				sum += a[i];
				firstSum[sum] = Math.min(firstSum[sum], i);
			}
			
			int last = 0;
			for (int i = 0; i < firstSum.length; i++) {
				if (firstSum[i] != Integer.MAX_VALUE) {
					last = firstSum[i]+1;
				} else {
					firstSum[i] = -last -1;
				}
			}
			
			for (int i = 1; i < firstSum.length; i++) {
				int expected = firstSum[i];
				long actual = st.search(0, i);
				
				if (expected != actual) {
					System.out.println(i + "\t expected: " + expected + "\t actual: " + actual);
					st.search(0, i);
				}
				assertEquals(expected, actual);
			}
		}
	}
	
	@Test
	public void testSumUpdate() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazySum st = new SegmentTreeLazySum(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] += change;
				}
				st.update(low, high, change);

				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					
					int expected = 0;
					for (int k = low; k <= high; k++) {
						expected += a[k];
					}
					long actual = st.get(low, high);
					
					if (expected != actual) {
						System.out.println(i + "\t expected: " + expected + "\t actual: " + actual);
						st.get(low, high);
					}
					assertEquals(expected, actual);
				}
				
			}
		}
	}

	public static class SegmentTreeLazySumNoRangeUpdate extends SegmentTreeLazy {
		public SegmentTreeLazySumNoRangeUpdate(int[] a) {
			super(a);
			this.neg_inf = 0;
		}

		@Override
		protected long function(long a, long b) {
			return a + b;
		}
	}

	/**
	 * Test the ability to do point updates to avoid lazy updates.
	 */
	@Test
	public void testSumUpdateNoLazy() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazySumNoRangeUpdate st = new SegmentTreeLazySumNoRangeUpdate(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] += change;
					
					// Do point updates to avoid lazy updates.
					st.update(k, k, change);
				}

				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					
					int expected = 0;
					for (int k = low; k <= high; k++) {
						expected += a[k];
					}
					long actual = st.get(low, high);
					
					if (expected != actual) {
						System.out.println(i + "\t expected: " + expected + "\t actual: " + actual);
						st.get(low, high);
					}
					assertEquals(expected, actual);
				}
				
			}
		}
	}
}
