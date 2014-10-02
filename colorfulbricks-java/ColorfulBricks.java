import java.util.HashMap;
import java.util.HashSet;

public class ColorfulBricks {

	public int countLayouts(String bricks) {
		HashMap<Character, Integer> s = new HashMap<Character, Integer>();
		for (int i = 0; i < bricks.length(); i++) {
			int v = s.get(bricks.charAt(i)) != null ? s.get(bricks.charAt(i)) : 0;
			s.put(bricks.charAt(i), v+1);
		}
		if (s.size() == 1) {
			return 1;
		} else if (s.size() == 2) {
//			int count = 0;
//			for (Character key : s.keySet()) {
//				if (s.get(key) > 1) {
//					count++;
//				}
//			}
//			if (count == s.size()) {
//				return 2;
//			} else {
//				return 0;
//			}
			return 2;
		} else {
			return 0;
		}
	}

}
