import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class P4 {

	public static void main(String[] args) {
		largestPalindromeProduct(System.in);
	}

	public static void largestPalindromeProduct(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		ArrayList<Long> list = largestPalindromeProduct();
		Collections.sort(list);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			long best = 0;
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) < n) {
					best = list.get(j);
				} else {
					break;
				}				
			}
			sb.append(best);
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static ArrayList<Long> largestPalindromeProduct() {
		ArrayList<Long> list = new ArrayList<>();
		for (int i = 100; i < 1000; i++) {
			for (int j = 100; j < 1000; j++) {
				long a = i*j;
				if (a >= 1000000) {
					break;
				}
				char[] s = Long.toString(a).toCharArray();
				boolean pal = true;
				for (int l = 0; l < s.length/2; l++) {
					if (s[l] != s[s.length-1 - l]) {
						pal = false;
						break;
					}
				}
				if (pal) {
					//System.out.println(i + "\t" + j + "\t" + a);
					list.add(a);
				}
			}
		}
		return list;
	}

}
