public class EllysXors {
	
	public long getXor(long L, long R) {
		return n1Xor(L-1) ^ n1Xor(R);
	}
	
	public long n1Xor(long n) {
		if (n % 4 == 0) return n;
		if (n % 4 == 1) return 1;
		if (n % 4 == 2) return n+1;
		return 0;
	}

}

