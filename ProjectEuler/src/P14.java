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
	
	public static HashMap<Long, Integer> cache2 = new HashMap<>();
	public static int[] longest;

	public static void longestCollatzSequence(InputStream in) {
		Scanner scan = new Scanner(in);
		//getAllLongestCollatzSequence3(5000000);
		int[] range = { 1, 2, 3, 6, 7, 9, 18, 19, 25, 27, 54, 55, 73, 97, 129, 171, 231, 235, 313, 327, 649, 654, 655, 667, 703, 871, 1161, 2223, 2322, 2323, 2463, 2919, 3711, 6171, 10971, 13255, 17647, 17673, 23529, 26623, 34239, 35497, 35655, 52527, 77031, 106239, 142587, 156159, 216367, 230631, 410011, 511935, 626331, 837799, 1117065, 1126015, 1501353, 1564063, 1723519, 2298025, 3064033, 3542887, 3732423, };
		
		int t = scan.nextInt();
		int[] a = new int[t];
		for (int i = 0; i < t; i++) {
			a[i] = scan.nextInt();
			int max = range[0];
			for (int j = 1; j < range.length; j++) {
				if (range[j] > a[i]) {
					break;
				}
				max = range[j];
			}
			System.out.println(max);
		}
		scan.close();
	}
	
	public static void getAllLongestCollatzSequence3(int n) {
		cache2.put(1L, 1);
		longest = new int[n+1];
		HashSet<Integer> set = new HashSet<>();
		
		for (long i = 1; i <= n; i++) {
			int len = 1;
			long a = i;
			while (a != 1) {
				if (cache2.containsKey(a)) {
					len += cache2.get(a)-1;
					break;
				}
				if (a % 2L == 0) {
					a = a/2L;
				} else {
					a = 3L*a + 1L;
				}
				len++;
			}
			
			a = i;
			while (a != 1) {
				if (cache2.containsKey(a)) {
					break;
				}
				cache2.put(a, len);
				if (a % 2L == 0) {
					a = a/2L;
				} else {
					a = 3L*a + 1L;
				}
				len--;
			}
		}
		
		int max = 0;
		int maxIdx = 0;
		String maxi = "{ ";
		for (long i = 1; i <= n; i++) {
			if (max <= cache2.get(i)) {
				max = cache2.get(i);
				maxIdx = (int) i;
				maxi += maxIdx + ", ";
			}
			longest[(int) i] = maxIdx;
			set.add(maxIdx);
		}
		maxi += "}";
		
		System.out.println("size: " + set.size());
		System.out.println(maxi);
	}
}
