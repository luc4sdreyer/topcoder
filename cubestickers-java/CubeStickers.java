import java.util.HashMap;

public class CubeStickers {

	public String isPossible(String[] sticker) {
		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < sticker.length; i++) {
			if (!map.containsKey(sticker[i])) {
				map.put(sticker[i], 0);
			}
			map.put(sticker[i], map.get(sticker[i]) + 1);
		}
		
		int atLeastPairs = 0;
		int distinct = 0;
		for (String key : map.keySet()) {
			if (map.get(key) > 1) {
				atLeastPairs++;
			} else {
				distinct++;
			}
		}
		
		if (atLeastPairs >= 3 
			|| (atLeastPairs >= 2 && distinct >= 2)
			|| (atLeastPairs >= 1 && distinct >= 4)
			|| (atLeastPairs >= 0 && distinct >= 6)) {
			return "YES";
		}
		return "NO";
	}

}
