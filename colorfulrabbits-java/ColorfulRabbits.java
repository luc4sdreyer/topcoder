import java.util.HashMap;

public class ColorfulRabbits {

	public int getMinimum(int[] replies) {
		HashMap<Integer, Integer> map = new HashMap<>();
		int num = 0;
		for (int i = 0; i < replies.length; i++) {
			if (!map.containsKey(replies[i])) {
				map.put(replies[i], 0);				
			}
			map.put(replies[i], map.get(replies[i]) + 1);
		}
		
		for (int type : map.keySet()) {
			int freq = map.get(type);
			int min = (type+1);
			if (type == 0) {
				min = freq;
			} else if ((freq % (type+1)) != 0) {
				min = (freq / (type+1) + 1) * (type+1);
			} else {
				min = freq;
			}
			
			num += min;
		}
		
		
		return num;
	}

}
