package dynamicProgramming;

import java.util.Arrays;

public class DynamicProg3 {
	public static void main(String[] args) {
		int[][] grid = {
			{7,3,8,1,5,3,8,1,5},
			{0,9,8,2,3,4,0,9,8},
			{0,2,0,4,8,2,0,4,9},
			{9,3,0,9,3,4,0,9,3},
			{0,3,0,5,8,9,1,4,5},
			{3,9,2,8,6,0,2,8,9},
			{0,9,8,2,3,4,0,9,8},
			{1,9,8,3,4,0,9,8,3}
		};
		
		int[][] bestSolution = new int[grid.length][grid[0].length];
		int[][] bestDirection = new int[grid.length][grid[0].length];
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				bestSolution[y][x] = grid[y][x];
				if (x > 0 && bestSolution[y][x-1] + grid[y][x] > bestSolution[y][x]) {
					bestSolution[y][x] = bestSolution[y][x-1] + grid[y][x];
					bestDirection[y][x] = 1;
				}
				if (y > 0 && bestSolution[y-1][x] + grid[y][x] > bestSolution[y][x]) {
					bestSolution[y][x] = bestSolution[y-1][x] + grid[y][x];
					bestDirection[y][x] = 2;
				}
			}
		}

		for (int y = 0; y < grid.length; y++) {
			System.out.println(Arrays.toString(bestSolution[y]));
		}
		System.out.println();
		for (int y = 0; y < grid.length; y++) {
			String line = "";
			for (int x = 0; x < grid[0].length; x++) {
				if (bestDirection[y][x] == 1) {
					line += "> ";
				} else if (bestDirection[y][x] == 2) {
					line += "v ";
				} else if (bestDirection[y][x] == 0) {
					line += ". ";
				}
			}
			System.out.println(line);
		}
	}
}
