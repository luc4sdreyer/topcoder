public class GreatFairyWar {

	public int minHP(int[] dps, int[] hp) {
		int d = 0;
		for (int j = 0; j < hp.length; j++) {
			while (hp[j]-- > 0) {
				for (int i = j; i < hp.length; i++) {
					d += dps[i];
				}
			}
		}
		return d;
	}

}
