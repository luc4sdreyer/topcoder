public class EasyConversionMachine {

	public String isItPossible(String originalWord, String finalWord, int k) {
		int cost = 0;
		for (int i = 0; i < originalWord.length(); i++) {			
			if (originalWord.charAt(i) != finalWord.charAt(i)) {
				cost++;
			}
		}
		int extra = k - cost;
		if (extra >= 0 && extra % 2 == 0) {
			return "POSSIBLE";
		} else {
			return "IMPOSSIBLE";
		}
	}

}
