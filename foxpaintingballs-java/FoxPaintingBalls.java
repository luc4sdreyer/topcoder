public class FoxPaintingBalls {

	public long theMax(long R, long G, long B, int N) {
		long sum = N*(N+1)/2;
		long min = Math.min(Math.min(R, G), B);
		long max = Math.max(Math.max(R, G), B);
		if (sum == 1) {
			return R+G+B;
		} else if (sum % 3 == 0) {
			return min/(sum/3);
		} else {
			long upperBound = min/(sum/3);
			long lowerBound = max/(sum/3 + 1);
			System.out.println(upperBound +" "+ lowerBound);
		}
		return 0;
	}

}
