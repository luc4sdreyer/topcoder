package gcj;

import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Iterator;

public class Jam2016_1A {
	//10^4 <  Short.MAX_VALUE (2^15)   < 10^5
	//10^9 <  Integer.MAX_VALUE (2^31) < 10^10
	//10^18 < Long.MAX_VALUE (2^63)    < 10^20

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	// path: C:\Users\Lucas\workspace_tc\CodeJam\src\gcj\Jam2016_1A.java
	static String filename = "C:\\Users\\Lucas\\Downloads\\C-sample";

	static String outputfile = filename + ".out";
	static MyScanner scan;
	static PrintStream out;

	public static void main(String[] args) throws NumberFormatException, IOException {
		scan = new MyScanner(new FileInputStream(filename+".in"));
		out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputfile)));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);
		
//		TheLastWord();
//		RankAndFile();
		BFFs();

		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
		scan.close();
		out.close();
	}
	
	private static String BFFs(int n, int[] a) {
		int[] g = new int[a.length+1];
		for (int i = 1; i < g.length; i++) {
			g[i] = a[i-1];
		}
		int cycle = 0;
		for (int start = 1; start < g.length; start++) {
			int[] visit = new int[g.length];
			int pos = start;
			int oldpos = start;
			int len = 0;
			while (visit[pos] == 0) {
				oldpos = pos;
				len++;
				visit[pos] = len;
				
				pos = g[pos];
			}
			cycle = Math.max(-visit[pos] + visit[oldpos]+1, cycle);
		}
		int path = 0;
		for (int start = 1; start < g.length; start++) {
			int[] visit = new int[g.length];
			int pos = start;
			int oldpos = start;
			int len = 0;
			while (visit[pos] == 0) {
				oldpos = pos;
				len++;
				visit[pos] = len;
				
				pos = g[pos];
			}
			if (g[oldpos] == pos && g[pos] == oldpos) {
				path = Math.max(len, path);	
			}
		}
		return Math.max(path, cycle) + "";
	}

	public static void BFFs() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			System.out.println(test);
			out.println("Case #" +(test + 1) + ": " + BFFs(n, a));
		}
	}

	public static String RankAndFile(int n, int[][] a) {
		int[] idx = new int[n*2];
		for (int i = 0; i < idx.length; i++) {
			idx[i] = i;
		}
		String out = "";
		do {
			int[][] valid = new int[n][n];
			boolean work = true;
			for (int i = 0; i < idx.length; i++) {
				if (idx[i] != idx.length-1) {
					if (i < n) {
						for (int x = 0; x < n; x++) {
							if (valid[i][x] == 0) {
								valid[i][x] = a[idx[i]][x];
							} else if (valid[i][x] != a[idx[i]][x]) {
								work = false;
								i = idx.length;
								break;
							}
						}
					} else {
						int x_pos = i - n;
						for (int y = 0; y < n; y++) {
							if (valid[y][x_pos] == 0) {
								valid[y][x_pos] = a[idx[i]][y];
							} else if (valid[y][x_pos] != a[idx[i]][y]) {
								work = false;
								i = idx.length;
								break;
							}
						}
					}
				}
			}
			if (work) {
				int valid_idx = 0;
				for (int i = 0; i < idx.length; i++) {
					if (idx[i] == idx.length-1) {
						valid_idx = i;
					}
				}
				if (valid_idx < n) {
					for (int x = 0; x < n; x++) {
						out += valid[valid_idx][x] + " ";
					}
				} else {
					valid_idx -= n;
					for (int y = 0; y < n; y++) {
						out += valid[y][valid_idx] + " ";
					}
				}
				return out;
			}
		} while (next_permutation(idx));
		return out;
	}
	
	public static boolean next_permutation(int str[]) {
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}

	public static void RankAndFile() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			int[][] a = new int[2*n-1][];
			for (int i = 0; i < a.length; i++) {
				a[i] = scan.nextIntArray(n);
			}
			System.out.println(test);
			out.println("Case #" +(test + 1) + ": " + RankAndFile(n, a));
		}
	}

	public static void test_TheLastWord2() {
		Random rand = new Random(0);
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 5; j++) {
				char[] c = new char[j];
				for (int k = 0; k < c.length; k++) {
					c[k] = (char) (rand.nextInt(26) + 'A');
				}
				String a = TheLastWord2(c);
				String b = TheLastWord(c);
				if (a.compareTo(b) != 0) {
					System.out.println("fail");
				}
			}
		}
	}
	
	public static String TheLastWord2(char[] c) {
		ArrayList<String> s = new ArrayList<>();
		int N = 1 << c.length;
		for (int n = 0; n < N; n++) {
			boolean[] active = new boolean[c.length];
			for (int i = 0; i < c.length; i++) {
				if (((1 << i) & n) != 0) {
					active[i] = true;
				}
			}
			String out = "";
			for (int i = 0; i < active.length; i++) {
				if (i == 0) {
					out += c[i];
				} else {
					if (active[i]) {
						out = c[i] + out;
					} else {
						out += c[i];
					}
				}
			}
			s.add(out);
		}
		Collections.sort(s);
		Collections.reverse(s);
		return s.get(0);
	}
	
	public static String TheLastWord(char[] c) {
		String out = "";
		for (int i = 0; i < c.length; i++) {
			if (i == 0) {
				out += c[i];
			} else {
				if (c[i] >= out.charAt(0)) {
					out = c[i] + out;
				} else {
					out += c[i];
				}
			}
		}
		return out;
	}

	public static void TheLastWord() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			char[] s = scan.next().toCharArray();
			out.println("Case #" +(test + 1) + ": " + TheLastWord(s));
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