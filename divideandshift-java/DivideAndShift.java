import java.util.LinkedHashSet;

public class DivideAndShift {

	public int getLeast(int N, int M) {
		LinkedHashSet<Integer> primes = getPrimes(1000);
		int min = Integer.MAX_VALUE;
		
		int n = N;
		int m = M;
		int bestP = 0;
		int ops = 0;
		while (n > 1) {
			ops++;
			bestP = 0;
			for (int i = 2; i*i <= n; i++) {
				if (primes.contains((int)i)) {
					if (n % i == 0) {
						int temp = m % (n/i);
						if (ops + temp < min) {
							min = ops + temp;
							bestP = i; 
						} else if (ops + (n/i) - temp < min) {
							min = ops + i - temp;
							bestP = i; 
						}
					}
				}
			}
			if (bestP == 0) {
				int temp = m % 1;
				if (ops + temp < min) {
					min = ops + temp;
					bestP = 1; 
				}
			}
			if (bestP == 0) {
				break;
			}
			n = n / bestP;
			m = m % (n/bestP);
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
}
