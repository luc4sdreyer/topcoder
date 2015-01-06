import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class TheKingsFactorization {

	public long[] getVector(long N, long[] primes) {
		long e = N;
		for (int i = 0; i < primes.length; i++) {
			e /= primes[i];
		}
		LinkedHashSet<Integer> pr = getPrimes(22361);
		ArrayList<Long> factors = new ArrayList<>();
		for (int p: pr) {
			while (e % p == 0) {
				factors.add((long) p);
				e /= p;
			}
		}
		ArrayList<Long> ret = new ArrayList<>();
		for (int i = 0; i < primes.length; i++) {
			ret.add(primes[i]);
			if (i < factors.size()) {
				ret.add(factors.get(i));
			}
		}
		long[] ret2 = new long[ret.size()];
		for (int i = 0; i < ret2.length; i++) {
			ret2[i] = ret.get(i);
		}
		return ret2;
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

}
