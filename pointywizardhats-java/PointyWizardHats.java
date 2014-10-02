import java.util.Arrays;

public class PointyWizardHats {
	boolean bpm(boolean[][] bpGraph, int u, boolean[] seen, int[] matchR)
	{
		int N = bpGraph[0].length;
		for (int v = 0; v < N; v++)
		{
			if (bpGraph[u][v] && !seen[v])
			{
				seen[v] = true;
				if (matchR[v] < 0 || bpm(bpGraph, matchR[v], seen, matchR))
				{
					matchR[v] = u;
					return true;
				}
			}
		}
		return false;
	}

	int maxBPM(boolean[][] bpGraph)
	{
		int M = bpGraph.length;
		int N = bpGraph[0].length;
		int[] matchR = new int[N];

		Arrays.fill(matchR, -1);

		int result = 0; // Count of jobs assigned to applicants
		for (int u = 0; u < M; u++)
		{
			boolean[] seen = new boolean[N];

			if (bpm(bpGraph, u, seen, matchR))
				result++;
		}
		return result;
	}

	public int getNumHats(int[] topHeight, int[] topRadius, int[] bottomHeight, int[] bottomRadius) {
		boolean[][] bpGraph = new boolean[topHeight.length][bottomHeight.length];		

		for (int i = 0; i < topHeight.length; i++) { //top
			for (int j = 0; j < bottomHeight.length; j++) { //bottom
				int rDiff = bottomRadius[j] - topRadius[i];
				double thetaDiff = Math.atan(bottomRadius[j] / (float)bottomHeight[j]) - Math.atan(topRadius[i] / (float)topHeight[i]);
				int mDiff = topHeight[i]*bottomRadius[j] - bottomHeight[j]*topRadius[i];
				if (rDiff > 0 && mDiff > 0) {
					bpGraph[i][j] = true;					
				}
			}
		}
		return maxBPM(bpGraph);
	}

}
