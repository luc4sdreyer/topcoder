class Ex5_8 {
	public static void main(String args[]) {
		byte[] b = new byte[4];
		int width = 2;
		drawLine(b, width*8, 3, 4, 1);
		String temp = "";
		for (int i = 0; i < b.length; i++) {
			if ((i % width) == 0) {
				temp += "\n";
			}
			
			temp += getByteString(b[i]);
		}
		System.out.println(temp);
	}
	
	public static String getByteString(byte b) {
		String temp = "";
		for (int i = 0; i < 8; i++) {
			if ((b & (1 <<i)) != 0) {
				temp = "1" + temp;
			} else {
				temp = "0" + temp;
			}
		}
		return temp;
	}

	public static void drawLine(byte[] screen, int width, int x1, int x2, int y) {
		int start = y*width/8 + x1/8;
		int end = y*width/8 + x2/8;

		for (int i = start; i <= end; i++) {
			if (i == start && i == end) {
				for (int j = 7 - (x2 % 8); j <= 7 - (x1 % 8); j++) {
					screen[i] = (byte) (screen[i] | (1 << j));
				}
			} else if (i == start) {
				for (int j = 0; j <= 7 - (x1 % 8); j++) {
					screen[i] = (byte) (screen[i] | (1 << j));
				}
			} else if (i == end) {
				for (int j = 7 - (x2 % 8); j < 8; j++) {
					screen[i] = (byte) (screen[i] | (1 << j));
				}
			} else {
				screen[i] = (byte)0xFF;
			}
		}
	}
}