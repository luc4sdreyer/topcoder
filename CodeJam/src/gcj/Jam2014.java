package gcj;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

import java.util.Iterator;

public class Jam2014 {
	//10^4 <  Short.MAX_VALUE (2^15)   < 10^5
	//10^9 <  Integer.MAX_VALUE (2^31) < 10^10
	//10^18 < Long.MAX_VALUE (2^63)    < 10^20


	public static void main(String[] args) throws NumberFormatException, IOException {		
		Scanner sc = new Scanner(new FileReader(filename+".in"));
		String output = null;
		String outputfile = filename + ".out";

		FileOutputStream Output = new FileOutputStream(outputfile);
		PrintStream file2 = new PrintStream(Output);

		boolean after25 = false;
		boolean after50 = false;
		boolean after75 = false;

		//int T = sc.nextInt();
		int T = Integer.parseInt(sc.nextLine());
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);


		for (int i = 0; i < T; i++) {
			
			int r = sc.nextInt();
			int c = sc.nextInt();
			int m = sc.nextInt();
			
			output = solve2(Math.max(r, c), Math.min(r, c), m);

			file2.println("Case #" + (i+1) + ": "+output);
			if ((100*(i+1)/T >= 25) && (!after25)) {
				System.out.println(Calendar.getInstance().getTime()+" - 25% done");				after25 = true;
			} else if ((100*(i+1)/T >= 50) && (!after50)) {
				System.out.println(Calendar.getInstance().getTime()+" - 50% done");				after50 = true;
			} else if ((100*(i+1)/T >= 75) && (!after75)) {
				System.out.println(Calendar.getInstance().getTime()+" - 75% done");				after75 = true;
			}
		}
		sc.close();		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}

	private static String solve2(int xLength, int yLength, int m) {
		int[][] grid = new int[yLength][xLength];
		int maxMines = xLength*yLength;
		if (m == maxMines-1) {
			grid[0][0] = 1;
			return gridToString(grid);
		} else if (m >= maxMines) {
			return "Impossible";
		}
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (x < 2 && y < 2) {
					grid[y][x] = 1;
					maxMines--;
				}
			}
		}
		if (m == maxMines) {
			return gridToString(grid);
		} else if (m > maxMines) {
			return "Impossible";
		}
		if (yLength == 1 || yLength == 2) {
			int t = 0;
			while (t < xLength && m < maxMines) {
				grid[0][t] = 1;
				if (yLength == 2) {
					grid[1][t] = 1;
				}
				t++;
				maxMines -= yLength;
			}
			if (m == maxMines) {
				return gridToString(grid);
			} else if (m > maxMines) {
				return "Impossible";
			} else {
				System.err.println("OOOPS");
			}
		}
		
		return null;
	}

	private static String gridToString(int[][] grid) {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (y == 0 && x == 0) {
					sb.append('c');
				} else if (grid[y][x] == 1) {
					sb.append('.');
				} else if (grid[y][x] == 0) {
					sb.append('*');
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static String solve(int n, ArrayList<Double> naomiOriginal,
			ArrayList<Double> kenOriginal) {
		Collections.sort(naomiOriginal);
		Collections.sort(kenOriginal);
		
		ArrayList<Double> naomi = new ArrayList<Double>();
		for (Double d : naomiOriginal) { naomi.add(d); }
		ArrayList<Double> ken = new ArrayList<Double>();
		for (Double d : kenOriginal) { ken.add(d); }
		
		int warScore = 0;
		while (!naomi.isEmpty()) {
			double nBlock = naomi.remove(0);
			double kBlock = -1;
			for (int i = 0; i < ken.size(); i++) {
				if (ken.get(i) > nBlock) {
					kBlock = ken.remove(i);
					break;
				}
			}
			if (kBlock == -1) {
				ken.remove(0);
				warScore++;
			}
		}

		int maxFakeWarScore = 0;
		for (int j = 0; j < n; j++) {
			naomi.clear();
			ken.clear();
			for (Double d : naomiOriginal) { naomi.add(d); }
			for (Double d : kenOriginal) { ken.add(d); }

			int fakeWarScore = 0;
			for (int i = 0; i < j; i++) {
				if (naomi.remove(0) > ken.remove(ken.size()-1)) {
					fakeWarScore++;
				}
			}
			
			while (!naomi.isEmpty()) {
				double nBlock = naomi.remove(naomi.size()-1);
				double kBlock = -1;
				for (int i = 0; i < ken.size(); i++) {
					if (ken.get(i) > nBlock) {
						kBlock = ken.remove(i);
						break;
					}
				}
				if (kBlock == -1) {
					ken.remove(ken.size()-1);
					fakeWarScore++;
				}
			}
			if (fakeWarScore > maxFakeWarScore) {
				maxFakeWarScore = fakeWarScore;
			} 
		}
		
		return maxFakeWarScore + " " + warScore;
	}

	private static String solve(double c, double f, double x) {
		int numFarms = 0;
		double totalTime = 0;
		double timeTillGoal = x/(2 + numFarms * f);
		double timeTillBuyFarm = c/(2 + numFarms * f);
		double nextTimeTillGoal = timeTillBuyFarm + x/(2 + (numFarms+1) * f);
		while (nextTimeTillGoal < timeTillGoal) {
			totalTime += timeTillBuyFarm;
			numFarms++;
			timeTillGoal = x/(2 + numFarms * f);
			timeTillBuyFarm = c/(2 + numFarms * f);
			nextTimeTillGoal = timeTillBuyFarm + x/(2 + (numFarms+1) * f);
		}
		totalTime += timeTillGoal;
		
		String out = String.format("%.7f", totalTime); 
		return out;
	}

	@SuppressWarnings("unused")
	private static String solve(int r1, ArrayList<Integer> row1,
			int r2, ArrayList<Integer> row2) {
		HashSet<Integer> s1 = new HashSet<Integer>(row1);
		HashSet<Integer> s2 = new HashSet<Integer>(row2);
		s1.retainAll(s2);
		if (s1.size() == 1) {
			Object[] a = s1.toArray();
			return ((Integer)a[0]).toString();
		} else if (s1.size() > 1) {
			return "Bad magician!";
		} else {
			return "Volunteer cheated!";
		}
	}

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "F:\\gcj\\2014\\Q\\C-sample";
}