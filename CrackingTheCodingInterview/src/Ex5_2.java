class Ex5_2 {
	public static void main(String args[]) {
		printBinary(0.5);
	}

	public static void printBinary(double d) {
		String bin = "";
		for (int i = 1; i <= 64; i++) {
			double t = 1 / (double)(1 << i);
			if (d >= t) {
				bin += "1";
				d -= t;
			} else {
				bin += "0";
			}
		}
		System.out.println(bin);
	}
}