public class Ex2_5 {
	public static void main(String args[]) {
		Ex2_5 ex = new Ex2_5();
		Node c1 = new Node(7);
		Node h1 = c1;
		c1 = c1.add(1);
		c1 = c1.add(6);

		Node c2 = new Node(5);
		Node h2 = c2;
		c2 = c2.add(9);
		c2 = c2.add(2);

		printList(h1);
		printList(h2);
		Node h3 = ex.sum(h1, h2);
		printList(h3);
		Node h4 = ex.sumReversed(h1, h2);
		printList(h4);
	}

	public Node sum(Node h1, Node h2) {
		int[] numbers = new int[2];
		int magnitude = 1;
		while (h1 != null && h2 != null) {
			if (h1 != null) {
				numbers[0] += h1.data * magnitude;
				h1 = h1.next;
			}
			if (h2 != null) {
				numbers[1] += h2.data * magnitude;
				h2 = h2.next;
			}
			magnitude *= 10;
		}
		int sum = numbers[0] + numbers[1];
		Node current = new Node(sum % 10);
		Node sumHead = current;
		sum /= 10;
		while (sum > 0) {
			current = current.add(sum % 10);
			sum /= 10;
		}
		return sumHead;
	}

	public Node sumReversed(Node h1, Node h2) {
		int[] numbers = new int[2];
		int magnitude = 1;
		Node c1 = h1;
		while (c1 != null) {
			c1 = c1.next;
			magnitude *= 10;
		}
		c1 = h1;
		while (c1 != null) {
			numbers[0] += c1.data * magnitude;
			c1 = c1.next;
			magnitude /= 10;
		}

		magnitude = 1;
		c1 = h2;
		while (c1 != null) {
			c1 = c1.next;
			magnitude *= 10;
		}
		c1 = h2;
		while (c1 != null) {
			numbers[1] += c1.data * magnitude;
			c1 = c1.next;
			magnitude /= 10;
		}

		int sum = numbers[0] + numbers[1];
		magnitude = (int)(Math.pow(10, (int)(Math.log(sum)/Math.log(2))));
		Node current = new Node(sum / magnitude);
		Node sumHead = current;
		sum -= (sum / magnitude) * magnitude;
		magnitude /= 10;
		while (sum > 0) {
			current = current.add(sum / magnitude);
			sum -= (sum / magnitude) * magnitude;
			magnitude /= 10;
		}
		return sumHead;
	}
	
	public static void printList(Node head) {
		Node temp = head;
		String out = "";
		while (temp != null) {
			out += temp.data + ", ";
			temp = temp.next;
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