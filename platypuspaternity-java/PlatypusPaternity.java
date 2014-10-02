public class PlatypusPaternity {

	public int maxFamily(String[] femaleCompatibility, String[] maleCompatibility, String[] siblingGroups) {
		int count = 0;
		int mother = 0, father = 0;
		int max = 0;
		for (int i3 = 0; i3 < maleCompatibility.length; i3++) {
		for (int i2 = 0; i2 < femaleCompatibility.length; i2++) {
		for (int group = 0; group < siblingGroups.length; group++) {
			count = 0;
			mother = 0;
			father = 0;
			for (int sib = 0; sib < siblingGroups[group].length(); sib++) {
				if (siblingGroups[group].charAt(sib) == 'Y') {
					count++;
					if (femaleCompatibility[i2].charAt(sib) == 'Y') {
						mother++;
					}
					if (maleCompatibility[i3].charAt(sib) == 'Y') {
						father++;
					}
				}
			}
			if (mother == count && father == count && count > 0) {
				max = Math.max(max, count+2);
			}
		}
		}
		}
		return max;
	}

}
