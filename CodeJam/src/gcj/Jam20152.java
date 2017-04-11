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
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Iterator;

public class Jam20152 {
	//10^4 <  Short.MAX_VALUE (2^15)   < 10^5
	//10^9 <  Integer.MAX_VALUE (2^31) < 10^10
	//10^18 < Long.MAX_VALUE (2^63)    < 10^20

	//static String size = "sample";
	//static String size = "small-attempt0";
	//static String size = "large"=
	//static String filename = "D:\\gcj\\2015\\B\\A-sample";
	//static String filename = "D:\\gcj\\2015\\B\\A-small-attempt0";
	//static String filename = "D:\\gcj\\2015\\B\\A-small-attempt1";
	//static String filename = "D:\\gcj\\2015\\2\\C-small-attempt2";
	//static String filename = "D:\\gcj\\2015\\2\\C-sample";
	//static String filename = "D:\\gcj\\2015\\2\\A-sample";
	static String filename = "D:\\gcj\\2015\\2\\A-large-practice";

	static String outputfile = filename + ".out";
	static MyScanner scan;
	static PrintStream out;

	public static void main(String[] args) throws NumberFormatException, IOException {
		scan = new MyScanner(new FileInputStream(filename+".in"));
		out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputfile)));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);
		
		//bilingual();
		pegman();

		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
		scan.close();
		out.close();
	}

	public static void testCounterCulture() {
		Random rand = new Random(0);
		for (int i = 1; i < 1000; i++) {
			int n = rand.nextInt(1000000);
			if (counterCulture2F(n) != counterCulture3F(n)) {
				System.out.println("FAIL: " + i);
			}
		}
	}
	
	public static int[] dx = {0, 1, 0, -1};
	public static int[] dy = {1, 0, -1, 0};

	static class Node implements Comparable<Node> {
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
			if (cost < next.cost) return -1;
			if (cost > next.cost) return 1;
			if (y < next.y) return -1;
			if (y > next.y) return 1;
			if (x < next.x) return -1;
			if (x > next.x) return 1;
			return 0;
		}
	}

	public static void pegman() {
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int r = scan.nextInt();
			int c = scan.nextInt();
			int min = 0;
			
			int[][] map = new int[r][c];
			for (int i = 0; i < map.length; i++) {
				char[] line = scan.nextLine().toCharArray();
				System.out.println(new String (line));
				for (int j = 0; j < map[0].length; j++) {
					if (line[j] == '^') {
						map[i][j] = 2;
					} else if (line[j] == 'v') {
						map[i][j] = 0;
					} else if (line[j] == '>') {
						map[i][j] = 1;
					} else if (line[j] == '<') {
						map[i][j] = 3;
					} else {
						map[i][j] = 4;
					}
				}
			}
			
			int col = 0;
			HashSet<Integer> colorz = new HashSet<>();
			int nodes = 0;
		
			int[][] color = new int[r][c];
			boolean[] cycle = new boolean[r*c + 1];
			for (int y = 0; y < color.length; y++) {
				for (int x = 0; x < color[0].length; x++) {
					if (map[y][x] == 4) {
						continue;
					}
					nodes++;
					if (color[y][x] == 0) {
						col++;
						
						Node top = new Node(x, y, map[y][x]);
						
						ArrayList<Node> visited = new ArrayList<>();
						visited.add(top);
						
						int newCol = col;
						boolean cyc = false;

						while (true) {
							if (top.x < 0 || top.x >= color[0].length) break;
							if (top.y < 0 || top.y >= color.length) break;
							if (color[top.y][top.x] == col && map[top.y][top.x] != 4) {
							//if (color[top.y][top.x] == col) {
								cyc = true;
								break;
							}
							if (color[top.y][top.x] != 0 && map[top.y][top.x] != 4) {
								newCol = color[top.y][top.x];
								break;
							}
							
							visited.add(top);
							color[top.y][top.x] = col;

							// visit every adjacent node
							if (map[top.y][top.x] == 4) {
								top = new Node(top.x + dx[top.cost], top.y + dy[top.cost], top.cost);
							} else {
								top = new Node(top.x + dx[map[top.y][top.x]], top.y + dy[map[top.y][top.x]], map[top.y][top.x]);
							}
						}
						
						for (int i = 0; i < visited.size(); i++) {
							color[visited.get(i).y][visited.get(i).x] = newCol;
						}
						if (cyc) {
							cycle[newCol] = true;
						}
						colorz.add(newCol);
					}
				}
			}
			
			int d = 0;
			for (int i = 0; i <= col; i++) {
				if (cycle[i]) {
					d++;
				}
			}
			int ans = colorz.size() - d;
			String output = "";
			if (colorz.size() == nodes && !colorz.isEmpty()) {
				boolean impossible = false;
				for (int y = 0; y < map.length; y++) {
					int numNodes = 0;
					int column = -1;
					for (int x = 0; x < map[0].length; x++) {
						if (map[y][x] != 4) {
							numNodes++;
							column = x;
						}
					}
					if (numNodes == 1) {
						int numNodes2 = 0;
						for (int i = 0; i < map.length; i++) {
							if (map[i][column] != 4) {
								numNodes2++;
							}
						}
						if (numNodes2 == 1) {
							impossible = true;
							y = map.length;
							break;
						}
					}
				}
				if (impossible) {
					output = "Case #" + (test+1) + ": IMPOSSIBLE";
				} else {
					output = "Case #" + (test+1) + ": " + ans;
				}
			} else {
				output = "Case #" + (test+1) + ": " + ans;
			}
			System.out.println(output);
			out.println(output);
		}
	}

	public static void bilingual() {
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int m = scan.nextInt();
			int size = m - 2;
			ArrayList<ArrayList<String>> words = new ArrayList<>();
			for (int i = 0; i < m; i++) {
				String[] line = scan.nextLine().split(" ");
				ArrayList<String> next = new ArrayList<>();
				for (int j = 0; j < line.length; j++) {
					next.add(line[j]);
				}
				words.add(next);
			}

			HashSet<String> knowEng = new HashSet<>();
			HashSet<String> knowFr = new HashSet<>();
			for (int i = 0; i < 2; i++) {
				ArrayList<String> sen = words.get(i);
				for (int j = 0; j < sen.size(); j++) {
					if (i == 0) {
						knowEng.add(sen.get(j));
					} else {
						knowFr.add(sen.get(j));
					}
				}
			}
			
			int baseBil = 0;
			HashSet<String> counted = new HashSet<>();
			for (String s: knowEng) {
				if (knowFr.contains(s)) {
					baseBil++;
					counted.add(s);
				}
			}

			int N = 1 << (size);
			int min = Integer.MAX_VALUE;
			for (int n = 0; n < N; n++) {
				boolean[] english = new boolean[size];
				for (int i = 0; i < size; i++) {
					if (((1 << i) & n) != 0) {
						english[i] = true;
					}
				}
				HashSet<String> eng = new HashSet<>();
				HashSet<String> fr = new HashSet<>();
				for (int i = 2; i < words.size(); i++) {
					ArrayList<String> sen = words.get(i);
					for (int j = 0; j < sen.size(); j++) {
						if (english[i-2]) {
							eng.add(sen.get(j));
						} else {
							fr.add(sen.get(j));
						}
					}
				}
				//int bil = 0;
//				for (String s: eng) {
//					if (!counted.contains(s) && (knowFr.contains(s) || fr.contains(s))) {
//						bil++;
//					}
//				}
//				for (String s: fr) {
//					if (!counted.contains(s) && knowEng.contains(s)) {
//						bil++;
//					}
//				}
				HashSet<String> newBil = new HashSet<>();
				for (String s: eng) {
					if (!counted.contains(s) && (knowFr.contains(s) || fr.contains(s))) {
						newBil.add(s);
					}
				}
				for (String s: fr) {
					if (!counted.contains(s) && (knowEng.contains(s) || eng.contains(s))) {
						newBil.add(s);
					}
				}
				
				min = Math.min(min, baseBil + newBil.size());
			}
			out.println("Case #" + (test+1) + ": " + min);
		}
	}

	public static void counterCulture2() {
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			long n = scan.nextLong();
			
			long total = counterCulture2F(n);
			
			out.println("Case #" + (test+1) + ": " + total);
		}
	}

	private static long counterCulture2F(long n) {
		long steps = 0;
		long run = 1;
		long add1 = 11;
		long add2 = 20;
		while (run * 10L < n) {
			run *= 10L;
			if ((((int)Math.log10(run)) % 2 == 1)) {
				steps += add1 -1;
				add1 *= 10L;
			} else {
				steps += add2 -1;
				add2 *= 10L;
			}
		}
		long part1 = 0;
		long part2 = 0;
		long extra = 0;
		if (n == 1) {
			steps = 1;
		} else if (n == 10) {
			steps = 10;
		} else {
		
			int length = (int) Math.ceil(Math.log10(n) / 2.0);
			long limit = (long) Math.pow(10, length);
			part1 = n % limit;
			if (part1 == 0 && length >= 1) {
				n--;
				part1 = n % limit;
				extra = 1;
			}
			part2 = 0;
			String p2 = "";
			String ns = Long.toString(n);
			int totalLen = (int) Math.ceil(Math.log10(n));
			for (int i = length; i < totalLen; i++) {
				p2 += ns.charAt(totalLen - i - 1);
			}
			if (p2.length() > 0 && Long.parseLong(p2) > 1) {
				part2 = Long.parseLong(p2);
			}
		}
		
		return steps + part1 + part2 + extra;
	}

	public static void counterCulture3() {
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			
			int ret = counterCulture3F(n);
			out.println("Case #" + (test+1) + ": " + ret);
		}
	}

	private static int counterCulture3F(int n) {

		int[] a = new int[n+1];
		int[] back = new int[n+1];
		Arrays.fill(a, Integer.MAX_VALUE);
		a[0] = 0;
		for (int i = 0; i < a.length-1; i++) {
			a[i+1] = Math.min(a[i] + 1, a[i+1]);
			if (a[i+1] == a[i]+1) {
				back[i+1] = i;
			}
			String s = Integer.toString(i);
			String rev = "";
			for (int j = 0; j < s.length(); j++) {
				rev += s.charAt(s.length() - j - 1);
			}
			int r = Integer.parseInt(rev);
			if (r < a.length) {
				a[r] = Math.min(a[r], a[i] + 1);
				if (a[r] == a[i] + 1) {
					back[r] = i;
				}
			}
		}
		StringBuilder backTrack = new StringBuilder();
		int p = a.length-1;
		while (p != 0) {
			backTrack.append(p + " ");
			p = back[p];
		}
		//out.println("Case #" + (test+1) + ": " + a[a.length-1]);
		return a[a.length-1];
	}

	public static void counterCulture() {
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			int n = scan.nextInt();
			
			int[] a = new int[n+1];
			int[] back = new int[n+1];
			Arrays.fill(a, Integer.MAX_VALUE);
			a[0] = 0;
			for (int i = 0; i < a.length-1; i++) {
				a[i+1] = Math.min(a[i] + 1, a[i+1]);
				if (a[i+1] == a[i]+1) {
					back[i+1] = i;
				}
				String s = Integer.toString(i);
				String rev = "";
				for (int j = 0; j < s.length(); j++) {
					rev += s.charAt(s.length() - j - 1);
				}
				int r = Integer.parseInt(rev);
				if (r < a.length) {
					a[r] = Math.min(a[r], a[i] + 1);
					if (a[r] == a[i] + 1) {
						back[r] = i;
					}
				}
			}
			StringBuilder backTrack = new StringBuilder();
			int p = a.length-1;
			while (p != 0) {
				backTrack.append(p + " ");
				p = back[p];
			}
			//out.println("Case #" + (test+1) + ": " + a[a.length-1]);
			out.println("Case #" + (test+1) + ": " + a[a.length-1] + "\tback: " + backTrack);
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