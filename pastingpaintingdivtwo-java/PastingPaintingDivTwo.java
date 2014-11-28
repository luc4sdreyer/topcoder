public class PastingPaintingDivTwo {

	public long countColors(String[] clipboard, int T) {
		char[][] clip = new char[clipboard.length][clipboard[0].length()];
		for (int i = 0; i < clip.length; i++) {
			clip[i] = clipboard[i].toCharArray(); 
		}
		
		int yL = clip.length;
		int xL = clip[0].length;
		int max = Math.max(yL, xL)+1;
		boolean[][] canvas = new boolean[max*2][max*2];
		if (T <= max) {
			for (int i = 0; i < T; i++) {
				for (int y = 0; y < clip.length; y++) {
					for (int x = 0; x < clip[0].length; x++) {
						if (clip[y][x] == 'B') {
							canvas[i+y][i+x] = true;
						}
					}
				}
			}
			long start = 0;
			for (int i = 0; i < canvas.length; i++) {
				for (int j = 0; j < canvas[0].length; j++) {
					if (canvas[j][i]) {
						start++;
					}
				}
			}
			return start;
		} else {
			for (int i = 0; i < max+1; i++) {
				for (int y = 0; y < clip.length; y++) {
					for (int x = 0; x < clip[0].length; x++) {
						if (clip[y][x] == 'B') {
							canvas[i+y][i+x] = true;
						}
					}
				}
			}
			long start = 0;
			long mid = 0;
			long end = 0;
			for (int y = 0; y < yL; y++) {
				for (int x = 0; x < xL; x++) {
					if (canvas[y][x]) {
						start++;
					}
				}
			}
			for (int y = 1; y <= yL; y++) {
				for (int x = 1; x <= xL; x++) {
					if (x == xL || y == yL) {
						if (canvas[y][x]) {
							mid++;
						}
					}
				}
			}
			for (int y = yL-1; y <= 2*yL; y++) {
				for (int x = xL-1; x <= 2*xL; x++) {
					if (x > xL || y > yL) {
						if (canvas[y][x]) {
							end++;
						}
					}
				}
			}
			long total = start + end + mid * (T - (max-2));
			System.out.println(start + "\t" + end + "\t" +mid + "\t");
			return total;
		}
	}

}
