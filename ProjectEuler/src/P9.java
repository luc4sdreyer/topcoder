import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class P9 {

	public static void main(String[] args) {
		specialPythagoreanTriplet(System.in);
	}

	public static void specialPythagoreanTriplet(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		HashMap<Integer, int[]> trips = getAllPythagoreanTriplets(3000);
//		for (int key: trips.keySet()) {
//			System.out.println(key + ": " + Arrays.toString(trips.get(key)));
//		}
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			sb.append(specialPythagoreanTriplet(n, trips));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static HashMap<Integer, int[]> getAllPythagoreanTriplets(int n) {
		HashMap<Integer, int[]> trips = new HashMap<>();
		HashMap<Integer, Integer> pSquares = new HashMap<>();
		for (int i = 1; i <= n; i++) {
			pSquares.put(i*i, i);
		}
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n - i; j++) {
				int cc = i*i + j*j;
				if (pSquares.containsKey(cc)) {
					int c = pSquares.get(cc);
					int sum = i+j+c; 
					if (sum > n) {
						break;
					}
					int[] t = new int[]{i, j, c};
					if (trips.containsKey(sum)) {
						int[] old = trips.get(sum);
						long newP = (long)(t[0]*t[1])*(long)(t[2]);
						long oldP = (long)(old[0]*old[1])*(long)(old[2]);
						if (newP > oldP) {
							trips.put(sum, t);
						}
					} else {
						trips.put(sum, t);
					}
				}
			}
		}
		return trips;
	}

	private static String specialPythagoreanTriplet(int n, HashMap<Integer, int[]> trips) {
		if (trips.containsKey(n)) {
			int[] t = trips.get(n);
			return (t[0] * t[1] * t[2]) + ""; 
		}
		return "-1";
	}
}
