public class EllysTSP {

	public int getMax2(String places) {
		int numC = 0;
		int numV = 0;
		for (int i = 0; i < places.length(); i++) {
			if (places.charAt(i) == 'V') {
				numV++;
			} else {
				numC++;
			}
		}
		if (Math.min(numC, numV) == 0) {
			return 1;
		} else if (Math.max(numC, numV) - Math.min(numC, numV) < 2) {
			return numC + numV;
		} else {
			return Math.min(numC, numV)*2 + 1; 
		}
	}

	public int getMax3(String places)
	{
		int balance = 0;
		for (int i = 0; i < places.length(); i++)
			balance += places.charAt(i) == 'C' ? -1 : 1;
		return places.length() - Math.max(0, Math.abs(balance) - 1);
	}

	public int getMax(String places)
	{
		int numC = 0;
		int numV = 0;
		for (int i = 0; i < places.length(); i++) {
			if (places.charAt(i) == 'V') {
				numV++;
			} else {
				numC++;
			}
		}
		return places.length() - Math.max(0, Math.abs(numV-numC) - 1);
	}

}
