import java.util.Arrays;

public class SlimeXSlimonadeTycoon {

	public int sell(int[] stock, int[] customers, int stale_limit) {
		int sold = 0;
		for (int day = 0; day < stock.length; day++) {
			for (int prevDay = day-(stale_limit-1); prevDay <= day; prevDay++) {
				if (customers[day] == 0) {
					break;
				}
				if (prevDay >= 0 && stock[prevDay] > 0) {
					int s = Math.min(stock[prevDay], customers[day]);
					stock[prevDay] -= s;
					customers[day] -= s;
					sold += s;
				}
			}
		}
		return sold;
	}

}
