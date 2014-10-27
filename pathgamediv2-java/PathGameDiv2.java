public class PathGameDiv2 {

	public int calc(String[] board) {
		char[][] b = {board[0].toCharArray(), board[1].toCharArray()};
		int c = 1;
		int y = 0;
		if (b[1][0] == '#') {
			c = 0;
		}
		if (b[0][0] == '#') {
			y = 1;
			c = 0;
		}
		for (int i = 0; i < b[0].length-1; i++) {
			if (b[0][i+1] == '.' && b[1][i+1] == '.') {
				if (i >= 0 && i < b[0].length-2) {
					if (!((b[0][i-1+1] == '#' && b[1][i+1+1] == '#') 
						|| (b[1][i-1+1] == '#' && b[0][i+1+1] == '#'))) {
						c++;
						b[y == 0 ? 1 : 0][i+1] = '#';
					} else {
						y = (y + 1)%2;
					}
				} else {
					c++;
					b[y == 0 ? 1 : 0][i+1] = '#';
				}
			}
		}
		return c;
	}
}
