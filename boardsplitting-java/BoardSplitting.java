public class BoardSplitting {

	public int minimumCuts(int desiredLength, int desiredCount, int actualLength) {
		int cuts = 0;

		int numBoards = 0;
		int currentBoard = actualLength;
		int residue = 0;
		while (numBoards < desiredCount) {
			if (residue >= desiredLength) {
				if (residue == desiredLength) {
					numBoards++;
					residue = 0;
				} else {
					residue -= desiredLength;
					numBoards++;
					cuts++;
				}
			} else {
				if (currentBoard > desiredLength) {
					currentBoard -= desiredLength;
					numBoards++;
					cuts++;
				} else {
					residue += currentBoard;
					currentBoard = actualLength;
				}
			}
		}
		return cuts;
	}

}
