import java.util.HashSet;

public class AntsMeet {

	public int countAnts(int[] x, int[] y, String direction) {
		int[] dy = new int[x.length];
		int[] dx = new int[x.length];
		for (int i = 0; i < x.length; i++) {
			if (direction.charAt(i) == 'N') {
				dy[i]++;
			} else if (direction.charAt(i) == 'S') {
				dy[i]--;
			} else if (direction.charAt(i) == 'E') {
				dx[i]++;
			} else if (direction.charAt(i) == 'W') {
				dx[i]--;
			}
		}
		HashSet<Integer> killed = new HashSet<Integer>();
		for (int t = 0; t <= 4000; t++) {
			HashSet<Integer> toKill = new HashSet<Integer>();
			for (int i = 0; i < x.length; i++) {
				if (killed.contains(i)) {
					continue;
				}
				for (int j = i+1; j < x.length; j++) {
					if (killed.contains(j)) {
						continue;
					}
					if (2*x[i] + dx[i]*t == 2*x[j] + dx[j]*t 
							&& 2*y[i] + dy[i]*t == 2*y[j] + dy[j]*t) {
						toKill.add(i);
						toKill.add(j);
					}
				}
			}
			for (int i : toKill) {
				killed.add(i);
			}
		}
		return x.length - killed.size();
	}

}
