import java.util.ArrayList;

public class SlimeXSlimesCity {

	public int merge(int[] population) {
		int ways = 0;
		for (int i = 0; i < population.length; i++) {
			ArrayList<Long> pop = new ArrayList<Long>();
			for (int j = 0; j < population.length; j++) {
				pop.add((long) population[j]);
			}
			long target = pop.remove(i);
			boolean added = true;
			while (added) {
				added = false;
				for (int j = 0; j < pop.size(); j++) {
					if (pop.get(j) <= target) {
						target += pop.remove(j);
						added = true;
						break;
					}
				}
			}
			if (pop.isEmpty()) {
				ways++;
			}
		}
		return ways;
	}

}
