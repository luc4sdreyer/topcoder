import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class HackJan15 {
	public static void main(String[] args) {
		//angryProfessor(System.in);
		maximiseSum4(System.in);
	}

	public static void maximiseSum4(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		
		for (int t = 0; t < tests; t++) {
			long n = scan.nextLong();
			long m = scan.nextLong();
			long[] a = scan.nextLongArray((int) n);
			long max = 0;
			long[] sum = new long[(int) n];
			
			long maxSingle = 0;
			for (int i = 0; i < a.length; i++) {
				maxSingle = Math.max(maxSingle % m, a[i]);
			}
			
			for (int i = 0; i < a.length; i++) {
				if (i == 0) {
					sum[0] = a[0] % m;
				} else {
					sum[i] = (sum[i-1] + a[i]) % m;
				}
				max = Math.max(max, sum[i]);
			}
			
			LinkedList<Long> sumList = new LinkedList<>();
			for (int i = 0; i < sum.length; i++) {
				sumList.add(sum[i]);
			}
			Collections.sort(sumList);
			for (int i = 0; i < sum.length; i++) {
				long diff = 0;
				if (i > 0) {
					diff = m - (a[i-1] % m);
				}
				long newM = m - diff - 1;
				//
				// find newM, or any before it
				//
				//sumList.
			}
			
			System.out.println(max);
		}
	}

	public static void maximiseSum3(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		
		for (int t = 0; t < tests; t++) {
			long n = scan.nextLong();
			long m = scan.nextLong();
			long[] a = scan.nextLongArray((int) n);
			long max = 0;
			long[] sum = new long[(int) n];
			
			for (int i = 0; i < a.length; i++) {
				if (i == 0) {
					sum[0] = a[0] % m;
				} else {
					sum[i] = (sum[i-1] + a[i]) % m;
				}
				max = Math.max(max, sum[i]);
			}
			//System.out.println(Arrays.toString(sum));
			for (int i = 0; i < sum.length; i++) {
				long diff = m - (a[i] % m);
				for (int j = i; j < sum.length; j++) {
					sum[j] = (sum[j] + diff) % m;
					max = Math.max(max, sum[j]);
				}
				//System.out.println(Arrays.toString(sum));
			}
			
			System.out.println(max);
		}
	}

	public static void maximiseSum2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		
		for (int t = 0; t < tests; t++) {
			long n = scan.nextLong();
			long m = scan.nextLong();
			long[] a = scan.nextLongArray((int) n);
			long max = 0;
			for (int i = 0; i < a.length; i++) {
				for (int j = i; j < a.length; j++) {
					long sum = 0;
					for (int k = i; k <= j; k++) {
						sum = (sum + a[k]) % m;
					}
					max = Math.max(max, sum);
				}
			}
			System.out.println(max);
		}
	}

	public static void maximiseSum(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		
		for (int t = 0; t < tests; t++) {
			long n = scan.nextLong();
			long m = scan.nextLong();
			long[] a = scan.nextLongArray((int) n);
			
			long maxEnding = 0;
			long maxSlice = 0;
			long maxSingle = 0;
			for (int i = 0; i < a.length; i++) {
				maxSingle = Math.max(maxSingle % m, a[i]);
			}
			
			for (int i = 0; i < a.length; i++) {
				maxEnding = Math.max(0, (maxEnding + a[i]) % m);
				maxSlice = Math.max(maxSlice, maxEnding);
			}
			if (maxSlice == 0) {
				maxSlice = maxSingle;
			}
			System.out.println(maxSlice);
		}
	}

	public static void angryProfessor(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			int[] a = scan.nextIntArray(n);
			int before = 0;
			for (int j = 0; j < a.length; j++) {
				if (a[j] <= 0) {
					before++;
				}
			}
			if (before >= k) {
				System.out.println("NO");
			} else {
				System.out.println("YES");
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
