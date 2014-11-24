public class RabbitStepping {

	public double getExpected(String f, int r) {
		char[] field = f.toCharArray();
		int numGames = 0;
		int total = 0;
		int N = 1 << field.length;
		for (int n = 0; n < N; n++) {
			int[] pos = new int[field.length];
			int numPos = 0;
			for (int i = 0; i < field.length; i++) {
				if (((1 << i) & n) != 0) {
					pos[i] = 1;
					numPos++;
				}
			}
			if (numPos == r) {
				total += play(field, pos);
				numGames++;
			}
		}
		return total / (double)numGames;
	}

	private int play(char[] field, int[] pos) {
		int numRem = 0;
		int[][] direction = new int[2][field.length];
		for (int len = field.length; len > 2; len--) {
			int[] newPos = new int[pos.length];
			int[][] newDirection = new int[2][field.length];
			for (int i = 0; i < len; i++) {
				if (i == 0) {
					newPos[i+1] += pos[i];
					newDirection[1][i+1] += pos[i];
					pos[i] = 0;
				} else if (i >= len-2) {
					newPos[i-1] += pos[i];
					newDirection[0][i-1] += pos[i];
					pos[i] = 0;
				} else {
					if (field[i] == 'W') {
						newPos[i-1] += pos[i];
						newDirection[0][i-1] += pos[i];
						pos[i] = 0;
					} else if (field[i] == 'B') {
						newPos[i+1] += pos[i];
						newDirection[1][i+1] += pos[i];
						pos[i] = 0;
					} else {
						if (len == field.length) {
							newPos[i-1] += pos[i];
							newDirection[0][i-1] += pos[i];
							pos[i] = 0;
						} else {
							newPos[i+1] += direction[0][i];
							newDirection[1][i+1] += direction[0][i];
							newPos[i-1] += direction[1][i];
							newDirection[0][i-1] += direction[1][i];
							direction[0][i] = 0;
							direction[1][i] = 0;
							pos[i] = 0;
						}
					}
				}
			}
			pos = newPos;
			direction = newDirection;
			for (int i = 0; i < len; i++) {
				if (pos[i] > 1) {
					pos[i] = 0;
				}
			}
		}
		for (int i = 0; i < 2; i++) {
			numRem += pos[i];
		}
		return numRem;
	}

}
