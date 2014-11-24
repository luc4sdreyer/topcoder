public class HyperKnight {

	public long countCells(int a, int b_, int R, int C, int k) {
		long s = Math.min(a, b_);
		long b = Math.max(a, b_);
		if (k == 2) {
			return 4*s*s;
		} else if (k == 3) {
			return 8*s*(b-s);
		} else if (k == 4) {
			return 2*(2*(b-s)*(b-s) + s*(C-2*b) + s*(R-2*b));
		} else if (k == 6) {
			return 2*(b-s)*(R - 2*b + C - 2*b);
		} else if (k == 8) {
			return (C-2*b)*(R-2*b);
		} 
		return 0;
	}

}
