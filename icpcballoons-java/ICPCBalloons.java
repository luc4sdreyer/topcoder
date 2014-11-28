import java.util.ArrayList;
import java.util.Collections;

public class ICPCBalloons {
	public static final int BIG = 1000000;
	public int minRepaintings(int[] balloonCount, String balloonSize_, int[] maxAccepted) {
		char[] balloonSize = balloonSize_.toCharArray();
		int min = BIG;
		int N = 1 << maxAccepted.length;
		ArrayList<Integer> lBalllons = new ArrayList<>();
		ArrayList<Integer> mBalllons = new ArrayList<>();
		for (int i = 0; i < balloonSize.length; i++) {
			if (balloonSize[i]== 'L') {
				lBalllons.add(balloonCount[i]);
			} else {
				mBalllons.add(balloonCount[i]);
			}
		}
		for (int n = 0; n < N; n++) {
			int[] large = new int[maxAccepted.length];
			for (int i = 0; i < maxAccepted.length; i++) {
				if (((1 << i) & n) != 0) {
					large[i] = 1;
				}
			}
			int res = 0;
			for (int i = 0; i < 2; i++) {
				ArrayList<Integer> maxA = new ArrayList<>();
				for (int j = 0; j < maxAccepted.length; j++) {
					if (large[j] == i) {
						maxA.add(maxAccepted[j]);
					}
				}
				if (i == 1) {					
					int t = getMin(lBalllons, maxA);
					if (!maxA.isEmpty()) {
						res += t;
					}
				} else {
					int t = getMin(mBalllons, maxA);
					if (!maxA.isEmpty()) {
						res += t;
					}
				}
			}			
			min = Math.min(min, res);
		}
		if (min >= BIG) {
			return -1;
		}
		return min;
	}
	private int getMin(ArrayList<Integer> balllons_, ArrayList<Integer> maxAccepted) {
		ArrayList<Integer> balllons = new ArrayList<>();
		for (int i = 0; i < balllons_.size(); i++) {
			balllons.add(balllons_.get(i));
		}
		Collections.sort(balllons);
		Collections.reverse(balllons);
		Collections.sort(maxAccepted);
		Collections.reverse(maxAccepted);
		for (int i = 0; i < balllons.size() && i < maxAccepted.size(); i++) {
			int m = Math.min(balllons.get(i), maxAccepted.get(i));
			balllons.set(i, balllons.get(i) - m);
			maxAccepted.set(i, maxAccepted.get(i) - m);
		}
		int remB = 0;
		int remA = 0;
		for (int i = 0; i < balllons.size(); i++) {
			remB += balllons.get(i);
		}
		for (int i = 0; i < maxAccepted.size(); i++) {
			remA += maxAccepted.get(i);
		}
		if (remB >= remA) {
			return remA;
		} else {
			return BIG;
		}
	}

}
