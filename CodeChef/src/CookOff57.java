import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;

public class CookOff57 {
	public static void main(String[] args) {
		//equality(System.in);
		versionControlSystem(System.in);
	}

	public static void versionControlSystem(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			int k = scan.nextInt();
			int[] ignored = scan.nextIntArray(m);
			int[] tracked = scan.nextIntArray(k);
			HashSet<Integer> ign = new HashSet<>();
			for (int i = 0; i < ignored.length; i++) {
				ign.add(ignored[i]);
			}
			HashSet<Integer> track = new HashSet<>();
			for (int i = 0; i < tracked.length; i++) {
				track.add(tracked[i]);
			}
			int a = 0;
			int b = 0;
			for (int i = 1; i <= n; i++) {
				if (track.contains(i) && ign.contains(i)) {
					a++;
				}
				if (!track.contains(i) && !ign.contains(i)) {
					b++;
				}
			}
			System.out.println(a + " " + b);
		}
	}
	
	public static void equality(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			ArrayList<Double> b = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				b.add((double) scan.nextLong());
			}

			Matrix m = new Matrix();
			m.resize(n, n);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i == j) {
						m.a[i][j] = 0;
					} else {
						m.a[i][j] = 1;
					}
				}
			}
			ArrayList<Double> sol = solve(m, b);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < sol.size(); i++) {
				sb.append((long)Math.round(sol.get(i)) + " ");
			}
			System.out.println(sb);
		}	
		//		Matrix m = new Matrix();
		//		m.resize(3, 3);
		//		m.a[0] = new double[]{1, 3, -2};
		//		m.a[1] = new double[]{3, 5, 6};
		//		m.a[2] = new double[]{2, 4, 3};
		//		
		//		ArrayList<Double> b = new ArrayList<>();
		//		b.add(5.0);
		//		b.add(7.0);
		//		b.add(8.0);

		//		ArrayList<Double> sol = solve(m, b);
	}

	/**
	 *  Matrix library based on The Hitchhiker�s Guide to the Programming Contests
	 * 	http://comscigate.com/Books/contests/icpc.pdf
	 */

	public static double EPS = 1e-8;

	public static class Matrix {
		int n;
		int m;
		double[][] a;

		public Matrix() {
			a = new double[1][1];
		}

		void resize(int x, int y) {
			resize(x, y, 0);
		}

		void resize(int x, int y, double v)
		{
			n=x; m=y;
			double[][] newA = new double[n][m];
			for (int i = 0; i < n; i++) {
				if (i < a.length) {
					for (int j = 0; j < m; j++) {
						if (j < a[0].length) {
							newA[i][j] = a[i][j];
						} else {
							newA[i][j] = v;
						}
					}
				}
			}
			a = newA;
		}

		double Gauss()
		// Row elimination based on the first n columns
		// if the first n columns is not invertible, kill yourself
		// otherwise, return the determinant of the first n columns
		{
			int i,j,k;
			double det=1.0, r;
			for(i=0;i<n;i++)
			{
				for(j=i, k=-1; j<n; j++) { 
					if(Math.abs(a[j][i])>EPS)
					{ k=j; j=n+1; }
				}
				if(k<0) {
					n=0;
					return 0.0;
				}
				if(k != i) {
					double[] temp = a[i];
					a[i] = a[k];
					a[k] = temp;
					det=-det;
				}
				r=a[i][i]; det*=r;
				for(j=i; j<m; j++) a[i][j]/=r;
				for(j=i+1; j<n; j++)
				{
					r=a[j][i];
					for(k=i; k<m; k++) a[j][k]-=a[i][k]*r;
				}
			}
			for(i=n-2; i>=0; i--)
				for(j=i+1; j<n; j++)
				{
					r=a[i][j];
					for(k=j; k<m; k++) a[i][k]-=r*a[j][k];
				}
			return det;
		}

		int inverse()
		// assume n=m. returns 0 if not invertible
		{
			int i, j, ii;
			Matrix T = new Matrix();
			T.resize(n, 2*n);
			for(i=0;i<n;i++) for(j=0;j<n;j++) T.a[i][j]=a[i][j];
			for(i=0;i<n;i++) T.a[i][i+n]=1;
			T.Gauss();
			if(T.n==0) return 0;
			for(i=0;i<n;i++) for(j=0;j<n;j++) a[i][j]=T.a[i][j+n];
			return 1;
		}
		ArrayList<Double> multiply(ArrayList<Double> v)
		// assume v is of size m
		{
			ArrayList<Double> rv = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				rv.add(0.0);
			}
			int i,j;
			for(i=0;i<n;i++)
				for(j=0;j<m;j++)
					rv.set(i, rv.get(i) + a[i][j]*v.get(j));
			return rv;
		}
		Matrix multiply(Matrix M1)
		{
			Matrix R = new Matrix();
			R.resize(n, M1.m);
			int i,j,k;
			for(i=0;i<n;i++)
				for(j=0;j<M1.m;j++)
					for(k=0;k<m;k++) R.a[i][j]+=a[i][k]*M1.a[k][j];
			return R;
		}
		void show()
		{
			int i,j;
			StringBuilder sb = new StringBuilder(); 
			for(i=0;i<n;i++)
			{
				for(j=0;j<m;j++) {
					sb.append(String.format("%15.10f ", (double)a[i][j]));
				}
				sb.append("\n");
			}
			sb.append("\n");
		}
	};

	static double det(Matrix M)
	// compute the determinant of M
	{
		Matrix M1=M;
		double r=M1.Gauss();
		if(M1.n==0) return 0.0;
		return r;
	}
	static ArrayList<Double> solve(Matrix M, ArrayList<Double> v)
	// return the vector x such that Mx = v; x is empty if M is not invertible
	{
		ArrayList<Double> x = null;
		Matrix M1=M;
		if(M1.inverse() == 0) return x;
		return M1.multiply(v);
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
