import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
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
import java.util.Set;
import java.util.Stack;

import dataStructures.Pair;

public class VariousAlgorithms {

	public static int clearBit(int value, int idx) {
		return (value & ~(1 << idx));
	}
	
	public static int setBit(int value, int idx) {
		return (value | (1 << idx));
	}
	
	public static boolean getBit(int value, int idx) {
		return (value & (1 << idx)) != 0;
	}

	public static long clearBitL(long value, int idx) {
		return (value & ~(1L << idx));
	}
	
	public static long setBitL(long value, int idx) {
		return (value | (1L << idx));
	}
	
	public static boolean getBitL(long value, int idx) {
		return (value & (1L << idx)) != 0;
	}

	/*******************************************************************************************************************************
	 * Disjoint-set data structure, also called a union–find data structure or merge–find set, is a data structure that keeps track
	 * of a set of elements partitioned into a number of disjoint (non-overlapping) subsets. It supports two useful operations:
	 * 
	 *  - Find: Determine which subset a particular element is in. Find typically returns an item from this set that serves as its
	 *    "representative"; by comparing the result of two Find operations, one can determine whether two elements are in the same
	 *    subset.
	 *  - Union: Join two subsets into a single subset.
	 *  
	 *  All operations are constant, by utilising path compression plus rank heuristics.
	 */
	public static class DisjointSet {
		int[] parent;
		int[] rank;
		int[] size;
		int maxSize = 0;
		
		public DisjointSet(int size) {
			parent = new int[size];
			rank = new int[size];
			this.size = new int[size];
			
			// This is not needed, it just clarifies the state to external observers.
			Arrays.fill(parent, -1);  
			Arrays.fill(rank, -1);
		}
		
		public void make_set(int v) {
			parent[v] = v;
			rank[v] = 0;
			size[v] = 1;
			maxSize = Math.max(maxSize, 1); 
		}
		 
		public int find_set(int v) {
			if (v == parent[v]) {
				return v;
			}	
			return parent[v] = find_set (parent[v]);
		}
		 
		public void union_sets(int a, int b) {
			a = find_set(a);
			b = find_set(b);
			if (a != b) {
				if (rank[a] < rank[b]) {
					int temp = a;
					a = b;
					b = temp;
				}
				size[a] += size[b];
				size[b] = 0;
				maxSize = Math.max(maxSize, size[a]); 
				
				parent[b] = a;
				if (rank[a] == rank[b]) {
					++rank[a];
				}	
			}
		}
	}

	/*******************************************************************************************************************************
	 * Maximum slice problem: Find the maximum sum of a subsequence of integers. 
	 * 
	 * For example, if the sequence is {5, -7, 3, 5, -2, 4, -1}
	 * 
	 * Speed: Linear time! O(n)
	 * Memory: O(1)
	 */	
	public static int maximumSlice(int[] S) {
		int maxEnding = 0;
		int maxSlice = 0;
		
		for (int i = 0; i < S.length; i++) {
			maxEnding = Math.max(0, maxEnding + S[i]);
			maxSlice = Math.max(maxSlice, maxEnding);
		}
		
		return maxSlice;
	}
	
	/*******************************************************************************************************************************
	 * Binary Search.
	 * 
	 * If the input function or array (f(x) or a[x]) is monotonic, find the point where f(x) or a[x] = target
	 * 
	 * Speed: Logarithmic time! O(log n). At most 32 iterations.
	 * Memory: O(1)
	 * 
	 * Think this is easy? Java's binary search contained a bug for 6 years.
	 */	
	public static int binarySearch(int[] a, int target) {
		int low = 0;
		int high = a.length-1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			if (a[mid] < target) {
				low = mid + 1;
			} else if (a[mid] > target) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
	
	/*******************************************************************************************************************************
	 * Binary Search with repeated values
	 */	
	public static int binarySearchWithDuplicates(int[] a, int target) {
		int low = 0;
		int high = a.length-1;
		int lastValid = -1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			if (a[mid] < target) {
				low = mid + 1;
			} else if (a[mid] > target) {
				high = mid - 1;
			} else {
				low = mid + 1;
				lastValid = mid;
			}
		}
		return lastValid;
	}
	
