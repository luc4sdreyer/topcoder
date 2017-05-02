import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

import org.junit.Test;

public class GraphAlgorithms {

	/*******************************************************************************************************************************
	 * DEPTH First Search in an array (DFS)
	 * 
	 * The depth first search is well geared towards problems where we want to find any
	 * solution to the problem (not necessarily the shortest path), or to visit all of
	 * the nodes in the graph. 
	 */


	private class NodeDfs {
		public int x, y;
		public NodeDfs (int x, int y) {
			this.x = x;this.y = y;
		}
	}

	int doFill(int x, int y) {
		int[][] map = new int[600][400];
		int[][] colours = new int[600][400];
		int[] dy = {-1, 0, 1, 0};
		int[] dx = {0, 1, 0, -1};
		
		int result = 0;
		int colour = 0;

		// Declare stack of nodes
		Stack<NodeDfs> stack = new Stack<NodeDfs>();
		stack.push(new NodeDfs(x, y));
		colour++;

		while (!stack.isEmpty()) {
			NodeDfs top = stack.pop();

			if (top.x < 0 || top.x >= map[0].length) continue;
			if (top.y < 0 || top.y >= map.length) continue;
			if (map[top.y][top.x] == 0) continue;

			colours[top.x][top.y] = colour; // Record visit

			result++;

			// visit every adjacent node
			for (int i = 0; i < dx.length; i++) {
				stack.push(new NodeDfs(top.x + dx[i], top.y + dy[i]));
			}
		}
		return result;
	}

	/*******************************************************************************************************************************
	 * BREADTH First Search in an array (BFS)
	 * 
	 * It has the extremely useful property that if all of the edges in a graph are
	 * unweighted (or the same weight) then the first time a node is visited is the
	 * shortest path to that node from the source node.
	 */

	private int targetX;
	private int targetY;

	public static class Node implements Comparable<Node> {
		public int cost, x, y;

		public Node(int cost) {
			this.cost = cost;
		}

		public Node(int x, int y, int cost) {
			this.cost = cost;
			this.x = x;
			this.y = y;
		}		

		public String toString() {
			return "(" + x + ", "+ y + ": " + cost + ")";
		}

		@Override
		public int compareTo(Node o) {
			Node next = (Node)o;
			if (y == next.y) {
				return Integer.compare(x, next.x);
			}
			return Integer.compare(y, next.y);
		}
	}

	public Node bfs(int x, int y) {
		int[][] map = new int[600][400];
		
		// Declare stack of nodes
		Queue<Node> q = new LinkedList<Node>();
		q.add(new Node(x, y, (int)0));
		Node target = new Node(0, 0, 0);
		
		int n = 10;
		boolean[][] visited = new boolean[n][n];

		while (q.size() != 0) {
			Node top = q.poll();

			if (top.x < 0 || top.x >= map[0].length || top.y < 0 || top.y >= map.length || visited[top.y][top.x]) {
				continue;
			}
			
			visited[top.y][top.x] = true;
			
			if (top.compareTo(target) == 0) {
				return top;
			}

			// visit every adjacent node
			q.add(new Node((int)(top.x + 1), top.y, (int)(top.cost+1)));
			q.add(new Node((int)(top.x - 1), top.y, (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y + 1), (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y - 1), (int)(top.cost+1)));
		}
		return null;
	}

	/*******************************************************************************************************************************
	 * Dijkstra's algorithm (Uniform cost search)
	 * 
	 * A lot like a breath first search, except it can be used in a weighted graph.
	 * The first time a node is visited is the shortest path to that node from the source node.
	 * Uses a Priority Queue (Heap, get best node in O(log n))
	 */
	public Node dijkstra() {
		int[][] map = new int[600][400];
		PriorityQueue<Node> q = new PriorityQueue<Node>();

		while (q.size() != 0) {
			Node top = q.poll();

			if (top.x < 0 || top.x >= map.length) continue;
			if (top.y < 0 || top.y >= map[0].length) continue;
			if(map[top.x][top.y] == 0) continue;
			
			map[top.x][top.y] = 1;
			if ((top.x == this.targetX) && (top.y ==this.targetY)) {
				return top;
			}

			// visit every adjacent node
			q.add(new Node((int)(top.x + 1), top.y, (int)(top.cost+1)));
			q.add(new Node((int)(top.x - 1), top.y, (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y + 1), (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y - 1), (int)(top.cost+1)));
		}
		return null;
	}

