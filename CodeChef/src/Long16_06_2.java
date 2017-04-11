import java.awt.geom.Point2D;
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

public class Long16_06_2 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

//		testMGCHGEOM();
//		MGCHGEOM();
		
//		testCHNWGM();
//		CHNWGM();
		
//		testSADPAIRS();
//		prefSADPAIRS();
		SADPAIRS();
//		testFRJUMP();
//		perfFRJUMP();
//		FRJUMP();
//		prefCHSQARR();
//		testCHSQARR();
//		CHSQARR();
//		testCHEFARK();
//		testCHEFARK2();
//	  	CHEFARK();
//		testCHEARMY();
//		CHEARMY();
//		BINOP();
//		CHCOINSG(System.in);
//		DEVARRAY(System.in);

		out.close();
	}
	
	
	public static void testMGCHGEOM() {
		int tests = 50000;
		long buffer = 100000000;
		long fact =   1000000000;
		for (int test = 0; test < tests; test++) {
			int qs = 100;
			HashSet<Long> set = new HashSet<>();
			for (int i = 0; i < qs; i++) {
				long x = ra.nextInt(10) - 5;
				long y = ra.nextInt(10) - 5;
				if (ra.nextBoolean()) {
					set.add((x + buffer) * fact + y + buffer);
				} else {
					set.remove((x + buffer) * fact + y + buffer);
				}
				Point[] p = new Point[set.size()];
				ArrayList<Point> hull = new ArrayList<>();
				int t = 0;
				for (Long s: set) {
					int y2 = (int) ((s % fact) - buffer);
					int x2 = (int) ((s / fact) - buffer);
					p[t] = new Point(x2, y2);
					t++;
				}
				if (set.size() > 2) {
					convexHull(p, p.length, hull);
				}
				double area = 0;
				for (int j = 0; j < hull.size(); j++) {
					long x1 = hull.get(j).x;
					long x2 = hull.get((j+1) % hull.size()).x;
					long y1 = hull.get(j).y;
					long y2 = hull.get((j+1) % hull.size()).y;
					area += x1*y2-x2*y1;
				}
				area = 0.5*Math.abs(area);
				if (area == -1000000000) {
					System.out.println();
				} 
			}
		}
	}

	public static void MGCHGEOM() {
		int tests = in.nextInt();
		long buffer = 100000000;
		long fact =   1000000000;
		for (int test = 0; test < tests; test++) {
			int qs = in.nextInt();
			HashSet<Long> set = new HashSet<>();
			for (int i = 0; i < qs; i++) {
				char q = in.next().toCharArray()[0];
				long x = in.nextLong();
				long y = in.nextLong();
				if (q == '+') {
					set.add((x + buffer) * fact + y + buffer);
				} else {
					set.remove((x + buffer) * fact + y + buffer);
				}
				Point[] p = new Point[set.size()];
				ArrayList<Point> hull = new ArrayList<>();
				int t = 0;
				for (Long s: set) {
					int y2 = (int) ((s % fact) - buffer);
					int x2 = (int) ((s / fact) - buffer);
					p[t] = new Point(x2, y2);
					t++;
				}
				if (set.size() > 2) {
					convexHull(p, p.length, hull);
				}
				double area = 0;
				for (int j = 0; j < hull.size(); j++) {
					long x1 = hull.get(j).x;
					long x2 = hull.get((j+1) % hull.size()).x;
					long y1 = hull.get(j).y;
					long y2 = hull.get((j+1) % hull.size()).y;
					area += x1*y2-x2*y1;
				}
				area = 0.5*Math.abs(area);
				out.println(String.format("%.1f", area));
			}
		}
	}

	/**
	 * convex hull based on:
	 * http://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/
	 *
	 */
	public static class Point {
		long x;
		long y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static Point nextToTop(Stack<Point> S)
	{
		Point p = S.peek();
		S.pop();
		Point res = S.peek();
		S.push(p);
		return res;
	}

	public static Point p0;

	public static long distSq(Point p1, Point p2)
	{
		return (p1.x - p2.x)*(p1.x - p2.x) +
				(p1.y - p2.y)*(p1.y - p2.y);
	}

	public static int orientation(Point p, Point q, Point r)
	{
		long val = (q.y - p.y) * (r.x - q.x) -
				(q.x - p.x) * (r.y - q.y);

		if (val == 0) return 0;  // colinear
		return (val > 0)? 1: 2; // clock or counterclock wise
	}

	// Prints convex hull of a set of n points.
	public static void convexHull(Point points[], int n, ArrayList<Point> hull)
	{
		// Find the bottommost point
		long ymin = points[0].y;
		int min = 0;
		for (int i = 1; i < n; i++)
		{
			long y = points[i].y;
			
			if ((y < ymin) || (ymin == y && points[i].x < points[min].x)) {
				ymin = points[i].y;
				min = i;
			}
		}

		// Place the bottom-most point at first position
		Point temp = points[0];
		points[0] = points[min];
		points[min] = temp;

		p0 = points[0];
		Arrays.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				int o = orientation(p0, p1, p2);
				if (o == 0)
					return (distSq(p0, p2) >= distSq(p0, p1))? -1 : 1;

				return (o == 2)? -1: 1;
			}
		});

		int m = 1; // Initialize size of modified array
		for (int i=1; i<n; i++)
		{
			// Keep removing i while angle of i and i+1 is same
			// with respect to p0
			while (i < n-1 && orientation(p0, points[i],
					points[i+1]) == 0)
				i++;


			points[m] = points[i];
			m++;  // Update size of modified array
		}

		if (m < 3) return;

		Stack<Point> S = new Stack<>();
		S.push(points[0]);
		S.push(points[1]);
		S.push(points[2]);

		// Process remaining n-3 points
		for (int i = 3; i < m; i++)
		{
			while (orientation(nextToTop(S), S.peek(), points[i]) != 2)
				S.pop();
			S.push(points[i]);
		}

		// Now stack has the output points, print contents of stack
		while (!S.empty())
		{
			Point p = S.peek();
			//System.out.println(p.x + " " + p.y);
			hull.add(p);
			S.pop();
		}
	}

	public static void testCHNWGM() {
		double score = 0;
		int tests = 10;
		
		for (int test = 0; test < tests; test++) {
			NewGame game = new NewGame();
			game.debug = true;
			while (true) {
				int[] a = new int[3];
				for (int i = 0; i < a.length; i++) {
					a[i] = ra.nextInt(19)+1;
				}
				if (a[0] == -1 && a[1] == -1 && a[2] == -1) {
					break;
				}
				for (int i = 0; i < a.length; i++) {
					a[i] -= 1;
				}
				String res = game.play(a);
				boolean gameOver = false;
				for (int i = 0; i < res.length(); i++) {
					if (res.charAt(i) == '-') {
						gameOver = true;
					}
				}
				if (gameOver) {
					game.play(a);
					break;
				}
			}
			score += game.score;
			//System.out.println("score: " + game.score);
		}
		System.out.println(score / tests);
	}

	public static void CHNWGM() {
		NewGame game = new NewGame();
		while (true) {
			int[] a = in.nextIntArray(3);
			if (a[0] < 0) {
				break;
			}
			for (int i = 0; i < a.length; i++) {
				a[i] -= 1;
			}
			String s = game.play(a);
			out.println(s);
//			System.out.println(game);
//			if (s.contains("-1")) {
//				break;
//			}
		}
	}

	public static class NewGame {
		final int N = 10; 
		int[][] map;
		int score;
		int moves;
		boolean debug = false;

		public NewGame() {
			map = new int[N][N];
			score = 0;
			moves = 0;
//			for (int i = 0; i < def.length; i++) {
//				int[][] p = def[i];
//				
//				int[][] newP = new int[p.length+2][p[0].length+2];
//				for (int j = 1; j < newP.length-1; j++) {
//					for (int k = 1; k < newP[j].length-1; k++) {
//						newP[j][k] = p[j-1][k-1];
//					}
//				}
//				def[i] = newP;
//			}
			
//			for (int i = 0; i < def.length; i++) {
//				int[][] p = def[i];
//				for (int j = 0; j < p.length; j++) {
//					for (int k = 0; k < p[0].length; k++) {
//						p[j][k] = 1;
//					}
//				}
//				def[i] = p;
//			}
		}

		public int[][] mv = {
				{3,6,5,2,2,2,1,10,8},
				{1,4,8,2,6,1,3,7,2},
				{1,5,8,3,4,8,2,6,8},
				{3,9,8,2,10,7,1,1,6},
				{3,10,8,2,8,3,-1,-1,-1},
		};

		public String play(int[] pieces) {
			boolean clear = false;
			clear = true;
			int[] res = getMoves(pieces);
			if (moves == 8) {
				return "-1 -1 -1 -1 -1 -1 -1 -1 -1";
			}
//			for (int i = 0; i < pieces.length; i++) {
//				if (pieces[i] >= 11) {
//					return "-1 -1 -1 -1 -1 -1 -1 -1 -1";
//				}
//			}
//			res = mv[moves];
			if (debug) {
				System.out.println(Arrays.toString(res));
			}

			int placed = 0;

			boolean gameOver = false;
			for (int i = 0; i < res.length; i++) {
				if (res[i] == -1) {
					gameOver = true;
				}
			}

			if (!gameOver) {
				int clearRows = 0;
				int clearColumns = 0;

				for (int i = 0; i < res.length; i+=3) {
					int[][] p = def[pieces[res[i]-1]];
					int posy = res[i+1] - p.length;
					int posx = res[i+2] - 1;

					int s1 = 0;
					for (int y = 0; y < N; y++) {
						for (int x = 0; x < N; x++) {
							if (map[y][x] > 0) {
								s1++;
							}
						}
					}

					if (debug)
						System.out.println("place "+((char)((pieces[res[i]-1]) + 'A'))+" at " + posx + ", " + posy);

					for (int py = 0; py < p.length; py++) {
						for (int px = 0; px < p[0].length; px++) {
							if (p[py][px] > 0) {
								if (map[posy+py][posx+px] != 0) {
									throw new RuntimeException();
								}
								map[posy+py][posx+px] = pieces[res[i]-1] +1;
								placed++;
							}
						}
					}

					int blockSize = 0;
					for (int py = 0; py < p.length; py++) {
						for (int px = 0; px < p[0].length; px++) {
							if (p[py][px] > 0) {
								blockSize++;
							}
						}
					}

					int s2 = 0;
					for (int y = 0; y < N; y++) {
						for (int x = 0; x < N; x++) {
							if (map[y][x] > 0) {
								s2++;
							}
						}
					}


					if (s2 - s1 != blockSize) {
						throw new RuntimeException();
					} 

					// clear
					boolean[] rows = new boolean[N];
					boolean[] columns = new boolean[N];

					for (int y = 0; y < N; y++) {
						int fill = 0;
						for (int x = 0; x < N; x++) {
							if (map[y][x] > 0) {
								fill++;
							}
						}
						if (fill == N) {
							if (debug)
								System.out.println("clear row " + y);
							rows[y] = true;
							clearRows++;
						}
					}

					for (int x = 0; x < N; x++) {
						int fill = 0;
						for (int y = 0; y < N; y++) {
							if (map[y][x] > 0) {
								fill++;
							}
						}
						if (fill == N) {
							if (debug)
								System.out.println("clear column " + x);
							columns[x] = true;
							clearColumns++;
						}
					}

					for (int y = 0; y < N; y++) {
						for (int x = 0; x < N; x++) {
							if (rows[y] || columns[x]) {
								if (clear) {
									map[y][x] = 0;
								}
							}
						}
					}

					if (debug)
						System.out.println(this);
				}

				int size = 0;
				for (int y = 0; y < N; y++) {
					for (int x = 0; x < N; x++) {
						if (map[y][x] > 0) {
							size++;
						}
					}
				}

				if (size == 0) {
					score += 500;
				}
				score += placed;
				score += clearColumns * clearColumns + clearRows * clearRows + (5 * clearRows * clearColumns);
				moves++;
			}

			String out = "";
			for (int i = 0; i < res.length; i++) {
				if (i > 0) {
					out += " ";
				}
				out += res[i];
			}
			return out;
		}

		public int[] getMoves(int[] pieces) {
			int[] res = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
			int[][] tMap = new int[N][];
			for (int i = 0; i < tMap.length; i++) {
				tMap[i] = new int[N];
				for (int j = 0; j < tMap[0].length; j++) {
					tMap[i][j] = map[i][j];
				}
			}

			for (int i = 0; i < pieces.length; i++) {
				int[][] p = def[pieces[i]];
				for (int y = 0; y < N; y++) {
					for (int x = 0; x < N; x++) {
						boolean valid = true;
						for (int py = 0; py < p.length; py++) {
							for (int px = 0; px < p[0].length; px++) {
								if (y+py >= N || x+px >= N || (p[py][px] > 0 && tMap[y+py][x+px] != 0)) {
									valid = false;
									py = p.length;
									break;
								} 
							}
						}
						if (valid) {
							for (int py = 0; py < p.length; py++) {
								for (int px = 0; px < p[0].length; px++) {
									if (p[py][px] > 0) {
										tMap[y+py][x+px] = pieces[i] +1;
									}
								}
							}
							if ((res[i*3 + 0] != -1) || (res[i*3 + 1] != -1) || (res[i*3 + 2] != -1)) {
								throw new RuntimeException();
							}
							res[i*3 + 0] = i+1;
							res[i*3 + 1] = y+p.length;
							res[i*3 + 2] = x+1;
							x = N;
							y = N;
							break;
						}
					}
				}
			}

			return res;
		}

		public String toString() {
			String out = "";
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					if (map[y][x] > 0) {
						out += (char)('A' + (map[y][x]-1)); 
					} else {
						if (x % 2 == 0) {
							out += ' ';
						} else {
							out += '.';
						}
					}
				}
				out += "|\n";
			}
			for (int x = 0; x < N; x++) {
				out += '-';
			}
			out += "/\n";
			return out;
		}

		int[][][] def = {
				{
					{1},
				},
				{
					{1},
					{1},
				},
				{
					{1, 1},
				},
				{
					{1},
					{1},
					{1},
				},
				{
					{1, 1, 1},
				},
				{
					{1},
					{1},
					{1},
					{1},
				},
				{
					{1, 1, 1, 1},
				},
				{
					{1},
					{1},
					{1},
					{1},
					{1},
				},
				{
					{1, 1, 1, 1, 1},
				},
				{
					{1, 1},
					{1, 1},
				},
				{
					{1, 1, 1},
					{1, 1, 1},
					{1, 1, 1},
				},
				{
					{1, 1, 1},
					{0, 0, 1},
					{0, 0, 1},
				},
				{
					{0, 0, 1},
					{0, 0, 1},
					{1, 1, 1},
				},
				{
					{1, 0, 0},
					{1, 0, 0},
					{1, 1, 1},
				},
				{
					{1, 1, 1},
					{1, 0, 0},
					{1, 0, 0},
				},
				{
					{1, 1},
					{1, 0},
				},
				{
					{1, 1},
					{0, 1},
				},
				{
					{0, 1},
					{1, 1},
				},
				{
					{1, 0},
					{1, 1},
				},
		};
	}

	public static void SADPAIRS() {
		int n = in.nextInt();
		int e = in.nextInt();
		int[] colours = in.nextIntArray(n);
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(i, new ArrayList<Integer>());
		}
		for (int i = 0; i < e; i++) {
			int a = in.nextInt() -1;
			int b = in.nextInt() -1;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		int[] ret = SADPAIRS2(n, g, colours);
		for (int i = 0; i < ret.length; i++) {
			out.println(ret[i]);
		}
	}
	
	public static void testSADPAIRS() {
		for (int t = 0; t < 1000; t++) {
 
			int n = ra.nextInt(50)+5;
			int e = ra.nextInt(n*(n-1)/2 + 1);
			int[] x = new int[n];
			for (int i = 0; i < n; i++) {
				x[i] = ra.nextInt(100);
			}
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				g.add(i, new ArrayList<Integer>());
			}
			for (int i = 0; i < e; i++) {
				int a = ra.nextInt(n);
				int b = ra.nextInt(n);
				while (a == b) {
					b = ra.nextInt(n);
				}
				g.get(a).add(b);
				g.get(b).add(a);
			}
			int[] ret1 = SADPAIRS3(n, g, x);
			int[] ret2 = SADPAIRS2(n, g, x);
			for (int i = 0; i < ret2.length; i++) {
				if (ret1[i] != ret2[i]) {
					System.out.println("fail");
				}
			}
		}
	}
	
	public static void prefSADPAIRS() {
		int componentSize = 60;
		long t2 = System.currentTimeMillis();
		for (int t = 0; t < 10; t++) {
			long t1 = System.currentTimeMillis();
			int n = 10*10000;
			int e = 10*160 * componentSize;
			int[] x = new int[n];
			for (int i = 0; i < n; i++) {
				x[i] = ra.nextInt(n);
			}
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				g.add(i, new ArrayList<Integer>());
			}
			int components = e/componentSize;
			for (int i = 0; i < components; i++) {
				for (int j = 0; j < componentSize; j++) {
					int a = ra.nextInt(componentSize) + i*componentSize;
					int b = ra.nextInt(componentSize) + i*componentSize;
					g.get(a).add(b);
					g.get(b).add(a);
				}
			}
			int[] ret2 = SADPAIRS2(n, g, x);
			t1 = System.currentTimeMillis() - t1;
			out.println(t1);
		}
		t2 = System.currentTimeMillis() - t2;
		//out.println("t2: " + t2);
	}

	public static int[] SADPAIRS3(int N, ArrayList<ArrayList<Integer>> g, int[] colours) {
		int[] ret = new int[N];
		for (int i = 0; i < N; i++) {
			ret[i] = numDisconnectedPairs3(N, g, colours, i);
		}
		return ret;
	}

	public static int numDisconnectedPairs3(int N,	ArrayList<ArrayList<Integer>> g, int[] colours, int disconnected) {		
		int sad = 0;
		for (int a = 0; a < N; a++) {
			for (int b = a+1; b < N; b++) {
				if (colours[a] == colours[b] && !canReach(a, b, g, disconnected)) {
					sad++;
				}
			}
		}
		return sad;
	}

	public static boolean canReach(int a, int b, ArrayList<ArrayList<Integer>> g, int di) {
		BitSet visited;
		int disconnected;
		visited = new BitSet();
		disconnected = di;

		Stack<Integer> s = new Stack<>();
		int top = a;
		s.add(top);

		while (!s.isEmpty()) {
			top = s.pop();
			visited.set(top);
			if (top == b) {
				return true;
			}
			if (top != disconnected) {
				for (int i = 0; i < g.get(top).size(); i++) {
					int child = g.get(top).get(i);
					if (!visited.get(child) && child != disconnected) {
						s.add(child);
					}
				}
			}
		}
		return false;
	}

	public static int[] SADPAIRS2(int N, ArrayList<ArrayList<Integer>> g, int[] colours) {
		long t1 = System.currentTimeMillis();

		CutVertices cv = new CutVertices(g, -1);
		ArrayList<HashSet<Integer>> components = new ArrayList<>();
		HashMap<Integer, HashSet<Integer>> colourGroups = new HashMap<>();
		int[] ret = new int[N];
		int baseSad = 0;

		//		System.out.println("a: " + (System.currentTimeMillis() - t1));
		//		t1 = System.currentTimeMillis();

		for (int i = 0; i < colours.length; i++) {
			if (!colourGroups.containsKey(colours[i])) {
				colourGroups.put(colours[i], new HashSet<>());
			}
		}

		//		System.out.println("b: " + (System.currentTimeMillis() - t1));
		//		t1 = System.currentTimeMillis();

		for (int a = 0; a < N; a++) {
			colourGroups.get(colours[a]).add(a);
		}

		//		System.out.println("c: " + (System.currentTimeMillis() - t1));
		//		t1 = System.currentTimeMillis();

		int[] localGroupSize = new int[N];

		for (Integer g2: colourGroups.keySet()) {
			HashSet<Integer> group = colourGroups.get(g2);
			HashMap<Integer, Integer> component = new HashMap<>();
			int gs = 0;
			for (Integer j: group) {
				Integer x = component.get(cv.component[j]);
				if (x == null) {
					component.put(cv.component[j], 1);
				} else {
					component.put(cv.component[j], x+1);
				}
				gs++;
			}
			for (Integer j: group) {
				localGroupSize[j] = component.get(cv.component[j]);
			}
			for (Integer j: component.keySet()) {
				baseSad += (gs - component.get(j)) * component.get(j);
			}
		}
		baseSad /= 2;

		//		System.out.println("d: " + (System.currentTimeMillis() - t1));
		//		t1 = System.currentTimeMillis();

		for (int i = 0; i < cv.numComponents; i++) {
			components.add(new HashSet<Integer>());
		}
		for (int i = 0; i < N; i++) {
			components.get(cv.component[i]).add(i);
		}

		for (int i = 0; i < N; i++) {
			ret[i] = baseSad;
			if (cv.cutVertex[i]) {
				ret[i] += numDisconnectedPairs2(N, g, colours, i, components.get(cv.component[i]));
			} else {
				ret[i] += localGroupSize[i]-1;
			}
		}

		//		System.out.println("e: " + (System.currentTimeMillis() - t1));
		//		t1 = System.currentTimeMillis();
		return ret;
	}

	public static int numDisconnectedPairs2(int N,	ArrayList<ArrayList<Integer>> g, int[] colours, int disconnected, HashSet<Integer> component) {
		CutVertices2 cv = new CutVertices2(g, disconnected, component);

		int sad = 0;
		HashMap<Integer, HashSet<Integer>> colourGroups = new HashMap<>();

		for (Integer a: component) {
			if (!colourGroups.containsKey(colours[a])) {
				colourGroups.put(colours[a], new HashSet<>());
			}
			colourGroups.get(colours[a]).add(a);
		}

		for (Integer g2: colourGroups.keySet()) {
			HashSet<Integer> group = colourGroups.get(g2);
			HashMap<Integer, Integer> component1 = new HashMap<>();
			int groupSize = 0;
			for (Integer j: group) {
				Integer x = component1.get(cv.component.get(j));
				if (x == null) {
					component1.put(cv.component.get(j), 1);
				} else {
					component1.put(cv.component.get(j), x+1);
				}
				groupSize++;
			}
			for (Integer j: component1.keySet()) {
				sad += (groupSize - component1.get(j)) * component1.get(j);
			}
		}
		sad /= 2;
		return sad;
	}

	public static class CutVertices2 {
		ArrayList<ArrayList<Integer>> g;
		BitSet visited;
		int numComponents;
		HashMap<Integer, Integer> component;
		int disconnected;

		public CutVertices2(ArrayList<ArrayList<Integer>> g, int di, HashSet<Integer> comp) {
			this.g = g;
			visited = new BitSet();
			component = new HashMap<>();
			disconnected = di;

			numComponents = 0;
			for (Integer i: comp) {
				if (!visited.get(i)) {
					numComponents++;
					component.put(i, numComponents);
					dfs(i);
				}
			}
		}

		public void dfs(int top) {
			visited.set(top);

			if (top != disconnected) {
				for (int i = 0; i < g.get(top).size(); i++) {
					int child = g.get(top).get(i);
					if (!visited.get(child) && child != disconnected) {
						component.put(child, component.get(top));
						dfs(child);
					}
				}
			}
		}
	}

	public static int[] SADPAIRS(int N, ArrayList<ArrayList<Integer>> g, int[] colours) {
		int[] ret = new int[N];
		for (int i = 0; i < N; i++) {
			ret[i] = numDisconnectedPairs(N, g, colours, i);
		}
		return ret;
	}

	public static int numDisconnectedPairs(int N,	ArrayList<ArrayList<Integer>> g, int[] colours, int disconnected) {
		CutVertices cv = new CutVertices(g, disconnected);

		int sad = 0;
		for (int a = 0; a < N; a++) {
			for (int b = a+1; b < N; b++) {
				if (colours[a] == colours[b] && cv.component[a] != cv.component[b]) {
					sad++;
				}
			}
		}
		return sad;
	}

	public static class CutVertices {
		int[] depth;
		int[] parent;
		int[] low;
		int[] color;
		int[] component;
		boolean[] cutVertex;
		ArrayList<ArrayList<Integer>> g;
		int N;
		int disconnected;
		int numComponents;

		public CutVertices(ArrayList<ArrayList<Integer>> g, int di) {
			N = g.size();
			this.g = g;
			depth = new int[N];
			parent = new int[N];
			low = new int[N];
			color = new int[N];
			component = new int[N];
			cutVertex = new boolean[N];
			disconnected = di;

			numComponents = 0;
			for (int i = 0; i < color.length; i++) {
				if (color[i] == 0) {
					parent[i] = -1;
					component[i] = numComponents++;
					dfs(i, 0);
				}
			}
		}

		public void dfs(int top, int dep) {
			depth[top] = dep;
			low[top] = depth[top];

			// white = 0, grey = 2, black = 3
					color[top] = 2;
			int childCount = 0;
			boolean art = false;

			if (top != disconnected) {
				for (int i = 0; i < g.get(top).size(); i++) {
					int child = g.get(top).get(i);
					if (color[child] == 0 && child != disconnected) {
						parent[child] = top;
						component[child] = component[top];

						dfs(child, dep+1);
						childCount++;
						if (low[child] >= depth[top]) {
							art = true;
						}
						low[top] = Math.min(low[top], low[child]);
					} else if (child != parent[top]) {
						low[top] = Math.min(low[top], depth[child]);
					}
				}
			}
			if ((parent[top] != -1 && art) || (parent[top] == -1 && childCount > 1)) {
				cutVertex[top] = true;
			}
			color[top] = 3;
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


	private static int slowCHEFARK(int K, int[] a) {
		int[] count = new int[a.length];
		HashSet<Integer> set = new HashSet<>();
		int base = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < 0) {
				base = setBit(base, i);
			}
		}
		do {
			int sum = 0;
			for (int i = 0; i < count.length; i++) {
				sum += count[i];
			}
			if (sum == K) {
				int x = base;
				for (int i = 0; i < a.length; i++) {
					if (a[i] != 0) {
						for (int j = 0; j < count[i]; j++) {
							if (getBit(x, i)) {
								x = clearBit(x, i);
							} else {
								x = setBit(x, i);
							}
						}
					}
				}
				set.add(x);
			}
		} while (next_number(count, K+1));
		return (int) (set.size() % mod);
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

	public static void CHEFARK() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = in.nextInt();
			int k = in.nextInt();
			int[] a = in.nextIntArray(n);
			out.println(CHEFARK(n, k, a));
		}
	}

	public static long CHEFARK(long n, long k, int[] a) {
		initInv();

		boolean zero = false;
		long ones = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				zero = true;
				n--;
			} else if (a[i] < 0) {
				//ones++;
			}
		}
		if (n == 0) {
			return 1;
		}
		long combin = 0;
		long sum = 0;
		long kLow = ones;
		long kHigh = ones;

		for (long i = 0; i < k; i++) {
			if (kLow == 0) {
				if (!zero) {
					kLow++;
				}
			} else {
				kLow--;
			}
			if (kHigh == n) {
				if (!zero) {
					kHigh--;
				}
			} else {
				kHigh++;
			}
		}

		//System.out.print(n+","+k+" combinations: ");
		for (long i = 0; i <= kHigh; i++) {
			//for (int i = 0; i <= n; i++) {
			if (i == 0) {
				combin = 1;
			} else {
				combin = ((((n - i + 1L) * combin) % mod) * inv[(int) i]) % mod;
			}
			//System.out.print(combin + " ");
			if (kLow <= i && i <= kHigh) {
				if ((i % 2L == kLow % 2L) || zero) {
					sum = (sum + combin) % mod;
				}
			}
		}
		//System.out.println();
		if (sum < 0) {
			return a[-1];
		}
		return sum;
	}

	public static final long mod = 1000000007;
	//	public static final long mod = 17;
	public static final int etm = eulerTotientFunction((int) mod) - 1;
	public static long[] inv = null;

	public static void initInv() {
		if (inv == null) {
			inv = new long[100001];
			for (int i = 1; i < inv.length; i++) {
				inv[i] = (fastModularExponent(i, etm, (int)mod) % mod);
			}
		}
	}

	public static int fastModularExponent(int a, int exp, int mod) {
		long[] results = new long[65];
		long m = mod;
		int power = 1;
		long res = 1;
		while (exp > 0) {
			if (power == 1) {
				results[power] = a % m;
			} else {
				results[power] = (results[power-1] * results[power-1]) % m;
			}
			if (exp % 2 == 1) {
				res = (res * results[power]) % m;
			}
			exp /= 2;
			power++;
		}
		return (int) (res % m);
	}

	public static int eulerTotientFunction(int n) {
		int result = n;
		for (int i = 2; i*i <= n; i++) {
			if (n % i == 0) {
				result -= result / i;
			}
			while (n % i == 0) {
				n /= i;
			}
		}

		if (n > 1) {
			result -= result / n;
		}
		return result;
	}

	public static long CHEFARK2(int n, int k, int[] a) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				n--;
			}
		}
		long mod = 1000000007;
		long combin = 1;
		long sum = k % 2 == 0 ? 1 : 0;

		for (int i = 1; i <= k && i <= n; i++) {
			combin = (combin * ((n - i + 1) / i)) % mod;
			if (k % 2 == i % 2) {
				sum = (sum + combin) % mod; 
			}
		}
		return sum;
	}

	public static void FRJUMP() {
		int n = in.nextInt();
		long[] a = in.nextLongArray(n);
		int m = in.nextInt();
		int[][] q = new int[m][3];
		for (int i = 0; i < m; i++) {
			q[i][0] = in.nextInt();
			q[i][1] = in.nextInt();
			if (q[i][0] == 1) {
				q[i][2] = in.nextInt();
			}
		}
		ArrayList<String> ret = fastFRJUMP(n, a, m, q);
		for (int i = 0; i < ret.size(); i++) {
			out.println(ret.get(i));
		}
	}

	public static void testFRJUMP() {
		for (int test = 0; test < 1000; test++) {
			int n = 1000;
			int Q = 1000;
			int bound = n;
			int numBound = 3;
			for (int i = 0; i < 20; i++) {
				long[] a = new long[n];
				for (int j = 0; j < a.length; j++) {
					a[j] = ra.nextInt(numBound)+1;
				}
				int[][] q = new int[Q][3];
				for (int j = 0; j < q.length; j++) {
					if (ra.nextBoolean()) {
						q[j][0] = 2;
						q[j][1] = ra.nextInt(bound)+1;
					} else {
						q[j][0] = 1;
						q[j][1] = ra.nextInt(bound)+1;
						q[j][2] = ra.nextInt(numBound)+1;
					}
				}
				ArrayList<String> ret1 = FRJUMP(a.length, a, q.length, q);
				ArrayList<String> ret2 = fastFRJUMP(a.length, a, q.length, q);
				for (int j = 0; j < ret1.size(); j++) {
					if (!ret1.get(j).equals(ret2.get(j))) {
						System.out.println("fail");
						FRJUMP(a.length, a, q.length, q);
						fastFRJUMP(a.length, a, q.length, q);
					}
				}
			}

		}
	}

	public static void perfFRJUMP() {
		int n = 100000;
		int Q = 100000;
		int bound = 100000;
		for (int i = 0; i < 20; i++) {
			long[] a = new long[n];
			for (int j = 0; j < a.length; j++) {
				a[j] = ra.nextInt(1000000000)+1;
			}
			int[][] q = new int[Q][3];
			for (int j = 0; j < q.length; j++) {
				if (ra.nextBoolean()) {
					q[j][0] = 2;
					q[j][1] = ra.nextInt(bound)+1;
				} else {
					q[j][0] = 1;
					q[j][1] = ra.nextInt(bound)+1;
					q[j][2] = ra.nextInt(1000000000)+1;
				}
			}
			long t1 = System.currentTimeMillis();
			ArrayList<String> ret = fastFRJUMP(a.length, a, q.length, q);
			t1 = System.currentTimeMillis() - t1;
			out.println(t1);
		}
	}

	public static ArrayList<String> fastFRJUMP(int N, long[] ain, int M, int[][] q) {
		long[] a = Arrays.copyOf(ain, ain.length);
		int S = 501;
		long[] pInt = new long[S];
		double[] pFloat = new double[S];
		long[] afterInt = new long[S];
		double[] afterFloat = new double[S];
		long startInt = 1;
		double startFloat = 1.0;

		Arrays.fill(afterFloat, 1.0);
		Arrays.fill(afterInt, 1);

		for (int i = 1; i < S; i++) {
			long prod = 1;
			double man = 1;
			int step = i;
			int k = 1;
			for (int j = 0; j < N; j+=step) {
				prod = (prod * a[j]) % mod;
				man *= a[j];
				k++;
				if (k % 10 == 0) {
					while (man > 10.0) {
						man /= 10.0;
					}
				}
			}
			while (man > 10.0) {
				man /= 10.0;
			}
			pInt[i] = prod;
			pFloat[i] = man;
		}

		ArrayList<String> ret = new ArrayList<>();
		for (int i = 0; i < q.length; i++) {
			if (q[i][0] == 1) {
				int idx = q[i][1] - 1;
				double ratio = q[i][2] / (double)a[idx];
				long iratio = ((long)q[i][2] * fastModularExponent((int) a[idx], etm, (int)mod)) % mod; 
				a[idx] = q[i][2];
				for (int j = 1; j < S; j++) {
					if (idx % j == 0) {
						afterInt[j] = (afterInt[j] * iratio) % mod;
						afterFloat[j] *= ratio;
						while (afterFloat[j] > 10.0) {
							afterFloat[j] /= 10.0;
						}
						while (afterFloat[j] < 1.0) {
							afterFloat[j] *= 10.0;
						}
					}
				}
			} else {
				long prod = startInt;
				double man = startFloat;
				int step = q[i][1];
				if (step < S) {
					prod = (pInt[step] * afterInt[step]) % mod;
					man = pFloat[step] * afterFloat[step];
					while (man < 1.0) {
						man *= 10.0;
					}
					if (Math.ceil(man) - man < 0.0000000001d) {
						man = Math.ceil(man); 
					}
				} else {
					int k = 1;
					for (int j = 0; j < N; j+=step) {
						prod = (prod * a[j]) % mod;
						man *= a[j];
						k++;
						if (k % 10 == 0) {
							while (man > 10.0) {
								man /= 10.0;
							}
						}
					}
				}
				ret.add(Double.toString(man).substring(0, 1) + " " + prod);
			}
		}
		return ret;
	}

	public static ArrayList<String> FRJUMP(int N, long[] ain, int M, int[][] q) {
		long[] a = Arrays.copyOf(ain, ain.length);
		ArrayList<String> ret = new ArrayList<>();
		for (int i = 0; i < q.length; i++) {
			if (q[i][0] == 1) {
				a[q[i][1] - 1] = q[i][2];
			} else {
				long prod = 1;
				double man = 1;
				int step = q[i][1];
				int k = 1;
				for (int j = 0; j < N; j+=step) {
					prod = (prod * a[j]) % mod;
					man *= a[j];
					k++;
					if (k % 10 == 0) {
						while (man > 10.0) {
							man /= 10.0;
						}
					}
				}
				ret.add(Double.toString(man).substring(0, 1) + " " + prod);
			}
		}
		return ret;
	}

	public static void prefCHSQARR() {
		Random rand = new Random(1);
		for (int k = 0; k < 1; k++) {
			long t1 = System.currentTimeMillis();

			int[][] q = new int[1000][2];
			int N = 1000;
			int M = N;
			int[][] mat = new int[N][M];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					mat[i][j] = rand.nextInt(1000);
				}
			}
			for (int i = 0; i < q.length; i++) {
				//				q[i][0] = rand.nextInt(20)+1;
				//				q[i][1] = rand.nextInt(20)+1;
				//				q[i][0] = rand.nextInt(200)+1;
				//				q[i][1] = rand.nextInt(200)+1;
				q[i][0] = rand.nextInt(N)+1;
				q[i][1] = rand.nextInt(M)+1;
				//				q[i][0] = N/20;
				//				q[i][1] = N/20;
			}
			CHSQARR(N, M, mat, q);
			out.println(System.currentTimeMillis() - t1);

		}
	}

	public static void testCHSQARR() {
		Random rand = new Random(0);
		for (int N = 1; N <= 20; N++) {
			for (int M = 1; M <= 20; M++) {
				int[][] mat = new int[N][M];
				int[][] q = new int[N*M][2];
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < M; j++) {
						int a = i+1;
						int b = j+1;
						q[i*M + j][0] = a;
						q[i*M + j][1] = b;
						mat[i][j] = rand.nextInt(10);
					}
				}
				long[] r1 = CHSQARR_slow(N, M, mat, q);
				long[] r2 = CHSQARR(N, M, mat, q);
				for (int i = 0; i < r2.length; i++) {
					if (r1[i] != r2[i]) {
						System.out.println("fail");
						CHSQARR(N, M, mat, q);
					}
				}
				//out.println(Arrays.toString());
			}
		}
	}

	public static long[] CHSQARR_slow(int N, int M, int[][] mat, int[][] q) {
		long[] r1 = new long[q.length];
		for (int i = 0; i < q.length; i++) {
			int a = q[i][0];
			int b = q[i][1];
			long minFill = Long.MAX_VALUE;
			for (int y = 0; y <= N-a; y++) {
				for (int x = 0; x <= M-b; x++) {
					long max = 0;
					for (int y2 = y; y2 < y+a; y2++) {
						for (int x2 = x; x2 < x+b; x2++) {
							max = Math.max(max, mat[y2][x2]);
						}
					}
					long sum = 0;
					for (int y2 = y; y2 < y+a; y2++) {
						for (int x2 = x; x2 < x+b; x2++) {
							sum += mat[y2][x2];
						}
					}
					long fill = max * a * b - sum;
					minFill = Math.min(fill, minFill);
				}
			}
			r1[i] = minFill;
		}
		return r1;
	}

	public static void CHSQARR() {
		int N = in.nextInt();
		int M = in.nextInt();
		int[][] mat = new int[N][];
		for (int i = 0; i < mat.length; i++) {
			mat[i] = in.nextIntArray(M);
		}
		int nq = in.nextInt();
		int[][] q = new int[nq][];
		for (int i = 0; i < q.length; i++) {
			q[i] = in.nextIntArray(2);
		}
		long[] ret = CHSQARR(N, M, mat, q);
		for (int i = 0; i < ret.length; i++) {
			out.println(ret[i]);
		}
	}

	public static long[] CHSQARR(int N, int M, int[][] mat, int[][] q) {
		SegmentTreeMax[] yMaxSeg = new SegmentTreeMax[M];
		int[][] yMax = new int[M][N+1];
		int[][] ySum2 = new int[M][];

		for (int x = 0; x < M; x++) {
			int[] sumColumn = new int[N+1];
			int[] column = new int[N];
			for (int y = 0; y < N; y++) {
				column[y] = mat[y][x];
				sumColumn[y+1] = sumColumn[y] + column[y];
			}
			yMaxSeg[x] = new SegmentTreeMax(column);
			ySum2[x] = sumColumn;
		}

		SegmentTreeMax[] xMaxSeg = new SegmentTreeMax[N];
		int[][] xMax = new int[N][M+1];
		int[][] xSum2 = new int[N][];

		for (int y = 0; y < N; y++) {
			int[] sumRow = new int[M+1];
			for (int x = 0; x < M; x++) {
				sumRow[x+1] = sumRow[x] + mat[y][x];
			}
			xMaxSeg[y] = new SegmentTreeMax(mat[y]);
			xSum2[y] = sumRow;
		}

		int[][] xSum = new int[M+1][N];
		for (int x = 0; x <= M; x++) {
			for (int y = 0; y < N; y++) {
				xSum[x][y] = xSum2[y][x]; 
			}
		}
		int[][] ySum = new int[N+1][M];
		for (int x = 0; x < M; x++) {
			for (int y = 0; y <= N; y++) {
				ySum[y][x] = ySum2[x][y]; 
			}
		}
		//long t2 = System.currentTimeMillis();
		int[][][] fmax = new int[10][N][M];
		int[][][] fmaxT = new int[10][M][N];
		for (int len = 0; len < 10; len++) {
			int len2 = 1 << (len-1);
			int[] dy = {0, len2, 0, len2};
			int[] dx = {0, 0, len2, len2};
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < M; x++) {
					if (len == 0) {
						fmax[len][y][x] = mat[y][x];
						fmaxT[len][x][y] = mat[y][x];
					} else {
						int max = 0;
						for (int i = 0; i < dx.length; i++) {
							int y2 = y + dy[i];
							int x2 = x + dx[i];
							if (y2 < N && x2 < M) {
								max = Math.max(max, fmax[len-1][y2][x2]);
							}
						}

						fmax[len][y][x] = max;
						fmaxT[len][x][y] = max;
					}
				}
			}
		}
		//System.out.println(System.currentTimeMillis() - t2);

		long[] ret = new long[q.length];

		@SuppressWarnings("unused")
		long maxTime = 0;
		for (int i = 0; i < q.length; i++) {

			@SuppressWarnings("unused")
			long t1 = System.currentTimeMillis();
			int a = q[i][0];
			int b = q[i][1];

			if (a < b) {
				ret[i] = doQuery(mat, xMaxSeg, xMax, xSum, b, a, M, N, true, fmaxT);
			} else {
				ret[i] = doQuery(mat, yMaxSeg, yMax, ySum, a, b, N, M, false, fmax);
			}
			//			t1 = System.currentTimeMillis() - t1;
			//			if (t1 > maxTime) {
			//				maxTime = t1;
			//				
			//			}
			//			if (t1 > maxTime/3) {
			//				System.out.println(a + " " + b + "	\t" + t1  + "\t idx: " + i);
			//			}
		}
		return ret;
	}

	private static long doQuery(int[][] mat, SegmentTreeMax[] yMaxSeg, int[][] yMax, int[][] ySum, int a, int b, int N, int M, boolean trans, int[][][] fmax) {
		int ab = a * b;

		// a >= b

		int[][] amax = new int[N-a+1][M-b+1];

		int type = 1;
		//if (a < 75 && b < 75) {
		//		if (a > 100 && b > 100) {
		//			type = 1;
		//		}
		if (b < 10 && a > 100) {
			type = 2;
			yMax = new int[N+1][M];
			for (int x = 0; x < M; x++) {
				for (int y = 0; y <= N-a; y++) {
					yMax[y][x] = (int) yMaxSeg[x].query_tree(y, y+a-1);
				}
			}
		} else {
			type = 3;
			int bBase = (31 - Integer.numberOfLeadingZeros(b));
			int bBase2 = 1 << (bBase);

			for (int y = 0; y <= N-a; y++) {
				for (int x = 0; x <= M-b; x++) {
					int max = 0;
					for (int y3 = y; y3 < y+a-bBase2; y3+=bBase2) {
						max = Math.max(max, fmax[bBase][y3][x]);
						max = Math.max(max, fmax[bBase][y3][x + b - bBase2]);
					}
					max = Math.max(max, fmax[bBase][y+a-bBase2][x]);
					max = Math.max(max, fmax[bBase][y+a-bBase2][x + b - bBase2]);

					amax[y][x] = max;
				}
			}
		}
		//segment = true;

		//		SegmentTreeLazyMax txMax2 = new SegmentTreeLazyMax(new int[M]);
		SegmentTreeMax txMax2 = new SegmentTreeMax(new int[M]);
		TreeMap<Integer, Integer> txMax = new TreeMap<>();
		int max = 0;
		int txSum = 0;
		int minFill = Integer.MAX_VALUE;

		for (int y = 0; y <= N - a; y++) {
			int[] yMaxY = null;
			if (y < yMax.length) {
				yMaxY = yMax[y];
			}
			int[] ySumYA = ySum[y+a];
			int[] ySumY = ySum[y];
			if (y != 0) {
				txSum = 0;
				for (int x2 = 0; x2 < b; x2++) {
					txSum += (ySumYA[x2] - ySumY[x2]);
				}
			}
			int[] cMax = new int[M - b+1];

			if (type == 0) {
				if (y != 0) {
					txMax.clear();
					for (int x2 = 0; x2 < b; x2++) {
						Integer key = yMaxY[x2];
						Integer val = txMax.get(key);
						if (val == null) {
							txMax.put(key, 1);
						} else {
							txMax.put(key, val + 1);
						}
					}
				}
				for (int x = 0; x <= M - b; x++) {
					if (x == 0 && y == 0) {
						for (int x2 = 0; x2 < b; x2++) {
							Integer key = yMaxY[x2];
							Integer val = txMax.get(key);
							if (val == null) {
								txMax.put(key, 1);
							} else {
								txMax.put(key, val + 1);
							}
						}
					} else if (x > 0) {
						// add right, erase left			
						Integer key = yMaxY[x-1];
						txMax.put(key, txMax.get(key) - 1);
						if (txMax.get(key) == 0) {
							txMax.remove(key);
						}
						if (x+b <= M) {
							key = yMaxY[x+b-1];
							Integer val = txMax.get(key);
							if (val == null) {
								txMax.put(key, 1);
							} else {
								txMax.put(key, val + 1);
							}
						}
					}
					if (x > 0) {
						if (yMaxY[x+b-1] < cMax[x-1]) {
							cMax[x] = txMax.lastKey();
						} else {
							cMax[x] = yMaxY[x+b-1];
						}
					} else {
						cMax[x] = txMax.lastKey();
					}
				}
			} else if (type == 1) {
				for (int x2 = 0; x2 < M; x2++) {
					txMax2.set_tree(x2, x2, yMaxY[x2]);
				}
				for (int x = 0; x <= M - b; x++) {
					if (x > 0) {
						// add right, erase left
						if (x+b <= M) {
							txMax2.set_tree(x+b-1, x+b-1, yMaxY[x+b-1]);
						}
					}
					if (x > 0) {
						if (yMaxY[x+b-1] < cMax[x-1]) {
							cMax[x] = txMax2.query_tree(x, x+b-1);
						} else {
							cMax[x] = yMaxY[x+b-1];
						}
					} else {
						cMax[x] = txMax2.query_tree(x, x+b-1);
					}
				}
			} else if (type == 2) {
				max = 0;
				for (int x = 0; x <= M - b; x++) {
					if (x == 0 || yMaxY[x+b-1] < cMax[x-1]) {
						max = 0;
						for (int x2 = x; x2 <= x+b-1; x2++) {
							max = Math.max(max, yMaxY[x2]);
						}
						cMax[x] = max;
					} else {
						cMax[x] = yMaxY[x+b-1];
					}
				}
			} else if (type == 3) {
				for (int x = 0; x <= M - b; x++) {
					cMax[x] = amax[y][x];
				}
			}

			for (int x = 0; x <= M - b; x++) {
				if (x == 0 && y == 0) {
					for (int x2 = 0; x2 < b; x2++) {
						txSum += (ySumYA[x2] - ySumY[x2]);
					}
				} else if (x > 0) {
					// add right, erase left
					txSum -= ySumYA[x-1] - ySumY[x-1];
					if (x+b <= M) {
						txSum += ySumYA[x+b-1] - ySumY[x+b-1];
					}
				}
				//				System.out.println("max: " + cMax[x] + "\t sum: " + txSum + "\t fill: " + (ab * cMax[x] - txSum));
				minFill = Math.min(minFill, ab * cMax[x] - txSum);
			}
		}
		return minFill;
	}

	public static class SegmentTreeMax extends SegmentTreeSum {
		protected int IDENTITY = Integer.MIN_VALUE;

		public SegmentTreeMax(int[] b) {
			super(b);
		}

		@Override
		protected int function(int a, int b) {
			return Math.max(a, b);
		}
	}

	public static class SegmentTreeSum {
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
			return a + b;
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = 0;

		public SegmentTreeSum(int[] b) {
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
		public void set_tree(int i, int j, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public int query_tree(int i, int j) {
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

	public static String fastCHEARMY(long k) {
		char[] s = Long.toString(k, 5).toCharArray();
		for (int i = 0; i < s.length; i++) {
			s[i] = (char) (((s[i] - '0') * 2) + '0');
		}
		return (new String(s));
	}

	public static boolean CHEARMY(int i) {
		char[] s = Integer.toString(i).toCharArray();
		long sum = 0;

		int len = s.length;
		int N = 1 << len;
		for (int n = 1; n < N; n++) {
			long prod = 1;
			for (int t = 0; t < len; t++) {
				if (((1 << t) & n) != 0) {
					prod *= (s[t] - '0');
				}
			}
			sum += prod;
		}
		if (sum % 2L == 0) {
			return true;
		} else {
			return false;
		}
	}


	public static void CHEARMY() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			long K = in.nextLong();
			out.println(fastCHEARMY(K-1));
		}
	}

	public static void BINOP() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			char[] a = in.next().toCharArray();
			char[] b = in.next().toCharArray();
			int oneA = 0;
			int oneB = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] == '1') {
					oneA++;
				}
				if (b[i] == '1') {
					oneB++;
				}
			}
			if (oneA == 0 || oneA == a.length) {
				out.println("Unlucky Chef");
				continue;
			}

			int ops = 0;
			int net = oneB - oneA;
			if (net < 0) {
				net *= -1;
				ops += net;
				for (int i = 0; i < b.length && net > 0; i++) {
					if (a[i] == '1' && b[i] == '0') {
						a[i] = '0';
						net--;
					}
				}
			} else if (net > 0) {
				ops += net;
				for (int i = 0; i < b.length && net > 0; i++) {
					if (a[i] == '0' && b[i] == '1') {
						a[i] = '1';
						net--;
					}
				}
			}
			for (int i = 0; i < b.length; i++) {
				if (a[i] == '0' && b[i] == '1') {
					ops++;
				}
			}
			out.println("Lucky Chef");
			out.println(ops);
		}
	}

	public static void CHCOINSG(InputStream in) {
		InputReader scan = new InputReader(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			if (n % 6 == 0) {
				System.out.println("Misha");
			} else {
				System.out.println("Chef");
			}
		}
	}

	public static void DEVARRAY(InputStream in) {
		InputReader scan = new InputReader(in);
		int n = scan.nextInt();
		int tests = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int min = Integer.MAX_VALUE;
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			min = Math.min(min, a[i]);
			max = Math.max(max, a[i]);
		}
		for (int i = 0; i < tests; i++) {
			int q = scan.nextInt();
			if (min <= q && q <= max) {
				System.out.println("Yes");
			} else {
				System.out.println("No");
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
