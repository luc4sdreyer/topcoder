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

public class P24 {

	public static void main(String[] args) {
		MyScanner scan = new MyScanner(System.in);
		int n = scan.nextInt();
		long[] a = scan.nextLongArray(n);
		String w = "abcdefghijklm";
		for (int i = 0; i < a.length; i++) {
			ArrayList<Character> word = new ArrayList<>();
			for (int j = 0; j < w.length(); j++) {
				word.add(w.charAt(j));
			}
			nth_permutation(word, a[i]-1);
			String out = "";
			for (int j = 0; j < w.length(); j++) {
				out += word.get(j);
			}
			System.out.println(out);
		}
	}
	
	public static void nth_permutation(ArrayList<Character> str, long n) {
		long a = 1L;
		for (int i = 2; i < str.size(); i++) {
			a *= i;
		}
		for (int i = 0; i < str.size()-1; i++) {
			int idx = (int) (n / a);
			n %= a;
			a /= str.size() - i - 1; 
			str.add(i, str.remove(i+idx));
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
