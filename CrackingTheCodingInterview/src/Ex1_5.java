public class Ex1_5 {
	public static void main(String args[]) {
		Ex1_5 ex = new Ex1_5();
		String s = "aab";
		System.out.println("Compress " + s + ": " + ex.compress(s));
	}

	public String compress(String s) {
		// ASSUME UTF16 max
		if (s.length() == 0) {
			return s;
		}
		StringBuilder compressed = new StringBuilder();
		int sequenceLength = 0;
		char oldChar = s.charAt(0);
		for (int i = 0; i < s.length(); i++) {
			if (oldChar == s.charAt(i)) {
				sequenceLength++;
			} else {
				compressed.append(oldChar);
				compressed.append(sequenceLength);
				oldChar = s.charAt(i);
				sequenceLength = 1;
			}
		}
		compressed.append(oldChar);
		compressed.append(sequenceLength);
		if (compressed.length() >= s.length()) {
			return s;
		} else {
			return compressed.toString();
		}
	}
}