public class ValueHistogram {

	public String[] build(int[] values) {
		int[] hist = new int[10]; 
		for (int i = 0; i < values.length; i++) {
			hist[values[i]]++;
		}
		int maxF = 0;
		for (int i = 0; i < hist.length; i++) {
			if (hist[i] > maxF) {
				maxF = hist[i]; 
			}
		}
		StringBuilder sb = new StringBuilder();
		String[] h = new String[maxF+1];
		for (int i = 0; i <= maxF; i++) {
			for (int j = 0; j < hist.length; j++) {
				if (hist[j] > i) {
					sb.append('X');
				} else 
				{
					sb.append('.');	
				}
			}
			//System.out.println(sb.toString());
			h[maxF-i] = sb.toString();
			sb = new StringBuilder();
		}
		
		return h;
	}

}
