import java.math.BigInteger;

public class GCDLCMEasy {

	public String possible(int n, int[] A, int[] B, int[] G, int[] L) {
		int[] xList = new int[n];
		int maxLCM = 0;
		for (int i = 0; i < L.length; i++) {
			maxLCM = Math.max(maxLCM, L[i]);
		}
		for (int k = 1; k <= maxLCM; k++) {
			xList = new int[n];
			xList[0] = k;		
			
			boolean update = true;
			while (update) {
				update = false;
				for (int j = 0; j < A.length; j++) {
					int aIdx = A[j];
					int a = xList[aIdx];
					if (a != 0) {
						for (int i = 0; i < A.length; i++) {
							if (xList[B[i]] == 0 && A[i] == aIdx) {
								int product = G[i] * L[i];
								if (product % a == 0) {
									xList[B[i]] = product / a;
									update = true;
								}
							}
						}
					}
				}
			}
			
			boolean fail = false;
			for (int i = 0; i < A.length; i++) {
				if (gcd(xList[A[i]], xList[B[i]]) != G[i] || lcm(xList[A[i]], xList[B[i]]) != L[i]) {
					fail = true;
				}
			}
			if (!fail) {
				return "Solution exists";
			}
		}
		return "Solution does not exist";
	}
	
	private static int gcd(int a, int b) {
	    return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
	}
	
	private static int lcm(int a, int b) {
		return (a * b) / gcd(a, b);
	}

}
