public class TwiceString {

	public String getShortest(String s) {
		for (int i = 1; i < s.length(); i++) {
			boolean fail = true;
			for (int j = 0; i+j < s.length(); j++) {
				fail = false;
				if (s.charAt(i+j) != s.charAt(j)) {
					fail = true;
					break;
				}
			}
			if (!fail) {
				return s + s.substring(s.length()-i);
			}
		}
		
		return s + s;
		
	}

}
