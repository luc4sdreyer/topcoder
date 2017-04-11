import java.io.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;

//  WeekOfCode21

public class Solution {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
//		perfWays();
//		testWays();
		countWays();
		
//		perfNL();
//		testNL();
//		nLetter();
		
//		testBM();
//		borrowingMoney();
		
//		lazySorting();
//		testLazySorting();
//		luckBalance();
//		kangaroo();
		
		out.close();
	}
	
	public static void perfWays() {
		for (int i = 1; i <= 1000; i++) {
			int[] a = {2,2};
			long r2 = countWays(a.length, a, 1, i);
			System.out.println(i + "\t" + r2 + "\t" + (r2 - countWays(a.length, a, 1, i-1)) + "\t" + (countWays(a.length-1, a, 1, i)));
//			System.out.println(i + "\t" + r2 + "\t" + (r2 - countWays(a.length, a, 1, i-1)));
		}
//		for (int i = 1; i <= 10; i++) {
//			for (int j = i; j <= 10; j++) {
//				int[] a = new int[]{i, j};
//				long r2 = countWays(a.length, a, 1, 1000);
//				System.out.println(a[0] + "\t" + a[1] + "\t" + r2);
//			}
//			//System.out.println(i + "\t" + r2 + "\t" + (r2 - countWays(a.length, a, 1, i-1)) + "\t" + (countWays(a.length-1, a, 1, i)));
//		}
	}
	
	public static void testWays() {
		for (int len = 1; len <= 5; len++) {
			for (int test = 0; test < 1000; test++) {
				int[] a = new int[len];
				for (int i = 0; i < a.length; i++) {
					a[i] = ra.nextInt(5)+1;
				}
				int L = ra.nextInt(5)+1;
				int R = ra.nextInt(5)+L;
				long r1 = countWaysSlow(len, a, L, R);
				long r2 = countWays(len, a, L, R);
				if (r1 != r2) {
					System.out.println("fail");
				}
			}
		}
	}
	
	public static void countWays() {
		int N = in.nextInt();
		int[] a = in.nextIntArray(N);
		int L = in.nextInt();
		int R = in.nextInt();
		System.out.println(countWaysSlow(N, a, L, R));
		System.out.println(countWays(N, a, L, R));
	}
	
	static final long m = (long) (1e9 + 7);
	
	public static long countWays(int N, int[] a, int L, int R) {
		Arrays.sort(a);
		long[][] F = new long[R+1][N];
		F[0][0] = 1;
		long sum = 0;
		for (int i = 0; i < F.length; i++) {
			for (int j = 0; j < N; j++) {
				long tempSum = F[i][j];
				if (i - a[j] >= 0) {
					for (int idx = 0; idx <= j; idx++) {
						tempSum += F[i - a[j]][idx];
					}
				}
				F[i][j] = tempSum % m;
			}
			long ts = 0;
			if (i >= L) {
				for (int idx = 0; idx < N; idx++) {
					sum = (sum + F[i][idx]) % m;
					ts = (ts + F[i][idx]) % m;
				}
			}
//			System.out.println(Arrays.toString(F[i]) + " \t" + ts);
		}
		
		return sum;
	}
	

	public static long countWaysSlow(int N, int[] a, int L, int R) {
		int[] times = new int[N];
		int[] F = new int[R+1];
		long sum2 = 0;
		for (int j = 0; j < F.length; j++) {
			times = new int[N];
			do {
				int sum = 0;
				for (int i = 0; i < N; i++) {
					sum += times[i] * a[i];
				}
				if (sum == j) {
					System.out.println(Arrays.toString(times));
					F[j]++;
				}
			} while (next_number(times, R+1));
			
			System.out.println(F[j]);
			if (j >= L) {
				sum2 += F[j];
			}
		}
		return sum2;
		
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

	public static void perfNL() {
		for (int t = 0; t < 10; t++) {
			int N = 2300;
			long[] P = new long[N];
			HashSet<Long> set = new HashSet<>();
			for (int i = 0; i < P.length; i++) {
				int x = ra.nextInt(200)-100;
				int y = ra.nextInt(200)-100;
				long pp = x + V + (y + V) * W;
				while (set.contains(pp)) {
					x = ra.nextInt(200)-100;
					y = ra.nextInt(200)-100;
					pp = x + V + (y + V) * W;
				}
				set.add(pp);
				P[i] = pp; 
			}

			long t1 = System.currentTimeMillis();
			long r2 = nLetter(N, P);
			if (-1 == r2) {
				System.out.println("fail");
			}
			System.out.println(System.currentTimeMillis() - t1);
		}
	}
	
	public static boolean timing = false;
	public static boolean debug = false;
	
	public static void testNL() {
		for (int t = 0; t < 10000; t++) {
			int N = ra.nextInt(20)+4;
			long[] P = new long[N];
			HashSet<Long> set = new HashSet<>();
			for (int i = 0; i < P.length; i++) {
				int x = ra.nextInt(20)-10;
				int y = ra.nextInt(20)-10;
				long pp = x + V + (y + V) * W;
				while (set.contains(pp)) {
					x = ra.nextInt(20)-10;
					y = ra.nextInt(20)-10;
					pp = x + V + (y + V) * W;
				}
				set.add(pp);
				P[i] = pp; 
			}
			
			if (debug) System.out.println("test " + t);
			long r1 = nLetterSlow(N, P);
			if (debug) System.out.println();
			long r2 = nLetter(N, P);
			if (debug) System.out.println();
			if (r1 != r2) {
				System.out.println("fail");
				nLetterSlow(N, P);
				nLetter(N, P);
			}
		}
		System.out.println("done!");
	}

	static long V = 20000;
	static long W = 100000;
	
	public static void nLetter() {
		int N = in.nextInt();
		long[] P = new long[N];
		for (int i = 0; i < P.length; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			P[i] = x + V + (y + V) * W; 
		}
		System.out.println(nLetter(N, P));
//		System.out.println(nLetterSlow(N, P));
	}
	
	public static boolean collinear(long x1, long y1, long x2, long y2, long x3, long y3) {
		return (x2-x1) * (y3-y1) == (x3-x1) * (y2-y1);
	}
	
	public static double angle(int x, int y) {
		double alp = 0;
		if (x == 0) {
			if (y > 0) {
				alp = Math.PI/2;
			} else {
				alp = Math.PI * 3/2;
			}
		} else {
			alp = Math.atan(y / (double)x);
			if (x < 0) {
				alp += Math.PI;
			} else if (y < 0) {
				alp += 2*Math.PI;
			}
		}
		return alp;
	}
	
	public static double angle360(int x, int y) {
		return angle(x, y) * 180 / Math.PI;
	}

	public static long nLetter(int N, long[] P) {
		
		long t1 = System.currentTimeMillis();
		
		Point[] p = new Point[N];
		for (int i = 0; i < p.length; i++) {
			int ox = (int) (P[i] % W - V);
			int oy = (int) (P[i] / W - V);
			p[i] = new Point(ox, oy);
		}
		int[] setSize = new int[N];
		double[][] angle = new double[N][N];
		int[][] pre = new int[N][N];
		int[][] list = new int[N][N];
		
		for (int i = 0; i < P.length; i++) {
			int ox = (int) (P[i] % W - V);
			int oy = (int) (P[i] / W - V);
			TreeMap<Double, long[]> map = new TreeMap<>();
			for (int j = 0; j < P.length; j++) {
				if (i != j) {
					int px = (int) (P[j] % W - V);
					int py = (int) (P[j] / W - V);
					double pa = angle360(px - ox, py - oy);
//					double pa = Math.random();
					long[] ps = new long[]{P[j], 1};
					if (map.isEmpty()) {
						map.put(pa, ps);
					} else {
						Double next = map.higherKey(pa);
						Double prev = map.lowerKey(pa);
						double close = 0;
						if (map.containsKey(pa)) {
							close = pa;
						} else if (next == null) {
							close = prev;
						} else if (prev == null) {
							close = next;
						} else {
							if (Math.abs(next.doubleValue() - pa) > Math.abs(prev.doubleValue() - pa)) {
								close = prev.doubleValue();
							} else {
								close = next.doubleValue();
							}
						}
						long[] qs = map.get(close);
						int qx = (int) (qs[0] % W - V);
						int qy = (int) (qs[0] / W - V);
						if (Math.abs(angleDeg2(new Point(ox, oy), new Point(qx, qy), new Point(px, py))) < EPS) {
							qs[1]++;
						} else {
							map.put(pa, ps);
						}
					}
				}
			}
			setSize[i] = map.size();
			double[] angleT = angle[i];
			int[] lineT = list[i];
			int[] listSumT = pre[i];
			int t = 0;
			for (Entry<Double, long[]> e: map.entrySet()) {
				angleT[t] = e.getKey();
				lineT[t] = (int) e.getValue()[1];
				t++;
			}
			int total = 0;
			for (int j = 0; j < lineT.length; j++) {
				total += lineT[j];
				listSumT[j] = total;
			}
			
		}
		
		if (timing) {
			System.out.println(System.currentTimeMillis() - t1 + " prepro");
			t1 = System.currentTimeMillis();
		}
		
		long ways = 0;
		for (int b = 0; b < N; b++) {
			for (int c = b+1; c < N; c++) {
				long numLeft = 0;
				long numRight = 0;
				int idxB = b;
				int idxC = c;
				Point pc = p[c];
				Point pb = p[b];
				if (pb.x > pc.x) {
					pc = p[b];
					pb = p[c];
					idxB = c;
					idxC = b;
				}

				double ahi = angleDeg2(pb, new Point(pb.x+1, pb.y), pc);
				numRight = countPoints(ahi, idxB, pre[idxB], list[idxB], angle[idxB], setSize[idxB], N);
				
				double bhi = angleDeg2(pc, new Point(pc.x+1, pc.y), pb);
				numLeft = countPoints(bhi, idxC, pre[idxC], list[idxC], angle[idxC], setSize[idxC], N);
				
				if (numLeft > 0 && numRight > 0) {
					if (debug) System.out.println(b + ":"+p[b] + " " + c + ":"+p[c] + " " + numLeft + " " + numRight);
				}
				
				ways += numLeft * numRight;
			}
		}

		if (timing)
			System.out.println(System.currentTimeMillis() - t1);
		
		return ways;
	}
	
	public static long countPoints(double ahi, int idxB, int[] pre, int[] line, double[] angle, int setSize, int N) {
		if (ahi < 0) {
			ahi += 360;
		}
		double alow = ahi - 90.0;
		if (alow < 0) {
			alow += 360;
		}
		int ihi = binSearch(ahi, angle, setSize);
		int ilow = binSearch(alow, angle, setSize);
		long numPoints = pre[ihi] - pre[ilow];
		if (numPoints < 0) {
			numPoints += N-1;
		}
		
		if (numPoints > 0) {
			numPoints -= line[ihi] - line[ilow];
		}
		return numPoints;
	}

	public static int binSearch(double x, double[] pre, int setSize) {
		int low = 0;
		int best = 0;
		int high = setSize-1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			if (Math.abs(pre[mid] - x) < EPS) {
				return mid;
			} else if (pre[mid] < x) {
				low = mid + 1;
			} else if (pre[mid] > x) {
				best = mid;
				high = mid - 1;
			}
		}
		return best;
	}

	public static void nLetter2(int N, long[] P) {
		for (int i = 0; i < P.length; i++) {
			int ox = (int) (P[i] % W - V);
			int oy = (int) (P[i] / W - V);
			ArrayList<Pair<Double, long[]>> list = new ArrayList<>();
			for (int j = 0; j < P.length; j++) {
				if (i != j) {
					int px = (int) (P[j] % W - V);
					int py = (int) (P[j] / W - V);
					Pair<Double, long[]> p = new Pair<Double, long[]>(angle360(px - ox, py - oy), new long[]{P[j], 0});
					
					list.add(p);
				}
			}
			Collections.sort(list);
			for (int j = 1; j < list.size(); j++) {
				int px = (int) (list.get(j-1).b[0] % W - V);
				int py = (int) (list.get(j-1).b[0] / W - V);
				int qx = (int) (list.get(j).b[0] % W - V);
				int qy = (int) (list.get(j).b[0] / W - V);
				if (collinear(ox, oy, px, py, qx, qy)) {
					list.get(j-1).b[1]++;
					list.remove(j--);
				}
			}
			
			System.out.println(ox + " " + oy);
		}
	}
	public static class Pair<A extends Comparable<A>, B> implements Comparable<Pair<A, B>> {
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
			return compareFirst;
		}
	}
	
	static public long nLetterSlow(int N, long[] Pin) {
		Point[] p = new Point[N];
		for (int i = 0; i < p.length; i++) {
			int px = (int) (Pin[i] % W - V);
			int py = (int) (Pin[i] / W - V);
			p[i] = new Point(px, py);
		}
		
		long ways = 0;
		for (int b = 0; b < N; b++) {
			for (int c = b+1; c < N; c++) {
				long numLeft = 0;
				long numRight = 0;
				for (int a = 0; a < N; a++) {
					if (a != b && a != c) {
						Point pc = p[c];
						Point pb = p[b];
						if (pb.x > pc.x) {
							pc = p[b];
							pb = p[c];
						}
						if (pointOnSegment(pc, pb, p[a]) == 0) {
							double cba = angleDeg2(pb, p[a], pc);
							if (cba > 0 && (cba < 90.0 + EPS)) {
								numRight++;
							} else {
								double bcd = angleDeg2(pc, p[a], pb);
								if (bcd > 0 && (bcd < 90.0 + EPS)) {
									numLeft++;
								}
							}
						}
					}
				}
				if (numLeft > 0 && numRight > 0) {
					if (debug) System.out.println(b + ":"+p[b] + " " + c + ":"+p[c] + " " + numLeft + " " + numRight);
				}
				ways += numLeft * numRight;
				
			}
		}
		return ways;
	}
	

	static final double PI = Math.PI;
	static final double EPS = 1e-9;

	
	/***************************************************************************
	 * Convex Hull
	 **************************************************************************/

	/**
	 * Which side is p3 to the line p1->p2? returns: 1 left, 0 on, -1 right
	 */
	static int sideSign(Point p1, Point p2, Point p3) {
		double sg = (p1.x - p3.x) * (p2.y - p3.y) - (p1.y - p3.y) * (p2.x - p3.x);
		if (Math.abs(sg) < EPS) return 0;
		if (sg > 0) return 1;
		return -1;
	}

	/**
	 * used by convex hull: from p3, if p1 is better than p2
	 */
	static boolean better(Point p1, Point p2, Point p3) {
		double sg = (p1.y - p3.y) * (p2.x - p3.x) - (p1.x - p3.x) * (p2.y - p3.y);
		// watch range of the numbers
		if (Math.abs(sg) < EPS) {
			return dist(p3, p1) > dist(p3, p2);
		}
		return sg < 0;
	}

	/**
	 * Convex hull (Graham scan)
	 * vin is modified
	 */
	static void convexHull(ArrayList<Point> vin, ArrayList<Point> vout)	{
		int n = vin.size();
		Collections.sort(vin);
		Point[] stk = new Point[n];
		int pstk, i;
		// hopefully more than 2 points
		stk[0] = vin.get(0);
		stk[1] = vin.get(1);
		pstk = 2;
		for (i = 2; i < n; i++) {
			if (dist(vin.get(i), vin.get(i - 1)) < EPS) continue;
			while (pstk > 1 && better(vin.get(i), stk[pstk - 1], stk[pstk - 2]))
				pstk--;
			stk[pstk] = vin.get(i);
			pstk++;
		}
		for (i = 0; i < pstk; i++) vout.add(new Point(stk[i]));
		
		// turn 180 degree
		for (i = 0; i < n; i++) {
			vin.get(i).y = -vin.get(i).y;
			vin.get(i).x = -vin.get(i).x;
		}
		Collections.sort(vin);
		stk[0] = vin.get(0);
		stk[1] = vin.get(1);
		pstk = 2;
		for (i = 2; i < n; i++) {
			if (dist(vin.get(i), vin.get(i - 1)) < EPS) continue;
			while (pstk > 1 && better(vin.get(i), stk[pstk - 1], stk[pstk - 2]))
				pstk--;
			stk[pstk] = vin.get(i);
			pstk++;
		}
		for (i = 1; i < pstk - 1; i++) {
			stk[i].x = -stk[i].x; // don’t forget rotate 180 d back.
			stk[i].y = -stk[i].y;
			vout.add(stk[i]);
		}
	}

	/**
	 * Test whether a simple polygon is convex
	 * return 0 if not convex, 1 if strictly convex,
	 * 2 if convex but there are unnecessary points 
	 * this function does not work if the polygon is self intersecting
	 * in that case, compute the convex hull of v, and see if both have the same area
	 */
	static int isConvex(ArrayList<Point> v) {
		int i, j, k;
		int c1 = 0;
		int c2 = 0;
		int c0 = 0;
		int n = v.size();
		for (i = 0; i < n; i++) {
			j = (i + 1) % n;
			k = (j + 1) % n;
			int s = sideSign(v.get(i), v.get(j), v.get(k));
			if (s == 0) c0++;
			if (s > 0) c1++;
			if (s < 0) c2++;
		}
		if (c1 != 0 && c2 != 0) return 0;
		if (c0 != 0) return 2;
		return 1;
	}

	/***************************************************************************
	 * Areas
	 **************************************************************************/
	static double trap(Point a, Point b) {
		return (0.5 * (b.x - a.x) * (b.y + a.y));
	}

	/**
	 * Area of any simple polygon, not necessarily convex.
	 */
	static double area(ArrayList<Point> vin) {
		int n = vin.size();
		double ret = 0.0;
		for (int i = 0; i < n; i++)
			ret += trap(vin.get(i), vin.get((i + 1) % n));
		return Math.abs(ret);
	}

	/**
	 * Area of any simple polygon, not necessarily convex.
	 */
	static double perimeter(ArrayList<Point> vin) {
		int n = vin.size();
		double ret = 0.0;
		for (int i = 0; i < n; i++)
			ret += dist(vin.get(i), vin.get((i + 1) % n));
		return ret;
	}
	
	/**
	 * Area of any simple triangle. Faster than the general area method.
	 */
	static double triarea(Point a, Point b, Point c) {
		return Math.abs(trap(a, b) + trap(b, c) + trap(c, a));
	}

	/**
	 * Height from a to the line bc
	 */
	static double height(Point a, Point b, Point c) {
		double s3 = dist(c, b);
		double ar = triarea(a, b, c);
		return (2.0 * ar / s3);
	}

	/***************************************************************************
	 * Points and Lines
	 **************************************************************************/
	
	/**
	 * Two lines given by p1->p2, p3->p4
	 * r is the intersection point
	 * return -1 if two lines are parallel
	 */
	static int intersection(Point p1, Point p2, Point p3, Point p4, Point r) {
		double d = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);
		if (Math.abs(d) < EPS) return -1;
		// might need to do something special!!!
		double ua;
		ua = (p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x);
		ua /= d;
		// ub = (p2.x - p1.x)*(p1.y-p3.y) - (p2.y-p1.y)*(p1.x-p3.x);
		//ub /= d;
		r.assign(p1.add((p2.subtract(p1)).multiply(ua)));
		return 0;
	}

	/**
	 * The closest point on the line p1->p2 to p3
	 */
	static void closestPoint(Point p1, Point p2, Point p3, Point r) {
		if (Math.abs(triarea(p1, p2, p3)) < EPS) {
			r.assign(p3);
			return;
		}
		Point v = p2.subtract(p1);
		v.normalize();
		double pr; // inner product
		pr = (p3.y - p1.y) * v.y + (p3.x - p1.x) * v.x;
		r.assign(p1.add(v.multiply(pr)));
	}

	/**
	 * Get the orthocenter, or the point where the altitudes intersect.
	 * An altitude of a triangle is a line segment through a vertex
	 * and perpendicular to (i.e. forming a right angle with) a line
	 */
	static int orthocenter(Point p1, Point p2, Point p3, Point r) {
		if (triarea(p1, p2, p3) < EPS) return -1;
		Point a1 = new Point();
		Point a2 = new Point();
		closestPoint(p2, p3, p1, a1);
		closestPoint(p1, p3, p2, a2);
		intersection(p1, a1, p2, a2, r);
		return 0;
	}

	/**
	 * The circumcenter of a triangle is the center of the circumscribed circle:
	 * a circle that passes through all the vertices of the polygon.
	 */
	static int circumcenter(Point p1, Point p2, Point p3, Point r) {
		if (triarea(p1, p2, p3) < EPS) return -1;
		Point a1 = new Point();
		Point a2 = new Point();
		Point b1 = new Point();
		Point b2 = new Point();
		a1.assign((p2.add(p3)).multiply(0.5));
		a2.assign((p1.add(p3)).multiply(0.5));
		b1.x = a1.x - (p3.y - p2.y);
		b1.y = a1.y + (p3.x - p2.x);
		b2.x = a2.x - (p3.y - p1.y);
		b2.y = a2.y + (p3.x - p1.x);
		intersection(a1, b1, a2, b2, r);
		return 0;
	}

	/**
	 * Angle bisection
	 */
	static int bcenter(Point p1, Point p2, Point p3, Point r) {

		if (triarea(p1, p2, p3) < EPS) return -1;
		double s1, s2, s3;
		s1 = dist(p2, p3);
		s2 = dist(p1, p3);
		s3 = dist(p1, p2);
		double rt = s2 / (s2 + s3);
		Point a1, a2;
		a1 = p2.multiply(rt).add(p3.multiply(1.0 - rt));
		rt = s1 / (s1 + s3);
		a2 = p1.multiply(rt).add(p3.multiply(1.0 - rt));
		intersection(a1, p1, a2, p2, r);
		return 0;
	}

	/***************************************************************************
	 * Angles
	 **************************************************************************/
	
	/**
	 * Angle from p1->p2 to p1->p3, returns -PI to PI
	 */
	static double angle(Point p1, Point p2, Point p3) {
		Point va = p2.subtract(p1);
		va.normalize();
		Point vb = new Point(va.y, va.x);
		Point v = p3.subtract(p1);
		double x, y;
		x = dot(v, va);
		y = dot(v, vb);
		return Math.atan2(y, x);
	}
	
	/**
	 * Angle from p1->p2 to p1->p3, returns -180 to 180
	 */
	static double angleDeg(Point p1, Point p2, Point p3) {
		return angle(p1, p2, p3) * 180.0 / PI;
	}
	
	/**
	 * Angle from p1->p2 to p1->p3, meaning how far you have to travel from p2 to p3.
	 * This function doesn't ignore the sign of the angle like angleDeg does.
	 */
	static double angleDeg2(Point p1, Point p2, Point p3) {
		Point va = p2.subtract(p1);
		Point vb = p3.subtract(p1);
		double b = Math.atan2(vb.y, vb.x);
		double a = Math.atan2(va.y, va.x);
		double a312 = b -a;	
		if (a312 < -PI/2) {
			a312 += 2*PI;
		}
		return a312 * 180.0 / PI;
	}

	/**
	 * In a triangle with sides a,b,c, the angle between b and c
	 * we do not check if a,b,c is a triangle here.
	 */
	static double angle(double a, double b, double c) {
		double cs = (b * b + c * c - a * a) / (2.0 * b * c);
		return Math.acos(cs);
	}

	/**
	 * Rotate p1 around p0 clockwise, by angle a
	 * Don’t pass by reference for p1, so r and p1 can be the same
	 */
	static void rotate(Point p0, Point p1, double a, Point r) {
		Point temp = p1.subtract(p0);
		r.x = Math.cos(a) * temp.x - Math.sin(a) * temp.y;
		r.y = Math.sin(a) * temp.x + Math.cos(a) * temp.y;
		r.assign(r.add(p0));
	}

	/**
	 * Reflect p3 around the p1->p2 line, return as r
	 */
	static void reflect(Point p1, Point p2, Point p3, Point r)	{
		if (dist(p1, p3) < EPS) {
			r = p3;
			return;
		}
		double a = angle(p1, p2, p3);
		r.assign(p3);
		rotate(p1, r, -2.0 * a, r);
	}

	/***************************************************************************
	 * Points, lines, and circles
	 **************************************************************************/

	/**
	 * Returns:
	 * 1 if the point is on the segment p1->p2;
	 * -1 if on the line but not on the segment
	 * 0 if not on the line;
	 */
	static int pointOnSegment(Point p1, Point p2, Point p)	{
		double s = triarea(p, p1, p2);
		if (s > EPS) return (0);
		double sg = (p.x - p1.x) * (p.x - p2.x);
		if (sg > EPS) return (-1);
		sg = (p.y - p1.y) * (p.y - p2.y);
		if (sg > EPS) return (-1);
		return 1;
	}

	/**
	 * Returns the number of intersections between a circle and a line: 0 - 2
	 */
	static int lineCircleIntersect(Point oo, double r, Point p1, Point p2, Point r1, Point r2) {
		Point m = new Point();
		closestPoint(p1, p2, oo, m);
		Point v = p2.subtract(p1);
		v.normalize();
		double r0 = dist(oo, m);
		if (r0 > r + EPS) return -1;
		if (Math.abs(r0 - r) < EPS) {
			r1.assign(m);
			r2.assign(m);
			return 1;
		}
		double dd = Math.sqrt(r * r - r0 * r0);
		r1.assign(m.subtract(v.multiply(dd)));
		r2.assign(m.subtract(v.multiply(dd)));
		return 0;
	}
	
	/**
	 * Returns the number of intersections between a circle and another circle: 0 - 2
	 * -1 if no intersection or infinite intersection
	 */
	static int circleCircleIntersect(Point o1, double r1, Point o2, double r2, Point q1, Point q2)	{
		double r = dist(o1, o2);
		if (r1 < r2) {
			swap(o1, o2);
			double temp = r1;
			r1 = r2;
			r2 = temp;
		}
		if (r < EPS) return (-1);
		if (r > r1 + r2 + EPS) return (-1);
		if (r < r1 - r2 - EPS) return (-1);
		Point v = o2.subtract(o1);
		v.normalize();
		q1 = o1.add(v.multiply(r1));
		if (Math.abs(r - r1 - r2) < EPS || Math.abs(r + r2 - r1) < EPS) {
			q2.assign(q1);
			return (1);
		}
		double a = angle(r2, r, r1);
		q2.assign(q1);
		rotate(o1, q1, a, q1);
		rotate(o1, q2, -a, q2);
		return 0;
	}

	/**
	 * Returns 1 if the point is in the polygon; 0 if outside; -1 if on the polygon 
	 */
	static int pointInPolygon(ArrayList<Point> poly, Point pin) {
		Point p = new Point(pin);
		ArrayList<Point> pv = new ArrayList<>();
		for (int i = 0; i < poly.size(); i++) {
			pv.add(new Point(poly.get(i)));
		}
		
		int i, j;
		int n = pv.size();
		pv.add(pv.get(0));
		for (i = 0; i < n; i++)
			if (pointOnSegment(pv.get(i), pv.get(i + 1), p) == 1) return (-1);
		for (i = 0; i < n; i++)
			pv.get(i).assign(pv.get(i).subtract(p));
		p.x = p.y = 0.0;
		double a, y;
		while (true) {
			a = Math.random() * 3;
			j = 0;
			for (i = 0; i < n; i++) {
				rotate(p, pv.get(i), a, pv.get(i));
				if (Math.abs(pv.get(i).x) < EPS) j = 1;
			}
			if (j == 0) {
				pv.get(n).assign(pv.get(0));
				j = 0;
				for (i = 0; i < n; i++)
					if (pv.get(i).x * pv.get(i + 1).x < -EPS) {
						y = pv.get(i + 1).y - pv.get(i + 1).x * (pv.get(i).y - pv.get(i + 1).y) / (pv.get(i).x - pv.get(i + 1).x);
						if (y > 0) j++;
					}
				return (j % 2);
			}
		}
	}

	/**
	 * Cut a convex polygon along the line p1->p2. pol1 is the resulting polygon on the left, pol2 on the right. 
	 */
	static void cutPoly(ArrayList<Point> pol, Point p1, Point p2, ArrayList<Point> pol1, ArrayList<Point> pol2) {
		ArrayList<Point> pp = new ArrayList<>();
		ArrayList<Point> pn = new ArrayList<>();
		pp.clear();
		pn.clear();
		int i, sg, n = pol.size();
		Point q1 = new Point();
		Point q2 = new Point();
		Point r = new Point();
		for (i = 0; i < n; i++) {
			q1.assign(pol.get(i));
			q2.assign(pol.get((i + 1) % n));
			sg = sideSign(p1, p2, q1);
			if (sg >= 0) pp.add(q1);
			if (sg <= 0) pn.add(q1);
			if (intersection(p1, p2, q1, q2, r) >= 0) {
				if (pointOnSegment(q1, q2, r) == 1) {
					pp.add(r);
					pn.add(r);
				}
			}
		}
		pol1.clear();
		pol2.clear();
		if (pp.size() > 2) convexHull(pp, pol1);
		if (pn.size() > 2) convexHull(pn, pol2);
	}

	public static class Point implements Comparable<Point> {
		public double x, y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Point(Point p) {
			this.x = p.x;
			this.y = p.y;
		}

		public Point() {
			this.x = 0;
			this.y = 0;
		}

		double length() {
			return Math.sqrt(x * x + y * y);
		}

		/**
		 * Normalise the vector to unit length; return -1 if the vector's length is 0.
		 */
		int normalize() {
			double l = length();
			if (Math.abs(l) < EPS) return -1;
			x /= l;
			y /= l;
			return 0;
		}

		Point subtract(Point p) {
			return new Point(x - p.x, y - p.y);
		}

		Point add(Point p) {
			return new Point(x + p.x, y + p.y);
		}

		Point multiply(double scalar) {
			return new Point(x * scalar, y * scalar);
		}

		void assign(Point p) {
			x = p.x;
			y = p.y;
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}

		public int compareTo(Point p) {
			if (Math.abs(x - p.x) < EPS) return Double.compare(y, p.y);
			return Double.compare(x, p.x);
		}
	}

	static void swap(Point a, Point b) {
		Point t = new Point(a);
		a.assign(b);
		b.assign(t);
	}

	/**
	 * The distance between two points
	 */
	static double dist(Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}

	/**
	 * the inner product of two vectors
	 */
	static double dot(Point a, Point b) {
		return (a.x * b.x + a.y * b.y);
	}
	
	public static void testBM() {
		for (int test = 0; test < 1000; test++) {
			int N = ra.nextInt(4)+1;
			int M = ra.nextInt(N*(N-1)/2 +1);
			boolean[][] g = new boolean[N][N];
			int[] cost = new int[N];
			for (int i = 0; i < cost.length; i++) {
				cost[i] = ra.nextInt(10);
			}
			for (int i = 0; i < M && N > 1; i++) {
				int a = ra.nextInt(N);
				int b = ra.nextInt(N);
				while (a == b) {
					a = ra.nextInt(N);
					b = ra.nextInt(N);
				}
				g[a][b] = true;
				g[b][a] = true;
			}
			String r1 = borrowingMoneySlow(N, g, cost);
			String r2 = borrowingMoney(N, g, cost);
			if (!r1.equals(r2)) {
				System.out.println("fail");
				borrowingMoneySlow(N, g, cost);
				borrowingMoney(N, g, cost);
			}
		}
	}
	
	public static void borrowingMoney() {
		int N = 0;
		boolean[][] g;
		int[] cost;
		
		N = in.nextInt();
		int m = in.nextInt();
		cost = in.nextIntArray(N);
		g = new boolean[N][N];
		
		for (int i = 0; i < m; i++) {
			int a = in.nextInt()-1;
			int b = in.nextInt()-1;
			g[a][b] = true;
			g[b][a] = true;
		}
		
		System.out.println(borrowingMoney(N, g, cost));
//		System.out.println(borrowingMoneySlow(N, g, cost));

	}
	
	public static String borrowingMoneySlow(int ns, boolean[][] g, int[] cost) {
		int len = ns;
		int N = 1 << len;
		int[] costF = new int[100*35 +1];
		for (int n = 0; n < N; n++) {
			boolean[] active = new boolean[len];
			for (int i = 0; i < len; i++) {
				if (((1 << i) & n) != 0) {
					active[i] = true;
				}
			}
			int sum = 0;
			boolean v = true;
			boolean[] gone = new boolean[ns];
			for (int i = 0; i < ns; i++) {
				if (active[i]) { // try to steal here
					if (!gone[i]) {
						sum += cost[i];
						for (int j = 0; j < ns; j++) {
							if (g[i][j]) {
								gone[j] = true;
							}
						}
					} else {
						v = false;
					}
				}
				
				if (!v) {
					if (i >= 1) {
						System.currentTimeMillis();
					}
					break;
				}
			}
			if (v) {
				costF[sum]++;
			}
		}
		for (int i = costF.length-1; i >= 0; i--) {
			if (costF[i] > 0) {
				return i + " " + costF[i];
			}
		}
		return "";
	}
	
	public static String borrowingMoney(int N, boolean[][] g, int[] cost) {
		boolean[] visit;
		visit = new boolean[N];
		HashMap<Long, Long> F;
		F = new HashMap<>();
		
		long mx = 0;
		long tw = 1;
		for (int i = 0; i < visit.length; i++) {
			if (!visit[i]) {
				F = new HashMap<>();
				
				dfs(true, i, -1, N, g, visit, cost, F);
				
				long max = -1;
				long fmask = 0;
				for (Entry<Long, Long> e: F.entrySet()) {
					if (e.getValue() > max) {
						max = e.getValue();
						fmask = e.getKey();
					}
				}
				long ways = 0;
				for (Entry<Long, Long> e: F.entrySet()) {
					if (e.getValue() == max) {
						ways++;
					}
				}
				mx += max; 
				tw *= ways;
			}
		}
		
		

		//System.out.println(max + " " + ways + " " + Long.toBinaryString(fmask));
		
		return (mx + " " + tw);
	}


	public static void dfs(boolean start, int top, int parent, int N, boolean[][] g, boolean[] visit, int[] cost, HashMap<Long, Long> F) {
		if (!visit[top]) {
			visit[top] = true;
			HashMap<Long, Long> current = F;
			HashMap<Long, Long> oldF = new HashMap<>();
			for (Entry<Long, Long> e: current.entrySet()) {
				oldF.put(e.getKey(), e.getValue());
			}
			if (start) {
				
				// do nothing
				current.put(0L, 0L);
				
				// steal here
				long fmask = 0;
				for (int i = 0; i < N; i++) {
					if (g[top][i]) {
						fmask |= 1L << i;
					}
				}
				current.put(fmask, (long)cost[top]);
			} else {
				// steal here

				for (Entry<Long, Long> e: oldF.entrySet()) {
					if (!getBit(e.getKey(), top)) {
						long max = e.getValue(); 
						long fmask = e.getKey();
						
						for (int i = 0; i < N; i++) {
							if (g[top][i]) {
								fmask |= 1L << i;
							}
						}
						if (max > -1) {
							max += cost[top];
							if (!current.containsKey(fmask) || current.get(fmask) < max) {
								current.put(fmask, max);
							}
//							if (current.get(fmask) == max) {
//								current.put(fmask, max);
//							}
						}
					}
				}
			}
			
			for (int i = 0; i < N; i++) {
				if (g[top][i]) {
					dfs(false, i, top, N, g, visit, cost, F);
				}
			}
		}
	}
	
	public static int clearBit(int x, int i) {
		return (x & ~(1 << i));
	}
	
	public static int setBit(int x, int i) {
		return (x | (1 << i));
	}
	
	public static boolean getBit(long x, int i) {
		return (x & (1L << i)) != 0;
	}
	
	public static void testLazySorting() {
		int[] a = new int[4];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < a.length; j++) {
				a[j] = ra.nextInt(10);
			}
			double r1 = slowLazySorting(a.length, a);
			double r2 = lazySorting(a.length, a);
			int t = 0;
			while (Math.abs(r1 - r2) > 0.1 && t < 10) {
				t++;
				r1 = slowLazySorting(a.length, a);
				r2 = lazySorting(a.length, a);
			}
			if (t == 10) {
				System.out.println("fail");
			}
		}
	}
	
	public static void lazySorting() {
		int n = in.nextInt();
		int[] a = in.nextIntArray(n);
		System.out.println(String.format("%.6f", lazySorting(n, a)));
//		System.out.println(String.format("%.6f", slowLazySorting(n, a)));
	}
	
	public static double slowLazySorting(int n, int[] a) {
		ArrayList<Integer> b = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			b.add(a[i]);
		}
		Arrays.sort(a);
		int sum = 0;
		int games = 1000000;
		for (int i = 0; i < games; i++) {
			boolean wrong = false;
			int time = 0;
			
			for (int j = 0; j < a.length; j++) {
				if (a[j] != b.get(j)) {
					wrong = true;
					break;
				}
			}
			
			while (wrong) {
				wrong = false;
				time++;
				Collections.shuffle(b);
				for (int j = 0; j < a.length; j++) {
					if (a[j] != b.get(j)) {
						wrong = true;
						break;
					}
				}
				if (!wrong) {
					sum += time;
				}
			}
		}
		return sum / (double)games;
	}

	
	public static double lazySorting(int n, int[] a) {
		int[] f = new int[101];
		int[] b = a.clone();
		Arrays.sort(b);
		if (Arrays.equals(a, b)) {
			return 0;
		}
		for (int i = 0; i < a.length; i++) {
			f[a[i]]++;
		}
		long c = 1;
		for (int i = 1; i <= n; i++) {
			c *= i;
		}
		for (int i = 0; i < f.length; i++) {
			while (f[i] > 0) {
				c /= f[i];
				f[i]--;
			}
		}
		double p = 1.0/c;
		return 1/p;
	}

	public static void luckBalance() {
		int n = in.nextInt();
		int k = in.nextInt();
		int[] luck = new int[n];
		int[] imp = new int[n];
		for (int i = 0; i < n; i++) {
			luck[i] = in.nextInt();
			imp[i] = in.nextInt();
		}
		System.out.println(luckBalance(n, k, luck, imp));
	}
	
	public static long luckBalance(int n, int k, int[] luck, int[] imp) {
		ArrayList<Integer> important = new ArrayList<>();
		long balance = 0;
		for (int i = 0; i < imp.length; i++) {
			if (imp[i] == 1) {
				important.add(luck[i]);
			} else {
				balance += luck[i];
			}
		}
		Collections.sort(important);
		Collections.reverse(important);
		for (int i = 0; i < important.size(); i++) {
			if (i < k) {
				balance += important.get(i);
			} else {
				balance -= important.get(i);
			}
		}
		return balance;
	}
	
	public static void kangaroo() {
		int x1 = in.nextInt();
		int v1 = in.nextInt();
		int x2 = in.nextInt();
		int v2 = in.nextInt();
		if (kangaroo(x1, v1, x2, v2)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}
	
	public static boolean kangaroo(int x1, int v1, int x2, int v2) {
		int dx = x1-x2;
		int dv = v2-v1;
		if (dv == 0) {
			return dx == 0;
		}
		if (((Math.abs(dx) % Math.abs(dv)) == 0) && dx / (double)dv > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("serial")
	public static class Counter<T> extends HashMap<T, Integer> {
		public Counter() {
			super();
		}
		public void add(T key) {
			this.add(key, 1);
		}
		public void add(T key, int count) {
			Integer i = this.get(key);
			if (i == null) {
				this.put(key, count);
			} else {
				this.put(key, i+count);
			}
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
