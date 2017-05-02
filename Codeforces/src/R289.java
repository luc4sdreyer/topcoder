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
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;


public class R289 {
	public static void main(String[] args) {
		//maximumInTable(System.in);
		paintingPebbles2(System.in);
	}	

	public static void paintingPebbles2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		
		int[][] colors = new int[n][k];
		for (int i = 0; i < a.length; i++) {
			
		}
	}
	
	public static void paintingPebbles(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		
		int[][] colors = new int[n][k]; 
		
		for (int i = 0; i < a.length; i++) {
			int sum = 0;
			if (i == 0) {
				for (int j = 0; j < k; j++) {
					if (sum == a[i]) {
						break;
					} else {
						sum++;
						colors[i][j]++;
					}
				}
			} else {
				ArrayList<int[]> list = new ArrayList<>();
				for (int j = 0; j < k; j++) {
					list.add(new int[]{colors[i-1][j], j});
				}
				Collections.sort(list, new Comparator<int[]>(){
					public int compare(int[] o1, int[] o2) {
						return Integer.compare(o1[0], o2[0]);
					}
				});
				if (a[i] < a[i-1]) {
					Collections.reverse(list);
				}
				
				colors[i] = colors[i-1].clone();
				sum = a[i-1];
				for (int j = 0; j < k; j++) {
					if (sum == a[i]) {
						break;
					} else {
						if (a[i] > a[i-1]) {
							colors[i][list.get(j)[1]]++;
							sum++;
						} else {
							colors[i][list.get(j)[1]]--;
							sum--;
						}
					}
				}
			}
			if (sum != a[i]) {
				System.out.println("NO");
				return;
			}
		}
		System.out.println("YES");
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < k; j++) {
				for (int m = 0; m < colors[i][j]; m++) {
					System.out.print((j+1) + " ");
				}
			}
			System.out.println();
		}
	}

//	public static void paintingPebbles(InputStream in) {
//		MyScanner scan = new MyScanner(in);
//		int n = scan.nextInt();
//		int k = scan.nextInt();
//		int[] a = scan.nextIntArray(n);
////		int[][] colors = new int[k][k];
////		for (int i = 0; i < a.length; i++) {
////			
////		}
//		boolean valid = true;
//		for (int i = 1; i < a.length; i++) {
//			if (Math.abs(a[i] - a[i-1]) > k) {
//				valid = false;
//			}
//		}
//		if (!valid) {
//			System.out.println("NO");
//			return;
//		}
//		for (int i = 0; i < a.length; i++) {
//			//int c = 1;
//			String out = "";
//			int[] color = new int[k];
//			int sum = 0;
//			for (int j = 0; j < color.length; j++) {
//				color[j] = a[i] / k;
//				sum += color[j];
//			}
//			int x = 0;
//			while (sum < a[i]) {
//				sum++;
//				color[x++]++;
//			}
//			for (int j = 0; j < color.length; j++) {
//				for (int m = 0; m < color[j]; m++) {
//					out += ((j+1) + " ");
//				}
//			}
////			while (a[i] > 0) {
////				int x = (int) Math.ceil(a[i] / (double)k);
////				System.out.println(x);
////				for (int j = 0; j < x; j++) {
////					out += (c + " ");
////				}
////				a[i] -= x;
////				c++;
////			}
//			System.out.println(out);
//		}
//	}

	public static void maximumInTable(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[][] a = new int[n][n];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (i == 0 && j == 0) {
					a[i][j] = 1;
				}  else {
					a[i][j] = (i > 0 ? a[i-1][j] : 0) + (j > 0 ? a[i][j-1] : 0);
				}
			}
		}
		System.out.println(a[n-1][n-1]);
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
