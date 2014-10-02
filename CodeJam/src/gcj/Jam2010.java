package gcj;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import java.util.Iterator;
import java.util.Map.Entry;



//import java.io.*;

//import pack.Person;

public class Jam2010 {

	/**
	 * @param args
	 */
	//static String filename = "D:\\jam\\a-small";

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
		int T = Integer.parseInt(br.readLine());

		/*ArrayList<String> list1 = new ArrayList<String>();
		for (int j = 0; j < totalWords; j++) {
			list1.add(br.readLine());
		}*/
		//int T = Integer.parseInt(br.readLine());
		System.out.println(Calendar.getInstance().getTime()+" - Started writing to: "+outputfile);

		for (int i = 0; i < T; i++) {

			StringTokenizer st = new StringTokenizer(br.readLine()," ");

			int R = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			int N = Integer.parseInt(st.nextToken());
			//int time = Integer.parseInt(st.nextToken());

			ArrayList<Integer> groups = new ArrayList<Integer>();
			st = new StringTokenizer(br.readLine()," ");
			for (int j = 0; j < N; j++) {
				groups.add(Integer.parseInt(st.nextToken()));
				
			}
			output = solve(R,k,N,groups);

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

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large";
	
	static String filename = "D:\\gcj\\2010\\Q\\C-large-practice";

	private static String solve(int r, int k, int N, ArrayList<Integer> groups) {
		int[][] m = new int[groups.size()][3];
		
		for (int j = 0; j < N; j++) {
			m[j][0] = groups.get(j);
		}
		for (int j = 0; j < N; j++) {
			int kset= 0;
			int x = 0;
			while (kset + m[(x+j)%N][0]<= k){
				kset += m[(x+j)%N][0];
				x++;
				if (x == N) {
					break;
				}
			}
			m[j][1] = kset;
			m[j][2] = x;
		}
		
		long euros = 0;
		long x = 0;
		
		long NEW = N;
		for (int j = 0; j < r; j++) {
			euros += m[(int) (x%NEW)][1];
			x += m[(int) (x%NEW)][2];
		}
		
		return String.valueOf(euros);
	}
	
	/*
	static String filename = "D:\\gcj\\2010\\Q\\B-sample-practice";
	
	private static String solve(ArrayList<BigInteger> nums) {
		Collections.sort(nums);
		
		ArrayList<BigInteger> diffs = new ArrayList<BigInteger>();
		
		for (int j = 1; j < nums.size(); j++) {
			diffs.add(nums.get(j).subtract(nums.get(j-1)));
		}
		Collections.sort(diffs);
		
		BigInteger fact;
		BigInteger minFact = null;
		if (diffs.size() > 1) {
			
			for (int j = 0; j < diffs.size(); j++) {
				for (int k = j+1; k < diffs.size(); k++) {
					fact = diffs.get(j).gcd(diffs.get(k));
					if (minFact == null) {
						minFact = fact.add(BigInteger.ZERO);
					}
					if (minFact.compareTo(fact) == -1) {
						minFact = fact.add(BigInteger.ZERO);
					}
				}
			}
			fact = minFact;
		} else {
			fact = diffs.get(0);
		}
		
		if (nums.get(0).mod(fact).compareTo(new BigInteger("0")) == 0) {
			return "0";
		} else {
			BigInteger res = fact.subtract(nums.get(0).mod(fact));
			return res.toString();
		}
		
		
	}*/
	
	/*
	static String filename = "D:\\gcj\\2010\\1B\\B-small-practice";
	//2010-1-B.b: Picking Up Chicks
	private static String solve(int totalChicks, int minChicks, double distance,
			double time, ArrayList<Integer> positions, ArrayList<Integer> speeds) {
		
		int canMakeIt = 0;
		for (int j = 0; j < totalChicks; j++) {
			if (positions.get(j) + speeds.get(j)*time >= distance) {
				canMakeIt++;
			}
		}
		if (canMakeIt < minChicks) {
			return "Impossible";
		}
		int toSpare = totalChicks - minChicks;
		int madeIt =0;
		for (int j = speeds.size()-1; j >= 0; j--) {
			if (positions.get(j) + speeds.get(j)*time >= distance) {
				if (j != speeds.size()-1) {
					if ((distance-positions.get(j+1))/speeds.get(j+1) < (distance-positions.get(j))/speeds.get(j)) {
						//collision
						//will col cause chick to be late?
						
					}
				} else {
					madeIt++;
				}	
			}
		}
		
		
		return "a";
	}
*/
	/*
	StringTokenizer st = new StringTokenizer(br.readLine()," ");

	int existingDirsC = Integer.parseInt(st.nextToken());
	int newDirsC = Integer.parseInt(st.nextToken());

	ArrayList<String> existingDirs = new ArrayList<String>();
	for (int j = 0; j < existingDirsC; j++) {
		existingDirs.add(br.readLine());
	}

	ArrayList<String> newDirs = new ArrayList<String>();
	for (int j = 0; j < newDirsC; j++) {
		newDirs.add(br.readLine());
	}

	output = solve(existingDirs, newDirs);


	static String filename = "D:\\gcj\\2010\\1B\\A-large-practice";
	//2010-1-B.a: File Fix-it

	public static String solve (ArrayList<String> existingDirs, ArrayList<String> newDirs) {
		LinkedHashSet<String> fs = new LinkedHashSet<String>();

		for (String path:existingDirs) {
			while (path.length() != 0) {
				fs.add(path);
				path = path.substring(0,path.lastIndexOf('/'));			
			}
		}

		long newPath = 0;

		for (String path:newDirs) {
			while (path.length() != 0) {
				if (fs.add(path)) {
					newPath++;
				}
				path = path.substring(0,path.lastIndexOf('/'));			
			}
		}

		return String.valueOf(newPath);
	}*/	

	/*
	static String filename = "D:\\gcj\\2010\\1A\\B-sample-practice";
	//2010-1-A.b: Make it Smooth
	//output = solve(N, K);

	public static String solve (int deleteCost,int insertCost,int smoothDistance,int 
			file2, ArrayList<Integer> pixels) {

		return null;
	}*/

	/*	
	StringTokenizer st = new StringTokenizer(br.readLine()," ");
	int lowA = Integer.parseInt(st.nextToken());
	int highA = Integer.parseInt(st.nextToken());
	int lowB = Integer.parseInt(st.nextToken());
	int highB = Integer.parseInt(st.nextToken());

	output = solve(lowA, highA, lowB, highB);

	static String filename = "D:\\gcj\\2010\\1A\\C-sample-practice";
	//2010-1-A.c: Number Game


	static private HashMap<Integer, HashMap<Integer, Character>> solutions = new HashMap<Integer, HashMap<Integer, Character>>();
	public static String solve (int lowA,int highA,int lowB,int highB) {
		for (int i = lowA; i <= highA; i++) {
			if (!solutions.containsKey(i)) {
				solutions.put(i, new HashMap<Integer, Character>());
			}
			for (int j = lowB; j <= highB; j++) {
				if (i==j) {
					if (!solutions.get(i).containsKey(j)) {
						solutions.get(i).put(j, 'L');
					}
				} else {
					int max = Math.max(i, j);
					int min = Math.min(i, j);
					if (!solutions.containsKey(min)) {
						solutions.put(min, new HashMap<Integer, Character>());
					}
					if (!solutions.get(min).containsKey(max)) {
						solutions.get(min).put(max, 'L');
					}
				}
			}
		}
		return null;
	}*/

	/*static String filename = "D:\\gcj\\2010\\1A\\A-small-practice";
	//2010-1-A.a: Rotate
	//output = solve(N, K);
	public static String solve (int boardLength, int rowLength, char[][] board) {
		int boardSize = boardLength*boardLength;
		int emptyUntil = 0;
		boolean emptySet = false;
		int fullFrom = boardLength*boardLength-1;
		boolean fullSet = false;

		System.out.println("NEW");
		System.out.println("boardLength: "+ boardLength);
		System.out.println("rowLength: "+rowLength);
		for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j < boardLength; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}

		for (int i = 0; i < boardSize; i++) {
			if ((!emptySet) && (board[i/boardLength][i%boardLength] != '.')) {
				emptySet = true;
				emptyUntil = i;
			}
		}

		for (int i = 1; i <= boardSize; i++) {
			if ((!fullSet) && (board[(boardSize-i)/boardLength][(boardSize-i)%boardLength] == '.')) {
				fullSet = true;
				fullFrom = boardSize-i;
			}
		}

		emptyUntil = (int)Math.floor((double)emptyUntil/(double)boardLength);
		fullFrom = (int)Math.ceil((double)fullFrom/(double)boardLength);

		int moves = 1;


		for (int i = 0; i < boardLength; i++) {
			if ((i >= emptyUntil) && (i <= fullFrom)) {
				String row = new String(board[i]).replace(".", "");

				StringBuilder s = new StringBuilder();
				while (s.length() < boardLength-row.length()) {
					s.append('.');
				}
				s.append(row);
				board[i] = s.toString().toCharArray();
				//board[i]
			}
		}

		//find blue
		int bLength = 0;
		boolean bWon = false;
		int iSearch = 0;
		int jSearch = 0;

		for (int i = 0; i < boardLength; i++) {
			if (i >= emptyUntil) {
				for (int j = 0; j < boardLength; j++) {
					//search east
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'B') {
						//iSearch++;
						jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}

					//search south
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'B') {
						iSearch++;
						//jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}

					//search south-east
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'B') {
						iSearch++;
						jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}

					//search north-east
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'B') {
						iSearch--;
						jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}
				}
			}
		}

		boolean blueWon = false;
		if (bWon == true) {
			blueWon = true;
		} 

		//find red
		bLength = 0;
		bWon = false;
		iSearch = 0;
		jSearch = 0;

		for (int i = 0; i < boardLength; i++) {
			if (i >= emptyUntil) {
				for (int j = 0; j < boardLength; j++) {

					//search east
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'R') {
						//iSearch++;
						jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}

					//search south
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'R') {
						iSearch++;
						//jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}

					//search south-east
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'R') {
						iSearch++;
						jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}

					//search north-east
					iSearch = i;
					jSearch = j;
					bLength = 0;
					while ((iSearch >= 0) && (iSearch < boardLength) && (jSearch >= 0) && (jSearch < boardLength) && board[iSearch][jSearch] == 'R') {
						iSearch--;
						jSearch++;
						bLength++;
						if (bLength == rowLength) {
							bWon = true;
							i = boardLength;
							j = boardLength;
							break;
						}
					}
				}
			}
		}

		boolean redWon = false;
		if (bWon == true) {
			redWon = true;
		} 

		String result;

		if (redWon && blueWon) {
			result = "Both";
		} else if (redWon) {
			result = "Red";
		} else if (blueWon) {
			result = "Blue";
		} else {
			result = "Neither";
		}

		System.out.println("RESULT: "+result);
		for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j < boardLength; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}

		return result;
	}*/

	/*
	static String filename = "D:\\gcj\\2010\\Q\\A-large-practice";
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



	
}