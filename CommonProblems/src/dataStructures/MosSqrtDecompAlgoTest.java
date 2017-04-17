package dataStructures;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;

import org.junit.Test;

public class MosSqrtDecompAlgoTest {

	/**
	 * Mo's Algorithm is a neat trick where you can reduce the running time by sqrt(N) by reordering the input queries.
	 * It's one way of doing query square root decomposition.
	 * 
	 * More info: https://blog.anudeep2011.com/mos-algorithm/
	 */

	public static class MosSqrtDecompAlgo {
		/**
		 * This implementation counts the number of distinct integers in any inclusive interval in O(sqrt(N)) 
		 */
		int[] count;
		int[] a;
		int answer;

		public MosSqrtDecompAlgo(int[] a, int maxValue) {
			count = new int[maxValue+1];
			this.a = a;
			answer = 0;
		}

		public void add(int index) {
			count[a[index]]++;
			if(count[a[index]] == 1) {
				answer++;
			}
		}

		void remove(int index) {
			count[a[index]]--;
			if(count[a[index]] == 0) {
				answer--;
			}
		}

		public int[] processQueries(int[][] q) {
			int[] ret = new int[q.length];
			final int blockSize = (int) (q.length / Math.sqrt(q.length));

			Arrays.sort(q, new Comparator<int[]>() {
				@Override
				public int compare(int[] a, int[] b) {
					if (a[0]/blockSize != b[0]/blockSize) {
						return Integer.compare(a[0]/blockSize, b[0]/blockSize);
					}
					return Integer.compare(a[1], b[1]);
				}
			});

			int leftPointer = 0;
			int rightPointer = 0;
			for(int i=0; i<q.length; i++) {
				int L = q[i][0];
				int R = q[i][1];

				while(leftPointer < L) {
					remove(leftPointer);
					leftPointer++;
				}
				while(leftPointer > L) {
					add(leftPointer-1);
					leftPointer--;
				}
				while(rightPointer <= R) {
					add(rightPointer);
					rightPointer++;
				}
				while(rightPointer > R+1) {
					remove(rightPointer-1);
					rightPointer--;
				}
				// Put the ordering back to the original
				ret[q[i][2]] = answer;
			}
			return ret;
		}
	}
	

	public static class MosSqrtDecompAlgo2 extends MosSqrtDecompAlgo {
		/**
		 * This implementation counts the number of integers larger than K in any inclusive interval in O(sqrt(N)) * O(log(N))
		 */
		SegmentTree st;
		
		public MosSqrtDecompAlgo2(int[] a, int maxValue) {
			super(a, maxValue);
			this.st = new SegmentTree(this.count);
		}

		public void add(int index) {
			count[a[index]]++;
			st.set(a[index], count[a[index]]);
		}

		void remove(int index) {
			count[a[index]]--;
			st.set(a[index], count[a[index]]);
		}

		public int[] processQueries(int[][] q) {
			int[] ret = new int[q.length];
			final int blockSize = (int) (q.length / Math.sqrt(q.length));

			Arrays.sort(q, new Comparator<int[]>() {
				@Override
				public int compare(int[] a, int[] b) {
					if (a[0]/blockSize != b[0]/blockSize) {
						return Integer.compare(a[0]/blockSize, b[0]/blockSize);
					}
					return Integer.compare(a[1], b[1]);
				}
			});

			int leftPointer = 0;
			int rightPointer = 0;
			for(int i=0; i<q.length; i++) {
				int L = q[i][0];
				int R = q[i][1];
				int k = q[i][3];

				while(leftPointer < L) {
					remove(leftPointer);
					leftPointer++;
				}
				while(leftPointer > L) {
					add(leftPointer-1);
					leftPointer--;
				}
				while(rightPointer <= R) {
					add(rightPointer);
					rightPointer++;
				}
				while(rightPointer > R+1) {
					remove(rightPointer-1);
					rightPointer--;
				}
				// Put the ordering back to the original
				ret[q[i][2]] = k == 0 ? (R - L + 1) : st.get(k, count.length);
			}
			return ret;
		}
	}
	

	public static class SegmentTree {
		private int[][] t;
		private int[] a;
		private int N;
		private int n;
		
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

		public void set(int i, int v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/
	
	@Test
	public void testCountQuery() {
		int maxLength = 100;
		int maxValue = 1000;
		int numTests = 500;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue);
			}
			MosSqrtDecompAlgo decomp = new MosSqrtDecompAlgo(a, maxValue);

			int[] expected = new int[numTests];
			int[][] q = new int[numTests][3];
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				HashSet<Integer> set = new HashSet<>();
				for (int k = low; k <= high; k++) {
					set.add(a[k]);
				}
				expected[i] = set.size();
				q[i] = new int[]{low, high, i};
			}
			
			int[] actual = decomp.processQueries(q);
			assertArrayEquals(expected, actual);
		}
	}

	@Test
	public void testKthRankQuery() {
		int maxLength = 1000;
		int maxValue = 1000;
		int numTests = 500;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue);
			}
			MosSqrtDecompAlgo2 decomp = new MosSqrtDecompAlgo2(a, maxValue);

			int[] expected = new int[maxLength];
			int[][] q = new int[expected.length][];
			
			for (int i = 0; i < expected.length; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				int bound = rand.nextInt(maxValue);
				
				int count = 0;
				for (int k = low; k <= high; k++) {
					if (a[k] >= bound) {
						count++;
					}
				}
				expected[i] = count;
				q[i] = new int[]{low, high, i, bound};
			}

//			System.out.println(Arrays.toString(expected));
			
			int[] actual = decomp.processQueries(q);
//			System.out.println(Arrays.toString(actual) + "\n");
			assertArrayEquals(expected, actual);
		}
	}

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);

		int N = in.nextInt();
		int[] a = in.nextIntArray(N);
		int M = in.nextInt();
		int[][] q = new int[M][3];
		for (int i = 0; i < q.length; i++) {
			int L = in.nextInt() -1;
			int R = in.nextInt() -1;
			q[i] = new int[]{L, R, i};
		}

		MosSqrtDecompAlgo decomp = new MosSqrtDecompAlgo(a, 1000000);
		int[] ret = decomp.processQueries(q);
		for (int i = 0; i < ret.length; i++) {
			out.println(ret[i]);
		}
	}

	public static InputReader in;
	public static PrintWriter out;

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
