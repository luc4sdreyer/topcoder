import java.io.*;
import java.math.*;
import java.util.*;

//http://codeforces.com/problemset/tags/dp/page/1?order=BY_SOLVED_DESC

public class DpPractice {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		new Thread(null, new Runnable() {
            public void run() {
            	DpPractice.run();
            }
        }, "1", 1 << 26).start(); // Set stack memory to 64 MB
	}
	
	public static void run() {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
		//tetrahedron(System.in);
		//poloThePenguinAndMatrix(System.in);
		//System.out.println(anotherProblemOnStrings(System.in));
		//theLeastRoundWay(System.in);								// TODO
		//colorStripe(System.in);
		//colorStripeTest();
		//goodSequences(System.in);
		//weather(System.in);
		//mysteriousPresent(System.in);
		//puzzles(System.in);
		//flippingGame(System.in);
		//berSUBall(System.in);
		//boredom(System.in);
//		ilyaAndQueries(System.in);
		beautifulNumbers();
	}
	
	public static void beautifulNumbers() {
		for(int i=1,j=0;i<=2520;i++)
	        if(2520%i==0)
	            Lcm[i]=j++;
	    for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}
	    
		int ts = in.nextInt();
		for (int te = 0; te < ts; te++) {
			long a = in.nextLong();
			long b = in.nextLong();
			System.out.println(beautifulNumbers(a, b));
			System.out.println(beautifulNumbersSlow(a, b));
		}
