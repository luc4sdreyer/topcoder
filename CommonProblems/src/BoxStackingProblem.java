import java.util.Arrays;
import java.util.Comparator;


public class BoxStackingProblem {

	public static void main(String[] args) {
		int[][] a = new int[][]{
				{4, 6, 7},
				{1, 2, 3},
				{4, 5, 6},
				{10, 12, 32}
		};
		boxStackingProblem(a);
	}

	public static int[] boxStackingProblem(int[][] a_) {
		// assume dimensions are w x h x d
		int[][] a = new int[a_.length*3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < a_.length; j++) {
				for (int k = 0; k < 3; k++) {
					a[j + a_.length*(i)][(i+k) % 3] = a_[j][k];
				}
			}
		}
		Arrays.sort(a, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {				
				return Integer.compare(o2[0] * o2[2], o1[0] * o1[2]);
			}
		});
		int max = -1;
		int[] dp = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			dp[i] = a[i][1];
			for (int j = 0; j < i; j++) {
				if (a[i][0] < a[j][0] && a[i][2] < a[j][2]) {
					dp[i] = Math.max(dp[i], dp[j]+a[i][1]);
					max = Math.max(max, dp[i]);
				}
			}
		}
		System.out.println("input:  ");
		for (int i = 0; i < a.length; i++) {
			System.out.println(Arrays.toString(a[i]));
		}
		System.out.println("dp:     " + Arrays.toString(dp));
		System.out.println("max:    " + max);
		return null;
	}

}
