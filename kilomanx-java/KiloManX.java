import java.util.PriorityQueue;

public class KiloManX {

	class Node implements Comparable<Node> {
		public int weapons, shots;
		
		public Node(int weapons, int shots) {
			this.weapons = weapons;
			this.shots = shots;
		}		
		
		public String toString() {
			return "(" + weapons + ", "+ shots + ")";
		}
		
		@Override
		public int compareTo(Node o) {
			Node next = (Node)o;
			if (shots < next.shots) return -1;
			if (shots > next.shots) return 1;
			return 0;
		}
	}
	
	public int leastShots(String[] damageChart, int[] bossHealth) {
		boolean[] visited = new boolean[1 << 16];
		PriorityQueue<Node> q = new PriorityQueue<Node>();
		q.add(new Node(0, 0));
		
		while(!q.isEmpty()) {
			Node top = q.poll();
			
			if (visited[top.weapons]) {
				continue;
			}
			visited[top.weapons] = true;
			
			if (top.weapons == (1 << damageChart.length) -1) {
				return top.shots;
			}
			
			for (int b = 0; b < damageChart.length; b++) {
				if ((top.weapons >> b) == 1) continue;
				int best = bossHealth[b];
				for (int w = 0; w < damageChart.length; w++) {
					if (w != b && ((top.weapons >> w) == 1) && damageChart[w].charAt(b) != '0') {
						//int shots = (int)Math.ceil(bossHealth[b] / (float)(damageChart[b].charAt(w) - '0'));
						int shotsNeeded = bossHealth[b] / (damageChart[w].charAt(b) - '0');
					    if (bossHealth[b] % (damageChart[w].charAt(b) - '0') != 0) shotsNeeded++;
					    best = Math.min(best, shotsNeeded);
					}
				}
				q.add(new Node(top.weapons | 1 << b, top.shots + best));
			}
		}
		
		return 0;
	}

}
