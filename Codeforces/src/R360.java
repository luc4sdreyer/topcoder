// Practice
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
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.math.BigInteger;

public class R360 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

		RemaindersGame();
//		NpHard();
//		Palindromes();
//		Opponents();
		
		out.close();
	}

	public static void RemaindersGame() {
		int n = in.nextInt();
		long k = in.nextInt();
		long[] x = in.nextLongArray(n);
		
		if (RemaindersGame(n, k, x)) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}

	public static boolean RemaindersGame(int n, long k, long[] x) {
		long lcm = x[0];
		boolean valid = false;
		for (int i = 1; i < x.length; i++) {
			lcm = (lcm * x[i] / (gcd(lcm, x[i])));
			if (lcm > k || x[i] == k) {
				valid = true;
				break;
			}
		}
		if (lcm >= k || valid) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean RemaindersGameSlow(int n, long k, long[] x) {
		
		boolean v = false;
		for (int i = 0; i < x.length; i++) {
			if (x[i] == k) {
				v = true;
			}
		}
		
		HashSet<String> set = new HashSet<>();
		int t = 0;
		while (t < k) {
			String s = "";
			for (int i = 0; i < x.length; i++) {
				s += t % x[i] + " ";
			}
			if (set.contains(s)) {
				break;
			}
			set.add(s);
			t++;
		}
		
		if (t == k || v) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void RemaindersGame2() {
		long[] x = new long[4];
		do {
			boolean v = true;
			for (int i = 0; i < x.length-1; i++) {
				if (x[i] > x[i+1] || x[i] == 0) {
					v = false;
				}
			}
			if (v) {
				
				for (int k = 1; k < 1000; k++) {
					boolean res1 = RemaindersGame(x.length, (long)k, x);
					boolean res2 = RemaindersGameSlow(x.length, (long)k, x);
					if (res1 != res2) {
						System.out.println("fail");
					}
				}
				
//				int k = 0;
//				while (true) {
//					HashSet<String> set = new HashSet<>();
//					int t = 0;
//					while (t < k) {
//						String s = "";
//						for (int i = 0; i < x.length; i++) {
//							s += t % x[i] + " ";
//						}
//						if (set.contains(s)) {
//							break;
//						}
//						set.add(s);
//						t++;
//					}
//					if (t < k) {
//						break;
//					}
//					k++;
//				}
//				int lcm = x[0];
//				for (int i = 1; i < x.length; i++) {
//					lcm = (int) (lcm * x[i] / (gcd(lcm, x[i])));
//				}
//				System.out.println(Arrays.toString(x) + "\t" + (k-1) + "\t" + lcm);
			}
		} while (next_number(x, 10));
	}
	
	public static long gcd(long a, long b) {
		long r;
		while (b != 0) {
			r = a % b;
			a = b;
			b = r;
		}
		return a;
	}
	
	public static boolean next_number(long list[], int base) {
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
	
	public static void NpHard() {
		int n = in.nextInt(); // vertices
		int m = in.nextInt(); 
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt()-1;
			int b = in.nextInt()-1;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		NpHard(n, g);
	}
	
	private static boolean NpHard(int n, ArrayList<ArrayList<Integer>> g) {
		int[] color = new int[n];
		for (int p = 0; p < n; p++) {
			if (color[p] == 0) {
				Queue<int[]> q = new LinkedList<>();
				int[] top = {p, 1};
				q.add(top);
				while (!q.isEmpty()) {
					top = q.poll();
					if (color[top[0]] > 0) {
						continue;
					}
					color[top[0]] = top[1];
					ArrayList<Integer> nn = g.get(top[0]);
					for (int i = 0; i < nn.size(); i++) {
						int nc = 1;
						if (top[1] == 1) {
							nc = 2;
						}
						q.add(new int[]{nn.get(i), nc});
					}
				}
			}
		}
		
		HashSet<Integer> c1 = new HashSet<>();
		HashSet<Integer> c2 = new HashSet<>();
		
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> nn = g.get(i);
			for (int j = 0; j < nn.size(); j++) {
				if (color[nn.get(j)] == color[i]) {
					out.println("-1");
					return false;
				}
			}
			if (!nn.isEmpty()) {
				if (color[i] == 1) {
					c1.add(i);
				} else {
					c2.add(i);
				}
			}
		}
		out.println(c1.size());
		for (Integer k: c1) {
			out.print((k+1) + " ");
		}
		out.println();

		out.println(c2.size());
		for (Integer k: c2) {
			out.print((k+1) + " ");
		}
		out.println();
		
		return true;
	}

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
	
	public static void Palindromes() {
		String s = in.next();
		StringBuilder sb = new StringBuilder();
		for (int i = s.length()-1; i >= 0; i--) {
			sb.append(s.charAt(i));
		}
		out.print(s);
		out.println(sb);
	}
	
	public static void Opponents() {
		int n = in.nextInt();
		int d = in.nextInt();
		char[][] day = new char[d][n];
		int[] da = new int[d];
		for (int i = 0; i < day.length; i++) {
			day[i] = in.next().toCharArray();
			for (int j = 0; j < day[i].length; j++) {
				if (day[i][j] == '1') {
					da[i]++;
				}
			}
		}
		int max = 0;
		int count = 0;
		for (int i = 0; i < da.length; i++) {
			if (da[i] < n) {
				count++;
			} else {
				max = Math.max(max, count);
				count = 0;
			}
		}
		max = Math.max(max, count);
		System.out.println(max);
	}

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
