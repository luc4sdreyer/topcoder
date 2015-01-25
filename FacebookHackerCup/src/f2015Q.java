import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class f2015Q {

	public static void main(String[] args) throws FileNotFoundException {
		//cookingTheBooks("./input/f2015Q/cooking_the_books");
		//newYearsResolution("./input/f2015Q/new_years_resolution");
		lazerMaze("./input/f2015Q/laser_maze");
	}

	public static void lazerMaze(String filename) throws FileNotFoundException {
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");
			
			int ys = scan.nextInt();
			int xs = scan.nextInt();
			char[][] map = new char[ys][xs];
			for (int j = 0; j < map.length; j++) {
				map[j] = scan.nextLine().toCharArray();
			}
			output.append(lazerMaze(ys, xs, map));

			out.println(output.toString());
		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}

	public static final int[] dx = {1, 0, -1, 0};
	public static final int[] dy = {0, 1, 0, -1};
	//public static final char[] turrentMap = {'>', 'v', '<', '^'};
	
	public static String lazerMaze(int ys, int xs, char[][] map) {
		int[] goal = {-1, -1};
		int[] start = {-1, -1};
		char[][][] lazerMap = new char[4][ys][xs];
		HashMap<Character, Integer> turretMap = new HashMap<>();
		turretMap.put('>', 0);
		turretMap.put('v', 1);
		turretMap.put('<', 2);
		turretMap.put('^', 3);
		for (int i = 0; i < 4; i++) {
			for (int y = 0; y < ys; y++) {
				lazerMap[i][y] = map[y].clone();
			}
			for (int y = 0; y < ys; y++) {
				for (int x = 0; x < xs; x++) {
					if (map[y][x] == 'G') {
						goal[0] = x;
						goal[1] = y;
					}
					if (map[y][x] == 'S') {
						start[0] = x;
						start[1] = y;
					}
					if (turretMap.containsKey(lazerMap[i][y][x])) {
						int dir = (turretMap.get(lazerMap[i][y][x]) + i) % 4;
						int px = x;
						int py = y;
						lazerMap[i][py][px] = '#';
						px += dx[dir];
						py += dy[dir];
						while (px >= 0 && px < xs && py >= 0 && py < ys && (map[py][px] == 'S' || map[py][px] == 'G' || map[py][px] == '.')) {
							lazerMap[i][py][px] = '#';
							px += dx[dir];
							py += dy[dir];
						}
					}
				}
			}
		}
		
		int[][][] visited = new int[4][ys][xs];
		int[] top = {start[0], start[1], 0};
		Queue<int[]> q = new LinkedList<>();
		for (int i = 0; i < 4; i++) {
			q.add(new int[]{top[0] + dx[i], top[1] + dy[i], top[2]+1});
		}
		int px = 0;
		int py = 0;
		int steps = 0;
		while (!q.isEmpty()) {
			top = q.poll();
			px = top[0];
			py = top[1];
			steps = top[2];
			if (!(px >= 0 && px < xs && py >= 0 && py < ys && visited[steps%4][py][px] <= 0
					&& (lazerMap[steps%4][py][px] == 'S' || lazerMap[steps%4][py][px] == 'G' || lazerMap[steps%4][py][px] == '.'))) {
				continue;
			}
			visited[steps%4][py][px]++;
			if (px == goal[0] && py == goal[1]) {
				return steps + "";
			}
			for (int i = 0; i < 4; i++) {
				q.add(new int[]{top[0] + dx[i], top[1] + dy[i], top[2]+1});
			}
		}
		return "impossible";
	}

	public static void newYearsResolution(String filename) throws FileNotFoundException {
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");
			
			int[] total = {scan.nextInt(), scan.nextInt(), scan.nextInt()};
			int n = scan.nextInt();
			int[][] food = new int[n][3];
			for (int j = 0; j < food.length; j++) {
				food[j] = new int[]{scan.nextInt(), scan.nextInt(), scan.nextInt()};
			}
			output.append(newYearsResolution(total, food));

			out.println(output.toString());
		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}

	public static String newYearsResolution(int[] totals, int[][] food) {
		int N = 1 << food.length;
		for (int n = 0; n < N; n++) {
			boolean[] active = new boolean[food.length];
			for (int i = 0; i < food.length; i++) {
				if (((1 << i) & n) != 0) {
					active[i] = true;
				}
			}
			if (check(totals, food, active)) {
				return "yes";
			}
		}
		return "no";
	}

	public static boolean check(int[] totals, int[][] food, boolean[] active) {
		int[] sum = new int[3];
		for (int i = 0; i < food.length; i++) {
			if (active[i]) {
				for (int j = 0; j < sum.length; j++) {
					sum[j] += food[i][j];
				}
			}
		}
		if (Arrays.equals(totals, sum)) {
			return true;
		}
		return false;
	}

	public static void cookingTheBooks(String filename) throws FileNotFoundException {
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");
			output.append(cookingTheBooks(scan.nextInt()));

			out.println(output.toString());
		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}

	public static String cookingTheBooks(int n) {
		char[] a = Integer.toString(n).toCharArray();
		int min = n;
		int max = n;
		for (int i = 0; i < a.length; i++) {
			for (int j = i+1; j < a.length; j++) {
				int s = swap(a, i, j);
				max = Math.max(max, s);
				min = Math.min(min, s);
			}
		}
		return min + " " + max;
	}

	public static int swap(char[] a_, int i, int j) {
		char[] a = a_.clone();
		char t = a[i];
		a[i] = a[j];
		a[j] = t;
		if (a[0] == '0') {
			return Integer.parseInt(new String(a_));
		}
		return Integer.parseInt(new String(a));
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
