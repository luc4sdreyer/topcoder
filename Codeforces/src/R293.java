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

public class R293 {
	public static void main(String[] args) {
		//vitalyAndStrings(System.in);
		//tanyaAndInvitation(System.in);
		//anyaAndSmartphone(System.in);
		ilyaAndEscalator2(System.in);
	}
	
	public static void ilyaAndEscalator2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		double p = scan.nextDouble();
		int t = scan.nextInt();
		
		double[][] dp = new double[n+1][t+1];
		dp[0][0] = 1;
		for (int x = 0; x < dp[0].length-1; x++) {
			for (int y = 0; y < dp.length; y++) {
				dp[y][x+1] += (1 - p) * dp[y][x];
				if (y < dp.length -1) {
					dp[y+1][x+1] += (p) * dp[y][x];
				}
			}
		}
		
		double prob = n;
		for (int y = 0; y < dp.length; y++) {
			prob -= dp[y][t] * (n-y);
		}
		
		System.out.println(prob);
	}
	
	public static void ilyaAndEscalator(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		double p = scan.nextDouble();
		int t = scan.nextInt();
		
		double[][] pas = new double[t+1][n+1];
		pas[0][0] = 1;
		for (int i = 1; i <= t; i++) {
			pas[i][0] = 1;
			for (int j = 1; j < n; j++) {
				pas[i][j] = pas[i-1][j] + pas[i-1][j-1];
			}
			pas[i][n] = pas[i-1][n-1];
		}
		
		double prob = 0;
		for (int j = 0; j <= n; j++) {
			prob += Math.pow(1-p, t-j) * Math.pow(p, j) * pas[t][j] * j;
		}
		int d = 0;
		while (d < t && pas[d][n] == 0) {
			d++;
		}
		for (int i = d; i < t; i++) {
			prob += Math.pow(1-p, i-d) * Math.pow(p, d) * pas[i][n] * n;
		}
		System.out.println(prob);
	}

	public static void anyaAndSmartphone(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int k = scan.nextInt();
		
		int[] order = scan.nextIntArray(n);
		int[] index = new int[n+2];
		
		for (int i = 0; i < order.length; i++) {
			index[order[i]] = i;
		}
		int[] moves = scan.nextIntArray(m);
		long gest = 0;
		for (int i = 0; i < moves.length; i++) {
			//System.out.println(index[moves[i]] / k + 1);
			gest += index[moves[i]] / k + 1;
			if (index[moves[i]] > 0) {
				int pos = index[moves[i]];
				index[moves[i]]--;
				index[order[pos-1]]++;
				
				int temp = order[pos];
				order[pos] = order[pos-1];
				order[pos-1] = temp;
			}
		}
		System.out.println(gest);
	}

	public static void tanyaAndInvitation(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] a = scan.nextLine().toCharArray();
		char[] b = scan.nextLine().toCharArray();
		
		int[] freq = new int[60];
		for (int i = 0; i < b.length; i++) {
			freq[b[i]-'A']++;
		}
		
		int hit = 0;
		int half = 0;
		boolean[] matched = new boolean[a.length];
		for (int i = 0; i < a.length; i++) {
			if (freq[a[i]-'A'] > 0) {
				freq[a[i]-'A']--;
				hit++;
				matched[i] = true;
			}
		}
		for (int i = 0; i < a.length; i++) {
			if (!matched[i]) {
				char match = a[i];
				if (a[i] <= 'Z') {
					match = (char) (a[i] + ('a' - 'A'));
				} else {
					match = (char) (a[i] - ('a' - 'A'));
				}
				if (freq[match - 'A'] > 0) {
					freq[match - 'A']--;
					half++;
				}
			}
		}
		System.out.println(hit + " " + half);
	}
	public static void vitalyAndStrings(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] a = scan.nextLine().toCharArray();
		char[] b = scan.nextLine().toCharArray();
		char[] c = a.clone();
		next_number(c, 'z'+1);
		if (Arrays.equals(c, b)) {
			System.out.println("No such string");
		} else {
			System.out.println(new String(c));
		}
	}
	
	public static boolean next_number(char list[], int base) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 'a';
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
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
