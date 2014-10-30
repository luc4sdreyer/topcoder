public class PerfectSequences {

	public String fixIt(int[] seq) {
		long[] s = new long[seq.length];
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s.length; j++) {
				s[j] = seq[j];
			}
			long original = s[i];
			s[i] = 0;
			if (s[i] != original && sum(s) == product(s)) {
				return "Yes";
			}
			s[i] = 1;
			if (s[i] != original && sum(s) == product(s)) {
				return "Yes";
			}
			
			long high = 1000000000;
			long low = 2;
			s[i] = -1;
			while (s[i] != (high + low)/2) {
				s[i] = (high + low)/2;
				long sum = sum(s);
				long product = product(s);
				if (s[i] != original && sum == product) {
					return "Yes";
				} else if (sum < product) {
					high = s[i];
				} else {
					low = s[i];
				}
			}
		}
		return "No";
	}
	
	public long sum(long[] list) {
		long sum = 0;
		for (int i = 0; i < list.length; i++) {
			sum += list[i];
		}
		return sum;
	}
	
	public long product(long[] list) {
		long product = 1;
		for (int i = 0; i < list.length; i++) {
			product *= list[i];
		}
		return product;
	}
}
