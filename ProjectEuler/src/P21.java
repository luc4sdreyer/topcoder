import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class P21 {

	public static void main(String[] args) {
		amicableNumbers(System.in);
	}
	
	public static void amicableNumbers(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		getAllAmicableNumbers(100001);
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			sb.append(amicableNumbers(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}
	
	public static int[] amicable;
	public static void getAllAmicableNumbers(int n) {
		amicable = new int[n];
		HashMap<Integer, Integer> d = new HashMap<Integer, Integer>();
		for (int i = 1; i <= n; i++) {
			int sum = 0;
			for (int j = 1; j*j <= i; j++) {
				if (i % j == 0) {
					int q = i/j;
					if (q != i) {
						sum += q;
					}
					if (q != j) {
						sum += j;
					}
				}
			}
			d.put(i, sum);
		}
		
		boolean[] am = new boolean[n];
		for (int key: d.keySet()) {
			int v = d.get(key);
			if (d.containsKey(v) && d.get(v) == key) {
				if (key < am.length && v < am.length && key != v) {
					am[key] = true;
					am[v] = true;
				}
			}
		}
		
		for (int i = 1; i < n; i++) {
			amicable[i] = amicable[i-1];
			if (am[i]) {
				amicable[i] += i;
			}
		}
	}
	
	public static String amicableNumbers(int n) {
		
		return amicable[n] + "";
	}

}
