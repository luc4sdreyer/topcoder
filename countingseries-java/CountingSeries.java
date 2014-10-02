public class CountingSeries {

	public long countThem(long a, long b, long c, long d, long upperBound) {
		long s1 = (upperBound-a)/b;
		if (a <= upperBound) {
			s1++;
		}
		long s2 = 0; //(long) (Math.log(upperBound/c)/Math.log(d))+1;
		long p = c;
		while (p <= upperBound) {
			// into a+b*x then it doesn't count
			if (!(p % a == 0 || (p > a && (p-a)%b == 0))) {
				s2++;
			}			
			p = p * d;
			if (d == 1) {
				break;
			}
		}
		return s1+s2;
	}

}
