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
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Cook59 {
	public static void main(String[] args) {
		//UTMOPR(System.in);
		//testUTMOPR();
		
		//testANKPAREN();
		//testANKPAREN2();
		ANKPAREN(System.in);
	}
	
	public static void testANKPAREN2() {
		Random rand = new Random(0);
		for (int t = 0; t < 1000; t++) {
			int j = 100000;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < j; i++) {
				sb.append(rand.nextBoolean() ? '(' : ')');
			}
			String str = sb.toString();
			
			String act = ANKPAREN(str.toCharArray(), rand.nextInt(1000000000), str).toString();
			if (act.equals(" ")) {
				System.out.println();
			}
		}
		for (int t = 0; t < 1000; t++) {
			int j = rand.nextInt(100000)+1;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < j; i++) {
				sb.append(rand.nextBoolean() ? '(' : ')');
			}
			String str = sb.toString();
			
			for (int i = 0; i < 100; i++) {
				String act = ANKPAREN(str.toCharArray(), rand.nextInt(j), str).toString();
				if (act.equals(" ")) {
					System.out.println();
				}
			}
		}
	}
	
	public static void testANKPAREN() {
//		String str = "(())()";
//		System.out.println(ANKPAREN(str.toCharArray(), 2, str));
		
		for (int j = 1; j < 12; j++) {
			int N = 1 << j;
			for (int n = 0; n < N; n++) {
				String str = "";
				for (int i = 0; i < j; i++) {
					if (((1 << i) & n) != 0) {
						str += "(";
					} else {
						str += ")";
					}
				}
				for (int k = 1; k <= j*10; k++) {
					String exp = slowANKPAREN(str.toCharArray(), k, str);
					String act = ANKPAREN(str.toCharArray(), k, str).toString();
					if (!exp.equals(act)) {
						System.out.println("fail");
						slowANKPAREN(str.toCharArray(), k, str);
						ANKPAREN(str.toCharArray(), k, str);
					}
				}
			}
		}
	}
	
	public static StringBuilder ANKPAREN(char[] s, int k, String str) {
		int h = 0;
		boolean valid = true;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '(') {
				h++;
			} else {
				h--;
			}
			if (h < 0) {
				valid = false;
				break;
			}
		}
		if (h != 0) {
			valid = false;
		}
		
		if (!valid) {
			if (k != 1) {
				sb.append("-1");
			} else {
				for (int i = 0; i < s.length; i++) {
					sb.append(str.charAt(i));
				}
			}
		} else {
			int max = 1;
			for (int i = 1; i < s.length; i++) {
				if (s[i] != s[i-1]) {
					max++;
				}
			}
			 
			k--;
			if (k < max) {
				int index = 0;
				if (k < max/2) {
					index = k*2 + 1;
				} else {
					index = (max - 1 - k)*2;
				}
				
				for (int i = 1; i <= index && i < s.length; i++) {
					if (s[i] == s[i-1]) {
						index++;
					}
				}

				for (int x = 0; x < s.length; x++) {
					if (x != index) {
						sb.append(s[x]);
					}
				}
			} else {
				sb.append("-1");
			}
		}
		
		return sb;
	}
	
	public static String slowANKPAREN(char[] s, int k, String str) {
		int h = 0;
		boolean valid = true;
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '(') {
				h++;
			} else {
				h--;
			}
			if (h < 0) {
				valid = false;
				break;
			}
		}
		if (h != 0) {
			valid = false;
		}
		
		if (!valid) {
			if (k != 1) {
				return "-1";
			} else {
				return str;
			}
		} else {
			LinkedHashSet<String> set = new LinkedHashSet<>();
			for (int i = 0; i < s.length; i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < s.length; j++) {
					if (j != i) {
						sb.append(s[j]);
					}
				}
				set.add(sb.toString());
			}
			
			ArrayList<Paren> list = new ArrayList<>();
			int age = 0;
			for (String st: set) {
				list.add(new Paren(st, age++, 0));
			}
			
			Collections.sort(list, new Comparator<Paren>() {
				@Override
				public int compare(Paren o1, Paren o2) {
					return o1.s.compareTo(o2.s);
				}
			});
			
			for (int i = 0; i < list.size(); i++) {
				list.get(i).lex = i;
			}
			
			int calcSize = 1;
			for (int i = 1; i < s.length; i++) {
				if (s[i] != s[i-1]) {
					calcSize++;
				}
			}
			
			if (calcSize != list.size()) {
				System.out.println("calcSize != list.size()");
			}
			
			String ret = "-1";
			k--;
			if (k < list.size()) {
				ret = list.get(k).s;
			}
			
			Collections.sort(list, new Comparator<Paren>() {
				@Override
				public int compare(Paren o1, Paren o2) {
					return Integer.compare(o1.age, o2.age);
				}
			});
			
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i).lex + " \t" + list.get(i).s);
//			}
//			System.out.println();
			
			return ret;	
		}
	}
	
	public static class Paren {
		public String s;
		public int age;
		public int lex;
		public Paren(String s, int age, int lex) {
			super();
			this.s = s;
			this.age = age;
			this.lex = lex;
		}
		
	}
	
	public static void ANKPAREN(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			String str = scan.next(); 
			char[] s = str.toCharArray();
			int k = scan.nextInt();
			
			StringBuilder x = ANKPAREN(s, k, str);
			
			System.out.println(x);
		}
		scan.close();
	}

	public static void testUTMOPR() {
		Random rand = new Random(0);
		for (int t = 0; t < 10000; t++) {
		for (int n = 1; n < 100; n++) {
			for (int k = 1; k < 10; k++) {
				int[] a = new int[n];
				for (int i = 0; i < a.length; i++) {
					a[i] = rand.nextInt(100);
				}
				
				//a[0] = 5;
				//a[1] = 7;
				
				int exp = UTMOPRslow(n, k, a);
				int act = UTMOPR(n, k, a);
				
				//System.out.println(exp);
				if (act != exp) {
					System.out.println("fail");
					UTMOPRslow(n, k, a);
					UTMOPR(n, k, a);
				}
			}
		}
			
		}
	}
	
	public static int UTMOPRslow(int n, long k, int[] a) {
		long sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		
		for (int i = 0; i < k-1; i++) {			
			sum = sum*2 + 1;
		}
		
		return (int) ((sum + 1) % 2);
	}
	
	public static int UTMOPR(int n, long k, int[] a) {
		long sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		
		if (k == 1) {
			return (int) ((sum + 1) % 2);
		} else {
			return 0;
		}
	}
	
	public static void UTMOPR(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			
			int x = UTMOPR(n, k, a);
			
			System.out.println(x % 2 == 0 ? "even": "odd");
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
