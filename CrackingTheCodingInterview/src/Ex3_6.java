import java.util.ArrayList;
import java.util.Collections;



public class Ex3_6 {
	
	public static class Node<E> {
		public E e;
		public Node<E> next;
		public Node(E e) {
			this.e = e;
		}
	}

	public static class Stack<E> {
		private Node<E> top;

		public Stack() {}

		public void push(E e) {
			Node<E> t = new Node<>(e);
			t.next = top;
			top = t;
		}

		public E peek() {
			return top.e;
		}

		public E pop() {
			if (top == null) {
				return null;
			}
			E ret = top.e;
			top = top.next;
			return ret;
		}

		public boolean isEmpty() {
			return top == null ? true : false;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			ArrayList<E> list = new ArrayList<>();
			Node<E> current = top;
			sb.append('(');
			while (current != null) {
				list.add(current.e);
				current = current.next;
			}
			Collections.reverse(list);
			
			for (int i = 0; i < list.size(); i++) {
				if (i != 0) {
					sb.append(',');
					sb.append(' ');
				}
				sb.append(list.get(i));
			}
			sb.append(')');
			return sb.toString();
		}
	}
	
	public static void stortStack(Stack<Integer> stack) {
		Stack<Integer> maxStack = new Stack<>();

		boolean sorted = false;
		while (!sorted) {
			sorted = true;
			while (!stack.isEmpty()) {
				if (maxStack.isEmpty()) {
					maxStack.push(stack.pop());
				} else {
					int a = stack.pop();
					int b = maxStack.pop();
					if (a <= b) {
						maxStack.push(b);
						maxStack.push(a);
					} else {
						sorted = false;
						maxStack.push(a);
						maxStack.push(b);
					}
				}
			}
			while (!maxStack.isEmpty()) {
				if (stack.isEmpty()) {
					stack.push(maxStack.pop());
				} else {
					int a = maxStack.pop();
					int b = stack.pop();
					if (a > b) {
						stack.push(b);
						stack.push(a);
					} else {
						sorted = false;
						stack.push(a);
						stack.push(b);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Stack<Integer> s = new Stack<>();
		s.push(3);
		s.push(2);
		s.push(5);
		s.push(1);
		s.push(7);
		System.out.println(s);
		stortStack(s);
		System.out.println(s);
		
	}
}