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

public class R296 {
	public static void main(String[] args) {
		//playingWithPaper(System.in);
		//errorCorrectSystem(System.in);
		//testErrorCorrectSystem();
		//glassCarving(System.in);
		cliqueProblem(System.in);
	}

	public static void cliqueProblem(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		long[] x = new long[n];
		long[] w = new long[n];
		long[][] xSubW = new long[n][2];
		long[][] xAddW = new long[n][2];
		for (int i = 0; i < n; i++) {
			x[i] = scan.nextInt();
			w[i] = scan.nextInt();
			long[] sub = {x[i] - w[i], i};
			xSubW[i] = sub;
			long[] add = {x[i] + w[i], i};
			xAddW[i] = add;
		}
		
		Arrays.sort(xSubW, new Comparator<long[]>() {
			@Override
			public int compare(long[] o1, long[] o2) {
				return Long.compare(o1[0], o2[0]);
			}
		});
		
		Arrays.sort(xAddW, new Comparator<long[]>() {
			@Override
			public int compare(long[] o1, long[] o2) {
				return Long.compare(o1[0], o2[0]);
			}
		});
		
		int maxSize = 0;
		for (int i = 0; i < n; i++) {
			long minSub = x[i] - w[i];
			int low = 0;
			int high = n-1;
			int mid = 0;
			int bestSub = -1;
			while (low <= high) {
				mid = (low + high)/2;
				if (xSubW[mid][0] >= minSub) {
					bestSub = mid;
					high = mid-1;
				} else {
					low = mid+1;
				}
			}
			
			int subLen = 0;
			if (bestSub >= 0) {
				subLen = n-1 - bestSub;
				if (subLen < 0) {
					subLen = 0;
				}
			}
			
			long maxAdd = x[i] + w[i];
			low = 0;
			high = n-1;
			mid = 0;
			int bestAdd = -1;
			while (low <= high) {
				mid = (low + high)/2;
				if (xAddW[mid][0] <= maxAdd) {
					bestAdd = mid;
					low = mid+1;
				} else {
					high = mid-1;
				}
			}
			int addLen = bestAdd;
			maxSize = Math.max(maxSize, addLen + subLen);
			System.out.println(subLen + " " + addLen);
			
		}
		System.out.println(maxSize);
	}

	public static void glassCarving(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int w = scan.nextInt();
		int h = scan.nextInt();
		int n = scan.nextInt();
		TreeSet<Integer> x = new TreeSet<>();
		x.add(0);
		x.add(w);
		TreeSet<Integer> y = new TreeSet<>();
		y.add(0);
		y.add(h);
		int[][] input = new int[n][2];
		for (int i = 0; i < n; i++) {
			char type = scan.next().toCharArray()[0];
			int next = scan.nextInt();
			input[i][1] = next;
			if (type == 'H') {
				input[i][0] = 1;
				y.add(next);
			} else {
				input[i][0] = 0;
				x.add(next);
			}
		}
		int maxX = 0;
		int maxY = 0;
		int prev = 0;
		for (Integer i: x) {			
			if (i > 0) {
				maxX = Math.max(maxX, i - prev);
			}
			prev = i;
		}
		prev = 0;
		for (Integer i: y) {		
			if (i > 0) {
				maxY = Math.max(maxY, i - prev);
			}
			prev = i;
		}
		long max = (long)maxX * maxY;
		ArrayList<Long> ans = new ArrayList<>();
		ans.add(max);
		
		for (int i = n-1; i > 0; i--) {
			int next = input[i][1];
			if (input[i][0] == 0) {
				maxX = Math.max(maxX, x.higher(next)- x.lower(next));
				x.remove(next);
			} else {
				maxY = Math.max(maxY, y.higher(next) - y.lower(next));
				y.remove(next);
			}
			//System.out.println(maxX + " " + maxY);
			ans.add((long)maxX * maxY);
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = ans.size()-1; i >= 0; i--) {
			sb.append(ans.get(i));
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	public static void testErrorCorrectSystem() {
		Random rand = new Random(0);
		int numT = 10000000;
		for (int test = 0; test < numT; test++) {
			int len = rand.nextInt(10) + 1;
			char[] s = new char[len];
			for (int i = 0; i < s.length; i++) {
				s[i] = (char) (rand.nextInt(26) + 'a');
			}
			char[] t = new char[len];
			for (int i = 0; i < s.length; i++) {
				t[i] = (char) (rand.nextInt(26) + 'a');
			}
			String exp = errorCorrectSystemSlow(s.clone(), t.clone());
			String act = errorCorrectSystem(s, t);
			if (!exp.equals(act)) {
				System.out.println("fail");
				errorCorrectSystem(s, t);
			}
		}
	}

	public static String errorCorrectSystemSlow(char[] s, char[] t) {
		int min = hamming(s, t);
		int[] best = {-1, -1};
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s.length; j++) {
				char temp = s[i];
				s[i] = s[j];
				s[j] = temp;
				int ham = hamming(s, t);
				if (ham < min) {
					min = ham;
					best[0] = i + 1; 
					best[1] = j + 1;
				}
				temp = s[i];
				s[i] = s[j];
				s[j] = temp;
			}
		}
		String out = min + "\n" + best[0] + " " + best[1]; 
		return out;
	}

