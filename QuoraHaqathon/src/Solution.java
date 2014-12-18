import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		//System.out.println(archery(System.in));
		//System.out.println(schedule(System.in));
		System.out.println(upvotes(System.in));		
	}
	
	public static String upvotes(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int k = scan.nextInt();
		int[] votes = new int[n];
		for (int i = 0; i < n; i++) {
			votes[i] = scan.nextInt();
		}
		scan.close();
		return upvotes2(n, k, votes);
	}
	
	public static String upvotes(int n, int k, int[] votes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= n-k; i++) {
			long[] net = {0, 0};
			long length = 1;
			for (int j = i+1; j < i + k; j++) {
				if (votes[j] >= votes[j-1]) {
					length++;
				} else{
					net[0] += length*(length-1)/2;
					length = 1;
				}
			}
			net[0] += length*(length-1)/2;
			
			length = 1;
			for (int j = i+1; j < i + k; j++) {
				if (votes[j] <= votes[j-1]) {
					length++;
				} else{
					net[1] += length*(length-1)/2;
					length = 1;
				}
			}
			net[1] += length*(length-1)/2;
			sb.append(net[0] - net[1]);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static String upvotes2(int n, int k, int[] votes) {
		int i = 0;
		long[] net = {0, 0};
		long length = 1;
		ArrayList<ArrayList<Long>> lengths = new ArrayList<>();
		lengths.add(new ArrayList<Long>());
		lengths.add(new ArrayList<Long>());
		
		for (int j = i+1; j < i + k; j++) {
			if (votes[j] >= votes[j-1]) {
				length++;
			} else{
				net[0] += length*(length-1)/2;
				lengths.get(0).add(length);
				length = 1;
			}
		}
		net[0] += length*(length-1)/2;
		lengths.get(0).add(length);
		
		length = 1;
		for (int j = i+1; j < i + k; j++) {
			if (votes[j] <= votes[j-1]) {
				length++;
			} else{
				net[1] += length*(length-1)/2;
				lengths.get(1).add(length);
				length = 1;
			}
		}
		net[1] += length*(length-1)/2;
		lengths.get(1).add(length);

		StringBuilder sb = new StringBuilder();
		if (k == 1) {
			for (int j = i; j < n; j++) {
				sb.append(0);
				sb.append("\n");
			}
			return sb.toString();
		}

		sb.append(net[0] - net[1]);
		sb.append("\n");
		
		for (i = 1; i <= n-k; i++) {
			for (int j = 0; j < 2; j++) {
				long oldLen = lengths.get(j).get(0);
				long newLen = oldLen-1;
				lengths.get(j).set(0, newLen);
				net[j] += (newLen*(newLen-1)/2 - oldLen*(oldLen-1)/2);
				if (newLen == 0) {
					lengths.get(j).remove(0);
				}
			}
			for (int j = 0; j < 2; j++) {
				int end = i + k-1;
				long oldLen = lengths.get(j).get(lengths.get(j).size()-1);
				
				if ((j == 0 && votes[end] >= votes[end-1]) || (j == 1 && votes[end] <= votes[end-1])) {
					long newLen = oldLen + 1;
					lengths.get(j).set(lengths.get(j).size()-1, newLen);
					net[j] += (newLen*(newLen-1)/2 - oldLen*(oldLen-1)/2);
				} else {
					lengths.get(j).add(1L);
				}
			}
			sb.append(net[0] - net[1]);
			sb.append("\n");
			
		}
		return sb.toString();
	}
	
	public static String schedule(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		double[][] t = new double[n][3];
		for (int i = 0; i < t.length; i++) {
			t[i][0] = (double)scan.nextInt();
			t[i][1] = (double)scan.nextDouble();
			t[i][2] = t[i][0] * t[i][1]; 
		}
		scan.close();
		ArrayList<double[]> a = new ArrayList<>();
		for (int i = 0; i < t.length; i++) {
			a.add(new double[]{t[i][0], t[i][1]});
		}
		
		return schedule2(n, a);
	}

	public static String schedule2(int n, ArrayList<double[]> a) {
		Collections.sort(a, new Comparator<double[]>(){
			@Override
			public int compare(double[] d0, double[] d1) {				
				return Double.compare(d0[0]*(1-d1[1]), d1[0]*(1-d0[1]));
			}			
		});
		
		double min = 0;
		double exp = 0;
		for (int i = a.size()-1; i >= 0; i--) {
			exp = a.get(i)[0] + a.get(i)[1] * exp;
		}		
		min = exp;
		
		return min+"";
	}

	public static String archery(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int[] r = new int[n];
		for (int i = 0; i < r.length; i++) {
			r[i] = scan.nextInt();
		}
		int m = scan.nextInt();
		long[][] p1 = new long[m][2];
		long[][] p2 = new long[m][2];
		for (int i = 0; i < m; i++) {
			p1[i][0] = scan.nextInt();
			p1[i][1] = scan.nextInt();
			p2[i][0] = scan.nextInt();
			p2[i][1] = scan.nextInt();
		}
		scan.close();

		return archeryF(n, r, m, p1, p2) + "";
	}

	public static long archeryF(int n, int[] r, int m, long[][] p1, long[][] p2) {
		long[] rCum = new long[1500000];
		long[] rCount = new long[1500000];
		Arrays.sort(r);
		for (int i = 0; i < n; i++) {
			rCount[r[i]]++;
		}
		for (int i = 1; i < rCum.length; i++) {
			rCum[i] = rCum[i-1] + rCount[i];
		}

		long num = 0;
		for (int i = 0; i < m; i++) {
			double r1 = Math.sqrt(p1[i][0] * p1[i][0] + p1[i][1] * p1[i][1]);
			double r2 = Math.sqrt(p2[i][0] * p2[i][0] + p2[i][1] * p2[i][1]);
			int rMin = (int) Math.min(r1, r2);
			int rMax = (int) Math.max(r1, r2);
			num += rCum[rMax] - rCum[rMin];
		}
		return num;
	}
}