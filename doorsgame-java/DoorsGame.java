import java.util.HashSet;

public class DoorsGame {

	public int determineOutcome(String doors_, int trophy) {
		char[] doors = doors_.toCharArray();
		HashSet<Character> set1 = new HashSet<>();
		HashSet<Character> set2 = new HashSet<>();
		for (int i = 0; i < doors.length; i++) {
			if (i < trophy) {
				set1.add(doors[i]);				
			} else {
				set2.add(doors[i]);
			}
		}
		int[] initialSize = {set1.size(), set2.size()};
		int turn = 0;
		while (!set1.isEmpty() && !set2.isEmpty()) {
			char best = 0;
			if (turn % 2 == 0) {
				for (char door : set1) {
					best = door;
					if (!set2.contains(door)) {
						break;
					}
				}
			} else {
				for (char door : set2) {
					best = door;
					if (!set1.contains(door)) {
						break;
					}
				}
			}
			set1.remove(best);
			set2.remove(best);
			turn++;
		}
		boolean johnWins = false;
		if (set1.isEmpty() && set2.isEmpty()) {
			return 0;
		} else if (set1.isEmpty()) {
			johnWins = true;
		}
		
		set1.clear();
		set2.clear();
		for (int i = 0; i < doors.length; i++) {
			if (i < trophy) {
				set1.add(doors[i]);				
			} else {
				set2.add(doors[i]);
			}
		}
		if (johnWins) {
			return initialSize[0]*2 -1;
		} else {
			return -1*(initialSize[1]*2);
		}
	}

}
