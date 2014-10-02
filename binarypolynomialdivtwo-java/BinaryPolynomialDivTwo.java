public class BinaryPolynomialDivTwo {

	public int countRoots(int[] a) {
		int f0 = a[0];
		int f1 = 0;
		for (int i = 0; i < a.length; i++) {
			f1 += a[i];
		}
		f1 %= 2;
		int num = 0;		
		if (f0 == 0) {
			num++;
		}
		if (f1 == 0) {
			num++;
		}
		return num;
	}

}
