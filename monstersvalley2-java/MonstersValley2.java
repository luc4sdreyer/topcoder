public class MonstersValley2 {

	public int minimumPrice(int[] dread, int[] price) {
		
		int partyDread = 0;
		int totalPrice = 0;
		int step = 0;
		
		int p2 = minPrice(partyDread+dread[step], totalPrice+price[step], step+1, dread, price);
		return p2;
	}

	public int minPrice(long partyDread, int totalPrice, int step, int[] dread, int[] price) {
		int min = 0;
		if (step == dread.length) {
			return totalPrice;
		}
		if (dread[step] > partyDread) {
			// has to bribe
			min = minPrice(partyDread+dread[step], totalPrice+price[step], step+1, dread, price);
		} else {
			// try bribe
			int p1 = minPrice(partyDread+dread[step], totalPrice+price[step], step+1, dread, price);
			// try fight
			int p2 = minPrice(partyDread, totalPrice, step+1, dread, price);
			min = Math.min(p1, p2);
		}
		return min;
	}

}
