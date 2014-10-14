import java.awt.Rectangle;
import java.util.ArrayList;

public class SortishDiv2 {

	public int ways(int sortedness, int[] seq) {
		ArrayList<Integer> options = new ArrayList<>();
		for (int i = 1; i <= seq.length; i++) {
			boolean found = false;
			for (int j = 0; j < seq.length; j++) {
				if (seq[j] == i) {
					found = true;
				}
			}
			if (!found) {
				options.add(i);
			}
		}
		return dfs(options, sortedness, seq);
	}
	
	private int dfs(ArrayList<Integer> options, int sortedness,int[] seq) {
		if (options.isEmpty()) {
			if (count(seq) == sortedness) {
				return 1;
			} else {
				return 0;
			}
		}
		if (count(seq) > sortedness) {
			return 0;
		}
		int c = 0;
		int idx = 0;
		for (int i = 0; i < seq.length; i++) {
			if (seq[i] == 0) {
				idx = i;
				break;
			}
		}
		for (int i = 0; i < options.size(); i++) {
			@SuppressWarnings("unchecked")
			ArrayList<Integer> newOptions= (ArrayList<Integer>) options.clone();
			
			int[] newS = seq.clone();
			newS[idx] = newOptions.remove((int)i);
			c += dfs(newOptions, sortedness, newS);
		}
		return c;
	}

	public int count(int[] seq) {
		int c = 0;
		for (int i = 0; i < seq.length; i++) {
			for (int j = i+1; j < seq.length; j++) {
				if (seq[i] != 0 && seq[j] != 0 && seq[i]< seq[j]) {
					c++;
				}
			}
		}
		return c;
	}

}
