public class BallAndHats {

	public int getHat(String hats, int numSwaps) {
		int pos = 0;
		for (int i = 0; i < hats.length(); i++) {
			if (hats.charAt(i) == 'o') {
				pos = i;
			}
		}
		if (numSwaps == 0) {
			return pos;
		}
		if (pos == 1) {
			numSwaps++;
		}
		if (numSwaps % 2 == 0) {
			return 0;
		} else {
			return 1;
		}
	}

}
