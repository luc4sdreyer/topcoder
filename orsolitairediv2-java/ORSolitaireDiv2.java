import java.util.ArrayList;

public class ORSolitaireDiv2 {

	public int getMinimum(int[] numbers, int goal) {
		ArrayList<Integer> subset = new ArrayList<>();
		for (int j = 0; j < numbers.length; j++) {
			boolean valid = true;
			for (int i = 0; i < 32; i++) {
				if (isBitSet(numbers[j], i) && !isBitSet(goal, i)) {
					valid = false;
					break;
				}
			}
			if (valid) {
				subset.add(numbers[j]);
			}
		}
		
		int min = subset.size();
		for (int i = 0; i < 32; i++) {
			if (isBitSet(goal, i)) {
				int t = 0;
				for (int j = 0; j < subset.size(); j++) {
					if (isBitSet(subset.get(j), i)) {
						t++;
					}
				}
				min = Math.min(min, t);
			}
		}
		return min;
	}
	
	public boolean isBitSet(int i, int n) {
		return (((i >> n) & 1) != 0) ? true : false; 
	}	

}
