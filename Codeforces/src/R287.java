import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.bind.ValidationEvent;


public class R287 {
	public static void main(String[] args) {
		//amrAndMusic(System.in);
		//amrAndPins(System.in);
//		for (int i = 0; i < 16; i++) {
//			guessYourWayOut(4, i+1);
//		}
		//guessYourWayOut(4, 4);
		//guessYourWayOut(System.in);
		theMathsLecture(System.in);
	}
	
	public static void theMathsLecture(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int m = scan.nextInt();
		int[][] rem = new int[n][k];
		int powerRes = 0;
		for (int i = 0; i < n; i++) {
			if (i == 0) {
				powerRes = 1 % k;
				for (int j = 1; j < 10; j++) {
					rem[i][j % k]++;  
				}
			} else {
				powerRes = (powerRes * 10) % k;
				for (int j = 0; j < 10; j++) {
					for (int p = 0; p < k; p++) {
						rem[i][(j * powerRes + p) % k] = (rem[i][(j * powerRes + p) % k] + rem[i-1][p]) % m;
					}  
				}
			}
		}
		int total = 0;
		for (int i = 0; i < rem.length; i++) {
			total = (total + rem[i][0]) % m;
		}
		
		int total2 = 0;
		for (int i = (int) Math.pow(10, n-1); i < (int) Math.pow(10, n); i++) {
			String str = Integer.toString(i);
			boolean valid = false;
			while (str.length() > 0) {
				int x = Integer.parseInt(str);
				if (x % k == 0) {
					valid = true;
					break;
				}
				str = str.substring(1);
			}
			if (valid) {
				System.out.println(i);
				total2++;
			}
		}
		System.out.println(total2);
		System.out.println(total);
	}
	
	public static void guessYourWayOut(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int height = scan.nextInt();
		long n = scan.nextLong();
		guessYourWayOut(height, n);
	}
	
	public static void guessYourWayOut(int height, long n) {
		long b = n -1;
		int i = 0;
		long visited = 0;
		for (long h = height-1; h >= 0; h--) {
			boolean bit = (((1L << h) & b) != 0) ? true: false;
			boolean left = (i % 2 == 0) ? false : true;
			if (bit == left) {
				visited++;
			} else {
				visited += 1L << (h+1);
				i++;
			}
			i++;
			//System.out.println(h + " " + visited + " " + (bit ? 1 : 0));
		}
		System.out.println(visited);
	}

	public static void amrAndPins(InputStream in) {
		MyScanner scan = new MyScanner(in);
		long r = scan.nextInt();
		long x = scan.nextInt();
		long y = scan.nextInt();
		long x1 = scan.nextInt();
		long y1 = scan.nextInt();
		double d = Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
		int m  = (int) (Math.ceil(d / (2 * r)));
		System.out.println(m);
	}
	
	public static void amrAndMusic(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[][] inst = new int[n][2];
		for (int i = 0; i < inst.length; i++) {
			inst[i][0] = scan.nextInt();
			inst[i][1] = i+1;
		}
		Arrays.sort(inst, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[0], o2[0]);
			}			
		});
		int max = 0;
		for (int i = 0; i < inst.length && k >= 0; i++) {
			k -= inst[i][0];
			if (k >= 0) {
				max++;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(max + "\n");
		for (int i = 0; i < max; i++) {
			sb.append(inst[i][1] + " ");
		}
		System.out.println(sb);
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
