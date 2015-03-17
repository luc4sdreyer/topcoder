
public class Main {
	public boolean squareFreeInteger(int n) {
		float i = 2;
		for (; n/i % i > 0 && i++ < n;);
		return i > n;
	}
	/*
	public boolean squareFreeInteger(int n) {
		for (float i = 2; i <= n; i++) {
			if ((n/i) % i == 0) {
				return 0 > 1;
			}
		}
		return 0 < 1;
	}
	*/
	/*
	public boolean squareFreeInteger(int n) {
		for (int i = 2; i < n; i++) {
			for (int j = 0; j < 3; j++) {
				if (n % i == 0) {
					n /= i;
					if (j > 0) {
						return false;
					}
				}
			} 
		}
		return true;
	}
	*/

	/*
	public String primesUnder1000(int i) {
		int n = i < 2 ? 999 : i;
		for (i = 2; n % i++ > 0;);
		return n < 3 ? "" : primesUnder1000(n-1) + (i > n ? n + "," : "");
	}

	public String xprimesUnder1000(int n) {
		int i = 2;
		while (n % i++ > 0);
		if (n < 999) {
			if (i > n) {
				return n + "," + xprimesUnder1000(n+1);
			} else {
				return xprimesUnder1000(n+1);
			}
		} else {
			return "";
		}
	}
	*/
	
	public String primesUnder1000(int i) {
		String x = "2";
		for (int n = 2; ++n < 999;) {
			for (i = 2; n % i++ > 0;);
			if (i > n) x += "," + n;
		}
		return x;
	}

	/*
	public String primesUnder1000(int b) {
		String x = "2";
		for (int i = 2, n = 3; n < 999;) {
			if (n % i++ == 0) {
				if (i > n) x += "," + n;
				n++;
				i = 2;
			}
		}
		return x;
	}
	 */
	/*
	String x = "";
	int i;
	String primesUnder1000(int n) {
		if (n < 999) {
			for (i = 2; i < n && n % i != 0; i++);
			x += (i == n) ? (i == 2 ? "" : ",") + n : "";
			primesUnder1000(n+1);
		}
		return x;
	}
	 */

	/*
	static int factorialTrailingZeros(int n) {  
		int result = 0;
		for (int i = 5; i <= n; i += 5) {
			int number = i;
			while (number % 5 == 0) {
				number /= 5;
				result++;
			}
		}
		return result;
	}
	 */
}
