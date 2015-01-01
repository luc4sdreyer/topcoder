import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P11 {

	public static void main(String[] args) {
		largestProductInAGrid(System.in);
	}

	public static void largestProductInAGrid(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		int[][] a = new int[20][20];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = scan.nextInt();
			}
		}
		sb.append(largestProductInAGrid(a));
		sb.append("\n");
		scan.close();
		System.out.println(sb.toString());
	}

	public static String largestProductInAGrid(int[][] a) {
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 3; j < a[0].length; j++) {
				int prod = 1;
				for (int k = 0; k < 4; k++) {
					prod *= a[i][j-k];
				}
				max = Math.max(max, prod);
			}
		}
		
		for (int j = 0; j < a[0].length; j++) {
			for (int i = 3; i < a.length; i++) {
				int prod = 1;
				for (int k = 0; k < 4; k++) {
					prod *= a[i-k][j];
				}
				max = Math.max(max, prod);
			}
		}
		
		for (int j = 3; j < a[0].length; j++) {
			for (int i = 3; i < a.length; i++) {
				int prod = 1;
				for (int k = 0; k < 4; k++) {
					prod *= a[i-k][j-k];
				}
				max = Math.max(max, prod);
			}
		}

		for (int i = 3; i < a.length; i++) {
			for (int j = 0; j < a[0].length-3; j++) {
				int prod = 1;
				for (int k = 0; k < 4; k++) {
					prod *= a[i-k][j+k];
				}
				max = Math.max(max, prod);
			}
		}
		
		return max+"";
	}

}
