
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;
 
public class Dec14Long {
	public static void main(String[] args) {
		//System.out.println(cApple(System.in));
		//System.out.println(xorsub(System.in));
		System.out.println(sanskar(System.in));
		//System.out.println(chefAndBracketPairs(System.in));
		//System.out.println(chefUnderPressure(System.in));	
	}
	
	public static String chefUnderPressure(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
 
		int n = scan.nextInt();
		int k = scan.nextInt();
		int cap = scan.nextInt();
		int[][] paths = new int[n-1][2];
		for (int j = 0; j < n-1; j++) {
			paths[j][0] = scan.nextInt();
			paths[j][1] = scan.nextInt();
		}
		int[] products = new int[n];
		for (int i = 0; i < products.length; i++) {
			products[i] = scan.nextInt();
		}
		int q = scan.nextInt();
		int[][] queries = new int[q][2];
		for (int i = 0; i < queries.length; i++) {
			queries[i][0] = scan.nextInt();
			queries[i][1] = scan.nextInt();
		}
		sb.append(chefUnderPressure(n, k, cap, paths, products, q, queries)+"\n");
		scan.close();
		return sb.toString();
	}
	
	public static String chefUnderPressure(int n, int numProductTypes, int cap,
			int[][] paths, int[] products, int q, int[][] queries) {
		StringBuilder sb = new StringBuilder();

		int N = n;
		int invalid = -1;
		int[][] shortestPaths = new int[N][N];
		for (int i = 0; i < shortestPaths.length; i++) {
			Arrays.fill(shortestPaths[i], invalid);			
		}
		for (int i = 0; i < paths.length; i++) {
			shortestPaths[paths[i][0]][paths[i][1]] = 1;
			shortestPaths[paths[i][1]][paths[i][0]] = 1;
		}
		
		Queue<Integer> queue = new LinkedList<>();
		queue.add(cap);
		boolean[] visited = new boolean[n]; 
		int[] dist = new int[n];
		while (queue.isEmpty()) {
			int top = queue.poll();
			if (visited[top]) {
				continue;
			}
			visited[top] = true;
			
		}
		
		for (int k = 0; k < shortestPaths.length; k++) {
			for (int i = 0; i < shortestPaths.length; i++) {
				for (int j = 0; j < shortestPaths.length; j++) {
					if (shortestPaths[i][k] != invalid && shortestPaths[k][j] != invalid) {
						shortestPaths[i][j] = Math.min(shortestPaths[i][j], shortestPaths[i][k] + shortestPaths[k][j]);
					}
				}
			}
		}
		
		return sb.toString();
	}

	public static String chefAndBracketPairs(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
 
		int n = scan.nextInt();
		int[] a = new int[n];
		for (int j = 0; j < n; j++) {
			a[j] = scan.nextInt();
		}
		sb.append(chefAndBracketPairsFslow(n, a)+"\n");
		scan.close();
		return sb.toString();
	}
	
	public static long mod = 1000000007;
	
	public static int chefAndBracketPairsF(int m, int[] a_) {
		long valid = 0;
		HashSet<Integer> pos = new HashSet<>();
		HashSet<Integer> neg = new HashSet<>();
		for (int i = 0; i < a_.length; i++) {
			if (a_[i] > 0) {
				pos.add(a_[i]);				
			} else {
				neg.add(-a_[i]);
			}
		}		
		pos.retainAll(neg);
		
		ArrayList<Integer> a = new ArrayList<>();
		for (int i = 0; i < a_.length; i++) {
			if (pos.contains(Math.abs(a_[i]))) {
				a.add(a_[i]);
			}
		}
		
		valid = getValidPairs(a, 0, a.size()-1);
		
		return (int) (valid % mod);
	}
	
	public static long getValidPairs(ArrayList<Integer> a, int left, int right) {
		long valid = 1;
		HashMap<Integer, Integer> map = new HashMap<>();
		int start = left;
		for (int i = left; i <= right; i++) {
			int n = a.get(i);
			if (n > 0) {
				if (!map.containsKey(n)) {
					map.put(n, 1);
				} else {
					map.put(n, map.get(n)+1);
				}
			} else {
				if (!map.containsKey(-n)) {
					map.put(-n, -1);
				} else {
					map.put(-n, map.get(-n)-1);
					if (map.get(-n) == 0) {
						map.remove(-n);
					}
				}
			}
			if (map.isEmpty()) {
				valid = (valid * getValidPairs(a, start, i)) % mod;
				start = i+1;
			}
		}
		
		return valid % mod;
	}

	public static int chefAndBracketPairsFslow(int m, int[] a) {
		long valid = 0;
		int N = 1 << a.length;
		for (int n = 0; n < N; n++) {
			ArrayList<Integer> num = new ArrayList<>();
			for (int i = 0; i < a.length; i++) {
				if (((1 << i) & n) != 0) {
					num.add(a[i]);
				}
			}
			if (check(num)) {
				//System.out.println(num);
				valid++;
			}
		}
		return (int) (valid % mod);
	}
	
