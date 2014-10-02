package singleton;

public class Test {
	Elvis elvis = Elvis.INSTANCE;
	public Test() {
	}
	
	public static void main(String[] args) {
		Test t1 = new Test();
		Test t2 = new Test();
		System.out.println("Is it the same Elvis? " + (t1.elvis == t2.elvis));
	}
	
}
