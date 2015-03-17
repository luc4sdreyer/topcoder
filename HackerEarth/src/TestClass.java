import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

class TestClass {
	public static void main(String[] args) {
		//letsBegin(System.in);
		//gameOfStrings(System.in);
		//testGameOfStrings();
		//uniqueRanking(System.in);
		//troublingTriple(System.in);
		randomGenerator(System.in);
	}
	public static void randomGenerator(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int p = scan.nextInt();
			long[] a = scan.nextLongArray(n);
			
			for (int i = 0; i < a.length; i++) {
				
			}
		}
	}
	public static void troublingTriple(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		long[] a = scan.nextLongArray(n);
		Arrays.sort(a);
		long num = 0;
		for (int i = 0; i < n; i++) {
			if (a[i] > k) {
				break;
			}
			for (int j = i+1; j < a.length; j++) {
				long p = a[i]*a[j];
				if (p > k) {
					break;
				}
				for (int m = j+1; m < a.length; m++) {
					if (p*a[m] <= k) {
						num++;
					} else {
						break;
					}
				}
			}
		}
		System.out.println(num);
	}

	public static void uniqueRanking(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
//		HashMap<Integer, int[]> marks = new HashMap<>();
//		for (int i = 0; i < n; i++) {
//			marks.put(i+1, scan.nextIntArray(k));
//		}
		int[][] marks = new int[n+1][k];
		for (int i = 1; i <= n; i++) {
			marks[i] = scan.nextIntArray(k);
		}
		
		int q = scan.nextInt();
		for (int i = 0; i < q; i++) {
			int m = scan.nextInt();
			int[] subjects = scan.nextIntArray(m);
			TreeSet<Integer> stud = new TreeSet<>();
			for (int j = 0; j < n; j++) {
				stud.add(j+1);
			}
			for (int j = 0; j < m; j++) {
				int min = Integer.MAX_VALUE;
				for (int s: stud) {
					min = Math.min(min, marks[s][subjects[j]-1]);
				}
				ArrayList<Integer> rem = new ArrayList<>();
				for (int s: stud) {
					if (marks[s][subjects[j]-1] > min) {
						rem.add(s);
					}
				}
				for (int l = 0; l < rem.size(); l++) {
					stud.remove(rem.get(l));
				}
				if (stud.size() == 1) {
					break;
				}
			}
			int min = Integer.MAX_VALUE;
			for (int s: stud) {
				min = Math.min(min, s);
			}
			ArrayList<Integer> rem = new ArrayList<>();
			for (int s: stud) {
				if (s > min) {
					rem.add(s);
				}
			}
			for (int l = 0; l < rem.size(); l++) {
				stud.remove(rem.get(l));
			}
			System.out.println(stud.first());
		}
	}
	
	public static void testGameOfStrings() {
		int numTests = 100000;
		int maxLength = 5;
		int range = 4;
		HashSet<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));		
		Random rand = new Random(0);
		for (int t = 0; t < numTests; t++) {
			char[] a = new char[rand.nextInt(maxLength) + 1];
			for (int i = 0; i < a.length; i++) {
				a[i] = (char) (rand.nextInt(range) + 'a');
			}
			char[] b = new char[rand.nextInt(maxLength) + 1];
			for (int i = 0; i < b.length; i++) {
				b[i] = (char) (rand.nextInt(range) + 'a');
			}
			
			int exp = 0;

			int N = 1 << a.length;
			for (int n = 0; n < N; n++) {
				String s1 = "";
				for (int i = 0; i < a.length; i++) {
					if (((1 << i) & n) != 0) {
						s1 += a[i];
					}
				}
				
				int M = 1 << b.length;
				for (int m = 0; m < M; m++) {
					String s2 = "";
					for (int i = 0; i < b.length; i++) {
						if (((1 << i) & m) != 0) {
							s2 += b[i];
						}
					}
					
					if (s2.equals(s1)) {
						boolean valid = true;
						for (int i = 0; i < s1.length(); i++) {
							if (vowels.contains(s1.charAt(i))) {
								valid = false;
								break;
							}
						}
						if (valid) {
							exp = Math.max(exp,  s1.length());
						}
					}
				}
			}
			
			int act = gameOfStrings(a, b);
			if (act != exp) {
				System.nanoTime();
				//gameOfStrings(a, b);
				System.out.println("fail");
			}
		}
	}

	public static void gameOfStrings(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		char[] a = scan.next().toCharArray();
		char[] b = scan.next().toCharArray();
		System.out.println(gameOfStrings(a, b));
	}
	
	public static int gameOfStrings(char[] a, char[] b) {

		int[][] dp = new int[a.length+1][b.length+1];
		int max = 0;
		int[] best = {0, 0};
		HashSet<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));		
		
		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[0].length; j++) {
				if (a[i-1] == b[j-1] && !vowels.contains(a[i-1])) {
					dp[i][j] = dp[i-1][j-1] + 1;
					if (dp[i][j] > max) {
						max = dp[i][j];
						best[0] = i;
						best[1] = j;
					}
				} else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
			}
		}
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				max = Math.max(max, dp[i][j]);
			}
		}
		return max;
	}

	public static void letsBegin(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] minWays = new int[n+1];
			int[] primes = {2,3,5,7};
			Arrays.fill(minWays, 1000000000);
			for (int i = 0; i < primes.length; i++) {
				if (primes[i] < minWays.length) {
					minWays[primes[i]] = 1;
				}
			}
			for (int i = 0; i < minWays.length; i++) {
				for (int j = 0; j < primes.length; j++) {
					if (i - primes[j] >= 0) {
						minWays[i] = Math.min(minWays[i], minWays[i - primes[j]] + 1);
					}
				}
			}
			
			System.out.println(minWays[n] == 1000000000 ? -1 : minWays[n]);
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