	public static boolean check(ArrayList<Integer> a) {
		Stack<Integer> s = new Stack<>();
		for (int n: a) {
			if (n < 0) {
				s.push(n);				
			} else {				
				if (s.isEmpty() || s.pop() != -n) {
					return false;
				}
			}
		}		
		return s.isEmpty();
	}
	
	public static int[] waysToSum(int[] values) {
		Arrays.sort(values);
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
	
	public static HashMap<Long, Long> waysToSumLong(ArrayList<Long> values, long limit) {
		//Arrays.sort(values);
		HashMap<Long, Long> ways = new HashMap<>();
		ways.put(0L, 1L);
		for (int i = 0; i < values.size(); i++) {
			HashMap<Long, Long> newWays = new HashMap<>(ways); // shallow copy
			for (long key: ways.keySet()) {
				long newSum = key + values.get(i);
				if (newSum <= limit) {
					newWays.put(newSum, ways.get(key) + (ways.containsKey(newSum) ? ways.get(newSum) : 0));
				}
			}
			ways = newWays;
		}
		return ways;
	}
	
	public static void sanskarTest5() {
		Random rand = new Random(0);
		long maxTime = 0;
		for (int n = 1; n <= 14; n++) {
			for (int k = 1; k <= 8; k++) {
				for (int i = 0; i < 100; i++) {
					ArrayList<Long> a = new ArrayList<>();
					for (int j = 0; j < n; j++) {
						//a.add((long) rand.nextInt(10000000));
						a.add((long) rand.nextInt(10));
					}
					
					long time = System.nanoTime();
					String res = Dec14Long.sanskar9(n, k, new ArrayList<Long>(a));
					//String res2 = sanskar3(n, k, a);
					String res2 = "";
					//res2 = Main.sanskar8(n, k, b);
					//try {
					res2 = Dec14Long.sanskar8(n, k, new ArrayList<Long>(a));
					time = (System.nanoTime() - time)/1000000;
					//if (time >= 100) {
					if (!res.equals(res2)) {
					//if (res.equals("yes")) {
						Dec14Long.sanskar8(n, k, new ArrayList<Long>(a));
						System.out.println(n + "\t " + k + "\t" + a + ": " + res + " vs " + res2);
						//System.out.println("   " + res + "\ttime: " + time);
					}
					maxTime = Math.max(maxTime, time);
//					} catch (Exception e) {
//						System.out.println(n + "\t " + k + "\t" + c + ": "+ res + " - failed: " +e);
//					}
				}
			}
		}
		System.out.println("max time: " + maxTime);
	}
	
	public static void sanskarTest6() {
		Random rand = new Random(0);
		long maxTime = 0;
		for (int n = 1; n <= 21; n++) {
			for (int k = 1; k <= 8; k++) {
				for (int i = 0; i < 100; i++) {
					ArrayList<Long> a = new ArrayList<>();
					for (int j = 0; j < n; j++) {
						//a.add((long) rand.nextInt(10000000));
						a.add((long) rand.nextInt(2));
					}
					ArrayList<Long> b = new ArrayList<Long>(a);
					
					long time = System.nanoTime();
					String res = Dec14Long.sanskar9(n, k, b);
					time = (System.nanoTime() - time)/1000000;
					if (time >= 100) {
						System.out.println(n + "\t " + k + "\t" + a + ": " + res + " \ttime: " + time);
					}
					maxTime = Math.max(maxTime, time);
				}
			}
		}
		System.out.println("max time: " + maxTime);
	}
	
	public static void sanskarTest4() {
		ArrayList<int[]> s = new ArrayList<>();
		int n = 7;
		s.add(new int[]{1, 4, 7});
		s.add(new int[]{0, 1, 4});
		s.add(new int[]{4, 5, 7});
		s.add(new int[]{3, 5, 6});
		s.add(new int[]{2, 3, 6, 7});
		s.add(new int[]{2, 7});
		algorithmX(n, s);
	}
	
	public static void sanskarTest3() {
		//int[] a = new int[]{9, 8, 8, 7, 7, 6, 5, 5, 4, 2, 2, 1};
		//int k = 4;
		//int[] a = new int[]{9, 8, 7, 6, 5, 4, 2, 1};
		//int k = 2;
		//int[] a = new int[]{8, 7, 5, 5, 5, 4, 4, 1};
		//int k = 3;
		//int[] a = new int[]{4, 3, 1, 7, 4, 2, 7, 2, 2, 6, 5, 8, 3, 4};
		//int k = 2;
		long[] a = new long[15];
		for (int i = 0; i < a.length; i++) {
			a[i] = 1;
		}
		a[0] = 1;
		int k = 3;
		
		ArrayList<Long> b = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			b.add((long) a[i]);
		}
		System.out.println(sanskar8(a.length, k, b));
	}
	
