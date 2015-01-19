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


public class R286 {
	public static void main(String[] args) {
		//mrKitayutasGift(System.in);
		mrKitayutasColorfulGraph(System.in);
	}

	public static void mrKitayutasColorfulGraph(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		boolean[][][] connected = new boolean[n+1][n+1][m+1];
		for (int i = 0; i < m; i++) {
			int u = scan.nextInt();
			int v = scan.nextInt();
			int col = scan.nextInt();
			connected[u][v][col] = true;
			connected[v][u][col] = true;
		}
		int queries = scan.nextInt();
		int[][] q = new int[queries][2];
		for (int i = 0; i < q.length; i++) {
			q[i][0] = scan.nextInt();
			q[i][1] = scan.nextInt();
		}
		scan.close();
		
		for (int i = 0; i < q.length; i++) {
			int num = 0;
			for (int color = 1; color <= m; color++) {
				boolean[] visited = new boolean[connected.length];
				if (dfs(q[i][0], q[i][1], color, connected, visited)) {
					num++;
				}
			}
			System.out.println(num);
		}
		
		return;
	}
	
	public static boolean dfs(int current, int dest, int color, boolean[][][] connected, boolean[] visited) {
		visited[current] = true;
		if (current == dest) {
			return true;
		}
		for (int i = 1; i < connected.length; i++) {
			if (connected[current][i][color] && !visited[i]) {
				if (dfs(i, dest, color, connected, visited)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void mrKitayutasGift(InputStream in) {
		MyScanner scan = new MyScanner(in);
		String s = scan.next();
		scan.close();
		ArrayList<Character> str = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			str.add(s.charAt(i));
		}
		
		for (int i = 0; i <= str.size(); i++) {
			for (int j = 0; j < 26; j++) {
				char c = (char) ('a' + j);
				@SuppressWarnings("unchecked")
				ArrayList<Character> str2 = (ArrayList<Character>) str.clone();
				str2.add(i, c);
				boolean isPal = true;
				for (int k = 0; k < str2.size()/2; k++) {
					if (str2.get(k) != str2.get(str2.size() - k -1)) {
						isPal = false;
					}
				}
				if (isPal) {
					String str3 = "";
					for (int k = 0; k < str2.size(); k++) {
						str3 += str2.get(k);
					}
					System.out.println(str3);
					return;
				}
			}
		}
		System.out.println("NA");
		return;
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
