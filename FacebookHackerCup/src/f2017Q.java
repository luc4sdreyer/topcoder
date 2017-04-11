import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class f2017Q {

	public static void main(String[] args) throws FileNotFoundException {
//		progressPie("./input/f2017Q/progress_pie");
		lazyLoading("./input/f2017Q/lazy_loading");
	}

	public static void lazyLoading(String filename) throws FileNotFoundException {
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");

			int n = scan.nextInt();
			int[] w = scan.nextIntArray(n);
			output.append(lazyLoading(n, w));

			out.println(output.toString());
		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}
	
	public static int lazyLoading(int n, int[] w) {
		Arrays.sort(w);
		int totalStack = 0;
		int successfulTrips = 0;
		for (int t = 1; t <= n; t++) {
			int minStack = (int)Math.ceil(50.0 / w[n - t]);
			totalStack += minStack;
			if (totalStack > n) {
				break;
			} else {
				successfulTrips = t;
			}
		}
		return successfulTrips;
	}

	public static void progressPie(String filename) throws FileNotFoundException {
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");

			int p = scan.nextInt();
			int x = scan.nextInt();
			int y = scan.nextInt();
			output.append(progressPie(p, x, y));

			out.println(output.toString());
		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}
	
	public static String progressPie(int p, int x, int y) {
		
		int xdiff = x - 50;
		int ydiff = y - 50;
		if (xdiff * xdiff + ydiff * ydiff <= 50*50) {
			if (p == 0) {
				return "white";
			} else if (p == 100) {
				return "black";
			}
			Point center = new Point(50, 50);
			Point a = new Point(x, y);
			Point b = new Point(50, 100);
			double bAngle = angleDeg2(center, a, b);
			while (bAngle < 0) {
				bAngle += 360;
			}
			double progressAngle = 360.0 * p / 100.0;
			if (bAngle <= progressAngle) {
				return "black";
			}
		}
		
		return "white";
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

	static final double PI = Math.PI;
	static final double EPS = 1e-9;
	
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
	
	public static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream in) {
			this.br = new BufferedReader(new InputStreamReader(in));
		}

		public void close() {
			try {
				this.br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					String s = br.readLine();
					if (s != null) {
						st = new StringTokenizer(s);
					} else {
						return null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextLong();
			}
			return a;
		}

		int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextInt();
			}
			return a;
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine(){
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

}
