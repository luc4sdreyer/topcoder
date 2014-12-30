import java.io.InputStream;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		//rearrangement(System.in);
		//tripplePalindrome(System.in);
		//lowestCombination(System.in);
		binaryCheck(System.in);
		//testRearrangement();
	}
	
	public static void binaryCheck(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int k = scan.nextInt();
		int x = scan.nextInt();
		int[] a = new int[m];
		for (int i = 0; i < m; i++) {
			a[i] = scan.nextInt();
		}
		scan.close();
		System.out.println(binaryCheck(n, m, k, x, a));
	}
	
	public static String binaryCheck(int n, int m, int k, int x, int[] a) {
		int num = 0;
		int max = x;
		for (int i = 0; i < a.length; i++) {
			max = Math.max(max, a[i]);
		}
		int exp = (int) Math.ceil(Math.log10(max) / Math.log(2));
		//System.out.println(exp);
		for (int i = 0; i < a.length; i++) {
			int ways = 0;
			for (int j = 0; j < exp; j++) {
				if (getBit(a[i], j) != getBit(x, j)) {
					ways++;
					if (ways > k) {
						break;
					}
				}
			}
			if (ways <= k) {
				num++;
			}
		}
		return num +"";
	}
	
	public static boolean getBit(int n, int i) {
		return (((1 << i) & n) != 0) ? true : false;
	}

	public static void lowestCombination(InputStream in) {
		Scanner scan = new Scanner(in);
		scan.nextInt();
		String s = scan.next();
		scan.close();
		System.out.println(lowestCombination(s));
	}

	public static String lowestCombination(String s) {
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

	public static void tripplePalindrome(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		int n = scan.nextInt();
		scan.close();
		sb.append(tripplePalindrome(n));
		System.out.println(sb.toString());
	}

	public static String tripplePalindrome(int n) {
		int num = 0;
		String out = "";
		while (num < 20) {
			n++;
			if (isTriple(n)) {
				out += n + "\n";
				num++;
			}
		}
		return out;
	}

	public static boolean isTriple(int n) {
		int num = 0;
		for (int base = 2; base <= 20; base++) {
			char[] m = Integer.toString(n, base).toCharArray();
			boolean pal = true;
			for (int i = 0; i < m.length/2; i++) {
				if (m[i] != m[m.length-1 - i]) {
					pal = false;
					break;
				}
			}
			if (pal) {
				num++;
			}
			if (num >= 3) {
				return true;
			}
		}
		return false;
	}

	public static void testRearrangement() {
		Random rand = new Random(0);
		for (int i = 0; i < 1000; i++) {
			int n = 1 << rand.nextInt(16);
			int[] a = new int[n];
			for (int j = 0; j < n; j++) {
				a[j] = rand.nextInt(20)+1;
			}
			rearrangement(n, a);
		}
	}

	public static void rearrangement(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		int n = scan.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = scan.nextInt();
		}
		scan.close();
		sb.append(rearrangement(n, a));
		System.out.println(sb.toString());
	}

	public static int rearrangement(int n, int[] a) {
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		int max = 1;
		int bpb = sum/n;
		for (int i = 0; i < n; i++) {
			bpb = sum/(n-i);
			max = (max * bpb) % 100007;
			sum -= bpb;
		}
		return max;
	}

}
