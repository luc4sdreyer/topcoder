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
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class LunchTime22 {
	public static void main(String[] args) {
		//savingAaGiftOfLove(System.in);
		//chefAnup(System.in);
		//testChefAnup();
		paintingTheBoxes(System.in);
	}
	
	public static void paintingTheBoxes(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int w = scan.nextInt();
			int[] colours = scan.nextIntArray(n);
			int queries = scan.nextInt();
			int previous = 0;
			for (int i = 0; i < queries; i++) {
				int pos = scan.nextInt()-1;
				int colour = scan.nextInt();
				int c1 = 0;
				int c2 = colours.length-1;

				int len = 1;
				int ways = 0;
				int wa = 0;
				if (i > 0) {
					c1 = pos-1;
					while (c1 >= 1 && colours[c1] == colours[c1-1]) {
						c1--;
					};
					c1 = Math.max(c1, 0);
					
					c2 = pos+1;
					while (c2 < colours.length-1 && colours[c2] == colours[c2+1]) {
						c2++;
					}
					c2 = Math.min(c2, colours.length-1);
					
					len = 1;
					ways = 0;
					for (int j = c1+1; j <= c2; j++) {
						if (colours[j] != colours[j-1]) {
							 wa = len - (w-1);
							if (wa > 0) {
								ways += wa;
							}
							len = 1;
						} else {
							len++;
						}
					}
					wa = len - (w-1);
					if (wa > 0) {
						ways += wa;
					}
				}
				ways = previous - ways;
				
				colours[pos] = colour;
				len = 1;
				for (int j = c1+1; j <= c2; j++) {
					if (colours[j] != colours[j-1]) {
						wa = len - (w-1);
						if (wa > 0) {
							ways += wa;
						}
						len = 1;
					} else {
						len++;
					}
				}
				wa = len - (w-1);
				if (wa > 0) {
					ways += wa;
				}
				
				previous = ways;
				System.out.println(ways);
			}
		}
	}
	
	public static void paintingTheBoxes2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int w = scan.nextInt();
			int[] colours = scan.nextIntArray(n);
			int queries = scan.nextInt();
			for (int i = 0; i < queries; i++) {
				int pos = scan.nextInt()-1;
				int colour = scan.nextInt();
				colours[pos] = colour;
				int len = 1;
				int ways = 0;
				for (int j = 1; j < colours.length; j++) {
					if (colours[j] != colours[j-1]) {
						int wa = len - (w-1);
						if (wa > 0) {
							ways += wa;
						}
						len = 1;
					} else {
						len++;
					}
				}
				int wa = len - (w-1);
				if (wa > 0) {
					ways += wa;
				}
				System.out.println(ways);
			}
		}
	}

	public static void testChefAnup() {
		for (int i = 0; i < 27; i++) {
			System.out.println(chefAnup(3, 3, i));
		}
	}

	public static void chefAnup(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			long k = scan.nextLong();
			long l = scan.nextLong();
			
			System.out.println(chefAnup(n, k, l-1));			
		}
	}

	public static String chefAnup(int n, long k, long L) {
		long[] mag = new long[n];
		mag[0] = 1;
		for (int i = 1; i < mag.length; i++) {
			if (mag[i-1] > Long.MAX_VALUE / k) {
				break;
			}
			mag[i] = mag[i-1] * k;
		}
		
		int[] ans = new int[n];
		for (int i = n-1; i >= 0; i--) {
			if (mag[i] != 0) {
				ans[n - i -1] = (int) (L / mag[i]);
				//if (i != 0) {
				//}
				L = L % mag[i];
			}
			ans[n - i -1]++;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(ans[i] + " ");
		}
		return sb.toString();
	}

	public static String chefAnup2(int n, long k, long L) {
		long[] mag = new long[n];
		mag[0] = 1;
		for (int i = 1; i < mag.length; i++) {
			mag[i] = mag[i-1] * k;
		}
		
		int[] ans = new int[n];
		for (int i = n-1; i >= 0; i--) {
			ans[n - i -1] = (int) (L / mag[i]);
			//if (i != 0) {
				ans[n - i -1]++;
			//}
			L = L % mag[i];
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(ans[i] + " ");
		}
		return sb.toString();
	}

	public static void savingAaGiftOfLove(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			long dist = scan.nextInt();
			int b = scan.nextInt();
			long[] foodX = new long[b];
			long[] foodPower = new long[b];
			for (int i = 0; i < b; i++) {
				foodX[i] = scan.nextLong();
				foodPower[i] = scan.nextLong();
			}
			int c = scan.nextInt();
			long[] tribalX = new long[c];
			long[] tribalMin = new long[c];
			long[] tribalPop = new long[c];
			for (int i = 0; i < c; i++) {
				tribalX[i] = scan.nextLong();
				tribalMin[i] = scan.nextLong();
				tribalPop[i] = scan.nextLong();
			}
			long low = 1;
			long high = 1L << 62;
			long mid = 0;
			long best = 0;
			while (low <= high) {
				mid = (low + high)/2;
				if (savingAaGiftOfLoveValid(mid, dist, foodX, foodPower, tribalX, tribalMin, tribalPop)) {
					best = mid;
					high = mid-1;
				} else {
					low = mid+1;
				}
			}
			System.out.println(best);
		}
	}

	public static boolean savingAaGiftOfLoveValid(long group, long dist, long[] foodX, long[] foodPower, long[] tribalX, long[] tribalMin, long[] tribalPop) {
		int f = 0;
		int t = 0;
		while (f < foodX.length) {
			if (t < tribalX.length && tribalX[t] < foodX[f]) {
				if (group >= tribalMin[t]) {
					group += tribalPop[t];
				}
				t++;
			} else {
				group -= foodPower[f];
				if (group < 1) {
					return false;
				}
				f++;
			}
		}
		if (group < 1) {
			return false;
		}
		return true;
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
					st = new StringTokenizer(br.readLine());
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