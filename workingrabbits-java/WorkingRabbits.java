public class WorkingRabbits {

	public double getEfficiency(String[] profit) {
		int p = 0;
		int n = 0;
		for (int i = 0; i < profit.length; i++) {
			for (int j = i+1; j < profit.length; j++) {
				p += profit[i].charAt(j)-'0';
				n++;
			}
		}
		return p/(double)n;
	}

}
