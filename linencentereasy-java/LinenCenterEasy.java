public class LinenCenterEasy {
	
	public int countStrings(String S, int N, int K) {
		countStringsFast(S, N, K);
		return countStringsSlow(S, N, K);
	}

	public int countStringsFast(String S, int N, int K) {
		int L = S.length();
		
		int[][] prefix_becomes = new int[L][]; //
		
		
		long[][] F = new long[L*K+N + 1][L+1]; //t, k
		F[L-1][1] = 1;
		long G = 1;
		long mod = (long) (1e9+9);
		long ways = 0;
		for (int i = 0; i < L-1; i++) {
			G = G * 26 % mod;
		}
		for (int t = L-1; t <= L*K+N; t++) {
			for (int k = 1; k <= L; k++) {
				for (int i = 0; i < t-L-1; i++) {
					F[t][k] += F[i][k-1]; 
				}
			}
			G = G * 26 % mod;
			if (t >= L*K) {
				// all != K are invalid
				for (int k = 1; k <= L; k++) {
					ways = (ways) % mod;
					if (k != K) {
						ways = (ways - F[t][k] + mod) % mod;
					}
				}
			}
		}
		return (int)(ways % mod);
	}
	
	public int countStringsSlow(String S, int N, int K) {
		int L = S.length();
		long[][] F = new long[L*K+N + 1][L*K+N + 1]; //t, k
		int ways = 0;
		
		for (int t = 1; t <= L*K+N; t++) {
			int[] x = new int[t];
			do {
				int num = 0;
				for (int i = 0; i < x.length - L+1; i++) {
					boolean match = true;
					for (int j = 0; j < L; j++) {
						if (x[i+j] != S.charAt(j) - 'a') {
							match = false;
						}
					}
					if (match) {
						num++;
						i += L-1;
					} 
				}
				F[t][num]++;
			} while (next_number(x, 26));
			
			if (t >= L*K) {
				ways += F[t][K]; 
			}
		}
		
		return ways;
	}
	
	public static boolean next_number(int list[], int base) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		LinenCenterEasy solution = new LinenCenterEasy();
		String S = "xy";
        int N = 2;
        int K = 1;

        solution.countStrings(S, N, K);
        
	}

}
