import java.util.Arrays;

public class UnsealTheSafe {

	public static long[][] keyPad = {
		{1, 1, 1},
		{1, 1, 1},
		{1, 1, 1},
	};
	public static long zero = 1;
	
	public static long[] cache;
	
	
	public static void getSAFECRAC(int n) {
		cache = new long[n+1];
		keyPad = new long[3][3];
		for (int i = 0; i < keyPad.length; i++) {
			Arrays.fill(keyPad[i], 1);
		}
		zero = 1;
		long[][] newKeyPad = new long[3][3];
		
		for (int i = 0; i < n; i++) {
			if (i > 0) {
				newKeyPad = new long[3][3];
				int x = 0;
				int y = 0;
				for (y = 0; y < 3; y++) {						
					if (y > 0) {
						newKeyPad[y][x] = (newKeyPad[y][x] + keyPad[y-1][x]);
					}
					if (y < 2) {
						newKeyPad[y][x] = (newKeyPad[y][x] + keyPad[y+1][x]);
					}
					newKeyPad[y][x] = (newKeyPad[y][x] + keyPad[y][x+1]);
				}

				y = 0;
				for (x = 1; x < 3; x++) {
					newKeyPad[y][x] = (newKeyPad[y][x] + keyPad[y+1][x]);
					newKeyPad[y][x] = (newKeyPad[y][x] + keyPad[y][x-1]);
					if (x < 2) {
						newKeyPad[y][x] = (newKeyPad[y][x] + keyPad[y][x+1]);
					}
				}
				newKeyPad[2][0] = (newKeyPad[2][0] + zero);
				
				newKeyPad[1][1] = (2*newKeyPad[0][0]);
				newKeyPad[2][2] = newKeyPad[0][0];
				newKeyPad[1][2] = newKeyPad[0][1];
				newKeyPad[2][1] = newKeyPad[1][0];
				
				zero = keyPad[2][0];
				keyPad = newKeyPad;
			}
			long sum = 0;
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					sum = (sum + keyPad[y][x]);
				}
			}
//			sum = (sum + (2 * keyPad[1][1]));
//			
//			sum = (sum + (2 * keyPad[1][0]));
//			
//			sum = (sum + (2 * keyPad[0][1]));
//			
//			sum = (sum + keyPad[0][2]);
//			sum = (sum + keyPad[2][0]);
			sum = (sum + zero);
			cache[i+1] = sum;
		}
	}

	public long countPasswords(int N) {
		getSAFECRAC(N);
		return cache[N];
	}

}
