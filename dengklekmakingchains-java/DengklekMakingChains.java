import java.util.ArrayList;
import java.util.Collections;

public class DengklekMakingChains {

	public int maxBeauty(String[] chains) {
		int a = maxBeauty2(chains);
		int b = maxBeauty3(chains);
		if (a > b) {
			return a;
		} else {
			return b;
		}
		
	}
	
	public int maxBeauty2(String[] chains) {
		//get best prelink
		ArrayList<Integer> pre = new ArrayList<Integer>();
		ArrayList<Integer> mid = new ArrayList<Integer>();
		ArrayList<Integer> post = new ArrayList<Integer>();
		for (int i = 0; i < chains.length; i++) {
			if ((chains[i].charAt(0) != '.' && chains[i].charAt(1) != '.') && chains[i].charAt(2) != '.') {
				mid.add(getBeauty(chains[i]));
			} else if (chains[i].charAt(2) != '.') {
				String t = chains[i].charAt(1)+""+chains[i].charAt(2);
				pre.add(getBeauty(t));
			} else if (chains[i].charAt(0) != '.') {
				String t = chains[i].charAt(0)+""+chains[i].charAt(1);
				post.add(getBeauty(t));
			}			
		}

		int beauty =0;
		for (int i = 0; i < mid.size(); i++) {
			beauty += mid.get(i);
		}
		if (beauty == 0) {
			mid.clear();
			for (int i = 0; i < chains.length; i++) {
				for (int j = 0; j < 3; j++) {
					if (chains[i].charAt(j) != '.') {
						mid.add(Integer.parseInt(chains[i].charAt(j)+""));
					}
				}
			}
			if (mid.size() == 0) {
				return 0;
			}
			int a = Collections.max(mid);
			
			int b =0;
			if (pre.size() >0) {
				b += Collections.max(pre);
			}
			if (post.size() >0) {
				b += Collections.max(post);
			}
			if (a> b) {
				return a;
			}else {
				return b;
			}
		} else {
			if (pre.size() >0) {
				beauty += Collections.max(pre);
			}
			if (post.size() >0) {
				beauty += Collections.max(post);
			}
		}

		return beauty;
	}
	public int maxBeauty3(String[] chains) {
		//get best prelink
		ArrayList<Integer> pre = new ArrayList<Integer>();
		ArrayList<Integer> mid = new ArrayList<Integer>();
		ArrayList<Integer> post = new ArrayList<Integer>();
		for (int i = 0; i < chains.length; i++) {
			if ((chains[i].charAt(0) != '.' && chains[i].charAt(1) != '.') && chains[i].charAt(2) != '.') {
				mid.add(getBeauty(chains[i]));
			} else if (chains[i].charAt(0) != '.') {
				String t = chains[i].charAt(0)+""+chains[i].charAt(1);
				post.add(getBeauty(t));
			} else if (chains[i].charAt(2) != '.') {
				String t = chains[i].charAt(1)+""+chains[i].charAt(2);
				pre.add(getBeauty(t));
			} 			
		}

		int beauty =0;
		for (int i = 0; i < mid.size(); i++) {
			beauty += mid.get(i);
		}
		if (beauty == 0) {
			mid.clear();
			for (int i = 0; i < chains.length; i++) {
				for (int j = 0; j < 3; j++) {
					if (chains[i].charAt(j) != '.') {
						mid.add(Integer.parseInt(chains[i].charAt(j)+""));
					}
				}
			}
			if (mid.size() == 0) {
				return 0;
			}
			int a = Collections.max(mid);
			
			int b =0;
			if (pre.size() >0) {
				b += Collections.max(pre);
			}
			if (post.size() >0) {
				b += Collections.max(post);
			}
			if (a> b) {
				return a;
			}else {
				return b;
			}
		} else {
			if (pre.size() >0) {
				beauty += Collections.max(pre);
			}
			if (post.size() >0) {
				beauty += Collections.max(post);
			}
		}

		return beauty;
	}


	private Integer getBeauty(String c) {
		c = c.replace(".", "");
		int b =0;
		for (int i = 0; i < c.length(); i++) {
			b += Integer.parseInt(c.charAt(i)+"");
		}
		return b;
	}



}
