public class FauxPalindromes {

	public String classifyIt(String word) {
		if (isPal(word)) {
			return "PALINDROME";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < word.length()-1; i++) {
			if (word.charAt(i+1) != word.charAt(i)) {
				sb.append(word.charAt(i));
			}
		}
		sb.append(word.charAt(word.length()-1));
		if (isPal(sb.toString())) {
			return "FAUX";
		}
		
		return "NOT EVEN FAUX";
	}
	
	public boolean isPal (String word) {
		boolean pal = true;
		for (int i = 0; i < word.length()/2; i++) {
			if (word.charAt(i) != word.charAt(word.length()-i-1)) {
				pal = false;
			}
		}
		return pal;
	}

}
