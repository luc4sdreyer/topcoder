import java.util.Arrays;

public class PointyWizardHats {

	public int getNumHats(int[] topHeight, int[] topRadius, int[] bottomHeight, int[] bottomRadius) {
		boolean[][] bpg = new boolean[topHeight.length][bottomHeight.length];
		for (int top = 0; top < topHeight.length; top++) {
			for (int bot = 0; bot < bottomHeight.length; bot++) {
				bpg[top][bot] = checkCompat(topHeight, topRadius, bottomHeight, bottomRadius, top, bot);
			}
		}
		return maxBPM(bpg);
	}
	
	boolean bpm(boolean[][] bpGraph, int u, boolean[] seen, int[] matchR)
	{
		int N = bpGraph[0].length;
		for (int v = 0; v < N; v++)
		{
			if (bpGraph[u][v] && !seen[v])
			{
				seen[v] = true; // Mark v as visited

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

		int result = 0; 
		for (int u = 0; u < M; u++)
		{
			boolean[] seen = new boolean[N];

			if (bpm(bpGraph, u, seen, matchR))
				result++;
		}
		return result;
	}

	public boolean checkCompat(int[] topHeight, int[] topRadius, int[] bottomHeight, int[] bottomRadius, int top, int bottom) {
		if (bottomRadius[bottom] > topRadius[top] 
				&& (bottomRadius[bottom]*topHeight[top] > topRadius[top]*bottomHeight[bottom])) {
			return true;
		} else {
			return false;
		}
	}

}
