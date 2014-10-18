import java.util.Arrays;

public class TheLuckyGameDivTwo {

	public int find(int a, int b, int jLen, int bLen) {
		int[][] grid = new int[4750][4750];
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], -1);			
		}
		
		for (int len = 1; len <= b-a+1; len++) {						
			for (int k = a; k <= b; k++) {
				if (len == 1) {
					if (checkNum(k)) {
						grid[len][k] = 1;
					} else {
						grid[len][k] = 0;
					}
				} else {
					if (grid[len-1][k] != -1 && grid[len-1][k+1] != -1) {
						if (checkNum(k+len-1)) {
							grid[len][k] = grid[len-1][k] + 1;
						} else {
							grid[len][k] = Math.max(grid[len-1][k], grid[len-1][k+1]);
						}
					}
				}
			}
		}
		
		int max = 0;
		for (int k = a; k <= b - jLen+1; k++) {
			if (grid[jLen][k] != -1) {
				int jTemp = grid[jLen][k];
				int bTemp = 0;
				for (int m = k; m <= b - bLen+1; m++) {
					if (grid[bLen][m] != -1) {
						bTemp = Math.max(bTemp, grid[bLen][m]);
					}
				}
				int best = Math.max(0, jTemp - bTemp);
				max = Math.max(max, best);
			}
		}
		
		String str = "";
		for (int i = 0; i < 10; i++) {
			
			for (int j = 4; j < 10; j++) {
				str += grid[i][j] + "\t";
			}
			str += '\n';
		}
		System.out.println(str);
		
		return max;
	}

	private boolean checkNum(int k) {
		String num = String.valueOf(k);
		boolean valid = false;
		for (int i = 0; i < num.length(); i++) {
			if (num.charAt(i) == '4' || num.charAt(i) == '7') {
				valid = true;
				break;
			}
		}
		return valid;
	}

}