	public static void sanskarTest2() {
		int[] a = new int[]{8, 5, 6, 4, 2, 2, 8, 9, 5, 1, 7, 7};
		//int[] a = new int[]{1, 2, 4, 5, 6};
		//int[] a = new int[]{1, 2, 4, 5, 7};
		int k = 4;
		
		int[] ways = waysToSum(a);
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		int setSize = sum / k;
		
		System.out.println(setSize + "\t" + ways[setSize] + "\t" + Arrays.toString(ways));
	}
 
	public static void sanskarTest() {
		Random rand = new Random(0);
		long maxTime = 0;
		for (int n = 1; n <= 21; n++) {
			for (int k = 1; k <= 8; k++) {
				for (int i = 0; i < 100; i++) {
					ArrayList<Long> a = new ArrayList<>();
					for (int j = 0; j < n; j++) {
						a.add((long) rand.nextInt(100)+1);
					}
					
					long time = System.nanoTime();
					String res3 = sanskar5(n, k, new ArrayList<Long>(a));
					//String res2 = sanskar3(n, k, new ArrayList<Long>(a));
					String res = sanskar4(n, k, new ArrayList<Long>(a));
					time = (System.nanoTime() - time)/1000000;
					//if (time >= 100) {
					if (!res.equals(res3)) {
					//if (res.equals("yes")) {
						sanskar5(n, k, new ArrayList<Long>(a));
						System.out.println(n + "\t " + k + "\t" + a);
						System.out.println("   " + res3 + "\ttime: " + time);
					}
					//}
					maxTime = Math.max(maxTime, time);
				}
			}
		}
		System.out.println(maxTime);
	}
 
	public static String sanskar(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
 
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			ArrayList<Long> a = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				a.add(scan.nextLong());
			}
			//sb.append(sanskar(n, k, a)+"\n");
			//sb.append(sanskar2(n, k, a)+"\n");
			//sb.append(sanskar4(n, k, a)+"\n");
			//sb.append(sanskar5(n, k, a)+"\n");
			//sb.append(sanskar6(n, k, a)+"\n");
			//sb.append(sanskar7(n, k, a)+"\n");
			//sb.append(sanskar8(n, k, a)+"\n");
			sb.append(sanskar9(n, k, a)+"\n");
		}
		scan.close();
		return sb.toString();
	}
	
	public static String sanskar9(int n, int k, ArrayList<Long> a_) {
		long sum = 0;
		for (int i = 0; i < a_.size(); i++) {
			sum += a_.get(i);
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a_.size(); i++) {
			if (a_.get(i) > setSize) {
				return "no";
			} else if (a_.get(i) == setSize) {
				a_.remove(i--);
				k--;
			}	
		}
		
		if (k <= 0) {
			return "yes";
		}
		
		long[] a = new long[a_.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = a_.get(i);
		}		

		int N = 1 << a.length;
		int[][] dp = new int[k+1][N];
		dp[0][0] = 1;
		
		for (int j = 0; j < k; j++) {
			//System.out.println();
			
			for (int mask = 0; mask < N; mask++) {
				if (dp[j][mask] == 0) {
					continue;
				}
				long total = 0;
				for (int i = 0; i < a.length; i++) {
					if (((1 << i) & mask) != 0) {
						total += a[i];
					}
				}
				total -= j*setSize;
				
				for (int i = 0; i < a.length; i++) {
					if (((1 << i) & mask) != 0) {
						continue;
					}
					int newMask = ((1 << i) | mask);
					if (total + a[i] == setSize) {
						dp[j+1][newMask] = 1;
						//System.out.println(printMask(mask, a) + " -> "+ printMask(newMask, a) + "\t " + (j+1));
					} else {
						dp[j][newMask] = 1;
						//System.out.println(printMask(mask, a) + " -> "+ printMask(newMask, a) + "\t " + j);
					}
				}
			}			
		}
		if (dp[k][N-1] == 1) {
			return "yes";
		} else {
			return "no";
		}
	}
	
	public static String printMask(int newMask, long[] a) {
		long[] temp = new long[a.length];
		for (int l = 0; l < temp.length; l++) {
			if ((newMask & (1 << l)) != 0) {
				temp[l] = a[l];
			}	
		}
		return Arrays.toString(temp);
	}
	
	public static String sanskar8(int n, int k, ArrayList<Long> a) {
		long sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > setSize) {
				return "no";
			} else if (a.get(i) == setSize) {
				a.remove(i--);
				k--;
			}	
		}
		
		if (k <= 0) {
			return "yes";
		}
		
		if (a.isEmpty()) {
			return "no";
		}
 
		Collections.sort(a);
		Collections.reverse(a);
		
		if (k == 1) {
			return "yes";
		} else if (k == 2) {
			return solveAllCombinations(a, setSize);
		} else {
			HashMap<Long, Pair<Long, HashSet<Long>>> map = getPossible2(a, setSize, -1);
			long rem = sum;
			ArrayList<int[]> results = new ArrayList<>();
			for (int i = 0; i < a.size(); i++) {
				long[] path = new long[22];
				Arrays.fill(path, -1);
				path[0] = i;
				getAllPaths(map, a, i, setSize, path, 1, setSize - a.get(i), results);
				rem -= a.get(i);
				if (rem < setSize) {
					break;
				}
			}
			
			ArrayList<Integer> solution = algorithmX(a.size(), results);
			for (int i = 0; i < solution.size()-1; i++) {
				long[] values = new long[results.get(solution.get(i)).length];
				for (int j = 0; j < results.get(solution.get(i)).length; j++) {
					values[j] = a.get(results.get(solution.get(i))[j]);
				}
				//System.out.println(Arrays.toString(values));
			}
			if (solution != null && !solution.isEmpty()) {
				return "yes";
			} else {
				return "no";
			}
		}
	}
	
	public static ArrayList<Integer> algorithmX(int n, ArrayList<int[]> s) {
		TreeMap<Integer, HashSet<Integer>> X = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			X.put(i, new HashSet<Integer>());
		}
		for (int i = 0; i < s.size(); i++) {
			for (int j = 0; j < s.get(i).length; j++) {
				HashSet<Integer> set = X.get(s.get(i)[j]);
				set.add(i);
			}
		}
		ArrayList<Integer> solution = new ArrayList<>();
		ArrayList<Integer> sol = algorithmXsolve(X, s, solution, n);
