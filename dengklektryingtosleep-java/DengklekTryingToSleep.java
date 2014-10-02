import java.util.ArrayList;
import java.util.Collections;

public class DengklekTryingToSleep {

	public int minDucks(int[] ducks) {
		if (ducks.length > 1) {
			ArrayList<Integer> d = new ArrayList<Integer>();
			for (int i = 0; i < ducks.length; i++) {
				d.add(ducks[i]);
			}
			Collections.sort(d);
			int range = d.get(d.size()-1) - d.get(0)+1;
			return range-ducks.length;
		} else {
			return 0;
		}
	}

}
