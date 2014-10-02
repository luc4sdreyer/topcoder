public class Ex1_4 {
	public static void main(String args[]) {
		Ex1_4 ex = new Ex1_4();
		char[] c = "Mr John Smith    ".toCharArray();
		ex.swap(c, 13);
		System.out.println(new String(c));
	}

	public void swap(char[] c, int trueLength) {
		int src = trueLength-1;
		int dest = c.length-1;
		while (src >= 0 && dest >= 0) {
			if (c[src] == ' ') {
				c[dest-2] = '%';
				c[dest-1] = '2';
				c[dest] = '0';
				dest -= 2;
			} else {
				c[dest] = c[src];
			}
			dest--;
			src--;
		}
	}
}