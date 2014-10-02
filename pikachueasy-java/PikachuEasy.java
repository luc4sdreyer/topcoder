public class PikachuEasy {

	public String check(String word) {
		while (word.length() >0){
			if ((word.length() > 1 && word.charAt(1) == 'i') && word.charAt(0) == 'p') {
				word = word.substring(2);				
			} else if ((word.length() > 1 && word.charAt(1) == 'a') && word.charAt(0) == 'k') {
				word = word.substring(2);				
			} else if (((word.length() > 2 && word.charAt(2) == 'u') && word.charAt(1) == 'h') && word.charAt(0) == 'c') {
				word = word.substring(3);				
			} else {
				return "NO";
			}
		}
		return "YES";
	}

}
