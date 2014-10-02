package gcj;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
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
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import java.util.Iterator;

public class Jam2008 {

	//-128 < byte < 127
	//-32,768 < short < 32,767
	//-2,147,483,648 < integer < 2,147,483,647
	//-9,223,372,036,854,775,808 < long < 9,223,372,036,854,775,807


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
	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "D:\\gcj\\2008\\1C\\B-small-practice";
	
	private static String solve(long n, long m, long x, long y, long z,ArrayList<Long> a) {
		long[] A = new long[(int) n];
		int[] D = new int[(int) n];
		
		int dsize = 0;
		
		for (int i = 0; i < n; i++) {
			A[i] = (a.get((int) (i%m)));
			a.set((int) (i%m), (x * a.get((int) (i%m)) + y * (i + 1)) % z);
		}
		
		for (int b = 0; b < n; b++) {
			for (int i = 0; i < dsize; i++) {
				if (A[b] > A[i]) {
					D[b] = (D[b] + D[i]) % 1000000007;	
				}
			}
			D[b] = (D[b]+1) % 1000000007; 
			dsize++;
		}
		
		int result = 0;
		for (int b = 0; b < n; b++) {
			result = (result + D[b])% 1000000007;
		}
		return String.valueOf(result);
	}
	
	
	private static int[] binaryIndexedTree;
	//binaryIndexedTree = new int[];

	private static int bitRead(int index) {
		int sum = 0;
		while (index > 0){
			sum += binaryIndexedTree[index];
			index -= (index & -index);
		}
		return sum;
	}

	private static void bitUpdate(int index, int val){
		if (index == 0) {
			binaryIndexedTree[0] = 0;
		} else {
			while (index <= binaryIndexedTree.length){
				binaryIndexedTree[index] += val;
				index += (index & -index);
			}
		}
	}

	/*
	private static int bitReadSingleSlow(int index) {
		if (index > 1) {
			return bitRead(index) - bitRead(index-1);
		} else {
			return bitRead(index);
		}
	}*/

	private static int bitReadSingle(int index) {
		int sum = binaryIndexedTree[index];
		if (index > 0) {
			int z = index - (index & -index);
			index--;
			while (index != z) {
				sum -= binaryIndexedTree[index];
				index -= (index & -index);
			}
		}
		return sum;
	}
	
	private static void scale(int c){
		for (int i = 1 ; i <= binaryIndexedTree.length ; i++)
			binaryIndexedTree[i] = binaryIndexedTree[i] / c;
	}

	
	/*
	static String filename = "D:\\gcj\\2008\\1C\\A-large-practice";
	
	private static String solve(int p, int k, int l, ArrayList<Long> keys) {
		Collections.sort(keys);
		Collections.reverse(keys);
		long sum = 0;
		for (int j = 0; j < p; j++) {
			for (int x = 0; x < k; x++) {
				if (j*k+x < keys.size()) {
					sum += (keys.get(j*k+x)*(j+1));
				}				
			}
		}
		return String.valueOf(sum);
	}*/
	
	/*
	static String filename = "D:\\gcj\\2008\\1B\\C-small-practice";

	private static String solve(int K, int n, ArrayList<Integer> indices) {
		int[] deck = new int[K];
		int S = (int) Math.ceil(Math.sqrt(K)); 
		int[] L2 = new int[S];

		//for (int j = 0; j < n; j++) {
		//	L2[(indices.get(j)-1)/S]++;
		//}
		int size = 0;
		int x = 0,ec = 0;
		int choice = 1;
		while (size < K) {
			if (x > 0 && x%K==0) {
				x = 0;
			}
			if (ec+1 + L2[x/S] < choice) {
				ec += L2[x/S];
			}
			if (deck[x] == 0) {
				if (ec+1 == choice) {
					deck[x] = choice++;
					size++;
					ec = 0;
					L2[x/S]++;
				} else {
					ec++;
				}

			}
			x++;

		}
		StringBuilder sb = new StringBuilder();

		for (int j = 0; j < n; j++) {
			if (j != 0) {sb.append(' ');}
			sb.append(deck[indices.get(j)-1]);
		}
		return sb.toString();
	}*/
		/*
	static String filename = "D:\\gcj\\2008\\1B\\B-small-practice";
	private static String solve(int a, int b, int l) {
		LinkedHashMap<Integer,Integer> pgroups = new LinkedHashMap<Integer,Integer>();
		int maxGroup = 0;
		for (int j = a; j <= b; j++) {
			//get primes
			int n = j;
			LinkedHashSet<Integer> sprimes = new LinkedHashSet<Integer>();
			for (int p:primes) {
				//if (p >= l) {
					if (n == 1) {
						break;
					}
					boolean loop = true;
					while (loop) {
						if (n%p == 0) {
							n = n/p;
							sprimes.add(p);
						} else {
							loop = false;
						}
					}
				//}
			}
			boolean largePrimes = false;
			for (int p:sprimes) {
				if (p >= l) {
					largePrimes = true;
				}
			}
			if (!largePrimes) {
				sprimes.add(j);
			}
			boolean grouped = false;
			int group = 0;
			for (int p:sprimes) {
				if (p >= l) {
					if (pgroups.containsKey(p)) {
						grouped = true;
						group = pgroups.get(p);
					}
				}
			}
			if (grouped) {
				for (int p:sprimes) {
					//if (p >= l) {
						pgroups.put(p, group);
					//}
				}				
			} else {
				group = ++maxGroup;
				for (int p:sprimes) {
					//if (p >= l) {
						pgroups.put(p, group);
					//}
				}
			}
		}
		LinkedHashSet<Integer> groups = new LinkedHashSet<Integer>();
		for (int p:pgroups.values()) {
			if (!groups.contains(p)) {
				groups.add(p);
			}
		}
		return String.valueOf(groups.size());
	}
	 */

