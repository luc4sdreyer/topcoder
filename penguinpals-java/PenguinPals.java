import java.util.ArrayList;

public class PenguinPals {

	public int findMaximumMatching(String colors) {
		int maxP = 0;
		//int list.size() = list.size();
		for (int start = 0; start < colors.length(); start++) {

			ArrayList<Character> list = new ArrayList<Character>();
			for (int i = 0; i < colors.length(); i++) {
				list.add(colors.charAt(i));
			}
			//int start = 0;
			int pairs = 0;
			boolean rem = false;
			boolean found = true;
			while (found) {
				found = false;
				for (int i = start; i < list.size()/2 + list.size(); i++) {
					for (int j = 1; j < list.size()/2; j++) {
						if ((list.get(i%list.size()) == list.get((i+j)%list.size())) && ((i%list.size()) != ((i+j)%list.size()))) {
							if ((i%list.size()) > ((i+j)%list.size())) {
								for (int k = 0; k <= (i%list.size())-((i+j)%list.size()); k++) {
									list.remove((i+j)%list.size());
									rem = true;
								}
							} else {
								for (int k = 0; k <= ((i+j)%list.size())-(i%list.size()); k++) {
									list.remove((i)%list.size());
									rem = true;
								}
							}
							if (rem) {
								pairs++;
								rem = false;
								found = true;
							}
						} else if ((list.get(i%list.size()) == list.get((i-j+list.size())%list.size())) && ((i%list.size()) != ((i-j+list.size())%list.size()))) {
							if ((i%list.size()) > ((i-j+list.size())%list.size())) {
								for (int k = 0; k <= (i%list.size())-((i-j+list.size())%list.size()); k++) {
									list.remove((i-j+list.size())%list.size());
									rem = true;
								}
							} else {
								for (int k = 0; k <= ((i-j+list.size())%list.size())-(i%list.size()); k++) {
									list.remove((i)%list.size());
									rem = true;
								}
							}
							if (rem) {
								pairs++;
								rem = false;
								found = true;
							}
						}
						if (list.size() == 2) {
							if (list.get(0) == list.get(1)) {
								list.remove(0);
								list.remove(0);
								pairs++;
							}
						}
					}
				}
			}
			if (pairs > maxP) {
				maxP = pairs;
			}
			
		}
		return maxP;
	}
}
