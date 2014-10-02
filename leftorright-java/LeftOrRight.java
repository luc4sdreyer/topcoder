public class LeftOrRight {

	public int maxDistance(String program) {
		int reach = 0;
		int max = 0;
		for (int i = 0; i < program.length(); i++) {
			if (program.charAt(i) == 'R') {
				reach++;
			} else {
				reach--;
			}
			max = Math.max(max, Math.abs(reach));
		}
		reach = 0;
		for (int i = 0; i < program.length(); i++) {
			if (program.charAt(i) == 'L') {
				reach++;
			} else {
				reach--;
			}
			max = Math.max(max, Math.abs(reach));
		}
		return max;
	}
}

