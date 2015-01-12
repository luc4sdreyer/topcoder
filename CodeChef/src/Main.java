import static org.junit.Assert.assertEquals;

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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) throws IOException {
		//System.out.println(chefAndStones(System.in));
		//System.out.println(qcdQueries3(System.in));
		//testGcdQueries2();
		//testGcdQueries();
		//System.out.println(serejaAndVotes(System.in));
		//System.out.println(oneDimensionalKingdoms(System.in));
		//testOneDimensionalKingdoms2();
		//System.out.println(chefAndALargePermutation(System.in));
		//testChefAndALargePermutation();
		System.out.println(queriesOnTheString(System.in));
		//testQueriesOnTheString();
		//System.out.println(serejaAndNumberDivision2(System.in));
		//testSerejaAndNumberDivision2();
		//System.out.println(xorQueries(System.in));
		//System.out.println(serejaAndLCM(System.in));
	}

	public static String serejaAndLCM(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();
	
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			int l = scan.nextInt();
			int r = scan.nextInt();
			sb.append(serejaAndLCM2(n, m, l, r));
			sb.append("\n");
		}
		scan.close();
		return sb.toString();
	}

	public static String serejaAndLCM(int n, int m, int l, int r) {
		int num = 0;
		for (int d= l; d <= r; d++) {
			int num2 = 0;
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = 1;
			}
			do {
				if (testLcm(a, d)) {
					//System.out.println(Arrays.toString(a));
					num2++;
				}
			} while (next_number(a, m+1)); 
			System.out.println(d + "\t" + num2);
			num += num2;
		}
		System.out.println();
		return num + "";
	}

	public static String serejaAndLCM2(int n, int m, int l, int r) {
		int num = 0;
		final long mod = 1000000007;
		
		long[] pow = new long[n];
		pow[0] = 1;
		for (int i = 1; i < pow.length; i++) {
			pow[i] = (pow[i-1] * m) % mod;
		}
		
		long[][] single = new long[m+1][n];
		for (int i = 0; i < single.length; i++) {
			single[i][0] = i;
			for (int j = 1; j < n; j++) {
				single[i][j] = (single[i][j-1]*(m - i) + pow[j] * i) % mod;	
			}
		}
		
		for (int d = l; d <= r; d++) {
			int num2 = 0;
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = 1;
			}
			do {
				if (testLcm(a, d)) {
					//System.out.println(Arrays.toString(a));
					num2++;
				}
			} while (next_number(a, m+1)); 
			System.out.println(d + "\t" + num2);
			num += num2;
		}
		System.out.println();
		return num + "";
	}

	public static boolean testLcm(int[] a, int d) {
		BigInteger lcm = BigInteger.valueOf(a[0]);
		for (int i = 1; i < a.length; i++) {
			lcm = lcm(lcm, BigInteger.valueOf(a[i]));
		}
		if (lcm.mod(BigInteger.valueOf(d)).compareTo(BigInteger.ZERO) == 0) {
			return true;
		}
		return false;
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
				list[i] = 1;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}

	public static String xorQueries(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();

		int m = scan.nextInt();
		int queries[][] = new  int[m][4];
		for (int j = 0; j < m; j++) {
			int type = scan.nextInt();
			queries[j][0] = type;
			for (int i = 1; i < 4; i++) {
				if (i == 2 &&(type == 0 || type == 2)) {
					break;
				}
				queries[j][i] = scan.nextInt();
			}
		}
		sb.append(xorQueries2(m, queries));
		scan.close();
		return sb.toString();
	}
	
	public static String xorQueries2(int m, int[][] queries) {
		ArrayList<Pair<Integer, Integer>> ref = new ArrayList<>();
		//SegmentTree st = new SegmentTree();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < queries.length; i++) {
			int[] q = queries[i];
			if (q[0] == 0) {
				Pair<Integer, Integer> p = new Pair<>(q[1], ref.size()-1);
				ref.add(p);
			} else if (q[0] == 1) {
				int max = 0;
				int result = 0;
				int y = 0;
				for (int j = q[1]-1; j < q[2]; j++) {
					result = ref.get(j).first ^ q[3];
					if (result > max) {
						max = result;
						y = ref.get(j).first;
					}
				}
				sb.append(y);
				sb.append("\n");
			} else if (q[0] == 2) {
				for (int j = 0; j < q[1]; j++) {
					ref.remove(ref.size()-1);
				}
			} else if (q[0] == 3) {
				int num = 0;
				for (int j = q[1]-1; j < q[2]; j++) {
					if (ref.get(j).first <= q[3]) {
						num++;
					}
				}
				sb.append(num);
				sb.append("\n");
			} else {
				int[] a = new int[q[2] - q[1]+1];
				int t = 0;
				for (int j = q[1]-1; j < q[2]; j++) {
					a[t++] = ref.get(j).first;
				}
				Arrays.sort(a);
				sb.append(a[q[3]-1]);
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static String xorQueries(int m, int[][] queries) {
		ArrayList<Pair<Integer, Integer>> ref = new ArrayList<>();
		ArrayList<Pair<Integer, Integer>> sorted = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < queries.length; i++) {
			int[] q = queries[i];
			if (q[0] == 0) {
				Pair<Integer, Integer> p = new Pair<>(q[1], ref.size()-1);
				ref.add(p);
				int insertIdx = Collections.binarySearch(sorted, p);
				insertIdx = -insertIdx + 1;
				sorted.add(insertIdx, p);
			} else if (q[0] == 1) {
				int max = 0;
				int result = 0;
				int y = 0;
				for (int j = q[1]-1; j < q[2]; j++) {
					result = ref.get(j).first ^ q[3];
					if (result > max) {
						max = result;
						y = ref.get(j).first;
					}
				}
				sb.append(y);
				sb.append("\n");
			} else if (q[0] == 2) {
				for (int j = 0; j < q[1]; j++) {
					Pair<Integer, Integer> p = ref.remove(ref.size()-1);
					int idx = Collections.binarySearch(sorted, p);
					sorted.remove(idx);
				}
			} else if (q[0] == 3) {
				int low = q[1]-1;
				int high = q[2]-1;
				int mid = (low + high)/2;
				while (low <= high) {
					mid = (low + high)/2;
					if (sorted.get(mid).first > q[3]) {
						high = mid-1;
					} else {
						low = mid+1;
					}
				}
				sb.append(mid - (q[1]-1));
				sb.append("\n");
			} else {
				sb.append(sorted.get((q[1]-1)));
			}
		}
		return null;
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

	public static void testSerejaAndNumberDivision2() {
		int numTests = 20;
		double avg = 0;
		for (int i = 0; i < numTests; i++) {
			long time = System.currentTimeMillis();
			avg += testSerejaAndNumberDivision2Sub(new Random(i));
			time = System.currentTimeMillis() - time;
			//System.out.println("\t" + time);
		}
		avg /= numTests;
		System.out.println("avg: " + avg);
	}

	public static double testSerejaAndNumberDivision2Sub(Random rand) {
		int numTests = 100;
		long y = 0;
		long min = Integer.MAX_VALUE;
		long max = 0;
		double avgY = 0;
		for (int t = 0; t < numTests; t++) {
			int m = rand.nextInt(1000)+1;
			char[] a = new char[m];
			for (int i = 0; i < a.length; i++) {
				a[i] = (char) (rand.nextInt(9)+1 + '0');
			}
			int n = 100;
			int[] b = new int[n];
			int range = rand.nextInt(1000000)+1; 
			for (int i = 0; i < b.length; i++) {
				b[i] = rand.nextInt(range)+1;
			}
			char[] ret = serejaAndNumberDivision2(a, n, b, rand).toCharArray();
			int r = f(ret, b); 
			y += r;
			avgY += r; 
			min = Math.min(min, r);
			max = Math.max(max,r );
		}
		avgY /= numTests;
		System.out.println(y + "\t" + avgY + "\t" + min + "\t" + max);
		return avgY;
	}

	public static int f(char[] a, int[] b) {
		int ret = 0;
		BigInteger A = new BigInteger(new String(a));
		for (int i = 0; i < b.length; i++) {
			ret += A.mod(BigInteger.valueOf(b[i])).intValue();
		}
		return ret;
	}

	public static int f(BigInteger A, int[] b) {
		int ret = 0;
		for (int i = 0; i < b.length; i++) {
			ret += A.mod(BigInteger.valueOf(b[i])).intValue();
		}
		return ret;
	}

	public static int f(ArrayList<Character> a, int[] b, int bLength) {
		if (bLength == 0) {
			bLength = b.length;
		}
		int ret = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i));
		}
		BigInteger A = new BigInteger(sb.toString());
		for (int i = 0; i < bLength; i++) {
			ret += A.mod(BigInteger.valueOf(b[i])).intValue();
		}
		return ret;
	}

	public static String serejaAndNumberDivision2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[] a = scan.nextLine().toCharArray();
			int n = scan.nextInt();
			int[] b = new int[n];
			for (int j = 0; j < n; j++) {
				b[j] = scan.nextInt();
			}
			sb.append(serejaAndNumberDivision3(a, n, b, null));
			sb.append("\n");
		}
		scan.close();
		return sb.toString();
	}	

	public static BigInteger lcm(BigInteger a, BigInteger b)
	{
		return b.multiply(a).divide(a.gcd(b));
	}

	public static int vDiff(char[] a, char[] b) {
		int diff = 0;

		int[] af = new int[10];
		for (int i = 0; i < a.length; i++) {
			af[a[i]-'0']++;
		}

		int[] bf = new int[10];
		for (int i = 0; i < b.length; i++) {
			bf[b[i]-'0']++;
		}

		for (int i = 0; i < af.length; i++) {
			diff += Math.abs(af[i] - bf[i]);
		}
		return diff;
	}

	@SuppressWarnings("unchecked")
	public static String serejaAndNumberDivision3(char[] a, int n, int[] b, Random random) {
//		if (n > -1) {
//			return new String(a);
//		}
		if (random == null) {
			random = new Random(0);
		}
		Arrays.sort(b);
//		for (int i = 0; i < b.length/2; i++) {
//			int t = b[i];
//			b[i] = b[b.length -i -1];
//			b[b.length -i -1] = t;
//		}
		
		ArrayList<Character> rand = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			rand.add(a[i]);
		}
		int min = Integer.MAX_VALUE;
		ArrayList<Character> minA = null;

		int f = f(rand, b, 0);
		if (f < min) {
			min = f;
			minA = (ArrayList<Character>) rand.clone();
		}
		Collections.sort(rand);
		f = f(rand, b, 0);
		if (f < min) {
			min = f;
			minA = (ArrayList<Character>) rand.clone();
		}
		Collections.reverse(rand);
		f = f(rand, b, 0);
		if (f < min) {
			min = f;
			minA = (ArrayList<Character>) rand.clone();
		}
		for (int i = 0; i < 10; i++) {
			Collections.shuffle(rand, random);
			f = f(rand, b, 0);
			if (f < min) {
				min = f;
				minA = (ArrayList<Character>) rand.clone();
				//System.out.println(rand);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < minA.size(); i++) {
			sb.append(minA.get(i));
		}
		return new String(sb.toString());
	}

	public static String serejaAndNumberDivision2(char[] a, int n, int[] b, Random random) {
		// get LCM
		BigInteger lcm = BigInteger.ONE;

		for (int i = 0; i < b.length; i++) {
			b[i] = (b[i]/10)*10 + 1;
		}
		
		Arrays.sort(a);
		Arrays.sort(b);
		int minB = 0;
		int min = Integer.MAX_VALUE;
		BigInteger A = new BigInteger(new String(a));
		BigInteger minA = A;
		BigInteger lcmLow = A.divide(lcm).multiply(lcm);
		BigInteger lcm2 = null;
		for (int i = 0; i < b.length; i++) {
			lcm2 = lcm(lcm, BigInteger.valueOf(b[i]));
			//			if (lcm2.bitLength() >= A.bitLength()) {
			//				break;
			//			}			
			lcm = lcm2;
			//			lcmLow = A.divide(lcm).multiply(lcm);
			//			int minDiff = vDiff(a, lcmLow.toString().toCharArray()); 
			//			if (minDiff < min) {
			//				min = minDiff;
			//				minA = lcmLow;
			//			}
			if (b[i] > 1 && minB == 0) {
				minB = b[i];
			}
		}

		lcmLow = A.divide(lcm).multiply(lcm);
		int minDiff = vDiff(a, lcmLow.toString().toCharArray()); 
		if (minDiff < min) {
			min = minDiff;
			minA = lcmLow;
		}
		char[] lcmA = lcm.toString().toCharArray();
		int[] lcmF = new int[10];
		for (int i = 0; i < lcmA.length; i++) {
			lcmF[lcmA[i]-'0']++;
		}
		//System.out.println(Arrays.toString(lcmF) + "\t" + lcm);
		while (lcm.bitLength() < A.bitLength()) {
			lcm2 = lcm.multiply(BigInteger.valueOf(3));
			if (lcm2.bitLength() > A.bitLength()) {
				break;
			}			
			lcm = lcm2;
			lcmLow = A.divide(lcm).multiply(lcm);
			minDiff = vDiff(a, lcmLow.toString().toCharArray()); 
			if (minDiff < min) {
				min = minDiff;
				minA = lcmLow;
			}
			lcmLow = lcmLow.add(lcm);
			minDiff = vDiff(a, lcmLow.toString().toCharArray()); 
			if (minDiff < min) {
				min = minDiff;
				minA = lcmLow;
			}
		}
		//		for (int i = 0; i < 1000; i++) {
		//			
		//		}
		if (minA.compareTo(BigInteger.ZERO) == 0) {
			minA = A;
		}
		return tryToMatch(a, minA);
	}

	public static String tryToMatch(char[] a, BigInteger target) {
		int[] af = new int[10];
		for (int i = 0; i < a.length; i++) {
			af[a[i]-'0']++;
		}

		char[] newA = new char[a.length];
		char[] tar = target.toString().toCharArray();
		int t = 0;
		int i = 0;
		for (i = 0; i < tar.length; i++) {
			if (af[tar[i]-'0'] > 0) {
				af[tar[i]-'0']--;
				newA[i] = tar[i]; 
			} else {
				while (af[t] == 0) {
					t++;
				}
				af[t]--;
				newA[i] = (char) (t + '0');
			}
		}
		while (t < af.length && af[t] != 0) {
			while (af[t] == 0) {
				t++;
			}
			af[t]--;
			newA[i++] = (char) (t + '0');
		}
		af[t]--;
		return new String(newA);
	}

	public static void testChefAndALargePermutation() {
		Random rand = new Random(0);
		for (int n = 1; n <= 30; n++) {
			for (int k = 0; k <= n; k++) {
				for (int i = 0; i < 10000; i++) {
					int[] a = new int[k];
					int last = 0;
					for (int j = 0; j < a.length; j++) {
						last = rand.nextInt((n - last) / (a.length - j))+1+last;
						a[j] = last;
					}
					String res1 = chefAndALargePermutationOld(n, k, a);
					String res2 = chefAndALargePermutation(n, k, a);
					if (!res1.equals(res2)) {
						System.out.println("FAIL");
						chefAndALargePermutationOld(n, k, a);
						chefAndALargePermutation(n, k, a);
					}
				}
			}
		}
	}

	public static String queriesOnTheString(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();

		int n = scan.nextInt();
		int m = scan.nextInt();
		char[] num = scan.nextLine().toCharArray();
		int queries[][] = new  int[m][3];
		for (int j = 0; j < m; j++) {
			queries[j][0] = scan.nextInt();
			queries[j][1] = scan.nextInt();
			queries[j][2] = scan.nextInt();
		}
		sb.append(queriesOnTheString6(n, m, num, queries));
		sb.append("\n");
		scan.close();
		return sb.toString();
	}
	
	public static void testQueriesOnTheString() {
		int maxLength = 10;
		int maxValue = 10;
		int numTests = 1000;
		Random rand = new Random(0);
		
//		char[] t = new char[100000];
//		for (int i = 0; i < t.length; i++) {
//			//t[i] = (char) (rand.nextInt(3) + '0');
//			t[i] = '0';
//		}
//		System.out.println(queriesOnTheString6(t.length, 1, t, new int[][]{{2, 1, t.length}}));
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			char[] num = new char[len];
			for (int i = 0; i < num.length; i++) {
				num[i] = (char) (rand.nextInt(maxValue) + '0');
			}
			
			int[][] queries = new int[numTests][3];
			for (int i = 0; i < numTests; i++) {
				//queries[i][0] = rand.nextInt(2)+1;
				queries[i][0] = 2;
				if (queries[i][0] == 1) {
					queries[i][1] = rand.nextInt(len)+1;
					queries[i][2] = rand.nextInt(maxValue);
				} else {
					queries[i][1] = rand.nextInt(len)+1;
					queries[i][2] = rand.nextInt(len - queries[i][1] + 1) + queries[i][1];
				}
			}
			
			String expected = queriesOnTheString2(len, queries.length, num, queries);
			String actual = queriesOnTheString6(len, queries.length, num, queries);
			if (!expected.equals(actual)) {
				actual = queriesOnTheString6(len, queries.length, num, queries);
				System.out.println("FAIL");
			}
		}
	}

	public static String queriesOnTheString(int n, int m, char[] numChar, int[][] queries) {

		int[][] map = new int[n][n];
		int res = 0;
		int[] num = new int[numChar.length];
		for (int i = 0; i < num.length; i++) {
			num[i] = numChar[i] - '0';
		}
		for (int i = 0; i < n; i++) {
			res = (10 * res + num[i]) % 3;
			map[i][0] = res;
		}
		for (int j = 1; j < n; j++) {
			int add = 3 - ((num[j-1] * 10) % 3);
			for (int i = j; i < n; i++) {
				map[i][j] = (map[i][j-1] + add) % 3;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == 1) {
				int diff = (3 - (num[queries[i][1]-1] % 3) + (queries[i][2] % 3)) % 3;
				if (diff != 0) {
					for (int y = queries[i][1]-1; y < map.length; y++) {
						for (int x = 0; x <= queries[i][1]-1; x++) {
							map[y][x] = (map[y][x] + diff) % 3;
						}
					}
				}
				num[queries[i][1]-1] = queries[i][2];
			} else {
				int count = 0;
				for (int y = queries[i][1]-1; y <= queries[i][2]-1; y++) {
					for (int x = queries[i][1]-1; x <= y; x++) {
						if (map[y][x] == 0) {
							count++;
						}
					}
				}
				sb.append(count);
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static String queriesOnTheString6(int n, int m, char[] numChar, int[][] queries) {
		int[] num = new int[numChar.length];
		for (int i = 0; i < num.length; i++) {
			num[i] = (numChar[i] - '0') % 3;
		}
		
		NodeST[] nodes = new NodeST[num.length];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new NodeST(num[i]);
		}
		StringBuilder sb = new StringBuilder();
		SegmentTreeE<NodeST> st = new SegmentTreeE<NodeST>(nodes);
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == 1) {
				st.set(queries[i][1]-1, new NodeST(queries[i][2]));
			} else {
				long add = st.get(queries[i][1]-1, queries[i][2]-1).answer;
				sb.append(add);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public static class NodeST implements BinaryOperation<NodeST> {
		private long answer;
		private int[] count1;
		private int[] count2;
		private int total;
		private boolean unset; 
		
		public NodeST(int a) {
			this.answer = a % 3 == 0 ? 1 : 0;
			this.count1 = new int[3];
			this.count2 = new int[3];
			this.total = a % 3;
			this.count1[total]++;
			this.count2[total]++;
			this.unset = false;
		}
		
		public NodeST(NodeST a, NodeST b) {
			this.answer = a.answer + b.answer;
			this.count1 = new int[3];
			this.count2 = new int[3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if ((i + j) % 3 == 0) {
						this.answer += (long)a.count2[i] * b.count1[j]; 
					}
				}
			}
			for (int i = 0; i < 3; i++) {
				this.count1[i] = a.count1[i] + b.count1[(3 - a.total+i) % 3];
				this.count2[i] = b.count2[i] + a.count2[(3 - b.total+i) % 3];
			}
			this.total = (a.total + b.total) % 3;
			this.unset = false;
		}
		
		@Override
		public NodeST function(NodeST e1, NodeST e2) {
			if (e1.unset) {
				if (e2.unset) {
					return zero;
				}
				return e2;
			}
			if (e2.unset) {
				return e1;
			}
			return new NodeST(e1, e2);
		}

		public final static NodeST zero;
		static {
			zero = new NodeST(0);
			zero.unset = true;
		}
		
		@Override
		public NodeST identity() {
			return zero;
		}
		
		public String toString() {
			return Long.toString(answer);
		}
	}
	
	public static interface BinaryOperation<E> {
		public E identity();
		public E function(E e1, E e2);
	}
	
	public static class SegmentTreeE<E extends BinaryOperation<E>> {

		private ArrayList<ArrayList<E>> t;
		private ArrayList<E> a;
		private int N;
		private int n;
		private E identity;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected E function(E e1, E e2) {
			return e1.function(e1, e2);
		}

		public SegmentTreeE(E[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new ArrayList<E>(N);
			this.identity = b[0].identity();
			for (int i = 0; i < N; i++) {
				if (i < b.length) {
					this.a.add(b[i]);
				} else {
					this.a.add(identity);
				}
			}
			t = new ArrayList<ArrayList<E>>(N);
			for (int x = 0; x < N; x++) {
				ArrayList<E> list = new ArrayList<E>();
				t.add(list);
				for (int i = 0; i < n+1; i++) {
					list.add(identity);
				}
				t.get(x).set(0, a.get(x));
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t.get(x).set(y, function(t.get(x).get(y-1), t.get(x+(1<<(y-1))).get(y-1)));
				}
			}
		}

		public void set(int i, E v) {
			this.a.set(i, v);
			t.get(i).set(0, v);
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t.get(x).set(y, function(t.get(x).get(y-1), t.get(x+(1<<(y-1))).get(y-1)));
			}
		}

		/**
		 * Get the function over the interval [a, b].
		 */
		public E get(int i, int j) {
			E res = identity;
			int h = 0; j++;
			while (i+(1<<h) <= j) {
				while ((i&((1<<(h+1))-1)) == 0 && i+(1<<(h+1)) <= j) h++;
				res = function(res, t.get(i).get(h));
				i += (1<<h);
			}
			while (i < j) {
				while (i+(1<<h) > j) h--;
				res = function(res, t.get(i).get(h));
				i += (1<<h);
			}
			return res;
		}
	}
	

	public static String queriesOnTheString5(int n, int m, char[] numChar, int[][] queries) {
		long[] num = new long[numChar.length];
		for (int i = 0; i < num.length; i++) {
			num[i] = (numChar[i] - '0') % 3;
		}
		
		int[][] endings = new int[3][num.length];
		int[] sum = new int[num.length];
		int[][] sumMap = new int[3][num.length];
		int[] cumulative = new int[num.length];
		
		for (int i = 0; i < endings[0].length; i++) {
			if (i == 0) {
				endings[(int) num[i]][i] = 1;
				cumulative[i] = endings[0][i];
				sum[0] = (int) num[i];
				sumMap[sum[i]][i]++;
			} else {
				endings[(int) num[i]][i]++;
				for (int j = 0; j < 3; j++) {
					endings[(int) ((j + num[i]) % 3)][i] += endings[j][i-1];
				}
				cumulative[i] = cumulative[i-1] + endings[0][i];
				sum[i] = (sum[i-1] + (int) num[i]) % 3;
				for (int j = 0; j < 3; j++) {
					sumMap[j][i] = sumMap[j][i-1];
				} 
				sumMap[sum[i]][i]++;
			}
		}
		
		int ab = getNumQueries(endings, sumMap, 0, n-1);

		
		StringBuilder sb = new StringBuilder();
		SegmentTreeSumMod3a st = new SegmentTreeSumMod3a(num);
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == 1) {
				int newValue = queries[i][2] % 3;
				if (num[queries[i][1]-1] != newValue) {
					num[queries[i][1]-1] = newValue;
					st = new SegmentTreeSumMod3a(num);
				}
			} else {
				long add = st.get(queries[i][1]-1, queries[i][2]-1)/10;
				sb.append(add);
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static int getNumQueries(int[][] endings, int[][] sumMap, int start, int end) {
		int n = sumMap[0].length;
		int[][] subCount = new int[3][n+1];
		int[] sumCount = new int[n+1];

		subCount[0][0] = endings[0][end];
		int i = end;
		for (int j = 0; j < 3; j++) {
			subCount[j][i+1] = endings[j][end] * (sumMap[j][i] + (j == 0 ? 1 : 0));
		}
		sumCount[i] = subCount[0][i] + subCount[1][i] + subCount[2][i];
		if (i > 0) {
			sumCount[i] = subCount[0][i] + subCount[1][i] + subCount[2][i] 
					- ((sumMap[0][i-1] * (sumMap[0][i-1]+1))/2 + (sumMap[1][i-1] * (sumMap[1][i-1]+1))/2 + (sumMap[2][i-1] * (sumMap[2][i-1]+1))/2);
		}
		return 0;
	}

	public static class SegmentTreeSumMod3a extends SegmentTree {
		public SegmentTreeSumMod3a(long[] a) {
			super(a);
			super.IDENTITY = 0;
		}

		@Override
		protected long function(long a, long b) {
			long rem = ((a%10) + (b%10)) % 3;
			long num = a/10 + b/10;
			if (rem == 0) {
				num++;
			}
			return num*10 + rem;
		}
	}

	public static String queriesOnTheString4(int n, int m, char[] numChar, int[][] queries) {

		int[] num = new int[numChar.length];
		for (int i = 0; i < num.length; i++) {
			num[i] = (numChar[i] - '0') % 3;
		}
		
		StringBuilder sb = new StringBuilder();
		int[][] cumulative = buildQueriesLookup(num);
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == 1) {
				int newValue = queries[i][2] % 3;
				if (num[queries[i][1]-1] != newValue) {
					num[queries[i][1]-1] = newValue;
				}
			} else {
				int add = 0;
				if (queries[i][1] > 1) {
					add = cumulative[queries[i][2]-1][0] - cumulative[queries[i][1]-2][0];
				} else {
					add = cumulative[queries[i][2]-1][0];
				}
				int rem = 0;
				if (queries[i][1] > 2) {
					rem = cumulative[queries[i][2]-2][1] - cumulative[queries[i][1]-3][1];
				} else if (queries[i][1] > 1) {
					rem = cumulative[queries[i][2]-2][1];
				}
				add -= rem;
				sb.append(add);
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static int[][] buildQueriesLookup(int[] num) {
		int[][] dp = new int[3][num.length];
		int[][] cumulative = new int[num.length][2];
		
		for (int i = 0; i < dp[0].length; i++) {
			if (i == 0) {
				dp[num[i]][i] = 1;
				cumulative[i][0] = dp[0][i];
			} else {
				dp[num[i]][i]++;
				for (int j = 0; j < 3; j++) {
					dp[(j + num[i]) % 3][i] += dp[j][i-1];
				}
				cumulative[i][0] = cumulative[i-1][0] + dp[0][i];
			}
		}
		
		dp = new int[3][num.length];
		for (int i = dp[0].length-1; i >= 0; i--) {
			if (i == dp[0].length-1) {
				dp[num[i]][i] = 1;
			} else {
				dp[num[i]][i]++;
				for (int j = 0; j < 3; j++) {
					dp[(j + num[i]) % 3][i] += dp[j][i+1];
				}
			}
		}
		
		cumulative[0][1] = dp[0][0];
		for (int i = 1; i < dp[0].length; i++) {
			cumulative[i][1] = cumulative[i-1][1] + dp[0][i];
		}
		return cumulative;
	}

	public static String queriesOnTheString2(int n, int m, char[] numChar, int[][] queries) {

		int[] num = new int[numChar.length];
		for (int i = 0; i < num.length; i++) {
			num[i] = (numChar[i] - '0') % 3;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == 1) {
				int newValue = queries[i][2] % 3;
				if (num[queries[i][1]-1] != newValue) {
					num[queries[i][1]-1] = newValue;
				}
			} else {
				int count = buildQueriesLookup(num, queries[i][1]-1, queries[i][2]-1);
				sb.append(count);
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static int buildQueriesLookup(int[] num, int a, int b) {
		int[][] dp = new int[3][b-a+1];
		
		int count = 0;
		for (int i = 0; i < dp[0].length; i++) {
			if (i == 0) {
				dp[num[a]][0] = 1;
			} else {
				dp[num[a+i]][i]++;
				for (int j = 0; j < 3; j++) {
					dp[(j + num[a+i]) % 3][i] += dp[j][i-1];
				}
			}
			count += dp[0][i];
		}
		return count;
	}

	public static class SegmentTreeSumMod3 extends SegmentTree {
		public SegmentTreeSumMod3(long[] a) {
			super(a);
			super.IDENTITY = 0;
		}

		protected int function(int a, int b) {
			return (a + b) % 3;
		}
	}

	public static String queriesOnTheString3(int n, int m, char[] numChar, int[][] queries) {
		int[] num = new int[numChar.length];
		for (int i = 0; i < num.length; i++) {
			num[i] = (numChar[i] - '0') % 3;
		}
		
		int[] add = new int[num.length];
		SegmentTree2[] st = buildQueriesOnTheString3(n, num, add);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m; i++) {
			if (queries[i][0] == 1) {
				int diff = (3 - (num[queries[i][1]-1] % 3) + (queries[i][2] % 3)) % 3;
				if (diff != 0) {
					num[queries[i][1]-1] = queries[i][2];
					st = buildQueriesOnTheString3(n, num, add);
				}
			} else {
				int count = st[add[queries[i][1]-1]].get(queries[i][1]-1, queries[i][2]-1);
				sb.append(count);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public static SegmentTree2[] buildQueriesOnTheString3(int n, int[] num, int[] add) {
		Arrays.fill(add, 0);
		for (int j = 1; j < n; j++) {
			add[j] = (add[j-1] + (3 - ((num[j-1] * 10) % 3))) % 3;
		}
		int[][] dp = new int[3][num.length];

		int res = 0;
		for (int i = 0; i < n; i++) {
			res = (res + num[i]) % 3;
			dp[res][i]++;
		}
		SegmentTree2[] st = new SegmentTree2[3];
		for (int i = 0; i < st.length; i++) {
			st[i] = new SegmentTree2(dp[i]);
		}
		return st;
	}

	public static class SegmentTree2 {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;
		
		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return a + b;
		}
		
		protected int IDENTITY = 0;

		public SegmentTree2(int[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new int[N];
			for (int i = 0; i < b.length; i++) {
				this.a[i] = b[i];
			}
			t = new int[N][n+1];
			for (int x = 0; x < N; x++) {
				t[x][0] = a[x];
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
				}
			}
		}

		public void set(int x, int v) {
			t[x][0] = a[x] = v;
			for (int y = 1; y <= n; y++) {
				int xx = x-(x&((1<<y)-1));
				t[xx][y] = function(t[xx][y-1], t[xx+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b].
		 */
		public int get(int i, int j) {
			int res = IDENTITY, h = 0; j++;
			while (i+(1<<h) <= j) {
				while ((i&((1<<(h+1))-1)) == 0 && i+(1<<(h+1)) <= j) h++;
				res = function(res, t[i][h]);
				i += (1<<h);
			}
			while (i < j) {
				while (i+(1<<h) > j) h--;
				res = function(res, t[i][h]);
				i += (1<<h);
			}
			return res;
		}
	}

	public static String chefAndALargePermutation(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] missing = new int[k];
			for (int j = 0; j < k; j++) {
				missing[j] = scan.nextInt();
			}
			sb.append(chefAndALargePermutation(n, k, missing));
			sb.append("\n");
		}
		scan.close();
		return sb.toString();
	}

	public static String chefAndALargePermutation(long n, int k, int[] missing) {
		if (k == 0) {
			if (n*(n+1)/2 % 2 == 0) {
				return "Chef";
			} else {
				return "Mom";
			}
		} else {
			HashSet<Integer> missingSet = new HashSet<>();
			for (int i = 0; i < missing.length; i++) {
				missingSet.add(missing[i]);
			}
			if (missingSet.contains(1)) {
				return "Chef";
			} else if (missingSet.contains(2)) {
				return "Mom";
			}

			Arrays.sort(missing);

			long missingSum = 0;
			long x = 0;
			long max = 0;
			boolean incomplete = false;
			for (int i = 0; i < missing.length; i++) {
				//
				long present = missing[i]-1;
				x = present*(present+1)/2 - missingSum;
				//max = Math.max(max, x);
				missingSum += missing[i];
				boolean first = true;
				while (i < missing.length-1 && missing[i] == missing[i+1]-1) {
					//					if (!first) {
					//						missingSum += missing[i];
					//					} else {
					//						first = false;
					//					}
					i++;
					missingSum += missing[i];
				}
				if (i < missing.length && missing[i]+1 <= n) {
					long next = missing[i]+1;
					if (next > x + 1) {
						incomplete = true;
						max = Math.max(max, x);
						break;
					}
				}
			}
			if (!incomplete && missing[missing.length-1] != n) {
				missingSum = 0;
				for (int i = 0; i < missing.length; i++) {
					if (!missingSet.contains(missing[i]-1)) {
						long a = missing[i]-1;
						x = a*(a+1)/2 - missingSum;
						max = Math.max(max, x);
					}
					missingSum += missing[i];
				}
				x = n*(n+1)/2 - missingSum;
				max = Math.max(max, x);
			}
			max = Math.max(max, x);
			x = max;
			//			if (!incomplete) {
			//				for (int i = 0; i < missing.length; i++) {
			//					missingSum += missing[i];
			//				}
			//				x = n*(n+1)/2 - missingSum;
			//			}

			if (x % 2 == 0) {
				return "Chef";
			} else {
				return "Mom";
			}
		}
	}

	public static String chefAndALargePermutationOld(int n, int k, int[] missing) {
		if (k == 0) {
			long ln = n;
			if (ln*(ln+1)/2 % 2 == 0) {
				return "Chef";
			} else {
				return "Mom";
			}
		} else {
			int[] a = new int[n-k];
			Arrays.sort(missing);
			int t = 0;
			int u = 0;
			for (int i = 1; i <= n; i++) {
				if (t < missing.length && i == missing[t]) {
					t++;
				} else {
					a[u++] = i;
				}
			}
			boolean[] ways = waysToSum(a);
			for (int i = 0; i < ways.length; i++) {
				if (!ways[i]) {
					if ((i+1) % 2 == 0) {
						return "Chef";
					} else {
						return "Mom";
					}
				}
			}
			if ((ways.length+1) % 2 == 0) {
				return "Chef";
			} else {
				return "Mom";
			}
		}
	}

	public static boolean[] waysToSum(int[] values) {
		//Arrays.sort(values);
		int max = 0;
		for (int i = 0; i < values.length; i++) {
			max += values[i];
		}
		boolean[] ways = new boolean[max+1];
		ways[0] = true;
		for (int i = 0; i < values.length; i++) {
			for (int sum = max; sum >= values[i]; sum--) {
				if (ways[sum - values[i]]) {
					ways[sum] = true;
				}
			}
		}
		return ways;
	}

	/**

#include <list>
#include <cmath>
#include <ctime>
#include <deque>
#include <queue>
#include <stack>
#include <bitset>
#include <cstdio>
#include <vector>
#include <cstdlib>
#include <numeric>
#include <sstream>
#include <iostream>
#include <algorithm>
#include <cstring>

using namespace std;

void oneDimensionalKingdoms(int n, vector<pair<int, int> > kingdoms) {
	std::sort(kingdoms.begin(), kingdoms.end());

	int mini = 0;
	int maxi = 2000;
	// for (int i = 0; i < n; i++) {
		// mini = std::min(mini, kingdoms[i].first);
		// maxi = std::max(maxi, kingdoms[i].second);
	// }

    int m = maxi - mini + 3;
	//int line[m][2];
	//memset(line, 0, sizeof(int) * m*2);
	int line[m];
	memset(line, 0, sizeof(int) * m);

	for (int i = 0; i < n; i++) {
		//line[kingdoms[i].first- mini][0]++;
		//line[kingdoms[i].second+1- mini][1]++;
		line[kingdoms[i].second+1- mini]++;
	}

	int bomb = 0;
	for (int i = 1; i < m; i++) {
		if (line[i] > line[i-1]) {
			bomb++;
			for (int j = 0; j < kingdoms.size(); j++) {
				if (kingdoms[j].first >= mini + i) {
					break;
				}
				pair<int,int> range = kingdoms[j--];
				kingdoms.erase(kingdoms.begin());
				//line[range.first- mini][0]--;
				//line[range.second+1- mini][1]--;
				line[range.second+1- mini]--;
			}
		}
	}

	cout << bomb << endl;
}

int main() {
	int t, n, a, b;
	cin >> t;
	for (int i = 0; i < t; i++) {
		cin >> n;
		vector<pair<int, int> > kingdoms;
		for (int j = 0; j < n; j++) {
			cin >> a;
			cin >> b;
			kingdoms.push_back(pair<int, int>(a, b));
		}
		oneDimensionalKingdoms(n, kingdoms);
	}
	return 0;
}


	 */

	public static void testOneDimensionalKingdoms2() throws IOException {
		int tests = 10;
		int n = 100000;
		int range = 2000;
		Random rand = new Random();
		for (int k = 0; k < 10; k++) {
			long time = 0;
			for (int i = 0; i < tests; i++) {
				int[][] king = new int[n][2];
				for (int j = 0; j < n; j++) {
					int a = rand.nextInt(range);
					int b = rand.nextInt(range - a) + a;
					king[j][0] = a;
					king[j][1] = b;
				}
				long time2 = System.currentTimeMillis();
				oneDimensionalKingdoms(n, king);
				time += System.currentTimeMillis() - time2;
			}
			//System.out.println(Arrays.toString(timer));
			System.out.println("time: " + time);
		}
	}

	public static void testOneDimensionalKingdoms() throws IOException {
		int tests = 100;
		int n = 10000;
		int range = 2000;
		long time = 0;
		Random rand = new Random(0);
		for (int k = 0; k < tests; k++) {
			StringBuilder sb = new StringBuilder();
			sb.append(1 + "\n");
			for (int i = 0; i < tests; i++) {
				sb.append(n + "\n");
				for (int j = 0; j < n; j++) {
					int a = rand.nextInt(range);
					int b = rand.nextInt(range - a) + a;
					sb.append(a + " " + b + "\n");
				}
			}
			ByteArrayInputStream b = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
			long time2 = System.currentTimeMillis();
			oneDimensionalKingdoms(b);
			time += System.currentTimeMillis() - time2;
			b.close();
		}
		System.out.println("time: " + time);
	}

	public static String oneDimensionalKingdoms(InputStream in) {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int[][] kingdoms = new int[n][2];
			for (int j = 0; j < n; j++) {
				kingdoms[j][0] = scan.nextInt();
				kingdoms[j][1] = scan.nextInt();
			}
			sb.append(oneDimensionalKingdoms(n, kingdoms));
			sb.append("\n");
		}
		scan.close();
		return sb.toString();
	}

	public static ArrayComparator arraySort = new ArrayComparator();
	public static class ArrayComparator implements Comparator<int[]> {
		@Override
		//		public int compare(int[] a, int[] b) {
		//			if (a[0] == b[0]) {
		//				return Integer.compare(a[1], b[1]);
		//			}
		//			return Integer.compare(a[0], b[0]);
		//		}
		public int compare(int[] a, int[] b) {
			if (a[0] == b[0]) {
				return (a[1] < b[1]) ? -1 : ((a[1] == b[1]) ? 0 : 1);
			}
			return (a[0] < b[0]) ? -1 : ((a[0] == b[0]) ? 0 : 1);
		}
	}

	public static long[] timer = {0, 0, 0};
	public static int oneDimensionalKingdoms(int n, int[][] kingdoms) {
		long time = System.currentTimeMillis();
		Arrays.sort(kingdoms, arraySort);

		ArrayList<int[]> kings = new ArrayList<>();
		for (int i = 0; i < kingdoms.length; i++) {
			kings.add(kingdoms[i]);
		}

		int min = 0;
		int max = 0;
		for (int i = 0; i < kingdoms.length; i++) {
			min = Math.min(min, kingdoms[i][0]);
			max = Math.max(max, kingdoms[i][1]);
		}

		int[][] line = new int[max - min + 3][2];
		for (int i = 0; i < kingdoms.length; i++) {
			line[kingdoms[i][0]- min][0]++;
			line[kingdoms[i][1]+1- min][1]++;
		}

		timer[0] += System.currentTimeMillis() - time;
		time = System.currentTimeMillis();
		
		int bomb = 0;
		int offset = 0;
		for (int i = 1; i < line.length; i++) {
			if (line[i][1] > line[i-1][1]) {
				bomb++;
				for (int j = offset; j < kings.size(); j++) {
					if (kings.get(j)[0] >= min + i) {
						break;
					}
					//int[] range = kings.remove(j--);
					// JAVA ARRAYLIST REMOVE IS O(N)!!!!!! WTF Joshua Bloch?
					int[] range = kings.get(j);
					offset++;
					line[range[0]- min][0]--;
					line[range[1]+1- min][1]--;
				}
			}
		}
		timer[1] += System.currentTimeMillis() - time;

		return bomb;
	}

	public static String serejaAndVotes(InputStream in) throws IOException {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int[] B = new int[n];
			for (int j = 0; j < n; j++) {
				B[j] = scan.nextInt();
			}
			sb.append(serejaAndVotes(n, B));
		}
		scan.close();
		return sb.toString();
	}


	public static String serejaAndVotes(int n, int[] B) {
		if (n == 1) {
			return B[0] == 100 ?  "YES\n" : "NO\n";
		}
		int unknown = 0;
		int b = 0;
		for (int i = 0; i < B.length; i++) {
			b += B[i];
			if (B[i] > 0) {
				unknown++;
			}
		}
		int low = b - unknown;
		if (low < 100 && b >= 100) {
			return "YES\n";
		} else {
			return "NO\n";
		}
	}

	public static void testGcdQueries2() throws IOException {
		int limit = 4;
		int intLimit = 100000;
		Random rand = new Random(0);
		for (int i = 0; i < limit; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(2);
			sb.append("\n");
			for (int j = 0; j < 2; j++) {
				int n = intLimit;
				int q = intLimit;
				sb.append(n + " " + q + "\n");
				for (int k = 0; k < n; k++) {
					if (k != 0) {
						sb.append(" ");
					}
					sb.append(rand.nextInt(intLimit)+1);
				}
				sb.append("\n");
				for (int k = 0; k < q; k++) {
					//					int a = rand.nextInt(n-1)+1;
					//					int b = a + rand.nextInt(n-a)+1;
					//					sb.append(a + " " + b + "\n");

					int a = intLimit/2;
					int b = intLimit/2;
					sb.append(a + " " + b + "\n");
				}
			}
			long time = System.nanoTime();
			qcdQueries3(new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8)));
			time = (System.nanoTime() - time) / 1000000;
			System.out.println("time: " + time);
		}
	}

	public static void testGcdQueries() throws IOException {
		int limit = 10;
		int intLimit = 5;
		Random rand = new Random(0);
		for (int i = 0; i < limit*100; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(rand.nextInt(limit)+1);
			sb.append("\n");
			for (int j = 0; j < limit; j++) {
				int n = rand.nextInt(limit)+2;
				int q = rand.nextInt(limit)+1;
				sb.append(n + " " + q + "\n");
				for (int k = 0; k < n; k++) {
					if (k != 0) {
						sb.append(" ");
					}
					sb.append(rand.nextInt(intLimit)+1);
				}
				sb.append("\n");
				for (int k = 0; k < q; k++) {
					int a = rand.nextInt(n-1)+1;
					int b = a + rand.nextInt(n-a)+1;
					sb.append(a + " " + b + "\n");
				}
			}
			String res1 = ""; //qcdQueries(new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8)));
			String res2 = qcdQueries3(new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8)));
			if (!res1.equals(res2)) {
				System.out.println("FAIL");
			}
		}

	}

	public static String qcdQueries3(InputStream in) throws IOException {
		MyScanner scan = new MyScanner(in);
		StringBuilder sb = new StringBuilder();

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int q = scan.nextInt();
			long[] a = new long[n];
			int[][] queries = new int[q][2];
			for (int j = 0; j < n; j++) {
				a[j] = scan.nextLong();
			}
			for (int j = 0; j < q; j++) {
				queries[j][0] = scan.nextInt();
				queries[j][1] = scan.nextInt();
			}
			sb.append(qcdQueries(n, q, a, queries));
		}
		scan.close();
		return sb.toString();
	}

	public static String qcdQueries(int n, int q, long[] a, int[][] queries) {
		StringBuilder sb = new StringBuilder();
		SegmentTree st = new SegmentTree(a);
		for (int i = 0; i < queries.length; i++) {
			int[] r1 = {0, queries[i][0]-2};
			long p1 = r1[1] >= r1[0] ? st.get(r1[0], r1[1]) : 0;

			int[] r2 = {queries[i][1], a.length-1};
			long p2 = r2[1] >= r2[0] ? st.get(r2[0], r2[1]) : 0;

			long gcd = gcd(p1, p2);
			sb.append(gcd);
			sb.append("\n");
		}
		return sb.toString();
	}

	public static long gcd(long a, long b) {
		long r;
		while(b != 0) {
			r = a % b;
			a = b;
			b = r;
		}
		return a;
	}

	public static class SegmentTreeSum extends SegmentTree {
		public SegmentTreeSum(long[] a) {
			super(a);
			super.IDENTITY = 0;
		}

		protected int function(int a, int b) {
			return a + b;
		}
	}

	public static class SegmentTree {
		private long[][] t;
		private long[] a;
		private int N;
		private int n;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected long function(long a, long b) {
			//return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).longValue();
			return gcd(a, b);
		}

		protected int IDENTITY = 0;

		public SegmentTree(long[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new long[N];
			for (int i = 0; i < b.length; i++) {
				this.a[i] = b[i];
			}
			t = new long[N][n+1];
			for (int x = 0; x < N; x++) {
				t[x][0] = a[x];
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
				}
			}
		}

		public void set(int x, int v) {
			t[x][0] = a[x] = v;
			for (int y = 1; y <= n; y++) {
				int xx = x-(x&((1<<y)-1));
				t[xx][y] = function(t[xx][y-1], t[xx+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b].
		 */
		public long get(int i, int j) {
			long res = IDENTITY;
			int h = 0; j++;
			while (i+(1<<h) <= j) {
				while ((i&((1<<(h+1))-1)) == 0 && i+(1<<(h+1)) <= j) h++;
				res = function(res, t[i][h]);
				i += (1<<h);
			}
			while (i < j) {
				while (i+(1<<h) > j) h--;
				res = function(res, t[i][h]);
				i += (1<<h);
			}
			return res;
		}
	}

	public static String chefAndStones(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			scan.nextLine();
			String[] line = scan.nextLine().split(" ");
			int[] time = new int[n];
			int[] profit = new int[n];
			for (int j = 0; j < n; j++) {
				time[j] = Integer.parseInt(line[j]);
			}
			line = scan.nextLine().split(" ");
			for (int j = 0; j < n; j++) {
				profit[j] = Integer.parseInt(line[j]);
			}
			sb.append(chefAndStones(n, k, time, profit));
			sb.append("\n");
		}
		scan.close();
		return sb.toString();
	}

	public static long chefAndStones(int n, int k, int[] time, int[] profit) {
		long max = 0;
		for (int i = 0; i < profit.length; i++) {
			long p = (long)(k / time[i]) * profit[i];
			max = Math.max(max, p); 
		}
		return max;
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