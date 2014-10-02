import java.util.LinkedList;

public class Ex3_7 {
	public static void main(String args[]) {
	}

	public static class AnimalQueue {
		LinkedList<Boolean> queue = new LinkedList<Boolean>();
		
		public AnimalQueue() {
			
		}
		
		public void enqueue(boolean isDog) {
			queue.add(isDog);
		}
		
		public boolean dequeueAny(boolean isDog) {
			if (queue.isEmpty()) {
				return false;
			}
			return queue.remove();
		}
		
		public boolean dequeueDog(boolean isDog) {
			if (queue.isEmpty()) {
				return false;
			}
			for (int i = 0; i < queue.size(); i++) {
				if (queue.get(i) == true) {
					return queue.get(i);
				}
			}
			return false;
		}
		
		public boolean dequeueCat(boolean isDog) {
			if (queue.isEmpty()) {
				return false;
			}
			for (int i = 0; i < queue.size(); i++) {
				if (queue.get(i) == false) {
					return queue.get(i);
				}
			}
			return false;
		}

	}
}