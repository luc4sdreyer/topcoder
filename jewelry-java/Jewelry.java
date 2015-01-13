import java.util.Arrays;
import java.util.HashMap;

public class Jewelry {

	public long howMany(int[] values) {
		int max = 0;
		for (int i = 0; i < values.length; i++) {
			max += values[i];
		}
		int[] ways = new int[max+1];
		ways[0] = 1;
		for (int i = 0; i < values.length; i++) {
			for (int sum = max; sum >= values[i]; sum--) {
			//for (int sum = values[i]; sum <= max; sum++) {
				ways[sum] += ways[sum - values[i]];
			}
		}
		System.out.println(Arrays.toString(ways));
		//[1, 1, 1, 2, 2, 4, 4, 4, 5, 5, 6, 5, 5, 4, 4, 4, 2, 2, 1, 1, 1]
		//[1, 1, 1, 2, 2, 4, 4, 4, 5, 5, 6, 5, 5, 4, 4, 4, 2, 2, 1, 1, 1]
		int[] w = new int[max+1];
		HashMap<Long, Long> m = waysToSumLong(values);
		for (long key: m.keySet()) {
			w[(int) key] = (int)(long)m.get(key);
		}
		System.out.println(Arrays.toString(w));
		return 0;		
	}
	
	public static HashMap<Long, Long> waysToSumLong(int[] values) {
		Arrays.sort(values);
		HashMap<Long, Long> ways = new HashMap<>();
		ways.put(0L, 1L);
		for (int i = 0; i < values.length; i++) {
			HashMap<Long, Long> newWays = new HashMap<>(ways); // shallow copy
			for (long key: ways.keySet()) {
				long newSum = key + values[i];
				newWays.put(newSum, ways.get(key) + (ways.containsKey(newSum) ? ways.get(newSum) : 0));
			}
			ways = newWays;
		}
		return ways;
	}
}
