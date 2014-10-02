public class SumOfPower {

	public int findSum(int[] array) {
		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = i; j < array.length; j++) {
				for (int k = i; k <= j; k++) {
					sum += array[k];					
				}
			}
		}
		return sum;
	}

}
