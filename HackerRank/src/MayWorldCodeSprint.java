import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class MayWorldCodeSprint {
	public static void main(String[] args) {
//		compareTheTriplets(System.in);
//		richieRich(System.in);
//		absolutePermutation(System.in);
//		testAbsolutePermutation();
//		beautifulQuadruples(System.in);
//		testBeautifulQuadruples();
//		divAndSpan(System.in);
//		testDivAndSpan();
		
		squareTenTree(System.in);
//		testSquareTenTree();
	}

	public static void testSquareTenTree() {
		squareTenTree("221", "577");
		System.out.println();
		squareTenTree("133", "155");
		System.out.println();
		squareTenTree("155", "155");
		System.out.println();
		squareTenTree("153", "155");
		System.out.println();
		squareTenTree("1", "1");
		System.out.println();
		squareTenTree("1", "2");
		System.out.println();
		squareTenTree("1", "3");
		System.out.println();
		squareTenTree("2", "12");
		System.out.println();
		squareTenTree("89", "212");
		System.out.println();
		squareTenTree("1", "123456789112233445566778899");
		System.out.println();
		squareTenTree("123456789", "123456789112233445566778899");
		System.out.println();
		squareTenTree("1", "10");
		System.out.println();
	}

	public static void squareTenTree(InputStream in) {
		MyScanner scan = new MyScanner(in);
		String strL = scan.next();
		String strR = scan.next();
		squareTenTree(strL, strR);
	}
	
	public static void squareTenTree(String sL, String sR) {
		BigInteger L = new BigInteger(sL);
		BigInteger R = new BigInteger(sR);
		
		if (sR.length() == 1) {
			System.out.println("1");
			System.out.println("0 " + (R.subtract(L.subtract(BigInteger.ONE)).toString()));
			return;
		} 
		
		StringBuilder sb = new StringBuilder();
		sb.append(sR.charAt(0));
		boolean eq = true;
		if (sR.length() != sL.length()) {
			eq = false;
		}
		if (eq && sR.charAt(0) != sL.charAt(0)) {
			eq = false;
		}
		for (int i = 1; i < sR.length(); i++) {
			if (eq) {
				sb.append(sR.charAt(i));
			} else {
				sb.append('0');
			}
			if (eq && sR.charAt(i) != sL.charAt(i)) {
				eq = false;
			}
		}
		
		BigInteger highBound = new BigInteger(sb.toString());
		
		R = R.subtract(highBound);
		L = highBound.add(BigInteger.ONE).subtract(L);
		
		ArrayList<Integer> outInt = new ArrayList<>();
		ArrayList<String> outStr = new ArrayList<>();
		process(L, false, outInt, outStr);
		process(R, true, outInt, outStr);
		
		for (int i = 1; i < outInt.size(); i++) {
			if (i > 0 && (outInt.get(i-1) == outInt.get(i))) {
				outStr.add(i-1, (new BigInteger(outStr.remove(i-1))).add(new BigInteger(outStr.remove(i-1))).toString());
				outInt.add(i-1, (outInt.remove(i-1) + outInt.remove(i-1)));
				i -= 2;
			}
		}

		System.out.println(outInt.size());
		for (int i = 0; i < outInt.size(); i++) {
			System.out.println(outInt.get(i) + " " + outStr.get(i));
		}
	}

	private static void process(BigInteger R, boolean reverse, ArrayList<Integer> outInt, ArrayList<String> outStr) {
		String tR = R.toString();
		
		char[] resL = new char[tR.length()];
		for (int i = 0; i < resL.length; i++) {
			resL[i] = tR.charAt(tR.length() - 1 - i); 
		}
		
		int idx = 0;
		int oldIdx = 0;
		int i = 0;

		ArrayList<Integer> myOutInt = new ArrayList<>();
		ArrayList<String> myOutStr = new ArrayList<>();
		while (idx < resL.length) {
			if (i <= 2) {
				idx++;
			} else {
				idx = 1 << (i - 2);
			}
			StringBuilder sb3 = new StringBuilder();
			
			boolean nonZero = false;
			for (int j = Math.min(idx-1, resL.length-1); j >= 0 && j >= oldIdx; j--) {
				if (resL[j] != '0') {
					nonZero = true;
				}
				if (nonZero) {
					sb3.append(resL[j]);
				}
			}
			if (nonZero) {
				myOutInt.add(i);
				myOutStr.add(sb3.toString());
			}	
			
			oldIdx = idx;
			i++;
		}
		
		if (reverse) {
			Collections.reverse(myOutStr);
			Collections.reverse(myOutInt);
		}
		for (int j = 0; j < myOutStr.size(); j++) {
			outStr.add(myOutStr.get(j));
			outInt.add(myOutInt.get(j));
		}
	}

	public static void testDivAndSpan() {
		for (int x = 1; x <= 5; x++) {
			for (int y = 1; y <= 5; y++) {
				//System.out.println("x/y: " + x + " " + y + "\t" + divAndSpan2(x,y) + "\t" + divAndSpan_slow(x,y));
				if (!(x == 0 && y == 0)) {
					System.out.println("x/y: " + x + " " + y + "\t" + divAndSpan3(x,y) + "\t" + divAndSpan_slow(x,y));
				}
			}
		}
	}
	
	public static long divAndSpan_slow(int X, int Y) {
		long num = 0;
		int len = X+Y;
		int[] a = new int[len * 2];
		
		do {
			int sumB = 0;
			int sumP = 0;
			
			int numB = 0;
			int numP = 0;
			for (int i = 0; i < a.length; i++) {
				if (a[i] == 0) {
					if (sumP > 0) {
						sumB = -1;
						break;
					}
					sumB++;
					numB++;
				} else if (a[i] == 1) {
					if (sumP > 0) {
						sumB = -1;
						break;
					}
					if (sumB <= 0) {
						sumB = -1;
						break;
					}
					sumB--;
					numB++;
				} else if (a[i] == 2) {
					sumP++;
					numP++;
				} else if (a[i] == 3) {
					if (sumP <= 0) {
						sumP = -1;
						break;
					}
					sumP--;
					numP++;
				}
			}
			ArrayList<Integer> c = new ArrayList<>();
			for (int i = 0; i < a.length; i++) {
				c.add(a[i]);
			}
			while (!c.isEmpty()) {
				boolean found = false;
				for (int i = 1; i < c.size(); i++) {
					if (i > 0 && ((c.get(i-1) == 0 && c.get(i) == 1) || (c.get(i-1) == 2 && c.get(i) == 3))) {
						c.remove(i-1);
						c.remove(i-1);
						i -= 2;
						found = true;
					}
				}
				if (!found) {
					sumB = -1;
					break;
				}
			}
			
			if (sumB == 0 && sumP == 0 && numB == X*2 && numP == Y*2) {
				String out = "";
				for (int i = 0; i < a.length; i++) {
					if (a[i] == 0) {
						out += '[';
					} else if (a[i] == 1) {
						out += ']';
					} else if (a[i] == 2) {
						out += '(';
					} else if (a[i] == 3) {
						out += ')';
					}
				}
//				System.out.println(out);
				num = (num + X*Y) % 1000000007L;
			}
		} while (next_number(a, 4));
		
		return num;
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

	public static void divAndSpan(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			System.out.println(divAndSpan_slow(x, y));
		}
	}
	
	public static long divAndSpan(int X, int Y) {
		long[][] F = new long[X+1][Y+1];
		F[0][1] = 1;
		F[1][0] = 1;
		
		for (int b = 0; b < F.length; b++) {
			for (int p = 0; p < F[0].length; p++) {
				if (p == 0 && b == 0) {
					F[b][p] = 0;
				} else if (b == 0) {
					if (p == 1) {
						F[b][p] = 1;
					} else {
						F[b][p] = p * 2 * F[b][p-1];
					}
				} else if (p == 0) {
					if (b == 1) {
						F[b][p] = 1;
					} else {
						F[b][p] = b * 2 * F[b-1][p];
					}
				} else {
					//F[b][p] = 2 * b * F[b-1][p] + 1 * p * F[b][p-1] + p * F[0][p-1];
					//F[b][p] = b * F[b-1][p] + p * F[b][p-1] + p * F[0][p-1];
					F[b][p] = b * 2 * F[b-1][p] + p * (F[b][p-1] + 2 * F[0][p-1]);
				}
			}
		}
		return F[X][Y];
	}
	
	private static long fCacheSize = 1000000; // Used to speed up calls, but answers will be correct without it.
	private static HashMap<Integer,BigInteger> fCache = new HashMap<Integer,BigInteger>();	
	private static BigInteger factorial(int n) {
        BigInteger ret;
        
        if (n < 0) return BigInteger.ZERO;
        if (n == 0) return BigInteger.ONE;
        
        if (null != (ret = fCache.get(n))) return ret;
        else ret = BigInteger.ONE;
        for (int i = n; i >= 1; i--) {
        	if (fCache.containsKey(n)) return ret.multiply(fCache.get(n));
        	ret = ret.multiply(BigInteger.valueOf(i));
        }
        
        if (fCache.size() < fCacheSize) {
        	fCache.put(n, ret);
        }
        return ret;
    }
	
	public static long divAndSpan3(int X, int Y) {
		return (factorial(2*X).multiply(factorial(2*Y))).divide(factorial(X + Y).multiply(factorial(X)).multiply(factorial(Y))).longValue();
	}
	
	public static long divAndSpan2(int X, int Y) {
		long[][] F = new long[X+1][Y+1];
		F[0][1] = 1;
		F[1][0] = 1;
		
		for (int b = 0; b < F.length; b++) {
			for (int p = 0; p < F[0].length; p++) {
				if (p == 0 && b == 0) {
					F[b][p] = 0;
				} else if (b == 0) {
					if (p == 1) {
						F[b][p] = 1;
					} else {
						F[b][p] = 2 * F[b][p-1];
					}
				} else if (p == 0) {
					if (b == 1) {
						F[b][p] = 1;
					} else {
						F[b][p] = 2 * F[b-1][p];
					}
				} else {
					//F[b][p] = 2 * b * F[b-1][p] + 1 * p * F[b][p-1] + p * F[0][p-1];
					//F[b][p] = b * F[b-1][p] + p * F[b][p-1] + p * F[0][p-1];
					
//					F[b][p] = 2*F[b-1][p] + 2*F[b][p-1];
					
					F[b][p] = F[b-1][p] + F[b][p-1];
					for (int i = 0; i <= b; i++) {
						for (int j = 0; j <= p; j++) {
//							if (i * 2 >= b && j * 2 >= p) {
//								break;
//							}
							if (!(i == 0 && j == 0)) {
								F[b][p] += F[i][j] * F[b-i][p-j]; 
							}
						}
					}
				}
			}
		}
		return F[X][Y];
	}

	public static void testBeautifulQuadruples() {
		long t1 = System.currentTimeMillis();
		//System.out.println(beautifulQuadruples2(new int[]{3000, 3000, 3000, 3000}));
		System.out.println(beautifulQuadruples2(new int[]{1, 2, 3, 4}));
		System.out.println("time: " + (System.currentTimeMillis() - t1));
	}

	public static void beautifulQuadruples(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int[] a = scan.nextIntArray(4);
		Arrays.sort(a);
		System.out.println(beautifulQuadruples2(a));
	}
	
	public static long beautifulQuadruples(int[] a) {
		//long[][][] F = new long[1 << 12][3001][4];
		long[][][] F = new long[1 << 6][51][4];
		for (int i = 1; i <= a[0]; i++) {
			F[i][i][0] = 1;
		}
		for (int n = 1; n < a.length; n++) {
			for (int i = 0; i < F.length; i++) {
				for (int j = 1; j <= a[n]; j++) {
					for (int k = 1; k <= j; k++) {
						F[i][j][n] += F[i ^ j][k][n-1]; 
					}
				}
			}
		}
		long ret = 0;
		for (int i = 1; i < F.length; i++) {
			for (int j = 1; j <= a[3]; j++) {
				ret += F[i][j][3];
			}
		}
		return ret;
	}
	
	public static long beautifulQuadruples2(int[] a) {
		long[][][] F = new long[4][1 << 12][3001];
		//long[][][] F = new long[1 << 6][51][4];
		//long[][][] F = new long[1 << 3][5][4];
		for (int i = 1; i <= a[0]; i++) {
			F[0][i][i] = 1;
		}
		for (int i = 0; i < F[0].length; i++) {
			for (int j = 1; j < F[0][0].length; j++) {
				F[0][i][j] += F[0][i][j-1];
			}
		}
		
		for (int n = 1; n < a.length; n++) {
			for (int i = 0; i < F[0].length; i++) {
				for (int j = 1; j <= a[n]; j++) {
					F[n][i][j] = F[n-1][i ^ j][j];
				}
				for (int j = 1; j < F[0][0].length; j++) {
					F[n][i][j] += F[n][i][j-1];
				}
			}
		}
		long ret = 0;
		for (int i = 1; i < F[0].length; i++) {
			for (int j = 1; j <= a[3]; j++) {
				ret += F[3][i][j] - F[3][i][j-1];
			}
		}
		return ret;
	}

	public static void absolutePermutation(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			System.out.println(absolutePermutation2(n, k));
		}
	}

	public static String testAbsolutePermutation() {
		for (int n = 1; n < 11; n++) {
			for (int k = 0; k < n; k++) {
//				System.out.println("n/k: " + n + " " + k);
				String r1 = absolutePermutation(n,k);
				String r2 = absolutePermutation2(n, k);
				if (!r1.equals(r2)) {
					System.out.println("fail");
				}
			}
			System.out.println();
		}
		return null;
	}

	public static String absolutePermutation2(int n, int k) {
		int[] x = new int[n];
		
		if (k == 0) {
			for (int i = 0; i < x.length; i++) {
				x[i] = i+1;
			}
		}
		
		int i = 0;
		int jump = k;
		while (k > 0 && i < x.length) {
			for (int j = 0; j < k && i+j < x.length; j++) {
				x[i+j] = j + jump + 1;
			}
			i += k;
			for (int j = 0; j < k && i+j < x.length; j++) {
				x[i+j] = i + j - k + 1;
			}
			i += k;
			jump += 2 * k;
		}
		
		boolean valid = true;
		HashSet<Integer> set = new HashSet<>();
		for (i = 0; i < x.length; i++) {
			set.add(x[i]);
			if (Math.abs(x[i] - (i+1)) != k || x[i] > n) {
				valid = false;
				break;
			}
		}
		for (int j = 0; j < x.length; j++) {
			if (!set.contains(j+1)) {
				valid = false;
				break;
			}
		}
		
		if (valid) {
			int[] ret = x;
			
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < ret.length; j++) {
				if (j > 0) {
					sb.append(' ');
				}
				sb.append(ret[j]);
			}
			return sb.toString();
		} else {
			return "-1";
		}
	}

	public static String absolutePermutation(int n, int k) {
		int[] x = new int[n];
		for (int i = 0; i < x.length; i++) {
			x[i] = i+1;
		}
		do {
			boolean valid = true;
			for (int i = 0; i < x.length; i++) {
				if (Math.abs(x[i] - (i+1)) != k) {
					valid = false;
					break;
				}
			}
			if (valid) {
				int[] ret = x;
				
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < ret.length; j++) {
					if (j > 0) {
						sb.append(' ');
					}
					sb.append(ret[j]);
				}
				return sb.toString();
			}
		} while (next_permutation(x));
		return "-1";
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

	public static void richieRich(InputStream in) {
		MyScanner scan = new MyScanner(in);
		scan.nextInt();
		int k = scan.nextInt();
		char[] s = scan.next().toCharArray();
		
//		for (int i = 0; i < s.length+4; i++) {
//			System.out.println(i + " " + richieRich(i, s.clone()));
//		}
		System.out.println(richieRich(k, s.clone()));
		
	}

	private static String richieRich(int k, char[] s) {		
		boolean[] ref = new boolean[s.length];
		for (int i = 0; i < s.length/2; i++) {
			if (s[i] != s[s.length -i -1]) {
				ref[i] = true;
				k--;
				if (s[i] > s[s.length -i -1]) {
					s[s.length -i -1] = s[i]; 
				} else {
					s[i] = s[s.length -i -1];
				}
			}
		}
		
		if (k < 0) {
			return "-1";
		}
		
		int t = 0;
		while (k > 0 && t <= s.length/2) {
			if (s[t] != '9') {
				int cost = 0;
				if (ref[t] || (t == s.length/2 && s.length % 2 == 1)) {
					cost = 1;
				} else {
					cost = 2;
				}
				if (k >= cost) {
					k -= cost;
					s[t] = '9';
					s[s.length -t -1] = '9';
				}
			}
			t++;
		}
		
		return new String(s);
	}

	public static void compareTheTriplets(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int[] a = scan.nextIntArray(3);
		int[] b = scan.nextIntArray(3);
		int al = 0;
		int bo = 0;
		for (int i = 0; i < b.length; i++) {
			if (a[i] > b[i]) {
				al++;
			} else if (a[i] < b[i]) {
				bo++;
			} 
		}
		System.out.println(al + " " + bo);
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
