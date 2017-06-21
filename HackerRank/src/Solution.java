import java.io.*;
import java.util.*;


/**
 * WeekofCode33
 */

public class Solution {
    public static InputReader in;
    public static PrintWriter out;
    public static boolean DEBUG = false;

    public static void main(String[] args) {
		// increase stack size
		new Thread(null, new Runnable() {
            public void run() {
            	new Solution().run();
            }
        }, "1", 1 << 26).start();
    }
    
    public void run() {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, false);

        bonnieAndClyde();
//        testPalindromicTable();
//        palindromicTable();
        //transformToPalindrome();
        //twinArrays();
        //patternCount();

        out.close();
    }

    public static void bonnieAndClyde() {
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        
        ArrayList<ArrayList<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Integer>());
		}
        for (int i = 0; i < m; i++) {
			int u = in.nextInt() -1;
			int v = in.nextInt() -1;
			g.get(u).add(v);
			g.get(v).add(u);
		}
        int[][] qu = new int[q][];
        for (int i = 0; i < qu.length; i++) {
			qu[i] = in.nextIntArray(3);
			for (int j = 0; j < qu[0].length; j++) {
				qu[i][j]--;
			}
		}
        
        String[] ret = bonnieAndClyde(n, g, qu);
        for (int i = 0; i < ret.length; i++) {
			System.out.println(ret[i]);
		}
    }
    
    public static String[] bonnieAndClyde(int N,  ArrayList<ArrayList<Integer>> g, int[][] q) {
		BlockCutTree bc = new BlockCutTree(g);
		bc.BCC();
		LCA[] lca = new LCA[bc.trees.length];
		for (int i = 0; i < lca.length; i++) {
			lca[i] = new LCA(bc.trees[i].length, bc.trees[i]);
		}
		String[] ret = new String[q.length];
		for (int i = 0; i < q.length; i++) {
			int a = q[i][0];
			int b = q[i][1];
			int c = q[i][2];
			if (bc.cv.component[a] == bc.cv.component[b] && bc.cv.component[b] == bc.cv.component[c]) {
				boolean unique = uniquePaths(g, a, b, c, bc, lca[bc.cv.component[a]], bc.trees[bc.cv.component[a]]);
				if (unique) {
					ret[i] = "YES";
				} else {
					ret[i] = "NO";
				}
			} else {
				ret[i] = "NO";
			}
		}
		return ret;
    }
	
	public static boolean isInSubTree(int p, int newRoot, LCA lca) {
		return lca.query(p, newRoot) == newRoot; 
	}
    
	public static boolean uniquePaths(ArrayList<ArrayList<Integer>> g, int a, int b, int c, BlockCutTree bcTree, LCA lca, BlockCutTree.BCTree[] tree) {
		int treeNodeA = bcTree.vertexToTreeNode.get(a);
		int treeNodeB = bcTree.vertexToTreeNode.get(b);
		int treeNodeC = bcTree.vertexToTreeNode.get(c);
		return uniquePathsTreeNodes(g, treeNodeA, treeNodeB, treeNodeC, bcTree, lca, tree);
	}
	
	public static boolean uniquePathsTreeNodes(ArrayList<ArrayList<Integer>> g, int treeNodeA, int treeNodeB, int treeNodeC,
			BlockCutTree bcTree, LCA lca, BlockCutTree.BCTree[] tree) {
		
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
			if (tree[treeNodeC].cv) {
				int childOfCtoA = lca.nodeAtPosition(treeNodeC, treeNodeA, 1);
				int childOfCtoB = lca.nodeAtPosition(treeNodeC, treeNodeB, 1);
				if (childOfCtoA == childOfCtoB) {
					result = uniquePathsTreeNodes(g, treeNodeA, treeNodeB, childOfCtoA, bcTree, lca, tree);
				}
			}
		}
		return result;
	}
    
	public static boolean uniquePathsOld(ArrayList<ArrayList<Integer>> g, int a, int b, int c, BlockCutTree bcTree, LCA lca, Tree[] tree) {
		HashSet<Integer> cvsA = getCvsOnPath(g, a, c, bcTree, lca, tree);
		HashSet<Integer> cvsB = getCvsOnPath(g, b, c, bcTree, lca, tree);
		cvsA.retainAll(cvsB);
		cvsA.remove(c);
		return cvsA.isEmpty();
	}
	
	public static HashSet<Integer> getCvsOnPath(ArrayList<ArrayList<Integer>> g, int a, int b, BlockCutTree bc, LCA lca, Tree[] tree) {
		// get all cvs on the path a->b
		int comp = bc.cv.component[a];
		int treeNodeA = bc.vertexToTreeNode.get(a);
		int treeNodeB = bc.vertexToTreeNode.get(b);
		int ancestor = lca.query(treeNodeA, treeNodeB);
		HashSet<Integer> cvs = new HashSet<>();
		
		int current = treeNodeA;
		while (current != ancestor) {
			if (bc.trees[comp][current].cv) {
				cvs.add(bc.trees[comp][current].originalId);
			}
			current = tree[current].parent.id;
		}

		current = treeNodeB;
		while (current != ancestor) {
			if (bc.trees[comp][current].cv) {
				cvs.add(bc.trees[comp][current].originalId);
			}
			current = tree[current].parent.id;
		}
		
		if (bc.trees[comp][ancestor].cv) {
			cvs.add(bc.trees[comp][ancestor].originalId);
		}
		
		return cvs;
	}

	public static class LCA {
		int N;
		int log2N;
		public int[] parent;
		public int[] level;
		int[][] ancestor;

		public LCA(int N, Tree[] tree) {
			this.N = N;
			this.log2N = 32 - Integer.numberOfLeadingZeros(N-1) +1;
			
			this.parent = new int[N];
			this.level = new int[N];
			this.ancestor = new int[N][log2N];
			
			int i, j;
			
			// Set up the parent array
			parent[0] = - 1;
			for (i = 1; i < N; i++) {
				parent[i] = tree[i].parent.id;
			}
			
			// Set up the level array
			for (i = 0; i < N; i++) {
				level[i] = tree[i].level;
			}

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
		int[] biConnected;		// Marks each biconnected component. Cut vertices cannot belong to a biconnected component (-1). 
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
			biConnected = new int[N];
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
			
			Arrays.fill(color, 0);
			Arrays.fill(biConnected, -1);
			int bicomp = 0;
			for (int i = 0; i < N; i++) {
				if (color[i] == 0 && !cutVertex[i]) {
					biConnected[i] = bicomp++;
					dfsSimple(i);
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


	public static class Tree {
		Tree parent;
		ArrayList<Tree> children;
		int id;
		int level;
		
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

	/*******************************************************************************************************************************
	 * Block Cut Tree
	 * 
	 * A block is a connected graph which does not have any cut edge. This expands on the idea of a biconnected component by
	 * including the cut vertices on the borders of the the biconnected component. The number of blocks and connected components
	 * are the same for any graph, but vertices (specifically cut vertices) can be part of more than one block.
	 * 
	 * This method breaks up a graph into a tree consisting of one vertex per block and one vertex per cut vertex. 
	 * 
	 * More info on block cut trees:
	 * http://www.math.ucsd.edu/~jverstra/154-part4-2014.pdf
	 * http://compalg.inf.elte.hu/~tony/Oktatas/TDK/FINAL/Chap%205.PDF
	 * 
	 * Based on http://www.geeksforgeeks.org/biconnected-components/
	 */

	public static class BlockCutTree
	{
	    public HashMap<Integer, Integer> vertexToTreeNode; // Works only for vertices that are NOT cvs.
		private int V, E; // No. of vertices & Edges respectively
	 
	    // Count is number of biconnected components. time is
	    // used to find discovery times
	    int count = 0, time = 0;
	    
	    static boolean print = false;
		private CutVertices cv;
		private BCTree[] cvTree;
		private BCTree[] blockTree;
		private ArrayList<HashSet<Integer>> components;
		private ArrayList<HashSet<Integer>> vertexToBlock;
		private BCTree[][] trees;
		private ArrayList<ArrayList<Integer>> g;
	 
	    public static class Edge
	    {
	        int u;
	        int v;
	        Edge(int u, int v)
	        {
	            this.u = u;
	            this.v = v;
	        }
	    };
	 
	    //Constructor
	    // G is used directly (zero copy)
	    BlockCutTree(ArrayList<ArrayList<Integer>> g)
	    {
	    	this.g = g;
	        V = g.size();
	        E = 0;
	        
			if (BlockCutTree.print) {
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
	    }
	 
	    //Function to add an edge into the graph
	    void addEdge(int v,int w)
	    {
	        g.get(v).add(w);
	        g.get(w).add(v);
	        E += 2;
	    }
	 
	    //Function to add an edge into the graph
	    void addEdgeSingle(int v,int w)
	    {
	        g.get(v).add(w);
	        E++;
	    }

		
		public static class SearchNode {
			int block;
			BCTree tree;
			public SearchNode(int block, BCTree tree) {
				this.block = block;
				this.tree = tree;
			}
		}

		public static class BCTree extends Tree {

//			Tree parent;
//			ArrayList<Tree> children;
//			int id;
//			int level;
			
			boolean cv;
			int originalId;
			HashSet<Integer> members;
			//ArrayList<BCTree> children;
			
			public BCTree(boolean cv, int originalId, int id) {
				super(id, 0);
				this.cv = cv;
				this.originalId = originalId;
				this.members = new HashSet<>();
				this.parent = null;
				this.children = new ArrayList<>();
			}
			
			public BCTree addChild(boolean cv, int originalId, int id) {
				BCTree child = new BCTree(cv, originalId, id);
				child.parent = this;
				child.parent.children.add(child);
				child.level = this.level +1;
				return child;
			}

			public String toString() {
				String me = (cv ? "c" : "b") + originalId + ": (" ;
				boolean first = true;
				for (Tree child: this.children) {
					BCTree realChild = (BCTree)child;
					if (!first) {
						me += ", ";
					}
					me += (realChild.cv ? "c" : "b") + realChild.originalId;
					first = false;
				}
				return me + ")";
			}
		}
	 
	    // A recursive function that finds and prints strongly connected
	    // components using DFS traversal
	    // u --> The vertex to be visited next
	    // disc[] --> Stores discovery times of visited vertices
	    // low[] -- >> earliest visited vertex (the vertex with minimum
	    //             discovery time) that can be reached from subtree
	    //             rooted with current vertex
	    // *st -- >> To store visited edges
	    void BCCUtil(int u, int disc[], int low[], LinkedList<Edge>st,
	                 int parent[])
	    {
	 
	        // Initialize discovery time and low value
	        disc[u] = low[u] = ++time;
	        int children = 0;
	        boolean singleVertex = true;
	 
	        // Go through all vertices adjacent to this
	        Iterator<Integer> it = g.get(u).iterator();
	        while (it.hasNext())
	        {
	        	singleVertex = false;
	            int v = it.next();  // v is current adjacent of 'u'
	 
	            // If v is not visited yet, then recur for it
	            if (disc[v] == -1)
	            {
	                children++;
	                parent[v] = u;
	 
	                // store the edge in stack
	                st.add(new Edge(u,v));
	                BCCUtil(v, disc, low, st, parent);
	 
	                // Check if the subtree rooted with 'v' has a
	                // connection to one of the ancestors of 'u'
	                // Case 1 -- per Strongly Connected Components Article
	                if (low[u] > low[v])
	                    low[u] = low[v];
	 
	                // If u is an articulation point,
	                // pop all edges from stack till u -- v
	                if ( (disc[u] == 1 && children > 1) ||
	                        (disc[u] > 1 && low[v] >= disc[u]) )
	                {
	                    while (st.getLast().u != u || st.getLast().v != v)
	                    {
	                    	if (print) {
	                    		System.out.print(st.getLast().u + "--" + st.getLast().v + " ");
	                    	}
	                    	components.get(count).add(st.getLast().u);
	                    	components.get(count).add(st.getLast().v);
	                    	vertexToBlock.get(st.getLast().u).add(count);
	                    	vertexToBlock.get(st.getLast().v).add(count);
	                        st.removeLast();
	                    }
                    	if (print) {
                    		System.out.println(st.getLast().u + "--" + st.getLast().v + " ");
                    	}
                    	components.get(count).add(st.getLast().u);
                    	components.get(count).add(st.getLast().v);
                    	vertexToBlock.get(st.getLast().u).add(count);
                    	vertexToBlock.get(st.getLast().v).add(count);
	                    st.removeLast();
	 
	                    count++;
	                }
	            }
	 
	            // Update low value of 'u' only of 'v' is still in stack
	            // (i.e. it's a back edge, not cross edge).
	            // Case 2 -- per Strongly Connected Components Article
	            else if (v != parent[u] && disc[v] < low[u])
	            {
	                if (low[u]>disc[v])
	                    low[u]=disc[v];
	                st.add(new Edge(u,v));
	            }
	        }
	        if (singleVertex) {
            	components.get(count).add(u);
            	vertexToBlock.get(u).add(count);
                count++;
	        }
	    }
	 
		// The function to do DFS traversal. It uses BCCUtil()
	    void BCC() {
			this.cv = new CutVertices(g);
//			System.out.println("time: done cv");
			this.cvTree = new BCTree[V];
			this.blockTree = new BCTree[V];
			this.components = new ArrayList<>();
			this.vertexToBlock = new ArrayList<>();
			vertexToTreeNode = new HashMap<>();
			
	        int disc[] = new int[V];
	        int low[] = new int[V];
	        int parent[] = new int[V];
	        LinkedList<Edge> st = new LinkedList<Edge>();
	 
	        // Initialize disc and low, and parent arrays
	        for (int i = 0; i < V; i++)
	        {
	        	this.components.add(new HashSet<Integer>());
	        	this.vertexToBlock.add(new HashSet<Integer>());
	            disc[i] = -1;
	            low[i] = -1;
	            parent[i] = -1;
	        }
	        
	        for (int i = 0; i < V; i++)
	        {
	            if (disc[i] == -1)
	                BCCUtil(i, disc, low, st, parent);
	 
	            int j = 0;
	 
	            // If stack is not empty, pop all edges from stack
	            while (st.size() > 0)
	            {
	                j = 1;
		            if (print) {
		            	System.out.print(st.getLast().u + "--" + st.getLast().v + " ");
		            }
                	components.get(count).add(st.getLast().u);
                	components.get(count).add(st.getLast().v);
                	vertexToBlock.get(st.getLast().u).add(count);
                	vertexToBlock.get(st.getLast().v).add(count);
	                st.removeLast();
	            }
	            if (j == 1)
	            {
		            if (print) {
		            	System.out.println();
		            }
	                count++;
	            }
	        }

	   	 
//	        System.out.println("time: done dfs");

	        // build BC tree
	        boolean[] done = new boolean[V];
	        trees = new BCTree[cv.numComponents][];
	        for (int i = 0; i < V; i++) {
	        	if (!done[i] && cv.cutVertex[i]) {
	        		int treeId = 0;
	        		BCTree root = new BCTree(true, i, treeId++);
	        		cvTree[i] = root;
	        		BCTree[] compTree = new BCTree[cv.componentSize[cv.component[i]] * 2];
	        		trees[cv.component[i]] = compTree;
	        		compTree[root.id] = root;
	        		vertexToTreeNode.put(i, cvTree[i].id);
	        		
					Stack<SearchNode> q = new Stack<>();
					SearchNode top = new SearchNode(-1, root);
					q.add(top);
					HashSet<Integer> cVisited = new HashSet<>();
					HashSet<Integer> bVisited = new HashSet<>();
					while (!q.isEmpty()) {
						top = q.pop();
						if (cVisited.contains(top.tree.originalId)) {
							continue;
						}
						cVisited.add(top.tree.originalId);
						done[top.tree.originalId] = true;

						HashSet<Integer> comps = vertexToBlock.get(top.tree.originalId);
						for (Integer comp: comps) {
							if (bVisited.contains(comp)) {
								continue;
							}
							bVisited.add(comp);
							BCTree blockChild = cvTree[top.tree.originalId].addChild(false, comp, treeId++);
							blockTree[comp] = blockChild;
							compTree[blockChild.id] = blockChild;
							
							for (Integer member: components.get(comp)) {
								blockChild.members.add(member);
								if (!vertexToTreeNode.containsKey(member)) {
									vertexToTreeNode.put(member, blockChild.id);
								}
								done[member] = true;
								if (cv.cutVertex[member] && member != top.tree.originalId) {
									if (cvTree[member] == null) {
										cvTree[member] = blockChild.addChild(true, member, treeId++);
										vertexToTreeNode.put(member, cvTree[member].id); // replace the block tree's id if this is cv
										compTree[cvTree[member].id] = cvTree[member];
										q.push(new SearchNode(-1, cvTree[member]));
									}
								}
							}
						}
					}
					trees[cv.component[i]] = Arrays.copyOf(compTree, treeId);
	        	}
			}

//	        System.out.println("time: build t1");
	        
	        // blocks with no cvs
	        for (int i = 0; i < V; i++) {
	        	if (!done[i]) {
	        		int treeId = 0;
	        		BCTree[] compTree = new BCTree[1];
	        		
	        		// there will be only one block/component here. 
					HashSet<Integer> comps = vertexToBlock.get(i);
					for (Integer comp: comps) {
						BCTree root = new BCTree(false, comp, treeId++);
						BCTree blockChild = root;
						blockTree[comp] = blockChild;
						compTree[blockChild.id] = blockChild;
		        		trees[cv.component[i]] = compTree;
						
						for (Integer member: components.get(comp)) {
							blockChild.members.add(member);
							if (!vertexToTreeNode.containsKey(member)) {
								vertexToTreeNode.put(member, blockChild.id);
							}
							done[member] = true;
							if (cv.cutVertex[member] && member != i) {
								if (cvTree[member] == null) {
									cvTree[member] = blockChild.addChild(true, member, treeId++);
									compTree[cvTree[member].id] = cvTree[member];
								}
							}
						}
					}
					trees[cv.component[i]] = Arrays.copyOf(compTree, treeId);
	        	}
	        }
	        //System.out.println("time: build t2");
	        System.currentTimeMillis();
	    }
	}

    public static void palindromicTable() {
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] aTemp = new int[n][];
        for (int i = 0; i < n; i++) {
        	aTemp[i] = in.nextIntArray(m);
		}
        int[] maxDims = palindromicTable(n, m, aTemp);
        
        System.out.println(maxDims[0]);
        for (int i = 1; i < maxDims.length; i++) {
        	System.out.print(maxDims[i] + " ");
		}
        System.out.println();
    }
    
    public static void testPalindromicTable() {
    	int numTests = 1000000;
    	Random rand = new Random(0);
    	int maxLength = 7;
    	for (int test = 0; test < numTests; test++) {
			int n = rand.nextInt(1) + 1;
			int m = rand.nextInt(maxLength) + 1;
			int[][] a = new int[n][m];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					a[i][j] = rand.nextInt(3);
				}
			}
			int[] correct = palindromicTableSlow(n, m, a);
			int[] act = palindromicTable(n, m, a);
			if (correct[0] != act[0] || !isPal(Arrays.copyOfRange(correct, 1, correct.length), a)) {
				System.out.println("fail");
				isPal(Arrays.copyOfRange(correct, 1, correct.length), a);
				palindromicTable(n, m, a);
			}
		}
    }
    
    public static boolean isPal(int[] area, int[][] a) {
    	int x1 = area[1];
    	int y1 = area[0];
    	int x2 = area[3];
    	int y2 = area[2];
    	int area2 = 0;
    	
		int numOdd = 0;
		int[] sum = new int[10];
		for (int i = y1; i <= y2; i++) {
			for (int j = x1; j <= x2; j++) {
				sum[a[i][j]]++;
				area2++;
			}
		}
		int numNonZeroEven = 0;
		for (int i = 0; i < sum.length; i++) {
			if ((sum[i] % 2) == 1) {
				numOdd++;
			} else if (sum[i] > 0 && i > 0) {
				numNonZeroEven++;
			}
		}
		if (((area2 % 2) == 0 && numOdd == 0) || ((area2 % 2) == 1 && numOdd == 1)) {
			if ((numNonZeroEven > 0 || area2 == 1)) {
				return true;
			}
		}
		return false;
    }
    
    public static int[] palindromicTableSlow(int n, int m, int[][] a) {
		int max = 0;
		int[] maxDims = new int[4];
		for (int y1 = 0; y1 < n; y1++) {
			for (int y2 = y1; y2 < n; y2++) {
				for (int x1 = 0; x1 < m; x1++) {
					for (int x2 = x1; x2 < m; x2++) {
						int area = 0;
						int numOdd = 0;
						int[] sum = new int[10];
						for (int i = y1; i <= y2; i++) {
							for (int j = x1; j <= x2; j++) {
								sum[a[i][j]]++;
								area++;
							}
						}
						int numNonZeroEven = 0;
						for (int i = 0; i < sum.length; i++) {
							if ((sum[i] % 2) == 1) {
								numOdd++;
							} else if (sum[i] > 0 && i > 0) {
								numNonZeroEven++;
							}
						}
						if (((area % 2) == 0 && numOdd == 0) || ((area % 2) == 1 && numOdd == 1)) {
							if ((numNonZeroEven > 0 || area == 1) && area > max) {
								max = area;
								maxDims = new int[]{y1, x1, y2, x2};
							}
						}
					}
				}
			}
		}
		return new int[]{max, maxDims[0], maxDims[1], maxDims[2], maxDims[3]};
    }

    public static int[] palindromicTable(int n, int m, int[][] aTemp) {
        boolean swap = false;
        int[][] a = aTemp;
        if (n > m) {
        	swap = true;
        	int temp = n;
        	n = m;
        	m = temp;
        	a = new int[n][m];
        	for (int i = 0; i < n; i++) {
        		for (int j = 0; j < m; j++) {
					a[i][j] = aTemp[j][i];
				}
        	}
        }
        
        int[][][] total = new int[m][n][10];

		for (int x = 0; x < m; x++) {
			int[][] col = total[x];
			for (int y = 0; y < n; y++) {
				col[y][a[y][x]]++;
				for (int i = 0; i < 10 && y > 0; i++) {
					col[y][i] += col[y-1][i];
				}
			}
    	}
		int max = 0;
		int[] maxDims = new int[4];
		
		for (int y1 = 0; y1 < n; y1++) {
			for (int y2 = y1; y2 < n; y2++) {
				int[] rowSum = new int[10];
				for (int x1 = 0; x1 < m; x1++) {
					for (int i = 0; i < 10; i++) {
						rowSum[i] += total[x1][y2][i];
					}
					if (y1 > 0) {
						for (int i = 0; i < 10; i++) {
							rowSum[i] -= total[x1][y1-1][i];
						}
					}
				}
				for (int x1 = 0; x1 < m; x1++) {
					// remove previous starting column from rowSum

					if (x1 > 0) {
						for (int i = 0; i < 10; i++) {
							rowSum[i] -= total[x1-1][y2][i];
						}
						if (y1 > 0) {
							for (int i = 0; i < 10; i++) {
								rowSum[i] += total[x1-1][y1-1][i];
							}
						}
					}
					
					int[] sum = Arrays.copyOf(rowSum, rowSum.length);
					
					for (int x2 = m-1; x2 >= x1; x2--) {
						int area = (x2 - x1 + 1) * (y2 - y1 + 1);
						if (area < max) {
							break;
						}
						int numOdd = 0;
						int numNonZeroEven = 0;
						int zeros = 0;
						int notZeros = 0;
						for (int i = 0; i < 10; i++) {
							if ((sum[i] % 2) == 1) {
								numOdd++;
							} else if (sum[i] > 0 && i > 0) {
								numNonZeroEven++;
							}
							if (sum[i] == 0) {
								zeros++;
							} else {
								notZeros++;
							}
						}
						boolean valid = false;
						if (((area % 2) == 0 && numOdd == 0) || ((area % 2) == 1 && numOdd == 1)) {
							if ((numNonZeroEven > 0 || area == 1)) {
								if (area > max) {
									max = area;
									if (swap) {
										maxDims = new int[]{x1, y1, x2, y2};
									} else {
										maxDims = new int[]{y1, x1, y2, x2};
									}
								}
								valid = true;
							}
						}

						boolean valid2 = false;
						if (((area % 2) == 0 && numOdd == 0) || ((area % 2) == 1 && numOdd == 1)) {
							if ((area % 2) == 0 && numNonZeroEven > 0) {
								valid2 = true;
								if (notZeros == 0 && zeros != 1) {
									valid2 = false;
								}
								if (notZeros == 0 && zeros != 1) {
									valid2 = false;
								}
							}
							if ((area % 2) == 1) {
								if (area == 1) {
									valid2 = true;
								}
								if (numNonZeroEven > 0) {
									valid2 = true;
								}
								if (notZeros == 0 && zeros != 1) {
									valid2 = false;
								}
								if (notZeros == 0 && zeros != 1) {
									valid2 = false;
								}
							}
						}
						if (valid != valid2) {
							System.out.println("fail");

							if (((area % 2) == 0 && numOdd == 0) || ((area % 2) == 1 && numOdd == 1)) {
								if ((numNonZeroEven > 0 || area == 1)) {
									if (area > max) {
										max = area;
										if (swap) {
											maxDims = new int[]{x1, y1, x2, y2};
										} else {
											maxDims = new int[]{y1, x1, y2, x2};
										}
									}
									valid = true;
								}
							}

							if (((area % 2) == 0 && numOdd == 0) || ((area % 2) == 1 && numOdd == 1)) {
								if ((area % 2) == 0 && numNonZeroEven > 0) {
									valid2 = true;
									if (notZeros == 0 && zeros != 1) {
										valid2 = false;
									}
									if (notZeros == 0 && zeros != 1) {
										valid2 = false;
									}
								}
								if ((area % 2) == 1) {
									if (area == 1) {
										valid2 = true;
									}
									if (numNonZeroEven > 0) {
										valid2 = true;
									}
									if (notZeros == 0 && zeros != 1) {
										valid2 = false;
									}
									if (notZeros == 0 && zeros != 1) {
										valid2 = false;
									}
								}
							}
						}
						
						for (int i = 0; i < 10; i++) {
							sum[i] -= total[x2][y2][i];
						}
						if (y1 > 0) {
							for (int i = 0; i < 10; i++) {
								sum[i] += total[x2][y1-1][i];
							}
						}
					}
				}
			}
		}
        return new int[]{max, maxDims[0], maxDims[1], maxDims[2], maxDims[3]};
    }

    public static void transformToPalindrome() {
        int n = in.nextInt();
        int k = in.nextInt();
        int m = in.nextInt();
        DisjointSet dset = new DisjointSet(n+1);
        for (int i = 0; i < n+1; i++) {
        	dset.make_set(i);
		}
        for (int i = 0; i < k; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int setA = dset.find_set(a);
            int setB = dset.find_set(b);
            dset.union_sets(setA, setB);
        }

        int[] a = in.nextIntArray(m);
        int[] b = new int[m];
        for (int i = 0; i < a.length; i++) {
            a[i] = dset.find_set(a[i]);
            b[m-1-i] = a[i];
        }

        //System.out.println(0);
        //System.out.println(Arrays.toString(a));
        

        // Longest common subsequence
		int[][] dp = new int[m+1][m+1];
		int max = 0;
		int[] best = {0, 0};
		int commonEnding = 0;
		
		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[0].length && i+j <= m+1; j++) {
				if (a[i-1] == b[j-1]) {
					dp[i][j] = dp[i-1][j-1] + 1;
					if (dp[i][j] > max) {
						max = dp[i][j];
						best[0] = i;
						best[1] = j;
						if (i == m - j +1) {
							commonEnding = -1;
						} else {
							commonEnding = 0;
						}
					}
				} else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
			}
		}
		System.out.println(max*2 + commonEnding);
    }

    public static class DisjointSet {
        int[] parent;
        int[] rank;
        int[] size;
        int maxSize = 0;

        public DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            this.size = new int[size];

            // This is not needed, it just clarifies the state to external observers.
            Arrays.fill(parent, -1);
            Arrays.fill(rank, -1);
        }

        public void make_set(int v) {
            parent[v] = v;
            rank[v] = 0;
            size[v] = 1;
            maxSize = Math.max(maxSize, 1);
        }

        public int find_set(int v) {
            if (parent[v] == -1) {
                return -1;
            }
            if (v == parent[v]) {
                return v;
            }
            return parent[v] = find_set (parent[v]);
        }

        public void union_sets(int a, int b) {
            a = find_set(a);
            b = find_set(b);
            if (a != b) {
                if (rank[a] < rank[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                size[a] += size[b];
                size[b] = 0;
                maxSize = Math.max(maxSize, size[a]);

                parent[b] = a;
                if (rank[a] == rank[b]) {
                    ++rank[a];
                }
            }
        }
    }

    public static void patternCount() {
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            char[] s = in.next().toCharArray();
            int lastOne = -1;
            int valid = 0;
            for (int one = 0; one < s.length; one++) {
                if (s[one] == '1') {
                    if (lastOne != -1) {
                        int numZero = 0;
                        for (int zero = lastOne+1; zero < one; zero++) {
                            if (s[zero] == '0') {
                                numZero++;
                            }
                        }
                        //System.out.println(numZero);
                        if (numZero > 0 && numZero == one - (lastOne + 1)) {
                            valid++;
                        }
                    }
                    lastOne = one;
                }
            }
            //System.out.println();
            System.out.println(valid);
        }
    }

    public static void twinArrays() {
        int n = in.nextInt();
        int[] a = in.nextIntArray(n);
        int[] b = in.nextIntArray(n);
        ArrayList<int[]> as = new ArrayList<>();
        ArrayList<int[]> bs = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            as.add(new int[]{a[i], i});
            bs.add(new int[]{b[i], i});
        }
        Comparator comp = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        };
        Collections.sort(as, comp);
        Collections.sort(bs, comp);
        int min = as.get(0)[0] + as.get(0)[0];
        if (as.get(0)[1] == bs.get(0)[1]) {
            min = as.get(0)[0] + bs.get(1)[0];
            min = Math.min(min, as.get(1)[0] + bs.get(0)[0]);
        }
        System.out.println(min);
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

