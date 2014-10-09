public class CompositeSmash {

	public String thePossible(int N, int target) {
		if (N % target != 0) {
			return "No";
		}
		if (N == target) {
			return "Yes";
		}
		
		int product = N;
		int[] primes = new int[1000];
		while (product > 1) {
			for (int i = 2; i <= product; i++) {
				if (product % i == 0 && isPrime(i)) {
					while (product % i == 0) {
						primes[i]++;
						product /= i;
					}
					break;
				}
			}
		}
		
		String prim = "";
		for (int i = 0; i < primes.length; i++) {
			if (primes[i] != 0) {
				prim += i + ", ";
			}
		}
		System.out.println(prim);
		
		int[] p = primes.clone();
		for (int i = 0; i < p.length; i++) {
			if (p[i] >= 4) {
				return "No";
			}
		}
		
		p = primes.clone();
		int c = 0;
		for (int i = 0; i < p.length; i++) {
			if (c == 0 && p[i] >= 2) {
				c++;
				p[i] = 0;
				i = 0;
			} else if (c == 1 && p[i] >= 1) {
				return "No";
			}
		}
		
		p = primes.clone();
		c = 0;
		for (int i = 0; i < p.length; i++) {
			if (c == 0 && p[i] >= 1) {
				c++;
				p[i] = 0;
				i = 0;
			} else if (c == 1 && p[i] >= 1) {
				c++;
				p[i] = 0;
				i = 0;
			} else if (c == 2 && p[i] >= 1) {
				return "No";
			}
		}
		return "Yes";
	}
	
	public boolean isPrime(int n) {
		if (n < 2) {
			return false;
		}
		for (int i = 2; i*i <= n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

}
