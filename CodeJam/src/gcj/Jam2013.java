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

public class Jam2013 {
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
			long R = Long.parseLong(st.nextToken());
			long T1 = Long.parseLong(st.nextToken());
//			int[][] lawn = new int[Y][X];
//			
//			for (int y = 0; y < Y; y++) {
//				st = new StringTokenizer(sc.nextLine()," ");
//				for (int x = 0; x < X; x++) {
//					lawn[y][x] = Integer.parseInt(st.nextToken());
//				}			
//			}

			output = solve(R, T1);

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

	private static String solve(long startR, long T) {
		long remT = T;
		long numCircles = 0;
		long R = startR;
		while (true) {
			long cost = 2*R+1;
			if (remT >= cost) {
				remT -= cost;
			} else {
				break;
			}
			R += 2;
			numCircles++;
		}
		return Long.toString(numCircles);
	}

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "F:\\gcj\\2013\\1A\\A-large";
}