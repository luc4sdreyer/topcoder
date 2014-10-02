class Ex5_6 {
	public static void main(String args[]) {
		bitSwap(103);
	}

	public static void bitSwap(int n) {
		int even = n & 0xAAAAAAAA;
		int odd = n & 0x55555555;
		int s = (even >> 1) | (odd << 1);
		System.out.println(s);
	}

	public static void bitSwap2(int n) {
		int s = ((n & 0xAAAAAAAA) >> 1) | ((n & 0x55555555) << 1);
		System.out.println(s);
	}
}