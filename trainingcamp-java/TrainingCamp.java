public class TrainingCamp {

	public String[] determineSolvers(String[] attendance, String[] problemTopics) {
		char[][] map = new char[attendance.length][problemTopics.length];
		for (int student = 0; student < map.length; student++) {
			for (int problem = 0; problem < map[0].length; problem++) {
				boolean valid = true;
				for (int day = 0; day < attendance[0].length(); day++) {
					if (problemTopics[problem].charAt(day) == 'X' && attendance[student].charAt(day) != 'X') {
						valid = false;
						break;
					}
				}
				if (valid) {
					map[student][problem] = 'X';
				} else {
					map[student][problem] = '-';
				}
			}
		}
		String[] out = new String[map.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = new String(map[i]);
		}
		return out;
	}

}
