public class PatrolRoute {

	public int countRoutes(int X, int Y, int minT, int maxT) {
		long num = 0;
		for (int i = 0; i <= X; i++) {
			for (int j = minT - i ; j <= maxT - i; j++) {
				num += (X-j)*(j-1) % 1000000007;
			}
		}
		return (int) num;
	}

}
