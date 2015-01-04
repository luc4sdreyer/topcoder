import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class P17 {

	public static void main(String[] args) {
		
		numberToWords(System.in);
		//test2();
	}
	public static void test2() {
		Random rand = new Random(0);
		for (int i = 0; i < 10000000; i++) {
			int a = rand.nextInt(1000000000);
			String exp = numberToWords2(a);
			String act = numberToWords(a);
			if (!exp.equals(act)) {
				System.out.print("");
				numberToWords(a);
			}
		}
	}
	
	public static void test() {
		for (int i = 1; i < 1011; i++) {
			System.out.println(i + "\t" + numberToWords(i));
		}
		System.out.println(numberToWords(10));
		System.out.println(numberToWords(100));
		System.out.println(numberToWords(1000));
		System.out.println(numberToWords(10000));
		System.out.println(numberToWords(100000));
		System.out.println(numberToWords(1000000));
	}

	public static void numberToWords(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();

			sb.append(numberToWords(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}
	

	
	public static String numberToWords(long n) {
		String[] one = {
				"",
				"One ",
				"Two ",
				"Three ",
				"Four ",
				"Five ",
				"Six ",
				"Seven ",
				"Eight ",
				"Nine ",
			};
		
		String[] ten = {
				"",
				"",
				"Twenty",
				"Thirty",
				"Forty",
				"Fifty",
				"Sixty",
				"Seventy",
				"Eighty",
				"Ninety",
			};
		
		String[] ten2 = {
				"Ten",
				"Eleven",
				"Twelve",
				"Thirteen",
				"Fourteen",
				"Fifteen",
				"Sixteen",
				"Seventeen",
				"Eighteen",
				"Nineteen",
			};
		if (n == 0) {
			return "Zero";
		}
		int i = 0;
		String num2 = "";
		while (n > 0) {
			int a = (int) (n % 1000L);
			String num = "";
			if (a != 0) {
				if (a / 100 >= 1) {
					num += one[a / 100] + "Hundred ";
				}
				if ((a % 100) / 10 >= 2) {
					num += ten[(a % 100) / 10];
					//if ((a % 10) >= 1) {
					num += " ";
					//}
				}
				if ((a % 100) == 10) {
					num += "Ten" + " ";
				} else if ((a % 10) >= 0) {
					if ((a % 100) / 10 == 1) {
						num += ten2[a % 10] + " ";
					} else {
						num += one[a % 10] + "";
					}
				}
				switch (i) {
				case 1:	num += "Thousand ";	break;
				case 2:	num += "Million ";	break;
				case 3:	num += "Billion ";	break;
				case 4:	num += "Trillion ";	break;
				}
			}
			num2 = num + num2;
			
			n /= 1000;
			i++;
		}
		return num2.trim();
	}
	
	public static String numberToWords2(long n) {
		String[] one = {
				"",
				"One",
				"Two",
				"Three",
				"Four",
				"Five",
				"Six",
				"Seven",
				"Eight",
				"Nine",
			};
		
		String[] ten = {
				"",
				"",
				"Twenty",
				"Thirty",
				"Forty",
				"Fifty",
				"Sixty",
				"Seventy",
				"Eighty",
				"Ninety",
			};
		
		String[] ten2 = {
				"Ten",
				"Eleven",
				"Twelve",
				"Thirteen",
				"Fourteen",
				"Fifteen",
				"Sixteen",
				"Seventeen",
				"Eighteen",
				"Nineteen",
			};
		
		if (n == 0) {
			return "Zero";
		}
		int i = 0;
		String num2 = "";
		while (n > 0) {
			int a = (int) (n % 1000L);
			String num = "";
			if (a != 0) {
				if (a / 100 >= 1) {
					num += one[a / 100] + " Hundred ";
				}
				if ((a % 100) / 10 >= 2) {
					num += ten[(a % 100) / 10];
					if ((a % 10) >= 1) {
						num += " ";
					}
				}
				if ((a % 100) == 10) {
					num += "Ten" + " ";
				} else if ((a % 10) >= 0) {
					if ((a % 100) / 10 == 1) {
						num += ten2[a % 10] + " ";
					} else {
						num += one[a % 10] + " ";
					}
				}
			}
			switch (i) {
			case 1:	num += "Thousand ";	break;
			case 2:	num += "Million ";	break;
			case 3:	num += "Billion ";	break;
			case 4:	num += "Trillion ";	break;
			}
			num2 = num + num2;
			
			n /= 1000;
			i++;
		}
		return num2.trim();
	}

}
