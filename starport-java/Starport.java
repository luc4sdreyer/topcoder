public class Starport {

	public double getExpectedTime(int N, int M) {
		double exp = 0;
		double r = M/(double)N;
		double ret = 0;
		int samples = 1000;
		for (int i = 0; i < samples; i++) {
			double fraction = i*r;
			double rem = fraction - ((int)fraction);
			exp += rem;
			ret = exp / (double)(i+1) * N;
		}
		return ret;
	}

}
