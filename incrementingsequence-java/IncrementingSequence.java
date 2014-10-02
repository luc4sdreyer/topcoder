import java.util.ArrayList;

public class IncrementingSequence {

	public String canItBeDone(int k, int[] A) {
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < A.length; i++) {
			nums.add(A[i]);
		}
		for (int n = 1; n <= A.length; n++) {
			int minIdx = -1;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < nums.size(); i++) {
				if (((n % k) == (nums.get(i) % k)) && nums.get(i) <= n && nums.get(i) < min) {
					min = nums.get(i);
					minIdx = i;
				}
			}
			if (minIdx == -1) {
				return "IMPOSSIBLE";
			}
			nums.remove(minIdx);
		}
		if (nums.size() == 0) {
			return "POSSIBLE";
		} else {
			return "IMPOSSIBLE";
		}
	}

}
