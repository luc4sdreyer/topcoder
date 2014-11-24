
import java.util.Arrays;

public class Badgers {

	public int feedMost(int[] hunger, int[] greed, int totalFood) {
		int N = hunger.length;				
		int[][] map = new int[N+1][N];
		for (int n = 1; n < map.length; n++) {
			for (int i = 0; i < map[0].length; i++) {
				if (n == 1) {
					map[n][i] = hunger[i];
				} else {
					map[n][i] += map[n-1][i] + greed[i];
				}
			} 			
		}
		int max = 0;
		for (int n = 1; n < map.length; n++) {
			int best = 0;
			Arrays.sort(map[n]);
			for (int i = 0; i < n; i++) {
				best += map[n][i];
			}
			if (best <= totalFood) {
				max = n;
			}
		}

		return max;
	}

}