	public static int hamming(char[] s, char[] t) {
		int sc = 0;
		for (int i = 0; i < t.length; i++) {
			if (s[i] != t[i]) {
				sc++;
			}
		}
		return sc;
	}

	public static void errorCorrectSystem(InputStream in) {
		MyScanner scan = new MyScanner(in);
		scan.nextInt();
		char[] s = scan.next().toCharArray();
		char[] t = scan.next().toCharArray();
		System.out.println(errorCorrectSystem(s, t));
	}	
	
	private static String errorCorrectSystem(char[] s, char[] t) {
		int[][][] dp = new int[2][s.length+1][26];
		int[][] comb = new int[26][26];
		int idx = 0;
		int hamming = 0;
		for (int i = 0; i <= s.length; i++) {
			if (i > 0) {
				for (int j = 0; j < 26; j++) {
					dp[idx][i][j] = dp[idx][i-1][j];
				}
			}
			if (i < s.length) {
				if (s[i] != t[i]) {
					comb[s[i] - 'a'][t[i] - 'a']++;
					hamming++;
					dp[idx][i][s[i] - 'a']++;
				}
			}
		}
		idx++;
		for (int i = 0; i <= t.length; i++) {
			if (i > 0) {
				for (int j = 0; j < 26; j++) {
					dp[idx][i][j] = dp[idx][i-1][j];
				}
			}
			if (i < s.length) {
				if (s[i] != t[i]) {
					dp[idx][i][t[i] - 'a']++;
				}
			}
		}
		int max = 0;
		int[] best = {0, 0}; 
		for (int i = 0; i < s.length; i++) {
			if (s[i] != t[i]) {
				if (comb[t[i] - 'a'][s[i] - 'a'] > 0) {
					max = 2;
					for (int j = 0; j < s.length; j++) {
						if (s[j] == t[i] && t[j] == s[i]) {
							best[0] = i+1;
							best[1] = j+1;
							i = s.length;
							break;
						}
					}
				}
			}
		}
		if (max == 0) {
			for (int i = 0; i < s.length; i++) {
				if (s[i] != t[i]) {
					int score = 0;
					//if (dp[1][s.length][s[i] - 'a'] - (i > 0 ? dp[1][i-1][s[i] - 'a'] : 0) > 0) {
					if (dp[1][s.length][s[i] - 'a'] - dp[1][i][s[i] - 'a'] > 0) {
						score++;
					//} else if (dp[0][s.length][t[i] - 'a'] - (i > 0 ? dp[0][i-1][t[i] - 'a'] : 0) > 0) {
					} else if (dp[0][s.length][t[i] - 'a'] - dp[0][i][t[i] - 'a'] > 0) {
						score++;
					}
					if (score > max) {
						max = score;
						for (int j = i+1; j < s.length; j++) {
							if ((s[j] != t[j]) && (s[j] == t[i] || t[j] == s[i])) {
								best[0] = i+1;
								best[1] = j+1;
								i = s.length;
								break;
							}
						}
					}
				}
			}
		}
		String out = (hamming - max) + "\n";
		//System.out.println(hamming - max);
		if (max > 0) {
			out += best[0] + " " + best[1];
		} else {
			out += "-1 -1";
		}
		return out;
	}

	public static void playingWithPaper(InputStream in) {
		MyScanner scan = new MyScanner(in);
		long t1 = scan.nextLong();
		long t2 = scan.nextLong();
		long num = 0;
		long a = Math.max(t1, t2);
		long b = Math.min(t1, t2);
		while (a != b && a > 0 && b > 0) {
			num += a / b;
			t1 = a % b;
			t2 = b;
			a = Math.max(t1, t2);
			b = Math.min(t1, t2);
			//System.out.println(a + " " + b);
		}
		if (a == b && a > 0 && b > 0) {
			num++;
		}
		System.out.println(num);
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
