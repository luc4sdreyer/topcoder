import java.io.InputStreamReader;
import java.util.Scanner;

public class C432 {
	public static void main(String[] args) {
//		String in = "10000\n";
//		for (int i = 0; i < 10000; i++) {
//			in += i + " " + (10000-i) + "\n";
//		}
//		long time = System.currentTimeMillis();
//		System.out.println("Total time: " + (System.currentTimeMillis() - time));
		probB();
	}
	
	public static void probB() {
		Scanner s = new Scanner(
				new InputStreamReader(System.in)
				//in
				//"2\n1 2\n2 1"
				//"3\n1 2\n2 1\n1 3"
				);
		
		int n = s.nextInt();
		int[] home = new int[n];
		int[] away = new int[n];
		for (int i = 0; i < n; i++) {
			s.nextLine();
			home[i] = s.nextInt();
			away[i] = s.nextInt();
		}
		s.close();
		
		int[] outHome = new int[n];
		int[] outAway = new int[n];
		for (int i = 0; i < away.length; i++) {
			for (int j = i+1; j < away.length; j++) {
				outHome[i]++;
				if (home[i] != away[j]) {
					outAway[j]++;
				} else {
					outHome[j]++;
				}
				outHome[j]++;
				if (home[j] != away[i]) {
					outAway[i]++;
				} else {
					outHome[i]++;
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < outAway.length; i++) {
			sb.append(outHome[i]);
			sb.append(" ");
			sb.append(outAway[i]);
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	public static void probA() {
		Scanner s = new Scanner(new InputStreamReader(System.in));
		
		int n = s.nextInt();
		int k = s.nextInt();
		s.nextLine();
		int[] times = new int[n];
		for (int i = 0; i < n; i++) {
			times[i] = s.nextInt();
		}
		s.close();
		
		int num = 0; 
		for (int i = 0; i < times.length; i++) {
			if (5 - times[i] >= k) {
				num++;
			}
		}
		
		System.out.println(num/3);
	}
}
