public class IncubatorEasy {

	public int maxMagicalGirls(String[] love) {
		
		int n = love.length;
		boolean[][] sp = new boolean[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (love[i].charAt(j) == 'Y') {
					sp[i][j] = true;
				}
			}
		}

		for (int k = 0; k < sp.length; k++) {
			for (int i = 0; i < sp.length; i++) {
				for (int j = 0; j < sp.length; j++) {
					if (sp[i][k] == true && sp[k][j] == true) {
						sp[i][j] = true;
					}
				}
			}
		}
		
		int max = 0;
		int M = 1 << n;
		for (int m = 0; m < M; m++) {
			boolean[] magical = new boolean[n];
			for (int i = 0; i < n; i++) {
				if (((1 << i) & m) != 0) {
					magical[i] = true;
				}
			}
			max = Math.max(max, eval(sp, magical));
		}
		
		return max;
	}

	private int eval(boolean[][] sp, boolean[] magical) {
		int magicalUnprotected = 0;
		boolean[] protect = new boolean[magical.length];
		for (int i = 0; i < magical.length; i++) {
			if (magical[i]) {
				for (int j = 0; j < sp[i].length; j++) {
					if (sp[i][j]) {
						protect[j] = true;
					}
				}
			}
		}
		
		for (int i = 0; i < magical.length; i++) {
			if (magical[i] && !protect[i]) {
				magicalUnprotected++;
			}
		}
		return magicalUnprotected;
		
	}

}
