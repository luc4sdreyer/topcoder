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

public class SolutionAll {
	public static void main(String[] args) {
//		flippingBits(System.in);
//		utopianTree(System.in);
//		maximizingXOR(System.in);
//		alternatingCharacters(System.in);
//		theLoveLetterMystery(System.in);
//		angryChildren(System.in);
//		gameOfThrones(System.in);
//		acmIcpcTeam(System.in);
//		fillingJars(System.in);
//		chocolateFeast(System.in);
//		sherlockAndSquares(System.in);
//		sherlockAndTheBeast(System.in);
//		findDigits(System.in);
//		isFibo(System.in);
//		sherlockAndPairs(System.in);
//		sherlockAndWatson(System.in);
//		iceCreamParlor(System.in);
//		almostSorted(System.in); 			//TODO
//		circleCity(System.in);
//		mrKmarsh(System.in); 				//TODO
//		encryption(System.in);
//		missingNumbers(System.in);
//		coinOnTheTable(System.in);		//TODO
//		theGridSearch2(System.in);
//		pairs(System.in);
//		theMaximumSubarray(System.in);
//		knapsack(System.in);
//		theLongestCommonSubsequence(System.in);
//		fibonacciModified(System.in);
//		pangrams(System.in);
//		candies(System.in);
//		palindromeIndex(System.in);
//		anagram(System.in);
//		quickSort1(System.in);
//		restaurant(System.in);
//		commonChild(System.in);
//		gemStones(System.in);
//		biggerIsGreater(System.in);
//		twoArrays(System.in);
//		markAndToys(System.in);
//		stockMaximize(System.in);
//		pRngSequenceGuessing(System.in);
//		keywordTranspositionCipher(System.in);
//		sherlockAndCost(System.in);
//		playGame2(System.in);					// TODO
//		redJohnIsBack(System.in);
	}
	
