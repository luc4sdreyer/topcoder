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

public class Jam2011 {

	/**
	 * @param args
	 */
	//static String filename = "D:\\jam\\a-small";
	//10^9 < Integer.MAX_VALUE (2^31) < 10^10
	//10^18 < Long.MAX_VALUE (2^63) < 10^20

	public static void main(String[] args) throws NumberFormatException, IOException {		
		//Person[] people = new Person[10];
		//Person temp;

		//try{
		// Open the file that is the first 
		// command line parameter
		FileInputStream fstream = new FileInputStream(filename+".in");
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String output;

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
			//int N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());

			int N = Integer.parseInt(st.nextToken());

			ArrayList<Integer> list = new ArrayList<Integer>();
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				list.add(Integer.parseInt(st.nextToken()));
			}

			/*int oeCount = Integer.parseInt(st.nextToken());
			ArrayList<String> opposedElements = new ArrayList<String>();
			for (int j = 0; j < oeCount; j++) {
				opposedElements.add(st.nextToken());
			}

			st.nextToken();
			String elementList = st.nextToken();*/

			output=  solve(N, list);

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

	static String filename = "D:\\gcj\\2011\\Q\\D-small-practice";

	private static String solve(int N, ArrayList<Integer> list) {
		boolean swap = true;
		int count = 0;
		while (swap) {
			swap = false;
			for (int j = 1; j < N; j++) {	
				if (list.get(j-1) > list.get(j)) {
					swap = true;
					count++;
					int temp = list.get(j-1);
					list.set(j-1, list.get(j));
					list.set(j, temp);
				}
			}

		}
		return String.valueOf(count*2);
	}

	/*
	static String filename = "D:\\gcj\\2011\\Q\\C-large-practice";

	private static String solve(int N, ArrayList<Integer> candy) {
		int sum = 0;
		for (int j = 0; j < N; j++) {
			sum ^= candy.get(j);
		}
		if (sum == 0) {
			Collections.sort(candy);
			sum = 0;
			for (int j = 1; j < N; j++) {
				sum += candy.get(j);
			}
			return String.valueOf(sum);
		} else {
			return "NO";
		}
	}
	 */

	/*
	static String filename = "D:\\gcj\\2011\\1B\\B-sample-practice";
	//2011-Q-b: Magicka
	public static String solve (ArrayList<String> baseElementList, ArrayList<String> opposedElementList, String elementList) {
		HashMap<String, Character> baseElements = new HashMap<String, Character>();
		for (String element: baseElementList) {
			baseElements.put(element.substring(0, 2),element.charAt(2));
		}
		ArrayList<Character> elements = new ArrayList<Character>();
		for (int j = 0; j < elementList.length(); j++) {
			elements.add(elementList.charAt(j));

			int size = elements.size();

			if (size >= 2) {
				String bString1 = (elements.get(size-2).toString()+elements.get(size-1).toString());
				String bString2 = (elements.get(size-1).toString()+elements.get(size-2).toString());
				if (baseElements.containsKey(bString1)) {
					elements.remove(size-1);
					elements.remove(size-2);
					elements.add(baseElements.get(bString1));
				} else if (baseElements.containsKey(bString2)) {
					elements.remove(size-1);
					elements.remove(size-2);
					elements.add(baseElements.get(bString2));
				} else {
					size = elements.size();
					for (String opElement:opposedElementList) {
						if (elements.size() > 0 ) {
							if (elements.get(size-1).equals(opElement.charAt(0))) {
								for (Character e:elements) {
									if (e.equals(opElement.charAt(1))) {
										elements.clear();
										size = 0;
										//break label;
										break;
									}
								}
							} else if (elements.get(size-1).equals(opElement.charAt(1))) {
								for (Character e:elements) {
									if (e.equals(opElement.charAt(0))) {
										elements.clear();
										size = 0;
										//break label;
										break;
									}
								}
							}
						}

					}
				}
			}
		}

		return elements.toString();
	}*/

	/*
	int N = Integer.parseInt(br.readLine());
	StringTokenizer st = new StringTokenizer(br.readLine()," ");
	ArrayList<Integer> candies = new ArrayList<Integer>();
	while (st.hasMoreTokens()) {
		Integer value = Integer.parseInt(st.nextToken());
		candies.add(value);
	}
	output=  solve(candies);

	static String filename = "D:\\gcj\\2011\\Q\\C-sample-practice";
	//2011-Q-c: Candy Splitting
	public static String solve (ArrayList<Integer> candies) {
		int xor = 0;
		long total = 0;

		Collections.sort(candies);
		for (Integer candy: candies) {
			xor ^= candy;
		}

		if (xor != 0) {
			return "NO";
		} else {
			for (int i = 1; i < candies.size(); i++) {
				total += candies.get(i);
			}
		}
		return String.valueOf(total);
	}*/

