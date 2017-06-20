package gen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MyScanner {
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