//		for (int i = 0; i < solution.size(); i++) {
//			System.out.println(Arrays.toString(s.get(solution.get(i))));
//		}
		return solution;
	}

	public static ArrayList<Integer> algorithmXsolve(TreeMap<Integer, HashSet<Integer>> X,  ArrayList<int[]> s, 
			ArrayList<Integer> solution, int n) {
		if (X.isEmpty()) {
			//System.out.println(solution);
			return solution;
		} else {
			int c = Integer.MAX_VALUE;
			int cIdx = 0;
			for (int key: X.keySet()) {
				if (X.get(key).size() < c) {
					cIdx = key;
					c = X.get(key).size();
				}
			}
			ArrayList<Integer> set = new ArrayList<>(X.get(cIdx));
			Collections.shuffle(set);
			for (int r : set) {
			//for (int r : X.get(cIdx)) {
				solution.add(r);
				ArrayList<HashSet<Integer>> cols = algorithmXselect(X, s, r);
				ArrayList<Integer> sol = algorithmXsolve(X, s, solution, n);
				if (sol != null && !sol.isEmpty()) {
//					for (int so: sol) {
//						//System.out.println(so);
//						ArrayList<Integer> t = new ArrayList<>();
//						t.add(so);
//						return t;
//					}
					return sol;
				}
				algorithmXdeselect(X, s, r, cols);
				solution.remove(solution.size()-1);
			}
			return null;
		}
	}
	
	public static ArrayList<HashSet<Integer>> algorithmXselect(TreeMap<Integer, HashSet<Integer>> X,  ArrayList<int[]> s, int r) {
		ArrayList<HashSet<Integer>> cols = new ArrayList<>();
		for (int j : s.get(r)) {
			for (int i : X.get(j)) {
				for (int k : s.get(i)) { 
					if (k != j) {
						X.get(k).remove(i);
					}
				}
			}
			cols.add(X.remove(j));
		}
		return cols;		
	}

	public static void algorithmXdeselect(TreeMap<Integer, HashSet<Integer>> X,  ArrayList<int[]> s, int r, 
			ArrayList<HashSet<Integer>> cols) {
		for (int m = s.get(r).length-1; m >= 0; m--) {
			int j = s.get(r)[m];
			X.put(j, cols.remove(cols.size()-1));
			for (int i : X.get(j)) {
				for (int k: s.get(i)) {
					if (k != j) {
						X.get(k).add(i);
					}
				}
			}
		}
	}
	
	private static void getAllPaths(HashMap<Long, Pair<Long, HashSet<Long>>> map, ArrayList<Long> a, 
			int i, long setSize, long[] path, int depth, long rem, ArrayList<int[]> results) {
		//long rem = setSize - a.get(i);
		
		for (int j = 0; j < a.size(); j++) {
			if (a.get(j) == rem) {
				int[] newPath = new int[depth+1];
				HashSet<Integer> used = new HashSet<>();
				for (int k = 0; k < newPath.length; k++) {
					newPath[k] = (int) path[k];
					if (used.contains(newPath[k])) {
						continue;
					}
					used.add(newPath[k]);					
				}
				newPath[depth] = j;
				if (used.contains(j)) {
					continue;
				}
				long[] values = new long[depth+1];
				long sum = 0;
				for (int k = 0; k < values.length; k++) {
					values[k] = a.get((int) newPath[k]);
					sum += values[k];
				}
				if (sum != setSize) {
					System.out.println("FAIL");
				}
				results.add(newPath);
				//System.out.println(Arrays.toString(newPath) + "\t" + Arrays.toString(values));
			}
		}
		
		if (map.containsKey(rem)) {
			Pair<Long, HashSet<Long>> p = map.get(rem);
			for (long b: p.second) {
				if (b != 0) {
					boolean valid = true;
					for (int j = 0; j <= depth; j++) {
						if (path[j] == b) {
							valid = false;
							break;
						}
					}
					if (valid) {
						path[depth] = b;
						getAllPaths(map, a, i, setSize, path, depth+1, rem-a.get((int) b), results);
						path[depth] = -1;
					}
				}
			}			
		}
	}

	//
	// Supposedly 7/8 and fast
	//
	public static String sanskar7(int n, int k, ArrayList<Long> a) {		
		long sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		if (sum == 0) {
			return "yes";
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > setSize) {
				return "no";
			} else if (a.get(i) == setSize) {
				a.remove(i--);
				k--;
			}	
		}
		
		if (a.isEmpty() && k != 0) {
			return "no";
		}
 
		Collections.sort(a);
		Collections.reverse(a);		
		ArrayList<Long> b = new ArrayList<>(a);
		
		if (k == 1) {
			return "yes";
		} else {
			if (k == 2) {
				return solveAllCombinations(b, setSize);
			}
			long total = !b.isEmpty() ? b.remove(0) : 0;
			while (!b.isEmpty()) {
				if (k == 2) {
					return solveAllCombinations(b, setSize);
				}
				long min = Long.MAX_VALUE;
				int minIdx = 0;
				boolean found = false;
				for (int i = 0; i < b.size(); i++) {
					if (b.get(i) + total == setSize) {
						found = true;
						minIdx = i;
						found = true;
						break;
					}
				}
				if (!found) {
					long w = 0;
					//long wtotal = 0;
					//long max = -1;
					for (int i = 0; i < b.size(); i++) {
						if (b.get(i) + total < setSize) {
							HashMap<Long, Long> map = getPossible(b, setSize-total, i);
							w = map.containsKey(setSize-total) ? map.get(setSize-total) : 0;				
							//wtotal = map.containsKey(setSize) ? map.get(setSize) : 0;
							//if (w < min && wtotal >= max) {
							if (w < min) {
								//max = wtotal;
								min = w;
								minIdx = i;
							}
						}
					}
				}
				long v = b.remove(minIdx);
				total += v;
				if (total == setSize) {
					k--;
					total = 0;
				}
			}
		}
		
		if (k == 0) {
			return "yes";
		} else {
			return "no";
		}
	}
	
	private static String solveAllCombinations(ArrayList<Long> b, long setSize) {
		//
		// brute force
		//
		
		//long first = b.remove(0);
		long first = !b.isEmpty() ? b.remove(0) : 0;
		int N = 1 << b.size();
		for (int m = 0; m < N; m++) {
			long total = first;
			for (int i = 0; i < b.size(); i++) {
				if (((1 << i) & m) != 0) {
					total += b.get(i);
				}
			}
			if (total == setSize) {
				return "yes";
			}
		}
		return "no";
	}
 
	//
	// Contains a bug, [1, 5, 6, 6, 5, 1, 9] doesn't work
	//
	public static String sanskar6(int n, int k, ArrayList<Long> a) {		
		long sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		if (sum == 0) {
			return "yes";
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > setSize) {
				return "no";
			} else if (a.get(i) == setSize) {
				a.remove(i--);
				k--;
			}	
		}
		
		if (a.isEmpty() && k != 0) {
			return "no";
		}
 
		Collections.sort(a);
		Collections.reverse(a);		
		ArrayList<Long> b = new ArrayList<>(a);
		
		if (k == 1) {
			return "yes";
		} else if (k == 2) {
			//
			// brute force
			//
			
			//long first = b.remove(0);
			long first = !b.isEmpty() ? b.remove(0) : 0;
			int N = 1 << b.size();
			for (int m = 0; m < N; m++) {
				long total = first;
				for (int i = 0; i < b.size(); i++) {
					if (((1 << i) & m) != 0) {
						total += b.get(i);
					}
				}
				if (total == setSize) {
					return "yes";
				}
			}
			return "no";
		} else { 		
			long total = 0;
			while (!b.isEmpty()) {
				long min = Long.MAX_VALUE;
				int minIdx = 0;
				for (int i = 0; i < b.size(); i++) {
					HashMap<Long, Long> map = getPossible(b, setSize-total, i);
					long w = map.containsKey(setSize-total) ? map.get(setSize-total) : 0;
					if (w < min) {
						min = w;
						minIdx = i;
					} else if (w == min && b.get(i) + total == setSize) {
						min = w;
						minIdx = i;
					}
				}
				long v = b.remove(minIdx);
				total += v;
				if (total == setSize) {
					k--;
					total = 0;
				}
			}
		}
		
		if (k == 0) {
			return "yes";
		} else {
			return "no";
		}
	}
	
	public static String sanskar5(int n, int k, ArrayList<Long> a) {		
		long sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > setSize) {
				return "no";
			} else if (a.get(i) == setSize) {
				a.remove(i--);
				k--;
			}	
		}
 
		Collections.sort(a);
		Collections.reverse(a);		
		ArrayList<Long> b = new ArrayList<>(a);
		
		if (k == 1) {
			return "yes";
		} else { 		
			int total = 0;
			while (!b.isEmpty()) {
				long min = Long.MAX_VALUE;
				int minIdx = 0;
				for (int i = 0; i < b.size(); i++) {
					HashMap<Long, Long> map = getPossible(b, setSize-total, i);
					long w = map.containsKey(setSize-total) ? map.get(setSize-total) : 0;
					if (w < min) {
						min = w;
						minIdx = i;
					} else if (w == min && b.get(i) + total == setSize) {
						min = w;
						minIdx = i;
					}
				}
				long v = b.remove(minIdx);
				total += v;
				if (total == setSize) {
					k--;
					total = 0;
				}
			}
		}
		
		if (k == 0) {
			return "yes";
		} else {
			return "no";
		}
	}
 
	public static String sanskar4(int n, int k, ArrayList<Long> a) {		
		long sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > setSize) {
				return "no";
			} else if (a.get(i) == setSize) {
				a.remove(i--);
				k--;
			}	
		}
		
		long[] aa = new long[a.size()];
		for (int i = 0; i < aa.length; i++) {
			aa[i] = a.get(i);					
		}
		
		@SuppressWarnings("unused")
		HashMap<Long, Long> possible = getPossible(a, setSize, -1);
		if (subSanskar4(n, k, aa, setSize)) {
			return "yes";
		}
		return "no";
	}
	
	
	
	public static boolean subSanskar4(int n, int k, long[] aa, long setSize) {
		ArrayList<Long> a = new ArrayList<>();
		for (int i = 0; i < aa.length; i++) {
			a.add(aa[i]);
		}
				
		Collections.sort(a);
		Collections.reverse(a);
		
		while (!a.isEmpty() && a.get(0) == setSize) {
			a.remove(0);
			k--;
		}
		
		while (!a.isEmpty() && k > 0) {
			int[][] candidates = null;
			HashSet<Long> candValue = null;
			int t = 0;
			long base = a.remove(0);
			for (int i = 0; i < a.size(); i++) {
				long v = a.get(i);
				if (base + v > setSize) {
					continue;
				} else if (base + v == setSize) {
					if (candidates == null) {
						candidates = new int[a.size()][2];
						candValue = new HashSet<>();
					}
					if (!candValue.contains(a.get(i))) {
						candValue.add(a.get(i));
						candidates[t++] = new int[]{i, 0};
						// Why continue if this number completes the set?
						break;
					}
				} else {
					HashMap<Long, Long> possible = getPossible(a, setSize, i);			
					if (possible.containsKey(setSize) && possible.get(setSize) < k-2) {
						continue;
					}		
					if (possible.containsKey(setSize - (base + v))) {
						if (candidates == null) {
							candidates = new int[a.size()][2];
							candValue = new HashSet<>();
						}
						//if (!candValue.contains(a.get(i)) && !candValue.contains(a.get(i))) {
						if (!candValue.contains(a.get(i))) {
							int numWays = (int) (possible.containsKey(setSize) ? possible.get(setSize) : 0);
							candValue.add(a.get(i));
							candValue.add(setSize - (base + v)); // add the remainder
							candidates[t++] = new int[]{i, numWays};
						}
					}
				}
			}
			
			if (candidates != null) {
				boolean valid = false;
				Arrays.sort(candidates, new Comparator<int[]>() {
					@Override
					public int compare(int[] o1, int[] o2) {						
						return Integer.compare(o2[1], o1[1]);
					}					
				});
				
				int max = candidates[0][1];
				for (int i = 0; i < t; i++) {
					if (candidates[i][1] < max) {
						break;
					}
					long[] newA = new long[a.size()];
					int t2 = 0;
					newA[t2++] = base + a.get(candidates[i][0]);
					for (int j = 0; j < a.size(); j++) {
						if (j != candidates[i][0]) {
							newA[t2++] = a.get(j);
						}
					}
					if (subSanskar4(n, k, newA, setSize)) {
						base += a.get(candidates[i][0]);					
						a.remove((int)candidates[i][0]);
						a.add(0, base);
						
						return true;
						//valid = true;
						//break;
					}
				}
			}
			
			if (!a.isEmpty() && a.get(0) == setSize) {
				a.remove(0);
				k--;
			} else if (candidates == null) {
				return false;
			}
		}
		
		while (!a.isEmpty() && a.get(0) == setSize) {
			a.remove(0);
			k--;
		}
		
		if (a.isEmpty() && k == 0) {
			return true;
		} else {
			return false;			
		}
	}
	public static String sanskar3(int n, int k, ArrayList<Long> a) {		
		long sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > setSize) {
				return "no";
			} else if (a.get(i) == setSize) {
				a.remove(i--);
				k--;
			}	
		}
		
		long[] aa = new long[a.size()];
		for (int i = 0; i < aa.length; i++) {
			aa[i] = a.get(i);					
		}
		
		@SuppressWarnings("unused")
		HashMap<Long, Long> possible = getPossible(a, setSize, -1);
		if (subSanskar(n, k, aa, setSize)) {
			return "yes";
		}
