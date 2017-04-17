import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Map.Entry;


public class Main {
	public static InputReader in;
	public static PrintWriter out;
	public static boolean DEBUG = false;
	
	public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
//        out = new PrintWriter(outputStream, true);
        out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

//      CHEFDIV6();
//      CHEFDIV5();
//      CHEFDIV4();
        
      CHEFDIV();
      
//      testCHEFDIV2();
//      testCHEFDIV3();
//      testCHEFDIV();
      
//        DGTCNT();
//        testRNDGRID2();
//        testRNDGRID();
//        RNDGRID();
//        testSMARKET2();
//        testSMARKET();
//        SMARKET();
//        CLIQUED2();
//        CLIQUED();
//        ROWSOLD2();
//        DISHLIFE();
//        SIMDISH();
		
		out.close();
	}
	
//	static int[] realNumFactors = new int[10000];
//	static {
//		for (int i = 1; i < realNumFactors.length; i++) {
//			for (int j = 1; j*j <= i; j++) {
//				if (i % j == 0) {
//					realNumFactors[i]++;
//					if (j*j != i) {
//						realNumFactors[i]++;
//					}
//				}
//			}
//		}
//	}
	
	public static void CHEFDIV() {
		long A = in.nextLong();
		long B = in.nextLong();
		if (B <= 10000000) {
			out.println(CHEFDIV7a((int)A, (int)B));
		} else {
			out.println(CHEFDIV7(A, B));	
		}
	}
	
	public static void testCHEFDIV2() {
		long t1 = System.currentTimeMillis();
		int maxValue = 1000000;
		int numTests = 1;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int a = rand.nextInt(maxValue) + 1;
			int b = rand.nextInt(maxValue - a + 1) + a;
			long actual = CHEFDIV7(maxValue-10000, maxValue);
		}
		t1 = System.currentTimeMillis() - t1;
		System.out.println("cacheNumFactors7.size(): " + cacheNumFactors7.size());
		System.out.println("cacheF7.size(): " + cacheF7.size());
		System.out.println("time: " + t1);
	}
	
	public static void testCHEFDIV3() {
//		for (int i = 1; i < 6; i++) {
//			int x= (int) Math.pow(10, i);
//			System.out.println(i + " : \t" + CHEFDIV7(1, x));
//		}
		long t1 = System.currentTimeMillis();
		int maxValue = 100000;
		int numTests = 1;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int a = rand.nextInt(maxValue) + 1;
			int b = rand.nextInt(maxValue - a + 1) + a;
			long expected = CHEFDIV4(a, b);
			long expected2 = CHEFDIV4x(a, b);
			assertEquals(expected, expected2);
		}
		t1 = System.currentTimeMillis() - t1;
		System.out.println("time: " + t1);
	}
	
	public static void testCHEFDIV() {
//		for (int i = 1; i < 6; i++) {
//			int x= (int) Math.pow(10, i);
//			System.out.println(i + " : \t" + CHEFDIV7(1, x));
//		}
		long t1 = System.currentTimeMillis();
		int maxValue = 1000;
		int numTests = 1000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int a = rand.nextInt(maxValue) + 1;
			int b = rand.nextInt(maxValue - a + 1) + a;
			long expected = CHEFDIV4(a, b);
			long expected2 = CHEFDIV4x(a, b);
			assertEquals(expected, expected2);
			long actual = CHEFDIV7(a, b);
			long actual2 = CHEFDIV7a(a, b);
			assertEquals(expected, actual);
			assertEquals(actual, actual2);
		}
		t1 = System.currentTimeMillis() - t1;
		System.out.println("time: " + t1);
	}
	
	static int[] cacheNumFactors7a;
	static int[] cacheF7a;
	
	public static int getNumFactorsA(int i) {
		if (cacheNumFactors7a[i] != -1) {
			return cacheNumFactors7a[i];
		}
		int numFactors = 0;
		for (int j = 1; j*j <= i; j++) {
			if (i % j == 0) {
				numFactors++;
				if (j*j != i) {
					numFactors++;
				}
			}
		}
		cacheNumFactors7a[i] = numFactors;
		return numFactors;
	}
	
	public static int getFA(int i) {
		if (cacheF7a[i] != -1) {
			return cacheF7a[i];
		}
		
		int max = 0;
		int lim = 0;
		for (int j = 2; j*j <= i; j++) {
			if (i % j == 0) {
				max = Math.max(getFA(j), max);
				max = Math.max(getFA(i/j), max);
				lim++;
				if (lim > 1 && getNumFactorsA(i) < 12) {
					break;
				}
			}
		}
		int res = max + getNumFactorsA(i);
		cacheF7a[i] = res;
		return res;
	}
	
	public static long CHEFDIV7a(int A, int B) {
		int limit = B;
		
		cacheNumFactors7a = new int[limit+1];
		Arrays.fill(cacheNumFactors7a, -1);
		cacheNumFactors7a[0] = 0;
		cacheNumFactors7a[1] = 1;
		cacheNumFactors7a[2] = 2;
		
		cacheF7a = new int[limit+1];
		Arrays.fill(cacheF7a, -1);
		cacheF7a[0] = 0;
		cacheF7a[1] = 0;
		cacheF7a[2] = 2;
		
		long sum = 0;
		for (int i = A; i <= B; i++) {
			sum = sum + getFA(i);
		}
		return sum;
	}
	
	static HashMap<Long, Integer> cacheNumFactors7;
	static HashMap<Long, Long> cacheF7;
	static BitSet primes7;
	private static int[] CHEFDIV4F;
	
	public static int getNumFactors(long i) {
		if (cacheNumFactors7.containsKey(i)) {
			return cacheNumFactors7.get(i);
		}
		int numFactors = 1;
		if (primes7 != null && primes7.get((int) i)) {
			numFactors = 2;
		} else {
			if (i > -1) {
				numFactors--;
				for (long j = 1; j*j <= i; j++) {
					if (i % j == 0) {
						numFactors++;
						if (j*j != i) {
							numFactors++;
						}
					}
				}
			} else {
				long r = i;
				for (long j = 2; r > 1; j++) {
					int fc = 0;
					while (r % j == 0) {
						fc++;
						r /= j;
					}
					numFactors *= fc+1;
				}
			}
		}
		cacheNumFactors7.put(i, numFactors);
		return numFactors;
	}
	
	public static long getF(long i) {
		if (cacheF7.containsKey(i)) {
			return cacheF7.get(i);
		}
		
		long max = 0;
		long r = i;
		int lim = 0;
		for (long j = 2; j*j <= r; j++) {
			if (r % j == 0) {
				max = Math.max(getF(j), max);
				max = Math.max(getF(i/j), max);
				
				lim++;
				if (lim > 1 && getNumFactors(i) < 12) {
					break;
				}
//				max = Math.max(Math.min((i/j), j), max);
				r /= j;
			}
		}
		long res = max + getNumFactors(i);
		cacheF7.put(i, res);
		return res;
	}
	
	public static long CHEFDIV7(long A, long B) {
		cacheNumFactors7 = new HashMap<>();
		cacheNumFactors7.put(0L, 0);
		cacheNumFactors7.put(1L, 1);
		cacheNumFactors7.put(2L, 2);
		
		cacheF7 = new HashMap<>();
		cacheF7.put(0L, 0L);
		cacheF7.put(1L, 0L);
		cacheF7.put(2L, 2L);
		
		if (B <= 1000000) {
			primes7 = getPrimes((int) B);
		}
		
		long sum = 0;
		for (long i = A; i <= B; i++) {
			sum = sum + getF(i);
		}
		return sum;
	}
