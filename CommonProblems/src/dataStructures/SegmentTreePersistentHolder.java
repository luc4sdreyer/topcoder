package dataStructures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Based on anudeeps's segment tree: https://github.com/anudeep2011/programming/blob/master/MKTHNUM.cpp
 */

public class SegmentTreePersistentHolder {
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
	
	public static InputReader in;
	public static PrintWriter out;
	
	public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, true);

		int n = in.nextInt();
		int m = in.nextInt();
		
		int N = n;
		int[] a = new int[N];
		int[] RM = new int[N];
		

		// range compression
		TreeMap<Integer, Integer> M = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			M.put(a[i], 0);
		}
		int maxI = 0;

		for (Integer it: M.keySet()) {
			M.put(it, maxI);
			RM[maxI] = it;
			maxI++;
		}
		
		int[] compressed = new int[maxI];
		for (int i = 0; i < compressed.length; i++) {
			compressed[i] = M.get(a[i]);
		}
		
		SegmentTree st = new SegmentTree(compressed, false);

		for (int i = 0; i < m; i++) {
			int u = in.nextInt() -1;
			int v = in.nextInt() -1;
			int k = in.nextInt();

			int ans = st.query(u, v, k);
			System.out.println(RM[ans]);
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