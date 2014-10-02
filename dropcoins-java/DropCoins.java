import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DropCoins {

	public int getMinimum(String[] board, int K) {
		int[][] steps = new int[board.length*2 +1][board[0].length()*2 +1];
		for (int i = 0; i < steps.length; i++) {
			Arrays.fill(steps[i], -1);
		}
		char[][] b = new char[board.length][board[0].length()];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				b[i][j] = board[i].charAt(j);
			}
		}
		takeSteps(steps, b,  board.length, board[0].length());
		steps[0][0] = 0;
		
		boolean[][] visited = new boolean[board.length*2 +1][board[0].length()*2 +1];
		Node top = new Node(0, 0, K, 0);
		Queue<Node> queue = new LinkedList<>();
		queue.add(top);
		while (!queue.isEmpty()) {
			top = queue.poll();
			if (top.x >= 0 && top.x < steps[0].length && top.y >= 0 && top.y < steps.length 
					&& steps[top.y][top.x] != -1 && top.sum >= 0 && visited[top.y][top.x] == false) {
				if (top.sum == 0) {
					return top.steps;
				}
				visited[top.y][top.x] = true;
				
				Node newNode = new Node(top.x+1, top.y, top.sum - steps[top.y][top.x], top.steps+1);
				queue.add(newNode);
				newNode = new Node(top.x-1, top.y, top.sum - steps[top.y][top.x], top.steps+1);
				queue.add(newNode);
				newNode = new Node(top.x, top.y+1, top.sum - steps[top.y][top.x], top.steps+1);
				queue.add(newNode);
				newNode = new Node(top.x, top.y-1, top.sum - steps[top.y][top.x], top.steps+1);
				queue.add(newNode);
			}
		}
		
		return -1;
	}
	
	public static class Node {
		public int x;
		public int y;
		public int sum;
		public int steps;
		public Node(int x, int y, int sum, int steps) {
			super();
			this.x = x;
			this.y = y;
			this.steps = steps;
			this.sum = sum;
		}		
	}

	private void takeSteps(int[][] steps, char[][] board, int px, int py) {
		if (count(board) > 0) {
			for (int i = 0; i < 4; i++) {
				int newPx = px;
				int newPy = py;
				if (i == 0) {
					newPy++;
					if (newPx >= 0 && newPx < steps[0].length && newPy >= 0 && newPy < steps.length 
							&& steps[newPy][newPx] == -1) {
						char[][] newBoard = new char[board.length][board[0].length];
						for (int j = 0; j < board.length; j++) {
							newBoard[j] = board[j].clone();
						}
						
						for (int y = 0; y < newBoard.length; y++) {
							for (int x = 0; x < newBoard[0].length; x++) {
								if (y == newBoard.length-1) {
									newBoard[y][x] = '.';
								} else {
									newBoard[y][x] = newBoard[y+1][x];
								}
							}
						}
						steps[newPy][newPx] = count(newBoard);
						takeSteps(steps, newBoard, newPx, newPy);
					}
					
				} else if (i == 1) {
					newPy--;
					if (newPx >= 0 && newPx < steps[0].length && newPy >= 0 && newPy < steps.length 
							&& steps[newPy][newPx] == -1) {
						char[][] newBoard = new char[board.length][board[0].length];
						for (int j = 0; j < board.length; j++) {
							newBoard[j] = board[j].clone();
						}
						
						for (int y = newBoard.length-1; y >= 0; y--) {
							for (int x = 0; x < newBoard[0].length; x++) {
								if (y == 0) {
									newBoard[y][x] = '.';
								} else {
									newBoard[y][x] = newBoard[y-1][x];
								}
							}
						}
						steps[newPy][newPx] = count(newBoard);
						takeSteps(steps, newBoard, newPx, newPy);
					}
				} else if (i == 2) {
					newPx++;
					if (newPx >= 0 && newPx < steps[0].length && newPy >= 0 && newPy < steps.length 
							&& steps[newPy][newPx] == -1) {
						char[][] newBoard = new char[board.length][board[0].length];
						for (int j = 0; j < board.length; j++) {
							newBoard[j] = board[j].clone();
						}

						for (int y = 0; y < newBoard.length; y++) {
							for (int x = 0; x < newBoard[0].length; x++) {
								if (x == newBoard[0].length-1) {
									newBoard[y][x] = '.';
								} else {
									newBoard[y][x] = newBoard[y][x+1];
								}
							}
						}
						steps[newPy][newPx] = count(newBoard);
						takeSteps(steps, newBoard, newPx, newPy);
					}
				} else if (i == 3) {
					newPx--;
					if (newPx >= 0 && newPx < steps[0].length && newPy >= 0 && newPy < steps.length 
							&& steps[newPy][newPx] == -1) {
						char[][] newBoard = new char[board.length][board[0].length];
						for (int j = 0; j < board.length; j++) {
							newBoard[j] = board[j].clone();
						}
						
						for (int y = newBoard.length-1; y >= 0; y--) {
							for (int x = newBoard[0].length -1; x >= 0; x--) {
								if (x == 0) {
									newBoard[y][x] = '.';
								} else {
									newBoard[y][x] = newBoard[y][x-1];
								}
							}
						}
						steps[newPy][newPx] = count(newBoard);
						takeSteps(steps, newBoard, newPx, newPy);
					}
				}
			}
		}
	}
	
	private int count(char[][] board) {
		int c = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 'o') {
					c++;
				}
			}
		}
		return c;
	}

}
