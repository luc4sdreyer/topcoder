import java.util.Arrays;

public class CellularAutomata {
	final static int s = 5;
	final static int s2 = s*s;
	final static int[] dy = {-1, -1, -1, 0, 1, 1,  1,  0};
	final static int[] dx = {-1 , 0,  1, 1, 1, 0, -1, -1};
	final static boolean debug = false;

	public static void main(String[] args) throws Exception {
		final int M = 1 << (s*s);
		
		int[] next = new int[M];
		int[] colour = new int[M];
		int count_colour = 0;
		boolean[] immortal = new boolean[M];
		Arrays.fill(next, -1);
		Arrays.fill(colour, -1);
		
		for (int i = 0; i < M; i++) {
			int grid = i;
			int my_group = 0;
			if (debug) {
				System.out.println(i);
				printGrid(i);
			}
			while (next[grid] == -1) {
				next[grid] = timeStep(grid);
				grid = next[grid];
				if (debug) {
					printGrid(grid);
				}
			}
			if (debug) {
				System.out.println();
			}

			int final_grid = next[grid];
			boolean final_state_immortal = false;
			if (colour[final_grid] != -1) {
				final_state_immortal = immortal[final_grid];
				my_group = colour[final_grid];
			} else if (final_grid != 0) {
				if (colour[final_grid] != -1) {
					my_group = colour[final_grid];
				} else {
					my_group = count_colour;
					count_colour++;
				}
				final_state_immortal = true;
			}

			if (debug) {
				printGrid(i);
				printGrid(final_grid);
				System.out.println(final_state_immortal);
				System.out.println();
			}

			grid = i;
			if (i == final_grid) {
				immortal[grid] = final_state_immortal;
				colour[grid] = my_group;
			}

			while (grid != final_grid) {
				immortal[grid] = final_state_immortal;
				colour[grid] = my_group;
				grid = next[grid];
			}
			
			if (colour[final_grid] == -1) {
				immortal[final_grid] = final_state_immortal;
				colour[final_grid] = my_group;
			}
		}
		
		int[] visited = new int[M];
		for (int i = 0; i < M; i++) {
			int grid = i;
			while (visited[i] != i) {
				visited[i] = i;
				int next_grid = timeStep(grid);
				if (next[grid] != next_grid) {
					throw new Exception();
				}
				if (colour[grid] != colour[i]) {
					throw new Exception();
				}
				if (immortal[grid] != immortal[i]) {
					throw new Exception();
				}
				grid = next_grid;
			}
		}
		
		int num_immortal= 0;
		for (int i = 0; i < immortal.length; i++) {
			if (immortal[i]) {
				num_immortal += 1;
			}
		}
		
		System.out.println(num_immortal);
		System.out.println(count_colour);
	}
	
	public static int timeStep(int grid) {
		int next_grid = 0;
		for (int y = 0; y < s; y++) {
			for (int x = 0; x < s; x++) {
				int neighbours = 0;
				for (int d = 0; d < dy.length; d++) {
					int posX = x + dx[d];
					int posY = y + dy[d];
					if (posX >= 0 && posX < s && posX >= 0 && posX < s && isSet(grid, posX + posY * s)) {
						neighbours++;
					}
					if (neighbours > 2) {
						break;
					}
				}
				if (neighbours == 1 || neighbours == 2) {
					next_grid = bitSet(next_grid, x + y * s);
				}
			}
		}
		return next_grid;
	}
	
	public static void printGrid(int grid) {
		for (int y = 0; y < s; y++) {
			for (int x = 0; x < s; x++) {
				System.out.print(isSet(grid, x + y * s) ? "X" : " ");
			}
			System.out.println("|");
		}
		for (int x = 0; x < s; x++) {
			System.out.print("=");
		}
		System.out.println("");
	}
	
	public static boolean isSet(int x, int n) {
		return ((1 << n) & x) != 0; 
	}
	
	public static int bitSet(int x, int n) {
		return ((1 << n) | x); 
	}
	
	public static int bitClear(int x, int n) {
		return ((~(1 << n)) & x); 
	}
}
