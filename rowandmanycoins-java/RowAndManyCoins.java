public class RowAndManyCoins {

	public String getWinner(String cells) {
		int a = 0;
		int b = 0;
		
		for (int i = 0; i < cells.length(); i++) {
			if (i == 0) {
				if (cells.charAt(i) == 'A') {
					a++;
				} else {
					b++;
				}
			} else {
				if (cells.charAt(i) != cells.charAt(i-1)) {
					if (cells.charAt(i) == 'A') {
						a++;
					} else {
						b++;
					}
				}
			}
		}
		if (a >= b) {
			return "Alice";
		} else {
			return "Bob";
		}
	}

}
