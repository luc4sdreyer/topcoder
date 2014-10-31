public class ToastXToast {

	public int bake(int[] undertoasted, int[] overtoasted) {
		int maxUnder = Integer.MIN_VALUE;
		int minOver = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		boolean maxTypeUnder = false;
		int min = Integer.MAX_VALUE;
		boolean minTypeOver = false;
		for (int i = 0; i < undertoasted.length; i++) {
			maxUnder = Math.max(maxUnder, undertoasted[i]);
			max = Math.max(max, undertoasted[i]);
			min = Math.min(min, undertoasted[i]);
			if (max == undertoasted[i]) {
				maxTypeUnder = true;
			}
			if (min == undertoasted[i]) {
				minTypeOver = false;	
			}
		}
		for (int i = 0; i < overtoasted.length; i++) {
			minOver = Math.min(minOver, overtoasted[i]);
			max = Math.max(max, overtoasted[i]);
			min = Math.min(min, overtoasted[i]);
			if (max == overtoasted[i]) {
				maxTypeUnder = false;
			}
			if (min == overtoasted[i]) {
				minTypeOver = true;
			}
		}
		if (maxTypeUnder == true || minTypeOver == true) {
			return -1;
		}
		if (maxUnder < minOver) {
			return 1;
		} else {
			return 2;
		}
	}

}
