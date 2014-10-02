import java.util.Arrays;
import java.util.HashSet;

public class TheSwapsDivTwo {

	public int find(int[] sequence) {
		HashSet<Integer> s = new HashSet<Integer>();
		for (int i = 0; i < sequence.length; i++) {
			for (int j = i+1; j < sequence.length; j++) {
				int[] newSequence = new int[sequence.length];
				for (int k = 0; k < newSequence.length; k++) {
					newSequence[k] = sequence[k];
				}
				int temp = newSequence[i];
				newSequence[i] = newSequence[j]; 
				newSequence[j] = temp;
				s.add(Arrays.hashCode(newSequence));
			}			
		}		
		return s.size();
	}

}
