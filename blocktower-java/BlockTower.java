
public class BlockTower {

	public int getTallest(int[] blockHeights) {
		int sum = 0;
		String best = "";
		for (int i = -1; i < blockHeights.length; i++) {
			String temp = "";
			int tempSum = 0;
			for (int j = 0; j < blockHeights.length; j++) {
				if (i == -1 && blockHeights[j] % 2 == 1) {
					tempSum += blockHeights[j];
					temp += blockHeights[j] + ", ";
				} else if (j <= i && blockHeights[j] % 2 == 0) {
					tempSum += blockHeights[j];
					temp += blockHeights[j] + ", ";
				} else if (j > i && blockHeights[j] % 2 == 1) {
					tempSum += blockHeights[j];
					temp += blockHeights[j] + ", ";
				}
			}
			if (tempSum > sum) {
				sum = tempSum;
				best = temp;
			}
		}
		//System.out.println(best);
		return sum;
	}

}
