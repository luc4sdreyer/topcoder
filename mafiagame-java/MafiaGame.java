import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class MafiaGame {

	public double probabilityToLose(int N, int[] decisions) {
		int[] scores = new int[501];
		boolean[] vunerable = new boolean[501];
		boolean[] voted = new boolean[501];
		
		HashSet<int[]> visited = new HashSet<>();
		while (true) {
			int numVotes = 0;
			Arrays.fill(voted, false);
			Arrays.fill(scores, 0);
			for (int i = 0; i < decisions.length; i++) {
				if (vunerable[decisions[i]]) {
					scores[decisions[i]]++;
					voted[decisions[i]] = true;
					numVotes++;
				}				
			}
			
			boolean allVoted = false;
			while (!allVoted) {
				allVoted = true;
				for (int i = 0; i < decisions.length; i++) {
					if (!voted[decisions[i]]) {
						allVoted = false;
					}
				}
				if (allVoted) {
					break;
				}
				int min = Integer.MAX_VALUE;
				for (int i = 0; i < scores.length; i++) {
					min = Math.min(min, scores[decisions[i]]);
				}
				for (int i = 0; i < decisions.length; i++) {
					if (scores[decisions[i]] == min) {
						for (int j = 0; j < voted.length; j++) {
							if (!voted[decisions[i]]) {
								scores[decisions[i]]++;
								voted[decisions[i]] = true;
								numVotes++;
							}
						}
					}
				}
			}
			while (numVotes < N) {				
				int min = Integer.MAX_VALUE;
				for (int i = 0; i < scores.length; i++) {
					min = Math.min(min, scores[decisions[i]]);
				}
				for (int i = 0; i < decisions.length; i++) {
					if (scores[decisions[i]] == min) {
						scores[decisions[i]]++;
						voted[decisions[i]] = true;
						numVotes++;
					}
				}
			}
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < scores.length; i++) {
				max = Math.max(max, scores[decisions[i]]);
			}
			ArrayList<Integer> top = new ArrayList<>();
			for (int i = 0; i < scores.length; i++) {
				if (scores[decisions[i]] == max) {
					top.add(decisions[i]);
				}
			}
			Collections.sort(top);
			int[] t = new int[top.size()];
			for (int i = 0; i < t.length; i++) {
				t[i] = top.get(i);
			}
			if (top.size() == 1) {
				return 1.0;
			}
			if (visited.contains(t)) {
				break;
			}
			visited.add(t);
		}
		return 0.0;
	}

}
