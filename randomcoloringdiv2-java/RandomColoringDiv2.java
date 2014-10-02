public class RandomColoringDiv2 {

	public int getCount(int maxR, int maxG, int maxB, int startR, int startG, int startB, int d1, int d2) {
		int count = 0;
		for (int R = 0; R < maxR; R++) {
			for (int G = 0; G < maxG; G++) {
				for (int B = 0; B < maxB; B++) {
					int diffR = Math.abs(R - startR);
					int diffG = Math.abs(G - startG);
					int diffB = Math.abs(B - startB);
					if ((diffR >= d1 || diffG >= d1 || diffB >= d1) &&
						(diffR <= d2 && diffG <= d2 && diffB <= d2)) {
						count++;
					}
				}
			}
		}
		return count;
	}

}
