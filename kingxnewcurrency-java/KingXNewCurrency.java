import java.util.HashSet;

public class KingXNewCurrency {

	public int howMany(int A, int B, int X) {
		HashSet<Integer> s = new HashSet<Integer>();
		if (A % X == 0 && B % X == 0) {
			return -1;
		} else if (A % X == 0) {
			for (int i = 1; i <= B; i++) {
				if (B % i == 0) {
					s.add(i);
				}
			}
			if (A-X >= 0 && B-X >= 0) {
				for (int i = 1; i <= Math.max(A, B); i++) {
					if ((A-X) % i == 0 && (B-X) % i == 0) {
						s.add(i);
					}
				}
			}
		} else if (B % X == 0) {
			for (int i = 1; i <= A; i++) {
				if (A % i == 0) {
					s.add(i);
				}
			}
			if (A-X >= 0 && B-X >= 0) {
				for (int i = 1; i <= Math.max(A, B); i++) {
					if ((A-X) % i == 0 && (B-X) % i == 0) {
						s.add(i);
					}
				}
			}
		} else {
			s.add(0);
		}
		
		return s.size();
	}
}