	/*
	private static String solve(int C) {
		BigDecimal root = new BigDecimal("5.236067977499789696409173668731276235440618359611525724270897245410520925637804899414414408378782274969508176150773783504253267724447073863586360121533452708866778173191879165811276645322639856580535761350417533785003423392414064442086432539097252592627228876299517402440681611775908909498492371390729728898482088641542689894099131693577019748678884425089754132956183176921499977424801530434115035957668332512498815178139408000562420855243542235556106306342820234093331982933959746352271201341749614202635904737885504389687061135660045757139956595566956917564578221952500060539231234005009286764875529722056766253666074485853505262330678494633422242317637277026632407680104443315825733505893098136226343198686471946989970180818952426445962034522141192232912598196325811104170495807048120403455994943506855551855572512388641655010262436312571024450");
		//BigDecimal root = new BigDecimal("1.00606797");
		for (int j = 0; j < C; j++) {
			root = root.multiply(root);
		}
		//BigDecimal b = root.pow(C);
		String r = root.toPlainString();
		r = r.substring(0, r.indexOf('.'));

		if (r.length() > 3) {
			r = r.substring(r.length()-3, r.length());
		} else if (r.length() == 2) {
			r = "0" + r;
		} else if (r.length() == 1) {
			r = "00" + r;
		}

		return r;
	}

	private static String getRoot() {
		BigDecimal rooted = new BigDecimal("5.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		BigDecimal guess = new BigDecimal("2.2360679774997896964091736687313");
		BigDecimal a;
		BigDecimal b;

		for (int j = 0; j < 100000; j++) {
			a = rooted.divide(guess,RoundingMode.HALF_EVEN);
			b = guess.add(a);
			guess = b.divide(new BigDecimal("2"),RoundingMode.HALF_EVEN);
		}

		return guess.toEngineeringString();
	}
	 */
	/*
	private static String solve(int M, int C, ArrayList<ArrayList<int[]>> orders) {
		StringBuilder sb = new StringBuilder();
		boolean init = false;
		for (int m:menu) {
			if (init) {sb.append(' ');}
			init = true;
			if (m == -1) {m = 0;} 
			sb.append(m);

		}
		return sb.toString();
	}*/


	/*
	//2008-1-A
	private static String solve(int N, ArrayList<Long> v1, ArrayList<Long> v2) {
		Collections.sort(v1);
		Collections.sort(v2);
		Collections.reverse(v2);

		long sum = 0;
		for (int j = 0; j < N; j++) {
			sum += v1.get(j)*v2.get(j);
		}

		return String.valueOf(sum);
	}*/
	/*

	//2008-Q-a: Saving the Universe
	public static String solve(ArrayList<String> engineList, ArrayList<String> queryList, int engineCount, int queryCount) {
		HashSet<String> engines = new HashSet<String>(engineList);
		int switches = 0;
		for (String q:queryList) {
			if (engines.size() == 1) {
				if (engines.contains(q)) {
					switches++;
					engines = new HashSet<String>(engineList);
					engines.remove(q);
				}
			} else if (engines.contains(q)) {
				engines.remove(q);

			}
		}
		return Integer.toString(switches);
	}
	 */


