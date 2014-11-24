import java.util.LinkedList;
import java.util.Queue;

public class Islands {
	public static class Node {
		int x;
		int y;
		Node parent;
		public Node(int x, int y, Node parent) {
			super();
			this.x = x;
			this.y = y;
			this.parent = parent;
		}		
	}
	public int beachLength(String[] k) {
		char[][] kingdom = new char[k.length][];
		for (int i = 0; i < kingdom.length; i++) {
			kingdom[i] = k[i].toCharArray();
		}
		
		int beaches = 0;
		int N = Math.max(k.length, k[0].length());
		boolean[][][][] visitedBeaches = new boolean[N][N][N][N];
		boolean[][] visitedWater = new boolean[k.length][k[0].length()];
		for (int i = 0; i < kingdom.length; i++) {
			for (int j = 0; j < kingdom[i].length; j++) {
				if (!visitedWater[i][j]) {
					Node top = new Node(j, i, null);
					if (kingdom[top.y][top.x] == '#') {
						continue;
					}
					Queue<Node> q = new LinkedList<>();
					q.add(top);
					while (!q.isEmpty()) {
						top = q.poll();
						if (top.x < 0 || top.x >= kingdom[i].length || top.y < 0 || top.y >= kingdom.length) {
							continue;
						}
						if (kingdom[top.y][top.x] == '.' && visitedWater[top.y][top.x]) {
							continue;
						}
						if (kingdom[top.y][top.x] == '#' && top.parent != null && kingdom[top.parent.y][top.parent.x] == '.') {
							if (visitedBeaches[top.parent.y][top.parent.x][top.y][top.x]) {
								continue;
							}
							visitedBeaches[top.parent.y][top.parent.x][top.y][top.x] = true;
							beaches++;
							continue;
						}
						visitedWater[top.y][top.x] = true;
						if (top.y % 2 == 0) {
							q.add(new Node(top.x-1, top.y+1, top));		
							q.add(new Node(top.x-1, top.y-1, top));
						} else {
							q.add(new Node(top.x+1, top.y+1, top));		
							q.add(new Node(top.x+1, top.y-1, top));
						}				
						q.add(new Node(top.x  , top.y+1, top));		
						q.add(new Node(top.x  , top.y-1, top));
						q.add(new Node(top.x+1, top.y  , top));				
						q.add(new Node(top.x-1, top.y  , top));
					}
				}
			}
		}
		return beaches;
	}

}
