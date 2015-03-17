import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;


class TestClass2 {
	public static void main(String[] args) {
		//aCoinGame();
		int n = 1;
		for (int i = 0; i < 10; i++) {
			System.out.println(n + "\t" + mehtasDilemma(n));
			n *= 10;
		}
	}
	
	public static int mehtasDilemma(int n) {
		boolean[][] fancy = new boolean[n][n];
		int f = 0;
		for (int i = 0; i < fancy.length; i++) {
			for (int j = 0; j < fancy[0].length; j++) {
				char[] num = Integer.toString(i*fancy[0].length + j).toCharArray();
				int product = 1;
				for (int k = 0; k < num.length; k++) {
					product *= (num[k] - '0');
				}
				int root = (int) Math.sqrt(product);
				if (root * root == product) {
					fancy[i][j] = true;
					f++;
				}
			}
		}
		
		return f;
//		for (int i = 0; i < fancy.length; i++) {
//			for (int j = 0; j < fancy[0].length; j++) {
//				if (fancy[i][j]) {
//					System.out.print("{}");
//				} else {
//					System.out.print("  ");
//				}
//			}
//			System.out.println();
//		}
	}

	public static final double phi = 1.61803398874989484820458683436563811772030917980576286213544862270526046281890;

	public static void aCoinGame() {
		HashSet<Pair<Integer, Integer>> cold = new HashSet<>();
		
		int n = 0;
		int m = 0;
		cold.add(new Pair<Integer, Integer>(n, m));
		int i = 0;
		while (n <= 1000000) {
			n = (int) Math.floor(i * phi);
			m = (int) Math.ceil(n * phi);
			cold.add(new Pair<Integer, Integer>(n, m));
			i++;
		}
		
		MyScanner scan = new MyScanner(System.in);
		int t = scan.nextInt();
		StringBuilder sb = new StringBuilder();
		String p1 = "Don't Play";
		String p2 = "Play";
		for (i = 0; i < t; i++) {
			n = scan.nextInt();
			m = scan.nextInt();
			Pair<Integer, Integer> p = new Pair<Integer, Integer>(Math.min(n, m), Math.max(n, m));
			if (cold.contains(p)) {
				sb.append(p1);
				sb.append("\n");
			} else {
				sb.append(p2);
				sb.append("\n");
			}
		}
		System.out.println(sb.toString());
	}


	public static class Pair<A extends Comparable<A>, B extends Comparable<B>>	implements Comparable<Pair<A, B>> {
		private A first;
		private B second;

		public Pair(A first, B second) {
			super();
			this.first = first;
			this.second = second;
		}

		public int hashCode() {
			int hashFirst = first != null ? first.hashCode() : 0;
			int hashSecond = second != null ? second.hashCode() : 0;

			return (hashFirst + hashSecond) * hashSecond + hashFirst;
		}

		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<A, B> otherPair = (Pair<A, B>) other;
				return 
						((this.first == otherPair.first ||
						(this.first != null && otherPair.first != null &&
						this.first.equals(otherPair.first))) &&
						(this.second == otherPair.second ||
						(this.second != null && otherPair.second != null &&
						this.second.equals(otherPair.second))));
			}

			return false;
		}

		public String toString()
		{ 
			return "(" + first + ", " + second + ")"; 
		}

		public A getFirst() {
			return first;
		}

		public void setFirst(A first) {
			this.first = first;
		}

		public B getSecond() {
			return second;
		}

		public void setSecond(B second) {
			this.second = second;
		}

		@Override
		public int compareTo(Pair<A, B> o) {
			int compareFirst = this.first.compareTo(o.first);
			if (compareFirst != 0) {
				return compareFirst;
			} else {
				return this.second.compareTo(o.second);
			}
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
