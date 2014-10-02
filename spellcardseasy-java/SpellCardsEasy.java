import java.util.ArrayList;

public class SpellCardsEasy {

	public int maxDamage(int[] level, int[] damage) {
		ArrayList<Integer> lev = new ArrayList<Integer>();
		ArrayList<Integer> dam = new ArrayList<Integer>();
		for (int i = 0; i < level.length; i++) {
			lev.add(level[i]);
		}
		for (int i = 0; i < damage.length; i++) {
			dam.add(damage[i]);
		}
		
		boolean moved = true;
		int maxD = 0;
		while(moved) {
			moved = false;
			int[] cost = new int[dam.size()];
			for (int i = dam.size()-1; i >= 0; i--) {
				int c = 0;
				if (lev.get(i)+i <= dam.size()) {
					c = dam.get(i);
					for (int j = i+1; j < lev.get(i)+i; j++) {
						c -= cost[j];
					}
				}
				cost[i] = c;
			}
			
			
			
			
			int maxC = Integer.MIN_VALUE;
			int maxIdx = -1;
			for (int i = 0; i < cost.length; i++) {
				if (cost[i] > maxC && lev.get(i)+i <= dam.size()) {
					maxC = cost[i];
					maxIdx = i;
				}
			}
			
			if (maxIdx > -1) {
				maxD += dam.get(maxIdx);
				int s = lev.get(maxIdx);
				for (int j = 0; j < s; j++) {
					dam.remove(maxIdx);
					lev.remove(maxIdx);
				}
				moved = true;
			}
		}
		return maxD;
	}

}
