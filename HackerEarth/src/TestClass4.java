import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;


class TestClass4 {
	public static void main(String[] args) {
		//matchMakers(System.in);
		agitatedChandan(System.in);
	}

	public static void agitatedChandan(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
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
			
			int cost = 0;
			if (max <= 100) {
				cost = 0;
			} else if (max <= 1000) {
				cost = 100;
			} else if (max <= 10000) {
				cost = 1000;
			} else  {
				cost = 10000;
			}
			System.out.println(cost + " " + max);
		}
	}

	public static void matchMakers(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int[] b = scan.nextIntArray(n);
			Arrays.sort(a);
			Arrays.sort(b);
			int count = 0;
			for (int i = 0; i < a.length; i++) {
				if ((a[i] % b[a.length - i -1] == 0) || (b[a.length - i -1] % a[i] == 0)) {
					count++;
				}
			}
			System.out.println(count);
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
