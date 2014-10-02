import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AlternateColors {

	public String getColor(long r, long g, long b, long k) {
		ArrayList<Long> s = new ArrayList<Long>();
		s.add(r);
		s.add(g);
		s.add(b);
		Collections.sort(s);
		
		long s1 = 3*s.get(0);
		long s2 = 2*(s.get(1)-s.get(0));
		long s3 = s.get(2)-s.get(1);
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("RED");
		list.add("GREEN");
		list.add("BLUE");
		
		if (k <= s1) {
			int idx = (int) (k % 3)-1;
			//int idx = (int) ((k) % (s1/3))-1;
			idx = idx < 0 ? 2 : idx;
			return list.get(idx);
		}
		
		if (r == s.get(0)) {
			list.remove(0);				
		} else if (g == s.get(0)) {
			list.remove(1);
		} else {
			list.remove(2);
		}
	
		if (k <= s2+s1) {
			int idx = ((k-s1) % 2 == 0) ? 1 : 0;
			return list.get(idx);
		} else {
			String longest;
			if (r == s.get(2)) {
				longest = "RED";
			} else if (g == s.get(2)) {
				longest = "GREEN";
			} else {
				longest = "BLUE";
			}
			return longest;
		}		
	}
}
