import java.util.ArrayList;
import java.util.Collections;

public class TwoWaysSorting {

	public String sortingMethod(String[] stringList) {
		ArrayList<String> s = new ArrayList<String>();
		ArrayList<String> s2 = new ArrayList<String>();
		ArrayList<Integer> i1 = new ArrayList<Integer>();
		ArrayList<Integer> i2 = new ArrayList<Integer>();
		for (int i = 0; i < stringList.length; i++) {
			s.add(stringList[i]);
			s2.add(stringList[i]);
			i1.add(stringList[i].length());
			i2.add(stringList[i].length());
		}
		Collections.sort(s);
		Collections.sort(i1);
		boolean lex = true;
		boolean len = true;
		for (int i = 0; i < stringList.length; i++) {
			if (!s.get(i).equals(s2.get(i))) {
				lex = false;
			}
			if (i1.get(i) != i2.get(i)) {
				len = false;
			}
		}
		if (lex && len) {
			return "both";
		} else if (lex) {
			return "lexicographically";
		} else if (len) {
			return "lengths";
		} else {
			return "none";
		}
	}

}
