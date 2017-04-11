package gcj;

import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Iterator;

public class Jam2016 {
	//10^4 <  Short.MAX_VALUE (2^15)   < 10^5
	//10^9 <  Integer.MAX_VALUE (2^31) < 10^10
	//10^18 < Long.MAX_VALUE (2^63)    < 10^20

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	// path: C:\Users\Lucas\workspace_tc\CodeJam\src\gcj\Jam2016.java
	static String filename = "C:\\Users\\Lucas\\Downloads\\C-large";

	static String outputfile = filename + ".out";
	static MyScanner scan;
	static PrintStream out;

	public static void main(String[] args) throws NumberFormatException, IOException {
		scan = new MyScanner(new FileInputStream(filename+".in"));
		out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputfile)));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);
		
//		CountingSheep();
//		RevengeOfThePancakes();
		CoinJam();

		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
		scan.close();
		out.close();
	}
	
	public static void CoinJam(int n, int num) {
		long x = (long) (Math.pow(2, n-1) + 1);
		long lim = (long) (Math.pow(2, n));

		HashSet<Long> notPrimes = new HashSet<>();
		HashMap<Long, Long> divisors = new HashMap<>();
		HashSet<Long> primes = new HashSet<>();
		
//		long limit = 1200000;
		long limit = 12000;
		for (long i = 2; i <= limit; i++) {
			if (!notPrimes.contains(i)) {
				primes.add(i);
				for (long j = 2*i; j <= limit; j+=i) {
					notPrimes.add(j);
					if (!divisors.containsKey(j)) {
						divisors.put(j, i);
					}
				}
			}
		}

		HashSet<BigInteger> bnotPrimes = new HashSet<>();
		HashMap<BigInteger, BigInteger> bdivisors = new HashMap<>();
		HashSet<BigInteger> bprimes = new HashSet<>();
		
		for (Long i: notPrimes) {
			bnotPrimes.add(BigInteger.valueOf(i));
		}
		for (Long i: divisors.keySet()) {
			bdivisors.put(BigInteger.valueOf(i), BigInteger.valueOf(divisors.get(i)));
		}
		for (Long i: primes) {
			bprimes.add(BigInteger.valueOf(i));
		}
		
		int found = 0;
		while (found < num && x < lim) {
			String bin = Long.toBinaryString(x);
			ArrayList<BigInteger> divis = new ArrayList<>();
			for (int b = 2; b <= 10; b++) {
				BigInteger altbase = new BigInteger(bin, b);
				if (!bprimes.contains(altbase)) {
					if (bdivisors.containsKey(altbase)) {
						divis.add(bdivisors.get(altbase));
					}
					else {
						boolean valid = true;
						BigInteger div = BigInteger.ZERO;
						for (BigInteger p: bprimes) {
							if (altbase.mod(p) == BigInteger.ZERO) {
								valid = false;
								div = p;
								break;
							}
						}
//						if (valid) {
//							BigInteger 
//							for (long i = limit; i <= altbase/i; i++) {
//								if (altbase % i == 0) {
//									valid = false;
//									div = i;
//									break;
//								}
//							}
//						}
						if (!valid) {
							divis.add(div);
						}
					}
				}
			}
			if (divis.size() == 9) {
				String ret = bin;
				for (int i = 0; i < divis.size(); i++) {
					ret += " " + divis.get(i);
				}
				System.out.println(ret);
				out.println(ret);
				found++;
			} 
			x += 2;
		}
		if (x >= lim) {
			System.out.println("failed");
		}
	}

	public static void CoinJam() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			int N = scan.nextInt();
			int J = scan.nextInt();
			out.println("Case #1:");
			CoinJam(N, J);
		}
	}

	public static String RevengeOfThePancakes2(char[] s) {
		boolean[] x = new boolean[s.length];
		for (int i = 0; i < x.length; i++) {
			x[i] = s[i] == '+' ? true : false;
		}
		int num = 0;
		boolean prev = x[x.length - 1];
		if (!prev) {
			num++;
		}
		for (int i = x.length - 2; i >= 0; i--) {
			if (x[i] != x[i+1]) {
				num++;
			}
		}
		return num + "";
	}
	
	public static String RevengeOfThePancakes(char[] s) {
		boolean[] x = new boolean[s.length];
		for (int i = 0; i < x.length; i++) {
			x[i] = s[i] == '+' ? true : false;
		}
		int num = 0;
		for (int i = x.length - 1; i >= 0; i--) {
			if (!x[i]) {
				num++;
				for (int j = 0; j <= i / 2; j++) {
					boolean temp = x[j]; 
					x[j] = x[i - j];
					x[i - j] = temp;
				}
				for (int j = 0; j <= i; j++) {
					x[j] = !x[j];
				}
			}
		}
		return num + "";
	}

	public static void RevengeOfThePancakes() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			char[] s = scan.next().toCharArray();
			out.println("Case #" + (test+1) + ": " + RevengeOfThePancakes2(s));
		}
	}

	public static String CountingSheep(long x) {
		long interval = x;
		int[] f = new int[10];
		int seen = 0;
		if (x == 0) {
			return "INSOMNIA";
		} else {
			while (seen < 10) {
				char[] s = Long.toString(x).toCharArray();
				for (int i = 0; i < s.length; i++) {
					f[s[i] - '0']++;
				}
				seen = 0;
				for (int i = 0; i < f.length; i++) {
					if (f[i] > 0) {
						seen++;
					}
				}
				if (seen < 10) {
					x += interval;
				}
			}
			return x + "";
		}
	}

	public static void CountingSheep() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			long x = scan.nextInt();
			out.println("Case #" + (test+1) + ": " + CountingSheep(x));
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