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

public class R291 {
	public static void main(String[] args) {
//		prefTestWatto();
//		testWatto();
		watto(System.in);
//		hanSolo(System.in);
//		chewbacca(System.in);
	}
	
	public static void prefTestWatto() {
		Random rand = new Random(0);
		for (int i = 0; i < 10; i++) {
			int numD = 500; 
			int numQ = 500;
			
			String[] data = new String[numD];
			for (int j = 0; j < data.length; j++) {
				int len = 6000;
				String s = "";
				for (int k = 0; k < len; k++) {
					s += ((char)('a' + rand.nextInt(3)) + "");
				}
				data[j] = s;
			}
			String[] q = new String[numQ];
			for (int j = 0; j < q.length; j++) {
				char[] s = data[j].toCharArray();
				int idx = rand.nextInt(s.length)/10; 
				s[idx] = (char) (s[idx]+1); 
				if (s[idx] == 'd') {
					s[idx] = 'a';
				} 
				q[j] = new String(s);
			}
			

//			String[] r1 = wattoSlow(numD, numQ, data, q);
//			String[] r2 = watto(numD, numQ, data, q);
//			for (int j = 0; j < r2.length; j++) {
//				if (r1[j] != r2[j]) {
//					System.out.println("fail");
//					wattoSlow(numD, numQ, data, q);
//					watto(numD, numQ, data, q);
//				}
//			}
			
			long t1 = System.currentTimeMillis();
			String[] r1 = watto(numD, numQ, data, q);
			System.out.println("t1: \t" + (System.currentTimeMillis()-t1));
			
			t1 = System.currentTimeMillis();
			String[] r2 = watto2(numD, numQ, data, q);
			System.out.println("t2: \t" + (System.currentTimeMillis()-t1));
			System.out.println();
			
			for (int j = 0; j < r2.length; j++) {
				if (r1[j] != r2[j]) {
					System.out.println("fail");
					watto(numD, numQ, data, q);
					watto2(numD, numQ, data, q);
				}
			}
		}
	}
	
	public static void testWatto() {
		Random rand = new Random(0);
		for (int i = 0; i < 10000; i++) {
			int numD = rand.nextInt(100)+1; 
			int numQ = rand.nextInt(100)+1;
			
			String[] data = new String[numD];
			for (int j = 0; j < data.length; j++) {
				int len = rand.nextInt(10)+1;
				String s = "";
				for (int k = 0; k < len; k++) {
					s += ((char)('a' + rand.nextInt(3)) + "");
				}
				data[j] = s;
			}
			String[] q = new String[numQ];
			for (int j = 0; j < q.length; j++) {
				int len = rand.nextInt(10)+1;
				String s = "";
				for (int k = 0; k < len; k++) {
					s += ((char)('a' + rand.nextInt(3)) + "");
				}
				q[j] = s;
			}
			

//			String[] r1 = wattoSlow(numD, numQ, data, q);
//			String[] r2 = watto(numD, numQ, data, q);
//			for (int j = 0; j < r2.length; j++) {
//				if (r1[j] != r2[j]) {
//					System.out.println("fail");
//					wattoSlow(numD, numQ, data, q);
//					watto(numD, numQ, data, q);
//				}
//			}
			
			String[] r1 = watto(numD, numQ, data, q);
			String[] r2 = watto2(numD, numQ, data, q);
			for (int j = 0; j < r2.length; j++) {
				if (r1[j] != r2[j]) {
					System.out.println("fail");
					watto(numD, numQ, data, q);
					watto2(numD, numQ, data, q);
				}
			}
		}
	}
	
	public static String[] wattoSlow(int numD, int numQ, String[] data, String[] q) {
		HashSet<String> set = new HashSet<>();
		for (int j = 0; j < data.length; j++) {
			set.add(data[j]);
		}
		
		String[] r1 = new String[q.length];
		for (int j = 0; j < q.length; j++) {
			r1[j] = "NO";
			for (int j2 = 0; j2 < q[j].length(); j2++) {
				for (int k = 0; k < 3; k++) {
					char[] ns = q[j].toCharArray();
					if (ns[j2] != (char) (k + 'a')) {
						ns[j2] = (char) (k + 'a');
						String ns2 = new String(ns);
						if (set.contains(ns2)) {
							r1[j] = "YES";
							break;
						}
					}
				}
			}
		}
		return r1;
	}

