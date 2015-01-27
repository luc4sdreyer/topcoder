import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.bind.ValidationEvent;


public class R288 {
	public static void main(String[] args) {
		//pashaAndPixels(System.in);
		//antonAndCurrencyYouAllKnow(System.in);
		//anyaAndGhosts(System.in);
		//arthurAndBrackets(System.in);
		test();
	}
	
	public static void test() {
		Random rand = new Random(0);
		long max = 0;
		for (int i = 0; i < 1000; i++) {
			int limit = 600;
			int[][] a = new int[limit][2];
			for (int j = 0; j < a.length; j++) {
				int x = rand.nextInt(limit-1)+1;
				int y = rand.nextInt(limit - x)+1;
				a[j][0] = x;
				a[j][1] = y;
			}
			long time = System.currentTimeMillis();
			arthurAndBrackets(limit, a);
			time = System.currentTimeMillis() - time;
			if (time > 100) {
				System.out.println(time);
			}
			max = Math.max(max, time);
		}
		System.out.println("max:" + max);
	}

	public static class Node {
		char[] seq;
		int depth = 0;
		int start = 0;
		public Node(char[] seq, int depth, int start) {
			super();
			this.seq = seq;
			this.depth = depth;
			this.start = start;
		}
	}
	public static void arthurAndBrackets(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[][] a = new int[n][2];
		for (int i = 0; i < a.length; i++) {
			a[i][0] = scan.nextInt();
			a[i][1] = scan.nextInt();
		}
		
		System.out.println(arthurAndBrackets(n, a));
	}
	
	private static String arthurAndBrackets(int n, int[][] a) {

		char[] seq2 = new char[2*n];
		Node top = new Node(seq2, 0, 0);
		Stack<Node> s = new Stack<>();
		s.push(top);
		char[] best = null;
		while (!s.isEmpty()) {
			top = s.pop();
			if (top.depth == seq2.length/2) {
				best = top.seq;
				break;
			}
			
			for (int i = 0; i < top.seq.length; i++) {
				if (top.seq[i] == 0) {
					int nextStart = i;

					//for (int j = nextStart + a[top.depth][0]; j <= nextStart + a[top.depth][1] && j < top.seq.length; j++) {
					for (int j = nextStart + a[top.depth][1]; j >= nextStart + a[top.depth][0]; j--) {
						if (top.seq[j] == 0) {
							char[] nextSeq = top.seq.clone();
							nextSeq[i] = '(';
							nextSeq[j] = ')';
							s.push(new Node(nextSeq, top.depth+1, nextStart));
						}
					}
					break;
				}
			}
		}
		if (best != null) {
			return new String(best);
		} else {
			return "IMPOSSIBLE";
		}
	}

	public static void anyaAndGhosts(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int m = scan.nextInt();
		int t = scan.nextInt();
		int r = scan.nextInt();
		int[] w = scan.nextIntArray(m);
		int[] stack = new int[1000];
		int offset = 350;
		int num = 0;
		for (int i = 0; i < w.length; i++) {
			int back = 0;
			if (offset + w[i] < 0) {
				System.out.println(-1);
				return;
			}
			while (stack[offset + w[i]] < r) {
				num++;
				for (int j = 0; j < t; j++) {
					if (back + offset + w[i] + j < 0) {
						System.out.println(-1);
						return;
					}
					stack[back + offset + w[i] + j]++;
				}
				back--;
			}
		}
		System.out.println(num);
	}
	
	public static void antonAndCurrencyYouAllKnow(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] n = scan.nextLine().toCharArray();
		int best = -1;
		int end = n[n.length-1] - '0';
		int last = -1;
		for (int i = 0; i < n.length; i++) {
			int x = n[i] - '0';
			if (x % 2 == 0) {
				if (best == -1 && end > x) {
					best = i;
					break;
				}
				last = i;
			}
		}
		if (best == -1) {
			best = last;
		}
		if (best != -1) {
			char temp = n[best];
			n[best] = n[n.length-1];
			n[n.length-1] = temp;
			System.out.println(new String(n));
		} else {
			System.out.println(-1);
		}
	}
	
	public static void pashaAndPixels(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int k = scan.nextInt();
		boolean[][] grid = new boolean[n][m];
		for (int p = 0; p < k; p++) {
			int y = scan.nextInt()-1;
			int x = scan.nextInt()-1;
			grid[y][x] = true;
			for (int i = y-1; i <= y; i++) {
				for (int j = x-1; j <= x; j++) {
					int count = 0;
					for (int i0 = i; i0 < i+2; i0++) {
						for (int j0 = j; j0 < j+2; j0++) {
							if (i0 >= 0 && i0 < n && j0 >= 0 && j0 < m) {
								if (grid[i0][j0]) {
									count++;
								}
							}
						}
					}
					if (count == 4) {
						System.out.println(p+1);
						return;
					}
				}
			}
		}
		System.out.println(0);
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
