

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class VariousAlgorithms {


	/*******************************************************************************************************************************
	 * DEPTH First Search in an array (DFS)
	 * 
	 * The depth first search is well geared towards problems where we want to find any
	 * solution to the problem (not necessarily the shortest path), or to visit all of
	 * the nodes in the graph. 
	 */

	public boolean[][] bitmap = new boolean[600][400];

	private class NodeDfs {
		public int x, y;
		public NodeDfs (int x, int y) {
			this.x = x;this.y = y;
		}
	}

	int doFill(int x, int y) {
		int result = 0;

		// Declare stack of nodes
		Stack<NodeDfs> s = new Stack<NodeDfs>();
		s.push(new NodeDfs(x, y));

		while (s.empty() == false) {
			NodeDfs top = s.pop();

			if (top.x < 0 || top.x > bitmap.length) continue;
			if (top.y < 0 || top.y > bitmap[0].length) continue;
			if (bitmap[top.x][top.y]) continue;

			bitmap[top.x][top.y] = true; // Record visit

			result++;

			// visit every adjacent node
			s.push(new NodeDfs(top.x + 1, top.y));
			s.push(new NodeDfs(top.x - 1, top.y));
			s.push(new NodeDfs(top.x, top.y + 1));
			s.push(new NodeDfs(top.x, top.y - 1));
		}
		return result;
	}

	/*******************************************************************************************************************************
	 * BREADTH First Search in an array (BFS)
	 * 
	 * It has the extremely useful property that if all of the edges in a graph are
	 * unweighted (or the same weight) then the first time a node is visited is the
	 * shortest path to that node from the source node.
	 */

	private int targetX;
	private int targetY;

	class Node implements Comparable<Node> {
		public int cost, x, y;

		public Node(int cost) {
			this.cost = cost;
		}

		public Node(int cost, int x, int y) {
			this.cost = cost;
			this.x = x;
			this.y = y;
		}		

		public String toString() {
			return "(" + x + ", "+ y + ": " + cost + ")";
		}

		@Override
		public int compareTo(Node o) {
			Node next = (Node)o;
			if (cost < next.cost) return -1;
			if (cost > next.cost) return 1;
			if (y < next.y) return -1;
			if (y > next.y) return 1;
			if (x < next.x) return -1;
			if (x > next.x) return 1;
			return 0;
		}
	}

	public Node bfs(int x, int y) {
		// Declare stack of nodes
		Queue<Node> q = new LinkedList<Node>();
		q.add(new Node(x, y, (int)0));

		while (q.size() != 0) {
			Node top = q.poll();

			if (top.x < 0 || top.x >= bitmap.length) continue;
			if (top.y < 0 || top.y >= bitmap[0].length) continue;
			if(bitmap[top.x][top.y]) continue;
			bitmap[top.x][top.y] = true;
			if ((top.x == this.targetX) && (top.y ==this.targetY)) {
				return top;
			}

			// visit every adjacent node
			q.add(new Node((int)(top.x + 1), top.y, (int)(top.cost+1)));
			q.add(new Node((int)(top.x - 1), top.y, (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y + 1), (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y - 1), (int)(top.cost+1)));
		}
		return null;
	}

	/*******************************************************************************************************************************
	 * Dijkstra's algorithm (Uniform cost search)
	 * 
	 * A lot like a breath first search, except it can be used in a weighted graph.
	 * The first time a node is visited is the shortest path to that node from the source node.
	 * Uses a Priority Queue (Heap, get best node in O(log n))
	 */
	public Node dijkstra() {
		PriorityQueue<Node> q = new PriorityQueue<Node>();

		while (q.size() != 0) {
			Node top = q.poll();

			if (top.x < 0 || top.x >= bitmap.length) continue;
			if (top.y < 0 || top.y >= bitmap[0].length) continue;
			if(bitmap[top.x][top.y]) continue;
			bitmap[top.x][top.y] = true;
			if ((top.x == this.targetX) && (top.y ==this.targetY)) {
				return top;
			}

			// visit every adjacent node
			q.add(new Node((int)(top.x + 1), top.y, (int)(top.cost+1)));
			q.add(new Node((int)(top.x - 1), top.y, (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y + 1), (int)(top.cost+1)));
			q.add(new Node(top.x, (int)(top.y - 1), (int)(top.cost+1)));
		}
		return null;
	}

	/*******************************************************************************************************************************
	 * Floyd Warshall All-Pairs Shortest Path algorithm
	 * 
	 * For when the graph is represented by an adjacency matrix. It runs in O(n^3) time, where n is the number of vertices in the
	 * graph. However, in comparison to Dijkstra, which only gives us the shortest path from one source to the targets,
	 * Floyd-Warshall gives us the shortest paths from all source to all target nodes.
	 * 
	 * This algorithm assumes that there are no negative cycles in the graph.
	 */
	public void floydWarshall(int[][] graph) {
		int N = graph.length;

		int[][] shortestPaths = new int[N][N];
		for (int k = 0; k < shortestPaths.length; k++) {
			for (int i = 0; i < shortestPaths.length; i++) {
				for (int j = 0; j < shortestPaths.length; j++) {
					shortestPaths[i][j] = Math.min(shortestPaths[i][j], shortestPaths[i][k] + shortestPaths[k][j]);
				}
			}
		}
	}

	public void floydWarshall(String[] data) {
		int n = data.length;
		int[][] sp = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (data[i].charAt(j) == 'Y') {
					sp[i][j] = 1;
				} else {
					sp[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		for (int k = 0; k < sp.length; k++) {
			for (int i = 0; i < sp.length; i++) {
				for (int j = 0; j < sp.length; j++) {
					if (sp[i][k] != Integer.MAX_VALUE && sp[k][j] != Integer.MAX_VALUE) {
						sp[i][j] = Math.min(sp[i][j], sp[i][k] + sp[k][j]);
					}
				}
			}
		}
	}

	public static class ListNode {
		public int node;
		public int weight;
		public ListNode(int node, int weight) {
			this.node = node;
			this.weight = weight;
		}
		public String toString() {
			return "[n: " + this.node + ", w: " + this.weight + "]";
		}
	}
	
	/*******************************************************************************************************************************
	 * Bellman Ford shortest path algorithm
	 * 
	 * Dijkstra and Bellman Ford are both single source shortest path algorithms. That is, given the source, find the shortest
	 * path to all other nodes in the graph starting from this source node. But Dijkstra is O(m*log(n)) and Bellman Ford is O(m*n).
	 * BUT unlike Dijkstra, Bellman Ford works when the edge costs are negative! This algorithm can also detect the presence of
	 * negative cycles in the graph if there are any.
	 */
	public static int bellmanFord(ArrayList<ArrayList<ListNode>> list) {
//		for (int i = 0; i < N; i++) {
//			list.add(new ArrayList<ListNode>());
//		}
//		for (int i = 0; i < s.length; i++) {;
//			ListNode temp = new ListNode(t[i]-1, weight[i]);
//			list.get(s[i]-1).add(temp);
//		}
		int N = list.size();
		int[] d = new int[N];
		Arrays.fill(d, Integer.MAX_VALUE);
		d[0] = 0;
		boolean updated = false;
		for (int n = 0; n < N-1; n++) { // yes, NOT n!! at most n-1 edges on the shortest path
			updated = false;
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).size(); j++) {
					if (d[list.get(i).get(j).node] > d[i] + list.get(i).get(j).weight) {
						d[list.get(i).get(j).node] = d[i] + list.get(i).get(j).weight;
						updated = true;
					}
				}
			}
			if (!updated) {
				break;
			}
		}

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				if (d[list.get(i).get(j).node] > d[i] + list.get(i).get(j).weight) {
					// Negative cycle!!
					d[list.get(i).get(j).node] = Integer.MAX_VALUE;
				}
			}
		}
		return d[N-1];
	}

	/*******************************************************************************************************************************
	 * Maximum Bipartite Matching (simple, unweighted)
	 * 
	 * Example problem: There are M job applicants and N jobs. Each applicant has a subset of jobs that he/she is interested in.
	 * Each job opening can only accept one applicant and a job applicant can be appointed for only one job. Find an assignment of
	 * jobs to applicants in such that as many applicants as possible get jobs.
	 * 
	 * Algorithm is O(n^3)
	 */
	// A DFS based recursive function that returns true if a
	// matching for vertex u is possible
	boolean bpm(boolean[][] bpGraph, int u, boolean[] seen, int[] matchR)
	{
		int N = bpGraph[0].length;
		// Try every job one by one
		for (int v = 0; v < N; v++)
		{
			// If applicant u is interested in job v and v is
			// not visited
			if (bpGraph[u][v] && !seen[v])
			{
				seen[v] = true; // Mark v as visited

				// If job 'v' is not assigned to an applicant OR
				// previously assigned applicant for job v (which is matchR[v]) 
				// has an alternate job available. 
				// Since v is marked as visited in the above line, matchR[v] 
				// in the following recursive call will not get job 'v' again
				if (matchR[v] < 0 || bpm(bpGraph, matchR[v], seen, matchR))
				{
					matchR[v] = u;
					return true;
				}
			}
		}
		return false;
	}

	// Returns maximum number of matching from M to N
	int maxBPM(boolean[][] bpGraph)
	{
		int M = bpGraph.length;
		int N = bpGraph[0].length;
		// An array to keep track of the applicants assigned to
		// jobs. The value of matchR[i] is the applicant number
		// assigned to job i, the value -1 indicates nobody is
		// assigned.
		int[] matchR = new int[N];

		// Initially all jobs are available
		Arrays.fill(matchR, -1);

		int result = 0; // Count of jobs assigned to applicants
		for (int u = 0; u < M; u++)
		{
			// Mark all jobs as not seen for next applicant.
			boolean[] seen = new boolean[N];

			// Find if the applicant 'u' can get a job
			if (bpm(bpGraph, u, seen, matchR))
				result++;
		}
		return result;
	}

	public void maxBpmTest() {
		//	    boolean bpGraph[][] = { {false, true, true, false, false, false},
		//	                        {true, false, false, true, false, false},
		//	                        {false, false, true, false, false, false},
		//	                        {false, false, true, true, false, false},
		//	                        {false, false, false, false, false, false},
		//	                        {false, false, false, false, false, true}
		//	                      };
		
//		boolean bpGraph[][] = {
//				{true, true, true},
//				{true, true, true},
//				{true, true, true}
//		};
		int n = 1000;
		boolean[][] bpGraph = new boolean[n][n];
		for (int i = 0; i < bpGraph.length; i++) {
			for (int j = 0; j < bpGraph.length; j++) {
				if (Math.random() < 0.51) {
					bpGraph[i][j] = true;
				}
			}
		}

		System.out.println("Maximum number of applicants that can get job is: " + maxBPM(bpGraph));
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
	 * [1, 1, 1, 2, 2, 4, 4, 4, 5, 5, 6, 5, 5, 4, 4, 4, 2, 2, 1, 1, 1]
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
	public boolean next_permutation(char str[]) {
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
	
	public boolean next_permutation(int str[]) {
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
		int N = 1 << list.length;
		for (int n = 0; n < N; n++) {
			boolean[] active = new boolean[list.length];
			for (int i = 0; i < list.length; i++) {
				if (((1 << i) & n) != 0) {
					active[i] = true;
				}
			}
		}
	}

	/*******************************************************************************************************************************
	 * Iteration of a set/array: k combinations of a set of n, in other words: counting in base n.
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
	 * Speed: About 1 second to run getPrimes(10^6)
	 * 
	 * Memory use is O(n), speed is O(n), both better than a normal Sieve of Eratosthenes.
	 *  
	 * Explanation of the speed: There is a nested loop but the number of times the loop
	 * runs is limited. The loop will run n/2 times, then n/3, then n/5, then n/7 and so on for every
	 * prime <= n. The series does not converge but it grows very slowly: this series is a subset of the 
	 * harmonic series (1, 1/2, 1/3, ... 1/n) that sums to 23 for n < 2^32. Therefore the sum of this
	 * subset will be smaller than 23*n, and therefore the time complexity is O(n). 
	 */
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
					ops++;
				}
			}
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
	
	public static long lcm(long a, long b)
	{
		return BigInteger.valueOf(b).multiply(BigInteger.valueOf(a)).divide(BigInteger.valueOf(a).gcd(BigInteger.valueOf(b))).longValue();
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
	 * Calculate the right side of Pascal's triangle (aka binomial coefficients). 
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
	 * Tree diameter, or the maximum distance between two nodes in a tree. 
	 * 
	 * The basic idea is:
	 * 1. DFS from a random node
	 * 2. Choose the node the farthest away and DFS from it.
	 * 3. Now the farthest node is equal to the diameter. 
	 * 
	 * This code assumes the input as lines of triplets, indicating two vertices and the weight of their connection.
	 * 
	 * Complexity: O(n)
	 */
	
	public static long treeDiameter() {
		Scanner scan = new Scanner(System.in);
		HashMap<Integer, ArrayList<int[]>> map = new HashMap<>();
		int n = scan.nextInt();
		int start = -1; 
		for (int i = 0; i < n-1; i++) {
			int a = scan.nextInt();
			if (start == -1) {
				start = a;
			}
			int b = scan.nextInt();
			int v = scan.nextInt();
			
			if (!map.containsKey(a)) {
				map.put(a, new ArrayList<int[]>());
			}
			map.get(a).add(new int[]{b, v});
			
			if (!map.containsKey(b)) {
				map.put(b, new ArrayList<int[]>());
			}
			map.get(b).add(new int[]{a, v});
		}
		scan.close();
		
		// First DFS
		
		long[] top = {start, 0};
		Stack<long[]> s = new Stack<long[]>();
		HashSet<Long> visited = new HashSet<>();
		s.add(top);
		long max = 0;
		long maxIdx = 0;
		while (!s.isEmpty()) {
			top = s.pop();
			if (visited.contains(top[0])) {
				continue;
			}
			visited.add(top[0]);
			if (max < top[1]) {
				max = top[1];
				maxIdx = top[0];
			}
			if (map.containsKey((int)top[0])) {
				ArrayList<int[]> children = map.get((int)top[0]);
				for (int i = 0; i < children.size(); i++) {
					s.push(new long[]{children.get(i)[0], top[1] + children.get(i)[1]});
				}
			}
		}
		
		// Second DFS
		
		top[0] = maxIdx;
		top[1] = 0;
		s = new Stack<long[]>();
		visited = new HashSet<>();
		s.add(top);
		max = 0;
		maxIdx = 0;
		while (!s.isEmpty()) {
			top = s.pop();
			if (visited.contains(top[0])) {
				continue;
			}
			visited.add(top[0]);
			if (max < top[1]) {
				max = top[1];
				maxIdx = top[0];
			}
			if (map.containsKey((int)top[0])) {
				ArrayList<int[]> children = map.get((int)top[0]);
				for (int i = 0; i < children.size(); i++) {
					s.push(new long[]{children.get(i)[0], top[1] + children.get(i)[1]});
				}
			}
		}
		
		return max;
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
	 * Standard Topsort. 
	 * 
	 * A topological sort (sometimes abbreviated topsort or toposort) or topological ordering of a directed graph is a linear
	 * ordering of its vertices such that for every directed edge uv from vertex u to vertex v, u comes before v in the ordering.
	 * 
	 * Returns false if the graph is not a DAG (contains a directed cycle).
	 * 
	 * O(n) time
	 */		
	@SuppressWarnings("unchecked")
	public static boolean topSort(ArrayList<Integer> order, HashMap<Integer, ArrayList<Integer>> graphRef) {
		
		// Create a copy of the graph
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
		for (Integer key: graphRef.keySet()) {
			graph.put(key, (ArrayList<Integer>) graphRef.get(key).clone());
		}
		
		// Count number of incoming edges
		HashMap<Integer, Integer> numIncoming = new HashMap<>();
		for (Integer key: graph.keySet()) {
			ArrayList<Integer> children = graph.get(key);
			for (Integer child: children) {
				numIncoming.put(child, numIncoming.containsKey(child) ? numIncoming.get(child) + 1 : 1);
			}
		}
		
		// Select vertices with no incoming edges
		Stack<Integer> roots = new Stack<>(); // Doesn't have to be a stack!
		for (Integer key: graph.keySet()) {
			if (!numIncoming.containsKey(key) || numIncoming.get(key) == 0) {
				roots.push(key);
			}
		}
		
		// Build the ordering
		while (!roots.isEmpty()) {
			Integer top = roots.pop();
			order.add(top);
			ArrayList<Integer> children = graph.get(top);
			for (int i = 0; i < children.size(); i++) {
				Integer rem = children.remove(i--);
				numIncoming.put(rem, numIncoming.get(rem) - 1);
				if (numIncoming.get(rem) == 0) {
					roots.push(rem);
				}
			}
		}
		
		// Count number of edges
		int edges = 0;
		for (Integer key: graph.keySet()) {
			edges += graph.get(key).size();
		}
		
		return edges > 0 ? false : true;
	}
	
	static int ops = 1;
	public static void main(String[] args) {
		int res = fastModularExponent(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		System.out.println(kmp("ABCABCABDABCAB", "ABCABD"));
		
		int TEST_PRIMES = 1;
		int TEST_BPM = 2;
		int TEST_ALL_COMBINATIONS = 3;
		int TEST_MAXIMUM_SLICE = 4;
		int TEST_BINARY_SEARCH = 5;
		int TEST_NEXT_NUMBER = 6;
		
		int testType = TEST_NEXT_NUMBER;
		
		long start = System.currentTimeMillis();
		if (testType == TEST_PRIMES) {
			long n = 2;
			long t = System.currentTimeMillis();
			for (int i = 0; i < 20; i++) {
				t = System.currentTimeMillis();
				long bound = 23 * n;
				getPrimes(n);
				System.out.printf("n: %-10d ops: %-10d bound: %-10d bound extra: %-15f CPU cycles per op: %-10d\n",
						n, ops, bound, (bound/(double)ops), (System.currentTimeMillis() - t) * 3000000L / ops);
				n *= 2;
				ops = 0;
			}
			
			n = 20000;
			t = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				getPrimes(n);
			}
			System.out.println("getPrimes(" + n + "): " + (System.currentTimeMillis() - t)/(float)1000 + " ms");
			
			t = System.currentTimeMillis();
			for (int i = 0; i < 10; i++) {
				getPrimesSlow((int) n);
			}
			System.out.println("getPrimesSlow(" + n + "): " + (System.currentTimeMillis() - t)/(float)10 + " ms");
		} else if (testType == TEST_BPM) {
			for (int i = 0; i < 5; i++) {			
				long time = System.currentTimeMillis();
				(new VariousAlgorithms()).maxBpmTest();
				System.out.println("Total time: " + (System.currentTimeMillis() - time));
			}		
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
		}
		System.out.println("total time: " + (System.currentTimeMillis() - start) + " ms");
		
	}
}
