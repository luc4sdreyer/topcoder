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

public class Jam2014A {
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
			/*
			int n = sc.nextInt();
			int l = sc.nextInt();
			sc.nextLine();
			
			ArrayList<String> sockets = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(sc.nextLine());
			for (int j = 0; j < n; j++) {
				sockets.add(st.nextToken());
			}
			ArrayList<String> devices = new ArrayList<String>();
			st = new StringTokenizer(sc.nextLine());
			for (int j = 0; j < n; j++) {
				devices.add(st.nextToken());
			}
			
			output = solve(n, l, sockets, devices);*/
			
			int n = sc.nextInt();
			ArrayList<int[]> nodes = new ArrayList<int[]>();
			for (int j = 0; j < n-1; j++) {
				int[] node = new int[2];
				node[0] = sc.nextInt();
				node[1] = sc.nextInt();
				nodes.add(node);
			}
			
			output = solve(n, nodes);
			

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

	private static String solve(int N, ArrayList<int[]> nodes) {
		int[] connections = new int[N+1];
		for (int i = 0; i < nodes.size(); i++) {
			connections[nodes.get(i)[0]]++;
			connections[nodes.get(i)[1]]++;
		}
		System.out.println();
		
		for (int n = 1; n <= N; n++) {
			Node root = new Node(null, n);
			Node[] nodeList = new Node[n];
			nodeList[n] = root;
			
			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < nodes.size(); j++) {
					
				}
			}
			
		}
		return "";
	}
	
	static class Node {
		Node parent;
		ArrayList<Node> children;
		int id;
		Node(Node parent, ArrayList<Node> children, int id) {
			this.parent = parent;
			this.children = children;
			this.id = id;
		}
		Node(Node parent, int id) {
			this.parent = parent;
			this.children = new ArrayList<Jam2014A.Node>();
			this.id = id;
		}
	}

	private static String solve(int n, int l, ArrayList<String> sockets,
			ArrayList<String> devices) {
		LinkedHashSet<Long> soc = new LinkedHashSet<Long>();
		for (int i = 0; i < sockets.size(); i++) {
			soc.add(Long.parseLong(sockets.get(i), 2));
		}
		
		LinkedHashSet<Long> dev = new LinkedHashSet<Long>();
		for (int i = 0; i < devices.size(); i++) {
			dev.add(Long.parseLong(devices.get(i), 2));
		}
		
		int[] socBits = new int[l];
		for (int i = 0; i < sockets.size(); i++) {
			for (int j = 0; j < sockets.get(i).length(); j++) {
				if (sockets.get(i).charAt(j) == '1') {
					socBits[j] += 1;
				}
			}
		}
		
		int[] devBits = new int[l];
		for (int i = 0; i < devices.size(); i++) {
			for (int j = 0; j < devices.get(i).length(); j++) {
				if (devices.get(i).charAt(j) == '1') {
					devBits[j] += 1;
				}
			}
		}
		
		boolean possible = true;
		for (int i = 0; i < devBits.length; i++) {
			if (devBits[i] != socBits[i] && devBits[i] !=  n - socBits[i]) {
				possible = false;
				break;
			}
		}
		if (!possible) {
			return "NOT POSSIBLE";
		}
		
		for (int i = 0; i < l; i++) {
			if (socBits[i]*2 == n) {
				LinkedHashSet<Long> newSoc = new LinkedHashSet<Long>();
				for (int j = 0; j < sockets.size(); j++) {
					newSoc.add(Long.parseLong(sockets.get(i), 2));
				}
				if (dev.size() == newSoc.size() && dev.containsAll(newSoc)) {
					continue;
				}
				
				newSoc = new LinkedHashSet<Long>();
				for (int j = 0; j < sockets.size(); j++) {
					char[] s = sockets.get(i).toCharArray();
					if (s[i] == '1') {
						s[i] = '0';
					} else {
						s[i] = '1';
					} 
					newSoc.add(Long.parseLong(new String(s), 2));
				}
			}
		}
		
		return "";
		
	}

	//static String size = "sample";
	//static String size = "small";
	//static String size = "large"=
	static String filename = "F:\\gcj\\2014\\1A\\B-sample";
}