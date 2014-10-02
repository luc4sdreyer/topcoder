import java.util.Arrays;

public class TomekPhone {

	public int minKeystrokes(int[] frequencies, int[] keySizes) {
		Arrays.sort(frequencies);
		int f = frequencies.length -1;
		int keystrokes = 0;
		for (int height = 1; height <= 50 && f >= 0; height++) {
			for (int i = 0; i < keySizes.length && f >= 0; i++) {
				if (height <= keySizes[i]) {
					keystrokes += height * frequencies[f--];
				}
			}
		}
		if (f == -1) {
			return keystrokes;
		} else {
			return -1;
		}
	}

}
