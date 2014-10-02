package extensibleEnums;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		System.out.println("Enter two numbers");
		Scanner scanner = new Scanner(System.in);
		double x = scanner.nextDouble();
		double y = scanner.nextDouble();
		test(BasicOperation.class, x, y);
		test(ExtendedOperation.class, x, y);
		scanner.close();
	}
	private static <T extends Enum<T> & Operation & Cloneable> void test(
			Class<T> opSet, double x, double y) {
		for (Operation op : opSet.getEnumConstants()) {
			System.out.printf("%f %s %f = %f%n",
					x, op, y, op.apply(x, y));
		}
	}
}
