import java.util.ArrayList;

public class Over9000Rocks {

	public int countPossibilities(int[] lowerBound, int[] upperBound) {
		int N = 1 << lowerBound.length;
		short[] map = new short[15000002];
		ArrayList<int[]> ranges = new ArrayList<>();
		for (int n = 0; n < N; n++) {
			boolean[] selected = new boolean[lowerBound.length];
			for (int i = 0; i < lowerBound.length; i++) {
				if (((1 << i) & n) != 0) {
					selected[i] = true;
				}
			}
			int[] range = getRange(lowerBound, upperBound, selected);
			ranges.add(range);
		}
		
		for (int[] is : ranges) {
			map[is[0]]++;
			map[is[1]+1]--;
		}
		
		int counting = 0;
		int num = 0;
		for (int i = 0; i < map.length; i++) {
			if (map[i] > 0) {
				counting += map[i];
			}
			if (map[i] < 0) {
				counting += map[i];
			}
			if (counting > 0 && i > 9000) {
				num++;
			}
		}
		return num;
	}

	private int[] getRange(int[] lowerBound, int[] upperBound, boolean[] selected) {
		int[] range = {0, 0};
		for (int i = 0; i < selected.length; i++) {
			if (selected[i]) {
				range[0] += lowerBound[i];
				range[1] += upperBound[i];
			}
		}
		return range;
	}

}

