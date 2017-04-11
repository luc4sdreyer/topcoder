// Practice
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

public class Practice {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		new Thread(null, new Runnable() {
            public void run() {
                Practice.run();
            }
        }, "1", 1 << 26).start(); // Set stack memory to 64 MB
	}
	
	public static void run() {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

//		testMTRWY();
		MTRWY();
		
		out.close();
	}
	
	public static void testMTRWY() {
		int N = 1000;
		int M = 900;
		int Q = 50000;
		System.out.println("1");
		System.out.println(N+" "+M+" "+Q);
		
		for (int i = 0; i < Q; i++) {
			int nq = ra.nextInt(4)+1;
			System.out.print(nq);
			if (nq == 1) {
				System.out.println(" " + (ra.nextInt(N)+1) + " " + (ra.nextInt(M-1)+1));
			}
			if (nq == 2) {
				System.out.println(" " + (ra.nextInt(N-1)+1) + " " + (ra.nextInt(M)+1));
			}
			if (nq == 3) {
				System.out.println(" " + (ra.nextInt(N)+1) + " " + (ra.nextInt(M)+1) + " " + (ra.nextInt(N)+1) + " " + (ra.nextInt(M)+1));
			}
			if (nq == 4) {
				System.out.println();
			}
		}
	}
	
	public static void MTRWY() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int N = in.nextInt();
			int M = in.nextInt();
			int qs = in.nextInt();
			int[][] q = new int[qs][5];
			for (int i = 0; i < q.length; i++) {
				int type = in.nextInt();
				q[i][0] = type;
				int[] typelen = {0, 2, 2, 4, 0};
				for (int j = 0; j < typelen[type]; j++) {
					q[i][1+j] = in.nextInt()-1;
				}
			}
			
			MTRWY(N, M, q);
		}
	}
	
	public static void MTRWY(int N, int M, int[][] q) {
		int[][] xwall = new int[M][N]; // wall to the right?
		int[][] ywall = new int[M][N]; // wall below?
		int[][] color = new int[M][N];
		for (int i = 0; i < color.length; i++) {
			Arrays.fill(color[i], -1);
		}
		
		for (int i = 0; i < q.length; i++) {
			if (q[i][0] == 1) {
				ywall[q[i][2]][q[i][1]]++;
			}
			if (q[i][0] == 2) {
				xwall[q[i][2]][q[i][1]]++;
			}
		}

		DisjointSet set = new DisjointSet(N*M);
		for (int y = M-1; y >= 0; y--) {
			for (int x = N-1; x >= 0; x--) {
				set.make_set(y * N + x);
				for (int i = 0; i < dx.length; i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					if ((nx > x || ny > y) && nx >= 0 && nx < xwall[0].length && ny >= 0 && ny < xwall.length) {
						if ((dx[i] == 1 && xwall[y][x] == 0) || (dx[i] == -1 && xwall[y][x-1] == 0) || (dy[i] == 1 && ywall[y][x] == 0) || (dy[i] == -1 && ywall[y-1][x] == 0)) {
							set.union_sets(y * N + x, ny * N + nx);
						} 
					}
				}
			}
		}
		
		long qsum = 0;
		for (int i = q.length-1; i >= 0; i--) {
			if (q[i][0] == 1) {
				int y = q[i][2];
				int x = q[i][1];
				ywall[y][x]--;
				if (ywall[y][x] == 0) {
					set.union_sets(y*N + x, (y+1)*N + x);
				}
			}
			if (q[i][0] == 2) {
				int y = q[i][2];
				int x = q[i][1];
				xwall[y][x]--;
				if (xwall[y][x] == 0) {
					set.union_sets(y*N + x, y*N + x+1);
				}
			}
			if (q[i][0] == 3) {
				int y = q[i][2];
				int x = q[i][1];
				int y2 = q[i][4];
				int x2 = q[i][3];
				if (set.find_set(y*N + x) == set.find_set(y2*N + x2)) {
					qsum++;
				}
			}
			if (q[i][0] == 4) {
				qsum += (long)set.maxSize;
			}
		}
		
		System.out.println(qsum);
	}
	
	static int[] dy = {0, 1, 0, -1};
	static int[] dx = {1, 0, -1, 0};
	

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
