public class MagicCandy {

	public int whichOne(int n) {
		int x = 0;
		int a = n;
		while (a != 1) {
			int sqrt = (int)(Math.sqrt(a));
			if (a == sqrt * sqrt) {
				x--;
			}
			a = a - sqrt;
		}
		return n + x;
	}

}
