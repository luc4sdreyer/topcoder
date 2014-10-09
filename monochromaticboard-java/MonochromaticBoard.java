public class MonochromaticBoard {

	public int theMin(String[] board) {
		int min = Integer.MAX_VALUE;
		
		int p = 0;
		boolean other = false;
		for (int i = 0; i < board.length; i++) {
			int numB = 0;
			for (int j = 0; j < board[i].length(); j++) {
				if (board[i].charAt(j) != 'W') {
					numB++;
				}
			}
			if (numB == board[i].length()) {
				p++;
			} else if (!other) {
				other = true;
				p += numB;
			}
		}
		min = Math.min(min, p);
		
		p = 0;
		other = false;
		for (int j = 0; j < board[0].length(); j++) {
			int numB = 0;
			for (int i = 0; i < board.length; i++) {
				if (board[i].charAt(j) != 'W') {
					numB++;
				}
			}
			if (numB == board.length) {
				p++;
			} else if (!other) {
				other = true;
				p += numB;
			}
		}
		min = Math.min(min, p);
		return min;
	}

}
