import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class P22 {

	public static void main(String[] args) {
		namesScores(System.in);
	}
	
	public static void namesScores(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		int t = scan.nextInt();
		String[] names = new String[t];
		for (int i = 0; i < t; i++) {
			names[i] = scan.next();
		}

		int q = scan.nextInt();
		String[] queries = new String[q];
		for (int i = 0; i < q; i++) {
			queries[i] = scan.next();
		}

		sb.append(namesScores(names, queries));
		sb.append("\n");
		
		scan.close();
		System.out.println(sb.toString());
	}
	
	public static String namesScores(String[] names, String[] queries) {
		int[] scores = new int[names.length];
		Arrays.sort(names);
		for (int i = 0; i < scores.length; i++) {
			int s = 0;
			for (int j = 0; j < names[i].length(); j++) {
				s += names[i].charAt(j) - 'A' + 1;
			}
			scores[i] = (i + 1) * s;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < queries.length; i++) {
			for (int j = 0; j < names.length; j++) {
				if (names[j].equals(queries[i])) {
					sb.append(scores[j]);
					sb.append("\n");
				}
			}
		}
		return sb.toString();
	}

}
