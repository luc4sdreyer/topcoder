package dynamicProgramming;
public class DynamicProg2 {
	public static void main(String[] args) {
		// Given a sequence of N numbers - A[1] , A[2] , ..., A[N].
		// Find the length of the longest non-decreasing sequence.
		
		int size = 30000;
		int[] numbers = new int[size];
		int[] longestSeq = new int[numbers.length];
		
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = (int) (Math.random()*size);
		}
		
		
		
		for (int n = 0; n < numbers.length; n++) {
			longestSeq[n] = 1;
			for (int s = 0; s < n; s++) {
				if (numbers[s] <= numbers[n] && longestSeq[s] + 1 > longestSeq[n]) {
					longestSeq[n] = longestSeq[s] + 1;
				}
			}
		}
		
//		System.out.println("i\tn\tlongest");
//		for (int n = 0; n < numbers.length; n++) {
//			System.out.println(n + "\t" + numbers[n] + "\t" + longestSeq[n]);
//		}
		System.out.println(longestSeq[longestSeq.length-1]);
	}
}
