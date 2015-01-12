package LTIME19;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;
 
public class Main {
	public static void main(String[] args) {
		//System.out.println(deliveryMan(System.in));
		System.out.println(binomialCoefficent(System.in));
		//testBinom2();
	}

	public static void testBinom2() {
		for (int i = 1; i < 66; i++) {
			long[] binom = binom((int) i);
			for (int j = 0; j < binom.length; j++) {
				binom[j] = binom[j] % 3;				
			}
			long sum = 0;
			for (int j = 0; j < binom.length; j++) {
				sum += binom[j];
			}
			System.out.println(i + "\t" + sum + "\t" + Arrays.toString(binom));
		}		
	}
	
	private static long fCacheSize = 1000000; // Used to speed up calls, but answers will be correct without it.
	private static HashMap<Integer,BigInteger> fCache = new HashMap<Integer,BigInteger>();	
	private static BigInteger factorial(int n)
    {
        BigInteger ret;
        
        if (n < 0) return BigInteger.ZERO;
        if (n == 0) return BigInteger.ONE;
        
        if (null != (ret = fCache.get(n))) return ret;
        else ret = BigInteger.ONE;
        for (int i = n; i >= 1; i--) {
        	if (fCache.containsKey(n)) return ret.multiply(fCache.get(n));
        	ret = ret.multiply(BigInteger.valueOf(i));
        }
        
        if (fCache.size() < fCacheSize) {
        	fCache.put(n, ret);
        }
        return ret;
    }

	public static void testBinom() {
		for (int i = 1; i < 100; i++) {
			System.out.println(i + "\t" + factorial(i).mod(BigInteger.valueOf(3)).longValue());
		}		
	}

	public static String binomialCoefficent(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
 
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			sb.append(binomialCoefficent(n)+"\n");
		}		
		scan.close();
		return sb.toString();
	}
	
	public static String binomialCoefficent(long n) {
		long[] binom = binom((int) n);
		long sum = 0;
		for (int i = 0; i < binom.length; i++) {
			sum += binom[i] % 3;
		}
		return sum + "";
	}	

	public static long[] binom(int n) {
		//Arrays.sort(values);
		long[] values = new long[n];
		for (int i = 0; i < n; i++) {
			values[i] = 1;
		}
		long max = 0;
		for (int i = 0; i < values.length; i++) {
			max += values[i];
		}
		long[] ways = new long[(int) (max+1)];
		ways[0] = 1;
		for (int i = 0; i < values.length; i++) {
			for (long sum = max; sum >= values[i]; sum--) {
				ways[(int) sum] += ways[(int) (sum - values[i])];
			}
		}
		return ways;
	}

	public static String deliveryMan(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
 
		int n = scan.nextInt();
		int x = scan.nextInt();
		int y = scan.nextInt();
		int[][] a = new int[n][3];
		for (int j = 0; j < n; j++) {
			a[j][0] = scan.nextInt();
		}
		for (int j = 0; j < n; j++) {
			a[j][1] = scan.nextInt();
		}
		for (int j = 0; j < n; j++) {
			a[j][2] = Math.abs(a[j][0] - a[j][1]);
		}
		sb.append(deliveryMan(n, x, y, a)+"\n");
		scan.close();
		return sb.toString();
	}
	
	public static String deliveryMan(int n, int x, int y, int[][] a) {
		Arrays.sort(a, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[2], o2[2]);
			}			
		});
		
		long max = 0;
		int t = a.length-1;
		while (x > 0 && y > 0 && t >= 0) {
			if (a[t][0] >= a[t][1]) {
				max += a[t][0];
				x--;
			} else {
				max += a[t][1];
				y--;
			}
			t--;
		}
		while (t >= 0) {
			if (x > 0) {
				max += a[t][0];
			} else {
				max += a[t][1];
			}
			t--;
		}
		
		return max + "";
	}
} 