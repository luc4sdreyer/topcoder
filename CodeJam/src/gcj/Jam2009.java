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

public class Jam2009 {
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
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		//getPrimesH();
		for (int i = 0; i < T; i++) {

			ArrayList<Integer> orders = new ArrayList<Integer>();
			//ArrayList<Long> v2 = new ArrayList<Long>();
			//ArrayList<String> queryList = new ArrayList<String>();

			//StringTokenizer st = new StringTokenizer(br.readLine()," ");
			long n = sc.nextLong();
			long m = sc.nextLong();
			long x = sc.nextLong();
			long y = sc.nextLong();
			long z = sc.nextLong();
			ArrayList<Long> A = new ArrayList<Long>();
			for (int j = 0; j < m; j++) {
				A.add(sc.nextLong());
			}

			output = solve(n,m,x,y,z,A);

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
	
	private static String solve(long n, long m, long x, long y, long z,
			ArrayList<Long> a) {
		// TODO Auto-generated method stub
		return null;
	}

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "D:\\gcj\\2009\\1A\\B-small-practice";
	/*
	//2009-Q-c: Welcome to Code Jam (Nice Dynamic programming problem)
	public static String solve (String in) {
		BigInteger z = new BigInteger("0");
		char[] input = in.toCharArray();
		char[] source = "welcome to code jam".toCharArray();
		//char[] source = "we".toCharArray();
		BigInteger[][] dp = new BigInteger[input.length][source.length];

		for (int i = 0; i < input.length; i++) {
			for (int j= 0; j < source.length; j++) {
				if (dp[i][j] == null) {
					dp[i][j] = new BigInteger("0");
				}
				if (i == 0) {
					if (j == 0) {
						if (input[i] == source[j]) {
							dp[i][j] = new BigInteger("1");
						} else {
							dp[i][j] = new BigInteger("0");
						}
					} else {
						dp[i][j] = new BigInteger("0");
					}
				} else if (j == 0) {
					int count = 0;
					for (int k = 0; k <= i; k++) {
						if (input[k]==source[0]) {
							count++;
						}
					}
					dp[i][j] = new BigInteger(String.valueOf(count));
				} else {
					if (input[i] == source[j]) {
						dp[i][j] = dp[i-1][j].add(dp[i-1][j-1]);
					} else {
						dp[i][j] = dp[i-1][j].add(z);
					}
				}
			}
		}
		

		int r = 0;
		String res = dp[input.length-1][source.length-1].toString();
		if (res.length() > 4) {
			r = Integer.parseInt(res.substring(res.length()-4,res.length()));
		} else {
			r = Integer.parseInt(res);
		}
		/*
		for (int j= 0; j < source.length; j++) {
			for (int i = 0; i < input.length; i++) {
				System.out.print(dp[i][j].intValue()+"\t");
			}
			System.out.println();
		}*/
		/*
		return String.format("%04d",r);
	}

	//static String filename = "D:\\gcj\\2009\\Q\\C-sample-practice";
	//2009-Q-a: Alien Language
	/*
	public static String solve (String text) {
		//buildWord(pattern,new StringBuilder());

		String welcome = "welcome to code jam";
		int w = 0;
		int[] welcomeOccurence = new int[welcome.length()];
		int total = 1;
		for (int i = 0; i < text.length(); i++) {
			if (welcomeOccurence[w%welcome.length()] == 0) {
				if (text.charAt(i) == welcome.charAt(w%welcome.length())) {
					welcomeOccurence[w%welcome.length()] = 1;
				}
			} else {
				if (text.charAt(i) == welcome.charAt(w%welcome.length())) {
					welcomeOccurence[w%welcome.length()]++;
				} else if (text.charAt(i) == welcome.charAt((w+1)%welcome.length())) {

						total *= welcomeOccurence[w%welcome.length()];
						w++;
						welcomeOccurence[w%welcome.length()] = 1;
				} 
			}


		}

		return String.valueOf(total);
	}

	//static HashSet<String> allPatterns = new HashSet<String>();

	/*public static void buildWord(String pattern, StringBuilder word){
		//StringBuilder word = new StringBuilder();
		String tokens = new String();
		if (pattern.length() == 0) {
			allPatterns.add(word.toString());
		} else if (pattern.charAt(0) == '(') {
			tokens = pattern.substring(1, pattern.indexOf(')', 1));
			pattern = pattern.substring(pattern.indexOf(')', 1)+1, pattern.length());
			for (int i = 0; i < tokens.length(); i++) {
				StringBuilder w = new StringBuilder(word);
				w.append(tokens.charAt(i));
				buildWord(pattern,w);
			}
		} else {
			StringBuilder w = new StringBuilder(word);
			w.append(pattern.charAt(0));
			buildWord(pattern.substring(1,pattern.length()),w); 
		}
	} */

	static Map sortByValue(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
				.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	} 

	static Map sortByKey(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getKey())
				.compareTo(((Map.Entry) (o2)).getKey());
			}
		});

		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	} 
}