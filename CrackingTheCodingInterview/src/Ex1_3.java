public class Ex1_3 {
	public static void main(String args[]) {
		String s1 = "qwertyq";
		String s2 = "rteywqq";
		String s3 = "rteywqw";
		Ex1_3 ex = new Ex1_3();
		ex.compare(s1, s2);
		ex.compare(s1, s3);
		ex.compareUnicode(s1, s2);
		ex.compareUnicode(s1, s3);
	}

	public boolean compare(String s1, String s2) {
		//Assume ASCII
		int[] freq1 = getFrequencyTable(s1);
		int[] freq2 = getFrequencyTable(s2);
		for (int i = 0; i < freq1.length; i++) {
			if (freq1[i] != freq2[i]) {
				System.out.println("Not permutations: " + s1 + " " + s2);
				return false;
			}
		}
		System.out.println("Are permutations: " + s1 + " " + s2);
		return true;
	}

	public int[] getFrequencyTable(String s) {
		int[] freq = new int[265];
		for (int i = 0; i < s.length(); i++) {
			freq[s.charAt(i)]++;
		}
		return freq;
	}

	public boolean compareUnicode(String s1, String s2) {
		boolean ret = (sort(s1)).equals(sort(s2));
		if (ret) {
			System.out.println("Are permutations: " + s1 + " " + s2);
		} else {
			System.out.println("Not permutations: " + s1 + " " + s2);
		}
		return ret;
	}

	public String sort(String s) {
		char[] c = s.toCharArray();
		java.util.Arrays.sort(c);
		return new String(c);
	}
}