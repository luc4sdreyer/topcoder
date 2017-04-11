import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SnackDown20161A {
	public static void main(String[] args) {
		testMAKELIS();
	}

	public static void testMAKELIS() {
		System.out.println(getNumLIS(new int[]{3,1,7,5,11,9,15,13}));
		int k = 27;
		Random rand = new Random(0);
		for (int len = 9; len <= 15; len++) {
			for (int i = 0; i < 1000000; i++) {
				int[] a = new int[len];
				BitSet set = new BitSet();
				for (int j = 0; j < a.length; j++) {
					int next = rand.nextInt(len)+1;
					while (set.get(next)) {
						next = rand.nextInt(len)+1;
					}
					set.set(next);
					a[j] = next;
				}

				int x = getNumLIS(a);

				if (x == k) {
					System.out.println(Arrays.toString(a));
					break;
				}
			}
		}
	}

	public static int getNumLIS(int[] a) {
		int len2 = a.length;
		int N = 1 << len2;
		int[] dist = new int[len2+1];
		for (int n = 0; n < N; n++) {
			int last = -1;
			int len3 = 1;
			int total = 0;
			for (int p = 0; p < len2; p++) {
				if (((1 << p) & n) != 0) {
					total++;
					if (last == -1) {
						last = a[p];
					} else if (last < a[p]) {
						last = a[p];
						len3++;
					}
				}
			}
			if (len3 == total) {
				dist[len3]++;
			}
		}
		for (int j = dist.length-1; j >= 0; j--) {
			if (dist[j] != 0) {
				return dist[j];
			}
		}
		return 0;
	}

	public static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream in) {this.br = new BufferedReader(new InputStreamReader(in));}
		public void close() {try {this.br.close();} catch (IOException e) {e.printStackTrace();}}
		String next() {
			while (st == null || !st.hasMoreElements()) {
			try {
				String s = br.readLine();
				if (s != null) {
					st = new StringTokenizer(s);
				}
				else {return null;}}
			catch (IOException e) {e.printStackTrace();}
			} return st.nextToken();
		}
		long[] nextLongArray(int n) {long[] a = new long[n]; for (int i = 0; i < a.length; i++) {a[i] = this.nextLong();} return a;}
		int[] nextIntArray(int n) {int[] a = new int[n]; for (int i = 0; i < a.length; i++) {a[i] = this.nextInt();} return a;}
		int nextInt() {return Integer.parseInt(next());}
		long nextLong() {return Long.parseLong(next());}
		double nextDouble() {return Double.parseDouble(next());}
		String nextLine() {String str = "";	try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