	public static void watto(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		String[] data = new String[n];
		for (int i = 0; i < data.length; i++) {
			data[i] = scan.next();
		}
		String[] q = new String[m];
		for (int i = 0; i < q.length; i++) {
			q[i] = scan.next();
		}
		String[] ret = watto2(n, m, data, q);
		for (int i = 0; i < ret.length; i++) {
			System.out.println(ret[i]);
		}
	}
	
	public static String[] watto2(int n, int m, String[] data, String[] q) {
		//long t1 = System.currentTimeMillis();
		
		String[] out = new String[q.length];
		
		Trie trie = new Trie(3);
		for (int i = 0; i < data.length; i++) {
			trie.addWord(Trie.toTrieValue(data[i]));
		}
		//System.out.println("t2 init time: " + (System.currentTimeMillis()-t1));
		
		for (int i = 0; i < q.length; i++) {
			if (trie.findSingleMistake(Trie.toTrieValue(q[i]))) {
				out[i] = "YES";
			} else {
				out[i] = "NO";
			}
		}
		return out;
	}
	
	/**
	 * Prefix trie with DFS
	 */
	
	public static String[] watto(int n, int m, String[] data, String[] q) {
		String[] out = new String[q.length];
		
		Node root = new Node('x');
		for (int i = 0; i < data.length; i++) {
			buildTree(root, data[i], 0);
		}
		for (int i = 0; i < q.length; i++) {
			boolean res = false;
			for (int j = 0; j < root.children.length; j++) {
				if (root.children[j] != null) {
					int miss = 0;
					if (q[i].charAt(0) != j + 'a') {
						miss++;
					}
					res = traverse(root.children[j], q[i], 1, miss);
				}
				if (res) {
					break;
				}
			}
			if (res) {
				out[i] = "YES";
			} else {
				out[i] = "NO";
			}
		}
		return out;
	}

	public static class TrieNode {
		private int words;
		private int prefixes;
		private TrieNode[] edges;
		private int depth;
		private TrieNode parent;
		
		public TrieNode(int alphabetSize, TrieNode parent) {
			this.edges = new TrieNode[alphabetSize];
			this.depth = parent == null ? 0 : parent.depth + 1;
			this.parent = parent;
		}
		
		public String toString() {
			return "(" + words + ", " + prefixes + ")";
		}
	}
	
	public static class Trie {
		private TrieNode root;
		private int size;
		private final int alphabetSize;
		
		public Trie(int alphabetSize) {
			this.alphabetSize = alphabetSize;
			this.root = new TrieNode(alphabetSize, null);
		}
		
		/**
		 * Add the word to the Trie. max(word[i]) must be less than alphabetSize!
		 */
		public void addWord(int[] word) {
			addWord(root, word);
		}
		
		public void removeWord(int[] word) {
			if (size > 0 && countWords(word) > 0) {
				removeWord(root, word);
			}
		}
		
		public int countWords(int[] word) {
			return countWords(root, word);
		}
		
		public int countPrefixes(int[] prefix) {
			return countPrefixes(root, prefix);
		}
		
		/**
		 * Find the first point where a given word is unique and return the length. 
		 * For example: this returns the number of keypresses required for an autocompleter to match a word, assuming the word is present.
		 */
		public int findShortestUniqueString(int[] word) {
			return findShortestUniqueString(root, word);
		}
		
		/**
		 * Find the closes match of the word. Only works on binary tries! 
		 * Can be used to find the maximum XOR of two elements in an array in O(nlog(n))
		 */
		public int[] find(int[] word) {
			int n = countWords(word);
			if (n > 0) {
				return word;
			} else {
				int[] bestMatch = word.clone();
				find(root, bestMatch, 0);
				return bestMatch;
			}
		}
		
		private void find(TrieNode vertex, int[] word, int length) {
			if (length < word.length) {
				int best = word[length];
				int alt = (word[length] + 1) % 2;
				if (vertex.edges[best] == null) {
					if (vertex.edges[alt] == null) {
						return;
					}
					word[length] = alt;
					find(vertex.edges[alt], word, length+1);
				} else {
					find(vertex.edges[best], word, length+1);
				}
			}
			return;
		}
		