	/*******************************************************************************************************************************
	 * Ternary Search.
	 * 
	 * Find the min/max if the input function or (f(x)) has only one local minimum or maximum. Minimum in this case.
	 * x will be accurate to 1e-500000.
	 * 
	 */	
	public static double ternarySearch() {
		double low = 0.0;
		double high = 1e100;
		double best = low;
		for (int i = 0; i < 1000000; i++) {
			double[] x = {low + (high-low)/3, high - (high-low)/3};
			double[] y = {0,0};
			for (int j = 0; j < y.length; j++) {
				//y[j] = f(x[i]);
			}
			best = (y[0]+y[1])/2;
			if (y[0] > y[1]) {
				low = x[0];
			} else {
				high = x[1];
			}
		}
		return best;
	}
	
	/*******************************************************************************************************************************
	 * Discrete Ternary Search.
	 * 
	 * If the minimum/maximum is flat, the function returns the first index.
	 */
	public static int ternarySearch(int[] f) {
		int low = 0;
		int high = f.length-1;
	    int mid = (low + high) >> 1;
		while(low < high) {
		    mid = (low + high) >> 1;
		    if(f[mid] > f[mid+1]) {
		    	low = mid+1;
		    } else {
		    	high = mid;
		    }
		}
		return low;
	}
	
	/*******************************************************************************************************************************
	 * Knapsack DP sub-problem: in how many ways can a list of values sum up to a given number?
	 * 
	 * For example: f([1, 2, 3, 4, 5]) = 
	 * [1, 1, 1, 2, 2, 3, 3, 3, 3, 3, 3, 2, 2, 1, 1, 1]
	 * 
	 * Or the binomial coefficients: f([1, 1, 1, 1, 1, 1]) =
	 * [1, 6, 15, 20, 15, 6, 1] 
	 */
	public static int[] waysToSum(int[] values) {
		//Arrays.sort(values);
		int max = 0;
		for (int i = 0; i < values.length; i++) {
			max += values[i];
		}
		int[] ways = new int[max+1];
		ways[0] = 1;
		for (int i = 0; i < values.length; i++) {
			for (int sum = max; sum >= values[i]; sum--) {
				ways[sum] += ways[sum - values[i]];
			}
		}
		return ways;
	}
	
	public static HashMap<Long, Long> waysToSumLong(int[] values) {
		//Arrays.sort(values);
		HashMap<Long, Long> ways = new HashMap<>();
		ways.put(0L, 1L);
		for (int i = 0; i < values.length; i++) {
			HashMap<Long, Long> newWays = new HashMap<>(ways); // shallow copy
			for (long key: ways.keySet()) {
				long newSum = key + values[i];
				newWays.put(newSum, ways.get(key) + (ways.containsKey(newSum) ? ways.get(newSum) : 0));
			}
			ways = newWays;
		}
		return ways;
	}

