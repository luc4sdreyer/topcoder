public class ConundrumReloaded {

	public int minimumLiars(String answers) {
		int N = 1 << answers.length();
		int minLiars = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			boolean inconsistent = false;
			int numLiars = 0;
			for (int j = 0; j < answers.length(); j++) {
				boolean isLiar = ((i & (1 << j)) == 0) ? true : false;
				if (isLiar) {
					numLiars++;
				}
				char next = ((i & (1 << ((j + 1) % answers.length()))) == 0) ? 'L' : 'H';
				if (isLiar) {
					next = ((i & (1 << ((j + 1) % answers.length()))) == 0) ? 'H' : 'L';
				}
				if (answers.charAt(j) != '?') {
					if (answers.charAt(j) != next) {
						inconsistent = true;
						break;
					}
				}
			}
			if (!inconsistent) {
				minLiars = Math.min(minLiars, numLiars);
			}
			
		}
		if (minLiars <= answers.length()) {
			return minLiars;
		} else {
			return -1;
		}
	}

}
