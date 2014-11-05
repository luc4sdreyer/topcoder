import java.util.Arrays;

public class PermutationSignature {

	public int[] reconstruct(String signature) {
		int[] ret = new int[signature.length()+1];
		int max = 1;
		int min = 1;
		ret[0] = 1;
		for (int i = 0; i < ret.length; i++) {
			if (i == 0 || signature.charAt(i-1) == 'I') {
				if (i < signature.length() && signature.charAt(i) == 'D') {
					int down = 0;
					while (i + down < signature.length() && signature.charAt(i + down) == 'D') {
						down++;
					}
					ret[i] = max + 1 + down;
				} else if (i != 0) {
					ret[i] = max + 1;
				}
			} else {					
				ret[i] = ret[i-1]-1;
			}
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			for (int j = 0; j <= i; j++) {
				max = Math.max(max, ret[j]);
				min = Math.min(min, ret[j]);
			}
		}
		max = Integer.MIN_VALUE;
		min = Integer.MAX_VALUE;
		for (int j = 0; j < ret.length; j++) {
			max = Math.max(max, ret[j]);
			min = Math.min(min, ret[j]);
		}
		if (min > 1) {
			for (int i = 0; i < ret.length; i++) {
				ret[i] -= (min-1);
			}
		}
		
		return ret;
	}

}
