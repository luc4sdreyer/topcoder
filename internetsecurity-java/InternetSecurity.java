import java.util.HashSet;

public class InternetSecurity {

	public String[] determineWebsite(String[] address, String[] keyword, String[] dangerous, int threshold) {
		HashSet<String> dangerWords = new HashSet<>();
		HashSet<String> safeSites = new HashSet<>();
		for (int i = 0; i < address.length; i++) {
			safeSites.add(address[i]);
		}
		for (int i = 0; i < dangerous.length; i++) {
			dangerWords.add(dangerous[i]);
		}
		boolean added = true;
		while (added) {
			added = false;
			String rem = "";
			for (int i = 0; i < address.length; i++) {
				String site = address[i];
				if (safeSites.contains(site)) {
					String[] words = keyword[i].split(" ");
					int c = 0;
					for (int j = 0; j < words.length; j++) {
						if (dangerWords.contains(words[j])) {
							c++;
						}
					}
					if (c >= threshold) {
						added = true;
						for (int j = 0; j < words.length; j++) {
							dangerWords.add(words[j]);
						}
						rem = site;
						break;
					}
				}
			}
			if (added) {
				safeSites.remove(rem);
			}
		}
		String[] ret = new String[address.length - safeSites.size()];
		int i = 0;
		for (String site : address) {
			if (!safeSites.contains(site)) {
				ret[i++] = site;
			}
		}
		return ret;
	}

}
