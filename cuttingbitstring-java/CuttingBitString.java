import java.util.PriorityQueue;

public class CuttingBitString {
	
	public class Element implements Comparable<Element>{
		public int pos, cost, steps;

		public Element(int pos, int cost, int steps) {
			super();
			this.pos = pos;
			this.cost = cost;
			this.steps = steps;
		}

		@Override
		public int compareTo(Element e) {
			if (this.cost < e.cost) return -1;
			if (this.cost > e.cost) return 1;
			return 0;
		}		
	} 

	public int getmin(String S) {
		PriorityQueue<Element> q= new PriorityQueue<Element>();
		q.add(new Element(0, 0, 0));
		boolean[][] visited = new boolean[S.length()+1][2500];
		
		while (!q.isEmpty()) {
			Element top = q.poll();
			if (visited[top.pos][top.cost]) {
				continue;
			}
			visited[top.pos][top.cost] = true;
			
			if (top.pos == S.length()) {
				return top.steps;
			}
			
			int i = 0;
			long f = 1; 
			while(i < 28) {
				String bin = Long.toBinaryString(f);
				int index = top.pos + bin.length();
				if (index <= S.length()) {
					if (S.substring(top.pos, index).equals(bin)) {
						int cost = S.length() - bin.length() + 1;
						if (!visited[index][cost]) {
							q.add(new Element(index, cost, top.steps + 1));
						}
					}
				} else {
					break;
				}
				i++;
				f *= 5;
			}
			
		}
		return -1;
	}

}