	/*

	//2008-Q-b: Train Timetable 

	 			ArrayList<int[]> list1 = new ArrayList<int[]>();
			ArrayList<int[]> list2 = new ArrayList<int[]>();
			int turnAround = Integer.parseInt(br.readLine());
			//problem1.add(String.valueOf(engineCount));
			StringTokenizer st = new StringTokenizer(br.readLine()," ");

			int listAlength = Integer.parseInt(st.nextToken());
			int listBlength = Integer.parseInt(st.nextToken());

			for (int j = 0; j < listAlength; j++) {
				StringTokenizer stT = new StringTokenizer(br.readLine()," ");
				StringTokenizer stA = new StringTokenizer(stT.nextToken(),":");
				int timeA = 60*Integer.parseInt(stA.nextToken());
				timeA += Integer.parseInt(stA.nextToken());

				stA = new StringTokenizer(stT.nextToken(),":");
				int timeB = 60*Integer.parseInt(stA.nextToken());
				timeB += Integer.parseInt(stA.nextToken());

				int[] times = new int[2];
				times[0] = timeA;
				times[1] = timeB;
				list1.add(times);
			}

			for (int j = 0; j < listBlength; j++) {
				StringTokenizer stT = new StringTokenizer(br.readLine()," ");
				StringTokenizer stA = new StringTokenizer(stT.nextToken(),":");
				int timeA = 60*Integer.parseInt(stA.nextToken());
				timeA += Integer.parseInt(stA.nextToken());

				stA = new StringTokenizer(stT.nextToken(),":");
				int timeB = 60*Integer.parseInt(stA.nextToken());
				timeB += Integer.parseInt(stA.nextToken());

				int[] times = new int[2];
				times[0] = timeA;
				times[1] = timeB;
				list2.add(times);
			}

			output = solve(turnAround, list1, list2);

	static String filename = "D:\\gcj\\2008\\Qualification\\A-small-practice";
	public static String solve (int turnAround, ArrayList<int[]> listA, ArrayList<int[]> listB) {
		HashMap<Integer,Integer> timesA = new HashMap<Integer,Integer>(listA.size()+listB.size());
		HashMap<Integer,Integer> timesB = new HashMap<Integer,Integer>(listA.size()+listB.size());

		for (int i = 0; i < listA.size(); i++) {
			int timeA = listA.get(i)[0];
			if (timesA.containsKey(timeA)) {
				int trains = timesA.get(timeA);
				timesA.put(timeA, --trains);
			} else {
				timesA.put(timeA, -1);
			}
			int timeB = listA.get(i)[1]+turnAround;
			if (timeB < 24*60) {
				if (timesB.containsKey(timeB)) {
					int trains = timesB.get(timeB);
					timesB.put(timeB, ++trains);
				} else {
					timesB.put(timeB, 1);
				}
			}
		}

		for (int i = 0; i < listB.size(); i++) {
			int timeB = listB.get(i)[0];
			if (timesB.containsKey(timeB)) {
				int trains = timesB.get(timeB);
				timesB.put(timeB, --trains);
			} else {
				timesB.put(timeB, -1);
			}
			int timeA = listB.get(i)[1]+turnAround;
			if (timeA < 24*60) {
				if (timesA.containsKey(timeA)) {
					int trains = timesA.get(timeA);
					timesA.put(timeA, ++trains);
				} else {
					timesA.put(timeA, 1);
				}
			}
		}

		int totalA = 0;
		int totalB = 0;

		int minA = Integer.MAX_VALUE;
		int minB = Integer.MAX_VALUE;

		//HashMapX<Integer,Integer> tX = new HashMapX<Integer,Integer>(timesA);

		//Set<Integer> setA = timesA.keySet();
		//Object o = (HashMap<Integer, Integer>) sortByKey(timesA);
		timesA = (HashMap<Integer, Integer>) sortByKey(timesA);
		timesB = (HashMap<Integer, Integer>) sortByKey(timesB);

		for (Map.Entry<Integer, Integer> entry: timesA.entrySet()) {
			totalA += entry.getValue();
			if (totalA < minA) {
				minA = totalA;
			}
		}

		for (Map.Entry<Integer, Integer> entry: timesB.entrySet()) {
			totalB += entry.getValue();
			if (totalB < minB) {
				minB = totalB;
			}
		}

		if (minB > 0) {
			minB = 0;
		}

		if (minA > 0) {
			minA = 0;
		}

		return String.valueOf(minA*-1) +" "+ String.valueOf(minB*-1);
	}*/

