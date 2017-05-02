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


public class R93 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);

		Password();

		out.close();
	}

	public static void Password() {
		char[] s = in.next().toCharArray();
		int[] zMid = zalgo(Arrays.copyOf(s, s.length-1));
		int[] zMidMax = new int[zMid.length+1];
		for (int i = 1; i < zMidMax.length; i++) {
			int mx = i < zMid.length ? zMid[i] : 0;
			zMidMax[i] = Math.max(zMidMax[i-1], mx);
		}
		int[] zEnd = zalgo(s);
		int max = 0;
		for (int i = 1; i < zEnd.length; i++) {
			if (zEnd[i] > 0 && zMidMax[i] >= zEnd[i] && i + zEnd[i] == zEnd.length) {
				max = Math.max(max, zEnd[i]);
			}
		}
		if (max == 0) {
			out.println("Just a legend");
		} else {
			out.println(new String(Arrays.copyOf(s, max)));
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
