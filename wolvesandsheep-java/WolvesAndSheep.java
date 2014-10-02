import java.util.ArrayList;

public class WolvesAndSheep {

	public int getmin(String[] field) {
		int[][] grid = new int[field.length][field[0].length()];

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (field[y].charAt(x) == '.') {
					grid[y][x] = 0;
				} else if (field[y].charAt(x) == 'S') {
					grid[y][x] = 1;
				} else {
					grid[y][x] = 2;
				}
			}
		}
		
		ArrayList<Integer> fencesX = new ArrayList<Integer>();
		for (int i = 1; i < grid[0].length; i++) {
			fencesX.add(i);
		}
		ArrayList<Integer> fencesY = new ArrayList<Integer>();
		for (int i = 1; i < grid.length; i++) {
			fencesY.add(i);
		}
		
		for (int j = 0; j < 100; j++) {
			for (int i = 0; i < fencesY.size(); i++) {
				boolean remove = true;
				int fence = fencesY.get(i);
				for (int x = 0; x < grid[0].length; x++) {
					if (grid[fence-1][x] > 0 && grid[fence][x] > 0 
							&& grid[fence-1][x] != grid[fence][x]) {
						remove = false;
						break;
					}
				}
				if (remove) {
					fencesY.remove(i--);
					for (int x = 0; x < grid[0].length; x++) {
						if (grid[fence-1][x] == 0 && grid[fence][x] > 0) {
							grid[fence-1][x] = grid[fence][x];
						}
						if (grid[fence][x] == 0 && grid[fence-1][x] > 0) {
							grid[fence][x] = grid[fence-1][x];
						}
					}
				}
			}
			
			for (int i = 0; i < fencesX.size(); i++) {
				boolean remove = true;
				int fence = fencesX.get(i);
				for (int y = 0; y < grid.length; y++) {
					if (grid[y][fence-1] > 0 && grid[y][fence] > 0 
							&& grid[y][fence-1] != grid[y][fence]) {
						remove = false;
						break;
					}
				}
				if (remove) {
					fencesX.remove(i--);
					for (int y = 0; y < grid.length; y++) {
						if (grid[y][fence-1] == 0 && grid[y][fence] > 0) {
							grid[y][fence-1] = grid[y][fence];
						}
						if (grid[y][fence] == 0 && grid[y][fence-1] > 0) {
							grid[y][fence] = grid[y][fence-1];
						}
					}
				}
			}
		}
		
		return fencesX.size() + fencesY.size();
	}

}
