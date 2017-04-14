package dataStructures;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

public class SegmentTreePersistentTest {

/**
 * Based on anudeeps's segment tree: https://github.com/anudeep2011/programming/blob/master/MKTHNUM.cpp
 */

	static boolean debug = false;
	
	public static class Node {
		public int count;
		public int repeatCount;
		public Node left, right;
		public char id;
		public static char counter = 'A';
		public int value;

		public Node(int count, Node left, Node right, Node parent, int value) {
			this.value = value;
			init(count, left, right, parent);
		}

		public Node(int count, Node left, Node right, Node parent) {
			init(count, left, right, parent);
		}
		
		public void init(int count, Node left, Node right, Node parent) {
			this.count = count;
			this.repeatCount = 1;
			this.left = left;
			this.right = right;
			this.id = Node.counter++;
			if (debug) System.out.println("new Node ["+this+"], left: [" + left + "]\t right: [" + right + "]\t parent: " + parent);
		}
		
		public String toString() {
			return this.id + " " + count + " (" + (this.left == null ? '.' : this.left.id) + ":" + (this.right == null ? '.' : this.right.id) + ")"; 
		}

		public Node insert(int left, int right, int w) {
			if (debug) System.out.println("internal insert: left " + left + "\t right " + right + "\t w " + w);
			if (left <= w && w < right) {
				// With in the range, we need a new node
				if (left+1 == right) {
					return new Node(this.count+1, null, null, this, left);
				}

				int mid = (left+right)>>1;

				return new Node(this.count+1, this.left.insert(left, mid, w), this.right.insert(mid, right, w), this);
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
		
		TreeMap<Integer, Integer> repCount;
		
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
				repCount = new TreeMap<>();
				HashMap<Integer, Integer> ref = new HashMap<>();
				for (int i = 0; i < a.length; i++) {
					if (!repCount.containsKey(a[i])) {
						repCount.put(a[i], 0);
					}
					repCount.put(a[i], repCount.get(a[i]) + 1);
				}
				
				int i = 0;
				for (Integer k: repCount.keySet()) {
					ref.put(k, i);
					reverseMap[i] = k;
					i++;
				}
				
				int[] compress = new int[a.length];
				int[] rCount = new int[a.length];
				for (i = 0; i < a.length; i++) {
					int ordinal = ref.get(a[i]);
					compress[i] = ordinal + rCount[ordinal];
					rCount[ordinal]++;
				}
				
				// above does compression but not to rank
				
				a = compress;
				
//				int i = 0;
//				for (Integer k: map.keySet()) {
//					ref.put(k, i);
//					reverseMap[i] = k;
//					i++;
//				}
//
//				int[] compress = new int[i];
//				for (Integer k: map.keySet()) {
//					ref.put(k, i);
//					reverseMap[i] = k;
//					i++;
//				}
//				
//				for (i = 0; i < a.length; i++) {
//					compress[i] = ref.get(a[i]);
//				}
//				a = compress;
			}
			
			root = new Node[N];
			Null = new Node(0, null, null, null);
			Null.left = Null;
			Null.right = Null;
			
			for (int i = 0; i < inputLength; i++) {
				// Build a tree for each prefix using segment tree of previous prefix
				if (debug) System.out.println("\n\ninserting index " + i + ": " + a[i]);
				root[i] = (i == 0 ? Null : root[i-1]).insert(0, inputLength, a[i]);
			}
		}
		