	/*******************************************************************************************************************************
	 * A permutation of a set of objects is an arrangement of those objects into a particular order.
	 * For example, there are six permutations of the set {1,2,3},
	 * namely (1,2,3), (1,3,2), (2,1,3), (2,3,1), (3,1,2), and (3,2,1).
	 * One might define an anagram of a word as a permutation of its letters.
	 * k of elements taken from a given set of size n. 
	 */		
	public static BigInteger permutation(int n, int k) {
		BigInteger ret;
		if (k > n) {
			return BigInteger.ZERO;
		}
		BigInteger d = factorial((BigInteger.valueOf(n).subtract(BigInteger.valueOf(k))).intValue());
        ret = factorial(n).divide(d);
        return ret;
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

	/**
	 * Number of permutations WITH repetition:  n^r
	 */
	public static long nPrWithRep(int n, int r) {
		long ret = 1;
		for (long i = 0; i < r; i++) {
			ret *= n;
		}
		return ret;
	}
	
	
	/*******************************************************************************************************************************
	 * Ordering ignored. A poker hand can be described as a 5-combination (k = 5) of cards from a 52 card deck (n = 52).
	 * There are 2,598,960 such combinations. Combinations just look at selected against not selected.
	 */
	public static BigInteger combination(int n, int k) {
        return permutation(n,k).divide(factorial(k));
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

	/**
	 * Number of combinations WITH repetition:  (n + r - 1)! / (r! * (n - 1)!)
	 */
	public static long nCrWithRep(int n, int r) {
		return nCr(n + r - 1, r);
	}
	
	/*******************************************************************************************************************************
	 * Calculate the factorial or n! 
	 * fCacheSize is used to speed up successive calls, but not required.
	 */
	private static long fCacheSize = 1000000; // Used to speed up calls, but answers will be correct without it.
	private static HashMap<Integer,BigInteger> fCache = new HashMap<Integer,BigInteger>();	
	private static BigInteger factorial(int n)
    {
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
	
	/*******************************************************************************************************************************
	 * Iteration of a set/array: Permutations
	 * Rearranges the elements into the lexicographically next greater permutation of elements. 
	 */	
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
	
	public static boolean next_combination(int[] idx, int n) {
		int k = idx.length;
		boolean changed = false;
		int cIdx = 0;
		for (int i = k-1; i >= 0; i--) {
			if ((i == k-1 && idx[i] < n) || (i < k-1 && idx[i] < idx[i+1]-1)) {
				idx[i]++;
				changed = true;
				cIdx = i;
				break;
			}
		}
		if (!changed) {
			return false;
		}
		for (int i = cIdx+1; i < k; i++) {
			idx[i] = idx[i-1] + 1;
		}
		if (idx[k-1] > n) {
			return false;
		}
		return true;
	}
	
	public static boolean next_combination_with_rep(int[] list, int base) {
		int i = list.length - 1;
		list[i]++;
		int carryEndIdx = -1;
		int carryEnd = -1;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;

				carryEndIdx = i;
				carryEnd = list[carryEndIdx];
				
				if (list[i] == base) {
					carry = true;
				}
			}
			
			for (int j = carryEndIdx; j < list.length; j++) {
				list[j] = carryEnd;
			}
		}
		
		return true;
	}
	
	/*******************************************************************************************************************************
	 * Get the nth permutation (0-indexed). Length of str is limited to 20 because 21! > 2^63
	 * @param <T>
	 */
	public static <T> void nth_permutation(ArrayList<T> str, long n) {
		long a = 1L;
		for (int i = 2; i < str.size(); i++) {
			a *= i;
		}
		for (int i = 0; i < str.size()-1; i++) {
			int idx = (int) (n / a);
			n %= a;
			a /= str.size() - i - 1; 
			str.add(i, str.remove(i+idx));
		}
	}

	/*******************************************************************************************************************************
	 * Iteration of a set/array: All combinations of a set.
	 * Check every possible combination of list elements.
	 *  
	 * Speed: O((2^n)*n), or a list of 23 elements in 1 second.
	 */
	public void all_combinations(int list[]) {
		int N = 1 << list.length;
		for (int n = 0; n < N; n++) {
			@SuppressWarnings("unused")
			int sum = 0;
			for (int i = 0; i < list.length; i++) {
				if (((1 << i) & n) != 0) {//if ((n >> i) % 2 == 1) { // Or equally: (((1 << j) & i) > 0)
					sum += list[i];
				}
			}
		}
	}
	public void all_combinations2(int list[]) {
		int len = list.length;
		int N = 1 << len;
		for (int n = 0; n < N; n++) {
			boolean[] active = new boolean[len];
			for (int i = 0; i < len; i++) {
				if (((1 << i) & n) != 0) {
					active[i] = true;
				}
			}
		}
	}

	/*******************************************************************************************************************************
	 * Iteration of a set/array: k combinations of a set of n, in other words: counting in base n.
	 * A.K.A. permutations with repetition
	 * This method counts from right to left to match integer counting.
	 *  
	 * Speed: O((n^k)*n), or a list of 9 elements in base 9 in 0.3 seconds.
	 */
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
	
	/*******************************************************************************************************************************
	 * Very fast prime function. Gets all primes up to and equal to limit.
	 * 
	 * Speed: About 1 second to run getPrimes(71M)
	 * 
	 * Memory use is O(n), speed is O(n), both better than a normal Sieve of Eratosthenes.
	 *  
	 * Explanation of the speed: There is a nested loop but the number of times the loop
	 * runs is limited. The loop will run n/2 times, then n/3, then n/5, then n/7 and so on for every
	 * prime <= n. The series does not converge but it grows very slowly: this series is a subset of the 
	 * harmonic series (1, 1/2, 1/3, ... 1/n) that sums to 23 for n < 2^32. Therefore the sum of this
	 * subset will be smaller than 23*n, and therefore the time complexity is O(n). 
	 */
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
	
	public static HashSet<Integer> getPrimesSet(int limit) {
		HashSet<Integer> primes = new HashSet<Integer>((int) (limit/10));
		BitSet p = getPrimes(limit);
		
		for (int i = p.nextSetBit(0); i >= 0 && i <= limit; i = p.nextSetBit(i+1)) {
			primes.add(i);
		}
		return primes;
	}
	
	/**
	 * Plain old Sieve of Eratosthenes.
	 */
	public static boolean[] getPrimesSlow(int limit) {
		boolean[] primes=new boolean[limit]; 

		Arrays.fill(primes,true);
		primes[0] = false; 
		primes[1] = false;
		for (int i=2;i<primes.length;i++) {
			for(int x = 2;2*x<i;x++) {
				if(i%x==0) {
					primes[i] = false;
				}
			}

			if(primes[i]) {
				for (int j=2; i*j<primes.length; j++) {
					primes[i*j] = false;
				}
			}
		}
		return primes;
	}
	
	public static ArrayList<Long> getPrimeFactors(long n) {
		ArrayList<Long> factors = new ArrayList<>();
		long d = 2;
		while (n > 1) {
			while (n % d == 0) {
				factors.add(d);
				n /= d;
			}
			d++;
			if (d*d > n) {
				if (n > 1) {
					factors.add(n);
					break;
				}
			}
		}
		return factors;
	}
	
	/*******************************************************************************************************************************
	 * Greatest common divisor (gcd), also known as the greatest common factor (gcf), or highest common factor (hcf),
	 * of two or more non-zero integers, is the largest positive integer that divides the numbers without a remainder.
	 * 
	 * Very fast, about 0.2 us for gcd(10^9, 10^9); 
	 */
	public static int gcd(int a, int b) {
	    return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
	}

	/**
	 * About twice as fast as the BigInteger method. 
	 */
	public static long gcd(long a, long b) {
		long r;
		while (b != 0) {
			r = a % b;
			a = b;
			b = r;
		}
		return a;
	}

	/*******************************************************************************************************************************
	 * The least common multiple (also called the lowest common multiple or smallest common multiple) of two integers a and b,
	 * is the smallest positive integer that is a multiple of both a and b. Remember that gcm(a, b) * lcm(a, b) = a*b
	 */
	public static int lcm(int a, int b)
	{
		return BigInteger.valueOf(b).multiply(BigInteger.valueOf(a)).divide(BigInteger.valueOf(a).gcd(BigInteger.valueOf(b))).intValue();
	}
	
	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}
	
