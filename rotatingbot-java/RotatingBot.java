import java.util.HashSet;

public class RotatingBot {

	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {1, 0, -1, 0};
	public static final int BIG = 10000;
	
	public int minArea(int[] moves) {
		HashSet<Integer> visited = new HashSet<>();
		int[] pos = { BIG/2, BIG/2 };
		int dir = 0;
		int key = pos[0] + pos[1]*BIG;
		visited.add(key);
		for (int i = 0; i < moves.length; i++) {
			for (int j = 0; j < moves[i]; j++) {
				pos[0] += dx[dir];
				pos[1] += dy[dir];
				key = pos[0] + pos[1]*BIG;
				if (visited.contains(key)) {
					return -1;
				}
				visited.add(key);
			}
			dir = (dir+1)%4;
		}
		
		int[] min = { Integer.MAX_VALUE, Integer.MAX_VALUE };
		int[] max = { 0, 0 };
		for (int key1 : visited) {
			int[] newPos = {key1 % BIG, key1 / BIG};
			for (int i = 0; i < newPos.length; i++) {
				min[i] = Math.min(min[i], newPos[i]); 
				max[i] = Math.max(max[i], newPos[i]);
			}
		}

		visited.clear();
		pos[0] = BIG/2;
		pos[1] = BIG/2;
		dir = 0;
		key = pos[0] + pos[1]*BIG;
		visited.add(key);
		
		for (int i = 0; i < moves.length; i++) {
			for (int j = 0; j < moves[i]; j++) {
				pos[0] += dx[dir];
				pos[1] += dy[dir];
				key = pos[0] + pos[1]*BIG;
				if (visited.contains(key)) {
					return -1;
				}
				visited.add(key);
			}
			
			if (i != moves.length -1) {
				// why did it turn?
				int[] nextPos = pos.clone();
				nextPos[0] += dx[dir];
				nextPos[1] += dy[dir];
				
				boolean outOfBounds = false;
				for (int j = 0; j < 2; j++) {
					if (nextPos[j] < min[j] || nextPos[j] > max[j]) {
						outOfBounds = true;
					}
				}
				key = nextPos[0] + nextPos[1]*BIG;
				if (!outOfBounds && !visited.contains(key)) {
					return -1;
				}
			}
			
			dir = (dir+1)%4;
		}
		
		int minSize = (max[0] - min[0] + 1) * (max[1] - min[1] + 1);  
		return minSize;
	}

}