	/*******************************************************************************************************************************
	 * Floyd Warshall All-Pairs Shortest Path algorithm
	 * 
	 * For when the graph is represented by an adjacency matrix. It runs in O(n^3) time, where n is the number of vertices in the
	 * graph. However, in comparison to Dijkstra, which only gives us the shortest path from one source to the targets,
	 * Floyd-Warshall gives us the shortest paths from all source to all target nodes.
	 * 
	 * This algorithm assumes that there are no negative cycles in the graph.
	 */
	public void floydWarshall(int[][] graph) {
		for (int k = 0; k < graph.length; k++) {
			for (int i = 0; i < graph.length; i++) {
				for (int j = 0; j < graph.length; j++) {
					if (graph[i][k] < Integer.MAX_VALUE && graph[k][j] < Integer.MAX_VALUE) {
						graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
					}
				}
			}
		}
	}

	public void floydWarshall(String[] data) {
		int n = data.length;
		int[][] sp = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (data[i].charAt(j) == 'Y') {
					sp[i][j] = 1;
				} else {
					sp[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		for (int k = 0; k < sp.length; k++) {
			for (int i = 0; i < sp.length; i++) {
				for (int j = 0; j < sp.length; j++) {
					if (sp[i][k] != Integer.MAX_VALUE && sp[k][j] != Integer.MAX_VALUE) {
						sp[i][j] = Math.min(sp[i][j], sp[i][k] + sp[k][j]);
					}
				}
			}
		}
	}

	public static class ListNode {
		public int node;
		public int weight;
		public ListNode(int node, int weight) {
			this.node = node;
			this.weight = weight;
		}
		public String toString() {
			return "[n: " + this.node + ", w: " + this.weight + "]";
		}
	}
	
	/*******************************************************************************************************************************
	 * Bellman Ford shortest path algorithm
	 * 
	 * Dijkstra and Bellman Ford are both single source shortest path algorithms. That is, given the source, find the shortest
	 * path to all other nodes in the graph starting from this source node. But Dijkstra is O(m*log(n)) and Bellman Ford is O(m*n).
	 * BUT unlike Dijkstra, Bellman Ford works when the edge costs are negative! This algorithm can also detect the presence of
	 * negative cycles in the graph if there are any.
	 */
	public static int bellmanFord(ArrayList<ArrayList<ListNode>> list) {
//		for (int i = 0; i < N; i++) {
//			list.add(new ArrayList<ListNode>());
//		}
//		for (int i = 0; i < s.length; i++) {;
//			ListNode temp = new ListNode(t[i]-1, weight[i]);
//			list.get(s[i]-1).add(temp);
//		}
		int N = list.size();
		int[] d = new int[N];
		Arrays.fill(d, Integer.MAX_VALUE);
		d[0] = 0;
		boolean updated = false;
		for (int n = 0; n < N-1; n++) { // yes, NOT n!! at most n-1 edges on the shortest path
			updated = false;
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).size(); j++) {
					if (d[list.get(i).get(j).node] > d[i] + list.get(i).get(j).weight) {
						d[list.get(i).get(j).node] = d[i] + list.get(i).get(j).weight;
						updated = true;
					}
				}
			}
			if (!updated) {
				break;
			}
		}

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				if (d[list.get(i).get(j).node] > d[i] + list.get(i).get(j).weight) {
					// Negative cycle!!
					d[list.get(i).get(j).node] = Integer.MAX_VALUE;
				}
			}
		}
		return d[N-1];
	}

	/*******************************************************************************************************************************
	 * Maximum Bipartite Matching (simple, unweighted)
	 * 
	 * Example problem: There are M job applicants and N jobs. Each applicant has a subset of jobs that he/she is interested in.
	 * Each job opening can only accept one applicant and a job applicant can be appointed for only one job. Find an assignment of
	 * jobs to applicants in such that as many applicants as possible get jobs.
	 * 
	 * Algorithm is O(n^3)
	 */
	// A DFS based recursive function that returns true if a
	// matching for vertex u is possible
	static boolean bpm(boolean[][] bpGraph, int u, boolean[] seen, int[] matchR)
	{
		int N = bpGraph[0].length;
		// Try every job one by one
		for (int v = 0; v < N; v++)
		{
			// If applicant u is interested in job v and v is
			// not visited
			if (bpGraph[u][v] && !seen[v])
			{
				seen[v] = true; // Mark v as visited

				// If job 'v' is not assigned to an applicant OR
				// previously assigned applicant for job v (which is matchR[v]) 
				// has an alternate job available. 
				// Since v is marked as visited in the above line, matchR[v] 
				// in the following recursive call will not get job 'v' again
				if (matchR[v] < 0 || bpm(bpGraph, matchR[v], seen, matchR))
				{
					matchR[v] = u;
					return true;
				}
			}
		}
		return false;
	}

	// Returns maximum number of matching from M to N
	public static int maxBPM(boolean[][] bpGraph)
	{
		int M = bpGraph.length;
		int N = bpGraph[0].length;
		// An array to keep track of the applicants assigned to
		// jobs. The value of matchR[i] is the applicant number
		// assigned to job i, the value -1 indicates nobody is
		// assigned.
		int[] matchR = new int[N];

		// Initially all jobs are available
		Arrays.fill(matchR, -1);

		int result = 0; // Count of jobs assigned to applicants
		for (int u = 0; u < M; u++)
		{
			// Mark all jobs as not seen for next applicant.
			boolean[] seen = new boolean[N];

			// Find if the applicant 'u' can get a job
			if (bpm(bpGraph, u, seen, matchR))
				result++;
		}
		return result;
	}
	
	/*******************************************************************************************************************************
	 * Finding cut vertices, O(|E| + |V|)
	 * 
	 * The classic sequential algorithm for computing biconnected components in a connected undirected graph is due to
	 * John Hopcroft and Robert Tarjan. It runs in linear time, and is based on depth-first search.
	 * 
	 * A biconnected component (also known as a block or 2-connected component) is a maximal biconnected subgraph.
	 * Any connected graph decomposes into a tree of biconnected components called the block-cut tree of the graph.
	 * The blocks are attached to each other at shared vertices called cut vertices or articulation points.
	 * Specifically, a cut vertex is any vertex whose removal increases the number of connected components.
	 */

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
		
		// A cut edge is an edge that when removed it creates more components than previously in the graph.
		// A graph can have cut vertices without cut edges, but not the other way around. Also know as a bridge.
		// More info: https://en.wikipedia.org/wiki/Bridge_(graph_theory)
		HashMap<Integer, ArrayList<Integer>> cutEdge;
		
		public CutVertices(ArrayList<ArrayList<Integer>> graph) {
			N = graph.size();
			this.g = graph;
			depth = new int[N];
			parent = new int[N];
			low = new int[N];
			color = new int[N];
			component = new int[N];
			biConnected = new int[N];
			cutVertex = new boolean[N];
			cutEdge = new HashMap<>();
			for (int i = 0; i < N; i++) {
				cutEdge.put(i, new ArrayList<Integer>());
			}
			
			for (int i = 0; i < N; i++) {
				if (color[i] == 0) {
					parent[i] = -1;
					component[i] = numComponents++;
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
	        	if (color[child] == 0) {
	        		parent[child] = top;
	    	        component[child] = component[top];
	    	        
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
	        	if (color[child] == 0 && !cutVertex[child]) {
	    	        biConnected[child] = biConnected[top];
	    	        dfsSimple(child);
                }
			}
	        color[top] = 2;
		}
	}

	/*******************************************************************************************************************************
	 * Minimum spanning tree, O(|E|.log|V|)
	 * 
	 * Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a weighted undirected graph. This means it
	 * finds a subset of the edges that forms a tree that includes every vertex, where the total weight of all the edges in the
	 * tree is minimized. The algorithm operates by building this tree one vertex at a time, from an arbitrary starting vertex,
	 * at each step adding the cheapest possible connection from the tree to another vertex.
	 * 
	 * A single graph can have many different spanning trees, depending on the starting node and other factors.
	 * 
	 * Returns a HashMap of the |V|-1 edges that make up the MST, each with its cost as the value. 
	 */

	public static HashMap<Pair<Integer, Integer>, Integer> primMST(ArrayList<ArrayList<Integer>> g, ArrayList<ArrayList<Integer>> cost) {
		int N = g.size();
		int[] e = new int[N];				// The vertex that connects to this node in the MST.
		int[] d = new int[N];				// The cost of the edge leading to the vertex in e.
		boolean[] visited = new boolean[N];
		Arrays.fill(d, Integer.MAX_VALUE);
		Arrays.fill(e, -1);
		TreeSet<Pair<Integer, Integer>> s = new TreeSet<>();
		for (int i = 0; i < N; i++) {
			s.add(new Pair<>(d[i], i));
		}
		int top = 0;
		while (!s.isEmpty()) {
			top = s.first().b;
			s.remove(s.first());
			if (visited[top]) {
				continue;
			}
			visited[top] = true;
			
			for (int i = 0; i < g.get(top).size(); i++) {
				int neighbour = g.get(top).get(i);
				int weight = cost.get(top).get(i);
				if (!visited[neighbour] && d[neighbour] > weight) {
					s.remove(new Pair<>(d[neighbour], neighbour));
					d[neighbour] = weight;
					e[neighbour] = top;
					s.add(new Pair<>(d[neighbour], neighbour));
				}
			}
		}
		
		HashMap<Pair<Integer, Integer>, Integer> mst = new HashMap<>();
		for (int i = 0; i < d.length; i++) {
			int a = Math.min(i, e[i]);
			int b = Math.max(i, e[i]);
			if (a != -1 && !mst.containsKey(a)) {
				mst.put(new Pair<>(a, b), d[i]);
			}
		}
		return mst;
	}

	/*******************************************************************************************************************************
	 * Tree diameter, or the maximum distance between two nodes in a tree. 
	 * 
	 * The basic idea is:
	 * 1. DFS from a random node
	 * 2. Choose the node the farthest away and DFS from it.
	 * 3. Now the farthest node is equal to the diameter. 
	 * 
	 * This code assumes the input as lines of triplets, indicating two vertices and the weight of their connection.
	 * 
	 * Complexity: O(n)
	 */
	
	public static long treeDiameter() {
		Scanner scan = new Scanner(System.in);
		HashMap<Integer, ArrayList<int[]>> map = new HashMap<>();
		int n = scan.nextInt();
		int start = -1; 
		for (int i = 0; i < n-1; i++) {
			int a = scan.nextInt();
			if (start == -1) {
				start = a;
			}
			int b = scan.nextInt();
			int v = scan.nextInt();
			
			if (!map.containsKey(a)) {
				map.put(a, new ArrayList<int[]>());
			}
			map.get(a).add(new int[]{b, v});
			
			if (!map.containsKey(b)) {
				map.put(b, new ArrayList<int[]>());
			}
			map.get(b).add(new int[]{a, v});
		}
		scan.close();
		
		// First DFS
		
		long[] top = {start, 0};
		Stack<long[]> s = new Stack<long[]>();
		HashSet<Long> visited = new HashSet<>();
		s.add(top);
		long max = 0;
		long maxIdx = 0;
		while (!s.isEmpty()) {
			top = s.pop();
			if (visited.contains(top[0])) {
				continue;
			}
			visited.add(top[0]);
			if (max < top[1]) {
				max = top[1];
				maxIdx = top[0];
			}
			if (map.containsKey((int)top[0])) {
				ArrayList<int[]> children = map.get((int)top[0]);
				for (int i = 0; i < children.size(); i++) {
					s.push(new long[]{children.get(i)[0], top[1] + children.get(i)[1]});
				}
			}
		}
		
		// Second DFS
		
		top[0] = maxIdx;
		top[1] = 0;
		s = new Stack<long[]>();
		visited = new HashSet<>();
		s.add(top);
		max = 0;
		maxIdx = 0;
		while (!s.isEmpty()) {
			top = s.pop();
			if (visited.contains(top[0])) {
				continue;
			}
			visited.add(top[0]);
			if (max < top[1]) {
				max = top[1];
				maxIdx = top[0];
			}
			if (map.containsKey((int)top[0])) {
				ArrayList<int[]> children = map.get((int)top[0]);
				for (int i = 0; i < children.size(); i++) {
					s.push(new long[]{children.get(i)[0], top[1] + children.get(i)[1]});
				}
			}
		}
		
		return max;
	}

	/*******************************************************************************************************************************
	 * Standard Topsort. 
	 * 
	 * A topological sort (sometimes abbreviated topsort or toposort) or topological ordering of a directed graph is a linear
	 * ordering of its vertices such that for every directed edge uv from vertex u to vertex v, u comes before v in the ordering.
	 * 
	 * Returns false if the graph is not a DAG (contains a directed cycle).
	 * 
	 * O(n + m) time
	 */		
	@SuppressWarnings("unchecked")
	public static boolean topSort(ArrayList<Integer> order, HashMap<Integer, ArrayList<Integer>> graphRef) {
		
		// Create a copy of the graph
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
		for (Integer key: graphRef.keySet()) {
			graph.put(key, (ArrayList<Integer>) graphRef.get(key).clone());
		}
		
		// Count number of incoming edges
		HashMap<Integer, Integer> numIncoming = new HashMap<>();
		for (Integer key: graph.keySet()) {
			ArrayList<Integer> children = graph.get(key);
			for (Integer child: children) {
				numIncoming.put(child, numIncoming.containsKey(child) ? numIncoming.get(child) + 1 : 1);
			}
		}
		
		// Select vertices with no incoming edges
		Stack<Integer> roots = new Stack<>(); // Doesn't have to be a stack!
		for (Integer key: graph.keySet()) {
			if (!numIncoming.containsKey(key) || numIncoming.get(key) == 0) {
				roots.push(key);
			}
		}
		
		// Build the ordering
		while (!roots.isEmpty()) {
			Integer top = roots.pop();
			order.add(top);
			ArrayList<Integer> children = graph.get(top);
			for (int i = 0; i < children.size(); i++) {
				Integer rem = children.remove(i--);
				numIncoming.put(rem, numIncoming.get(rem) - 1);
				if (numIncoming.get(rem) == 0) {
					roots.push(rem);
				}
			}
		}
		
		// Count number of edges
		int edges = 0;
		for (Integer key: graph.keySet()) {
			edges += graph.get(key).size();
		}
		
		return edges > 0 ? false : true;
	}
	
	/*******************************************************************************************************************************
	 * Depth assignment in a graph. 
	 * 
	 * A starting node is nominated, after that each node is assigned a depth = 1 + max(depth of "parents")
	 * 
	 * O(n + m) time
	 */
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
	
	/*******************************************************************************************************************************
	 * Simple and fast Pair class
	 */
	public static class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A, B>> {
		public A a;
		public B b;
		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
		public int hashCode() {
			return a.hashCode() * 13 + b.hashCode();
		}
		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<A, B> op = (Pair<A, B>) other;
				return ((this.a == op.a || this.a.equals(op.a)) && (this.b == op.b || this.b.equals(op.b)));
			}
			return false;
		}
		public String toString() { 
			return "(" + a + ", " + b + ")"; 
		}
		@Override
		public int compareTo(Pair<A, B> o) {
			int compareFirst = this.a.compareTo(o.a);
			return compareFirst != 0 ? compareFirst : this.b.compareTo(o.b);
		}
	}
	
	/*******************************************************************************************************************************
	 * Standard way of reading in a graph
	 */
	public static void readGraph() {
		Random in = new Random(0);
		
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
		}
	}

	/*******************************************************************************************************************************
	 * Graph generator
	 */
	public static void generateTree(Random ra, int N, int C) {
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			g.add(new ArrayList<Integer>());
			cost.add(new ArrayList<Integer>());
		}
		for (int i = 1; i < N-1; i++) {
			int oldNode = ra.nextInt(i);
			int newNode = i;
			int c = ra.nextInt(C);
			g.get(oldNode).add(newNode);
			g.get(newNode).add(oldNode);
			cost.get(oldNode).add(c);
			cost.get(newNode).add(c);
		}
	}

	/**
	 * Recurrent edges are allowed.
	 */
	public static void generateGraph(Random ra, int N, int M, int C, 
			ArrayList<ArrayList<Integer>> g, ArrayList<HashSet<Integer>> gset, ArrayList<ArrayList<Integer>> cost) {
		/**
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		ArrayList<HashSet<Integer>> gset = new ArrayList<>();
		ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
		*/
		for (int i = 0; i < N; i++) {
			g.add(new ArrayList<Integer>());
			gset.add(new HashSet<Integer>());
			cost.add(new ArrayList<Integer>());
		}
		for (int i = 1; i < N-1; i++) {
			int oldNode = ra.nextInt(i);
			int newNode = i;
			int c = ra.nextInt(C);
			g.get(oldNode).add(newNode);
			g.get(newNode).add(oldNode);
			gset.get(oldNode).add(newNode);
			gset.get(newNode).add(oldNode);
			cost.get(oldNode).add(c);
			cost.get(newNode).add(c);
		}
		M = (int) Math.min(M, (N-1L)*N/2);
		for (int i = 0; i < M - (N - 2); i++) {
			int a = ra.nextInt(N);
			int b = ra.nextInt(N);
			while (a == b || gset.get(a).contains(b)) {
				a = ra.nextInt(N);
				b = ra.nextInt(N);
			}
			int c = ra.nextInt(C);
			g.get(a).add(b);
			g.get(b).add(a);
			gset.get(a).add(b);
			gset.get(b).add(a);
			cost.get(a).add(c);
			cost.get(b).add(c);
		}
	}
	
	public static int clearBit(int x, int i) {
		return (x & ~(1 << i));
	}
	
	public static int setBit(int x, int i) {
		return (x | (1 << i));
	}
	
	public static boolean getBit(int x, int i) {
		return (x & (1 << i)) != 0;
	}

	@Test
	public void CutVerticesTest() {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		int N = 9;
		for (int i = 0; i < N; i++) {
			graph.add(new ArrayList<Integer>());
		}
		graph.get(0).add(1);	graph.get(1).add(0);
		graph.get(1).add(2);	graph.get(2).add(1);
		graph.get(0).add(2);	graph.get(2).add(0);
		graph.get(3).add(4);	graph.get(4).add(3);
		graph.get(4).add(5);	graph.get(5).add(4);
		graph.get(5).add(6);	graph.get(6).add(5);
		graph.get(5).add(7);	graph.get(7).add(5);
		graph.get(5).add(8);	graph.get(8).add(5);
		graph.get(6).add(7);	graph.get(7).add(6);
		graph.get(6).add(8);	graph.get(8).add(6);
		graph.get(7).add(8);	graph.get(8).add(7);
		
		// Visually:
		// 0--2     4--5--6
		// | /      |  |\/|
		// |/       |  |/\|           
		// 1        3  7--8
		
		GraphAlgorithms.CutVertices cv = new GraphAlgorithms.CutVertices(graph);
		
		// Three biconnected components in the graph
		assertEquals(cv.biConnected[N-1], 2);
	}

	@Test
	public void CutVerticesTest2() {
		int numTests = 100;
		int maxSize = 100;
		Random rand = new Random(0);
		for (int test = 0; test < numTests; test++) {
			int N = rand.nextInt(maxSize) +2;
			int M = rand.nextInt(N*(N-1)/2) +1;
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<HashSet<Integer>> gset = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			generateGraph(rand, N, M, 1, g, gset, cost);
			
			int numEdges = M;
			int numNodes = N;
			
			CutVertices cv = new CutVertices(g);
			while (numEdges > 0 && numNodes > 0) {
				
				if (rand.nextBoolean()) {
					// delete random edge
					int edgeCount = 0;
					int delEdge = rand.nextInt(numEdges*2);
					int nodeA = -1;
					int nodeB = -1;
					for (int j = 0; j < g.size() && nodeA == -1; j++) {
						for (int i = 0; i < g.get(j).size() && nodeA == -1; i++) {
							if (edgeCount == delEdge) {
								nodeA = j;
								nodeB = g.get(j).get(i);
							}
							edgeCount++;
						}
					}
	
					g.get(nodeA).remove(Integer.valueOf(nodeB));
					g.get(nodeB).remove(Integer.valueOf(nodeA));
	
					CutVertices cv2 = new CutVertices(g);
					if (cv.cutEdge.get(nodeA).contains(nodeB)) {
						// components should increase
						assertEquals(cv.numComponents+1, cv2.numComponents); 
					} else {
						// components should stay the same
						assertEquals(cv.numComponents, cv2.numComponents);
					}
					numEdges--;
					cv = cv2;
					
				} else {
					// delete random node
					int nodeA = rand.nextInt(numNodes);
	
					for (int j = 0; j < g.size(); j++) {
						ArrayList<Integer> children = g.get(j);
						for (int i = 0; i < children.size(); i++) {
							while (children.contains(Integer.valueOf(nodeA))) {
								children.remove(Integer.valueOf(nodeA));
								g.get(nodeA).remove(Integer.valueOf(i));
								numEdges--;
							}
						}
					}
					g.remove(nodeA);
	
					// rename nodes
					for (int j = 0; j < g.size(); j++) {
						ArrayList<Integer> children = g.get(j);
						for (int i = 0; i < children.size(); i++) {
							if (children.get(i) >= nodeA) {
								children.set(i, children.get(i)-1);	
							}
						}
					}
					
					CutVertices cv2 = new CutVertices(g);
					if (cv.cutVertex[nodeA]) {
						// components should increase
						assertTrue(cv2.numComponents > cv.numComponents); 
					} else {
						// components should stay the same, unless the removed node is a singular component
						int compSize = 0;
						for (int i = 0; i < cv.component.length; i++) {
							if (cv.component[nodeA] == cv.component[i]) {
								compSize++;
							}
						}
						if (compSize == 1) {
							assertEquals(cv.numComponents - 1, cv2.numComponents);
						} else {
							assertEquals(cv.numComponents, cv2.numComponents);
						}
					}
					
					numNodes -= 1;
					cv = cv2;
				}
			}
		}
	}
	
	@Test
	public void maxBpmTest() {
		int n = 1000;
		Random ra = new Random(0);
		boolean[][] bpGraph = new boolean[n][n];
		for (int i = 0; i < bpGraph.length; i++) {
			for (int j = 0; j < bpGraph.length; j++) {
				if (ra.nextDouble() < 0.001) {
					bpGraph[i][j] = true;
				}
			}
		}

		assertEquals(GraphAlgorithms.maxBPM(bpGraph), 551);
	}
}
