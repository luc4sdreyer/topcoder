import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class R284 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

		name();
//		crazyTown();
	}
	
	public static void name() {
		int n = in.nextInt();
		int T = in.nextInt();
		int[] p = new int[n];
		int[] t = new int[n];
		for (int i = 0; i < t.length; i++) {
			p[i] = in.nextInt();
			t[i] = in.nextInt();
		}
		System.out.println(name(n, T, p, t));
	}
	
	public static double name(int n, int TT, int[] p, int[] ST) {
		n++;
		TT++;
		double F[][][] = new double[n][TT][TT];
		
		F[0][0][0] = 1;
		for (int tt = 1; tt < TT; tt++) {
			for (int st = 1; st < ST[0]; st++) {
				F[0][tt][st] += F[0][tt-1][st-1] * (1-p[0]/100.0);  
			}
		}
		
		for (int s = 1; s < n; s++) {
			for (int tt = s; tt < TT; tt++) {
				if (s == n-1) {
					int st = 0;
					double sum = 0;
					for (int i = 0; i < ST[s-1]-1; i++) {
						sum += F[s-1][tt-1][i] * p[s-1]/100.0;
					}
					sum += F[s-1][tt-1][ST[s-1]-1];
					F[s][tt][st] += sum;
				} else {
					double sum = 0;
					for (int st = 0; st < ST[s-1]; st++) {
						sum += F[s-1][tt-1][st] * p[s-1]/100.0;
					}
					sum += F[s-1][tt-1][ST[s-1]-1] * (1-p[s-1]/100.0);
					F[s][tt][0] += sum;
					
					for (int st = 1; st < ST[s]; st++) {
						F[s][tt][st] += F[s][tt-1][st-1] * (1-p[s]/100.0);
					}
				}
			}
		}
		
		double total = 0;
		double[] suma = new double[n]; 
		for (int s = 0; s < n; s++) {
			for (int st = 0; st < TT; st++) {
				suma[s] += F[s][TT-1][st];
			}
		}
		for (int i = 0; i < suma.length; i++) {
			total += i * suma[i];
		}
		return total;
	}
	
	public static double name2(int n, int TT, int[] p, int[] ST) {
		n++;
		TT++;
		double F[][][] = new double[n][TT][TT];
		
		F[0][0][0] = 1;
		for (int tt = 1; tt < TT; tt++) {
			for (int st = 1; st < ST[0]; st++) {
				F[0][tt][st] += F[0][tt-1][st-1] * (1-p[0]/100.0);  
			}
		}
		
		for (int s = 1; s < n; s++) {
			for (int tt = s; tt < TT; tt++) {
				if (s == n-1) {
					int st = 0;
					double sum = 0;
					for (int i = 0; i < ST[s-1]-1; i++) {
						sum += F[s-1][tt-1][i] * p[s-1]/100.0;
					}
					sum += F[s-1][tt-1][ST[s-1]-1];
					F[s][tt][st] += sum;
				} else {
					for (int st = 0; st < ST[s]; st++) {
						if (st == 0) {
							double sum = 0;
							for (int i = 0; i < ST[s-1]-1; i++) {
								sum += F[s-1][tt-1][i] * p[s-1]/100.0;
							}
							sum += F[s-1][tt-1][ST[s-1]-1];
							F[s][tt][st] += sum;
						} else {
							F[s][tt][st] += F[s][tt-1][st-1] * (1-p[s]/100.0);
						}
					}
				}
			}
		}
		
		double total = 0;
		double[] suma = new double[n]; 
		for (int s = 0; s < n; s++) {
			for (int st = 0; st < TT; st++) {
				suma[s] += F[s][TT-1][st];
			}
		}
		for (int i = 0; i < suma.length; i++) {
			total += i * suma[i];
		}
		return total;
	}

	public static void crazyTown() {
		long x1 = in.nextLong();
		long y1 = in.nextLong();
		long x2 = in.nextLong();
		long y2 = in.nextLong();
		
		int n = in.nextInt();
		long[][] p = new long[n][3];
		for (int i = 0; i < p.length; i++) {
			p[i] = in.nextLongArray(3);
		}
		System.out.println(crazyTown(x1,x2,y1,y2,p));
	}
	
	public static int crazyTown(long x1, long x2, long y1, long y2, long[][] p) {
		int between = 0;
		for (int i = 0; i < p.length; i++) {
			if (p[i][1] != 0) {
				double fx1 = -p[i][0] / (double)p[i][1] * x1 - p[i][2] / (double)p[i][1];
				double h1 = y1 - fx1;
				
				double fx2 = -p[i][0] / (double)p[i][1] * x2 - p[i][2] / (double)p[i][1];
				double h2 = y2 - fx2;
				
				if (Math.signum(h1) != Math.signum(h2)) {
					between++;
				}
			} else {
				double fy1 = -p[i][1] / (double)p[i][0] * x1 - p[i][2] / (double)p[i][0];
				double h1 = x1 - fy1;
				
				double fy2 = -p[i][1] / (double)p[i][0] * x2 - p[i][2] / (double)p[i][0];
				double h2 = x2 - fy2;
				
				if (Math.signum(h1) != Math.signum(h2)) {
					between++;
				}
			}
		}
		return between;
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
