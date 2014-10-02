package explicitTypeParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<Integer> integers = new HashSet<>(Arrays.asList(1, 2, 3));
		Set<Double> doubles = new TreeSet<>(Arrays.asList(1.1, 2.2, 3.3));
		Set<Number> numbers = Test.<Number>union(integers, doubles);
		System.out.println(numbers);
	}
	
	public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}

}
