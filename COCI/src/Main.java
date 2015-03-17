import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) {
		//teta(System.in);
		//kriza(System.in);
		acm(System.in);
	}

	public static void acm(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[][] d = new int[3][n];
		for (int i = 0; i < d.length; i++) {
			d[i] = scan.nextIntArray(n);
		}
		int[][] sum = new int[3][n];
		for (int i = 0; i < sum.length; i++) {
			for (int j = 0; j < sum[0].length; j++) {
				if (j > 0) {
					sum[i][j] = sum[i][j-1];
				}
				sum[i][j] += d[i][j];
			}
		}
		
		long min = Long.MAX_VALUE;
		for (int p1 = 0; p1 < 3; p1++) {
			for (int p2 = 0; p2 < 3; p2++) {
				if (p1 != p2) {
					for (int p3 = 0; p3 < 3; p3++) {
						if (p3 != p2 && p3 != p1) {
							for (int i = 0; i < n-2; i++) {
								for (int j = i+1; j < n-1; j++) {
									long a = sum[p1][i];
									//long b = sum[p2][j] - (i > 0 ? sum[p2][i-1] : 0);
									long b = sum[p2][j] - sum[p2][i];
									long c = sum[p3][n-1] - sum[p3][j];
									long s = a + b + c; 
									min = Math.min(min, s);
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println(min);
	}

	public static void kriza(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] chain = scan.nextIntArray(n);
		
		//kriza(n, k, chain);
		kriza2(n, k, chain);
	}

	public static void kriza2(int n, int k, int[] chain) {
		int[] pos = new int[n+1];
		for (int i = 0; i < chain.length; i++) {
			pos[chain[i]] = i;
		}
		
		int partition = 0;
		int prevPartition = 0;
		long a = 0;
		for (int i = 0; i < n; i++) {
			 int door = (i % n)+1;
			 prevPartition = partition;
			 partition = pos[door];
			 int dist = partition - prevPartition;
			 if (dist < 0) {
				 dist += n;
			 }
			 a += dist;
		}
		long b = 0;
		for (int i = 0; i < n; i++) {
			 int door = (i % n)+1;
			 prevPartition = partition;
			 partition = pos[door];
			 int dist = partition - prevPartition;
			 if (dist < 0) {
				 dist += n;
			 }
			 b += dist;
		}
		long total = 0;
		if (k > 2*n) {
			long div = k / n - 1;
			k = k % n + n;
			if (div > 0) {
				total = div * b; 
			}
		}

		partition = 0;
		prevPartition = 0;
		for (int i = 0; i < k; i++) {
			 int door = (i % n)+1;
			 prevPartition = partition;
			 partition = pos[door];
			 int dist = partition - prevPartition;
			 if (dist < 0) {
				 dist += n;
			 }
			 total += dist;
		}
		System.out.println(total);
	}

	public static void kriza(int n, int k, int[] chain) {
		int[] pos = new int[n+1];
		for (int i = 0; i < chain.length; i++) {
			pos[chain[i]] = i;
		}
		
		int partition = 0;
		int prevPartition = 0;
		long total = 0;
		long prevTotal = 0;
		for (int i = 0; i < k; i++) {
			 int door = (i % n)+1;
			 prevPartition = partition;
			 partition = pos[door];
			 int dist = partition - prevPartition;
			 if (dist < 0) {
				 dist += n;
			 }
			 if (i % n == 0) {
				 //System.out.println(total - prevTotal);
				 prevTotal = total;
			 }
			 total += dist;
		}
		System.out.println(total);
	}

	public static void teta(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int k = scan.nextInt();
		int[] mealPrice = scan.nextIntArray(k);
		int menu = scan.nextInt();
		int[] menuItems = scan.nextIntArray(4);
		int t = scan.nextInt();
		int[] tray = scan.nextIntArray(t);
		
		int[] ftray = new int[k+1];
		for (int i = 0; i < tray.length; i++) {
			ftray[tray[i]]++;
		}
		
		int min = Integer.MAX_VALUE;
		for (int i = 0; i <= t; i++) {
			int cost = menu * i;
			for (int j = 1; j < ftray.length; j++) {
				cost += ftray[j] * mealPrice[j-1];
			}
			min = Math.min(min, cost);
			for (int j = 0; j < menuItems.length; j++) {
				if (ftray[menuItems[j]] > 0) {
					ftray[menuItems[j]]--;
				}
			}
		}
		System.out.println(min);
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
