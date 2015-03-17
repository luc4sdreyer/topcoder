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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;


public class Rockethon2015 {
	public static void main(String[] args) {
		//game(System.in);
		//testPermutations2();
		//permutations(System.in);
		inversionsProblem(System.in);
	}
	
	public static void reverse(int[] a, int start, int end) {
		for (int i = start; i < (end + start + 1)/2; i++) {
			int temp = a[i];
			a[i] = a[start + end - i];
			a[start + end - i] = temp;
		}
	}
	
	public static void inversionsProblem(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		inverse(a, n, k);
		System.out.println(numInv / (double)numCount);
	}
	
	public static int numCount = 0;
	public static int numInv = 0;
	
	public static void inverse(int[] a, int n, int k) {
		if (k == 0) {
			numCount++;
			for (int i = 0; i < a.length; i++) {
				for (int j = i+1; j < a.length; j++) {
					if (a[i] > a[j]) {
						numInv++;
					}
				}
			}
		} else {
			for (int i = 0; i < a.length; i++) {
				for (int j = i; j < a.length; j++) {
					reverse(a, i, j);
					inverse(a, n, k-1);
					reverse(a, i, j);
				}
			}
		}
	}

	public static int[] fastPerm(int n, long m) {
		long numMax = 1L << (n-1);
		int[] p = new int[n];
		if (m == numMax) {
			for (int j = 0; j < p.length; j++) {
				p[j] = p.length - j;
			}
			return p;
		}
		int idx = 0;
		int level = 1;
		boolean last = true;
		while (numMax > 0) {
			if (numMax == 1 && last) {
				last = false;
			} else {
				numMax /= 2;
			}
			if (m <= numMax) {
				p[idx] = level;
				idx++;
			} else {
				m -= numMax;
			}
			level++;
		}
		HashSet<Integer> num = new HashSet<>();
		for (int i = 0; i < p.length; i++) {
			num.add(p[i]);
		}
		for (int i = n; i >= 1; i--) {
			if (!num.contains(i)) {
				p[idx++] = i;
			}
		}
		return p;
	}
	
	public static void permutations(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		long m = scan.nextLong();

		int[] p = fastPerm(n, m);
		String out = "";
		for (int i = 0; i < p.length; i++) {
			out += p[i] + " ";
		}
		System.out.println(out);
	}
	
	public static int[] permutations(int n, long m) {
		int[] p = new int[n];
		for (int j = 0; j < p.length; j++) {
			p[j] = j+1;
		}
		int max = permf(p);
		int numMax = 0;
		do {
			int score = permf(p);
			if (score == max) {
				numMax++;
			}
			if (numMax == m) {
				return p;
			}
		} while (next_permutation(p));
		return p;
	}
	
	public static int permf(int[] p) {
		int score = 0;
		for (int i = 0; i < p.length; i++) {
			for (int j = i; j < p.length; j++) {
				int min = Integer.MAX_VALUE;
				for (int k = i; k <= j; k++) {
					min = Math.min(min, p[k]);
				}
				score += min;
			}
		}
		return score;
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
	
	public static void testPermutations2() {
		for (int i = 1; i < 9; i++) {
			int[] p = new int[i];
			for (int j = 0; j < p.length; j++) {
				p[j] = j+1;
			}
			long numMax = 1 << (i-1);
			for (int j = 1; j <= numMax; j++) {
				if (!Arrays.equals(permutations(i, j), fastPerm(i, j))) {
					System.out.println();
				}
			}
		}
	}

	public static void testPermutations() {
		for (int i = 4; i < 7; i++) {
			System.out.println("perm: " + i);
			int[] p = new int[i];
			for (int j = 0; j < p.length; j++) {
				p[j] = j+1;
			}
			int j = 0;
			int max = permf(p);
			int numMax = 0;
			do {
				int score = permf(p);
				if (score == max) {
					numMax++;
				}
				System.out.println(j + " " + Arrays.toString(p) + "\t " + score + (score == max ? (" max" + Arrays.toString(fastPerm(i, numMax)) ) : ""));
			} while (next_permutation(p));
			System.out.println(numMax);
		}
	}

	public static void game(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n1 = scan.nextInt();
		int n2 = scan.nextInt();
		scan.nextInt();
		scan.nextInt();
		if (n1 > n2) {
			System.out.println("First");
		} else {
			System.out.println("Second");
		}
	}

	public static int depth(char top, HashMap<Character, HashSet<Character>> dep, int[] depth) {
		if (depth[top - 'a'] > -1) {
			return depth[top - 'a'];
		}
		int max = 0;
		HashSet<Character> children = dep.get(top);
		for (char child: children) {
			max = Math.max(max, depth(child, dep, depth) + 1);
		}
		depth[top - 'a'] = max;
		return max;
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
