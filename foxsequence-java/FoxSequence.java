import java.util.ArrayList;
import java.util.HashSet;

import javax.print.attribute.HashAttributeSet;

public class FoxSequence {

	public String isValid(int[] seq) {
		int N = seq.length;
		HashSet<Integer> primes = getPrimes(N);
		for (int a = 1; a < N; a++) {
			for (int b = a+1; b < N; b++) {
				for (int c = b; c < N; c++) {
					for (int d = c+1; d < N; d++) {
						if (check(a, b, c, d, seq, primes)) {
							return "YES";
						}
					}
				}
			}
		}
		return "NO";
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

	private boolean check(int a, int b, int c, int d, int[] seq, HashSet<Integer> primes) {
		int N = seq.length;
		if (0 < a && a < b && b <= c && c < d && d < N-1) {
			int diff = 0;
			boolean first = true;
			for (int i = 1; i <= a; i++) {
				if (first) {
					first = false;
					diff = seq[i-1] - seq[i];
					if (diff >= 0) {
						return false;
					}
				} else if (seq[i-1] - seq[i] != diff) {
					return false;
				}
			}
			first = true;
			for (int i = a+1; i <= b; i++) {
				if (first) {
					first = false;
					diff = seq[i-1] - seq[i];
					if (diff <= 0) {
						return false;
					}
				} else if (seq[i-1] - seq[i] != diff) {
					return false;
				}
			}
			first = true;
			for (int i = b+1; i <= c; i++) {
				if (first) {
					first = false;
					diff = seq[i-1] - seq[i];
					if (diff != 0) {
						return false;
					}
				} else if (seq[i-1] - seq[i] != diff) {
					return false;
				}
			}
			first = true;
			for (int i = c+1; i <= d; i++) {
				if (first) {
					first = false;
					diff = seq[i-1] - seq[i];
					if (diff >= 0) {
						return false;
					}
				} else if (seq[i-1] - seq[i] != diff) {
					return false;
				}
			}
			first = true;
			for (int i = d+1; i < N; i++) {
				if (first) {
					first = false;
					diff = seq[i-1] - seq[i];
					if (diff <= 0) {
						return false;
					}
				} else if (seq[i-1] - seq[i] != diff) {
					return false;
				}
			}
			return true;
		}	
		return false;
	}

}
