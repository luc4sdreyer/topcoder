import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Feb15Long {
	public static void main(String[] args) throws IOException {
		//chefAndChain(System.in);
		//chefAndEquality(System.in);
		//letUsPlayWithRankList(System.in);
		//chefAndStrangeFormula(System.in);
		chefAndStrings2(System.in);
		//testShefAndStrings();
	}
	
	public static void testShefAndStrings() {
		int maxLength = 1000000;
		long time = System.currentTimeMillis();
		Random rand = new Random(0);
		int[] str = new int[maxLength];
		int[][] q = new int[maxLength][4];
		for (int i = 0; i < str.length; i++) {
			str[i] = rand.nextInt(4);
			q[i][0] = 0;
			q[i][1] = 1;
			q[i][2] = 1;
			q[i][3] = maxLength;
		}
		chefAndStrings2(str, q);
		System.out.println("time: "+ (System.currentTimeMillis() - time));
	}

	public static int charToInt(char c) {
		int ret = 0;
		if (c == 'c') {
			ret = 0;
		} else if (c == 'h') {
			ret = 1;
		} else if (c == 'e') {
			ret = 2;
		} else {
			ret = 3;
		}
		return ret;
	}

	public static void chefAndStrings2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] str = scan.nextLine().toCharArray();
		int[] s = new int[str.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = charToInt(str[i]);
		}
		int nq = scan.nextInt();		
		int[][] q = new int[nq][4];
		for (int i = 0; i < nq; i++) {
			q[i][0] = charToInt(scan.next().charAt(0));
			q[i][1] = charToInt(scan.next().charAt(0));
			q[i][2] = scan.nextInt();
			q[i][3] = scan.nextInt();
		}
		
		System.out.println(chefAndStrings2(s, q));
	}
	
	public static String chefAndStrings2(int[] s, int[][] q) {
		StringBuilder sb = new StringBuilder();
		long[][] b = new long[s.length+1][4];
		for (int i = 1; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				b[i][j] = b[i-1][j];
				if (s[i-1] == j) {
					b[i][j]++;	
				}
			}
		}
		long[][] a = new long[16][s.length+1];		
		{
			int c1 = 0;
			int c2 = 0;
			for (int i = 0; i < 16; i++) {
				c1 = i / 4;
				c2 = i % 4;
				for (int j = 1; j < a[0].length; j++) {
					if (c1 != c2) {
						if (s[j-1] == c1) {
							a[i][j] = a[i][j-1] + b[j][c2];
						} else {
							a[i][j] = a[i][j-1];
						}
					}
				}
			}
		}
		
		for (int i = 0; i < q.length; i++) {
			int c1 = q[i][0];
			int c2 = q[i][1];
			int start = q[i][2];
			int end = q[i][3];
			long total = -(a[c1*4 + c2][end-1] - a[c1*4 + c2][start-1]);
			total += b[end][c2] * (b[end-1][c1] - b[start-1][c1]);
			sb.append(total + "\n");
		}
		
		return sb.toString();
	}

	public static void chefAndStrings(InputStream in) {
		MyScanner scan = new MyScanner(in);
		HashMap<Character, Integer> map = new HashMap<>();
		map.put('c', 0);
		map.put('h', 1);
		map.put('e', 2);
		map.put('f', 3);
		char[] str = scan.nextLine().toCharArray();
		int[] s = new int[str.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = map.get(str[i]);
		}
		int nq = scan.nextInt();
		
		int[][] b = new int[s.length+1][4];
		for (int i = 1; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				b[i][j] = b[i-1][j];
				if (s[i-1] == j) {
					b[i][j]++;	
				}
			}
		}
		
		for (int i = 0; i < nq; i++) {
			int c1 = map.get(scan.next().charAt(0));
			int c2 = map.get(scan.next().charAt(0));
			int start = scan.nextInt();
			int end = scan.nextInt();
			long total = 0;
			for (int j = start; j < end; j++) {
				if (s[j-1] == c1) {
					total += b[end][c2] - b[j][c2]; 
				}
			}
			System.out.println(total);
		}
		System.out.println();
	}

	public static void chefAndStrangeFormula(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		long m = scan.nextLong();
		long[] a = scan.nextLongArray(n);
		BigInteger sum = BigInteger.ZERO;
		long[] fref = new long[10000];
		fref[1] = 1;
		for (int i = 2; i < fref.length; i++) {
			fref[i] = (fref[i-1] * i) % m;
		}
		long fact = 1;
		for (int i = 0; i < a.length; i++) {
			fact = 1;
			if (m <= a[i]+1) {
				fact = 0;
			} else if (a[i]+1 < fref.length) {
				fact = fref[(int) (a[i]+1)];
			} else {
				for (long j = 1; j <= a[i]+1; j++) {
					fact = (fact * j) % m;
					if (fact == 0) {
						break;
					}
				}
			}
			BigInteger p = BigInteger.valueOf(a[i]);
			sum = sum.add(p.multiply(p.multiply(p.add(BigInteger.ONE))).divide(BigInteger.valueOf(2)).add(BigInteger.valueOf(fact-1 + m))).mod(BigInteger.valueOf(m));
		}
		System.out.println(sum);
	}

	public static void letUsPlayWithRankList(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			long n = scan.nextLong();
			long s = scan.nextLong();
			long ideal = n*(n+1)/2;
			long changes = 0;
			if (s == ideal) {
				changes = 0;
			} else if (s > ideal) {
				changes = 1;
			} else {
				s -= n;
				changes = 1;
				while (s >= changes) {
					s -= changes++;
				}
				changes = n - changes;
			}
			System.out.println(changes);
		}
	}

	public static void chefAndEquality(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int[] freq = new int[1000001];
			int max = 0;
			for (int i = 0; i < a.length; i++) {
				freq[a[i]]++;
				max = Math.max(max, freq[a[i]]);
			}
			System.out.println(a.length - max);
		}
	}

	public static void chefAndChain(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			char[] s = scan.nextLine().toCharArray();
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < 2; i++) {
				int count = 0;
				for (int j = 0; j < s.length; j++) {
					if ((s[j] == '+') == ((j+i) % 2 == 0)) {
						count++;
					}
				}
				min = Math.min(min, count);
			}
			System.out.println(min);
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