	/*
	//int N = Integer.parseInt(br.readLine());
	StringTokenizer st = new StringTokenizer(br.readLine()," ");
	//StringBuilder op = new StringBuilder();
	int N = Integer.parseInt(st.nextToken());
	ArrayList<Integer[]> sequence = new ArrayList<Integer[]>();
	while (st.hasMoreTokens()) {
		Integer command[] = new Integer[3];
		String colour = st.nextToken();
		if (colour.equals("O")) {
			command[0] = 1;
		} else if (colour.equals("B")) {
			command[0] = 2;
		}
		command[1] = Integer.parseInt(st.nextToken());
		command[2] = 0;

		sequence.add(command);
	}

	output=  solve(sequence);

	static String filename = "D:\\gcj\\2011\\Q\\A-sample-practice";
	//2011-Q-a: Bot Trust
	public static String solve (ArrayList<Integer[]> sequence) {
		int time;
		time= 0;

		int r1Pos = 1;
		int r2Pos = 1;
		int r1Goal = 0;
		int r2Goal = 0;
		int r1Dist = 100;
		int r2Dist = 100;
		boolean r1Forward = true;
		boolean r2Forward = true;

		while (sequence.size() > 0) {
			if (r1Goal == 0) {
				for (Integer[] command: sequence) {
					if (command[0] == 1) {
						r1Goal = command[1];
						if (r1Goal >= r1Pos) {
							r1Forward = true;
						} else {
							r1Forward = false;
						}
						break;
					}
				}
				if (r1Goal == 0) {
					//r1Finished = true;
				}
			}
			if (r2Goal == 0) {
				for (Integer[] command: sequence) {
					if (command[0] == 2) {
						r2Goal = command[1];
						if (r2Goal >= r2Pos) {
							r2Forward = true;
						} else {
							r2Forward = false;
						}
						break;
					}
				}
				if (r2Goal == 0) {
					//r2Finished = true;
				}
			}

			//robot1
			if ((sequence.get(0))[0] == 1) {
				r1Dist = (r1Goal - r1Pos);
				r2Dist = (r2Goal - r2Pos);

				//make r1 press button if it's at goal
				if (r1Dist == 0) {
					r1Goal = 0;
					sequence.remove(0);

					//move r2 one 
					if (Math.abs(r2Dist) >= 1) {
						if (r2Forward) {
							r2Pos++;
						} else {
							r2Pos--;
						}
					}
					time++;
				} else {
					//move r1
					if (r1Forward) {
						r1Pos = r1Pos + Math.abs(r1Dist);
					} else {
						r1Pos = r1Pos - Math.abs(r1Dist);
					}

					//move r2
					if (Math.abs(r2Dist) > Math.abs(r1Dist)) {
						if (r2Forward) {
							r2Pos = r2Pos + Math.abs(r1Dist);
						} else {
							r2Pos = r2Pos - Math.abs(r1Dist);
						}
					} else {
						r2Pos = r2Goal;
					}

					time = time + Math.abs(r1Dist);
				}
			} else if ((sequence.get(0))[0] == 2) {//robot2
				r1Dist = (r1Goal - r1Pos);
				r2Dist = (r2Goal - r2Pos);

				//make r2 press button if it's at goal
				if (r2Dist == 0) {
					r2Goal = 0;
					sequence.remove(0);

					//move r1 one 
					if (Math.abs(r1Dist) >= 1) {
						if (r1Forward) {
							r1Pos++;
						} else {
							r1Pos--;
						}
					}
					time++;
				} else {
					//move r2
					if (r2Forward) {
						r2Pos = r2Pos + Math.abs(r2Dist);
					} else {
						r2Pos = r2Pos - Math.abs(r2Dist);
					}

					//move r1
					if (Math.abs(r1Dist) > Math.abs(r2Dist)) {
						if (r1Forward) {
							r1Pos = r1Pos + Math.abs(r2Dist);
						} else {
							r1Pos = r1Pos - Math.abs(r2Dist);
						}
					} else {
						r1Pos = r1Goal;
					}

					time = time + Math.abs(r2Dist);
				}
			}
		}

		return String.valueOf(time);
	}*/

	/*
	static String filename = "D:\\gcj\\2011_africa\\Q\\B-small-practice";
	//2010A-Q-b: Investing at the Market
	public static String solve (int capital, ArrayList<Integer> prices) {
		String result;
		boolean canBuy = false;
		for (Integer price:prices){
			if (price <=  capital) {
				canBuy = true;
			} 
		}
		if (!canBuy) {
			return "IMPOSSIBLE";
		}

		for (int i = 0; i < prices.size()-1; i++) {
			if ((i == 0) && (Integer.MAX_VALUE >= prices.get(i)) && (prices.get(i) >= prices.get(i+1))) {
				prices.remove(i);
			} else if (i == prices.size()-2)&& (Integer.MAX_VALUE >= prices.get(i)) && (prices.get(i) >= prices.get(i+1))) {

			} else if  {

			}
		}

		return null;
	}*/

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

