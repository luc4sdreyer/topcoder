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


public class R277 {
	public static void main(String[] args) {
		//System.out.println(calculatingFunction(System.in));
		//System.out.println(orInMatrix(System.in));
		//System.out.println(palindromeTransformation(System.in));
		test();
		
	}
	
	public static void test() {
		Random rand = new Random(0);
		int numTests = 1000;
		for (int i = 0; i < numTests; i++) {
			char[] w = new char[rand.nextInt(numTests)+1];
			for (int j = 0; j <= w.length/2; j++) {
				char r = (char) (rand.nextInt(26)+'a');
				w[j] = r;
				w[w.length -j -1] = r;
			}
			int ops = rand.nextInt(numTests/10);
			int start = rand.nextInt(w.length);
			int idx = start;
			for (int j = 0; j < ops; j++) {
				int type = rand.nextInt(2);
				if (type == 0) {
					idx = (idx + 1 + w.length) % w.length;
				} else if (type == 1) {
					w[idx] = (char) ((w[idx] - 'a' + 1) % 26 + 'a'); 
				} 
			}
			
			int actual = palindromeTransformation(w.length, start, w);
			if (actual < 0) {
				System.nanoTime();
			}
			if (ops != actual) {
				System.nanoTime();
				palindromeTransformation(w.length, start, w);
			}
		}
	}

	public static String palindromeTransformation(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int p = scan.nextInt() - 1;
		char[] w = scan.next().toCharArray();
		
		return palindromeTransformation(n, p, w) + "";
	}

	private static int palindromeTransformation(int n, int p, char[] w) {
		int upcost = 0;
		int[] first = {-1, 0};
		int[] last = {-1, 0};
		int minP = Integer.MAX_VALUE;
		
		for (int i = 0; i < w.length/2; i++) {
			if (w[i] != w[w.length -i -1]) {
				if (first[0] == -1) {
					first[0] = i;
					last[1] = w.length -i -1;
				}
				last[0] = i;
				first[1] = w.length -i -1;
				int max = Math.max(w[i], w[w.length -i -1]) - 'a';
				int min = Math.min(w[i], w[w.length -i -1]) - 'a';
				upcost += Math.min(26 - max + min, max - min);
			}
		}
		for (int j = 0; j < last.length; j++) {
			minP = Math.min(Math.abs(p - last[j]), minP);
		}

		for (int j = 0; j < first.length; j++) {
			minP = Math.min(Math.abs(p - first[j]), minP);
		}
		int movecost = Math.abs(last[0] - first[0]) + minP;
		
		
		return (upcost + movecost);
	}

	public static String orInMatrix(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		boolean[][] a = new boolean[n][m];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = scan.nextInt() == 1 ? true : false;
			}
		}
		return orInMatrix(n, m, a);
	}

	public static String orInMatrix(int n, int m, boolean[][] a) {
		boolean[][] b = new boolean[n][m];
		for (int i = 0; i < b.length; i++) {
			b[i] = a[i].clone();
		}
		
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < m; x++) {
				if (!a[y][x]) {
					for (int i = 0; i < n; i++) {
						b[i][x] = false;
					}
					for (int j = 0; j < m; j++) {
						b[y][j] = false;
					}
				}
			}
		}
		
		boolean[][] c = new boolean[n][m];
		for (int i = 0; i < c.length; i++) {
			c[i] = b[i].clone();
		}
		
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < m; x++) {
				if (b[y][x]) {
					for (int i = 0; i < n; i++) {
						c[i][x] = true;
					}
					for (int j = 0; j < m; j++) {
						c[y][j] = true;
					}
				}
			}
		}
		
		boolean valid = true;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (c[i][j] != a[i][j]) {
					valid = false;
				}
			}
		}
		if (!valid) {
			return "NO";
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("YES\n");
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (j != 0) {
						sb.append(" ");
					}
					if (b[i][j]) {
						sb.append(1);
					} else {
						sb.append(0);
					}
				}
				sb.append("\n");
				
			}
			return sb.toString();
		}
	}

	public static String calculatingFunction(InputStream in) {
		MyScanner scan = new MyScanner(in);
		long n = scan.nextLong();
		return f3(n) + "";
	}
	
	public static long f3(long n) {
		return (n % 2 == 0 ? n/2 : -(n/2 + 1));
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
