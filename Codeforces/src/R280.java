import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class R280 {

	public static void main(String[] args) {
		//System.out.println(vanyaAndCubes(System.in));
		//System.out.println(vanyaAndLanterns(System.in));
		//System.out.println(vanyaAndExams(System.in));
		System.out.println(vanyaAndComputerGame(System.in));
	}

	public static String vanyaAndComputerGame(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int x = input.nextInt();
		int y = input.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = input.nextInt();
		}
		input.close();
		return vanyaAndComputerGame(n, x, y, a)+"";
	}

	public static String vanyaAndComputerGame(int n, long x, long y, int[] a) {
		StringBuilder sb = new StringBuilder();
		String[] rez = new String[(int) (x+y)];
		int t = 0;
		long[] counter = new long[2];
		while (counter[0] < x || counter[1] < y) {
			if ((counter[0]+1.0)*y > (counter[1]+1.0)*x) {
				rez[t++] = "Vova";
				counter[1]++;
			} else if ((counter[0]+1.0)/x < (counter[1]+1.0)/y) {
				rez[t++] = "Vanya";
				counter[0]++;
			} else {
				rez[t++] = "Both";
				rez[t++] = "Both";
				counter[0]++;
				counter[1]++;
			}
		}
		for (int i = 0; i < a.length; i++) {
			sb.append(rez[(int) ((a[i]-1) % (x+y))] + "\n");
		}
		return sb.toString();
	}

	public static String vanyaAndExams(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int max = input.nextInt();
		int avg = input.nextInt();
		int[][] a = new int[n][2];
		for (int i = 0; i < a.length; i++) {
			a[i][0] = input.nextInt();
			a[i][1] = input.nextInt();
		}
		input.close();
		return vanyaAndLanterns(n, max, avg, a)+"";
	}
	
	public static String vanyaAndLanterns(int n, int max, int avg, int[][] a) {
		Arrays.sort(a, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {				
				return Integer.compare(o1[1], o2[1]);
			}			
		});
		
		long currentAvg = 0;
		for (int i = 0; i < a.length; i++) {
			currentAvg += (long)a[i][0];
		}
		long reqPoints = (long)n * (long)avg - currentAvg;
		long numEssays = 0;
		int idx = 0;
		while (reqPoints > 0) {
			while (a[idx][0] >= max) {
				idx++;
			}
			long added = Math.min(reqPoints, max - a[idx][0]);
			reqPoints -= added;
			a[idx][0] += added;
			numEssays += ((long)a[idx][1] * added);	
		}
		return numEssays+"";
	}

	public static String vanyaAndLanterns(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int l = input.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = input.nextInt();
		}
		input.close();
		
		Arrays.sort(a);
		double max = 0;
		max = Math.max(max, a[0] - 0);
		max = Math.max(max, l - a[a.length-1]);
		for (int i = 1; i < a.length; i++) {
			max = Math.max(max, (a[i] - a[i-1]) / 2.0);
		}
		
		return max+"";
	}
	
	public static String vanyaAndCubes(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		input.close();
		int num = 1;
		int i = 1;
		while (num <= n) {
			num += (i+1)*(i+2)/2;
			if (num <= n) {
				i++;
			}			
		}
		return i+"" ;
	}
}
