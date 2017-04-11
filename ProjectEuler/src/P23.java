import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class P23 {

	public static void main(String[] args) {
		MyScanner scan = new MyScanner(System.in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		HashSet<Integer> absum = nonAbundantSums();
		for (int i = 0; i < a.length; i++) {
			if (absum.contains(a[i]) || a[i] > 28123) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}
	
	public static HashSet<Integer> nonAbundantSums() {
		int max = 28123;
		ArrayList<Integer> ab = new ArrayList<>();
		HashSet<Integer> absum = new HashSet<>();
		for (int i = 0; i <= max; i++) {
			long sum = 1;
			for (int j = 2; j*j <= i; j++) {
				if (i % j == 0) {
					sum += j;
					if (j*j != i) {
						sum += i / j;
					}
				}
			}
			if (sum > i) {
				ab.add(i);
			}
		}
		
		for (int i : ab) {
			for (int j : ab) {
				int prod = i+j;
				if (prod > max) {
					break;
				}
				absum.add(prod);
			}
		}
		return absum;
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
