import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;

public class ThreeTeleports {

	public int shortestDistance(int xMe, int yMe, int xHome, int yHome, String[] teleports) {
		HashSet<Point> visited = new HashSet<>();
		HashSet<Point> points = new HashSet<>();
		HashMap<Point, Point> pointLookup = new HashMap<>();
		for (int i = 0; i < teleports.length; i++) {
			String[] t = teleports[i].split(" ");
			Point p1 = new Point(Integer.parseInt(t[0]), Integer.parseInt(t[1]));
			Point p2 = new Point(Integer.parseInt(t[2]), Integer.parseInt(t[3]));
			points.add(p1);
			points.add(p2);
			pointLookup.put(p1, p2);
			pointLookup.put(p2, p1);
		}
		int time = 0;
		Point position = new Point(xMe, yMe);
		Point target = new Point(xHome, yHome);		
		points.add(target);
		return (int) dfs(visited, points, pointLookup, time, position, target);
	}

	private long dfs(HashSet<Point> visited, HashSet<Point> points, HashMap<Point, Point> pointLookup, long time, Point position, Point target) {
		if (visited.contains(target) || visited.size() == points.size()) {
			return time;
		} else {
			long min = 1000000000000L;
			for (Point p : points) {
				if (!visited.contains(p)) {
					HashSet<Point> nextVisited = new HashSet<>(visited);
					nextVisited.add(p);
					long timeDelta = (long) (Math.abs(position.getX() - p.getX()) + Math.abs(position.getY() - p.getY()));
					if (pointLookup.containsKey(position) && pointLookup.get(position).equals(p)) {
						timeDelta = Math.min(timeDelta, 10);
					}
					min = Math.min(min, dfs(nextVisited, points, pointLookup, timeDelta, p, target));
				}
			}
			return time + min;
		}	
	}

}
