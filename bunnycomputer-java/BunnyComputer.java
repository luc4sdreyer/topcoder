import java.util.ArrayList;

public class BunnyComputer {

	public int getMaximum(int[] preference, int k) {
		int ret = 0;
		for (int i = 0; i <= k; i++) {
			ArrayList<Integer> modGroup = new ArrayList<>();
			for (int j = i; j < preference.length; j++) {
				if ((j - i) % (k+1) == 0) {
					modGroup.add(preference[j]);
				}
			}
			ret += getMax(modGroup);
		}
		return ret;
	}

	private int getMax(ArrayList<Integer> modGroup) {
		int max = 0;
		if (modGroup.size() % 2 == 0) {
			for (int i = 0; i < modGroup.size(); i++) {
				max += modGroup.get(i);
			}
		} else {
			for (int i = 0; i < modGroup.size(); i++) {
				int temp = 0;
				for (int j = 1; j < modGroup.size(); j++) {
					if (j != i && j-1 != i) {
						temp += modGroup.get(j) + modGroup.get(j-1);
						j++;
					}
				}
				max = Math.max(max, temp);
			}
		}
		return max;
	}

}