//		while (next_permutation(aa)) {
//			if (subSanskar(n, k, aa, setSize)) {
//				return "yes";
//			}
//		}
		return "no";
	}
	
	public static boolean subSanskar(int n, int k, long[] aa, long setSize) {
		ArrayList<Long> a = new ArrayList<>();
		for (int i = 0; i < aa.length; i++) {
			a.add(aa[i]);
		}
				
		Collections.sort(a);
		Collections.reverse(a);
		
		while (!a.isEmpty() && a.get(0) == setSize) {
			a.remove(0);
			k--;
		}
		
		while (!a.isEmpty() && k > 0) {
			int[] candidates = null;
			HashSet<Long> candValue = null;
			int t = 0;
			long base = a.remove(0);
			for (int i = 0; i < a.size(); i++) {
				long v = a.get(i);
				if (base + v > setSize) {
					continue;
				} else if (base + v == setSize) {
					if (candidates == null) {
						candidates = new int[a.size()];
						candValue = new HashSet<>();
					}
					if (!candValue.contains(a.get(i))) {
						candValue.add(a.get(i));
						candidates[t++] = i;
						// Why continue if this number completes the set?
						break;
					}
				} else {
					HashMap<Long, Long> possible = getPossible(a, setSize, i);
					if (possible.containsKey(setSize) && possible.get(setSize) < k-2) {
						continue;
					}
					if (possible.containsKey(setSize - (base + v))) {
						if (candidates == null) {
							candidates = new int[a.size()];
							candValue = new HashSet<>();
						}
						//if (!candValue.contains(a.get(i)) && !candValue.contains(a.get(i))) {
						if (!candValue.contains(a.get(i))) {
							candValue.add(a.get(i));
							candValue.add(setSize - (base + v)); // add the remainder
							candidates[t++] = i;
						}
					}
				}
			}
			
			if (candidates != null) {
				boolean valid = false;
				for (int i = 0; i < t; i++) {
					long[] newA = new long[a.size()];
					int t2 = 0;
					newA[t2++] = base + a.get(candidates[i]);
					for (int j = 0; j < a.size(); j++) {
						if (j != candidates[i]) {
							newA[t2++] = a.get(j);
						}
					}
					if (subSanskar(n, k, newA, setSize)) {
						base += a.get(candidates[i]);					
						a.remove((int)candidates[i]);
						a.add(0, base);
						
						return true;
						//valid = true;
						//break;
					}
				}
			}
			
			if (!a.isEmpty() && a.get(0) == setSize) {
				a.remove(0);
				k--;
			} else if (candidates == null) {
				return false;
			}
		}
		
		while (!a.isEmpty() && a.get(0) == setSize) {
			a.remove(0);
			k--;
		}
		
		if (a.isEmpty() && k == 0) {
			return true;
		} else {
			return false;			
		}
	}
 
	public static boolean next_permutation(long str[]) {
		long temp;
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
	
	public static HashMap<Long, Long> getPossible(ArrayList<Long> a, long setSize, int excludeIdx) {
		HashMap<Long, Long> ways = new HashMap<>();
		ways.put(0L, 1L);
		for (int i = 0; i < a.size(); i++) {
			if (i != excludeIdx) {
				HashMap<Long, Long> newWays = new HashMap<>(ways); // shallow copy
				for (long key: ways.keySet()) {
					long newSum = key + a.get(i);
					if (newSum <= setSize) {
						newWays.put(newSum, ways.get(key) + (ways.containsKey(newSum) ? ways.get(newSum) : 0));
					}
				}
				ways = newWays;
			}
		}
		return ways;
	}
	
	public static HashMap<Long, Pair<Long, HashSet<Long>>> getPossible2(ArrayList<Long> a, long setSize, int excludeIdx) {
		HashMap<Long, Pair<Long, HashSet<Long>>> ways = new HashMap<>();
		ways.put(0L, new Pair<>(1L, new HashSet<Long>()));
		for (int i = 0; i < a.size(); i++) {
			if (i != excludeIdx) {
				HashMap<Long, Pair<Long, HashSet<Long>>> newWays = new HashMap<>(ways); // shallow copy
				for (long key: ways.keySet()) {
					long newSum = key + a.get(i);
					if (newSum <= setSize) {
						if (!ways.containsKey(newSum)) {
							HashSet<Long> newSet = new HashSet<Long>();
							newSet.add((long) i);
							newWays.put(newSum, new Pair<>(ways.get(key).first, newSet));
						} else {
							Pair<Long, HashSet<Long>> pair = ways.get(newSum);
							pair.first += ways.get(key).first;
							pair.second.add((long) i);
						}						
					}
				}
				ways = newWays;
			}
		}
		return ways;
	}
 
	public static String sanskar(int n, int k, ArrayList<Long> a) {
		long sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i);
		}
		if (sum % k != 0) {
			return "no";
		}
		long setSize = sum / k;
 
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > setSize) {
				return "no";
			} else if (a.get(i) == setSize) {
				a.remove(i--);
				k--;
			}	
		}
 
		Collections.sort(a);
		Collections.reverse(a);
 
		long[] setSizes = new long[k];
 
		long[] aa = new long[a.size()];
		for (int i = 0; i < aa.length; i++) {
			aa[i] = a.get(i);
		}
		
		HashMap<Long, Long> possible = getPossible(a, -1, -1);
		
		for (int i = 0; i < aa.length; i++) {
			if (!possible.containsKey(setSize - aa[i]) || possible.get(setSize - aa[i]) < k) {
				return "no";
			}
		}
		
		boolean res = dfs(aa, k, setSizes, setSize, 1, 1000, possible, 0);
 
		return res ? "yes" : "no";
	}
 
	public static boolean dfs(long[] a, int k, long[] setSizes, long setSize, int numComponents, int prevNumC, 
			HashMap<Long, Long> possibleInital, int depth) {
		int realSize = a.length;
		if (realSize > 0 && k > 0 && a[0] == setSize) {
			a[0] = 0;
			prevNumC = numComponents;
			numComponents = 0;
			k--;
			realSize--;
			//base++;
		}
		if (k == 0) {
			boolean valid = true;
			for (int i = 0; i < a.length; i++) {
				if (a[i] != 0) {
					valid = false;
				}
			}
			return valid;
		}
		if (realSize * 2 < k) {
			return false;
		}
		int start = 1;
		while (start < a.length && a[start] == 0) {
			start++;
		}
		
		boolean valid = false;
		for (int i = start; i < a.length; i++) {
			if (a[i] != 0 && a[0] + a[i] <= setSize) {
				valid = true;
			}
		}
		if (valid) {
			HashMap<Long, Long> possible = null;
			if (depth < 5) {
				possible = new HashMap<>();
				possible.put(0L, 1L);
				for (int i = 0; i < a.length; i++) {
					if (a[i] != 0) {
						HashMap<Long, Long> newWays = new HashMap<>(possible); // shallow copy
						for (long key: possible.keySet()) {
							long newSum = key + a[i];
							newWays.put(newSum, possible.get(key) + (possible.containsKey(newSum) ? possible.get(newSum) : 0));
						}
						possible = newWays;
					}
				}
			}
			for (int i = start; i < a.length; i++) {
				if (a[i] != 0 && a[0] + a[i] <= setSize && possibleInital.containsKey(setSize - a[0]) 
						&& (depth >= 5 || possible.containsKey(setSize - a[0]))) {
					long[] newA = new long[a.length-1];
					int t = 1;
					newA[0] = a[0] + a[i];
					for (int j = 1; j < a.length; j++) {
						if (a[j] != 0 && j != i) {
							newA[t++] = a[j];
						}
					}
					if (dfs(newA, k, setSizes, setSize, numComponents+1, prevNumC, possibleInital, depth+1)) {
						return true;
					}
				}
			}
		}
		return false;
	}
 
	public static String xorsub(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
 
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] a = new int[n];
			for (int j = 0; j < a.length; j++) {
				a[j] = scan.nextInt();
			}
			sb.append(xorsub(n, k, a)+"\n");
		}
		scan.close();
		return sb.toString();
	}
 
	public static String xorsub(int n, int k, int[] a) {
		boolean[] all = new boolean[1024+1];
		for (int i = 0; i < a.length; i++) {
			all[a[i]] = true;
		}
		boolean added = true;
		while (added) {
			added = false;
			for (int i = 0; i < all.length; i++) {
				if (all[i]) {
					for (int j = 0; j < all.length; j++) {
						if (all[j]) {
							int res = i ^ j;
							if (!all[res]) {
								all[res] = true;
								added = true;
							}
						}
					}
				}
			}
		}
		int max = 0;
		for (int i = 0; i < all.length; i++) {
			if (all[i]) {
				max = Math.max(max, i ^ k);
			}
		}
		return max + "";
	}
 
	public static String cApple(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
 
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int[] trees = new int[n];
			for (int j = 0; j < trees.length; j++) {
				trees[j] = scan.nextInt();
			}
			sb.append(appleF(n, trees)+"\n");
		}
		scan.close();
		return sb.toString();
	}
 
	public static String appleF(int n, int[] trees) {
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < trees.length; i++) {
			set.add(trees[i]);
		}
 
		return set.size()+"";
	}

	
	public static class Pair<A extends Comparable<A>, B> implements Comparable<Pair<A, B>> {
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
			return this.first.compareTo(o.first);
		}
	}
} 