	/*

	//2008-Q-a: Saving the Universe 
	ArrayList<String> engineList = new ArrayList<String>();
	ArrayList<String> queryList = new ArrayList<String>();
	int engineCount = Integer.parseInt(br.readLine());

	for (int j = 0; j < engineCount; j++) {
		engineList.add(br.readLine());
	}

	int queryCount = Integer.parseInt(br.readLine());

	for (int j = 0; j < queryCount; j++) {
		queryList.add(br.readLine());
	}

	output = solve(engineList, queryList, engineCount, queryCount);

	static String filename = "D:\\gcj\\2008\\Qualification\\A-small-practice";
	public static String solve (ArrayList<String> engineList, ArrayList<String> queryList, int engineCount, int queryCount) {

		HashSet<String> engines = new HashSet<String>();
		int switches = 0;
		for (int i = 0; i < queryList.size(); i++) {
			if (!engines.contains(queryList.get(i))) {
				if (engines.size() == engineCount-1) {
					switches++;
					engines.clear();
				}
				engines.add(queryList.get(i));
			}
		}
		//queryList
		return String.valueOf(switches);
	}*/


	/*
	//2010-c: Theme Park (not working)
	public String solve (ArrayList<String> problem) {
		String output;
		StringTokenizer st = new StringTokenizer(problem.get(0));

        //i = totals[100];

        int R = Integer.parseInt(st.nextToken());
        //System.out.println(R);
        int k = Integer.parseInt(st.nextToken());
        //System.out.println(k);
        int N = Integer.parseInt(st.nextToken());
        //System.out.println(N);
        //int grandtotal = 0;

        st = new StringTokenizer(problem.get(1));
        int[] line = new int[N];

        for (int j = 0; j < N; j++) {
        	line[j] = Integer.parseInt(st.nextToken());
        	//System.out.println(line[j]);
        }

        //function
        int seats = k, tickets = 0, l = 0, total = 0, start = -1, end;

        for (int ride = 0; ride < R; ride++) {
        	total = 0;
        	//1 4 2 1

        	while (start != l) {
        		if (line[l]+ total <= seats) {
        			if (start == -1) {
        				start = l;
        			}
        			//System.out.print(line[l]+ " ");
        			total += line[l];
        		} else {
        			//start = 
        			break;
        		}

        		l++;
        		if (l == N) {
        			l = 0;
        		}
        	}
        	//System.out.println();
        	//l++;
        	if (l == N) {
    			l = 0;
    		}
        	start = -1;

        	tickets += total;

        	//System.out.println("total: "+ total);
        	//System.out.println("l: "+ l);
        	//total = 0;
        }
        //System.out.println();
        //totals[i] = tickets; 
        //write(T, tickets);


         //for (int i = 0; i < t; i++) {
         return String.valueOf(total);
	}*/

	/*
	//2010-a: Snapper
	public String solve (String readLine) {
		String output;
		StringTokenizer st = new StringTokenizer(readLine);

		int N = Integer.parseInt(st.nextToken());
		//System.out.println(R);
		int k = Integer.parseInt(st.nextToken());
		//System.out.println(k);

		//st = new StringTokenizer(br.readLine());
		int[] chain = new int[N];

		//function
		chain[0] = 2;
		for (int snaps = 0; snaps < k; snaps++) {
			//snap
			for (int j = 0; j < N-1; j++) {
				switch (chain[j]) {
				case 2: chain[j] = 3;
				break;

				case 3: chain[j] = 0;
				if (chain[j+1] == 2) {
					chain[j+1] = 5;
				}
				break;

				case 5: chain[j] = 3;
				break;
				}
				//System.out.print(chain[j]+ " ");
			}
			//final step
			switch (chain[N-1]) {
			case 2: chain[N-1] = 3;
			break;

			case 3: chain[N-1] = 0;
			break;

			case 5: chain[N-1] = 3;
			break;
			}
			//System.out.println(chain[N-1]);


			//power
			//first step
			switch (chain[0]) {
			case 0: chain[0] = 2;
			break;

			case 1: chain[0] = 3;
			break;
			}
			//System.out.print(chain[0]+ " ");
			//intermediate
			for (int j = 1; j < N; j++) {
				if (chain[j-1] == 3) {
					switch (chain[j]) {
					case 0: chain[j] = 2;
					break;

					case 1: chain[j] = 3;
					break;
					}
				} else {
					switch (chain[j]) {
					case 2: chain[j] = 0;
					break;

					case 3: chain[j] = 1;
					break;
					}
				}
				//System.out.print(chain[j]+ " ");
			}
			//System.out.println();
		}

		if (chain[N-1] == 3) {
			output = "ON";
		} else {
			output = "OFF";
		}

		return output;
	}*/
	
	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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
