import java.util.Arrays;
import java.util.HashSet;

public class RotatingBot {

	public static final int EAST = 0;
	public static final int NORTH = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 3;
	
	public int minArea(int[] moves) {
		int[] dx = {1, 0, -1, 0};
		int[] dy = {0, 1, 0, -1};
		int direction = 0;
		int BIG = 10000;
		int x = BIG/2;
		int y = BIG/2;
		HashSet<Integer> visited = new HashSet<>();
		int key = x + BIG*y;
		visited.add(key);
		for (int i = 0; i < moves.length; i++) {
			for (int j = 0; j < moves[i]; j++) {
				x += dx[direction];
				y += dy[direction];
				key = x + BIG*y;
				if (visited.contains(key)) {
					return -1;
				}
				visited.add(key);
			}
			direction = (direction+1)%4;
		}
		int[] min = {Integer.MAX_VALUE, Integer.MAX_VALUE};
		int[] max = {Integer.MIN_VALUE, Integer.MIN_VALUE};
		for (int pos : visited) {
			int tx = pos % BIG;
			int ty = pos / BIG;
			min[0] = Math.min(min[0], tx);
			min[1] = Math.min(min[1], ty);
			max[0] = Math.max(max[0], tx);
			max[1] = Math.max(max[1], ty);
		}
		
		x = BIG/2;
		y = BIG/2;
		visited.clear();
		direction = 0;
		key = x + BIG*y;
		visited.add(key);
		for (int i = 0; i < moves.length; i++) {
			for (int j = 0; j < moves[i]; j++) {
				x += dx[direction];
				y += dy[direction];
				key = x + BIG*y;
				if (visited.contains(key)) {
					return -1;
				}
				visited.add(key);
			}
			boolean last = i == moves.length -1 ? true : false;
			if (!last) {
				int nextX = x + dx[direction];
				int nextY = y + dy[direction];
				key = nextX + BIG*nextY;
				if (!visited.contains(key) && !(nextX > max[0] || nextX < min[0] || nextY > max[1] || nextY < min[1])) {
					return -1;
				}
			}
			direction = (direction+1)%4;		
		}
		int w = max[0] - min[0]+1;
		int h = max[1] - min[1]+1;
		return w*h;		
	}
}
