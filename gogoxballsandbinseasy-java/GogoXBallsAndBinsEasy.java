public class GogoXBallsAndBinsEasy {

	public int solve(int[] T) {
		int max = 0;
		int[] X = T.clone();
		max = Math.max(max, count(T, X));
		while (next_permutation(X)) {
			max = Math.max(max, count(T, X));
		}
		return max;
	}
	
	public int count(int[] T, int[] X) {
		int n = 0;
		for (int i = 0; i < X.length; i++) {
			if (T[i] > X[i]) {
				n += T[i] - X[i];
			}
		}
		return n;
	}
	
	public boolean next_permutation(int str[]) {
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}

}