		public int query(int u, int v, int k) {
			/**
			 * What would the k-th number (1-indexed) in a[u, v] (inclusive) be, if this segment was sorted?
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
				if (debug) System.out.println("Q left: " + left + " right: " + right + " a: " + a + " b: " + b + " k: " + k + " found leaf node!");
				return left;
			}

			int mid = (left+right)>>1;
			@SuppressWarnings("unused")
			int newCount = a.left.count - b.left.count + (!repCount.containsKey(a.left.value) ? 1 : repCount.get(a.left.value)) - 
					(!repCount.containsKey(b.left.value) ? 1 : repCount.get(b.left.value));
			int oldCount = a.left.count - b.left.count;
			int count = oldCount;
			if (count >= k) {
				if (debug) System.out.println("Q left: " + left + " right: " + right + " a: " + a + " b: " + b + " k: " + k + " count: " + count + " going <");
				return query_tree(a.left, b.left, left, mid, k);
			}
			if (debug) System.out.println("Q left: " + left + " right: " + right + " a: " + a + " b: " + b + " k: " + k + " count: " + count + " going >");
			return query_tree(a.right, b.right, mid, right, k-count);
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/
	
	
	public static InputReader in;
	public static PrintWriter out;
	
	public static void main(String[] args) {
		{
			int[][] data2 = new int[][]{
				{0, 4, 1, 5, 2, 6, 3, 0},
				{0, 0, 4, 1, 5, 2, 6, 3},
				{0, 4, 1, 5, 4, 2, 6, 3},
				//{0, 0, 0, 4, 1, 5, 2, 6, 3},
			};
			int failCount = 0;
			for (int id = 0; id < data2.length; id++) {
				int[] data = data2[id];
				SegmentTree st = new SegmentTree(data, true);
				for (int a = 0; a < data.length; a++) {
					for (int b = a; b < data.length; b++) {
						for (int k = 0; k <= b-a; k++) {
							int extra2 = 0;
							if (id == 0) {
								if (b >= 7) {
									if (a == 0 && k >= 1) {
										extra2 = 1;
									}
									if (a > 0 && k < 1) {
										extra2 = -1;
									}
								}
							} else if (id == 1) {
								if (b >= 1) {
									if (a == 0 && k >= 1) {
										extra2 = 1;
									}
									if (a == 1 && k < 1) {
										extra2 = -1;
									}
								}
							} else if (id == 2) {
//								if (b >= 4) {
//									if (a == 0 && k >= 5) {
//										extra2 = 1;
//									}
//									if (a == 1 && k < 5) {
//										extra2 = -1;
//									}
//								}
							} else if (id == 3) {
								if (b >= 1) {
									if (a == 0) {
										if (k == 1) {
											extra2 = 1;
										}
										if (k >= 2) {
											extra2 = 2;
										}
									}
									if (a == 1 && k < 1) {
										extra2 = -1;
									}
								}
							}
							int r1 = st.query(a, b, k+1+extra2);
							
							TreeSet<Integer> x = new TreeSet<>();
							ArrayList<Integer> y = new ArrayList<>();
							for (int i = a; i <= b; i++) {
								x.add(data[i]);
							}
							for (Integer m: x) {
								y.add(m);
							}
							if (k >= y.size()) {
								break;
							}
							int r2 = y.get(k);
							String extra = "";
							if (r1 != r2) {
								extra = "       <--------     FAIL";
								failCount += 1; 
								System.out.println(k + "\t in ["+a+", "+b+"] = " + r1 + ", should be " + r2 + "\t buffer " + extra2 + extra);
								if (debug) System.out.println("\n");
							}
						}
						System.out.println();
					}
				}
			}
			System.out.println("failCount: " + failCount);
			if (System.currentTimeMillis() < 0) {
				return;
			}
		}
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
	
	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/
	
	public static void shuffle(int[] a, Random rand) {
        for (int i = a.length; i > 1; i--) {
        	int r = rand.nextInt(i);
        	int temp = a[i-1];
        	a[i-1] = a[r];
        	a[r] = temp;
        }
	}

	@Test
	public void testKthNumber() {
		int maxLength = 100;
		int numTests = 1000;
		int maxValue = 1000000000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] data = new int[len];
			HashSet<Integer> chosen = new HashSet<>();
			
			for (int i = 0; i < data.length; i++) {
				int r = rand.nextInt(maxValue) - maxValue/2;
				while (chosen.contains(r)) {
					r = rand.nextInt(maxValue) - maxValue/2;
				}
				data[i] = r;
			}
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int[] sorted = new int[high - low + 1];
				for (int k = 0; k < sorted.length; k++) {
					sorted[k] = data[low + k];
				}
				Arrays.sort(sorted);
				
				for (int k = 0; k < sorted.length; k++) {
					int expected = sorted[k];
					
					int actual = st.query(low, high, k+1);
					assertEquals(expected, actual);
				}
			}
		}
	}
	
	@Test
	public void testQuerySpeed1() {
		// 10^5 size, 10^5 queries
		int len = 100000;
		int numTests = 1;
		Random rand = new Random(0);
		int sum = 0;
		for (int j = 1; j <= numTests; j++) {
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				data[i] = i;
			}
			shuffle(data, rand);
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < len; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int actual = st.query(low, high, 0);
				sum += actual;
			}
		}
		assertTrue(sum >= 0);
	}
	
	@Test
	public void testQuerySpeed2() {
		// 10 size, 10 queries, 10^5 tests
		int len = 10;
		int numTests = 10000;
		Random rand = new Random(0);
		int sum = 0;
		for (int j = 1; j <= numTests; j++) {
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				data[i] = i;
			}
			shuffle(data, rand);
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < len; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int actual = st.query(low, high, 0);
				sum += actual;
			}
		}
		assertTrue(sum >= 0);
	}

	//@Test
	public void testKthNumberWithDuplicates() {
		// Not working... 
		int maxLength = 100;
		int numTests = 1000;
		int maxValue = maxLength;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				int r = rand.nextInt(maxValue) - maxValue/2;
				data[i] = r;
			}
			Arrays.sort(data);
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int[] sorted = new int[high - low + 1];
				for (int k = 0; k < sorted.length; k++) {
					sorted[k] = data[low + k];
				}
				Arrays.sort(sorted);
				int[] rank = new int[sorted.length];
				int count = 0;
				for (int k = 1; k < rank.length; k++) {
					if (sorted[k] != sorted[k-1]) {
						count++;
					}
					rank[k] = count;
				}
				
				for (int k = 0; k < sorted.length; k++) {
					int expected = rank[k];
					
					int actual = st.query(low, high, k+1);
					assertEquals(expected, actual);
				}
			}
		}
	}
	
}
