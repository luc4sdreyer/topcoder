public class CheatingQuiz {

	public int[] howMany(String answers) {
		int hasA = 0;
		int hasB = 0;
		int hasC = 0;
		int[] options = new int[answers.length()];
		for (int i = answers.length() -1; i >= 0 ; i--) {
			if (answers.charAt(i) == 'A' && hasA == 0) {
				hasA  = 1;
			} else if (answers.charAt(i) == 'B' && hasB == 0) {
				hasB  = 1;
			} else if (answers.charAt(i) == 'C' && hasC == 0) {
				hasC  = 1;
			}
			options[i] = hasA + hasB + hasC; 
		}
		return options;
	}

}
