import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;


public class R290 {
	public static void main(String[] args) {
		foxAndNames2(System.in);
	}		
	
	public static void foxAndNames2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		char[][] a = new char[n][];
		String[] s = new String[n];
		for (int i = 0; i < a.length; i++) {
			s[i] = scan.nextLine();
			a[i] = s[i].toCharArray();
		}
		HashMap<Integer, ArrayList<Integer>> dep = new HashMap<>();
		for (int i = 0; i < 26; i++) {
			dep.put(i, new ArrayList<Integer>());
		}
		for (int i = 1; i < a.length; i++) {
			if (s[i].indexOf(s[i-1]) == 0) {
				continue;
			} else if (s[i-1].indexOf(s[i]) == 0) {
				System.out.println("Impossible");
				return;
			} else {
				int t = 0;
				while (t < a[i].length && t < a[i-1].length && a[i][t] == a[i-1][t]) {
					t++;
				}
				if (t < a[i].length && t < a[i-1].length) {
					if (!dep.get(a[i-1][t] - 'a').contains(a[i][t] - 'a')) {
						dep.get(a[i-1][t] - 'a').add(a[i][t] - 'a');
					}
				}
			}
		}
		
		ArrayList<Integer> order = new ArrayList<>();
		if (!topSortIter(order, dep)) {
			System.out.println("Impossible");
			return;
		}
		
		String ret = "";
		for (int i = 0; i < order.size(); i++) {
			ret += (char)(order.get(i) +  'a');
		}
		System.out.println(ret);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean topSortIter(ArrayList<Integer> order, HashMap<Integer, ArrayList<Integer>> graphRef) {
		
		// Create a copy of the graph
		HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
		for (Integer key: graphRef.keySet()) {
			graph.put(key, (ArrayList<Integer>) graphRef.get(key).clone());
		}
		
		// Count number of incoming edges
		HashMap<Integer, Integer> numIncoming = new HashMap<>();
		for (Integer key: graph.keySet()) {
			ArrayList<Integer> children = graph.get(key);
			for (Integer child: children) {
				numIncoming.put(child, numIncoming.containsKey(child) ? numIncoming.get(child) + 1 : 1);
			}
		}
		
		// Select vertices with no incoming edges
		Stack<Integer> roots = new Stack<>(); // Doesn't have to be a stack!
		for (Integer key: graph.keySet()) {
			if (!numIncoming.containsKey(key) || numIncoming.get(key) == 0) {
				roots.push(key);
			}
		}
		
		// Build the ordering
		while (!roots.isEmpty()) {
			Integer top = roots.pop();
			order.add(top);
			ArrayList<Integer> children = graph.get(top);
			for (int i = 0; i < children.size(); i++) {
				Integer rem = children.remove(i--);
				numIncoming.put(rem, numIncoming.get(rem) - 1);
				if (numIncoming.get(rem) == 0) {
					roots.push(rem);
				}
			}
		}
		
		// Count number of edges
		int edges = 0;
		for (Integer key: graph.keySet()) {
			edges += graph.get(key).size();
		}
		
		return edges > 0 ? false : true;
	}
	
//	public static boolean topSortIter(ArrayList<Character> order, HashMap<Character, HashSet<Character>> dep) {
//		HashSet<Character> visited = new HashSet<>();
//		HashSet<Character> workingSet = new HashSet<>();
//		Stack<Character> stack = new Stack<>();
//		for (char i: dep.keySet()) {
//			if (!visited.contains(i)) {
//				char c = i;
//				stack.add(c);
//				while (!stack.isEmpty()) {
//					c = stack.pop();
//					if (workingSet.contains(c)) {
//						// Cycle detected!
//						return false;
//					}
//					if (!visited.contains(c)) {
//						workingSet.add(c);
//						HashSet<Character> children = dep.get(c);
//						for (char child: children) {
//							stack.add(child);
//						}
//						visited.add(c);
//						workingSet.remove(c);
//						order.add(c);
//					}
//				}
//			}
//		}
//		Collections.reverse(order);
//		return true;
//	}
	
	public static boolean topSortRecur(ArrayList<Character> order, HashMap<Character, HashSet<Character>> dep) {

		HashSet<Character> visited = new HashSet<>();
		HashSet<Character> workingSet = new HashSet<>();
		
		for (char c: dep.keySet()) {
			if (!visited.contains(c)) {
				if (!visit(c, visited, workingSet, order, dep)) {
					return false;
				}
			}
		}
		Collections.reverse(order);
		return true;
	}
	
