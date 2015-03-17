import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) {
		//chefAndNotebooks(System.in);
		//countSubstrings(System.in);
		//countSubstrings2(System.in);
		//devuAndHisClass(System.in);
		//devuAndHisClassTest();
		//signWave(System.in);
		//signWaveTest();
		//chefAndProblems(System.in);
		//matrix(System.in, true);
		//testMatrix();
		serejaAndRandomArray(System.in);
//		Generator2 g = new Generator2();
//		int[] a = new int[50];
//		g.generator2(50, 5, a);
//		System.out.println(Arrays.toString(a));
	}

	public static void serejaAndRandomArray(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			Generator1 gen1 = new Generator1();
			Generator2 gen2 = new Generator2();
			char[] str = scan.nextLine().toCharArray();
			boolean m1 = true;
			boolean m2 = true;
			for (int s = 0; s <= 31313; s++) {
				gen1.srand1(s);
				gen2.srand2(s);
				m1 = true;
				m2 = true;
				for (int i = 0; i < str.length; i++) {
					if (gen1.nextInteger1() % 2 != str[i] - '0') {
						m1 = false;
					}
					if (gen2.nextInteger2() % 2 != str[i] - '0') {
						m2 = false;
					}
					if (m1 == false && m2 == false) {
						break;
					}
				}
				if (m1 || m2) {
					break;
				}
			}
			if (m1) {
				System.out.println("LCG");
			} else if (m2) {
				System.out.println("Xorshift");
			} else {
				System.out.println("FAIL");
			}
		}
	}

	public static final long INT_32_MAX = 1L << 32;

	public static class Generator1 {
		long X; // we assume that unsigned is a 32bit integer type

		void srand1(long S){
			X = S;
		}

		long nextInteger1(){
			X = (X * 1103515245 + 12345) % INT_32_MAX;
			return (((X / 65536) % 32768) % INT_32_MAX);
		}

		void generator1(int N, long S, int A[]){
			srand1(S);
			for(int i=1;i<=N;i++){
				A[i] = (int) (nextInteger1() % 2L); 
			}
		}

		void generator1(int N, long S, long A[]){
			srand1(S);
			for(int i=1;i<=N;i++){
				for (int j = 0; j < 64; j++) {
					if (nextInteger1() % 2 == 1) {
						A[i] |= (1L << j);	
					}					
				}
			}
		}
	}

	public static class Generator2 {
		long x, y, z, w; // we assume that unsigned is a 32bit integer type

		void srand2(long S){
			x = S;
			y = (x * S) % INT_32_MAX;
			z = (y * S) % INT_32_MAX;
			w = (z * S) % INT_32_MAX;
		}

		long nextInteger2() {
			long t = (x ^ ((x * (1 << 11)) % INT_32_MAX)) % INT_32_MAX;
			x = y;
			y = z;
			z = w;
			w = ((w ^ (w / (1 << 19))) ^ (t ^ (t / (1 << 8)))) % INT_32_MAX;
			return (w % INT_32_MAX);
		}

		void generator2(int N, long S, int A[]){
			srand2(S);
			for(int i=1;i<=N;i++){
				A[i-1] = (int) (nextInteger2() % 2L); 
			}
		}
	}

	public static void testMatrix() {
		int numTests = 1000000;
		Random rand = new Random(0);
		int size = 6;
		int nq = 20;
		for (int i = 0; i < numTests; i++) {
			int n = rand.nextInt(size)+1;
			int m = rand.nextInt(size)+1;
			String input = "1\n" + m + " " + n + " " + (nq*2) + "\n";
			for (int j = 0; j < nq; j++) {
				input += (rand.nextInt(2)+1) + " " + (rand.nextInt(m) + 1) + " " + (rand.nextInt(n) + 1) + "\n4\n"; 
			}
			ArrayList<Integer> exp = matrix(new ByteArrayInputStream(input.getBytes()), false);
			ArrayList<Integer> act = matrix(new ByteArrayInputStream(input.getBytes()), true);
			for (int j = 0; j < exp.size(); j++) {
				if (exp.get(j) != act.get(j)) {
					matrix(new ByteArrayInputStream(input.getBytes()), true);
					System.nanoTime();
				}
			}
		}
	}

	public static ArrayList<Integer> matrix(InputStream in, boolean fast) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		ArrayList<Integer> ans = new ArrayList<>();
		for (int t = 0; t < tests; t++) {
			int m = scan.nextInt();
			int n = scan.nextInt();
			int qs = scan.nextInt();

			boolean[][][] w = new boolean[2][n+2][m+2];
			int[][] color = new int[n+2][m+2];
			int[] componentSizeA = new int[n*m];
			SegmentTreeVerbose componentSize = new SegmentTreeVerbose(componentSizeA);
			int[] maxSize = {0};
			int[] cid = {0};
			long out = 0;

			componentSize.update_tree(0, 0, n*m);
			maxSize[0] = componentSize.query_tree(0, componentSizeA.length-1);

			for (int i = 0; i < qs; i++) {
				int type = scan.nextInt();
				if (type == 1) {
					int x = scan.nextInt();
					int y = scan.nextInt();
					if (!w[0][y][x]) {
						w[0][y][x] = true;
						if (fast) {
							if (((w[0][y][x-1] || w[1][y][x-1] || w[1][y+1][x-1]) && (w[1][y][x] || w[0][y][x+1] || w[1][y+1][x])) || x == 1 || x == m || y == 1 || y == n) {
								scan(n, m, w, color, componentSize, maxSize, x, y, type-1, cid, fast); 
							}
						} else {
							scan(n, m, w, color, componentSize, maxSize, x, y, type-1, cid, fast); 
						}
					}
				} else if (type == 2) {
					int x = scan.nextInt();
					int y = scan.nextInt();
					if (!w[1][y][x]) {
						w[1][y][x] = true;
						if (fast) {
							if (((w[0][y-1][x] || w[1][y-1][x] || w[0][y-1][x+1]) && (w[0][y][x] || w[1][y+1][x] || w[0][y][x+1])) || x == 1 || x == m || y == 1 || y == n) {
								scan(n, m, w, color, componentSize, maxSize, x, y, type-1, cid, fast); 
							}
						} else {
							scan(n, m, w, color, componentSize, maxSize, x, y, type-1, cid, fast);
						}
					}
				} else if (type == 3) {
					int[] p1 = {0,0};
					p1[0] = scan.nextInt();
					p1[1] = scan.nextInt();
					int[] p2 = {0,0};
					p2[0] = scan.nextInt();
					p2[1] = scan.nextInt();
					if (color[p1[1]][p1[0]] == color[p2[1]][p2[0]]) {
						out++;
						//ans.add(1);
						//System.out.println(out);
					}
				} else if (type == 4) {
					out += maxSize[0];
					//ans.add(maxSize[0]);
					//System.out.println(out);
				}
			}
			System.out.println(out);
		}
		return ans;
	}

	public static class PointS {
		int x;
		int y;
		int steps;
		public PointS(int X, int Y, int steps) {
			this.x = X;
			this.y = Y;
			this.steps = steps;
		}
		public int getDistance(PointS x) {
			return Math.abs(x.x - this.x) + Math.abs(x.y - this.y);
		}
		public int getFScore(PointS x) {
			return this.getDistance(x) + steps;
		}

		@Override
		public int hashCode() {
			int hash = 1;
			hash = hash * 17 + x;
			hash = hash * 31 + y;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PointS other = (PointS) obj;
			if (x != other.x) {
				return false;
			} else if (y != other.y)
				return false;
			return true;
		}

		public String toString() {
			return "("+this.x+","+this.y+"):"+this.steps;
		}
	}

	public static class PointSComparator implements Comparator<PointS>
	{
		@Override
		public int compare(PointS x, PointS y)
		{
			return Integer.compare(x.getFScore(y), y.getFScore(x));
		}
	}

	public static void scan(int n, int m, boolean[][][] w, int[][] color, SegmentTreeVerbose componentSize, 
			int[] maxSize, int sx, int sy, int type, int[] cid, boolean fast) {
		if (!fast) {
			scanOld(n, m, w, color, componentSize, maxSize, sx, sy, type-1, cid);
			return;
		}
		int[] target = {sx, sy};
		if (type == 0) {
			target[1]++;
		} else {
			target[0]++;
		}
		final int BIG = 10000;
		boolean found = false;
		HashSet<Integer> visited = new HashSet<>();
		int col = color[sy][sx];
		int oldCol = col;

		Comparator<PointS> comparator = new PointSComparator();

		HashSet<PointS> closedset = new HashSet<PointS>();
		HashSet<PointS> openset = new HashSet<PointS>();

		PointS start = new PointS(sx, sy, 0);
		PriorityQueue<PointS> pq = new PriorityQueue<PointS>(10, comparator);
		pq.add(start);
		openset.add(start);

		while (!pq.isEmpty()) {
			PointS current = pq.poll();
			openset.remove(current);
			int x = current.x;
			int y = current.y;

			if (x < 1 || x > m || y < 1 || y > n || color[y][x] != col || closedset.contains(current)) {
				continue;
			}
			if (current.x == target[0] && current.y == target[1]) {
				found = true;
				break;
			}

			closedset.add(current);
			ArrayList<PointS> neighs = new  ArrayList<PointS>();
			if (!w[0][y][x]) {
				neighs.add(new PointS(x, y+1, current.steps+1));
			}
			if (!w[0][y-1][x]) {
				neighs.add(new PointS(x, y-1, current.steps+1));
			}
			if (!w[1][y][x]) {
				neighs.add(new PointS(x+1, y, current.steps+1));
			}
			if (!w[1][y][x-1]) {
				neighs.add(new PointS(x-1, y, current.steps+1));
			}
			for (PointS neigh : neighs) {
				int tentative_g_score = current.steps + 1;
				if (closedset.contains(neigh)) {
					if (current.steps >= neigh.steps) {
						continue;
					}
				}
				if (!openset.contains(neigh) || tentative_g_score < neigh.steps) {
					neigh.steps = tentative_g_score;
					if (!openset.contains(neigh)) {
						openset.add(neigh);
						pq.add(neigh);
					}
				}
			}
		}

		if (found) {
			return;
		}

		LinkedList<int[]> q = new LinkedList<>();
		int[] top = new int[2];
		top[0] = sx;
		top[1] = sy;
		q.add(top);
		visited = new HashSet<>();
		cid[0]++;
		col = cid[0];

		while (!q.isEmpty()) {
			top = q.poll();
			int y = top[1];
			int x = top[0];
			if (x < 1 || x > m || y < 1 || y > n || visited.contains(y * BIG + x)) {
				continue;
			}
			visited.add(y * BIG + x);
			color[y][x] = col;

			if (!w[0][y][x]) {
				q.add(new int[]{x, y+1});
			}
			if (!w[0][y-1][x]) {
				q.add(new int[]{x, y-1});
			}
			if (!w[1][y][x]) {
				q.add(new int[]{x+1, y});
			}
			if (!w[1][y][x-1]) {
				q.add(new int[]{x-1, y});
			}
		}

		int newSize = visited.size();
		componentSize.update_tree(col, col, newSize);
		componentSize.update_tree(oldCol, oldCol, -newSize);

		maxSize[0] = componentSize.query_tree(0, n*m-1);
	}

	public static void scanOld(int n, int m, boolean[][][] w, int[][] color, SegmentTreeVerbose componentSize, 
			int[] maxSize, int sx, int sy, int type, int[] cid) {
		int[] target = {sx, sy};
		if (type == 0) {
			target[1]++;
		} else {
			target[0]++;
		}
		final int BIG = 10000;
		boolean found = false;
		HashSet<Integer> visited = new HashSet<>();
		int col = color[sy][sx];
		int oldCol = col;


		Queue<int[]> q = new LinkedList<>();
		int[] top = {sx, sy};
		q.add(top);

		while (!q.isEmpty()) {
			top = q.poll();
			int y = top[1];
			int x = top[0];
			if (x < 1 || x > m || y < 1 || y > n || color[y][x] != col || visited.contains(y * BIG + x)) {
				continue;
			}
			visited.add(y * BIG + x);

			if (x == target[0] && y == target[1]) {
				found = true;
				break;
			}

			if (!w[0][y][x]) {
				q.add(new int[]{x, y+1});
			}
			if (!w[0][y-1][x]) {
				q.add(new int[]{x, y-1});
			}
			if (!w[1][y][x]) {
				q.add(new int[]{x+1, y});
			}
			if (!w[1][y][x-1]) {
				q.add(new int[]{x-1, y});
			}
		}

		if (found) {
			return;
		}

		q = new LinkedList<>();
		top[0] = sx;
		top[1] = sy;
		q.add(top);
		visited = new HashSet<>();
		cid[0]++;
		col = cid[0];

		while (!q.isEmpty()) {
			top = q.poll();
			int y = top[1];
			int x = top[0];
			if (x < 1 || x > m || y < 1 || y > n || visited.contains(y * BIG + x)) {
				continue;
			}
			visited.add(y * BIG + x);
			color[y][x] = col;

			if (!w[0][y][x]) {
				q.add(new int[]{x, y+1});
			}
			if (!w[0][y-1][x]) {
				q.add(new int[]{x, y-1});
			}
			if (!w[1][y][x]) {
				q.add(new int[]{x+1, y});
			}
			if (!w[1][y][x-1]) {
				q.add(new int[]{x-1, y});
			}
		}

		int newSize = visited.size();
		componentSize.update_tree(col, col, newSize);
		componentSize.update_tree(oldCol, oldCol, -newSize);

		maxSize[0] = componentSize.query_tree(0, n*m-1);
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

	public static void chefAndProblems(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int k = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		for (int i = 0; i <= m; i++) {
			list.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < a.length; i++) {
			list.get(a[i]).add(i);
		}
		for (int i = 0; i < k; i++) {
			int left = scan.nextInt()-1;
			int right = scan.nextInt()-1;
			int max = 0;

			for (int j = 0; j < list.size(); j++) {
				ArrayList<Integer> range = list.get(j);
				int low = 0;
				int high = range.size()-1;
				int mid = 0;
				int bestR = -1;
				while (low <= high) {
					mid = (low + high)/2;
					if (range.get(mid) < right) {
						bestR = range.get(mid);
						low = mid+1;
					} else if (range.get(mid) > right) {
						high = mid-1;  
					} else {
						bestR = range.get(mid);
						break;
					}
				}
				if (bestR == -1) {
					continue;
				}

				low = 0;
				high = range.size()-1;
				mid = 0;
				int bestL = -1;
				while (low <= high) {
					mid = (low + high)/2;
					if (range.get(mid) < left) {
						low = mid+1;
					} else if (range.get(mid) > left) {
						bestL = range.get(mid);
						high = mid-1;  
					} else {
						bestL = range.get(mid);
						break;
					}
				}
				if (bestL == -1) {
					continue;
				}
				max = Math.max(max, bestR - bestL);
			}
			System.out.println(max);
		}
	}

	public static void countSubstrings(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int q = scan.nextInt();
			char[] s = scan.nextLine().toCharArray();
			int[] thresh = new int[n];
			long[] sum = new long[n];
			long[] pre = new long[n];

			int one = 0;
			int zero = 0;

			int x = 0;
			int y = 0;
			int backX = 0;
			while (x < n) {
				if ((one <= k && zero < k && s[x] == '0') || (one < k && zero <= k && s[x] == '1')) {
					thresh[x] = y;
					if (s[x] == '0') {
						zero++;
					} else {
						one++;
					}
					x++;
				} else {
					sum[y] = x-y;
					pre[y+1] = pre[y] + sum[y];
					y++;
					if (s[backX] == '0') {
						zero--;
					} else {
						one--;
					}
					backX++;
				}
			}
			for (int i = 0; i < q; i++) {
				int left = scan.nextInt()-1;
				int right = scan.nextInt()-1;

				x = right;

				int a = thresh[x]; 
				long s1 = 0;
				int left1 = Math.min(left, a);
				int right1 = Math.min(right, a);
				s1 = pre[right1] - pre[left1];

				int left2 = Math.max(left, a);
				int right2 = Math.max(right, a);
				long len = x+1-a;
				long f1 = len - (left2 - a);
				long f2 = len - (right2 +1 - a);
				long s2 = f1*(f1+1)/2 - f2*(f2+1)/2;

				System.out.println(s1+ s2);
			}

		}
	}

	public static void signWaveTest() {
		int limit = 50;
		for (int c = 0; c < 1; c++) {
			for (int s = 0; s < limit; s++) {
				for (int k = 1; k <= limit*2; k++) {
					//System.out.println("s: " + s + "\t c: " + c);
					long act = signWaveSlow3(s, c, k);
					long exp = signWave(s, c, k);
					if (exp != act) {
						System.out.println("fail");
					}
				}
			}
		}
	}

	public static void signWave(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int s = scan.nextInt();
			int c = scan.nextInt();
			int k = scan.nextInt();




			//System.out.println(signWave(s, c, k) + "\t" + signWaveSlow2(s, c, k));
			System.out.println(signWave(s, c, k));
			//System.out.println(signWaveSlow3(s, c, k));
		}
	}

	public static long signWaveSlow(int s_, int c_, int k) {
		HashMap<Integer, Integer> f = new HashMap<>();
		int max = Math.max(s_, c_);
		int s = s_;
		int c = c_;
		for (int i = max; i >= 1 && s > 1; i--) {
			for (int j = 0; j <= (1 << s_); j += (1 << i)) {
				f.put(j, f.containsKey(j) ? f.get(j) + 1 : 1);
			}
			s--;
		}

		for (int i = max-1; i >= 0 && c > 0; i--) {
			boolean even = true;
			for (int j = 0; j <= (1 << c_); j += (1 << i)) {
				if (even) {
					even = false;
				} else {
					f.put(j, f.containsKey(j) ? f.get(j) + 1 : 1);
					even = true;
				}
			}
			c--;
		}

		long total = 0;
		for (int key: f.keySet()) {
			if (f.get(key) >= k) {
				total++;
			}
		}
		return total;
	}

	public static long signWaveSlow3(int s, int c, int k) {

		long[] dp = new long[s];

		for (int i = 0; i < dp.length; i++) {
			if (i == 0) {
				dp[0] = 3;
			} else {
				long sum = 0;
				for (int j = dp.length-1; j >= 1; j--) {
					dp[j] = dp[j-1];
					sum += dp[j];
				}
				dp[0] = sum - 1;
			}
			//System.out.println(Arrays.toString(dp));
		}

		long total = 0;
		for (int i = k-1; i < dp.length; i++) {
			total += dp[i];
		}
		return total;
	}

	public static long signWaveSlow2(int s, int c, int k) {
		int max = 14;
		int[] f = new int[1 << max + 1];
		int[] freq = new int[c+s+1];

		for (int i = 12; i >= 0 && s > 0; i--) {
			boolean even = true;
			for (int j = 0; j <= (1 << max); j += (1 << i)) {
				if (even) {
					f[j]++;
					even = false;
				} else {
					even = true;
				}
			}
			s--;
		}
		for (int i = 12; i >= 0 && c > 0; i--) {
			boolean even = true;
			for (int j = 0; j <= (1 << max); j += (1 << i)) {
				if (even) {
					even = false;
				} else {
					f[j]++;
					even = true;
				}
			}
			c--;
		}
		for (int i = 0; i < f.length; i++) {
			if (f[i] > 0) {
				freq[f[i]-1]++;
			}
		}
		System.out.println(Arrays.toString(freq));

		long total = 0;
		for (int i = 0; i < f.length; i++) {
			if (f[i] >= k) {
				total++;
			}
		}
		return total;
	}

	public static long signWave(int s, int c, int k) {
		if (s == 0 && c == 0 && k == 1) {
			return 0;
		}
		int[] init = {s, c};
		int S = s;
		long[] dp = new long[s+1];

		for (int i = 0; i < S; i++) {
			if (i == 0) {
				dp[0] = 3;
			} else {
				long sum = 0;
				for (int j = S-1; j >= 1; j--) {
					dp[j] = dp[j-1];
					sum += dp[j];
				}
				dp[0] = sum - 1;
			}
			//System.out.println(Arrays.toString(dp));
		}
		for (int i = S-2; i >= 0 && c > 0; i--) {
			dp[i+1] += dp[i];
			dp[i] = 0;
			c--;
		}
		long add = (1L << (init[1]));
		while (init[1] >= init[0]) {
			dp[0] += add;
			add /= 2L;
			if (add < 2) {
				break;
			}
			init[1]--;
		}
		//System.out.println(Arrays.toString(dp));

		long total = 0;
		for (int i = k-1; i < dp.length; i++) {
			total += dp[i];
		}
		return total;
	}

	public static void devuAndHisClassTest() {
		Random rand = new Random(0);
		int maxLength = 10;
		for (int i = 0; i < 10000000; i++) {
			int length = rand.nextInt(maxLength);
			char[] s = new char[length];
			for (int j = 0; j < length; j++) {
				if (rand.nextBoolean()) {
					s[j] = 'B';
				} else {
					s[j] = 'G';
				}
			}
			long exp = devuAndHisClassCounting(0, s.clone());
			long act = devuAndHisClass(0, s);
			if (exp != act) {
				devuAndHisClassCounting(0, s);
				devuAndHisClass(0, s);
				System.out.println("fail");
			}	
		}
	}

	public static void devuAndHisClass(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int type = scan.nextInt();
			char[] s = scan.nextLine().toCharArray();
			long min = devuAndHisClass(type, s);
			System.out.println(min);
		}
	}

	public static long devuAndHisClassCounting(int type, char[] s) {
		long min = Long.MAX_VALUE;
		char[] order = {'B', 'G'};
		min = Math.min(min, devuAndHisClassCounting(type, s, order));
		order[1] = 'B';
		order[0] = 'G';
		min = Math.min(min, devuAndHisClassCounting(type, s, order));
		if (min == Long.MAX_VALUE) {
			min = -1;
		}
		return min;
	}

	public static long devuAndHisClassCounting(int type, char[] s, char[] order) {
		int[] f = new int[2];
		for (int i = 0; i < s.length; i++) {
			if (s[i] == order[0]) {
				f[0]++;
			} else {
				f[1]++;
			}
		}
		long[] cost = new long[2];
		for (int i = 0; i < s.length; i++) {
			if (s[i] != order[i % 2]) {
				cost[i % 2]++;
			}
		}
		if (cost[0] != cost[1]) {
			return Long.MAX_VALUE;
		}
		return cost[0];
	}

	public static long devuAndHisClass(int type, char[] s) {
		long min = Long.MAX_VALUE;
		char[] order = {'B', 'G'};
		min = Math.min(min, devuAndHisClass(type, s, order));
		order[1] = 'B';
		order[0] = 'G';
		min = Math.min(min, devuAndHisClass(type, s, order));
		if (min == Long.MAX_VALUE) {
			min = -1;
		}
		return min;
	}

	public static long devuAndHisClass(int type, char[] str, char[] order) {
		long cost = 0;
		char[] s = str.clone();
		int[] next = new int[2];
		for (int i = 0; i < s.length; i++) {
			if (s[i] != order[i % 2]) {
				while (next[i % 2] < s.length && (s[next[i % 2]] != order[i % 2] || next[i % 2] < i || s[next[i % 2]] == order[next[i % 2] % 2])) {
					next[i % 2]++;
				}
				if (next[i % 2] >= s.length) {
					return Long.MAX_VALUE;
				}
				char temp = s[next[i % 2]];
				s[next[i % 2]] = s[i];
				s[i] = temp;
				if (type >= 1) {
					cost += Math.abs(next[i % 2] - i);
				} else {
					cost++;
				}

			}
		}
		return cost;
	}

	public static void countSubstrings2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int q = scan.nextInt();
			char[] s = scan.nextLine().toCharArray();
			long[][] dp = new long[n][n];

			for (int i = 0; i < n; i++) {
				long[][] counter = new long[n][2];
				for (int j = i; j < n; j++) {
					if (j > 0) {
						for (int m = 0; m < 2; m++) {
							counter[j][m] = counter[j-1][m];
						}
						dp[i][j] = dp[i][j-1]; 
					}
					if (counter[j][0] <= k && counter[j][1] <= k) {
						//if (counter[j][0] < k && counter[j][1] < k) {
						//if (counter[j][(int)(s[j] - '0')] < k && counter[j][((int)(s[j] - '0') + 1) % 2] <= k) {
						counter[j][(int)(s[j] - '0')]++;
					}
					if (counter[j][0] <= k && counter[j][1] <= k) {
						dp[i][j]++;
					}

				}
			}
			for (int i = 0; i < dp.length; i++) {
				System.out.println(Arrays.toString(dp[i]));
			}
			for (int i = 0; i < q; i++) {
				int a = scan.nextInt() -1;
				int b = scan.nextInt() -1;
				long sum = 0;
				for (int y = a; y <= b; y++) {
					sum += dp[y][b]; 
				}
				System.out.println(sum);
			}
		}
	}

	public static void chefAndNotebooks(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int x = scan.nextInt();
			int y = scan.nextInt();	
			int k = scan.nextInt();
			int n = scan.nextInt();
			boolean valid = false;
			for (int i = 0; i < n; i++) {
				int p = scan.nextInt();
				int c = scan.nextInt();
				if (c <= k && p >= (x-y)) {
					valid = true;
				}
			}
			System.out.println(valid ? "LuckyChef" : "UnluckyChef");
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
					st = new StringTokenizer(br.readLine());
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