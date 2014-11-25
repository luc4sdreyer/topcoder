public class AlienAndGame {

	public int getNumber(String[] b) {
		boolean[][] board = new boolean[b.length][b[0].length()];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (b[i].charAt(j) == 'B') {
					board[i][j] = true;
				}
			}
		}
		
		int max = 0;
		for (int k = 1; k <= board.length && k <= board[0].length; k++) {
			for (int i = 0; i <= board.length-k; i++) {
				for (int j = 0; j <= board[0].length-k; j++) {
					boolean valid = true;
					for (int m = i; m < i+k; m++) {
						boolean uniform = true;
						for (int n = j+1; n < j+k; n++) {
							if (board[m][n-1] != board[m][n]) {
								uniform = false;
								break;
							}
						}
						if (!uniform) {
							valid = false;
							break;
						}
					}
					if (valid) {
						max = Math.max(max, k*k);
					}
				}
			}
		}
		return max;
	}

}
