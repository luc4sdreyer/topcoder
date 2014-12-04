public class TheSquareRootDilemma {

	public int countPairs(int N, int M) {
		int num = 0;
		for (int a = 1; a <= N; a++) {
			int rem = a;
			for (int i = 2; i*i <= a; i++) {
				while (rem >= i*i && rem % (i*i) == 0) {
					rem /= i*i;
				}
			}
			for (int b = 1; b*b <= M/rem; b++) {
				num++;
			}
		}
		return num;
	}

}
