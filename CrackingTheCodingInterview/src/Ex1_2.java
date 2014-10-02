public class Ex1_2 {
	public static void main(String args[]) {
		char[] s = "Lucas1X".toCharArray();
		(new Ex1_2()).reverse(s);
		System.out.println(s);
	}

	void reverse(char[] str) {
		int len = str.length;
		len--;
		for (int i = 0; i < (len)/2; i++) {
			char temp = str[i];
			str[i] = str[len-i-1];
			str[len-i-1] = temp;
		}
	}
}