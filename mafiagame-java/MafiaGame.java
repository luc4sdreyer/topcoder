import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class MafiaGame {

	public double probabilityToLose(int N, int[] decisions) {
		int[] scores = new int[501];
		boolean[] vunerable = new boolean[501];
		Arrays.fill(vunerable, true);
		
		int randomPart = 0;
		HashSet<Integer> visited = new HashSet<>();
		while (true) {
			int numVotes = 0;
			Arrays.fill(scores, 0);
			for (int i = 0; i < decisions.length; i++) {
				if (vunerable[i]) {
					scores[decisions[i]]++;
					numVotes++;
				}				
			}
			
			while (numVotes < decisions.length) {
				int min = Integer.MAX_VALUE;
				for (int i = 0; i < decisions.length; i++) {
					if (vunerable[i]) {
						min = Math.min(min, scores[i]);
					}
				}
				for (int i = 0; i < decisions.length; i++) {
					if (scores[i] == min && vunerable[i]) {
						scores[i]++;
						numVotes++;
					}
				}
				if (numVotes < decisions.length && randomPart == 0) {
					min = Integer.MAX_VALUE;
					for (int i = 0; i < decisions.length; i++) {
						if (vunerable[i]) {
							min = Math.min(min, scores[i]);
						}
					}
					for (int i = 0; i < decisions.length; i++) {
						if (scores[i] == min && vunerable[i]) {
							randomPart++;
						}
					}
				}
			}
			while (numVotes < N) {
				int min = Integer.MAX_VALUE;
				for (int i = 0; i < N; i++) {
					if (vunerable[i]) {
						min = Math.min(min, scores[i]);
					}
				}
				for (int i = 0; i < N; i++) {
					if (scores[i] == min && vunerable[i]) {
						scores[i]++;
						numVotes++;
					}
				}
				if (numVotes < N && randomPart == 0) {
					min = Integer.MAX_VALUE;
					for (int i = 0; i < N; i++) {
						if (vunerable[i]) {
							min = Math.min(min, scores[i]);
						}
					}
					for (int i = 0; i < N; i++) {
						if (scores[i] == min && vunerable[i]) {
							randomPart++;
						}
					}
				}
			}
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < decisions.length; i++) {
				max = Math.max(max, scores[i]);
			}
			ArrayList<Integer> top = new ArrayList<>();
			Arrays.fill(vunerable, false);
			for (int i = 0; i < N; i++) {
				if (scores[i] == max) {
					top.add(i);
					vunerable[i] = true;
				}
			}
			Collections.sort(top);
			int[] t = new int[top.size()];
			for (int i = 0; i < t.length; i++) {
				t[i] = top.get(i);				
			}
			if (top.size() == 1) {
				if (randomPart != 0) {
					return 1.0/randomPart;
				} else {
					return 1.0;
				}
			}
			if (visited.contains(Arrays.hashCode(t))) {
				break;
			}
			visited.add(Arrays.hashCode(t));
		}
		return 0.0;
	}

}
