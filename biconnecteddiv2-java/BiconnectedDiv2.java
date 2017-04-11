import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BiconnectedDiv2 {

	public int minimize(int[] w1, int[] w2) {
		long sum = w1[0] + w1[w1.length-1];
		for (int i = 1; i < w1.length - 1; i++) {
			if (w1[i] < 0) {
				sum += w1[i];	
			}
		}
		for (int i = 0; i < w2.length; i++) {
			sum += w2[i];	
		}
		return (int)sum;
	}

	public int slow(int[] w1, int[] w2) {
		int nn = w1.length+1;
		int[][] cost = new int[nn][nn];
		
		for (int i = 0; i < w1.length; i++) {
			cost[i][i+1] = w1[i];
			cost[i+1][i] = w1[i];
		}
		for (int i = 0; i < w2.length; i++) {
			cost[i][i+2] = w2[i];
			cost[i+2][i] = w2[i];
		}
		
		int min = Integer.MAX_VALUE;
		int len = w1.length;
		int N = 1 << len;
		for (int n = 0; n < N; n++) {
			boolean[] active = new boolean[len];
			for (int i = 0; i < len; i++) {
				if (((1 << i) & n) != 0) {
					active[i] = true;
				}
			}

			int len2 = w2.length;
			int N2 = 1 << len2;
			for (int n2 = 0; n2 < N2; n2++) {
				boolean[] active2 = new boolean[len2];
				for (int i = 0; i < len2; i++) {
					if (((1 << i) & n2) != 0) {
						active2[i] = true;
					}
				}
				
				ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
				for (int i = 0; i < nn; i++) {
					graph.add(new ArrayList<>());
				}
				
				int weight = 0;
				for (int i = 0; i < w1.length; i++) {
					if (active[i]) {
						graph.get(i).add(i+1);
						graph.get(i+1).add(i);
						weight += cost[i][i+1];
					}
				}
				
				for (int i = 0; i < w2.length; i++) {
					if (active2[i]) {
						graph.get(i).add(i+2);
						graph.get(i+2).add(i);
						weight += cost[i][i+2];
					}
				}
				
				CutVertices cv = new CutVertices(graph);
				boolean bicon = true;
				for (int i = 0; i < cv.component.length; i++) {
					if (cv.component[i] != 0) {
						bicon = false;
						break;
					}
				}
				for (int i = 0; i < cv.biConnected.length; i++) {
					if (cv.biConnected[i] != 0) {
						bicon = false;
						break;
					}
				}
				if (bicon) {
					if (weight < min) {
						min = weight;
					}
				}
			}
		}
		return min;
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
		ArrayList<ArrayList<Integer>> g;
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
			
			int comp = 0;
			for (int i = 0; i < N; i++) {
				if (color[i] == 0) {
					parent[i] = -1;
					component[i] = comp++;
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
}
