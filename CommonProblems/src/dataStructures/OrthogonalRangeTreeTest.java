package dataStructures;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.junit.Test;


public class OrthogonalRangeTreeTest {	
	/**
	 * Orthogonal Range Tree from:
	 * 
	 * https://www.cs.ucsb.edu/~suri/cs235/RangeSearching.pdf
	 * https://courses.csail.mit.edu/6.851/spring12/lectures/L03.html
	 * 
	 * This version is designed to answer: how many points is in a given rectangle in O(log^2(n)))
	 * 
	 * This is a simplified version because it has to compensate for duplicates on the y-axis.
	 * If it didn't have to it could be a true 2D segment tree, e.g. find the
	 * product of the value of all points in a rectangle. 
	 */

	public static class OrthogonalRangeTree {
		protected long tree[];
		private int[][] yTree;
		private int[][] yTreeCumulative;
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

		/**
		 * The input array are y-values of points on a Cartesian system.
		 */
		public OrthogonalRangeTree(int[] a) {
			N = 1 << (int) (Math.log10(a.length)/Math.log10(2))+1;
			MAX = N*2;

			int[] arr = Arrays.copyOf(a, N);
			yTree = new int[MAX][];
			yTreeCumulative = new int[MAX][];
			tree = new long[MAX];
			build_tree(1, 0, N-1, arr);
		}
		
		/**
		 * Build and initialize tree
		 */
		private void build_tree(int node, int a, int b, int[] y) {
			if (a > b) {
				return; // Out of range
			}

			if (a == b) { // Leaf node
//				tree[node] = value[a]; // Initialize value
				yTree[node] = new int[]{y[a]};
				yTreeCumulative[node] = new int[]{0, 1};
				return;
			}

			build_tree(node*2, a, (a+b)/2, y); // Initialize left child
			build_tree(node*2+1, 1+(a+b)/2, b, y); // Initialize right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Initialize root value
			
			// Build the y-tree from the children's arrays
			
			// eliminate duplicates
			TreeMap<Integer, Integer> tm = new TreeMap<>();
			for (int child = 0; child < 2; child++) {
				int[] childTree = yTree[node*2 + child];
				int[] childTreeCumulative = yTreeCumulative[node*2 + child];
				for (int i = 0; i < childTree.length; i++) {
					
					// remember to compensate for the fact that child nodes have been compressed
					for (int j = 0; j < childTreeCumulative[i+1] - childTreeCumulative[i]; j++) {
						tm.put(childTree[i], tm.containsKey(childTree[i]) ? tm.get(childTree[i]) + 1 : 1);
					}
				}
			}

			int[] newTree = new int[tm.size()];
			int[] count = new int[tm.size()];
			int[] cumulative = new int[tm.size()+1];
			int i = 0;
			for (Entry<Integer, Integer> c: tm.entrySet()) {
				newTree[i] = c.getKey();
				count[i] = c.getValue();
				i++;
			}
			for (int j = 1; j < cumulative.length; j++) {
				cumulative[j] = cumulative[j-1] + count[j-1];
			}
			yTree[node] = newTree;
			yTreeCumulative[node] = cumulative;
		}

		/**
		 * Query tree to get max element value within range [i, j]
		 */
		public long query_tree(int x1, int x2, int y1, int y2) {
			return query_tree(1, 0, N-1, x1, x2, y1, y2);
		}

		/**
		 * All bounds are inclusive.
		 * @param node The tree index
		 * @param a Internal query's lower bound
		 * @param b Internal query's upper bound
		 * @param x1 External query's lower bound
		 * @param x2 External query's upper bound
		 */
		protected long query_tree(int node, int a, int b, int x1, int x2, int y1, int y2) {
			if (a > b || a > x2 || b < x1) {
				return neg_inf; // Out of range
			}

			if (a >= x1 && b <= x2) { // Current segment is totally within range [i, j]
				int leftIdx = Arrays.binarySearch(yTree[node], y1);
				if (leftIdx < 0) {
					leftIdx = -1 * (leftIdx+1);
				}
				int rightIdx = Arrays.binarySearch(yTree[node], y2);
				if (rightIdx < 0) {
					rightIdx = -1 * (rightIdx+1);
				} else {
					rightIdx++;
				}
				int numPoints = 0;
				if (yTreeCumulative[node] != null) {
					numPoints = yTreeCumulative[node][rightIdx] - yTreeCumulative[node][leftIdx];
				}
				return numPoints;
			}

			long q1 = query_tree(node*2, a, (a+b)/2, x1, x2, y1, y2); // Query left child
			long q2 = query_tree(1+node*2, 1+(a+b)/2, b, x1, x2, y1, y2); // Query right child

			long res = function(q1, q2); // Return final result
			return res;
		}
		
		public String toString() {
			return Arrays.toString(this.tree);
		}
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/

	@Test
	public void test2dRangeQueryWithDuplicates() {
		int maxLength = 1000;
		int numTests = 300;
		int maxValue = maxLength/10;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				int r = rand.nextInt(maxValue);
				data[i] = r;
			}
			
			OrthogonalRangeTree st = new OrthogonalRangeTree(data);
			
			for (int i = 0; i < numTests; i++) {
				int lowX = rand.nextInt(len);
				int highX = rand.nextInt(len-lowX) + lowX;
				
				int lowY = rand.nextInt(maxValue);
				int highY = rand.nextInt(maxValue-lowY) + lowY;
				
				int expected = 0;
				for (int k = lowX; k <= highX; k++) {
					if (lowY <= data[k] && data[k] <= highY) {
//						System.out.println("expected: " + k+":"+data[k]);
						expected++;
					}
				}
//				System.out.println(expected);
				int actual = (int) st.query_tree(lowX, highX, lowY, highY);
//				System.out.println(actual + "\n");
				assertEquals(expected, actual);
			}
		}
	}

	@Test
	public void testSpeed() {
		/**
		 * ~1 second for 200k input and 200k queries. 
		 */
		long t1 = System.currentTimeMillis();
		int maxLength = 200000;
		int numTests = 1;
		int maxValue = (int) Math.sqrt(maxLength);
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = maxLength;
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				int r = rand.nextInt(maxValue);
				data[i] = r;
			}
			
			OrthogonalRangeTree st = new OrthogonalRangeTree(data);
			
			for (int i = 0; i < maxLength; i++) {
				int lowX = rand.nextInt(len);
				int highX = rand.nextInt(len-lowX) + lowX;
				
				int lowY = rand.nextInt(maxValue);
				int highY = rand.nextInt(maxValue-lowY) + lowY;
				st.query_tree(lowX, highX, lowY, highY);
			}
		}
		System.out.println("time: " + (System.currentTimeMillis() - t1));
	}
}
