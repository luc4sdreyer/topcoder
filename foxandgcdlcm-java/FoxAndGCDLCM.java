import java.math.BigInteger;

public class FoxAndGCDLCM {

	public long get(long G, long L) {
		long min = Long.MAX_VALUE;
		for (long i = 1; i*i*G <= L; i++) {
			long A = i*G;
			if (L % A == 0) {
				long B = L/i;
				if (gcd(A, B) == G && (lcm(A, B) == L)) {
					min = Math.min(min, A + B);
				}
			}
		}
		if (min == Long.MAX_VALUE) {
			return -1;
		}
		return min;
	}
	
	public static long gcd(long a, long b) {
	    return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).longValue();
	}
	
	public static long lcm(long a, long b)
	{
		return BigInteger.valueOf(b).multiply(BigInteger.valueOf(a)).divide(BigInteger.valueOf(a).gcd(BigInteger.valueOf(b))).longValue();
	}

}