		/**
		 * Determine if this word with exactly one spelling mistake (one character different) exists in the trie.
		 */
		public boolean findSingleMistake(int[] s) {
			SearchNode top = new SearchNode(root, 0, 0);
			Stack<SearchNode> stack = new Stack<>();
			stack.add(top);
			
			while (!stack.isEmpty()) {
				top = stack.pop();

				if (top.miss > 1) {
					continue;
				}
				
				if (top.idx == s.length) {
					if (top.miss == 1 && top.n.words > 0) {
						return true;
					} else {
						continue;
					}
				}
				
				for (int j = 0; j < top.n.edges.length; j++) {
					if (top.n.edges[j] != null) {
						int mymiss = top.miss;
						if (s[top.idx] != j) {
							mymiss++;
						}
						stack.add(new SearchNode(top.n.edges[j], top.idx+1, mymiss));
					}
				}
			}
			
			return false;
		}
		
		public static class SearchNode {
			TrieNode n;
			int idx;
			int miss;
			public SearchNode(TrieNode n, int idx, int miss) {
				super();
				this.n = n;
				this.idx = idx;
				this.miss = miss;
			}
		}
		
		/**
		 * Get the lexicographically next entry. (Resumed in-order traversal). Returns null if there is nothing after it.
		 */
		public int[] getNext(int[] word) {
			int[] bestMatch = word.clone();
			getNext(root, word, 0);
			//findClosestString(root, bestMatch, 0);
			return bestMatch;
		}

		private void getNext(TrieNode root, int[] word, int length) {
			//
			// Keep
			//
		}
		
		private void addWord(TrieNode vertex, int[] word) {
			for (int length = 0; length < word.length; length++) {
				vertex.prefixes++;
				int k = word[length];
				if (vertex.edges[k] == null) {
					vertex.edges[k] = new TrieNode(alphabetSize, vertex);
				}
				vertex = vertex.edges[k];
			}
			vertex.words++;
			size++;
		}
		
		private void removeWord(TrieNode vertex, int[] word) {
			for (int length = 0; length < word.length; length++) {
				if (vertex.prefixes > 0 && size > 0) {
					vertex.prefixes--;
				}
				int k = word[length];
				if (vertex.edges[k] == null) {
					vertex.edges[k] = new TrieNode(alphabetSize, vertex);
				}
				vertex = vertex.edges[k];
			}
			if (vertex.words > 0 && size > 0) {
				vertex.words--;
				size--;	
			}
		}

		private int findShortestUniqueString(TrieNode vertex, int[] word) {
			for (int length = 0; length < word.length; length++) {
				int k = word[length];
				if (vertex.edges[k].prefixes + vertex.edges[k].words == 1) {
					return length+1;
				} else {
					vertex = vertex.edges[k];
				}
			}
			return word.length;
		}
		
		/**
		 * Returns the number of words stored in the Trie.
		 */
		public int getSize() {
			return size;
		}
		
		private int countWords(TrieNode vertex, int[] word) {
			for (int length = 0; length < word.length; length++) {
				int k = word[length];
				if (vertex.edges[k] == null) {
					return 0;
				} else {
					vertex = vertex.edges[k];
				}
			}
			return vertex.words;
		}
		
		private int countPrefixes(TrieNode vertex, int[] prefix) {
			for (int length = 0; length < prefix.length; length++) {
				int k = prefix[length];
				if (vertex.edges[k] == null) {
					return 0;
				} else {
					vertex = vertex.edges[k];
				}
			}
			return vertex.prefixes;
		}
		
		public static int[] toTrieValue(char[] str) {
			int[] ret = new int[str.length];
			for (int i = 0; i < str.length; i++) {
				ret[i] = str[i] - 'a';
			}
			return ret;
		}
		
		public static int[] toTrieValue(String str) {
			int[] ret = new int[str.length()];
			char[] s = str.toCharArray();
			for (int i = 0; i < s.length; i++) {
				ret[i] = s[i] - 'a';
			}
			return ret;
		}
		
		public static int[] toTrieValue(long n) {
			int[] ret = new int[64];
			for (int i = ret.length-1; i >= 0; i--) {
				ret[i] = (n & 1) != 0 ? 1 : 0;
				n = n >> 1;
			}
			return ret;
		}
		