	public static void redJohnIsBack(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		int[] ans = {0, 0, 0, 0, 1, 2, 2, 3, 4, 4, 6, 8, 9, 11, 15, 19, 24, 32, 42, 53, 68, 91, 119, 155, 
				204, 269, 354, 462, 615, 816, 1077, 1432, 1912, 2543, 3385, 4522, 6048, 8078, 10794, 14475, 19385};
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
//			int[] dp = new int[n+1];
//			int[] x = new int[n+1];
//			for (int i = 1; i < dp.length; i++) {
//				if (i <= 3) {
//					dp[i] = 1;
//				} else if (i <= 4) {
//					dp[i] = 2;
//				} else {
//					dp[i] = dp[i-1] + dp[i-4];
//				}
//				x[i] = getPrimes(dp[i]).size();
//			}
			System.out.println(ans[n]);
		}
	}

	public static LinkedHashSet<Integer> getPrimes(long limit) {
		LinkedHashSet<Integer> primes = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> notPrimes = new LinkedHashSet<Integer>();
		boolean prime = true;
		for (int i = 2; i <= limit; i++) {
			prime = true;
			if (notPrimes.contains(i)) {
				prime = false;
			}
			if (prime) {
				primes.add(i);
				for (int j = 2; j*i <= limit; j++) {
					notPrimes.add(j*i);
				}
			}
		}
		return primes;
	}
	
	public static void playGame2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int[][] f = new int[n][2];
			int[] total = new int[n];
			for (int i = 0; i < f.length; i++) {
				total[i] = (i > 0 ? total[i-1] : 0) + a[i];
			}
			System.out.println(Math.max(bestPlay(n-1, 0, total, a), bestPlay(n-1, 1, total, a)));
		}
	}
	
	public static long bestPlay(int i, int player, int[] total, int[] a) {
		if (i < 0) {
			return 0;
		}
		long best = 0;
		long sum = 0;
		for (int j = 1; j <= 3 && i-j+1 >= 0; j++) {
			sum += a[i-j+1];
			best = Math.max(best, sum + (i-j >= 0 ? total[i-j] : 0) - bestPlay(i - j, (player + 1) % 2, total, a));
		}
		if (i < 3 && player == 0) {
			best = Math.max(best, total[i]);
		}
		return best;
	}

	public static void playGame(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int[][] f = new int[n][2];
			int[] total = new int[n];
			for (int i = 0; i < f.length; i++) {
				total[i] = (i > 0 ? total[i-1] : 0) + a[i];
			}
			for (int i = 0; i < 3; i++) {
				f[i][0] = total[i];
			}
			for (int i = 3; i < f.length; i++) {
				int sum = 0;
				for (int k = 0; k < 3; k++) {
					sum += a[i-k];
					f[i][0] = Math.max(f[i][0], total[i-(k+1)] - f[i-(k+1)][0] + sum);
//					for (int j = 0; j < 2; j++) {
//						if (j == 0) {
//							sum += a[i-k];
//						}
//						f[i][j] = Math.max(f[i][j], total[i-(k+1)] - f[i-(k+1)][(j + 1) % 2] + sum);	
//					}
				}
			}
			for (int i = 0; i < total.length; i++) {
				System.out.println(Arrays.toString(f[i]));
			}
			System.out.println(f[n-1][0]);
		}
	}
	
	public static void sherlockAndCost(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] b = scan.nextIntArray(n);
			int[] best = {0, 0};
			for (int i = 1; i < b.length; i++) {
				int[] nextBest = new int[2];
				nextBest[0] = Math.max(best[0], Math.abs(1 - b[i-1]) + best[1]);
				nextBest[1] = Math.max(Math.abs(b[i] - b[i-1]) + best[1], Math.abs(b[i] - 1) + best[0]);
				best = nextBest;
				//System.out.println(Arrays.toString(nextBest));
			}
			System.out.println(Math.max(best[0], best[1]));
		}
	}
	
	public static void keywordTranspositionCipher(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			char[] keyword = (scan.nextLine()).toCharArray();
			char[] ciphertext = scan.nextLine().toCharArray();
			boolean[] used = new boolean[26];
			char[] filtered = new char[26];
			String decoded = "";
			int idx = 0;
			char[] alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			ArrayList<ArrayList<Character>> block = new ArrayList<>();
			
			for (int i = 0; i < keyword.length; i++) {
				if (!used[keyword[i] - 'A']) {
					used[keyword[i] - 'A'] = true;
					filtered[idx++] = keyword[i];
				}
			}
			for (int i = 0; i < idx; i++) {
				ArrayList<Character> column = new ArrayList<>();
				column.add(filtered[i]);
				block.add(column);
			}
			idx = block.size();
			for (int i = 0; i < alpha.length; i++) {
				if (!used[alpha[i] - 'A']) {
					used[alpha[i] - 'A'] = true;
					block.get(idx % block.size()).add(alpha[i]);
					idx++;
				}
			}
			
			Collections.sort(block, new Comparator<ArrayList<Character>>(){
				@Override
				public int compare(ArrayList<Character> o1,	ArrayList<Character> o2) {
					return Character.compare(o1.get(0), o2.get(0));
				}
			});
			idx = 0;
			char[] substi = new char[26];
			for (int i = 0; i < block.size(); i++) {
				for (int j = 0; j < block.get(i).size(); j++) {
					substi[idx++] = block.get(i).get(j);
				}
			}
			
			for (int i = 0; i < ciphertext.length; i++) {
				if (ciphertext[i] == ' ') {
					decoded += " ";
				}
				for (int j = 0; j < filtered.length; j++) {
					if (substi[j] == ciphertext[i]) {
						decoded += (char)('A' + j);
					}
				}
			}
			System.out.println(decoded);
		}
	}
	
	public static void pRngSequenceGuessing(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			long t1 = scan.nextLong();
			long t2 = scan.nextLong();
			int[] a = scan.nextIntArray(10);
			Random rand = new Random();
			for (long time = t1; time <= t2; time++) {
				rand.setSeed(time);
				boolean valid = true;
				for (int i = 0; i < 10; i++) {
					if (rand.nextInt(1000) != a[i]) {
						valid = false;
						break;
					}
				}
				if (valid) {
					System.out.print(time + " ");
					for (int i = 0; i < 10; i++) {
						System.out.print(rand.nextInt(1000) + " ");
					}
					System.out.println();
				}
			}
		}
	}
	
	public static void stockMaximize(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int[] max = new int[n];
			for (int i = n-1; i >= 0; i--) {
				max[i] = Math.max(i < n-1 ? max[i+1] : 0, a[i]);
			}
			long tp = 0;
			for (int i = 0; i < max.length-1; i++) {
				if (a[i] < max[i+1]) {
					tp += max[i+1] - a[i];
				}
			}
			System.out.println(tp);
		}
	}
	
	public static void markAndToys(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		Arrays.sort(a);
		int sum = 0;
		int count = 0;
		for (int i = 0; i < a.length && sum + a[i] <= k; i++) {
			count++;
			sum += a[i];
		}
		System.out.println(count);
	}
	
	public static void twoArrays(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int[] b = scan.nextIntArray(n);
			Arrays.sort(a);
			Arrays.sort(b);
			boolean valid = true;
			for (int j = 0; j < b.length; j++) {
				if (a[j] + b[b.length -j -1] < k) {
					valid = false;
				}
			}
			if (valid) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}
	
	public static void biggerIsGreater(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[] s = scan.nextLine().toCharArray();
			if (next_permutation(s)) {
				System.out.println(new String(s));
			} else {
				System.out.println("no answer");
			}
		}
	}

	public static boolean next_permutation(char str[]) {
		char temp;
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
	
	public static void gemStones(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		HashSet<Character> all = null;
		for (int i = 0; i < n; i++) {
			char[] line = scan.nextLine().toCharArray();
			HashSet<Character> next = new HashSet<>();
			for (int j = 0; j < line.length; j++) {
				next.add(line[j]);
			}
			if (all == null) {
				all = next;
			} else {
				all.retainAll(next);
			}
		}
		System.out.println(all.size());
	}
	
	public static void commonChild(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		char[] a = scan.nextLine().toCharArray();
		char[] b = scan.nextLine().toCharArray();
		int[][] dp = new int[a.length][b.length];
		int max = 0;
		
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				if (i == 0 || j == 0) {
					if (a[i] == b[j]) {
						dp[i][j]++;
					}
				} else {
					if (a[i] == b[j]) {
						dp[i][j] = dp[i-1][j-1] + 1;
						if (dp[i][j] > max) {
							max = dp[i][j];
						}
					} else {
						dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
					}
				}
			}
		}
		System.out.println(max);
	}
	
	public static void restaurant(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			int r = gcd(a, b);
			System.out.println(a*b / (r*r));
		}
	}

	public static void quickSort1(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		ArrayList<Integer> left = new ArrayList<>();
		ArrayList<Integer> right = new ArrayList<>();
		int p = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] < p) {
				left.add(a[i]);
			} else {
				right.add(a[i]);
			}
		}
		left.add(p);
		left.addAll(right);
		for (int i: left) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public static void anagram(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[] s = scan.nextLine().toCharArray();
			if (s.length % 2 != 0) {
				System.out.println(-1);
			} else {
				ArrayList<Character> left = new ArrayList<>();
				ArrayList<Character> right = new ArrayList<>();
				for (int j = 0; j < s.length; j++) {
					if (j < s.length/2) {
						left.add(s[j]);
					} else {
						right.add(s[j]);
					}
				}
				for (int j = 0; j < left.size(); j++) {
					boolean contains = false;
					for (int k = 0; k < right.size(); k++) {
						if (right.get(k) == left.get(j)) {
							contains = true;
							right.remove(k);
							break;
						}
					}
					if (contains) {
						left.remove(j--);
					}
				}
				System.out.println(left.size());
			}
		}
	}
	
	public static void palindromeIndex(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[] str = scan.nextLine().toCharArray();
			int idx = -1;
			for (int j = 0; j < str.length/2; j++) {
				if (str[j] != str[str.length -j -1]) {
					boolean isPal = true;
					for (int k = j; k < str.length/2; k++) {
						if (str[k+1] != str[str.length -k -1]) {
							isPal = false;
						}
					}
					if (isPal) {
						idx = j;
					} else {
						idx = str.length -j -1;
					}
					break;
				}
			}
			System.out.println(idx);
		}
	}
	
	public static void candies(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int[] candy = new int[n];
		for (int i = 1; i < a.length; i++) {
			if (a[i] > a[i-1]) {
				candy[i-1] = 1;
				while (i < a.length && a[i] > a[i-1]) {
					candy[i] = candy[i-1] + 1;
					i++;
				}
			}
		}
		for (int i = a.length-2; i >= 0; i--) {
			if (a[i] > a[i+1]) {
				candy[i+1] = 1;
				while (i >= 0 && a[i] > a[i+1]) {
					candy[i] = Math.max(candy[i], candy[i+1] + 1);
					i--;
				}
			}
		}
		int cost = 0;
		for (int i = 0; i < candy.length; i++) {
			if (candy[i] == 0) {
				cost++;
			} else {
				cost += candy[i];
			}
		}
		//System.out.println(Arrays.toString(a));
		//System.out.println(Arrays.toString(candy));
		System.out.println(cost);
	}
	
	public static void pangrams(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		String s = scan.nextLine().toLowerCase().replace(" ", "");
		int[] freq = new int[26];
		for (int i = 0; i < s.length(); i++) {
			freq[s.charAt(i) - 'a']++;
		}
		boolean valid = true;
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] == 0) {
				valid = false;
			}
		}
		System.out.println(valid ? "pangram" : "not pangram");
	}
	
	public static void fibonacciModified(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		BigInteger b = BigInteger.valueOf(scan.nextInt());
		BigInteger a = BigInteger.valueOf(scan.nextInt());
		int n = scan.nextInt();
		for (int i = 0; i < n-2; i++) {
			BigInteger temp = a;
			a = a.multiply(a).add(b);
			//System.out.println(a + " = " + temp + " * " + temp + " + " + b);
			b = temp;
		}
		String ret = a.toString();
		//System.out.println("size: " + ret.length());
		System.out.println(ret);
	}
	
	public static void theLongestCommonSubsequence(InputStream in) {		
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int[] b = scan.nextIntArray(m);
		int[][] dp = new int[a.length][b.length];
		int max = 0;
		int[] best = {0, 0};
		
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				if (i == 0 || j == 0) {
					if (a[i] == b[j]) {
						dp[i][j]++;
					}
				} else {
					if (a[i] == b[j]) {
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
		}
		int i = a.length-1;
		int j = b.length-1;
		ArrayList<Integer> lcs = new ArrayList<>();
		while (i >= 0 && j >= 0) {
			if (a[i] == b[j]) {
				lcs.add(a[i]);
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
		for (int x: lcs) {
			sb.append(x + " ");
		}
		System.out.println(sb);
	}
	
	public static void knapsack(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			boolean[] dp = new boolean[k+1];
			for (int j = 0; j < a.length; j++) {
				if (a[j] < dp.length) {
					dp[a[j]] = true;
				}
			}
			int max = 0;
			for (int j = 1; j < dp.length; j++) {
				if (dp[j]) {
					max = j;
					for (int m = 0; m < a.length; m++) {
						if (j + a[m] < dp.length) {
							dp[j + a[m]] = true;
						}
					}
				}
			}
			System.out.println(max);
		}
	}
	
	public static void theMaximumSubarray(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			
			int maxSum = 0;
			int maxPart = 0;
			int any = 0;
			int max = Integer.MIN_VALUE;
			for (int j = 0; j < a.length; j++) {
				maxSum = Math.max(0, maxSum + a[j]);
				maxPart = Math.max(maxPart, maxSum);
				if (a[j] > 0) {
					any += a[j];
				}
				max = Math.max(max, a[j]);
			}
			if (maxPart == 0) {
				maxPart = max;
			}
			if (any == 0) {
				any = max;
			}
			System.out.println(maxPart + " " + any);
		}
	}
	
	public static void pairs(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		long k = scan.nextInt();
		long[] a = scan.nextLongArray(n);
		HashSet<Long> set = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			set.add(a[i]);
		}
		int num = 0;
		for (int i = 0; i < a.length; i++) {
			if (set.contains(k + a[i])) {
				num++;
			}
		}
		System.out.println(num);
	}
	
	public static void theGridSearch2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[][] ref = new char[scan.nextInt()][scan.nextInt()];
			for (int j = 0; j < ref.length; j++) {
				ref[j] = scan.nextLine().toCharArray();
			}
			char[][] pattern = new char[scan.nextInt()][scan.nextInt()];
			for (int j = 0; j < pattern.length; j++) {
				pattern[j] = scan.nextLine().toCharArray();
			}

			int[][] pmt = new int[pattern.length][pattern[0].length+1];
			for (int j = 0; j < pmt.length; j++) {
				Arrays.fill(pmt[j], -1);
				char[] k = pattern[j];
				
				// Build the Partial Match Table
				for (int m = 1; m < k.length; m++) {
					int pos = pmt[j][m - 1];
					while (pos != -1 && k[pos] != k[m - 1]) {
						pos = pmt[j][pos];
					}
					pmt[j][m] = pos + 1;
				}
			}
