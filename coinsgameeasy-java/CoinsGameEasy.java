import java.util.ArrayList;
import java.util.Collections;

public class CoinsGameEasy {

	public int minimalSteps(String[] board) {
		int totalMoves = 0;
		char[][] map = new char[board.length][board[0].length()];

		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				map[y][x] = board[y].charAt(x);
			}
		}
		ArrayList<Integer> minmoves = new ArrayList<Integer>();
		minmoves.add(move(1,0, totalMoves+1, map));
		minmoves.add(move(0,1, totalMoves+1, map));
		minmoves.add(move(-1,0, totalMoves+1, map));
		minmoves.add(move(0,-1, totalMoves+1, map));
		int min = Collections.min(minmoves);
		if (min > 10) {
			min = -1;
		}
		return min;
	}

	public int move(int xDir, int yDir, int totalMoves, char[][] map) {
		if (totalMoves > 10) {
			return 1000;
		}
		char[][] newMap = new char[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				newMap[y][x] = map[y][x];
			}
		}
		int offboard = 0;
		int pos1x = -1;
		int pos1y = -1;
		int pos2x = -1;
		int pos2y = -1;
		for (int y = 0; y < newMap.length; y++) {
			for (int x = 0; x < newMap[0].length; x++) {
				if (newMap[y][x] == 'o' && pos1x == -1) {
					pos1x = x;
					pos1y = y;
				} else if (newMap[y][x] == 'o') {
					pos2x = x;
					pos2y = y;					
				}
			}
		}
		
		if (pos1y+yDir < 0 || pos1y+yDir >= newMap.length) {
			offboard++;
		} else if (pos1x+xDir < 0 || pos1x+xDir >= newMap[0].length) {
			offboard++;
		}
		if (pos2y+yDir < 0 || pos2y+yDir >= newMap.length) {
			offboard++;
		} else if (pos2x+xDir < 0 || pos2x+xDir >= newMap[0].length) {
			offboard++;
		}
		if (offboard == 2) {
			return 1000;
		} else if (offboard == 1) {
			return totalMoves;
		}
		
		//move
		
		int newpos1x = pos1x;
		int newpos1y = pos1y;
		int newpos2x = pos2x;
		int newpos2y = pos2y;
		if (newMap[pos1y+yDir][pos1x+xDir] != '#') {
			newpos1y += yDir;
			newpos1x += xDir;
		}
		if (newMap[pos2y+yDir][pos2x+xDir] != '#') {
			newpos2y += yDir;
			newpos2x += xDir;
		}
		newMap[pos1y][pos1x] = '.';
		newMap[pos2y][pos2x] = '.';
		newMap[newpos1y][newpos1x] = 'o';
		newMap[newpos2y][newpos2x] = 'o';
		if (newpos1x == newpos2x && newpos1y == newpos2y) {
			return 1000;
		}
		
		ArrayList<Integer> minmoves = new ArrayList<Integer>();
		minmoves.add(move(1,0, totalMoves+1, newMap));
		minmoves.add(move(0,1, totalMoves+1, newMap));
		minmoves.add(move(-1,0, totalMoves+1, newMap));
		minmoves.add(move(0,-1, totalMoves+1, newMap));
		
		return Collections.min(minmoves);
	}
}
