import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class R274 {
	public static void main(String[] args) {
		//System.out.println(expression(System.in));
		//System.out.println(towers(System.in));
		System.out.println(exams(System.in));
	}
	
	public static String exams(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int[][] t = new int[n][2];
		for (int i = 0; i < n; i++) {
			t[i][0] = input.nextInt();
			t[i][1] = input.nextInt();
		}
		input.close();
		return towers(n, t) + "";
	}
	
	public static String towers(int n, int[][] t) {
		Arrays.sort(t, new Comparator<int[]>() {
		     @Override
		     public int compare(int[] o1, int[] o2) {
		    	 if (o1[0] == o2[0]) {
		    		 return ((Integer) o1[1]).compareTo(o2[1]); 
		    	 }
		         return ((Integer) o1[0]).compareTo(o2[0]);
		     }
		});
		
		int d = 1;
		for (int i = 0; i < t.length; i++) {
			if (t[i][1] >= d) {
				d = t[i][1];
			} else {
				d = t[i][0];
			}
		}
		return d + "";
	}

	public static String towers(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int k = input.nextInt();
		int[] h = new int[n];
		for (int i = 0; i < n; i++) {
			h[i] = input.nextInt();
		}
		input.close();
		return towers(n, k ,h) + "";
	}
	
	public static String towers(int n, int k, int[] h) {
		ArrayList<Integer> ops = new ArrayList<>();
		ArrayList<Integer> bestOps = new ArrayList<>();
		int instability = Integer.MAX_VALUE; 
		
		for (int i = 0; i < k; i++) {
			int[] ht = h.clone();
			Arrays.sort(ht);
			int newInst = ht[ht.length-1] - ht[0];
			if (newInst < instability) {
				instability = newInst;
				bestOps = new ArrayList<>(ops);
			}
			int minIdx = 0;
			int min = 10000;
			int maxIdx = 0;
			int max = 0;
			for (int j = 0; j < h.length; j++) {
				if (h[j] < min) {
					min = h[j];
					minIdx = j;
				}
			}
			for (int j = 0; j < h.length; j++) {
				if (h[j] > max && j != minIdx) {
					max = h[j];
					maxIdx = j;
				}
			}
			h[maxIdx]--;
			h[minIdx]++;
			ops.add(maxIdx);
			ops.add(minIdx);
		}
		int[] ht = h.clone();
		Arrays.sort(ht);
		int newInst = ht[ht.length-1] - ht[0];
		if (newInst < instability) {
			instability = newInst;
			bestOps = new ArrayList<>(ops);
		}
		String out = instability + " " + bestOps.size()/2+"\n";
		for (int i = 0; i < bestOps.size(); i+=2) {
			out += (bestOps.get(i)+1) + " " + (bestOps.get(i+1)+1) + "\n";
		}
		return out;
	}

	public static String expression(InputStream in) {
		Scanner input = new Scanner(in);
		int a = input.nextInt();
		int b = input.nextInt();
		int c = input.nextInt();
		input.close();
		return expressionF(a, b, c) + "";
	}
	
	public static int expressionF(int a, int b, int c) {
		int max = 0;
		max = Math.max(max, a+b+c);
		max = Math.max(max, a*b*c);
		max = Math.max(max, a+b*c);
		max = Math.max(max, (a+b)*c);
		max = Math.max(max, a*(b+c));
		max = Math.max(max, a*b+c);
		return max;
	}
}
