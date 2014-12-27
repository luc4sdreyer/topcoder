public class TheKingsArmyDiv2 {

	public int getNumber(String[] state) {
		int numH = 0;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length(); j++) {
				if (state[i].charAt(j) == 'H') {
					numH++;
				}
			}
		}
		
		int numAdj = 0;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length()-1; j++) {
				if (state[i].charAt(j) == 'H' && state[i].charAt(j+1) == 'H') {
					numAdj++;
				}
			}
		}

		for (int i = 0; i < state.length-1; i++) {
			for (int j = 0; j < state[0].length(); j++) {

				if (state[i].charAt(j) == 'H' && state[i+1].charAt(j) == 'H') {
					numAdj++;
				}
			}
		}

		
		if (numH == 0) {
			return 2;
		} else if (numAdj == 0) {
			return 1;
		} else {
			return 0;
		}
	}

}
