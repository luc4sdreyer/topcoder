public class IncubatorEasy {

	public int maxMagicalGirls(String[] l) {
		char[][] love = new char[l.length][l.length];
		for (int i = 0; i < love.length; i++) {
			love[i] = l[i].toCharArray();
		}
		int N = love.length;
		
		// floydWarshall
		for (int k = 0; k < love.length; k++) {
			for (int i = 0; i < love.length; i++) {
				for (int j = 0; j < love.length; j++) {
					if (love[i][k] == 'Y' && love[k][j] == 'Y') {
						love[i][j] = 'Y';
					}
				}
			}
		}
		
		int max = 0;
		for (int i = 0; i < (1 << N); i++) {
			boolean[] magical = new boolean[N];
			for (int j = 0; j < magical.length; j++) {
				if (((i >> j) & 1) != 0) {
					magical[j] = true;
				}
			}
			max = Math.max(max, calc(love, magical));
		}
		return max;
	}

	private int calc(char[][] love, boolean[] magical) {
		boolean[] protect = new boolean[love.length];
		int numP = 0;
		int N = love.length;
		for (int i = 0; i < N; i++) {
			if (numP == N) {
				return 0;
			}
			if (magical[i]) {
				for (int j = 0; j < N; j++) {
					if (love[i][j] == 'Y' && !protect[j]) {
						protect[j] = true;
						numP++;
					}
				}
			}
		}
		
		int numMagicUnprotected = 0;
		for (int i = 0; i < N; i++) {
			if (magical[i] && !protect[i]) {
				numMagicUnprotected++;
			}
		}
		return numMagicUnprotected;
	}

}
