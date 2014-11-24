public class SequenceOfCommands {

	public String whatHappens(String[] commands) {
		int x = 0;
		int y = 0;
		int direction = 0;
		int[] dx = {1, 0, -1, 0};
		int[] dy = {0, 1, 0, -1};
		for (int k = 0; k < 4; k++) {			
			for (int i = 0; i < commands.length; i++) {
				for (int j = 0; j < commands[i].length(); j++) {
					if (commands[i].charAt(j) == 'S') {
						x += dx[direction];
						y += dy[direction];
					} else if (commands[i].charAt(j) == 'R') {
						direction = (direction + 1) % 4;
					} else {
						direction--;
						if (direction < 0) {
							direction = 3;
						}
					}
				}
			}
		}
		if (x == 0 && y == 0) {
			return "bounded";
		} else {
			return "unbounded";
		}
	}

}
