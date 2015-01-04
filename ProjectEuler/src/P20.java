import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class P20 {

	public static void main(String[] args) {
		factorialDigitSum(System.in);
	}
	
	public static void factorialDigitSum(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			sb.append(factorialDigitSum(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}
	
	public static String factorialDigitSum(int n) {
		BigInteger a = BigInteger.ONE;
		for (int i = 1; i <= n; i++) {
			a = a.multiply(BigInteger.valueOf(i));
		}
		char[] c = a.toString().toCharArray();
		int sum = 0;
		for (int i = 0; i < c.length; i++) {
			sum += c[i] - '0';
		}
		return sum+"";
	}

}
