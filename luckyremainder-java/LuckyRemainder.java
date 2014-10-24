public class LuckyRemainder {

	public int getLuckyRemainder(String X) {
		int mod9 = (X.charAt(0) - '0');
		for (int i = 1; i < X.length(); i++) {
			int temp = 0;
			for (int j = 0; j <= i; j++) {
				temp += X.charAt(j) - '0';
			}
			temp = temp % 9;
			if (i > 1) {
				for (int j = 0; j < i; j++) {
					temp += ((X.charAt(j) - '0')*10 + (X.charAt(i) - '0')) % 9;
				}
			}
			temp += (X.charAt(i) - '0');
			mod9 += temp % 9;
		}
		return mod9 % 9;
	}

}
