public class Dubs {	
	private long count(long A) {
		long sum = A / 10L;
		if ((A % 100L) / 10L > A % 10L) {
			sum--;
		}
		return sum;
	}
	
	public long count(long L, long R) {
		return count(R) - count(L-1);
	}
}
