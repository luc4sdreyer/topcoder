public class RabbitNumber {

	public int theCount(int low, int high) {
		int count = 0;
		for (int i = 0; i < 10000; i++) {
			if (check(i)) {
				System.out.println("i: " + i + "\t" + check(i) + "\t" + rabbit(i));
				count++;
			}
		}
		System.out.println(count);
		return 0;
	}

	private boolean check(int i) {
		long a = rabbit(i);
		a *= a;
		
		long b = rabbit(i*i);
		if (a == b) {
			return true;
		} else {
			return false;
		}
	}
	
	private long rabbit(long i) {
		int sum = 0;
		while (i > 0) {
			long level = (int)Math.pow(10, (int)Math.log10(i));
			long rem = i / level;
			sum += rem;
			i = i % level;
		}
		return sum;
	}

}
