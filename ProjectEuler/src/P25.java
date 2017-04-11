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

public class P25 {

	public static void main(String[] args) {
		ArrayList<Integer> f = NdigitFibonacciNumber();
		MyScanner scan = new MyScanner(System.in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		for (int i = 0; i < a.length; i++) {
			System.out.println(f.get(a[i]));
		}
	}
	
	public static ArrayList<Integer> NdigitFibonacciNumber() {
		int max = 1;
		BigInteger a = BigInteger.ZERO;
		BigInteger b = BigInteger.ONE;
		BigInteger old = a;
		ArrayList<Integer> first = new ArrayList<>();
		first.add(0);
		first.add(1);
		
		int i = 2;
		int lastFound = -20;
		int step = 4;
		while (max <= 5000) {
			old = b;
			b = a.add(b);
			a = old;
			if (i >= lastFound + step || i <= 21) {
				int size = b.toString().length();
				if (size > max) {
					lastFound = i;
					max = size;
					first.add(i);
					if (first.get(first.size()-1) - first.get(first.size()-2) == 4) {
						first.add(i+5);
						first.add(i+10);
						first.add(i+15);
						step = 19;
						max += 3;
					} else {
						step = 4;
					}
	//				System.out.println(i + " \t" + (first.get(first.size()-1) - first.get(first.size()-2)));
				}
			}
			i++;
		}
		return first;
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
