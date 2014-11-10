public class StonesGame {

	public String winner(int N, int M, int K, int L) {
		if (canMove(N, M, K, L)) {
			return "Romeo";
		}
		int min = N;
		int max = 1;
		for (int i = -K + 1; i < K; i++) {
			int newStart = M + i;
			if (newStart > 0 && newStart < N) {
				if (canMove(N, newStart, K, L)) {
					min = Math.min(min, newStart);
					max = Math.max(max, newStart);							
				}
			}
		}
		int won = 0;
		int newStart = min;
		if (newStart > 0 && newStart < N) {
			if (canMove(N, newStart, K, L)) {
				won++;					
			}
		}
		newStart = max;
		if (newStart > 0 && newStart < N) {
			if (canMove(N, newStart, K, L)) {
				won++;				
			}
		}
		if (won == 2) {
			return "Strangelet";
		}
		
		return "Draw";
	}

	private boolean canMove(int N, int start, int length, int target) {
		int diff = Math.abs(target - start);
		if (diff < length
				&& ((diff+1) % 2 == length % 2)) {
			if (length % 2 == 1) {
				int pivot = (start + target) / 2;
				if (pivot - (length) / 2 >= 1 && pivot + (length) / 2 <= N) {
					return true;
				}
			} else {
				double pivot = (start + target) / 2.0;
				if (pivot - (length) / 2 >= 0.0 && pivot + (length) / 2 <= N+1) {
					return true;
				}
			}
		}
		return false;		
	}

}
