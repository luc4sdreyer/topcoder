import java.util.HashMap;

public class AntsMeet {
	public static final int[] dx = {1, 0, -1, 0};
	public static final int[] dy = {0, 1, 0, -1};
	public static final int BIG = 10000;
	
	public int countAnts(int[] x, int[] y, String direction) {
		int[] dir = new int[direction.length()];
		for (int i = 0; i < direction.length(); i++) {
			if (direction.charAt(i) == 'E') {
				dir[i] = 0;
			} else if (direction.charAt(i) == 'N') {
				dir[i] = 1;
			} else if (direction.charAt(i) == 'W') {
				dir[i] = 2;
			} else {
				dir[i] = 3;
			}
		}
		
		boolean[] dead = new boolean[dir.length];
		for (int i = 0; i < x.length; i++) {
			x[i] *= 2;
			y[i] *= 2;
		}
		for (int t = 1; t <= 4001; t++) {			
			HashMap<Integer, Integer> positions = new HashMap<>();
			boolean[] toKill = new boolean[dead.length];
			for (int i = 0; i < x.length; i++) {
				if (!dead[i]) {
					int pos = x[i] + dx[dir[i]] * t + (y[i] + dy[dir[i]] * t) * BIG;
					if (positions.containsKey(pos)) {
						toKill[i] = true;
						toKill[positions.get(pos)] = true;
					} else {
						positions.put(pos, i);
					}
				}
			}
			for (int i = 0; i < toKill.length; i++) {
				if (toKill[i]) {
					dead[i] = true;
				}
			}
		}
		int numAlive = 0;
		for (int i = 0; i < dead.length; i++) {
			if (!dead[i]) {
				numAlive++;
			}
		}
		return numAlive;
	}

}
