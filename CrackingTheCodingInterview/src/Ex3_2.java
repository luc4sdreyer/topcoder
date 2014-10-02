import java.util.ArrayList;
import java.util.Collections;

public class Ex3_2 {
	public static void main(String args[]) throws WrongMinException {
//		Stack<Integer> s = new Stack<Integer>();
//		for (int j = 0; j < 5; j++) {
//			s.push(j);
//		}
//		while (s.size() > 2) {
//			System.out.println(s.pop() + "\t min: " + s.getMin());
//		}
//		System.out.println();
//		for (int j = 0; j < 5; j++) {
//			s.push(j);
//		}
//		while (s.size() > 0) {
//			System.out.println(s.pop() + "\t min: " + s.getMin());
//		}
//		System.out.println();
//		System.out.println();
//		
//		Queue<Integer> q = new Queue<Integer>();
//		for (int j = 0; j < 5; j++) {
//			q.add(j);
//		}
//		while (q.size() > 2) {
//			System.out.print(q.poll() + ", ");
//		}
//		System.out.println();
//		for (int j = 0; j < 5; j++) {
//			q.add(j);
//		}
//		while (q.size() > 0) {
//			System.out.print(q.poll() + ", ");
//		}
//		System.out.println();
//		System.out.println();
		
		testStack();
	}
	
	public static void testStack() throws WrongMinException {
		Stack<Integer> myS = new Stack<Integer>();
		java.util.Stack<Integer> javaS = new java.util.Stack<Integer>();
		
		for (int i = 0; i < 100; i++) {
			int length = (int) (Math.random()*10);
			if (Math.random() < 0.5) {
				for (int j = 0; j < length; j++) {
					int rand = (int) (Math.random()*1000);
					myS.push(rand);
					javaS.push(rand);
					compareMin(myS, javaS);
				}
			} else {
				for (int j = 0; j < length; j++) {
					if (javaS.isEmpty()) {
						break;
					}
					myS.pop();
					javaS.pop();
					compareMin(myS, javaS);
				}
			}
		}
	}

	private static void compareMin(Stack<Integer> myS,
			java.util.Stack<Integer> javaS) throws WrongMinException {
		if (myS.size() > 0 || !javaS.isEmpty()) {
			int min = Integer.MAX_VALUE;
			for (Integer integer : javaS) {
				min = Math.min(min, integer);
			}
			int myMin = myS.getMin();
			if (min != myMin) {
				myS.getAsList();
				System.out.println(myS);
				throw new WrongMinException("Wrong min");
			}
		}
	}
	
	public static class WrongMinException extends Exception {
		private static final long serialVersionUID = 1L;
		public WrongMinException(String message) {
	        super(message);
	    }
	    public WrongMinException(String message, Throwable cause) {
	        super(message, cause);
	    }
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
				min = new Node<E>(top.e);
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
		
		public ArrayList<Node<E>> getAsList() {
			ArrayList<Node<E>> list = new ArrayList<Node<E>>();
			Node<E> current = top;
			while (current != null) {
				list.add(current);
				current = current.next;
			}
			Collections.reverse(list);
			return list;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			ArrayList<Node<E>> list = getAsList();
			sb.append("[");
			for (int i = 0; i < list.size(); i++) {
				if (i != 0) {
					sb.append(", ");
				}
				sb.append(list.get(i).e);
			}
			sb.append("]");
			return sb.toString();
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