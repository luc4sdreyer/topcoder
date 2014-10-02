import java.util.ArrayList;
import java.util.Collections;

public class CucumberMarket {

	public String check(int[] price, int budget, int k) {
		ArrayList<Integer> p = new ArrayList<Integer>();
		for (int i = 0; i < price.length; i++) {
			p.add(price[i]);
		}
		Collections.sort(p);
		Collections.reverse(p);
		int max = 0;
		for (int i = 0; i < k; i++) {
			max += p.get(i);
		}
		if (max > budget)
			return "NO";
		else
			return "YES";
	}

}
