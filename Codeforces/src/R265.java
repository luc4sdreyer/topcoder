import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class R265 {
	public static final long mod = 1000000007;
	public static void main(String[] args) {
		System.out.println(noToPalindromes(System.in));
		//System.out.println(substitutesInNumber(System.in));		
	}

	public static String substitutesInNumber(InputStream in) {
		Scanner input = new Scanner(in);
		String s = input.next();
		int n = input.nextInt();
		String[][] queries = new String[n][2];
		for (int i = 0; i < n; i++) {
			String str = input.next();
			queries[i] = new String[]{str.substring(0, str.indexOf("->")), str.substring(str.indexOf("->")+2)};
		}
		input.close();
		return substitutesInNumber(s, n, queries) + "";
	}


	public static String substitutesInNumber(String s, int n, String[][] queries) {
		ArrayList<ArrayList<Byte>> result = new ArrayList<>();
		for (int i = 0; i < 10	; i++) {
			ArrayList<Byte> a = new ArrayList<Byte>();
			a.add((byte)(i + '0'));
			result.add(a);
		}
		//int[] time = new int[10];
		for (int i = 0; i < queries.length; i++) {
			for (int j = 0; j < result.size(); j++) {
				byte[] newQ = new byte[queries[i][1].length()];
				char[] c = queries[i][1].toCharArray();
				for (int k = 0; k < newQ.length; k++) {
					newQ[k] = (byte) c[k];
				}
				ArrayList<Byte> dest = result.get(j);
				byte replace = (byte) queries[i][0].charAt(0);
				for (int k = 0; k < dest.size(); k++) {
					if (dest.get(k) == replace) {
						dest.remove(k);
						for (int m = 0; m < newQ.length; m++) {
							dest.add(k, newQ[m]);
							if (m != 0) {
								k++;
							}
						}
					}
				}
			}
		}
		char[] str = s.toCharArray();
		StringBuilder sb = new StringBuilder();
		//String[] newRes = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < result.get(str[i]-'0').size(); j++) {
				sb.append((char)(byte)result.get(str[i]-'0').get(j));
			}
			//s = s.replace((char)(i + '0') + "", result[i]);
		}
		s = sb.toString();
		if (s.isEmpty()) {
			return "0";
		}
		BigInteger b = new BigInteger(s);
		return b.mod(BigInteger.valueOf(mod)).toString();
	}
	public static String substitutesInNumber2(String s, int n, String[][] queries) {
		String[] result = new String[10];
		for (int i = 0; i < result.length; i++) {
			result[i] = (char)(i + '0') + "";
		}
		//int[] time = new int[10];
		for (int i = 0; i < queries.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[j] = result[j].replace(queries[i][0], queries[i][1]);				
			}
		}
		char[] str = s.toCharArray();
		StringBuilder sb = new StringBuilder();
		//String[] newRes = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			sb.append(result[str[i]-'0']);
			//s = s.replace((char)(i + '0') + "", result[i]);
		}
		s = sb.toString();
		if (s.isEmpty()) {
			return "0";
		}
		BigInteger b = new BigInteger(s);
		return b.mod(BigInteger.valueOf(mod)).toString();
	}

	public static String noToPalindromes(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int p = input.nextInt();
		String s = input.next();
		input.close();
		return noToPalindromes(n, p, s.toCharArray()) + "";
	}

	private static String noToPalindromes(int n, int p, char[] s) {
		while (next_number(s, p + 'a', 'a')) {
			if (!isPal(s)) {
				return new String(s);
			}
		}
		return "NO";
	}
	
	public static boolean isPal(char[] s) {
		for (int i = 1; i < s.length; i++) {
			if (s[i] == s[i-1]) {
				return true;
			}
		}
		for (int i = 2; i < s.length; i++) {
			if (s[i] == s[i-2]) {
				return true;
			}
		}
		return false;
	}

	public static boolean next_number(char list[], int base, int offset) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = (char) offset;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}
}
