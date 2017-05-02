import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class R299 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);

//		TavasAndKarafs();
		TavasAndMalekas();

		out.close();
	}

	public static void TavasAndMalekas() {
		int n = in.nextInt();
		int m = in.nextInt();
		char[] s = in.next().toCharArray();
		int[] a = in.nextIntArray(m);
		for (int i = 0; i < a.length; i++) {
			a[i]--;
		}

		int[] z = zalgo(s);
		for (int i = 0; i < z.length; i++) {
			if (z[i] + i == z.length) {
				z[i] = 1;
			}
		}
		long mod = 1000 * 1000 * 1000 + 7;
		int prevEnd = -1;
		int empty = 0;
		boolean valid = true;
		
		for (int i = 0; i < a.length; i++) {
			empty += Math.max(0, a[i] - prevEnd -1);
			if (prevEnd < i) {
				// nothing to do
			} else {
				// overlap
				int idx = s.length - (prevEnd - a[i]) - 1;
				if (idx >= 0 && idx < z.length) {
					if (z[idx] != 1) {
						valid = false;
						break;
					}
				}
			}
			prevEnd = a[i] + s.length -1;
		}
		
		empty += n - prevEnd -1;
		
		long ways = 1;
		for (int i = 0; i < empty; i++) {
			ways = (ways * 26L) % mod;
		}
		if (valid) {
			System.out.println(ways);
		} else {
			System.out.println(0);
		}
	}
	
	public static int[] zalgo(char[] s) {
		int L = 0;
		int R = 0;
		int n = s.length;
		int[] z = new int[n];
		
		for (int i = 1; i < n; i++) {
			if (i > R) {
				L = R = i;
				while (R < n && s[R-L] == s[R]) {
					R++;
				}
				z[i] = R-L; R--;
			} else {
				int k = i-L;
				if (z[k] < R-i+1) {
					z[i] = z[k];
				} else {
					L = i;
					while (R < n && s[R-L] == s[R]) {
						R++;
					}
					z[i] = R-L; R--;
				}
			}
		}
		return z;
	}

	public static void TavasAndKarafs() {
		long A = in.nextLong();
		long B = in.nextLong();
		long n = in.nextLong();
		
		for (int i = 0; i < n; i++) {
			long L = in.nextLong();
			long T = in.nextLong();
			long M = in.nextLong();
			
			// shortest possible
			if (f(A, B, L) > T) {
				out.println(-1);
			} else {
				long low = L;
				long high = getX(A, B, L, T, L+M*T);
				long lastValid = -1;
				while (low <= high) {
					long mid = (low + high) >>> 1;
					long fx = fs(A, B, L, mid);
					if (fx <= T*M) {
						low = mid + 1;
						lastValid = mid;
					} else if (fx > T*M) {
						high = mid - 1;
					}
				}
				out.println(lastValid);
			}
		}
	}

	public static long getX(long a, long b, long L, long F, long max) {
		long low = L;
		long high = max;
		long lastValid = -1;
		while (low <= high) {
			long mid = (low + high) >>> 1;
			long fx = f(a, b, mid);
			if (fx <= F) {
				low = mid + 1;
				lastValid = mid;
			} else if (fx > F) {
				high = mid - 1;
			}
		}
		return lastValid;
	}

	public static long f(long a, long b, long x) {
		return a + (x-1)*b;
	}

	public static long fs(long a, long b, long L, long R) {
		return (R-L+1)*a + b*(R*(R-1) - (L-1)*(L-2))/2;
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
