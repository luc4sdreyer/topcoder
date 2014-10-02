public class MyVeryLongCake {

	public int cut(int n) {
		int N = n, p, res = 1;
		for (int i = 2; i * i <= N; i++) {
			if (N % i == 0){
				p = 1;
				while (N % i == 0){
					N /= i;
					p *= i;
				}
				res *= p - p / i;
			}
		}
		if (N > 1) {
			res *= N - 1;
		}
		return n - res;
	}

}
