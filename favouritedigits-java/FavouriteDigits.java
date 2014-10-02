public class FavouriteDigits {

	public long findNext(long N, int digit1, int count1, int digit2, int count2) {
		/*int[] record = new int[10];
		
		if (N < count1 + count2) {
			N = (long) Math.pow(10, count1 + count2-1);
		}
		
		for (long j = 0; j <= 1000000000000000L; j++) {
			record = new int[10];
			String sN = Long.toString(N);
			for (int i = 0; i < sN.length(); i++) {
				record[sN.charAt(i)-0x30]++;
			}
			if ((record[digit1] >= count1) && (record[digit2] >= count2)) {
				return N;
			} else {
				N++;
			}
		}
		return -1;*/
		
		while(!check(N,digit1,count1,digit2,count2)) {
			N++;
		}
		return N;
		
	}

	private boolean check(long N, int d1, int c1, int d2, int c2) {
		String t = N+ "";
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) == d1+0x30) {
				c1--;
				continue;
			}
			if (t.charAt(i) == d2+0x30) {
				c2--;
			}
		}
		return (c2<=0 && c1 <=0) ? true  :false;
	}

}
