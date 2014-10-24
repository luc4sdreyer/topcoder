public class GreaterGameDiv2 {

	public int calc(int[] snuke, int[] sothe) {
		int snu = 0;
		for (int i = 0; i < sothe.length; i++) {
			if (snuke[i] > sothe[i]) {
				snu++;
			}
		}
		return snu;
	}

}
