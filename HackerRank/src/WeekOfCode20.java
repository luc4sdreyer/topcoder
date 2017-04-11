import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class WeekOfCode20 {
	public static void main(String[] args) {
//		divisibleSumPairs(System.in);
//		nonDivisibleSubset(System.in);
//		synchronousShopping(System.in);
//		testSynchronousShopping();
//		prefTestSynchronousShopping();
		simpleGame(System.in);
//		testSimpleGame();
//		joggingCats(System.in);
//		testJoggingCats();
//		catcationRentals(System.in);
//		testCatcationRentals();
	}

	public static void simpleGame(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int k = scan.nextInt();
		System.out.println(getDynamicWays(m, n, k));
		try {
			solve(m, n, k);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testCatcationRentals() {
		for (int j = 0; j < 10; j++) {
			long t1 = System.currentTimeMillis();
			Random rand = new Random(0);
							
			int n = 5000;
			int d = 10000;
			int k = 5000;
			int[][] req = new int[n][2];
			for (int i = 0; i < req.length; i++) {
				req[i][0] = rand.nextInt(d) + 1;
				req[i][1] = req[i][0] + rand.nextInt(d - req[i][0] + 1);
			}
			int[] q = new int[k];
			for (int i = 0; i < q.length; i++) {
				q[i] = rand.nextInt(k) + 1;
			}
			catcationRentals(n, d, k, req, q);
			System.out.println("time: " + (System.currentTimeMillis() - t1));
		}
	}

	public static void catcationRentals(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int d = scan.nextInt();
		int k = scan.nextInt();
		
		int[][] req = new int[n][2];
		for (int i = 0; i < req.length; i++) {
			req[i][0] = scan.nextInt();
			req[i][1] = scan.nextInt();
		}
		
		int[] q = new int[k];
		for (int i = 0; i < q.length; i++) {
			q[i] = scan.nextInt();
		}
		
		int[] res = catcationRentals(n, d, k, req, q);
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i]);
		}		
	}
	
	public static int[] catcationRentals(int n, int d, int k, int[][] req, int[] q) {
		int sqrtd = (int) Math.ceil(Math.sqrt(d));
		ArrayList<ArrayList<int[]>> partReq = new ArrayList<>();
		for (int i = 0; i < sqrtd; i++) {
			partReq.add(new ArrayList<>());
		}
		
		int[][] qs = new int[q.length][3];
		for (int i = 0; i < qs.length; i++) {
			qs[i][0] = q[i];
			qs[i][1] = i;
		}
		Arrays.sort(qs, (a,b) -> Integer.compare(a[0], b[0]));
		
		for (int i = 0; i < req.length; i++) {
			int reqLen = req[i][1] - req[i][0] + 1;   
			for (int j = 0; j < sqrtd; j++) {
				if (reqLen >= j*sqrtd) {
					partReq.get(j).add(req[i]);
				} else {
					break;
				}
			}
		}
		
		int querySubset = 0;
		SegmentTreeLazySum st = new SegmentTreeLazySum(new int[d+1]);
		ArrayList<int[]> subreq = null;
		for (int i = 0; i < qs.length; i++) {
			while (qs[i][0] > querySubset * sqrtd) {
				querySubset++;
			}
			subreq = partReq.get(querySubset-1);
			
			for (int j = 0; j < subreq.size(); j++) {
				int a = subreq.get(j)[0];
				int b = subreq.get(j)[1];
				if ((b - a + 1 >= qs[i][0])) {
					if (st.query_tree(a, b) == 0) {
						st.update_tree(a, b, 1);
					}
				}
			}
			qs[i][2] = st.query_tree(0, d);
		}
		
		Arrays.sort(qs, (a,b) -> Integer.compare(a[1], b[1]));
		int[] res = new int[qs.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = qs[i][2];
		}
		
		return res;
	}

	public static void testJoggingCats() {
		Random rand = new Random(0);
		ArrayList<int[]> stat = new ArrayList<>();
		
		for (int x = 0; x < 100; x++) {
			//int n = rand.nextInt(50000)+1;
			//int n = rand.nextInt(3000)+1;
			//int m = rand.nextInt(100000 - (n-1)) + 1;
			int n = 50;
			int m = 100;
			HashMap<Integer, ArrayList<Integer>> g = new HashMap<>();
			HashMap<Integer, HashSet<Integer>> gset = new HashMap<>();
			for (int i = 1; i <= n; i++) {
				g.put(i, new ArrayList<Integer>());
				gset.put(i, new HashSet<Integer>());
			}
			
			for (int i = 2; i <= n; i++) {
				g.get(i).add(i-1);
				g.get(i-1).add(i);
				gset.get(i).add(i-1);
				gset.get(i-1).add(i);
			}
			
			for (int i = 0; i < m; i++) {
				int a = rand.nextInt(n)+1;
				//int a = rand.nextInt(4)+1;
				int b = rand.nextInt(n)+1;
				int tries = 0;
				while (a == b || (gset.get(a).contains(b) && tries < 10)) {
					if (a == b) {
						b = rand.nextInt(n)+1;
					}
					if (gset.get(a).contains(b)) {
						tries++;
					}
				}
				if (tries < 10) {
					g.get(a).add(b);
					g.get(b).add(a);
					gset.get(a).add(b);
					gset.get(b).add(a);
				}
			}

			long t1 = System.currentTimeMillis();
			long r1 = joggingCats(n, m, g);
			long r2 = joggingCats3(n, m, g);
			if (r1 != r2) {
				System.out.println("fail: " + r1 + "\t" + r2);
			}
			int time = (int) (System.currentTimeMillis() - t1);
			stat.add(new int[]{n, m, time});
		}
		
//		Collections.sort(stat, (a, b) -> Integer.compare(a[2], b[2]));
//		for (int i = 0; i < stat.size(); i++) {
//			System.out.println(Arrays.toString(stat.get(i)));
//		}
		
	}
	
	/**
	 * Segment tree with lazy updates. The default implementation is the max function.
	 * 
	 * Lazy updates are a bit of a hack, because they might to be changed if the function changes.
	 * For example with the max function a lazy parent node the update is just added, but with the sum function
	 * the update must be multiplied by the number of children.
	 */

	public static class SegmentTreeLazy {
		protected int tree[];
		protected int lazy[];
		private int N;
		private int MAX;
		protected int neg_inf = Integer.MIN_VALUE;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 * If one of the inputs are neg_inf the function should ignore them. If both are, it should probably return neg_inf.
		 */
		protected int function(int a, int b) {
			return Math.max(a, b);
		}

		public SegmentTreeLazy(int[] a) {
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
			if (lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if (a != b) {
					lazy[node*2] += lazy[node]; // Mark child as lazy
					lazy[node*2+1] += lazy[node]; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if (a > b || a > j || b < i) { // Current segment is not within range [i, j]
				return;
			}

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] += value;

				if (a != b) { // Not leaf node
					lazy[node*2] += value;
					lazy[node*2 + 1] += value;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		/**
		 * Query tree to get max element value within range [i, j]
		 */
		public int query_tree(int i, int j) {
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
		protected int query_tree(int node, int a, int b, int i, int j) {
			if (a > b || a > j || b < i) {
				return neg_inf; // Out of range
			}

			if (lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if (a != b) {
					lazy[node*2] += lazy[node]; // Mark child as lazy
					lazy[node*2+1] += lazy[node]; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if (a >= i && b <= j) { // Current segment is totally within range [i, j]
				return tree[node];
			}

			int q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			int q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			int res = function(q1, q2); // Return final result
			return res;
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
		public int search(int i, int value) {
			int left = i;
			int right = N-1;
			int mid = 0;
			int f = 0;
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

	public static class SegmentTreeLazySum extends SegmentTreeLazy {
		@Override
		protected int function(int a, int b) {
			return a + b;
		}
		
		public SegmentTreeLazySum(int[] a) {
			super(a);
			this.neg_inf = 0;
		}

		@Override
		protected void update_tree(int node, int a, int b, int i, int j, int value) {
			if (lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if (a != b) {
					lazy[node*2] += lazy[node]/2; // Mark child as lazy
					lazy[node*2+1] += lazy[node]/2; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if (a > b || a > j || b < i) // Current segment is not within range [i, j]
				return;

			if (a >= i && b <= j) { // Segment is fully within range
				tree[node] += value * (b - a + 1);

				if (a != b) { // Not leaf node
					lazy[node*2] += value * (b - a + 1)/2;
					lazy[node*2 + 1] += value * (b - a + 1)/2;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1 + node*2, 1 + (a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		@Override
		protected int query_tree(int node, int a, int b, int i, int j) {
			if (a > b || a > j || b < i) {
				return neg_inf; // Out of range
			}

			if (lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if (a != b) {
					lazy[node*2] += lazy[node]/ 2; // Mark child as lazy
					lazy[node*2+1] += lazy[node]/ 2; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if (a >= i && b <= j) { // Current segment is totally within range [i, j]
				return tree[node];
			}

			int q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			int q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			int res = function(q1, q2); // Return final result
			return res;
		}
	}

	public static void joggingCats(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		
		HashMap<Integer, ArrayList<Integer>> g = new HashMap<>();
		for (int i = 1; i <= n; i++) {
			g.put(i, new ArrayList<Integer>());
		}
		for (int i = 0; i < m; i++) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			g.get(a).add(b);
			g.get(b).add(a);
		}
		
		System.out.println(joggingCats3(n, m, g));
	}
	
	public static class Pair {
		int a;
		ArrayList<Integer> b;
		public Pair(int a, ArrayList<Integer> b) {
			super();
			this.a = a;
			this.b = b;
		}
		public String toString() {
			return a + ": " + b.toString();
		}
	}
	
	public static long joggingCats3(int n, int m, HashMap<Integer, ArrayList<Integer>> g) {
		HashMap<Integer, Integer> pairs = new HashMap<>();
		HashMap<Integer, HashSet<Integer>> skipMap = new HashMap<>();
		
		ArrayList<Pair> glist = new ArrayList<>();
		glist.add(new Pair(0, new ArrayList<>()));
		for (int x = 1; x <= n; x++) {
			glist.add(new Pair(x, g.get(x)));
			skipMap.put(x, new HashSet<Integer>());
		}
		Collections.sort(glist, (a, b) -> Integer.compare(a.b.size(), b.b.size()));
		
		for (int x = 1; x <= n; x++) {
			ArrayList<Integer> neighbours = glist.get(x).b;
			int myIdx = glist.get(x).a;
			HashSet<Integer> skip = skipMap.get(myIdx);
			for (int i = 0; i < neighbours.size(); i++) {
				if (!skip.contains(neighbours.get(i))) {
					for (int j = i+1; j < neighbours.size(); j++) {
						int a = neighbours.get(i);
						int b = neighbours.get(j);
						if (!skip.contains(b)) {
							if (a > b) {
								int temp = a;
								a = b;
								b = temp;
							}
							int p = a * 100000 + b;
							if (!pairs.containsKey(p)) {
								pairs.put(p, 1);
							} else {
								pairs.put(p, pairs.get(p) + 1);
							}
							skipMap.get(a).add(myIdx);
							skipMap.get(b).add(myIdx);
						}
					}
				}
			}
		}
		
		long loops = 0;
		for (Integer p: pairs.values()) {
			loops += (p * (p-1))/2;
		}
		//loops /= 2;
		return loops;
	}
	
	public static long joggingCats(int n, int m, HashMap<Integer, ArrayList<Integer>> g) {
		HashMap<Integer, Integer> pairs = new HashMap<>();
		
		for (int x = 1; x <= n; x++) {
			ArrayList<Integer> neighbours = g.get(x);			
			for (int i = 0; i < neighbours.size(); i++) {
				for (int j = i+1; j < neighbours.size(); j++) {
					int a = neighbours.get(i);
					int b = neighbours.get(j);
					if (a > b) {
						int temp = a;
						a = b;
						b = temp;
					}
					int p = a * 100000 + b;
					if (!pairs.containsKey(p)) {
						pairs.put(p, 1);
					} else {
						pairs.put(p, pairs.get(p) + 1);
					}
				}
			}
		}
		
		long loops = 0;
		for (Integer p: pairs.values()) {
			loops += (p * (p-1))/2;
		}
		loops /= 2;
		return loops;
	}
	
	public static long joggingCats2(int n, int m, HashMap<Integer, ArrayList<Integer>> g) {
		HashMap<Integer, Integer> depth = setDepth(g, 1);
		HashMap<Integer, Integer> upPairs = new HashMap<>(); 
		HashMap<Integer, Integer> downPairs = new HashMap<>();
		HashMap<Integer, Integer> pairs = null;
		
		for (int x = 1; x <= n; x++) {
			ArrayList<Integer> neighbours = g.get(x);
			int myDepth = depth.get(x);
			
			for (int back = 0; back < 2; back++) {
				boolean less = back == 0 ? true : false;
				for (int i = 0; i < neighbours.size(); i++) {
					if ((!less && depth.get(neighbours.get(i)) > myDepth) || (less && depth.get(neighbours.get(i)) < myDepth)) {
						for (int j = i+1; j < neighbours.size(); j++) {
							if ((!less && depth.get(neighbours.get(j)) > myDepth) || (less && depth.get(neighbours.get(j)) < myDepth)) {
								int p = neighbours.get(i) * 100000 + neighbours.get(j);
								if (less) {
									pairs = upPairs;
								} else {
									pairs = downPairs;
								}
								if (!pairs.containsKey(p)) {
									pairs.put(p, 1);
								} else {
									pairs.put(p, pairs.get(p) + 1);
								}
							}
						}
					}
				}
			}
		}
		
		long loops = 0;
		for (Integer p: upPairs.keySet()) {
			if (downPairs.containsKey(p)) {
				loops += upPairs.get(p) * downPairs.get(p);
			}
		}
		return loops;
	}

	public static HashMap<Integer, Integer> setDepth(HashMap<Integer, ArrayList<Integer>> graphRef, int start) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);
		HashMap<Integer, Integer> depth = new HashMap<>();
		
		depth.put(start, 0);
		ArrayList<Integer> neighbours = graphRef.get(start);
		for (Integer i: neighbours) {
			queue.add(i);
		}
		
		while (!queue.isEmpty()) {
			Integer top = queue.poll();
			if (depth.containsKey(top)) {
				continue;
			}
			neighbours = graphRef.get(top);
			int max = 0;
			for (Integer i: neighbours) {
				Integer d = depth.get(i);
				if (d != null) {
					max = Math.max(max, d);
				} else {
					queue.add(i);
				}
			}
			depth.put(top, max+1);
		}
		
		return depth;
	}
		
	public static int setBit(int x, int i) {
		return (x | (1 << i));
	}
	
	public static boolean getBit(int x, int i) {
		return (x & (1 << i)) != 0;
	}

	public static void testSynchronousShopping() {
		Random rand = new Random(0);
		int tests = 0;
		for (int n = 2; n <= 20; n++) {
			for (int m = 0; m <= 20 && m <= n * 10; m++) {
				for (int k = 1; k <= 10; k++) {
					for (int overfish = 0; overfish <= k; overfish++) {

						int[][] cost = new int[n+1][n+1];
						int[] fish = new int[n+1];
						
						for (int i = 2; i < cost.length; i++) {
							//int w = rand.nextInt(10000) + 1;
							int w = rand.nextInt(10) + 1;
							cost[i-1][i] = w;
							cost[i][i-1] = w;
						}
						
						for (int i = 0; i < m; i++) {
							//int w = rand.nextInt(10000) + 1;
							int w = rand.nextInt(10) + 1;
							int a = rand.nextInt(cost.length-1) + 1;
							int b = rand.nextInt(cost.length-1) + 1;
							while (a == b) {
								b = rand.nextInt(cost.length-1) + 1;
							}
							if (cost[a][b] == 0) {
								cost[a][b] = w;
								cost[b][a] = w;
							}
						}
						
						for (int i = 0; i < k; i++) {
							int a = rand.nextInt(fish.length-1) + 1;
							fish[a] = setBit(fish[a], i);
						}
						
						if (overfish > 0) {
							for (int i = 1; i < fish.length; i++) {
								int over = rand.nextInt(overfish) + 1;
								for (int j = 0; j < over; j++) {
									fish[i] = setBit(fish[i], rand.nextInt(k));
								}
							}
						}
						
						int res1 = synchronousShopping(fish, cost, n, k);
						int res2 = synchronousShopping4(fish, cost, n, k);
						if (res1 != res2) {
							System.out.println("fail: ");
							res1 = synchronousShopping(fish, cost, n, k);
							res2 = synchronousShopping4(fish, cost, n, k);
						}
						tests++;
					}
				}
			}
		}

//		long t1 = System.currentTimeMillis();
//		testSynchronousShopping(362, 1949, 10, 0, rand);
//		int time = (int) (System.currentTimeMillis() - t1);
//		System.out.println("time: " + time);
	}
	
	public static void prefTestSynchronousShopping() {
		Random rand = new Random(0);
		ArrayList<int[]> stat = new ArrayList<>();
		
		for (int test = 0; test < 1; test++) {
			
			int n = rand.nextInt(1000) + 2;
			int m = rand.nextInt(2000) + 1;
			//int k = rand.nextInt(10) + 1;
			int k = 10;
			//int over = rand.nextInt(10);
			int overfish = 0;

			long t1 = System.currentTimeMillis();
			int[][] cost = new int[n+1][n+1];
			int[] fish = new int[n+1];
			
			for (int i = 2; i < cost.length; i++) {
				//int w = rand.nextInt(10000) + 1;
				int w = rand.nextInt(10) + 1;
				cost[i-1][i] = w;
				cost[i][i-1] = w;
			}
			
			for (int i = 0; i < m; i++) {
				//int w = rand.nextInt(10000) + 1;
				int w = rand.nextInt(10) + 1;
				int a = rand.nextInt(cost.length-1) + 1;
				int b = rand.nextInt(cost.length-1) + 1;
				while (a == b) {
					b = rand.nextInt(cost.length-1) + 1;
				}
				if (cost[a][b] == 0) {
					cost[a][b] = w;
					cost[b][a] = w;
				}
			}
			
			for (int i = 0; i < k; i++) {
				int a = rand.nextInt(fish.length-1) + 1;
				fish[a] = setBit(fish[a], i);
			}
			
			if (overfish > 0) {
				for (int i = 1; i < fish.length; i++) {
					int over = rand.nextInt(overfish) + 1;
					for (int j = 0; j < over; j++) {
						fish[i] = setBit(fish[i], rand.nextInt(k));
					}
				}
			}
			
			int res2 = synchronousShopping4(fish, cost, n, k);
			
			int time = (int) (System.currentTimeMillis() - t1);
			stat.add(new int[]{n, m, k, overfish, time});
		}
		
		Collections.sort(stat, (a, b) -> Integer.compare(a[4], b[4]));
		for (int i = 0; i < stat.size(); i++) {
			System.out.println(Arrays.toString(stat.get(i)));
		}
	}

	public static void synchronousShopping(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int k = scan.nextInt();
		int[] fish = new int[n+1];
		for (int i = 1; i <= n; i++) {
			int t = scan.nextInt();
			for (int j = 0; j < t; j++) {
				fish[i] = setBit(fish[i], scan.nextInt()-1);
			}
		}
		int[][] cost = new int[n+1][n+1];
		for (int i = 1; i <= m; i++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			cost[x][y] = scan.nextInt();
			cost[y][x] = cost[x][y];
		}
		
		System.out.println(synchronousShopping4(fish, cost, n, k));
	}

	public static int synchronousShopping4(int[] fish, int[][] cost, int n, int k) {
		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
		for (int i = 0; i < cost.length; i++) {
			ArrayList<Integer> temp = new ArrayList<>();
			adj.add(temp);
			for (int j = 0; j < cost[0].length; j++) {
				if (cost[i][j] > 0) {
					temp.add(j);
				}
			}
		}
		
		int[] h = new int[n+1];
		setH(fish, cost, k, h, adj);

		int len = k;
		int N = 1 << len;
		int min = getPath(fish, cost, k, h, adj, false);

		return min;
	}

	public static int getPath(int[] fish, int[][] cost, int k, int[] h, ArrayList<ArrayList<Integer>> adj, boolean debug) {
		PriorityQueue<Node5> queue = new PriorityQueue<>();
		Node5 top = new Node5(1, 0, fish[1], 0);
		queue.add(top);
		
		int[][] visited = new int[cost.length][1 << k];
		for (int i = 0; i < visited.length; i++) {
			Arrays.fill(visited[i], 100000000);
		}
		int visits = 0;
		int passes = 0;

		while (!queue.isEmpty()) {
			top = queue.poll();
			passes++;
			if (top.travelled >= visited[top.i][top.state]) {
				continue;
			}
			visited[top.i][top.state] = top.travelled;
			visits++;
			
			ArrayList<Integer> neigh = adj.get(top.i); 
			for (int i = 0; i < neigh.size(); i++) {
				int nextState = top.state | fish[neigh.get(i)];
				Node5 next = new Node5(neigh.get(i), top.travelled + cost[top.i][neigh.get(i)], nextState, 0);
				next.v = next.travelled; // + h[neigh.get(i)];
				queue.add(next);
				
//				nextState = top.state;
//				next = new Node4(neigh.get(i), top.travelled + cost[top.i][neigh.get(i)], nextState, top, 0, top.steps+1);
//				next.v = next.travelled; /// + h[neigh.get(i)];
//				queue.add(next);
			}
		}
		
		int min = 100000000;
		for (int i = 0; i < visited[0].length; i++) {
			for (int j = i; j < visited[0].length; j++) {
				if ((i | j) == visited[0].length-1) {
					int dist = Math.max(visited[cost.length-1][i], visited[cost.length-1][j]);
					if (dist < min) {
						min = dist;
					}
				}
			}
		}
		
		return min;
	}

	public static int synchronousShopping(int[] fish, int[][] cost, int n, int k) {
		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
		for (int i = 0; i < cost.length; i++) {
			ArrayList<Integer> temp = new ArrayList<>();
			adj.add(temp);
			for (int j = 0; j < cost[0].length; j++) {
				if (cost[i][j] > 0) {
					temp.add(j);
				}
			}
		}
		
		int[] h = new int[n+1];
		setH(fish, cost, k, h, adj);

		int len = k;
		int N = 1 << len;
		int[] pathCost = new int[N];
		
		for (int i = 0; i < N; i++) {			
			pathCost[i] = getPath(fish, cost, k, h, adj, i, false);
		}
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < pathCost.length; i++) {
			int dist = Math.max(pathCost[i], pathCost[pathCost.length - i - 1]);
			if (dist < min) {
				min = dist;
			}
		}
		
		return min;
	}

	private static void setH(int[] fish, int[][] cost, int k, int[] h, ArrayList<ArrayList<Integer>> adj) {
		PriorityQueue<Node4> queue = new PriorityQueue<>();
		Node4 top = new Node4(cost.length-1, 0, fish[cost.length-1], null, 0, 1);
		queue.add(top);
		int visit = 0;
		
		Arrays.fill(h, -1);

		while (!queue.isEmpty()) {
			top = queue.poll();
			if (h[top.i] >= 0) {
				continue;
			}
			visit++;
			h[top.i] = top.travelled;
			
			ArrayList<Integer> neigh = adj.get(top.i); 
			for (int i = 0; i < neigh.size(); i++) {
				Node4 next = new Node4(neigh.get(i), top.travelled + cost[top.i][neigh.get(i)], top.state | fish[neigh.get(i)], top, 0, top.steps+1);
				next.v = next.travelled;
				queue.add(next);
			}
		}
	}

	public static int getPath(int[] fish, int[][] cost, int k, int[] h, ArrayList<ArrayList<Integer>> adj, int mask, boolean debug) {
		PriorityQueue<Node4> queue = new PriorityQueue<>();
		Node4 top = new Node4(1, 0, fish[1], null, 0, 1);
		queue.add(top);
		
		boolean[][] visited = new boolean[cost.length][1 << k];
		int visits = 0;

		while (!queue.isEmpty()) {
			top = queue.poll();
			//int fishCollected = Integer.bitCount(mask & top.state); 
			int fishCollected = (mask & top.state);
			if (visited[top.i][fishCollected]) {
				continue;
			}
			visited[top.i][fishCollected] = true;
			visits++;
			if (debug) {
				System.out.println("loc: " + top.i + "\t fishCollected: " + fishCollected);
			}
			
			//if (top.i == cost.length-1 && fishCollected >= k) {
			if (top.i == cost.length-1 && fishCollected == mask) {
				break;
			}
			
			ArrayList<Integer> neigh = adj.get(top.i); 
			for (int i = 0; i < neigh.size(); i++) {
				int nextState = top.state | fish[neigh.get(i)];
				if (visited[neigh.get(i)][nextState]) {
					continue;
				}
				Node4 next = new Node4(neigh.get(i), top.travelled + cost[top.i][neigh.get(i)], nextState, top, 0, top.steps+1);
				next.v = next.travelled + h[neigh.get(i)];
				queue.add(next);
			}
		}
		
		//if (!(top.i == cost.length-1 && Integer.bitCount(mask & top.state) >= k)) {
//		if (!(top.i == cost.length-1 && (mask & top.state) == mask)) {
//			System.out.println("fail");
//			getPath(fish, cost, k, h, adj, mask, true);
//		}
//		System.out.println("visits: " + visits + "\t mask: " + mask);
				
//		Node4 current = top;
//		ArrayList<Node4> path = new ArrayList<>();
//		while (current != null) {
//			path.add(current);
//			current = current.parent;
//		}
//		Collections.reverse(path);
		
		return top.travelled;
	}

	public static int synchronousShopping2(int[] fish, int[][] cost, int n, int k) {
		ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
		for (int i = 0; i < cost.length; i++) {
			ArrayList<Integer> temp = new ArrayList<>();
			adj.add(temp);
			for (int j = 0; j < cost[0].length; j++) {
				if (cost[i][j] > 0) {
					temp.add(j);
				}
			}
		}
		
		int[] h = new int[n+1];
		setH(fish, cost, k, h, adj);
		int[][] dist = new int[1 << k][n+1];
		int[][] begin = new int[1 << k][n+1];
		
		int[] F = new int[1 << k];
		int[] P = new int[1 << k];
		for (int i = 0; i < P.length; i++) {
			P[i] = 1;
		}
		
		for (int i = 0; i < (1 << k); i++) {
			setH(fish, cost, k, dist[i], begin[i], adj, i);
		}
		
		F[0] = 0;
		for (int i = 1; i < (1 << k); i++) {
			int min = Integer.MAX_VALUE;
			int end = 0;
			for (int j = 0; j < i; j++) {
				if ((~i & j) == 0 && dist[i-j][P[j]] >= 0 && F[j] < Integer.MAX_VALUE) {
					int d = F[j] + dist[i-j][P[j]];
					if (d < min) {
						min = d;
						end = begin[i-j][P[j]];
					}
				}
			}
			F[i] = min;
			P[i] = end;
		}
		
		int[] G = new int[1 << k];
		// Fastest way to the exit should take into account what the exit is selling.

		for (int i = 0; i < (1 << k); i++) {
			int min = Integer.MAX_VALUE;
			int eMask = ~fish[fish.length-1] & i;
			for (int j = 0; j < (1 << k); j++) {
				if ((eMask & j) == eMask && F[j] < Integer.MAX_VALUE) {
					int d = F[j] + h[P[j]];
					if (d < min) {
						min = d;
					}
				}
			}
			G[i] = min;
		}
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < (1 << k); i++) {
			for (int j = 0; j < (1 << k); j++) {
//				if (((i | j) + 1 == k) && (min > Math.min(F[i], F[j]))) {
//					min = Math.min(F[i], F[j]);
//				}
				if (((i | j) == (1 << k) - 1) && (min > Math.max(G[i], G[j])) && G[i] < Integer.MAX_VALUE && G[j] < Integer.MAX_VALUE) {
					min = Math.max(G[i], G[j]);
				}
			}
		}
		
		return min; //getPath2(fish, cost, k, h, adj, false);
	}

	public static void setH(int[] fish, int[][] cost, int k, int[] h, int[] begin, ArrayList<ArrayList<Integer>> adj, int mask) {
		PriorityQueue<Node4> queue = new PriorityQueue<>();

		Arrays.fill(h, -1);
		
		Node4 top = null;
		for (int i = 1; i < fish.length; i++) {
			if (Integer.bitCount(fish[i] & mask) >= Integer.bitCount(mask)) {
				queue.add(new Node4(i, 0, fish[i], null, 0, 1));
				begin[i] = i;
			}
		}
		int visit = 0;

		while (!queue.isEmpty()) {
			top = queue.poll();
			if (h[top.i] >= 0) {
				continue;
			}
			visit++;
			h[top.i] = top.travelled;
			if (top.parent != null) {
				begin[top.i] = top.parent.i;
			}
			
			ArrayList<Integer> neigh = adj.get(top.i); 
			for (int i = 0; i < neigh.size(); i++) {
				Node4 next = new Node4(neigh.get(i), top.travelled + cost[top.i][neigh.get(i)], top.state | fish[neigh.get(i)], top.parent, 0, top.steps+1);
				if (top.parent == null) {
					next.parent = top;
				}
				next.v = next.travelled;
				queue.add(next);
			}
		}
	}
	
	public static int getPath2(int[] fish, int[][] cost, int k, int[] h, ArrayList<ArrayList<Integer>> adj, boolean debug, int start, int end, int mask) {
		PriorityQueue<Node4> queue = new PriorityQueue<>();
		Node4 top = new Node4(start, 0, fish[start], null, 0, 1);
		queue.add(top);
		
		boolean[] visited = new boolean[cost.length];
		int visits = 0;
		
		while (!queue.isEmpty()) {
			top = queue.poll();
			if (visited[top.i]) {
				continue;
			}
			visited[top.i] = true;
			visits++;
			if (debug) {
				System.out.println("loc: " + top.i + "\t fishCollected: " + top.state);
			}
			
			if ((end > 0 && top.i == end) || ((top.state & mask) == mask)) {
				return top.travelled;
			}
			
			ArrayList<Integer> neigh = adj.get(top.i); 
			for (int i = 0; i < neigh.size(); i++) {
				int nextState = top.state | fish[neigh.get(i)];
				Node4 next = new Node4(neigh.get(i), top.travelled + cost[top.i][neigh.get(i)], nextState, top, 0, top.steps+1);
				next.v = next.travelled + h[neigh.get(i)];
				queue.add(next);
			}
		}
		
		return -1;
	}
	
	public static class Node5 implements Comparable<Node5> {
		private int i;
		private int travelled;
		private int state;
		private int v;
		
		public Node5(int i, int travelled, int state, int v) {
			this.i = i;
			this.travelled = travelled;
			this.state = state;
			this.v = v;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int)(state / Integer.MAX_VALUE);
			result = prime * result + (int)(state % Integer.MAX_VALUE);
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node4 other = (Node4) obj;
			if (state != other.state)
				return false;
			return true;
		}
		
		public String toString() {
			return i + "";
		}

		@Override
		public int compareTo(Node5 o) {
			return Integer.compare(this.v, o.v);
		}
	}
	
	public static class Node4 implements Comparable<Node4> {
		private int i;
		private int travelled;
		private int state;
		private Node4 parent;
		private int v;
		private int steps;
		
		public Node4(int i, int travelled, int state, Node4 parent, int v, int steps) {
			super();
			this.i = i;
			this.travelled = travelled;
			this.state = state;
			this.parent = parent;
			this.v = v;
			this.steps = steps;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int)(state / Integer.MAX_VALUE);
			result = prime * result + (int)(state % Integer.MAX_VALUE);
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node4 other = (Node4) obj;
			if (state != other.state)
				return false;
			return true;
		}
		
		public String toString() {
			return i + "";
		}

		@Override
		public int compareTo(Node4 o) {
			return Integer.compare(this.v, o.v);
		}
	}
	
	public static int[][] sgf = null;
	
	public static void initSGF() {
		sgf = new int[601][601]; // K N
		for (int k = 2; k < sgf.length; k++) {
			if (k == 2) {
				for (int n = 1; n < sgf.length; n++) {
					sgf[k][n] = (n % 2 == 0) ? 1 : 0; 
				}
			} else if (k == 3) {
				sgf[k] = new int[]{
0, 0, 1, 2, 3, 1, 4, 3, 2, 4, 5, 6, 7, 8, 9, 7, 6, 9, 8, 11, 10, 12, 13, 10, 11, 13, 12, 15, 14, 16, 17, 5, 15, 17, 16, 19, 18, 20, 21, 18, 19, 21, 20, 23, 22, 25, 24, 22, 23, 24, 25, 26, 27, 29, 28, 27, 26, 28, 29, 30, 31, 14, 32, 31, 30, 32, 33, 34, 35, 37, 36, 35, 34, 36, 37, 38, 39, 40, 41, 39, 38, 41, 40, 43, 42, 44, 45, 42, 43, 45, 44, 47, 46, 48, 49, 46, 47, 49, 48, 51, 50, 52, 53, 50,
51, 53, 52, 55, 54, 57, 56, 54, 55, 56, 57, 58, 59, 61, 60, 59, 58, 60, 61, 62, 63, 64, 65, 63, 62, 65, 64, 67, 66, 68, 69, 66, 67, 69, 68, 71, 70, 73, 72, 70, 71, 72, 73, 74, 75, 77, 76, 75, 74, 76, 77, 78, 79, 81, 80, 79, 78, 80, 81, 82, 83, 85, 84, 83, 82, 84, 85, 86, 87, 88, 89, 87, 86, 89, 88, 91, 90, 92, 93, 90, 91, 93, 92, 95, 94, 96, 97, 94, 95, 97, 96, 99, 98, 100, 101, 33, 99, 101,
100, 103, 102, 105, 104, 102, 103, 104, 105, 106, 107, 109, 108, 107, 106, 108, 109, 110, 111, 113, 112, 111, 110, 112, 113, 114, 115, 117, 116, 115, 114, 116, 117, 118, 119, 120, 121, 119, 118, 121, 120, 123, 122, 124, 125, 122, 123, 125, 124, 127, 126, 128, 129, 126, 127, 129, 128, 131, 130, 132, 133, 130, 131, 133, 132, 135, 134, 137, 136, 134, 135, 136, 137, 138, 139, 141, 140, 139, 138,
140, 141, 142, 143, 145, 144, 143, 142, 144, 145, 146, 147, 149, 148, 147, 146, 148, 149, 150, 151, 152, 153, 151, 150, 153, 152, 155, 154, 156, 157, 154, 155, 157, 156, 159, 158, 160, 161, 158, 159, 161, 160, 163, 162, 164, 165, 162, 163, 165, 164, 167, 166, 169, 168, 166, 167, 168, 169, 170, 171, 98, 172, 171, 170, 172, 173, 174, 175, 177, 176, 175, 174, 176, 177, 178, 179, 181, 180, 179, 178,
180, 181, 182, 183, 184, 185, 183, 182, 185, 184, 187, 186, 188, 189, 186, 187, 189, 188, 191, 190, 193, 192, 190, 191, 192, 193, 194, 195, 197, 196, 195, 194, 196, 197, 198, 199, 200, 201, 199, 198, 201, 200, 203, 202, 204, 205, 202, 203, 205, 204, 207, 206, 208, 209, 206, 207, 209, 208, 211, 210, 212, 213, 210, 211, 213, 212, 215, 214, 217, 216, 214, 215, 216, 217, 218, 219, 221, 220, 219, 218,
220, 221, 222, 223, 225, 224, 223, 222, 224, 225, 226, 227, 229, 228, 227, 226, 228, 229, 230, 231, 232, 233, 231, 230, 233, 232, 235, 234, 236, 237, 234, 235, 237, 236, 239, 238, 240, 241, 238, 239, 241, 240, 243, 242, 244, 245, 242, 243, 245, 244, 247, 246, 249, 248, 246, 247, 248, 249, 250, 251, 253, 252, 251, 250, 252, 253, 254, 255, 256, 257, 173, 254, 257, 256, 259, 258, 260, 261, 258, 259,
261, 260, 263, 262, 265, 264, 262, 263, 264, 265, 266, 267, 269, 268, 267, 266, 268, 269, 270, 271, 273, 272, 271, 270, 272, 273, 274, 275, 277, 276, 275, 274, 276, 277, 278, 279, 280, 281, 279, 278, 281, 280, 283, 282, 284, 285, 282, 283, 285, 284, 287, 286, 288, 289, 286, 287, 289, 288, 291, 290, 292, 293, 290, 291, 293, 292, 295, 294, 297, 296, 294, 295, 296, 297, 298, 299, 301, 300, 299, 298};
			} else {
				for (int n = 1; n < sgf.length; n++) {
					sgf[k][n] = n - 1; 
				}
			}
		}
	}

	public static void testSimpleGame() {
		if (sgf == null) {
			initSGF();
		}
		for (int k = 2; k <= 6; k++) {
			long t1 = System.currentTimeMillis();
			getDynamicWays(10, 600, k);
			System.out.println(System.currentTimeMillis() - t1);
		}
//		Random rand = new Random(0);
//		for (int m = 1; m <= 6; m++) {
//			for (int n = m; n <= 20; n++) {
//				for (int k = 2; k <= 10; k++) {
//					int res1 = verifyLemma1(m, n, k);
//					int res2 = (int) getDynamicWays(m, n, k);
//					if (res1 != res2) {
//						System.out.println("fail");
//					}
////					System.out.println("m: " + m + "\t n: " + n + "\t k: " + k + "\t" + );
//					//verifySGF(m, n, k, rand);
//				}
//			}
//		}
	}

	public static int verifyLemma1(int m, int n, int k) {
		int[] x = new int[m];
		int ways = 0;
		do {
			int sum = 0;
			int[] a = new int[m];
			for (int j = 0; j < a.length; j++) {
				a[j] = x[j]+1;
			}
			for (int j = 0; j < a.length; j++) {
				sum += a[j];
			}
			if (sum == n) {
//				ways++;
				int sgSum = 0;
				for (int j = 0; j < a.length; j++) {
					sgSum ^= sgf[k][a[j]];
				}
				boolean wayToWin = false;
				for (int i = 0; i < a.length; i++) {
					int tempSG = sgf[k][a[i]];
					int tempSum = sgSum ^ tempSG;
					for (int j = 0; j < tempSG; j++) {
						if ((tempSum ^ j) == 0) {
							wayToWin = true;
							break;
						}
					}
					if (wayToWin) {
						break;
					}
				}
				if (wayToWin) {
					ways++;
				}
//				if (!((wayToWin && sgSum != 0) || (!wayToWin && sgSum == 0))) {
//					System.out.println("this shouldn't happen");
//				}
//				if (sgSum == 0) {
//					System.out.println(Arrays.toString(a) + "\t" + sgSum);
//				} else {
//					System.out.println(Arrays.toString(a) + "\t" + sgSum);
//				}
			}
		} while (next_number(x, n));
		
//		long dw = getDynamicWays(m, n, k);
//		
//		if (ways != dw) {
//			System.out.println("\t\t\t\tfail: " + dw);
//		} else {
//			System.out.println("\t\t\t\tcorr: " + dw);
//		}
		
		return ways;
	}
	
	public static final int mod = 1000000007;
	
	public static final int[][] prec = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,},
			{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,},
			{0,0,0,2,2,4,4,6,6,8,8,10,10,12,12,14,14,16,16,18,18,20,20,22,22,24,24,26,26,28,28,30,30,32,32,34,34,36,36,38,38,40,40,42,42,44,44,46,46,48,48,50,50,52,52,54,54,56,56,58,58,60,60,62,62,64,64,66,66,68,68,70,70,72,72,74,74,76,76,78,78,80,80,82,82,84,84,86,86,88,88,90,90,92,92,94,94,96,96,98,98,100,100,102,102,104,104,106,106,108,108,110,110,112,112,114,114,116,116,118,118,120,120,122,122,124,124,126,126,128,128,130,130,132,132,134,134,136,136,138,138,140,140,142,142,144,144,146,146,148,148,150,150,152,152,154,154,156,156,158,158,160,160,162,162,164,164,166,166,168,168,170,170,172,172,174,174,176,176,178,178,180,180,182,182,184,184,186,186,188,188,190,190,192,192,194,194,196,196,198,198,200,200,202,202,204,204,206,206,208,208,210,210,212,212,214,214,216,216,218,218,220,220,222,222,224,224,226,226,228,228,230,230,232,232,234,234,236,236,238,238,240,240,242,242,244,244,246,246,248,248,250,250,252,252,254,254,256,256,258,258,260,260,262,262,264,264,266,266,268,268,270,270,272,272,274,274,276,276,278,278,280,280,282,282,284,284,286,286,288,288,290,290,292,292,294,294,296,296,298,298,300,300,302,302,304,304,306,306,308,308,310,310,312,312,314,314,316,316,318,318,320,320,322,322,324,324,326,326,328,328,330,330,332,332,334,334,336,336,338,338,340,340,342,342,344,344,346,346,348,348,350,350,352,352,354,354,356,356,358,358,360,360,362,362,364,364,366,366,368,368,370,370,372,372,374,374,376,376,378,378,380,380,382,382,384,384,386,386,388,388,390,390,392,392,394,394,396,396,398,398,400,400,402,402,404,404,406,406,408,408,410,410,412,412,414,414,416,416,418,418,420,420,422,422,424,424,426,426,428,428,430,430,432,432,434,434,436,436,438,438,440,440,442,442,444,444,446,446,448,448,450,450,452,452,454,454,456,456,458,458,460,460,462,462,464,464,466,466,468,468,470,470,472,472,474,474,476,476,478,478,480,480,482,482,484,484,486,486,488,488,490,490,492,492,494,494,496,496,498,498,500,500,502,502,504,504,506,506,508,508,510,510,512,512,514,514,516,516,518,518,520,520,522,522,524,524,526,526,528,528,530,530,532,532,534,534,536,536,538,538,540,540,542,542,544,544,546,546,548,548,550,550,552,552,554,554,556,556,558,558,560,560,562,562,564,564,566,566,568,568,570,570,572,572,574,574,576,576,578,578,580,580,582,582,584,584,586,586,588,588,590,590,592,592,594,594,596,596,598,598,},
			{0,0,0,0,3,3,10,12,21,19,36,42,55,57,78,82,105,93,136,150,171,181,210,222,253,249,300,316,351,351,406,408,465,415,528,558,595,621,666,694,741,753,820,852,903,919,990,1008,1081,1047,1176,1216,1275,1299,1378,1404,1485,1459,1596,1626,1711,1689,1830,1810,1953,1773,2080,2142,2211,2269,2346,2406,2485,2529,2628,2692,2775,2823,2926,2976,3081,3079,3240,3312,3403,3459,3570,3628,3741,3747,3916,3978,4095,4105,4278,4290,4465,4317,4656,4744,4851,4923,5050,5124,5253,5275,5460,5538,5671,5697,5886,5914,6105,5973,6328,6414,6555,6589,6786,6822,7021,6897,7260,7300,7503,7383,7750,7632,8001,7399,8256,8382,8515,8637,8778,8902,9045,9153,9316,9444,9591,9703,9870,9984,10153,10215,10440,10576,10731,10851,11026,11148,11325,11395,11628,11754,11935,12009,12246,12322,12561,12477,12880,13032,13203,13339,13530,13668,13861,13947,14196,14338,14535,14625,14878,14970,15225,15157,15576,15726,15931,16029,16290,16390,16653,16593,17020,17124,17391,17335,17766,17712,18145,17607,18528,18712,18915,19083,19306,19476,19701,19819,20100,20274,20503,20625,20910,21034,21321,21285,21736,21918,22155,22285,22578,22710,23005,22977,23436,23572,23871,23847,24310,24288,24753,24247,25200,25398,25651,25797,26106,26254,26565,26553,27028,27180,27495,27487,27966,27960,28441,27951,28920,29080,29403,29403,29890,29892,30381,29899,30876,30882,31375,30897,31878,31402,32385,30453,32896,33150,33411,33661,33930,34182,34453,34689,34980,35236,35511,35751,36046,36288,36585,36775,37128,37392,37675,37923,38226,38476,38781,38979,39340,39594,39903,40105,40470,40674,41041,41085,41616,41896,42195,42459,42778,43044,43365,43579,43956,44226,44551,44769,45150,45370,45753,45813,46360,46638,46971,47197,47586,47814,48205,48273,48828,49060,49455,49527,50086,50160,50721,50311,51360,51672,52003,52299,52650,52948,53301,53547,53956,54258,54615,54865,55278,55530,55945,56037,56616,56926,57291,57549,57970,58230,58653,58753,59340,59604,60031,60135,60726,60832,61425,61047,62128,62454,62835,63109,63546,63822,64261,64377,64980,65260,65703,65823,66430,66552,67161,66799,67896,68184,68635,68763,69378,69508,70125,69771,70876,71010,71631,71281,72390,72042,73153,71349,73920,74296,74691,75051,75466,75828,76245,76555,77028,77394,77815,78129,78606,78922,79401,79557,80200,80574,81003,81325,81810,82134,82621,82785,83436,83764,84255,84423,85078,85248,85905,85591,86736,87126,87571,87909,88410,88750,89253,89433,90100,90444,90951,91135,91806,91992,92665,92367,93528,93880,94395,94587,95266,95460,96141,95851,97020,97218,97903,97617,98790,98506,99681,97941,100576,100998,101475,101845,102378,102750,103285,103497,104196,104572,105111,105327,106030,106248,106953,106687,107880,108264,108811,109035,109746,109972,110685,110427,111628,111858,112575,112321,113526,113274,114481,112773,115440,115840,116403,116643,117370,117612,118341,118099,119316,119562,120295,120057,121278,121042,122265,120573,123256,123510,124251,124021,125250,125022,126253,124569,127260,127036,128271,126591,129286,127608,130305,124255,131328,131838,132355,132861,133386,133894,134421,134913,135460,135972,136503,136999,137550,138048,138601,139047,139656,140176,140715,141219,141778,142284,142845,143299,143916,144426,144991,145449,146070,146530,147153,147453,148240,148776,149331,149851,150426,150948,151525,151995,152628,153154,153735,154209,154846,155322,155961,156277,157080,157614,158203,158685,159330,159814,160461,160785,161596,162084,162735,163063,163878,164208,165025,164871,166176,166744,167331,167883,168490,169044,169653,170155,170820,171378,171991,172497,173166,173674,174345,174693,175528,176094,176715,177229,177906,178422,179101,},
			{0,0,0,0,0,4,4,20,28,56,48,120,152,220,244,364,412,560,464,816,920,1140,1252,1540,1716,2024,2048,2600,2840,3276,3396,4060,4236,4960,4160,5984,6280,7140,7476,8436,9012,9880,10192,11480,12208,13244,13860,15180,15908,17296,16912,19600,20488,22100,22916,24804,25892,27720,27712,30856,31992,34220,34436,37820,38156,41664,35904,45760,46344,50116,50804,54740,56596,59640,60432,64824,67104,70300,72388,76076,78484,82160,82512,88560,91280,95284,97972,102340,105532,109736,111584,117480,120848,125580,127924,134044,136596,142880,138368,152096,155000,161700,164628,171700,176004,182104,184400,192920,197632,204156,207876,215820,219924,227920,224848,240464,244840,253460,257028,266916,271748,280840,278656,295240,299512,310124,308420,325500,324044,341376,302848,357760,356808,374660,373940,392084,397844,410040,409680,428536,435840,447580,453956,467180,474868,487344,487056,508080,516480,529396,537748,551300,561292,573800,580704,596904,607328,620620,629140,644956,653956,669920,665792,695520,705152,721764,731524,748660,760956,776216,785456,804440,817544,833340,845268,862924,875532,893200,896720,924176,937312,955860,968148,988260,1002252,1021384,1027168,1055240,1068784,1089836,1096500,1125180,1132244,1161280,1125184,1198144,1205944,1235780,1243796,1274196,1289828,1313400,1321168,1353400,1370832,1394204,1409828,1435820,1453028,1478256,1481424,1521520,1539552,1565620,1582868,1610564,1630444,1656360,1667232,1703016,1722720,1750540,1763284,1798940,1812292,1848224,1817472,1898400,1912472,1949476,1962964,2001460,2022628,2054360,2062544,2108184,2130272,2162940,2178244,2218636,2235444,2275280,2248016,2332880,2349608,2391444,2402436,2450980,2468676,2511496,2486144,2573000,2585464,2635500,2611140,2699004,2675148,2763520,2516224,2829056,2806216,2895620,2873268,2963220,2978708,3031864,3010384,3101560,3124288,3172316,3189188,3244140,3267764,3317040,3296656,3391024,3416736,3466100,3491156,3542276,3575276,3619560,3638496,3697960,3731904,3777484,3803732,3858140,3885988,3939936,3917760,4022880,4052016,4106980,4136356,4192244,4230604,4278680,4307184,4366296,4406840,4455100,4492852,4545100,4584796,4636304,4654928,4728720,4769456,4822356,4861492,4917220,4960188,5013320,5040416,5110664,5152832,5209260,5238612,5309116,5339268,5410240,5360192,5512640,5544064,5616324,5648196,5721300,5767260,5827576,5859568,5935160,5984440,6044060,6090612,6154284,6203708,6265840,6293392,6378736,6429640,6492980,6543044,6608580,6662852,6725544,6767872,6843880,6898344,6963596,7009316,7084700,7131452,7207200,7197696,7331104,7379088,7456420,7503908,7583156,7640620,7711320,7753008,7840920,7899960,7971964,8023156,8104460,8157724,8238416,8239632,8373840,8427392,8510740,8557780,8649124,8704300,8789000,8794016,8930376,8979824,9073260,9079924,9217660,9225108,9363584,9110528,9511040,9519992,9660036,9669588,9810580,9864420,9962680,9972752,10116344,10178800,10271580,10326564,10428396,10491396,10586800,10593808,10746800,10812400,10908404,10972596,11071620,11145884,11236456,11288992,11402920,11477776,11571020,11632244,11740764,11803988,11912160,11888320,12085216,12150096,12259940,12324452,12436340,12516140,12614424,12673392,12794200,12876696,12975676,13049588,13158860,13235836,13343760,13362768,13530384,13608048,13718740,13789876,13908836,13989244,14100680,14129184,14294280,14369216,14489644,14520916,14686780,14719108,14885696,14651456,15086400,15120344,15288900,15322708,15493204,15572292,15699320,15728720,15907256,15994352,16117020,16191588,16328620,16411332,16542064,16537808,16757360,16841920,16974516,17052756,17193540,17286156,17414440,17451040,17637224,17725504,17861900,17906580,18088476,18135140,18316960,18096256,18547360,18594904,18779684,18821972,19013940,19100708,19250136,19257040,19488280,19576992,19728380,19774532,19970444,20023796,20214480,20001360,20460496,20509608,20708500,20721284,20958500,21009604,21210504,21001344,21464520,21480312,21720556,21513412,21978620,21772492,22238720,20689920,22500864,22296776,22765060,22561972,23031316,23053204,23299640,23098448,23570040,23631296,23842524,23867332,24117100,24180276,24393776,24195728,24672560,24744032,24953460,25019604,25236484,25347372,25521640,25551584,25808936,25921792,26098380,26167764,26389980,26466660,26683744,26488000,26979680,27059536,27277796,27357540,27578100,27704492,27880600,27955312,28185304,28320088,28492220,28613492,28801356,28931132,29112720,29148496,29426320,29558160,29742164,29866868,30060260,30200540,30380616,30458016,30703240,30838560,31028140,31114004,31355324,31443364,31684800,31469376,32016576,32107056,32350660,32441956,32687060,32830252,33025784,33117360,33366840,33520424,33710236,33854548,34055980,34209868,34404080,34488080,34754544,34911800,35107380,35262436,35462596,35630292,},
			{0,0,0,0,0,0,5,5,35,55,126,110,330,430,715,851,1365,1645,2380,2060,3876,4280,5985,6665,8855,10151,12650,13450,17550,19550,23751,25655,31465,34085,40920,36376,52360,53340,66045,68165,82251,87915,101270,105430,123410,132026,148995,158435,178365,190705,211876,215300,249900,262300,292825,307001,341055,360915,395010,406770,455126,477010,521855,539095,595665,616001,677040,620720,766480,758820,864501,861245,971635,995315,1088430,1094126,1215450,1251250,1353275,1391675,1502501,1557705,1663740,1684060,1837620,1893276,2024785,2086305,2225895,2308515,2441626,2507690,2672670,2766530,2919735,3010751,3183545,3287585,3464840,3462280,3764376,3837800,4082925,4165325,4421275,4546751,4780230,4875190,5160610,5306470,5563251,5709435,5989005,6161445,6438740,6497876,6913340,7062120,7413705,7567585,7940751,8142695,8495410,8595890,9078630,9268126,9691375,9815375,10334625,10471125,11009376,10358880,11716640,11527220,12457445,12285101,13232835,13300355,14043870,13906590,14891626,15017890,15777195,15899755,16701685,16955001,17666220,17598860,18671940,18876380,19720001,19943025,20811575,21180675,21947850,22185626,23130030,23541090,24359335,24753295,25637001,26109985,26964280,26996920,28342440,28689776,29772765,30145645,31256555,31800535,32795126,33212870,34389810,34998990,36041955,36656251,37752925,38466525,39524100,39957700,41356876,42031680,43252665,43948385,45212895,46040751,47239010,47889730,49332470,50176790,51494751,52258415,53727345,54548605,56031760,55711376,58409520,58792960,60862165,61282805,63391251,64183575,65998350,66489150,68685050,69586126,71452955,72354115,74303685,75411245,77238876,77813660,80260180,81288520,83369265,84431001,86567815,87874295,89857530,90899610,93240126,94598670,96717335,97998215,100290905,101692501,103962600,104085800,107734200,108684900,111607501,112601605,115584315,117015235,119666470,120685126,123855810,125388130,128154195,129626115,132563501,134263465,137085620,137514580,141722460,143033876,146475945,147765785,151348015,153069755,156340626,156945250,161455750,162894250,166695375,167393751,172061505,172808265,177556160,170352320,183181376,180488020,188939205,186312845,194831715,194495651,200860990,198371070,207029130,207054210,213338251,213211595,219790485,220847065,226387980,224176876,233132900,233467100,240027425,240414225,247073751,248761155,254274090,254585210,261630670,263470626,269145735,270724655,276821545,279036065,284660376,282991640,292664520,293590080,300836285,301843501,309177995,311524935,317691990,318841830,326380626,329011550,335246275,337812475,344291325,347567501,353518180,354725540,362929260,365769520,372527001,375420385,382313855,386052095,392292290,394966626,402464790,406246470,412833855,416249135,423402001,427189645,434171760,433133040,445145680,447098776,456326325,458382725,467716275,471322175,479318126,481576830,491134490,495094310,503167995,507083251,515421285,520166805,527897020,530494140,540597876,544961440,553526545,557980905,566685735,572070751,580078170,584525210,593706590,599277030,607573751,612953975,621682425,627531325,636035400,638554376,650635480,655388940,665485485,670353845,680588251,686633435,695946630,700960070,711563490,717911626,727441715,733710995,743584205,750483585,759993876,764424820,776673660,782950140,793626505,799925001,810855375,818089875,828363250,833785250,846153126,853114130,864228015,870154535,882590945,888772001,901244960,895682080,920193120,921445040,939438501,940839365,958984195,963869255,978833310,980530126,998988970,4510103,19454308,24790348,40232494,47390518,61326693,63568293,82740093,88882369,104475898,110739458,126537328,134814288,148927619,155109603,171650023,180218663,194707808,202869744,218104258,227258638,241842673,244591153,265926369,272958613,290358678,297555358,315142948,324566244,340282543,347724143,365780843,375707443,391641244,401439548,417867158,428846378,444462013,451523869,471429253,481497973,498772338,508904658,526494744,538164908,554599963,563859723,583091503,594592619,611972888,622342128,641247658,652174298,670919369,668405193,700991593,706455893,731467918,737127494,762351948,772101108,793647303,799637863,825357619,835866403,857486548,867740388,890037758,902425994,923014933,929039413,956421773,967371853,990261994,1314531,24539321,38108701,59257516,69595612,94420336,108029036,130031561,142537761,166094987,179688091,202614426,202296346,239593706,247831362,277036671,285426991,314947181,327694601,353329112,361385576,392186356,405378816,431522821,443778237,471342431,485700271,511649126,512611366,552446862,562177986,593739611,602916611,635531361,648818737,677826116,679475876,720627896,730392056,763940737,765946321,807768691,809955231,852115826,774485362,896986226,864063366,942383991,909726271,988313237,978144981,34778089,2656169,81782709,74377005,129331254,119976054,177427904,179459204,226076855,195044759,275282319,269200839,325048524,319025980,375379714,381865134,426280149,418597669,477754105,484809869,529805874,533811034,582439764,592291980,635660099,606841539,689471219,685680459,743877480,740362904,798883254,808023954,854492929,851353105,910710909,921569769,967541614,977254374,24989473,41131857,83058952,78853192,141754512,152703348,201080637,212005317,261041827,279535107,321642598,329796742,382887482,400874762,444781027,459128723,507327797,524775697,570532372,545882772,634399348,635193452,698933337,700059657,764138967,778156723,830020882,831794402,896583742,912522362,963832223,978831287,31771010,53386370,100404825,103258841,169738385,187026925,239776430,257264870,310523716,},
			{0,0,0,0,0,0,0,6,6,56,96,252,236,792,1032,2002,2538,4368,5512,8568,8208,15504,16704,26334,29598,42504,48840,65780,73500,98280,111104,142506,159306,201376,223776,278256,272656,376992,380952,501942,517662,658008,690672,850668,898188,1086008,1156704,1370754,1465538,1712304,1835304,2118760,2236320,2598960,2753584,3162510,3356766,3819816,4061736,4582116,4839660,5461512,5776512,6471002,6814842,7624512,8004512,8936928,8864448,10424128,10403928,12103014,12143758,13991544,14138064,16108764,16333020,18474840,18827664,21111090,21598530,24040016,24676296,27285336,28009520,30872016,31770312,34826302,35909622,39175752,40463416,43949268,45413388,49177128,50873688,54891018,56798418,61124064,63259584,67910864,69760464,75287520,77401544,83291670,85686990,91962520,94697136,101340876,104362540,111469176,114872976,122391522,126162162,134153712,138317256,146803272,150849888,160389488,164876208,174963438,179881022,190578024,195989064,207288004,212688684,225150024,231065824,244222650,250187130,264566400,270610560,286243776,285449280,309319296,308669496,333859526,333388302,359933112,360172016,387610812,388097052,416965528,418270608,448072338,450196642,481008528,484026984,515853624,519239664,552689424,557054328,591600030,596981670,632671880,639154680,675993780,683511980,721656936,730365576,769754986,779628546,820384032,831474016,873642672,884971152,929632032,942278688,988455798,2470735,50220241,65730841,115034277,131984917,183009457,201570321,254260027,274446403,328902953,350779393,407057953,430080377,488847529,513680689,574396999,601066639,663834529,692446129,757291165,787339285,854900865,886987825,956800531,990463995,63130034,98441234,174032274,203617010,289653170,321032154,410141720,443388080,535650026,571372706,666333326,704043950,802350026,842731442,943861732,986922692,91033275,136895219,244032771,291717531,403031611,453689571,568204521,621886321,739729587,796598787,917788287,977331927,102565516,165446860,294249646,360029926,493032516,561811492,699109492,763504116,912679492,980270092,133945011,204798731,363112181,437865165,600390761,678121097,845994197,927838157,100139640,185675304,363048006,452422686,634943966,720789366,916056006,5934399,206616445,300149005,506861495,604664215,817031251,911778875,137369744,236136104,468124994,564085250,809549000,902830664,161897793,155325633,525431489,516441849,900414279,889140623,287114482,280642858,685804598,677319958,96761291,93852675,520265477,522575957,956602307,964772507,406061220,412755420,868935996,881820036,345524739,364711339,836129965,862306269,341058578,373313738,860621958,900176774,395135937,441582841,944920887,998457207,510301696,563115616,91607849,151844153,689173439,756995855,303337178,379486338,934442478,18451575,582837427,675557827,248874870,350373710,932912420,43410613,635312469,753397869,356442262,483892942,96673901,233604485,856384391,3093744,635955644,791502420,435774553,601417497,256232996,431141756,97727875,282105315,960661155,140313212,845439868,34968917,752476187,952087547,682187430,893119854,634996099,856396819,611329912,844551272,611621835,856771235,636310114,893681914,685838307,954028187,760655316,41520173,861215419,154941572,987978302,294911031,141409084,461069668,321978377,655279697,530162276,876739476,766442419,126543436,31306005,396701781,325245854,704657134,648760397,42400534,2353729,411079369,386535662,809469662,801821715,240360508,248733167,702567527,727797110,197233063,239546432,716803576,784519905,277881058,363262161,872469977,976323780,502189997,624261266,159085539,307637128,859152088,27019884,588065268,782984107,353849636,576110429,52833622,406985615,894117079,276202567,774038527,184360363,700649283,132064289,659629089,119925871,667085911,148562907,715062267,218599499,805284323,330666085,929381725,485399471,104974240,683442863,324127520,925445899,588170132,212064674,895530874,543961800,250096337,921806396,649882845,346274141,96670614,818047327,576181304,337814842,118959395,906272258,710739691,524121807,353329200,192072462,44781655,910839948,789041781,681146753,584600050,503722202,432867435,379302461,325967310,308630576,281696673,292456505,291767178,331537150,358042983,426636389,472672886,578525108,651997677,787981233,881799426,55789755,170351252,382742790,413694943,769639569,822133522,217286491,291692684,726497176,830704649,298092448,424538361,932900423,90473793,631756485,819849598,395503367,615072056,224991155,461537092,121077327,389958160,84626785,385745858,116511887,451336344,217612479,578110288,388815927,783411920,631017149,52679559,945118647,394337033,332030532,704478525,792670584,193558770,327964235,757261788,938844657,404467643,626252738,114277588,391137163,916438076,234454418,790065787,157168829,744072206,160252594,674185667,244685815,790396528,411456530,982491547,661560745,265371387,996002466,528838652,415793724,975469261,921954635,411677821,515513383,935801376,197506301,186613501,968977882,889696402,830980790,683839870,784575934,667413878,830832472,646879672,970827850,824883586,205647827,91305251,536386535,461146175,964146469,824272349,490038540,390875924,115182121,56794897,840705065,831109465,667743716,693076380,597442983,672711783,630956344,749259184,769445885,931863485,14082325,115958005,366045076,513252356,826522233,19620450,396710627,643818827,77815871,371491087,871052378,220450435,777643372,182313245,798820962,259916275,935826146,438512883,189908843,750270659,562327953,180430466,54351340,731895324,667255920,395879233,402327637,191148910,260861544,102555737,244161807,139419880,353541744,191324617,590323857,482997754,955839864,904069057,451430724,463867493,78446690,147720587,838247327,973655160,732201523,933790996,761687563,30720853,928093133,255734455,232815352,629335265,677260832,143255122,},
			{0,0,0,0,0,0,0,0,7,7,84,154,462,476,1716,2212,5005,6685,12376,16156,27132,28960,54264,58142,100947,114849,177100,201222,296010,343420,475020,545062,736281,846363,1107568,1250824,1623160,1725136,2324784,2401210,3262623,3423889,4496388,4649554,6096454,6529068,8145060,8646666,10737573,11524115,13983816,14842100,18009460,19455744,22957480,24612252,28989675,31212223,36288252,38710574,45057474,48466460,55525372,59286424,67945521,72579885,82598880,87343312,99795696,104119008,119877472,122963554,143218999,147131425,170230452,172254810,201359550,207398332,237093780,241748178,277962685,285077891,324540216,328763708,377447148,391178256,437353560,450885484,504981379,523098135,581106988,596661878,666563898,692331948,762245484,787009272,869107785,899795925,988172368,15014609,120529249,165753897,267339913,313736585,429840328,485548968,609344093,662126571,807245615,878669317,25023350,96119382,264243143,346553895,526561562,603561254,813729366,921306626,127594995,237047633,470108166,595613644,843323463,963138249,249404054,399874640,690625472,837834386,169379390,333025208,688177565,835036669,249655734,410629142,856577686,983125576,511839286,649998160,218472668,297597042,979650422,133400157,798689845,899227107,679057319,806696285,624372683,656480319,638413704,845826588,725120605,889175937,888600657,104744942,133132828,243136854,463172531,732107029,883356366,67573091,398506989,635937673,13638038,96079502,733959095,164092904,564880705,968679065,512019522,19198739,581203449,970173751,778476877,396714252,110105974,643212766,582584073,196894602,202637047,641088179,977228853,807585674,913567028,691729587,19108347,921217737,301564503,32101954,768907816,811723787,429377062,343245801,291483359,320440106,364016082,139585947,656048870,71797672,176945683,572965812,936366958,535482728,944275752,364355568,210944040,46439800,746959035,461448469,563229530,437789364,670992395,270665553,81819054,365561172,807622096,25754873,860661866,309252571,253553211,435001655,999272248,725538917,111163147,640442207,602945104,344047397,488719224,820197842,782975601,180490365,500600371,858379720,656882892,363079554,267522932,657717117,348637977,464329011,916770554,810165380,988895639,160923618,582428143,250462415,715230448,559102377,405619986,82409859,672376931,734915188,534751899,48573946,12473761,474826152,125757499,128303348,895312112,202816706,342348593,696925653,488588019,62996873,356269614,90866845,968158960,12099008,347556202,804061771,518304401,664687804,504797853,218333455,331990549,425718671,25404664,651263941,611139124,971036933,115878202,198272098,566900255,450771904,992086427,130677280,419929486,626590253,879542725,756077548,400668857,924418016,13689072,599934031,749632088,427320511,640183266,189534800,717693852,861302275,15190206,962360391,566383165,658030891,405677386,925203727,568180851,961909969,89714352,900449733,6821103,170600300,356776367,484010180,177597168,859763654,508052088,553137070,387671079,789085234,856755395,429845603,956387532,680523629,728441281,35482602,215591817,371956318,461325866,712139014,509951908,773754603,406610486,629540575,197284551,685813518,928809885,431538580,648885553,628637249,406084489,769233719,249864076,479011924,230576825,289775025,399481111,720760873,808751973,714348518,511491971,230355403,561742135,865865642,14492929,910639317,925695342,185936866,352271952,563492898,352128180,982737358,984163489,173375008,308282696,927997548,385407390,158036059,277487323,741335475,47511942,446330488,759521957,193307694,478620945,612063519,270987088,152851059,203884905,209908663,345677083,460832967,765836365,697896347,534957495,39710745,724769255,570997243,408146517,282444514,659122415,623508699,552900525,394085688,165867158,664331486,575603696,491611760,860898971,973016918,101761743,490610231,379433249,660441076,776399753,838639623,376405223,655725134,264464065,249589351,526873886,722291952,251228342,645018923,526430071,807741648,442703641,626740067,91608611,315054816,566052635,254002314,960304609,242732378,370007917,353193077,892193748,227215539,625294407,937926816,669156788,51306364,125055827,608091979,95708082,173437238,685285328,710186261,999428237,367020298,145260129,363635880,231400801,21713854,367980357,199513603,666653172,299888419,240611878,345575980,204601449,520634643,674933308,750936688,769499519,436428706,607787057,168979332,310892121,467302293,1534518,705112754,804072118,891752574,844515338,581663162,250541753,65556750,151510729,678485923,678478113,474028610,964211001,754327972,143202577,339640715,351687030,700913830,727654472,244328838,410865983,36049346,542868713,486391305,267011006,422123115,728457644,933072222,74205092,175625665,453096891,513399979,15839010,578515566,915015374,78032165,305103337,707033635,342489352,893249240,185484579,180126494,994340648,873705878,931265416,475442341,160438858,314977872,848028987,375290563,162207789,835546523,273167341,252015453,353135867,525944233,576393942,112183019,119290718,658264022,160260249,197535336,879837838,786777201,460676462,17663859,87563303,123266640,947436287,303319908,229400680,652761515,124745839,124135330,826961915,232327903,531756669,711292056,437072387,237732398,743102796,572775970,652310072,113500001,369441926,408849087,101548733,156875696,58000732,777028868,450505290,456370400,493124223,895980583,402291202,583609809,396829202,106347963,697968022,687709805,529361862,709810392,117106985,42580352,689759429,963419108,478352755,650210805,716415929,96639722,639991191,448424154,487652044,957976981,500521279,742419973,922289071,46716850,999231132,298290231,980226949,263108689,116778058,50575014,663026424,837025656,875772808,95582329,14495282,47873840,341367787,548566093,121278692,450791054,621849515,336724322,113453617,341022161,869235051,784259329,165127363,440191921,279872590,},
			{0,0,0,0,0,0,0,0,0,8,8,120,232,792,904,3432,4376,11440,15920,31824,42688,77520,90800,170544,186160,346104,400312,657800,739560,1184040,1404344,2035800,2372856,3365856,3957664,5379616,6156512,8347680,9366432,12620256,13530640,18643560,20116584,26978328,27988456,38320568,41476840,53524680,57056872,73629072,79492560,99884400,105145376,133784560,145605712,177100560,191572016,231917400,252817496,300674088,322708296,386206920,420279896,491796152,530439064,621216192,671483584,778789440,828644224,969443904,36979897,198774713,262439593,473109697,551527169,799579057,852014657,186189386,286770042,641902106,727092826,176716379,292388187,801756795,849154411,529365748,707971028,373200845,560091037,348337294,617621134,471375511,675255447,760554032,130828153,235867858,585058594,919192403,369183756,834413061,128005374,7560688,662934512,466952994,193900459,243341973,203717390,370067632,260967033,883217979,154898781,821795503,124066465,227890260,780032397,146859665,495346042,627515118,630623680,722315620,856825718,487568507,53284198,983637402,386843109,275157499,409552630,431258412,563091255,525794542,109498834,637583265,664092044,850650957,76112170,254487019,389797631,944306065,740529070,21318282,772041797,593008307,907101504,773422462,356640667,683464755,929003536,451201586,80838989,212175363,360709433,109727142,839571891,295328419,684212978,928922202,396973111,179273477,359273501,224329266,440668856,251588258,792249905,458480309,712598510,52755820,830071725,252885198,239081480,288468444,507499578,400655094,735032224,842574564,204127318,879777055,420783935,790685167,305720874,867056308,510357207,414456047,516365877,752742567,318097405,216562262,235325145,155856739,795800286,936381206,132534285,940234465,656879355,566400652,256962490,231302775,567759189,369368234,976930862,433606430,13704349,896198619,928991037,249100083,327942870,4654837,4258305,696222881,890553515,878820209,42457756,129771710,718305218,49377047,661363939,261589621,968325258,414708825,447819875,182085660,10784819,262841862,235741210,382602640,714677742,294243195,846177898,778649127,767372542,645490830,322385229,734012080,291089443,913832846,325759518,85766522,538463036,182651715,318825733,170198619,167327817,47850243,940908175,849658523,788111252,645175446,975001142,540359412,398263629,678496822,223069255,241139118,732921564,449055396,706981717,563200650,466679988,885699873,667818164,760848059,342611684,576126297,163354699,763234036,325604644,799137670,654522556,207135585,668681335,557939789,26376793,470774197,36355484,614489818,381578073,708696846,891700352,524913867,321334039,887734282,885848562,676010046,800825560,824052928,593793337,322853315,65193987,221316800,238384045,627518577,755341418,709975841,522996596,698938314,776559765,887696990,183341085,633911248,796280507,360954482,156509992,559278334,377614558,787795679,410470269,675282504,772365863,921798795,861082848,300128532,877299756,657239004,287663582,915759439,532296085,75479217,324033683,214865731,815712579,492601961,492680060,149144015,533720810,508298695,818828487,978821181,599039158,56033038,614648775,323460667,979477535,454494209,231176648,214067212,750600709,460357063,794542843,146506327,967789603,322365191,974312398,136255036,557118203,836753377,670573310,774500180,715168105,404025817,737665326,285600668,436929166,87106554,748363146,585930143,749268454,670878401,429436677,344116293,379968104,723126799,655626711,42693366,687969363,656905029,159044234,41184140,563788829,794337078,14389673,640627795,934426986,431874592,656541308,149570043,636142968,907024321,275317938,951531970,422775705,666562356,556834285,573973813,407154078,336251668,547708513,758770287,219169868,792079214,795824435,534213608,772006530,233029036,449913833,288560785,355802448,255407807,99767421,845141453,812080028,928739068,439382786,539042652,532008291,873242652,994387792,295387006,572794384,338915665,141153692,709220574,673807158,286231359,486667889,127026820,467724193,468472302,821475161,729883135,313580011,515714257,1351759,618276141,478490723,20477126,689172518,898592364,406656410,625059364,828307753,771300471,125356272,110572177,36746564,620841595,264478137,487690042,896468940,107244041,866347277,89133723,299793072,259478820,951000142,663902384,61043938,570572332,462232882,473270980,755487530,94492645,624584535,388569499,760559205,544825740,210236356,990760285,251509061,395258029,117459602,671829896,164063216,981881677,59848156,738011923,621500686,607338944,298372396,514857035,852708092,646822099,45670464,454166760,903401291,655945129,756704513,242807295,78049101,480503753,399981683,913419792,949256130,368140062,581665499,957043434,939047403,81928176,919445522,437667783,631430131,15897323,600244157,108730693,204498538,312508696,673596367,531578207,495839069,982102493,775033102,195902815,560006347,24331503,555508852,642176513,31490477,551597699,621327998,586094921,611613167,914508040,88987410,45048991,659904597,829366097,13225463,466640591,249272277,507715726,19981217,859258349,605134239,787953228,492507615,924730218,356609634,269024352,353567533,193069069,986515521,446222602,906276993,159327745,910702795,849105106,195322183,422579897,355403720,181542543,883126908,827043070,340230935,463919491,895897514,605360360,687747090,177501482,399978203,524057068,663036037,410985316,305364262,31188679,825079375,9248850,143435802,406196627,916399381,724316793,259365174,911988139,539193678,368558737,731896758,949256633,544927423,970135997,96746556,213058998,632519552,930713427,942732620,851666168,274808824,185452799,681905491,627703297,85122968,365304003,818229551,81596080,591258704,961610459,910932317,697339443,491309050,493045188,348862032,70605028,11746487,674893912,51842897,79203969,935726426,590701476,138136923,55921147,896113094,866298116,253720556,963737490,718071237,},
			{0,0,0,0,0,0,0,0,0,0,9,9,165,333,1287,1623,6435,8163,24310,34902,75582,103854,203490,256482,490314,555426,1081575,1276903,2220075,2493747,4292145,5138721,7888725,9337965,13884156,16616124,23535820,27330732,38608020,44767380,61523748,68551308,95548245,106232373,145008513,153691993,215553195,235535643,314457495,339157359,450978066,490790514,636763050,671846490,886322710,963148374,217566343,323794807,652411468,814312108,217471385,401930705,944827751,226507504,872894676,218562637,47381525,508739477,522361518,4882279,361453616,45363753,639125570,413146307,442125958,423319583,871053613,754335134,42072828,320476261,88783253,402291622,164253326,802990887,443226693,497447950,124511034,168646892,433559164,826814094,625252373,7259609,986896407,994077882,841440566,447792739,550930798,369493947,520207812,590145370,200861559,428633275,95453686,733540421,762019761,10602859,818863379,603750648,949654550,43178172,908844886,893412136,527412528,550941572,718949905,89647106,486107673,479979912,927408543,794881990,244444774,165622905,749473622,430444713,873424956,115086224,174335893,22368075,346227245,858182246,228436932,515315958,815425897,129883454,267072063,926741028,919468422,543543547,296241252,908851912,120405148,491109556,326771340,274848443,74926412,307429013,762798603,488870252,40829085,859703481,826766114,130738134,321099793,301630996,23155974,132102601,747867543,746934127,643241966,638362638,208544132,48363697,313213659,808714000,216536232,787445234,588088784,967905128,528978475,620410432,593895841,305951955,814002580,631711537,720674824,384456630,370122946,399584767,368909200,214268944,900384762,958099250,752068003,77588964,343986102,252595179,758002279,426824726,768151254,182184795,872005843,988147067,323097697,40866847,164415635,88888743,263005103,361344493,345692701,9587945,35959871,745215483,891990151,691533614,445914565,30315525,244280199,610280706,889766926,183271934,84177800,204555961,672728832,257014868,689663872,378561345,405221018,839294232,373976802,233047233,484594911,733842305,11006387,326895304,665048520,625657006,650589806,457157571,719168829,298925984,227174881,406953167,194598663,342900992,365381456,628702989,269391568,967279536,286057021,915437100,709683712,240807821,816488566,271601804,933377462,41022482,508497899,645867603,183596730,820731327,868213443,195571449,815739780,154408739,701376804,353206825,702020598,327368109,578108225,29011218,757455743,40268770,421120311,182267975,591318762,16779427,221435135,966723530,288150103,636039451,885725280,98344610,322475814,117191844,219464894,187495096,611453915,212119735,50142518,479507585,709732873,504461744,494852703,299181926,150872125,453129668,376649269,251155955,939740151,769145152,794108441,850844147,200371037,46431230,848615577,782755883,983826284,519912598,533954921,763343547,240673672,120248510,792847175,648115452,962761140,974873845,745145290,69202275,499028514,918569170,92464468,711360160,50166102,744376088,704087805,742506060,346994151,441202089,389054534,734608484,517503061,225933256,859403566,970580489,147559668,824669302,889610206,566974372,540350416,106145236,677319842,176787819,179697767,3253644,410547640,534430488,402451881,956694418,46578982,71640926,285224875,54187032,307870871,405380623,750800793,387634356,900320035,169881644,899619696,393117234,959329061,992852251,571800034,954472671,729167384,10893921,145228837,433281501,481189471,481555740,575314882,208909159,676538132,479762116,682065487,135031945,379026377,955997553,690213203,561092433,923956844,893012940,28184064,701768594,848703212,986011224,391764752,67066871,90943773,198337233,78391395,190604147,460502656,520064072,598048497,208224537,390819809,439132169,566831713,876358078,976136514,658389024,889294061,538138985,300548466,167312722,235760384,807249674,65144312,653621706,820860698,850585972,519512746,637608363,489598290,904749614,703967079,198912181,117334337,902625764,8901592,254699991,330135968,560167841,57759537,45890724,552000500,969686106,920158152,646332092,385534024,940558837,661781697,574884814,332728032,370878549,237719005,652544417,862543326,892901862,735987473,143076091,832076000,510909313,978051101,430046173,268145850,990652342,483205702,824191492,516213019,285328772,803769858,528617623,763594262,517305632,238085747,691781026,944015829,71313524,928399670,860325962,30605353,774521872,350757314,51249273,724490820,397575202,204114750,457888152,546240017,996826060,705931246,495833230,337439747,660102969,301575875,150941190,179779209,377928878,794945294,384608023,739067813,868800430,907755487,376946177,41683040,859340445,275036074,203611114,691009668,616747385,884421079,753815050,531496927,968747015,966895642,351850665,768026109,892556522,346723864,377088154,548346240,98335254,258348258,845006491,16401328,479553610,638116955,285225460,844438006,340556639,898760371,102934844,251847974,409498864,194604507,799719081,518765362,237879841,185573639,460601723,2504280,471492293,308100600,950135258,664987840,303846977,561128567,413802925,119385002,6544203,815453620,432250560,204237580,363783541,654722981,722364436,93424832,568173065,756469356,287750228,950379007,30544923,821627297,484930668,135030415,243019859,61043114,302798175,972026458,827473140,247555361,760250908,88834148,338620008,342288379,454417664,332401776,61308993,703867118,611105847,273120253,593849365,889326758,811736251,304890803,609187560,55556360,35243538,350170769,689230969,970181258,284160723,178935081,270763039,640854313,859902990,350556380,962145819,571992036,377535383,787672321,549612005,658056640,954276601,991174163,93565180,722551037,384774094,905516255,672015639,711959101,792162440,443611556,266488176,553929124,601953086,680643898,180917629,689063928,526455169,726193174,497213292,285746604,373127716,284135281,428605886,147496455,460598398,909844065,},
			{0,0,0,0,0,0,0,0,0,0,0,10,10,220,460,2002,2770,11440,14560,48620,71452,167960,236120,497420,663020,1307504,1552400,3124550,3778470,6906900,7846004,14307150,17168670,28048800,33676080,52451256,63601800,94143280,111167680,163011640,192816456,273438880,314875840,445891810,509412610,708930508,775296380,101716323,218598243,677106633,839456201,505433686,753462166,679075379,932255939,317936225,767390225,575968351,265771304,648873880,715211233,783142562,173030308,286591130,322329356,540584323,262229230,14131181,686006360,280064777,558173741,33528105,827885510,113004525,440213836,524146744,975615817,466674906,960386356,364631176,891269149,900295583,733947391,52085849,222113316,136782440,852936145,856438767,97979079,350356243,172580216,252523688,731515989,754940984,914807977,677268077,700143418,543261808,311410254,664485029,686307439,231795086,865325577,415141770,960734841,472228291,733492161,866613069,953267421,395854523,530358046,330326309,729100950,563355862,407994021,773365405,192345380,598721079,848610549,826023040,522532817,592596968,31331120,603975698,95948837,367188201,704166497,440702488,834660387,701898566,530870785,632977888,924415999,626246410,251154933,309739579,914160242,894189397,820158256,542365836,987613647,761857850,26449916,822392223,798195842,198822585,833519589,40955029,489883752,671411620,409393071,112768471,237073208,645240915,670375366,396224444,677098458,963037328,228741240,70247769,603430932,263006767,517898087,637845827,684483829,612437982,525809721,735859723,105466844,540931468,885265998,440254699,561730701,667604758,177234772,266379835,808989377,126848672,651948647,73981905,535734993,7695114,995228988,97375066,89544420,32604703,935903028,332046992,184913212,712492754,252986957,520123227,200562222,226084156,519243802,988514781,369557710,283222205,585771538,605239373,720523054,243552685,89297167,131334361,342720783,774063408,800357709,257968962,525991897,341279926,998086986,630815324,243124456,846501271,862069588,176451954,883332494,725304369,500696890,58549141,944066686,845653303,117131190,604824215,419855398,552318608,513409018,559255018,346889701,218943542,62116545,27802474,914418544,682987727,681117998,499917793,210776379,952934407,639752258,342396727,288712868,591565525,214284098,176692400,597209271,193788794,572430540,565609146,303518648,392443194,997188254,450373251,735319231,840713686,290402290,794411891,53281319,635252430,76040884,905769007,265690637,659832427,338419483,925946752,924750940,345350233,5859756,989082840,392344517,358247454,854030884,571758218,487211788,745935649,407052762,570375619,628270931,84586596,335454952,659957772,645699819,191689229,56131170,505384589,957304174,983075918,865919925,413521134,101060895,71684622,806262818,32382920,83978825,723149160,366361604,721441993,670864690,801397564,267068092,235396151,333054463,355789013,567678144,382205022,691340433,519931616,775360712,334939685,292591364,411197918,903732883,295998322,218867877,739091529,82121785,231507693,285065914,850016778,602203553,413260108,559165672,955664269,343165050,525327372,971835505,312148013,690788020,112547237,481713621,137215024,179634973,168394034,660365922,73295359,356525444,680323427,752213281,24870119,14282293,971521763,99116409,219606315,930517991,699093021,294381798,363941223,830841225,390081106,101986991,785294674,83258080,418352114,78364055,474845621,846594379,347250239,984135234,966829519,267538727,585092161,390968428,12595119,449205695,322978010,476105529,30203948,640523774,747072562,859845617,333161883,25043409,540447769,237805308,164941305,9986634,712777679,851608507,589282862,133879713,819638445,608756464,309858879,917015782,656890378,916968426,516735697,118046865,539605028,51148217,881189240,63591004,299248646,893936295,844807725,102216865,157343865,427153751,373456826,180011988,658604004,209512884,371586325,446715536,871569165,798087457,977523093,316796847,90070231,435088377,985823247,777622840,294405857,80771843,668446434,824467552,656938688,740172875,292467379,737173010,402900541,175760571,658254332,194964005,363541578,900469998,8520509,333758241,585366162,206055356,685393443,965454505,386067911,292348424,939647727,977516017,274908320,852331002,323509978,363236710,182677334,54910419,125969642,667020239,474030531,153421168,337316664,814482220,242915826,362618543,657676497,860689782,419983178,327911828,92624385,357439168,249313273,914164798,707534628,296165936,720506099,918769919,141156230,345913818,571137045,104251801,508004616,100872762,503817422,687930008,348518805,145617561,291587287,927754295,315556146,109532367,475121984,725542843,315680769,111262551,385249164,82431110,853848708,46625841,254550790,600017794,360501210,713098462,212364304,356819581,310748732,127130229,988299137,500672092,976260917,48665623,180448730,881455024,681673186,383851680,975804603,93409498,468777786,875907878,241967011,411323013,103486446,586789530,941097768,958632174,392533564,588961787,849172809,435627142,809132378,94494188,595967481,350311369,459302563,87101697,73843964,434199376,453355626,81841783,296309631,980039350,780054580,194907932,820476183,417205603,814256778,683948388,880972881,142047777,622403136,439366590,416552617,599655110,264033550,147493396,204577235,431860245,321587074,919026744,352778305,671052625,925086248,920935293,432161517,529465562,572908001,395091124,569656914,719519181,84708734,50297935,854113393,916540555,57698365,458343361,444494299,251787065,232847698,986565866,804651470,658171471,213264999,582708224,524837628,497765545,12891383,43768377,226162028,65138611,949840502,871432704,80500969,944668869,435145991,697551035,514944055,968083925,244380471,744443005,706688214,54752965,896571883,923780827,511384936,259782442,802067697,214020199,505301634,594484849,878484006,772273337,859275738,444745082,371625146,41514790,800319947,449274280,},
	};
	
	public static long getDynamicWays(int inM, int inN, int K) {
		if (sgf == null) {
			initSGF();
		}
		
		if (K >= 4) {
			return prec[inM][inN];
		}
		
		int M = inM;
		int N = inN;
		
		int len3 = 1 << (32 - Integer.numberOfLeadingZeros(N - 1) );
		int[][][] F = new int[M+1][N+1][len3];
		
		for (int n = 1; n <= N; n++) {
			F[1][n][sgf[K][n]] = 1;
		}
		
		for (int m = 2; m <= M; m++) {
			for (int n = 1; n <= N; n++) {
				for (int i = 1; i < n; i++) {
					int sgfi = sgf[K][n-i];
					for (int x = 0; x < len3; x++) {
						F[m][n][x] = (F[m][n][x] + F[m-1][i][sgfi ^ x]) % mod;
					}
				}
			}
		}
		
		long ret = 0;
		for (int i = 1; i < F[0][0].length; i++) {
			ret = (ret + F[inM][inN][i]) % mod;
		}
		return ret;
	}
	
    static int K;
    static HashMap<Integer, BitSet>[] cache;
    static int[] mexCache;
    final static long MOD = 1000000007;

    static int mex(int n) {
        if (mexCache[n] == -1) {
            mexCache[n] = 0;
            BitSet set = new BitSet();
            for (int k = 2; k <= K; ++k) {
                set.or(d(n, k));
            }
            while (set.get(mexCache[n])) {
                mexCache[n]++;
            }
        }
        return mexCache[n];
    }

    static BitSet d(int n, int k) {
        if (cache[n] == null) {
            cache[n] = new HashMap<>();
        }
        BitSet ret = cache[n].get(k);
        if (ret == null) {
            ret = new BitSet();
            if (k == 1) {
                ret.set(mex(n));
            } else {
                for (int n1 = 1; n1 < n; ++n1) {
                    BitSet s1 = d(n1, k - 1);
                    int v2 = mex(n - n1);
                    for (int v1 = s1.nextSetBit(0); v1 >= 0; v1 = s1.nextSetBit(v1 + 1)) {
                        ret.set(v1 ^ v2);
                    }
                }
            }
            cache[n].put(k, ret);
        }
        return ret;
    }

    public static void solve(int in_m, int in_n, int in_k) throws IOException {
        int n = in_n;
        int m = in_m;
        K = in_k;
        
        int[] values = new int[n + 1];
        if (K <= 3) {
            cache = new HashMap[n + 1];
            mexCache = new int[n + 1];
            Arrays.fill(mexCache, -1);
            for (int i = 0; i <= n; ++i) {
                values[i] = mex(i);
            }
        } else {
            for (int i = 2; i <= n; ++i) {
                values[i] = i - 1;
            }
        }
        int maxValue = 1;
        for (int i = 0; i <= n; ++i) {
            while (maxValue <= values[i]) {
                maxValue *= 2;
            }
        }
//        System.err.println(Arrays.toString(values));
        long[][] d = new long[n + 1][maxValue];
        d[0][0] = 1;
        for (int it = 0; it < m; ++it) {
            long[][] d1 = new long[n + 1][maxValue];
            for (int i = 0; i <= n; ++i) {
                for (int v = 0; v < maxValue; ++v) {
                    if (d[i][v] == 0) {
                        continue;
                    }
                    for (int j = 1; i + j <= n; ++j) {
                        d1[i + j][v ^ values[j]] += d[i][v];
                    }
                }
            }
            d = d1;
            for (int i = 0; i <= n; ++i) {
                for (int v = 0; v < maxValue; ++v) {
                    d[i][v] %= MOD;
                }
            }
        }
        long ans = 0;
        for (int v = 1; v < maxValue; ++v) {
            ans += d[n][v];
        }
        System.out.println(ans % MOD);
    }

	public static boolean next_number(int list[], int base) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}

	public static void verifySGF(int m, int n, int k, Random rand) {
		int[] dist = new int[m];
		int remaining = n;
		for (int i = 0; i < dist.length; i++) {
			dist[i]++;
			remaining--;
		}
		while (remaining > 0) {
			int cut = rand.nextInt(remaining) + 1;
			dist[rand.nextInt(dist.length)] += cut;
			remaining -= cut;
		}
		
		int[] sg = new int[n+1];
		sg[1] = 0;
		for (int i = 2; i < sg.length; i++) {
			TreeSet<Integer> followers = new TreeSet<>();
			
			for (int stack = 2; stack <= k; stack++) {
				int[] a = new int[stack];
				for (int j = 0; j < a.length; j++) {
					a[j] = 1;
				}
				do {
					int sum = 0;
					for (int j = 0; j < a.length; j++) {
						sum += a[j];
					}
					if (sum == i) {
						int sgSum = 0;
						for (int j = 0; j < a.length; j++) {
							sgSum ^= sg[a[j]];
						}
						followers.add(sgSum);
					}
				} while (next_combination_with_rep(a, i));
			}
			
			if (followers.isEmpty()) {
				sg[i] = 0;
			} else {
				int max = followers.last();
				boolean done = false;
				for (int j = 0; j < max; j++) {
					if (!followers.contains(j)) {
						sg[i] = j;
						done = true;
						break;
					}
				}
				if (!done) {
					sg[i] = max+1;
				}
			}
		}
		for (int i = 0; i < sg.length; i++) {
			if (sg[i] != sgf[k][i]) {
				System.out.println("fail");
			}
		}
	}
	
	public static boolean next_combination_with_rep(int[] list, int base) {
		int i = list.length - 1;
		list[i]++;
		int carryEndIdx = -1;
		int carryEnd = -1;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;

				carryEndIdx = i;
				carryEnd = list[carryEndIdx];
				
				if (list[i] == base) {
					carry = true;
				}
			}
			
			for (int j = carryEndIdx; j < list.length; j++) {
				list[j] = carryEnd;
			}
		}
		
		return true;
	}

	public static void nonDivisibleSubset(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();		
		int[] a = scan.nextIntArray(n);
		int[] count = new int[k];
		for (int i = 0; i < a.length; i++) {
			count[a[i] % k]++;
		}
		int max = 0;
		for (int i = 1; i <= count.length/2; i++) {
			int j = count.length - i;
			if (i != j) {
				max += Math.max(count[i], count[j]);
			} else {
				if (count[i] > 0) {
					max++;
				} 
			}
		}
		if (count[0] > 0) {
			max++;
		}
		System.out.println(max);
	}

	public static void divisibleSumPairs(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();		
		int[] a = scan.nextIntArray(n);
		int num = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i+1; j < a.length; j++) {
				if ((a[i] + a[j]) % k == 0) {
					num++;
				}
			}
		}
		System.out.println(num);
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
					String s = br.readLine();
					if (s != null) {
						st = new StringTokenizer(s);
					} else {
						return null;
					}
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
