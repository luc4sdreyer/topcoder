package dataStructures;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class SegmentTreePersistentGeneralTest {

	/**
	 * A little slower but generalised persistent segment tree.
	 * 
	 * You start off with segment tree version 0, then modify some node.
	 * Create a new root for version 1, and log(N) nodes down to the modified node.
	 * The new nodes link to old nodes where possible.
	 * 
	 * The tree starts off at size O(N.log(N)). After M updates the size
	 * will be O((N + M).log(N)). 
	 * 
	 * If you have a NxM matrix with not many changes between the rows,
	 * you can use this to compress the size down to O((N + M).log(N)).
	 * 
	 * This tree supports updating more than one value per version.
	 * Note that each value will take O(log(N)) time and space.
	 * 
	 * Based on http://www.geeksforgeeks.org/persistent-segment-tree-set-1-introduction/
	 * 
	 *  
	 * === Doesn't use an identity value!!! ===
	 * 
	 */
	static boolean debug = false;

	public static class Node {
		public Node left, right;
		public int value;
		public int version;
		
		// for testing
		public static int counter;

		public Node(Node left, Node right, Node parent, int value) {
			this.value = value;
			this.left = left;
			this.right = right;
			if (parent != null) {
				this.version = parent.version;
			}
			Node.counter++;
		}

		public String toString() {
			return this.value + " (" 
					+ (this.left == null ? '.' : ""+this.left.value) + ":" 
					+ (this.right == null ? '.' : ""+this.right.value) + ")"; 
		}
	}

	public static class SegmentTreePersistentGeneral {
		int[] input;
		ArrayList<Node> version;
		
		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return a + b;
		}

		/**
		 * The value of INVALID indicates an out of bounds query. It should be a number that will never happen.
		 */
		protected int INVALID = Integer.MIN_VALUE;

		public SegmentTreePersistentGeneral(int[] input) {
			Node root = new Node(null, null, null, 0); // this zero is just a placeholder
			version = new ArrayList<>();
			version.add(root);
			root.version = 0;
			this.input = input;
			build(root, 0, input.length-1);
		}

		private void build(Node n, int low, int high) {
			if (low==high) {
				n.value = input[low];
				return;
			}
			int mid = (low+high) / 2;
			n.left = new Node(null, null, n, 0);  // these zeros are just a placeholders
			n.right = new Node(null, null, n, 0);
			build(n.left, low, mid);
			build(n.right, mid+1, high);
			n.value = function(n.left.value, n.right.value);
		}
		
		/**
		 * Upgrades to new Version
		 * @param idxValues: list of [index, value] items to update
		 * Time Complexity : O(logn)
		 * Space Complexity : O(logn)
		 */
		public void upgrade(ArrayList<int[]> idxValues) {
			Node prev = version.get(version.size() -1);
			Node cur = new Node(null, null, null, 0);
			version.add(cur);
			cur.version = version.size();
			
			// The update shouldn't be empty but this code will handle that case.
			if (idxValues.isEmpty()) {
				cur.value = prev.value;
				cur.left = prev.left;
				cur.right = prev.right;
				return;
			}
			for (int[] idxValue: idxValues) {
				upgrade(prev, cur, 0, input.length-1, idxValue[0], idxValue[1]);
			}
		}
		
		private void upgrade(Node prev, Node cur, int low, int high, int idx, int value) {
			if (idx > high || idx < low || low > high)
				return;

			if (low == high) {
				cur.value = value;
				return;
			}
			int mid = (low+high) / 2;
			if (idx <= mid) {
				// link to right child of previous version
				if (cur.right == null || cur.right.version < cur.version) {
					cur.right = prev.right;
				}
				if (cur.left == null || cur.left.version < cur.version) {
					// create new node in current version
					cur.left = new Node(null, null, cur, 0);
				}
				
				upgrade(prev.left,cur.left, low, mid, idx, value);
			} else {
				// link to left child of previous version
				if (cur.left == null || cur.left.version < cur.version) {
					cur.left = prev.left;
				}

				if (cur.right == null || cur.right.version < cur.version) {
					// create new node for current version
					cur.right = new Node(null, null, cur, 0);
				}
				upgrade(prev.right, cur.right, mid+1, high, idx, value);
			}

			// calculating data for current version
			// by combining previous version and current
			// modification
			cur.value = function(cur.left.value, cur.right.value);
		}

		/**
		 * Query according to version number in range [l, r] (inclusive)
		 */
		public int query(int version, int l, int r) {
			return query(this.version.get(version), 0, input.length-1, l, r);
		}
		
		/**
		 * Query according to some version's root node
		 */
		private int query(Node n, int low, int high, int l, int r) {
			if (l > high || r < low || low > high)
				return this.INVALID;
			if (l <= low && high <= r)
				return n.value;
			int mid = (low+high) / 2;
			int p1 = query(n.left,low,mid,l,r);
			int p2 = query(n.right,mid+1,high,l,r);
			if (p1 != INVALID) {
				if (p2 != INVALID) {
					return function(p1, p2);
				} else {
					return p1;
				}
			} else {
				if (p2 != INVALID) {
					return p2;
				} else {
					return INVALID;
				}
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/
	
	@Test
	public void testPersistentSum() {
		int maxLength = 100;
		int numTests = 500;
		int maxValue = 1000000;
		Random rand = new Random(0);
		
		for (int test = 1; test <= numTests; test++) {
			int len = rand.nextInt(maxLength)+1;
			int[][] data = new int[len][len];

			for (int j = 0; j < data[0].length; j++) {
				data[0][j] = rand.nextInt(maxValue) - maxValue/2;
			}
			SegmentTreePersistentGeneral st = new SegmentTreePersistentGeneral(data[0]);
			
			for (int i = 1; i < data.length; i++) {
				// do logN updates
				ArrayList<int[]> updates = new ArrayList<>();
				for (int j = 0; j < data.length; j++) {
					updates.add(new int[]{j, rand.nextInt(maxValue) - maxValue/2});
				}
				Collections.shuffle(updates, rand);
				ArrayList<int[]> updates2 = new ArrayList<>();
				for (int j = 0; j < 1 + (int)(Math.log(data.length) / Math.log(2)); j++) {
				//for (int j = 0; j < 1; j++) {
					updates2.add(updates.get(j));
				}
				
				// update data
				for (int j = 0; j < data.length; j++) {
					data[i][j] = data[i-1][j];
				}
				for (int[] update: updates2) {
					data[i][update[0]] = update[1];
				}
				
				// update st
				st.upgrade(updates2);
			}

			for (int row = 0; row < data.length; row++) {
				for (int i = 0; i < numTests; i++) {
					int low = rand.nextInt(len);
					int high = rand.nextInt(len-low) + low;
					//int row = rand.nextInt(len);
					
					int expected = 0;
					for (int j = low; j <= high; j++) {
						expected += data[row][j];
					}
					
					int actual = st.query(row, low, high);
					assertEquals(expected, actual);
				}
			}
		}
	}
	
	@Test
	public void testPersistentSumPerformance() {
		int length = 100000;
		int numTests = 7;
		int maxValue = 1000;
		Random rand = new Random(0);
		
		for (int test = 1; test <= numTests; test++) {
			Node.counter = 0;
			long time = System.currentTimeMillis();
			int len = length;
			int[] data = new int[len];
			double log2n = 1 + (int)(Math.log(len) / Math.log(2));

			for (int j = 0; j < len; j++) {
				data[j] = rand.nextInt(maxValue) - maxValue/2;
			}
			SegmentTreePersistentGeneral st = new SegmentTreePersistentGeneral(data);

			long timeBuild = System.currentTimeMillis();
			// Do N updates, in N/logN batches of size logN  
			for (int i = 1; i < len/log2n; i++) {
				// do logN updates
				HashSet<Integer> updates = new HashSet<>();
				ArrayList<int[]> updates2 = new ArrayList<>();
				for (int j = 0; j < (int)log2n; j++) {
				//for (int j = 0; j < 5; j++) {
					int idx = rand.nextInt(len);
					while (updates.contains(idx)) {
						idx = rand.nextInt(len);
					}
					updates.add(idx);
					updates2.add(new int[]{idx, rand.nextInt(maxValue) - maxValue/2});
				}
				// update st
				st.upgrade(updates2);
			}
			timeBuild = System.currentTimeMillis() - timeBuild;

			long timeQ = System.currentTimeMillis();
			for (int i = 0; i < len; i++) {
				int row = rand.nextInt((int) (len/log2n));
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				st.query(row, low, high);
			}
			timeQ = System.currentTimeMillis() - timeQ;
			
			time = System.currentTimeMillis() - time;
			System.out.println("time: " + time + "\t builing: " + timeBuild + "\t querying: " + timeQ + "\t nodes created: "
			+ Node.counter + "\t nodes/version: " + (Node.counter/(double)len) + "\t nodes/update: " + (Node.counter/(double)len/log2n));
		}
	}
	
	public static class SegmentTreePersistentGeneralMax extends SegmentTreePersistentGeneral {
		protected int function(int a, int b) {
			return Math.max(a, b);
		}
		
		public SegmentTreePersistentGeneralMax(int[] input) {
			super(input);
		}
	}

	@Test
	public void testPersistentMax() {
		int maxLength = 100;
		int numTests = 500;
		int maxValue = 1000000;
		Random rand = new Random(0);
		
		for (int test = 1; test <= numTests; test++) {
			int len = rand.nextInt(maxLength)+1;
			int[][] data = new int[len][len];

			for (int j = 0; j < data[0].length; j++) {
				data[0][j] = rand.nextInt(maxValue) - maxValue/2;
			}
			SegmentTreePersistentGeneralMax st = new SegmentTreePersistentGeneralMax(data[0]);
			
			for (int i = 1; i < data.length; i++) {
				// do logN updates
				ArrayList<int[]> updates = new ArrayList<>();
				for (int j = 0; j < data.length; j++) {
					updates.add(new int[]{j, rand.nextInt(maxValue) - maxValue/2});
				}
				Collections.shuffle(updates, rand);
				ArrayList<int[]> updates2 = new ArrayList<>();
				for (int j = 0; j < 1 + (int)(Math.log(data.length) / Math.log(2)); j++) {
					updates2.add(updates.get(j));
				}
				
				// update data
				for (int j = 0; j < data.length; j++) {
					data[i][j] = data[i-1][j];
				}
				for (int[] update: updates2) {
					data[i][update[0]] = update[1];
				}
				
				// update st
				st.upgrade(updates2);
			}

			for (int row = 0; row < data.length; row++) {
				for (int i = 0; i < numTests; i++) {
					int low = rand.nextInt(len);
					int high = rand.nextInt(len-low) + low;
					//int row = rand.nextInt(len);
					
					int expected = Integer.MIN_VALUE;
					for (int j = low; j <= high; j++) {
						expected = Math.max(expected, data[row][j]);
					}
					
					int actual = st.query(row, low, high);
					if (expected != actual) {
						System.out.println();
						st.query(row, low, high);
					}
					assertEquals(expected, actual);
				}
			}
		}
	}
}
