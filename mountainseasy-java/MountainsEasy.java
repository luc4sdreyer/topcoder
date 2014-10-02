public class MountainsEasy {

	public int countPlacements(String[] picture, int N) {
		StringBuilder sb = new StringBuilder();
		int W = 6;
		int H = 3;
		char[][] map = new char[H][W];
		//int[][] X = {{1,1},{2,2},{4,1}};
		int[][] X = {{2,2}};
		for (int i = 0; i < X.length; i++) {
			for (int x = 0; x < W; x++) {				
				for (int y = 0; y <= X[i][1] - Math.abs(x - X[i][0]); y++) {
					map[y][x] = 'X';
				}
			}
		}
		for (int i = 1; i <= map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[map.length-i][j] == '\0') {
					sb.append('.');
				} else {
					sb.append(map[map.length-i][j]);
				}
			}
			sb.append('\n');
		}
		System.out.println(sb.toString());
		return 0;
	}

}
