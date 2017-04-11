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

public class Infinitum15 {
	public static void main(String[] args) {
		kElementSequences(System.in);
//		testkElementSequences();
//		perfkElementSequences();
	}
	
	public static void testkElementSequences() {
		for (int len = 1; len <= 10; len++) {
//			System.out.println("len: " + len);
			for (int sum = len; sum <= len*10; sum++) {
//				System.out.println(sum + "\t " + kElementSequencesSlow(sum, len) + "\t" + kElementSequences(sum, len));
				long r1 = kElementSequences(sum, len);
				long r2 = kElementSequences2(sum, len);
				if (r1 != r2) {
					System.out.println("fail " + sum + " " + len + " " + r1 + " " + r2);
				} else {
//					System.out.println("pass");
				}
			}
//			System.out.println();
		}
	} 
	
	public static void perfkElementSequences() {
		long t1 = System.currentTimeMillis();
		for (int len = 10; len <= 10; len++) {
//			System.out.println("len: " + len);
			for (int sum = 2000000-100; sum <= 2000000; sum++) {
//				System.out.println(sum + "\t " + kElementSequencesSlow(sum, len) + "\t" + kElementSequences(sum, len));
				long r1 = -10; //kElementSequences(sum, len);
				long r2 = kElementSequences2(sum, len);
				if (r1 > r2) {
					System.out.println("fail " + sum + " " + len + " " + r1 + " " + r2);
				} else {
//					System.out.println("pass");
				}
			}
//			System.out.println();
		}
		System.out.println(System.currentTimeMillis() - t1);
	} 

	public static long kElementSequencesSlow(int sum, int len) {
		int[] a = new int[len];
		int valid = 0;
		do {
			int s = 0;
			for (int i = 0; i < a.length; i++) {
				s += a[i] + 1; 
			}
			if (s == sum) {
				valid++;
			}
		} while (next_number(a, (sum - len + 1)));
		return valid;
	} 
	
	public static final long mod = 1000000007;
	public static final int maxSum = 2000000;
	public static final long[] pre_ncr = new long[maxSum+1];
	static {
		pre_ncr[0] = 1; 
		int etf = eulerTotientFunction((int) mod);
		for (int i = 1; i < pre_ncr.length; i++) {
			pre_ncr[i] = (fastModularExponent(i, etf - 1, (int) mod) % mod);
		}
	}

	public static long kElementSequences(int sum, int len) {
		if (len == 1) {
			return 1;
		} else if (len == 2) {
			return sum-1;
		} else {
			int base = (sum - len + 1);
			long total = 0;
			for (int i = 0; i < base; i++) { 
				total += (base-i) * nCr(i+len-3, i);
			}
			return total % mod;
		}
	}

	public static long kElementSequences2(int sum, int len) {
		if (len == 1) {
			return 1;
		} else if (len == 2) {
			return sum-1;
		} else {
			final int base = (sum - len + 1);
			final long len3 = len-3;
			long total = base;
			long ncr = 1;
			for (int i = 1; i <= base; i++) {
				ncr = ((pre_ncr[i] * (i+len3) % mod) * ncr) % mod;
				total = (ncr * (base-i) % mod + total) % mod;
			}
			return total;
		}
	}

	public static int eulerTotientFunction(int n) {
		int result = n;
		for (int i = 2; i*i <= n; i++) {
			if (n % i == 0) {
				result -= result / i;
			}
			while (n % i == 0) {
				n /= i;
			}
		}
		   
		if (n > 1) {
			result -= result / n;
		}
		return result;
	}
	
	public static int fastModularExponent(int a, int exp, int mod) {
		long[] results = new long[65];
		long m = mod;
		int power = 1;
		long res = 1;
		while (exp > 0) {
			if (power == 1) {
				results[power] = a % m;
			} else {
				results[power] = (results[power-1] * results[power-1]) % m;
			}
			if (exp % 2 == 1) {
				res = (res * results[power]) % m;
			}
			exp /= 2;
			power++;
		}
		return (int) (res % m);
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

	public static void testNumUniqSequences() {
		Random rand = new Random(0);
		for (int len = 5; len <= 5; len++) {
			for (int symbols = 1; symbols <= len; symbols++) {
				for (int test = 0; test < 100; test++) {
					int[] a = new int[len];
					for (int i = 0; i < a.length; i++) {
						a[i] = rand.nextInt(symbols);
					}
					a = new int[]{3, 3, 3, 3, 2, 3};
					long r1 = numUniqSequences(a);
					long r2 = numUniqSequencesSlow(a);
					if (r1 != r2) {
						System.out.println(r1);
						System.out.println(r2);
						System.out.println("fail " + Arrays.toString(a) );
						numUniqSequences(a);
						numUniqSequencesSlow(a);
					}
				}
			}
		}
	}

	public static long numUniqSequencesSlow(int[] input) {
		int sum = 0;
		for (int i = 0; i < input.length; i++) {
			sum += input[i];
		}
		
		int[] a = new int[sum];
		int k = 0;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i]; j++) {
				a[k++] = i;
			}
		}
		int count = 0;
		do {
			count++;
		} while (next_permutation(a));
		return count;
	}

	public static long numUniqSequences2(int[] input) {
		int sum = 0;
		for (int i = 0; i < input.length; i++) {
			sum += input[i];
		}
		
		long uniq = factorial(sum);
		for (int i = 0; i < input.length; i++) {
			uniq /= factorial(input[i]);
		}
		
		return uniq;
	}

	public static long numUniqSequences(int[] input) {
		int sum = 0;
		for (int i = 0; i < input.length; i++) {
			sum += input[i];
		}
		
		long uniq = factorial(sum);
		for (int i = 0; i < input.length; i++) {
			uniq /= factorial(input[i]);
		}
		
		return uniq;
	}
	
	public static long factorial(int n) {
		long f = 1;
		for (long i = 2; i <= n; i++) {
			f *= i;
		}
		return f;
	}
	
	/**
	 * Number of permutations without repetition:  n! / (n-r)!
	 */
	public static long nPr(int n, int r) {
		if (n == 0) {
			return 1;
		}
		long ret = 1;
		for (long i = n - r + 1; i <= n; i++) {
			ret *= i;
		}
		return ret;
	}

	public static void kElementSequences(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			System.out.println(kElementSequences2(scan.nextInt(), scan.nextInt()));
		}
	}
	
	public static boolean next_permutation(int str[]) {
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Number of combinations without repetition:  n! / (r! * (n - r)!)
	 */
	public static long nCr(int n, int r) {
		if (n == 0) {
			return 1;
		}
		long ret = 1;
		//
		// symmetry
		//
		if (n - r < r) {
			r = n - r;
		}
		for (long i = n - r + 1; i <= n; i++) {
			ret *= i;
		}
		for (long i = 2; i <= r; i++) {
			ret /= i;
		}
		return ret;
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
