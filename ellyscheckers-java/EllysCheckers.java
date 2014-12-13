public class EllysCheckers {

	public String getWinner(String board) {
		char[] b = board.toCharArray();
		int moves = 0;
		for (int i = 0; i < b.length; i++) {
			if (b[i] == 'o') {
				moves += b.length - i -1;
			}
		}
		return moves % 2 == 0 ? "NO" : "YES";
	}

}
