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


public class f2017R1 {

	public static void main(String[] args) throws FileNotFoundException {
		pieProgress("./input/f2017R1/pie_progress");
	}

	public static void pieProgress(String filename) throws FileNotFoundException {
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");

			int N = scan.nextInt();
			int M = scan.nextInt();
			long[][] C = new long[N][M];
			for (int j = 0; j < C.length; j++) {
				C[j] = scan.nextLongArray(M);
				Arrays.sort(C[j]);
			}
			output.append(pieProgress(N, M, C));

			out.println(output.toString());
		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}
	
	public static long pieProgress(int N, int M, long[][] C) {
		long[][] T = new long[N][M+1];
		for (int i = 0; i < C.length; i++) {
			for (int j = 1; j <= C[0].length; j++) {
				T[i][j] = T[i][j-1] + C[i][j-1];
			}
			for (int j = 1; j <= C[0].length; j++) {
				T[i][j] += (j)*(j); 
			}
		}
		
		long[][] F = new long[N][N+1];
		
		for (int i = 0; i <= N && i < T[0].length; i++) {
			F[0][i] = T[0][i];
		}
		
		for (int d = 1; d < N; d++) {
			for (int p = 0; p <= N; p++) {
				for (int buy = 0; buy <= N; buy++) {
					if (p+1-buy >= 1 && buy < T[0].length && p+1-buy <= N) {
						long c = F[d-1][p+1-buy] + T[d][buy];
						if (F[d-1][p+1-buy] != 0) {
							if (F[d][p] == 0) {
								F[d][p] = c;
							} else {
								F[d][p] = Math.min(F[d][p], c);	
							}
						}
					}
				}
			}
		}
		
		return F[N-1][1];
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
