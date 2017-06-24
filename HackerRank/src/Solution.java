import java.io.*;
import java.util.*;

/**
 * WeekofCode33
 */

public class Solution {
    public static InputReader in;
    public static InputReader in2;
    public static PrintWriter out;
    public static boolean DEBUG = false;

    public static void main(String[] args) {
		// increase stack size
		new Thread(null, new Runnable() {
            public void run() {
            	try {
					new Solution().run();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
            }
        }, "1", 1 << 26).start();
    }
    
    public void run() throws FileNotFoundException {
    	long t1 = System.currentTimeMillis();
        InputStream inputStream = System.in;
//    	inputStream = new FileInputStream("C:\\Users\\Lucas\\Downloads\\input06.txt");
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, false);

        bonnieAndClyde();
//        testPalindromicTable();
//        palindromicTable();
        //transformToPalindrome();
        //twinArrays();
        //patternCount();
       
        out.flush();
//        System.out.println(System.currentTimeMillis() - t1);
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
        	out.println(ret[i]);
		}
    }
    
    public static String[] bonnieAndClyde(int N,  ArrayList<ArrayList<Integer>> g, int[][] q) {
		BlockCutTree bc = new BlockCutTree(g);

    	long t1 = System.currentTimeMillis();
		LCA[] lca = new LCA[bc.numComponents];
		for (int i = 0; i < lca.length; i++) {
			//lca[i] = new LCA(bc.trees[i].length, bc.trees[i]);
			lca[i] = new LCA(bc.forestParent[i].length, bc.forestParent[i], bc.forestLevel[i]);
		}

//		System.out.println("lca: "+ (System.currentTimeMillis() - t1));
		t1 = System.currentTimeMillis();
		
		String[] ret = new String[q.length];
		for (int i = 0; i < q.length; i++) {
			int a = q[i][0];
			int b = q[i][1];
			int c = q[i][2];
			ret[i] = uniquePaths(g, a, b, c, bc, lca[bc.component[a]]) ? "YES" : "NO";
		}
//		System.out.println("queries: "+ (System.currentTimeMillis() - t1));
		t1 = System.currentTimeMillis();
		return ret;
    }
	
	public static boolean isInSubTree(int p, int newRoot, LCA lca) {
		return lca.query(p, newRoot) == newRoot; 
	}
    
	public static boolean uniquePaths(ArrayList<ArrayList<Integer>> g, int a, int b, int c, BlockCutTree bcTree, LCA lca) {
		if (a == b) {
			// Really, HackerRank?!
			return a == c;
		}
		int treeNodeA = bcTree.vertexToTreeNode[a];
		int treeNodeB = bcTree.vertexToTreeNode[b];
		int treeNodeC = bcTree.vertexToTreeNode[c];
		if (bcTree.component[a] == bcTree.component[b] && bcTree.component[b] == bcTree.component[c] 
				&& uniquePathsTreeNodes(g, treeNodeA, treeNodeB, treeNodeC, c, bcTree, lca)) {
			return true;
		} else {
			return false;
		}
	}
	

	public static boolean uniquePathsTreeNodes(ArrayList<ArrayList<Integer>> g, int treeNodeA, int treeNodeB, int treeNodeC, int c,
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

