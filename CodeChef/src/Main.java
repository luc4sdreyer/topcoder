import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.*;

public class Main {
	public static InputReader in;
	public static PrintWriter out;
	public static final boolean debug = false;

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, false);

//		SBTR();
		testCLONEME();
//		CLONEME();
//		testPRMQ();
//		PRMQ2();
//		NEO01();
//		UNIONSET2();
//		SUMQ();
//		XENRANK();
//		GOODSET();

		out.close();
	}

	public static void SBTR() {
		int n = in.nextInt(); // vertices
		int m = in.nextInt(); // vertices
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		int[] cost = in.nextIntArray(n);
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() -1;
			int b = in.nextInt() -1;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		boolean[] removed = SBTR(n, m, g, cost);
		int numRemoved = 0;
		for (int i = 0; i < removed.length; i++) {
			if (removed[i]) {
				numRemoved++;
			}
		}
		System.out.println(numRemoved);
		for (int i = 0; i < removed.length; i++) {
			if (removed[i]) {
				System.out.print((i+1) + " ");
			}
		}
		System.out.println();
	}
	
	public static boolean[] SBTR(int n, int m, ArrayList<ArrayList<Integer>> g, int[] cost) {
		boolean[] removed = new boolean[n];
		
		if (m == (2*(n-1))) {
			HashSet<Integer> rimEdges = new HashSet<>();
			HashSet<Integer> centerEdges = new HashSet<>();

			for (int j = 0; j < g.size(); j++) {
				ArrayList<Integer> adj = g.get(j);
				if (adj.size() == 3) {
					rimEdges.add(j);
				}
				if (adj.size() == n-1) {
					centerEdges.add(j);
				}
			}
			if ((n == 4 && rimEdges.size() == 4 && centerEdges.size() == 4) || (n > 4 && rimEdges.size() == n-1 && centerEdges.size() == 1)) {
				// wheel graph
				if (n == 4) {
					rimEdges.remove(0);
					centerEdges.clear();
					centerEdges.add(0);
				}
				int centre = 0;
				for (Integer e: centerEdges) {
					centre = e;
				}
				// either remove all rim edges or the centre and the cheapest rim
				long rimCost = 0;
				int minRim = Integer.MAX_VALUE;
				int minRimIdx = 0;
				for (Integer e: rimEdges) {
					rimCost += cost[e];
					if (cost[e] < minRim) {
						minRim = cost[e];
						minRimIdx = e;
					}
				}
				
				long centreCost = minRim + cost[centre];
				if (centreCost < rimCost) {
					removed[centre] = true;
					removed[minRimIdx] = true;
				} else {
					for (Integer e: rimEdges) {
						removed[e] = true;
					}
				}
				return removed;
			}
		}
		
		if (m == n*(n - 1)/2) {
			HashSet<Integer> rimEdges = new HashSet<>();

			for (int j = 0; j < g.size(); j++) {
				ArrayList<Integer> adj = g.get(j);
				if (adj.size() == n-1) {
					rimEdges.add(j);
				}
			}
			
			if (rimEdges.size() == n) {
				// complete graph. Remove all but the most expensive two.
				ArrayList<int[]> nodes = new ArrayList<>();
				for (Integer e: rimEdges) {
					nodes.add(new int[]{e, cost[e]});
				}
				Collections.sort(nodes, new Comparator<int[]>()  {
					@Override
					public int compare(int[] o1, int[] o2) {
						return Integer.compare(o2[1], o1[1]);
					}
				});
				
				for (int i = 2; i < nodes.size(); i++) {
					removed[nodes.get(i)[0]] = true;
				}
				return removed;
			}
		}
		
		if (n <= 20) {
			// small network, try all combinations

			long minCost = Long.MAX_VALUE;
			boolean[] best = new boolean[n];
			
			int N = 1 << n;
			for (int j = 0; j < N; j++) {
				boolean[] tempRemoved = new boolean[n];
				for (int i = 0; i < n; i++) {
					if (((1 << i) & j) != 0) {
						tempRemoved[i] = true;
					}
				}
				CutVertices cv = new CutVertices(g, tempRemoved, cost);
				TreeMap<Integer, Integer> canRemove = getRemovable(cv, g, cost, tempRemoved);
				
				if (canRemove.isEmpty()) {
					if (cv.numComponents > 1) {
						trimCheapComponents(cv, g, tempRemoved);
					}
					long tempCost = 0;
					for (int i = 0; i < tempRemoved.length; i++) {
						if (tempRemoved[i]) {
							tempCost += cost[i];
						}
					}
					if (tempCost < minCost) {
						minCost = tempCost;
						best = tempRemoved.clone();
					}
				}
			}
			return best;
		}
		
		// doesn't work :(
		if (n <= 20) {
			// smallish network, try 10^6 solutions.

			long minCost = Long.MAX_VALUE;
			boolean[] best = new boolean[n];
			int numTries = 10000;
			CutVertices cv = new CutVertices(g, removed, cost);
			ArrayList<Integer> canRemove = getAllRemovable(cv, g, cost, removed);
			Random r = new Random(0);
			
			for (int j = 0; j < numTries; j++) {
				boolean[] tempRemoved = new boolean[n];
				for (Integer i: canRemove) {
					if (r.nextBoolean()) {
						tempRemoved[i] = true;
					}
				}
				CutVertices cv2 = new CutVertices(g, tempRemoved, cost);
				ArrayList<Integer> canRemove2 = getAllRemovable(cv2, g, cost, tempRemoved);
				
				if (canRemove2.isEmpty()) {
					if (cv2.numComponents > 1) {
						trimCheapComponents(cv2, g, tempRemoved);
					}
					long tempCost = 0;
					for (int i = 0; i < tempRemoved.length; i++) {
						if (tempRemoved[i]) {
							tempCost += cost[i];
						}
					}
					if (tempCost < minCost) {
						minCost = tempCost;
						best = tempRemoved.clone();
					}
				}
			}
			if (minCost == Long.MAX_VALUE) {
				Arrays.fill(removed, true);
			}
			return best;
		}
		
		if (n < 500 || m == n || m < n+3) {
			CutVertices cv = new CutVertices(g, removed, cost);
			
			while (true) {
				TreeMap<Integer, Integer> canRemove = getRemovable(cv, g, cost, removed);
				
				if (!canRemove.isEmpty()) {
	//				System.out.println("removed " + (canRemove.firstEntry().getValue()+1));
					removed[canRemove.firstEntry().getValue()] = true;
					
					cv = new CutVertices(g, removed, cost);
					// remove all but the most expensive component
					if (cv.numComponents > 1) {
						trimCheapComponents(cv, g, removed);
						cv = new CutVertices(g, removed, cost);
					}
				} else {
					break;
				}
			}
			return removed;
		}

		Arrays.fill(removed, true);
		return removed;
	}

	public static TreeMap<Integer, Integer> getRemovable(CutVertices cv, ArrayList<ArrayList<Integer>> g, int[] cost, boolean[] removed) {
		TreeMap<Integer, Integer> canRemove = new TreeMap<>();

		for (int j = 0; j < g.size(); j++) {
			if (removed[j]) {
				continue;
			}
			ArrayList<Integer> children = g.get(j);
			for (int i = 0; i < children.size(); i++) {
				int child = children.get(i);
				if (removed[child]) {
					continue;
				}
				if (!cv.cutEdge.get(j).contains(child)) {
					canRemove.put(cost[child], child);
					canRemove.put(cost[j], j); //don't care about dups
				}
			}
		}
		return canRemove;
	}

	public static ArrayList<Integer> getAllRemovable(CutVertices cv, ArrayList<ArrayList<Integer>> g, int[] cost, boolean[] removed) {
		ArrayList<Integer> canRemove = new ArrayList<>();
		HashSet<Integer> cr = new HashSet<>();

		for (int j = 0; j < g.size(); j++) {
			if (removed[j]) {
				continue;
			}
			ArrayList<Integer> children = g.get(j);
			for (int i = 0; i < children.size(); i++) {
				int child = children.get(i);
				if (removed[child]) {
					continue;
				}
				if (!cv.cutEdge.get(j).contains(child)) {
					cr.add(child);
					cr.add(j);
				}
			}
		}
		for (Integer i: cr) {
			canRemove.add(i);
		}
		return canRemove;
	}

	public static void trimCheapComponents(CutVertices cv, ArrayList<ArrayList<Integer>> g, boolean[] removed) {
		long maxCost = 0;
		int maxIdx = 0;
		for (int i = 0; i < cv.numComponents; i++) {
			if (cv.componentCost[i] > maxCost) {
				maxCost = cv.componentCost[i];
				maxIdx = i;
			}
		}
		for (int i = 0; i < g.size(); i++) {
			if (!removed[i] && cv.component[i] != maxIdx) {
				removed[i] = true;
			}
		}
	}

	public static class CutVertices {
		int[] depth;			// The depth
		int[] parent;			// The parent of each node 
		int[] component;		// Marks each connected component. Most problems have just one component 
		int[] biConnected;		// Marks each biconnected component. Cut vertices cannot belong to a biconnected component (-1)
		boolean[] cutVertex;	// Marks cut vertices
		int[] low;				// Used as part of the algorithm
		int[] color;			// Tracks visited status
		int N;					// Size of the graph
		int numComponents;		// Number of components
		ArrayList<ArrayList<Integer>> g;
		boolean[] removed;
		int[] nodeCost;
		long[] componentCost;
		
		// A cut edge is an edge that when removed it creates more components than previously in the graph.
		// A graph can have cut vertices without cut edges, but not the other way around. Also know as a bridge.
		// More info: https://en.wikipedia.org/wiki/Bridge_(graph_theory)
		HashMap<Integer, ArrayList<Integer>> cutEdge;
		
		public CutVertices(ArrayList<ArrayList<Integer>> graph, boolean[] removed, int[] cost) {
			this.removed = removed;
			this.nodeCost = cost;
			N = graph.size();
			this.g = graph;
			depth = new int[N];
			parent = new int[N];
			low = new int[N];
			color = new int[N];
			component = new int[N];
			componentCost = new long[N];
			biConnected = new int[N];
			cutVertex = new boolean[N];
			cutEdge = new HashMap<>();
			for (int i = 0; i < N; i++) {
				cutEdge.put(i, new ArrayList<Integer>());
			}
			
			for (int i = 0; i < N; i++) {
				if (!removed[i] && color[i] == 0) {
					parent[i] = -1;
					component[i] = numComponents++;
			        componentCost[component[i]] += nodeCost[i];
					dfs(i, 0);
				}
			}
			
			Arrays.fill(color, 0);
			Arrays.fill(biConnected, -1);
			int bicomp = 0;
			for (int i = 0; i < N; i++) {
				if (color[i] == 0 && !cutVertex[i]) {
					biConnected[i] = bicomp++;
					dfsSimple(i);
				}
			}
		}
				
		public void dfs(int top, int dep) {
			depth[top] = dep;
	        low[top] = depth[top];
	        
	        // colours: unseen = 0, pre-process = 1, post-process = 2
	        color[top] = 1;
	        int childCount = 0;
	        boolean articulation = false;
	        
	        for (int i = 0; i < g.get(top).size(); i++) {
	        	int child = g.get(top).get(i);
	        	if (removed[child]) {
	        		continue;
	        	}
	        	if (color[child] == 0) {
	        		parent[child] = top;
	    	        component[child] = component[top];
	    	        componentCost[component[child]] += nodeCost[child];
	    	        
	        		dfs(child, dep+1);
	        		childCount++;
	        		if (low[child] >= depth[top]) {
	        			articulation = true;
	        		}
	        		if (low[child] > depth[top]) {
	        			cutEdge.get(top).add(child);
	        			cutEdge.get(child).add(top);
	        		}
	        		low[top] = Math.min(low[top], low[child]);
	        	} else if (child != parent[top]) {
                	low[top] = Math.min(low[top], depth[child]);
                }
			}
    		if ((parent[top] != -1 && articulation) || (parent[top] == -1 && childCount > 1)) {
    			cutVertex[top] = true;
    		}
	        color[top] = 2;
		}
		
		public void dfsSimple(int top) {
	        // colours: unseen = 0, pre-process = 1, post-process = 2
	        color[top] = 1;
	        
	        for (int i = 0; i < g.get(top).size(); i++) {
	        	int child = g.get(top).get(i);
	        	if (removed[child]) {
	        		continue;
	        	}
	        	if (color[child] == 0 && !cutVertex[child]) {
	    	        biConnected[child] = biConnected[top];
	    	        dfsSimple(child);
                }
			}
	        color[top] = 2;
		}
	}
	
	public static int isCyclic(ArrayList<ArrayList<Integer>> g, boolean[] removed) {
		// Mark all the vertices as not visited and not part of recursion stack
		int N = g.size();
		boolean[] visited = new boolean[N];
		int[] parent = new int[N];

		// DFS to assign parents
		for (int u = 0; u < N; u++) {
			if (!visited[u] && !removed[u]) {
				parent[u] = -1;

				int top = u;
				Stack<Integer> s = new Stack<>();
				s.add(top);
				
				while (!s.isEmpty()) {
					top = s.pop();
					if (!visited[top] && !removed[top]) {
						visited[top] = true;
						ArrayList<Integer> adj = g.get(top);
						for (Integer i: adj) {
							if (!visited[i] && !removed[i]) {
								parent[i] = top;
								s.add(i);
							}
						}
					}
				}
			}
		}

		// DFS to check parents
		visited = new boolean[N];
		for (int u = 0; u < N; u++) {
			if (!visited[u] && !removed[u]) {
				parent[u] = -1;

				int[] top = {u, -1};
				Stack<int[]> s = new Stack<>();
				s.add(top);
				
				while (!s.isEmpty()) {
					top = s.pop();
					if (!removed[top[0]]) {
						if (!visited[top[0]]) {
							visited[top[0]] = true;
							ArrayList<Integer> adj = g.get(top[0]);
							for (Integer i: adj) {
								s.add(new int[]{i, top[0]});
							}
						} else if (parent[top[0]] != top[1]) {
							// If an adjacent is visited and not parent of current vertex,
							// then there is a cycle.
							return parent[top[0]];
						}
					}
				}
			}
		}
		return -1;
	}

	public static void CLONEME() {
		int tests = in.nextInt();
		for (int j = 0; j < tests; j++) {
			int N = in.nextInt();
			int Q = in.nextInt();
			int[] a = in.nextIntArray(N);
			int[][] q = new int[Q][];
			
			for (int i = 0; i < q.length; i++) {
				q[i] = in.nextIntArray(4);
			}
			String[] res = CLONEME(N, a, Q, q);
			for (int i = 0; i < res.length; i++) {
				System.out.println(res[i]);
			}
		}
	}

	public static void testCLONEME() {
		Random rand = new Random(0);
		int tests = 10000;
		int maxLen = 10;
		int manInt = 10;
		for (int j = 0; j < tests; j++) {
			int N = rand.nextInt(maxLen) + 1;
			int Q = 1;
			
			int[] a = new int[N];
			for (int i = 0; i < a.length; i++) {
				a[i] = rand.nextInt(manInt) +1;
			}
			int[][] q = new int[Q][];
			for (int i = 0; i < q.length; i++) {
				int[] q1 = new int[4];
				for (int k = 0; k < 2; k++) {
					q1[k] = rand.nextInt(N) +1;
				}
				if (q1[0] > q1[1]) {
					int temp = q1[0];
					q1[0] = q1[1];
					q1[1] = temp;
					
				}
				int offset = rand.nextInt(N - q1[1] + 1);
				for (int k = 2; k < 4; k++) {
					q1[k] = offset + q1[k-2];
				}
				q[i] = q1;
			}
			
			String[] exp = CLONEME(N, a, Q, q);
			String[] act = CLONEME2(N, a, Q, q);
			for (int i = 0; i < exp.length; i++) {
				if (exp[i] != act[i]) {
					System.out.println("fail");
					CLONEME2(N, a, Q, q);
				}
			}
		}
	}
	
	public static String[] CLONEME2(int N, int[] A, int Q, int[][] q) {
		int[] hashed = new int[N];
		for (int i = 0; i < hashed.length; i++) {
			// clear top bit to save it for the ST error code
			hashed[i] = clearBit(Integer.hashCode(A[i]), 32);
		}
		
		SegmentTreePersistentGeneralXor stXorHash = new SegmentTreePersistentGeneralXor(hashed);
		SegmentTreePersistentGeneralXor stXor = new SegmentTreePersistentGeneralXor(A);
		SegmentTreePersistentGeneralProduct stProd = new SegmentTreePersistentGeneralProduct(A);
		SegmentTreePersistentGeneralMax stMax = new SegmentTreePersistentGeneralMax(A);
		SegmentTreePersistentGeneralMin stMin = new SegmentTreePersistentGeneralMin(A);
		SegmentTreePersistentGeneral stSum = new SegmentTreePersistentGeneral(A);
		
		int sqrt = (int)Math.sqrt(stMax.query(0, 0, N-1)) + 1;
		SegmentTreeRangePersistentGeneral stRange = new SegmentTreeRangePersistentGeneral(A, sqrt);
		SegmentTreeRangePersistentMin stMinRange = new SegmentTreeRangePersistentMin(A, sqrt);
		SegmentTreeRangePersistentMax stMaxRange = new SegmentTreeRangePersistentMax(A, sqrt);
		
		String[] res = new String[Q];
		for (int i = 0; i < q.length; i++) {
			int a = q[i][0] -1;
			int b = q[i][1] -1;
			int c = q[i][2] -1;
			int d = q[i][3] -1;
			
			if (stXorHash.query(0, a, b) == stXorHash.query(0, c, d) 
					&& stProd.query(0, a, b) == stProd.query(0, c, d) 
					&& stMax.query(0, a, b) == stMax.query(0, c, d) 
					&& stSum.query(0, a, b) == stSum.query(0, c, d)) {
				// probably the same list
				res[i] = "YES";
			} else {
				// try for 1 missmatch
				// if the product and the sum is out but at most max
				long sum1 = stSum.query(0, a, b);
				long sum2 = stSum.query(0, c, d);
				long max = Math.max(stMax.query(0, a, b), stMax.query(0, c, d));
				
				if (b-a+1 <= 4) {

					//slow way
					int[] x = new int[b-a + 1];
					int[] y = new int[b-a + 1];
					for (int j = 0; j < x.length; j++) {
						x[j] = A[a+j];
						y[j] = A[c+j];
					}
					Arrays.sort(x);
					Arrays.sort(y);
					int numOut = 0;
					for (int j = 0; j < y.length; j++) {
						if (x[j] != y[j]) {
							numOut++;
						}
						if (numOut > 1) {
							break;
						}
					}
					if (numOut > 1) {
						res[i] = "NO";
					} else {
						res[i] = "YES";
					}
					continue;
				}
				int mistakes = 0;
				if (stMax.query(0, a, b) != stMax.query(0, c, d)) {
					mistakes++;
				}
				int[][] mr1 = stMinRange.query(0, a, b);
				int[][] mr2 = stMinRange.query(0, c, d);
				for (int j = 0; j < mr2.length; j++) {
					if (mr1[j][0] != mr2[j][0]) {
						mistakes++;
					}
				}
				int[][] nr1 = stMaxRange.query(0, a, b);
				int[][] nr2 = stMaxRange.query(0, c, d);
				for (int j = 0; j < mr2.length; j++) {
					if (nr1[j][0] != nr2[j][0]) {
						mistakes++;
					}
				}
				
				if (mistakes <= 1 && Math.max(sum1, sum2) - Math.min(sum1, sum2) <= max) {
					// maybe we can try to find the missmatch?
					
					int[][] r1 = stRange.query(0, a, b);
					int[][] r2 = stRange.query(0, c, d);
					int[] e = new int[2];
					boolean invalid = false;
					for (int j = 0; j < r1.length; j++) {
						for (int k = 0; k < r1[0].length && !invalid; k++) {
							int diff = Math.abs(r1[j][k] - r2[j][k]);
							if (diff > 0) {
								if (diff <= 1) {
									e[j]++;
								} else {
									invalid = true;
									break;
								}
							}
						}
					}
					for (int j = 0; j < e.length; j++) {
						if (e[j] > 2) {
							invalid = true;
						}
					}
					if (!invalid) {
						res[i] = "YES";
					} else {
						res[i] = "NO";
					}
					
					//slow way
//					int[] x = new int[b-a + 1];
//					int[] y = new int[b-a + 1];
//					for (int j = 0; j < x.length; j++) {
//						x[j] = A[a+j];
//						y[j] = A[c+j];
//					}
//					Arrays.sort(x);
//					Arrays.sort(y);
//					int numOut = 0;
//					for (int j = 0; j < y.length; j++) {
//						if (x[j] != y[j]) {
//							numOut++;
//						}
//						if (numOut > 1) {
//							break;
//						}
//					}
//					if (numOut > 1) {
//						res[i] = "NO";
//					} else {
//						res[i] = "YES";
//					}
				} else {
					res[i] = "NO";
				}
			}
			
		}
		
		return res;
	}
	

	
	public static String[] CLONEME(int N, int[] A, int Q, int[][] q) {
		int[] hashed = new int[N];
		for (int i = 0; i < hashed.length; i++) {
			// clear top bit to save it for the ST error code
			hashed[i] = clearBit(Integer.hashCode(A[i]), 32);
		}
		
		SegmentTreePersistentGeneralXor stXorHash = new SegmentTreePersistentGeneralXor(hashed);
		SegmentTreePersistentGeneralXor stXor = new SegmentTreePersistentGeneralXor(A);
		SegmentTreePersistentGeneralProduct stProd = new SegmentTreePersistentGeneralProduct(A);
		SegmentTreePersistentGeneralMax stMax = new SegmentTreePersistentGeneralMax(A);
		SegmentTreePersistentGeneralMin stMin = new SegmentTreePersistentGeneralMin(A);
		SegmentTreePersistentGeneral stSum = new SegmentTreePersistentGeneral(A);
		
		String[] res = new String[Q];
		for (int i = 0; i < q.length; i++) {
			int a = q[i][0] -1;
			int b = q[i][1] -1;
			int c = q[i][2] -1;
			int d = q[i][3] -1;
			
			if (stXorHash.query(0, a, b) == stXorHash.query(0, c, d) 
					&& stProd.query(0, a, b) == stProd.query(0, c, d) 
					&& stMax.query(0, a, b) == stMax.query(0, c, d) 
					&& stSum.query(0, a, b) == stSum.query(0, c, d)) {
				// probably the same list
				res[i] = "YES";
			} else {
				// try for 1 missmatch
				// if the product and the sum is out but at most max
				long sum1 = stSum.query(0, a, b);
				long sum2 = stSum.query(0, c, d);
				long max = Math.max(stMax.query(0, a, b), stMax.query(0, c, d));
				
				if (Math.max(sum1, sum2) - Math.min(sum1, sum2) <= max) {
					// maybe we can try to find the missmatch?
					
					//slow way
					int[] x = new int[b-a + 1];
					int[] y = new int[b-a + 1];
					for (int j = 0; j < x.length; j++) {
						x[j] = A[a+j];
						y[j] = A[c+j];
					}
					Arrays.sort(x);
					Arrays.sort(y);
					int numOut = 0;
					for (int j = 0; j < y.length; j++) {
						if (x[j] != y[j]) {
							numOut++;
						}
						if (numOut > 1) {
							break;
						}
					}
					if (numOut > 1) {
						res[i] = "NO";
					} else {
						res[i] = "YES";
					}
				} else {
					res[i] = "NO";
				}
			}
			
		}
		
		return res;
	}
	
	public static class NodeRange {
		public NodeRange left, right;
		public int[][] value;
		public int version;
		
		// for testing
		public static int counter;

		public NodeRange(NodeRange left, NodeRange right, NodeRange parent, int[][] value) {
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

	public static class SegmentTreeRangePersistentMax extends SegmentTreeRangePersistentGeneral {
		@Override
		protected int[][] function(int[][] a, int[][] b) {
			int[] x = new int[]{a[0][0], a[1][0], b[0][0], b[1][0]};
			Arrays.sort(x);
			
			int[][] n = new int[][]{{x[3]}, {x[2]}};
			return n;
		}
		@Override
		protected int[][] function(int a) {
			int[][] n = new int[2][1];
			n[0][0] = -1;
			n[1][0] = a;
			return n;
		}
		
		public SegmentTreeRangePersistentMax(int[] input, int N) {
			super(input, N);
		}
	}

	public static class SegmentTreeRangePersistentMin extends SegmentTreeRangePersistentGeneral {
		@Override
		protected int[][] function(int[][] a, int[][] b) {
			int[] x = new int[]{a[0][0], a[1][0], b[0][0], b[1][0]};
			Arrays.sort(x);
			
			int[][] n = new int[][]{{x[0]}, {x[1]}};
			return n;
		}
		@Override
		protected int[][] function(int a) {
			int[][] n = new int[2][1];
			n[0][0] = a;
			n[1][0] = Integer.MAX_VALUE;
			return n;
		}
		
		public SegmentTreeRangePersistentMin(int[] input, int N) {
			super(input, N);
		}
	}

	public static class SegmentTreeRangePersistentGeneral {
		int[] input;
		ArrayList<NodeRange> version;
		
		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int[][] function(int[][] a, int[][] b) {
			int[][] n = new int[2][N+1];
			for (int i = 0; i <= N; i++) {
				n[0][i] = a[0][i] + b[0][i];
			}
			for (int i = 0; i <= N; i++) {
				n[1][i] = a[1][i] + b[1][i];
			}
			return n;
		}
		protected int[][] function(int a) {
			int[][] n = new int[2][N+1];
			n[0][a/N]++;
			n[1][a%N]++;
			return n;
		}

		/**
		 * The value of INVALID indicates an out of bounds query. It should be a number that will never happen.
		 */
		protected int INVALID = Integer.MIN_VALUE;
		public int N;

		public SegmentTreeRangePersistentGeneral(int[] input, int N) {
			NodeRange root = new NodeRange(null, null, null, null); // this zero is just a placeholder
			this.N = N;
			version = new ArrayList<>();
			version.add(root);
			root.version = 0;
			this.input = input;
			build(root, 0, input.length-1);
		}

		private void build(NodeRange n, int low, int high) {
			if (low==high) {
				n.value = function(input[low]);
				return;
			}
			int mid = (low+high) / 2;
			n.left = new NodeRange(null, null, n, null);  // these zeros are just a placeholders
			n.right = new NodeRange(null, null, n, null);
			build(n.left, low, mid);
			build(n.right, mid+1, high);
			n.value = function(n.left.value, n.right.value);
		}

		/**
		 * Query according to version number in range [l, r] (inclusive)
		 */
		public int[][] query(int version, int l, int r) {
			return query(this.version.get(version), 0, input.length-1, l, r);
		}
		
		/**
		 * Query according to some version's root node
		 */
		private int[][] query(NodeRange n, int low, int high, int l, int r) {
			if (l > high || r < low || low > high)
				return null;
			if (l <= low && high <= r)
				return n.value;
			int mid = (low+high) / 2;
			int[][] p1 = query(n.left,low,mid,l,r);
			int[][] p2 = query(n.right,mid+1,high,l,r);
			if (p1 != null) {
				if (p2 != null) {
					return function(p1, p2);
				} else {
					return p1;
				}
			} else {
				if (p2 != null) {
					return p2;
				} else {
					return null;
				}
			}
		}
	}

	public static class SegmentTreePersistentGeneralMax extends SegmentTreePersistentGeneral {
		@Override
		protected long function(long a, long b) {
			return Math.max(a, b);
		}
		
		public SegmentTreePersistentGeneralMax(int[] input) {
			super(input);
		}
	}

	public static class SegmentTreePersistentGeneralMin extends SegmentTreePersistentGeneral {
		@Override
		protected long function(long a, long b) {
			return Math.min(a, b);
		}
		
		public SegmentTreePersistentGeneralMin(int[] input) {
			super(input);
		}
	}
	
	public static class SegmentTreePersistentGeneralXor extends SegmentTreePersistentGeneral {
		@Override
		protected long function(long a, long b) {
			return a ^ b;
		}
		
		public SegmentTreePersistentGeneralXor(int[] input) {
			super(input);
		}
	}
	
	public static class SegmentTreePersistentGeneralProduct extends SegmentTreePersistentGeneral {
		public final int mod = 1000000007;
		@Override
		protected long function(long a, long b) {
			return (a * b) % mod;
		}
		
		public SegmentTreePersistentGeneralProduct(int[] input) {
			super(input);
		}
	}
	
	public static void testPRMQ() {
		int numTests = 100;
		int len = 1000;
		Random ra = new Random(0);
		int maxPrime = 1000;
		int maxA = 1000;
		for (int test = 0; test < numTests; test++) {
			int[] a = new int[len];
			for (int i = 0; i < a.length; i++) {
				a[i] = ra.nextInt(maxA)+1;
			}
			int[][] q = new int[len][];
			
			for (int i = 0; i < q.length; i++) {
				q[i] = new int[4];
				q[i][0] = ra.nextInt(len) + 1;
				q[i][1] = ra.nextInt(len - q[i][0] +1) + q[i][0];
				q[i][2] = ra.nextInt(maxPrime) +1;
				q[i][3] = ra.nextInt(maxPrime - q[i][2] +1) + q[i][2] +1;
			}
			int[] actual = PRMQ2(a.length, a, q.length, q);
			int[] expected = PRMQ(a.length, a, q.length, q);
			for (int i = 0; i < expected.length; i++) {
				if (actual[i] != expected[i]) {
					System.out.println("fail");
					PRMQ2(a.length, a, q.length, q);
				}
			}
		}
	}

	public static void PRMQ2() {
		int N = in.nextInt();
		int[] a = in.nextIntArray(N);
		int Q = in.nextInt();
		int[][] q = new int[Q][];
		
		for (int i = 0; i < q.length; i++) {
			q[i] = in.nextIntArray(4);
		}
		int[] res = PRMQ2(N, a, Q, q);
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i]);
		}
	}

	static TreeMap<Integer, Integer> p;
	
	public static int[] PRMQ2(int N, int[] a, int Q, int[][] q) {
		if (p == null) {
			p = getPrimesSet(1000000);
		}
		ArrayList<ArrayList<int[]>> versionUpdates = new ArrayList<>();
		for (int i = 0; i < p.size(); i++) {
			versionUpdates.add(new ArrayList<>());
		}
		for (int i = 0; i < a.length; i++) {
			// factorize A
			HashMap<Integer, Integer> primeCount = factorize(a[i]);
			for (Map.Entry<Integer, Integer> e: primeCount.entrySet()) {
				versionUpdates.get(p.get(e.getKey())).add(new int[]{i, e.getValue()});
			}
		}
		
		int[] base = new int[a.length];
		ArrayList<int[]> v0 = versionUpdates.get(0);
		for (int[] i: v0) {
			base[i[0]] = i[1];
		}
		
		SegmentTreePersistentGeneral st = new SegmentTreePersistentGeneral(base);
		for (int i = 1; i < versionUpdates.size(); i++) {
			ArrayList<int[]> v = versionUpdates.get(i);
			st.upgrade(v);
		}
		
		int[] primeCount = new int[1000001];
		int num = -1;
		for (int i = 0; i < primeCount.length; i++) {
			if (p.containsKey(i)) {
				num++;
			}
			primeCount[i] = num;
		}
		
		int[] res = new int[Q];
		for (int i = 0; i < q.length; i++) {
			int p1 = primeCount[q[i][2]];
			int p2 = primeCount[q[i][3]];
			int left = q[i][0] -1;
			int right = q[i][1] -1;
			
			int sum = (int)st.query(p2, left, right);
			if (p1 > 0) {
				if (!p.containsKey(q[i][2])) {
					p1++;
				}
				sum -= st.query(p1-1, left, right);
			}
			res[i] = sum;
		}
		return res;
		
	}

	private static HashMap<Integer, Integer> factorize(int n) {
		HashMap<Integer, Integer> primeCount  = new HashMap<>();
		int d = 2;
		int exp = 0;
		while (n > 1) {
			while (n % d == 0) {
				exp++;
				primeCount.put(d, exp);
				n /= d;
			}
			d++;
			if (d*d > n) {
				if (n > 1) {
					exp++;
					primeCount.put(n, exp);
					break;
				}
			}
		}
		return primeCount;
	}
	
	public static TreeMap<Integer, Integer> getPrimesSet(int limit) {
		TreeMap<Integer, Integer> primes = new TreeMap<>();
		BitSet p = getPrimes(limit);
		
		for (int i = p.nextSetBit(0); i >= 0 && i <= limit; i = p.nextSetBit(i+1)) {
			primes.put(i, 0);
		}
		
		int idx = 0;
		for (Map.Entry<Integer, Integer> e: primes.entrySet()) {
			e.setValue(idx++);
		}
		return primes;
	}
	
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
	 */


	public static class Node {
		public Node left, right;
		public long value;
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
		protected long function(long a, long b) {
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
		public long query(int version, int l, int r) {
			return query(this.version.get(version), 0, input.length-1, l, r);
		}
		
		/**
		 * Query according to some version's root node
		 */
		private long query(Node n, int low, int high, int l, int r) {
			if (l > high || r < low || low > high)
				return this.INVALID;
			if (l <= low && high <= r)
				return n.value;
			int mid = (low+high) / 2;
			long p1 = query(n.left,low,mid,l,r);
			long p2 = query(n.right,mid+1,high,l,r);
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

	public static BitSet getPrimes(int limit) {
		BitSet notPrimes = new BitSet(limit+1);
		BitSet primes = new BitSet(limit+1);
		
		for (int i = notPrimes.nextClearBit(2); i >= 0 && i <= limit; i = notPrimes.nextClearBit(i+1)) {
			primes.set(i);
			for (int j = 2*i; j <= limit; j+=i) {
				notPrimes.set(j);
			}
		}
		return primes;
	}
	
	public static void PRMQ() {
		int N = in.nextInt();
		int[] a = in.nextIntArray(N);
		int Q = in.nextInt();
		int[][] q = new int[Q][];
		for (int i = 0; i < q.length; i++) {
			q[i] = in.nextIntArray(4);
		}
		
		int[] res = PRMQ(N, a, Q, q);
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i]);
		}
	}
	
	static BitSet p2;
	public static int[] PRMQ(int N, int[] a, int Q, int[][] q) {
		if (p2 == null) {
			p2 = getPrimes(1000000);
		}
		int[] res = new int[Q];
		for (int i = 0; i < q.length; i++) {
			res[i] = F(a, p2, q[i][0] -1, q[i][1] -1, q[i][2], q[i][3]);
		}
		return res;
	}
	
	public static int F(int[] a, BitSet p, int L, int R, int X, int Y) {
		int result = 0;
		for (int i = X; i <= Y; i++) {
			if (p.get(i)) {
				for (int j = L; j <= R; j++) {
					int number = a[j];
					int exp = 0;
					while (number % i == 0) {
						exp++;
						number /= i;
					}
					result += exp;
				}
			}
		}
		return result;
	}
	
	public static void NEO01() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = in.nextInt();
			long[] a = in.nextLongArray(n);
			Arrays.sort(a);
			reverse(a);
			
			long result = 0;
			long sum = 0;
			int i = 0;
			for (i = 0; i < a.length; i++) {
				long newResult = (sum + a[i]) * (i+1);
				if (newResult > result) {
					result = newResult;
					sum += a[i];
				} else {
					break;
				}
			}
			while (i < a.length) {
				result += a[i];
				i++;
			}
			
			System.out.println(result);
		}
	}
	
    public static void reverse(long[] a) {
    	int N = a.length;
    	for (int i = 0; i < N/2; i++) {
    		long temp = a[i];
    		a[i] = a[N - i - 1];
    		a[N - i - 1] = temp;
		}
    }
    
    public static int clearBit(int value, int idx) {
		return (value & ~(1 << idx));
	}
	
	public static int setBit(int value, int idx) {
		return (value | (1 << idx));
	}
	
	public static boolean getBit(int value, int idx) {
		return (value & (1 << idx)) != 0;
	}
	
	public static long clearBitL(long value, int idx) {
		return (value & ~(1L << idx));
	}
	
	public static long setBitL(long value, int idx) {
		return (value | (1L << idx));
	}
	
	public static boolean getBitL(long value, int idx) {
		return (value & (1L << idx)) != 0;
	}

	public static void UNIONSET2() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = in.nextInt();
			int k = in.nextInt();
			final int arrSize = k/64+1;
			ArrayList<BitSize2> list = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				int num = in.nextInt();
				int[] temp = in.nextIntArray(num);
				long[] x = new long[arrSize];
				Arrays.fill(x, -1);
				HashSet<Integer> s = new HashSet<>();
				for (int j = 0; j < temp.length; j++) {
					s.add(temp[j]);
				}
				for (int j = 0; j < arrSize; j++) {
					for (int m = 0; m < 64; m++) {
						int z = j * 64 + m;
						if (z >= 1 && z <= k && !s.contains(z)) {
							x[j] = clearBitL(x[j], m);
						}
					}
				}
				list.add(new BitSize2(x, num));
			}
			Collections.sort(list);
			int valid = 0;
			for (int i = 0; i < list.size(); i++) {
				for (int j = i+1; j < list.size(); j++) {
					if (list.get(i).size + list.get(j).size < k) {
						break;
					}
					long[] x = list.get(i).set;
					long[] y = list.get(j).set;
					int num = 0;
					for (int m = 0; m < arrSize; m++) {
						if ((x[m] | y[m]) != -1) {
							break;
						}
						num++;
					}
					if (num == arrSize) {
						valid++;
					}
				}
			}
			System.out.println(valid);
		}
	}

	public static void UNIONSET() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = in.nextInt();
			int k = in.nextInt();
			ArrayList<BitSize> list = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				int num = in.nextInt();
				int[] temp = in.nextIntArray(num);
				BitSet x = new BitSet();
				for (int j = 0; j < num; j++) {
					x.set(temp[j]);
				}
				list.add(new BitSize(x, num));
			}
			Collections.sort(list);
			int valid = 0;
			for (int i = 0; i < list.size()/2; i++) {
				for (int j = i+1; j < list.size()/2; j++) {
					if (list.get(i).size + list.get(j).size < k) {
						break;
					}
					BitSet b = (BitSet) list.get(i).set.clone();
					b.or(list.get(j).set);
					int numSet = b.cardinality();
					if (numSet == k) {
						valid++;
					}
				}
			}
			System.out.println(valid);
		}
	}
	
	public static class BitSize2 implements Comparable<BitSize2> {
		long[] set;
		int size;
		public BitSize2(long[] set, int size) {
			this.set = set;
			this.size = size;
		}
		@Override
		public int compareTo(BitSize2 o) {			
			return Integer.compare(o.size, size);
		}
	}
	
	public static class BitSize implements Comparable<BitSize> {
		BitSet set;
		int size;
		public BitSize(BitSet set, int size) {
			super();
			this.set = set;
			this.size = size;
		}
		@Override
		public int compareTo(BitSize o) {			
			return Integer.compare(o.size, size);
		}
	}

	public static void SUMQ() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int[] s = in.nextIntArray(3);
			long[] a = in.nextLongArray(s[0]);
			long[] b = in.nextLongArray(s[1]);
			long[] c = in.nextLongArray(s[2]);
			
			long mod = 1000000007;
			Arrays.sort(a);
			Arrays.sort(b);
			Arrays.sort(c);
			long sumA = 0;
			long sumC = 0;
			int idxA = 0;
			int idxC = 0;
			long total = 0;
			
			for (int i = 0; i < b.length; i++) {
				long lenA = 0;
				long lenC = 0;
				while (idxA < a.length && a[idxA] <= b[i]) {
					sumA = (sumA + a[idxA]) % mod;
					idxA++;
					lenA++;
				}
				while (idxC < c.length && c[idxC] <= b[i]) {
					sumC = (sumC + c[idxC]) % mod;
					idxC++;
					lenC++;
				}
				lenA = idxA;
				lenC = idxC;
				total = (total + ((sumA * sumC) % mod)
						+ ((b[i] * (((sumA * lenC) % mod) + ((sumC * lenA) % mod))) % mod)
						+ ((b[i] * b[i]) % mod) * ((lenA * lenC) % mod)) % mod;
			}
			System.out.println(total);
		}
	}

	public static void XENRANK() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			long a = in.nextLong();
			long b = in.nextLong();
			long base = a + b;
			long rank = (base * (base +1))/2 + a +1;
			System.out.println(rank);
		}
	}

	public static void GOODSET() {
		int tests = in.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = in.nextInt();
			TreeSet<Integer> set = new TreeSet<>();
			int c = 1;
			while (set.size() < n) {
				boolean valid = false;
				while (!valid) {
					set.add(c);
					valid = true;
					for (Integer x: set) {
						for (Integer y: set) {
							if (x != y && set.contains(x+y)) {
								valid = false;
								break;
							}
						}
						if (!valid) {
							break;
						}
					}
					if (!valid) {
						set.remove(c);
						c++;
					}
				}
				c++;
			}
			for (Integer x: set) {
				System.out.print(x + " ");
			}
			System.out.println();
		}
	}
	 
    public static class InputReader {
        public BufferedReader r;
        public StringTokenizer st;
        public InputReader(InputStream s) {r = new BufferedReader(new InputStreamReader(s), 32768); st = null;}
        public String next() {while (st == null || !st.hasMoreTokens()) {try {st = new StringTokenizer(r.readLine());}
        catch (IOException e) {throw new RuntimeException(e);}} return st.nextToken();}
        public int nextInt() {return Integer.parseInt(next());}
        public long nextLong() {return Long.parseLong(next());}
        public double nextDouble() {return Double.parseDouble(next());}
        public long[] nextLongArray(int n) {long[] a = new long[n]; for (int i = 0; i < a.length; i++) {a[i] = this.nextLong();} return a;}
        public int[] nextIntArray(int n) {int[] a = new int[n];	for (int i = 0; i < a.length; i++) {a[i] = this.nextInt();} return a;}
    }
}
