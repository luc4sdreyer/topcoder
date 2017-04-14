import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
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
import java.math.BigInteger;


public class Main {
	public static InputReader in;
	public static PrintWriter out;
	
	public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, true);
//        out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

        SMARKET();
//        CHEFDIV5();
//        CLIQUED2();
//        CLIQUED();
//        ROWSOLD2();
//        DISHLIFE();
//        SIMDISH();
		
		out.close();
	}
	
	public static void SMARKET() {
		int tests = in.nextInt();
		for (int test = 0; test < tests; test++) {
			int N = in.nextInt();
			int Q = in.nextInt();
			int[] a = in.nextIntArray(N);
			
			int[] leftBound = new int[N];
			int[] rightBound = new int[N];
			int[] colour = new int[N];
			int[] colourStart = new int[N];
			int[] middleCompressedRange = new int[N];
			
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
			SegmentTree st = new SegmentTree(middleCompressedRange, true);
			
			for (int i = 0; i < Q; i++) {
				int L = in.nextInt() -1;
				int R = in.nextInt() -1;
				int k = in.nextInt();
				int ret = 0;
				
				if (colour[L] == colour[R]) {
					int range = R - L + 1;
					ret = range >= k ? 1 : 0;
				} else {
					int leftStart = L;
					int midStart = colourStart[colour[leftStart]+1];
					int rightStart = colourStart[colour[R]];
					int rightEnd = R;
					
					ret += leftBound[leftStart] >= k ? 1 : 0;
					ret += rightBound[rightEnd] >= k ? 1 : 0;
					if (midStart != rightStart) {
						// getting interesting...
						int u = colour[midStart];
						int v = colour[rightStart]-1;
						ret += st.query(u, v, k);
					}
				}
				out.println(ret);
			}
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

	public static class Node {
		public int count;
		public int repeatCount;
		public Node left, right;
		public char id;
		public static char counter = 'A';

		public Node(int count, Node left, Node right) {
			this.count = count;
			this.repeatCount = 1;
			this.left = left;
			this.right = right;
			this.id = Node.counter++;
		}
		
		public String toString() {
			return this.id + " " + count + " (" + (this.left == null ? '.' : this.left.id) + ":" + (this.right == null ? '.' : this.right.id) + ")"; 
		}

		public Node insert(int left, int right, int w) {
			if (left <= w && w < right) {
				// With in the range, we need a new node
				if (left+1 == right) {
					return new Node(this.count+1, null, null);
				}

				int mid = (left+right)>>1;

				return new Node(this.count+1, this.left.insert(left, mid, w), this.right.insert(mid, right, w));
			}

			// Out of range, we can use previous tree node.
			return this;
		}
	}

	public static class SegmentTree {
		int N;
		int inputLength;
		Node[] root;
		Node Null;
		
		// used for compression
		boolean do_compression;
		int[] reverseMap;
		
		/**
		 * Either the input must be already be compressed, or do_compression must be true 
		 * to let the tree do it's own compression.
		 */
		public SegmentTree(int[] a, boolean do_compression) {
			this.do_compression = do_compression;
			inputLength = a.length;
			N = 1 << (int) (Math.log10(inputLength)/Math.log10(2))+1;
			
			if (do_compression) {
				// apply compression
				
				reverseMap = new int[N];
				TreeMap<Integer, Integer> map = new TreeMap<>();
				HashMap<Integer, Integer> ref = new HashMap<>();
				for (int i = 0; i < a.length; i++) {
					if (!map.containsKey(a[i])) {
						map.put(a[i], 0);
					}
					map.put(a[i], map.get(a[i]) + 1);
				}
				
				int i = 0;
				for (Integer k: map.keySet()) {
					int repeats = map.get(k);
					ref.put(k, i);
					for (int j = 0; j < repeats; j++) {
						reverseMap[i] = k;
						i++;
					}
				}
				
				int[] compress = new int[a.length];
				int[] repCount = new int[a.length];
				for (i = 0; i < a.length; i++) {
					int ordinal = ref.get(a[i]);
					compress[i] = ordinal + repCount[ordinal];
					repCount[ordinal]++;
				}
				a = compress;
			}
			
			root = new Node[N];
			Null = new Node(0, null, null);
			Null.left = Null;
			Null.right = Null;
			
			for (int i = 0; i < inputLength; i++) {
				// Build a tree for each prefix using segment tree of previous prefix
				root[i] = (i == 0 ? Null : root[i-1]).insert(0, inputLength, a[i]);
			}
		}
		
		public int query(int u, int v, int k) {
			/**
			 * What would be the k-th number in a[u, v] (inclusive), if this segment was sorted?
			 * Based on http://www.spoj.com/problems/MKTHNUM/
			 */
			int ret = this.query_tree(root[v], (u==0?Null:root[u-1]), 0, inputLength, k);
			if (do_compression) {
				return reverseMap[ret];
			} else {
				return ret;
			}
		}

		private int query_tree(Node a, Node b, int left, int right, int k) {
			if (left+1 == right) {
				return left;
			}

			int mid = (left+right)>>1;
			int count = a.left.count - b.left.count;
			if (count >= k) {
				return query_tree(a.left, b.left, left, mid, k);
			}
			return query_tree(a.right, b.right, mid, right, k-count);
		}
	}

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

	public static void CHEFDIV4() {
		long time = System.currentTimeMillis();
		int limit = 1000000;
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
		System.out.println("precomp time: " + (System.currentTimeMillis() - time));
		
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
//			if (i < 1000) {
//				System.out.println(i + "\tmax " + max + "\tnumFactors " + numFactors[i] + "\tf " + f[i]+ " ");
//			}
		}
		

		long sum = 0;
		for (int i = A; i <= B; i++) {
			sum = sum + f[i];
		}
		out.println(sum);
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
