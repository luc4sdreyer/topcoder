import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class R281 {
	public static void main(String[] args) {
		//System.out.println(football());
		//System.out.println(wrestling());
		System.out.println(basketball());
	}
	
	public static String basketball() {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int[] dist1 = new int[n];
		for (int i = 0; i < n; i++) {
			dist1[i] = s.nextInt();
		}
		int m = s.nextInt();
		int[] dist2 = new int[m];
		for (int i = 0; i < m; i++) {
			dist2[i] = s.nextInt();
		}
		s.close();
		return basketballF(n, dist1, m, dist2);
	}
	public static String basketballF(int n, int[] dist1, int m, int[] dist2) {
		Arrays.sort(dist1);
		Arrays.sort(dist2);
		int max = 0;
		int[] maxScore = new int[2];
		
		int[] all = new int[n+m];
		int[] idx = new int[2];
		for (int i = 0; i < all.length; i++) {
			if (i < dist1.length) {
				all[i] = dist1[idx[0]++];
			} else {
				all[i] = dist2[idx[1]++];
			}
		}
		Arrays.sort(all);
		idx[0] = 0;
		idx[1] = 0;
		int[] len = {n, m};
		
		int d = 0;
		while (idx[0] < dist1.length && dist1[idx[0]] <= d) {
			idx[0]++;
		}
		while (idx[1] < dist2.length && dist2[idx[1]] <= d) {
			idx[1]++;
		}
		int[] scores = new int[2];
		for (int j = 0; j < scores.length; j++) {
			scores[j] = idx[j]*2 + (len[j] - idx[j])*3;
		}
		int adv = scores[0] - scores[1];
		if (adv > max) {
			max = adv;
			maxScore[0] = scores[0];
			maxScore[1] = scores[1];
		} else if (adv == max && scores[0] > maxScore[0]) {
			maxScore[0] = scores[0];
			maxScore[1] = scores[1];
		}
		
		for (int i = 0; i < all.length; i++) {
			d = all[i];
			while (idx[0] < dist1.length && dist1[idx[0]] <= d) {
				idx[0]++;
			}
			while (idx[1] < dist2.length && dist2[idx[1]] <= d) {
				idx[1]++;
			}
			scores = new int[2];
			for (int j = 0; j < scores.length; j++) {
				scores[j] = idx[j]*2 + (len[j] - idx[j])*3;
			}
			adv = scores[0] - scores[1];
			if (adv > max) {
				max = adv;
				maxScore[0] = scores[0];
				maxScore[1] = scores[1];
			} else if (adv == max && scores[0] > maxScore[0]) {
				maxScore[0] = scores[0];
				maxScore[1] = scores[1];
			}
		}
		return maxScore[0] + ":" + maxScore[1];
	}
	
	public static String basketballF3(int n, int[] dist1, int m, int[] dist2) {
		Arrays.sort(dist1);
		Arrays.sort(dist2);
		int max = 0;
		int[] maxScore = new int[2];
		for (int i = 0; i < dist1.length; i++) {
			
			int[] scores = new int[2];
			scores[0] = (i+1)*2 + (n - i-1)*3;
			int numLess = 0;
			for (int j = 0; j < dist2.length; j++) {
				if (dist2[j] <= dist1[i]) {
					numLess++;
				} else {
					break;
				}
			}

			scores[1] = (numLess)*2 + (m - numLess)*3;
			System.out.println(Arrays.toString(scores));
			int adv = scores[0] - scores[1];
			if (adv > max) {
				max = adv;
				maxScore[0] = scores[0];
				maxScore[1] = scores[1];
			} else if (adv == max && scores[0] > maxScore[0]) {
				maxScore[0] = scores[0];
				maxScore[1] = scores[1];
			}
		}
		return maxScore[0] + " " + maxScore[1];
	}
	
	public static String basketballF2(int n, int[] dist1, int m, int[] dist2) {
		long low = 0;
		long high = Integer.MAX_VALUE;
		while (low <= high) {
			int d = (int) ((low + high)/2);
			int[] scores = new int[2];
			for (int i = 0; i < dist1.length; i++) {
				if (dist1[i] <= d) {
					scores[0] += 2;
				} else {
					scores[0] += 3;
				}
			}
			for (int i = 0; i < dist2.length; i++) {
				if (dist2[i] <= d) {
					scores[1] += 2;
				} else {
					scores[1] += 3;
				}
			}
		}
		return null;
	}

	public static String wrestling() {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int[] points = new int[n];
		for (int i = 0; i < n; i++) {
			points[i] = s.nextInt();
		}
		s.close();
		return wrestlingF(n, points);
	}
	
	public static String wrestlingF(int n, int[] points) {
		long[] scores = new long[2];
		boolean firstWasLast = true;
		ArrayList<Integer> list1 = new ArrayList<>();
		ArrayList<Integer> list2 = new ArrayList<>();
		for (int i = 0; i < points.length; i++) {
			if (points[i] > 0) {
				scores[0] += points[i];
				firstWasLast = true;
				list1.add(points[i]);
			} else {
				scores[1] += -1*points[i];
				firstWasLast = false;
				list2.add(-1*points[i]);
			}
		}
		boolean firstWasWinner = true; 
		if (scores[0] == scores[1]) {
			int comp = 0;
			boolean equal = true;
			for (int i = 0; i < list1.size() && i < list2.size(); i++) {
				if (list1.get(i) != list2.get(i)) {
					equal = false;
					comp = list1.get(i) > list2.get(i) ? 1 : -1;
					break;
				}
			}
			if (equal) {
				if (list1.size() == list2.size()) {
					comp = 0;
				} else {
					comp = list1.size() > list2.size() ? 1 : -1;
				}				
			} else {
				
			}
			if (comp == 0) {
				firstWasWinner = firstWasLast;
			} else {
				firstWasWinner = comp < 0 ? false : true;
			}
		} else {
			firstWasWinner = scores[0] > scores[1] ? true : false;
		}
		return firstWasWinner ? "first" : "second";
	}

	public static String football() {
		Scanner s = new Scanner(System.in);
		String[] homeAway = new String[]{s.next(), s.next()};
		int n = s.nextInt();
		int[][] numCards = new int[100][2];
		boolean[][] hasRed = new boolean[100][2];
		String out = "";
		for (int i = 0; i < n; i++) {
			int minute = s.nextInt();
			int home = s.next().equals("h") ? 0 : 1;
			int number = s.nextInt();
			int type = s.next().equals("y") ? 1 : 2;
			numCards[number][home] += type;
			if (numCards[number][home] >= 2 && !hasRed[number][home]) { 
				out += homeAway[home] + " " + number + " " + minute + "\n";
				hasRed[number][home] = true;
			}
		}
		s.close();
		return out;
	}
}
