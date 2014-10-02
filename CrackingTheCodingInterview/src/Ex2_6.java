import java.util.HashSet;

public class Ex2_6 {
	public static void main(String args[]) {
		Ex2_6 ex = new Ex2_6();
		int i = 0;
		Node c1 = new Node(i++);
		Node h1 = c1;
		c1 = c1.add(i++);
		c1 = c1.add(i++);
		c1 = c1.add(i++);
		Node h2 = c1;
		c1 = c1.add(i++);
		c1 = c1.add(i++);
		c1 = c1.add(i++);
		
		printList(h1);
		c1.next = h2;

		printList(h1);
		printListNoData(h1);
	}
	
	public static void printListNoData(Node head) {
		HashSet<Node> s = new HashSet<Node>();
		Node temp = head;
		String out = "";
		while (temp != null) {
			if (s.contains(temp)) {
				out += "Loop back to " + temp;
				break;
			} else {
				s.add(temp);
				out += temp.data + ", ";
				temp = temp.next;
			}
		}
		System.out.println(out);
	}
	
	public static void printList(Node head) {
		HashSet<Integer> s = new HashSet<Integer>();
		Node temp = head;
		String out = "";
		while (temp != null) {
			if (s.contains(temp.data)) {
				out += "Loop back to " + temp.data;
				break;
			} else {
				s.add(temp.data);
				out += temp.data + ", ";
				temp = temp.next;
			}
		}
		System.out.println(out);
	}

	public static class Node {
		int data;
		Node next = null;
		public Node(int data) {
			this.data = data;
		}
		public Node add(int newData) {
			Node newNode = new Node(newData);
			this.next = newNode;
			return newNode;
		}
	}
}