//			ArrayList<ArrayList<TreeSet<Integer>>> matches = new ArrayList<>();
//			for (int j = 0; j < ref.length; j++) {
//				ArrayList<TreeSet<Integer>> list = new ArrayList<>();
//				for (int k = 0; k < pattern.length; k++) {
//					list.add(kmp(ref[j], pattern[k], pmt[k]));
//				}
//				matches.add(list);
//			}
			int[][] maxLength = new int[ref.length][pattern.length];
			TreeSet<Integer> res = kmp(ref[0], pattern[0], pmt[0]);
			if (!res.isEmpty()) {
				maxLength[0][0] = 1;
			}
			boolean valid = false;
			for (int j = 1; j < maxLength.length; j++) {
				for (int k = 0; k < maxLength[0].length; k++) {
					if (k == 0) {
						res = kmp(ref[j], pattern[0], pmt[0]);
						if (!res.isEmpty()) {
							maxLength[j][0] = 1;
						}
					} else {
						if (maxLength[j-1][k-1] == 1) {
							res = kmp(ref[j], pattern[k], pmt[k]);
							if (!res.isEmpty()) {
								maxLength[j][k] = 1;
							}
						}
					}
				}
				if (maxLength[j][maxLength[0].length-1] == 1) {
					valid = true;
					break;
				}
			}
			System.out.println(valid ? "YES" : "NO");
		}
	}
	
	public static void theGridSearch(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[][] ref = new char[scan.nextInt()][scan.nextInt()];
			for (int j = 0; j < ref.length; j++) {
				ref[j] = scan.nextLine().toCharArray();
			}
			char[][] pattern = new char[scan.nextInt()][scan.nextInt()];
			for (int j = 0; j < pattern.length; j++) {
				pattern[j] = scan.nextLine().toCharArray();
			}

			int[][] pmt = new int[pattern.length][pattern[0].length+1];
			for (int j = 0; j < pmt.length; j++) {
				Arrays.fill(pmt[j], -1);
				char[] k = pattern[j];
				
				// Build the Partial Match Table
				for (int m = 1; m < k.length; m++) {
					int pos = pmt[j][m - 1];
					while (pos != -1 && k[pos] != k[m - 1]) {
						pos = pmt[j][pos];
					}
					pmt[j][m] = pos + 1;
				}
			}
			ArrayList<ArrayList<TreeSet<Integer>>> matches = new ArrayList<>();
			for (int j = 0; j < ref.length; j++) {
				ArrayList<TreeSet<Integer>> list = new ArrayList<>();
				for (int k = 0; k < pattern.length; k++) {
					list.add(kmp(ref[j], pattern[k], pmt[k]));
				}
				matches.add(list);
			}
			int[][] maxLength = new int[ref.length][pattern.length];
			if (!matches.get(0).get(0).isEmpty()) {
				maxLength[0][0] = 1;
			}
			boolean valid = false;
			for (int j = 1; j < maxLength.length; j++) {
				for (int k = 0; k < maxLength[0].length; k++) {
					if (k == 0) {
						if (!matches.get(j).get(0).isEmpty()) {
							maxLength[j][0] = 1;
						}
					} else {
						if (maxLength[j-1][k-1] == 1 && !matches.get(j).get(k).isEmpty()) {
							maxLength[j][k] = 1;
						}
					}
				}
				if (maxLength[j][maxLength[0].length-1] == 1) {
					valid = true;
					break;
				}
			}
			System.out.println(valid ? "YES" : "NO");
		}
	}

	public static TreeSet<Integer> kmp(char[] s, char[] k, int[] pmt) {
		TreeSet<Integer> matches = new TreeSet<>();
		
		if (k.length == 0) {
			matches.add(0);
			return matches;
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
	
	public static void missingNumbers(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int m = scan.nextInt();
		int[] b = scan.nextIntArray(m);
		int[][] freq = new int[2][10001];
		for (int i = 0; i < a.length; i++) {
			freq[0][a[i]]++;
		}
		for (int i = 0; i < b.length; i++) {
			freq[1][b[i]]++;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < freq[0].length; i++) {
			if (freq[0][i] != freq[1][i]) {
				sb.append(i + " ");
			}
		}
		System.out.println(sb);
	}
	
	public static void encryption(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] s = scan.nextLine().toCharArray();
		
		int maxArea = 100000;
		int[] best = {0,0};		
		for (int h = (int) Math.floor(Math.sqrt(s.length)); h <= (int) Math.ceil(Math.sqrt(s.length)); h++) {
			for (int w = h; w <= (int) Math.ceil(Math.sqrt(s.length)); w++) {
				if (w*h < maxArea && w*h >= s.length) {
					maxArea = w*h;
					best[0] = h;
					best[1] = w;
				}
			}
		}
		
		char[][] out = new char[best[0]][best[1]];
		for (int i = 0; i < out.length; i++) {
			Arrays.fill(out[i], ' ');
		}
		for (int i = 0; i < s.length; i++) {
			int offset = i % out[0].length;
			out[i / out[0].length][i % out[0].length] = s[i];
		}
		
		String st = "";
		for (int x = 0; x < out[0].length; x++) {
			for (int y = 0; y < out.length; y++) {
				st += out[y][x];
			}
			if (st.charAt(st.length()-1) != ' ') {
				st += " ";
			}
		}
		//System.out.println(st.length() + " " +st);
		System.out.println(st);
	}
	
	public static void mrKmarsh(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int[][] map = new int[n][m];
		for (int i = 0; i < n; i++) {
			char[] row = scan.nextLine().toCharArray();
			for (int j = 0; j < m; j++) {
				map[i][j] = row[j] == 'x' ? 0 : 1; 
			}
		}
		
		SegmentTree[] st = new SegmentTree[n];
		for (int i = 0; i < st.length; i++) {
			st[i] = new SegmentTree(map[i]);
		}
		
		int max = 0;
		for (int i = 0; i < n; i++) {
			int start = 0;
			int end = start;
			int prevEnd = 0;
			boolean first = true;
			while (true) {
				prevEnd = end;
				if (first) {
					first = false;
				} else {
					start = end;
				}
				while (start < m-1 && map[i][start] == 0) {
					start++;
				}
				end = start;
				while (end < m && map[i][end] == 1) {
					end++;
				}
				int up = i;
				while (up > 0 && st[up].get(start, end-1) == end - start) {
					up--;
				}
				int down = i;
				while (down < n && st[down].get(start, end-1) == end - start) {
					down++;
				}
				if (end - start >= 2 && down - up >= 2) {
					int perim = (end - start)*2 + (down - up - 1)*2;
					max = Math.max(max, perim);
					//System.out.println(i +" " + perim);
				}
				if (end == prevEnd) {
					break;
				}
			}
		}
		if (max == 0) {
			System.out.println("impossible");
		} else {
			System.out.println(max);
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
			return a + b;
		}

		protected int IDENTITY = 0;

		public SegmentTree(int[] b) {
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

		/**
		 * Find the point x where the function over [0, x] == v, assuming the function (not the underlying array!) is nondecreasing. Time: O(log n)
		 */
		public int search(int v) {
			int x = 0;
			int value = v;
			if (value > t[0][n]) {
				return -(search(t[0][n]) + 1) -1;
			}
			int height = n-1;
			while (height >= 0 && x >= 0 && x < N) {
				int left = t[x][height];
				int right = left + (x + (1 << (height)) < N ? t[x + (1 << (height))][height] : 0);
				if (value <= left) {
					// go left
					if (height == 0) {
						value -= left;
					}
				} else {
					// go right
					if (height == 0) {
						value -= right;
					} else {
						value -= left;
					}
					x += (1 << (height));
				} 
				height--;
			}
			if (value == 0) {
				return x;
			} else {
				return -1 * x - 1;
			}
		}

		/**
		 * Find the first point b where the function over [a, b] == v, assuming the function (not the underlying array!) is nondecreasing. Time: O(log n)^2
		 */
		public int search2(int a, int v) {
			int left = a;
			int right = N-1;
			int mid = 0;
			int f = 0;
			int best = -1;
			if (v > t[0][n]) {
				return -(search2(0, t[0][n]) + 1) -1;
			}
			boolean found = false;
			while (left <= right) {
				mid = (left + right) / 2;
				f = get(a, mid);
				if (f == v) {
					found = true;
					best = mid;
					right = mid-1;
				} else if (f < v) {
					left = mid+1;
				} else {
					if (best+1 <= mid) {
						if (get(best+1, mid) != 0) {
							best = mid;
						}
					} else {
						best = mid;
					}
					right = mid-1;
				}
			}
			if (found) {
				return best;
			} else {
				return -best - 1;
			}
		}
	}
	
	public static void circleCity(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		int[][] q = new int[t][2];
		TreeSet<Integer> set = new TreeSet<>();
		int max = 0;
		for (int j = 0; j < t; j++) {
			q[j][0] = scan.nextInt();
			q[j][1] = scan.nextInt();
			set.add(q[j][0]);
			max = Math.max(max, q[j][0]);
		}
		//max = (int) (Math.sqrt(max) + 1);
		long time = System.currentTimeMillis();
		
		TreeSet<Integer> pSquares = new TreeSet<>();
		for (int i = 1; i*i <= max; i++) {
			pSquares.add(i*i);
		}
		
		for (int i = 0; i < q.length; i++) {
			final int limit = q[i][0];
			int points = 0;
			for (int j = 0; j*j <= limit; j++) {
				if (pSquares.contains(limit - j*j)) {
					//System.out.println(j*j + " " + (limit - j*j));
					points++;
				}
			}
			points = points * 4;
			//System.out.println(points);
			if (q[i][1] >= points) {
				System.out.println("possible");
			} else {
				System.out.println("impossible");
			}
		}
		//System.out.println("time: " + (System.currentTimeMillis() - time));
	}
	
	public static void circleCity2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		int[][] q = new int[t][2];
		TreeSet<Integer> set = new TreeSet<>();
		int max = 0;
		for (int j = 0; j < t; j++) {
			q[j][0] = scan.nextInt();
			q[j][1] = scan.nextInt();
			set.add(q[j][0]);
			max = Math.max(max, q[j][0]);
		}
		max = (int) (Math.sqrt(max) + 1);

		long time = System.currentTimeMillis();
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = 1; i <= max; i++) {
			int b = i*i; 
			for (int j = i; j <= max; j++) {
				int c = b + j*j;
				if (set.contains(c)) {
					if (i != j) {
						map.put(c, map.containsKey(c) ? map.get(c) + 2 : 2);
					} else {
						map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
					}
				}
			}
		}
		for (int i = 0; i < q.length; i++) {
			int points = map.containsKey(q[i][0]) ? (map.get(q[i][0]) * 4 + 4) : 4;
			if (q[i][1] >= points) {
				System.out.println("possible");
			} else {
				System.out.println("impossible");
			}
		}
		//System.out.println("time: " + (System.currentTimeMillis() - time));
	}
	
	public static void iceCreamParlor(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int j = 0; j < t; j++) {
			int m = scan.nextInt();
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			
			TreeMap<Integer, TreeSet<Integer>> map = new TreeMap<>();
			for (int i = 0; i < a.length; i++) {
				if (!map.containsKey(a[i])) {
					map.put(a[i], new TreeSet<Integer>());
				}
				map.get(a[i]).add(i);
			}
			
			for (int i = a.length-1; i >= 0; i--) {
				if (map.containsKey(m - a[i])) {
					TreeSet<Integer> s = map.get(m - a[i]);
					if (!s.contains(i) || s.size() > 1) {
						System.out.println((map.get(m - a[i]).first()+1) + " " + (i+1));
						break;
					}
				}
			}
		}
	}
	
	public static void lonelyInteger(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], map.containsKey(a[i]) ? map.get(a[i]) + 1 : 1);
		}
		for (int k: map.keySet()) {
			if (map.get(k) == 1) {
				System.out.println(k);
				break;
			}
		}
	}
	
	public static void almostSorted(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		
		int numOutOfOrder = 0;
		for (int i = 1; i < a.length; i++) {
			if (a[i] < a[i-1]) {
				numOutOfOrder++;
			}
		}
		if (numOutOfOrder == 0) {
			System.out.println("yes");
		} else if (numOutOfOrder == 1) {
			
		}
	}
	
	public static void sherlockAndWatson(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int qu = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int[] q = scan.nextIntArray(qu);
		for (int i = 0; i < q.length; i++) {
			System.out.println(a[negMod((q[i] - k), n)]);
		}
	}
	
	public static int negMod(int n, int mod) {
		int a = Math.abs(n);
		return (((a / mod + 1) * mod) + n) % mod;
	}
	
	public static void sherlockAndPairs(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			HashMap<Integer, Integer> map = new HashMap<>();
			for (int j = 0; j < a.length; j++) {
				map.put(a[j], map.containsKey(a[j]) ? (map.get(a[j]) + 1) : 1);
			}
			long num = 0;
			for (int x : map.keySet()) {
				long f = map.get(x);
				num += f * f - f;
			}
			System.out.println(num);
		}
		scan.close();
	}
	
	public static void sherlockAndTheBeast(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			String ret = null; 
			for (int j = n / 3; j >= 0; j--) {
				int x = n - j*3;
				if (x % 5 == 0) {
					StringBuilder sb = new StringBuilder();
					for (int l = 0; l < j*3; l++) {
						sb.append('5');
					}
					for (int l = 0; l < x; l++) {
						sb.append('3');
					}
					ret = sb.toString();
					j = n;
					n = 0;
					break;
				}
			}
			if (ret == null) {
				System.out.println(-1);
			} else {
				System.out.println(ret);
			}
		}
		scan.close();
	}
	
	public static void isFibo(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		long a1 = 1;
		long b = 1;
		HashSet<Long> fib = new HashSet<>();
		while (a1 <= 10000000000L) {
			fib.add(a1);
			long temp = a1;
			a1 = a1 + b;
			b = temp;
		}
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			if (fib.contains(n)) {
				System.out.println("IsFibo");
			} else {
				System.out.println("IsNotFibo");
			}
		}
		scan.close();
	}
	
	public static void findDigits(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextInt();
			char[] a = Long.toString(n).toCharArray();
			int num = 0;
			for (int j = 0; j < a.length; j++) {
				if (a[j] != '0' && (n % (a[j] - '0')) == 0) {
					num++;
				}
			}
			System.out.println(num);
		}
		scan.close();
	}
	
	public static void sherlockAndGCD(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int[] a = new int[n];
			for (int j = 0; j < a.length; j++) {
				a[j] = scan.nextInt();
			}
			System.out.println(sherlockAndGCD(n, a));
		}
		scan.close();
	}

	public static String sherlockAndGCD(int n, int[] a) {
		for (int j = 0; j < a.length; j++) {
			int gcd = a[j];
			for (int k = j; k < a.length; k++) {
				gcd = gcd(a[k], gcd);
				if (gcd == 1) {
					return "YES";
			 	}
			}
		}
		return "NO";
	}

	public static int gcd(int a, int b) {
		int r;
		while (b != 0) {
			r = a % b;
			a = b;
			b = r;
		}
		return a;
	}
	
	public static void manasaAndStones(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int a = scan.nextInt();
			int b = scan.nextInt();
			TreeSet<Integer> set = new TreeSet<>();
			set.add(0);
			for (int j = 0; j < n-1; j++) {
				TreeSet<Integer> next = new TreeSet<>();
				for (int k: set) {
					next.add(k + a);
					next.add(k + b);
				}
				set = next;
			}
			StringBuilder sb = new StringBuilder(); 
			for (int k: set) {
				sb.append(k + " ");
			}
			System.out.println(sb.toString());
		}
		scan.close();
	}
	
	public static void halloweenParty(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long k = scan.nextInt();
			if (k % 2 == 0) {
				System.out.println((k/2) * (k/2));
			} else {
				k++;
				System.out.println((k/2) * (k/2) - k/2);
			}
			
		}
		scan.close();
	}
	
	public static void sherlockAndSquares(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			
			int sqrtA = (int) Math.sqrt(a);
			int sqrtB = (int) Math.sqrt(b);
			int x = sqrtB - sqrtA;
			if (sqrtA * sqrtA == a) {
				x++;
			}
			
			System.out.println(x);
		}
		scan.close();
	}
	
	public static void chocolateFeast(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int c = scan.nextInt();
			int m = scan.nextInt();
			
			int eaten = 0;
			int chocs = n / c;
			int wrappers = 0;
			while (chocs > 0) {
				eaten += chocs;
				wrappers += chocs;
				
				chocs = wrappers / m;
				wrappers = wrappers % m;
			}
			System.out.println(eaten);
		}
		scan.close();
	}
	
	public static void fillingJars(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		long sum = 0;
		for (int i = 0; i < m; i++) {
			long a = scan.nextInt();
			long b = scan.nextInt();
			long k = scan.nextInt();
			sum += (b-a+1) * k;
		}
		System.out.println(sum/n);
		scan.close();
	}
	
	public static void acmIcpcTeam(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		scan.nextInt();
		BigInteger[] a = new BigInteger[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = new BigInteger(scan.nextLine(), 2);
		}
		int[] best = new int[501];
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i+1; j < a.length; j++) {
				BigInteger or = a[i].or(a[j]);
				int topics = 0;
				for (int k = 0; k <= or.bitLength(); k++) {
					if (or.testBit(k)) {
						topics++;
					}
				}
				best[topics]++;
				max = Math.max(max, topics);
			}
		}
		System.out.println(max);
		System.out.println(best[max]);
		scan.close();
	}
	
	public static void gameOfThrones(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] s = scan.nextLine().toCharArray();
		int[] freq = new int[26];
		for (int i = 0; i < s.length; i++) {
			freq[s[i] - 'a']++;
		}
		int numOdd = 0;
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] % 2 == 1) {
				numOdd++;
			}
		}
		if (numOdd > 1) {
			System.out.println("NO");
		} else {
			System.out.println("YES");
		}
		scan.close();
	}
	
	public static void angryChildren(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		long[] a = new long[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = scan.nextLong();
		}
		Arrays.sort(a);
		long min = Long.MAX_VALUE;
		for (int i = k-1; i < a.length; i++) {
			min = Math.min(min, a[i] - a[i - k + 1]);
		}
		System.out.println(min);
		scan.close();
	}
	
	public static void theLoveLetterMystery(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[] s = scan.nextLine().toCharArray();
			int changes = 0;
			for (int j = 0; j < s.length/2; j++) {
				changes += Math.abs(s[j] - s[s.length -j -1]);
			}
			System.out.println(changes);
		}
		scan.close();
	}
	
	public static void alternatingCharacters(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			char[] s = scan.nextLine().toCharArray();
			int del = 0;
			for (int j = 1; j < s.length; j++) {
				if (s[j] == s[j-1]) {
					del++;
				}
			}
			System.out.println(del);
		}
		scan.close();
	}
	
	public static void maximizingXOR(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int l = scan.nextInt();
		int r = scan.nextInt();
		int max = 0;
		for (int i = l; i <= r; i++) {
			for (int j = i; j <= r; j++) {
				max = Math.max(max, i ^ j);
			}
		}
		System.out.println(max);
		scan.close();
	}
	
	public static void utopianTree(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long h = 1;
			int n = scan.nextInt();
			for (int j = 0; j < n; j++) {
				if (j % 2 == 0) {
					h *= 2;
				} else {
					h++;
				}
			}
			System.out.println(h);
		}
		scan.close();
	}
	
	public static void flippingBits(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			System.out.println(((long)~scan.nextLong() + 4294967296L));
		}
		scan.close();
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
