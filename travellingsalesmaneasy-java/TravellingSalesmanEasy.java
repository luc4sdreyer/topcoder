import java.util.ArrayList;
import java.util.Collections;

public class TravellingSalesmanEasy {

	public int getMaxProfit(int M, int[] profit, int[] city, int[] visit) {
		ArrayList<ArrayList<Integer>> p = new ArrayList<>();
		for (int i = 0; i <= M; i++) {
			p.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < profit.length; i++) {
			p.get(city[i]).add(profit[i]);
		}
		for (int i = 0; i < p.size(); i++) {
			Collections.sort(p.get(i));
		}
		int max = 0;
		for (int i = 0; i < visit.length; i++) {
			ArrayList<Integer> x = p.get(visit[i]);
			if (!x.isEmpty()) {
				max += x.remove(x.size()-1);
			}
		}
		return max;
	}

}
