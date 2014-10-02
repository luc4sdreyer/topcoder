import java.util.Arrays;

public class ImportantSequence {

	public int getCount(int[] B, String operators) {
		long[] vars = new long[B.length+1];
		for (int j = 0; j < B.length; j++) {
			if (operators.charAt(j) == '+') {
				vars[j+1] = B[j] - vars[j];
			} else {
				vars[j+1] = vars[j] - B[j];
			}
		}

		long limUp = 2000000000;
		long limDown = 2000000000;
		boolean up = true;
		for (int i = 0; i < B.length; i++) {
			if (operators.charAt(i) == '+') {
				up = up == true ? false : true;
			}
			if (!up) {
				limDown = Math.min(limDown, vars[i+1]-1);
			}
		}
		up = false;
		limUp = Math.min(limUp, vars[0]-1);
		for (int i = 0; i < B.length; i++) {
			if (operators.charAt(i) == '+') {
				up = up == true ? false : true;
			}
			if (!up) {
				limUp = Math.min(limUp, vars[i+1]-1);
			}
		}
		if (limUp == 2000000000 || limDown == 2000000000) {
			return -1;
		}
		return (int) (limUp + limDown + 1);
	}

}
