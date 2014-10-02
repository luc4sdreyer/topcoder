import java.util.Arrays;

public class RollingDiceDivTwo {

	public int minimumFaces(String[] rolls) {
		int[] min = new int[rolls[0].length()];
		Arrays.fill(min, 0);
		for (int i = 0; i < rolls.length; i++) {
			char[] roll = rolls[i].toCharArray();
			Arrays.sort(roll);
			for (int j = 0; j < roll.length; j++) {
				min[j] = Math.max(min[j], roll[j]-'0');
			}
		}
		int sum = 0;
		for (int i = 0; i < min.length; i++) {
			sum += min[i];
		}
		return sum;
	}

}
