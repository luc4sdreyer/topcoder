package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import base.Tron.Po;
import base.Tron.*;

public class TronTest {
	static final int map_size = 15;
	public static void main(String[] args) {
//		testMt();
		testSimMove();
	}
	
	public static void testSimMove() {
		int numTests = 50;
		Random rand = new Random(1);
		long t1 = System.currentTimeMillis();
		int won = 0;
		int lost = 0;
		int tied = 0;
		
		for (int i = 0; i < numTests; i++) {
			int size = 15;
			Po[] start = new Po[2];
			start[0] = new Po(1, size/2);
			start[1] = new Po(size-2, size/2);
			byte[][] bm = new byte[15][15];
			
			TM map = new TM(bm, start[0], start[1]);
			
			MgT bot1 = new MgT(0);
			bot1.timeLimit = 20;
			bot1.doTimeLimit = false;
			bot1.maxdepth = 8;
			
			Po old1 = start[0];
			Po old2 = start[1];
			
			while (true) {
				System.out.println(map);
				ArrayList<Po> p1 = map.getAllMoves(old1);
				ArrayList<Po> p2 = map.getAllMoves(old2);
				
				if (p1.isEmpty() && p2.isEmpty()) {
					tied++;
					break;
				} else if (p1.isEmpty()) {
					lost++;
					break;
				} else if (p2.isEmpty()) {
					won++;
					break;
				}
				
				Po move1 = bot1.move(map);
				if (!map.canMove(move1, 0)) {
					lost++;
					break;
				}
				
				Po move2 = p2.get(rand.nextInt(p2.size()));
//				Collections.sort(p2, new Comparator<Po>() {
//					@Override
//					public int compare(Po o1, Po o2) {
//						return Integer.compare(o1.x, o2.x);
//					}
//				});
//				Po move2 = p2.get(0);
				if (!map.canMove(move2, 1)) {
					won++;
					break;
				}
				
				if (move1.equals(move2)) {
					tied++;
					break;
				}
				
				map.move(move1, 0);
				map.move(move2, 1);
				old1 = move1;
				old2 = move2;
			}
		}
		System.out.println("Out of " + numTests + " games, won / tied / lost: [" + won + " - " + tied + " - " + lost + "]");
		System.out.println("Time: " + ((System.currentTimeMillis() - t1)/1000));
	}
	
	public static void testMt() {
		int numTests = 30;
		double avgEff = 0;
		Random rand = new Random(1);
		long t1 = System.currentTimeMillis();
		
		for (int i = 0; i < numTests; i++) {
			int size = 15;
			Po[] start = new Po[2];
			start[0] = new Po(1, size/2);
			start[1] = new Po(size-2, size/2);
			byte[][] bm = new byte[15][15];
			int randRange = 3;
			
			int toFill = randRange;
			int filled = 0;
			while (filled < toFill) {
				int rx = 1 + rand.nextInt(size-2);
				int ry = 1 + rand.nextInt(size-2);
				boolean valid = true;
				for (int j = 0; j < start.length; j++) {
					if (rx == start[j].x) {
						valid = false;
					}
					if (ry == start[j].y) {
						valid = false;
					}
				}
				if (valid) {
					bm[ry][rx] = 1;
					filled++;
				}
			}
			
			TM map = new TM(bm, start[0], start[1]);
			
			MgT bot1 = new MgT(0);
			bot1.timeLimit = 10;
			int step = 0;
			int space = (size - 2) * (size - 2) - 2 - toFill;
			map.stt = State.Survival;
			Po old = start[0];
			
			while (true) {
				ArrayList<Po> p = map.getAllMoves(old);
				if (p.isEmpty()) {
					break;
				}
				Po move1 = bot1.nextSurvivalMove(map, p, false, false);
//				Po move2 = bot1.nextSurvivalMove3(map, p, false, false);
//				if (!move1.equals(move2)) {
//					System.out.println(map);
//					System.out.println();
//					move1 = bot1.nextSurvivalMove(map, p, false, false);
//					move2 = bot1.nextSurvivalMove3(map, p, false, false);
//				}	
				if (!map.move(move1, 0)) {
					break;
				}
				map.move(move1, 0);
				old = move1;
//				System.out.println(map);
				step++;
			}
			
			avgEff += step * 1.0 / space;
		}
		avgEff = (avgEff / numTests) * 100;  
		
		System.out.printf("Avg Efficiency: %.2f%%\n", avgEff);
		System.out.println("Time: " + ((System.currentTimeMillis() - t1)/1000));
	}
}