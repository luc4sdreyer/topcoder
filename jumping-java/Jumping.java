import java.util.Arrays;

public class Jumping {

	public String ableToGet(int x, int y, int[] jumpLengths) {
		Arrays.sort(jumpLengths);
		int subLength = 0;
		for (int i = 0; i < jumpLengths.length - 1; i++) {
			subLength += jumpLengths[i];
		}
		int min = jumpLengths[jumpLengths.length - 1] - subLength;
		int max = jumpLengths[jumpLengths.length - 1] + subLength;
		double r = Math.sqrt(x * x + y * y); 
		if (min <= r && r <= max) {
			return "Able";
		} else {
			return "Not able";
		}
	}
}
