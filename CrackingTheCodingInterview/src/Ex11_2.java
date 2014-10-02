import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class Ex11_2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] s = {"shit", "ram", "one", "two", "mar", "wot", "neo", "this"};
		anagramSort2(s);
		System.out.println(Arrays.toString(s));
	}
	
	public static void anagramSort(String[] array) {
		String[] temp = new String[array.length];
		boolean[] matched = new boolean[array.length];
		HashMap<Integer, ArrayList<Integer>> buckets = new HashMap<Integer, ArrayList<Integer>>();
		int[][] charMaps = new int[array.length][26];
		for (int i = 0; i < array.length; i++) {
			for (int k = 0; k < array[i].length(); k++) {
				charMaps[i][array[i].charAt(k) - 'a']++;
			}
		}

		for (int i = 0; i < array.length; i++) {
			if (!matched[i]) {
				ArrayList<Integer> b = new ArrayList<Integer>();
				b.add(i);
				buckets.put(i, b);
				for (int j = i+1; j < array.length; j++) {
					if (!matched[j]) {
						boolean equal = true;
						for (int k = 0; k < 26; k++) {
							if (charMaps[i][k] != charMaps[j][k]) {
								equal = false;
								break;
							}
						}

						if (equal) {
							matched[i] = true;
							matched[j] = true;
							buckets.get(i).add(j);
						}
					}
				}
			}
		}

		int idx = 0;
		for (int key : buckets.keySet()) {
			ArrayList<Integer> b = buckets.get(key);
			for (int i = 0; i < b.size(); i++) {
				temp[idx++] = array[b.get(i)];
			}
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = temp[i];
		}
	}
	
	public static void anagramSort2(String[] array) {
		Arrays.sort(array, new AnagramComparator());
	}
	
	public static class AnagramComparator implements Comparator<String> {
		public int compare(String a, String b) {
			char[] aChars = a.toCharArray();
			char[] bChars = b.toCharArray();
			Arrays.sort(aChars);
			Arrays.sort(bChars);
			return (new String(aChars)).compareTo(new String(bChars));
		}
	}

}
