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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.StringTokenizer;

public class Snack2 {
	public static void main(String[] args) {
		//TTENIS(System.in);
		DIVNINE(System.in);
		//testDIVNINE();
	}
	
	public static void testDIVNINE() {
		for (int i = 0; i < 100000; i++) {
			char[] s = Integer.toString(i).toCharArray();
			int[] n = new int[s.length];
			for (int j = 0; j < s.length; j++) {
				n[j] = s[j] - '0';
			}
			int act = DIVNINE(n);
			int exp = 0;
			exp = DIVNINEslow(n);
			
			if (act != exp) {
				System.out.println("fail");
				DIVNINE(n);
				DIVNINEslow(n);
				System.nanoTime();
			}
		}
	}
	
	public static int DIVNINEslow(int[] n) {
		int[] ans = new int[n.length];
		if (n.length > 1) {
			ans[0] = 1;
		}
		
		int minDiff = 9;
		do {
			if (div9(ans) == 0) {
				int diff = 0;
				for (int j = 0; j < ans.length; j++) {
					diff += Math.abs(ans[j] -  n[j]);
				}
				minDiff = Math.min(minDiff, diff);
			} 
		} while (next_number(ans, 10));
		if (div9(ans) == 0) {
			int diff = 0;
			for (int j = 0; j < ans.length; j++) {
				diff += Math.abs(ans[j] -  n[j]);
			}
			minDiff = Math.min(minDiff, diff);
		} 
		return minDiff;
	}

	public static boolean next_number(int list[], int base) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}
	
	public static int DIVNINE(int[] n) {
		int distance = 0;
		int m = div9(n); 
		if (m != 0) {
			int sum = 0;
			boolean lessThan9 = true;
			for (int i = 0; i < n.length; i++) {
				sum += n[i];
				if (sum > 9) {
					lessThan9 = false;
					break;
				}
			}
			
			if (lessThan9 && n.length > 1) {
				distance = 9 - m;
			} else {
				if (m < 9 - m) {
					distance = m;
				} else { 
					distance = 9 - m;
				}
			}
		}
		return distance; 
	}

	public static void DIVNINE(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			char[] s = scan.nextLine().toCharArray();
			int[] n = new int[s.length];
			for (int i = 0; i < s.length; i++) {
				n[i] = s[i] - '0';
			}
			
			int distance = DIVNINE(n);
			
			System.out.println(distance);
		}
	}
	
	static int div9(int[] n) {
		int rem = 0;
		for (int i = 0; i < n.length; i++) {
			rem = (rem + n[i]) % 9;
		}
		return rem; 
	}
	
	public static void TTENIS(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			char[] s = scan.nextLine().toCharArray();
			
			int minus = 0;
			int plus = 0;
			
			boolean suddenDeath = false;
			boolean win = false;
			for (int i = 0; i <= s.length; i++) {
				if (minus == 10 && plus == 10) {
					suddenDeath = true;
				}
				if (suddenDeath) {
					if (Math.abs(minus - plus) >= 2) {
						if (plus > minus) {
							win = true;
						} else {
							win = false;
						}
						break;
					}
				} else {
					if (minus == 11 || plus == 11) {
						if (plus > minus) {
							win = true;
						} else {
							win = false;
						}
						break;
					}
				}
				if (i < s.length) {
					if (s[i] == '0') {
						minus++;
					} else {
						plus++;
					}
				}
			}
			System.out.println(win ? "WIN" : "LOSE");
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
