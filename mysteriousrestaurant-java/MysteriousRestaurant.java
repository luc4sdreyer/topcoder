public class MysteriousRestaurant {

	public int maxDays(String[] prices, int budget) {
		int[][] pCopy = new int[prices.length][prices[0].length()];		
		for (int i = 0; i < pCopy.length; i++) {
			for (int j = 0; j < pCopy[0].length; j++) {
				if (prices[i].charAt(j) >= 'a') {
					pCopy[i][j] = prices[i].charAt(j) - 'a'+36;
				} else if (prices[i].charAt(j) >= 'A') {
					pCopy[i][j] = prices[i].charAt(j) - 'A'+10;
				} else {
					pCopy[i][j] = prices[i].charAt(j) - '0';
				}
			}
		}
		
		int max = 0;
		for (int day = 0; day < pCopy.length; day++) {
			int[][] p = new int[pCopy.length][pCopy[0].length];
			for (int i = 0; i < p.length; i++) {
				p[i] = pCopy[i].clone();
			}
			
			for (int i = 7; i <= day; i++) {
				for (int j = 0; j < p[0].length; j++) {
					p[i%7][j] += p[i][j];
					p[i][j] = 0;
				}
			}
			
			int money = budget;
			for (int i = 0; i <= day; i++) {
				int min = p[i][0];
				for (int j = 0; j < p[0].length; j++) {
					min = Math.min(min, p[i][j]);
				}
				if (money - min >= 0) {
					max = Math.max(max, i+1);
					money -= min;
				} else {
					break;
				}
			}
			
			
		}
		return max;
	}

}
