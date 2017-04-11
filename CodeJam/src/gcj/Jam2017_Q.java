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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
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
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Iterator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Jam2017_Q {
	//10^4 <  Short.MAX_VALUE (2^15)   < 10^5
	//10^9 <  Integer.MAX_VALUE (2^31) < 10^10
	//10^18 < Long.MAX_VALUE (2^63)    < 10^20

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	// path: C:\Users\Lucas\workspace_tc\CodeJam\src\gcj\Jam2016.java
	//static String filename = "C:\\Users\\Lucas\\workspace_tc\\CodeJam\\input\\2017\\Q\\A-small-attempt0";
	static String filename = "C:\\Users\\Lucas\\workspace_tc\\CodeJam\\input\\2017\\Q\\C-large";

	static String outputfile = filename + ".out";
	static MyScanner scan;
	static PrintStream out;

	public static void main(String[] args) throws NumberFormatException, IOException {
        String location = Jam2017_Q.class.toString();
        // Print source file path
        Files.find(Paths.get("C:\\Users\\Lucas\\workspace_tc\\CodeJam\\src\\gcj"),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile())
             .map(Path::toString)
             .filter(a -> a.indexOf(location.substring(location.indexOf(".") + 1)) >= 0)
             .forEach(System.out::println);
        
		scan = new MyScanner(new FileInputStream(filename+".in"));
		out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputfile)));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);
		
//		OversizedPancakeFlipper();
//		TidyNumbers();
		BathroomStalls();

		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
		scan.close();
		out.close();
	}

	public static void BathroomStalls() {
		int tests = scan.nextInt();
		for (int test = 0; test < tests; test++) {
			long n = scan.nextLong();
			long k = scan.nextLong();
			out.println("Case #" + (test+1) + ": " +BathroomStallsFast(n, k));
		}
	}

	public static String BathroomStallsFast(long N, long K) {
		// Total forgot about TreeSet...

		DefaultDict<Long,Long> a = new DefaultDict<>(0L);
		HashSet<Long> enqueued = new HashSet<>(); 
		PriorityQueue<Long> queue = new PriorityQueue<>(new Comparator<Long>() {
			@Override
			public int compare(Long a, Long b) {
				return Long.compare(b, a);
			}});

		long k = K;
		a.put(N, 1L);
		queue.add(N);
		enqueued.add(N);
		
		long i = N;
		long lastMax = 0;
		long lastMin = 0;
		while (a.size() > 0) {
			i = queue.poll();
			if (i == 0) {
				break;
			}
			long max = a.get(i);
			lastMax = i/2;
			lastMin = (i-1)/2;
			a.put(lastMax, max + a.get(lastMax));
			a.put(lastMin, max + a.get(lastMin));
			k -= max;
			a.put(i, 0L);
			if (!enqueued.contains(lastMax)) {
				enqueued.add(lastMax);
				queue.add(lastMax);
			}
			if (!enqueued.contains(lastMin)) {
				enqueued.add(lastMin);
				queue.add(lastMin);
			}
			
			if (k <= 0) {
				break;
			}
		}
		return lastMax + " " + lastMin;
	}

	public static String BathroomStalls(int N, int K) {
		int[] a = new int[(N+1)];
		int k = K;
		a[N]++;
		int lastMax = 0;
		int lastMin = 0;
		for (int i = a.length-1; i >= 1; i--) {
			a[i/2] += a[i];
			a[(i-1)/2] += a[i];
			k -= a[i];
			a[i] = 0;
			lastMax = i/2;
			lastMin = (i-1)/2;
			if (k <= 0) {
				break;
			}
		}
		return lastMax + " " + lastMin;
	}

	public static void TidyNumbers() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			long K = scan.nextLong();
			out.println("Case #" + (test+1) + ": " +TidyNumbers(K));
		}
	}

	public static String TidyNumbers(long n) {
		char[] x = (n + "").toCharArray();
		for (int i = 1; i < x.length; i++) {
			if (x[i-1] > x[i]) {
				x[i-1] -= 1;
				for (int j = i; j < x.length; j++) {
					x[j] = '9';
				}
				if (i == 1 && x[0] == '0') {
					x = Arrays.copyOfRange(x, 1, x.length);
				}
				i = 0;
			}
		}
		return new String(x);
	}

	public static void OversizedPancakeFlipper() {
		int tests = scan.nextInt();

		for (int test = 0; test < tests; test++) {
			char[] S = scan.next().toCharArray();
			int K = scan.nextInt();
			out.println("Case #" + (test+1) + ": " +OversizedPancakeFlipper(S, K));
		}
	}

	public static String OversizedPancakeFlipper(char[] S, int K) {
		int flip = 0 ;
		for (int i = 0; i <= S.length - K; i++) {
			if (S[i] == '-') {
				for (int j = i; j < i+K; j++) {
					S[j] = S[j] == '-' ? '+' : '-'; 
				}
				flip++;
			}
		}
		int upside = 0;
		for (int i = 0; i < S.length; i++) {
			if (S[i] == '+') {
				upside++;
			}
		}
		if (upside == S.length) {
			return flip + "";
		}
		return "IMPOSSIBLE";
	}

	@SuppressWarnings("serial")
	public static class DefaultDict<K, V> extends HashMap<K, V> {
		private V defaultValue;  
	    public DefaultDict(V defaultValue) {
	        this.defaultValue = defaultValue;    
	    }
	
	    @SuppressWarnings("unchecked")
		@Override
	    public V get(Object key) {
	        V returnValue = super.get(key);
	        if (returnValue == null) {
	            try {
	                returnValue = this.defaultValue;
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	            this.put((K) key, returnValue);
	        }
	        return returnValue;
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