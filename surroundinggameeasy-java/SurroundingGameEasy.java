public class SurroundingGameEasy {

	public int score(String[] cost, String[] benefit, String[] stone) {
		int score = 0;
		for (int y = 0; y < stone.length; y++) {
			for (int x = 0; x < stone[y].length(); x++) {
				if (stone[y].charAt(x) == 'o') {
					score -= Integer.parseInt(cost[y].charAt(x)+"");
				}
				if (stone[y].charAt(x) == 'o' || (
						(y-1 < 0 || (y-1 >= 0 && stone[y-1].charAt(x) == 'o')) &&
						(x-1 < 0 || (x-1 >= 0 && stone[y].charAt(x-1) == 'o')) &&
						(y+1 >= stone.length || (y+1 < stone.length && stone[y+1].charAt(x) == 'o')) &&
						(x+1 >= stone[y].length() || (x+1 < stone[y].length() && stone[y].charAt(x+1) == 'o'))
						)) {
					score += Integer.parseInt(benefit[y].charAt(x)+"");
				}
			}
		}
		return score;
	}

}
