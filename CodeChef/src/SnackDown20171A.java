import java.io.*;
import java.util.*;

public class SnackDown20171A {
    public static InputReader in;
    public static PrintWriter out;
	public static final boolean debug = false;
 
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, false);
        
//        ISSNAKE();
//        testISSNAKE();
//        SNTEMPLE();
        PROTEPOI();
        
        out.close();
    }
    
    public static void PROTEPOI() {
    	int tests = in.nextInt();
    	for (int test = 0; test < tests; test++) {
    		int n = in.nextInt();
    		int k = in.nextInt();
    		int m = in.nextInt();
    		int[][] snakes = new int[m][];
    		for (int i = 0; i < snakes.length; i++) {
				snakes[i] =in.nextIntArray(4); 
			}
    		PROTEPOI(n, k, m, snakes);
    	}
    }
    
    public static void PROTEPOI(int n, int k, int m, int[][] snakes) {
    	// split into snakes into x,y perspectives
    	
    	ArrayList<Point> x = new ArrayList<>();
    	ArrayList<Point> y = new ArrayList<>();
    	
    	for (int i = 0; i < snakes.length; i++) {
			x.add(new Point(snakes[i][0], snakes[i][2]));
		}
    	
    	for (int i = 0; i < snakes.length; i++) {
			y.add(new Point(snakes[i][1], snakes[i][3]));
		}
    	
    	int minX = getMin(n, k, m, x);
    	int minY = getMin(n, k, m, y);
    	if (minX == -1 || minY == -1) {
    		System.out.println(-1);
    	} else {
    		System.out.println(minX + minY);
    	}
	}

	public static int getMin(int n, int k, int m, ArrayList<Point> pointList) {
		Collections.sort(pointList);
		// compress
		TreeMap<Integer, Integer> comp = new TreeMap<>();
		
		for (int i = 0; i < m; i++) {
			comp.put(pointList.get(i).x, 0);
			comp.put(pointList.get(i).y+1, 0);
		}
		int count  = 0;
		int start = (n - k)/2 + 1;
		int end = start + k-1;

		comp.put(start, 0);
		comp.put(end, 0);
		
		int[] revMap = new int[m*2 +2]; 
		
		for (Integer key: comp.keySet()) {
			comp.put(key, count);
			revMap[count] = key;
			count++;
		}
		int[] lengthList = new int[comp.size() +1];
		for (int i = 0; i < pointList.size(); i++) {
			int idx = comp.get(pointList.get(i).x);
			lengthList[idx] = Math.max(lengthList[idx], pointList.get(i).y);
		}
		
		SegmentTree st = new SegmentTree(lengthList);
		
		int numSnakes = 0;
		int pos = comp.get(start);
		while (pos <= comp.get(end)) {
			// get longest snake that starts at POS or before
			int snakeEnd = st.get(0, pos);
			int oldPos = pos;
			pos = comp.get(snakeEnd+1);
			if (oldPos == pos) {
				// stuck, nothing available
				return -1;
			}
			numSnakes++;
			if (debug) {
				System.out.println("picked snake ending at " +snakeEnd);
			}
		}
		if (debug) {
			System.out.println("finished with "+numSnakes+" snakes");
		}
	
		System.currentTimeMillis();
		return numSnakes;
	}

	public static class SegmentTree {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 * 
		 * t[width == N][height == n] is essentially a binary tree structure with the root at t[0][n], children at t[0][n-1] and t[N/2][n-1], and so forth. 
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return Math.max(a, b);
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = 0;

		public SegmentTree(int[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new int[N];
			for (int i = 0; i < b.length; i++) {
				this.a[i] = b[i];
			}
			t = new int[N][n+1];
			for (int x = 0; x < N; x++) {
				t[x][0] = a[x];
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
				}
			}
		} 

		/**
		 * Set position i to v. Time: O(log n)
		 */
		public void set(int i, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public int get(int i, int j) {
			int res = IDENTITY, height = 0; j++;
			while (i+(1<<height) <= j) {
				while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			while (i < j) {
				while (i+(1<<height) > j) height--;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			return res;
		}
	}

	public static void SNTEMPLE() {
    	int tests = in.nextInt();
    	for (int test = 0; test < tests; test++) {
    		int[] a = in.nextIntArray(in.nextInt());
    		System.out.println(SNTEMPLE(a));
    	}
    }
    
    public static long SNTEMPLE(int[] a) {
    	int N = a.length;

    	int[] lineA = getLine(a, N);
    	int[] lineB = reverse(getLine(reverse(a), N));
    	long max = 0;
    	
    	for (int x = 0; x < N; x++) {
    		// binary search
    		int low = 0;
    		int high = lineA[x];
    		long best = 0;
    		while (low <= high) {
    			int mid = (low + high) >>> 1;
    			int len = mid;
    			int bPos = 2*(len-1) + x;
    			if (bPos < x) {
    				bPos = x;
    			}
    			if (bPos < N && lineB[bPos] >= len) {
    				best = Math.max(best, len);
    				low = mid + 1;
    			} else {
    				high = mid - 1;
    			}
    		}
    		long area = best * best;
//    		System.out.println(area);
    		max = Math.max(max, area);
		}
    	
    	long totalArea = 0;
    	for (int i = 0; i < a.length; i++) {
			totalArea += a[i];
		}
    	
//    	System.out.println(Arrays.toString(a));
//    	System.out.println(Arrays.toString(lineA));
//    	System.out.println(Arrays.toString(lineB));
    	return totalArea - max;
	}
    
    public static int[] reverse(int[] a) {
    	int N = a.length;
    	int[] reverseA = new int[N];
    	for (int i = 0; i < reverseA.length; i++) {
    		reverseA[i] = a[N - i - 1];
		}
    	return reverseA;
    }

    /**
     * Get longest line for each position in a.
     */
    public static int[] getLine(int[] a, int N) {
    	int y = 0;
    	int startX = 0;
    	int endX = 0;
		int[] lineA = new int[N];
		while (endX < N) {
			while (endX < N && y <= a[endX]) {
				if (y == a[endX]) {
					lineA[startX] = endX - startX;
					startX++;
					endX++;
				} else {
					endX++;
					y++;
				}
			}
			if (endX == N) {
				while (startX < endX) {
					lineA[startX] = endX - startX;
					startX++;
					y--;
				}
			} else {
				while (startX <= endX && y > a[endX]) {
					lineA[startX] = endX - startX;
					startX++;
					y--;
				}
			}
		}
		return lineA;
	}

	public static void testISSNAKE() {
    	int tests = 10000;
    	Random r = new Random(0);
    	for (int test = 0; test < tests; test++) {
    		char[][] t = new char[2][r.nextInt(10)+1];
    		for (int i = 0; i < t[0].length; i++) {
    			for (int j = 0; j < t.length; j++) {
    				if (r.nextDouble() < 0.333) {
    					t[j][i] = '.';
    				} else {
    					t[j][i] = '#';
    				}
				} 
			}
    		if (slowISSNAKE(t, r) != ISSNAKE(t)) {
    			System.out.println("fail");
    			System.currentTimeMillis();
    		}
    		
    	}
    }
    
    public static boolean slowISSNAKE(char[][] t, Random r) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < t[0].length; i++) {
			list.add(new char[]{t[0][i], t[1][i]});
		}
		int[] dx = {-1, 0, 0, 1};
		int[] dy = {0, 1, -1, 0};
		
		int scales = 0;
		for (int x = 0; x < list.size(); x++) {
			for (int y = 0; y < 2; y++) {
				if (list.get(x)[y] == '#') {
					scales++;
				}
			}
		}
		
		for (int tries = 0; tries < 1000; tries++) {
			for (int x = 0; x < list.size(); x++) {
				for (int y = 0; y < 2; y++) {
					if (list.get(x)[y] == '#') {
						Point top = new Point(x, y);
						Stack<Point> s = new Stack<>();
						s.push(top);
						boolean[][] visited = new boolean[list.size()][2];
						int numVisited = 0;
						while (!s.isEmpty()) {
							top = s.pop();
							if (visited[top.x][top.y]) {
								continue;
							}
							visited[top.x][top.y] = true;
							numVisited++;
							
							ArrayList<Point> possible = new ArrayList<>();
							for (int i = 0; i < dy.length; i++) {
								Point p = new Point(top.x + dx[i], top.y + dy[i]);
								if (p.x >= 0 && p.x < list.size() && p.y >= 0 && p.y < 2 && list.get(p.x)[p.y] == '#' && !visited[p.x][p.y]) {
									possible.add(p);
								}
							}
							if (!possible.isEmpty()) {
								s.add(possible.get(r.nextInt(possible.size())));
							}
						}
						if (numVisited == scales) {
							return true;
						}
					}
				}
			}
			
		}
    	return false;
    }
    
    public static void ISSNAKE() {
    	int tests = in.nextInt();
    	for (int test = 0; test < tests; test++) {
			int len = in.nextInt();
			char[][] t = new char[2][len];
			t[0] = in.next().toCharArray();
			t[1] = in.next().toCharArray();
			if (ISSNAKE(t)) {
				System.out.println("yes");
			} else {
				System.out.println("no");
			}
    	}
    }
    	
    public static boolean ISSNAKE(char[][] t) {
		ArrayList<char[]> list = new ArrayList<>();
		for (int i = 0; i < t[0].length; i++) {
			list.add(new char[]{t[0][i], t[1][i]});
		}
		int[] dx = {0, 0, 1};
		int[] dy = {1, -1, 0};
		
		int scales = 0;
		for (int x = 0; x < list.size(); x++) {
			for (int y = 0; y < 2; y++) {
				if (list.get(x)[y] == '#') {
					scales++;
				}
			}
		}
		if (scales == 0) {
			return false;
		}

		boolean connected = false;
		for (int x = 0; x < list.size(); x++) {
			for (int y = 0; y < 2; y++) {
				if (list.get(x)[y] == '#') {
					Point top = new Point(x, y);
					Stack<Point> s = new Stack<>();
					s.push(top);
					boolean[][] visited = new boolean[list.size()][2];
					int numVisited = 0;
					while (!s.isEmpty()) {
						top = s.pop();
						if (visited[top.x][top.y]) {
							continue;
						}
						visited[top.x][top.y] = true;
						numVisited++;
						for (int i = 0; i < dy.length; i++) {
							Point p = new Point(top.x + dx[i], top.y + dy[i]);
							if (p.x >= 0 && p.x < list.size() && p.y >= 0 && p.y < 2 && list.get(p.x)[p.y] == '#' && !visited[p.x][p.y]) {
								s.push(p);
							}
						}
					}
					if (numVisited == scales) {
						connected = true;
					} else {
						x = list.size();
						y = 2;
						break;
					}
				}
			}
		}
		if (!connected) {
			return false;
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (i >= 1) {
				boolean notfull = false;
				for (int j = 0; j < 2; j++) {
					for (int k = 0; k < 2; k++) {
						if (list.get(i-j)[k] != '#') {
							notfull = true;
						}
					}
				}
				if (!notfull) {
					list.remove(i-1);
					list.remove(i-1);
					i -= 2;
				}
			}
		}

		
		scales = 0;
		for (int x = 0; x < list.size(); x++) {
			for (int y = 0; y < 2; y++) {
				if (list.get(x)[y] == '#') {
					scales++;
				}
			}
		}
		boolean legend = true;
		
		for (int x = 0; x < list.size(); x++) {
			for (int y = 0; y < 2; y++) {
				if (list.get(x)[y] == '#') {
					if (x + 1 < list.size() && list.get(x)[0] == '#' && list.get(x)[1] == '#') {
						if (list.get(x+1)[y] == '#') {
							continue;
						}
					}
					Point top = new Point(x, y);
					Stack<Point> s = new Stack<>();
					s.push(top);
					boolean[][] visited = new boolean[list.size()][2];
					int numVisited = 0;
					while (!s.isEmpty()) {
						top = s.pop();
						int numNeighbours = 0;
						visited[top.x][top.y] = true;
						numVisited++;
						for (int i = 0; i < dy.length; i++) {
							Point p = new Point(top.x + dx[i], top.y + dy[i]);
							if (p.x >= 0 && p.x < list.size() && p.y >= 0 && p.y < 2 && list.get(p.x)[p.y] == '#' && !visited[p.x][p.y]) {
								s.push(p);
								numNeighbours++;
							}
						}
						if (numNeighbours > 1) {
							legend = false;
							break;
						}
					}
					if (legend && connected && numVisited == scales) {
						return true;
					} else {
						return false;
					}
					
				}
			}
		}
		
		if (scales == 0) {
			return true;
		}
		return false;
    }
    
    public static class Point implements Comparable<Point> {
    	int x,y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return "(" + x + ", " + y + ")";
		}

		@Override
		public int compareTo(Point o) {
			if (x == o.x) {
				return Integer.compare(y, o.y);
			}
			return Integer.compare(x, o.x);
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
  