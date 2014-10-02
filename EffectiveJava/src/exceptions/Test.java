package exceptions;

import java.io.IOException;

public class Test {
	public void get(int n) throws IOException {
		if (n == 0) {
			throw new RuntimeException();
		}
		if (n == 1) {
			throw new IOException();
		}
	}
	public static void main(String[] args) {
		Test test = new Test();
		try {
			test.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			test.get(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
