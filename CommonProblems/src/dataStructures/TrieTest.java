package dataStructures;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import org.junit.Test;

public class TrieTest {

/**
 * Tries are a secret weapon against XOR query problems: 
 * https://threads-iiith.quora.com/Tutorial-on-Trie-and-example-problems
 * 
 * They act like a frequency map, so inserting won't replace anything, only increase the count!
 */
	
	public static class TrieNode {
		private int words;
		private int prefixes;
		private TrieNode[] edges;
		private int depth;
		@SuppressWarnings("unused")
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


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************************************************
	 * TESTS
	 ***************************************************************************************************/
	
	@Test
	public void testStandardOps() {
		int numTests = 100;
		int maxLength = 5;
		int maxWords = 300;
		Random rand = new Random(0);
		for (int i = 0; i < numTests; i++) {
			int numWords = rand.nextInt(maxWords);
			ArrayList<String> lexicon = new ArrayList<>();
			for (int j = 0; j < numWords; j++) {
				int length = rand.nextInt(maxLength);
				char[] str = new char[length];
				for (int k = 0; k < length; k++) {
					str[k] = (char) (rand.nextInt(26) + 'a');
				}
				lexicon.add(new String(str));
			}
			HashMap<String, Integer> map = new HashMap<>();
			Trie trie = new Trie(26);
			
			for (int j = 0; j < numTests; j++) {
				int type = rand.nextInt(3);
				String word = lexicon.get(rand.nextInt(lexicon.size()));
				if (type < 2) {
					
					// add
					trie.addWord(Trie.toTrieValue(word));
					map.put(word, map.containsKey(word) ? map.get(word) + 1 : 1);
					
				} else if (type < 3) {
					
					//remove
					trie.removeWord(Trie.toTrieValue(word));
					if (map.containsKey(word)) {
						int num = map.get(word);
						if (num > 0) {
							map.put(word, num-1);
						}
					}
				}
				
				for (int k = 0; k < lexicon.size(); k++) {
					String w = lexicon.get(k);
					// contains word
					int expected = map.containsKey(w) ? map.get(w) : 0;
					int actual = trie.countWords(Trie.toTrieValue(w));

					if (actual != expected) {
						System.nanoTime();
						trie.countPrefixes(Trie.toTrieValue(w));
					}
					assertEquals(expected, actual);
					
					for (int m = 0; m < 3 && m < w.length(); m++) {
						String sub = w.substring(0, m);
						// contains prefix
						expected = 0;
						for (String key : map.keySet()) {
							if (key.startsWith(sub) && sub.length() < key.length()) {
								expected += map.get(key);
							}
						}
						actual = trie.countPrefixes(Trie.toTrieValue(sub));
						if (actual != expected) {
							System.nanoTime();
							trie.countPrefixes(Trie.toTrieValue(sub));
						}
						assertEquals(expected, actual);
					}
				}
			}
		}
	}

	@Test
	public void testFind() {
		int numTests = 100;
		int maxNumbers = 30000;
		int maxSize = 1000000;
		Random rand = new Random(0);
		for (int i = 0; i < numTests; i++) {
			long[] numbers = new long[rand.nextInt(maxNumbers) + 1];
			Trie trie = new Trie(2);
			for (int j = 0; j < numbers.length; j++) {
				numbers[j] = rand.nextInt(maxSize);
				trie.addWord(Trie.toTrieValue(numbers[j]));
			}
			for (int j = 0; j < numTests; j++) {
				long expected = 0;
				long target = rand.nextInt(maxSize);
				for (int k = 0; k < numbers.length; k++) {
					expected = Math.max(target ^ numbers[k], expected);
				}
				
				long actual = Trie.toLong(trie.find(Trie.toTrieValue(~target))) ^ target;
				
				if (actual != expected) {
					System.nanoTime();
					actual = Trie.toLong(trie.find(Trie.toTrieValue(1L ^ target)));
				}
			}
		}
	}
}
