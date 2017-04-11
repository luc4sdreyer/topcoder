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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Long15_07 {
	public static void main(String[] args) {
		//COPS(System.in);
		SUMPAIR(System.in);
		//testSUMPAIR();
	}
	
	public static void testSUMPAIR() {
		Random rand = new Random(0);
		int tests = 1000;
		int maxdiff = 30;
		for (int i = 0; i < tests; i++) {
			for (int len = 1; len <= 8; len++) {
				for (int d = 1; d <= maxdiff ; d++) {
					int[] list = new int[len];
					for (int j = 0; j < list.length; j++) {
						list[j] = rand.nextInt(maxdiff);
					}
					long act = SUMPAIR(list.length, d, list);
					long exp = SUMPAIRslow(list.length, d, list);
					if (act != exp) {
						System.out.println("FAIL");
						SUMPAIR(list.length, d, list);
						SUMPAIRslow(list.length, d, list);
					}
				}
			}
		}
		System.out.println("i");
	}
	
	public static boolean next_permutation(int str[]) {
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public static long SUMPAIR(int m, int d, int[] list) {
		Arrays.sort(list);
		long sum = 0;
		for (int i = list.length-2; i >= 0; i--) {
			if (Math.abs(list[i+1] - list[i]) < d) {
				sum += list[i] + list[i+1];
				i--;
			}
		}
		return sum;
	}
	
	public static long SUMPAIRslow(int m, int d, int[] list) {
		long max = 0;
		Arrays.sort(list);
		do {
			long sum = 0;
			for (int i = 0; i < list.length-1; i+=2) {
				if (Math.abs(list[i+1] - list[i]) < d) {
					sum += list[i] + list[i+1];
				}
			}
			if (sum > max) {
				max = sum;
				//System.out.println(max + " \t "+Arrays.toString(list));
			}
			max = Math.max(max, sum);
		}
		while (next_permutation(list));
		return max;
	}
	
	public static void SUMPAIR(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int d = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			System.out.println(SUMPAIR(n, d, a));
		}
	}

	public static void COPS(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int M = scan.nextInt();
			int x = scan.nextInt();
			int y = scan.nextInt();
			int[] a = scan.nextIntArray(M);
			for (int i = 0; i < a.length; i++) {
				a[i]--;
			}
			int d = x*y;
			
			boolean[] safe = new boolean[100];
			Arrays.fill(safe, true);
			for (int i = 0; i < a.length; i++) {
				for (int j = a[i] - d; j <= a[i] + d; j++) {
					if (j >= 0 && j < safe.length) {
						safe[j] = false;
					}
				}
			}
			
			int n = 0;
			for (int i = 0; i < safe.length; i++) {
				if (safe[i]) {
					n++;
				}
			}
			System.out.println(n);
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
