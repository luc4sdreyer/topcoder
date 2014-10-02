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
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;

import java.util.Iterator;

public class Jam2013B {
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


		//getPrimesH();
		for (int i = 0; i < T; i++) {

			//ArrayList<Integer> orders = new ArrayList<Integer>();
			//ArrayList<Long> v2 = new ArrayList<Long>();

			StringTokenizer st = new StringTokenizer(sc.nextLine()," ");
			int N = Integer.parseInt(st.nextToken());
			int X = Integer.parseInt(st.nextToken());
			int Y = Integer.parseInt(st.nextToken());
			
			//ArrayList<Long> motes = new ArrayList<Long>();
			
//			st = new StringTokenizer(sc.nextLine()," ");
//			for (int j = 0; j < N; j++) {
//				motes.add(Long.parseLong(st.nextToken()));
//			}

			output = solve(N, X, Y);

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
	
	
//
//	private static String solve(long A, int N, ArrayList<Long> motes) {
//		Collections.sort(motes); 
//		
//		Game g = new Game(A, 0, motes);
//		Stack<Game> stack = new Stack<Game>();
//		stack.add(g);
//		
//		int minMods = Integer.MAX_VALUE;
//		
//		while (!stack.empty()) {
//			Game game = stack.pop();
//			
//			if (game.motes.isEmpty()) {
//				if (game.mods < minMods) {
//					minMods = game.mods;
//				}
//				continue;
//			}
//			
//			if (game.motes.get(0) < game.armin) {
//				game.armin += game.motes.remove(0);
//				stack.add(game);
//			} else {
//				
//				//add
//				//if (game.motes.get(0) < (game.armin*2 - 1)) {
//				if (game.armin - 2 < game.motes.get(game.motes.size()-1) && game.mods <= motes.size() && game.armin > 1) {
//					Game newGame = new Game(game.armin, game.mods, game.motes);
//					newGame.armin += newGame.armin - 1;
//					newGame.mods++;
//					stack.add(newGame);
//				}
//				
//				//remove				
//				//Game newGame = new Game(game.armin, game.mods, game.motes);
//				game.mods++;
//				game.motes.remove(0);
//				stack.add(game);
//			}
//		}
//		return Integer.toString(minMods);
//		
//	}
//
//	public static class Game {
//		int mods = 0;
//		ArrayList<Long> motes;
//		long armin = 0;
//		
//		public Game(long armin, int mods, ArrayList<Long> motes) {
//			super();
//			this.armin = armin;
//			this.mods = mods;
//			this.motes = new ArrayList<Long>(motes.size());
//			for (int i = 0; i < motes.size(); i++) {
//				this.motes.add(motes.get(i));
//			}
//		}
//	}


	private static String solve(int N, int X, int Y) {
		boolean[][] board = new boolean[10000][20000];
		final int X_OFF = 10000;
		Random r = new Random();
		int totalSims = 10000000;
		int hit = 0;
		
		for (int sim = 0; sim < totalSims; sim++) {	
			//board = new boolean[10000][20000];
			int filledR = 0;
			Point prevLeft = new Point(0,0);
			Point prevRight = new Point(0,0);	
			for (int i = 0; i < N; i++) {		
				
				if (i == 0) {
					board[prevRight.y][X_OFF + prevRight.x] = true;
				}	
				
				boolean right = false;
				//if (prevRight.x == 0 || prevLeft.x == 0) {
				if (prevRight.x == 0 && prevLeft.x == 0) {
					//advance
					filledR += 2;
					prevLeft.y = 0;
					prevLeft.x = -filledR;
					prevRight.y = 0;
					prevRight.x = filledR;
					continue;
				} else if (prevLeft.x == 0) {
					right = true;
				} else if (prevRight.x == 0) {
					right = false;
				} else {
					right = r.nextBoolean();
				}
				if (right == true) {
					board[prevRight.y][X_OFF + prevRight.x] = true;
					prevRight.y++;
					prevRight.x--;
				} else {
					board[prevLeft.y][X_OFF + prevLeft.x] = true;
					prevLeft.y++;
					prevLeft.x++;
				}
				
//				StringBuilder sb = new StringBuilder();
//				for (int y = 4; y >= 0; y--) {
//					for (int x = -2; x <= 2; x++) {
//						if (((x+y) % 2) == 0) {
//							if (board[y][X_OFF + x] == true) {
//								sb.append('#');
//							} else {
//								sb.append('.');
//							}
//						} else {
//							sb.append(' ');
//						}
//					}
//					sb.append('\n');
//				}
//				sb.append("========================================");
//				System.out.println(sb.toString());
			}
			if (board[Y][X_OFF + X]) {
				hit++;
			}
			//System.out.println(hit);
			board[Y][X_OFF + X] = false;
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
//			System.out.println("========================================");
		}
		double prob = hit/(double)totalSims;
		return Double.toString(prob);
	}


	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "F:\\gcj\\2013\\1B\\B-sample";
}