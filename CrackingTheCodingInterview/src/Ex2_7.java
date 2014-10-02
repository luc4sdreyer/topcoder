import java.util.HashSet;

public class Ex2_7 {
	public static void main(String args[]) {
		Ex2_7 ex = new Ex2_7();
		int i = 0;
		Node c1 = new Node(i++);
		Node h1 = c1;
		c1 = c1.add(i++);
		//c1 = c1.add(i);
		//c1 = c1.add(i--);
		c1 = c1.add(i--);
		c1 = c1.add(i--);
		
		printList(h1);
		System.out.println(ex.isPalindrome(h1));
	}

	public boolean isPalindrome(Node h) {
		int len = 0;
		Node current = h;
		while (current != null) {
			current = current.next;
			len++;
		}
		int i = len/2;
		current = h;
		while (i < len) {
			current = current.next;
			i++;
		}
		Node middle = current;

		i = len/2;
		Node previous = null;
		Node temp = null;
		while (i < len) {
			if (current == null) {
				break;
			}
			temp = current.next;
			current.next = previous;
			previous = current;
			current = temp;
			i++;
		}
		
		current = middle;
		i = 0;
		while (i < len/2) {
			if (previous.data != h.data) {
				return false;
			}
			previous = previous.next;
			h = h.next;
			i++;
		}
		return true;
	}
	
	public static void printList(Node head) {
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