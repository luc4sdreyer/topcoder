import java.util.Arrays;

// Can you spot the "memory leak"?
public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}
	public Object pop() throws Exception {
		if (size == 0)
			throw new Exception();
		return elements[--size];
	}
	/**
	 * Ensure space for at least one more element, roughly
	 * doubling the capacity each time the array needs to grow.
	 */
	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}
	
	public static void main(String[] args) throws Exception {
		Stack s = new Stack();
		long i = 0;
		while(true) {
			s.push(new Integer(0));
			s.pop();
			i++;
			if (i % 100000000L == 0) {
				System.out.println("GB allocated: " + (i / 1000000000D * 8));
			}
		}
	}
}