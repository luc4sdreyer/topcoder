import java.util.Arrays;

public class Stamp {

	public int getMinimumCost(String desiredColor, int stampCost, int pushCost) {
		char[] colors = {'R', 'G', 'B'};
		int minCost = Integer.MAX_VALUE;
		for (int stampLength = 1; stampLength <= desiredColor.length(); stampLength++) {
			
			char[] colorStrip = desiredColor.toCharArray();
			int[] cost = new int[colorStrip.length];
			Arrays.fill(cost, 10000000);
			for (int i = 0; i < colorStrip.length; i++) {
				boolean canPaint = false;
				for (int j = 0; j < colors.length; j++) {
					boolean validColor = true;
					for (int k = 0; k < stampLength; k++) {
						if (i + k >= colorStrip.length) {
							validColor = false;
							break;
						}
						if (i + k < colorStrip.length && colorStrip[i + k] != colors[j] && colorStrip[i + k] != '*' ) {
							validColor = false;
							break;
						}
					}
					if (validColor) {
						canPaint = true;
						break;
					}
				}
				if (canPaint) {
					for (int k = 0; k < stampLength; k++) {
						if (i == 0) {
							cost[i + k] = pushCost;
						} else if (cost[i - 1] + pushCost < cost[i + k]) {
							cost[i + k] = cost[i - 1] + pushCost;
						}
					}
				}
			}
			
			if (cost[desiredColor.length() - 1] != Integer.MAX_VALUE 
					&& cost[desiredColor.length() - 1] < minCost 
					&& cost[desiredColor.length() - 1] + stampCost*stampLength < minCost) {
				minCost = cost[desiredColor.length() - 1] + stampCost*stampLength;
			}
		}
		return minCost;
	}

}
