package gen;
import static org.junit.Assert.*;

import java.util.*;

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
	public static int maxBPM(boolean[][] bpGraph) {
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
		boolean[] cutVertex;	// Marks cut vertices
		int[] low;				// Used as part of the algorithm
		int[] color;			// Tracks visited status
		int N;					// Size of the graph
		int numComponents;		// Number of components
		int[] componentSize;	// Component size
		ArrayList<ArrayList<Integer>> g;
		
		// A cut edge is an edge that when removed it creates more components than previously in the graph.
		// A graph can have cut vertices without cut edges, but not the other way around. Also know as a bridge.
		// More info: https://en.wikipedia.org/wiki/Bridge_(graph_theory)
		HashMap<Integer, HashSet<Integer>> cutEdge;
		
		public CutVertices(ArrayList<ArrayList<Integer>> graph) {
			N = graph.size();
			this.g = graph;
			depth = new int[N];
			parent = new int[N];
			low = new int[N];
			color = new int[N];
			component = new int[N];
			cutVertex = new boolean[N];
			cutEdge = new HashMap<>();
			for (int i = 0; i < N; i++) {
				cutEdge.put(i, new HashSet<Integer>());
			}
			
			for (int i = 0; i < N; i++) {
				if (color[i] == 0) {
					parent[i] = -1;
					component[i] = numComponents++;
					dfs(i, 0);
				}
			}
			
			componentSize = new int[numComponents];
			for (int i = 0; i < N; i++) {
				componentSize[component[i]]++;
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
	}

	/*******************************************************************************************************************************
	 * Block Cut Tree
	 * 
	 * A block is a connected graph which does not have any cut edge (AKA bridge). It can include any number of cut vertices,
	 * but they will always be on the "edge" of the block. Cut vertices will always be part of more than one block.
	 * 
	 * This method breaks up a graph into a tree consisting of one vertex per block and one vertex per cut vertex. 
	 * It's optimised for speed so it doesn't provide a nice block cut tree as output, only a parent array and a LCA class.
	 * Runtime is O(N+M) but it has a very large constant, so in practice worst case is 100k vertices and 200k edges in one second.
	 * 
	 * Wikipedia: A block or biconnected component is a maximal subgraph in which every two vertices or edges belong to a simple
	 * cycle. It may be a 2-vertex-connected subgraph, a bridge edge, or an isolated vertex. In a connected graph, the collection
	 * of blocks and the articulation points separating them form the vertices of a tree, the block-cut tree, whose edges connect
	 * blocks to the articulation points within those blocks.
	 * 
	 * More info on block cut trees:
	 * http://www.math.ucsd.edu/~jverstra/154-part4-2014.pdf
	 * http://compalg.inf.elte.hu/~tony/Oktatas/TDK/FINAL/Chap%205.PDF
	 * 
	 * Based on http://www.geeksforgeeks.org/biconnected-components/
	 */
	
	public static class BlockCutTree {
	    static boolean debug = false;
	    // 
		public final static boolean enableCutEdge = false;
	    
		private int N; 						// Number of vertices
		ArrayList<ArrayList<Integer>> g;
	 
	    // Count is number of biconnected components. time is
	    // used to find discovery times
	    int count = 0, time = 0;
	    
	    public int[] vertexToTreeNode;					// Maps vertex -> tree node
		ArrayList<ArrayList<Integer>> blockToVertex;	// Maps block -> vertex
		ArrayList<ArrayList<Integer>> vertexToBlock;	// Maps vertex -> blocks (in the case of CVs)
        int[] depth;
        int[] low;
        int[] parent;
        int[][] forestParent;				// Parent array per component
        int[][] forestLevel;				// Level array per component
    	int[][] st;							// Internal use
    	int stIdx;							// Internal use

		int[] biConnected;					// Marks each biconnected component (AKA block) 
		boolean[] cutVertex;				// Cut vertices
		int[] component;					// Marks each connected component (i.e. completely separate graph)
		int numComponents;					// Number of components
		int[] componentSize;				// Component size

		HashMap<Integer, HashSet<Integer>> cutEdge;
	    
	    // G is not copied, but also not modified
	    public BlockCutTree(ArrayList<ArrayList<Integer>> g) {
	    	this.g = g;
	        N = g.size();
	        
			this.blockToVertex = new ArrayList<>();
			this.vertexToBlock = new ArrayList<>();
			vertexToTreeNode = new int[N];
			cutEdge = new HashMap<>();
	        depth = new int[N];
	        low = new int[N];
	        parent = new int[N];
	        cutVertex = new boolean[N];
	        biConnected = new int[N];
	        component = new int[N];
	    	st = new int[N*2][2];
	    	stIdx = 0;

	        Arrays.fill(vertexToTreeNode, -1);
	        Arrays.fill(depth, -1);
	        Arrays.fill(low, -1);
	        Arrays.fill(parent, -1);
	        Arrays.fill(biConnected, -1);

	        for (int i = 0; i < N; i++) {
	        	this.blockToVertex.add(new ArrayList<Integer>());
	        	this.vertexToBlock.add(new ArrayList<Integer>());
	        	if (enableCutEdge) {
	        		cutEdge.put(i, new HashSet<Integer>());
	        	}
	        }
	        
			if (BlockCutTree.debug) {
				System.out.println();
				System.out.println(g.size());
				for (int i = 0; i < g.size(); i++) {
					System.out.println(i);
				}
				for (int i = 0; i < g.size(); i++) {
					for (Integer j: g.get(i)) {
						if (j > i) {
							System.out.println(i + " " + j);
						}
					}
				}
			}
			
			BCC();
	    }
			 
	    // A recursive function that finds and prints strongly connected
	    // components using DFS traversal
	    // u --> The vertex to be visited next
	    // disc[] --> Stores discovery times of visited vertices
	    // low[] -- >> earliest visited vertex (the vertex with minimum
	    //             discovery time) that can be reached from subtree
	    //             rooted with current vertex
	    // *st -- >> To store visited edges
	    void BCCUtil(int top) {
	        // Initialize discovery time and low value
	        depth[top] = low[top] = ++time;
	        int children = 0;
	        boolean singleVertex = true;
	        boolean articulation = false;

	        for (Integer child: g.get(top)) {
	        	singleVertex = false;
	 
	            // If v is not visited yet, then recur for it
	            if (depth[child] == -1) {
	                children++;
	                parent[child] = top;
	                component[child] = component[top];
	 
	                // store the edge in stack
	                st[stIdx][0] = top;
	                st[stIdx][1] = child;
	                stIdx++;
	                BCCUtil(child);
	                
	                if (low[child] >= depth[top]) {
	        			articulation = true;
	        		}
		        	if (enableCutEdge) {
		        		if (low[child] > depth[top]) {
		        			cutEdge.get(top).add(child);
		        			cutEdge.get(child).add(top);
		        		}
		        	}
	 
	                // Check if the subtree rooted at 'child' has a connection to one of the ancestors of 'top'
	                // Case 1 -- per Strongly Connected Components Article
	                if (low[top] > low[child])
	                    low[top] = low[child];
	 
	                // If top is an articulation point, pop all edges from stack till top -- child
	                if ((depth[top] == 1 && children > 1) || (depth[top] > 1 && low[child] >= depth[top])) {
	                	int[] last = st[stIdx-1];
	                    while (last[0] != top || last[1] != child) {
	                    	vertexToBlock.get(last[0]).add(count);
	                    	vertexToBlock.get(last[1]).add(count);
	                    	biConnected[last[0]] = count;
	                    	biConnected[last[1]] = count;
	                    	stIdx--;
	                        last = st[stIdx-1];
	                    }
                    	vertexToBlock.get(last[0]).add(count);
                    	vertexToBlock.get(last[1]).add(count);
                    	biConnected[last[0]] = count;
                    	biConnected[last[1]] = count;
                    	stIdx--;
	 
	                    count++;
	                }
	            }
	 
	            // Update low value of 'top' only of 'child' is still in stack (i.e. it's a back edge, not cross edge).
	            // Case 2 -- per Strongly Connected Components Article
	            else if (child != parent[top] && depth[child] < low[top]) {
	                if (low[top] > depth[child])
	                    low[top] = depth[child];

	                st[stIdx][0] = top;
	                st[stIdx][1] = child;
	                stIdx++;
	            }
	        }

    		if ((parent[top] != -1 && articulation) || (parent[top] == -1 && children > 1)) {
    			cutVertex[top] = true;
    		}
	        if (singleVertex) {
            	vertexToBlock.get(top).add(count);
            	biConnected[top] = count;
                count++;
	        }
	    }
	 
		// The function to do DFS traversal. It uses BCCUtil()
	    private void BCC() {
	        for (int i = 0; i < N; i++) {
	            if (depth[i] == -1) {
					component[i] = numComponents++;
	                BCCUtil(i);
	            }
	            boolean empty = true;
	 
	            // If stack is not empty, pop all edges from stack
	            while (stIdx > 0) {
	            	empty = false;                	
                	vertexToBlock.get(st[stIdx-1][0]).add(count);
                	vertexToBlock.get(st[stIdx-1][1]).add(count);
                	biConnected[st[stIdx-1][0]] = count;
                	biConnected[st[stIdx-1][1]] = count;
                	stIdx--;
	            }
	            if (!empty) {
	                count++;
	            }
	        }
	        
	        componentSize = new int[numComponents];
			for (int i = 0; i < N; i++) {
				componentSize[component[i]]++;
				blockToVertex.get(biConnected[i]).add(i);
				if (cutVertex[i]) {
					biConnected[i] = -1;
				}
			}
	        buildTree();
	    }
	    
	    public void buildTree() {
//	        // build BC tree
	        boolean[] done = new boolean[N];
	        forestParent = new int[numComponents][];
	        forestLevel = new int[numComponents][];
	        for (int i = 0; i < N; i++) {
	        	if (!done[i] && cutVertex[i]) {
	        		int treeId = 0;
	        		forestParent[component[i]] = new int[componentSize[component[i]] * 2];
	        		int[] treeParent = forestParent[component[i]];
	        		forestLevel[component[i]] = new int[componentSize[component[i]] * 2];
	        		int[] treeLevel = forestLevel[component[i]];
	        		
	        		treeParent[treeId] = -1;
	        		treeLevel[treeId] = 0;
	        		vertexToTreeNode[i] = treeId++;
	        		
	        		// DFS while alternating between CV and block vertices. 
					Stack<Integer> q = new Stack<>();
					int top = i;
					q.add(top);
					HashSet<Integer> memVisited = new HashSet<>();
					HashSet<Integer> cVisited = new HashSet<>();
					HashSet<Integer> bVisited = new HashSet<>();
					while (!q.isEmpty()) {
						top = q.pop();
						if (cVisited.contains(top)) {
							continue;
						}
						cVisited.add(top);
						done[top] = true;

						ArrayList<Integer> comps = vertexToBlock.get(top);
						for (Integer comp: comps) {
							if (bVisited.contains(comp)) {
								continue;
							}
							
							int blockChildId = treeId++;
							bVisited.add(comp);
							treeParent[blockChildId] = vertexToTreeNode[top];
							treeLevel[blockChildId] = treeLevel[vertexToTreeNode[top]] +1;

							for (Integer member: blockToVertex.get(comp)) {
								if (vertexToTreeNode[member] == -1) {
									vertexToTreeNode[member] = blockChildId;
								}
								done[member] = true;
								
								if (cutVertex[member] && member != top) {
									if (!memVisited.contains(member)) {
										memVisited.add(member);
										int newId = treeId++;
										vertexToTreeNode[member] = newId;
										treeParent[newId] = blockChildId;
										treeLevel[newId] = treeLevel[blockChildId] +1;
										q.push(member);
									}
								}
							}
						}
					}
					forestParent[component[i]] = Arrays.copyOf(forestParent[component[i]], treeId);
					forestLevel[component[i]] = Arrays.copyOf(forestLevel[component[i]], treeId);
	        	}
			}

	        // blocks with no cvs
	        for (int i = 0; i < N; i++) {
	        	if (!done[i]) {
	        		int treeId = 0;
	        		
	        		// there will be only one block/component here. 
					int comp = biConnected[i];
					int rootId = treeId++;
					
	        		forestParent[component[i]] = new int[componentSize[component[i]] * 2];
	        		int[] treeParent = forestParent[component[i]];
	        		forestLevel[component[i]] = new int[componentSize[component[i]] * 2];
	        		int[] treeLevel = forestLevel[component[i]];
	        		
	    	        Arrays.fill(treeParent, -1);
	    	        Arrays.fill(treeLevel, -1);
	        		
	        		treeParent[treeId] = -1;
	        		treeLevel[treeId] = 0;
					
					for (Integer member: blockToVertex.get(comp)) {
						if (vertexToTreeNode[member] == -1) {
							vertexToTreeNode[member] = rootId;
						}
						done[member] = true;
					}
					forestParent[component[i]] = Arrays.copyOf(forestParent[component[i]], treeId);
					forestLevel[component[i]] = Arrays.copyOf(forestLevel[component[i]], treeId);
	        	}
	        }
	        System.currentTimeMillis();
	    }
	    
	    /**
	     * Utility method to get an LCA tree for each component in the tree.
	     */
	    public LCA[] getLCA() {
			LCA[] lca = new LCA[numComponents];
			for (int i = 0; i < lca.length; i++) {
				lca[i] = new LCA(forestParent[i].length, forestParent[i], forestLevel[i]);
			}
			return lca;
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
	 * Cycle detection in an undirected graph.
	 * based on http://www.geeksforgeeks.org/detect-cycle-undirected-graph/
	 */

	// TODO: there is a bug here. It thinks that https://www.codechef.com/JUNE17/problems/SBTR is not cyclic
	// Returns true if the graph contains a cycle, else false.
	public int isCyclic(ArrayList<ArrayList<Integer>> g) {
		// Mark all the vertices as not visited and not part of recursion stack
		int N = g.size();
		boolean[] visited = new boolean[N];
		int[] parent = new int[N];

		// DFS to assign parents
		for (int u = 0; u < N; u++) {
			if (!visited[u]) {
				parent[u] = -1;

				int top = u;
				Stack<Integer> s = new Stack<>();
				s.add(top);
				
				while (!s.isEmpty()) {
					top = s.pop();
					if (!visited[top]) {
						visited[top] = true;
						ArrayList<Integer> adj = g.get(top);
						for (Integer i: adj) {
							if (!visited[i]) {
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
			if (!visited[u]) {
				parent[u] = -1;

				int[] top = {u, -1};
				Stack<int[]> s = new Stack<>();
				s.add(top);
				
				while (!s.isEmpty()) {
					top = s.pop();
					if (!visited[top[0]]) {
						visited[top[0]] = true;
						ArrayList<Integer> adj = g.get(top[0]);
						for (Integer i: adj) {
							s.add(new int[]{i, top[0]});
						}
					} else if (parent[top[0]] != top[1]) {
						// If an adjacent is visited and not parent of current vertex,
						// then there is a cycle.
						return top[0];
					}
				}
			}
		}
		return -1;
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
		public int[] parent;
		public int[] level;
		int[][] ancestor;

		public LCA(int N, Tree[] tree) {
			this.parent = new int[N];
			this.level = new int[N];
			int i;
			
			// Set up the parent array
			parent[0] = - 1;
			for (i = 1; i < N; i++) {
				parent[i] = tree[i].parent.id;
			}
			
			// Set up the level array
			for (i = 0; i < N; i++) {
				level[i] = tree[i].level;
			}
			init(N);
		}
		
		public LCA(int N, int[] parent, int[] level) {
			this.parent = parent;
			this.level = level;
			init(N);
		}
		
		public void init(int N) {
			this.N = N;
			this.log2N = 32 - Integer.numberOfLeadingZeros(N-1) +1;
			this.ancestor = new int[N][log2N];
			int i, j;
			
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
		
		/**
		 * Return the length of the path from p to q.
		 * len(p, p) == 0
		 * len(p, q) == number of steps, or number of nodes between p and q, +1  
		 */
		public int pathLength(int p, int q) {
			int lca = query(p, q);
			// (p - LCA) + (q - LCA)
			return level[p] + level[q] - 2*level[lca];
		}
		
		private int pathLength(int p, int q, int lca) {
			return level[p] + level[q] - 2*level[lca];
		}
		
		/**
		 * Return the (zero based) n'th node on the path from p to q.
		 * nodeAtPosition(p, q, 0) == p and nodeAtPosition(p, q, pathLength(p, q)) == q.
		 * 
		 * A similar technique can be used to check if node x is on the path, by checking the node at x's depth. 
		 */
		public int nodeAtPosition(int p, int q, int n) {
			int lca = query(p, q);
			if (n > pathLength(p, q, lca)) {
				return -1;
			}
			
			// Determine if n is on the path from p -> lca or lca.child -> q
			if (n <= pathLength(p, lca, lca)) {
				return nthAncestor(p, n);
			} else {
				n = pathLength(lca, q, lca) - (n - (level[p] - level[lca]));
				return nthAncestor(q, n);
			}
		}
		
		/**
		 * Return the (zero based) n'th ancestor of p. nthAncestor(p, 0) == p. 
		 */
		public int nthAncestor(int p, int n) {
			int log, i;
			
			if (n > level[p]) {
				return -1;
			}
			int targetLevel = level[p] - n; 
			
			// We compute the value of [log(L[p)]
			for (log = 1; 1 << log <= level[p]; log++);			
			log--;

			// We find the ancestor of node p situated the target level.
			for (i = log; i >= 0; i--) {
				if (level[p] - (1 << i) >= targetLevel) {
					p = ancestor[p][i];
				}
			}
			return p;
		}
	}
	
	/*******************************************************************************************************************************
	 * General use Tree class
	 */
	public static class Tree {
		public Tree parent;
		public ArrayList<Tree> children;
		public int id;
		public int level;
		
		public Tree(int id, int level) {
			children = new ArrayList<>();
			this.id = id;
			this.level = level;
			parent = null;
		}

		public Tree addChild(int id) {
			Tree child = new Tree(id, this.level +1);
			children.add(child);
			child.parent = this;
			return child;
		}

		public String toString() {
			String me = id + ": (" ;
			boolean first = true;
			for (Tree child: this.children) {
				if (!first) {
					me += ", ";
				}
				me += child.id;
				first = false;
			}
			return me + ")";
		}
	}
	
	public static void printTree(ArrayList<ArrayList<Integer>> g) {
		for (int i = 0; i < g.size(); i++) {
			System.out.println(i);
		}
		for (int i = 0; i < g.size(); i++) {
			for (Integer j: g.get(i)) {
				if (j > i) {
					System.out.println(i + " " + j);
				}
			}
		}
	}
	
	public static void printTree(Tree[] g) {
		for (int i = 0; i < g.length; i++) {
			System.out.println(g[i].id);
		}
		for (int i = 0; i < g.length; i++) {
			for (Tree j: g[i].children) {
				if (j.id > g[i].id) {
					System.out.println(g[i].id + " " + j.id);
				}
			}
		}
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
	 * Graph builder
	 */
	public static class GraphBuilder {
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		public GraphBuilder() {
			g = new ArrayList<>();
		}
		void addEdge(int a, int b) {
			while (g.size() <= a || g.size() <= b) {
				g.add(new ArrayList<Integer>());
			}
			g.get(a).add(b);
			g.get(b).add(a);
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

	/*******************************************************************************************************************************
	 * Another graph generator
	 */
	public static Tree[] generateTree(Random ra, int N) {
		Tree[] roots = new Tree[N];
		roots[0] = new Tree(0, 0);
		for (int i = 1; i < N; i++) {
			int parent = ra.nextInt(i);
			int child = i;
			roots[child] = roots[parent].addChild(child);
		}
		return roots;
	}
	
	/*******************************************************************************************************************************
	 * Convert an adjacency list to a Tree
	 */
	public static Tree[] adjacencyListToTree(ArrayList<ArrayList<Integer>> g) {
		int N = g.size();
		Tree[] roots = new Tree[N];
		roots[0] = new Tree(0, 0);
		boolean[] visited = new boolean[N];
		int top = 0;
		Queue<Integer> q = new LinkedList<>();
		q.add(top);
		while (!q.isEmpty()) {
			top = q.poll();
			if (!visited[top]) {
				visited[top] = true;
				for (Integer child: g.get(top)) {
					if (!visited[child]) {
						roots[child] = roots[top].addChild(child);
						q.add(child);
					}
				}
			}
		}
		return roots;
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

	/**
	 * Recurrent edges are allowed.
	 */
	public static void generateFastGraph(Random ra, int N, int M, ArrayList<ArrayList<Integer>> g) {
		/**
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		ArrayList<HashSet<Integer>> gset = new ArrayList<>();
		ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
		*/
		ArrayList<HashSet<Integer>> gset = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			g.add(new ArrayList<Integer>());
			gset.add(new HashSet<Integer>());
		}
		for (int i = 1; i < N-1; i++) {
			int oldNode = ra.nextInt(i);
			int newNode = i;
			g.get(oldNode).add(newNode);
			g.get(newNode).add(oldNode);
		}
		M = (int) Math.min(M, (N-1L)*N/2);
		for (int i = 0; i < M - (N - 2); i++) {
			int a = ra.nextInt(N);
			int b = ra.nextInt(N);
			while (a == b || gset.get(a).contains(b)) {
				a = ra.nextInt(N);
				b = ra.nextInt(N);
			}
			g.get(a).add(b);
			g.get(b).add(a);
			gset.get(a).add(b);
			gset.get(b).add(a);
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
	public void CutVerticesPerfTest() throws InterruptedException {
		Thread t = new Thread(null, new Runnable() { public void run() { new GraphAlgorithms()._CutVerticesPerfTest(); } }, "1", 1 << 26);
		t.start();
		t.join();
	}
	
	public void _CutVerticesPerfTest() {
		// Given distinct vertices a, b, c: can you go from a -> c and b -> c without visiting any node except c more than once?
		int numTests = 10;
		int maxSize = 100000;
		Random rand = new Random(0);

		ArrayList<ArrayList<ArrayList<Integer>>> gs = new ArrayList<>();
		for (int test = 0; test < numTests; test++) {
			long time = System.currentTimeMillis();
			int N = maxSize;
			int M = 110000;
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			generateFastGraph(rand, N, M, g);
			gs.add(g);
			time = System.currentTimeMillis() - time;
			System.out.println(time);
			
		}
		System.out.println("done with graphs");
		
		for (int test = 0; test < numTests; test++) {
			long time = System.currentTimeMillis();
			CutVertices cv = new CutVertices(gs.get(test));
			time = System.currentTimeMillis() - time;
			System.out.println("time: " + time);
		}
	}
	
	@Test
	public void CycleDetectionTest() {
		int numTests = 200;
		int maxSize = 100;
		Random rand = new Random(0);
		for (int test = 0; test < numTests; test++) {
			int N = rand.nextInt(maxSize) +2;
			int M = rand.nextInt(N*(N-1)/2) +1;
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<HashSet<Integer>> gset = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			generateGraph(rand, N, M, 1, g, gset, cost);
			
			int node = isCyclic(g);
			while (node >= 0) {
				// delete the node that was part of a cycle
				for (int j = 0; j < g.size(); j++) {
					ArrayList<Integer> children = g.get(j);
					for (int i = 0; i < children.size(); i++) {
						while (children.contains(Integer.valueOf(node))) {
							children.remove(Integer.valueOf(node));
							g.get(node).remove(Integer.valueOf(i));
						}
					}
				}
				g.remove(node);

				// rename nodes
				for (int j = 0; j < g.size(); j++) {
					ArrayList<Integer> children = g.get(j);
					for (int i = 0; i < children.size(); i++) {
						if (children.get(i) >= node) {
							children.set(i, children.get(i)-1);	
						}
					}
				}
				node = isCyclic(g);
			}
			
			// In a graph with no cycles, all the edges will be cut-edges, AKA a tree.
			CutVertices cv = new CutVertices(g);
			for (int j = 0; j < g.size(); j++) {
				ArrayList<Integer> children = g.get(j);
				for (int i = 0; i < children.size(); i++) {
					assertTrue(cv.cutEdge.get(j).contains(children.get(i)));
				}
			}
		}
	}

	@Test
	public void BigCycleDetectionTest() {
		int N = 1000000;	
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			g.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < N; i++) {
			int a = i;
			int b = (i+1) % N;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		
		int node = isCyclic(g);
		assertTrue(node >= 0);
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
	
	@Test
	public void testLCA() {
		int numTests = 2000;
		int maxSize = 200;
		Random r = new Random(0);
		for (int test = 0; test < numTests; test++) {
			int N = r.nextInt(maxSize) + 1;
			Tree[] tree = generateTree(r, N);
			LCA lca = new LCA(N, tree);
			for (int query = 0; query < numTests; query++) {
				int a = r.nextInt(N);
				int b = r.nextInt(N);
				int actual = lca.query(a, b);
				
				if (tree[a].level < tree[b].level) {
					int tmp = a;
					a = b;
					b = tmp;
				}
				Tree pa = tree[a];
				Tree pb = tree[b];
				while (pa.level > pb.level) {
					pa = pa.parent;
				}
				
				while (true) {
					if (pa.id == pb.id) {
						break;
					}
					pa = pa.parent;
					pb = pb.parent;
				}

				int expected = pa.id;
				assertEquals(expected, actual);
			}
		}
	}

	@Test
	public void testLCAnthAncestor() {
		int numTests = 1000;
		int maxSize = 1000;
		Random r = new Random(0);
		for (int test = 0; test < numTests; test++) {
			int N = r.nextInt(maxSize) + 1;
			Tree[] tree = generateTree(r, N);
			LCA lca = new LCA(N, tree);
			for (int query = 0; query < numTests; query++) {
				int a = r.nextInt(N);
				int n = r.nextInt(lca.level[a] + 1);
				int actual = lca.nthAncestor(a, n);
				
				Tree pa = tree[a];
				for (int i = 0; i < n; i++) {
					pa = pa.parent;
				}
				int expected = pa.id;
				assertEquals(expected, actual);
			}
		}
	}

	@Test
	public void testLCAnthNodeOnPath() {
		int numTests = 1000;
		int maxSize = 1000;
		Random r = new Random(0);
		for (int test = 0; test < numTests; test++) {
			int N = r.nextInt(maxSize) + 1;
			Tree[] tree = generateTree(r, N);
			
			LCA lca = new LCA(N, tree);
			for (int query = 0; query < numTests; query++) {
				int a = r.nextInt(N);
				int b = r.nextInt(N);
				int n = lca.pathLength(a, b);
				
				// cheat a bit here (LCA is tested elsewhere)
				int ancestor = lca.query(a, b);
				
				int[] nodes = new int[n+1];
				Tree pa = tree[a];
				int count = 0;
				nodes[count] = pa.id;
				while (pa.id != ancestor && count < n) {
					pa = pa.parent;
					count++;
					if (count < n) {
						nodes[count] = pa.id;
					}
				}
				
				int back = n;
				pa = tree[b];
				nodes[back] = pa.id;
				while (pa.id != ancestor && back > count) {
					nodes[back] = pa.id;
					pa = pa.parent;
					back--;
					nodes[back] = pa.id;
				}
				
				for (int i = 0; i <= n; i++) {
					int actual = lca.nodeAtPosition(a, b, i);
					int expected = nodes[i];
					if (actual != expected) {
						printTree(tree);
						lca.nodeAtPosition(a, b, i);
					}
					assertEquals(expected, actual);
				}
			}
		}
	}
	
	@Test
	public void testLCAPerformance() {
		int numTests = 7;
		int size = 100000;
		Random r = new Random(0);
		for (int test = 0; test < numTests; test++) {
			int N = size;
			Tree[] tree = generateTree(r, N);
			
			long time = System.currentTimeMillis();
			LCA lca = new LCA(N, tree);
			for (int query = 0; query < size; query++) {
				int a = r.nextInt(N);
				int b = r.nextInt(N);
				lca.query(a, b);
			}
			time = System.currentTimeMillis() - time;
			System.out.println("Time: "+ time);
		}
	}
	
	@Test
	public void BlockCutTreeTest() {
		// Given distinct vertices a, b, c: can you go from a -> c and b -> c without visiting any node except c more than once?
		// uses a very slow method to verify (iterating through all permutations)
		
		int numTests = 50;
		int maxSize = 8;
		Random rand = new Random(0);
		for (int test = 0; test < numTests; test++) {
			int N = rand.nextInt(maxSize) +2;
			//int N = maxSize;
			int M = rand.nextInt(N*(N-1)/2) +1;
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<HashSet<Integer>> gset = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			generateGraph(rand, N, M, 1, g, gset, cost);
			
			BlockCutTree bc = new BlockCutTree(g);
			LCA[] lca = bc.getLCA();
			for (int a = 0; a < N; a++) {
				for (int b = 0; b < N; b++) {
					for (int c = 0; c < N; c++) {
						if (bc.component[a] == bc.component[b]
								&& bc.component[a] == bc.component[c]
								&& a != b && a != c && b != c){
							boolean correct = uniquePaths(g, a, b, c);
							boolean mine = uniquePaths(g, a, b, c, bc, lca[bc.component[a]]);
							assertEquals(correct, mine);
						}
					}
				}
			}
		}
	}

	@Test
	public void BlockCutTreePerfTest() throws InterruptedException {
		Thread t = new Thread(null, new Runnable() { public void run() { new GraphAlgorithms()._BlockCutTreePerfTest(); } }, "1", 1 << 26);
		t.start();
		t.join();
	}

	public void _BlockCutTreePerfTest() {
		// Given distinct vertices a, b, c: can you go from a -> c and b -> c without visiting any node except c more than once?
		int numTests = 1;
		int maxSize = 200000;
		Random rand = new Random(0);

		ArrayList<ArrayList<ArrayList<Integer>>> gs = new ArrayList<>();
		for (int test = 0; test < numTests; test++) {
			long time = System.currentTimeMillis();
			int N = maxSize;
			int M = 350000;
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			generateFastGraph(rand, N, M, g);
			gs.add(g);
			time = System.currentTimeMillis() - time;
			System.out.println(time);	
		}
		
		System.out.println("done with graphs");
		
		for (int test = 0; test < numTests; test++) {
			long t1 = System.currentTimeMillis();
			ArrayList<ArrayList<Integer>> g = gs.get(test);
			int N = g.size();
			long time = System.currentTimeMillis();
			BlockCutTree bc = new BlockCutTree(g);
			t1 = System.currentTimeMillis() - t1;
			
			long t3 = System.currentTimeMillis();
			LCA[] flca = new LCA[bc.numComponents];
			for (int i = 0; i < flca.length; i++) {
				flca[i] = new LCA(bc.forestParent[i].length, bc.forestParent[i], bc.forestLevel[i]);
			}
			for (int q = 0; q < N; q++) {
				int a = rand.nextInt(N);
				int b = rand.nextInt(N);
				int c = rand.nextInt(N);
				while (a == b) {
					b = rand.nextInt(N);
				}
				while (a == c || b == c) {
					c = rand.nextInt(N);
				}
				if (bc.component[a] == bc.component[b]
					&& bc.component[a] == bc.component[c] 
					&& a != b && a != c && b != c){
					
					uniquePaths(g, a, b, c, bc, flca[bc.component[a]]);
				}
			}
			t3 = System.currentTimeMillis() - t3;
			
			time = System.currentTimeMillis() - time;
			System.out.println("time: " + time + "\t other: " + (t3) + "\t build: " + t1 + "\t " + (100.0*t1/time) + "%");
		}
	}
	
	public <E> boolean hasIntersection(HashSet<E> a, HashSet<E> b) {
		@SuppressWarnings("unchecked")
		HashSet<E> x = (HashSet<E>) a.clone();
		x.retainAll(b);
		return !x.isEmpty();
	}
	
	public boolean isInSubTree(int p, int newRoot, LCA lca) {
		return lca.query(p, newRoot) == newRoot; 
	}
	
	public boolean uniquePaths(ArrayList<ArrayList<Integer>> g, int a, int b, int c, BlockCutTree bcTree, LCA lca) {
		int treeNodeA = bcTree.vertexToTreeNode[a];
		int treeNodeB = bcTree.vertexToTreeNode[b];
		int treeNodeC = bcTree.vertexToTreeNode[c];
		return uniquePathsTreeNodes(g, treeNodeA, treeNodeB, treeNodeC, c, bcTree, lca);
	}
	
	public boolean uniquePathsTreeNodes(ArrayList<ArrayList<Integer>> g, int treeNodeA, int treeNodeB, int treeNodeC, int c,
			BlockCutTree bcTree, LCA lca) {
		
		boolean result = false;
		if (isInSubTree(treeNodeA, treeNodeC, lca)) {
			if (treeNodeA == treeNodeC) {
				result = true;
			} else {
				int childOfCtoA = lca.nodeAtPosition(treeNodeC, treeNodeA, 1);
				result = !isInSubTree(treeNodeB, childOfCtoA, lca);
			}
		} else {
			result = isInSubTree(treeNodeB, treeNodeC, lca);
		}
		if (!result) {
			// special case: C is a CV on the edge of a block and A and B can get in via other routes
			if (bcTree.cutVertex[c]) {
				int childOfCtoA = lca.nodeAtPosition(treeNodeC, treeNodeA, 1);
				int childOfCtoB = lca.nodeAtPosition(treeNodeC, treeNodeB, 1);
				if (childOfCtoA == childOfCtoB) {
					treeNodeC = childOfCtoA;
					if (isInSubTree(treeNodeA, treeNodeC, lca)) {
						if (treeNodeA == treeNodeC) {
							result = true;
						} else {
							childOfCtoA = lca.nodeAtPosition(treeNodeC, treeNodeA, 1);
							result = !isInSubTree(treeNodeB, childOfCtoA, lca);
						}
					} else {
						result = isInSubTree(treeNodeB, treeNodeC, lca);
					}
				}
			}
		}
		return result;
	}
	
	public boolean uniquePaths(ArrayList<ArrayList<Integer>> g, int a, int b, int c) {
		// can you go from a -> c and b -> c without visiting any node except c more than once?

		int len = g.size();
		int N = 1 << len;
		for (int j = 0; j < N; j++) {
			boolean[] active = new boolean[len];
			for (int i = 0; i < len; i++) {
				if (((1 << i) & j) != 0) {
					active[i] = true;
				}
			}
			boolean[] visited = new boolean[len];
			int top = a;
			Queue<Integer> q = new LinkedList<>();
			q.add(top);
			boolean foundA = false;
			while (!q.isEmpty()) {
				top = q.poll();
				if (!visited[top] && (active[top] || top == c)) {
					visited[top] = true;
					if (top == c) {
						foundA = true;
						break;
					}
					for (Integer adj: g.get(top)) {
						q.add(adj);
					}
				}
			}
			
			visited = new boolean[N];
			top = b;
			q = new LinkedList<>();
			q.add(top);
			boolean foundB = false;
			while (!q.isEmpty()) {
				top = q.poll();
				if (!visited[top] && (!active[top] || top == c)) {
					visited[top] = true;
					if (top == c) {
						foundB = true;
						break;
					}
					for (Integer adj: g.get(top)) {
						q.add(adj);
					}
				}
			}
			
			if (foundA && foundB) {
				return true;
			}
		}
		return false;
	}

	public HashSet<Integer> getCVs(ArrayList<ArrayList<Integer>> g, int a, int b, CutVertices cv) {
		// Djikstra
		// shortest path, but try to avoid cvs
		PriorityQueue<int[]> q = new PriorityQueue<int[]>(10, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
//				int comp = Integer.compare(o1[1], o2[1]); 
//				if (comp == 0) {
//					return Integer.compare(o2[3], o1[3]);
//				}
//				return comp;
				int comp = Integer.compare(o1[1] + 10000 * o1[3], o2[1] + 10000 * o2[3]);
				return comp;
			}
		});
		// position, distance, parent, number of cvs
		int[] top = new int[]{a, 0, -1, 0};
		if (cv.cutVertex[top[0]]) {
			top[3]++;
		}
		q.add(top);
		HashSet<Integer> visited = new HashSet<>();
		int[] parents = new int[g.size()];
		Arrays.fill(parents, -1);
		while (!q.isEmpty()) {
			top = q.poll();
			if (visited.contains(top[0])) {
				continue;
			}
			visited.add(top[0]);
			parents[top[0]] = top[2];
			if (top[0] == b) {
				break;
			}
			ArrayList<Integer> adj = g.get(top[0]);
			for (Integer i: adj) {
				if (!visited.contains(i)) {
					int numCvs = top[3];
					if (cv.cutVertex[i]) {
						numCvs++;
					}
					q.add(new int[]{i, top[1]+1, top[0], numCvs});
				}
			}
		}
		int current = b;
		HashSet<Integer> cvs = new HashSet<>();
		while (current != a) {
			if (cv.cutVertex[current]) {
				cvs.add(current);
			}
			current = parents[current]; 
		}
		return cvs;
	}
	
	@Test
	public void CCTest() {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		int N = 6;
		for (int i = 0; i < N; i++) {
			graph.add(new ArrayList<Integer>());
		}
		graph.get(0).add(1);	graph.get(1).add(0);
		graph.get(1).add(2);	graph.get(2).add(1);
		graph.get(2).add(3);	graph.get(3).add(2);
		graph.get(3).add(4);	graph.get(4).add(3);
		graph.get(3).add(5);	graph.get(5).add(3);
		graph.get(5).add(1);	graph.get(1).add(5);
		
		CutVertices cv = new CutVertices(graph);
	}

	@Test
	public void CC2Test() {
		GraphBuilder g = new GraphBuilder();
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(5, 1);
		BlockCutTree bct = new BlockCutTree(g.g);
		assertEquals(bct.count, 3);
		
		g = new GraphBuilder();
		g.addEdge(1, 0);
		g.addEdge(1, 2);
		g.addEdge(1, 6);
		g.addEdge(2, 3);
		g.addEdge(4, 2);
		g.addEdge(4, 5);
		g.addEdge(4, 6);
		g.addEdge(6, 7);
		bct = new BlockCutTree(g.g);
		assertEquals(bct.count, 5);

		g = new GraphBuilder();
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(4, 5);
		bct = new BlockCutTree(g.g);
		assertEquals(bct.count, 5);
		
		g = new GraphBuilder();
		g.addEdge(2, 0);
		g.addEdge(2, 1);
		g.addEdge(2, 3);
		g.addEdge(2, 4);
		g.addEdge(2, 5);
		g.addEdge(2, 6);
		g.addEdge(1, 0);
		g.addEdge(3, 4);
		g.addEdge(5, 6);
		bct = new BlockCutTree(g.g);
		assertEquals(bct.count, 3);
		
		g = new GraphBuilder();
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		bct = new BlockCutTree(g.g);
		assertEquals(bct.count, 1);
	}

	@Test
	public void CC3Test() {
		GraphBuilder gb = new GraphBuilder();
		gb.addEdge(1, 0);
		gb.addEdge(1, 2);
		gb.addEdge(1, 6);
		gb.addEdge(2, 3);
		gb.addEdge(4, 2);
		gb.addEdge(4, 5);
		gb.addEdge(4, 6);
		gb.addEdge(6, 7);
		BlockCutTree g = new BlockCutTree(gb.g);
		assertEquals(g.count, 5);
	}

	public static void main(String[] args) {
//		new GraphAlgorithms().BlockCutTreeTest();
		
		// increase stack size
		new Thread(null, new Runnable() {
            public void run() {
            	GraphAlgorithms g = new GraphAlgorithms();
            	g._BlockCutTreePerfTest();
//            	g.BlockCutTreeTestFast();
            }
        }, "1", 1 << 26).start();
	}
}
