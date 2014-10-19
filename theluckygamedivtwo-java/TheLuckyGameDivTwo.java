import java.util.Arrays;

public class TheLuckyGameDivTwo {

	public int find(int a, int b, int jLen, int bLen) {
		short[][] grid = new short[4750][4750];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] =  -1;
			}			
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
							grid[len][k] = (short) (grid[len-1][k] + 1);
						} else {
							grid[len][k] = (short) Math.max(grid[len-1][k], grid[len-1][k+1]);
						}
					}
				}
			}
		}
		
		int max = 0;
		for (int k = a; k <= b - jLen+1; k++) {
			if (grid[jLen][k] != -1) {
				int bTemp = 1000000;
				for (int m = k; m <= k + bLen; m++) {
					if (grid[bLen][m] != -1) {
						bTemp = Math.min(bTemp, grid[bLen][m]);
					}
				}
				int best = Math.max(0, bTemp);
				max = Math.max(max, best);
			}
		}
		
//		String str = "";
//		for (int i = 0; i < 30; i++) {
//			
//			for (int j = 0; j < 30; j++) {
//				str += grid[i][j] + "\t";
//			}
//			str += '\n';
//		}
//		System.out.println(str);
		
		return max;
	}

	private boolean checkNum(int k) {
		String num = String.valueOf(k);
		boolean valid = true;
		for (int i = 0; i < num.length(); i++) {
			if (!(num.charAt(i) == '4' || num.charAt(i) == '7')) {
				valid = false;
				break;
			}
		}
		return valid;
	}

}