	/*******************************************************************************************************************************
	 * Euler’s totient function.
	 * Gives the number of positive integers smaller or equal to and relatively prime with n.
	 * For example f(9) = 6 (1, 2, 4, 5, 7, 8)
	 * 
	 * Speed: about 20 us for f(10^9)
	 * 
	 * Mathematical description: f(n) = {a Î N: 1 <= a <= n, gcd(a, n) = 1}
	 * Function properties:
	 * 		If p is prime, then f(p) = p – 1
	 *		If m and n are coprime, then f(m * n) = f(m) * f(n).
	 */
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
	
	/*******************************************************************************************************************************
	 * Modular "division" = modular multiplicative inverse
	 * 
	 * To get a/b % m take the the modular multiplicative inverse of b and then multiply with a.
	 * 
	 * Speed: about 20 us for f(10^9)
	 */
	public static int modularMultiplicativeInverse(int b, int mod) {
		return fastModularExponent(b, eulerTotientFunction(mod) - 1, mod) % mod;
	}
	
	/*******************************************************************************************************************************
	 * Goldbach's conjecture is one of the oldest and best-known unsolved problems in number theory and in all of mathematics:
	 * 
	 * Every even integer greater than 2 can be expressed as the sum of two primes.
	 * 
	 * The conjecture has been shown to hold up through 4 × 10^18 and is generally assumed to be true, but remains unproven.
	 */
	public boolean GoldbachConjectureIsSumOfTwoPrimes(int n) {
		if (n % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*******************************************************************************************************************************
	 * Calculate the right side of Pascal's triangle (a.k.a. binomial coefficients). With zeros representing the "gaps" 
	 * Complexity: O(n^2)
	 */
	public int[] PascalsTriangle(int n) {
		int lim = n;
		int[] ptr = new int[lim+2];
		ptr[0] = 1;
		for (int i = 1; i <= lim; i++) {
			int[] newPtr = new int[ptr.length];
			for (int j = i%2; j <= i; j += 2) {
				if (j == 0 ){
					newPtr[j] = ptr[j+1]*2;
				} else {
					newPtr[j] = ptr[j+1] + ptr[j-1];
				}
			}
			ptr = newPtr;
		}
		return ptr;
	}
	
	/*******************************************************************************************************************************
	 * Calculate a full row of Pascal's triangle (a.k.a. binomial coefficients). 
	 * Complexity: O(n)
	 * The long type will overflow at n = 63
	 */
	public static long[] PascalsTriangleFast(long n) {
		long[] ptr = new long[(int) n+1];
		ptr[0] = 1;
		for (int i = 1; i < ptr.length; i++) {
			ptr[i] = ptr[i-1] * (n + 1 - i) / i;
		}
		return ptr;
	}
	
	/*******************************************************************************************************************************
	 * Very fast way to calculate a modular exponent. Cannot overflow!
	 */
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
	
	public static BigInteger fastModularExponent(BigInteger a, BigInteger exp, BigInteger mod) {
		ArrayList<BigInteger> results = new ArrayList<>();
		BigInteger m = mod;
		int power = 1;
		BigInteger res = BigInteger.ONE;
		BigInteger two = BigInteger.valueOf(2);
		results.add(BigInteger.ZERO);
		while (!exp.equals(BigInteger.ZERO)) {
			if (power == 1) {
				results.add(a.mod(m));
			} else {
				results.add(results.get(power-1).multiply(results.get(power-1)).mod(m));
			}
			if (exp.mod(two).equals(BigInteger.ONE)) {
				res = (res.multiply(results.get(power))).mod(m);
			}
			exp = exp.divide(two);
			power++;
		}
		return res.mod(m);
	}

	/*******************************************************************************************************************************
	 * Returns a remainder that doesn't change direction when the sign of n changes. For example:
	 * negMod(2, 3) = 2
	 * negMod(1, 3) = 1
	 * negMod(0, 3) = 0
	 * negMod(-1, 3) = -2
	 * negMod(-2, 3) = -1
	 * 
	 * Nice for working on an circular array. 
	 */
	public static int negMod(int n, int mod) {
		int a = Math.abs(n);
		return (((a / mod + 1) * mod) + n) % mod;
	}

	/*******************************************************************************************************************************
	 * Knuth Morris Pratt substring matching algorithm: returns a list of each starting position of a substring match.
	 * Complexity: O(n + k)
	 */
	public static ArrayList<Integer> kmp(char[] s, char[] k) {
		int[] pmt = new int[k.length + 1]; // Partial Match Table
		Arrays.fill(pmt, -1);
		ArrayList<Integer> matches = new ArrayList<>();
		
		if (k.length == 0) {
			matches.add(0);
			return matches;
		}
		
		// Build the Partial Match Table
		for (int i = 1; i <= k.length; i++) {
			int pos = pmt[i - 1];
			while (pos != -1 && k[pos] != k[i - 1]) {
				pos = pmt[pos];
			}
			pmt[i] = pos + 1;
		}
		
		int sp = 0;
		int kp = 0;
		while (sp < s.length) {
			while (kp != -1 && (kp == k.length || k[kp] != s[sp])) {
				kp = pmt[kp];
			}
			kp++;
			sp++;
			if (kp == k.length) {
				matches.add(sp - k.length);
			}
		}
		return matches;
	}

	public static ArrayList<Integer> kmp(String s, String k) {
		return kmp(s.toCharArray(), k.toCharArray());
	}

	/*******************************************************************************************************************************
	 * Longest common subsequence (LCS), a classic dynamic programming problem. O(n^2)
	 * 
	 */	
	public static String longestCommonSubsequence(char[] a, char[] b) {
		int[][] dp = new int[a.length + 1][b.length + 1];
		int max = 0;
		int[] best = {0, 0};
		
		for (int i = 1; i < dp.length; i++) {
			for (int j = 1; j < dp[0].length; j++) {
				if (a[i-1] == b[j-1]) {
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
		int i = a.length;
		int j = b.length;
		ArrayList<Character> lcs = new ArrayList<>();
		while (i >= 1 && j >= 1) {
			if (a[i-1] == b[j-1]) {
				lcs.add(a[i-1]);
				i--;
				j--;
			} else {
				if (i == 0 && j > 0) {
					j--;
				} else if (j == 0 && i > 0) {
					i--;
				} else if (i > 0 && j > 0) {
					if (dp[i-1][j] > dp[i][j-1]) {
						i--;
					} else {
						j--;
					}
				} else {
					break;
				}
			}
		}
		
		Collections.reverse(lcs);
		StringBuilder sb  = new StringBuilder();
		for (char x: lcs) {
			sb.append(x);
		}
		return sb.toString();
	}
	
	/*******************************************************************************************************************************
	 * Simple matrix multiplication. O(n^3)
	 */	
	public static int[][] multiplyMatrix(int[][] A, int[][] B, int mod) {
		int[][] C = new int[A.length][B[0].length];
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[0].length; j++) {
				for (int k = 0; k < B.length; k++) {
					C[i][j] = (int) ((C[i][j] + ((long)A[i][k] * B[k][j])) % mod); 
				}
			}
		}
		return C;
	}
	
	/*******************************************************************************************************************************
	 * Fast matrix exponentiation. O(m^2 * log n), where m = A.length
	 */
	public static int[][] fastMatrixExponent(int[][] A, long n, int mod) {
		if (n == 1) {
			return A;
		} else {
			if (n % 2 == 0) {
				int[][] T = fastMatrixExponent(A, n/2, mod);
				return multiplyMatrix(T, T, mod);
			} else {
				return multiplyMatrix(A, fastMatrixExponent(A, n-1, mod), mod);
			}
		}
	}
	

	/*******************************************************************************************************************************
	 * Geometry: Convex hull with the Graham scan. O(n * log(n))
	 * Based on http://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/
	 */

	public static class Point {
		long x;
		long y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static class GrahamScan {
		public static Point p0;

		public static Point nextToTop(Stack<Point> S) {
			Point p = S.peek();
			S.pop();
			Point res = S.peek();
			S.push(p);
			return res;
		}

		public static long distSq(Point p1, Point p2) {
			return (p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y);
		}

		public static int orientation(Point p, Point q, Point r) {
			long val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

			if (val == 0) return 0;  // co-linear
			return (val > 0)? 1: 2;  // clock or counter clock wise
		}

		// Returns list of hull points in the list hull. 
		public GrahamScan(Point[] points, ArrayList<Point> hull) {
			// Find the bottommost point
			int n = points.length;
			long ymin = points[0].y;
			int min = 0;
			for (int i = 1; i < n; i++) {
				long y = points[i].y;
				
				if ((y < ymin) || (ymin == y && points[i].x < points[min].x)) {
					ymin = points[i].y;
					min = i;
				}
			}

			// Place the bottom-most point at first position
			Point temp = points[0];
			points[0] = points[min];
			points[min] = temp;

			p0 = points[0];
			Arrays.sort(points, new Comparator<Point>() {
				@Override
				public int compare(Point p1, Point p2) {
					int o = orientation(p0, p1, p2);
					if (o == 0) {
						return (distSq(p0, p2) >= distSq(p0, p1))? -1 : 1;
					} else {
						return (o == 2)? -1: 1;
					}
				}
			});

			int m = 1; // Initialize size of modified array
			for (int i = 1; i < n; i++) {
				// Keep removing i while angle of i and i+1 is same
				// with respect to p0
				while (i < n-1 && orientation(p0, points[i], points[i+1]) == 0) {
					i++;
				}
				points[m] = points[i];
				m++;  // Update size of modified array
			}

			if (m < 3) {
				return;
			}

			Stack<Point> S = new Stack<>();
			S.push(points[0]);
			S.push(points[1]);
			S.push(points[2]);

			// Process remaining n-3 points
			for (int i = 3; i < m; i++) {
				while (orientation(nextToTop(S), S.peek(), points[i]) != 2) {
					S.pop();
				}	
				S.push(points[i]);
			}
			
			// Could just return the stack here.
			while (!S.empty()) {
				Point p = S.peek();
				hull.add(p);
				S.pop();
			}
		}
	}
	
	/*******************************************************************************************************************************
	 * Geometry: Calculate the area of any polygon in O(n).
	 * Positive overlaps are counted twice while negative overlaps are subtracted. (Green's Theorem)
	 * Based on http://alienryderflex.com/polygon_area/
	 */
	
	public static double polyArea(ArrayList<Point> list) {
		double area = 0;
		for (int j = 0; j < list.size(); j++) {
			long x1 = list.get(j).x;
			long x2 = list.get((j+1) % list.size()).x;
			long y1 = list.get(j).y;
			long y2 = list.get((j+1) % list.size()).y;
			area += x1*y2-x2*y1;
		}
		return 0.5 * Math.abs(area);
	}
	
	/*******************************************************************************************************************************
	 * Geometry: check if 3 points are collinear
	 */
	public static boolean collinear(long x1, long y1, long x2, long y2, long x3, long y3) {
		return (x2-x1) * (y3-y1) == (x3 - x1) * (y2 - y1);
	} 

	/*******************************************************************************************************************************
	 * Just a HashMap wrapper to make counting easier
	 */
	
	@SuppressWarnings("serial")
	public static class Counter<T> extends HashMap<T, Integer> {
		public void add(T key) {
			Integer i = this.get(key);
			this.put(key, i == null ? 1 : i + 1);
		}
		public void add(T key, int count) {
			Integer i = this.get(key);
			this.put(key, i == null ? count : i + count);
		}
	}

	/*******************************************************************************************************************************
	 * Based on Python's DefaultDict: for normal defaults
	 */

	@SuppressWarnings("serial")
	public static class DefaultDict<K, V> extends HashMap<K, V> {
		private V defaultValue;  
	    public DefaultDict(V defaultValue) {
	        this.defaultValue = defaultValue;    
	    }
	
	    @SuppressWarnings("unchecked")
		@Override
	    public V get(Object key) {
	        V returnValue = super.get(key);
	        if (returnValue == null) {
	            try {
	                returnValue = this.defaultValue;
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	            this.put((K) key, returnValue);
	        }
	        return returnValue;
	    }    
	}

	/*******************************************************************************************************************************
	 * Based on Python's DefaultDict: for default objects
	 */

	@SuppressWarnings("serial")
	public static class DefaultDict2<K, V> extends HashMap<K, V> {
	    Class<V> klass;
	    public DefaultDict2(Class<V> klass) {
	        this.klass = klass;    
	    }
	
	    @SuppressWarnings("unchecked")
		@Override
	    public V get(Object key) {
	        V returnValue = super.get(key);
	        if (returnValue == null) {
	            try {
	                returnValue = klass.newInstance();
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	            this.put((K) key, returnValue);
	        }
	        return returnValue;
	    }    
	}
	
	/*******************************************************************************************************************************
	 * Shuffle an array
	 */
	public static void shuffle(int[] a, Random rand) {
        for (int i = a.length; i > 1; i--) {
        	int r = rand.nextInt(i);
        	int temp = a[i-1];
        	a[i-1] = a[r];
        	a[r] = temp;
        }
	}
	
	/*******************************************************************************************************************************
	 * Simple and fast Pair class
	 */
	
	public static class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A, B>> {
		public A a;
		public B b;
		public Pair(A a, B b) {
			this.a = a;
			this.b = b;
		}
		public int hashCode() {
			return a.hashCode() * 13 + b.hashCode();
		}
		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<A, B> op = (Pair<A, B>) other;
				return ((this.a == op.a || this.a.equals(op.a)) && (this.b == op.b || this.b.equals(op.b)));
			}
			return false;
		}
		public String toString() { 
			return "(" + a + ", " + b + ")"; 
		}
		@Override
		public int compareTo(Pair<A, B> o) {
			int compareFirst = this.a.compareTo(o.a);
			return compareFirst != 0 ? compareFirst : this.b.compareTo(o.b);
		}
	}
	
	/*******************************************************************************************************************************/
	
	
	static int ops = 1;
	public static void main(String[] args) {
		
		int TEST_PRIMES = 1;
		int TEST_BPM = 2;
		int TEST_ALL_COMBINATIONS = 3;
		int TEST_MAXIMUM_SLICE = 4;
		int TEST_BINARY_SEARCH = 5;
		int TEST_NEXT_NUMBER = 6;
		int TEST_PRIMES_2 = 7;
		int TEST_NEXT_PER_COM = 8;
		
		int testType = TEST_NEXT_PER_COM;
		
		long start = System.currentTimeMillis();
		if (testType == TEST_PRIMES) {
			long n = 2;
			long t = System.currentTimeMillis();
			for (int i = 0; i < 20; i++) {
				t = System.currentTimeMillis();
				long bound = 23 * n;
				getPrimes((int) n);
				System.out.printf("n: %-10d ops: %-10d bound: %-10d bound extra: %-15f CPU cycles per op: %-10d\n",
						n, ops, bound, (bound/(double)ops), (System.currentTimeMillis() - t) * 3000000L / ops);
				n *= 2;
				ops = 0;
			}
			
			n = 20000;
			t = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				getPrimes((int) n);
			}
			System.out.println("getPrimes(" + n + "): " + (System.currentTimeMillis() - t)/(float)1000 + " ms");
			
			t = System.currentTimeMillis();
			for (int i = 0; i < 10; i++) {
				getPrimesSlow((int) n);
			}
			System.out.println("getPrimesSlow(" + n + "): " + (System.currentTimeMillis() - t)/(float)10 + " ms");
		} else if (testType == TEST_PRIMES_2) {
			ArrayList<Long> times = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				long n = 71000000;
				long t = System.currentTimeMillis();
				getPrimes((int) n);
				times.add((System.currentTimeMillis() - t));
				System.out.println("getPrimes("+n+") \t" + times.get(times.size()-1));
			}
			System.out.println("Fastest time: " + Collections.min(times));
		} else if (testType == TEST_ALL_COMBINATIONS) {
			for (int i = 0; i < 5; i++) {			
				long time = System.currentTimeMillis();
				int[] list = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
				(new VariousAlgorithms()).all_combinations(list);
				System.out.println("Total time: " + (System.currentTimeMillis() - time));
			}		
		} else if (testType == TEST_MAXIMUM_SLICE) {
			int[] S = {5, -7, 3, 5, -2, 4, -1};
			System.out.println(maximumSlice(S));
		} else if (testType == TEST_BINARY_SEARCH) {
			int[] a = new int[(Integer.MAX_VALUE-2)];
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			System.out.println("Started search");
			System.out.println("search: " + binarySearch(a, a[a.length-1]) + "\t real index: " + (a.length-1));
		} else if (testType == TEST_NEXT_NUMBER) {
			int[] a = new int[9];
			char[] c = {'A', 'B', 'C'};
			
			//System.out.println("" + c[a[0]] + c[a[1]] + c[a[2]] + c[a[3]] + c[a[4]]);
			while (next_number(a, 9)) {
				//System.out.println("" + c[a[0]] + c[a[1]] + c[a[2]] + c[a[3]] + c[a[4]]);
			}
		} else if (testType == TEST_NEXT_PER_COM) {
			int[] a = {1,2,3,4};
			int k = 0;
			System.out.println("Permutations");
			do {
				System.out.println(Arrays.toString(a));
				k++;
			} while (next_permutation(a));
			System.out.println("Num Permutations: " + k + ", nPr(4, 4): " + nPr(4, 4));
			System.out.println();
			
			a = new int[]{0,1,2};
			k = 0;
			System.out.println("Combinations");
			do {
				System.out.println(Arrays.toString(a));
				k++;
			} while (next_combination(a, 4));
			System.out.println("Num Combinations: " + k + ", nCr(5, 3): " + nCr(5, 3));
			System.out.println();
			
			a = new int[]{0,0,0};
			k = 0;
			System.out.println("Combinations with repetition");
			do {
				System.out.println(Arrays.toString(a));
				k++;
			} while (next_combination_with_rep(a, 4));
			System.out.println("Num Combinations: " + k + ", nCrWithRep(4, 3): " + nCrWithRep(4, 3));
			
		}
		System.out.println("total time: " + (System.currentTimeMillis() - start) + " ms");
		
	}
}
