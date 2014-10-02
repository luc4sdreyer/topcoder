import java.util.HashMap;

public class KingdomAndDucks {

	public int minDucks(int[] duckTypes) {
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
		int max = 0;
		for (int i = 0; i < duckTypes.length; i++) {
			int c = m.get(duckTypes[i]) == null ? 1 : m.get(duckTypes[i])+1;
			m.put(duckTypes[i], c);
			if (c > max) {
				max = c;
			}
		}
		return max*m.size();
	}

}
