import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BubbleSortWithReversals {
	
	static class TupleComparator implements Comparator<int[]> {

		@Override
		public int compare(int[] arg0, int[] arg1) {
			if (arg0[0] < arg1[0]) return -1;
			if (arg0[0] > arg1[0]) return 1;
			return 0;
		}
		
	}

	public int getMinSwaps(int[] A, int K) {
		ArrayList<int[]> numbers = new ArrayList<int[]>();
		for (int i = 0; i < A.length; i++) {
			int[] temp = {A[i], i};
			numbers.add(temp);
		}
		TupleComparator comp = new TupleComparator();
		Collections.sort(numbers, comp);
		int[] offsets = new int[A.length];
		for (int i = 0; i < numbers.size(); i++) {
			offsets[numbers.get(i)[1]] = i - numbers.get(i)[1];
		}
		//System.out.println(Arrays.toString(offsets));
		
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < A.length; i++) {
			for (int len = 2; len < offsets.length; len++) {
				if (i + len < A.length) {
					int[] diffs = new int[len];
					if (len % 2 == 0) {
						
					}
					for (int j = 0; j < diffs.length; j++) {
						diffs[j] 
					}
					for (int j = i; j < i+len; j++) {
						diff += offsets[j]
					}
				}
			}
		}
		return 0;
	}

}
