import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		//System.out.println(eulersCriterion(System.in));
		//System.out.println(differenceAndProduct(System.in));
		System.out.println(wetSharkAnd42(System.in));		
		//test3();
	}
	
	public static void test2() {
		HashSet<Pair<Integer, Integer>> set = new HashSet<>();
		set.add(new Pair<>(1,2));
		boolean t = set.contains(new Pair<>(1,2));
		System.out.println(differenceAndProductF3(25, -100));
		for (int i = 0; i < 10; i++) {
			System.out.println(differenceAndProductF(360000, i));
		}		
	}
	
	public static void test3() {
		wetSharkAnd42F2(1000000000000000000L);
		for (int i = 1; i < 1000; i++) {
			int res1 = wetSharkAnd42F(i);			
			int res2 = wetSharkAnd42F2(i);
			if (res1 != res2) {
				System.out.println("fail: " + i + ": " +res1 + " vs " + res2);
			}			
		}
	}
	
	public static String wetSharkAnd42(InputStream in) {
		Scanner scan = new Scanner(in);
		int t = scan.nextInt();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t; i++) {
			long s = scan.nextLong();
			sb.append(wetSharkAnd42F2(s));
			sb.append("\n");
		}
		scan.close();
		
		return sb.toString();
	}
	
	public static int mod = 1000000007;
	public static int wetSharkAnd42F2(long s) {
		long ret = 2L*s;
		long preSections = 0;
		long sections = ret / 42;
		while (preSections < sections) {
			ret += (sections - preSections)*2;
			preSections = sections;
			sections = ret / 42;
		}
		return (int) (ret % mod);
	}
	
	public static int wetSharkAnd42F(long s) {
		int i = 1;
		while (s > 0) {
			if (i % 2 == 0 && i % 42 != 0) {
				s--;
			}
			if (s != 0) {
				i++;
			}
		}
		return i;
	}

	public static String differenceAndProduct(InputStream in) {
		Scanner scan = new Scanner(in);
		int t = scan.nextInt();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t; i++) {
			long d = scan.nextLong();
			long p = scan.nextLong();
			sb.append(differenceAndProductF4(d, p));
			sb.append("\n");
		}
		scan.close();
		
		return sb.toString();
	}
	
	public static int differenceAndProductF(long d, long p) {
		long num = 0;
		long low = -Math.abs(p);
		long high = Math.abs(p);
		for (long a = low; a <= high; a++) {
			for (int j = 0; j < 2; j++) {
				long b = j == 0 ? a + d : a - d;
				if (a*b == p) {
					num++;
					System.out.println(p + ", " + d + ", " + a + ", " + b);
				}
				if (d == 0) {
					break;
				}
			}
		}
		return (int) num;
	}
	
	public static int[][][] count = new int[3][3][8];
	public static int differenceAndProductF4(long d, long p) {
		HashSet<Pair<Long, Long>> set = new HashSet<>();
		long p2 = Math.abs(p);
		if (p > 0 && Math.abs(p) > Math.abs(d/2)) {
			p2 -= Math.abs(d/2);
		}
		boolean found = false;
		for (long a = 0; a*a <= p2; a++) {
			for (int i = 0; i < 2; i++) {
				long c = i == 0 ? a : -a;
				for (int j = 0; j < 2; j++) {
					long b = j == 0 ? c + d : c - d;
					if (check(c, b, p, d, set)) {
						found = true;
						
						if (p != 0) {
							count[p == 0 ? 1 : (p > 0 ? 2 : 0)][d == 0 ? 1 : (d > 0 ? 2 : 0)][i*4+ j*2 + 0]++;
						}
					}
					if (check(b, c, p, d, set)) {
						found = true;
						if (p != 0) {
							count[p == 0 ? 1 : (p > 0 ? 2 : 0)][d == 0 ? 1 : (d > 0 ? 2 : 0)][i*4+ j*2 + 1]++;
						}
					}
				}
			}
			if (found) {
				break;
			}
		}
		return set.size();
	}
	
	public static int differenceAndProductF3(long d, long p) {
		HashSet<Pair<Long, Long>> set = new HashSet<>();
		long p2 = Math.abs(p);
		for (long a = 0; a*a <= p2; a++) {
			for (int i = 0; i < 2; i++) {
				long c = i == 0 ? a : -a;
				for (int j = 0; j < 2; j++) {
					long b = j == 0 ? c + d : c - d;
					check(c, b, p, d, set);
					check(b, c, p, d, set);
				}
			}
		}
		return set.size();
	}

	public static boolean check(long a, long b, long p, long d, HashSet<Pair<Long, Long>> set) {
		if (a * b == p && Math.abs(a - b) == d) {
			Pair<Long, Long> pair = new Pair<>(a, b);
			if (!set.contains(pair)) {
				set.add(pair);
				return true;
			}
			return false;
		}
		return false;
	}
	
	public static int differenceAndProductF2(long d, long p) {
		long num = 0;
		long low = Math.min((long)(Math.signum(p) * Math.sqrt(Math.abs(p))), 0);
		long high = Math.max((long)(Math.signum(p) * (Math.sqrt(Math.abs(p)+1))), 0);
		for (long a = low; a <= high; a++) {
			if (check(a, a + d, p, d) || check(a, a - d, p, d)) {
				long r = 0;
				if (a == 0) {
					r = d;
				} else {
					r = p/a;
				}
				int n = 0;
				if (check(a, r, p, d)) {
					n++;
					//if (a != 0 && check(-a, -r, p, d)) {
					if (check(-a, -r, p, d)) {
						if (!(d == 0 && p == 0)) {
							n++;
						}
					}
				}
				if (d != 0 && p != 0 && Math.abs(r) != Math.abs(a) && check(r, a, p, d)) {
					n++;
					//if (r != 0 && check(-r, -a, p, d)) {
					if (check(-r, -a, p, d)) {
						n++;
					}
				}
				return n;
			}
		}
		return (int) num;
	}

	public static boolean check(long a, long b, long p, long d) {
		if (a * b == p && (a - b == d || b - a == d)) {
			return true;
		}
		return false;
	}

	public static String eulersCriterion(InputStream in) {
		Scanner scan = new Scanner(in);
		int t = scan.nextInt();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t; i++) {
			long a = scan.nextLong();
			long m = scan.nextLong();
			sb.append(eulersCriterionF2(a, m));
			sb.append("\n");
		}
		scan.close();
		
		return sb.toString();
	}

	public static String eulersCriterionF(long a, long m) {
		if (a == 0) {
			return "YES";
		}
		if (m % 2 != 1) {
			return "NO";
		}
		long exp = (m-1)/2;
		long res = 1;
		for (int i = 0; i < exp; i++) {
			res = (res * a) % m;
		}
		return res % m == 1 ? "YES" : "NO";
	}

	public static String eulersCriterionF2(long a, long m) {
		if (a == 0) {
			return "YES";
		}
		if (m % 2 != 1) {
			return "NO";
		}
		long exp = (m-1)/2;
		long res = fastModularExponent(a, exp, m);
		return res % m == 1 ? "YES" : "NO";
	}

	public static long fastModularExponent(long a, long exp, long mod) {
		long[] results = new long[65];
		int power = 1;
		long res = 1;
		while (exp > 0) {
			if (power == 1) {
				results[power] = a % mod;
			} else {
				results[power] = (results[power-1] * results[power-1]) % mod;
			}
			if (exp % 2 == 1) {
				res = (res * results[power]) % mod;
			}
			exp /= 2;
			power++;
		}
		return res % mod;
	}
	
	public static class Pair<A extends Comparable<A>, B extends Comparable<B>>	implements Comparable<Pair<A, B>> {
		private A first;
		private B second;

		public Pair(A first, B second) {
			super();
			this.first = first;
			this.second = second;
		}

		public int hashCode() {
			int hashFirst = first != null ? first.hashCode() : 0;
			int hashSecond = second != null ? second.hashCode() : 0;

			return (hashFirst + hashSecond) * hashSecond + hashFirst;
		}

		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<A, B> otherPair = (Pair<A, B>) other;
				return 
					((this.first == otherPair.first ||
						(this.first != null && otherPair.first != null &&
						this.first.equals(otherPair.first))) &&
					(this.second == otherPair.second ||
						(this.second != null && otherPair.second != null &&
						this.second.equals(otherPair.second))));
			}

			return false;
		}

		public String toString()
		{ 
			return "(" + first + ", " + second + ")"; 
		}

		public A getFirst() {
			return first;
		}

		public void setFirst(A first) {
			this.first = first;
		}

		public B getSecond() {
			return second;
		}

		public void setSecond(B second) {
			this.second = second;
		}

		@Override
		public int compareTo(Pair<A, B> o) {
			int compareFirst = this.first.compareTo(o.first);
			if (compareFirst != 0) {
				return compareFirst;
			} else {
				return this.second.compareTo(o.second);
			}
		}
	}
}
