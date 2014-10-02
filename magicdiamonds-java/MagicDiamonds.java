public class MagicDiamonds {

	public long minimalTransfer(long n) {
		long N = n;
		int transfers = 0;
		while (N > 0) {
			long temp = N;
			while (isPrime(temp)) {
				temp--;
			}
			N -= temp;
			transfers++;
		}
		return transfers;
	}
	
	public boolean isPrime(long n) {
		if (n < 2) {
			return false;
		}
		int f = (int) Math.sqrt(n);
		for (int i = 2; i <= f; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

}
