public class CoinsGameEasy {
	public final static int[] dx = {1, 0, -1, 0};
	public final static int[] dy = {0, 1, 0, -1};
	public final static int BIG = 100;
	
	public int minimalSteps(String[] b) {
		char[][] board = new char[b.length][b[0].length()];
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1;
		
		for (int i = 0; i < board.length; i++) {
			board[i] = b[i].toCharArray();
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 'o') {
					if (x1 == -1) {
						x1 = j;
						y1 = i;
					} else {
						x2 = j;
						y2 = i;						
					}
				}
			}
		}
		int moves = dfs(board, x1,y1,x2,y2,1);
		if (moves > 10) {
			return -1;
		}
		return moves;
	}

	private int dfs(char[][] board, int x1, int y1, int x2, int y2, int moves) {
		int min = BIG;
		if (moves > 10) {
			return moves;
		}
		for (int dir = 0; dir < 4; dir++) {
			int newX1 = x1 + dx[dir];
			int newY1 = y1 + dy[dir];			
			int newX2 = x2 + dx[dir];
			int newY2 = y2 + dy[dir];
			boolean offBoard1 = false;
			boolean offBoard2 = false;
			if (newX1 < 0 || newX1 >= board[0].length || newY1 < 0 || newY1 >= board.length) {
				offBoard1 = true;
			}
			if (newX2 < 0 || newX2 >= board[0].length || newY2 < 0 || newY2 >= board.length) {
				offBoard2 = true;
			}
			if ((offBoard1 && !offBoard2) || (!offBoard1 && offBoard2)) {
				min = Math.min(min, moves);
				continue;
			}
			if (offBoard1 && offBoard2) {
				// game over
				continue;
			}
			boolean moved1 = true;
			boolean moved2 = true;
			if (board[newY1][newX1] == '#') {
				newX1 = x1;
				newY1 = y1;
				moved1 = false;
			}
			if (board[newY2][newX2] == '#') {
				newX2 = x2;
				newY2 = y2;
				moved2 = false;
			}
			if (!moved1 && !moved2) {
				// nothing happened
				continue;
			}
			if (newX1 == newX2 && newY1 == newY2) {
				// game over
				continue;				
			}
			min = Math.min(min, dfs(board, newX1, newY1, newX2, newY2, moves+1));
		}
		return min;
	}

}
