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

public class Jam2013C {
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
			int X = Integer.parseInt(st.nextToken());
			int Y = Integer.parseInt(st.nextToken());

//			String name = st.nextToken();
//			int n = Integer.parseInt(st.nextToken());

			//ArrayList<Long> motes = new ArrayList<Long>();

			//			st = new StringTokenizer(sc.nextLine()," ");
			//			for (int j = 0; j < N; j++) {
			//				motes.add(Long.parseLong(st.nextToken()));
			//			}

			output = solve(X, Y);
			//output = solve(name, n);

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


	private static String solve(String name, int n) {
		long value = 0;
		for (int i = 0; i < name.length(); i++) {
			String shorterName = name.substring(i, name.length());
			//System.out.println(shorterName);
			for (int j = n; j <= shorterName.length(); j++) {
				String sub = shorterName.substring(0, j);
				//System.out.println("\t"+sub);
				int longest = 1;
				int max = 0;
				boolean prevConst = false;
				for (int k = 0; k < sub.length(); k++) {
					if (n != 1) {
						if ((sub.charAt(k) != 'a') &&
								(sub.charAt(k) != 'e') &&
								(sub.charAt(k) != 'i') &&
								(sub.charAt(k) != 'o') &&
								(sub.charAt(k) != 'u')) {
							if (!prevConst) {
								prevConst = true;
								longest = 1;
							} else {
								longest++;
								if (longest > max) {
									max = longest;
								}
							}
						} else {
							prevConst = false;
							longest = 0;
						}						
					} else {
						if ((sub.charAt(k) != 'a') &&
								(sub.charAt(k) != 'e') &&
								(sub.charAt(k) != 'i') &&
								(sub.charAt(k) != 'o') &&
								(sub.charAt(k) != 'u')) {
							max = 1;
						}
					}
				}
				if (max >= n) {
					value++;
					//System.out.println("value:"+value);
				}
			}
		}
		return Long.toString(value);
	}

	//astar a* a-star search
	public static class PointS {
		Point p;
		int steps;
		char direction;
		public PointS(int X, int Y, int steps, char direction) {
			super();
			this.p = new Point(X,Y);
			this.steps = steps;
			this.direction = direction;
		}
		public int getDistance(PointS x) {
			return Math.abs(x.p.x - this.p.x) + Math.abs(x.p.y - this.p.y);
		}
		public int getFScore(PointS x) {
			return this.getDistance(x) + steps;
		}


		@Override
		public int hashCode() {
			int hash = 1;
			hash = hash * 17 + p.x;
			hash = hash * 31 + p.y;
			//hash = hash * 13 + steps;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PointS other = (PointS) obj;
			if (p == null) {
				if (other.p != null)
					return false;
			} else if (p.x != other.p.x) {
				return false;
			} else if (p.y != other.p.y)
				return false;
			//if (steps != other.steps)
			//	return false;
			return true;
		}
		
		public String toString() {
			return "("+this.p.x+","+this.p.y+"):"+this.steps+","+this.direction;
		}
	}

	public static class PointSComparator implements Comparator<PointS>
	{
		@Override
		public int compare(PointS x, PointS y)
		{
			// Assume neither string is null. Real code should
			// probably be more robust
			int xF = x.getFScore(y);
			int yF = y.getFScore(x);
			if (xF < yF)
			{
				return -1;
			}
			if (xF > yF)
			{
				return 1;
			}
			return 0;
		}
	}

	@SuppressWarnings("unused")
	private static String solve(int X, int Y) {
		//		StringBuilder sb = new StringBuilder();
		//		int prev = 1;
		//		int meX = 0;
		//		int meY = 0;
		//		while (meX != X) {
		//			if (meX > 0 || (meX == 0 && X > 0)) {
		//				meX -= prev;
		//				prev++;
		//				sb.append('W');
		//			} else {
		//				meX += prev;
		//				prev++;
		//				sb.append('E');
		//			}
		//			System.out.println(meX + " " +X);
		//		}
		//		while (meY != Y) {
		//			if (meY > 0 || (meY == 0 && Y > 0)) {
		//				meY -= prev;
		//				prev++;
		//				sb.append('S');
		//			} else {
		//				meY += prev;
		//				prev++;
		//				sb.append('N');
		//			}
		//			System.out.println(meY + " " +Y);
		//		}
		//		return sb.toString();
		PointS start = new PointS(0, 0, 0, 'X');
		PointS goal = new PointS(X, Y, 0, 'X');
		Comparator<PointS> comparator = new PointSComparator();

		HashSet<PointS> closedset = new HashSet<PointS>();
		HashSet<PointS> openset = new HashSet<PointS>();
		HashMap<PointS, PointS> came_from = new HashMap<PointS, PointS>();
		
		
		PriorityQueue<PointS> pq = new PriorityQueue<PointS>(10, comparator);
		pq.add(start);
		openset.add(start);
		ArrayList<PointS> path = new ArrayList<PointS>();

		//function A*(start,goal)
		//closedset := the empty set    // The set of nodes already evaluated.
		//came_from := the empty map    // The map of navigated nodes.

		//g_score[start] := 0    // Cost from start along best known path.
		// Estimated total cost from start to goal through y.
		//f_score[start] := g_score[start] + heuristic_cost_estimate(start, goal)

		while (!pq.isEmpty()) {
			PointS current = pq.poll();
			openset.remove(current);
			//System.out.println("current: "+current);
			if (current.p.x == goal.p.x && current.p.y == goal.p.y) {
				//System.out.println("DONE!");
				path.add(current);
				PointS prev = came_from.get(current);
				while (!prev.equals(start)) {					
					path.add(prev);
					prev = came_from.get(prev);
				}
				break;
			}

			closedset.add(current);
			PointS neigh0 = new PointS(current.p.x+(current.steps+1), current.p.y, current.steps, 'E');
			PointS neigh1 = new PointS(current.p.x-(current.steps+1), current.p.y, current.steps, 'W');
			PointS neigh2 = new PointS(current.p.x, current.p.y+(current.steps+1), current.steps, 'N');
			PointS neigh3 = new PointS(current.p.x, current.p.y-(current.steps+1), current.steps, 'S');
			ArrayList<PointS> neighs = new  ArrayList<PointS>();
			neighs.add(neigh0);
			neighs.add(neigh1);
			neighs.add(neigh2);
			neighs.add(neigh3);
			for (PointS neigh : neighs) {
				int tentative_g_score = current.steps + 1;
				if (closedset.contains(neigh)) {
					if (current.steps >= neigh.steps) {
						continue;
					}
				}
				if (!openset.contains(neigh) || tentative_g_score < neigh.steps) {
					came_from.put(neigh, current);
					neigh.steps = tentative_g_score;
					if (!openset.contains(neigh)) {
						openset.add(neigh);
						pq.add(neigh);
					}
				}
			}
		}
		
		if (path.size() != 0) {
			Collections.reverse(path);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < path.size(); i++) {
				sb.append(path.get(i).direction);
			}
			return sb.toString();
		} else {
			return "can't find";
		}
		 
	}

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "F:\\gcj\\2013\\1C\\B-sample";
}