//	
//	public static long CHEFDIV6(int A, int B) {
//		long time = System.currentTimeMillis();
//		
////		int A = (int)in.nextLong();
////		int B = (int)in.nextLong();
////		int A = 932451;
////		int B = 935212;
//		
//		ArrayList<Integer> primes = getPrimesSet((int)(B/2));
//		Collections.reverse(primes);
//		
//		int[] list = new int[primes.size()];
//		long currentNum = 1;
//		long[] numFactors = new long[B+1];
//		HashSet<Integer> numPrimes = new HashSet<>();
//		int numF = 1;
//		Arrays.fill(numFactors, 2);
//		numFactors[0] = 0;
//		numFactors[1] = 1;
//
//		// decompose A
////		long ax = A;
////		for (int j = 0; j < list.length; j++) {
////			while (ax % primes.get(j) == 0) {
////				ax /= primes.get(j);	
////				list[j]++;
////				numPrimes.add(primes.get(j));
////			}
////		}
////		for (int i = 0; i < list.length; i++) {
////			numF *= list[i]+1;
////		}
////		numFactors[A] = numF;
////		currentNum = A;
//		
//		boolean valid = true;
//		HashSet<Integer> found = new HashSet<>();
//		for (int i = 1; i <= numFactors.length; i++) {
//			found.add(i);
//		}
//		
//		int count = 0;
//		long actualNumber = 1;
//		
//		while (valid) {
//			count++;
//			int i = list.length - 1;
//			list[i]++;
//			int prevNf = numF;
//			long prevNum = currentNum;
//
//			currentNum *= primes.get(i);
//			numPrimes.add(primes.get(i));
//			
//			if (numPrimes.size() == 1) {
//				// only factor
//				numF++;
//			} else if (list[i] == 1) {
//				numF = numF * 2;
//			} else{ 
//				numF = numF * (list[i]+1) / list[i];
//			}
//			
//
//			if (currentNum < numFactors.length) {
//				numFactors[(int) currentNum] = numF;
//			}
////			doChecks(currentNum, numFactors, numF, list, primes, found);
//			
//			if (currentNum > B) {
//				boolean carry = true;
//				list[i]--;
//				while (carry) {
//					if (i == 0) {
//						valid = false;
//						break;
//					}
//					numF = prevNf;
//					currentNum = prevNum;
//					
//					carry = false;
//
//					numPrimes.remove(primes.get(i));
//					int k = 0;
//					while (currentNum % primes.get(i) == 0) {
//						list[i]--;
//						k++;
//						currentNum /= primes.get(i);
//					}
//
//					numF = numF / (k+1);
//					
//					--i;
//					list[i]++;
//					currentNum *= primes.get(i);
//					numPrimes.add(primes.get(i));
//					
//					if (numPrimes.size() == 1) {
//						// only factor
//						numF++;
//					} else if (list[i] == 1) {
//						numF = numF * 2;
//					} else{ 
//						numF = numF * (list[i]+1) / list[i];
//					}
//					if (currentNum < numFactors.length) {
//						numFactors[(int) currentNum] = numF;
//					}
//					
//					prevNf = numF;
//					prevNum = currentNum;
//					
//					if (currentNum > B) {
//						carry = true;
////						System.out.println("carry");
//					}
////					doChecks(currentNum, numFactors, numF, list, primes, found);
//				}
//			}
//
//			if (valid) {
//			}
//		}
//		if (DEBUG) {
//			System.out.println(Arrays.toString(numFactors));
//			for (int j = 0; j < numFactors.length; j++) {
//				if (numFactors[j] != realNumFactors[j]) {
//					System.out.println("fail");
//				}
//			}
//		}
//
//		int[] f = new int[B+1];
//		for (int k = 2; k < f.length; k++) {
//			int max = 0;
//			for (int j = 1; j*j <= k; j++) {
//				if (k % j == 0) {
//					max = Math.max(f[j], max);
//					max = Math.max(f[k/j], max);
//				}
//			}
//			f[k] = (int) (max + numFactors[k]);
//		}
//		
//		long sum = 0;
//		for (int j = A; j <= B; j++) {
//			sum = sum + f[j];
//		}
//		return sum;
//	}
//
//	public static void doChecks(long currentNum, long[] numFactors, int numF,
//			int[] list, ArrayList<Integer> primes, HashSet<Integer> found) {
//
////		System.out.println(Arrays.toString(list) + "\t num: " + currentNum + "  \t numF: " + numF);
//		// checks
//		if (currentNum < numFactors.length) {
//			numFactors[(int) currentNum] = numF;
//			if (realNumFactors[(int) currentNum] != numF) {
//				System.out.println("fail nf");
//			}
//		}
//		
//		long actualNumber = 1;
//		for (int j = 0; j < list.length; j++) {
//			for (int k = 0; k < list[j]; k++) {
//				actualNumber *= primes.get(j);
//			}
//		}
//		if (actualNumber != currentNum) {
//			System.out.println("fail actualnum");
//		}
//		if (found.contains((int) (currentNum))) {
//			found.remove((int) (currentNum));
//		}
//
//	}

	public static void CHEFDIV5() {
		long time = System.currentTimeMillis();
		int limit = 100;
		int[] numFactors = new int[limit+1];
		Arrays.fill(numFactors, 2);
		
		for (int i = 2; i <= limit; i++) {
			int r = i;
			for (int j = 2; j*j <= r; j++) {
				int nf = 0;
				int power = 0;
				while (r % j == 0 && j*j <= r) {
					nf += 2;
					if (nf > 2) {
						power+=2;
					}
					r /= j;
				}
				numFactors[i] += nf - power;
//				if (r % j == 0 && j*j <= r) {
//					numFactors[i]++;
//					if (j*j != r) {
//						numFactors[i]++;
//						System.out.println("factors of "+i+" are "+(r/j)+" and "+j);
//					} else {
//						System.out.println("factors of "+i+" are "+j);
//					}
//					r /= j;
//					
//					
//					while (r % j == 0 && j*j <= r) {
//						numFactors[i]++;
//						System.out.println("factors of "+r+" are "+j);
//						r /= j;
//					}
//				}
			}
			System.out.println();
		}

		System.out.println("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20");
		System.out.println("[0, 1, 2, 2, 3, 2, 4, 2, 4, 3, 4, 2, 6, 2, 4, 4, 5, 2, 6, 2, 6, 4, 4, 2, 8, 3, 4, 4, 6, 2, 8, 2, 6, 4, 4, 4, 9, 2, 4, 4, 8, ");
		System.out.println(Arrays.toString(Arrays.copyOf(numFactors, 100)));
		System.out.println("precomp time: " + (System.currentTimeMillis() - time));
	}
	
	public static long CHEFDIV4x(int A, int B) {
		int limit = B;
		int[] numFactors = new int[limit+1];
		
		for (int i = 1; i <= limit; i++) {
			for (int j = 1; j*j <= i; j++) {
				if (i % j == 0) {
					numFactors[i]++;
					if (j*j != i) {
						numFactors[i]++;
					}
				}
			}
		}
		int[] f = new int[B+1];
		for (int i = 2; i < f.length; i++) {
			int max = 0;
			int lim = 0;
			for (int j = 2; j*j <= i; j++) {
				if (i % j == 0) {
					max = Math.max(f[j], max);
					max = Math.max(f[i/j], max);
					if (lim == 1) {
						int a1 = f[j];
						int a2 = f[i/j];
						System.currentTimeMillis();
					}
					
					lim++;
					if (lim > 1 && numFactors[i] < 12) {
						break;
					}
				}
			}
			f[i] = max + numFactors[i];
			if (f[i] != CHEFDIV4F[i]) {
				int x = f[i];
				long y = cacheF7.get((long)i);
				long z = numFactors[i];
				System.out.println("error");
				System.currentTimeMillis();
			}
		}
		long sum = 0;
		for (int i = A; i <= B; i++) {
			sum = sum + f[i];
		}
		return sum;
	}
	
	public static long CHEFDIV4(int A, int B) {
		long time = System.currentTimeMillis();
		int limit = B;
		int[] numFactors = new int[limit+1];
		
		for (int i = 1; i <= limit; i++) {
			for (int j = 1; j*j <= i; j++) {
				if (i % j == 0) {
					numFactors[i]++;
					if (j*j != i) {
						numFactors[i]++;
					}
				}
			}
		}
		if (DEBUG) {
			System.out.println("precomp time: " + (System.currentTimeMillis() - time));
		}
		TreeMap<Integer, Integer> bestMap = new TreeMap<>();
		TreeMap<Integer, Integer> fMap = new TreeMap<>();
		int[] f = new int[B+1];
		for (int i = 2; i < f.length; i++) {
			int max = 0;
			int bestMax = 0;
			int limi = 0;
			for (int j = 2; j*j <= i; j++) {
				if (i % j == 0) {
					if (f[j] > max) {
						bestMax = j;
					}
					max = Math.max(f[j], max);
					if (f[i/j] > max) {
						bestMax = i/j;
					}
					max = Math.max(f[i/j], max);
//					if (numFactors[i] < 12) {
//						break;	
//					}
				}
			}
			f[i] = max + numFactors[i];
//			if (f[i] != cacheF7.get((long)i)) {
//				int x = f[i];
//				long y = cacheF7.get((long)i);
//				long z = numFactors[i];
//				System.currentTimeMillis();
//			}
			if (i < 1000 && bestMax > 0) {
//				System.out.println(i + "\tmax " + max + "\tnumFactors " + numFactors[i] + "\tf " + f[i]+ " ");
//				System.out.println(i + "\tnumFactors " + numFactors[i] + "\tf[best] " + f[bestMax]+ " " + "\t best: " + i/bestMax);
			}
			bestMap.put(bestMax, bestMap.containsKey(bestMax) ? bestMap.get(bestMax) + 1 : 1);
			fMap.put(max, fMap.containsKey(max) ? fMap.get(max) + 1 : 1);
			if (i == B) {
//				System.out.println(i + "\tnumFactors " + numFactors[i] + "\tf[best] " + f[bestMax]+ " " + "\t maxFactor: " + bestMax);
			}
		}
		CHEFDIV4F = f;

		long sum = 0;
		for (int i = A; i <= B; i++) {
			sum = sum + f[i];
		}
		return sum;
	}

	public static void CHEFDIV3() {
		int limit = 1000000;
		int[] largestFactor = new int[limit+1];
		int[] numFactors = new int[limit+1];
		
		for (int i = 1; i <= limit; i++) {
			largestFactor[i] = 1;
			for (int j = 1; j*j <= i; j++) {
				if (i % j == 0) {
					if (j != 1) {
						largestFactor[i] = Math.max(largestFactor[i], i/j);
					}
					numFactors[i]++;
					if (j*j != i) {
						numFactors[i]++;
					}
				}
			}
		}
		
		int A = (int)in.nextLong();
		int B = (int)in.nextLong();
		int[] f = new int[B+1];
		for (int i = 2; i < f.length; i++) {
			int max = 0;
			for (int j = 1; j*j <= i; j++) {
				if (i % j == 0) {
					max = Math.max(f[j], max);
					max = Math.max(f[i/j], max);
				}
			}
			f[i] = max + numFactors[i];
		}

		long sum = 0;
		for (int i = A; i <= B; i++) {
			sum = sum + f[i];
		}
		out.println(sum);
	}

	public static void DGTCNT() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			long a = in.nextLong();
			long b = in.nextLong();
			int[] limit = in.nextIntArray(10);
			HashSet<Integer> set = new HashSet<>();
			for (int i = 0; i < limit.length; i++) {
				set.add(limit[i]);
			}
			if (set.size() == 1 && set.contains(0)) {
				System.out.println(DGTCNTslow2(limit, b) - (DGTCNTslow2(limit, (a-1))));
			} else {
				System.out.println(DGTCNTslow(limit, (int) b) - (DGTCNTslow(limit, (int) (a-1))));
			}
		}
	}
	static long[] DGTCNT_F;
	
	public static long DGTCNTslow2(int[] limit, long a) {
		long min = 1023456789;
		if (DGTCNT_F == null) {
			DGTCNT_F = new long[19];
			long x = 1;
			for (int i = 1; i <= 18; i++) {
				x *= Math.min(i, 10);
				if (i >= 10) {
					DGTCNT_F[i] = x;
				}
			}
		}
		if (a < min) {
			return 0;
		}

		char[] s = (a+"").toCharArray();
		long res = 0;
		int n = s.length;
		for (int i = 0; i < s.length; i++) {
			res += DGTCNT_F[n - i - 1] * (s[i] - '0' - 1); 
		}
		long next = 1;
		for (int i = 0; i < n-1; i++) {
			next *= 10;
		}
		res += DGTCNTslow2(limit, next -1);
		return res;
	}
	public static int DGTCNTslow(int[] limit, int max) {
		int start = 0;
		int numValid = 0;
		while (start <= max) {
			char[] s = (start+"").toCharArray();
			int[] digitCount = new int[10];
			for (int k = 0; k < s.length; k++) {
				digitCount[s[k]-'0']++;
			}
			boolean valid = true;
			for (int k = 0; k < digitCount.length; k++) {
				if (digitCount[k] == limit[k]) {
					valid = false;
				}
			}
			if (valid) {
//				System.out.println(start);
				numValid++;
			}
			start++;
		}
		return numValid;
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
	
	public static BitSet getPrimes(int limit) {
		BitSet notPrimes = new BitSet(limit+1);
		BitSet primes = new BitSet(limit+1);
		
		for (int i = notPrimes.nextClearBit(2); i >= 0 && i <= limit; i = notPrimes.nextClearBit(i+1)) {
			primes.set(i);
			for (int j = 2*i; j <= limit; j+=i) {
				notPrimes.set(j);
			}
		}
		return primes;
	}
	
	public static ArrayList<Integer> getPrimesSet(int limit) {
		ArrayList<Integer> primes = new ArrayList<Integer>((int) (limit/10));
		BitSet p = getPrimes(limit);
		
		for (int i = p.nextSetBit(0); i >= 0 && i <= limit; i = p.nextSetBit(i+1)) {
			primes.add(i);
		}
		return primes;
	}
	
	public static class SegmentTreeSum extends SegmentTree2 {
		protected int IDENTITY = 0;
		
		public SegmentTreeSum(int[] b) {
			super(b);
		}

		protected int function(int a, int b) {
			return a + b;
		}
	}
	

	public static class SegmentTree2 {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 * 
		 * t[width == N][height == n] is essentially a binary tree structure with the root at t[0][n], children at t[0][n-1] and t[N/2][n-1], and so forth. 
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return a + b;
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
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

		/**
		 * Set position i to v. Time: O(log n)
		 */
		public void set(int i, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public int get(int i, int j) {
			int res = IDENTITY, height = 0; j++;
			while (i+(1<<height) <= j) {
				while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			while (i < j) {
				while (i+(1<<height) > j) height--;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			return res;
		}
	}
	
	public static class SegmentTreeMax extends SegmentTree {

		protected int IDENTITY = 0;
		
		public SegmentTreeMax(int[] b) {
			super(b);
		}

		protected int function(int a, int b) {
			return Math.max(a, b);
		}
	}

	public static class SegmentTree {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 * 
		 * t[width == N][height == n] is essentially a binary tree structure with the root at t[0][n], children at t[0][n-1] and t[N/2][n-1], and so forth. 
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return a * b;
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected int IDENTITY = 1;

		public SegmentTree(int[] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new int[N];
			Arrays.fill(a, IDENTITY); // this breaks the tree...
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

		/**
		 * Set position i to v. Time: O(log n)
		 */
		public void set(int i, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public int get(int i, int j) {
			int res = IDENTITY, height = 0; j++;
			while (i+(1<<height) <= j) {
				while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			while (i < j) {
				while (i+(1<<height) > j) height--;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			return res;
		}
	}

	
	public static void testSMARKET2() {
		long t1 = System.currentTimeMillis();
		int maxLength = 10000;
		int maxValue = 1000;
		int numTests = 1000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			int av = 0;
			for (int i = 0; i < len; i++) {
				if (rand.nextBoolean()) {
					av = rand.nextInt(maxValue)+1;
				}
				a[i] = av;
			}

			int[] expected = new int[rand.nextInt(maxLength)+1];
			int[][] q = new int[expected.length][];
			
			int bound = 0;
			for (int i = 0; i < expected.length; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				if (rand.nextBoolean()) {
					bound = rand.nextInt(maxValue)+1;
				}
				q[i] = new int[]{low, high, i, bound};
			}
			SMARKET(a, q);
		}
		t1 = System.currentTimeMillis() - t1;
		System.out.println("time: " + t1);
	}
	
	public static void testSMARKET() {
		long t1 = System.currentTimeMillis();
		int maxLength = 500;
		int maxValue = 2;
		int numTests = 300;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			int av = 0;
			for (int i = 0; i < len; i++) {
				if (rand.nextBoolean()) {
					av = rand.nextInt(maxValue)+1;
				}
				a[i] = av;
			}

			int[] expected = new int[maxLength];
			int[][] q = new int[expected.length][];
			
			int bound = 0;
			for (int i = 0; i < expected.length; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				bound = rand.nextInt(high - low + 1);
				q[i] = new int[]{low, high, i, bound};
				
				int count = 0;
				int seqLen = 1;
				for (int k = low; k <= high; k++) {
					if (k > low) {
						if (a[k] == a[k-1]) {
							seqLen++;
						} else {
							if (seqLen >= bound) {
								count++;
							}
							seqLen = 1;
						}
					}
				}
				if (seqLen >= bound) {
					count++;
				}
				expected[i] = count;
			}

//			System.out.println(Arrays.toString(expected));
			
			int[] actual = SMARKET(a, q);
//			System.out.println(Arrays.toString(actual) + "\n");
			assertArrayEquals(expected, actual);
		}
		t1 = System.currentTimeMillis() - t1;
		System.out.println("time: " + t1);
	}
	
	public static void SMARKET() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int N = in.nextInt();
			int Q = in.nextInt();
			int[] a = in.nextIntArray(N);
			
			int[][] q = new int[Q][];
			
			for (int i = 0; i < Q; i++) {
				int L = in.nextInt() -1;
				int R = in.nextInt() -1;
				int k = in.nextInt();
				q[i] = new int[]{L, R, i, k};	
			}
			
			int[] ret = SMARKET(a, q);
			for (int i = 0; i < ret.length; i++) {
				out.println(ret[i]);
			}
		}
		out.close();
	}
	
	private static int[] SMARKET(int[] a, int[][] q) {
		int N = a.length;
		
		int[] leftBound = new int[N];
		int[] rightBound = new int[N];
		int[] colour = new int[N];
		int[] colourStart = new int[N+1];
		int[] middleCompressedRange = new int[N+1];
		
		int bound = 0;
		for (int i = 1; i < N; i++) {
			if (a[i] != a[i-1]) {
				bound++;
				colourStart[bound] = i;
			}
			colour[i] = bound;
		}
		
		int counter = 1;
		for (int i = 0; i < N; i++) {
			if (i > 0) {
				if (a[i] == a[i-1]) {
					counter++;
				} else {
					counter = 1;
				}
			}
			rightBound[i] = counter;
		}
		
		counter = 1;
		for (int i = N-1; i >= 0; i--) {
			if (i < N-1) {
				if (a[i] == a[i+1]) {
					counter++;
				} else {
					counter = 1;
				}
			}
			leftBound[i] = counter;
		}

		middleCompressedRange[0] = leftBound[0];
		int midCount = 1;
		for (int i = 0; i < N; i++) {
			if (i == colourStart[midCount]) {
				middleCompressedRange[midCount] = leftBound[i];
				midCount++;
			}
		}
		middleCompressedRange = Arrays.copyOf(middleCompressedRange, midCount);
		for (int i = 0; i < middleCompressedRange.length; i++) {
			bound = Math.max(bound, middleCompressedRange[i]);
		}
		return Calc.processQueries(middleCompressedRange, 
				bound+1, colour, colourStart, leftBound, rightBound, q);
	}
	
	public static class Calc {

		public static int[] processQueries(int[] middleCompressedRange, int maxValue,
				int[] colour, int[] colourStart, int[] leftBound,
				int[] rightBound, int[][] q) {
			
			int[] ret = new int[q.length];
			OrthogonalRangeTree st = new OrthogonalRangeTree(middleCompressedRange);
			
			for(int i = 0; i < q.length; i++) {
				int L = q[i][0];
				int R = q[i][1];
				int k = q[i][3];
				int answer = 0;

				if (colour[L] == colour[R]) {
					int range = R - L + 1;
					answer = range >= k ? 1 : 0;
				} else {
					int leftStart = L;
					int midStart = colourStart[colour[leftStart]+1];
					int rightStart = colourStart[colour[R]];
					int rightEnd = R;
					
					answer += leftBound[leftStart] >= k ? 1 : 0;
					answer += rightBound[rightEnd] >= k ? 1 : 0;
					if (midStart != rightStart) {
						// getting interesting...
						int u = colour[midStart];
						int v = colour[rightStart]-1;
						
						// Put the ordering back to the original
						answer += k == 0 ? (v - u + 1) : st.query_tree(u, v, k, maxValue);
					}
				}
				ret[q[i][2]] = answer;
			}
			return ret;
		}
		
	} 
	
	public static class OrthogonalRangeTree {
		protected long tree[];
		private int[][] yTree;
		private int[][] yTreeCumulative;
		private int N;
		private int MAX;
		protected long neg_inf = 0;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 * If one of the inputs are neg_inf the function should ignore them. If both are, it should probably return neg_inf.
		 */
		protected long function(long a, long b) {
			return a + b;
		}

		/**
		 * The input array are y-values of points on a Cartesian system.
		 */
		public OrthogonalRangeTree(int[] a) {
			N = 1 << (int) (Math.log10(a.length)/Math.log10(2))+1;
			MAX = N*2;

			int[] arr = Arrays.copyOf(a, N);
			yTree = new int[MAX][];
			yTreeCumulative = new int[MAX][];
			tree = new long[MAX];
			build_tree(1, 0, N-1, arr);
		}
		
		/**
		 * Build and initialize tree
		 */
		private void build_tree(int node, int a, int b, int[] y) {
			if (a > b) {
				return; // Out of range
			}

			if (a == b) { // Leaf node
//				tree[node] = value[a]; // Initialize value
				yTree[node] = new int[]{y[a]};
				yTreeCumulative[node] = new int[]{0, 1};
				return;
			}

			build_tree(node*2, a, (a+b)/2, y); // Initialize left child
			build_tree(node*2+1, 1+(a+b)/2, b, y); // Initialize right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Initialize root value
			
			// Build the y-tree from the children's arrays
			
			// eliminate duplicates
			TreeMap<Integer, Integer> tm = new TreeMap<>();
			for (int child = 0; child < 2; child++) {
				int[] childTree = yTree[node*2 + child];
				int[] childTreeCumulative = yTreeCumulative[node*2 + child];
				for (int i = 0; i < childTree.length; i++) {
					
					// remember to compensate for the fact that child nodes have been compressed
					for (int j = 0; j < childTreeCumulative[i+1] - childTreeCumulative[i]; j++) {
						tm.put(childTree[i], tm.containsKey(childTree[i]) ? tm.get(childTree[i]) + 1 : 1);
					}
				}
			}

			int[] newTree = new int[tm.size()];
			int[] count = new int[tm.size()];
			int[] cumulative = new int[tm.size()+1];
			int i = 0;
			for (Entry<Integer, Integer> c: tm.entrySet()) {
				newTree[i] = c.getKey();
				count[i] = c.getValue();
				i++;
			}
			for (int j = 1; j < cumulative.length; j++) {
				cumulative[j] = cumulative[j-1] + count[j-1];
			}
			yTree[node] = newTree;
			yTreeCumulative[node] = cumulative;
		}

		/**
		 * Query tree to get max element value within range [i, j]
		 */
		public long query_tree(int x1, int x2, int y1, int y2) {
			return query_tree(1, 0, N-1, x1, x2, y1, y2);
		}

		/**
		 * All bounds are inclusive.
		 * @param node The tree index
		 * @param a Internal query's lower bound
		 * @param b Internal query's upper bound
		 * @param x1 External query's lower bound
		 * @param x2 External query's upper bound
		 */
		protected long query_tree(int node, int a, int b, int x1, int x2, int y1, int y2) {
			if (a > b || a > x2 || b < x1) {
				return neg_inf; // Out of range
			}

			if (a >= x1 && b <= x2) { // Current segment is totally within range [i, j]
				int leftIdx = Arrays.binarySearch(yTree[node], y1);
				if (leftIdx < 0) {
					leftIdx = -1 * (leftIdx+1);
				}
				int rightIdx = Arrays.binarySearch(yTree[node], y2);
				if (rightIdx < 0) {
					rightIdx = -1 * (rightIdx+1);
				} else {
					rightIdx++;
				}
				int numPoints = 0;
				if (yTreeCumulative[node] != null) {
					numPoints = yTreeCumulative[node][rightIdx] - yTreeCumulative[node][leftIdx];
				}
				return numPoints;
			}

			long q1 = query_tree(node*2, a, (a+b)/2, x1, x2, y1, y2); // Query left child
			long q2 = query_tree(1+node*2, 1+(a+b)/2, b, x1, x2, y1, y2); // Query right child

			long res = function(q1, q2); // Return final result
			return res;
		}
		
		public String toString() {
			return Arrays.toString(this.tree);
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


	public static void CLIQUED() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int N = in.nextInt(); // vertices
			int K = in.nextInt();
			int X = in.nextInt();
			int M = in.nextInt();
			int S = in.nextInt() -1;
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				g.add(new ArrayList<Integer>());
				cost.add(new ArrayList<Integer>());
			}
			
			// old
			for (int i = 0; i < K; i++) {
				for (int j = i+1; j < K; j++) {
					int a = i;
					int b = j;
					int c = X;
					g.get(a).add(b);
					g.get(b).add(a);
					cost.get(a).add(c);
					cost.get(b).add(c);
				}
			}
			
			// new
			for (int i = 0; i < M; i++) {
				int a = in.nextInt() -1;
				int b = in.nextInt() -1;
				int c = in.nextInt();
				g.get(a).add(b);
				g.get(b).add(a);
				cost.get(a).add(c);
				cost.get(b).add(c);
			}
			
			PriorityQueue<Node2> q = new PriorityQueue<>();
			HashSet<Integer> visited = new HashSet<>();
			q.add(new Node2(S, 0));
			long[] min = new long[N];
			
			while (!q.isEmpty()) {
				Node2 top = q.poll();
				if (visited.contains(top.x)) {
					continue;
				}
				visited.add(top.x);
				min[top.x] = top.cost;
				
				ArrayList<Integer> neighbours = g.get(top.x);
				ArrayList<Integer> costs = cost.get(top.x);
				for (int i = 0; i < neighbours.size(); i++) {
					q.add(new Node2(neighbours.get(i), top.cost + costs.get(i)));
				}
			}
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < min.length; i++) {
				sb.append(min[i] + " ");
			}
			out.println(sb);
		}
	}

	public static void CLIQUED2() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int N = in.nextInt(); // vertices
			int K = in.nextInt();
			int X = in.nextInt();
			int M = in.nextInt();
			int S = in.nextInt() -1;
			
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				g.add(new ArrayList<Integer>());
				cost.add(new ArrayList<Integer>());
			}
			
			// new
			for (int i = 0; i < M; i++) {
				int a = in.nextInt() -1;
				int b = in.nextInt() -1;
				int c = in.nextInt();
				g.get(a).add(b);
				g.get(b).add(a);
				cost.get(a).add(c);
				cost.get(b).add(c);
			}
			
			PriorityQueue<Node2> q = new PriorityQueue<>();
			q.add(new Node2(S, 0));
			long[] min = new long[N];
			BitSet visited = new BitSet();
			boolean oldNode = false;
			
			while (!q.isEmpty()) {
				Node2 top = q.poll();
				if (visited.get(top.x)) {
					continue;
				}
				visited.set(top.x);
				min[top.x] = top.cost;
				
				ArrayList<Integer> neighbours = g.get(top.x);
				ArrayList<Integer> costs = cost.get(top.x);
				for (int i = 0; i < neighbours.size(); i++) {
					q.add(new Node2(neighbours.get(i), top.cost + costs.get(i)));
				}
				if (!oldNode && top.x < K) {
					oldNode = true;
					for (int i = 0; i < K; i++) {
						if (i != top.x) {
							q.add(new Node2(i, top.cost + X));
						}
					}
				}
			}
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < min.length; i++) {
				sb.append(min[i] + " ");
			}
			out.println(sb);
		}
	}

	public static class Node2 implements Comparable<Node2> {
		public int x;
		public long cost;

		public Node2(int x, long cost) {
			this.x = x;
			this.cost = cost;
		}	

		public String toString() {
			return "(" + x + ": " + cost + ")";
		}

		@Override
		public int compareTo(Node2 o) {
			Node2 next = (Node2)o;
			return Long.compare(this.cost, next.cost);
		}
	}

	public static void ROWSOLD2() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			char[] s = in.next().toCharArray();
			int[] men = new int[s.length];
			int[] gaps = new int[s.length+1];

			for (int i = 0; i < s.length; i++) {
				if (s[i] == '1') {
					men[i]++;
				}
				if (i > 0) {
					men[i] += men[i-1];
				}
			}
			
			int size = 0;
			for (int i = 0; i < s.length; i++) {
				if (i > 0 && men[i] > 0) {
					if (s[i] == '0') {
						size++;
					} else {
						gaps[i] = size;
						size = 0;
					}
				}
			}
			if (s[s.length-1] == '0') {
				gaps[s.length] = size;
			}
			long moves = 0;
			for (int i = 1; i < gaps.length; i++) {
				if (gaps[i] > 0) {
					moves += ((long)(gaps[i]+1)) * men[i-1];
				}
			}
			out.println(moves);
		}
	}

	public static void DISHLIFE() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			ArrayList<int[]> a = new ArrayList<>();
			int N = in.nextInt();
			int K = in.nextInt();
			for (int i = 0; i < N; i++) {
				a.add(in.nextIntArray(in.nextInt()));
			}
			int[] count = new int[K+1];
			for (int i = 0; i < a.size(); i++) {
				int[] island = a.get(i);
				for (int j = 0; j < island.length; j++) {
					count[island[j]]++;
				}
			}
			
			int min = Integer.MAX_VALUE;
			for (int i = 1; i < count.length; i++) {
				min = Math.min(min, count[i]);
			}
			
			if (min == 0) {
				out.println("sad");
			} else {
				boolean some = false;
				for (int i = 0; i < a.size(); i++) {
					int[] island = a.get(i);
					
					min = Integer.MAX_VALUE;
					for (int j = 0; j < island.length; j++) {
						min = Math.min(min, count[island[j]]);
					}
					
					if (min >= 2) {
						some = true;
						break;
					}
				}
				if (some) {
					out.println("some");
				} else {
					out.println("all");
				}
			}
		}
	}

	public static void SIMDISH() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			HashSet<String> a = new HashSet<>();
			HashSet<String> b = new HashSet<>();
			for (int i = 0; i < 4; i++) {
				a.add(in.next());
			}
			for (int i = 0; i < 4; i++) {
				b.add(in.next());
			}
			a.retainAll(b);
			if (a.size() >= 2) {
				out.println("similar");
			} else {
				out.println("dissimilar");
			}
		}
	}

	public static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {return Integer.parseInt(next());}
        public long nextLong() {return Long.parseLong(next());}
        public double nextDouble() {return Double.parseDouble(next());}
        public long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) a[i] = this.nextLong();
			return a;
		}
        public int[] nextIntArray(int n) {
        	int[] a = new int[n];
        	for (int i = 0; i < a.length; i++) a[i] = this.nextInt();
			return a;
		}
    }
}
