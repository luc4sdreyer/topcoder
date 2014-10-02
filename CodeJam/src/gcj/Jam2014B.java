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
import java.util.LinkedHashSet;
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

public class Jam2014B {
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
			
			int n = sc.nextInt();
			ArrayList<String> words = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(sc.nextLine());
			for (int j = 0; j < n; j++) {
				words.add(sc.nextLine());
			}
			output = solve(n, words);
			
//			int a = sc.nextInt();
//			int b = sc.nextInt();
//			int k = sc.nextInt();
//			output = solve(a, b, k);

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

	private static String solve(int a, int b, int k) {
		int winCount = 0;
		/*
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				if ((i & j) < k) {
					winCount++;
				}
			}
		}*/
		
		
		for (int n = 0; n < 11; n++) {
			int temp = 0;
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 11; j++) {
					if ((i & j) < n) {
						temp++;
					}
				}
			}
			System.out.print(temp + " ");
		}
		System.out.println();
		
		
		int low = (int) Math.pow(2, ((int) (Math.log10(Math.min(a-1, b-1))/Math.log10(2))));
		winCount += low*low + low*(b-low) + low*(a-low);

		for (int i = low; i < a; i++) {
			for (int j = low; j < b; j++) {
				if ((i & j) < k) {
					winCount++;
				}
			}
		}
		
		return Integer.toString(winCount);
	}

	private static String solve(int n, ArrayList<String> words) {
		int numUniqueLetters = 0;
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			int currentNumLetters = 1;
			char old = '0';
			for (int j = 0; j < word.length(); j++) {
				if (j == 0) {
					old = word.charAt(j);
				} else if (word.charAt(j) != old) {
					old = word.charAt(j);
					currentNumLetters++;
				}
			}
			if (i == 0) {
				numUniqueLetters = currentNumLetters;
			} else if (numUniqueLetters != currentNumLetters) {
				//return "Fegla Won";
			}
		}
		
		String test = null;
		for (int w = 0; w < words.size(); w++) {
			String word = words.get(w);
			int letterIndex = 0;
			char old = '0';
			
			String theWord = new String();
			for (int j = 0; j < word.length(); j++) {
				old = word.charAt(j);
				while (j+1 < word.length() && old == word.charAt(j+1)) {
					j++;
				}
				theWord += old;
			}
			if (test == null) {
				test = theWord; 
			} else if (!test.equals(theWord)) {
				return "Fegla Won";
			}
		}
		
		int[][] map = new int[words.size()][numUniqueLetters];
		for (int w = 0; w < map.length; w++) {
			String word = words.get(w);
			int letterIndex = 0;
			int numLetters = 1;
			char old = '0';
			for (int j = 0; j < word.length(); j++) {
				old = word.charAt(j);
				numLetters = 1;
				while (j+1 < word.length() && old == word.charAt(j+1)) {
					numLetters++;
					j++;
				}
				map[w][letterIndex++] = numLetters;
			}
		}
		
		int totalOps = 0;
		for (int letter = 0; letter < map[0].length; letter++) {
//			int average = 0;
//			for (int w = 0; w < map.length; w++) {
//				average += map[w][letter];
//			}
//			average = (int) Math.round(average/(map.length+0.0));
			int minOps = 10000;
			for (int bestLength = 0; bestLength <= 100; bestLength++) {
				int ops = 0;
				for (int w = 0; w < map.length; w++) {
					ops += Math.abs(map[w][letter] - bestLength);
				}
				//ops += Math.abs(map[0][letter] - map[1][letter]);
				if (ops < minOps) {
					minOps = ops;
				}
			}
			totalOps += minOps;
		}
		return Integer.toString(totalOps);
	}

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "F:\\gcj\\2014\\1B\\A-large-practice";
}