		public static int[] toTrieValue(int n) {
			int[] ret = new int[32];
			for (int i = ret.length-1; i >= 0; i--) {
				ret[i] = (n & 1) != 0 ? 1 : 0;
				n = n >> 1;
			}
			return ret;
		}
		
		public static long toLong(int[] n) {
			long ret = 0;
			for (int i = 0; i < n.length; i++) {
				ret |= n[i];
				if (i < n.length-1) {
					ret = ret << 1;
				}
			}
			return ret;
		}
		
		public static int toInt(int[] n) {
			int ret = 0;
			for (int i = 0; i < n.length; i++) {
				ret |= n[i];
				if (i < n.length-1) {
					ret = ret << 1;
				}
			}
			return ret;
		}
	}

	public static boolean traverse(Node n, String s, int i, int miss) {
		if (i == s.length()) {
			if (miss == 1 && n.terminal) {
				return true;
			} else {
				return false;
			}
		}
		if (miss > 1) {
			return false;
		}
		
		boolean hasChild = false;
		for (int j = 0; j < n.children.length; j++) {
			if (n.children[j] != null) {
				hasChild = true;
			}
		}
		if (!hasChild) {
			return false;
		}
		boolean res = false;
		for (int j = 0; j < n.children.length; j++) {
			int mymiss = miss;
			if (s.charAt(i) != j + 'a') {
				mymiss++;
			}
			if (n.children[j] != null) {
				res = traverse(n.children[j], s, i+1, mymiss);
			}
			if (res) {
				return true;
			}
		}
		return false;
	}
	
	public static void buildTree(Node n, String s, int i) {
		if (i == s.length()) {
			n.terminal = true;
			return;
		}
		Node next = null;
		if (n.children[s.charAt(i) - 'a'] == null) {
			next = new Node(s.charAt(i));
			n.children[s.charAt(i) - 'a'] = next;
		} else {
			next = n.children[s.charAt(i) - 'a'];
		}
		buildTree(next, s, i+1);
	}

	public static class Node {
		char type;
		Node[] children = new Node[3];
		boolean terminal;
		
		public Node(char type) {
			super();
			this.type = type;
		}
		public String toString() {
			return type + "";
		}
	}
	
	public static void hanSolo(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int x0 = scan.nextInt();
		int y0 = scan.nextInt();
		int[][] storm = new int[n][2];
		boolean[] odd = new boolean[n];
		for (int i = 0; i < n; i++) {
			storm[i][0] = scan.nextInt() - x0;
			storm[i][1] = scan.nextInt() - y0;
			if (!((storm[i][0] < 0 && storm[i][1] < 0) || (storm[i][0] >= 0 && storm[i][1] >= 0))) {
				odd[i] = true;
			}
			for (int j = 0; j < 2; j++) {
				storm[i][j] = Math.abs(storm[i][j]); 
			}
		}
		
		// norm
		boolean xline = false;
		boolean yline = false;
		for (int i = 0; i < storm.length; i++) {
			for (int j = 2; j <= Math.max(storm[i][0], storm[i][1]); j++) {
				while ((storm[i][0] % j == 0) && (storm[i][1] % j == 0)) {
					storm[i][0] /= j;
					storm[i][1] /= j;
				}
			}
			if (storm[i][0] == 0) {
				xline = true;
			} else if (storm[i][1] == 0) {
				yline = true;
			}
		}
		
		int uniq = 0;
		boolean[] ot = {true, false};
		for (int i = 0; i < 2; i++) {
			HashSet<Integer> set = new HashSet<>();
			for (int j = 0; j < storm.length; j++) {
				if (odd[j] == ot[i]) {
					if (storm[j][0] != 0 && storm[j][1] != 0) {
						set.add(storm[j][0] * 30000 + storm[j][1]);
					}
				}
			}
			
			uniq += set.size();
		}
		if (yline) {
			uniq++;
		}
		if (xline) {
			uniq++;
		}
		
		System.out.println(uniq);
	}	
	
	public static void chewbacca(InputStream in) {
		MyScanner scan = new MyScanner(in);
		char[] a = scan.next().toCharArray();
		for (int i = 0; i < a.length; i++) {
			if (a[i] >= '5') {
				if ((i == 0 && a[i] != '9') || i > 0) {
					a[i] = (char) ('9' - a[i] + '0');
				}
			}
		}
		System.out.println(new String(a));
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
