package gcj;

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
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import java.util.Iterator;

public class Jam2013A {
	//10^4 <  Short.MAX_VALUE (2^15)   < 10^5
	//10^9 <  Integer.MAX_VALUE (2^31) < 10^10
	//10^18 < Long.MAX_VALUE (2^63)    < 10^20


	public static void main(String[] args) throws NumberFormatException, IOException {		
		Scanner sc = new Scanner(new FileReader(filename+".in"));
		String output;
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
			long A = Long.parseLong(st.nextToken());
			long B = Long.parseLong(st.nextToken());
//			int[][] lawn = new int[Y][X];
//			
//			for (int y = 0; y < Y; y++) {
//				st = new StringTokenizer(sc.nextLine()," ");
//				for (int x = 0; x < X; x++) {
//					lawn[y][x] = Integer.parseInt(st.nextToken());
//				}			
//			}

			output = solve(A, B);

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
	/*
	private static String solve(String nextLine) {
		char[] a = {'f','g','d','e','b','c','a','n','o','l','m','j','k','h','i','w','v','u','t','s','r','q','p','z','y','x'};
		char[] b = {'c','v','s','o','h','e','y','b','k','g','l','u','i','x','d','f','p','j','w','n','t','z','r','q','a','m'};
		HashMap<Character,Character> lib = new HashMap<Character,Character>();
		for (int i = 0; i< a.length; i++) {
			if(!lib.containsKey(a[i])) {
				lib.put(a[i], b[i]);
			}
		}
		
		StringBuilder out = new StringBuilder();
		for (int i = 0; i< nextLine.length(); i++) {
			if (nextLine.charAt(i) == ' ') {out.append(' ');} else {
			out.append(lib.get(nextLine.charAt(i)));}
		}
		return out.toString();
	}*/

	/*
	private static String solve(int a, int b) {
		int length = String.valueOf(b).length();
		if (length == 1) {
			return "0";
		}
		HashSet<Integer> found = null;
		long total = 0;
		for (int i = a; i < b; i++) {
			found = null;
			String s = String.valueOf(i);
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < s.length(); j++) {
				sb.append(s.charAt((j+s.length()-1)%s.length()));
			}
			int shift = Integer.parseInt(sb.toString());
			for (int j = 1; j < length; j++) {
				
				if (shift > i && shift <= b) {
					if (found == null) {
						found = new HashSet<Integer>();
					}
					if (!found.contains(shift)) {
						found.add(shift);
						//System.out.println(i+","+shift);
						total++;
					}
				}
				
				//s = String.valueOf(shift);
				sb = new StringBuilder();
				for (int k = 0; k < s.length(); k++) {
					sb.append(s.charAt((k+s.length()-(1+j))%s.length()));
				}
				shift = Integer.parseInt(sb.toString());
			}
		}
		return String.valueOf(total);
	}*/

	/*
	private static String solve(int n, int s, int p, ArrayList<Integer> googlers) {
		int max= 0;
		Collections.sort(googlers);
		Collections.reverse(googlers);
		int pmin = (3*p-4 >= 1) ? (3*p-4) : 1;
		int pmax = (3*p-2 >= 1) ? (3*p-2) : 1;
		for (int j = 0; j< n; j++) {
			if (p == 0) {
				max++;
			} else if ((googlers.get(j)) >= pmax) {
				max++;
			} else if (((googlers.get(j)) >= pmin) && (s > 0)) {
				s--;
				max++;
			}
		}
		
		return String.valueOf(max);
	}*/

//	private static String solve(char[][] map) {
//		int scoreX = 0;
//		int scoreO = 0;
//		int scoreT = 0;
//		for (int y = 0; y < map.length; y++) {
//			for (int x = 0; x < map[0].length; x++) {
//				if (map[y][x] == 'X') {
//					scoreX++;
//				} else if (map[y][x] == 'O') {
//					scoreO++;
//				} else if (map[y][x] == 'T') {
//					scoreT++;
//				} 
//			}
//			if ((scoreX == 3 && scoreT == 1) || (scoreX == 4 && scoreT == 0)) {
//				return "X won";
//			} else if ((scoreO == 3 && scoreT == 1) || (scoreO == 4 && scoreT == 0)) {
//				return "O won";
//			}
//			scoreX = 0;
//			scoreO = 0;
//			scoreT = 0;
//		}
//		
//		for (int x = 0; x < map[0].length; x++) {
//			for (int y = 0; y < map.length; y++) {
//				if (map[y][x] == 'X') {
//					scoreX++;
//				} else if (map[y][x] == 'O') {
//					scoreO++;
//				} else if (map[y][x] == 'T') {
//					scoreT++;
//				} 
//			}
//			if ((scoreX == 3 && scoreT == 1) || (scoreX == 4 && scoreT == 0)) {
//				return "X won";
//			} else if ((scoreO == 3 && scoreT == 1) || (scoreO == 4 && scoreT == 0)) {
//				return "O won";
//			}
//			scoreX = 0;
//			scoreO = 0;
//			scoreT = 0;
//		}
//		
//		int x = 0;
//		int y = 0;
//		while (x < 4) {
//			if (map[y][x] == 'X') {
//				scoreX++;
//			} else if (map[y][x] == 'O') {
//				scoreO++;
//			} else if (map[y][x] == 'T') {
//				scoreT++;
//			}
//			y++;
//			x++;
//		}
//		if ((scoreX == 3 && scoreT == 1) || (scoreX == 4 && scoreT == 0)) {
//			return "X won";
//		} else if ((scoreO == 3 && scoreT == 1) || (scoreO == 4 && scoreT == 0)) {
//			return "O won";
//		}
//		scoreX = 0;
//		scoreO = 0;
//		scoreT = 0;
//		
//		x = 0;
//		y = 3;
//		while (x < 4) {
//			if (map[y][x] == 'X') {
//				scoreX++;
//			} else if (map[y][x] == 'O') {
//				scoreO++;
//			} else if (map[y][x] == 'T') {
//				scoreT++;
//			}
//			y--;
//			x++;
//		}
//		if ((scoreX == 3 && scoreT == 1) || (scoreX == 4 && scoreT == 0)) {
//			return "X won";
//		} else if ((scoreO == 3 && scoreT == 1) || (scoreO == 4 && scoreT == 0)) {
//			return "O won";
//		}
//		scoreX = 0;
//		scoreO = 0;
//		scoreT = 0;
//		
//		for (x = 0; x < map[0].length; x++) {
//			for (y = 0; y < map.length; y++) {
//				if (map[y][x] == '.') {
//					return "Game has not completed";
//				}
//			}
//		}
//		
//		return "Draw";
//	}
	
//	private static String solve(int[][] lawn) {
//		HashSet<Integer> levelSet = new HashSet<Integer>(); 
//		for (int y = 0; y < lawn.length; y++) {
//			for (int x = 0; x < lawn[0].length; x++) {
//				levelSet.add(lawn[y][x]);
//			}
//		}
//		
//		ArrayList<Integer> levels = new ArrayList<Integer>();
//		for (Integer i : levelSet) {
//			levels.add(i);
//		}
//		Collections.sort(levels);
//		
//		//System.out.println("==================================================");
//		for (int i = 0; i < levels.size(); i++) {
//			boolean[][] checkmap = new boolean[lawn.length][lawn[0].length];
//
//			for (int y = 0; y < lawn.length; y++) {
//				for (int x = 0; x < lawn[0].length; x++) {
//					//System.out.print(lawn[y][x] + " ");
//				}
//				//System.out.println();
//			}
//			//System.out.println();
//			
//			for (int y = 0; y < lawn.length; y++) {
//				for (int x = 0; x < lawn[0].length; x++) {
//					if (lawn[y][x] == levels.get(i)) {
//						checkmap[y][x] = true;
//					}
//				}
//			}
//			
//			for (int y = 0; y < lawn.length; y++) {
//				int max = 0;
//				for (int x = 1; x < lawn[0].length; x++) {
//					if (lawn[y][x] != lawn[y][x-1]) {
//						break;
//					} else {
//						if (max == 0) {
//							max = 2;
//						} else {						
//							max++;
//						}
//					}
//				}
//				if (max == lawn[0].length || lawn[0].length == 1) {
//					for (int x = 0; x < lawn[0].length; x++) {
//						checkmap[y][x] = false;
//					}
//				}
//			}
//			
//			for (int x = 0; x < lawn[0].length; x++) {
//				int max = 0;
//				for (int y = 1; y < lawn.length; y++) {
//					if (lawn[y][x] != lawn[y-1][x]) {
//						break;
//					} else {
//						if (max == 0) {
//							max = 2;
//						} else {						
//							max++;
//						}
//					}
//				}
//				if (max == lawn.length || lawn.length == 1) {
//					for (int y = 0; y < lawn.length; y++) {
//						checkmap[y][x] = false;
//					}
//				}
//			}
//			
//			for (int y = 0; y < lawn.length; y++) {
//				for (int x = 0; x < lawn[0].length; x++) {
//					if (checkmap[y][x]) {
//						return "NO";
//					}
//				}
//			}
//			
//			if (i + 1 < levels.size())
//			for (int y = 0; y < lawn.length; y++) {
//				for (int x = 0; x < lawn[0].length; x++) {
//					if (lawn[y][x] == levels.get(i)) {
//						lawn[y][x] = levels.get(i+1);
//					}
//				}
//			}
//		}
//		
//		return "YES";
//	}

	private static String solve(long a, long b) {
		long rootA = (long) Math.ceil(Math.sqrt(a));
		long rootB = (long) Math.floor(Math.sqrt(b));
		
		long numPalin = 0;
		
		if (a <= 1) {
			numPalin++;
		}
		
//		if (a < 10) {
//			for (int i = (int) a; i < Math.min(rootB, 10); i++) {
//				// is palin^2 palin?
//				long palinSq = (long) Math.pow(i, 2);
//				boolean isPalin = true;
//				long sqLength = (long) Math.floor(Math.log10(palinSq))+1;
//				for (long j = 0; j < sqLength / 2; j++) {
//					if (((palinSq % (long)Math.pow(10, sqLength - j + 1)) / (long)Math.pow(10, sqLength - j)) 
//							!= ((palinSq % (long)Math.pow(10, j+1)) / (long)Math.pow(10, j)))	{
//						isPalin = false;
//						break;
//					}
//				}
//				
//				if (isPalin) {
//					//numPalin++;
//				}
//			}
//		}
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		
		for (long order = (long) Math.floor(Math.log10(rootA)); order < (long) Math.ceil(Math.log10(rootB)); order++) {
			long length = (order) / 2; 
			//length += (order % 2) == 1 ? 1 : 0;
			
			//length--;
			long start = (long) Math.pow(10, length);
			long end = (long) Math.pow(10, length+1);
			
//			if (order == (long) Math.floor(Math.log10(rootA))) {
//				start = (long) Math.ceil(rootA / Math.pow(10, Math.floor((Math.log10(rootA))) - length));
//			}
//			if (order + 1 == (long) Math.ceil(Math.log10(rootB))) {
//				end = (long) Math.floor(rootB / Math.pow(10, Math.floor((Math.log10(rootB))) - length - 1));
//			}
			
			for (long i = start; i < end; i++) {
				//Get palin of i:
				long palin = 0;
				for (long j = 0; j < order - length; j++) {
					long s = ((i % (long)Math.pow(10, length - j + 1))) / ((long)Math.pow(10, length - j));
					palin += s * ((long)Math.pow(10, j));
				}
				palin += i * ((long)Math.pow(10, order - length));
				
				// is palin^2 palin?
				long palinSq = (long) Math.pow(palin, 2);
				boolean isPalin = true;
				long sqLength = (long) Math.floor(Math.log10(palinSq));
				for (long j = 0; j < (sqLength+1) / 2; j++) {
					if (((palinSq % (long)Math.pow(10, sqLength - j + 1)) / (long)Math.pow(10, sqLength - j)) 
							!= ((palinSq % (long)Math.pow(10, j+1)) / (long)Math.pow(10, j)))	{
						isPalin = false;
						break;
					}
				}
				
				if (isPalin) {
					if (palinSq > 1 && palinSq >= a && palinSq <= b) {
						numPalin++;
					}
				}
				

				if (palinSq > 1 && palinSq >= a && palinSq <= b) {
					if (palinSq < min) {
						min = palinSq;
					}
					if (palinSq > max) {
						max = palinSq;
					}
				}
				
				//System.out.println(palin + " \t" + palinSq + " \t" + isPalin);
			}
		}
		//System.out.println("min max: "+min + "\t" + max);
		//System.out.println("____________________________________________________");
//		
//		int numPal = 0;
//		for (long i = a; i <= b; i++) {
//			if (Math.pow((long)Math.sqrt(i), 2) == i) {				
//				String iStr = Long.toString(i);
//				boolean isPal = true;
//				for (int j = 0; j < iStr.length()/2; j++) {
//					if (iStr.charAt(j) != iStr.charAt(iStr.length() - j -1)) {
//						isPal = false;
//					}
//				}
//				if (isPal) {
//					iStr = Long.toString((long)Math.sqrt(i));
//					isPal = true;
//					for (int j = 0; j < iStr.length()/2; j++) {
//						if (iStr.charAt(j) != iStr.charAt(iStr.length() - j -1)) {
//							isPal = false;
//						}
//					}
//					if (isPal) {
//						numPal++;
//					}
//				}
//			}
//		}
//		if (numPal != numPalin) {
//			//System.out.println("OOPSIE!!!");
//		} else {
//			//System.out.println("MATCH!!!");
//		}
		return Long.toString(numPalin);
	}

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "F:\\gcj\\2013\\Q\\C-large-1";
}