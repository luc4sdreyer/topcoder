import java.util.HashMap;

public class PilingRectsDiv2 {
	public int getmax(int[] X, int[] Y, int limit) {
		int max = 0;
		for (int w = 1; w <= 200; w++) {
			for (int h = 1; h <= 200; h++) {
				if (w*h >= limit) {
					int num = 0;
					for (int i = 0; i < X.length; i++) {
						if ((X[i] >= w && Y[i] >= h) || (X[i] >= h && Y[i] >= w)) {
							num++;
						}
					}
					max = Math.max(max, num);
				}
			}
		}
		if (max == 0) {
			return -1;
		}
		return max;
	}
}
