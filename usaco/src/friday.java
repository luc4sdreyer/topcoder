/*
ID: luc4sdr1
LANG: JAVA
TASK: friday
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

class friday {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);
	public static String input_name = "friday";

	public static void main(String[] args) throws IOException {
//		OutputStream outputStream = System.out;
		in = new InputReader(new FileReader(input_name + ".in"));
		out = new PrintWriter(new BufferedWriter(new FileWriter(input_name + ".out")));
//		out = new PrintWriter(outputStream, true);
//		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed

		solve();
		out.close();
	}
	
	public static void solve() {
		int N = in.nextInt();
		int year = 1900;
		int dayWeek = 0; // Monday
		int dayMonth = 1; // first
		int month = 1; // January
		int dayYear = 1;
		int[] d13 = new int[7];
		while (year < 1900 + N) {
			if (dayMonth == 13) {
				d13[dayWeek]++;
			}
			if (dayMonth >= 28) {
				boolean nextMonth = false;
				if (month == 2) {
					boolean leap = false;
					if (year % 400 == 0) {
						leap = true;
					} else if (year % 100 == 0) {
						leap = false;
					} else if (year % 4 == 0) {
						leap = true;
					}
					if ((leap && dayMonth == 29) || (!leap && dayMonth == 28)) {
						nextMonth = true;
					}
				} else if ((month == 4 || month == 6 || month == 9 || month == 11) && dayMonth == 30) {
					nextMonth = true;
				} else if (dayMonth == 31) {
					nextMonth = true;
				}
				if (nextMonth) {
					if (month == 12) {
						year++;
						month = 1;
						dayYear = 0;
					} else {
						month++;
					}
					dayMonth = 0;
				}
			}
			dayYear++;
			dayWeek = (dayWeek + 1) % 7;
			dayMonth++;
		}
		
		for (int i = 0; i < d13.length; i++) {
			if (i > 0) 
			{
				out.print(" ");
			}
			out.print(d13[(i + 5) % 7]);
		}
		out.print('\n');
		
	}
	
	@SuppressWarnings("serial")
	public static class Counter<T> extends HashMap<T, Integer> {
		public Counter() {
			super();
		}
		public void add(T key) {
			this.add(key, 1);
		}
		public void add(T key, int count) {
			Integer i = this.get(key);
			if (i == null) {
				this.put(key, count);
			} else {
				this.put(key, i+count);
			}
		}
	}

	public static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer = null;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
		}
		public InputReader(InputStreamReader stream) {
			reader = new BufferedReader(stream, 32768);
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {return Integer.parseInt(next());}
		public long nextLong() {return Long.parseLong(next());}
		public double nextDouble() {return Double.parseDouble(next());}
		public long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) a[i] = this.nextLong();
			return a;
		}
		public int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) a[i] = this.nextInt();
			return a;
		}
	}
}
