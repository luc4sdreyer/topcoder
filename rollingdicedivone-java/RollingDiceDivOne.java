public class RollingDiceDivOne {

	public long mostLikely(int[] dice) {
		if (dice.length == 1) {
			return 1;
		}
		
		long smallA = 1;
		long smallB = 1;
		long largeA = 1;
		long largeB = 1;
		long nextA = 0;
		long nextB = 0;
		for (int i = 1; i < dice.length; i++) {
			if (i == 1) {
				smallB = dice[i];
				largeB = dice[i-1];
				if (smallB > largeB) {
					long temp = smallB;
					smallB = largeB;
					largeB = temp;
				}
			} else {
			
				smallA = nextA;
				smallB = nextB;
				largeA = 1;
				largeB = dice[i];
				if ((largeB - largeA) < (nextB - nextA)) {
					long temp = smallB;
					smallB = largeB;
					largeB = temp;
					
					temp = smallA;
					smallA = largeA;
					largeA = temp;
				}
			}
			
			nextA = smallA + (largeA-1) + (smallB - smallA +1);
			nextB = smallA + largeB;
//			nextA = smallA + largeA;
//			nextB = smallB + largeB;
			
		}
		return nextA;
	}

}
