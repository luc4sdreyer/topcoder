import java.util.Arrays;
import java.util.HashSet;

public class NumberGameAgain {

	public long solve(int k, long[] table) {
		Arrays.sort(table);
		HashSet<Long> forbid = new HashSet<>();		
		for (int i = 0; i < table.length; i++) {
			forbid.add(table[i]);
		}
		for (int i = table.length-1; i >= 0; i--) {
			long t = table[i];
			while (t != 1) {
				t /= 2;
				if (forbid.contains(t)) {
					table[i] = 0;
					break;
				}
			}
		}
		forbid.clear();
		for (int i = 0; i < table.length; i++) {
			if (table[i] != 0) {
				forbid.add(table[i]);
			}
		}
		
		long limit = 1L << k;
		long contrib = 0;
		for (long f: forbid) {
			long power = 1;
			while (f < limit) {
				f *= 2;
				contrib += power;
				power *= 2;
			}
		}
		//System.out.println(limit);
		//System.out.println(contrib);
		return limit-contrib-2;
	}

}
