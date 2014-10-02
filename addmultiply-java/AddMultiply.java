import java.util.Arrays;

public class AddMultiply {
	public int[] makeExpression(int y) {
		for (int i = -1000; i <= 1000; i++) {
			for (int j = -1000; j <= 1000; j++) {
				if (i != 0 && i != 1 && j != 0 && j != 1) {
					if (Math.abs(i * j - y) <= 1000 && (y - i * j != 0) && (y - i * j != 1)) {
						int[] a = {i , j, y - i * j};
						return a;
					}
				}
			}
		}
		return null;
	}

}
