import java.util.ArrayList;

public class MathContest {

	public int countBlack(String ballSequence, int repetitions) {
		boolean[] stack = new boolean[repetitions*ballSequence.length()];
		int start = 0;
		int end = 0;
		for (int i = 0; i < repetitions; i++) {
			for (int j = 0; j < ballSequence.length(); j++) {
				if (ballSequence.charAt(j) == 'W') {
					stack[end++] = true;
				} else {
					stack[end++] = false;
				}
			}
		}
		boolean inverted = false;
		boolean atFront = true;
		int numBlack = 0;
		while (start < end) {
			boolean top;
			if (atFront) {
				top = stack[start++];
			} else {
				top = stack[--end];
			}
			if (top == inverted) {
				numBlack++;
				inverted = inverted == false ? true : false;
			} else {
				atFront = atFront == false ? true : false;
			}
		}
		return numBlack;
	}

}
