import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SRMRoomAssignmentPhase {

	public int countCompetitors(int[] ratings, int K) {
		int mine = ratings[0];
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < ratings.length; i++) {
			list.add(ratings[i]);
		}
		Collections.sort(list);
		Collections.reverse(list);
		int myIdx = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == mine) {
				myIdx = i;
				break;
			}
		}
		if ((myIdx+1) % K == 0) {
			return ((myIdx+1) / K) - 1;
		} else {
			return myIdx / K;
		}
	}

}
