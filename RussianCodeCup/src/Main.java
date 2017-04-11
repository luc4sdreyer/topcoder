import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) {
		testA();
//		probA(System.in);
	}
	
	public static void testA() {
		for (int A = 1; A <= 10; A++) {
			for (int B = 1; B <= 10; B++) {
				for (int C = 1; C <= 10; C++) {
					long ret1 = probA(A,B,C);
					
					long minDiff = Long.MAX_VALUE; 
					long a = 0;
					for (int i = 0; i <= A+1; i++) {
						for (int j = 0; j <= B+1; j++) {
							long area = i*j*C*C;
							if ((i > 0 && j > 0) && minDiff > Math.abs(A*B - area)) {
								minDiff = Math.abs(A*B - area);
								a = area;
							}
//							if (i*C <= A && j*C <= B) {
//								
//							} else if (i*C <= A) {
//								
//							} else if (j*C <= B) {
//								
//							} else {
//								
//							}
						}
					}
					a = Math.max(C*C, a);
					if (ret1 != minDiff) {
						System.out.println("fail");
						probA(A,B,C);
					}
				}
			}
		}
	}

	public static void probA(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			long a = scan.nextLong();
			long b = scan.nextLong();
			long c = scan.nextLong();
			System.out.println(probA(a,b,c));
			
		}
	}

	public static long probA(long a, long b, long c) {
		long width = 0;
		if (a >= c) {
			width = (long)Math.round(a / c);
		}
		width = Math.max(1, width);
		
		long height = 0;
		if (b >= c) {
			height = (long)Math.round(b / c);
		}
		height = Math.max(1, height);
		
		boolean wOutside = width * c > a;
		boolean hOutside = height * c > b;
		long extra = 0;
		long right = 0;
		long left = 0;
		
		if (height == 1 && width == 1) {
			long corner = (a % c) * (a % b);
			extra = -1;
			if (corner > c*c/2.0) {
				extra = 0;
			}
		} else if (height == 1) {
			width = Math.round(a*b/(double)(c*c));
		} else if (width == 1) {
			height = Math.round(a*b/(double)(c*c));
		} else {
			if (c/2.0 > (c % a)) {
				// better to add
				right = b/c;
			} else {
				right = b/c;
			}
//			if (wOutside && hOutside) {
//				long corner = (a % c) * (a % b);
//				extra = -1;
//				if (corner > c*c/2.0) {
//					extra = 0;
//				}
//			}
		}
		
		long area = (height*width + extra);
		area = Math.max(1, area);
		area = area * c * c;
		
		//return area;
		return Math.abs(a*b - area);
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