	public static boolean visit(char c, HashSet<Character> visited, HashSet<Character> workingSet, ArrayList<Character> order, HashMap<Character, HashSet<Character>> dep) {
		if (workingSet.contains(c)) {
			return false;
		}
		if (!visited.contains(c)) {
			workingSet.add(c);
			HashSet<Character> children = dep.get(c);
			for (char child: children) {
				if (!visit(child, visited, workingSet, order, dep)) {
					return false;
				}
			}
			visited.add(c);
			workingSet.remove(c);
			order.add(c);
		}
		return true;
	}

	public static void foxAndNames(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		char[][] a = new char[n][];
		String[] s = new String[n];
		for (int i = 0; i < a.length; i++) {
			s[i] = scan.nextLine();
			a[i] = s[i].toCharArray();
		}
		HashMap<Character, HashSet<Character>> dep = new HashMap<>();
		for (int i = 0; i < 26; i++) {
			dep.put((char) (i + 'a'), new HashSet<Character>());
		}
		for (int i = 1; i < a.length; i++) {
			if (s[i].indexOf(s[i-1]) == 0) {
				continue;
			} else if (s[i-1].indexOf(s[i]) == 0) {
				System.out.println("Impossible");
				return;
			} else {
				int t = 0;
				while (t < a[i].length && t < a[i-1].length && a[i][t] == a[i-1][t]) {
					t++;
				}
				if (t < a[i].length && t < a[i-1].length) {
					dep.get(a[i-1][t]).add(a[i][t]);
				}
			}
		}
		
		int maxDepth = 1;
		int maxIdx = 0;
		ArrayList<int[]> depths = new ArrayList<>();
		for (int c = 0; c < 26; c++) {
			char top = (char) (c + 'a');
			char start = top;
			Stack<Character> st = new Stack<Character>();
			HashSet<Character> visited = new HashSet<>();
			st.add(top);
			boolean valid = true;
			int depth = 0;
			boolean first = true;
			while (!st.isEmpty()) {
				top = st.pop();
				if (!first && top == start) {
					valid = false; // Loop!
					break;
				}
				first = false;
				if (visited.contains(top)) {
					continue;
				}
				depth++;
				visited.add(top);
				if (dep.containsKey(top)) {
					HashSet<Character> children = dep.get(top);
					for (char child: children) {
						st.push(child);
					}
				}
			}
			
			if (!valid) {
				System.out.println("Impossible");
				return;
			}
			//if (depth > maxDepth) {
				depths.add(new int[]{depth , c});
				maxDepth = depth;
				maxIdx = c;
			//}
		}
		Collections.sort(depths, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o2[0], o1[0]);
			}			
		});
		
//		Queue<Character> st = new LinkedList<Character>();
//		char top = (char)(maxIdx + 'a');
//		st.add(top);
//		while (!st.isEmpty()) {
//			top = st.poll();
//			visited.add(top);
//			order.add(top);
//			if (dep.containsKey(top)) {
//				HashSet<Character> children = dep.get(top);
//				for (char child: children) {
//					st.add(child);
//				}
//			}
//		}
		
		int[] depth = new int[26];
		Arrays.fill(depth, -1);
		maxDepth = 0;
		for (int i = 0; i < depths.size(); i++) {
			char top = (char) (depths.get(i)[1] + 'a');
			char start = top;
			depth[depths.get(i)[1]] = depth(top, dep, depth);
			maxDepth = Math.max(maxDepth, depth[depths.get(i)[1]]);
		}
		
		ArrayList<Character> order = new ArrayList<>();
		for (int i = maxDepth; i >= -1; i--) {
			for (int j = 0; j < depth.length; j++) {
				if (depth[j] == i) {
					order.add((char) (j + 'a'));
				}
			}
		}
		
		//HashSet<Character> visited = new HashSet<>();
		
//		char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
//		for (int i = 0; i < alpha.length; i++) {
//			//if (!visited.contains(alpha[i])) {
//			order.add(alpha[i]);
//			//}
//		}
//		
//		for (int i = 0; i < alpha.length; i++) {
//			int bestIdx = alpha.length-1;
//			char top = (char)(i + 'a');
//			HashSet<Character> after = (HashSet<Character>) dep.get(top).clone();
//			for (int j = 0; j < alpha.length; j++) {
//				if (after.contains(order.get(j))) {
//					
//				}
//				if (after.isEmpty()) {
//					break;
//				}
//			} 
//		}
		String ret = "";
		for (int i = 0; i < order.size(); i++) {
			ret += order.get(i);
		}
		System.out.println(ret);
	}

	public static int depth(char top, HashMap<Character, HashSet<Character>> dep, int[] depth) {
		if (depth[top - 'a'] > -1) {
			return depth[top - 'a'];
		}
		int max = 0;
		HashSet<Character> children = dep.get(top);
		for (char child: children) {
			max = Math.max(max, depth(child, dep, depth) + 1);
		}
		depth[top - 'a'] = max;
		return max;
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
