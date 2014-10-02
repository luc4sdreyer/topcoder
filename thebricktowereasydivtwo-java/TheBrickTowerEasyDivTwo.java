import java.util.HashSet;

public class TheBrickTowerEasyDivTwo {

	public int find(int redCount, int redHeight, int blueCount, int blueHeight) {
		HashSet<Integer> s = new HashSet<Integer>();
		
		int h = 0;
		for (int i = 0; i < 2*Math.min(redCount, blueCount); i++) {
			if (i % 2 == 0) {
				h += redHeight;
			} else {
				h += blueHeight;
			}
			s.add(h);
		}
		if (redCount >= blueCount + 1) {
			s.add(h + redHeight);
		} else if (blueCount >= redCount + 1) {
			s.add(h + blueHeight);
		}
		
		h = 0;
		for (int i = 0; i < 2*Math.min(redCount, blueCount); i++) {
			if (i % 2 == 1) {
				h += redHeight;
			} else {
				h += blueHeight;
			}
			s.add(h);
		}
		if (redCount >= blueCount + 1) {
			s.add(h + redHeight);
		} else if (blueCount >= redCount + 1) {
			s.add(h + blueHeight);
		}
		return s.size();
	}

}
