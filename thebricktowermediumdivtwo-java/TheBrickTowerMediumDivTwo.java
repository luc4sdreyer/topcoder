public class TheBrickTowerMediumDivTwo {

	public int[] find(int[] heights) {
		int[] positions = new int[heights.length];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = i;
		}
		int min = dist(positions, heights);
		int[] best = positions.clone();
		while (next_permutation(positions)) {
			int t = dist(positions, heights);
			if (t < min) {
				min = t;
				best = positions.clone();				
			}
		}
		return best;
	}
	
	public int dist(int[] positions, int[] heights) {
		int dist = 0;
		for (int i = 1; i < heights.length; i++) {
			dist += Math.max(heights[positions[i-1]], heights[positions[i]]);
		}
		return dist;
	}
	
	public boolean next_permutation(int str[]) {
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
}
