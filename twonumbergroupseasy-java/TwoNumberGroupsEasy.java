import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class TwoNumberGroupsEasy {
	public int solve(int[] A, int[] numA, int[] B, int[] numB) {
		ArrayList<long[]> dist = new ArrayList<>();
		int len1 = A.length;
		int len2 = B.length;
		boolean flag;
		int len = 0;
		for (int a = 0; a < len1; a++) {
			for (int b = 0; b < len2; b++) {
				long diff = Math.abs(A[a] - B[b]);
				flag = false;
				for (int i = 0; i < len; i++) {
					if (dist.get(i)[1] == diff) {
						dist.get(i)[0] += numA[a]+numB[b];
						flag = true;
						break;
					}
				}
				if (!flag) {
					dist.add(new long[]{numA[a]+numB[b], diff});
					len++;
				}
			}
			
		}
		Collections.sort(dist, new Comparator<long[]>() {

			@Override
			public int compare(long[] o1, long[] o2) {
				if (Long.compare(o1[0], o2[0]) == 0) {
					return Long.compare(o1[1], o2[1]);
				}
				return Long.compare(o1[0], o2[0]);
			}
			
		});
		return (int) dist.get(0)[0];
	}
	public int solve2(int[] A, int[] numA, int[] B, int[] numB) {
		int min = Integer.MAX_VALUE;
		LinkedHashSet<Integer> pr = getPrimes(31623);
		for (int p: pr) {
			min = Math.min(min, diff(p, A, numA, B, numB));
			//System.out.println(i + "\t" +diff(i, A, numA, B, numB));
		}		
		return min;
	}
	
	public static LinkedHashSet<Integer> getPrimes(long limit) {
		LinkedHashSet<Integer> primes = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> notPrimes = new LinkedHashSet<Integer>();
		boolean prime = true;
		for (int i = 2; i <= limit; i++) {
			prime = true;
			if (notPrimes.contains(i)) {
				prime = false;
			}
			if (prime) {
				primes.add(i);
				for (int j = 2; j*i <= limit; j++) {
					notPrimes.add(j*i);
				}
			}
		}
		return primes;
	}

	public int diff(int mod, int[] a, int[] numA, int[] b, int[] numB) {
		int diff = 0;		
		HashMap<Integer, Integer> map1 = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			int v = a[i] % mod;
			if (map1.containsKey(v)) {
				map1.put(v, map1.get(v) + numA[i]);
			} else {
				map1.put(v, numA[i]);
			}
		}	
		HashMap<Integer, Integer> map2 = new HashMap<>();
		for (int i = 0; i < b.length; i++) {
			int v = b[i] % mod;
			if (map2.containsKey(v)) {
				map2.put(v, map2.get(v) + numB[i]);
			} else {
				map2.put(v, numB[i]);
			}
		}
		for (int k : map1.keySet()) {
			if (map2.containsKey(k)) {
				diff += Math.abs(map1.get(k) - map2.get(k));
			} else {
				diff += map1.get(k);
			}
		}
		for (int k : map2.keySet()) {
			if (!map1.containsKey(k)) {
				diff += map2.get(k);
			}
		}
		return diff;
	}

}
