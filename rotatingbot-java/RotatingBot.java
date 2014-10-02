public class RotatingBot {
	
	public class Position {
		int x, y;
		Position(int x, int y) {
			this.x =x;
			this.y =y;
		}
	}

	
	public int minArea(int[] moves) {
		boolean[][] board = new boolean[5000][5000];
		Position current = new Position(2500,2500);
		int minX = 0, minY = 0;
		int maxX = 4999, maxY = 4999;
		board[current.y][current.x] = true;
		for (int i = 0; i < moves.length; i++) {
			if (i % 4 == 0) {
				for (int j = 0; j < moves[i]; j++) {
					current.x++;
					if (board[current.y][current.x]) {
						return -1;
					}
					board[current.y][current.x] = true;
				}
			} else if (i % 4 == 2) {
				for (int j = 0; j < moves[i]; j++) {
					current.x--;
					if (board[current.y][current.x]) {
						return -1;
					}
					board[current.y][current.x] = true;
				}
			} else if (i % 4 == 1) {
				for (int j = 0; j < moves[i]; j++) {
					current.y++;
					if (board[current.y][current.x]) {
						return -1;
					}
					board[current.y][current.x] = true;
				}
			} else if (i % 4 == 3) {
				for (int j = 0; j < moves[i]; j++) {
					current.y--;
					if (board[current.y][current.x]) {
						return -1;
					}
					board[current.y][current.x] = true;
				}
			}
		}
		maxY = 0;
		maxX = 0;
		minY = 1000000;
		minX = 1000000;
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (board[y][x]) {
					if (y > maxY) {
						maxY = y;
					}
					if (x > maxX) {
						maxX = x;
					}
					if (y < minY) {
						minY = y;
					}
					if (x < minX) {
						minX = x;
					}
				}
			}
		}

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				board[y][x] = false;
				if (x < minX) {
					board[y][x] = true;
				}
				if (x > maxX) {
					board[y][x] = true;
				}
				if (y < minY) {
					board[y][x] = true;
				}
				if (y > maxY) {
					board[y][x] = true;
				}
			}
		}
		current = new Position(2500,2500);
		board[current.y][current.x] = true;
		if (!board[current.y][current.x]) {
			return -1;
		}
		for (int i = 0; i < moves.length; i++) {
			if (i % 4 == 0) {
				for (int j = 0; j < moves[i]; j++) {
					current.x++;
					board[current.y][current.x] = true;
					if (!board[current.y][current.x]) {
						return -1;
					}
				}
				if (!board[current.y][current.x+1] && i < moves.length-1) {
					return -1;
				}
			} else if (i % 4 == 2) {
				for (int j = 0; j < moves[i]; j++) {
					current.x--;
					board[current.y][current.x] = true;
					if (!board[current.y][current.x]) {
						return -1;
					}
				}
				if (!board[current.y][current.x-1] && i < moves.length-1) {
					return -1;
				}
			} else if (i % 4 == 1) {
				for (int j = 0; j < moves[i]; j++) {
					current.y++;
					board[current.y][current.x] = true;
					if (!board[current.y][current.x]) {
						return -1;
					}
				}
				if (!board[current.y+1][current.x] && i < moves.length-1) {
					return -1;
				}
			} else if (i % 4 == 3) {
				for (int j = 0; j < moves[i]; j++) {
					current.y--;
					board[current.y][current.x] = true;
					if (!board[current.y][current.x]) {
						return -1;
					}
				}
				if (!board[current.y-1][current.x] && i < moves.length-1) {
					return -1;
				}
			}
		}
		//System.out.println(maxY + " " + minY + " " + maxX + " " + minX);
		return (maxY-minY+1)*(maxX-minX+1);
	}

}
