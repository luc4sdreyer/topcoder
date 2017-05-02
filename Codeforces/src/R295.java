import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class R295 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, false);

//		DNAAlignmentTest();
//		DNAAlignment();
		Cubes();

		out.close();
	}
	
	public static boolean canRemove(Node n, HashMap<Node, Integer> set) {
		boolean can = true;
		for (int x = n.x-1; x <= n.x+1; x++) {
			Node above = new Node(x, n.y+1, 0);
			if (set.containsKey(above)) {
				can = false;
				for (int x2 = above.x-1; x2 <= above.x+1; x2++) {
					if (n.x != x2) {
						Node below = new Node(x2, above.y-1, 0);
						if (set.containsKey(below)) {
							can = true;
						}
					}
				}
				if (!can) {
					break;
				}
			}
		}
		return can;
	}
	
	public static void Cubes() {
		int[] dy = {1, 1, 1, 0, 0, 0, 0, 0, -1, -1, -1};
		int[] dx = {-1,0, 1,-2,-1, 0, 1, 2, -1,  0,  1};
		int N = in.nextInt();
		HashMap<Node, Integer> all = new HashMap<>();
		TreeSet<Integer> canRemove = new TreeSet<>();
		HashMap<Integer, Node> getNode = new HashMap<>();
		for (int i = 0; i < N; i++) {
			all.put(new Node(in.nextInt(), in.nextInt(), i), i);
		}
		
		for (Node n: all.keySet()) {
			getNode.put(n.cost, n);
			if (canRemove(n, all)) {
				canRemove.add(n.cost);
			}
		}
		
		ArrayList<Integer> removed = new ArrayList<>();
		
		while (!canRemove.isEmpty()) {
			for (int i = 0; i < 2; i++) {
				boolean maximize = i == 0 ? true : false;
				if (!canRemove.isEmpty()) {
					Integer bestIdx = maximize ? canRemove.last() : canRemove.first();
					Node best = getNode.get(bestIdx);
					
					canRemove.remove(bestIdx);
					all.remove(best);
					removed.add(best.cost);
//					System.out.println("removed " + best);
					
					for (int j = 0; j < dx.length; j++) {
						Node neighbour = new Node(best.x + dx[j], best.y + dy[j], 0);
						if (all.containsKey(neighbour)) {
							int neighbourC = all.get(neighbour);
							if (canRemove(neighbour, all)) {
								canRemove.add(neighbourC);
							} else {
								canRemove.remove(Integer.valueOf(neighbourC));
							}
						}
					}
				}
			}
		}

		long answer = 0;
		long exp = 1;
		long mod = 1000000009;
		for (int i = removed.size()-1; i >= 0; i--) {
			answer = (answer + exp * removed.get(i)) % mod; 
			exp = (exp * N) % mod;
		}
		System.out.println(answer);
	}

	public static class Node {
		public int cost, x, y;
		
		public Node(int x, int y, int cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}		

		public String toString() {
			return "(" + x + ", "+ y + ": " + cost + ")";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}

	public static void DNAAlignment() {
		in.next();
		char[] s = in.next().toCharArray();
		int[] x = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			if (s[i] == 'A') {
				x[i] = 0;
			} else if (s[i] == 'C') {
				x[i] = 1;
			} else if (s[i] == 'G') {
				x[i] = 2;
			} else if (s[i] == 'T') {
				x[i] = 3;
			} 
		}
		out.println(DNAAlignment(x));
	}

	public static void DNAAlignmentTest() {
		int numTests = 1000;
		Random rand = new Random(0);
		for (int len = 1; len <= 6; len++) {
			for (int i = 0; i < numTests; i++) {
				int[] num = new int[len];
				for (int j = 0; j < num.length; j++) {
					num[j] = rand.nextInt(4);
				}
				long expected = DNAAlignmentSlow(num);
				long actual = DNAAlignment(num);
				if (actual != expected) {
					System.out.println("fail: " + Arrays.toString(num));
					DNAAlignment(num);
				}
			}
		}
	}


	public static long DNAAlignment(int[] num) {

		// r1: dominance = 1 match
		int[] freq = new int[4];
		int maxF = 0;
		for (int i = 0; i < num.length; i++) {
			freq[num[i]]++;
			maxF = Math.max(maxF, freq[num[i]]);
		}
		int countF = 0;
		int distinct = 0;
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] == maxF) {
				countF++;
				distinct++;
			}
		}

		// assert
		long predNumMatches = 0;
		if (countF == 1) {
			predNumMatches = 1;
		} else {
			predNumMatches = fastModularExponent(distinct, num.length, 1000000007);
		}
		return predNumMatches;
	}
	
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

	public static long DNAAlignmentSlow(int[] numA) {
		int length = numA.length;

		//			System.out.println("A");
		//			System.out.println(Arrays.toString(numA));

		int max = 0;
		int[] numB = new int[length];
		do {
			int matches = 0;
			for (int startA = 0; startA < length; startA++) {
				for (int startB = 0; startB < length; startB++) {
					for (int i = 0; i < length; i++) {
						if (numA[(startA + i) % length] == numB[(startB + i) % length]) {
							matches++;
						}
					}
				}
			}
			if (matches > max) {
				max = matches;
			}
		} while (next_number(numB, 4));

		int NumMatches = 0;
//		System.out.println("max: " + max);
		numB = new int[length];
		do {
			int matches = 0;
			for (int startA = 0; startA < length; startA++) {
				for (int startB = 0; startB < length; startB++) {
					for (int i = 0; i < length; i++) {
						if (numA[(startA + i) % length] == numB[(startB + i) % length]) {
							matches++;
						}
					}
				}
			}
			if (matches == max) {
//				System.out.println(Arrays.toString(numB));
				NumMatches++;
			}
		} while  (next_number(numB, 4));
		//			System.out.println("NumMatches: " + NumMatches);
		//			System.out.println("\n\n");
		return NumMatches;
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
