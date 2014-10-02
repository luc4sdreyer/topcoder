public class TheBrickTowerMediumDivTwo {

	public boolean next_permutation(int str[])	{
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public int[] find(int[] heights) {
		int[] idx = new int[heights.length];
		int[] bestIdx = new int[heights.length];
		int min = 1000000000;
		for (int i = 0; i < idx.length; i++) {
			idx[i] = i;
		}
		boolean notDone = true;
		while (notDone) {
			int cost = 0;
			for (int i = 1; i < heights.length; i++) {
				cost += Math.max(heights[idx[i]], heights[idx[i-1]]);
			}
			if (cost < min) {
				min = cost;
				bestIdx = idx.clone();
			}
			notDone = next_permutation(idx);
		}
		return bestIdx;
	}

}
