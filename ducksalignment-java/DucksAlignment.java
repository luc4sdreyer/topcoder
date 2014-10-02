import java.util.ArrayList;

public class DucksAlignment {
	
	public int minimumTime(String[] grid) {
		int min = Integer.MAX_VALUE;
		char[][] c = new char[grid.length][grid[0].length()];
		for (int i = 0; i < c.length; i++) {
			c[i] = grid[i].toCharArray();
		}

		int numDucks = 0;
		for (int x = 0; x < c[0].length; x++) {
			for (int y = 0; y < c.length; y++) {
				if (c[y][x] == 'o') {
					numDucks++;
				}
			}
		}
		ArrayList<int[]> ducks = new ArrayList<>();
		for (int x = 0; x < c[0].length; x++) {
			for (int y = 0; y < c.length; y++) {
				if (c[y][x] == 'o') {
					int[] t = {x, y};
					ducks.add(t);
				}
			}
		}

		for (int row = 0; row < c.length; row++) {			
			for (int column = 0; column < c[0].length - numDucks+1; column++) {
				int time = 0;
				ArrayList<int[]> ducksCopy = new ArrayList<>();
				for (int i = 0; i < ducks.size(); i++) {
					ducksCopy.add(ducks.get(i).clone());
				}
				
				int counter = 0;
				while (!ducksCopy.isEmpty()) {
					int idx = removeClosest(column + counter, row, ducksCopy);
					int dist = Math.abs(ducksCopy.get(idx)[0] - (column + counter)) + Math.abs(ducksCopy.get(idx)[1] - row);
					ducksCopy.remove(idx);
					counter++;
					
					time += dist;	
				}
				min = Math.min(min, time);
			}
		}
	
		for (int column = 0; column < c[0].length; column++) {
			for (int row = 0; row < c.length - numDucks+1; row++) {		
				int time = 0;
				ArrayList<int[]> ducksCopy = new ArrayList<>();
				for (int i = 0; i < ducks.size(); i++) {
					ducksCopy.add(ducks.get(i).clone());
				}
				
				int counter = 0;
				while (!ducksCopy.isEmpty()) {
					int idx = removeClosest(column, row + counter, ducksCopy);
					int dist = Math.abs(ducksCopy.get(idx)[0] - (column)) + Math.abs(ducksCopy.get(idx)[1] - (row + counter));
					ducksCopy.remove(idx);
					counter++;
					
					time += dist;	
				}
				min = Math.min(min, time);
			}
		}
		
		return min;
	}

	private int removeClosest(int x, int y, ArrayList<int[]> ducksCopy) {
		int min = Integer.MAX_VALUE;
		int idx = 0;
		for (int i = 0; i < ducksCopy.size(); i++) {
			int dist = Math.abs(ducksCopy.get(i)[0] - x) + Math.abs(ducksCopy.get(i)[1] - y);
			if (dist < min) {
				min = dist;
				idx = i;
			}
		}
		return idx;
	}

}
