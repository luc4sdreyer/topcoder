import java.util.Scanner;

public class Circuits {

	public int howLong(String[] connects, String[] costs) {
		boolean[][] connectionMap = new boolean[connects.length][connects.length];
		int[][] costMap = new int[connects.length][connects.length];
		Scanner sc;
		Scanner sc2;
		for (int i = 0; i < connects.length; i++) {
			 sc = new Scanner(connects[i]);
			 sc2 = new Scanner(costs[i]);
			 while(sc.hasNext()) {
				 int x = sc.nextInt();
				 connectionMap[i][x] = true;
				 costMap[i][x] = sc2.nextInt();
			 }
			 sc.close();
			 sc2.close();
		}
		
		return 0;
	}

}
