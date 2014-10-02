public class AkariDaisukiDiv2 {

	public int countTuples(String S) {
		if (S.length() < 5) {
			return 0;
		}
		int count = 0;
		S = S.substring(1, S.length()-1);
		for (int start = 0; start < S.length(); start++) {
			for (int end = start+1; end < S.length(); end++) {
				String token = S.substring(start, end);
				for (int i = end+1; i <= S.length()-token.length(); i++) {
					if (S.substring(i, i+token.length()).equals(token)) {
						count++;
					}
				}
			}
		}
		return count;
	}

}
