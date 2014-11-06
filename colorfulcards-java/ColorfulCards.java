import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ColorfulCards {

	public int[] theCards(int N, String colors) {
		HashSet<Integer> primes = getPrimes(N*N);
		char[] col = colors.toCharArray();
		int[] ret = new int[colors.length()];
		for (int i = 0; i < col.length; i++) {
			int startN = 1; 
			int start = 0;
			while (start != i) {
				if ((col[start] == 'B' && !primes.contains(startN)) || (col[start] == 'R' && primes.contains(startN))) {
					start++;
				}
				startN++;
			}
			int end = col.length-1;
			int endN = N;
			while (end != i) {
				if ((col[end] == 'B' && !primes.contains(endN)) || (col[end] == 'R' && primes.contains(endN))) {
					end--;
				}
				endN--;
			}
			int num = 0;
			int x = 0;
			for (int n = startN; n <= endN; n++) {
				if ((col[i] == 'B' && !primes.contains(n)) || (col[i] == 'R' && primes.contains(n))) {
					num++;
					x = n;
				}
			}
			if (num > 1) {
				ret[i] = -1;
			} else {
				ret[i] = x;
			}
		}
		return ret;
	}
	
	private HashSet<Integer> getPrimes(int N) {
		ArrayList<Integer> primes = new ArrayList<>();
		for (int i = 2; i*i <= N; i++) {
			boolean prime = true;
			int max = 2;
			for (int j = 0; j < primes.size(); j++) {
				if (i % primes.get(j) == 0) {
					max = Math.max(max, primes.get(j));
					prime = false;
					break;
				}
			}
			if (prime) {
				for (int k = max+1; k*k <= i; k++) {
					if (i % k == 0) {
						prime = false;
						break;
					}
				}
			}
			if (prime) {
				primes.add(i);
			}
		}
		HashSet<Integer> p = new HashSet<>();
		for (Integer prime : primes) {
			p.add(prime);
		}
		return p;
	}

}
