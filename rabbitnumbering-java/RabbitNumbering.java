import java.util.Arrays;

public class RabbitNumbering {

	public int theCount(int[] maxNumber) {
		Arrays.sort(maxNumber);
		long max = 1;
		long mod = 1000000007;
		for (int i = 0; i < maxNumber.length; i++) {
			max = (max * (maxNumber[i] - (i))) % mod;
		}
		return (int) max;
	}

}
