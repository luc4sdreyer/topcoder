public class GogoXCake {

	public String solve(String[] cake, String[] cutter) {
		char[][] cakeGrid = new char[cake.length][cake[0].length()];
		for (int i = 0; i < cakeGrid.length; i++) {
			cakeGrid[i] = cake[i].toCharArray();
		}
		char[][] cutterGrid = new char[cutter.length][cutter[0].length()];
		for (int i = 0; i < cutterGrid.length; i++) {
			cutterGrid[i] = cutter[i].toCharArray();
		}
		int cutterStartY = 0;
		int cutterStartX = 0;
		for (int y = 0; y < cutterGrid.length; y++) {
			for (int x = 0; x < cutterGrid[0].length; x++) {
				if (cutterGrid[y][x] == '.') {
					cutterStartY = y;
					cutterStartX = x;
					y = cutterGrid.length;
					break;
				}
			}
		}
		

		for (int y = 0; y < cakeGrid.length; y++) {
			for (int x = 0; x < cakeGrid[0].length; x++) {
				if (cakeGrid[y][x] == '.') {
					if (!fill(cakeGrid, cutterGrid, x - cutterStartX, y - cutterStartY)) {
						return "NO";
					}
				}
			}
		}
		
		for (int y = 0; y < cakeGrid.length; y++) {
			for (int x = 0; x < cakeGrid[0].length; x++) {
				if (cakeGrid[y][x] == '.') {
					return "NO";
				}
			}
		}
		
		return "YES";
	}
	
	public boolean fill(char[][] cakeGrid, char[][] cutterGrid, int offsetX, int offsetY) {
		for (int y = 0; y < cutterGrid.length; y++) {
			for (int x = 0; x < cutterGrid[0].length; x++) {
				if (offsetY + y < 0 || offsetY + y >= cakeGrid.length) {
					return false;
				}
				if (offsetX + x < 0 || offsetX + x >= cakeGrid[0].length) {
					return false;
				}
				if (cutterGrid[y][x] == '.') {
					if (cakeGrid[offsetY + y][offsetX + x] != cutterGrid[y][x]) {
						return false;
					}
					cakeGrid[offsetY + y][offsetX + x] = 'X';
				}				
			}
		}
		return true;
	}

}
