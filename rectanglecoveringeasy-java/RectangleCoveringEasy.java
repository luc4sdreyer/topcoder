public class RectangleCoveringEasy {

	public int solve(int holeH, int holeW, int boardH, int boardW) {
		if ((boardW >= holeW && boardH > holeH) || (boardH >= holeW && boardW > holeH) ||
			(boardW > holeW && boardH >= holeH) || (boardH > holeW && boardW >= holeH)) {
			return 1;
		} else {
			return -1;
		}
	}

}
