import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P14 {

	public static void main(String[] args) {		
		longestCollatzSequence(System.in);
	}

	public static void longestCollatzSequence(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		//getAllLongestCollatzSequence(50000);
		getAllLongestCollatzSequence2(5000000);
		
		int t = scan.nextInt();
		int[] a = new int[t];
		for (int i = 0; i < t; i++) {
			a[i] = scan.nextInt();
		}
		scan.close();
		sb.append(longestCollatzSequence(a));
		sb.append("\n");
		System.out.println(sb.toString());
	}

	public static String longestCollatzSequence(int n) {
		return cache.get(n)+"";
	}
	
	public static HashMap<Integer, Integer> cache = new HashMap<>();

	public static String longestCollatzSequence(int[] a) {
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			max = Math.max(max, a[i]);
		}
		int[] best = new int[max];
		for (int i = 0; i < a.length; i++) {
			ArrayList<Integer> seq = new ArrayList<>();
			seq.add(a[i]);
			int len = 1;
			while (seq.get(seq.size()-1) != 1) {
				int current = seq.get(seq.size()-1);
				if (cache.containsKey(current)) {
					len = cache.get(current);
					seq.remove(seq.size()-1);
					break;
				}
				if (current % 2 == 0) {
					seq.add(current/2);
				} else {
					seq.add(3*current + 1);
				}
			}
			for (int j = 0; j < seq.size(); j++) {
				if (seq.get(seq.size()-1 - j) != 1 && cache.containsKey(seq.get(seq.size()-1 - j))) {
					System.out.print("");
				}
				cache.put(seq.get(seq.size()-1 - j), len);
				len++;
			}
		}
		return null;
		
	}
	
	public static void getAllLongestCollatzSequence2(int n) {
		HashMap<Integer, ArrayList<Integer>> lengths = new HashMap<>();
		for (int i = 1; i <= n; i++) {
			long a = i;
			int len = 1;
			while (a != 1) {
				if (a % 2 == 0) {
					a = a/2;
				} else {
					a = 3*a + 1;
				}
				len++;
			}
			if (!lengths.containsKey(len)) {
				lengths.put(len, new ArrayList<Integer>());
			}
			ArrayList<Integer> le = lengths.get(len);
			le.add(i);
			lengths.put(len, le);
		}
	}
	
	public static void getAllLongestCollatzSequence(int n) {
		for (int i = 1; i <= n; i++) {
			ArrayList<Integer> seq = new ArrayList<>();
			seq.add(i);
			int len = 1;
			while (seq.get(seq.size()-1) != 1) {
				int current = seq.get(seq.size()-1);
				if (cache.containsKey(current)) {
					len = cache.get(current);
					seq.remove(seq.size()-1);
					break;
				}
				if (current % 2 == 0) {
					seq.add(current/2);
				} else {
					seq.add(3*current + 1);
				}
			}
			for (int j = 0; j < seq.size(); j++) {
				if (seq.get(seq.size()-1 - j) != 1 && cache.containsKey(seq.get(seq.size()-1 - j))) {
					System.out.print("");
				}
				cache.put(seq.get(seq.size()-1 - j), len);
				len++;
			}
		}
	}

}