//		System.out.println(ops);
	}
	
	public static long beautifulNumbersSlow(long L, long R) {
		int[] count = new int[(int) (R+1)];
		for (int i = 1; i <= R; i++) {
			int t = i;
			boolean valid = true;
			while (t > 0) {
				if ((t % 10 != 0) && (i % (t % 10) != 0)) {
					valid = false;
					break;
				}
				t /= 10;
			}
			count[i] += count[i-1];
			if (valid) {
				count[i]++;
			}
		}
		return count[(int) R] - count[(int) (L-1)];
	}
	
	public static long beautifulNumbers(long L, long R) {
		return beautifulNumbers(R) - beautifulNumbers(L-1);
	}

	static int[] Lcm = new int[2527];
	static long[][][] dp = new long[20][2527][60];
	static int[] num = new int[30];
	static long ops = 0; 
	public static long beautifulNumbers(long N) {
		int Num = 0;
	    while(N > 0) {
	        num[Num++] = (int) (N%10);
	        N/=10;
	    }
	    return dfs(Num-1,0,1,true);
	}
	
	static long gcd(long a,long b) {
		ops++;
	    if(b==0)
	        return a;
	    return gcd(b,a%b);
	}
	
	static long dfs(int p,int mod,int lcm,boolean limit) {
		ops++;
	    if (p<0)
	    	return ((mod%lcm) == 0) ? 1 : 0;
	    if (!limit && (dp[p][mod][Lcm[lcm]] != -1))
	        return dp[p][mod][Lcm[lcm]];
	    int e = limit ? num[p] : 9;
	    long res = 0;
	    for (int i=0;i<=e;i++) {
	        res+=dfs(p-1, (mod*10+i) % 2520, (int) (i !=0 ? i*lcm/gcd(i,lcm) : lcm), limit && i==e);
	    }
	    if (!limit)
	    	dp[p][mod][Lcm[lcm]]=res;
	    return res;
	}
	
	public static long beautifulNumbers2(long N) {
//		int LEN = (N + "").length();
//		int M = 2520;
//		int MASK = 1 << 8;
//		F[0][0][0][0] = 1;
//		for (int len = 1; len < LEN; len++) {
//			for (int sl = 0; sl < 2; sl++) {
//				for (int m = 0; m < M; m++) {
//					
//					for (int digit = 1; digit <= 9; digit++) {
//						
//					}
//					
//					for (int mask = 0; mask < MASK; mask++) {
//						
//					}
//				}
//			}
//		}
		return 0;
	}

	public static void ilyaAndQueries(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] s = scan.nextLine().toCharArray();
		int n = scan.nextInt();
		
		int[] sum = new int[s.length+1];
		for (int i = 1; i < s.length; i++) {
			sum[i] = sum[i-1];
			if (s[i] == s[i-1]) {
				sum[i]++;
			}
		}
		
		for (int i = 0; i < n; i++) {
			int left = scan.nextInt() - 1;
			int right = scan.nextInt() - 1;
			System.out.println(sum[right] - sum[left]);
		}
	}

	public static void boredom(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		
		int[] freq = new int[100001];
		//int[] freq = new int[10];
		for (int i = 0; i < a.length; i++) {
			freq[a[i]]++;
		}
		
		long[][] dp = new long[freq.length][2];
		for (int i = 1; i < freq.length; i++) {
			dp[i][1] = Math.max(dp[i-1][0], dp[i-1][1]);
			if (freq[i] == 0) {
				dp[i][0] = dp[i][1]; 
			} else {				
				dp[i][0] = dp[i-1][1] + (long)i * freq[i];
			}
		}
		System.out.println(Math.max(dp[dp.length-1][0], dp[dp.length-1][1]));
	}

	public static void berSUBall(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a1 = scan.nextIntArray(n);
		
		int m = scan.nextInt();
		int[] b1 = scan.nextIntArray(m);
		
		ArrayList<Integer> a = new ArrayList<>();
		for (int i = 0; i < a1.length; i++) {
			a.add(a1[i]);
		}
		Collections.sort(a);
		
		ArrayList<Integer> b = new ArrayList<>();
		for (int i = 0; i < b1.length; i++) {
			b.add(b1[i]);
		}
		Collections.sort(b);
		
		int pairs = 0;
		while (!b.isEmpty() && !a.isEmpty()) {
			if (b.get(b.size()-1) > a.get(a.size()-1) + 1) {
				b.remove(b.size()-1);
			} else if (b.get(b.size()-1) >= a.get(a.size()-1) - 1) {
				a.remove(a.size()-1);
				b.remove(b.size()-1);
				//System.out.println(a.remove(a.size()-1) + " - " + b.remove(b.size()-1));
				pairs++;
			} else {
				a.remove(a.size()-1);
			}
		}
		System.out.println(pairs);
	}
	
	public static void flippingGame(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		
		int[] one = new int[n+1];
		int[] zero = new int[n+1];
		for (int i = 0; i < n+1; i++) {
			if (i > 0) {
				one[i] = one[i-1];
				zero[i] = zero[i-1];
			}
			if (i < n && a[i] == 0) {
				zero[i]++;
			} else {
				one[i]++;
			}
		}
		
		int max = 0;
		for (int start = 0; start < n; start++) {
			for (int end = start+1; end <= n; end++) {
				int score = start > 0 ? one[start - 1] : 0;
				if (end < n) {
					score += one[n-1] - one[end];
				}
				
				score += zero[end] - (start > 0 ? zero[start-1] : 0);
				max = Math.max(max, score);
			}
		}
		System.out.println(max);
	}

	public static void puzzles(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int[] a = scan.nextIntArray(m);
		
		int min = Integer.MAX_VALUE;
		Arrays.sort(a);
		for (int i = n-1; i < a.length; i++) {
			min = Math.min(min, a[i] - a[i-(n-1)]);
		}
		System.out.println(min);
	}

	public static void mysteriousPresent(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int w = scan.nextInt();
		int h = scan.nextInt();
		ArrayList<int[]> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int[] x = {scan.nextInt(), scan.nextInt(), i+1};
			list.add(x);
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)[0] <= w || list.get(i)[1] <= h) {
				list.remove(i--);
			}
		}
		
		int[][] a = new int[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			a[i] = list.get(i);
		}
		
		Arrays.sort(a, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {				
				return Long.compare((long)o2[0] * o2[1], (long)o1[0] * o1[1]);
			}
		});
		
		int[] dp = new int[a.length];
		int[] back = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (a[i][0] < a[j][0] && a[i][1] < a[j][1]) {
					if (dp[i] < dp[j] + 1) {
						dp[i] = dp[j] + 1;
						back[i] = j;
					}
				}
			}
		}
		int max = 0;
		int maxIdx = 0;
		for (int i = 0; i < back.length; i++) {
			if (dp[i] > max) {
				max = dp[i];
				maxIdx = i;
			}
		}
		
		String ret = "";
		for (int i = 0; i < max; i++) {
			 ret += (a[maxIdx][2]) + " ";
			 maxIdx = back[maxIdx];
		}
		
		System.out.println(max);
		if (max > 0) {
			System.out.println(ret);
		}
	}

	public static void weather(InputStream in) {
		MyScanner scan = null;
		try {
			scan = new MyScanner(new FileInputStream("input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		int[] zeros = new int[n];
		int[] zerosBack = new int[n];
		int[] pos = new int[n];
		int[] neg = new int[n];
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			zeros[i] = i > 0 ? zeros[i-1] : 0; 
			if (a[i] == 0) {
				zeros[i]++;
			}
			pos[i] = i > 0 ? pos[i-1] : 0; 
			if (a[i] > 0) {
				pos[i]++;
			}
		}
		for (int i = n-1; i >= 0; i--) {
			zerosBack[i] = (i < n-1) ? zerosBack[i+1] : 0; 
			if (a[i] == 0) {
				zerosBack[i]++;
			}
			neg[i] = (i < n-1) ? neg[i+1] : 0; 
			if (a[i] < 0) {
				neg[i]++;
			}
		}
		for (int i = 0; i < n-1; i++) {
			min = Math.min(min, zeros[i] + pos[i] + neg[i+1] + zerosBack[i+1]);
		}
		FileWriter write = null;
		try {
			write = new FileWriter("output.txt");
			write.write(min + "\n");
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void goodSequences(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		LinkedHashSet<Integer> allp = getPrimes(100000);
		HashMap<Integer, Integer> dp = new HashMap<>();
		for (int p: allp) {
			dp.put(p, 0);
		}
		int best = 0;
		for (int i = 0; i < a.length; i++) {
			HashSet<Integer> primes = getPrimeFactors(a[i]);
			int max = 0;
			for (int p: primes) {
				max = Math.max(dp.get(p), max);
			}
			max++;
			for (int p: primes) {
				dp.put(p, max);
			}
			best = Math.max(best, max);
		}
		System.out.println(best); 
	}
	
	public static HashSet<Integer> getPrimeFactors(int n) {
		HashSet<Integer> factors = new HashSet<>();
		int d = 2;
		while (n > 1) {
			while (n % d == 0) {
				factors.add(d);
				n /= d;
			}
			d++;
			if (d*d > n) {
				if (n > 1) {
					factors.add(n);
					break;
				}
			}
		}
		return factors;
	}
	
	
	public static LinkedHashSet<Integer> getPrimes(long limit) {
		LinkedHashSet<Integer> primes = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> notPrimes = new LinkedHashSet<Integer>();
		boolean prime = true;
		for (int i = 2; i <= limit; i++) {
			prime = true;
			if (notPrimes.contains(i)) {
				prime = false;
			}
			if (prime) {
				primes.add(i);
				for (int j = 2; j*i <= limit; j++) {
					notPrimes.add(j*i);
				}
			}
		}
		return primes;
	}

	public static void colorStripeTest() {
		int n = 500000;
		int k = 26;
		char[] c = new char[n];
		Random rand = new Random(0);
		for (int i = 0; i < c.length; i++) {
			c[i] = (char) ('A' + rand.nextInt(k));
		}
		long time = System.currentTimeMillis();
		colorStripe(n, k, c);
		time = System.currentTimeMillis() - time;
		System.out.println(time);
	}

	public static void colorStripe(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		char[] a = scan.nextLine().toCharArray();
		
		colorStripe(n, k, a);
	}
	
	/**
	 * Java was too slow :(
	 */
	public static void colorStripe(int n, int k, char[] a) {
		int[][] dp = new int[n][k];
		int[][] back = new int[n][k];
		for (int i = 0; i < k; i++) {
			dp[0][i] = i == (a[0] - 'A') ? 0 : 1;
		}
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < k; j++) {
				int min = Integer.MAX_VALUE;
				for (int m = 0; m < k; m++) {
					if (min > dp[i-1][m] && j != m) {
						min = dp[i-1][m];
						back[i][j] = m;
					}
				}
				min += j == (a[i] - 'A') ? 0 : 1;
				dp[i][j] = min;
			}
		}
		
		int min = Integer.MAX_VALUE;
		int minIdx = 0;
		for (int i = 0; i < k; i++) {
			if (dp[n-1][i] < min) {
				min = dp[n-1][i];
				minIdx = i;
			}
		}
		
		char[] ret = new char[n];
		int current = minIdx;
		for (int i = n-1; i >= 0; i--) {
			ret[i] = (char) (current + 'A');
			current = back[i][current];
		}
		System.out.println(min);
		System.out.println(new String(ret));
	}

	public static class Array1Comp implements Comparator<int[]> {
		@Override
		public int compare(int[] o1, int[] o2) {
			return Integer.compare(o1[0], o2[0]);
		}
	}
	
	public static class Array2Comp implements Comparator<int[]> {
		@Override
		public int compare(int[] o1, int[] o2) {
			return Integer.compare(o1[1], o2[1]);
		}
	}

	public static void theLeastRoundWay(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[][] a = new int[n][n];
		for (int i = 0; i < n; i++) {
			a[i] = scan.nextIntArray(n);
		}
		int[][] backtrack = new int[n][n];
		int[][][] dp = new int[n][2][2];
		int[][][] nextDp = new int[n][2][2];
		for (int i = 0; i < n; i++) {
			nextDp = new int[n][2][2];
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < 2; k++) {
					int[] rem = {0, 0};
					int x = a[i][j];
					while (x > 0 && x % 2 == 0) {
						rem[0]++;
						x /= 2;
					}
					while (x > 0 && x % 5 == 0) {
						rem[1]++;
						x /= 5;
					}
					if (i == 0 && j == 0) {
						for (int m = 0; m < 2; m++) {
							nextDp[j][k][m] = rem[m];
						}
					} else if (i == 0) {
						for (int m = 0; m < 2; m++) {
							nextDp[j][k][m] = (nextDp[j-1][k][m] + rem[m]);
							backtrack[i][j] = 1;
						}
					} else if (j == 0) {
						for (int m = 0; m < 2; m++) {
							nextDp[j][k][m] = (dp[j][k][m] + rem[m]);
							backtrack[i][j] = 2;
						}
					} else {
						ArrayList<int[]> list = new ArrayList<>();
						list.add(new int[]{dp[j][0][0], dp[j][0][1], 2});
						list.add(new int[]{dp[j][1][0], dp[j][1][1], 2});
						list.add(new int[]{nextDp[j-1][0][0], nextDp[j-1][0][1], 1});
						list.add(new int[]{nextDp[j-1][1][0], nextDp[j-1][1][1], 1});
						if (k == 0) {
							Collections.sort(list, new Array1Comp());
						} else {
							Collections.sort(list, new Array2Comp());
						}
						for (int m = 0; m < 2; m++) {
							nextDp[j][k][m] = (list.get(0)[m] + rem[m]);
							backtrack[i][j] = list.get(0)[2];
						}
					}
				}
			}
			dp = nextDp;
		}
		int min = Integer.MAX_VALUE;
		ArrayList<Character> path = new ArrayList<>();
		int x = n-1;
		int y = n-1;
		while (x >= 0 && y >= 0) {
			if (backtrack[y][x] == 0) {
				break;
			} else if (backtrack[y][x] == 1) {
				path.add('R');
				x--;
			} else {
				path.add('D');
				y--;
			}
		}
		Collections.reverse(path);
		StringBuilder sb = new StringBuilder();
		for (char c: path) {
			sb.append(c);
		}
		for (int k = 0; k < 2; k++) {
			for (int m = 0; m < 2; m++) {
				min = Math.min(dp[n-1][k][m], min);
			}
		}
		System.out.println(min);
		System.out.println(sb);
	}
	
	public static void test() {
		Random rand = new Random(0);
		int max = 1000;
		int numTests = 1000;
		for (int t = 0; t < numTests; t++) {
			int k = rand.nextInt(max);
			char[] s = new char[rand.nextInt(max)];
			for (int i = 0; i < s.length; i++) {
				s[i] = (char) (rand.nextInt(2) + '0');
			}
			anotherProblemOnStrings(k, s);
		}
	}

	public static long anotherProblemOnStrings(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int k = scan.nextInt();
		char[] s = scan.nextLine().toCharArray();
		return anotherProblemOnStrings(k, s);
	}
	
	private static long anotherProblemOnStrings(int k, char[] s) {

		int start = 0;
		ArrayList<Integer> ones = new ArrayList<>();
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '1') {
				ones.add(i);
			}
		}
		if (k == 0) {
			ArrayList<Integer> numZ = new ArrayList<>();
			int len = 0;
			if (s[0] == '0') {
				len++;
			}
			for (int i = 1; i < s.length; i++) {
				if (s[i] == '0' && s[i-1] == s[i]) {
					len++;
				} else if (s[i] == '0') {
					len = 1;
				} else {
					numZ.add(len);
					len = 0;
				}
			}
			numZ.add(len);
			long total = 0;
			for (int i = 0; i < numZ.size(); i++) {
				total += (long)numZ.get(i) * (numZ.get(i)+1)/2;
			}
			return total;
		} 
		if (ones.isEmpty()) {
			return 0;
		}
		int end = 0;
		long total = 0;
		for (int i = 0; i < ones.size()-k+1; i++) {
			start = ones.get(i);
			end = ones.get(i + k-1);
			int leftZ = 0;
			int rightZ = 0;
			start--;
			while (start >= 0 && start <= s.length && s[start] == '0') {
				leftZ++;
				start--;
			}
			end++;
			while (end >= 0 && end < s.length && s[end] == '0') {
				rightZ++;
				end++;
			}
			total += (long)(leftZ+1) * (rightZ +1);
		}
		return total;
	}

	public static void poloThePenguinAndMatrix(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int d = scan.nextInt();
		int[][] a = new int[n][m];
		for (int i = 0; i < a.length; i++) {
			a[i] = scan.nextIntArray(m);
		}
		
		boolean valid = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (Math.abs(a[0][0] - a[i][j]) % d != 0) {
					valid = false;
				}
			}
		}
		
		if (!valid) {
			System.out.println(-1);
			return;
		}
		
		int min = Integer.MAX_VALUE;
		int max = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				min = Math.min(min, a[i][j]);
				max = Math.max(max, a[i][j]);
			}
		}
		
		int best = Integer.MAX_VALUE;
		for (int i0 = min; i0 <= max; i0+=d) {
			int total = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					total += Math.abs(a[i][j] - i0)/d;
				}
			}
			best = Math.min(best, total);
		}
		
		System.out.println(best);
	}

	public static final int mod = 1000000007;
	public static void tetrahedron(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] dp = new int[4];
		dp[3] = 1;
		for (int i = 1; i <= n; i++) {
			int[] nextDp = new int[4];
			for (int j = 0; j < nextDp.length; j++) {
				for (int k = 0; k < nextDp.length; k++) {
					if (k != j) {
						nextDp[j] = (nextDp[j] + dp[k]) % mod;
					}
				}
			}
			dp = nextDp;
		}
		System.out.println(dp[3]);
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
		public void subtract(T key) {
			Integer i = this.get(key);
			this.put(key, i-1);
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