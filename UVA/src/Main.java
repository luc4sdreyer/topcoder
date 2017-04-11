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
import java.util.Collections;
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

public class Main {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

		Polygons();
		
		out.close();
	}
	
	public static void Polygons() {
		int N = 1;
		while (true) {
			N = in.nextInt();
			if (N == 0) {
				break;
			}
			ArrayList<Point> poly1 = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				poly1.add(new Point(in.nextInt(), in.nextInt()));
			}
			int M = in.nextInt();
			ArrayList<Point> poly2 = new ArrayList<>();
			for (int i = 0; i < M; i++) {
				poly2.add(new Point(in.nextInt(), in.nextInt()));
			}
			
			
			ArrayList<Point> points = new ArrayList<>();
			
			// Add all points inside, and find all intersections
			
			for (int i = 0; i < poly1.size(); i++) {
				if (pointInPolygon(poly2, poly1.get(i)) != 0) {
					points.add(poly1.get(i));
				}
			}
			for (int i = 0; i < poly2.size(); i++) {
				if (pointInPolygon(poly1, poly2.get(i)) != 0) {
					points.add(poly2.get(i));
				}
			}
			for (int i = 0; i < poly1.size(); i++) {
				for (int j = 0; j < poly2.size(); j++) {
					Point p = new Point();
					if (intersection(poly1.get(i), poly1.get((i+1) % N), poly2.get(j), poly2.get((j+1) % M), p) == 0) {
						if ((pointOnSegment(poly1.get(i), poly1.get((i+1) % N), p) == 1) && 
								(pointOnSegment(poly2.get(j), poly2.get((j+1) % M), p) == 1)) {
							points.add(p);
						}
					}
				}
			}
			double overlap = 0;
			double area = area(poly1) + area(poly2);
			if (points.size() > 1) {
				ArrayList<Point> hull = new ArrayList<>();
				convexHull(points, hull);
				overlap = area(hull);
			}
			area -= overlap * 2;
			System.out.print(String.format("%1$8s", String.format("%.2f", area)));
		}
		System.out.println();
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
