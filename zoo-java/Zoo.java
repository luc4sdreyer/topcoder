import java.util.HashMap;

public class Zoo {

	public long theCount(int[] answers) {
		HashMap<Integer, Integer> num = new HashMap<>();
		for (int i = 0; i < answers.length; i++) {
			if (!num.containsKey(answers[i])) {
				num.put(answers[i], 0);
			}
			num.put(answers[i], num.get(answers[i]) + 1);
		}
		
		int freq = 2;
		int all = 0;
		for (int i = 0; i < answers.length; i++) {
			int f = 0;
			if (num.containsKey(i)) {
				f = num.get(i);
			} else {
				return 0;
			}
			if (f > freq) {
				return 0;
			}
			freq = f;
			all += f;
			if (all == answers.length) {
				break;
			}
		}

		int ans = 1;
		for (int i = 0; i < answers.length; i++) {
			int f = 0;
			if (num.containsKey(i)) {
				f = num.get(i);
				if (num.containsKey(i+1)) {
					ans *= f;
				}
			}
		}
		
		return ans*2;
	}

}
