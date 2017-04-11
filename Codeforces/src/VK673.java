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
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class VK673 {
	public static void main(String[] args) {
//		bearAndGame(System.in);
//		System.out.println(problemsForRound(System.in));
//		bearAndColors(System.in);
//		testBearAndColors();
//		bearAndTwoPaths(System.in);
		testPFR();
	}
	
	public static void bearAndTwoPaths(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		
		int a = scan.nextInt();
		int b = scan.nextInt();
		int c = scan.nextInt();
		int d = scan.nextInt();
		
		ArrayList<Integer> open = new ArrayList<>();
		HashSet<Integer> closed = new HashSet<>();
		closed.add(a);
		closed.add(b);
		closed.add(c);
		closed.add(d);
		for (int i = 1; i <= n; i++) {
			if (!closed.contains(i)) {
				open.add(i);
			}
		}
		
		if (n > 4) {
			if (k < n + 1) {
				System.out.println("-1");
			} else {
				System.out.print(a + " " + c + " ");
				for (int i = 0; i < open.size(); i++) {
					System.out.print(open.get(i) + " ");
				}
				System.out.println(d + " " + b);
				
				System.out.print(c + " " + a + " ");
				for (int i = 0; i < open.size(); i++) {
					System.out.print(open.get(i) + " ");
				}
				System.out.println(b + " " + d);
			}
		} else {
			System.out.println("-1");
		}
	}
	
	public static void testBearAndColors() {
		Random rand = new Random(0);
		for (int i = 0; i < 10000; i++) {
			for (int len = 1; len < 10; len++) {
				for (int numCol = 1; numCol <= len; numCol++) {
					int[] a = new int[len];
					for (int j = 0; j < a.length; j++) {
						a[j] = rand.nextInt(numCol); 
					}
					
					int[] numDom = new int[a.length+1];
					for (int y = 0; y < a.length; y++) {
						for (int x = y; x < a.length; x++) {
							int[] fcount = new int[a.length+1];
							for (int j = y; j <= x; j++) {
								fcount[a[j]]++;
							}
							int max = 0;
							int maxN = a.length + 10;
							for (int j = 0; j < fcount.length; j++) {
								if (fcount[j] > max || (max == fcount[j] && j < maxN)) {
									max = fcount[j];
									maxN = j;
								}
							}
							numDom[maxN+1]++;
						}
					}
					
					for (int j = 0; j < a.length; j++) {
						a[j]++;
					}
					
					int[] nd2 = bearAndColors(a);
					if (!Arrays.equals(numDom, nd2)) {
						System.out.println("fail");
					}
				}
			}
		}
	}

	public static void bearAndColors(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] t = scan.nextIntArray(n);
		int[] numDom = bearAndColors(t);
		
		StringBuilder out = new StringBuilder();
		for (int i = 1; i < numDom.length; i++) {
			if (i > 1) {
				out.append(" ");
			}
			out.append(numDom[i]);	
		}
		System.out.println(out);
	}

	private static int[] bearAndColors(int[] t) {
		int[] numDom = new int[t.length+1];
		for (int y = 0; y < t.length; y++) {
			HashMap<Integer, Integer> map = new HashMap<>();
			int max = 0;
			int maxN = 0;
			for (int x = y; x < t.length; x++) {
				if (x == y) {
					max = 1;
					maxN = t[x];
					map.put(t[x], 1);
					numDom[maxN]++;
				} else {
					if (!map.containsKey(t[x])) {
						map.put(t[x], 1);
					} else {
						map.put(t[x], map.get(t[x]) + 1);
					}
					int currentF = map.get(t[x]);
					if (currentF > max || (currentF == max && t[x] < maxN)) {
						max = currentF;
						maxN = t[x];
					}
					numDom[maxN]++;
				}
			}
		}
		return numDom;
	}
	
	public static void testPFR() {
		Random rand = new Random(0);
		for (int i = 0; i < 1000; i++) {
			for (int n = 2; n < 10; n++) {
				for (int m = 0; m < 20; m++) {
					String input = n + " " + m;
					for (int j = 0; j < m; j++) {
						int p1 = rand.nextInt(n) + 1;
						int p2 = rand.nextInt(n) + 1;
						while (p2 == p1) {
							p2 = rand.nextInt(n) + 1;
						}
						input += "\n" + p1 + " " + p2; 
					}
					
					int r1 = problemsForRound(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
					int r2 = problemsForRoundSlow(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
					if (r1 != r2) {
						System.out.println("fail");
						problemsForRound(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
						problemsForRoundSlow(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
					}
				}
			}			
		}
	}

	public static int problemsForRoundSlow(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n2 = scan.nextInt();
		int m = scan.nextInt();
		int[] d1 =  new int[m];
		int[] d2 =  new int[m];
		
		int max1 = 1;
		int min2 = n2;
		for (int i = 0; i < m; i++) {
			int[] a = scan.nextIntArray(2);
			Arrays.sort(a);
			d1[i] = a[0];
			d2[i] = a[1];
			max1 = Math.max(max1, d1[i]);
			min2 = Math.min(min2, d2[i]);
		}

		int nunVal = 0;
		int N = 1 << n2;
		for (int n = 0; n < N; n++) {
			boolean[] active = new boolean[n2];
			for (int i = 0; i < n2; i++) {
				if (((1 << i) & n) != 0) {
					active[i] = true;
				}
			}
			int nt = 0;
			int nf = 0;
			boolean foundT = false;
			boolean invalid = false;
			for (int i = 0; i < active.length; i++) {
				if (active[i]) {
					foundT = true;
					nt++;
				} else {
					if (foundT) {
						invalid = true;
						break;
					}
					nf++;
				}
			}
			if (!invalid && nt > 0 && nf > 0) {
				HashSet<Integer> fset = new HashSet<>();
				HashSet<Integer> tset = new HashSet<>();
				for (int i = 0; i < active.length; i++) {
					if (active[i]) {
						tset.add(i+1);
					} else {
						fset.add(i+1);
					}
				}
				for (int i = 0; i < d1.length; i++) {
					if ((tset.contains(d1[i]) && tset.contains(d2[i])) || (fset.contains(d1[i]) && fset.contains(d2[i]))) {
						invalid = true;
						break;
					}
				}
			} else {
				invalid = true;
			}
			if (!invalid) {
				nunVal++;				
			}
		}
		return nunVal;
	}

	public static int problemsForRound(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int[] d1 =  new int[m];
		int[] d2 =  new int[m];
		
		int max1 = 1;
		int min2 = n;
		for (int i = 0; i < m; i++) {
			int[] a = scan.nextIntArray(2);
			Arrays.sort(a);
			d1[i] = a[0];
			d2[i] = a[1];
			max1 = Math.max(max1, d1[i]);
			min2 = Math.min(min2, d2[i]);
		}
		
		if (max1 >= min2) {
			return 0;
		} else {
			return (min2 - max1);
		}
	}

	public static void bearAndGame(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] x = scan.nextIntArray(n);
		boolean[] watch = new boolean[90];
		int i = 0;
		for (int j = 0; j < 15; j++) {
			if (i+j < watch.length) {
				watch[i+j] = true;
			}
		}
		for (i = 0; i < x.length; i++) {
			for (int j = 0; j < 15; j++) {
				if (x[i]+j < watch.length) {
					watch[x[i]+j] = true;
				}
			}
		}
		int stop = 90;
		for (i = 0; i < watch.length; i++) {
			if (!watch[i]) {
				stop = i;
				break;
			}
		}
		System.out.println(stop);
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
