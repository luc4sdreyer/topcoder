public class PowerOfThreeEasy {

	public String ableToGet(int x, int y) {
		if (dfs(x, y, 0, 0, 1) == 1) {
			return "Possible";
		} else {
			return "Impossible";
		}
	}

	private int dfs(long targetX, long targetY, long x, long y, long step) {
		if (x > targetX || y > targetY) {
			return 0;
		}
		if (x == targetX && y == targetY) {
			return 1;
		}
		int max = 0;
		max = Math.max(max, dfs(targetX, targetY, x+step, y, step*3));
		if (max == 1) {
			return max;
		}
		max = Math.max(max, dfs(targetX, targetY, x, y+step, step*3));
		return max;
	}

}
