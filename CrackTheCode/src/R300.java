import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class R300 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);

		code();

		out.close();
	}

	public static void code() {
		int n = 5422;
		int start = 0;
		for (int i = 0; i < n-1; i++) {
			if (i % 2 == 0) {
				start += 1;
			} else {
				start += 11;
			}
			System.out.println(start);
		}
		
//		int n = 5114;
//		int start = 9;
//		for (int i = 0; i < n-1; i++) {
//			if (i % 2 == 0) {
//				start += 2;
//			} else {
//				start += 10;
//			}
//			System.out.println(start);
//		}
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
