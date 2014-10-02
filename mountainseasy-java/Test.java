
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(calc(2,1,3));
		//System.out.println(calc(2,2,5));
		System.out.println(calc(3,2,5));
		System.out.println(calc(4,3,14));
		System.out.println(calc2(10,4,19));
		System.out.println(calc(4,2,5));
		

	}

	public static long calc(int N, int P, int A) {		
		//return (A*N-(P*(P+1)/2))*factorial(P);
		System.out.println("factorial(P): "+factorial(P));
		return ((long)Math.pow(A,N-P)*N-(P*(P+1)/2))*factorial(P) % 1000000009;
	}
	public static long calc2(int N, int P, int A) {		
		//return (A*N-(P*(P+1)/2))*factorial(P);
		System.out.println("factorial(P): "+factorial(P));
		return ((long)(Math.pow(A,2)*N-Math.pow(N,2)+1))*factorial(P);
	}
	
	public static long factorial(long n) {
		long f = 1;
		for (int i = 1; i <= n; i++) {
			f *= i;
		}
		return f;
	} 

}
