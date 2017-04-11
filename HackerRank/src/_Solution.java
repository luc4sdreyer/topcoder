import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class _Solution {
	public static void main(String[] args) {
//		angryProfessor(System.in);
//		serviceLane(System.in);
//		cutTheSticks(System.in);
//		cavityMap(System.in);
//		lisasWorkbook(System.in);
		sherlockAndArray(System.in);
	}

	public static void sherlockAndArray(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			long[] a = scan.nextLongArray(n);
			long sumR = 0;
			for (int i = 0; i < a.length; i++) {
				sumR += a[i];
			}
			long sumL = 0;
			
			boolean found = false;
			for (int i = 0; i < a.length; i++) {
				sumR -= a[i];
				if (sumR == sumL) {
					found = true;
					break;
				}
				sumL += a[i];
			}
			if (found) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}

	public static void lisasWorkbook(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] t = scan.nextIntArray(n);
		
		int chapter = 0;
		int testStart = 1;
		int spec = 0;
		for (int page = 1; page < 1000000000; page++) {
			if (t[chapter] <= k) {
				if (page >= testStart && page < testStart + t[chapter]) {
					spec++;
				}
				chapter++;
				testStart = 1;
				if (chapter == n) {
					break;
				}
			} else {
				if (page >= testStart && page < testStart + k) {
					spec++;
				}
				t[chapter] -= k;
				testStart += k;
			}
		}
		System.out.println(spec);
	}

	public static void cavityMap(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		char[][] ref = new char[n][];
		char[][] map = new char[n][];
		for (int i = 0; i < ref.length; i++) {
			ref[i] = scan.nextLine().toCharArray();
			map[i] = ref[i].clone();
		}
		
		int[] dx = {0, 1, 0, -1};
		int[] dy = {-1, 0, 1, 0};
		
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				int num = 0;
				for (int i = 0; i < dy.length; i++) {
					int x2 = x + dx[i];
					int y2 = y + dy[i];
					if (x2 >= 0 && x2 < n && y2 >= 0 && y2 < n && ref[y2][x2] < ref[y][x]) {
						num++;
					}
				}
				if (num == 4) {
					map[y][x] = 'X';
				}
			}
			System.out.println(new String(map[y]));
		}
	}

	public static void cutTheSticks(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		Arrays.sort(a);
		int[] len = new int[1001];
		for (int i = 0; i < a.length; i++) {
			len[a[i]]++;
		}
		int sum = 0;
		for (int i = len.length-1; i >= 0; i--) {
			sum += len[i];
			if (len[i] != 0) {
				len[i] = sum;
			}
		}
		for (int i = 0; i < len.length; i++) {
			if (len[i] != 0) {
				System.out.println(len[i]);
			}
		}
	}

	public static void serviceLane(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int tests = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		for (int test = 0; test < tests; test++) {
			int i = scan.nextInt();
			int j = scan.nextInt();
			
			int min = 10;
			for (int p = i; p <= j; p++) {
				min = Math.min(min, a[p]);
			}
			System.out.println(min);
		}
	}

	public static void angryProfessor(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int sum = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] <= 0) {
					sum++;
				}
			}
			if (sum < k) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
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
