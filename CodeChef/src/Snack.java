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
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.StringTokenizer;

public class Snack {
	public static void main(String[] args) {
		//DEVJERRY(System.in);
		//DEVSHOW(System.in);
		testDEVSHOW();
	}
	
	public static void testDEVSHOW() {
		Random rand = new Random(0);
		for (int n = 3; n <= 10; n++) {
			for (int i = 0; i < 1000; i++) {
				int[] v1 = new int[n-2];
				int[] v2 = new int[n-2];
				for (int j = 0; j < v1.length; j++) {
					int r = rand.nextInt(n) + 1;
					while (r == j + 1) {
						r = rand.nextInt(n) + 1;
					}
					v1[j] = r;
					r = rand.nextInt(n) + 1;
					while (r == j + 1 || r == v1[j]) {
						r = rand.nextInt(n) + 1;
					}
					v2[j] = r;
				}
				
				String[] answers = {"", ""}; 
				int ans = DEVSHOWslow(n, v1, v2, answers);
				
				String[] actual = {"", ""}; 
				int act = DEVSHOW(n, v1, v2, actual);
				
				if (ans != act) {
					System.out.println("fail");
					DEVSHOWslow(n, v1, v2, answers);
					DEVSHOW(n, v1, v2, actual);
				}
			}
		}
	}
	
