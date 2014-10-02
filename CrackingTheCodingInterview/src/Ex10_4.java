class Ex10_4 {
	public static void main(String[] args) {
		int[] a = {0, 1, 2, 3, 7, 8, 8, 8, 9, 9, 10, 11, 12, 13, 14, 16, 20, 21, 24, 56, 56, 57};
		findDuplicates(a);
	}

	public static void findDuplicates(int[] a) {
		byte[] bitVector = new byte[4*1024];
		for (int i = 0; i < a.length; i++) {
			if ((bitVector[a[i]/8] & (1 << (a[i] % 8))) != 0) {
				System.out.println(a[i]);
			} else {
				bitVector[a[i]/8] |= (1 << (a[i]% 8));
			}
		}
	}
}