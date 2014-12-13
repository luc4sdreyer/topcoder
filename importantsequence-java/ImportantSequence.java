public class ImportantSequence {

	public int getCount(int[] B, String operators) {
		char[] ops = operators.toCharArray();
		long[] res0 = getRes(0, ops, B);
		long[] res1 = getRes(1, ops, B);
		long[] intercept = new long[B.length];
		long[] offset = res1;
		for (int i = 0; i < B.length; i++) {
			intercept[i] = res1[i+1] - res0[i+1];			
		}
		boolean inf = true;
		long a = Long.MAX_VALUE;
		long b = Long.MAX_VALUE;
		for (int i = 0; i < intercept.length; i++) {
			if (intercept[i] < 0) {
				b = Math.min(b, offset[i+1]);
				inf = false;
			}
			a = Math.min(a, res0[i+1]);
		}
		long min = b;
		if (a < 0) {
			min += a;
		}
		if (inf) {
			return -1;
		} else if (min <= 0) {
			return 0;
		}
		return (int) min;
	}

	private long[] getRes(long x, char[] ops, int[] b) {
		long[] res = new long[b.length+1];
		res[0] = x;
		for (int i = 1; i < res.length; i++) {
			if (ops[i-1] == '+') {
				res[i] = b[i-1] - res[i-1];
			} else {
				res[i] = -1*(b[i-1] - res[i-1]);
			}			
		}
		return res;
	}

}
