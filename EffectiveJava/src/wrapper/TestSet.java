package wrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class TestSet {

	public static void main(String[] args) {
		//Stack<Number> stack = new Stack<>();
		//stack.pop();
		BrokenInheritanceSet<Number> bSet = new BrokenInheritanceSet<>();
		
		HashSet<Number> set = new HashSet<>();
		InstrumentedSet<Number> iSet = new InstrumentedSet<>(set);
		
		ArrayList<AddCounterSet<Number>> sets = new ArrayList<>();
		sets.add(bSet);
		sets.add(iSet);
		
		for (AddCounterSet<Number> s : sets) {
			s.addAll(Arrays.asList(0, 1, 2));
			System.out.println(s.getClass().getSimpleName() + " size: " + s.getAddCount());
		}
	}
}
