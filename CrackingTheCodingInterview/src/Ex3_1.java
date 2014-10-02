public class Ex3_1 {
	public static void main(String args[]) {
		Stack<Integer> s = new Stack<Integer>();
		for (int j = 1; j < 5; j++) {
			s.push(j);
		}
		while (s.size() > 2) {
			System.out.println(s.pop() + "\t min: " + s.getMin());
		}
		System.out.println();
		for (int j = 0; j < 5; j++) {
			s.push(j);
		}
		while (s.size() > 0) {
			System.out.println(s.pop() + "\t min: " + s.getMin());
		}
		System.out.println();
		System.out.println();
		
		Queue<Integer> q = new Queue<Integer>();
		for (int j = 0; j < 5; j++) {
			q.add(j);
		}
		while (q.size() > 2) {
			System.out.print(q.poll() + ", ");
		}
		System.out.println();
		for (int j = 0; j < 5; j++) {
			q.add(j);
		}
		while (q.size() > 0) {
			System.out.print(q.poll() + ", ");
		}
		System.out.println();
		System.out.println();
	}

	public static class Node<E> {
		E e;
		Node<E> next;
		public Node(E e) {
			this.e = e;
		}
	}

	public static class Stack<E extends Comparable<E>> {
		private Node<E> top;
		private Node<E> min;
		private int size = 0;
		
		public Stack() {}
		
		public void push(E e) {
			Node<E> newTop = new Node<E>(e);
			newTop.next = top;
			top = newTop;
			if (min == null) {
				min = top;
			} else if (e.compareTo(min.e) < 0) {
				min.next = min;
				min.e = top.e;
			}
			size++;
		}
		
		public E peek() {
			if (top == null) {
				return null;
			}
			return top.e;
		}
		
		public E pop() {
			if (top == null) {
				return null;
			}
			if (top == min) {
				min = min.next;
			}
			E ret = top.e;
			top = top.next;
			size--;
			return ret;
		}
		
		public int size() {
			return size;
		}
		
		public E getMin() {
			if (min == null) {
				return null;
			}
			return min.e;
		}
	}

	public static class Queue<E> {
		private Node<E> front, back;
		private int size = 0;

		public Queue() {}

		public E poll() {
			if (front == null) {
				return null;
			}
			E ret = front.e;
			front = front.next;
			if (front == null) {
				back = null;
			}
			size--;
			return ret;
		}

		public E peek() {
			if (front == null) {
				return null;
			}
			return front.e;
		}

		public void add(E e) {
			Node<E> newNode = new Node<E>(e);
			if (front == null) {
				front = newNode;
				back = front;
			} else {
				back.next = newNode;
				back = newNode;
			}
			size++;
		}
		
		public int size() {
			return size;
		}
	}
}