import java.util.LinkedList;
import java.util.Queue;

public class ConnectingGameDiv2 {

	public static class Node {
		int x, y;
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public int getmin(String[] board) {
		boolean[] valid = new boolean['z'+1];
		int[] cost = new int['z'+1];
		boolean[][] paths = new boolean['z'+1]['z'+1];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length(); j++) {
				if (valid[board[i].charAt(j)] = false) {
					valid[board[i].charAt(j)] = true;
					Node top = new Node(i,j);
					Queue<Node> q = new LinkedList<>();
					boolean[][] visited = new boolean[board.length][board[0].length()];
					q.add(top);
					int c = 0;
					while(!q.isEmpty()) {
						top = q.poll();
						if (top.x < 0 || top.x >= visited[0].length || top.y < 0 || top.y >= visited.length) {
							continue;
						}
						if (board[top.y].charAt(top.x) != board[i].charAt(j)) {
							paths[board[i].charAt(j)][board[top.y].charAt(top.x)] = true;
							continue;
						}
						if (visited[top.y][top.x]) {
							continue;
						}
						visited[top.y][top.x] = true;
						c++;
						q.add(new Node(top.x + 1, top.y + 1));						
						q.add(new Node(top.x + 1, top.y + 0));
						q.add(new Node(top.x + 1, top.y - 1));
						q.add(new Node(top.x + 0, top.y - 1));
						q.add(new Node(top.x - 1, top.y - 1));
						q.add(new Node(top.x - 1, top.y + 0));
						q.add(new Node(top.x - 1, top.y + 1));
						q.add(new Node(top.x + 0, top.y + 1));
					}
					cost[board[i].charAt(j)] = c;
				}				
			}
		}
		
		
		
		//boolean[][] paths = new boolean['z'+1]['z'+1];
		return 0;
	}

}
