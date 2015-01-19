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


public class R285 {
	public static void main(String[] args) {
		//System.out.println(contest(System.in));
		hishaAndChangingHandles(System.in);
	}

	public static void hishaAndChangingHandles(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int q = scan.nextInt();
		String[][] names = new String[q][2];
		for (int i = 0; i < names.length; i++) {
			for (int j = 0; j < 2; j++) {
				names[i][j] = scan.next();
			}
		}
		ArrayList<ArrayList<String>> mapping = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			boolean found = false;
			for (int j = 0; j < mapping.size(); j++) {
				for (int k = mapping.get(j).size()-1; k >= 0; k--) {
					if (mapping.get(j).get(k).equals(names[i][0])) {
						found = true;
						mapping.get(j).add(names[i][1]);
						j = 1000;
						break;
					}
				}
			}
			if (!found) {
				ArrayList<String> list = new ArrayList<>();
				list.add(names[i][0]);
				list.add(names[i][1]);
				mapping.add(list);
			}
		}
		
		System.out.println(mapping.size());
		for (int i = 0; i < mapping.size(); i++) {
			System.out.println(mapping.get(i).get(0) + " " + mapping.get(i).get(mapping.get(i).size()-1));
		}
		return;
	}
	public static String contest(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int a = scan.nextInt();
		int b = scan.nextInt();
		int c = scan.nextInt();
		int d = scan.nextInt();
		int p1 = Math.max(3*a/10, a - a*c/250);
		int p2 = Math.max(3*b/10, b - b*d/250);
		if (p1 > p2) {
			return "Misha"; 
		} else if (p1 < p2) {
			return "Vasya";
		} else {
			return "Tie";
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
