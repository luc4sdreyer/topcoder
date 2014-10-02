public class XorBoardDivTwo {

	public int theMax(String[] board) {
		int max = 0;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length(); j++) {
				int sum = 0;
				int[][] grid = new int[board.length][board[0].length()];
				for (int i2 = 0; i2 < grid.length; i2++) {
					for (int j2 = 0; j2 < grid[0].length; j2++) {
						if (board[i2].charAt(j2) == '1') {
							grid[i2][j2] = 1;
						}
					}
				}
				
				for (int k = 0; k < board.length; k++) {
					grid[k][j] = grid[k][j] == 1 ? 0 : 1;
				}
				for (int k = 0; k < board[0].length(); k++) {
					grid[i][k] = grid[i][k] == 1 ? 0 : 1;
				}
				for (int i2 = 0; i2 < grid.length; i2++) {
					for (int j2 = 0; j2 < grid[0].length; j2++) {
						if (grid[i2][j2] == 1) {
							sum++;
						}
					}
				}
				max = Math.max(max, sum);
			}
		}
		return max;
	}

}
