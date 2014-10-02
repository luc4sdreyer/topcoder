public class HappyLetterDiv2 {

	public char getHappyLetter(String letters) {
		int[] count = new int[26];
		for (int i = 0; i < letters.length(); i++) {
			count[letters.charAt(i)-'a']++;
		}
		int max = -1;
		int maxIdx = 0;
		for (int i = 0; i < count.length; i++) {
			if (count[i]> max) {
				max = count[i];
				maxIdx = i;
			} 
		}
		int sumRest = 0;
		for (int i = 0; i < count.length; i++) {
			if (i != maxIdx) {
				sumRest += count[i];
			}
		}
		if (max > sumRest) {
			return (char) ((char)maxIdx + 'a');
		} else {
			return '.';
		}
	}

}
