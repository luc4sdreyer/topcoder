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
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;


public class LunchTime20 {
	public static void main(String[] args) {
		//pieceOfCake(System.in);
		//candidateWalk(System.in);
		//justMultiply(System.in);
		manyBananas(System.in);
		//test();
	}
	
	public static void manyBananas(InputStream in) {
		MyScanner scan = new MyScanner(in);
		HashMap<Integer, Integer> map = new HashMap<>();
		int n = scan.nextInt();
		for (int i = 0; i < n; i++) {
			map.put(scan.nextInt(), scan.nextInt());
		}
		int nq = scan.nextInt();
		int[][] q = new int[nq][2];
		for (int i = 0; i < nq; i++) {
			char c = scan.next().charAt(0);
			int a = 0;
			int b = scan.nextInt();
			if (c == '?') {
				a = 0;
			} else if (c == '+') {
				a = 1;
			} else {
				a = 2;
			}
			q[i][0] = a;
			q[i][1] = b;
		}
		for (int i = 0; i < q.length; i++) {
			if (q[i][0] == 0) {
				long sum = 0;
				for (int key: map.keySet()) {
					sum += (long)(map.get(key)) * (q[i][1] % key); 
				}
				System.out.println(sum);
			} else if (q[i][0] == 1) {
				map.put(q[i][1], map.containsKey(q[i][1]) ? map.get(q[i][1]) + 1 : 1);
			} else {
				map.put(q[i][1], map.get(q[i][1]) - 1);
			}
		}
	}
	
	public static void justMultiply(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			long m = scan.nextLong();
			String line = scan.next();
			line = line.replace("**", "m");
			StringTokenizer st = new StringTokenizer(line, "*");
			ArrayList<String> power = new ArrayList<>();
			while (st.hasMoreTokens()) {
				power.add(st.nextToken());
			}
			BigInteger[][] a = new BigInteger[power.size()][2];
			for (int j = 0; j < power.size(); j++) {
				String[] s = power.get(j).split("m");
				a[j][0] = new BigInteger(s[0]);
				a[j][1] = new BigInteger(s[1]);
			}
			BigInteger total = BigInteger.ONE;
			BigInteger mod = BigInteger.valueOf(m); 
			for (int j = 0; j < a.length; j++) {
				BigInteger res = fastModularExponent(a[j][0], a[j][1], mod);
				total = total.multiply(res).mod(mod);
			}
			System.out.println(total);			
		}
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
	
	public static class Node implements Comparable<Node> {
		int[] pos;
		long v;
		public Node(int[] pos, long v) {
			super();
			this.pos = pos;
			this.v = v;
		}
		
		@Override
		public int compareTo(Node o) {
			return Long.compare(this.v, o.v);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(pos);
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
			if (!Arrays.equals(pos, other.pos))
				return false;
			return true;
		}		
	}
	
	public static void candidateWalk(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int d = scan.nextInt();
			System.out.println(candidateWalk(n, d));
		}
	}
	
	private static String candidateWalk(int n, int d) {
		PriorityQueue<Node> q = new PriorityQueue<>();
		int[] start = new int[n];
		int[] last = new int[n];
		for (int j = 0; j < last.length; j++) {
			last[j] = d-1;
		}
		Node top = new Node(start, 0);
		q.add(top);
		Node end = new Node(last, 0);
		HashMap<Node, Node> visited = new HashMap<>();
		long min = Long.MAX_VALUE;
		while (!q.isEmpty()) {
			top = q.poll();
			
			if (top.equals(end)) {
				min = Math.min(min, top.v);
			}
			for (int j = 0; j < n; j++) {
				if (top.pos[j] < d-1) {
					int[] nextPos = top.pos.clone();
					nextPos[j]++;
					long xor = 0;
					long sum = 0;
					for (int k = 0; k < nextPos.length; k++) {
						xor ^= nextPos[k];
						sum += nextPos[k];
					}
					long nextVal = xor * sum;
					Node next = new Node(nextPos, top.v + nextVal);
					if (visited.containsKey(next)) {
						Node best = visited.get(next);
						if (next.v < best.v) {
							visited.put(next, next);
						} else {
							continue;
						}
					} else {
						visited.put(next, next);
					}
					q.add(next);
				}
			}
		}
		return min + "";
		//return min + " " + visited.size();
	}
	
	public static void test() {
		for (int d = 1; d < (1 << 16); d++) {
			long expected = 0;
			
			long total = 0;
			for (int k = 0; k < d; k++) {
				total += k*k;
			}
			expected = total;
			
			long actual = Long.parseLong(candidateWalk(1, d));
			if (actual != expected) {
				System.out.println("fail: " + d);
			}
			
		}
	}

	public static void pieceOfCake(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			char[] s = scan.nextLine().toCharArray();
			int[] freq = new int[26];
			for (int j = 0; j < s.length; j++) {
				freq[s[j]-'a']++;
			}
			boolean valid = false;
			for (int j = 0; j < freq.length; j++) {
				int sum = 0;
				for (int k = 0; k < freq.length; k++) {
					if (j != k) {
						sum += freq[k];
					}
					
				}
				if (sum == freq[j]) {
					valid = true;
				}
			}
			System.out.println(valid ? "YES" : "NO");
		}
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
