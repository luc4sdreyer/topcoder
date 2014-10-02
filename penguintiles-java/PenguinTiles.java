public class PenguinTiles {

	public int minMoves(String[] tiles) {
		int x = 0, y = 0;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length(); j++) {
				if (tiles[i].charAt(j) == '.') {
					x = i;
					y = j;
				}
			}
		}
		if (x == y && x == tiles.length-1) {
			return 0;
		} else if (x == tiles.length-1 || y == tiles[0].length()-1) {
			return 1;
		} else {
			return 2;
		}
	}
}
