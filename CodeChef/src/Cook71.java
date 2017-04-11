
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.math.BigInteger;

public class Cook71 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		new Thread(null, new Runnable() {
            public void run() {
                Cook71.run();
            }
        }, "1", 1 << 26).start();
	}
		
	public static void run() {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
//		testCHEFLAND();
		CHEFLAND();
//		testRIGHTTRI();
//		RIGHTTRI();
//		STICKS();
		
		out.close();
	}
	
	public static void CHEFLAND() {
		int n = in.nextInt(); // vertices
		int m = in.nextInt(); 
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Integer>());
		}
		ArrayList<Counter<Integer>> gset = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			gset.add(new Counter<Integer>());
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt()-1;
			int b = in.nextInt()-1;
			if (!gset.get(a).containsKey(b)) {
				g.get(a).add(b);
				g.get(b).add(a);
			}
			gset.get(a).add(b);
			gset.get(b).add(a);
		}
		
		if (CHEFLAND(n, g, gset)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
		
	}
	
	public static boolean CHEFLAND(int N, ArrayList<ArrayList<Integer>> g, ArrayList<Counter<Integer>> gset) {
		CutVertices cv = new CutVertices(g, gset);
				
		int bridges = 0;
		for (Integer k: cv.cutEdge.keySet()) {
			bridges += cv.cutEdge.get(k).size();
		}
		if (bridges == 0) {
			return true;
		}
		bridges /=2 ;
		
		HashSet<Integer> bridgeNodes = new HashSet<>();
		for (Integer k: cv.cutEdge.keySet()) {
			ArrayList<Integer> nn = cv.cutEdge.get(k);
			for (int i = 0; i < nn.size(); i++) {
				bridgeNodes.add(nn.get(i));
			}
		}
		
		// Find a bridge node with only one bridge attached
		int start = -1;
		for (Integer k: bridgeNodes) {
			if (cv.cutEdge.get(k).size() == 1) {
				start = k;
				break;
			}
		}
		
		int firstStart = start;
		HashSet<Integer> visited = new HashSet<>();
		for (int j = 0; j < g.get(firstStart).size(); j++) {
			Queue<Integer> q = new LinkedList<>();
			q.add(firstStart);
			visited.remove(firstStart);
			int top = firstStart;
			
			while (!q.isEmpty()) {
				top = q.poll();
				if (visited.contains(top)) {
					continue;
				}
				visited.add(top);
				if (top != start && bridgeNodes.contains(top)) {
					start = top;
					q.clear();
					q.add(start);
					visited.remove(top);
					continue;
					// shortest path to a bridgeNodes. restart from here
				}
				for (int i = 0; i < g.get(top).size(); i++) {
		        	int child = g.get(top).get(i);
		        	q.add(child);
				}
			}
		}
		
		for (Integer k: bridgeNodes) {
			if (!visited.contains(k)) {
				return false; 
			}
		}
		return true;
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
		ArrayList<Counter<Integer>> gset;
		
		// A cut edge is an edge that when removed it creates more components than previously in the graph.
		// A graph can have cut vertices without cut edges, but not the other way around. Also know as a bridge.
		// More info: https://en.wikipedia.org/wiki/Bridge_(graph_theory)
		HashMap<Integer, ArrayList<Integer>> cutEdge;
		
		int stackTop = 0;
		int stackDep = 0;
		
		public CutVertices(ArrayList<ArrayList<Integer>> graph, ArrayList<Counter<Integer>> gset) {
			N = graph.size();
			this.g = graph;
			depth = new int[N];
			parent = new int[N];
			low = new int[N];
			color = new int[N];
			component = new int[N];
			biConnected = new int[N];
			cutVertex = new boolean[N];
			this.gset = gset;
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
	        		if (low[child] > depth[top] && gset.get(top).get(child) == 1) {
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

	public static void testCHEFLAND() {
		Random ra = new Random(0);
		for (int test = 0; test < 20; test++) {
			int N = ra.nextInt(100000) + 1;
			int M = ra.nextInt(N*2);
			int C = 1;
			boolean allowMultiEdges = true;

			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<Counter<Integer>> gset = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				g.add(new ArrayList<Integer>());
				gset.add(new Counter<Integer>());
			}
			for (int i = 1; i < N; i++) {
				int oldNode = ra.nextInt(i);
				int newNode = i;
				int c = ra.nextInt(C);
				g.get(oldNode).add(newNode);
				g.get(newNode).add(oldNode);
				gset.get(oldNode).add(newNode);
				gset.get(newNode).add(oldNode);
			}
			if (!allowMultiEdges) {
				M = (int) Math.min(M, (N-1L)*N/2);
			}
			for (int i = 0; i < M - (N-1) && N > 1; i++) {
				int a = ra.nextInt(N);
				int b = ra.nextInt(N);
				while ((allowMultiEdges && a == b)) {
					a = ra.nextInt(N);
					b = ra.nextInt(N);
				}
				int c = ra.nextInt(C);
				if (!gset.get(a).containsKey(b)) {
					g.get(a).add(b);
					g.get(b).add(a);
				}
				gset.get(b).add(a);
				gset.get(a).add(b);
				
			}
			
			CHEFLAND(N, g, gset);
		}
	}

	public static void RIGHTTRI() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			long H = in.nextLong();
			long S = in.nextLong();
			out.println(RIGHTTRI(H, S));
		}
	}
	
	public static void testRIGHTTRI() {
		for (int S = 1; S < 10000; S++) {
			for (int H = 1; H < 10000; H++) {
				String ret = RIGHTTRI(H, S);
				StringTokenizer st = new StringTokenizer(ret);
				double x = Double.parseDouble(st.nextToken());
				if (x != -1) {
					double y = Double.parseDouble(st.nextToken());
					if (Math.abs(x*y - S*2.0) > 0.0001) {
						System.out.println("fail");
					}
				}
			}
		}
	}
	
	public static String RIGHTTRI(long H, long S) {
		BigInteger det1 = BigInteger.valueOf(H).pow(4).subtract(BigInteger.valueOf(16).multiply(BigInteger.valueOf(S).multiply(BigInteger.valueOf(S))));  
		if (det1.compareTo(BigInteger.ZERO) < 0) {
			return "-1";
		}
		double det2 = Math.sqrt(det1.doubleValue());
		double x = (H*H + det2)/2.0;
		if (x < 0) {
			return "-1";
		}
		x = Math.sqrt(x);
		double y = 2*S/x;
		
		if (x > y) {
			double temp = x;
			x = y;
			y = temp;
		}
		
		return String.format("%.12f %.12f %d", x, y, H);
	}

	public static void STICKS() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int N = in.nextInt();
			int[] a = in.nextIntArray(N);
			out.println(STICKS(N, a));
		}
	}

	public static int STICKS(int n, int[] a) {
		int[] len = new int[1001];
		for (int i = 0; i < a.length; i++) {
			len[a[i]]++;
		}
		for (int i = len.length-1; i >= 0; i--) {
			if (len[i] >= 2) {
				if (len[i] >= 4) {
					return i*i;
				}
				for (int j = i-1; j >= 0; j--) {
					if (len[j] >= 2) {
						return j*i;
					}
				}
				return -1;
			}
		}
		return -1;
	}

	@SuppressWarnings("serial")
	public static class Counter<T> extends HashMap<T, Integer> {
		public void add(T key) {
			Integer i = this.get(key);
			this.put(key, i == null ? 1 : i + 1);
		}
		public void add(T key, int count) {
			Integer i = this.get(key);
			this.put(key, i == null ? count : i + count);
		}
	}

	public static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
			tokenizer = null;
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {return Integer.parseInt(next());}
		public long nextLong() {return Long.parseLong(next());}
		public double nextDouble() {return Double.parseDouble(next());}
		public long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) a[i] = this.nextLong();
			return a;
		}
		public int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) a[i] = this.nextInt();
			return a;
		}
	}
}
