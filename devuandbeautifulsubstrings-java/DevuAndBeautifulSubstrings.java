import java.util.HashMap;
import java.util.TreeMap;

public class DevuAndBeautifulSubstrings {

	public long countBeautifulSubstrings(int n, int cnt) {
		for (int len = 1; len < 16; len++) {

			int N = 1 << len;
			TreeMap<Integer, Integer> dist = new TreeMap<>();
			for (int x = 0; x < N; x++) {
				boolean[] active = new boolean[len];
				for (int i = 0; i < len; i++) {
					if (((1 << i) & x) != 0) {
						active[i] = true;
					}
				}
				
				int beauty = 0;
				int counter = 1;
				for (int i = 1; i < active.length; i++) {
					if (active[i] == active[i-1]) {
						counter++;
					} else {
						beauty += counter * (counter - 1)/2;
						counter = 1;
					}
				}
				beauty += counter * (counter - 1)/2;
				
				dist.put(beauty, dist.containsKey(beauty) ? (dist.get(beauty) + 1) : 1);
			}
			for (Integer i: dist.keySet()) {
				System.out.println("length: " + len + "\t beauty: " + i + "\t count: " + dist.get(i));
			}
			System.out.println();
		}
		
		
		return 0;
	}

}
