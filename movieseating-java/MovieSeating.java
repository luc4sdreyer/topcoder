public class MovieSeating {

	public long getSeatings(int numFriends, String[] hall) {
		long ret = 0;
		for (int i = 0; i < hall.length; i++) {
			long ne = 0;
			for (int j = 0; j < hall[0].length(); j++) {
				if (hall[i].charAt(j) == '.') {
					ne++;
				}
			}
			if (ne >= numFriends) {
				ret += partialFactorial(ne, numFriends);
			}			 
		}

		for (int j = 0; j < hall[0].length(); j++) {
			long ne = 0;
			for (int i = 0; i < hall.length; i++) {
				if (hall[i].charAt(j) == '.') {
					ne++;
				}
			}
			if (ne >= numFriends) {
				ret += partialFactorial(ne, numFriends);
			}			 
		}
		
		return numFriends > 1 ? ret : ret / 2;
	}
	
	public long partialFactorial(long ne, long nf) {
		long ret = 1;
		for (long i = ne - nf + 1; i <= ne; i++) {
			ret *= i;
		}
		return ret;
	}

}
