import java.math.BigInteger;

public class FoxAndGCDLCM {

	public long get(long G, long L) {
		double AB = G;
		AB *= L;
		long sAB = (long) Math.sqrt(AB);
		
		long A = Long.MAX_VALUE/2;
		long B = Long.MAX_VALUE/2;
		for (long i = G; i <= sAB; i += G) {
			if (L % i == 0) {
				long tA = i;
				long tB = (L/i) * G;
				if (lcm(tA, tB) == L) {
					if (tA + tB < A + B) {
						A = tA;
						B = tB;
					}
				}
			}
		}
		if (A == Long.MAX_VALUE/2) {
			return -1;
		}
		return A+B;
	}
	
	public long lcm(long a, long b) {
	    return BigInteger.valueOf(b).multiply(BigInteger.valueOf(a)).divide(BigInteger.valueOf(a).gcd(BigInteger.valueOf(b))).longValue();
	}
}
