import java.util.HashMap;

public class EllysNumberGuessing {

	public int getNumber(int[] guesses, int[] answers) {
		HashMap<Long, Integer> map = new HashMap<>();
		for (int i = 0; i < answers.length; i++) {
			long g = guesses[i];
			long a = answers[i];
			long[] p = {g+a, g-a};
			for (int j = 0; j < p.length; j++) {
				if (p[j] >= 1 && p[j] <= 1000000000L) {
					if (!map.containsKey(p[j])) {
						map.put(p[j], 0);
					}
					map.put(p[j], map.get(p[j]) + 1);
				}
			}
		}
		
		int numCand = 0;
		int ret = 0;
		for (long key : map.keySet()) {
			if (map.get(key) == answers.length) {
				numCand++;
				ret = (int) key;
			}
		}
		
		if (numCand == 1) {
			return ret;
		} else if (numCand == 2) {
			return -1;
		} else {
			return -2;
		}
	}

}
