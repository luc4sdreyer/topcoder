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

public class Solution {
	public static void main(String[] args) {
		//worstPermutation(System.in);
		//numberList(System.in);
		//numberListTest();
		//supermanCelebratesDiwali(System.in);
		//subtreesAndPaths(System.in);
		//testSubtreesAndPaths();
		palindromicBorder(System.in);
		//testPalindromicBorder();
	}

	public static void testPalindromicBorder() {
		Random rand = new Random(1);
		char[] str = new char[20];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) (rand.nextInt(2) + 'a');
		}
		
		palindromicBorder(str);
	}

	public static void palindromicBorder(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] s = scan.nextLine().toCharArray();
		palindromicBorder(s);
	}

	private static void palindromicBorder(char[] s) {
		System.out.println("\t" + Arrays.toString(s));
		int numPalBor = 0; 
		int[][] map = new int[s.length][s.length];
		for (int i = 0; i < s.length; i++) {
			for (int j = i; j < s.length; j++) {
				for (int b = 1; b <= (j-i); b++) {
					boolean match = true;
					for (int m = 0; m+b <= (j-i); m++) {
						if (s[i+m] != s[i+m+b]) {
							match = false;
							break;
						}
						if (m <= (j-i-b)/2 && s[i+m+b] != s[j - m]) {
							match = false;
							break;
						}
					}
					if (match) {
						//String str = new String(s);
						//System.out.println(str.substring(i, j+1) + ": \t" + str.substring(i, j+1).substring(b));
						numPalBor++;
						map[j-i][i]++;
					}
				}
			}
		}
		for (int i = 0; i < map.length; i++) {
			System.out.print((i+1) + "\t[");
			for (int j = 0; j < map.length-i; j++) {
				System.out.print(map[i][j] == 0 ? " " : map[i][j]);
				System.out.print("  ");
			}
			System.out.println(".");
		}
		int numPal2 = 0;
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; i + j < s.length && i - j >= 0; j++) {
				if (s[i + j] == s[i - j]) {
					// all matching strings
					int numMatch = 0;
					for (int k = i+j+1; k < map.length; k++) {
						int match = 0;
						for (int m = i-j; m <= i+j && k + m < s.length; m++) {
							if (s[i-j + m] == s[k + m]) {
								match++;
							} else {
								break;
							}
						}
						if (match == j*2 + 1) {
							//numMatch++;
							numPal2++;
							String str = new String(s);
							System.out.println(str.substring(i-j, i+j+1) + ", "+i+": \t" + str.substring(k, k + j*2 + 1));
						}
					}
					//numPal2++;// += numMatch*(numMatch+1)/2;
				}
			}
		}
		System.out.println(numPalBor);
		System.out.println(numPal2);
	}

	public static void testSubtreesAndPaths() {
		String input = "5\n1 2\n2 3\n2 4\n5 1\n";
		Random rand = new Random();
		int tests = 10000;
		input += tests + "\n";
		for (int i = 0; i < tests; i++) {
			int a = rand.nextInt(5)+1;
			int b = rand.nextInt(5)+1;
			input += "max " + a + " " + b + "\n"; 
		}
		InputStream is = new ByteArrayInputStream( input.getBytes() );
		subtreesAndPaths(is);
	}

	public static void subtreesAndPaths(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		ArrayList<ArrayList<Integer>> tree = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			tree.add(new ArrayList<Integer>());
		}

		int[] parent = new int[n+1];
		
		boolean[] exists = new boolean[n+1];
		exists[1]= true;


		for (int i = 0; i < n-1; i++) {
			int a = scan.nextInt();
			int b = scan.nextInt();
			if (exists[a]) {
				tree.get(a).add(b);
				exists[b] = true;
				parent[b] = a;
			} else if (exists[b]) {
				tree.get(b).add(a);
				exists[a] = true;
				parent[a] = b;
			} else {
				System.out.println("fail");
			}
		}

		int nq = scan.nextInt();
		int[][] q = new int[nq][3];
		for (int i = 0; i < q.length; i++) {
			String s = scan.next();
			if (s.equals("add")) {
				q[i][0] = 0;				
			} else {
				q[i][0] = 1;
			}
			q[i][1] = scan.nextInt();
			q[i][2] = scan.nextInt();	
		}
		subtreesAndPaths(n, tree, q, parent, exists);
	}
	

	private static void subtreesAndPaths(int n, ArrayList<ArrayList<Integer>> tree, int[][] q, int[] parent, boolean[] exists) {
		
		int[] inorder = new int[n+1];
		int[] inorderLookup = new int[n+1];
		
		Stack<Integer> stack = new Stack<>();
		int top = 1;
		stack.push(top);
		int t = 0;
		boolean[] visited = new boolean[n+1];
		while (!stack.isEmpty()) {
			top = stack.peek();
			if (top != 0) {
				if (!visited[top]) {
					if (tree.get(top).isEmpty()) {
						stack.push(0);
					} else {
						stack.push(tree.get(top).get(0));
					}
				} else {
					//System.out.print(top + ", ");
					inorder[t++] = top;
					stack.pop();
					if (tree.get(top).size() < 2) {
						stack.push(0);
					} else {
						for (int i = 1; i < tree.get(top).size(); i++) {
							stack.push(tree.get(top).get(i));
						}
					}
				}
			} else {
				stack.pop();
				if (!stack.isEmpty()) {
					visited[stack.peek()] = true;
				}
			}
		}
		
		for (int i = 0; i < inorder.length; i++) {
			inorderLookup[inorder[i]] = i; 
		}
		
		int[][] bounds = new int[n+1][2];
		for (int i = 0; i < bounds.length; i++) {
			bounds[i][0] = Integer.MAX_VALUE;
		}
		int[] depth = new int[n+1];
		
		stack = new Stack<>();
		visited = new boolean[n+1];
		top = 1;
		stack.push(top);
		int maxDepth = 0;
		while (!stack.isEmpty()) {
			top = stack.pop();
			int child = 0;
			for (int i = 0; i < tree.get(top).size(); i++) {
				child = tree.get(top).get(i);
				stack.push(child);
				depth[child] = depth[parent[child]] + 1;
				maxDepth = Math.max(maxDepth, depth[child]);
			}
		}
		
		ArrayList<ArrayList<Integer>> byDepth = new ArrayList<>();
		for (int i = 0; i <= maxDepth; i++) {
			byDepth.add(new ArrayList<Integer>());
		}
		for (int i = 0; i <= n; i++) {
			byDepth.get(depth[i]).add(i);
		}
		
		for (int i = maxDepth; i >= 0; i--) {
			for (int j = 0; j < byDepth.get(i).size(); j++) {
				int node = byDepth.get(i).get(j);
//				if (tree.get(node).isEmpty()) {
//					bounds[inorderLookup[node]][0] = inorderLookup[node];
//					bounds[inorderLookup[node]][1] = inorderLookup[node];
//				}
				bounds[inorderLookup[node]][0] = Math.min(bounds[inorderLookup[node]][0], inorderLookup[node]);
				bounds[inorderLookup[node]][1] = Math.max(bounds[inorderLookup[node]][1], inorderLookup[node]);
				
				bounds[inorderLookup[parent[node]]][0] = Math.min(bounds[inorderLookup[parent[node]]][0], bounds[inorderLookup[node]][0]);
				bounds[inorderLookup[parent[node]]][1] = Math.max(bounds[inorderLookup[parent[node]]][1], bounds[inorderLookup[node]][1]);
			}
		}
		
		int[] temp = new int[n];
		SegmentTreeVerbose st = new SegmentTreeVerbose(temp);
		
		for (int i = 0; i < q.length; i++) {;
			if (q[i][0] == 0) {
				int root = q[i][1];
				int v = q[i][2];
				st.update_tree(bounds[inorderLookup[root]][0], bounds[inorderLookup[root]][1], v);				
			} else {
				int a = q[i][1];
				int b = q[i][2];
				
				stack = new Stack<Integer>();
				top = a;
				stack.push(top);
				visited = new boolean[n+1];
				int[] prev = new int[n+1];
				while (!stack.isEmpty()) {
					top = stack.pop();
					visited[top] = true;
					if (top == b) {
						break;
					}
					for (int j = 0; j < tree.get(top).size(); j++) {
						int child = tree.get(top).get(j);
						if (!visited[child]) {
							stack.push(child);
							prev[child] = top;
						}
					}
					int par = parent[top];
					if (par != 0 && !visited[par]) {
						stack.push(par);
						prev[par] = top;
					}
				}
				
				if (top != b) {
					int[] aa = {0};
					int ab = aa[10];
				}
				
				int max = Integer.MIN_VALUE;
				max = Math.max(max, st.query_tree(inorderLookup[top], inorderLookup[top]));
				while (top != a) {
					top = prev[top];
					max = Math.max(max, st.query_tree(inorderLookup[top], inorderLookup[top]));
				}
				System.out.println(max);
			}
		}
	}


	public static class SegmentTreeVerbose {
		private int tree[];
		private int lazy[];
		private int N;
		private int MAX;
		private int inf = Integer.MAX_VALUE;

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected int function(int a, int b) {
			return Math.max(a, b);
		}
		
		protected int IDENTITY = 0;

		public SegmentTreeVerbose(int[] a) {
			N = 1 << (int) (Math.log10(a.length)/Math.log10(2))+1;
			MAX = N*2;

			int[] arr = new int[N];
			for (int i = 0; i < a.length; i++) {
				arr[i] = a[i];
			}

			tree = new int[MAX];
			lazy = new int[MAX];	 
			build_tree(1, 0, N-1, arr);
			Arrays.fill(lazy, 0);
		}
		/**
		 * Build and init tree
		 */
		private void build_tree(int node, int a, int b, int[] arr) {
			if(a > b) return; // Out of range

			if(a == b) { // Leaf node
				tree[node] = arr[a]; // Init value
				return;
			}

			build_tree(node*2, a, (a+b)/2, arr); // Init left child
			build_tree(node*2+1, 1+(a+b)/2, b, arr); // Init right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Init root value
		}

		/**
		 * Increment elements within range [i, j] with value value
		 */
		public void update_tree(int i, int j, int value) {
			update_tree(1, 0, N-1, i, j, value);
		}

		private void update_tree(int node, int a, int b, int i, int j, int value) {

			if(lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if(a != b) {
					lazy[node*2] += lazy[node]; // Mark child as lazy
					lazy[node*2+1] += lazy[node]; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if(a > b || a > j || b < i) // Current segment is not within range [i, j]
				return;

			if(a >= i && b <= j) { // Segment is fully within range
				tree[node] += value;

				if(a != b) { // Not leaf node
					lazy[node*2] += value;
					lazy[node*2+1] += value;
				}

				return;
			}

			update_tree(node*2, a, (a+b)/2, i, j, value); // Updating left child
			update_tree(1+node*2, 1+(a+b)/2, b, i, j, value); // Updating right child

			tree[node] = function(tree[node*2], tree[node*2+1]); // Updating root with max value
		}

		/**
		 * Query tree to get max element value within range [i, j]
		 */
		public int query_tree(int i, int j) {
			return query_tree(1, 0, N-1, i, j);
		}

		private int query_tree(int node, int a, int b, int i, int j) {
			if(a > b || a > j || b < i) return -inf; // Out of range

			if(lazy[node] != 0) { // This node needs to be updated
				tree[node] += lazy[node]; // Update it

				if(a != b) {
					lazy[node*2] += lazy[node]; // Mark child as lazy
					lazy[node*2+1] += lazy[node]; // Mark child as lazy
				}

				lazy[node] = 0; // Reset it
			}

			if(a >= i && b <= j) // Current segment is totally within range [i, j]
				return tree[node];

			int q1 = query_tree(node*2, a, (a+b)/2, i, j); // Query left child
			int q2 = query_tree(1+node*2, 1+(a+b)/2, b, i, j); // Query right child

			int res = function(q1, q2); // Return final result

			return res;
		}
	}

	public static void supermanCelebratesDiwali(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int h = scan.nextInt();
		int loss = scan.nextInt();
		int[][] people = new int[h+1][n];
		for (int i = 0; i < n; i++) {
			int m = scan.nextInt();
			int[] a = scan.nextIntArray(m);
			for (int j = 0; j < a.length; j++) {
				people[a[j]][i]++;
			}
		}
		int[][] dp = new int[h+1][n];
		for (int j = h; j >= 0; j--) {
//			for (int i = 0; i < n; i++) {
//				for (int k = 0; k < n; k++) {
//					int dist = 1;
//					if (k != i) {
//						dist = loss;
//					}
//					dp[j][i] = Math.max(dp[j][i], people[j][i] + (j+dist <= h ? dp[j+dist][k] : 0));
//				}				
//			}
			int max = 0;
			for (int i = 0; i < n; i++) {
				max = Math.max(max, (j+loss <= h ? dp[j+loss][i] : 0));
			}
			for (int i = 0; i < n; i++) {
				dp[j][i] = people[j][i] + Math.max(max, (j+1 <= h ? dp[j+1][i] : 0));
			}
		}
		int max = 0;
		for (int i = 0; i < n; i++) {
			max = Math.max(dp[0][i], max);
		}
		System.out.println(max);
	}

	public static void numberListTest() {
		int numTests = 100000;
		int range = 20;
		Random rand = new Random(0);
		for (int t = 0; t < numTests; t++) {
			int len = rand.nextInt(range)+1;
			int[] a = new int[len];
			for (int i = 0; i < a.length; i++) {
				a[i] = rand.nextInt(range)+1;
			}
			int k = rand.nextInt(range+10);
			long exp = numberList3(a.length, k, a);
			long act = numberList2(a.length, k, a);
			if (exp != act) {
				System.out.println("fail");
			}
				
		}
	}

	public static void numberList(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			System.out.println(numberList2(n, k, a));
		}
	}

	public static long numberList3(int n, int k, int[] a) {
		int c = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i; j < a.length; j++) {
				int max = 0;
				for (int m = i; m <= j; m++) {
					max = Math.max(max, a[m]);
				}
				if (max > k) {
					c++;
				}
			}
		}
		return c;
	}

	public static long numberList2(int n, int k, int[] a) {
		long freq = (long)n*(n+1)/2;
		long len = 0;
		for (int i = 1; i < a.length; i++) {
			if (i == 0) {
				if (a[i] <= k) {
					len = 1;
				}
			} else {
				if (a[i-1] <= k) {
					len++;
				} else {
					freq -= len*(len+1)/2;
					len = 0;
				}
			}
		}
		if (a[n-1] <= k) {
			len++;
		}
		freq -= len*(len+1)/2;
		return freq;
	}

	public static long numberList(int n, int k, int[] a) {
		long freq = 0;
		//long[] f = new long[10];
		for (int i = 0; i < a.length; i++) {
			int right = 1;
			for (int j = i+1; j < a.length && a[j] < a[i]; j++) {
				right++;
			}
			int left = 1;
			for (int j = i-1; j >= 0 && a[j] < a[i]; j--) {
				left++;
			}
			//f[a[i]] += (long)left * right;
			if (a[i] > k) {
				freq += (long)left * right;	
			}
		}
		return freq;
		//System.out.println(freq);
		//System.out.println(Arrays.toString(f));
	}

	public static void worstPermutation(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int[] pos = new int[n+1];
		for (int i = 0; i < a.length; i++) {
			pos[a[i]] = i;
		}
		
		for (int i = 0; i < a.length && k > 0; i++) {
			if (pos[n-i] > i) {
				int temp = a[i];
				a[i] = a[pos[n-i]];
				a[pos[n-i]] = temp;
				
				pos[a[pos[n-i]]] = pos[n-i];
				pos[a[i]] = i;
				k--;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.length; i++) {
			sb.append(a[i] + " ");
		}
		System.out.println(sb);
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
