import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class R269 {
	public static final long mod = 1000000007;
	
	public static void main(String[] args) {
		//System.out.println(sticks(System.in));
		System.out.println(importantThings(System.in));		
	}

	private static String importantThings(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int[][] a = new int[n][2];
		for (int i = 0; i < a.length; i++) {
			a[i][0] = scan.nextInt();
			a[i][1] = i+1; 
		}
		scan.close();
		return importantThings(a) + "";
	}
	
	public static ArrayList<int[]> p = new ArrayList<>();
	public static String importantThings(int[][] a) {
		Arrays.sort(a, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[0], o2[0]);
			}
		});
		int[] len = new int[2001];
		for (int i = 0; i < a.length; i++) {
			len[a[i][0]]++;
		}
		long numPaths = 1;
		int max = 0;
		Arrays.sort(len);
		for (int i = 0; i < len.length; i++) {
			if (len[i] != 0) {
				numPaths *= len[i];
			}
			max = Math.max(max, len[i]);
		}
		if (numPaths >= 3) {
			System.out.println("YES");
			int[] path = new int[a.length];
			for (int i = 0; i < a.length; i++) {
				path[i] = a[i][1];
			}
			p.add(path.clone());
			for (int i = 2; i < a.length; i++) {
				if (a[i][0] == a[i-1][0] && a[i-1][0] == a[i-2][0]) {					
					if (p.size() >= 3){
						break;
					}
					for (int j = 1; j <= 2; j++) {
						int[] newPath = path.clone();
						int temp = newPath[i];
						newPath[i] = newPath[i-j];
						newPath[i-j] = temp;
						p.add(newPath);
						if (p.size() >= 3){
							break;
						}
					}
				}
			}
			if (p.size() < 3){
				for (int i = 1; i < a.length; i++) {
					if (a[i][0] == a[i-1][0]) {
						for (int j = 1; j <= 1; j++) {
							int[] newPath = path.clone();
							int temp = newPath[i];
							newPath[i] = newPath[i-j];
							newPath[i-j] = temp;
							p.add(newPath);
							if (p.size() >= 3){
								break;
							}
						}
					}
				}
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < p.get(i).length; j++) {
					System.out.print(p.get(i)[j] + " ");
				}
				System.out.println();
			}
			
		} else {
			System.out.println("NO");
		}		
		return "";
	} 
	
	public static String importantThings2(int[][] a) {
		Arrays.sort(a, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[0], o2[0]);
			}
		});
		int min = a[0][0];
		for (int i = 0; i < a.length; i++) {
			if (a[i][0] == min) {
				int[] path = new int[a.length];
				path[0] = a[i][1];
				getPaths(a, min, path, 1);
			}
		}
		if (p.size() >= 3) {
			System.out.println("YES");
			for (int i = 0; i < 3; i++) {
				System.out.println(Arrays.toString(p.get(i)));
			}
		} else {
			System.out.println("NO");
		}
		return "";
	}

	private static void getPaths(int[][] a, int min, int[] path, int depth) {
		if (depth == a.length) {
			p.add(path);
			return;
		}
		if (p.size() >= 3) {
			return;
		}
		int newMin = -1;
		for (int i = 0; i < a.length; i++) {
			if (a[i][0] > min && newMin == -1) {
				newMin = a[i][0];
				break;
			}
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i][0] == newMin) {
				int[] newPath = path.clone();
				newPath[depth] = a[i][1];
				getPaths(a, newMin, newPath, depth+1);
			}
		}
	}

	public static String sticks(InputStream in) {
		Scanner scan = new Scanner(in);
		int[] a = new int[6];
		for (int i = 0; i < a.length; i++) {
			a[i] = scan.nextInt();
		}
		scan.close();
		return sticks(a) + "";
	}

	private static String sticks(int[] a) {
		int[] len = new int[10];
		for (int i = 0; i < a.length; i++) {
			len[a[i]]++;			
		}
		boolean legs = false;
		for (int i = 0; i < len.length; i++) {
			if (len[i] >= 4) {
				legs = true;
				len[i] -= 4;
			}			
		}
		Arrays.sort(len);
		if (legs && len[len.length-1] == 2) {
			return "Elephant";
		} else if (legs && len[len.length-1] == 1) {
			return "Bear";
		}
		return "Alien";
	}
}
