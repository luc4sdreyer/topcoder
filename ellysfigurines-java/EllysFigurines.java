import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class EllysFigurines {

	public int getMoves(String[] board, int R, int C) {

		char[][] ngrid = new char[board.length][board[0].length()];
		for (int y = 0; y < ngrid.length; y++) {
			for (int x = 0; x < ngrid[0].length; x++) {
				ngrid[y][x] = board[y].charAt(x);
			}
		}
		Node s = new Node(ngrid, 0);
		
		Queue<Node> q = new LinkedList<Node>();
		//PriorityQueue<Node> q = new PriorityQueue<Node>();
		q.add(s);
		ArrayList<Integer> best = new ArrayList<Integer>();

		while (q.size() != 0) {
			Node top = q.poll();
			

//			for (int y = 0; y < top.grid.length; y++) {
//				for (int x = 0; x < top.grid[0].length; x++) {
//					System.out.print(top.grid[y][x]);
//				}
//				System.out.println();
//			}
//			System.out.println();
//			System.out.println();
			
			if (top.numFig == 0) {
				best.add(top.moves);
				if (best.size() > 0) {
					return Collections.min(best);
				}
				continue;
			}
			
			// use max rows
			if (R >= C) {
				ArrayList<Node> nextMoves = new ArrayList<Node>();
				for (int y = 0; y < top.grid.length; y++) {
					if (top.isRowClear(y, y+R)) {
						continue;
					}
					Node newN = top.clone();
					newN.clearRow(y, y+R);
					newN.moves++;
					//q.add(newN);
					nextMoves.add(newN);
				}
				for (int x = 0; x < top.grid[0].length; x++) {
					if (top.isColumnClear(x, x+C)) {
						continue;
					}
					Node newN = top.clone();
					newN.clearColumn(x, x+C);
					newN.moves++;
					//q.add(newN);
					nextMoves.add(newN);
				}
				Collections.sort(nextMoves);
				//Collections.reverse(nextMoves);
				for (int i = 0; i < nextMoves.size(); i++) {
					q.add(nextMoves.get(i));
				}
			} else {
				ArrayList<Node> nextMoves = new ArrayList<Node>();
				for (int x = 0; x < top.grid[0].length; x++) {
					if (top.isColumnClear(x, x+C)) {
						continue;
					}
					Node newN = top.clone();
					newN.clearColumn(x, x+C);
					newN.moves++;
					//q.add(newN);
					nextMoves.add(newN);
				}
				for (int y = 0; y < top.grid.length; y++) {
					if (top.isRowClear(y, y+R)) {
						continue;
					}
					Node newN = top.clone();
					newN.clearRow(y, y+R);
					newN.moves++;
					//q.add(newN);
					nextMoves.add(newN);
				}
				Collections.sort(nextMoves);
				//Collections.reverse(nextMoves);
				for (int i = 0; i < nextMoves.size(); i++) {
					q.add(nextMoves.get(i));
				}
			}
		}
		return Collections.min(best);
	}

	private class Node implements Comparable<Node>{
		public char[][] grid;
		public int moves;
		public int numFig;
		public Node(char[][] grid, int moves) {
			super();
			this.grid = grid;
			this.moves = moves;
			this.numFig = getNumFig();
		}
		public int getNumFig() {
			int num = 0;
			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[0].length; x++) {
					if (this.grid[y][x] == 'X') {
						num++;
					}
				}
			}
			return num;
		}
		public void clearRow(int r0, int r1) {
			for (int y = r0; y < r1 && y < grid.length; y++) {
				for (int x = 0; x < grid[0].length; x++) {
					this.grid[y][x] = '.';
				}
			}
			this.numFig = getNumFig();
		}
		public boolean isRowClear(int r0, int r1) {
			for (int y = r0; y < r1 && y < grid.length; y++) {
				for (int x = 0; x < grid[0].length; x++) {
					if (this.grid[y][x] == 'X') {
						return false; 
					}
				}
			}
			return true;
		}
		public void clearColumn(int c0, int c1) {
			for (int y = 0; y < grid.length; y++) {
				for (int x = c0; x < c1 && x < grid[0].length; x++) {
					this.grid[y][x] = '.';
				}
			}
			this.numFig = getNumFig();
		}
		public boolean isColumnClear(int c0, int c1) {
			for (int y = 0; y < grid.length; y++) {
				for (int x = c0; x < c1 && x < grid[0].length; x++) {
					if (this.grid[y][x] == 'X') {
						return false; 
					}
				}
			}
			return true;
		}
		public Node clone() {
			char[][] newGrid = new char[grid.length][grid[0].length];
			for (int y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid[0].length; x++) {
					newGrid[y][x] = grid[y][x];
				}
			}
			return new Node(newGrid, moves);
		}
		public int compareTo(Node arg0) {
			if (this.numFig < arg0.numFig) {
				return -1;
			} else if (this.numFig > arg0.numFig) {
				return 1;
			} else {
				return 0;
			}
		}		
	}
}