	public static void DEVSHOW(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int[] v1 = scan.nextIntArray(n - 2);
			int[] v2 = scan.nextIntArray(n - 2);
			
			String[] answers = {"", ""};
			int ret = DEVSHOW(n, v1, v2, answers);
			System.out.println(ret);
			if (ret == 0) {
				System.out.println("both");
			} else if (ret == 1) {
				System.out.println("one");
			} else {
				System.out.println("none");
			}
			System.out.println(answers[0]);
			System.out.println(answers[1]);
		}
	}
	
	public static class ArrayComp implements Comparator<int[]> {
		@Override
		public int compare(int[] o1, int[] o2) {
			return Integer.compare(o1[0], o2[0]);
		}
	}
	
	public static int DEVSHOW(int n, int[] v1, int[] v2, String[] answers) {
		int[][] tally = new int[n][2];
		for (int i = 0; i < tally.length; i++) {
			tally[i][1] = i+1;
		}
		for (int i = 0; i < v1.length; i++) {
			tally[v1[i]-1][0]++;
		}
		for (int i = 0; i < v2.length; i++) {
			tally[v2[i]-1][0]++;
		}
		
		int d1 = -1;
		int d2 = -1;
		int a1 = -1;
		int a2 = -1; 
		int min = 0;
		int max = 0;
		int friendIdx = 0;
		int friendScore = 0;
		
		Arrays.sort(tally, new ArrayComp());
		
		//
		// Vote for max
		//		
		int mostIdx = 0;
		for (int i = tally.length-1; i >= 0; i--) {
			if (tally[i][1] < n-1) {
				mostIdx = i;
				break;
			}
		}
		
		d1 = tally[mostIdx][1];
		a1 = tally[mostIdx][1];
		
		tally[mostIdx][0]++;
		tally[mostIdx][0]++;
		
		Arrays.sort(tally, new ArrayComp());
		
		//
		// If your friend is min vote for him
		//
		min = tally[0][0];
		friendIdx = 0;
		friendScore = 0;
		for (int i = 0; i < tally.length; i++) {
			if (tally[i][1] == n) {
				friendScore = tally[i][0];
				friendIdx = i;
				break;
			}
		}
		
		if (friendScore == min) {
			d2 = tally[friendIdx][1];
			tally[friendIdx][0]++;
		}

		min = tally[0][0];
		friendIdx = 0;
		friendScore = 0;
		for (int i = 0; i < tally.length; i++) {
			if (tally[i][1] == n-1) {
				friendScore = tally[i][0];
				friendIdx = i;
				break;
			}
		}
		
		if (friendScore == min) {
			a2 = tally[friendIdx][1];
			tally[friendIdx][0]++;
		}

		Arrays.sort(tally, new ArrayComp());
		
		//
		// Vote if friend > lowest+1
		//
		
		if (d2 == -1) {
			min = tally[0][0];
			friendIdx = 0;
			friendScore = 0;
			for (int i = 0; i < tally.length; i++) {
				if (tally[i][1] == n) {
					friendScore = tally[i][0];
					friendIdx = i;
					break;
				}
			}
			
			if (friendScore > min+1) {
				d2 = tally[friendIdx][1];
				tally[friendIdx][0]++;
			}
		}
		
		if (a2 == -1) {
			min = tally[0][0];
			friendIdx = 0;
			friendScore = 0;
			for (int i = 0; i < tally.length; i++) {
				if (tally[i][1] == n-1) {
					friendScore = tally[i][0];
					friendIdx = i;
					break;
				}
			}
			
			if (friendScore > min+1) {
				a2 = tally[friendIdx][1];
				tally[friendIdx][0]++;
			}
		}

		Arrays.sort(tally, new ArrayComp());
		
		//
		// Vote if one friend > lowest+1
		//
		boolean voted = false;
		if (d2 == -1) {
			min = tally[0][0];
			friendIdx = 0;
			friendScore = 0;
			for (int i = 0; i < tally.length; i++) {
				if (tally[i][1] == n) {
					friendScore = tally[i][0];
					friendIdx = i;
					break;
				}
			}
			d2 = tally[friendIdx][1];
			tally[friendIdx][0]++;
			voted = true;
		}
		
		if (a2 == -1 && voted == false) {
			min = tally[0][0];
			friendIdx = 0;
			friendScore = 0;
			for (int i = 0; i < tally.length; i++) {
				if (tally[i][1] == n-1) {
					friendScore = tally[i][0];
					friendIdx = i;
					break;
				}
			}

			a2 = tally[friendIdx][1];
			tally[friendIdx][0]++;
		}
		Arrays.sort(tally, new ArrayComp());
		
		//
		// Just vote already
		//
		if (d2 == -1) {
			min = tally[0][0];
			friendIdx = 0;
			friendScore = 0;
			for (int i = 0; i < tally.length; i++) {
				if (tally[i][1] == n) {
					friendScore = tally[i][0];
					friendIdx = i;
					break;
				}
			}
			d2 = tally[friendIdx][1];
			tally[friendIdx][0]++;
		}
		
		if (a2 == -1) {
			min = tally[0][0];
			friendIdx = 0;
			friendScore = 0;
			for (int i = 0; i < tally.length; i++) {
				if (tally[i][1] == n-1) {
					friendScore = tally[i][0];
					friendIdx = i;
					break;
				}
			}

			a2 = tally[friendIdx][1];
			tally[friendIdx][0]++;
		}
		
		Arrays.sort(tally, new ArrayComp());

		answers[0] = d1 + " " + d2;
		answers[1] = a1 + " " + a2;
		
		min = tally[0][0];
		max = tally[tally.length-1][0];
		
		int score = 0;
		int scoreD = 0;
		int scoreA = 0;
		for (int i = 0; i < tally.length; i++) {
			if (tally[i][1] == n-1) {
				scoreD = tally[i][0];
				break;
			}
		}
		for (int i = 0; i < tally.length; i++) {
			if (tally[i][1] == n) {
				scoreA = tally[i][0];
				break;
			}
		}
		if (max == scoreD || min == scoreD) {
			score++;
		}
		if (max == scoreA || min == scoreA) {
			score++;
		}
		
		return score;
	}

	public static int DEVSHOWslow(int n, int[] v1, int[] v2, String[] answers) {
		int[] tally = new int[n+1];
		for (int i = 0; i < v1.length; i++) {
			tally[v1[i]]++;
		}
		for (int i = 0; i < v2.length; i++) {
			tally[v2[i]]++;
		}
		int best = 2;
		for (int d1 = 1; d1 <= n; d1++) {
			if (d1 != n-1) {
				for (int d2 = 1; d2 <= n; d2++) {
					if (d2 != n-1 && d2 != d1) {
						for (int a1 = 1; a1 <= n; a1++) {
							if (a1 != n) {
								for (int a2 = 1; a2 <= n; a2++) {
									if (a2 != n && a2 != a1) {
										int[] temp = tally.clone();
										temp[d1]++;
										temp[d2]++;
										temp[a1]++;
										temp[a2]++;
										
										int max = 0;
										int min = n*2;
										for (int i = 1; i < temp.length; i++) {
											max = Math.max(max, temp[i]);
											min = Math.min(min, temp[i]);
										}
										answers[0] = d1 + " " + d2;
										answers[1] = a1 + " " + a2;
										
										int score = 0;
										if (max == temp[n-1] || min == temp[n-1]) {
											score++;
										}
										if (max == temp[n] || min == temp[n]) {
											score++;
										}
										best = Math.min(best, score);
									}
								}
							}
						}
					}
				}
			}
		}
		return best;
	}


	public static class Node implements Comparable<Node> {
		public int cost, x, y;

		public Node(int cost) {
			this.cost = cost;
		}

		public Node(int x, int y, int cost) {
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
			if (y < next.y) return -1;
			if (y > next.y) return 1;
			if (x < next.x) return -1;
			if (x > next.x) return 1;
			return 0;
		}
	}
	
	public static void DEVJERRY(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			Node start = new Node(scan.nextInt() - 1, scan.nextInt() - 1, 0);
			Node end = new Node(scan.nextInt() - 1, scan.nextInt() - 1, 0);
			Node bomb = new Node(scan.nextInt() - 1, scan.nextInt() - 1, 0);
			
			boolean[][] visited = new boolean[n][n];
			visited[bomb.y][bomb.x] = true;
			
			// Declare stack of nodes
			Queue<Node> q = new LinkedList<Node>();
			q.add(start);
			int steps = 0;

			while (q.size() != 0) {
				Node top = q.poll();

				if (top.x < 0 || top.x >= visited[0].length) continue;
				if (top.y < 0 || top.y >= visited.length) continue;
				if(visited[top.y][top.x]) continue;
				
				visited[top.y][top.x] = true;
				if (top.compareTo(end) == 0) {
					steps = top.cost;
					break;
				}

				// visit every adjacent node
				q.add(new Node((int)(top.x + 1), top.y, (int)(top.cost+1)));
				q.add(new Node((int)(top.x - 1), top.y, (int)(top.cost+1)));
				q.add(new Node(top.x, (int)(top.y + 1), (int)(top.cost+1)));
				q.add(new Node(top.x, (int)(top.y - 1), (int)(top.cost+1)));
			}			
			
			System.out.println(steps);
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
