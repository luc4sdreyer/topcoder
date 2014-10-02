public class ChocolateBar {

	public int maxLength(String letters) {
		int max = 0;
		for (int i = 0; i < letters.length(); i++) {
			int[] a = new int[26];
			int numLetters = 0;
			for (int j = i; j < letters.length(); j++) {
				if (a[letters.charAt(j) - 'a'] == 0) {
					a[letters.charAt(j) - 'a'] = 1;
					numLetters++;
				} else {
					break;
				}
			}
			max = Math.max(max, numLetters);
		}
		return max;
	}

}
