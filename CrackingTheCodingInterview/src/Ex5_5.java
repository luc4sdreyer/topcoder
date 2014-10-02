class Ex5_5 {
	public static void main(String args[]) {
		bitSwapRequired(31, 14);
		bitSwapRequired2(31, 14);
	}

	public static void bitSwapRequired(int a, int b) {
		int count = 0;
		for (int i = a ^ b; i != 0; i = i >> 1) {
			count += (i & 1);
		}
		System.out.println(count);
	}

	public static void bitSwapRequired2(int a, int b) {
		int count = 0;
		for (int i = a ^ b; i != 0; i = i & (i-1)) {
			count ++;
		}
		System.out.println(count);
	}
}