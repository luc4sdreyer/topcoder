public class MixingColors {

	public int minColors(int[] colors) {
		int max = 0;
		for (int i = 0; i < colors.length; i++) {
			max = Math.max(max, colors[i]);
		}
		return Math.min(colors.length, (int) (Math.round(Math.log(max)/Math.log(2))));
	}

}
