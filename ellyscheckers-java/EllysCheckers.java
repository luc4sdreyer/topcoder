public class EllysCheckers {

	public String getWinner(String board) {
		boolean first = true;
		boolean win = false;
		for (int i = 0; i < board.length(); i++) {
			if (board.charAt(i) == 'o') {
				if (first) {
					first = false;
					if ((board.length() - i - 1) % 2 == 1) {
						win = true;
					} else {
						win = false;
					}
				} else {
					if ((board.length() - i - 1) % 2 == 1) {
						win = win == true ? false : true; 
					}
				}
			}
		}
		if (win) {
			return "YES";
		} else {
			return "NO";
		}
	}

}
