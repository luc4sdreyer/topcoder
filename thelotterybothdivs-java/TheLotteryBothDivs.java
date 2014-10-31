import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TheLotteryBothDivs {

	public double find(String[] goodSuffixes) {
		HashSet<String> set = new HashSet<>();
		for (int i = 0; i < goodSuffixes.length; i++) {
			boolean found = false;
			for (int j = 0; j < goodSuffixes[i].length(); j++) {
				if (set.contains(goodSuffixes[i].substring(j, goodSuffixes[i].length()))) {
					found = true;
					break;
				}
			}
			if (!found) {
				ArrayList<String> toRem = new ArrayList<>();
				for (String string : set) {
					boolean sub = true;
					for (int j = 0; j < goodSuffixes[i].length() && j < string.length(); j++) {
						if (goodSuffixes[i].charAt(goodSuffixes[i].length()-1 -j) != string.charAt(string.length()-1 -j)) {
							sub = false;
							break;
						}
					}
					if (sub) {
						toRem.add(string);
					}
				}
				for (int j = 0; j < toRem.size(); j++) {
					set.remove(toRem.get(j));
				}
				set.add(goodSuffixes[i]);
			}
		}
		double p = 0;
		for (String string : set) {
			p += Math.pow(10, -string.length());
		}
		return p;
	}

}