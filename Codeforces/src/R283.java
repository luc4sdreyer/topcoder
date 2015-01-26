import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.xml.bind.ValidationEvent;


public class R283 {
	public static final long mod = 1000000007;
	public static void main(String[] args) {
		//System.out.println(minimumDifficulty(System.in));
		//System.out.println(secretCombination(System.in));
		System.out.println(removingColumns(System.in));
		//test();
	}

	private static void test() {
		Random rand = new Random(0);
		int size = 6;
		for (int i = 0; i < 1000; i++) {
			String[] s = new String[size];
			for (int j = 0; j < s.length; j++) {
				s[j] = "";
				for (int k = 0; k < s.length; k++) {
					s[j] += (char)(rand.nextInt(10) + 'a');
				}
			}
			int res1 = removingColumns(size, size, s);
			int res2 = removingColumnsSlow(size, size, s);
			if (res1 != res2) {
				System.out.println();
				removingColumns(size, size, s);
			}
		}
	}

	public static String removingColumns(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		String[] rows = new String[n];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = scan.next();
		}
		scan.close();
		return removingColumns(n, m, rows) + "";
		//return removingColumnsSlow(n, m, rows) + "";
	}

	public static int removingColumnsSlow(int n2, int m, String[] rows) {
		ArrayList<char[]> columns = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			columns.add(new char[n2]);
		}
		
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length(); j++) {
				columns.get(j)[i] = rows[i].charAt(j);
			}
		}
		
		int max = 0;
		int N = 1 << columns.size();
		for (int n = 0; n < N; n++) {
			ArrayList<char[]> newList = new ArrayList<>();
			for (int i = 0; i < columns.size(); i++) {
				if (((1 << i) & n) != 0) {
					newList.add(columns.get(i));
				}
			}			

			String[] newStr = new String[n2];
			for (int j = 0; j < n2; j++) {
				StringBuilder sb = new StringBuilder();
				for (int k = 0;  k < newList.size(); k++) {			
					sb.append(newList.get(k)[j]);					
				}
				newStr[j] = sb.toString();
			}
			boolean invalid = false;
			for (int j = 1; j < newStr.length; j++) {
				if (newStr[j].compareTo(newStr[j-1]) < 0) {
					invalid = true;
					break;
				}
			}
			if (!invalid) {
				max = Math.max(max, newList.size());
			}
		}
		
		return columns.size() - max;
	}
	public static int removingColumns(int n, int m, String[] rows) {
		ArrayList<char[]> columns = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			columns.add(new char[n]);
		}
		
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows[i].length(); j++) {
				columns.get(j)[i] = rows[i].charAt(j);
			}
		}

		boolean modi = true;
		int rem = 0;
		while (modi) {
			int i = 0;
			modi = false;
			for (int j = 1; j < n; j++) {
				if (i < columns.size()) {
					if (columns.get(i)[j] < columns.get(i)[j-1]) {
						columns.remove(i);
						modi = true;
						rem++;
						break;
					}
				}
			}
			if (modi == true) {
				continue;
			}
			for (int j = 1; j < n; j++) {
				if (i < columns.size()) {
					if (columns.get(i)[j] < columns.get(i)[j-1]) {
						columns.remove(i);
						modi = true;
						rem++;
						break;
					} else if (columns.get(i)[j] == columns.get(i)[j-1]) {
						while (i < columns.size() && columns.get(i)[j] == columns.get(i)[j-1]) {
							i++;
						}
						if (i < columns.size() && columns.get(i)[j] < columns.get(i)[j-1]) {
							columns.remove(i);
							modi = true;
							rem++;
							break;
						}
						i = 0;
					}
				}
			}
			
			String[] newStr = new String[n];
			for (int j = 0; j < n; j++) {
				StringBuilder sb = new StringBuilder();
				for (int k = 0;  k < columns.size(); k++) {			
					sb.append(columns.get(k)[j]);					
				}
				newStr[j] = sb.toString();
			}
			for (int j = 1; j < newStr.length; j++) {
				if (newStr[j].compareTo(newStr[j-1]) < 0) {
					modi = true;
					break;
				}
			}
		}
//		for (int j = 0; j < n; j++) {
//			for (int i = 0; i < columns.size(); i++) {			
//				System.out.print(columns.get(i)[j]);
//			}
//			System.out.println();
//		}
		return rem;
	}

	public static String secretCombination(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		String s = scan.next();
		scan.close();
		return secretCombination(s) + "";
	}

	private static String secretCombination(String s) {
		BigInteger min = null;
		String best = "";
		char[] c = s.toCharArray();
		for (int inc = 0; inc < 10; inc++) {
			for (int rot = 0; rot < s.length(); rot++) {
				char[] n = new char[c.length];
				for (int i = 0; i < n.length; i++) {
					n[i] = (char) ((c[(i + rot)%c.length] + inc));
					if (n[i] >= '9' + 1) {
						n[i] -= 10;  
					}
				}
				String str = new String(n);
				BigInteger val = new BigInteger(str);
				if (min == null) {
					min = val;
					best = str;
				} else {
					if (val.compareTo(min) < 0) {
						min = val;
						best = str;
					}
				}
			}
		}
		return best;
	}
	
	public static String minimumDifficulty(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = scan.nextInt();
		}
		scan.close();
		return minimumDifficulty(a) + "";
	}


	private static int minimumDifficulty(int[] a) {
		int min = Integer.MAX_VALUE;
		for (int i = 1; i < a.length-1; i++) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int j = 0; j < a.length; j++) {
				if (j != i) {
					list.add(a[j]);
				}
			}
			int dist = 0;
			for (int j = 1; j < list.size(); j++) {
				dist = Math.max(dist, list.get(j) - list.get(j-1));						
			}
			min = Math.min(min, dist);
		}
		return min;
	}
}
