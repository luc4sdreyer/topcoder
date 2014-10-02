import java.util.ArrayList;
import java.util.Collections;

public class FoxAndVacation {

	public int maxCities(int total, int[] d) {
		ArrayList<Integer> p = new ArrayList<Integer>();
		for (int i = 0; i < d.length; i++) {
			p.add(d[i]);
		}
		Collections.sort(p);
		int max = 0;
		int j = 0;
		for (j = 0; j < p.size(); j++) {
			if (p.get(j) + max > total)	{
				return j;
			} else {
				max += p.get(j);
			}
		}
		return j;
	}

}
