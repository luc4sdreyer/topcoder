package gcj;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import java.util.Iterator;


//import java.io.*;

//import pack.Person;

public class Jam2010A {

	/**
	 * @param args
	 */
	//static String filename = "D:\\jam\\a-small";


	public static void main(String[] args) throws NumberFormatException, IOException {		
		//Person[] people = new Person[10];
		//Person temp;

		String filename = null;
		//try{
		// Open the file that is the first 
		// command line parameter
		FileInputStream fstream = new FileInputStream(filename+".in");
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String output = null;

		FileOutputStream Output;
		PrintStream file2;

		String outputfile = filename + ".out";
		Output = new FileOutputStream(outputfile);
		file2 = new PrintStream(Output);

		boolean after25 = false;
		boolean after50 = false;
		boolean after75 = false;

		/*StringTokenizer st = new StringTokenizer(br.readLine()," ");
		int patternLength = Integer.parseInt(st.nextToken());
		int totalWords = Integer.parseInt(st.nextToken());*/
		//int T = Integer.parseInt(br.readLine());

		/*ArrayList<String> list1 = new ArrayList<String>();
		for (int j = 0; j < totalWords; j++) {
			list1.add(br.readLine());
		}*/
		int T = Integer.parseInt(br.readLine());
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		for (int i = 0; i < T; i++) {

//			StringTokenizer st = new StringTokenizer(br.readLine()," ");
//			StringBuilder op = new StringBuilder();
//			ArrayList<String> words = new ArrayList<String>();
//			while (st.hasMoreTokens()) {
//				words.add(st.nextToken());
//			}
//			for (int j = 0; j < words.size(); j++){
//				if (j != 0) {
//					op.append(' ');
//				}
//				op.append(words.get(words.size()-j-1));
//			}
//			output=  op.toString();

			file2.println("Case #" + (i+1) + ": "+output);
			if ((100*(i+1)/T >= 25) && (!after25)) {
				System.out.println(Calendar.getInstance().getTime()+" - 25% done");
				after25 = true;
			} else if ((100*(i+1)/T >= 50) && (!after50)) {
				System.out.println(Calendar.getInstance().getTime()+" - 50% done");
				after50 = true;
			} else if ((100*(i+1)/T >= 75) && (!after75)) {
				System.out.println(Calendar.getInstance().getTime()+" - 75% done");
				after75 = true;
			}
		}
		in.close();
		System.out.println(Calendar.getInstance().getTime()+" - Finished writing to: "+outputfile);
	}

	/*
	static String filename = "D:\\gcj\\2010_africa\\Q\\B-large-practice";
	//2010A-Q-b: Reverse Words
//	StringTokenizer st = new StringTokenizer(br.readLine()," ");
//	StringBuilder op = new StringBuilder();
//	ArrayList<String> words = new ArrayList<String>();
//	while (st.hasMoreTokens()) {
//		words.add(st.nextToken());
//	}
//	for (int j = 0; j < words.size(); j++){
//		if (j != 0) {
//			op.append(' ');
//		}
//		op.append(words.get(words.size()-j-1));
//	}
//	output=  op.toString();
	public static String solve (int credit, int itemCount, ArrayList<Integer> items) {

		return null;
	}*/
	
	/*
	static String filename = "D:\\gcj\\2010_africa\\Q\\A-large-practice";
	//2010A-Q-a: Store Credit
//	int credit = Integer.parseInt(br.readLine());
//	int itemCount = Integer.parseInt(br.readLine());
//	StringTokenizer st = new StringTokenizer(br.readLine()," ");
//	ArrayList<Integer> items = new ArrayList<Integer>();
//	for (int j = 0; j < itemCount; j++) {
//		items.add(Integer.parseInt(st.nextToken()));
//	}
	public static String solve (int credit, int itemCount, ArrayList<Integer> items) {
		//for (int i = 0; i < ridesCount; i++) {
		HashSet<Integer> itemSet = new HashSet<Integer>(items);
		HashMap<Integer, Integer> itemMap = new HashMap<Integer, Integer>();
		
		for (int i = 0; i < itemCount; i++) {
			itemMap.put(items.get(i), i+1);
		}
		
		String result = null;
		for (Integer item: itemSet) {
			if (itemSet.contains(credit - item)) {
				int itemPos1 = itemMap.get(item);
				int itemPos2 = 0;
				if (item*2 == credit) {
					for (int i = 0; i < itemCount; i++) {
						if (i != itemPos1) {
							itemPos2 = items.get(i)+1;
						}
					}
				} else {
					itemPos2 = itemMap.get(credit-item);
				}	
				if (itemPos1 < itemPos2) {
					result = itemPos1 + " " + itemPos2;
				} else {
					result = itemPos2 + " " + itemPos1;
				}
			}
		}
		return result;
	}*/
	
	/*
	static String filename = "D:\gcj\2010_africa\Q\A-large-practice";
	//2010-Q-a: Snapper
	//output = solve(N, K);
	public static String solve (int N, int k) {
		//for (int i = 0; i < ridesCount; i++) {
		String result;
		if ((k+1)%(Math.pow(2, N))==0) {
			result = "ON";
		} else {
			result = "OFF";
		}
		return result;
	}*/
	
	/*
	static String filename = "D:\\gcj\\2010\\Q\\C-large-practice";
	//2010-Q-c: Theme Park
	public static String solve (int ridesCount, int kartSize, int groupsCount, long[][] result) {
		int i;
		long euros = 0;
		int marker = 0;
		boolean notSkipped = true;
		for (i = 0; i < ridesCount; i++) {
			if (result[marker][1] == 0) {
				int j = marker;
				while (result[marker][1] + result[j][0] <= kartSize) {
					result[marker][1] += result[j][0];
					j++;
					if (j == groupsCount) {j = 0;};
				}
				if (j == groupsCount) {j = 0;};
				result[marker][2] = j;
			}
			if ((result[marker][3] != 0)&&(notSkipped)) {
				long skipLength = ridesCount-result[marker][3];
				long setLength = i-result[marker][3];
				long setEuros = (skipLength/setLength)*(euros-result[marker][4]);
				euros = setEuros+result[marker][4];
				i = (int) ((result[marker][3] + (skipLength/setLength)*setLength))-1;
				notSkipped = false;
				continue;
			} else {
				result[marker][3] = i;
				result[marker][4] = euros;
			}
			euros += result[marker][1];
			marker = (int) result[marker][2];
		}
		return String.valueOf(euros);
	}*/

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