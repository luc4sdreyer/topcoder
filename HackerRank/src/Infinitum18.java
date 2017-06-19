import java.io.*;
import java.util.*;

/**
 * infinitum18
 */

public class Infinitum18 {
    public static InputReader in;
    public static PrintWriter out;
    public static boolean DEBUG = false;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, false);

        divisorExploration3();
//        testCountSolutions();
//        countSolutions();
//        tower3coloring();
//        pythagoreanTriple();
//        minimumHeightTriangle();

        out.close();
    }
    
    public static void divisorExploration3() {
    	for (int n = 1; n <= 100000; n++) {
    		int sum = 0;
			for (int i = 1; i <= n; i++) {
				if (n % i == 0) {
					sum += eulerTotientFunction(i);
				}
			}
			if (sum != n) {
				System.out.println("n: " + n + "\t sum: " + sum);
			}
		}
    }
    
    public static void testCountSolutions() {
    	int lim = 100;
    	for (int a = 1; a < lim; a++) {
			for (int b = 1; b < lim; b++) {
				for (int c = 1; c < lim; c++) {
					for (int d = 1; d < lim; d++) {
//						System.out.println();
						int exp = countSolutionsOld(a,b,c,d);
						int act = (int) countSolutions(a,b,c,d);
						if (exp != act) {
							System.out.println("fail");
							exp = countSolutionsOld(a,b,c,d);
							act = (int) countSolutions(a,b,c,d);
						}
					}
				}
			}
		}
    }

    public static void countSolutions2() {
    	System.out.println(countSolutions(53, 21, 10000, 10000));
    	System.out.println(countSolutions(21, 53, 10000, 10000));
//    	for (int i = 1; i <= 100; i++) {
//    		for (int j = 1; j <= 100; j++) {
//    			System.out.print(countSolutions(i, j, 1000, 1000) + "\t");
//    		}
//    		System.out.println();
//		}
    }

    public static void countSolutions() {
        int queries = in.nextInt();
        for (int q = 0; q < queries; q++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int c = in.nextInt();
			int d = in.nextInt();
			System.out.println(countSolutions(a,b,c,d));
		}
    }

	public static int countSolutionsOld(int a, int b, int c, int d) {
		int sol = 0;
		for (long x = 1; x <= c; x++) {
			for (long y = 1; y <= d; y++) {
				if (x * x + y * y == x * a + y * b) {
					sol++;
//					System.out.println(x + " " + y);
				}
				if (x > a && y > b) {
					break;
				}
			}
		}
		return sol;
	}
	public static long countSolutions(long a, long b, long c, long d) {
		long sol = 0;
		if (d > c) {
			sol = findSol(a, b, c, d); 
		} else {
			sol = findSol(b, a, d, c);
		}
		
		return sol;
	}

	private static long findSol(long a, long b, long c, long d) {
		long sol = 0;
		for (long x = 1; x <= c; x++) {
			long ps = b*b + 4L*x*a - 4L*x*x;
			if (ps >= 0) {
				long s = (long) Math.floor(Math.sqrt(ps));
				if (s*s == ps) {
					if (checkSol(b + s, a, b, c, d)) {
						sol++;
					}
					if (s != 0 && checkSol(b - s, a, b, c, d)) {
						sol++;
					}
				}
			}
		}
		return sol;
	}

	public static boolean checkSol(long s, long a, long b, long c, long d) {
		if (s % 2L == 0) {
			s /= 2L;
			if (s > 0 && s <= d) {
				return true;
			}
		}
		return false;
	}

	public static final int mod = 1000000007;
    //public static final int mod = 10007;
    
    public static void tower3coloring() {
        int n = in.nextInt();
        int res = tower3coloring(n);
    	//System.out.println(i  + " " +res + " " + res2);
        System.out.println(res);
    }
    
	public static int tower3coloring2(int n) {
		long res = 1;
		for (int i = 0; i < n; i++) {
			//res = (res * 3) % mod;
			res *= 3;
		}
		long res2 = 1;
		for (int i = 0; i < res; i++) {
			res2 = (res2 * 3) % mod;
		}
		return (int) res2;
	}
    
	public static int tower3coloring(int n) {
		int md = eulerTotientFunction(mod);
		int exp = fastModularExponent(3, n, md);
		return fastModularExponent(3, exp, mod);
	}
	
	public static int eulerTotientFunction(int n) {
		int result = n;
		for (int i = 2; i*i <= n; i++) {
			if (n % i == 0) {
				result -= result / i;
			}
			while (n % i == 0) {
				n /= i;
			}
		}
		   
		if (n > 1) {
			result -= result / n;
		}
		return result;
	}

	public static int fastModularExponent(int a, int exp, int mod) {
		long[] results = new long[65];
		long m = mod;
		int power = 1;
		long res = 1;
		while (exp > 0) {
			if (power == 1) {
				results[power] = a % m;
			} else {
				results[power] = (results[power-1] * results[power-1]) % m;
			}
			if (exp % 2 == 1) {
				res = (res * results[power]) % m;
			}
			exp /= 2;
			power++;
		}
		return (int) (res % m);
	}

    public static void pythagoreanTriple() {
        long a = in.nextLong();
        long start = 0;
        long aLim = a;
        if (aLim % 2 == 1) {
        	aLim--;
        }
        for (long r = 0; r < a; r+=2) {
        	if (calc(r, a)) {
        		return;
        	}
        	if (calc(aLim - r, a)) {
        		return;
        	}
        }
    }

    public static boolean calc(long r, long a) {
        long s = a - r;
        double x = (r*r) / (2.0*s);
        if (r > 0 && s > 0 && (r*r) % (2*s) == 0) {
            long t = (r*r) / (2*s);
            long b = r + t;
            long c = r +s +t;
            //System.out.println(r + " " + s + " " + t);
            System.out.println(a + " " + b + " " + c);
            return true;
        }
        return false;
	}

	public static void minimumHeightTriangle() {
        int b = in.nextInt();
        int a = in.nextInt();
        System.out.println((int)Math.ceil(a*2.0 / b));
    }

    public static class InputReader {
        public BufferedReader r;
        public StringTokenizer st;
        public InputReader(InputStream s) {r = new BufferedReader(new InputStreamReader(s), 32768); st = null;}
        public String next() {while (st == null || !st.hasMoreTokens()) {try {st = new StringTokenizer(r.readLine());}
        catch (IOException e) {throw new RuntimeException(e);}} return st.nextToken();}
        public int nextInt() {return Integer.parseInt(next());}
        public long nextLong() {return Long.parseLong(next());}
        public double nextDouble() {return Double.parseDouble(next());}
        public long[] nextLongArray(int n) {long[] a = new long[n]; for (int i = 0; i < a.length; i++) {a[i] = this.nextLong();} return a;}
        public int[] nextIntArray(int n) {int[] a = new int[n];	for (int i = 0; i < a.length; i++) {a[i] = this.nextInt();} return a;}
    }
}
