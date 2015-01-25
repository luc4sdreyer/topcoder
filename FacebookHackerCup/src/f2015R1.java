import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class f2015R1 {

	public static void main(String[] args) throws FileNotFoundException {
		//homework("./input/f2015R1/homework");
		autocomplete("./input/f2015R1/autocomplete");
	}
	
	public static void autocomplete(String filename) throws FileNotFoundException {
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");
			
			int n = scan.nextInt();
			String[] words = new String[n];
			for (int j = 0; j < words.length; j++) {
				words[j] = scan.next();
			}
			output.append(autocomplete(n, words));
			out.println(output.toString());

		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}
	
	public static long autocomplete(int n, String[] words) {
		Trie trie = new Trie(26);
		long sum = 0;
		for (int i = 0; i < words.length; i++) {
			int[] val = Trie.toTrieValue(words[i]);
			trie.addWord(val);
			long s = trie.findShortestUniqueString(val);
			sum += s;
		}
		return sum;
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


	public static class StackNode {
		private TrieNode vertex;
		private int[] word;
		private int length;
		public StackNode(TrieNode vertex, int[] word, int length) {
			super();
			this.vertex = vertex;
			this.word = word;
			this.length = length;
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
			addWord(root, word, 0);
		}
		
		public void removeWord(int[] word) {
			if (size > 0 && countWords(word) > 0) {
				removeWord(root, word, 0);
			}
		}
		
		public int countWords(int[] word) {
			return countWords(root, word, 0);
		}
		
		public int countPrefixes(int[] prefix) {
			return countPrefixes(root, prefix, 0);
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
		
		public int findShortestUniqueString(int[] word) {
			return findShortestUniqueString(root, word, 0);
		}

		private int findShortestUniqueString(TrieNode vertex, int[] word, int len) {
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
		
		private void addWord(TrieNode v, int[] w, int l) {
			Stack<StackNode> s = new Stack<>();
			StackNode top = new StackNode(v, w, l);
			s.add(top);
			while (!s.isEmpty()) {
				top = s.pop();
				if (top.length == top.word.length) {
					top.vertex.words++;
					size++;
					break;
				} else {
					top.vertex.prefixes++;
					int k = top.word[top.length];
					if (top.vertex.edges[k] == null) {
						top.vertex.edges[k] = new TrieNode(alphabetSize, top.vertex);
					}
					s.add(new StackNode(top.vertex.edges[k], top.word, top.length + 1));
				}
			}
		}

		private void removeWord(TrieNode vertex, int[] word, int length) {
			if (length == word.length) {
				if (vertex.words > 0 && size > 0) {
					vertex.words--;
					size--;	
				}
			} else {
				if (vertex.prefixes > 0 && size > 0) {
					vertex.prefixes--;
				}
				int k = word[length];
				if (vertex.edges[k] == null) {
					vertex.edges[k] = new TrieNode(alphabetSize, vertex);
				}
				removeWord(vertex.edges[k], word, length + 1);
			}
		}
		
		/**
		 * Returns the number of words stored in the Trie.
		 */
		public int getSize() {
			return size;
		}
		
		private int countWords(TrieNode vertex, int[] word, int length) {
			if (length == word.length) {
				return vertex.words;
			} else {
				int k = word[length];
				if (vertex.edges[k] == null) {
					return 0;
				} else {
					return countWords(vertex.edges[k], word, length + 1);
				}
			}
		}
		
		private int countPrefixes(TrieNode vertex, int[] prefix, int length) {
			if (length == prefix.length) {
				return vertex.prefixes;
			} else {
				int k = prefix[length];
				if (vertex.edges[k] == null) {
					return 0;
				} else {
					return countPrefixes(vertex.edges[k], prefix, length + 1);
				}
			}
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

	public static void homework(String filename) throws FileNotFoundException {
		LinkedHashSet<Integer> primes = getPrimes(10000000);

		int[][] primacity = new int[9][10000001];
		for (int i = 2; i <= 10000000; i++) {
			int pr = 0;
			if (primes.contains(i)) {
				pr++;
			} else {
				for (int j = 2; j*j <= i; j++) {
					if (i % j == 0) {
						int a = i / j;
						if (primes.contains(a)) {
							pr++;
						}
						if (j != a) {
							a = j;
							if (primes.contains(a)) {
								pr++;
							}
						}
					}
				}
			}
			primacity[pr][i] = 1;
		}
		
		MyScanner scan = new MyScanner(new FileInputStream(filename+".txt"));
		StringBuilder output = null;
		String outputfile = filename + ".out";
		PrintStream out = new PrintStream(new FileOutputStream(outputfile));
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			output = new StringBuilder();
			output.append("Case #" + (i+1) + ": ");
			
			int a = scan.nextInt();
			int b = scan.nextInt();
			int k = scan.nextInt();
			
			if (k > 8) {
				output.append(0);
			} else {
				int sum = 0;
				for (int j = a; j <= b; j++) {
					sum += primacity[k][j];
				}
				output.append(sum);
			}
			out.println(output.toString());

		}
		scan.close();
		out.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}


	public static LinkedHashSet<Integer> getPrimes(long limit) {
		LinkedHashSet<Integer> primes = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> notPrimes = new LinkedHashSet<Integer>();
		boolean prime = true;
		for (int i = 2; i <= limit; i++) {
			prime = true;
			if (notPrimes.contains(i)) {
				prime = false;
			}
			if (prime) {
				primes.add(i);
				for (int j = 2; j*i <= limit; j++) {
					notPrimes.add(j*i);
				}
			}
		}
		return primes;
	}

	public static int swap(char[] a_, int i, int j) {
		char[] a = a_.clone();
		char t = a[i];
		a[i] = a[j];
		a[j] = t;
		if (a[0] == '0') {
			return Integer.parseInt(new String(a_));
		}
		return Integer.parseInt(new String(a));
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
