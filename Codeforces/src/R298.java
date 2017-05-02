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


public class R298 implements Runnable {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		new Thread(null, new R298(), "whatever", 1<<26).start();
	}
	
	public void run() {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);

		PolycarpusDice();
//		Handshakes();

		out.close();
	}
	
	// Solved 
	public static void Handshakes() {
		int N = in.nextInt();
		int[] b = in.nextIntArray(N);
		int max = 0;
		for (int i = 0; i < b.length; i++) {
			max = Math.max(max, b[i]);
		}
		ArrayList<Stack<Integer>> a = new ArrayList<>();
		for (int i = 0; i <= max+1; i++) {
			a.add(new Stack<Integer>());
		}
		for (int i = 0; i < b.length; i++) {
			a.get(b[i]).add(i);
		}

		ArrayList<Integer> ret = new ArrayList<>();
		dfs2(0, ret, a, N, 0);
		
		if (ret.size() != N) {
			out.println("Impossible");
		} else {
			out.println("Possible");
			for (int i = 0; i < ret.size(); i++) {
				out.print((ret.get(i)+1) + " ");
			}
		}
	}
	
	public static void dfs2(int i, ArrayList<Integer> ret,
			ArrayList<Stack<Integer>> a, int N, int shakes) {

		if (ret.size() == N || shakes < 0 || shakes >= a.size()) {
			return;
		}
		
		Stack<Integer> idx = a.get(shakes);
		if (idx.isEmpty()) {
			// form 1 or more groups
			dfs2(0, ret, a, N, shakes-3);
			return;
		}
		ret.add(idx.pop());
		
		dfs2(0, ret, a, N, shakes+1);
		if (ret.size() != N) {
			idx.push(ret.remove(ret.size()-1));
		}
	}
	
	public static void dfs(int i, ArrayList<Integer> ret,
			ArrayList<Stack<Integer>> a, int N, int shakes) {

		//System.out.println(ret);
		if (ret.size() == N) {
			return;
		}
		
		Stack<Integer> idx = a.get(shakes);
		if (idx.isEmpty()) {
			return;
		}
		ret.add(idx.pop());
		
		if (!a.get(shakes+1).isEmpty()) {
			shakes++;
			dfs(i+1, ret, a, N, shakes);
		}
		if (shakes >= 2 && !a.get(shakes-2).isEmpty()) {
			shakes -= 3;
			dfs(i+1, ret, a, N, shakes);
		}
		if (ret.size() != N) {
			idx.push(ret.remove(ret.size()-1));
		}
	}

	public static void PolycarpusDice() {
		int N = in.nextInt();
		long A = in.nextLong();
		int[] d = in.nextIntArray(N);
		if (N == 1) {
			System.out.println(d[0] - 1);
		} else {
			long sum = 0;
			for (int i = 0; i < d.length; i++) {
				sum += d[i];
			}
			for (int i = 0; i < d.length; i++) {
				long otherMin = (N-1);
				long otherMax = sum - d[i];
				
				long diceMin = Math.max(1, A - otherMax);
				long diceMax = Math.min(d[i], A - otherMin);
				
				long known = d[i] - (diceMax - diceMin + 1);

				out.print(known + " ");
			}
		}
	}
	
	public static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
			tokenizer = null;
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
