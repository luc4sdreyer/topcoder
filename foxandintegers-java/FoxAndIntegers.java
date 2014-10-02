public class FoxAndIntegers {

	public int[] get(int AminusB, int BminusC, int AplusB, int BplusC) {
		
		int A = (AminusB + AplusB)/2;
		int B = (BminusC + BplusC)/2;
		int C = BplusC - B;
		if ((AminusB == A - B) && (BminusC == B - C) && (AplusB == A + B) && (BplusC == B + C)) {
			int[] t = {A,B,C};
			return t;
		}
		int[] t = new int[0];
		return t;
	}

}
