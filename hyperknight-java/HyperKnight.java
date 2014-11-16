public class HyperKnight {

	public long countCells(int a, int b, int numRows, int numColumns, int k) {
		long S = Math.min(a, b);
		long L = Math.max(a, b);
		long ret = 0;
		if (k == 0 || k == 1 || k == 5 || k == 7) {
			return 0;
		} else if (k == 2) {
			ret = 4 * (S*S); 
		} else if (k == 3) {
			ret = 8 * (L-S)*S; 
		} else if (k == 4) {
			ret = 4*(L-S)*(L-S) + (2*numRows - 4*L + 2*numColumns - 4*L)*S;
		} else if (k == 6) {
			ret = (L-S) * (2*numRows - 4*L + 2*numColumns - 4*L);
		} else if (k == 8) {
			ret = (numRows - 2*L) * (numColumns - 2*L);
		}
		return ret;
	}

}
