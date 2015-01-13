package dataStructures;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import dataStructures.TrieHolder.Trie;

public class TrieHolderTest {

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
				
				long actual = Trie.toLong(trie.find(Trie.toTrieValue(1L ^ target))) ^ target;
				
				if (actual != expected) {
					System.nanoTime();
					actual = Trie.toLong(trie.find(Trie.toTrieValue(1L ^ target)));
				}
			}
		}
	}

}
