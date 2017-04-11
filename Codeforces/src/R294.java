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
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class R294 {
	public static void main(String[] args) {
		interestingSubstrings(System.in);
//		testTeamTraining();
//		teamTraining(System.in);
//		compilationErrors(System.in);
//		chess(System.in);
	}

	public static void interestingSubstrings(InputStream in) {
		MyScanner scan = new MyScanner(in);
		long[] a = scan.nextLongArray(26);
		char[] stemp = scan.next().toCharArray();
		int[] s = new int[stemp.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = stemp[i] - 'a';
		}
		
		long[] sum = new long[s.length];
		sum[0] = a[s[0]];
		for (int i = 1; i < sum.length; i++) {
			sum[i] = sum[i-1] + a[s[i]];
		}
		
		long num = 0;
		for (int c = 0; c < 26; c++) {
			HashMap<Long, Integer> map = new HashMap<>();
			
			for (int i = 0; i < sum.length; i++) {
				if (i > 0 && s[i] == c) {
					// close
					if (map.containsKey(sum[i-1])) {
						num += map.get(sum[i-1]);
					}
				}
				if (s[i] == c) {
					// opening
					if (!map.containsKey(sum[i])) {
						map.put(sum[i], 1);
					} else {
						map.put(sum[i], map.get(sum[i]) + 1);
					}
				}
			}
		}
		
		System.out.println(num);
	}
	
	public static void testTeamTraining() {
		Random rand = new Random(0);
		for (int i = 0; i < 1000000; i++) {
			int n = rand.nextInt(100);
			int m = rand.nextInt(100);
			
			int max = 0;
			for (int t1 = 0; t1 <= n; t1++) {
				for (int t2 = 0; t2 <= m; t2++) {
					// t1 = n m m 
					int costN = t1 + t2*2;
					int costM = t1*2 + t2;
					if (costN <= n && costM <= m) {
						max = Math.max(max, t1 + t2);
					}
				}
			}
			
			int m2 = teamTraining(n, m);
			if (max != m2) {
				teamTraining(n, m);
				System.out.println("fail");
			}
			
		}
	}

	public static void teamTraining(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		System.out.println(teamTraining(n, m));
	}
	
	public static int teamTraining(int n, int m) {
		int b = Math.max(n, m);
		int a = Math.min(n, m);
		if (b >= 2*a) {
			return a;
		}
		int num = 0;
		while (b < 2*a && b >= 2 && a >= 1) {
			if (b == a) {
				num += (a/3) * 2;
				b %= 3;
				a %= 3;
				int b2 = Math.max(b, a);
				int a2 = Math.min(b, a);
				if (b2 >= 2 && a2 >= 1) {
					num++;
				}
				break;
			}
			b -= 2;
			a--;
			num++;
		}
		return num;
	}

	public static void compilationErrors(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[][] a = new int[3][];
		for (int i = 0; i < 3; i++) {
			a[i] = scan.nextIntArray(n-i);
		}
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			map.put(a[0][i], 0);
		}
		TreeMap<Integer, Integer> mapBig2small = new TreeMap<>();
		TreeMap<Integer, Integer> mapSmall2big = new TreeMap<>();

		int x = 0;
		for (Integer key: map.keySet()) {
			mapBig2small.put(key, x);
			mapSmall2big.put(x, key);
			x++;
		}
		
		int[][] freq = new int[3][n];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				freq[i][mapBig2small.get(a[i][j])]++;
			}
		}
		
		for (int i = 1; i < freq.length; i++) {
			for (int j = 0; j < freq[0].length; j++) {
				if (freq[i-1][j] > freq[i][j]) {
					System.out.println(mapSmall2big.get(j));
					break;
				}
			}
		}
	}
	
	public static void chess(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[][] b = new char[8][8];
		for (int i = 0; i < b.length; i++) {
			b[i] = scan.next().toCharArray();
		}
		HashMap<Character, Integer> map = new HashMap<>();
		map.put('Q', 9);
		map.put('R', 5);
		map.put('B', 3);
		map.put('N', 3);
		map.put('P', 1);
		
		map.put('q', -9);
		map.put('r', -5);
		map.put('b', -3);
		map.put('n', -3);
		map.put('p', -1);
		
		int score = 0;
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (map.containsKey(b[i][j])) {
					score += map.get(b[i][j]);
				}
			}
		}
		
//		System.out.println(score);
		if (score == 0) {
			System.out.println("Draw");
		} else if (score > 0) {
			System.out.println("White");
		} else {
			System.out.println("Black");
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
