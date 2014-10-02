import java.util.HashMap;

public class ContestWinner {

	public int getWinner(int[] events) {
		HashMap<Integer, Integer> m = new HashMap<Integer, Integer>();
		for (int i = 0; i < events.length; i++) {
			m.put(events[i], m.get(events[i]) == null ? 1 : m.get(events[i])+1);
		}
		int maxNum = 0;
		for (int i : m.keySet()) {
			maxNum = Math.max(maxNum, m.get(i));
		}
		m = new HashMap<Integer, Integer>();
		for (int i = 0; i < events.length; i++) {
			m.put(events[i], m.get(events[i]) == null ? 1 : m.get(events[i])+1);
			if (m.get(events[i]) == maxNum) {
				return events[i];
			}
		}
		return 0;
	}

}
