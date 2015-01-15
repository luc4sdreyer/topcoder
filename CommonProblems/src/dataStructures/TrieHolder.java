package dataStructures;

/**
 * Tries are a secret weapon against XOR query problems: 
 * https://threads-iiith.quora.com/Tutorial-on-Trie-and-example-problems
 */

public class TrieHolder {	
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
		 * Typically used to find the maximum XOR of two elements in an array in O(nlog(n))
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

		private void addWord(TrieNode vertex, int[] word, int length) {
			if (length == word.length) {
				vertex.words++;
				size++;
			} else {
				vertex.prefixes++;
				int k = word[length];
				if (vertex.edges[k] == null) {
					vertex.edges[k] = new TrieNode(alphabetSize, vertex);
				}
				addWord(vertex.edges[k], word, length + 1);
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
	}
}
