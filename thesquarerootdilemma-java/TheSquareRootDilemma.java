public class TheSquareRootDilemma {

	public int countPairs(int N, int M) {
		int num = 0;
		for (long a = 1; a <= N; a++) {
			long r = a;
			for (long i = 2; i*i <= a; i++) {
				while ((r % (i*i)) == 0) {
					r /= i*i;
				}
			}
			for (long i = 1; i*i*r <= M; i++) {
				num++;
			}
		}
		return num;
	}

}
