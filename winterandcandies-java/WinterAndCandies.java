import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;

public class WinterAndCandies {

	public int getNumber(int[] type) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < type.length; i++) {
			if (!map.containsKey(type[i])) {
				map.put(type[i], 0);
			}
			map.put(type[i], map.get(type[i])+1);
		}
		
		ArrayList<Integer> ways = new ArrayList<>();
		int n = 1;
		while (true) {
			if (!map.containsKey(n)) {
				break;
			}
			if (ways.isEmpty()) {
				ways.add(map.get(n));
			} else {
				ways.add(ways.get(ways.size()-1) * map.get(n));
			}
			
			n++;
		}
		
		int sum = 0;
		for (int i : ways) {
			sum += i;
		}
		return sum;
	}

}
