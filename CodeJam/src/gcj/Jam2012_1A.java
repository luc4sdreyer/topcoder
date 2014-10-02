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

public class Jam2012_1A {
	//-10^9 < Integer.MAX_VALUE (2^31) < 10^10
	//-10^18 < Long.MAX_VALUE (2^63) < 10^20


	public static void main(String[] args) throws NumberFormatException, IOException {		
		Scanner sc = new Scanner(new FileReader(filename+".in"));
		String output;
		String outputfile = filename + ".out";

		FileOutputStream Output = new FileOutputStream(outputfile);
		PrintStream file2 = new PrintStream(Output);

		boolean after25 = false;
		boolean after50 = false;
		boolean after75 = false;

		int T = sc.nextInt();
		//int T = Integer.parseInt(sc.nextLine());
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);


		//getPrimesH();
		for (int i = 0; i < T; i++) {

			/*int a = sc.nextInt();
			int b = sc.nextInt();
			ArrayList<Double> probs = new ArrayList<Double>();
			
			for (int j = 0; j< a; j++) {
				probs.add(sc.nextDouble());
			}

			output = solve(a,b,probs);*/
			
			int N = sc.nextInt();
			ArrayList<Integer> a = new ArrayList<Integer>();
			ArrayList<Integer> b = new ArrayList<Integer>();
			
			for (int j = 0; j< N; j++) {
				a.add(sc.nextInt());
				b.add(sc.nextInt());
			}

			output = solve(N,a,b);
			
			

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


	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=

	private static String solve(int N, ArrayList<Integer> a,
			ArrayList<Integer> b) {
		ArrayList<Integer> oldA = (ArrayList<Integer>) a.clone();
		ArrayList<Integer> oldB = (ArrayList<Integer>) b.clone();
		ArrayList<Integer> newA = (ArrayList<Integer>) a.clone();
		ArrayList<Integer> newB = (ArrayList<Integer>) b.clone();
		Collections.sort(newA);
		Collections.sort(newB);
		
		int stars = 0;
		int levels = 0;
		
		Integer min = Collections.min(oldB);
		if (min <= stars) {
			int index = oldB.indexOf(min);
			stars += 1;
			stars += 2;
			levels++;
			oldA.remove(index);
			oldB.remove(index);
		} else {
			ArrayList<Integer> tries = new ArrayList<Integer>();
			int max2 = -1;
			for (int i = 0; i < oldA.size(); i++) {
				if (oldA.get(i) <= stars) {
					if (oldA.get(i) > max2) {
						tries.clear();
						max2 = oldA.get(i);
					} else if (oldA.get(i) == max2) {
						tries.add(oldA.get(i));			
					}
				}
			}
			max2 = -1;
			for (int i = 0; i < tries.size(); i++) {
				int index = oldB.indexOf(tries.get(i));
			}
		}
		return null;
	}


	static String filename = "D:\\gcj\\2012\\1A\\B-sample";

	
	
	/*static String filename = "D:\\gcj\\2012\\1A\\A-sample";

	private static String solve(int a, int b, ArrayList<Double> probs) {
		for (int i = 0; i < array.length; i++) {
			
		}
		return null;
	}*/
}
